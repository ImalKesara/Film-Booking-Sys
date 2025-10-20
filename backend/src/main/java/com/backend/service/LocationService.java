package com.backend.service;

import com.backend.model.Location;
import com.backend.repository.LocationRepository;
import com.backend.security.dto.LocationDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LocationService {

    private final LocationRepository repo;

    public Location create(LocationDto dto){
        Location l = new Location();
        l.setName(dto.getName());
        l.setCity(dto.getCity());
        l.setAddress(dto.getAddress());

        return repo.save(l);
    }

    public List<Location> getAll(){
        return repo.findAll();
    }

    public Location getById(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
    }

    public void delete(Long id){
        repo.deleteById(id);
    }

}
