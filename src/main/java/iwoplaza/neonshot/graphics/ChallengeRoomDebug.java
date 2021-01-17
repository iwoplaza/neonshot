package iwoplaza.neonshot.graphics;

import iwoplaza.meatengine.Direction;
import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.PathfinderDebug;
import iwoplaza.meatengine.graphics.sprite.Sprite;
import iwoplaza.meatengine.pathfinding.IPathfindingActor;
import iwoplaza.meatengine.world.World;
import iwoplaza.neonshot.world.ChallengeRoom;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.io.IOException;
import java.util.List;

public class ChallengeRoomDebug
{
    public static final ChallengeRoomDebug INSTANCE = new ChallengeRoomDebug();

    private Sprite sprite;

    public void init(IAssetLoader loader) throws IOException
    {
        TextureAsset texture;
        loader.registerAsset(texture = new TextureAsset(AssetLocation.asResource("textures/debug.png")));

        this.sprite = new Sprite(texture, 32, 32);
    }

    private void drawIconAt(IGameRenderContext ctx, int frameX, int frameY, Vector2ic position, float rotation)
    {
        final int tileSize = ctx.getTileSize();

        GlStack.push();

        Vector2f pos = new Vector2f(position);
        pos.mul(tileSize);
        GlStack.translate(pos.x + tileSize/2.0f, pos.y + tileSize/2.0f, 0);
        GlStack.rotate(rotation, 0, 0, 1);
        GlStack.translate(-tileSize/2.0f, -tileSize/2.0f, 0);

        this.sprite.setFrameX(frameX);
        this.sprite.setFrameY(frameY);
        this.sprite.draw();

        GlStack.pop();
    }

    public void draw(IGameRenderContext context, ChallengeRoom room)
    {
        if (room.isCompleted())
            return;

        // Drawing the entrance
        drawIconAt(context, 0, 0, room.getEntrance(), room.getEntranceDirection().getAngle());

        // Drawing the exit
        drawIconAt(context, 1, 0, room.getExit(), 0);
    }

    public void draw(IGameRenderContext context, World world)
    {
        world.getChallengeRooms().forEach(r -> {
            this.draw(context, r);
        });
    }
}
