import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class CardsAPI {
    fun getById(id: String) {}
    fun create(name: String, idList: String) {}
    fun create(name: String, idList: String, params: Class<S>) {
//        desc: String, pos: String, due: String, start: String, dueComplete: Boolean, idMembers: Array<String>,  idLabels: Array<String>,
//        urlSource: String, mimeType: String, idCardSource: String, keepFromSource: String, address: String, locationName: String,
//        coordinates: String
    }
    fun update (id: String, name: String, desc: String, closed: Boolean, IdMembers: Array<String>, IdAttachmentCover: String, idList: String,
                idLabels: Array<String>, pos: String, due: String, start: String, dueComplete: Boolean, subscribed: Boolean,
                address: String, locationName: String, coordinates: String) {

    }
    fun updateCover (id: String, color: String, brightness: String, url: String, idAttachment: String, size: String ) {}

}