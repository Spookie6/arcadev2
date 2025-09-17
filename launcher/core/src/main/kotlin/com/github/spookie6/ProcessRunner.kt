package com.github.spookie6

import Arcade
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
                val exitCode = process?.waitFor() ?: -1
                println("${game.metadata.name} finished with exit code $exitCode")
                process = null
            }.start()
        } catch (e: Exception) {
            e.printStackTrace()
            process = null
        }
    }

    fun abortProcess() {
        process?.destroy()
        process = null
    }

    fun isRunning(): Boolean = process != null
}
