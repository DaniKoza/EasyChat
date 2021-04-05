package com.danikoza.easychat.items

import android.view.View
import com.danikoza.easychat.R
import com.danikoza.easychat.databinding.UserRowNewMessageBinding
import com.danikoza.easychat.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem

class UserItem(private val user: User) : BindableItem<UserRowNewMessageBinding>() {
    override fun bind(viewBinding: UserRowNewMessageBinding, position: Int) {
        viewBinding.textViewUserName.text = user.userName
        Picasso.get().load(user.profileImageUrl).into(viewBinding.imageViewAvatar)
    }

    override fun getLayout(): Int = R.layout.user_row_new_message

    override fun initializeViewBinding(view: View): UserRowNewMessageBinding = UserRowNewMessageBinding.bind(view)

}