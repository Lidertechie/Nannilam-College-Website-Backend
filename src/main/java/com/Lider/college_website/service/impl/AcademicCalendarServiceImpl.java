package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.AcademicCalendarFileRequest;
import com.Lider.college_website.dto.request.AcademicCalendarRequest;
import com.Lider.college_website.dto.response.AcademicCalendarResponse;
import com.Lider.college_website.entity.AcademicCalendar;
import com.Lider.college_website.entity.AcademicCalendarFile;
import com.Lider.college_website.entity.AcademicYear;
import com.Lider.college_website.exception.ResourceNotFoundException;
import com.Lider.college_website.repository.AcademicCalendarFileRepository;
import com.Lider.college_website.repository.AcademicCalendarRepository;
import com.Lider.college_website.repository.AcademicYearRepository;
import com.Lider.college_website.service.AcademicCalendarService;
import com.Lider.college_website.service.mapper.AcademicCalendarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AcademicCalendarServiceImpl implements AcademicCalendarService {

    private final AcademicCalendarRepository calendarRepository;
    private final AcademicCalendarFileRepository fileRepository;
    private final AcademicYearRepository academicYearRepository;

    @Override
    @Transactional
    public AcademicCalendarResponse createTopic(AcademicCalendarRequest request) {
        AcademicYear year = getYearOrThrow(request.getAcademicYearId());

        AcademicCalendar calendar = AcademicCalendar.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .academicYear(year)
                .build();

        AcademicCalendar saved = calendarRepository.save(calendar);
        return AcademicCalendarMapper.toSummaryResponse(saved, 0);
    }

    @Override
    @Transactional
    public AcademicCalendarResponse updateTopic(Long id, AcademicCalendarRequest request) {
        AcademicCalendar calendar = getCalendarOrThrow(id);

        if (!calendar.getAcademicYear().getId().equals(request.getAcademicYearId())) {
            calendar.setAcademicYear(getYearOrThrow(request.getAcademicYearId()));
        }

        calendar.setTitle(request.getTitle());
        calendar.setDescription(request.getDescription());

        long count = fileRepository.countByAcademicCalendarId(id);
        return AcademicCalendarMapper.toSummaryResponse(calendar, count);
    }

    @Override
    @Transactional
    public void deleteTopic(Long id) {
        AcademicCalendar calendar = getCalendarOrThrow(id);
        // Explicitly clean up child files first to avoid FK constraint violations —
        // consistent, no-surprises approach (same as Gallery's deleteGallery discussion).
        fileRepository.deleteAll(fileRepository.findAllByAcademicCalendarId(id));
        calendarRepository.delete(calendar);
    }

    @Override
    @Transactional
    public AcademicCalendarResponse toggleActive(Long id) {
        AcademicCalendar calendar = getCalendarOrThrow(id);
        calendar.setActive(!calendar.isActive());
        long count = fileRepository.countByAcademicCalendarId(id);
        return AcademicCalendarMapper.toSummaryResponse(calendar, count);
    }

    @Override
    @Transactional(readOnly = true)
    public AcademicCalendarResponse getById(Long id) {
        AcademicCalendar calendar = getCalendarOrThrow(id);
        List<AcademicCalendarFile> files = fileRepository.findAllByAcademicCalendarId(id);
        return AcademicCalendarMapper.toDetailResponse(calendar, files);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AcademicCalendarResponse> getActiveByYear(Long academicYearId) {
        return calendarRepository
                .findAllByAcademicYearIdAndActiveTrueOrderByCreatedAtDesc(academicYearId)
                .stream()
                .map(calendar -> {
                    List<AcademicCalendarFile> files =
                            fileRepository.findAllByAcademicCalendarId(calendar.getId());

                    return AcademicCalendarMapper.toDetailResponse(calendar, files);
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AcademicCalendarResponse> getAllActive() {
        return calendarRepository.findAllByActiveTrueOrderByCreatedAtDesc()
                .stream()
                .map(calendar -> {
                    List<AcademicCalendarFile> files =
                            fileRepository.findAllByAcademicCalendarId(calendar.getId());

                    return AcademicCalendarMapper.toDetailResponse(calendar, files);
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AcademicCalendarResponse> getAllForAdmin() {
        return calendarRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(calendar -> {
                    List<AcademicCalendarFile> files =
                            fileRepository.findAllByAcademicCalendarId(calendar.getId());

                    return AcademicCalendarMapper.toDetailResponse(calendar, files);
                })
                .toList();
    }

    @Override
    @Transactional
    public AcademicCalendarResponse addFile(Long calendarId, AcademicCalendarFileRequest request) {
        AcademicCalendar calendar = getCalendarOrThrow(calendarId);

        AcademicCalendarFile file = AcademicCalendarFile.builder()
                .academicCalendar(calendar)
                .fileUrl(request.getFileUrl())
                .fileName(request.getFileName())
                .build();

        fileRepository.save(file);

        List<AcademicCalendarFile> allFiles = fileRepository.findAllByAcademicCalendarId(calendarId);
        return AcademicCalendarMapper.toDetailResponse(calendar, allFiles);
    }

    @Override
    @Transactional
    public void deleteFile(Long calendarId, Long fileId) {
        AcademicCalendarFile file = fileRepository.findById(fileId)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("AcademicCalendarFile", "id", fileId));

        if (!file.getAcademicCalendar().getId().equals(calendarId)) {
            throw new ResourceNotFoundException(
                    "File " + fileId + " does not belong to academic calendar " + calendarId);
        }

        fileRepository.delete(file);
    }

    private AcademicCalendar getCalendarOrThrow(Long id) {
        return calendarRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("AcademicCalendar", "id", id));
    }

    private AcademicYear getYearOrThrow(Long id) {
        return academicYearRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("AcademicYear", "id", id));
    }
}