package com.mahmutgunduz.westy.Model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BottomShetModelSubn(
    val id: Int,
    val title: String,
    val img: Int,
    val price: Double,
    val oldPrice: Double,
    val newPrice: Double,
    val discountInfo: String
) : Parcelable
