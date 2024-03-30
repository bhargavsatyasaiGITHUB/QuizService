package com.quiz.QuizService.services;


import com.quiz.QuizService.daos.QuizDao;

import com.quiz.QuizService.feign.IQuizInterface;
import com.quiz.QuizService.models.QuestionWrapper;
import com.quiz.QuizService.models.Quiz;
import com.quiz.QuizService.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
   IQuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

     List<Integer> questions=quizInterface.getQuestionsForQuiz(category,numQ).getBody();
     Quiz quiz=new Quiz();
     quiz.setTitle(title);
     quiz.setQuestionList(questions);
     quizDao.save(quiz);
           return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
     Quiz quiz=quizDao.findById(id).get();
 List<Integer> questionIds=quiz.getQuestionList();
 ResponseEntity<List<QuestionWrapper>> questions=quizInterface.getQuestionsFromId(questionIds);
        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
     ResponseEntity<Integer> score=quizInterface.getScore(responses);
        return score;
    }
}
