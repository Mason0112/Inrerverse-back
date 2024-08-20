package com.interverse.demo.event;

import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;


@Component
public class FriendEventListener implements PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {

	@Override
	public boolean requiresPostCommitHandling(EntityPersister persister) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onPostDelete(PostDeleteEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostUpdate(PostUpdateEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostInsert(PostInsertEvent event) {
		// TODO Auto-generated method stub
		
	}

}
