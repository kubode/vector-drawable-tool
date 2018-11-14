package com.example.checker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<RecyclerView>(R.id.recycler_view).apply {
            adapter = IconsAdapter(
                context,
                onItemClick = { drawableResId ->
                    Toast.makeText(
                        this@MainActivity,
                        resources.getResourceName(drawableResId),
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
        }
    }
}

class IconViewHolder(
    parent: ViewGroup,
    onItemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_icon, parent, false)
) {
    private val imageView: ImageView = itemView.findViewById(R.id.image_view)

    init {
        itemView.setOnClickListener {
            onItemClick(resourceId)
        }
    }

    @DrawableRes
    var resourceId: Int = -1
        set(value) {
            field = value
            imageView.setImageResource(value)
        }
}

class IconsAdapter(
    context: Context,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<IconViewHolder>() {

    private val iconResourceIds: List<Int>

    init {
        val typedArray = context.resources.obtainTypedArray(R.array.icons)
        iconResourceIds = (0 until typedArray.length())
            .map { typedArray.getResourceId(it, -1) }
        typedArray.recycle()
    }

    override fun getItemCount() = iconResourceIds.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        IconViewHolder(parent, onItemClick)

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        holder.resourceId = iconResourceIds[position]
    }
}
