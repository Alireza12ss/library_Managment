package com.example.library.repository;

import com.example.library.entity.BookGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookGroupRepository extends JpaRepository<BookGroup, Long> {
    Optional<BookGroup> findByName(String name);
    @Query("SELECT bg FROM BookGroup bg WHERE LOWER(bg.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<BookGroup> searchByKeyword(@Param("keyword") String keyword);

    boolean existsByName(String name);
}
