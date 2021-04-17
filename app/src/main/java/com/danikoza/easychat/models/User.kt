package com.danikoza.easychat.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User (val uid: String,val userName: String, val profileImageUrl: String): Parcelable {
    constructor() : this("","","")
}

