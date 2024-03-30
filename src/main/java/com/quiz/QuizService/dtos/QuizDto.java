package com.quiz.QuizService.dtos;

import lombok.Data;

@Data
public class QuizDto {
    private String categoryName;
    private int numOfQuestions;
    private String title;
}
