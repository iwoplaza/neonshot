#version 330

in vec2 vTexCoord;

out vec4 fragColor;

uniform vec4 uColor;
uniform sampler2D uTextureDiffuse;

void main()
{
    vec4 color = uColor;
    color *= texture(uTextureDiffuse, vTexCoord);
    fragColor = color;
    // fragColor = color * 0.1 + vec4(1.0, 1.0, 0.0, 1.0);
}
