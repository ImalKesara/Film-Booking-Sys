package com.backend.controller;

import com.backend.model.Payment;
import com.backend.security.dto.PaymentDto;
import com.backend.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/payment")
@CrossOrigin
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public Payment create(@Valid @RequestBody PaymentDto dto){
        return service.createPayment(dto);
    }

    @GetMapping
    public List<Payment> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Payment getById(@PathVariable Long id){
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        service.delete(id);
        return "Payment deleted successfully!";
    }

}
