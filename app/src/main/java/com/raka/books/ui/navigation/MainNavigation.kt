package com.raka.books.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.raka.books.ui.bookdetail.BookDetailViewModel
import com.raka.books.ui.home.HomeViewModel

/**
 * MainNavigation graph that contains first app page
 */
sealed class MainNavigation(override val route: String) : Navigation(route) {
    override val graphId = "Root"

    companion object {
        /**
         * get all destinations on this graph
         */
        fun getAllNavigation() = setOf(Home, Detail)
    }

    /**
     * Home Screen
     */
    object Home : MainNavigation("HOME_SCREEN") {
        context(NavGraphBuilder)
        override fun compose(controller: NavController) {
            composable(route = getFullRoute(), arguments = getArguments()) {
                val viewModel: HomeViewModel = hiltViewModel()
                val callResult by viewModel.booksList.collectAsStateWithLifecycle()

            }
        }
    }

    /**
     * Book Detail Screen
     */
    object Detail : MainNavigation("DETAIL_SCREEN") {
        // Argument keys for passing data between screen
        object ArgKeys {
            const val ID_BOOK = "ID_BOOK"
        }

        // Argument for passing data from Home to Detail Screen
        class Arguments(savedStateHandle: SavedStateHandle) {
            val idBook = SavedArgument(savedStateHandle, ArgKeys.ID_BOOK, 0)
        }

        // Navigation from Home to Detail Screen
        fun NavController.navigateTo(idBook: Int) {
            navigate(route = "$route/$idBook")
        }

        context(NavGraphBuilder)
        override fun compose(controller: NavController) {
            composable(route = getFullRoute(), arguments = getArguments()) {
                val viewModel: BookDetailViewModel = hiltViewModel()
                val callResult by viewModel.bookDetail.collectAsStateWithLifecycle()
            }
        }
    }
}