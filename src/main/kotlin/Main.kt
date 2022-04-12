import API.ReqresAuthRESTClient
import API.ReqresRESTClient
import dto.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import models.User

fun main() = runBlocking {
    println("Hola Ktor Client")
    val client = HttpClient(CIO)
    val response: HttpResponse = client.get("https://ktor.io")
    println(response.status)
    client.close()

    // Probamos nuestro cliente con llamadas asíncronas
    // Las vamos a lanzar todas y luego recogemos sus resultados... y así experimentamos con ellas.
    // Usuario por ID
    val getByID = async { ReqresRESTClient.getById(1) }
    val getAll = async { ReqresRESTClient.getAll() }
    val getAllQuery = async { ReqresRESTClient.getAll(2, 6) }
    val post = async { ReqresRESTClient.post(User("Pepe", "Perez")) }

    println("Usuario por ID")
    val user = getByID.await().body<UserGetDTO>().data
    println(user)

    println("Todos los usuarios")
    val users = getAll.await().body<UsersListGetDTO>().data
    println(users)

    println("Usuario creado")
    val userCreated = post.await().body<UserPostDTO>()
    println(userCreated)

    user.first_name = "Juan"
    user.last_name = "Modificado"
    val put = async{ ReqresRESTClient.put(user) }

    println("Usuario modificado")
    val userModified = put.await().body<UserUpdateDTO>()
    println(userModified)

    val delete = async { ReqresRESTClient.delete(userModified.id) }
    println("Usuario borrado")
    val userDeleted = delete.await().status
    println(userDeleted)

    println("Hacemos Login")
    val login = ReqresRESTClient.login(LoginDTO("eve.holt@reqres.in", "cityslicka"))
    val token = login.body<LoginResponseDTO>().token
    println(token)

    // Hacemos una peticion con el token a un cliente con Auth
    println("Petición con token")
    val newUser = ReqresRESTClient.getByIdAuth(1, token).body<UserGetDTO>().data
    println(newUser)

    println("Get all paginados y limitados")
    getAllQuery.await().body<UsersListGetDTO>().data.forEach {
        println(it)
    }


}