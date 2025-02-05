package com.example.controller;

import com.example.dto.request.PermissionRequestDTO;
import com.example.dto.response.PermissionResponseDTO;
import com.example.mapper.PermissionMapper;
import com.example.service.IPermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final IPermissionService service;
    private final PermissionMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.toResponseDTO(service.findById(id)));
    }

    @PostMapping("/save")
    public ResponseEntity<PermissionResponseDTO> create(@Valid @RequestBody PermissionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponseDTO(service.save(mapper.toEntity(request))));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponseDTO> updateById(
            @Valid @RequestBody PermissionRequestDTO request, @PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(mapper.toResponseDTO(service
                        .updateById(mapper.toEntity(request), id)));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<PermissionResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(mapper.toListResponseDTO(service.findAll()));
    }
    
    @GetMapping("/all-page")
    public ResponseEntity<PagedModel<PermissionResponseDTO>> findAllPage(Pageable pageable) {;
        return ResponseEntity.status(HttpStatus.OK).body(new PagedModel<>(
                mapper.toPageResponseDTO(service.findAllPage(pageable))));
    }
}
