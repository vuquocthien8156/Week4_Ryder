package com.example.qthien.besttrip.model

import android.content.ContentValues.TAG
import android.util.Log
import com.example.qthien.besttrip.data.Taxi
import com.example.qthien.besttrip.presenter.IPreFragResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MoFragResult(var iPreFragResult: IPreFragResult) {

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Taxi")

    fun getAllData(){
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val arr = mutableListOf<Taxi>()
                for(node in dataSnapshot.children ){
                    val taxi = node.getValue(Taxi::class.java)
                    if (taxi != null) {
                        arr.add(taxi)
                    }
                }
                Log.w("Sizeee", arr.size.toString())
                Log.w("Sizeee", arr.toString())
                iPreFragResult.success(arr)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                iPreFragResult.failure("Failed to read value")
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}