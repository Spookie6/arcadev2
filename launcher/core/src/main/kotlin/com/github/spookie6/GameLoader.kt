package com.github.spookie6

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class GameLoader {
    val loadedGames = mutableListOf<LoadedGame>()
    private val json = Json { ignoreUnknownKeys = true } // lenient, safe

    data class LoadedGame(
        val metadata: GameMetadata,
        val dir: Path
    )

    @Serializable
    data class GameMetadata(
        val name: String,
        val authors: List<String>,
        val entrypoint: String,
        val coverimage: String,
        val runCommandRaw: String? = null
    ) {
        val runCommand: String
            get() = runCommandRaw ?: "python $entrypoint"
    }

    fun loadGames () {
        val basePath = Paths.get("").toAbsolutePath().parent
        val gamesPath = basePath.resolve("games")

        Files.list(gamesPath).use { gameDirs ->
            gameDirs.filter { Files.isDirectory(it) }.forEach { dir ->
                val metaFile = dir.resolve("metadata.json")
                if (Files.exists(metaFile)) {
                    try {
                        val metadata = loadMetaData(metaFile)
                        loadedGames.add(LoadedGame(metadata, dir))
                    } catch (e: Exception) {
                        println("Failed to load metadata from $metaFile: ${e.message}")
                    }
                } else {
                    println("No metadata.json in $dir, skipping")
                }
            }
        }
        println("Loaded games: $loadedGames")
    }

    fun loadMetaData(path: Path): GameMetadata {
        val content = Files.newBufferedReader(path).use { it.readText() }
        return json.decodeFromString<GameMetadata>(content)
    }
}
