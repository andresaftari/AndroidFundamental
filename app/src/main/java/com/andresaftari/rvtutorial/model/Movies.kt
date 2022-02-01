package com.andresaftari.rvtutorial.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Sesuatu yang ada di dunia nyata dan memiliki profil
@Parcelize
data class Movie(
    val title: String?,
    val genre: String?,
    val images: Int
): Parcelable