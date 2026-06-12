package com.smartconcierge.plugins

fun String.isValidEmail(): Boolean {
    return isNotEmpty() && contains("@")
}