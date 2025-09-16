package com.github.spookie6.scenes

import Arcade
import com.badlogic.gdx.utils.ScreenUtils

class StartScene(arcade: Arcade) : BaseScene(arcade) {
    override fun show() {
        // runs once when this screen becomes active
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0f, 0f, 0f, 1f)
        camera.update()
        batch.projectionMatrix = camera.combined

        batch.begin()
        batch.draw(arcade.backgroundTexture, 0f, 0f, viewport.worldWidth, viewport.worldHeight)
        batch.end()
    }

    override fun dispose() {
        ;
    }
}
