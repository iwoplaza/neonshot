package ivesiris.neonshot.engine.physics;

import java.util.ArrayList;
import java.util.List;

public class WorldColliderFactory
{

    private final List<LineSegment> segments;

    public WorldColliderFactory()
    {
        this.segments = new ArrayList<>();
    }

    public List<LineSegment> getSegments()
    {
        return segments;
    }

    public void addSegment(LineSegment segment)
    {
        this.segments.add(segment);
    }

    public void addSegment(float x0, float y0, float x1, float y1)
    {
        this.segments.add(new LineSegment(x0, y0, x1, y1));
    }

    public WorldCollider create()
    {
        return new WorldCollider(this.segments);
    }

}
