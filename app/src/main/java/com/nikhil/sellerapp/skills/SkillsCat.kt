package com.nikhil.sellerapp.skills

data class SkillsCat(
    val categoryName: String,
    val skills: List<String>
){
    constructor() : this("", emptyList())
}
