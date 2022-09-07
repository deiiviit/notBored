package com.example.notbored.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notbored.R

class CategoriesAdapter (private val categoriesList : List<String>, context: Context) : BaseAdapter() {

    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = categoriesList.size


    override fun getItem(position: Int): Any = categoriesList[position]


    override fun getItemId(position: Int): Long = position.toLong()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.categories_list_item, parent, false)

        val tvCategory = view.findViewById(R.id.tvCategory) as TextView
        tvCategory.text = getItem(position) as String

        return view
    }
}