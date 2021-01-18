package iwoplaza.meatengine.graphics.shader.uniform;

import iwoplaza.meatengine.graphics.shader.Shader;

public abstract class Uniform
{
    protected final Shader ownerShader;
    protected final int uniformLocation;

    public Uniform(Shader shader, String uniformKey)
    {
        this.ownerShader = shader;
        this.uniformLocation = shader.getProgram().getUniformLocation(uniformKey);

        if (this.uniformLocation < 0)
        {
            throw new IllegalArgumentException(String.format("Uniform '%s' isn't defined in the '%s' shader", uniformKey, shader.toString()));
        }
    }
}
