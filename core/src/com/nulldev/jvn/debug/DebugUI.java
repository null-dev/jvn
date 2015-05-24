package com.nulldev.jvn.debug;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.nulldev.jvn.JVN;
import com.nulldev.jvn.JVNConfig;
import com.nulldev.jvn.TickManager;
import com.nulldev.jvn.graphics.DrawableActor;
import com.nulldev.jvn.graphics.JVNCoordinate;
import com.nulldev.jvn.graphics.Keyframer;
import com.nulldev.jvn.locale.JVNLocale;

/*
 * Simple debug console that can be pulled up by tapping the "MENU" button on Android or holding "CTRL+GRAVE" on PC
 */

public class DebugUI {
	private static JVNLogger logger = new JVNLogger("DebugUI");
	static boolean debugConsoleKeysPressed = false;
	static boolean debugConsoleActive = false;
	static SpriteBatch batch = new SpriteBatch();
	static ShapeRenderer sr = new ShapeRenderer();
	static BitmapFont debugConsoleFont = new BitmapFont();
	static ArrayList<Character> typedCharacters = new ArrayList<Character>();

	static ArrayList<String> previousCommands = new ArrayList<String>();
	static int commandIndex = -2;
	static ArrayList<Character> currentCommand = new ArrayList<Character>();

	static boolean instructionTracingActive = false;
	static boolean methodCallTracingActive = false;

