package iwoplaza.neonshot.ui.game;

import iwoplaza.meatengine.Window;
import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetConsumer;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.util.Color;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.StaticText;
import iwoplaza.meatengine.graphics.sprite.Sprite;
import iwoplaza.meatengine.ui.UIItem;
import iwoplaza.neonshot.CommonFonts;
import iwoplaza.neonshot.CommonShaders;
import iwoplaza.neonshot.Statics;
import iwoplaza.neonshot.graphics.HealthBarRenderer;
import iwoplaza.neonshot.powerup.Powerup;
import iwoplaza.neonshot.world.entity.PlayerEntity;
import org.joml.Matrix4f;
import org.joml.Vector2ic;

import java.io.IOException;

public class PlayerHUD extends UIItem<IGameRenderContext> implements IAssetConsumer
{
    private final boolean topRight;
    private final Matrix4f modelViewMatrix;
    private final HealthBarRenderer.HealthBarSpec healthBarSpec;
    protected final StaticText healthLabel;
    protected final StaticText powerupsLabel;
    protected Sprite powerupSprite;

    private PlayerEntity player;

    public PlayerHUD(boolean topRight)
    {
        this.topRight = topRight;
        this.modelViewMatrix = new Matrix4f();
        this.healthBarSpec = new HealthBarRenderer.HealthBarSpec(
                true,
                new Color(1, 0.2f, 0.2f, 1.0f),
                new Color(1, 0.7f, 0.2f, 1.0f)
        );

        this.healthLabel = new StaticText(CommonShaders.textShader, CommonFonts.georgia, "HEALTH");
        this.powerupsLabel = new StaticText(CommonShaders.textShader, CommonFonts.georgia, "POWER-UPS");
    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        TextureAsset texture;
        // Assets are disposed of automatically.
        loader.registerAsset(texture = new TextureAsset(AssetLocation.asResource(Statics.RES_ORIGIN, "textures/entities/items.png")));

        this.powerupSprite = new Sprite(texture, 32, 32);
    }

    public void setPlayer(PlayerEntity player)
    {
        this.player = player;
    }

    public void onResized(Window window)
    {
        final int windowWidth = window.getWidth();

        int uiScale = windowWidth > 1000 ? 3 : 2;

        if (this.topRight)
        {
            this.modelViewMatrix.identity().translate(windowWidth - 5 * uiScale, window.getHeight() - 40 * uiScale, 0).scale(uiScale);
        }
        else
        {
            this.modelViewMatrix.identity().translate(10, 10, 0).scale(uiScale);
        }
    }

    private void drawPowerups(IGameRenderContext ctx)
    {
        GlStack.push();
        if (this.topRight)
        {
            GlStack.translate(-HealthBarRenderer.BIG_WIDTH - 10, -5, 0);
        }
        else
        {
            GlStack.translate(HealthBarRenderer.BIG_WIDTH + 10, -5, 0);
        }

        for (Powerup powerup : this.player.getPowerups())
        {
            if (this.topRight)
            {
                GlStack.translate(-30, 0, 0);
            }

            Vector2ic frame = powerup.getTextureFrame();
            this.powerupSprite.setFrameX(frame.x());
            this.powerupSprite.setFrameY(frame.y());
            this.powerupSprite.draw();

            if (!this.topRight)
            {
                GlStack.translate(30, 0, 0);
            }
        }

        GlStack.pop();
    }

    @Override
    public void render(IGameRenderContext ctx)
    {
        GlStack.MAIN.set(this.modelViewMatrix);

        GlStack.push();
        if (this.topRight)
        {
            GlStack.translate(-HealthBarRenderer.BIG_WIDTH, 0, 0);
        }
        HealthBarRenderer.INSTANCE.draw(ctx, player, healthBarSpec);

        GlStack.push();
        if (this.topRight)
        {
            GlStack.translate(45, 25, 0);
        }
        else
        {
            GlStack.translate(2, 25, 0);
        }
        GlStack.scale(0.5f);
        this.healthLabel.render();
        GlStack.pop();

        GlStack.pop();

        GlStack.push();
        if (this.topRight)
        {
            GlStack.translate(-HealthBarRenderer.BIG_WIDTH - 54, 25, 0);
        }
        else
        {
            GlStack.translate(HealthBarRenderer.BIG_WIDTH + 10, 25, 0);
        }
        GlStack.scale(0.5f);
        this.powerupsLabel.render();
        GlStack.pop();

        this.drawPowerups(ctx);

        super.render(ctx);
    }
}
