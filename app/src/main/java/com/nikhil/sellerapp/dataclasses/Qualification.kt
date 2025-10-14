package com.nikhil.sellerapp.dataclasses

import com.google.firebase.Timestamp
import java.util.Date

data class Qualification (
    val instName:String?="",
    val rollNo:String?=null,
    val endYear:String?=null,
    val degree:String?=null,
    val aggregate:Any?=0.0,
    val max:Any?=100,


)