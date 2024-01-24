package com.tailoredapps.bookodyssee_km.android.ui.finished

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tailoredapps.bookodyssee_km.android.R
import com.tailoredapps.bookodyssee_km.android.ui.base_ui.AppNavigationBar
import com.tailoredapps.bookodyssee_km.android.ui.base_ui.AppScaffold
import com.tailoredapps.bookodyssee_km.android.ui.base_ui.BookItem
import com.tailoredapps.bookodyssee_km.db.LocalBook
import com.tailoredapps.bookodyssee_km.db.ReadingState
import com.tailoredapps.bookodyssee_km.db.convertStringToList
import org.koin.androidx.compose.getViewModel

@Composable
fun FinishedScreen(
    navController: NavController,
    viewModel: FinishedViewModel = getViewModel(),
    onBookItemClick: (String) -> Unit,
    onToReadClick: () -> Unit,
    onFinishedClick: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.dispatch(FinishedViewModel.Action.GetFinishedBooks)
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        FinishedView(
            navController = navController,
            bookList = state.finishedBookList,
            onBookItemClick = onBookItemClick,
            onToReadClick = onToReadClick,
            onFinishedClick = onFinishedClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinishedView(
    navController: NavController,
    bookList: List<LocalBook>,
    onBookItemClick: (String) -> Unit,
    onToReadClick: () -> Unit,
    onFinishedClick: () -> Unit
) {
    AppScaffold(
        title = stringResource(R.string.title_finished),
        bottomBar = {
            AppNavigationBar(
                navController = navController,
                onToReadClick = onToReadClick,
                onFinishedClick = onFinishedClick
            )
        },
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(top = 16.dp)
        ) {
            items(bookList) { book ->
                BookItem(
                    bookId = book.bookId,
                    title = book.title.orEmpty(),
                    authorList = convertStringToList(book.authors.orEmpty()),
                    imageUrl = book.imageLink,
                    modifier = Modifier.padding(
                        horizontal = 24.dp,
                        vertical = 6.dp
                    ),
                    onBookClick = { onBookItemClick(book.bookId) }
                )
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FinishedPreview() {
    FinishedView(
        navController = rememberNavController(),
        bookList = listOf(
            LocalBook(
                userId = 0,
                bookId = "id",
                title = "title",
                authors = "author",
                publisher = "publisher",
                publishedDate = "publishedDate",
                pageCount = 200,
                imageLink = "image",
                readingState = ReadingState.FINISHED.state
            )
        ),
        onBookItemClick = {},
        onToReadClick = {},
        onFinishedClick = {}
    )
}