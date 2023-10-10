package dev.jmoicano.salesmanager.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.jmoicano.salesmanager.ui.home.HomeScreen
import dev.jmoicano.salesmanager.ui.home.viewmodel.SalesViewModel
import dev.jmoicano.salesmanager.ui.sale.SaleScreen
import dev.jmoicano.salesmanager.ui.sale.viewmodel.ProductsViewModel

@Composable
fun SalesManagerNavigation() {
    val salesViewModel: SalesViewModel = hiltViewModel()
    val productsViewModel: ProductsViewModel = hiltViewModel()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, viewModel = salesViewModel)
        }
        composable(Screen.Product.route) {
            SaleScreen(navController = navController, viewModel = productsViewModel)
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Product : Screen("product")
}