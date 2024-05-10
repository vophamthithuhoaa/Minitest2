package com.example.firebaseproject

import com.google.firebase.firestore.Exclude

data class Product(
    // on below line creating variables.
    @Exclude
    var pdID: String? = "",
    var pdName: String? = "",
    var pdType: String? = "",
    var pdPrice: String? = "",
    var pdImage: String? = ""
)
