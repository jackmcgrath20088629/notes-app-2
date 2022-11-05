package utils

object StatusUtility {
    @JvmStatic
    val statuses = setOf("Todo", "Doing", "Done")

    @JvmStatic
    fun isValidStatus(statusToCheck: String?): Boolean {
        for (status in statuses) {
            if (status.equals(statusToCheck, ignoreCase = true)) {
                return true
            }
        }
        return false
    }
}