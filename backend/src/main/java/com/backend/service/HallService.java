package com.backend.service;

import com.backend.model.Hall;
import com.backend.model.Location;
import com.backend.repository.HallRepository;
import com.backend.repository.LocationRepository;
import com.backend.security.dto.HallDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class HallService {

    private final HallRepository repo;
    private final LocationRepository locationRepo;

    public Hall createHall(HallDto dto){
        Location location = locationRepo.findById(dto.getLocation())
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + dto.getLocation()));

        Hall h = new Hall();
        h.setName(dto.getName());
        h.setTotalSeats(dto.getTotalSeats());
        h.setLocation(location);

        return repo.save(h);

    }

    public List<Hall> getAll(){
        return repo.findAll();
    }

    public Hall getById(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hall not found with id: " + id));
    }

    public void delete(Long id){
        repo.deleteById(id);
    }

}
