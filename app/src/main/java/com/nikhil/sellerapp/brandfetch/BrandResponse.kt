package com.nikhil.sellerapp.brandfetch

data class BrandResponse(
    val logos: List<Logo>?
)

data class Logo(
    val formats: List<LogoFormat>?
)

data class LogoFormat(
    val src: String?
)
data class Brandname(
    val name: String?,
    val domain: String?
){
    override fun toString(): String {
        return name?:""
    }
}

