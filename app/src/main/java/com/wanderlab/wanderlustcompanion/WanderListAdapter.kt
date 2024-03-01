package com.wanderlab.wanderlustcompanion


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wanderlab.wanderlustcompanion.data.model.MyWAnderListData


class WanderListAdapter (private var listdata: MutableList<MyWAnderListData>, private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<WanderListAdapter.ViewHolder>() {
    private val TAG = "WanderListAdapter"
    private var onClickListener: View.OnClickListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val listItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        Log.v(TAG,listItem.toString())
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titletextView
        holder.titletextView.text = listdata[position].titleLine
        holder.imageView.setImageResource(listdata[position].ImgId)
        holder.imageView.setOnClickListener {
            itemClickListener.onItemClick(position,listdata[position])
        }

    }


    // onClickListener Interface
    interface ItemClickListener {
        fun onItemClick(position: Int, data: MyWAnderListData)
        fun onLongClick(position: Int, data: MyWAnderListData)
    }

    fun refreshData(listdataset: MutableList<MyWAnderListData>) {
        this.listdata = listdataset
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listdata.size
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView
        var titletextView: TextView
        var relativeLayout: LinearLayout

        init {
            super.itemView
            imageView = itemView.findViewById<View>(R.id.recycle_item_imageview) as ImageView
            titletextView = itemView.findViewById<View>(R.id.recycle_item_titleview) as TextView
            relativeLayout = itemView.findViewById<View>(R.id.recycle_item_linear_layout) as LinearLayout

        }
    }
}

private fun View.OnClickListener.onClick(position: Int, myReciepeListData: MyWAnderListData) {

}
