package com.nikhil.sellerapp.dataclasses

import com.google.firebase.Timestamp
import java.util.Date

data class Experience(
    val designation:String="",
    val cologo:String?=null,
    val companyname:String="",
    val startDate: String?=null,
    val endDate: String?=null,
    val description:String?=null

    )
//{
//    constructor() : this("", "", Timestamp(Date()), Timestamp(Date()), null)
//
//}
