package com.raka.books

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavGraph
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.raka.books.ui.home.HomeScreen
import com.raka.data.CallResult
import com.raka.data.model.Book
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val dataResult = listOf(
        Book(id=13, image="https://dummyimage.com/500x500.png/666666/ffffff",
            releaseDate="6/5/1967",
            author="Gabriel García Márquez",
            description="Gabriel García Márquez's magical realist novel chronicles the Buendía family's multigenerational story in the fictional town of Macondo. The novel begins with the founding of Macondo by José Arcadio Buendía and his wife, Úrsula. The Buendía family is marked by extraordinary events and supernatural occurrences. Each generation struggles with love, power, and the search for identity. The novel's rich symbolism and lyrical prose create a tapestry of myth and reality. The cyclical nature of the family's history reflects broader themes of fate and destiny. Márquez's exploration of solitude and human connection is central to the narrative. The novel's intricate plot and vivid characters draw readers into the world of Macondo. The Buendía family's triumphs and tragedies mirror the broader history of Latin America. Themes of political upheaval, social change, and cultural identity are woven throughout the story. The novel's magical elements enhance its exploration of human experience. The narrative's non-linear structure and shifting perspectives add depth and complexity. Márquez's masterful blending of the ordinary and the extraordinary creates a unique literary experience. One Hundred Years of Solitude is celebrated for its imaginative storytelling and profound themes. The novel's enduring legacy and impact on literature are undeniable. Its exploration of love, loss, and the passage of time continues to resonate with readers.",
            title="One Hundred Years of Solitude"),
        Book(id=12, image="https://dummyimage.com/500x500.png/666666/ffffff",
            releaseDate="6/5/1988",
            author="Nadila N",
            description="The Buendía family's triumphs and tragedies mirror the broader history of Latin America. Themes of political upheaval, social change, and cultural identity are woven throughout the story. The novel's magical elements enhance its exploration of human experience. The narrative's non-linear structure and shifting perspectives add depth and complexity. Márquez's masterful blending of the ordinary and the extraordinary creates a unique literary experience. One Hundred Years of Solitude is celebrated for its imaginative storytelling and profound themes. The novel's enduring legacy and impact on literature are undeniable. Its exploration of love, loss, and the passage of time continues to resonate with readers.",
            title="The Alchemist")
    )

    @Test
    fun testHomeScreen(){


        val callResult = CallResult.success(dataResult)
        composeTestRule. setContent {
            HomeScreen(callResult = callResult, onRetryButtonClick = { }, navigateToDetail = {})
        }

        dataResult.forEach { item->
            composeTestRule.onNodeWithText(item.title).assertIsDisplayed()
        }
    }

    @Test
    fun testNoDataHomeScreen(){
        val callResult = CallResult.error<List<Book>>("",500)
        composeTestRule.setContent {
            HomeScreen(callResult = callResult, onRetryButtonClick = { /*TODO*/ },{})
        }
        composeTestRule.onNodeWithText("No Data Found").assertIsDisplayed()
        composeTestRule.onNodeWithText("Try again").performClick()
    }

    @Test
    fun testNavigateToDetailScreen(){
        val callResult = CallResult.success(dataResult)
        composeTestRule. setContent {
            HomeScreen(callResult = callResult, onRetryButtonClick = { }, navigateToDetail = {})
        }

        // click one of the item
        composeTestRule.onNodeWithText(dataResult[0].title).performClick()

        // in detail screen, assert if data is correct
        composeTestRule.onNodeWithText(dataResult[0].title).assertIsDisplayed()
        composeTestRule.onNodeWithText(dataResult[0].description).assertIsDisplayed()
    }
}