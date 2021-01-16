#version 330

in vec2 vTexCoord;

out vec4 fragColor;

uniform vec4 uColor;
uniform vec4 uHighlightColor;
uniform float uHighlight;

void main()
{
    fragColor = mix(uColor, uHighlightColor, uHighlight);
}
