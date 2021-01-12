package iwoplaza.meatengine.physics;

import java.util.Collections;
import java.util.List;

public class WorldCollider
{

    private final List<LineSegment> lineSegments;

    public WorldCollider(List<LineSegment> lineSegments)
    {
        this.lineSegments = Collections.unmodifiableList(lineSegments);
    }

    public List<LineSegment> getLineSegments()
    {
        return lineSegments;
    }

}
