package com.yacine.diceroller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        roll_button.setOnClickListener {
            rollDice()
        }
    }

    private fun rollDice() {
        val rand = Random().nextInt(6) + 1
        val diceRollImage = when (rand) {
            1 -> {
                R.drawable.dice_1
            }
            2 -> {
                R.drawable.dice_2
            }
            3 -> {
                R.drawable.dice_3
            }
            4 -> {
                R.drawable.dice_4
            }
            5 -> {
                R.drawable.dice_5
            }
            else -> {
                R.drawable.dice_6
            }
        }
        dice_num.setImageResource(diceRollImage)
    }

}
