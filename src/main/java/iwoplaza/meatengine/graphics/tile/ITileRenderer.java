package iwoplaza.meatengine.graphics.tile;

import iwoplaza.meatengine.world.tile.TileData;
import iwoplaza.meatengine.world.tile.TileLocation;

import java.util.List;

public interface ITileRenderer
{

    void render(TileData tileData, TileLocation location, int tileSize, int tileMapWidth, int tileMapHeight, List<Integer> indices, List<Float> positions, List<Float> texCoords);

}
