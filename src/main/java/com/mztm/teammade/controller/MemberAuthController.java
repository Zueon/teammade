package com.mztm.teammade.controller;

import com.mztm.teammade.dto.MemberLoginReqDto;
import com.mztm.teammade.dto.MemberSignupReqDto;
import com.mztm.teammade.dto.ResponseDTO;
import com.mztm.teammade.dto.TokenInfo;
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

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberAuthController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberSignupReqDto req){

        try {
            Member member = memberService.signup(req);
            MemberDto response = new MemberDto(member);

            return ResponseEntity.ok().body(response);

        } catch (Exception  e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody MemberLoginReqDto memberLoginRequestDto) {
        String email = memberLoginRequestDto.getEmail();
        String password = memberLoginRequestDto.getPassword();
        try {
            TokenInfo tokenInfo = memberService.login(email, password);
            return ResponseEntity.ok().body(tokenInfo);

        } catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }

    }
}
