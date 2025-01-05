package com.example.schedule.service;


import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final HttpSession session;
    private final EntityManager em;
    private final UserRepository userRepository;


    @Override
    // 필터링을 통해 무조건 로그인 한 유저만 이용할 수있는 기능.
    public ScheduleResponseDto addSchedule(ScheduleRequestDto dto) {
        String email = (String) session.getAttribute("userEmail");
        User user = userRepository.findByEmail(email).get();

        Schedule schedule = new Schedule(dto.getTitle(), dto.getContents(), user);
        Schedule save = scheduleRepository.save(schedule);

        return ScheduleResponseDto.toDto(save);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedulue(String author, String date) {
        LocalDate localDate = null;

        if (date != null && !date.isBlank()) {
            localDate = LocalDate.parse(date);
        }
        List<Schedule> schedules = scheduleRepository.findAllSchedule(author, localDate);
        return schedules.stream()
                .map(ScheduleResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        return ScheduleResponseDto.toDto(schedule);
    }

    @Override
    @Transactional
    public ScheduleResponseDto modifySchedule(Long id, ScheduleRequestDto dto) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        String title = dto.getTitle();
        String contents = dto.getContents();

        schedule.updateSchedule(title, contents);
        em.flush();//메소드가 끝나기전에 커밋함.(변경사항 즉시 반영)
        return ScheduleResponseDto.toDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        scheduleRepository.delete(schedule);

    }
}
