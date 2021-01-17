package iwoplaza.neonshot.graphics;

import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.graphics.Color;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.mesh.FlatMesh;
import iwoplaza.meatengine.graphics.shader.core.FlatShader;
import iwoplaza.meatengine.helper.MeshHelper;
import iwoplaza.neonshot.CommonShaders;
import iwoplaza.neonshot.graphics.shader.ProgressBarShader;
import iwoplaza.neonshot.world.entity.IDamageable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HealthBarRenderer
{
    public static final HealthBarRenderer INSTANCE = new HealthBarRenderer();

    private FlatMesh background;
    private FlatMesh smallBorderMesh;
    private FlatMesh bigBorderMesh;

    private ProgressBarShader shader;

    public static final int SMALL_WIDTH = 20;
    public static final int SMALL_HEIGHT = 3;
    public static final int BIG_WIDTH = 80;
    public static final int BIG_HEIGHT = 12;
    public static final int THICKNESS = 1;

    private final Map<IDamageable, HealthMeta> healthMetaMap = new HashMap<>();

    public void init() throws IOException
    {
        this.background = MeshHelper.createFlatRectangle(1, 1);
        this.smallBorderMesh = MeshHelper.createBorder(SMALL_WIDTH, SMALL_HEIGHT, THICKNESS);
        this.bigBorderMesh = MeshHelper.createBorder(BIG_WIDTH, BIG_HEIGHT, THICKNESS);

        this.shader = new ProgressBarShader();
        this.shader.load();
    }

    private void drawBackground(boolean big)
    {
        FlatShader shader = CommonShaders.flatShader;
        shader.bind();

        GlStack.push();

        if (big)
        {
            GlStack.scale(BIG_WIDTH, BIG_HEIGHT, 1);
        }
        else
        {
            GlStack.scale(SMALL_WIDTH, SMALL_HEIGHT, 1);
        }

        shader.setProjectionMatrix(GlStack.MAIN.projectionMatrix);
        shader.setModelViewMatrix(GlStack.MAIN.top());
        shader.setColor(0, 0, 0, 1);
        this.background.render();

        GlStack.pop();

        shader.setProjectionMatrix(GlStack.MAIN.projectionMatrix);
        shader.setModelViewMatrix(GlStack.MAIN.top());
        shader.setColor(1, 1, 1, 1);

        if (big)
        {
            this.bigBorderMesh.render();
        }
    }

    private void drawFill(HealthBarSpec spec, HealthMeta meta)
    {
        float transition = 1 - meta.getTransitionProgress();
        transition *= transition * transition;
        transition = 1 - transition;

        final float lastHealth = meta.getLastHealthPercentage();
        final float currHealth = meta.getCurrentHealthPercentage();

        GlStack.push();

        this.shader.bind();
        this.shader.setHighlight(1 - transition);
        this.shader.setColor(spec.baseColor.getR(), spec.baseColor.getG(), spec.baseColor.getB(), spec.baseColor.getA());
        this.shader.setHighlightColor(
                spec.highlightColor.getR(),
                spec.highlightColor.getG(),
                spec.highlightColor.getB(),
                spec.highlightColor.getA()
        );

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

        this.shader.setProjectionMatrix(GlStack.MAIN.projectionMatrix);
        this.shader.setModelViewMatrix(GlStack.MAIN.top());
        this.background.render();

        GlStack.pop();
    }

    public void draw(IGameRenderContext context, IDamageable damagable, HealthBarSpec spec)
    {
        this.drawBackground(spec.isBig());

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
        private final Color baseColor;
        private final Color highlightColor;

        public HealthBarSpec(boolean big, Color baseColor, Color highlightColor)
        {
            this.big = big;
            this.baseColor = baseColor;
            this.highlightColor = highlightColor;
        }

        public boolean isBig()
        {
            return big;
        }

        public Color getBaseColor()
        {
            return baseColor;
        }

        public Color getHighlightColor()
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
