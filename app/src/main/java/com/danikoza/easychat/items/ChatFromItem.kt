package com.danikoza.easychat.items

import android.view.View
import com.danikoza.easychat.R
import com.danikoza.easychat.databinding.ChatFromRowBinding
import com.danikoza.easychat.models.User
import com.xwray.groupie.viewbinding.BindableItem

class ChatFromItem (val user: User, private val msg : String) : BindableItem<ChatFromRowBinding>() {
    override fun bind(viewBinding: ChatFromRowBinding, position: Int) {
    }

    override fun getLayout() : Int = R.layout.chat_from_row

    override fun initializeViewBinding(view: View) : ChatFromRowBinding = ChatFromRowBinding.bind(view)
}