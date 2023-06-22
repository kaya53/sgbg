package com.sgbg.repository;


import com.sgbg.domain.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findById(Long roomId);

    List<Room> findAllByOrderByCreatedDateDesc();
    
    List<Room> findAllByParentCategory(String parentCategory);

    List<Room> findAllByChildCategory(String childCategory);

    List<Room> findByTitleContaining(String keyword);

//    List<Room> findAllBy(Pageable pageable);
//    Optional<Room> findByUserId(Long userId);
//    List<Room> findByUserId(Long userId);

}
