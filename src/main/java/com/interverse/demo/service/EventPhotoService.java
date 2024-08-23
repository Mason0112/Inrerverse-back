package com.interverse.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.Event;
import com.interverse.demo.model.EventPhoto;
import com.interverse.demo.model.EventPhotoRepository;
import com.interverse.demo.model.EventRepository;
import com.interverse.demo.model.User;
import com.interverse.demo.model.UserRepository;

@Service
public class EventPhotoService {

	@Autowired
	private EventPhotoRepository epRepo;
	
	@Autowired
	private EventRepository eRepo;
	
	@Autowired
	private UserRepository uRepo;
	
	public EventPhoto saveEventPhoto(EventPhoto eventPhoto) {
		Event event = eventPhoto.getEvent();
		User uploaderId = eventPhoto.getUploaderId();
		if(eRepo.existsById(event.getId()) && uRepo.existsById(uploaderId.getId())) {
			return epRepo.save(eventPhoto);
		}
		throw new IllegalStateException("Event or User is not exists.");
	}

	// 尋找event中所有照片
	public List<EventPhoto> findAllByEventId(Integer eventId) {
		return epRepo.findByEventId(eventId);
	}


	// user從event中刪除自己上傳的照片
	public void deletePhotoIfOwner(Integer id, Integer uploaderId) {
		EventPhoto photo = epRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Photo not found with ID: " + id));

		// 檢查上傳者ID是否與要求刪除的使用者ID相同
		if (!photo.getUploaderId().getId().equals(uploaderId)) {
			throw new SecurityException("You do not have permission to delete this photo.");
		}

		epRepo.deleteById(id);
	}
}
