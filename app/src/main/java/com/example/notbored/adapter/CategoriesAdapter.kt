package com.example.notbored.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notbored.R
import com.example.notbored.SuggestionActivity

class CategoriesAdapter (private val categoriesList : List<String>, context: Context, private val participants : Int) : BaseAdapter() {


    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    // Gets the size of the list
    override fun getCount(): Int = categoriesList.size

    // Gets the item at the specified position in the list
    override fun getItem(position: Int): Any = categoriesList[position]

    // gets the row id associated with the specified position in the list
    override fun getItemId(position: Int): Long = position.toLong()

    // Gets a View that displays the data at the specified position in the data set
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.categories_list_item, parent, false)

        val tvCategory = view.findViewById(R.id.tvCategory) as TextView
        tvCategory.text = getItem(position) as String

        view.setOnClickListener {
            val intent = Intent(parent?.context, SuggestionActivity::class.java)
            intent.putExtra("category", getItem(position) as String)
            intent.putExtra("participants", participants )
            parent?.context?.startActivity(intent)
        }


        return view
    }

}