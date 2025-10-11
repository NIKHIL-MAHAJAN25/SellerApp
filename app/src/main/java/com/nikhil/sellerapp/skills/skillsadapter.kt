package com.nikhil.sellerapp.skills

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.nikhil.sellerapp.databinding.SkillCateoryItemBinding

class skillsadapter(val list:MutableList<SkillsCat>):RecyclerView.Adapter<skillsadapter.ViewHolder>() {
    inner class ViewHolder(val binding: SkillCateoryItemBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun bindata(position: Int){
            val item=list[position]

             binding.tvct.text=list[position].categoryName
            binding.skillChipGroup.removeAllViews()
            for (sname in list[position].skills){
                val chip= Chip(itemView.context)
                chip.text=sname
                binding.skillChipGroup.addView(chip)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding=SkillCateoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bindata(position)
    }
    fun updatedata(newList:List<SkillsCat>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}