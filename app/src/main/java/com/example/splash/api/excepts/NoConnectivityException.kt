package com.example.splash.api.excepts

import java.io.IOException


class NoConnectivityException : IOException() {
    override val message: String?
        get() = "No internet connection"
}
