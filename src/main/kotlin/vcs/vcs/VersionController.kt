package dev.artisra.vcs.vcs

interface VersionController {
    fun add(file: String?)
    fun commit(message: String?)
    fun config(username: String?)
    fun checkout(commitCode: String?)
    fun help()
    fun log()
}