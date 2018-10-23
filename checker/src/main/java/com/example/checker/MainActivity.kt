package com.example.checker

import android.content.Context
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recycler_view).apply {
            adapter = IconsAdapter(context)
        }
    }
}

class IconViewHolder(
        parent: ViewGroup
) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_icon, parent, false)
) {
    private val imageView: ImageView = itemView.findViewById(R.id.image_view)

    @DrawableRes
    var resourceId: Int = -1
        set(value) {
            field = value
            imageView.setImageResource(value)
        }
}

class IconsAdapter(context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<IconViewHolder>() {
    private val iconResourceIds: List<Int>

    init {
        val typedArray = context.resources.obtainTypedArray(R.array.icons)
        iconResourceIds = (0 until typedArray.length())
                .map { typedArray.getResourceId(it, -1) }
        typedArray.recycle()
    }

    override fun getItemCount() = iconResourceIds.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        return IconViewHolder(parent)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        holder.resourceId = iconResourceIds[position]
    }
}
