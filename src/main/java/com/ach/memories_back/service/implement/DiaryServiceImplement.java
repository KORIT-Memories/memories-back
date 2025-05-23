package com.ach.memories_back.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ach.memories_back.common.dto.request.diary.PatchDiaryRequestDto;
import com.ach.memories_back.common.dto.request.diary.PostCommentRequestDto;
import com.ach.memories_back.common.dto.request.diary.PostDiaryRequestDto;
import com.ach.memories_back.common.dto.response.ResponseDto;
import com.ach.memories_back.common.dto.response.diary.GetCommentResponseDto;
import com.ach.memories_back.common.dto.response.diary.GetDiaryResponseDto;
import com.ach.memories_back.common.dto.response.diary.GetEmpathyResponseDto;
import com.ach.memories_back.common.dto.response.diary.GetMyDiaryResponseDto;
import com.ach.memories_back.common.entity.CommentEntity;
import com.ach.memories_back.common.entity.DiaryEntity;
import com.ach.memories_back.common.entity.EmpathyEntity;
import com.ach.memories_back.repository.CommentRepository;
import com.ach.memories_back.repository.DiaryRepository;
import com.ach.memories_back.repository.EmpathyRespository;
import com.ach.memories_back.service.DiaryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaryServiceImplement implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final EmpathyRespository empathyRespository;
    private final CommentRepository commentRepository;

    @Override
    public ResponseEntity<ResponseDto> postDiary(PostDiaryRequestDto dto, String userId) {
        
        try {

            DiaryEntity diaryEntity = new DiaryEntity(dto, userId);
            diaryRepository.save(diaryEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();

            return ResponseDto.databaseError();
        }

        return ResponseDto.success(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<? super GetMyDiaryResponseDto> getMyDiary(String userId) {

        List<DiaryEntity> diaryEntities = new ArrayList<>();
        
        try {
            
            diaryEntities = diaryRepository.findByOrderByDiaryNumberDesc();
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetMyDiaryResponseDto.success(diaryEntities);
    }

    @Override
    public ResponseEntity<? super GetDiaryResponseDto> getDiary(Integer diaryNumber) {

        DiaryEntity diaryEntity = null;

        try {

            diaryEntity = diaryRepository.findByDiaryNumber(diaryNumber);
            if (diaryEntity == null) return ResponseDto.noExistDiary();
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetDiaryResponseDto.success(diaryEntity);
    }

    @Override
    public ResponseEntity<ResponseDto> patchDiary(PatchDiaryRequestDto dto, Integer diaryNumber, String userId) {
        
        try {

            DiaryEntity diaryEntity = diaryRepository.findByDiaryNumber(diaryNumber);
            if (diaryEntity == null) return ResponseDto.noExistDiary();

            String writerId = diaryEntity.getUserId();
            boolean isWriter = writerId.equals(userId);
            if (!isWriter) return ResponseDto.noPermission();

            diaryEntity.patch(dto);
            diaryRepository.save(diaryEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDto> deleteDiary(Integer diaryNumber, String userId) {
        
        try {

            DiaryEntity diaryEntity = diaryRepository.findByDiaryNumber(diaryNumber);
            if (diaryEntity == null) return ResponseDto.noExistDiary();

            String writerId = diaryEntity.getUserId();
            boolean isWriter = writerId.equals(userId);
            if (!isWriter) return ResponseDto.noPermission();

            empathyRespository.deleteByDiaryNumber(diaryNumber);
            commentRepository.deleteByDiaryNumber(diaryNumber);
            diaryRepository.delete(diaryEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<? super GetEmpathyResponseDto> getEmpathy(Integer diaryNumber) {

        List<EmpathyEntity> empathyEntities = new ArrayList<>();
        
        try {

            empathyEntities = empathyRespository.findByDiaryNumber(diaryNumber);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetEmpathyResponseDto.success(empathyEntities);
    }

    @Override
    public ResponseEntity<ResponseDto> putEmpathy(Integer diaryNumber, String userId) {
        
        try {

            boolean existDiary = diaryRepository.existsByDiaryNumber(diaryNumber);
            if (!existDiary) return ResponseDto.noExistDiary();

            EmpathyEntity empathyEntity = empathyRespository.findByUserIdAndDiaryNumber(userId, diaryNumber);
            if (empathyEntity == null) {
                empathyEntity = new EmpathyEntity(userId, diaryNumber);
                empathyRespository.save(empathyEntity);
            }   else {
                empathyRespository.delete(empathyEntity);
            }
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<? super GetCommentResponseDto> getComment(Integer diaryNumber) {
        
        List<CommentEntity> commentEntities = new ArrayList<>();

        try {

            commentEntities = commentRepository.findByDiaryNumberOrderByWriteDateDesc(diaryNumber);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetCommentResponseDto.success(commentEntities);
    }

    @Override
    public ResponseEntity<ResponseDto> postComment(PostCommentRequestDto dto, Integer diaryNumber, String userId) {
        
        try {
            
            boolean existDiary = diaryRepository.existsByDiaryNumber(diaryNumber);
            if (!existDiary) return ResponseDto.noExistDiary();

            CommentEntity commentEntity = new CommentEntity(dto, diaryNumber, userId);
            commentRepository.save(commentEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success(HttpStatus.CREATED);
    }

    


    
}
