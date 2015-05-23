package com.nulldev.jvn.debug;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.nulldev.jvn.JVNConfig;
import com.nulldev.jvn.TickManager;
import com.nulldev.jvn.locale.JVNLocale;

public class DebugUI {
	private static JVNLogger logger = new JVNLogger("DebugUI");
	static boolean debugConsoleKeysPressed = false;
	static boolean debugConsoleActive = false;
	static SpriteBatch batch = new SpriteBatch();
	static ShapeRenderer sr = new ShapeRenderer();
	static BitmapFont debugConsoleFont = new BitmapFont();
	static ArrayList<Character> typedCharacters = new ArrayList<Character>();

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
				//Press enter yeah!
				if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
					if(typedCharacters.size() != 0) {
						String command = getStringRepresentation(typedCharacters);
						typedCharacters.clear();
						//No longer needed
						//logger.info(String.format(JVNLocale.s("debugConsoleIssueCommand"), command));
						executeDebugCommand(command);
					}
				}
			}
		}
	}
	
	//Execute debug commands
	public static void executeDebugCommand(String command) {
		String[] splitCommands = command.split(" ");
		//Get the TPS (FPS)
		if(splitCommands[0].equalsIgnoreCase("GETTPS")) {
			logger.info(JVNLocale.s("getTpsResult") + TickManager.tps);
		} else {
			//Wut command is that?
			logger.warning(JVNLocale.s("debugConsoleUnknownCommand"));
		}
	}
	
	//Check the keybinds
	public static void checkKeyBinds() {
		//Debug console
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
		//Render the text box
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

		//Render the actual text
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		debugConsoleFont.setColor(0, 0, 0, 1);
		debugConsoleFont.draw(batch, "debug> " + getStringRepresentation(typedCharacters) + "_",
				camera.viewportWidth/-2+3,
				camera.viewportHeight/-2+16);
		batch.end();

		//Render output
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
	public static String cheatyTextInputProbe() {
		for(int key = Input.Keys.A; key <= Input.Keys.Z; key++) {
			if(Gdx.input.isKeyJustPressed(key)) {
				//Caught a key!
				char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
				String typed = Character.toString(alphabet[key-Input.Keys.A]);
				//TODO Implement capslock somehow
				if(isShift()) {
					typed = typed.toUpperCase();
				} else {
					typed = typed.toLowerCase();
				}
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
			if(isShift()) {
				return "<";
			} else {
				return ",";
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.PERIOD)) {
			if(isShift()) {
				return ">";
			} else {
				return ".";
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.SLASH)) {
			if(isShift()) {
				return "?";
			} else {
				return "/";
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.APOSTROPHE)) {
			if(isShift()) {
				return "\"";
			} else {
				return "'";
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.SEMICOLON)) {
			if(isShift()) {
				return ":";
			} else {
				return ";";
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET)) {
			if(isShift()) {
				return "{";
			} else {
				return "[";
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET)) {
			if(isShift()) {
				return "}";
			} else {
				return "]";
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET)) {
			if(isShift()) {
				return "{";
			} else {
				return "[";
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSLASH)) {
			if(isShift()) {
				return "|";
			} else {
				//Don't get confused here :/
				return "\\";
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
			if(isShift()) {
				return "_";
			} else {
				return "-";
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)) {
			if(isShift()) {
				return "+";
			} else {
				return "=";
			}
		}
		return "";
	}

	//From stackoverflow :/, I'm too lazy
	public static String getStringRepresentation(ArrayList<Character> list)
	{    
		StringBuilder builder = new StringBuilder(list.size());
		for(Character ch: list)
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
