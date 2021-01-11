#version 330

in vec2 vTexCoord;

out vec4 fragColor;

uniform vec4 uColor;
uniform bool uUseTexture;
uniform sampler2D uTextureDiffuse;

void main()
{
    vec4 color = uColor;

    if (uUseTexture)
    {
        color *= texture(uTextureDiffuse, vTexCoord);
    }

    fragColor = color;
}
