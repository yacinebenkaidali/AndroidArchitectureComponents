package com.yacine.diceroller.screens.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class GameViewModel : ViewModel() {

    // The current word
    private var _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    // The current score
    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score
    private var _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>


    init {
        _score.value = 0
        resetList()
        nextWord()
        Timber.i("Created")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Cleared")
    }

    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            _eventGameFinish.value = true
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

}