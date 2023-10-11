package dev.jmoicano.salesmanager.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.jmoicano.salesmanager.R
import dev.jmoicano.salesmanager.ui.Screen
import dev.jmoicano.salesmanager.ui.currencyFormat
import dev.jmoicano.salesmanager.ui.data.ViewProduct
import dev.jmoicano.salesmanager.ui.data.ViewSale
import dev.jmoicano.salesmanager.ui.home.viewmodel.SalesViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: SalesViewModel
) {
    val uiState by viewModel.salesUiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getSales()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { navController.navigate(Screen.Product.route) }) {
            Text(text = stringResource(R.string.make_a_sell))
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            content = {
                items(uiState.sales) { sale ->
                    Sale(sale = sale)
                }
            })

        Text(
            modifier = Modifier.align(Alignment.End),
            text = "Total de vendas: ${uiState.totalEarnings.currencyFormat()}"
        )

    }
}

@Composable
fun Sale(sale: ViewSale) {
    var expanded by remember { mutableStateOf(false) }
    val discount = sale.discount
    val totalSale = sale.salesTotal
    Card(modifier = Modifier.padding(vertical = 5.dp)) {
        Column(modifier = Modifier.padding(5.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1F)) {
                    Text(text = sale.client)
                    Text(text = sale.salesFinal.currencyFormat())
                }
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector =
                        if (expanded) Icons.Outlined.KeyboardArrowUp
                        else Icons.Outlined.KeyboardArrowDown,
                        contentDescription = stringResource(R.string.expand)
                    )
                }
            }
            if (expanded) {
                sale.products.forEach { product ->
                    val rate = product.totalValue / totalSale
                    product.discount = discount * rate
                    Product(product = product)
                }
            }
        }
    }
}

@Composable
fun Product(product: ViewProduct) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "${product.quant} ${product.name}")
        Text(text = product.finalValue.currencyFormat())
    }
}
