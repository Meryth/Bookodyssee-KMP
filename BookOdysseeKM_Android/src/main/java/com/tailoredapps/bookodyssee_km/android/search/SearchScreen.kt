package com.tailoredapps.bookodyssee_km.android.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tailoredapps.bookodyssee_km.BookItem
import com.tailoredapps.bookodyssee_km.android.R
import com.tailoredapps.bookodyssee_km.android.base_ui.BookItem
import org.koin.androidx.compose.getViewModel

@Composable
fun SearchScreen(
    onBookClick: (String) -> Unit,
    viewModel: SearchViewModel = getViewModel(),
) {
    val state by viewModel.state.collectAsState()

    SearchView(
        searchQuery = state.query,
        searchResult = state.searchResult,
        onQueryChange = { viewModel.dispatch(SearchViewModel.Action.OnQueryChange(it)) },
        onSearchClick = { viewModel.dispatch(SearchViewModel.Action.OnSearchClick(it)) },
        onBookClick = onBookClick
    )
}

@Composable
fun SearchView(
    searchQuery: String,
    searchResult: List<BookItem>,
    onQueryChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onBookClick: (String) -> Unit,
) {
    Scaffold(
//        title = stringResource(id = R.string.app_name)
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding()
                )
                .padding(24.dp)
        ) {
            SearchBar(
                searchTerm = searchQuery,
                onSearchChange = onQueryChange,
                onSearchClick = onSearchClick,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            LazyColumn() {
                items(searchResult) { book ->
                    BookItem(
                        bookId = book.id,
                        title = book.volumeInfo.title,
                        authorList = book.volumeInfo.authors,
                        imageUrl = book.volumeInfo.imageLinks?.thumbnail,
                        modifier = Modifier.padding(vertical = 6.dp),
                        onBookClick = onBookClick
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    searchTerm: String,
    onSearchChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = searchTerm,
        onValueChange = { value ->
            onSearchChange(value)
        },
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = stringResource(R.string.search_hint),
                color = MaterialTheme.colorScheme.outline
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
            cursorColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )
        },
        singleLine = true,
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClick.invoke(searchTerm)
            }
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    SearchBar(
        searchTerm = "",
        onSearchChange = {},
        onSearchClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchPreview() {
    SearchView(
        searchQuery = "search",
        searchResult = emptyList(),
        onQueryChange = {},
        onSearchClick = {},
        onBookClick = {}
    )
}