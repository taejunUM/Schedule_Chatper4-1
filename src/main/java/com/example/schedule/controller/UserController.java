package com.example.schedule.controller;

import com.example.schedule.dto.LoginRequestDto;
import com.example.schedule.dto.UserRequestDto;
import com.example.schedule.dto.UserResponseDto;
import com.example.schedule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")

public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     *
     * @param dto 회원가입시 입력된 유저 정보
     * @return 회원가입한 유저 정보를 반환해줌
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> addUser(@RequestBody UserRequestDto dto) {
        return new ResponseEntity<>(userService.addUser(dto), HttpStatus.CREATED);
    }

    /**
     * 모든 유저 조회
     *
     * @return 조회된 모든 유저 정보
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAllUser() {
        return new ResponseEntity<>(userService.findAllUser(), HttpStatus.OK);
    }

    /**
     * 특정 유저 조회
     *
     * @param id 회원가입한 유저의 고유 id
     * @return id로 검색한 유저 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    /**
     * 유저 정보 수정
     *
     * @param id  회원가입한 유저의 고유id
     * @param dto 수정시 입력된 유저 정보
     * @return 수정된 유저 정보 반환
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> modifyUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto dto
    ) {
        return new ResponseEntity<>(userService.modifyUser(id, dto), HttpStatus.OK);
    }

    /**
     * 회원 탈퇴
     *
     * @param id 회원가입한 유저의 고유id
     * @return 상태코드 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    ) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 로그인
     *
     * @param dto 로그인시 입력된 유저 email, pw
     * @return 상태코드 반환
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequestDto dto) {
        userService.login(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 로그아웃
     * @return 상태코드 반환
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(){
        userService.logout();
        return ResponseEntity.noContent().build();
    }
}
