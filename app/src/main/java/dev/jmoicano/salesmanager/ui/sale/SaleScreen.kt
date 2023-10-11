package dev.jmoicano.salesmanager.ui.sale

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.jmoicano.salesmanager.R
import dev.jmoicano.salesmanager.ui.currencyFormat
import dev.jmoicano.salesmanager.ui.data.ViewProduct
import dev.jmoicano.salesmanager.ui.sale.viewmodel.ProductUiState
import dev.jmoicano.salesmanager.ui.sale.viewmodel.ProductsViewModel

@Composable
fun SaleScreen(
    navController: NavHostController,
    viewModel: ProductsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getSaleNumber()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.End),
            text = stringResource(R.string.sale_number, uiState.saleId)
        )

        Divider(modifier = Modifier.padding(vertical = 10.dp))

        CostumerSection(viewModel)

        Divider(modifier = Modifier.padding(vertical = 10.dp))

        ProductsSection(modifier = Modifier.weight(1F), viewModel, uiState)

        Divider(modifier = Modifier.padding(vertical = 10.dp))

        SaleSection(uiState)

        Divider(modifier = Modifier.padding(vertical = 10.dp))

        DiscountSection(viewModel)

        Divider(modifier = Modifier.padding(vertical = 10.dp))

        ActionSection(modifier = Modifier.align(Alignment.End), viewModel, navController)
    }
}

@Composable
private fun DiscountSection(viewModel: ProductsViewModel) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = viewModel.saleDiscount,
            onValueChange = viewModel::updateDiscount,
            label = {
                Text(text = stringResource(R.string.discount))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            )
        )
        Spacer(modifier = Modifier.size(10.dp))
        Button(onClick = { viewModel.applyDiscount() }) {
            Text(text = stringResource(R.string.apply))
        }
    }
}

@Composable
private fun ActionSection(
    modifier: Modifier = Modifier,
    viewModel: ProductsViewModel,
    navController: NavHostController
) {
    Row(
        modifier = modifier
    ) {
        OutlinedButton(
            onClick = {
                viewModel.resetSale()
                navController.popBackStack()
            }
        ) {
            Text(text = stringResource(R.string.cancel))
        }
        Spacer(modifier = Modifier.size(10.dp))
        Button(
            onClick = {
                viewModel.saveSale()
                navController.popBackStack()
            }
        ) {
            Text(text = stringResource(R.string.save))
        }
    }
}

@Composable
private fun SaleSection(uiState: ProductUiState) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = stringResource(R.string.sale_quant_items, uiState.totalQuant))
        Text(
            text = stringResource(
                R.string.sale_total_price,
                uiState.finalPrice.currencyFormat()
            )
        )
        Text(text = stringResource(R.string.discount_label, uiState.discount.currencyFormat()))
    }
}

@Composable
private fun ProductsSection(
    modifier: Modifier = Modifier,
    viewModel: ProductsViewModel,
    uiState: ProductUiState
) {
    Column(modifier = modifier) {
        Text(text = stringResource(R.string.product))
        OutlinedCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.productName,
                    onValueChange = viewModel::updateProductName,
                    label = {
                        Text(text = stringResource(R.string.product_name))
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.size(5.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.productQuant,
                    onValueChange = viewModel::updateQuant,
                    label = {
                        Text(text = stringResource(R.string.product_quant))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.size(5.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.productPrice,
                    onValueChange = viewModel::updatePrice,
                    label = {
                            Text(text = stringResource(R.string.product_unit_price))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.includeItem()
                        }
                    )
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = stringResource(
                        R.string.item_price,
                        viewModel.itemPrice.currencyFormat()
                    )
                )
                Button(
                    modifier = Modifier.align(Alignment.End),
                    onClick = { viewModel.includeItem() }
                ) {
                    Text(text = stringResource(R.string.include))
                }
            }
        }
        LazyColumn(
            modifier = Modifier.weight(1F),
            content = {
                items(uiState.products) { product ->
                    Card(modifier = Modifier.padding(vertical = 5.dp)) {
                        Product(product = product)
                    }
                }
            }
        )
    }
}

@Composable
private fun CostumerSection(viewModel: ProductsViewModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = viewModel.clientName,
            onValueChange = viewModel::updateClientName,
            placeholder = {
                Text(text = stringResource(R.string.write_costumer_name))
            },
            label = {
                Text(text = stringResource(id = R.string.costumer))
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )
    }
}

@Composable
fun Product(product: ViewProduct) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = product.name)
        Text(text = "${product.quant}")
        Text(text = product.unitPrice.currencyFormat())
        Text(text = (-product.discount).currencyFormat())
        Text(text = product.finalValue.currencyFormat())
    }
}