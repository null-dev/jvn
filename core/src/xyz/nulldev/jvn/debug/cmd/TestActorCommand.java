package xyz.nulldev.jvn.debug.cmd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import xyz.nulldev.jvn.JVN;
import xyz.nulldev.jvn.debug.ConsoleCommand;
import xyz.nulldev.jvn.graphics.JVNCoordinate;
import xyz.nulldev.jvn.graphics.Keyframer;
import xyz.nulldev.jvn.graphics.SimpleTextureActor;

/**
 * Project: jvn
 * Created: 13/11/15
 * Author: nulldev
 */
public class TestActorCommand extends ConsoleCommand {

    public TestActorCommand(String command) {
        super(command);
    }

    @Override
    public void invoke(String... args) {
        SimpleTextureActor tempActor = new SimpleTextureActor(new Texture(Gdx.files.internal("img/icon_white.png")));
        Keyframer keyframer = tempActor.getKeyframer();
        tempActor.setScale(0.5f);
        //You must add the keyframer to an actor first, then keyframe coords and stuff
        keyframer.keyframeCoordinate(new JVNCoordinate(JVN.CAMERA.position.x,0), 2000);
        keyframer.keyframeOpacity(0f, 2000);
        keyframer.keyframeRotation(90, 2000);
        keyframer.keyframeScale(2f, 2000);
        JVN.ACTOR_LIST.put(tempActor.getZIndex(), tempActor);
    }
}
