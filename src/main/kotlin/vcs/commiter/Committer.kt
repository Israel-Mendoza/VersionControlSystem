package dev.artisra.vcs.commiter

import dev.artisra.vcs.logger.Logger

interface Committer {
    val logger: Logger
    fun commit(commit: Commit): Boolean
}