package iwoplaza.neonshot.ui.game;

import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.Window;
import iwoplaza.meatengine.graphics.Drawable;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.StaticText;
import iwoplaza.meatengine.graphics.mesh.Mesh;
import iwoplaza.meatengine.graphics.shader.core.FlatShader;
import iwoplaza.meatengine.helper.MeshHelper;
import iwoplaza.neonshot.CommonFonts;
import iwoplaza.neonshot.CommonShaders;
import org.joml.Matrix4f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;

public class FinishScreen implements IDisposable
{

    private final Matrix4f modelViewMatrix;

    // Resources
    private final StaticText mainLabel;
    private final StaticText pressEnterToContinue;
    private final Drawable<FlatShader> background;

    private final IContinueAction action;

    public FinishScreen(String mainTitle, IContinueAction action)
    {
        this.modelViewMatrix = new Matrix4f().identity();
        this.mainLabel = new StaticText(CommonShaders.textShader, CommonFonts.georgia, mainTitle);
        this.mainLabel.setPosition(-this.mainLabel.getTextWidth() / 2.0f, 0);
        this.pressEnterToContinue = new StaticText(CommonShaders.textShader, CommonFonts.georgia, "Press ENTER to continue.");
        this.pressEnterToContinue.setPosition(-this.pressEnterToContinue.getTextWidth() / 2.0f, 0);
        this.background = new Drawable<>(MeshHelper.createFlatRectangle(1, 1), CommonShaders.flatShader);

        this.action = action;
    }

    @Override
    public void dispose()
    {
        this.mainLabel.dispose();
        this.pressEnterToContinue.dispose();
        this.background.dispose();
    }

    public void handleKeyPressed(int keyCode, int mods)
    {
        if (keyCode == GLFW_KEY_ENTER)
        {
            this.action.perform();
        }
    }

    public void render(IEngineContext ctx, Window window)
    {
        GlStack.MAIN.set(this.modelViewMatrix);

        GlStack.push();

        GlStack.scale(window.getWidth(), window.getHeight(), 1);

        FlatShader shader = this.background.getShader();
        shader.bind();
        shader.getColor().set(0, 0, 0, 0.7f);

        this.background.draw();

        GlStack.pop();

        // Rendering the labels

        GlStack.push();
        GlStack.translate(window.getWidth() / 2.0f, window.getHeight() / 2.0f + 20, 0);
        GlStack.scale(2);
        this.mainLabel.render();
        GlStack.pop();

        GlStack.push();
        GlStack.translate(window.getWidth() / 2.0f, window.getHeight() / 2.0f, 0);
        this.pressEnterToContinue.render();
        GlStack.pop();
    }

    @FunctionalInterface
    public interface IContinueAction
    {
        void perform();
    }
}
