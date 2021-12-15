package com.example.colormyviews

//import android.graphics.Color
import android.graphics.Color.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    lateinit var box_three_view:View
    lateinit var box_four_view:View
    lateinit var box_five_view:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        box_three_view = findViewById(R.id.box_three_text)
        box_four_view = findViewById(R.id.box_four_text)
        box_five_view = findViewById(R.id.box_five_text)

        setListeners()
    }

    fun setListeners() {
        val clickableViews: List<View> = listOf(
            findViewById(R.id.box_one_text),
            findViewById(R.id.box_two_text),
            box_three_view,
            box_four_view,
            box_five_view,
            findViewById(R.id.constraint_layout),
            findViewById(R.id.red_button),
            findViewById(R.id.yellow_button),
            findViewById(R.id.green_button))
        for(item in clickableViews) {
            item.setOnClickListener { makeColored(it) }
        }
    }

    private fun makeColored(view: View) {
        when (view.id) {
            // Boxes using Color class colors for background
            R.id.box_one_text -> view.setBackgroundColor(DKGRAY)
            R.id.box_two_text -> view.setBackgroundColor(GRAY)

            // Boxes using Android color resources for background
            R.id.box_three_text -> view.setBackgroundResource(android.R.color.holo_green_light)
            R.id.box_four_text -> view.setBackgroundResource(android.R.color.holo_green_dark)
            R.id.box_five_text -> view.setBackgroundResource(android.R.color.holo_green_light)

            // Boxes using custom color for the background
            R.id.red_button -> box_three_view.setBackgroundResource(R.color.my_red)
            R.id.yellow_button -> box_four_view.setBackgroundResource(R.color.my_yellow)
            R.id.green_button -> box_five_view.setBackgroundResource(R.color.my_green)

            else -> view.setBackgroundColor(LTGRAY)
        }
    }
}