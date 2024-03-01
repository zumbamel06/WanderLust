package com.wanderlab.wanderlustcompanion.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wanderlab.wanderlustcompanion.R
import com.wanderlab.wanderlustcompanion.WanderListAdapter
import com.wanderlab.wanderlustcompanion.data.model.MyWAnderListData
import com.wanderlab.wanderlustcompanion.databinding.FragmentRecommendationBinding

class NotificationsFragment : Fragment(), WanderListAdapter.ItemClickListener{

    private var _binding: FragmentRecommendationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onItemClick(position: Int, data:MyWAnderListData) {
        Toast.makeText(activity, "TEST: " + position, Toast.LENGTH_SHORT).show()
        /*
        val displayname = intent.getStringExtra("login_name")
        val food_genere = intent.getStringExtra("food_genre")
        val intent_pass = Intent(this, ContentDisplayActivity::class.java)
        intent_pass.putExtra("login_name", displayname)
        intent_pass.putExtra("food_genre", food_genere)
        intent_pass.putExtra("position", position)
        intent_pass.putExtra("data", data.titleLine)
        startActivity(intent_pass)*/
    }

    override fun onLongClick(position: Int, data: MyWAnderListData) {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
        _binding = FragmentRecommendationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        val titletext:TextView = binding.titleTextList
        titletext.text = "Recommendations"
        var myListData: MutableList<MyWAnderListData> = mutableListOf()
        val StringArray: Array<String> = resources.getStringArray(R.array.list_of_list)
        for (i in StringArray) {
            myListData.add(MyWAnderListData(i,R.drawable.ic_home_black_24dp))
        }
        val recyclerView = root.findViewById(R.id.list_item_view_main) as RecyclerView
        val adapter = WanderListAdapter(myListData,this)
        adapter.refreshData(myListData)
        val horizontalLayoutManagaer =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        //recyclerView.setHasFixedSize(true)
        //adapter.notifyDataSetChanged()
        recyclerView.layoutManager = horizontalLayoutManagaer
        recyclerView.adapter = adapter
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}