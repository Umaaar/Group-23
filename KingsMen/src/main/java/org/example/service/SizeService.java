package org.example.service;

import java.util.List;
import java.util.Optional;

import org.example.model.Size;
import org.example.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class SizeService {

@Autowired
SizeRepository sizeRepository; 

public List<Size> getAllSizes(){
    return sizeRepository.findAll();
}
public Optional<Size> getSizeById(Long id){
    return sizeRepository.findById(id);
}
    public Optional<Size> getSizeByName(String Name){
        return Optional.ofNullable(sizeRepository.findByName(Name).orElseThrow(() -> new EntityNotFoundException("Size not found")));
    }
public void saveSize(Size size){
    sizeRepository.save(size);  

}

public void deleteSize(Long id){
    sizeRepository.deleteById(id);
}

public void updateSize(Size size){
    sizeRepository.save(size);
}

}
