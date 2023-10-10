package dev.jmoicano.salesmanager.ui

import java.text.NumberFormat

fun Double.currencyFormat(): String = NumberFormat.getCurrencyInstance().format(this)