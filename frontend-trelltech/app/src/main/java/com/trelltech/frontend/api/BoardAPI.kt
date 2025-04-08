package com.trelltech.frontend.api

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class BoardAPI {
    private val client = HttpClient(CIO)
//    get
    suspend fun get(id: String): HttpResponse {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("boards", id)
            }
        }
        return response.body()
    }
    suspend fun getMembers(id: String) {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("boards", id, "members")
            }
        }
        return response.body()
    }
    suspend fun getMemberships(id: String) {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("boards", id, "memberships")
            }
        }
        return response.body()
    }
    suspend fun getChecklists(id: String) {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("boards", id, "checklists")
            }
        }
        return response.body()
    }
    suspend fun getCards(id: String) {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("boards", id, "cards")
            }
        }
        return response.body()
    }
    suspend fun getCards(id: String, filter: String) {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("boards", id, "cards", filter)
            }
        }
        return response.body()
    }
    suspend fun getLabels(id: String, fields: String, limit: String) {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("boards", id, "labels")
                parameters.append("fields", fields)
                parameters.append("limit", limit)
            }
        }
        return response.body()
    }
    suspend fun getLists(id:String, cards:String, card_fields:String, filter: String, fields: String) {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("boards", id, "lists")
                parameters.append("cards", cards)
                parameters.append("card_fields", card_fields)
                parameters.append("filter", filter)
                parameters.append("fields", fields)
            }
        }
        return response.body()
    }
    suspend fun getFilteredLists(id: String, filter: String) {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("boards", id, "lists")
                parameters.append("filter", filter)
            }
        }
        return response.body()
    }

//    post
    suspend fun create(name: String){
        val response: HttpResponse = client.post {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("boards")
                parameters.append("name", name)
            }
        }
        return response.body()
    }
//    suspend fun create(name: String, fields: Class<T>) {
////        defaultLabels: Boolean, defaultLists: Boolean, desc: String, idOrganization: String, idBoardSource: String,
////        keepFromSource: String, powerUps: String,
////        prefs_permissionLevel: String, prefs_voting: String, prefs_comments: String, prefs_invitations: String, prefs_selfJoin: Boolean,
////        prefs_cardCovers: Boolean, prefs_background: String, prefs_cardAging: String
//        val response: HttpResponse = client.post {
//            url {
//                protocol = URLProtocol.HTTP
//                host = " http://127.0.0.1:8080"
//                appendPathSegments("boards")
//                parameters.append("name", name)
//            }
//            setBody(fields)
//        }
//        return response.body()
//    }
//    suspend fun createLabel(id: String, name: String, color: String) {
//        val response: HttpResponse = client.post {
//            url {
//                protocol = URLProtocol.HTTP
//                host = " http://127.0.0.1:8080"
//                appendPathSegments("boards")
//                parameters.append("name", name)
//                parameters.append("color", color)
//            }
//            setBody(fields)
//        }
//        return response.body()
//    }
    suspend fun createList(id:String, name: String, pos: String) {
        val response: HttpResponse = client.post {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("boards", id, "lists")
                parameters.append("name", name)
                parameters.append("pos", pos)
            }
        }
        return response.body()
    }

//    put
//    suspend fun update(id: String, fields: Class<T> ) {
////        name: String, desc: String, closed: Boolean, subscribed: String, idOrganization: String
//        val response: HttpResponse = client.put {
//            url {
//                protocol = URLProtocol.HTTP
//                host = " http://127.0.0.1:8080"
//                appendPathSegments("boards")
//                parameters.append("name", name)
//            }
//            setBody(fields)
//        }
//        return response.body()
//    }
//    suspend fun updatePrefs(id: String, fields: Class<T>) {
//        //    permissionLevel: String, selfJoin: Boolean, cardCovers: Boolean, hideVotes: Boolean,
//        //    invitations: String, voting: String, comments: String, background: String, cardAging: String, calendarFeedEnabled: Boolean
//        val response: HttpResponse = client.put {
//            url {
//                protocol = URLProtocol.HTTP
//                host = " http://127.0.0.1:8080"
//                appendPathSegments("boards")
//                parameters.append("name", name)
//            }
//            setBody(fields)
//        }
//        return response.body()
//    }
//    suspend fun updateLabelNames(id: String, fields: Class<T>) {
////        green: String, yellow: String, orange: String, red: String, purple: String, blue: String
//        val response: HttpResponse = client.put {
//            url {
//                protocol = URLProtocol.HTTP
//                host = " http://127.0.0.1:8080"
//                appendPathSegments("boards")
//                parameters.append("name", name)
//            }
//            setBody(fields)
//        }
//        return response.body()
//    }
    suspend fun invitMemberEmail(id: String, email: String, type:String) {
        val response: HttpResponse = client.put{
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("boards", id, "members")
                parameters.append("email", email)
                parameters.append("type", type)
            }
        }
    }
    suspend fun addMember(id: String, idMember: String, type: String, allowBillableGuest: Boolean) {
        val response: HttpResponse = client.put{
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("boards", id, "members", idMember)
                parameters.append("type", type)
                parameters.append("allowBillableGuest", allowBillableGuest.toString())
            }
        }
    }
    suspend fun updateMembershipMember(id: String, idMembership: String, type: String, member_fields: String) {
        val response: HttpResponse = client.put{
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("boards", id, "memberships", idMembership)
                parameters.append("type", type)
                parameters.append("member_fields", member_fields)
            }
        }
    }

//    delete
    suspend fun delete(id: String) {
        val response: HttpResponse = client.delete {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("boards", id)
            }
        }
    }
    suspend fun removeMember(id: String, idMember: String) {
        val response: HttpResponse = client.delete {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("boards", id, "members", idMember)
            }
        }
    }


}
