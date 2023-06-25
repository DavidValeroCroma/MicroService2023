package com.example.pagoservice.Repositories;

import com.example.pagoservice.Entities.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<PagoEntity, Long> {
    PagoEntity findByIdProveedor(String idProveedor);
}
