package com.msu.morrison.geoquiz4

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.msu.morrison.geoquiz4.databinding.ActivityCheatBinding
import com.msu.morrison.geoquiz4.databinding.ActivityMainBinding

const val EXTRA_ANSWER_SHOWN = "com.msu.morrison.geoquiz4.answer_shown"

private const val EXTRA_ANSWER_IS_TRUE =
    "com.msu.morrison.geoquiz4.answer_is_true"

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding
    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)



        binding.showAnswerButton.setOnClickListener{
            showAnswer()
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(EXTRA_ANSWER_SHOWN,true)
            })
        }
    }

    private fun showAnswer() {
        val answerText = if (answerIsTrue) R.string.true_button else R.string.false_button
        binding.answerTextView.setText(answerText)
    }


    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}