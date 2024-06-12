package dev.artisra

import dev.artisra.utils.dispatchAction
import dev.artisra.utils.getCommand
import dev.artisra.vcs.commiter.CommitterImpl
import dev.artisra.vcs.logger.LoggerImpl
import dev.artisra.vcs.vcs.VersionControllerImpl
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.exists

fun main(args: Array<String>) {
    // Creating the base directory for the VCS system
    val baseVCSPath = Path("./vcs")
    if (!baseVCSPath.exists()) baseVCSPath.createDirectory()

    // Creating a Version Controller object to handle versioning.
    val logger = LoggerImpl("./vcs/log.txt")
    val committer = CommitterImpl(logger)
    val vs = VersionControllerImpl(committer, "./vcs")

    // Main program handler:
    val command: String? = getCommand(args)
    val argument: String? = args.getOrNull(1)
    dispatchAction(command, argument, vs)
}