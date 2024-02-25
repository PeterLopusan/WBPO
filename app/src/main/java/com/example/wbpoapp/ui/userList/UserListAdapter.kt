package com.example.wbpoapp.ui.userList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wbpoapp.R
import com.example.wbpoapp.databinding.AdapterUserListBinding
import com.example.wbpoapp.models.UserModel
import com.example.wbpoapp.sharedPreferences.SharedPreferencesManager
import com.example.wbpoapp.ui.MainActivity

class UserListAdapter : ListAdapter<UserModel, UserListAdapter.UserListHolder>(DiffCallbackUserList) {

    inner class UserListHolder(private val binding: AdapterUserListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setValues(user: UserModel) {
            binding.apply {
                txtId.text = MainActivity.instance.getString(R.string.user_list_fragment_id, user.id ?: 0)
                txtFirstname.text = MainActivity.instance.getString(R.string.user_list_fragment_firstname, user.firstname ?: "")
                txtLastname.text = MainActivity.instance.getString(R.string.user_list_fragment_lastname, user.lastname ?: "")
                txtEmail.text = MainActivity.instance.getString(R.string.user_list_fragment_email, user.email ?: "")

                Glide.with(MainActivity.instance)
                    .load(user.avatar)
                    .placeholder(R.drawable.loading_animation)
                    .into(binding.imgProfilePicture)


                var isFollowed = SharedPreferencesManager().getIfIdIsFollowed(user.id ?: 0)

                btnFollow.text =
                    if (isFollowed) MainActivity.instance.getString(R.string.user_list_fragment_email_unfollow) else MainActivity.instance.getString(R.string.user_list_fragment_email_follow)
                btnFollow.setTextColor(ContextCompat.getColor(MainActivity.instance, if (isFollowed) R.color.unfollow_color else R.color.follow_color))

                btnFollow.setOnClickListener {
                    if (isFollowed) {
                        user.id?.let { id ->
                            SharedPreferencesManager().setFollowId(id, false)
                            isFollowed = !isFollowed
                            btnFollow.text = MainActivity.instance.getString(R.string.user_list_fragment_email_follow)
                            btnFollow.setTextColor(ContextCompat.getColor(MainActivity.instance, R.color.follow_color))
                        }
                    } else {
                        user.id?.let { id ->
                            SharedPreferencesManager().setFollowId(id, true)
                            isFollowed = !isFollowed
                            btnFollow.text = MainActivity.instance.getString(R.string.user_list_fragment_email_unfollow)
                            btnFollow.setTextColor(ContextCompat.getColor(MainActivity.instance, R.color.unfollow_color))

                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListAdapter.UserListHolder {
        return UserListHolder(AdapterUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserListAdapter.UserListHolder, position: Int) {
        holder.setValues(getItem(position))
    }

    companion object DiffCallbackUserList : DiffUtil.ItemCallback<UserModel>() {
        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.id == newItem.id
        }
    }
}