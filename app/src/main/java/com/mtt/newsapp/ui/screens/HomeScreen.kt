package com.mtt.newsapp.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mtt.newsapp.ui.components.EmptyStateComponent
import com.mtt.newsapp.ui.components.Loader
import com.mtt.newsapp.ui.components.NewsRowComponent
import com.mtt.newsapp.ui.viewmodel.NewsViewModel
import com.mtt.utilities.CoreUtilities
import com.mtt.utilities.ResourceState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    newsViewModel: NewsViewModel = hiltViewModel()
) {
    if (CoreUtilities.isInternetConnected(LocalContext.current)) {
        val newsResponse by newsViewModel.news.collectAsState()
        val pagerState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f) {
            100
        }

        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            pageSize = PageSize.Fill, pageSpacing = 8.dp
        ) { page: Int ->
            when (newsResponse) {
                is ResourceState.Loading -> {
                    Loader()
                }

                is ResourceState.Success -> {
                    val response = (newsResponse as ResourceState.Success).data
                    Log.d("TAG", "HomeScreen:${response.totalResults} ")
                    if (response.articles!!.isNotEmpty()) {
                        NewsRowComponent(page, response.articles[page])
                    } else {
                        EmptyStateComponent()
                    }
                }

                is ResourceState.Error -> {

                    val error = (newsResponse as ResourceState.Error)
                    Log.d("TAG", "HomeScreen: $error")

                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No Internet Available", textAlign = TextAlign.Center, modifier = Modifier)
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No Internet Available", textAlign = TextAlign.Center, modifier = Modifier)
    }

}