package com.danikoza.easychat


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.danikoza.easychat.databinding.ActivityNewMessageBinding
import com.danikoza.easychat.items.UserItem
import com.danikoza.easychat.models.User
import com.google.firebase.database.*
import com.xwray.groupie.GroupieAdapter


class NewMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewMessageBinding
    private lateinit var groupieAdapter: GroupieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Select User"

        groupieAdapter = GroupieAdapter()
        populateAdapter()
        binding.recyclerviewNewmessage.adapter = groupieAdapter

        groupieAdapter.setOnItemClickListener { item, view ->
            val userItem = item as UserItem
            val intent = Intent(this, ChatLogActivity::class.java)
            intent.putExtra(USER_KEY, userItem.user)
            startActivity(intent)
            finish()
        }


    }

    private fun populateAdapter() {
        val ref = FirebaseDatabase.getInstance().getReference("/users")

        val userListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val user = it.getValue(User::class.java)
                    if (user != null)
                        groupieAdapter.add(UserItem(user))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        ref.addListenerForSingleValueEvent(userListener)
    }

    companion object {
        val USER_KEY = "USER_KEY"
    }
}