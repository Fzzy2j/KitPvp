package me.fzzy.kits.util

object Methods {
    fun makePrettyString(s: String): String {
        val split = s.replace("_", " ").split(" ")
        var result = ""
        for (word in split) {
            result += " ${word.substring(0, 1).toUpperCase()}${word.substring(1).toLowerCase()}"
        }
        return result.substring(1)
    }
}