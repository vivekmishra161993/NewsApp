package com.mtt.newsapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mtt.newsapp.R
import com.mtt.newsapp.data.entity.ArticlesItem
import com.mtt.newsapp.data.entity.NewsResponse
import com.mtt.newsapp.ui.theme.AppTypography

@Composable
fun Loader() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(80.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(60.dp)
                .padding(10.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun NewsList(response: NewsResponse) {
    LazyColumn {
        items(response.articles!!.size) {
            val articles = response.articles
            NormalTextComponent(value = articles[it]?.title ?: "NA")
        }
    }
}


@Composable
fun NewsRowComponent(page: Int, articlesItem: ArticlesItem?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(Color.White)
    ) {
        AsyncImage(
            model = articlesItem?.urlToImage,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder)
        )
        Spacer(modifier = Modifier.size(10.dp))
        articlesItem?.title?.let { HeadingTextComponent(value = it) }
        Spacer(modifier = Modifier.size(10.dp))
        articlesItem?.description?.let { NormalTextComponent(value = it) }
        Spacer(modifier = Modifier.weight(1f))
        AuthorDetailsComponent(articlesItem?.author, articlesItem?.source?.name)
    }
}

@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentHeight(),
        textAlign = TextAlign.Justify,
        style = AppTypography.bodyMedium
    )
}

@Composable
fun HeadingTextComponent(value: String, textAlign: TextAlign = TextAlign.Justify) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
            .wrapContentHeight(), textAlign = textAlign,
        style = AppTypography.titleLarge
    )
}

@Preview
@Composable
fun NewsRowComponentPreview() {
    val article = ArticlesItem(author = "Mr.X", title = "Compose Check")
    NewsRowComponent(page = 0, articlesItem = article)
}

@Composable
fun AuthorDetailsComponent(authorName: String?, source: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 16.dp)
    ) {
        authorName?.let { Text(text = it, style = AppTypography.bodySmall) }
        Spacer(modifier = Modifier.weight(1f))
        source?.let { Text(text = it, style = AppTypography.bodySmall) }
    }
}

@Composable
fun EmptyStateComponent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_news),
            contentDescription = "No News Available"
        )
        HeadingTextComponent(value = "No News Available", TextAlign.Justify)
    }
}

@Preview
@Composable
fun EmptyStateComponentPreview() {
    EmptyStateComponent()
}