package com.example.applicationquiz

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.applicationquiz.databinding.ActivityQuizBinding
import com.shashank.sony.fancytoastlib.FancyToast

class QuizActivity : AppCompatActivity() {
    private lateinit var binding:ActivityQuizBinding
    private lateinit var list:List<QuestionModel>
    var score = 0
    private var count = 1
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = arrayListOf()

        binding.score.text = "Score : $score"
        binding.questionNumber.text = "Question : $count"
        (list as ArrayList<QuestionModel>).add(QuestionModel("Which of the following is OPEN AI latest Model","GPT-3.5","GPT-4","PaLM","LLaMA","GPT-4"))
        (list as ArrayList<QuestionModel>).add(QuestionModel("GPT-3 was trained on _____ parameters ?","175 billion","174 million","176 trillion","100 billion","175 billion"))
        (list as ArrayList<QuestionModel>).add(QuestionModel("GPT - 3.5 is ?","encoder only model","Fine tuned of GPT 3","sparse mixture of models","first gpt model","Fine tuned of GPT 3"))
        (list as ArrayList<QuestionModel>).add(QuestionModel("Which parameter characterize LLM ?","Performance of pre-training","size of ANN","cost of pre-training","All of above","All of above"))
        (list as ArrayList<QuestionModel>).add(QuestionModel("Gemini is ?","Multimodal","BiMultimodal","TriMultimodal","QuadMultimodal","Multimodal"))

        var length = list.size
        binding.totalNumberOfQuestions.text = "Total Questions : $length"

        binding.question.text = list.get(0).question
        binding.option1.text = list.get(0).option1
        binding.option2.text = list.get(0).option2
        binding.option3.text = list.get(0).option3
        binding.option4.text = list.get(0).option4

        binding.option1.setOnClickListener{
            nextQues(binding.option1.text.toString())
        }
        binding.option2.setOnClickListener{
            nextQues(binding.option2.text.toString())
        }
        binding.option3.setOnClickListener{
            nextQues(binding.option3.text.toString())
        }
        binding.option4.setOnClickListener{
            nextQues(binding.option4.text.toString())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun nextQues(ans:String) {
        val finalAns = list.get(count-1).answer
        count++;

        if(ans==finalAns){
            score++;
            FancyToast.makeText(this, "Correct Answer", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS,true).show()
        }
        else{
            // answer is wrong
            FancyToast.makeText(this, "Wrong Answer. Correct is $finalAns", FancyToast.LENGTH_SHORT, FancyToast.WARNING,true).show()
        }

        if(count>list.size){
            val intent = Intent(this,ResultPage::class.java)
            intent.putExtra("SCORE",score)
            startActivity(intent)
            finish()
        }

        binding.score.text = "Score : $score"
        binding.questionNumber.text = "Question : $count"
        binding.question.text = list.get(count-1).question
        binding.option1.text = list.get(count-1).option1
        binding.option2.text = list.get(count-1).option2
        binding.option3.text = list.get(count-1).option3
        binding.option4.text = list.get(count-1).option4

        binding.option1.setOnClickListener{
            nextQues(binding.option1.text.toString())
        }
        binding.option2.setOnClickListener{
            nextQues(binding.option2.text.toString())
        }
        binding.option3.setOnClickListener{
            nextQues(binding.option3.text.toString())
        }
        binding.option4.setOnClickListener{
            nextQues(binding.option4.text.toString())
        }
    }
}