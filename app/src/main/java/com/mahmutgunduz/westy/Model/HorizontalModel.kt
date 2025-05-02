package com.mahmutgunduz.westy.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HorizontalModel(
    val id: Int,
    val img: Int,
    val txt: String,
    val price: Double,
    val oldPrice: Double,
    val discountInfo: String
) : Parcelable