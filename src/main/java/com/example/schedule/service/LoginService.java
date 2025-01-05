package com.example.schedule.service;

import com.example.schedule.dto.LoginRequestDto;
import com.example.schedule.entity.User;
import com.example.schedule.exception.ErrorCode;
import com.example.schedule.exception.GlobalException;
import com.example.schedule.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final HttpSession session;

    public void login(LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        if (!user.getPw().equals(dto.getPw())) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        session.setAttribute("userEmail", dto.getEmail());
    }
}
