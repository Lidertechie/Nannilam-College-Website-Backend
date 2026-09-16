package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.PrincipalRequest;
import com.Lider.college_website.dto.response.PrincipalResponse;
import com.Lider.college_website.entity.Principal;
import com.Lider.college_website.exception.ResourceNotFoundException;
import com.Lider.college_website.repository.PrincipalRepository;
import com.Lider.college_website.service.PrincipalService;
import com.Lider.college_website.service.mapper.PrincipalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrincipalServiceImpl implements PrincipalService {

    private final PrincipalRepository principalRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PrincipalResponse> getAllPrincipals() {
        return principalRepository.findAll()
                .stream()
                .map(PrincipalMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PrincipalResponse getPrincipalById(Long id) {
        Principal principal = principalRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Principal", "id", id));
        return PrincipalMapper.toResponse(principal);
    }

    @Override
    @Transactional
    public PrincipalResponse createPrincipal(PrincipalRequest request) {
        Principal principal = PrincipalMapper.toEntity(request);
        Principal saved = principalRepository.save(principal);
        return PrincipalMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public PrincipalResponse updatePrincipal(Long id, PrincipalRequest request) {
        Principal principal = principalRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Principal", "id", id));

        principal.setFromDate(request.getFromDate());
        principal.setToDate(request.getToDate());
        principal.setName(request.getName());
        principal.setQualification(request.getQualification());
        principal.setDesignation(request.getDesignation());
        principal.setImageUrl(request.getImageUrl());

        Principal updated = principalRepository.save(principal);
        return PrincipalMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void deletePrincipal(Long id) {
        Principal principal = principalRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Principal", "id", id));
        principalRepository.delete(principal);
    }
}