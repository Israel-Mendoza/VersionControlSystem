package dev.artisra.vcs.logger

import dev.artisra.vcs.commiter.Commit
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.*

class LoggerImpl(logFileLocation: String) : Logger {

    private val logFilePath: Path = Path(logFileLocation)

    init {
        if (!logFilePath.exists()) {
            logFilePath.createFile()
        }
    }

    override fun log(commit: Commit) {
        val currentLogs = logFilePath.readText()
        Files.delete(logFilePath)
        Files.createFile(logFilePath)
        logFilePath.writeText("${commit.commitLog}\n\n")
        logFilePath.appendText(currentLogs)
    }

    override fun readLogs(): String {
        return logFilePath.readText()
    }
}