	//The debug loop that should be called every render...
	public static void debugLoop(OrthographicCamera camera) {
		//EVERYTHING should be inside this if statement
		if(JVNConfig.readBoolean("debug")) {
			checkKeyBinds();
			//Render the debug console if it is toggled...
			if(debugConsoleActive) {
				renderDebugConsole(camera);
				for(char typedCharacter : cheatyTextInputProbe().toCharArray()) {
					typedCharacters.add(typedCharacter);
				}
				//Allow removing characters
				if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)
						&& typedCharacters.size() != 0) {
					typedCharacters.remove(typedCharacters.size()-1);
				}
				//Cycle through commands
				if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
					if(commandIndex == -2) {
						commandIndex = previousCommands.size();
						//Stupid warning...
						currentCommand = (ArrayList<Character>) typedCharacters.clone();
					}
					commandIndex--;
					if(commandIndex == -1) {
						commandIndex = 0;
					}
					
					typedCharacters.clear();
					for (char c : previousCommands.get(commandIndex).toCharArray()) {
						typedCharacters.add(c);
					}
				}
				if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
					if(commandIndex != -2) {
						commandIndex++;
						if(commandIndex == previousCommands.size()) {
							commandIndex = -2;
							//Stupid warning...
							typedCharacters = (ArrayList<Character>) currentCommand.clone();
						} else {
							typedCharacters.clear();
							for (char c : previousCommands.get(commandIndex).toCharArray()) {
								typedCharacters.add(c);
							}
						}
					}
				}
				//Press enter yeah!
				if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
					if(typedCharacters.size() != 0) {
						String command = getStringRepresentation(typedCharacters);
						typedCharacters.clear();
						//No longer needed
						//logger.info(String.format(JVNLocale.s("debugConsoleIssueCommand"), command));
						executeDebugCommand(command);
						//Reset command index
						commandIndex = -2;
					}
				}
			}
		}
	}

	//Execute debug commands
	public static void executeDebugCommand(String command) {
		//Add this command to previous commands
		previousCommands.add(command);
		String[] splitCommands = command.split(" ");
		//Get the TPS (FPS)
		if(splitCommands[0].equalsIgnoreCase("GETTPS")) {
			logger.info(JVNLocale.s("getTpsResult") + TickManager.tps);
			//Spawn an actor just for testing
		} else if(splitCommands[0].equalsIgnoreCase("TESTACTOR")) {
			DrawableActor tempActor = new DrawableActor(new Texture(Gdx.files.internal("img/icon_white.png")));
			tempActor.setScale(0.5f);
			Keyframer tempKeyframer = new Keyframer();
			tempActor.addKeyframer(tempKeyframer);
			//You must add the keyframer to an actor first, then keyframe coords and stuff
			tempKeyframer.keyframeCoordinate(new JVNCoordinate(JVN.camera.viewportWidth,0), 2000);
			tempKeyframer.keyframeOpacity(0f, 2000);
			tempKeyframer.keyframeRotation(90, 2000);
			tempKeyframer.keyframeScale(2f, 2000);
			JVN.actorList.put(tempActor.getZIndex(), tempActor);
		} else if(splitCommands[0].equalsIgnoreCase("GC")) {
			logger.info(JVNLocale.s("garbageCollectInfo"));
			System.gc();
			//Trace instruction calls
		} else if(splitCommands[0].equalsIgnoreCase("TRACEINSTRUCTIONS")) {
			instructionTracingActive = !instructionTracingActive;
			Runtime.getRuntime().traceInstructions(instructionTracingActive);
			logger.info(String.format(JVNLocale.s("traceInstructionsInfo"),
					instructionTracingActive));
			//Trace method calls
		} else if(splitCommands[0].equalsIgnoreCase("TRACEMETHODCALLS")) {
			methodCallTracingActive = !methodCallTracingActive;
			Runtime.getRuntime().traceMethodCalls(methodCallTracingActive);
			logger.info(String.format(JVNLocale.s("traceMethodCallsInfo"),
					methodCallTracingActive));
		} else {
			//Wut command is that?
			logger.warning(JVNLocale.s("debugConsoleUnknownCommand"));
		}
	}

	//Check the keybinds
	public static void checkKeyBinds() {
		//Debug console
		//Can be activated by tapping the "MENU" button on Android or holding "CTRL+GRAVE" on PC
		if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)
				|| Gdx.input.isKeyPressed(Input.Keys.MENU)){
			if((Gdx.input.isKeyPressed(Input.Keys.GRAVE)
					|| Gdx.input.isKeyPressed(Input.Keys.MENU))
					&& !debugConsoleKeysPressed) {
				debugConsoleKeysPressed = true;
				if(debugConsoleActive) {
					debugConsoleActive = false;
					logger.info(JVNLocale.s("debugOff"));
					Gdx.input.setOnscreenKeyboardVisible(false);
					//Reset command index
					commandIndex = -2;
				} else {
					debugConsoleActive = true;
					logger.info(JVNLocale.s("debugOn"));
					Gdx.input.setOnscreenKeyboardVisible(true);
				}
			}
		}

		//Check if the debug keys are released
		if(debugConsoleKeysPressed
				&& (!Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)
						|| !Gdx.input.isKeyPressed(Input.Keys.GRAVE))) {
			debugConsoleKeysPressed = false;
		}
	}

	//Render debug console
	public static void renderDebugConsole(OrthographicCamera camera) {
		//Render input box background
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		sr.setProjectionMatrix(camera.combined);
		sr.begin(ShapeType.Filled);
		sr.setColor(0.9f, 0.9f, 0.9f, 0.7f);
		sr.rect(camera.viewportWidth/-2,
				camera.viewportHeight/-2,
				Gdx.graphics.getWidth(), 20);
		sr.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);

		//Render typed text for the input box
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		debugConsoleFont.setColor(0, 0, 0, 1);
		debugConsoleFont.draw(batch, "debug> " + getStringRepresentation(typedCharacters) + "_",
				camera.viewportWidth/-2+3,
				camera.viewportHeight/-2+16);
		batch.end();

		//Render program log
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		//White please :)
		debugConsoleFont.setColor(1, 1, 1, 1);
		int increment = 16;
		int firstCord = 35-increment;
		int maxLines = (int) ((camera.viewportHeight-35)/16/2);
		for(int i = 1; i <= maxLines; i++) {
			if(i <= JVNLogger.programOutput.size()) {
				String line = JVNLogger.programOutput.get(JVNLogger.programOutput.size()-i);
				if(line.startsWith("INFO")) {
					debugConsoleFont.setColor(Color.GREEN);
					line = line.replaceFirst("INFO", "");
				} else if(line.startsWith("WARN")) {
					debugConsoleFont.setColor(Color.YELLOW);
					line = line.replaceFirst("WARN", "");
				} else if(line.startsWith("SEVR")) {
					debugConsoleFont.setColor(Color.RED);
					line = line.replaceFirst("SEVR", "");
				}
				debugConsoleFont.draw(batch, line,
						camera.viewportWidth/-2+3,
						camera.viewportHeight/-2+firstCord+i*increment);
			}
		}
		batch.end();
	}	

	//Print device info
	//Will be removed later probably
	//TODO REMOVE
	public static void printDeviceInfo() {
		logger.info("Printing device info...");
		//Screen height and width
		logger.info("Screen height: " + Gdx.graphics.getHeight());
		logger.info("Screen width: " + Gdx.graphics.getWidth());
	}

	//Dispose
	public static void dispose() {
		batch.dispose();
		sr.dispose();
		debugConsoleFont.dispose();
	}

	//Cheaty and hardcoded text input :)
	//Catches text input for the debug console
	public static String cheatyTextInputProbe() {
		for(int key = Input.Keys.A; key <= Input.Keys.Z; key++) {
			if(Gdx.input.isKeyJustPressed(key)) {
				//Caught a key!
				char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
				String typed = Character.toString(alphabet[key-Input.Keys.A]);
				//TODO Implement capslock somehow
				typed = isShift() ? typed.toUpperCase() : typed.toLowerCase();
				return typed;
			}
		}
		for(int key = Input.Keys.NUM_0; key <= Input.Keys.NUM_9; key++) {
			if(Gdx.input.isKeyJustPressed(key)) {
				//Caught a key!
				String typed = Integer.toString(key-Input.Keys.NUM_0);
				//TODO Implement capslock somehow
				if(isShift()) {
					if(key-Input.Keys.NUM_0 == 0) {
						typed = ")";
					} else {
						char[] special = "!@#$%^&*(".toCharArray();
						typed = Character.toString(special[key-Input.Keys.NUM_0-1]);
					}
				}
				return typed;
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			return " ";
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.COMMA)) {
			return isShift() ? "<" : ",";
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.PERIOD)) {
			return isShift() ? ">" : ".";
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.SLASH)) {
			return isShift() ? "?" : "/";
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.APOSTROPHE)) {
			return isShift() ? "\"" : "'";
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.SEMICOLON)) {
			return isShift() ? ":" : ";";
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET)) {
			return isShift() ? "{" : "[";
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET)) {
			return isShift() ? "}" : "]";
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET)) {
			return isShift() ? "{" : "[";
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSLASH)) {
			//Don't get confused here :/
			return isShift() ? "|" : "\\";
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
			return isShift() ? "_" : "-";
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)) {
			return isShift() ? "+" : "=";
		}
		return "";
	}

	//From stackoverflow :/, I'm too lazy
	public static String getStringRepresentation(ArrayList<Character> list)
	{    
		StringBuilder builder = new StringBuilder(list.size());
		for(Character ch : list)
		{
			builder.append(ch);
		}
		return builder.toString();
	}

	//Is shift key down?
	public static boolean isShift() {
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)
				|| Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
			return true;
		} else {
			return false;
		}
	}
}
