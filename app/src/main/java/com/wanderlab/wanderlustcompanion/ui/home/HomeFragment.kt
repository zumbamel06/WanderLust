package com.wanderlab.wanderlustcompanion.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wanderlab.wanderlustcompanion.DBHelper
import com.wanderlab.wanderlustcompanion.data.model.LoggedInUser
import com.wanderlab.wanderlustcompanion.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var email: String? = null
    private var city: String? = null
    private var address: String? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var display_name: String? = null
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        /*
        this.display_name = requireArguments().getString("login_name")
        this.firstName = requireArguments().getString("first_name")
        this.lastName = requireArguments().getString("last_name")
        this.address = requireArguments().getString("address")
        this.city = requireArguments().getString("city")
        this.email = requireArguments().getString("email")
        val firstName:TextView = binding.firstNameTextHome
        val lastName:TextView = binding.lastNameTextHome
        val address:TextView = binding.addressViewHome
        val city:TextView = binding.cityViewHome
        lastName.text = this.lastName
        firstName.text = this.firstName
        address.text = this.address
        city.text = this.city
        */
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}