package iwoplaza.meatengine.graphics;

import iwoplaza.meatengine.Direction;
import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.sprite.Sprite;
import iwoplaza.meatengine.pathfinding.IPathfindingActor;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.io.IOException;
import java.util.List;

public class PathfinderDebug
{
    public static final PathfinderDebug INSTANCE = new PathfinderDebug();

    private Sprite sprite;

    public void init(IAssetLoader loader) throws IOException
    {
        TextureAsset texture;
        loader.registerAsset(texture = new TextureAsset(AssetLocation.asResource("textures/debug.png")));

        this.sprite = new Sprite(texture, 32, 32);
    }

    public void draw(IGameRenderContext context, IPathfindingActor actor)
    {
        final int tileSize = context.getTileSize();

        List<Vector2ic> path = actor.getPath();

        if (actor.isRequestPending())
        {
            GlStack.push();

            Vector2f pos = new Vector2f(actor.getPosition());
            pos.mul(tileSize);
            GlStack.translate(pos.x, pos.y, 0);

            this.sprite.setFrameX(1);
            this.sprite.draw();

            GlStack.pop();
        }

        if (path == null)
        {
            return;
        }

        for (int i = 0; i < path.size() - 1; i++)
        {
            Vector2ic vec = path.get(i);
            Vector2ic next = path.get(i + 1);

            Direction direction = Direction.fromVector(new Vector2i(next).sub(vec));

            GlStack.push();

            Vector2f pos = new Vector2f(vec);
            pos.mul(tileSize);
            GlStack.translate(pos.x + tileSize/2, pos.y + tileSize/2, 0);
            GlStack.rotate(direction.getAngle(), 0, 0, 1);
            GlStack.translate(-tileSize/2, -tileSize/2, 0);

            this.sprite.setFrameX(0);
            this.sprite.draw();

            GlStack.pop();
        }
    }
}
