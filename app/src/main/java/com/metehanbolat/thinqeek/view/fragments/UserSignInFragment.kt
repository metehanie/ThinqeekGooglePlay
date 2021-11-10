package com.metehanbolat.thinqeek.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.metehanbolat.thinqeek.R
import com.metehanbolat.thinqeek.databinding.FragmentUserSignInBinding
import com.metehanbolat.thinqeek.viewmodel.UserSignInFragmentViewModel

class UserSignInFragment : Fragment() {

    private var _binding: FragmentUserSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserSignInFragmentViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserSignInBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = UserSignInFragmentViewModel()
        auth = Firebase.auth

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            if (isLoading){
                invisible()
            }else{
                visible()
            }
        }

        binding.signInButton.setOnClickListener {
            viewModel.isLoading.value = true
            viewModel.signInUser(binding.emailText.text.toString(), binding.passwordText.text.toString(), auth, requireContext(), requireActivity())
        }

        binding.goSignUp.setOnClickListener {
            navController = findNavController()
            navController.navigate(R.id.action_userSignInFragment_to_userSignUpFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun visible(){
        binding.emailText.visibility = View.VISIBLE
        binding.passwordText.visibility = View.VISIBLE
    }

    fun invisible(){
        binding.emailText.visibility = View.INVISIBLE
        binding.passwordText.visibility = View.INVISIBLE
    }

}