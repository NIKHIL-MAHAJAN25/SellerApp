package com.nikhil.sellerapp.dataclasses

import com.google.firebase.Timestamp
import java.util.Date

data class Qualification (
    val instName:String?="",
    val rollNo:String?=null,
    val endYear:Timestamp=Timestamp(Date()),
    val degree:String?=null,
    val aggregate:Double?=0.0,
    val max:Int?=100,
    val description:String?=""

)