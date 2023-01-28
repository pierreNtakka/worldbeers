package com.ditta.worldbeers.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Beer(
    val id: Int,
    val name: String,
    val tagLine: String,
    @SerializedName("first_brewed") val firstBrewed: String,
    val description: String,
    @SerializedName("image_url") val imageUrl: String,
    val abv: Double,
    val ibu: Double?,
    @SerializedName("target_fg") val targetFg: Int,
    @SerializedName("target_og") val targetOg: Double,
    val ebc: Double,
    val srm: Double,
    val ph: Double,
    @SerializedName("attenuation_level") val attenuationLevel: Double,
    val volume: Amount,
    @SerializedName("boil_volume") val boilVolume: Amount,
    val method: Method,
    val ingredients: Ingredients,
    @SerializedName("food_pairing") val foodPairing: List<String>,
    @SerializedName("brewers_tips") val brewersTips: String,
)


data class Ingredients(
    val malt: List<Specification>,
    val hops: List<Specification>,
    val yeast: String
)

data class Specification(
    val name: String,
    val amount: Amount,
    val add: String? = null,
    val attribute: String? = null
)

data class Method(
    @SerializedName("mash_temp") val mashTemp: List<MethodInfo>,
    val fermentation: MethodInfo,
    val twist: String? = null
)

data class MethodInfo(val temp: Amount, val duration: Int)

data class Amount(val value: Double, val unit: Unit)

enum class Unit {

    @SerializedName("kilograms")
    Kg,

    @SerializedName("celsius")
    C,

    @SerializedName("grams")
    gr,

    @SerializedName("litres")
    l;
}