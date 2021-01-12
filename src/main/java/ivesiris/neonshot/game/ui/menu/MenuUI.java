package ivesiris.neonshot.game.ui.menu;

import ivesiris.neonshot.engine.EngineContext;
import ivesiris.neonshot.engine.graphics.GlStack;
import ivesiris.neonshot.engine.graphics.mesh.FlatBorderMesh;
import ivesiris.neonshot.engine.graphics.mesh.FlatMesh;
import ivesiris.neonshot.engine.graphics.shader.FlatShader;
import ivesiris.neonshot.engine.ui.UIItem;
import ivesiris.neonshot.game.CommonShaders;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MenuUI extends UIItem
{

    private static final int OPTION_SPACING = MenuOptionUI.HEIGHT + 4;
    private final List<MenuOptionUI> options;

    private int menuOption = 0;
    private int prevMenuOption = 0;
    private float optionSwitchProgress = 0;

    private int x, y;

    private FlatBorderMesh borderMesh;

    public MenuUI(int x, int y)
    {
        super();
        this.x = x;
        this.y = y;
        this.options = new ArrayList<>();
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

        if (this.borderMesh != null)
            this.borderMesh.cleanUp();

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

        this.borderMesh = new FlatBorderMesh(indices, positions);
    }

    @Override
    public void render(EngineContext context)
    {
        super.render(context);

        GlStack.push();
        GlStack.translate(x - 1, y - this.menuOption * OPTION_SPACING + 8, 0);

        FlatShader shader = CommonShaders.flatShader;
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

            shader.setColor(1, 1, 0, (1 - this.optionSwitchProgress) * 0.5F);
        }
        else
        {
            shader.setColor(1, 1, 0, 1);
        }


        shader.setProjectionMatrix(GlStack.MAIN.projectionMatrix);
        shader.setModelViewMatrix(GlStack.MAIN.top());


        this.borderMesh.render();

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
        }
    }

    public int getSelectedOption()
    {
        return this.menuOption;
    }

}
