package utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.CategoryUtility.categories
import utils.CategoryUtility.isValidCategory

internal class CategoryUtilityTest {
    @Test
    fun categoriesReturnsFullCategoriesSet() {
        Assertions.assertEquals(5, categories.size)
        Assertions.assertTrue(categories.contains("Home"))
        Assertions.assertTrue(categories.contains("College"))
        Assertions.assertTrue(categories.contains("Work"))
        Assertions.assertTrue(categories.contains("Other"))
        Assertions.assertTrue(categories.contains("Life"))
        Assertions.assertFalse(categories.contains(""))
    }

    @Test
    fun isValidCategoryTrueWhenCategoryExists() {
        Assertions.assertTrue(isValidCategory("Home"))
        Assertions.assertTrue(isValidCategory("home"))
        Assertions.assertTrue(isValidCategory("HOME"))
        Assertions.assertTrue(isValidCategory("College"))
        Assertions.assertTrue(isValidCategory("college"))
        Assertions.assertTrue(isValidCategory("COLLEGE"))
        Assertions.assertTrue(isValidCategory("Work"))
        Assertions.assertTrue(isValidCategory("work"))
        Assertions.assertTrue(isValidCategory("WORK"))
        Assertions.assertTrue(isValidCategory("Life"))
        Assertions.assertTrue(isValidCategory("life"))
        Assertions.assertTrue(isValidCategory("LIFE"))
        Assertions.assertTrue(isValidCategory("Other"))
        Assertions.assertTrue(isValidCategory("other"))
        Assertions.assertTrue(isValidCategory("OTHER"))
    }

    @Test
    fun isValidCategoryFalseWhenCategoryDoesNotExist() {
        Assertions.assertFalse(isValidCategory("Hom"))
        Assertions.assertFalse(isValidCategory("colllege"))
        Assertions.assertFalse(isValidCategory("wrk"))
        Assertions.assertFalse(isValidCategory("lifee"))
        Assertions.assertFalse(isValidCategory("oher"))
        Assertions.assertFalse(isValidCategory("oter"))
        Assertions.assertFalse(isValidCategory(""))
    }
}