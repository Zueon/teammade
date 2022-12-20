package com.mztm.teammade.persistence;

import com.mztm.teammade.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}