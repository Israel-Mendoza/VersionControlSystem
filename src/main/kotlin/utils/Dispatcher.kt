package dev.artisra.utils

import dev.artisra.vcs.vcs.VersionController

fun dispatchAction(command: String?, argument: String?, vc: VersionController) {

    val option = getCommandOption(command)

    when (option) {
        VCSOptions.ADD -> vc.add(argument)
        VCSOptions.CHECKOUT -> vc.checkout(argument)
        VCSOptions.COMMIT -> vc.commit(argument)
        VCSOptions.CONFIG -> vc.config(argument)
        VCSOptions.LOG -> vc.log()
        VCSOptions.HELP -> vc.help()
        VCSOptions.UNKNOWN -> println("'$command' is not a SVCS command.")
    }
}