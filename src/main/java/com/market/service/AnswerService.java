package com.market.service;

import com.market.dto.AnswerDto;
import com.market.entity.Answer;
import com.market.entity.Inquiry;
import com.market.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public List<Answer> getAnswerById(Long inquiryId) {
        List<Answer> answers = answerRepository.findByInquiryId(inquiryId);
        return answers;
    }

    public void save(Answer answer) {
        answerRepository.save(answer);
    }

    public int getCountById(Long inquiryId) {
        return answerRepository.getCountById(inquiryId);
    }

    public void delete(Long commentId) {
        answerRepository.deleteById(commentId);
    }
}

