package dev.artisra.utils

fun getCommand(args: Array<String>): String? {
    if (args.isEmpty()) return null
    return args.first()
}

fun getCommandOption(commandDescription: String?): VCSOptions = when {
    commandDescription.equals("config", ignoreCase = true) -> VCSOptions.CONFIG
    commandDescription.equals("add", ignoreCase = true) -> VCSOptions.ADD
    commandDescription.equals("log", ignoreCase = true) -> VCSOptions.LOG
    commandDescription.equals("commit", ignoreCase = true) -> VCSOptions.COMMIT
    commandDescription.equals("checkout", ignoreCase = true) -> VCSOptions.CHECKOUT
    commandDescription.equals("--help", ignoreCase = true) -> VCSOptions.HELP
    commandDescription == null -> VCSOptions.HELP
    else -> VCSOptions.UNKNOWN
}
