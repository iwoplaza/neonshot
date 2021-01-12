package iwoplaza.meatengine.physics;

import org.joml.Vector2f;

public class LineSegment
{

    private final Vector2f point1;
    private final Vector2f point2;

    private final float lengthSq;
    private final float length;
    private final Vector2f normal;

    public LineSegment(float x1, float y1, float x2, float y2)
    {
        this.point1 = new Vector2f(x1, y1);
        this.point2 = new Vector2f(x2, y2);
        this.lengthSq = this.point1.distanceSquared(this.point2);
        this.length = (float) Math.sqrt(this.lengthSq);
        this.normal = new Vector2f(-(y2-y1), x2-x1).normalize();
    }

    public LineSegment(Vector2f point1, Vector2f point2)
    {
        this(point1.x, point1.y, point2.x, point2.y);
    }

    public Vector2f getPoint1()
    {
        return point1;
    }

    public Vector2f getPoint2()
    {
        return point2;
    }

    public float getLengthSq()
    {
        return lengthSq;
    }

    public float getLength()
    {
        return length;
    }

    public Vector2f getNormal()
    {
        return normal;
    }

    public Vector2f getClampedProjection(float x, float y)
    {
        Vector2f startToPoint = new Vector2f(x, y).sub(this.point1);
        Vector2f d = new Vector2f(this.point2).sub(this.point1);
        float projT = Math.max(0, Math.min(startToPoint.dot(d) / this.lengthSq, 1));

        return new Vector2f(this.point1).lerp(this.point2, projT);
    }

    public float getMinimalDistanceToPoint(float x, float y)
    {
        return this.getClampedProjection(x, y).distance(x, y);
    }

}
