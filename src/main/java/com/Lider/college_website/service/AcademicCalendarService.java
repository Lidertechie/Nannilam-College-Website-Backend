package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.AcademicCalendarFileRequest;
import com.Lider.college_website.dto.request.AcademicCalendarRequest;
import com.Lider.college_website.dto.response.AcademicCalendarResponse;

import java.util.List;

public interface AcademicCalendarService {

    AcademicCalendarResponse createTopic(AcademicCalendarRequest request);
    AcademicCalendarResponse updateTopic(Long id, AcademicCalendarRequest request);
    void deleteTopic(Long id);
    AcademicCalendarResponse toggleActive(Long id);

    AcademicCalendarResponse getById(Long id);                                   // full detail, with files
    List<AcademicCalendarResponse> getActiveByYear(Long academicYearId);         // summary list
    List<AcademicCalendarResponse> getAllActive();                              // summary list, all years
    List<AcademicCalendarResponse> getAllForAdmin();                             // summary list, includes inactive

    AcademicCalendarResponse addFile(Long calendarId, AcademicCalendarFileRequest request);
    void deleteFile(Long calendarId, Long fileId);
}