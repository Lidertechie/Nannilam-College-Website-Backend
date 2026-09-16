package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.NaanMudhalvanSchemeRequestDTO;
import com.Lider.college_website.dto.response.NaanMudhalvanSchemeResponseDTO;

import java.util.List;

public interface NaanMudhalvanSchemeService {

    NaanMudhalvanSchemeResponseDTO create(NaanMudhalvanSchemeRequestDTO requestDTO);

    List<NaanMudhalvanSchemeResponseDTO> getAll();

    NaanMudhalvanSchemeResponseDTO getById(Long id);

    NaanMudhalvanSchemeResponseDTO update(Long id, NaanMudhalvanSchemeRequestDTO requestDTO);

    void delete(Long id);

    NaanMudhalvanSchemeResponseDTO updateVerifiedStatus(Long id, boolean verified);
}