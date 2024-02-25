package com.example.wbpoapp.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.wbpoapp.R
import com.example.wbpoapp.databinding.FragmentRegistrationBinding
import com.example.wbpoapp.models.RegistrationModel
import com.example.wbpoapp.sharedPreferences.SharedPreferencesManager
import com.example.wbpoapp.ui.MainActivity


class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnRegister.setOnClickListener {
                if (checkUserData()) {
                    imgLoading.visibility = VISIBLE
                    registerUser()
                } else {
                    Toast.makeText(requireContext(), requireContext().getString(R.string.registration_fragment_fill_all_info), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkUserData(): Boolean {
        binding.apply {
            return editTextEmail.text.toString().isNotBlank() && editTextPassword.text.toString().isNotBlank()
        }
    }


    private fun registerUser() {
        val body = RegistrationModel(
            email = binding.editTextEmail.text.toString(),
            password = binding.editTextPassword.text.toString()
        )

        viewModel.registerUser(body).observe(viewLifecycleOwner) { result ->
            binding.imgLoading.visibility = GONE
            if (result) {
                Toast.makeText(requireContext(), requireContext().getString(R.string.registration_fragment_register_user_success), Toast.LENGTH_SHORT).show()
                SharedPreferencesManager().setFirstRegistration(false)
                MainActivity.instance.findNavController()?.navigate(RegistrationFragmentDirections.actionRegistrationFragmentToUserListFragment())
            } else {
                Toast.makeText(requireContext(), requireContext().getString(R.string.registration_fragment_register_user_unsuccess), Toast.LENGTH_SHORT).show()
            }
        }
    }
}