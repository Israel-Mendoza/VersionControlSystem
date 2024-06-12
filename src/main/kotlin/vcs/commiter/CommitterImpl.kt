package dev.artisra.vcs.commiter

import dev.artisra.vcs.logger.Logger
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.createDirectory
import kotlin.io.path.exists

class CommitterImpl(override val logger: Logger) : Committer {

    /**
     * Commit the files to the repository.
     * @param message The commit message
     * @param files The files to commit
     * @param repository The repository to commit to
     * @return Whether the commit was successful or not
     */
    override fun commit(commit: Commit): Boolean {
        val fileContentsHashCode = commit.hashCode
        val desiredPath = commit.commitBaseRepository.resolve(fileContentsHashCode)
        if (desiredPath.exists()) return false // No need to commit the current files.

        // The desired path doesn't exist. Creating it:
        desiredPath.createDirectory()
        // Copying the files to the desired path:
        copyFilesToDirectory(commit.files, desiredPath)
        // Logging the commit.
        logger.log(commit)
        return true
    }

    companion object {
        /**
         * Copies files to a target directory.
         * @param filesToCopy The files we want to copy
         * @param targetDirectory The directory we want to copy the files to
         */
        fun copyFilesToDirectory(filesToCopy: List<Path>, targetDirectory: Path) {
            filesToCopy.forEach {
                val targetPath = targetDirectory.resolve(it.fileName)
                Files.copy(it, targetPath, StandardCopyOption.REPLACE_EXISTING)
            }
        }
    }
}