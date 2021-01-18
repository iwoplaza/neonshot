package iwoplaza.neonshot.graphics;

import iwoplaza.meatengine.graphics.Drawable;
import iwoplaza.meatengine.graphics.mesh.Mesh;
import iwoplaza.meatengine.util.Color;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.mesh.FlatMesh;
import iwoplaza.meatengine.graphics.shader.core.FlatShader;
import iwoplaza.meatengine.helper.MeshHelper;
import iwoplaza.meatengine.util.IColorc;
import iwoplaza.neonshot.CommonShaders;
import iwoplaza.neonshot.graphics.shader.ProgressBarShader;
import iwoplaza.neonshot.world.entity.IDamageable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HealthBarRenderer
{
    public static final HealthBarRenderer INSTANCE = new HealthBarRenderer();

    private Drawable<FlatShader> background;
    private Drawable<ProgressBarShader> fill;
    private Drawable<FlatShader> bigBorder;

    private ProgressBarShader shader;

    public static final int SMALL_WIDTH = 20;
    public static final int SMALL_HEIGHT = 3;
    public static final int BIG_WIDTH = 80;
    public static final int BIG_HEIGHT = 12;
    public static final int THICKNESS = 1;

    private final Map<IDamageable, HealthMeta> healthMetaMap = new HashMap<>();

    public void init() throws IOException
    {
        this.shader = new ProgressBarShader();
        this.shader.load();

        Mesh rectangle = MeshHelper.createFlatRectangle(1, 1);
        this.background = new Drawable<>(rectangle, CommonShaders.flatShader);
        this.fill = new Drawable<>(rectangle, this.shader);
        this.bigBorder = new Drawable<>(MeshHelper.createBorder(BIG_WIDTH, BIG_HEIGHT, THICKNESS), CommonShaders.flatShader);
    }

    private void drawBackground(boolean big)
    {
        GlStack.push();

        if (big)
        {
            GlStack.scale(BIG_WIDTH, BIG_HEIGHT, 1);
        }
        else
        {
            GlStack.scale(SMALL_WIDTH, SMALL_HEIGHT, 1);
        }

        this.background.draw(s -> {
            s.getColor().set(0, 0, 0, 1);
        });

        GlStack.pop();
    }

    private void drawFill(HealthBarSpec spec, HealthMeta meta)
    {
        float transition = 1 - meta.getTransitionProgress();
        transition *= transition * transition;
        transition = 1 - transition;
        final float t = transition;

        final float lastHealth = meta.getLastHealthPercentage();
        final float currHealth = meta.getCurrentHealthPercentage();

        GlStack.push();

        GlStack.translate(THICKNESS, THICKNESS, 0);

        if (spec.isBig())
        {
            GlStack.scale(BIG_WIDTH - THICKNESS * 2, BIG_HEIGHT - THICKNESS * 2, 1);
        }
        else
        {
            GlStack.scale(SMALL_WIDTH - THICKNESS * 2, SMALL_HEIGHT - THICKNESS * 2, 1);
        }

        float percentage = lastHealth + (currHealth - lastHealth) * transition;
        GlStack.scale(percentage, 1, 1);

        this.fill.draw(s -> {
            s.getHighlight().set(1 - t);
            s.getColor().set(spec.baseColor);
            s.getHighlightColor().set(spec.highlightColor);
        });

        GlStack.pop();
    }

    public void draw(IGameRenderContext context, IDamageable damagable, HealthBarSpec spec)
    {
        this.drawBackground(spec.isBig());

        if (spec.isBig())
        {
            this.bigBorder.draw(s -> {
                s.getColor().set(1, 1, 1, 1);
            });
        }

        HealthMeta meta = healthMetaMap.get(damagable);
        if (meta == null)
        {
            meta = new HealthMeta(0.5f, damagable.getMaxHealth(), damagable.getHealth());
            healthMetaMap.put(damagable, meta);
        }

        meta.setHealth(damagable.getHealth(), damagable.getMaxHealth());
        meta.update(context.getDeltaTime());

        this.drawFill(spec, meta);
    }

    public static class HealthBarSpec
    {
        private final boolean big;
        private final IColorc baseColor;
        private final IColorc highlightColor;

        public HealthBarSpec(boolean big, IColorc baseColor, IColorc highlightColor)
        {
            this.big = big;
            this.baseColor = baseColor;
            this.highlightColor = highlightColor;
        }

        public boolean isBig()
        {
            return big;
        }

        public IColorc getBaseColor()
        {
            return baseColor;
        }

        public IColorc getHighlightColor()
        {
            return highlightColor;
        }
    }

    private static class HealthMeta
    {
        private final float transitionDuration;
        private float transitionTime;

        private int lastMaxHealth;
        private int lastHealth;
        private int currentMaxHealth;
        private int currentHealth;

        public HealthMeta(float transitionDuration, int maxHealth, int currentHealth)
        {
            this.transitionDuration = transitionDuration;
            this.lastMaxHealth = maxHealth;
            this.currentMaxHealth = maxHealth;
            this.lastHealth = currentHealth;
            this.currentHealth = currentHealth;
            this.transitionTime = 0.0f;
        }

        public void update(float deltaTime)
        {
            if (this.transitionTime > 0)
            {
                this.transitionTime -= deltaTime;
                if (this.transitionTime < 0)
                {
                    this.transitionTime = 0;
                }
            }
        }

        public void setHealth(int health, int maxHealth)
        {
            if (this.currentHealth == health && this.currentMaxHealth == maxHealth)
            {
                return;
            }

            this.lastMaxHealth = this.currentMaxHealth;
            this.lastHealth = this.currentHealth;

            this.currentMaxHealth = maxHealth;
            this.currentHealth = health;

            this.transitionTime = this.transitionDuration;
        }

        public float getTransitionDuration()
        {
            return transitionDuration;
        }

        public float getCurrentHealthPercentage()
        {
            return (float) this.currentHealth / this.currentMaxHealth;
        }

        public float getLastHealthPercentage()
        {
            return (float) this.lastHealth / this.lastMaxHealth;
        }

        public float getTransitionProgress()
        {
            return 1 - transitionTime / transitionDuration;
        }
    }
}
