package com.github.spookie6.scenes

import Arcade
import com.badlogic.gdx.Screen

abstract class BaseScene(protected val arcade: Arcade) : Screen {
    protected val batch get() = arcade.batch
    protected val camera get() = arcade.camera
    protected val viewport get() = arcade.viewport

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun pause() {}
    override fun resume() {}
    override fun hide() {}
}
