package com.danikoza.easychat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.danikoza.easychat.databinding.ActivityChatLogBinding
import com.danikoza.easychat.databinding.ActivityNewMessageBinding
import com.danikoza.easychat.models.User
import com.xwray.groupie.GroupieAdapter

class ChatLogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatLogBinding
    private lateinit var groupieAdapter: GroupieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        supportActionBar?.title = user.userName

        groupieAdapter = GroupieAdapter()
        //populateAdapter()
        binding.recyclerviewChatlog.adapter = groupieAdapter

    }

    private fun populateAdapter() {
        TODO("Not yet implemented")
    }
}