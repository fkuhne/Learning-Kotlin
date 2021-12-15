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

package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

class GameViewModel : ViewModel() {

    companion object {
        private const val DONE = 0L
        private const val ONE_SEC = 1000L
        private const val COUNTDOWN_TIMER = 10000L
    }

    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    private val timer : CountDownTimer

    // The current word - internal, private param
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    // The current score - internal, private param
    private val _score = MutableLiveData<Int>()
    // Public param that will be exposed to the Fragment
    val score : LiveData<Int>
        get() = _score

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    data class Buzzer(var active: Boolean, var buzzPattern: LongArray)
    private var _buzz = MutableLiveData<Buzzer>()
    val buzz: LiveData<Buzzer>
        get() = _buzz

    val currentTimeString = Transformations.map(currentTime) {
        DateUtils.formatElapsedTime(it)
    }

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Log.i("GameViewModel", "GameViewModel created!")
        resetList()
        nextWord()
        _score.value = 0
        _buzz.value = Buzzer(false, NO_BUZZ_PATTERN)
        _eventGameFinish.value = false

        // Creates a timer which triggers the end of the game when it finishes
        timer = object : CountDownTimer(COUNTDOWN_TIMER, ONE_SEC) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished/ ONE_SEC
            }
            override fun onFinish() {
                _currentTime.value = DONE
                _eventGameFinish.value = true
            }
        }.start()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed.")
        timer.cancel()
    }

    /**
     * Resets the list of words and randomizes the order
     */
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

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

    fun onSkip() {
        _score.value = (_score.value)?.minus(1)
        _buzz.value = Buzzer(true, PANIC_BUZZ_PATTERN)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (_score.value)?.plus(1)
        _buzz.value = Buzzer(true, CORRECT_BUZZ_PATTERN)
        nextWord()
    }

    fun onGameFinished() {
        _buzz.value = Buzzer(true, GAME_OVER_BUZZ_PATTERN)
        _eventGameFinish.value = false
    }

    fun resetBuzzer() {
        _buzz.value = Buzzer(false, NO_BUZZ_PATTERN)
    }
}
