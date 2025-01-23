package com.kachinga.eschool.repository;

import com.kachinga.eschool.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>,
        JpaSpecificationExecutor<Notification> {
    Optional<Notification> findByUuid(UUID uuid);

    @Query("FROM Notification n where n.recipientId = :recipientId and (n.read is null or n.read = false)")
    List<Notification> newNotifications(@Param("recipientId") Long recipientId);
}