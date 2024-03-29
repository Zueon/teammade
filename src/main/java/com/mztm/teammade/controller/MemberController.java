package com.mztm.teammade.controller;


import com.mztm.teammade.dto.*;
import com.mztm.teammade.entity.Member;
import com.mztm.teammade.dto.MemberDto;
import com.mztm.teammade.entity.Notification;
import com.mztm.teammade.entity.Project;
import com.mztm.teammade.persistence.ProjectRepository;
import com.mztm.teammade.service.FileStorageService;
import com.mztm.teammade.service.MemberService;
import com.mztm.teammade.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final FileStorageService storageService;
    private final ProjectRepository projectRepository;


    @GetMapping("/")
    public ResponseEntity<?> getMember(){
        String email = SecurityUtil.getCurrentMemberEmail();
        Member member = memberService.getMemberByEmail(email);
        log.info(member);

        ProjectDTO projectDto = null;
        StudyDto studyDto = null;
        ResumeDto resumeDto = null;

        if (member.getProject() != null){
            Project proj = projectRepository.findById(member.getProject().getId()).orElseThrow(()->new IllegalArgumentException("존재하지 !"));
             projectDto = ProjectDTO.builder()
                     .members(proj.getMembers().stream().map(MemberDto::new).collect(Collectors.toList()))
                     .todos(proj.getTodos().stream().map(TodoDto::new).collect(Collectors.toList()))
                     .title(member.getProject().getTitle())
                     .pid(member.getProject().getId())
                     .hostId(member.getProject().getHost().getMid())
                     .build();
        }

        if (member.getStudy() != null){
            studyDto = StudyDto.builder()
                    .title(member.getStudy().getTitle())
                    .build();
        }

        if (member.getResume() != null){
            resumeDto = storageService.getResume(member);
        }

        MemberDto dto = MemberDto.builder()
                .address(member.getEmail())
                .email(member.getEmail())
                .gender(member.getGender())
                .name(member.getName())
                .nickname(member.getNickname())
                .project(projectDto)
                .study(studyDto)
                .resume(resumeDto)
                .mid(member.getMid())
                .build();


        ResponseDTO<MemberDto> response = ResponseDTO.<MemberDto>builder().data(dto).build();

        return ResponseEntity.ok().body(response);

    }

    @PostMapping("/uploadResume")
    public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file) {
        String email = SecurityUtil.getCurrentMemberEmail();
        String message = "";

        try {
            storageService.storeResume(file, email);

            message = "파일 업로드를 성공하였습니다: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder().message(message).build());
        } catch (Exception e) {
            message = "파일 업로드를 실패하였습니다: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDTO.builder().message(message).build());
        }
    }

    @GetMapping("/sentNotifications")
    public ResponseEntity<?> sentList(){
        Member member=memberService.getMemberByEmail(SecurityUtil.getCurrentMemberEmail());
        List<Notification> entities = member.getSendList();
        List<NotificationDto> sentNotifications = entities.stream().map(NotificationDto::new).collect(Collectors.toList());
        ResponseDTO<NotificationDto> response = ResponseDTO.<NotificationDto>builder().data(sentNotifications).build();

        return ResponseEntity.ok().body(response);
    }


    public void updateMember(){}

    public void deleteMember(){}
}
