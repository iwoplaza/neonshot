#version 330

layout (location=0) in vec2 position;
layout (location=1) in vec2 texCoord;

out vec2 vTexCoord;

uniform mat4 uProjectionMatrix;
uniform mat4 uModelViewMatrix;

void main()
{
    gl_Position = uProjectionMatrix * uModelViewMatrix * vec4(position, 0.0, 1.0);
    vTexCoord = texCoord;
}