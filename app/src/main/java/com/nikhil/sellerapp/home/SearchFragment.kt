package com.nikhil.sellerapp.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.nikhil.sellerapp.R
import com.nikhil.sellerapp.databinding.FragmentSearchBinding
import com.nikhil.sellerapp.homeSkill.DataSkill
import com.nikhil.sellerapp.homeSkill.ServiceAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    private var _binding:FragmentSearchBinding?=null
    private val binding get()=_binding!!
    lateinit var serviceAdapter: ServiceAdapter
    private val db= Firebase.firestore
    val docId="loPFPxaKFVI4P4ZWuzYB"
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding=FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        loadinfo()

    }
private fun setup(){
    serviceAdapter=ServiceAdapter()
    binding.recyclerservices.apply {
        adapter=serviceAdapter
    }
}
    private fun loadinfo(){
        db.collection("Skills").addSnapshotListener{ snapshot,error ->
            if(error!=null){
                return@addSnapshotListener
            }
            if(snapshot!=null && !snapshot.isEmpty){
                val skill=snapshot.toObjects(DataSkill::class.java)
                serviceAdapter.submitList(skill)
            }else{
                Log.d("Firestore Info", "Current skills data: null or empty")
                // If the collection is empty, submit an empty list to clear the UI.
                serviceAdapter.submitList(emptyList())
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}