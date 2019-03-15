package com.example.qthien.besttrip.data

data class Leg (
   var distance : KiLoTime? = null,
   var duration : KiLoTime? = null,
   var end_address : String? = null,
   var start_address : String? = null,
   var end_location : Location? = null,
   var start_location : Location? = null,
   var steps : ArrayList<Step>
)
