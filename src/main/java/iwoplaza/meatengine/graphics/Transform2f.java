package iwoplaza.meatengine.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Transform2f
{
    private final Vector2f position;
    private final Vector3f rotation;
    private final Vector2f scale;
    private final Matrix4f matrix;

    public Transform2f()
    {
        this.position = new Vector2f();
        this.rotation = new Vector3f();
        this.scale = new Vector2f(1, 1);
        this.matrix = new Matrix4f().identity();
    }

    public void updateTransform()
    {
        this.matrix.identity().translate(new Vector3f(position, 0))
                .rotateX(rotation.x)
                .rotateY(rotation.y)
                .rotateZ(rotation.z)
                .scale(new Vector3f(scale, 1));
    }

    public void setPosition(float x, float y)
    {
        this.position.set(x, y);
    }

    public void setPosition(Vector2f src)
    {
        this.position.set(src);
    }

    public void setRotationX(float x)
    {
        this.rotation.x = x;
    }

    public void setRotationY(float y)
    {
        this.rotation.y = y;
    }

    public void setRotationZ(float z)
    {
        this.rotation.z = z;
    }

    public void rotateX(float amount)
    {
        this.rotation.x += amount;
    }

    public void rotateY(float amount)
    {
        this.rotation.y += amount;
    }

    public void rotateZ(float amount)
    {
        this.rotation.z += amount;
    }

    public void setScale(float amount)
    {
        this.scale.set(amount);
    }

    public void setScale(float x, float y)
    {
        this.scale.set(x, y);
    }

    public Vector2f getPosition()
    {
        return position;
    }

    public Vector3f getRotation()
    {
        return rotation;
    }

    public Vector2f getScale()
    {
        return scale;
    }

    public Matrix4f getMatrix()
    {
        return this.matrix;
    }
}
