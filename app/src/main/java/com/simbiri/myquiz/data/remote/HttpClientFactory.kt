package com.simbiri.myquiz.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {

    fun create(): HttpClient{
        return HttpClient(OkHttp){ // OKHttp is specifically for android REST calls
            // we install the ContentNegotiation plugin the same way we did in the ktor
            // backend in configSerialization
            install(ContentNegotiation){
                json(json = Json{
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
            // we also install the HttpTimeout plugin to handle network timeouts
            install(HttpTimeout){
                socketTimeoutMillis = 30_000L
                requestTimeoutMillis= 30_000L
            }
            // we also configure Logging the same way we did with configLogging in backend
            // using CallLogging
            install(Logging){
                level= LogLevel.ALL
                logger= Logger.ANDROID
            }
            // we specify that our client will receive JSON requests as default, not xml or other types
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}