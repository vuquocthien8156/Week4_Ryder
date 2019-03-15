package com.example.qthien.besttrip.data

data class Step (
    var start_location : Location? = null,
    var end_location : Location? = null,
    var polyline : Polyline? = null
)