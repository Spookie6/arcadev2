package com.github.spookie6.scenes

import Arcade
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.ScreenUtils

class LoadingScene(arcade : Arcade) : BaseScene(arcade) {
    override fun show() {
        Gdx.app.log("LoadingScene", "Queuing assets...")
        arcade.assets.load("images/arcade-background.png", Texture::class.java)
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
            arcade.font.draw(batch, "Launching %${arcade.assets.progress * 100}", 80f, 100f)
            batch.end()
        }
    }

    override fun dispose() {
    }
}
