package com.github.spookie6.scenes

import Arcade
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.ScreenUtils
import com.github.spookie6.GameLoader

class GameLaunchScene(
    arcade: Arcade,
    private val game: GameLoader.LoadedGame
) : BaseScene(arcade) {

    private var launchTime = 0f
    private var launched = false

    override fun show() {
        // Launch the game in background
        Thread {
            arcade.processRunner.runGame(game)
            launched = true
        }.start()
    }

    override fun render(delta: Float) {
        launchTime += delta
        ScreenUtils.clear(0f, 0f, 0f, 1f)

        camera.update()
        batch.projectionMatrix = camera.combined

        batch.begin()
        // Example retro launching animation
        if (((launchTime * 2).toInt() % 2) == 0) {
            arcade.font.draw(batch, "Launching ${game.metadata.name}...", 80f, 100f)
        }
        batch.end()

        // If process is finished, return to menu
        if (!arcade.processRunner.isRunning() && launched) {
            arcade.screen = MenuScene(arcade)
        }
    }

    override fun hide() {
        // Resume rendering when back in menu
        Gdx.graphics.setContinuousRendering(true)
    }
}
