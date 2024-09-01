package com.interverse.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.dto.NotificationDto;
import com.interverse.demo.model.Notification;
import com.interverse.demo.model.NotificationRepository;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notifRepo;

	public NotificationDto convert(Notification notif) {
		NotificationDto notifDto = new NotificationDto();
		notifDto.setId(notif.getId());
		notifDto.setSource(notif.getSource());
		notifDto.setContent(notif.getContent());
		notifDto.setStatus(notif.getStatus());
		notifDto.setSenderId(notif.getSender().getId());
		notifDto.setReceiverId(notif.getReceiver().getId());

		return notifDto;
	}

	public NotificationDto addNotification(Notification notification) {
		
		return convert(notifRepo.save(notification));
	}
	
	public List<NotificationDto> findMyNotification(Integer id) {

		List<Notification> notificationList = notifRepo.findByReceiverId(id);

		List<NotificationDto> notificationDtoList = notificationList.stream()
				.map(this::convert)
				.collect(Collectors.toList());

		return notificationDtoList;
	}

}
