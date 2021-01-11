#version 330

layout (location=0) in vec2 position;

uniform mat4 uProjectionMatrix;
uniform mat4 uModelViewMatrix;

void main()
{
    gl_Position = uProjectionMatrix * uModelViewMatrix * vec4(position, 0.0, 1.0);
}