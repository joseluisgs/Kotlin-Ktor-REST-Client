package API

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.*
import dto.LoginDTO
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.utils.EmptyContent.headers
import models.User
import mu.KotlinLogging
import io.ktor.http.*

object ReqresRESTClient {
    private val logger = KotlinLogging.logger {}
    private const val API_URL = "https://reqres.in/api"

    private val client = HttpClient(CIO) {
        //install(Logging)
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    private var token: String =""
    private val clientAuth = HttpClient(CIO) {
        //install(Logging)
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        install(Auth) {
            bearer {
                token = "Bearer $token"
            }
        }
    }

    suspend fun getAll(): HttpResponse {
        logger.info { "Obtener todos los usuarios" }
        return client.get("$API_URL/users")
    }

    suspend fun getById(id: Int): HttpResponse {
        logger.info { "Get usuario por ID" }
        return client.get("$API_URL/users/$id")
    }

    suspend fun post(user: User): HttpResponse {
        logger.info { "Crear usuario" }
        return client.post("$API_URL/users") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
    }

    // Tambien le podria pasar el id del usuario que quiero actualizar
    suspend fun put(user: User): HttpResponse {
        logger.info { "Actualizar usuario" }
        return client.put("$API_URL/users/${user.id}") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
    }

    suspend fun delete(id: Int): HttpResponse {
        logger.info { "Eliminar usuario" }
        return client.delete("$API_URL/users/$id")
    }

    suspend fun login(login: LoginDTO): HttpResponse {
        logger.info { "Login" }
        return client.post("$API_URL/login") {
            contentType(ContentType.Application.Json)
            setBody(login)
        }
    }
}