package com.mztm.teammade.service;

import com.mztm.teammade.dto.ApplyDto;
import com.mztm.teammade.dto.NotificationDto;



public interface NotificationService {
    NotificationDto notifyApplyEvent(ApplyDto applyDto);
    NotificationDto notifyAcceptEvent(ApplyDto applyDto);

}