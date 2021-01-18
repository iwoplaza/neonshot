package iwoplaza.neonshot.ui.menu;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetConsumer;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.SoundAsset;
import iwoplaza.meatengine.audio.SoundSource;
import iwoplaza.meatengine.graphics.Drawable;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.mesh.FlatBorderMesh;
import iwoplaza.meatengine.graphics.shader.core.FlatShader;
import iwoplaza.meatengine.ui.UIItem;
import iwoplaza.neonshot.CommonShaders;
import iwoplaza.neonshot.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuUI extends UIItem<IEngineContext> implements IAssetConsumer
{

    private static final int OPTION_SPACING = MenuOptionUI.HEIGHT + 4;
    private final List<MenuOptionUI> options;

    private int menuOption = 0;
    private int prevMenuOption = 0;
    private float optionSwitchProgress = 0;

    private int x, y;

    private final SoundSource soundSource;
    private SoundAsset switchSound;
    private Drawable<FlatShader> border;

    public MenuUI(int x, int y)
    {
        super();
        this.x = x;
        this.y = y;
        this.options = new ArrayList<>();

        this.soundSource = new SoundSource(false, true);
    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        loader.registerAsset(switchSound = new SoundAsset(
                AssetLocation.asResource(Statics.RES_ORIGIN, "sfx/bleep.ogg")
        ));
        this.soundSource.setBuffer(switchSound);
    }

    public void addOption(String label)
    {
        MenuOptionUI option = new MenuOptionUI(label, x, y - this.options.size() * OPTION_SPACING);
        this.options.add(option);
        this.addChild(option);

        if (this.options.size() == 1)
        {
            option.onSelected();
        }
    }

    public void updateBorderMesh()
    {
        int maxWidth = 0;
        for (MenuOptionUI option : this.options)
        {
            int width = option.label.getTextWidth();
            if (width > maxWidth)
                maxWidth = width;
        }

        if (this.border != null)
            this.border.dispose();

        int dx = maxWidth / 2 + 14;
        int dy = 14;
        int t = 2;

        int[] indices = new int[] {
                // Left
                0, 3, 4, 4, 3, 7,
                // Bottom
                0, 4, 1, 1, 4, 5,
                // Right
                5, 6, 2, 1, 5, 2,
                // Top
                7, 2, 6, 7, 3, 2
        };

        float[] positions = new float[] {
                -dx, -dy,
                dx, -dy,
                dx, dy,
                -dx, dy,

                -dx + t, -dy + t,
                dx - t, -dy + t,
                dx - t, dy - t,
                -dx + t, dy - t
        };

        this.border = new Drawable<>(new FlatBorderMesh(indices, positions), CommonShaders.flatShader);
    }

    @Override
    public void render(IEngineContext context)
    {
        super.render(context);

        GlStack.push();
        GlStack.translate(x - 1, y - this.menuOption * OPTION_SPACING + 8, 0);

        FlatShader shader = this.border.getShader();
        shader.bind();

        if (this.optionSwitchProgress > 0)
        {
            this.optionSwitchProgress -= context.getDeltaTime() * 5;
            if (this.optionSwitchProgress < 0)
                this.optionSwitchProgress = 0;

            float t = this.optionSwitchProgress;
            t = (float) (Math.pow(t, 3));
            float offset = t * OPTION_SPACING;
            GlStack.translate(0, this.prevMenuOption < this.menuOption ? offset : -offset, 0);

            shader.getColor().set(1, 1, 0, (1 - this.optionSwitchProgress) * 0.5F);
        }
        else
        {
            shader.getColor().set(1, 1, 0, 1);
        }

        this.border.draw();

        GlStack.pop();

        shader.unbind();
    }

    public void selectPrevious()
    {
        if (this.menuOption > 0)
        {
            this.prevMenuOption = this.menuOption;
            this.menuOption--;
            this.optionSwitchProgress = 1;

            this.options.get(this.prevMenuOption).onDeselected();
            this.options.get(this.menuOption).onSelected();

            this.soundSource.play();
        }
    }

    public void selectNext()
    {
        if (this.menuOption < this.options.size()-1)
        {
            this.prevMenuOption = this.menuOption;
            this.menuOption++;
            this.optionSwitchProgress = 1;

            this.options.get(this.prevMenuOption).onDeselected();
            this.options.get(this.menuOption).onSelected();

            this.soundSource.play();
        }
    }

    public int getSelectedOption()
    {
        return this.menuOption;
    }

}
