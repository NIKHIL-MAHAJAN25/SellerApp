package com.nikhil.sellerapp.profilepage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.nikhil.sellerapp.R
import com.nikhil.sellerapp.certificate.CertAdapter
import com.nikhil.sellerapp.databinding.FragmentBasicBinding
import com.nikhil.sellerapp.dataclasses.Certification
import com.nikhil.sellerapp.dataclasses.Freelancer
import com.nikhil.sellerapp.dataclasses.Qualification
import com.nikhil.sellerapp.qualification.QualAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BasicFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BasicFragment : Fragment() {
    private var _binding:FragmentBasicBinding?=null
    private var param1: String? = null
    private var param2: String? = null
    private val binding get()=_binding!!
    private val db=Firebase.firestore
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val uid=auth.currentUser?.uid
    var userlist= arrayListOf<Certification>()
    val qlist= arrayListOf<Qualification>()
    lateinit var qadapter:QualAdapter
    lateinit var adapterr: CertAdapter

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
       _binding=FragmentBasicBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterr= CertAdapter(userlist)
        binding.recylercert.layoutManager=LinearLayoutManager(requireContext())
        binding.recylercert.adapter=adapterr
        userlist.clear()





        qadapter= QualAdapter(qlist)
        binding.recylerqual.layoutManager=LinearLayoutManager(requireContext())
        binding.recylerqual.adapter=qadapter
        qlist.clear()
        startlisten()
        startlistencert()
        binding.btnCertEdit.setOnClickListener {
            findNavController().navigate(R.id.prof_to_cert)
        }
        binding.btnQualEdit.setOnClickListener {
            findNavController().navigate(R.id.prof_to_qual)
        }
    }
    private fun startlistencert(){
        if(uid!=null){
            db.collection("Freelancers").document(uid).addSnapshotListener{snapshot,error->
                if(error!=null){
                    Log.e("Firestore error","Listen failed",error)
                    return@addSnapshotListener
                }
                if(snapshot!=null && snapshot.exists()){
                    val freelancer=snapshot.toObject(Freelancer::class.java)
                    val newcert=freelancer?.certification?: emptyList()
                    adapterr.updateData(newcert)
                }else{
                    Log.d("Firestore Info", "Current data: null")

                }
            }
        }
    }
private fun startlisten(){
    if(uid!=null){
        db.collection("Freelancers").document(uid).addSnapshotListener{snapshot,error->
            if(error!=null){
                Log.e("Firestore error","Listen failed",error)
                return@addSnapshotListener
            }
            if(snapshot!=null && snapshot.exists()){
                val freelancer=snapshot.toObject(Freelancer::class.java)
                val newqual=freelancer?.qualification?: emptyList()
                qadapter.updateData(newqual)
            }else{
                Log.d("Firestore Info", "Current data: null")

            }
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
         * @return A new instance of fragment BasicFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BasicFragment().apply {
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