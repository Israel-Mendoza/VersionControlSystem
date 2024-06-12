package dev.artisra.vcs.commiter

import java.nio.file.Path
import java.security.MessageDigest

class Commit(
    val user: String,
    val message: String,
    val files: List<Path>,
    val commitBaseRepository: Path
) {
    val hashCode: String
    val commitLog: String
    init {
        val sb = StringBuilder().apply {
            files.forEach { append(it.toFile().readText()) }
        }
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hashBytes = messageDigest.digest(sb.toString().toByteArray())
        hashCode =  hashBytes.joinToString("") { "%02x".format(it) }

        commitLog = "commit $hashCode\nAuthor: $user\n$message"
    }
}