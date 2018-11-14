package com.example.checker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<RecyclerView>(R.id.recycler_view).apply {
            adapter = IconsAdapter(context)
        }
    }
}

class IconViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_icon, parent, false)
) {
    private val iconImageView: ImageView = itemView.findViewById(R.id.icon_image_view)
    private val nameTextView: TextView = itemView.findViewById(R.id.name_text_view)

    var iconResource: IconResource? = null
        set(value) {
            field = value
            iconImageView.setImageResource(value?.id ?: -1)
            nameTextView.text = value?.name
        }
}

data class IconResource(
    @DrawableRes val id: Int,
    val name: String
)

class IconsAdapter(context: Context) : RecyclerView.Adapter<IconViewHolder>() {

    private val iconResources: List<IconResource>

    init {
        val typedArray = context.resources.obtainTypedArray(R.array.icons)
        iconResources = (0 until typedArray.length())
            .map { typedArray.getResourceId(it, -1) }
            .map { IconResource(it, context.resources.getResourceEntryName(it)) }
            .sortedBy { it.name }
        typedArray.recycle()
    }

    override fun getItemCount() = iconResources.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IconViewHolder(parent)

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        holder.iconResource = iconResources[position]
    }
}
