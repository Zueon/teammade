package com.mztm.teammade.controller;

import com.mztm.teammade.dto.ResponseDTO;
import com.mztm.teammade.entity.Member;
import com.mztm.teammade.entity.MemberDto;
import com.mztm.teammade.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Log4j2
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody MemberDto userDTO) {
        try {

            // 요청으로 받은 사용자 정보 (userDTO) 사용하여 엔티티 생성
            Member user = Member.builder()
                    .name(userDTO.getName())
                    .nickname(userDTO.getNickname())
                    .email(userDTO.getEmail())
                    .password(userDTO.getPassword())
                    .gender(userDTO.getGender())
                    .address(userDTO.getAddress())
                    .build();

            // 서비스 사용하여 생성한 사용자를 레포지터리에 저장한다
            Member registeredUser = memberService.create(user);

            // 응답으로 보낼 DTO 생성
            MemberDto responseUserDTO = new MemberDto(registeredUser);

            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e) {
            log.info(e);
            // 사용자 정보는 항상 하나 -> 리스트x
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody MemberDto userDTO) {
        Member user = memberService.getByCredentials(
                userDTO.getEmail(), userDTO.getPassword());
        if (user != null) {
            final MemberDto responseUserDTO = MemberDto.builder()
                    .email(user.getEmail())
                    .mid(userDTO.getMid())
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Fail to Login").build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }
}
