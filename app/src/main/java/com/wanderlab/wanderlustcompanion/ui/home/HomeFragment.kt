package com.wanderlab.wanderlustcompanion.ui.home


import android.R.attr.name
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wanderlab.wanderlustcompanion.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var email: String? = null
    private var city: String? = null
    private var address: String? = null
    private var last_name: String? = null
    private var first_name: String? = null
    private var displayname: String? = null
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private fun readBundle(bundle: Bundle?) {
        if (bundle != null) {
            this.displayname = bundle.getString("login_name")
            this.first_name = bundle.getString("first_name")
            this.last_name = bundle.getString("last_name")
            this.address = bundle.getString("address")
            this.city = bundle.getString("city")
            this.email = bundle.getString("email")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        readBundle(arguments);

        val firstName:TextView = binding.firstNameTextHome
        val lastName:TextView = binding.lastNameTextHome
        val address:TextView = binding.addressViewHome
        val city:TextView = binding.cityViewHome
        lastName.text = this.last_name
        firstName.text = this.first_name
        address.text = this.address
        city.text = this.city

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        fun newInstance(displayname: String, firstname: String, lastname: String, address: String, city:String, email:String): HomeFragment {
            val bundle = Bundle()
            bundle.putString("login_name", displayname)
            bundle.putString("first_name", firstname)
            bundle.putString("last_name", lastname)
            bundle.putString("address", address)
            bundle.putString("city", city)
            bundle.putString("email", email)

            val fragment = HomeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}