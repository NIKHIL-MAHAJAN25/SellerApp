package com.nikhil.sellerapp.dataclasses

import com.google.firebase.Timestamp
import java.util.Date

data class Review(
    val reviewerUid: String = "",       // UID of the client who left the review
    val reviewerName: String = "",      // Name of the reviewer
    val rating: Int = 0,                // The star rating (e.g., 1 to 5)
    val reviewText: String = "",        // The actual text content of the review
    val timestamp: Timestamp = Timestamp(Date()) // When the review was submitted
)
