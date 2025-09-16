package com.github.spookie6.scenes

import Arcade
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.ScreenUtils

class MenuScene(arcade: Arcade) : BaseScene(arcade) {
//    private val card = Texture("images/game-card.png")

    override fun show() {
        ;
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0f, 0f, 0f, 1f)
        camera.update()
        batch.projectionMatrix = camera.combined

//        batch.begin()
//        batch.draw(card, 50f, 50f, 100f, 150f) // placeholder card
//        batch.end()
    }

    override fun dispose() {
//        card.dispose()
    }
}
