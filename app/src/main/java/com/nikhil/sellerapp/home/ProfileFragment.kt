package com.nikhil.sellerapp.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.nikhil.sellerapp.MainActivity
import com.nikhil.sellerapp.R
import com.nikhil.sellerapp.databinding.FragmentProfileBinding
import com.nikhil.sellerapp.dataclasses.Freelancer
import com.nikhil.sellerapp.dataclasses.User
import com.nikhil.sellerapp.profilepage.BasicFragment
import com.nikhil.sellerapp.profilepage.ExperienceFragment
import com.nikhil.sellerapp.profilepage.SkillsFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private var _binding:FragmentProfileBinding?=null
    private var param1: String? = null
    private var param2: String? = null
    private var profileListener:ListenerRegistration?=null // for holding listener
    private var plink=null
    val auth:FirebaseAuth=FirebaseAuth.getInstance()
    private val uid=auth.currentUser?.uid
    val db=Firebase.firestore
    private val binding get() = _binding!!//to prevent memory leaks
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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadinfo()

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()

        }

        binding.imgbt.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.prof_to_edit)
        }
        binding.chipskills.setOnCheckedStateChangeListener{group,checkedIds->
            val checkedId=checkedIds.firstOrNull()?:return@setOnCheckedStateChangeListener
            when (checkedId) {
                R.id.basichip -> replaceFragment(BasicFragment())
                R.id.skills -> replaceFragment(SkillsFragment())
                R.id.exp -> replaceFragment(ExperienceFragment())
            }
        }
        if (savedInstanceState == null) {
            binding.basichip.isChecked = true
        }
        }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun loadinfo(){
        if(uid!=null){
            db.collection("Users").document(uid)
                .addSnapshotListener { snapshot,error->
                    //Now what happens is we are getting npe exception ie firestore add on listener run asyncronoulsy and giving result even when fragment is destroyed so we make a local
                    //copy of binding
                    val b=_binding?:return@addSnapshotListener
                    if (error != null) {
                        // Handle error, maybe log it
                        return@addSnapshotListener
                    }
                    if(snapshot != null && snapshot.exists()){
                      val user=snapshot.toObject<User>()
                        b.tvname.text=user?.fullName
                        Glide.with(this@ProfileFragment)
                            .load(user?.profilePictureUrl)
                            .error(R.drawable.ic_launcher_background)
                            .into(binding.profileImage)



                    }
                }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        // 1. Get the specialized manager for nested fragments.
        val fragmentManager = childFragmentManager
        // 2. Start a transaction.
        val transaction = fragmentManager.beginTransaction()
        // 3. Replace the content of the container with the new fragment.
        transaction.replace(R.id.framelayout, fragment)
        // 4. Commit the transaction to make it happen.
        transaction.commit()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}