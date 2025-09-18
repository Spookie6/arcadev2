package com.github.spookie6

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator

object FontManager {
    private val fontCache = mutableMapOf<String, BitmapFont>()
    private val generators = mutableMapOf<String, FreeTypeFontGenerator>()

    fun getFont(ttfPath: String, size: Int, color: Color = Color.WHITE): BitmapFont {
        val key = "$ttfPath-$size-$color"

        return fontCache.getOrPut(key) {
            val generator = generators.getOrPut(ttfPath) {
                FreeTypeFontGenerator(Gdx.files.internal(ttfPath))
            }
            val parameter = FreeTypeFontGenerator.FreeTypeFontParameter().apply {
                this.size = size
                this.color = color
                this.minFilter = Texture.TextureFilter.Linear
                this.magFilter = Texture.TextureFilter.Linear
            }
            generator.generateFont(parameter)
        }
    }

    fun dispose() {
        fontCache.values.forEach { it.dispose() }
        fontCache.clear()
        generators.values.forEach { it.dispose() }
        generators.clear()
    }
}
