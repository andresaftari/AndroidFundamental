package com.andresaftari.recyclerview.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
* Class ini disebut dengan Model atau data class, fungsi class ini adalah menampung data yang akan
* digunakan pada View (sama seperti POJO di Java, bedanya data class ini tidak perlu getter/setter)
 */
@Parcelize
data class Movie(
    val title: String?,
    val genre: String?,
    val year: String?,
    val images: Int
): Parcelable