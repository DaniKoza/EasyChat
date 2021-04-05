package com.danikoza.easychat


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.danikoza.easychat.databinding.ActivityNewMessageBinding
import com.danikoza.easychat.items.UserItem
import com.danikoza.easychat.models.User
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupieAdapter


class NewMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewMessageBinding
    private lateinit var mDatabase: DatabaseReference
    private lateinit var groupieAdapter:  GroupieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Select User"

        groupieAdapter = GroupieAdapter()
        populateAdapter()

        binding.recyclerviewNewmessage.adapter = groupieAdapter
    }

    private fun populateAdapter() {
        mDatabase = Firebase.database.reference
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
}