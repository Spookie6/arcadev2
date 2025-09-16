package com.github.spookie6

import Arcade
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.github.spookie6.scenes.MenuScene
import com.github.spookie6.scenes.StartScene

class InputHandler(val arcade: Arcade) {

    fun handleInputs() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit()

        when (arcade.screen) {
            is StartScene -> {
                if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
                    arcade.screen = MenuScene(arcade) // switch to menu
                }
            }
        }
    }
}
