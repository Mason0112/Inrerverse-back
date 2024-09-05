package com.interverse.demo.event;

import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.interverse.demo.model.Friend;
import com.interverse.demo.model.Notification;
import com.interverse.demo.model.NotificationRepository;
import com.interverse.demo.service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class FriendListener implements PostInsertEventListener, PostUpdateEventListener {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private NotificationService notifService;
	
	@Autowired
	private NotificationRepository notifRepo;

	private Integer getuUserIdFromRequest() {
		Integer requestUserId = Integer.valueOf(request.getHeader("X-User-ID"));
		return requestUserId;
	}

	@Override
	public boolean requiresPostCommitHandling(EntityPersister persister) {
		return true;
	}

	@Override
	public void onPostUpdate(PostUpdateEvent event) {
		Object entity = event.getEntity();

		if (entity instanceof Friend) {
			Friend friend = (Friend) entity;
			Integer requestUserId = getuUserIdFromRequest();
			
			if ((friend.getUser2().getId()).equals(requestUserId)) {
				
			}
			
		}

	}

	@Override
	public void onPostInsert(PostInsertEvent event) {
		Object entity = event.getEntity();
		
		//檢查確定是操作 friend entity
		if (entity instanceof Friend) {
			Friend friend = (Friend) entity;
			Integer requestUserId = getuUserIdFromRequest();

			if ((friend.getUser1().getId()).equals(requestUserId)) {

				// 封裝資料
				Notification notification = new Notification();
				notification.setSource(1); // 1暫定為好友
				notification.setContent("sent a friend request to you!!");
				notification.setStatus(false);
				notification.setSender(friend.getUser1());
				notification.setReceiver(friend.getUser2());

				notifService.addNotification(notification);
				notifRepo.flush();
			}
		}

	}

}
