package com.tailoredapps.bookodyssee_km.android.ui.book

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.tailoredapps.bookodyssee_km.BookImageLinks
import com.tailoredapps.bookodyssee_km.VolumeInfo
import com.tailoredapps.bookodyssee_km.android.R
import com.tailoredapps.bookodyssee_km.android.ui.base_ui.AppScaffold
import com.tailoredapps.bookodyssee_km.android.ui.base_ui.PrimaryButton
import com.tailoredapps.bookodyssee_km.android.ui.book.items.DataRow
import com.tailoredapps.bookodyssee_km.db.ReadingState
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun BookScreen(
    bookId: String,
    viewModel: BookViewModel = getViewModel { parametersOf(bookId) }
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    val volumeInfo = state.bookItem?.volumeInfo

    if (volumeInfo != null) {
        BookView(
            bookVolume = volumeInfo,
            scrollState = scrollState,
            readingState = state.readingState,
            onAddToListClick = { viewModel.dispatch(BookViewModel.Action.AddBookToReadingList) },
            onUpdateReadingState = { viewModel.dispatch(BookViewModel.Action.ChangeReadingState(it)) },
            onRemoveFromListClick = { viewModel.dispatch(BookViewModel.Action.RemoveBookFromReadingList) }
        )
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookView(
    bookVolume: VolumeInfo,
    scrollState: ScrollState,
    readingState: String,
    onAddToListClick: () -> Unit,
    onUpdateReadingState: (String) -> Unit,
    onRemoveFromListClick: () -> Unit
) {
    AppScaffold(
        title = stringResource(id = R.string.app_name)
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding()
                )
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = bookVolume.title,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            )

            if (!bookVolume.imageLinks?.thumbnail.isNullOrEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(bookVolume.imageLinks?.thumbnail),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(
                            width = 100.dp,
                            height = 140.dp
                        )
                        .align(Alignment.CenterHorizontally)
                )
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )

            DataRow(
                field = stringResource(R.string.lb_author),
                value = bookVolume.authors.orEmpty()
            )
            DataRow(
                field = stringResource(R.string.lb_publisher),
                value = bookVolume.publisher.orEmpty()
            )
            DataRow(field = stringResource(R.string.lb_year), value = bookVolume.publishedDate)
            DataRow(
                field = stringResource(R.string.lb_page_count),
                value = bookVolume.pageCount.toString()
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )

            Text(
                text = bookVolume.description.orEmpty(),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            if (readingState == ReadingState.NOT_ADDED.state) {
                PrimaryButton(
                    btnText = stringResource(R.string.btn_add_to_list),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    onClick = onAddToListClick
                )
            } else {
                if (readingState != ReadingState.FINISHED.state) {

                    if (readingState != ReadingState.CURRENTLY_READING.state) {
                        PrimaryButton(
                            btnText = stringResource(R.string.btn_start_reading),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 24.dp),
                            onClick = { onUpdateReadingState(ReadingState.CURRENTLY_READING.state) }
                        )
                    }

                    PrimaryButton(
                        btnText = stringResource(R.string.btn_finish),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        onClick = { onUpdateReadingState(ReadingState.FINISHED.state) }
                    )
                }

                PrimaryButton(
                    btnText = stringResource(R.string.btn_remove_from_list),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    onClick = onRemoveFromListClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookViewPreview() {
    BookView(
        bookVolume = VolumeInfo(
            title = "101 dad jokes",
            authors = listOf("Florian Mötz"),
            publisher = "Diamir",
            publishedDate = "18.12.2023",
            description = "Jokes",
            pageCount = 101,
            imageLinks = BookImageLinks("asd")
        ),
        scrollState = ScrollState(0),
        readingState = ReadingState.FINISHED.state,
        onAddToListClick = {},
        onUpdateReadingState = {},
        onRemoveFromListClick = {}
    )
}
