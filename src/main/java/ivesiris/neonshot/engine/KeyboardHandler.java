package ivesiris.neonshot.engine;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyboardHandler extends GLFWKeyCallback
{

    private GameEngine gameEngine;

    public KeyboardHandler(GameEngine gameEngine)
    {
        this.gameEngine = gameEngine;
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods)
    {
        if (action == GLFW_PRESS)
            gameEngine.handleKeyPressed(key, mods);
        else if (action == GLFW_RELEASE)
            gameEngine.handleKeyReleased(key, mods);
    }

}
