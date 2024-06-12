package dev.artisra.vcs.logger

import dev.artisra.vcs.commiter.Commit

interface Logger {
    fun log(commit: Commit)
    fun readLogs(): String
}