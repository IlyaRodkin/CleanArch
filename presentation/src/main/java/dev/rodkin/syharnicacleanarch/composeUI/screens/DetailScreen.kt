package dev.rodkin.syharnicacleanarch.composeUI.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.rodkin.syharnicacleanarch.composeUI.common.RedButton
import coil.compose.SubcomposeAsyncImage
import dev.rodkin.domain.useCases.useCasesImpl.OnBasketMode
import dev.rodkin.syharnicacleanarch.R
import dev.rodkin.syharnicacleanarch.composeUI.theme.Icons
import dev.rodkin.syharnicacleanarch.presenters.BasketViewModel
import dev.rodkin.syharnicacleanarch.presenters.CatalogViewModel

@Composable
fun DetailScreen(
    catalogViewModel: CatalogViewModel,
    basketViewModel: BasketViewModel,
    navController: NavController,
    itemId: Long
) {
    val item = catalogViewModel.getItemFromId(itemId)
    val count = basketViewModel.getItemCountFromId(itemId).collectAsState(initial = 0).value
    if (item == null) {

    } else
        Box(modifier = Modifier.fillMaxHeight()) {
            LazyColumn {
                item() {
                    Box() {
                        LazyRow {
                            items(item.imgUrl.size) { index ->
                                Row() {
                                    SubcomposeAsyncImage(
                                        modifier = Modifier
                                            .height(340.dp)
                                            .widthIn(min = 200.dp),
                                        model = item.imgUrl[index],
                                        loading = {
                                            Box(
                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                CircularProgressIndicator()
                                            }
                                        },
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop
                                    )
                                    Divider(
                                        modifier = Modifier
                                            .height(340.dp)
                                            .width(2.dp),
                                        color = Color.Black,
                                        thickness = 2.dp
                                    )
                                }
                            }
                        }
                        IconButton(
                            modifier = Modifier.align(Alignment.TopStart),
                            onClick = { navController.navigateUp() }) {
                            Image(
                                painter = painterResource(id = Icons.ArrowBack.image),
                                contentDescription = stringResource(id = Icons.ArrowBack.description)
                            )
                        }
                    }
                    Divider(
                        color = Color.Black,
                        thickness = 2.dp
                    )
                    Box(
                        modifier = Modifier
                            .height(70.dp)
                            .fillMaxWidth()
                            .padding(start = 18.dp, end = 18.dp, top = 13.dp)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.TopStart),
                            text = item.label,
                            fontSize = 22.sp,
                        )
                        Row(modifier = Modifier.align(Alignment.BottomStart)) {
                            Text(
                                text = item.price.toString().plus(" BYN"),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                modifier = Modifier
                                    .padding(start = 10.dp, bottom = 2.dp)
                                    .align(Alignment.Bottom),
                                text = item.weight.toString().plus(" г"),
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                        Row(
                            modifier = Modifier.align(Alignment.BottomEnd),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(28.dp),
                                painter = painterResource(id = Icons.BigSmile.image),
                                contentDescription = stringResource(
                                    id = Icons.BigSmile.description
                                )
                            )
                            Icon(
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .size(28.dp),
                                painter = painterResource(id = Icons.Share.image),
                                contentDescription = stringResource(
                                    id = Icons.Share.description
                                )
                            )
                        }

                    }
                    Divider(
                        modifier = Modifier.padding(12.dp),
                        color = Color.Black,
                        thickness = 2.dp
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 18.dp),
                        text = item.about,
                        fontSize = 16.sp
                    )
                    Row(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 150.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.composition),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = item.productComposition,
                                fontSize = 12.sp
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.nutrition_facts),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = item.toString(),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
            if (count == 0)
                RedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, bottom = 70.dp)
                        .align(Alignment.BottomCenter),
                    text = stringResource(id = R.string.add_to_basket),
                    onClickButton = { basketViewModel.updateBasket(item, OnBasketMode.ADD) },
                    icon = Icons.BigPlus
                )
            else
                RedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, bottom = 70.dp)
                        .align(Alignment.BottomCenter),
                    text = stringResource(id = R.string.add_to_basket),
                    count = count,
                    backgroundColor = Color.White,
                    onClickIconAdd = { basketViewModel.updateBasket(item, OnBasketMode.ADD) },
                    onClickIconRemove = { basketViewModel.updateBasket(item, OnBasketMode.REMOVE) }
                )
        }
}


