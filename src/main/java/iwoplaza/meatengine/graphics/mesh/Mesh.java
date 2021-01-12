package iwoplaza.meatengine.graphics.mesh;

import iwoplaza.meatengine.IDisposable;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.memFree;

public abstract class Mesh implements IDisposable
{
    private final int vertexCount;
    private final int vaoId;
    private final List<Integer> vboIds;
    private final int idxVboId;

    public Mesh(int[] indices)
    {
        this.vertexCount = indices.length;
        this.vboIds = new ArrayList<>();
        this.vaoId = glGenVertexArrays();

        glBindVertexArray(vaoId);

        IntBuffer indicesBuffer = null;
        try
        {
            idxVboId = glGenBuffers();
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
        finally
        {
            if (indicesBuffer != null)
                memFree(indicesBuffer);
        }

        glBindVertexArray(0);
    }

    protected void createFloatVBO(float[] values, int dimension)
    {
        FloatBuffer buffer = null;

        try
        {
            final int vboId = glGenBuffers();
            buffer = MemoryUtil.memAllocFloat(values.length);
            buffer.put(values).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glVertexAttribPointer(vboIds.size(), dimension, GL_FLOAT, false, 0, 0);
            vboIds.add(vboId);
        }
        finally
        {
            if (buffer != null)
                memFree(buffer);
        }
    }

    protected void createIntVBO(int[] values, int dimension)
    {
        IntBuffer buffer = null;

        try
        {
            final int vboId = glGenBuffers();
            buffer = MemoryUtil.memAllocInt(values.length);
            buffer.put(values).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glVertexAttribPointer(vboIds.size(), dimension, GL_FLOAT, false, 0, 0);
            vboIds.add(vboId);
        }
        finally
        {
            if (buffer != null)
                memFree(buffer);
        }
    }

    public int getVaoId()
    {
        return vaoId;
    }

    public int getVertexCount()
    {
        return vertexCount;
    }

    @Override
    public void dispose()
    {
        // Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for (int vboId : this.vboIds)
            glDeleteBuffers(vboId);
        glDeleteBuffers(idxVboId);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

    public void render()
    {
        glBindVertexArray(vaoId);

        for (int i = 0; i < this.vboIds.size(); ++i)
            glEnableVertexAttribArray(i);

        // Draw the vertices
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);

        for (int i = 0; i < this.vboIds.size(); ++i)
            glDisableVertexAttribArray(i);

        glBindVertexArray(0);
    }

    protected void bindVertexArray()
    {
        glBindVertexArray(vaoId);
    }

    protected void unbindVertexArray()
    {
        glBindVertexArray(0);
    }
}
