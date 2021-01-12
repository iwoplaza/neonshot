package iwoplaza.meatengine.graphics.shader;

public class ShaderHelper
{
    public static <T extends Shader> void operateOnShader(T shader, IShaderOperationFunction<T> function)
    {
        shader.bind();
        function.operate(shader);
        shader.unbind();
    }

    @FunctionalInterface
    public interface IShaderOperationFunction<T extends Shader>
    {
        void operate(T shader);
    }
}
