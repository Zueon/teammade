package com.mztm.teammade.controller;

import com.mztm.teammade.dto.ResponseDTO;
import com.mztm.teammade.dto.StudyDto;
import com.mztm.teammade.entity.Member;
import com.mztm.teammade.entity.Study;
import com.mztm.teammade.service.FileStorageService;
import com.mztm.teammade.service.MemberService;
import com.mztm.teammade.service.PostService;
import com.mztm.teammade.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyController {
    private final PostService postService;
    private final MemberService memberService;


    @GetMapping
    public ResponseEntity<?> getStudies(){
        List<Study> studyEntities =  postService.getStudyList();
        List<StudyDto> studyList = studyEntities.stream().map(StudyDto::new).collect(Collectors.toList());

        ResponseDTO<StudyDto> response = ResponseDTO.<StudyDto>builder().data(studyList).build();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity createStudy(@RequestBody StudyDto dto) {
        String email = SecurityUtil.getCurrentMemberEmail();
        Member member = memberService.getMemberByEmail(email);
        try {

            Study study = new Study(dto);

            study.addHost(member);

            Study createStudy = postService.createStudy(study);
            StudyDto createStudyDto = new StudyDto(createStudy);

            ResponseDTO<StudyDto> response = ResponseDTO.<StudyDto>builder().data(createStudyDto).build();
            return ResponseEntity.ok().body(response);

        } catch (Exception e){
            String err = e.getMessage();
            ResponseDTO<StudyDto> response = ResponseDTO.<StudyDto>builder().error(err).build();

            return ResponseEntity.badRequest().body(response);
        }
    }
}
