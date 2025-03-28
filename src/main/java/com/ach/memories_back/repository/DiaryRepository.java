package com.ach.memories_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ach.memories_back.common.entity.DiaryEntity;
import java.util.List;


@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Integer> {

    boolean existsByDiaryNumber(Integer diaryNumber);
    
    DiaryEntity findByDiaryNumber(Integer diaryNumber);
    
    List<DiaryEntity> findByUserIdOrderByWriteDateDesc(String userId);
    List<DiaryEntity> findByOrderByDiaryNumberDesc();
}
