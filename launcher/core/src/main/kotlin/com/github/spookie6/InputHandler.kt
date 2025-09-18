package com.github.spookie6

import Arcade
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.github.spookie6.scenes.GameLaunchScene
import com.github.spookie6.scenes.MenuScene
import com.github.spookie6.scenes.StartScene

class InputHandler(val arcade: Arcade) {
    fun handleInputs() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            if (arcade.processRunner.isRunning()) {
                arcade.processRunner.abortProcess()
            } else {
                Gdx.app.exit()
            }
        }

        if (arcade.processRunner.isRunning()) return
        when (arcade.screen) {
            is StartScene -> {
                if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) && !Gdx.input.isKeyPressed(Input.Keys.H)) {
                    arcade.screen = MenuScene(arcade)
                }
            }
            is MenuScene -> {
                if (Gdx.input.isKeyPressed(Input.Keys.H)) {
                    arcade.screen = GameLaunchScene(arcade, arcade.gameLoader.loadedGames[0])
                }
            }
        }
    }
}
