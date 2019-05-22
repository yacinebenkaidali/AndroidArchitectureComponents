package com.yacine.diceroller

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yacine.diceroller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binder: ActivityMainBinding
    private val user = User("Yacine", "BENKAIDALI")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binder.user = user
        binder.user!!.nickName = "Mate"
        binder.nameDisplay.setOnClickListener {
            user.name = binder.nameInput.text.toString()
            binder.invalidateAll()
        }

    }

}
