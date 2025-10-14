package com.nikhil.sellerapp.dataclasses

import com.google.firebase.Timestamp
import java.util.Date

data class Certification(
    val skillname:String="",
    val certNo:String="",
    val issuingcompany:String="",
    val issuedate: String?=null,
    val description:String?=null

    )
//{
//    constructor() : this("", "", Timestamp(Date()), null)
//
//}
