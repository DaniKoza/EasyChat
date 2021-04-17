package com.danikoza.easychat.items

import android.view.View
import com.danikoza.easychat.R
import com.danikoza.easychat.databinding.ChatToRowBinding
import com.danikoza.easychat.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem

class ChatToItem(val user: User, private val msg: String) :
    BindableItem<ChatToRowBinding>() {
    override fun bind(viewBinding: ChatToRowBinding, position: Int) {

    }

    override fun getLayout(): Int = R.layout.chat_to_row

    override fun initializeViewBinding(view: View): ChatToRowBinding = ChatToRowBinding.bind(view)
}