package dev.artisra.vcs.vcs

import dev.artisra.vcs.commiter.Commit
import dev.artisra.vcs.commiter.Committer
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.*

class VersionControllerImpl(
    private val commiter: Committer, basePath: String
) : VersionController {

    private val configPath = Path(basePath)
    private val commitsPath = Path("$basePath/commits")
    private val configFilePath = Path("$basePath/config.txt")
    private val indexFilePath = Path("$basePath/index.txt")
    private val logsFilePath = Path("$basePath/log.txt")
    private val helpDescription = """These are SVCS commands:
config     Get and set a username.
add        Add a file to the index.
log        Show commit logs.
commit     Save changes.
checkout   Restore a file."""

    init {
        // Creating directories if they don't exist:
        createDirIfNotExisting(configPath)
        createDirIfNotExisting(commitsPath)
        // Creating files if they don't exist:
        createFileIfNotExisting(configFilePath)
        createFileIfNotExisting(indexFilePath)
        createFileIfNotExisting(logsFilePath)
    }

    override fun add(file: String?) {
        file?.let {
            if (Path(it).exists()) {
                appendToIndex(it)
                println("The file '$it' is tracked.")
            } else {
                println("Can't find '$it'.")
            }
            return
        }
        val indexLines = indexFilePath.readLines()
        if (indexLines.isEmpty()) {
            println("Add a file to the index.")
            return
        }
        println("Tracked files:")
        indexLines.forEach(::println)
    }

    override fun checkout(commitCode: String?) {
        if (commitCode == null) {
            println("Commit id was not passed.")
            return
        }

        // Getting the commit path.
        val desiredCommit = commitsPath
            .listDirectoryEntries()
            .singleOrNull { it.fileName.toString() == commitCode }

        if (desiredCommit == null) {
            println("Commit does not exist.")
            return
        }

        // Copying the files in the commit path to the root
        Files.list(desiredCommit).forEach {
            Files.copy(it, Path("./${it.fileName}"), StandardCopyOption.REPLACE_EXISTING)
        }

        println("Switched to commit $commitCode.")
    }

    override fun commit(message: String?) {
        if (message == null) {
            println("Message was not passed.")
            return
        }
        val user = try {
            configFilePath.readLines().first().trim()
        } catch (e: Exception) {
            config(null)
            return
        }
        val trackedFiles = indexFilePath.readLines().map { Path("./${it.trim()}") }
        val currentCommit = Commit(user, message, trackedFiles, commitsPath)
        val successfulCommit = commiter.commit(currentCommit)
        if (successfulCommit) {
            println("Changes are committed.")
        } else {
            println("Nothing to commit.")
        }
    }

    override fun config(username: String?) {
        username?.let {
            writeToConfig(it)
            printNameInConfig()
            return
        }
        val nameInConfig = configFilePath.readText().trim()
        if (nameInConfig.isBlank() || nameInConfig.isEmpty()) {
            println("Please, tell me who you are.")
            return
        }
        printNameInConfig()
    }

    override fun help() {
        println(helpDescription)
    }

    override fun log() {
        val logs = commiter.logger.readLogs()
        if (logs.isEmpty()) {
            println("No commits yet.")
        } else {
            println(logs.trim())
        }
    }

    private fun writeToConfig(text: String) {
        configFilePath.writeText("$text\n")
    }

    private fun appendToIndex(text: String) {
        indexFilePath.appendText("$text\n")
    }

    private fun printNameInConfig() {
        val nameInConfig = configFilePath.readText().trim()
        println("The username is $nameInConfig.")
    }

    companion object {
        fun createDirIfNotExisting(path: Path) {
            if (!path.exists()) path.createDirectory()
        }

        fun createFileIfNotExisting(path: Path) {
            if (!path.exists()) path.createFile()
        }
    }
}