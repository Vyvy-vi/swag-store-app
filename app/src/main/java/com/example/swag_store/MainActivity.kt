package com.example.swag_store

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
    private val productsState: MutableStateFlow<Products> =
        MutableStateFlow(Products(ProductWarehouse.productsList))
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InitUI()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun InitUI() {
        val products by productsState.collectAsState()
        Toast.makeText(this, "Searching products...", Toast.LENGTH_SHORT).show()
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            var searchPhrase by remember {
                mutableStateOf("")
            }


            TopAppBar()
            UpperPanel()

            OutlinedTextField(
                value = searchPhrase,
                onValueChange = {
                    searchPhrase = it
                },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp)
            )

            var productItems = products.items;
            if (searchPhrase.isNotEmpty()) {
                productItems = productItems.filter { it.title.contains(searchPhrase, ignoreCase = true) }
            }

            ProductItemsList(items = productItems)
    }
}

@Composable
private fun ProductItemsList(items: List<ProductItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 20.dp)
    ) {
        items(
            items = items,
            itemContent = { productItem ->
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = productItem.image),
                        contentDescription = productItem.title,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth(0.25f)
                    )
                    Column (modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = productItem.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = productItem.description,
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .padding(top = 5.dp, bottom = 5.dp)
                        )
                        Text(
                            text = "$${productItem.price}",
                            textAlign = TextAlign.Right,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        )
    }
}
}