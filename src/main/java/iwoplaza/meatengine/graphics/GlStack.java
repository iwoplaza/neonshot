package iwoplaza.meatengine.graphics;

import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.util.Stack;

public class GlStack
{

    public static GlStack MAIN = new GlStack();

    public final Matrix4f projectionMatrix;
    Stack<GlState> stateStack;

    public GlStack()
    {
        this.projectionMatrix = new Matrix4f();
        this.stateStack = new Stack<>();
        this.stateStack.push(new GlState());
    }

    public void pushState()
    {
        this.stateStack.push(new GlState(this.top()));
    }

    public void popState()
    {
        this.stateStack.pop();
    }

    public Matrix4f top()
    {
        return this.stateStack.peek().matrix;
    }

    public static void push()
    {
        MAIN.pushState();
    }

    public static void pop()
    {
        MAIN.popState();
    }

    public static void translate(float x, float y, float z)
    {
        MAIN.top().translate(x, y, z);
    }

    public static void rotate(Quaternionf quat)
    {
        MAIN.top().rotate(quat);
    }

    public static void rotate(float angle, float x, float y, float z)
    {
        MAIN.top().rotate(angle, x, y, z);
    }

    public static void scale(float x, float y, float z)
    {
        MAIN.top().scale(x, y, z);
    }

    public static void scale(float a)
    {
        MAIN.top().scale(a);
    }

    public static void identity()
    {
        MAIN.top().identity();
    }

    public void set(Matrix4f src)
    {
        this.top().set(src);
    }

    public void apply(Matrix4f other)
    {
        this.top().mul(other);
    }

}
