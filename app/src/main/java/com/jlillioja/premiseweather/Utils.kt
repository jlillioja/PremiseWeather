package com.jlillioja.premiseweather

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.toast(text: String, long: Boolean = true) = Toast.makeText(this, text, if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
fun log(text: String) = Log.d("MainActivity", text)
