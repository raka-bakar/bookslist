package com.raka.books.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raka.books.usecase.GetBooksUseCase
import com.raka.books.utils.RefreshFlow
import com.raka.data.CallResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(getBooksUseCase: GetBooksUseCase) :
    ViewModel() {

    /**
     * booksList of Flow type
     * it is being observed by HomeScreen
     */

    val booksList = RefreshFlow {
        getBooksUseCase.getBooks().stateIn(
            viewModelScope + Dispatchers.IO,
            SharingStarted.WhileSubscribed(), CallResult.loading()
        )
    }
}