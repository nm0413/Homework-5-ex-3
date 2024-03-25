package com.msu.morrison.geoquiz4

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
private const val IS_CHEATER_KEY_PREFIX = "IS_CHEATER_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )


    fun isCheaterForQuestion(index: Int): Boolean {
        return savedStateHandle.get<Boolean>(getCheaterKey(index)) ?: false

    }

    fun markAsCheaterForQuestion(index: Int, isCheater: Boolean) {
        savedStateHandle.set(getCheaterKey(index), isCheater)
    }

    val currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResID

    fun moveToNext() {
        savedStateHandle.set(CURRENT_INDEX_KEY, (currentIndex + 1) % questionBank.size)
    }

    private fun getCheaterKey(index: Int): String {
        return "$IS_CHEATER_KEY_PREFIX$index"
    }

}