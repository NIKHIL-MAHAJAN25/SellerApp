package com.nikhil.sellerapp.dataclasses

import com.nikhil.sellerapp.skills.Skill
import com.nikhil.sellerapp.skills.SkillsCat

data class Freelancer(
        val uid: String="",
        val name:String="", //done
        val primaryskill:String?=null,//below name




        val qualification:String?=null,//basic
        val certification:List<Certification> = emptyList(),//basic
        val projectRate:Double?=null,//basic
        val reviews:List<Review> = emptyList(),//basic
        val rating:Double?=0.0,//basic


        val skills:List<Skill> = emptyList(),//section done

        val experience:List<Experience> = emptyList(),//section done
        val profcomp:Boolean?=false,

        )
