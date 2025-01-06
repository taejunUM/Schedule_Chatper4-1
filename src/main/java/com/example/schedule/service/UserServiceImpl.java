package com.example.schedule.service;

import com.example.schedule.dto.LoginRequestDto;
import com.example.schedule.dto.UserRequestDto;
import com.example.schedule.dto.UserResponseDto;
import com.example.schedule.entity.User;
import com.example.schedule.exception.ErrorCode;
import com.example.schedule.exception.GlobalException;
import com.example.schedule.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EntityManager em;
    private final HttpSession session;

    @Override
    public UserResponseDto addUser(UserRequestDto dto) {
        User user = new User(dto.getUserName(), dto.getEmail(), dto.getPw());
        User save = userRepository.save(user);

        return UserResponseDto.toDto(save);
    }

    @Override
    public List<UserResponseDto> findAllUser() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findByIdOrElseThrow(id);
        return UserResponseDto.toDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto modifyUser(Long id, UserRequestDto dto) {
        User user = userRepository.findByIdOrElseThrow(id);

        String userName = dto.getUserName();
        String email = dto.getEmail();
        String pw = dto.getPw();

        user.updateUser(userName, email, pw);
        em.flush();
        return UserResponseDto.toDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findByIdOrElseThrow(id);
        userRepository.delete(user);
        session.invalidate();
    }

    @Override
    public void login(LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        if (!user.getPw().equals(dto.getPw())) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        session.setAttribute("userEmail", dto.getEmail());
    }

    @Override
    public void logout() {
        session.invalidate();
    }

}
