package com.mztm.teammade.service;

import com.mztm.teammade.controller.NotificationController;
import com.mztm.teammade.dto.ApplyDto;
import com.mztm.teammade.dto.NotificationDto;
import com.mztm.teammade.entity.Member;
import com.mztm.teammade.entity.Notification;
import com.mztm.teammade.entity.Project;
import com.mztm.teammade.persistence.MemberRepository;
import com.mztm.teammade.persistence.NotificationRepository;
import com.mztm.teammade.persistence.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final ProjectRepository projectRepo;
    private final MemberRepository memberRepo;
    private final NotificationRepository notificationRepo;


    @Override
    @Transactional
    public NotificationDto notifyApplyEvent(ApplyDto applyDto) {
        Long projectId = applyDto.getPid();
        String senderEmail = applyDto.getSenderEmail();
        String content = "";

        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("invalid project"));

        Member sender = memberRepo.findByEmail(senderEmail)
                .orElseThrow(() -> new IllegalArgumentException("invalid sender"));

        Member receiver = project.getHost();
        if (receiver == null) throw new IllegalArgumentException("invalid host");

        content = sender.getNickname() + "님이 프로젝트에 참가를 신청하였습니다!";

        Notification notification = Notification.builder()
                .content(content)
                .type("APPLY")
                .receiver(receiver)
                .sender(sender)
                .build();

        sender.addToSendList(notification);
        receiver.addToReceiveList(notification);
        notification = notificationRepo.save(notification);

        return Notification.toDto(notification);
    }

    @Override
    public NotificationDto notifyAcceptEvent(ApplyDto applyDto) {
        String applierEmail = applyDto.getSenderEmail();
        Long projectId = applyDto.getPid();
        String content = "";


        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("invalid project"));
        Member applier = memberRepo.findByNickname(applierEmail)
                .orElseThrow(() -> new IllegalArgumentException("invalid applier"));
        Member host = project.getHost();
        if (host == null) throw new IllegalArgumentException("invalid host");



        project.addMember(applier);

        content = applier.getNickname() + "님의 프로젝트 참가 신청이 수락되었습니다!";

        Notification notification = Notification.builder()
                .content(content)
                .type("ACCEPT")
                .receiver(applier)
                .sender(host)
                .build();

        host.addToSendList(notification);
        applier.addToReceiveList(notification);
        notification = notificationRepo.save(notification);

        return Notification.toDto(notification);
    }

    private void validateApply(ApplyDto applyDto){
        String applierEmail = applyDto.getSenderEmail();
        Long projectId = applyDto.getPid();

        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("invalid project"));
        Member applier = memberRepo.findByEmail(applierEmail).orElseThrow(() -> new IllegalArgumentException("invalid applier"));;

    }
}
