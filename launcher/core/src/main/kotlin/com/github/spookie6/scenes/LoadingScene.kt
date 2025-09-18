package com.github.spookie6.scenes

import Arcade
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.ScreenUtils
import com.github.spookie6.FontManager

class LoadingScene(arcade : Arcade) : BaseScene(arcade) {
    override fun show() {
        Gdx.app.log("LoadingScene", "Queuing assets...")
        arcade.assets.load("images/arcade-background.png", Texture::class.java)
        FontManager.getFont("fonts/8-bit-hud.ttf", 32)
        FontManager.getFont("fonts/8-bit-hud.ttf", 24)
        FontManager.getFont("fonts/8-bit-hud.ttf", 16)
        FontManager.getFont("fonts/8-bit-hud.ttf", 8)

//        arcade.gameLoader.loadedGames.forEach { try {arcade.assets.load(it.dir.toString() + it.metadata.coverimage, Texture::class.java)} catch (e: Exception) {e.printStackTrace()} }
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0f, 0f, 0f, 1f)

        if (arcade.assets.update()) {
            arcade.assets.finishLoading()
            Gdx.app.log("Assets", "Loaded: ${arcade.assets.diagnostics}")
            arcade.screen = StartScene(arcade)
        } else {
            camera.update()
            batch.projectionMatrix = camera.combined

            batch.begin()
            FontManager.getFont("fonts/8-bit-hud.ttf", 8).draw(batch, "Launching %${arcade.assets.progress * 100}", 80f, 100f)
            batch.end()
        }
    }

    override fun dispose() {
    }
}
