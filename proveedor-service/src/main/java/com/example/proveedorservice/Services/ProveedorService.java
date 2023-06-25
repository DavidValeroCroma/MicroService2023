package com.example.proveedorservice.Services;

import com.example.proveedorservice.Entities.ProveedorEntity;
import com.example.proveedorservice.Repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProveedorService {

    @Autowired
    ProveedorRepository proveedorRepository;

    public ArrayList<ProveedorEntity> obtenerProveedores(){
        return (ArrayList<ProveedorEntity>) proveedorRepository.findAll();
    }
    public ProveedorEntity obtenerProveedorPorId(String idProveedor){
        return proveedorRepository.getReferenceById(idProveedor);
    }

    public void guardarProveedor(ProveedorEntity data){proveedorRepository.save(data);}

}
