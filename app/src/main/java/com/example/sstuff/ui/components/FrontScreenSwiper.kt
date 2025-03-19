package com.example.sstuff.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import kotlin.math.absoluteValue


@SuppressLint("DiscouragedApi")
@Composable
fun FrontScreenSlider(
    slides: List<Pair<String,String>>,
    navController: NavController
) {
    val pagerState = rememberPagerState(pageCount = {
        slides.size
    })
    HorizontalPager(
        state = pagerState,
        pageSpacing = 15.dp,
        pageSize = PageSize.Fixed(300.dp),
        contentPadding = PaddingValues(horizontal = 55.dp, vertical = 10.dp),
        modifier = Modifier.padding(top = 50.dp)
    ) { page ->
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Card(
                Modifier
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleX = lerp(
                            start = 0.8f,
                            stop = 1.2f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleY = lerp(
                            start = 0.8f,
                            stop = 1.2f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .clickable(
                        enabled = true,
                        onClick = {
                            navController.navigate(slides[page].second)
                        }
                    )
            ) {
                val context = LocalContext.current
                val drawableId = remember(slides[page]) {
                    context.resources.getIdentifier(
                        slides[page].first,
                        "drawable",
                        context.packageName
                    )
                }
                Image(
                    painter = painterResource(id = drawableId),
                    contentDescription = null,
                    modifier = Modifier.size(400.dp)
                )
            }

        }
    }
}