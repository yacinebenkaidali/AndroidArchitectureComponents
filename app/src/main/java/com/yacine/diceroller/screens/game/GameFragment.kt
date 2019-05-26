/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yacine.diceroller.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.yacine.diceroller.R
import com.yacine.diceroller.databinding.GameFragmentBinding
import timber.log.Timber

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {
    private lateinit var viewModel: GameViewModel

    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )
        viewModel.score.observe(this, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })
        viewModel.word.observe(this, Observer { newWord ->
            binding.wordText.text = newWord
        })
        viewModel.eventGameFinish.observe(this, Observer { newEventGameFinish->
            if (newEventGameFinish) {
                gameFinished()
                viewModel.onGameFinishComplete()
            }
        })


        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
        }

        return binding.root

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        Timber.i("Called ViewModelProviders.of")
    }

    /**
     * Resets the list of words and randomizes the order
     */


    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val currentScore = viewModel.score.value ?: 0
        val action = GameFragmentDirections.actionGameToScore(currentScore)
        findNavController(this).navigate(action)
    }

    /**
     * Moves to the next word in the list
     */


    /** Methods for buttons presses **/


    /** Methods for updating the UI **/


}
