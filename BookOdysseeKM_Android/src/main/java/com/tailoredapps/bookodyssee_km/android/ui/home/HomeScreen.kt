package com.tailoredapps.bookodyssee_km.android.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = getViewModel(),
    onBookItemClick: (id: String) -> Unit,
    onAddClick: () -> Unit,
    onToReadClick: () -> Unit,
    onFinishedClick: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.dispatch(HomeViewModel.Action.GetSavedBooks)
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        HomeView(
            navController = navController,
            toReadList = state.toReadList,
            currentlyReadingList = state.currentlyReadingList,
            onBookItemClicked = onBookItemClick,
            onAddClick = onAddClick,
            onToReadClick = onToReadClick,
            onFinishedClick = onFinishedClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeView(
    navController: NavController,
    toReadList: List<LocalBook>,
    currentlyReadingList: List<LocalBook>,
    onBookItemClicked: (id: String) -> Unit,
    onAddClick: () -> Unit,
    onToReadClick: () -> Unit,
    onFinishedClick: () -> Unit
) {
    AppScaffold(
        title = stringResource(id = R.string.app_name),
        actions = {
            IconButton(onClick = onAddClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "btnAdd",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
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

        ) {

            item {
                Text(
                    text = stringResource(R.string.lb_currently_reading),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(
                        start = 24.dp,
                        top = 16.dp
                    )
                )

                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )
            }

            items(currentlyReadingList) { book ->
                BookItem(
                    bookId = book.bookId,
                    title = book.title.orEmpty(),
                    authorList = convertStringToList(book.authors.orEmpty()),
                    imageUrl = book.imageLink,
                    modifier = Modifier.padding(
                        horizontal = 24.dp,
                        vertical = 6.dp
                    ),
                    onBookClick = { onBookItemClicked(book.bookId) }
                )
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )

            }

            item {
                Text(
                    text = stringResource(R.string.lb_to_read),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(
                        start = 24.dp,
                        top = 16.dp
                    )
                )

                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )
            }

            items(toReadList) { book ->
                BookItem(
                    bookId = book.bookId,
                    title = book.title.orEmpty(),
                    authorList = convertStringToList(book.authors.orEmpty()),
                    imageUrl = book.imageLink,
                    modifier = Modifier.padding(
                        horizontal = 24.dp,
                        vertical = 6.dp
                    ),
                    onBookClick = { onBookItemClicked(book.bookId) }
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

@Preview
@Composable
private fun HomePreview() {
    MaterialTheme {
        HomeView(
            navController = rememberNavController(),
            toReadList = listOf(
                LocalBook(
                    userId = 0,
                    bookId = "id",
                    title = "title",
                    authors = "author",
                    publisher = "publisher",
                    publishedDate = "publishedDate",
                    pageCount = 200,
                    imageLink = "image"
                )
            ),
            currentlyReadingList = listOf(
                LocalBook(
                    userId = 0,
                    bookId = "id",
                    title = "title",
                    authors = "author",
                    publisher = "publisher",
                    publishedDate = "publishedDate",
                    pageCount = 200,
                    imageLink = "image",
                    readingState = ReadingState.CURRENTLY_READING.state
                )
            ),
            onBookItemClicked = {},
            onAddClick = {},
            onToReadClick = {},
            onFinishedClick = {}
        )
    }
}
