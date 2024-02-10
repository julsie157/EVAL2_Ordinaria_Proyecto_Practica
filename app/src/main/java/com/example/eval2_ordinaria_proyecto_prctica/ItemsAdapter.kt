import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eval2_ordinaria_proyecto_prctica.InfoActivity
import com.example.eval2_ordinaria_proyecto_prctica.Item
import com.example.eval2_ordinaria_proyecto_prctica.R

class ItemsAdapter(private val items: List<Item>, private val context: Context) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nombreTextView: TextView = view.findViewById(R.id.textViewNombre)
        private val sinopsisTextView: TextView = view.findViewById(R.id.textViewSinopsis)
        private val imageViewImagen: ImageView = view.findViewById(R.id.imageViewImagen)

        fun bind(item: Item) {
            nombreTextView.text = item.nombre
            sinopsisTextView.text = itemView.context.getString(R.string.tap_for_more_info)
            Glide.with(itemView.context)
                .load(item.imagen)
                .into(imageViewImagen)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, InfoActivity::class.java).apply {
                    putExtra("nombre", item.nombre)
                    putExtra("sinopsis", item.sinopsis)
                    putExtra("imagen", item.imagen)
                    putExtra("trailer", item.trailer)
                }
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
