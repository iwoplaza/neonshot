package iwoplaza.neonshot.ui.menu;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.graphics.StaticText;
import iwoplaza.meatengine.ui.UIItem;
import iwoplaza.neonshot.CommonFonts;
import iwoplaza.neonshot.CommonShaders;

public class MenuOptionUI extends UIItem
{
    public static final int HEIGHT = 20;

    public final int x, y;
    protected StaticText label;

    public MenuOptionUI(String labelText, int x, int y)
    {
        super();

        this.x = x;
        this.y = y;
        this.label = new StaticText(CommonShaders.textShader, CommonFonts.georgia, labelText);
        this.label.setPosition(x - this.label.getTextWidth()/2F, y);
        this.onDeselected();
    }

    @Override
    public void render(IEngineContext context)
    {
        super.render(context);

        this.label.render();
    }

    @Override
    public void dispose()
    {
        super.dispose();

        this.label.dispose();
    }

    public void onSelected()
    {
        this.label.setColor(1, 1, 1, 1);
    }

    public void onDeselected()
    {
        this.label.setColor(0.5F, 0.5F, 0.5F, 0.5F);
    }
}
