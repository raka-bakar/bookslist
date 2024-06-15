package com.raka.books.ui.bookdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raka.books.ui.navigation.MainNavigation
import com.raka.books.usecase.GetBookUseCase
import com.raka.data.CallResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    getBookUseCase: GetBookUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Set SavedStateHandle to MainNavigation
    private val args = MainNavigation.Detail.Arguments(savedStateHandle)

    // getting argument from SavedStateHandle in MainNavigation
    private val idBook = args.idBook.get()

    /**
     * bookDetail of Flow type
     * it is being observed by BookDetailScreen
     */
    val bookDetail = getBookUseCase.getBook(idBook).stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(), CallResult.loading()
    )
}