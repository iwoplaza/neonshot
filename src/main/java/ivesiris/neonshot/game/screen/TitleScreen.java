package ivesiris.neonshot.game.screen;

import ivesiris.neonshot.engine.Window;
import ivesiris.neonshot.engine.graphics.GlStack;
import ivesiris.neonshot.engine.screen.IScreen;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;

public class TitleScreen implements IScreen
{

    private static final int WIDTH = 448;
    private static final int HEIGHT = 224;

    private Matrix4f modelViewMatrix;

    @Override
    public void init(Window window) throws Exception
    {
        float[] positions = new float[] {
                0, 128,
                0, 0,
                512, 0,
                512, 128,
        };
        float[] texCoords = new float[] {
                0.0F, 0.0F,
                0.0F, 1.0F,
                1.0F, 1.0F,
                1.0F, 0.0F,
        };
        int[] indices = new int[] {
                0, 1, 3, 3, 1, 2,
        };

        this.modelViewMatrix = new Matrix4f();

        this.onResized(window);
    }

    private void onResized(Window window)
    {
        final int windowWidth = window.getWidth();
        final int windowHeight = window.getHeight();

        GlStack.MAIN.projectionMatrix.identity().ortho(0, windowWidth, 0, windowHeight, -1, 1000);
        final float scale = this.getAppropriateScaling(window);
        this.modelViewMatrix.identity().translate(windowWidth / 2F, windowHeight / 2F, 0).scale(scale).translate(-WIDTH / 2F, -HEIGHT / 2F, 0);
    }

    @Override
    public void update()
    {

    }

    @Override
    public void updatePreFrame(Window window)
    {

    }

    @Override
    public void render(float deltaTime, Window window)
    {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);

        if (window.isResized())
        {
            this.onResized(window);
            window.setResized(false);
        }

        glViewport(0, 0, window.getWidth(), window.getHeight());
        window.setClearColor(0, 0, 0, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDepthFunc(GL_ALWAYS);

        GlStack.MAIN.set(modelViewMatrix);

        Matrix4f matrix = new Matrix4f(modelViewMatrix);
        matrix.translate(WIDTH / 2F - 512 / 2F, HEIGHT - 128, 0);
    }

    @Override
    public void cleanUp()
    {
    }

    @Override
    public void handleKeyPressed(int key, int mods)
    {
    }

    @Override
    public void handleKeyReleased(int key, int mods)
    {
    }

    private int getAppropriateScaling(Window window)
    {
        int xScale = window.getWidth() / WIDTH;
        int yScale = window.getHeight() / HEIGHT;

        return Math.max(1, Math.min(xScale, yScale));
    }

}
