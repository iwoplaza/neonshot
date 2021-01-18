package iwoplaza.meatengine.graphics.sprite;

import iwoplaza.meatengine.graphics.Drawable;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.mesh.Mesh;
import iwoplaza.meatengine.graphics.mesh.TexturedMesh;
import iwoplaza.meatengine.graphics.shader.core.SpriteShader;

import java.io.FileNotFoundException;

public class SpriteRenderer
{
    public static final SpriteRenderer INSTANCE = new SpriteRenderer();

    private Drawable spriteDrawable;
    private SpriteShader shader;

    public void init() throws FileNotFoundException
    {
        int[] indices = new int[] {
                0, 1, 3,
                3, 1, 2,
        };

        float[] positions = new float[] {
                0, 1,
                0, 0,
                1, 0,
                1, 1,
        };

        float[] texCoords = new float[] {
                0, 0,
                0, 1,
                1, 1,
                1, 0,
        };

        this.shader = new SpriteShader();
        this.shader.load();

        this.spriteDrawable = new Drawable(new TexturedMesh(indices, positions, texCoords), this.shader);
    }

    public void draw(Sprite sprite)
    {
        GlStack.push();

        GlStack.scale(sprite.getFrameWidth(), sprite.getFrameHeight(), 1);
        this.shader.bind();
        this.shader.getFrameOffset().set(sprite.getFrameX(), sprite.getFrameY());
        this.shader.getFrameSize().set((float) sprite.getFrameWidth() / sprite.getTexture().getWidth(), (float) sprite.getFrameHeight() / sprite.getTexture().getHeight());
        this.shader.getColor().set(1, 1, 1, 1);
        this.shader.getOverlayColor().set(sprite.getOverlayColor());

        sprite.getTexture().bind();
        this.spriteDrawable.draw();

        GlStack.pop();
    }
}
