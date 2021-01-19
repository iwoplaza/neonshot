package iwoplaza.meatengine.graphics.tile;

import iwoplaza.meatengine.world.tile.TileData;
import iwoplaza.meatengine.world.tile.TileLocation;

import java.util.List;

public interface ITileRenderer
{
    void render(TileData tileData, TileLocation location, TileMapSpec tileMapSpec, List<Integer> indices, List<Float> positions, List<Float> texCoords);

    class TileMapSpec
    {
        public final int width;
        public final int height;
        public final int tileSize;

        public TileMapSpec(int width, int height, int tileSize)
        {
            this.width = width;
            this.height = height;
            this.tileSize = tileSize;
        }
    }

}
