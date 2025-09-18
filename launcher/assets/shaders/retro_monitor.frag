#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

uniform float u_time;
uniform vec2 u_resolution;

float warp = 0.75; // simulate curvature of CRT monitor
float scan = 0.75; // simulate darkness between scanlines

void main() {
    // squared distance from center
    vec2 uv = v_texCoords;
    vec2 dc = abs(0.5-uv);
    dc *= dc;

    // warp the fragment coordinates
    uv.x -= 0.5; uv.x *= 1.0+(dc.y*(0.3*warp)); uv.x += 0.5;
    uv.y -= 0.5; uv.y *= 1.0+(dc.x*(0.4*warp)); uv.y += 0.5;

    // sample inside boundaries, otherwise set to black
    if (uv.y > 1.0 || uv.x < 0.0 || uv.x > 1.0 || uv.y < 0.0)
        gl_FragColor = vec4(0.0,0.0,0.0,1.0);
    else {
        // base color
        vec4 color = texture2D(u_texture, uv) * v_color;

        // add horizontal scanlines
        float scanline = sin(uv.y * u_resolution.y * 1.5 + (u_time * 10)) * 0.04;
        color.rgb -= scanline;

        // slight greenish tint
        color.g += 0.03;

        gl_FragColor = color;
        }
	}
