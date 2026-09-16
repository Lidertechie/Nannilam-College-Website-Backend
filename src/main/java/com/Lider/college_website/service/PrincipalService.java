package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.PrincipalRequest;
import com.Lider.college_website.dto.response.PrincipalResponse;

import java.util.List;

public interface PrincipalService {
    List<PrincipalResponse> getAllPrincipals();
    PrincipalResponse getPrincipalById(Long id);
    PrincipalResponse createPrincipal(PrincipalRequest request);
    PrincipalResponse updatePrincipal(Long id, PrincipalRequest request);
    void deletePrincipal(Long id);
}