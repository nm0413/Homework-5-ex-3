package com.msu.morrison.geoquiz4

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.msu.morrison.geoquiz4.databinding.ActivityMainBinding



private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result ->
        //Handle the result
        if(result.resultCode == Activity.RESULT_OK) {
            val isCheater = result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
            quizViewModel.markAsCheaterForQuestion(quizViewModel.currentIndex, isCheater)

        }
    }

    private var answerChosen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.trueButton.setOnClickListener{
            checkAnswer(true)
        }


        binding.falseButton.setOnClickListener{
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }

        binding.cheatButton.setOnClickListener{
            // Start cheat activity
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
        }

        updateQuestion()
    }


    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextview.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer

        if (quizViewModel.isCheaterForQuestion(quizViewModel.currentIndex)) {
            Toast.makeText(this, R.string.judgment_toast, Toast.LENGTH_SHORT).show()
        } else {
            val messageResId = if (userAnswer == correctAnswer) R.string.correct_toast else R.string.incorrect_toast
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
            quizViewModel.markAsCheaterForQuestion(quizViewModel.currentIndex, false)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

}