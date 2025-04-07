import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class OrganizationsAPI {
    fun getActions(id: String) {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "localhost:8080"
                appendPathSegments("organizations", id, "actions")
            }
        }
        return response.body()
    }
    fun create(displayName: String, desc: String, website: String) {
        val response: HttpResponse = client.post {
            url {
                protocol = URLProtocol.HTTPS
                host = "localhost:8080"
                appendPathSegments("organizations")
                parameters.append("displayName", displayName)
                parameters.append("desc", desc)
                parameters.append("website", website)
            }
        }
        return response.body()
    }
    fun update(id: String, name: String, displayName: String, desc: String, website: String) {
        val response: HttpResponse = client.put {
            url {
                protocol = URLProtocol.HTTPS
                host = "localhost:8080"
                appendPathSegments("organizations", id)
                parameters.append("name", name)
                parameters.append("displayName", displayName)
                parameters.append("desc", desc)
                parameters.append("website", website)
            }
        }
    }
    fun delete(id: String) {
        val response: HttpResponse = client.delete {
            url {
                protocol = URLProtocol.HTTPS
                host = "localhost:8080"
                appendPathSegments("organizations", id)
            }
        }
    }
}