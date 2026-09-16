package com.Lider.college_website.service.mapper;

import com.Lider.college_website.dto.request.PrincipalRequest;
import com.Lider.college_website.dto.response.PrincipalResponse;
import com.Lider.college_website.entity.Principal;

public class PrincipalMapper {

    public static PrincipalResponse toResponse(Principal principal) {
        return new PrincipalResponse(
                principal.getId(),
                principal.getFromDate(),
                principal.getToDate(),
                principal.getName(),
                principal.getQualification(),
                principal.getDesignation(),
                principal.getImageUrl()
        );
    }

    public static Principal toEntity(PrincipalRequest request) {
        Principal principal = new Principal();
        principal.setFromDate(request.getFromDate());
        principal.setToDate(request.getToDate());
        principal.setName(request.getName());
        principal.setQualification(request.getQualification());
        principal.setDesignation(request.getDesignation());
        principal.setImageUrl(request.getImageUrl());
        return principal;
    }
}