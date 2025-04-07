import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class ListsAPI {
    fun getById (id: String) {}
    fun getById (id: String, fields: String) {}
    fun update (id: String, name: String, closed: Boolean, idBoard: String, pos: String, subscribed: Boolean) {}
    fun create (name: String,idBoard: String){}
    fun create (name: String, idBoard: String, idListSource: String, pos: String) {}
    fun archive (id: String, value: String = "closed") {}
    fun unarchive (id: String) {}
    // fields : all or a comma-separated list of member fields Default: avatarHash,fullName,initials,username
    fun addMember (id: String, fields: String) {}
}