package iwoplaza.meatengine.ui;

import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.IEngineContext;

import java.util.ArrayList;
import java.util.List;

public abstract class UIItem implements IDisposable
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

    public void render(IEngineContext ctx)
    {
        for (UIItem item : childItems)
        {
            item.render(ctx);
        }
    }

    public void dispose()
    {
        for (UIItem item : childItems)
        {
            item.dispose();
        }
    }

}
