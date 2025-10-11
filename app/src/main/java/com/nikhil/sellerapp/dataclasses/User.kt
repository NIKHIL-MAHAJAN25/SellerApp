package com.nikhil.sellerapp.dataclasses

import java.util.Date


data class User (
    var uid: String="",//serialization error
    val email: String="",
    val occupation:String?=null,
    val createdon: Date? = null,
    val fullName: String?=null,
    val phoneNumber: String?=null,
    val profilePictureUrl: String?=null,
    val state: String?=null,
    val bio: String?=null,
    val language:List<String> = listOf(),
    val userole:UserRole?=null,
    val approved:Boolean?=false,
    val security:Int?=null,
    val profilecomplete:Boolean?=false
    )
enum class UserRole{
    CLIENT,
    FREELANCER,
    ADMIN
}