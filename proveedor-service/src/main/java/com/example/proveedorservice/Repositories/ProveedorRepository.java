package com.example.proveedorservice.Repositories;

import com.example.proveedorservice.Entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity,String> {
    @Query("select e from ProveedorEntity e where e.id = :id")
    ProveedorEntity findByIdProveedor(@Param("id") String id);

}
