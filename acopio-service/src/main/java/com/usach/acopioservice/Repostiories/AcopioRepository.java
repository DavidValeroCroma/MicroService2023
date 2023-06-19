package com.usach.acopioservice.Repostiories;

import com.usach.acopioservice.Entities.AcopioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface AcopioRepository extends JpaRepository<AcopioEntity, Long> {
    ArrayList<AcopioEntity> findByIdProveedor(Long idProveedor);
}
