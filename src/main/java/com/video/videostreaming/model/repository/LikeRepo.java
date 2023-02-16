//package com.video.videostreaming.model.repository;
//
//
//import com.video.videostreaming.model.entity.Like;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface LikeRepo extends JpaRepository<Like, Long> {
//
//    @Query("UPDATE Like l set l.totalLike = ?1 where l.id = ?2")
//    Like updateTotalLike(int totalLike, Long id);
//
//}
