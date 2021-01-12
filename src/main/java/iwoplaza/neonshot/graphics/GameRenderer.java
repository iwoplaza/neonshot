package iwoplaza.neonshot.graphics;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.world.World;
import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.Window;
import iwoplaza.meatengine.assets.IAssetConsumer;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.neonshot.graphics.entity.IGameRenderContext;
import org.joml.Matrix4f;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class GameRenderer implements IDisposable, IAssetConsumer
{

    private static final float Z_NEAR = -10F;
    private static final float Z_FAR = 1000F;

    private Matrix4f projectionMatrix;
    private Matrix4f modelViewMatrix;

    private WorldRenderer worldRenderer;

    public GameRenderer()
    {
    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        this.worldRenderer.registerAssets(loader);
    }

    public void init(Window window, World world) throws Exception
    {
        this.modelViewMatrix = new Matrix4f().identity();
        this.onResized(window, world);

        this.worldRenderer = new WorldRenderer(window, world);
    }

    private void onResized(Window window, World world)
    {
        final int windowWidth = window.getWidth();
        final int windowHeight = window.getHeight();
        int cameraX = 0;
        int cameraY = 0;

        this.projectionMatrix = new Matrix4f().ortho(0, windowWidth, 0, windowHeight, Z_NEAR, Z_FAR);
        this.modelViewMatrix.identity().
                translate(windowWidth/2F, windowHeight/2F, 0F).
                scale(getAppropriateScaling(window, world))
                .translate(-cameraX, -cameraY, 0F);
    }

    private void clear()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(IGameRenderContext context, Window window, World world) throws Exception
    {
        this.clear();

        if (window.isResized())
        {
            this.onResized(window, world);
            window.setResized(false);
        }

        this.worldRenderer.render(context, window, world);
    }

    private int getAppropriateScaling(Window window, World world)
    {
        return 1;
    }

    @Override
    public void dispose()
    {
        this.worldRenderer.dispose();
    }

    public Matrix4f getProjectionMatrix()
    {
        return projectionMatrix;
    }

    public Matrix4f getModelViewMatrix()
    {
        return modelViewMatrix;
    }

    public WorldRenderer getWorldRenderer()
    {
        return this.worldRenderer;
    }
}
