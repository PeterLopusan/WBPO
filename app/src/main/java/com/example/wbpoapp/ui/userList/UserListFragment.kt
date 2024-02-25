package com.example.wbpoapp.ui.userList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.wbpoapp.R
import com.example.wbpoapp.databinding.FragmentUserListBinding

class UserListFragment : Fragment() {
    private lateinit var binding: FragmentUserListBinding
    private val viewModel: UserListViewModel by activityViewModels()
    private var pageCounter = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgLoading.visibility = VISIBLE
        viewModel.getUserList(pageCounter)

        val adapter = UserListAdapter()
        binding.recyclerUsers.apply {
            this.adapter = adapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && !viewModel.listEnd) {
                        pageCounter++
                        binding.imgLoading.visibility = VISIBLE
                        viewModel.getUserList(pageCounter)
                    }
                }
            })
        }

        viewModel.userList.observe(viewLifecycleOwner) { userList ->
            binding.imgLoading.visibility = GONE
            if (userList != null) {
                if (pageCounter == 1) {
                    adapter.submitList(userList)
                } else {
                    adapter.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(requireContext(), requireContext().getString(R.string.user_list_fragment_email_unsuccessfully_loading), Toast.LENGTH_SHORT).show()
            }
        }
    }
}