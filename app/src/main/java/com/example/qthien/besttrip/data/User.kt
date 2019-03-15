package com.example.qthien.besttrip.data

class User() {
    var id : String? = null
    var username : String? = null
    var email : String? = null
    var password : String? = null

    constructor(username : String , email : String , password : String , id : String = "") : this(){
        this.username = username
        this.email = email
        this.password = password
        this.id = id
    }
}