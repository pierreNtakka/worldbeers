package com.ditta.worldbeers.model

import com.google.gson.Gson
import com.google.gson.GsonBuilder


object GsonProvider {

    val gson: Gson by lazy {
        GsonBuilder()
            .create()
    }
}