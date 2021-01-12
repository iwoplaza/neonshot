package iwoplaza.neonshot.graphics;

import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.Window;
import iwoplaza.meatengine.assets.IAssetConsumer;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.shader.ShaderHelper;
import iwoplaza.meatengine.world.World;
import iwoplaza.neonshot.CommonShaders;
import org.joml.Matrix4f;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class GameRenderer implements IDisposable, IAssetConsumer
{

    private static final float Z_NEAR = -10F;
    private static final float Z_FAR = 1000F;

    private Matrix4f modelViewMatrix;

    private final World world;
    private final WorldRenderer worldRenderer;

    public GameRenderer(World world)
    {
        this.world = world;
        this.modelViewMatrix = new Matrix4f();
        this.worldRenderer = new WorldRenderer(world);
    }

    public void init(Window window)
    {
        this.onResized(window);

        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        this.worldRenderer.registerAssets(loader);
    }

    public void onResized(Window window)
    {
        final int windowWidth = window.getWidth();
        final int windowHeight = window.getHeight();

        this.modelViewMatrix.identity().translate(windowWidth / 2F, windowHeight / 2F, 0);
        GlStack.MAIN.projectionMatrix.identity().ortho(0, windowWidth, 0, windowHeight, Z_NEAR, Z_FAR);

        ShaderHelper.operateOnShader(CommonShaders.tileShader, shader -> {
            shader.setProjectionMatrix(GlStack.MAIN.projectionMatrix);
            shader.setDiffuseTexture(0);
        });
    }

    private void clear()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(IGameRenderContext context, Window window, World world) throws Exception
    {
        this.clear();

        GlStack.MAIN.set(modelViewMatrix);

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
}
