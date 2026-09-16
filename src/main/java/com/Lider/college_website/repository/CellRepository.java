package com.Lider.college_website.repository;

import com.Lider.college_website.entity.Cell;
import com.Lider.college_website.enums.CellCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CellRepository extends JpaRepository<Cell, Long> {

    List<Cell> findAllByActiveTrue();

    List<Cell> findAllByCategoryAndActiveTrue(CellCategory category);

    List<Cell> findAllByStaffId(Long staffId);

    List<Cell> findAll(); // admin view, includes inactive — inherited from JpaRepository, listed for clarity

    boolean existsByStaffIdAndCategory(Long staffId, CellCategory category);

    boolean existsByStaffIdAndCategoryAndIdNot(Long staffId,
                                               CellCategory category,
                                               Long id);
}