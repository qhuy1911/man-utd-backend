package com.example.manutdbackend.repository;

import com.example.manutdbackend.models.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizeRepository extends JpaRepository<Size, Long> {
    List<Size> findByProductId(Long productId);
}
