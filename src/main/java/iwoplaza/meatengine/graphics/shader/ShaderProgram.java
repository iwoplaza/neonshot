package iwoplaza.meatengine.graphics.shader;

import iwoplaza.meatengine.IDisposable;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram implements IDisposable
{
    private final int programId;

    public ShaderProgram()
    {
        this.programId = glCreateProgram();
        if (programId == 0)
            throw new IllegalStateException("Could not create shader");
    }

    public void link(ShaderPartial vertexShader, ShaderPartial fragmentShader)
    {
        // Attaching partial shaders.
        glAttachShader(programId, vertexShader.getId());
        glAttachShader(programId, fragmentShader.getId());

        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0)
        {
            throw new IllegalArgumentException("Error linking shader code: " + glGetProgramInfoLog(programId, 1024));
        }

        // Deattaching partial shaders after linking.
        glDetachShader(programId, vertexShader.getId());
        glDetachShader(programId, fragmentShader.getId());

        // Validating the program
        glValidateProgram(programId);

        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0)
        {
            throw new IllegalArgumentException("Warning validating shader code: " + glGetProgramInfoLog(programId, 1024));
        }
    }

    public void use()
    {
        glUseProgram(programId);
    }

    public int getUniformLocation(String uniformName)
    {
        return glGetUniformLocation(programId, uniformName);
    }

    @Override
    public void dispose()
    {
        if (programId != 0)
        {
            glDeleteProgram(programId);
        }
    }
}
