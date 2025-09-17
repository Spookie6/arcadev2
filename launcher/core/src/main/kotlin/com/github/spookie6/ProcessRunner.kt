package com.github.spookie6

import Arcade
import com.badlogic.gdx.Gdx
import com.github.spookie6.scenes.MenuScene
import java.io.File

class ProcessRunner(val arcade: Arcade) {
    var process: Process? = null

    fun runGame(game: GameLoader.LoadedGame) {
        if (process != null) {
            println("A process is already running.")
            return
        }

        try {
            val parts = game.metadata.runCommand.split(" ")
            val pb = ProcessBuilder(parts)
                .directory(File(game.dir.toString()))
                .inheritIO()

            process = pb.start()

            Thread {
                try {
                    val exitCode = process?.waitFor() ?: -1
                    println("${game.metadata.name} finished with exit code $exitCode")
                } finally {
                    Gdx.app.postRunnable{ reset() }
                }
            }.start()
        } catch (e: Exception) {
            e.printStackTrace()
            reset()
        }
    }

    fun abortProcess() {
        process?.destroy()
        reset()
    }

    private fun reset() {
        process = null
        arcade.screen = MenuScene(arcade)
        arcade.rendering = true
    }

    fun isRunning(): Boolean = process != null
}
