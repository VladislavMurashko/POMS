package by.bsuir.murashko.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getDrawable
import by.bsuir.murashko.quizapp.model.Question
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : AppCompatActivity(), View.OnClickListener {
    private var mCorrectAnswers: Int = 0
    private var mCurrentProgressPosition: Int = 1
    private var mSelectedOptionPosition: Int = 0
    private var mIsUserSubmitted: Boolean = false
    private var questionsList = ArrayList<Question>()
    private var storage = FirebaseStorage.getInstance()
    private var database = FirebaseDatabase.getInstance()
    private var storageRef = storage.getReference("/")
    private var databaseRef = database.getReference("questions/ru")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.layout_loading_dialog)
        val dialog = builder.create()
        loadQuestions(dialog)

        initClickListeners()
    }

    private fun loadQuestions(dialog: AlertDialog) {
        dialog.show()
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children
                children.forEach {
                    it.getValue(Question::class.java)?.let { it1 -> questionsList.add(it1) }
                }
                setQuestion()
                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun initClickListeners() {
        tv_ans_1.setOnClickListener(this)
        tv_ans_2.setOnClickListener(this)
        tv_ans_3.setOnClickListener(this)
        tv_ans_4.setOnClickListener(this)
    }

    /**
     * A function for setting the question to UI components.
     */
    private fun setQuestion() {
        val currentQuestion = questionsList[mCurrentProgressPosition - 1]

        resetOptionsView()

        btn_submit_answer.text = "Подтвердить"

        progressBar.progress = mCurrentProgressPosition

        tv_progress.text = String.format("%d/%d", mCurrentProgressPosition, progressBar.max)
        tv_question.text = currentQuestion.question

        storageRef.child(currentQuestion.imageUrl).downloadUrl.addOnSuccessListener { Uri ->
            Glide.with(this)
                    .load(Uri)
                    .apply(RequestOptions()
                            .error(R.drawable.image_not_found)
                            .placeholder(R.drawable.circular_progress_style)
                            .fitCenter())
                    .into(iv_questionImage)
        }

        tv_ans_1.text = currentQuestion.optionOne
        tv_ans_2.text = currentQuestion.optionTwo
        tv_ans_3.text = currentQuestion.optionThree
        tv_ans_4.text = currentQuestion.optionFour
    }

    override fun onClick(v: View?) {
        if (mIsUserSubmitted) {
            return
        }

        when (v?.id) {
            R.id.tv_ans_1 -> {
                selectedOptionView(tv_ans_1, 1)
            }

            R.id.tv_ans_2 -> {
                selectedOptionView(tv_ans_2, 2)
            }

            R.id.tv_ans_3 -> {
                selectedOptionView(tv_ans_3, 3)
            }

            R.id.tv_ans_4 -> {
                selectedOptionView(tv_ans_4, 4)
            }
        }
    }

    fun onSubmitButtonClick(view: View) {
        //if the user has not selected any of the options
        if (mSelectedOptionPosition == 0) {

            mCurrentProgressPosition++

            when {
                mCurrentProgressPosition <= questionsList.size -> {
                    mIsUserSubmitted = false
                    setQuestion()
                }
                else -> {
                    val intent = Intent(this, EndGameActivity::class.java)
                    intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                    intent.putExtra(Constants.TOTAL_QUESTIONS, questionsList.size)
                    startActivity(intent)
                }
            }
        } else {
            val question = questionsList[mCurrentProgressPosition - 1]

            if (question.correctAnswer != mSelectedOptionPosition) {
                answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
            } else {
                mCorrectAnswers++
            }

            answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

            if (mCurrentProgressPosition == questionsList.size) {
                btn_submit_answer.text = "Завершить"
            } else {
                btn_submit_answer.text = "Следующий вопрос"
            }

            mSelectedOptionPosition = 0
            mIsUserSubmitted = true
        }
    }

    /**
     * A function to set the view of selected option view.
     */
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        resetOptionsView()

        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = getDrawable(this, R.drawable.selected_option_border_bg)
    }

    /**
     * A function to set default options view when the new question is loaded or when the answer is reselected.
     */
    private fun resetOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, tv_ans_1)
        options.add(1, tv_ans_2)
        options.add(2, tv_ans_3)
        options.add(3, tv_ans_4)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    /**
     * A function for answer view which is used to highlight the answer is wrong or right.
     */
    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {

            1 -> {
                tv_ans_1.background = getDrawable(this, drawableView)
            }
            2 -> {
                tv_ans_2.background = getDrawable(this, drawableView)
            }
            3 -> {
                tv_ans_3.background = getDrawable(this, drawableView)
            }
            4 -> {
                tv_ans_4.background = getDrawable(this, drawableView)
            }
        }
    }
}