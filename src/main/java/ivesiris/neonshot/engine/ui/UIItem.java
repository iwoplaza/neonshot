package ivesiris.neonshot.engine.ui;

import ivesiris.neonshot.engine.EngineContext;

import java.util.ArrayList;
import java.util.List;

public abstract class UIItem
{

    protected List<UIItem> childItems;

    public UIItem()
    {
        this.childItems = new ArrayList<>();
    }

    protected void addChild(UIItem item)
    {
        this.childItems.add(item);
    }

    public void render(EngineContext context)
    {
        for (UIItem item : childItems)
        {
            item.render(context);
        }
    }

    public void cleanUp()
    {
        for (UIItem item : childItems)
        {
            item.cleanUp();
        }
    }

}
