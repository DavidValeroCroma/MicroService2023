package com.example.proveedorservice.Services;

import com.example.proveedorservice.Entities.ProveedorEntity;
import com.example.proveedorservice.Repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    ProveedorRepository proveedorRepository;

    public List<ProveedorEntity> obtenerProveedores(){
        return (List<ProveedorEntity>) proveedorRepository.findAll();
    }
    public ProveedorEntity obtenerProveedorPorId(String id){
        return proveedorRepository.findByIdProveedor(id);
    }

    public void guardarProveedor(ProveedorEntity data){proveedorRepository.save(data);}

}
