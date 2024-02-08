package com.example.my_quiz_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView, feedbackTextView, scoreTextView;
    private RadioGroup optionsRadioGroup;
    private Button submitButton;
    private RadioButton option1, option2;

    private int currentQuestionIndex = 0; // Track the current question index
    private int userScore = 0; // Track the user's score

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        feedbackTextView = findViewById(R.id.feedbackTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);

        // Set initial question
        updateQuestion();

        // Set click listener for the Submit button
        submitButton.setOnClickListener(v -> checkAnswer());
    }

    // Method to update the UI with the current question
    private void updateQuestion() {
        if (currentQuestionIndex < questions.length) {
            Question currentQuestion = questions[currentQuestionIndex];
            questionTextView.setText(currentQuestion.getQuestion());
            option1.setText(currentQuestion.getOptions()[0]);
            option2.setText(currentQuestion.getOptions()[1]);

            feedbackTextView.setText(""); // Clear feedback
        } else {
            feedbackTextView.setText("Please Re_play");
        }
    }

    // Method to check the selected answer against the correct answer
    private void checkAnswer() {
        int selectedOptionId = optionsRadioGroup.getCheckedRadioButtonId();

        if (selectedOptionId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedOptionId);
            String selectedAnswer = selectedRadioButton.getText().toString();

            String correctAnswer = questions[currentQuestionIndex].getCorrectAnswer();

            if (selectedAnswer.equals(correctAnswer)) {
                feedbackTextView.setText("Correct!");
                userScore++;
            } else {
                feedbackTextView.setText("Incorrect. Correct answer: " + correctAnswer);
            }

            currentQuestionIndex++;
            updateQuestion();

            // Check if the quiz is completed
            if (currentQuestionIndex == questions.length) {
                scoreTextView.setText("Your Score: " + userScore + " / " + questions.length);
                // Reset the quiz or perform any other actions if needed
                // You can also provide an option for the user to restart the quiz
                userScore = 0;
                currentQuestionIndex = 0;
                optionsRadioGroup.clearCheck();
            }


        } else {
            feedbackTextView.setText("Please select an answer.");
        }
    }


    // Data model for quiz questions
    private static class Question {
        private String question;
        private String[] options;
        private String correctAnswer;

        public Question(String question, String[] options, String correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestion() {
            return question;
        }

        public String[] getOptions() {
            return options;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }

    // Array of quiz questions
    private final Question[] questions = {
            new Question("What is the capital of France?", new String[]{"Paris", "Berlin"}, "Paris"),
            new Question("Which planet is known as the Red Planet?", new String[]{"Mars", "Venus"}, "Mars")
            // Add more questions as needed
    };
}
