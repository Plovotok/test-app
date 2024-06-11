package ru.plovotok.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.host
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.path
import io.ktor.util.StringValues
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException
import java.net.UnknownHostException

/**
 * Безопасно выполняет запрос на сервер
 * @param T модель, возвращаемая от сервера при успешном ответе
 * @param host адрес сервера
 * @param path endpoint запроса
 * @param method Http-метод запроса
 * @param body тело запроса (модель, передаваемая серверу)
 */
internal suspend inline fun <reified T> HttpClient.safeRequest(
    host: String,
    path: String,
    method: HttpMethod,
    params: StringValues = StringValues.Empty,
    body: Any? = null
) =
    try {
        val response = this.request {
            this.method = method
            this.host = host
            url {
                protocol = URLProtocol.HTTPS
                path(path)
                parameters.appendAll(params)
                headers {
                    append("Accept", "application/vnd.github+json")
                    if (NetworkConstants.ACCESS_TOKEN.isNotEmpty()) {
                        append("Authorization", "Bearer ${NetworkConstants.ACCESS_TOKEN}")
                    }
                }
            }
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            if (body != null) setBody(body)
        }
        ApiResponse.Success(data = response.body<T>())
    } catch (e: Exception) {
        e.printStackTrace()
        ApiResponse.Error(e)
    }