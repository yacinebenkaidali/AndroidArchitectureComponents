package com.yacine.diceroller.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel


private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

class GameViewModel : ViewModel() {

    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

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

    private var timer: CountDownTimer

    private var _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime
    val currentTimeString: LiveData<String> = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }
    private lateinit var wordList: MutableList<String>

    private var _buzzEvent = MutableLiveData<BuzzType>()
    val buzzEvent: LiveData<BuzzType>
        get() = _buzzEvent


    companion object {
        const val COUNTDOWN_PANIC_SECONDS = 10L
        const val DONE = 0L
        const val ONE_SECOND = 1000L
        const val COUNTDOWN_TIME = 30000L
    }

    fun onBazzComplete() {
        _buzzEvent.value = BuzzType.NO_BUZZ
    }

    init {
        _score.value = 0
        resetList()
        nextWord()
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onFinish() {
                _eventGameFinish.value = true
                _buzzEvent.value = BuzzType.GAME_OVER
                _currentTime.value = DONE
            }

            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished / ONE_SECOND <= COUNTDOWN_PANIC_SECONDS) {
                    _buzzEvent.value = BuzzType.COUNTDOWN_PANIC
                }
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }
        }
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
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
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        _buzzEvent.value = BuzzType.CORRECT
        nextWord()
    }

}