package com.example.qthien.besttrip.model

import android.app.Activity
import android.content.ContentValues
import android.util.Log
import com.example.qthien.besttrip.data.User
import com.example.qthien.besttrip.presenter.IPreFragLogin
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MoFragLogin(var iPreFragLogin: IPreFragLogin){

    fun login(mAuth : FirebaseAuth ,activity: Activity, email : String , password : String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity, object : OnCompleteListener<AuthResult>{
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful()) {
                        getUser(mAuth.currentUser!!.uid)
                        // Sign in success, update UI with the signed-in user's information
                    } else {
                        // If sign in fails, display a message to the user.
                        iPreFragLogin.failure(task.exception.toString())
                    }
                }
            });
    }

    fun getUser(id : String){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("User")

        val query = myRef.orderByChild("id").equalTo(id)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(napshot in dataSnapshot.children)
                {
                    val u = napshot.getValue(User::class.java)
                    iPreFragLogin.success(u)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }
}