package ivesiris.neonshot.engine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window
{

    private final String windowTitle;
    private long windowHandle;
    private final int windowedWidth;
    private final int windowedHeight;
    private int width;
    private int height;
    private final boolean vSync;
    private boolean resized;
    private boolean fullscreen;

    Window(String title, int width, int height, boolean vSync)
    {
        this.windowTitle = title;
        this.windowedWidth = width;
        this.windowedHeight = height;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        this.resized = false;
        this.fullscreen = false;
    }

    public void init()
    {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        //this.width = vidmode.width();
        //this.height = vidmode.height();

        // Create the window
        windowHandle = glfwCreateWindow(width, height, windowTitle, NULL, NULL);
        if (windowHandle == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Center the window
        glfwSetWindowPos(
                windowHandle,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );

        glfwSetFramebufferSizeCallback(windowHandle, (window, w, h) -> {
            this.width = w;
            this.height = h;
            this.resized = true;
        });

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowHandle);

        if (vSync)
            glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(windowHandle);

        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
    }

    public void update()
    {
        glfwSwapBuffers(windowHandle);
        glfwPollEvents();
    }

    public void setFullscreen()
    {
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowMonitor(this.windowHandle, glfwGetPrimaryMonitor(), 0, 0, vidmode.width(), vidmode.height(), vidmode.refreshRate());
        fullscreen = true;
    }

    public void setWindowed()
    {
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowMonitor(this.windowHandle, NULL, 0, 0, windowedWidth, windowedHeight, vidmode.refreshRate());
        glfwSetWindowPos(this.windowHandle, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

        fullscreen = false;
    }

    public void setClearColor(float r, float g, float b, float a)
    {
        glClearColor(r, g, b, a);
    }

    public void setResized(boolean resized)
    {
        this.resized = resized;
    }

    public boolean isFullscreen()
    {
        return fullscreen;
    }

    public boolean isKeyPressed(int keyCode)
    {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }

    public boolean shouldClose()
    {
        return glfwWindowShouldClose(windowHandle);
    }

    public boolean isVSyncEnabled()
    {
        return this.vSync;
    }

    public boolean isResized()
    {
        return this.resized;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return height;
    }

    public String getWindowTitle()
    {
        return windowTitle;
    }

    public long getHandle()
    {
        return this.windowHandle;
    }

}
