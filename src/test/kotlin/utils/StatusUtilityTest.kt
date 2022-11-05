package utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.StatusUtility.statuses
import utils.StatusUtility.isValidStatus

internal class StatusUtilityTest {

    @Test
    fun statusesReturnsFullStatusesSet() {
        Assertions.assertEquals(3, StatusUtility.statuses.size)
        Assertions.assertTrue(StatusUtility.statuses.contains("Todo"))
        Assertions.assertTrue(StatusUtility.statuses.contains("Doing"))
        Assertions.assertTrue(StatusUtility.statuses.contains("Done"))
        Assertions.assertFalse(StatusUtility.statuses.contains(""))
    }

    @Test
    fun isValidStatusTrueWhenStatusExists() {
        Assertions.assertTrue(StatusUtility.isValidStatus("Todo"))
        Assertions.assertTrue(StatusUtility.isValidStatus("todo"))
        Assertions.assertTrue(StatusUtility.isValidStatus("TODO"))
        Assertions.assertTrue(StatusUtility.isValidStatus("doing"))
        Assertions.assertTrue(StatusUtility.isValidStatus("DOING"))
        Assertions.assertTrue(StatusUtility.isValidStatus("Doing"))
        Assertions.assertTrue(StatusUtility.isValidStatus("DONE"))
        Assertions.assertTrue(StatusUtility.isValidStatus("done"))
        Assertions.assertTrue(StatusUtility.isValidStatus("Done"))
    }

    @Test
    fun isValidStatusFalseWhenStatusDoesNotExist() {
        Assertions.assertFalse(StatusUtility.isValidStatus("To"))
        Assertions.assertFalse(StatusUtility.isValidStatus("toodo"))
        Assertions.assertFalse(StatusUtility.isValidStatus("dome"))
        Assertions.assertFalse(StatusUtility.isValidStatus("doimg"))
        Assertions.assertFalse(StatusUtility.isValidStatus("doig"))
        Assertions.assertFalse(StatusUtility.isValidStatus("don"))
        Assertions.assertFalse(StatusUtility.isValidStatus(""))
    }

}