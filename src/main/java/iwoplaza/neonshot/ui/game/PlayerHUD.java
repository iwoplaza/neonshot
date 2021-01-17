package iwoplaza.neonshot.ui.game;

import iwoplaza.meatengine.Window;
import iwoplaza.meatengine.graphics.Color;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.StaticText;
import iwoplaza.meatengine.ui.UIItem;
import iwoplaza.meatengine.world.IPlayerEntity;
import iwoplaza.neonshot.CommonFonts;
import iwoplaza.neonshot.CommonShaders;
import iwoplaza.neonshot.graphics.HealthBarRenderer;
import org.joml.Matrix4f;

public class PlayerHUD extends UIItem<IGameRenderContext>
{
    private final Matrix4f modelViewMatrix;
    private final IPlayerEntity player;
    private final HealthBarRenderer.HealthBarSpec healthBarSpec;
    protected final StaticText healthLabel;

    private int uiScale = 1;

    public PlayerHUD(IPlayerEntity player)
    {
        this.modelViewMatrix = new Matrix4f();
        this.player = player;
        this.healthBarSpec = new HealthBarRenderer.HealthBarSpec(
                true,
                new Color(1, 0.2f, 0.2f, 1.0f),
                new Color(1, 0.7f, 0.2f, 1.0f)
        );

        this.healthLabel = new StaticText(CommonShaders.textShader, CommonFonts.georgia, "Health");
        this.healthLabel.setPosition(0, 0);
    }

    public void onResized(Window window)
    {
        final int windowWidth = window.getWidth();

        this.uiScale = windowWidth > 1000 ? 3 : 2;

        this.modelViewMatrix.identity().translate(10, 10, 0).scale(uiScale);
    }

    @Override
    public void render(IGameRenderContext ctx)
    {
        GlStack.MAIN.set(this.modelViewMatrix);

        HealthBarRenderer.INSTANCE.draw(ctx, player, healthBarSpec);

        GlStack.push();
        GlStack.translate(2, 12, 0);
        GlStack.scale(0.5f);
        this.healthLabel.render();
        GlStack.pop();

        super.render(ctx);
    }
}
