#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

uniform float u_time; // for animation
uniform vec2 u_resolution;

void main() {
    vec2 uv = v_texCoords;

    // base color
    vec4 color = texture2D(u_texture, uv) * v_color;

    // add horizontal scanlines
    float scanline = sin(uv.y * u_resolution.y * 1.5) * 0.04;
    color.rgb -= scanline;

    // slight greenish tint
    color.g += 0.03;

    gl_FragColor = color;
}
