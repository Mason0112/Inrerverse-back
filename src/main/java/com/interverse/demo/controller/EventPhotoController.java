package com.interverse.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.dto.EventPhotoDTO;
import com.interverse.demo.model.EventPhoto;
import com.interverse.demo.model.EventRepository;
import com.interverse.demo.model.UserRepository;
import com.interverse.demo.service.EventPhotoService;

@RestController
@RequestMapping("/eventPhoto")
public class EventPhotoController {
	
	@Autowired
	private EventPhotoService epService;
	
	@Autowired
	private EventRepository eRepo;
	
	@Autowired
	private UserRepository uRepo;
	
	//建立
	@PostMapping
	public ResponseEntity<?> createEventPhoto(@RequestBody EventPhotoDTO eventPhotoDTO) {
		try {
			EventPhoto eventPhoto = convertToEntity(eventPhotoDTO);
			EventPhoto savedEventPhoto = epService.saveEventPhoto(eventPhoto);
			return ResponseEntity.ok(convertToDTO(savedEventPhoto));

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	
	//尋找event裡所有照片
	@GetMapping("/event/{eventId}")
	public ResponseEntity<List<EventPhotoDTO>> getAllPhotoByEventId(@PathVariable Integer eventId){
		try {
			List<EventPhoto> allByEventId = epService.findAllByEventId(eventId);
			List<EventPhotoDTO> photoDTO = allByEventId.stream().map(this::convertToDTO).collect(Collectors.toList());
			return ResponseEntity.ok(photoDTO);
		}catch (Exception e) {
			System.out.println("錯誤 " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

			}
		
	}
	
	// user可以在event中刪除自己上傳的照片
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteEventPhoto(@PathVariable Integer id, @RequestParam Integer uploaderId) {
	    if (uploaderId == null) {
	        return ResponseEntity.badRequest().body("Uploader ID is required.");
	    }
	    try {
	        epService.deletePhotoIfOwner(id, uploaderId);
	        return ResponseEntity.ok("Photo deleted successfully.");
	    } catch (SecurityException | IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
	    }
	}
	
	//轉換DTO
		public EventPhotoDTO convertToDTO(EventPhoto eventPhoto) {
		   EventPhotoDTO dto = new EventPhotoDTO();
		   
		   dto.setId(eventPhoto.getId());
		   dto.setPhoto(eventPhoto.getPhoto());
		   dto.setEventId(eventPhoto.getEvent().getId());
		   dto.setUploaderId(eventPhoto.getUploaderId().getId());
		   
		   return dto;
		}
		
		// DTO轉換實體
		private EventPhoto convertToEntity(EventPhotoDTO dto) {
			EventPhoto eventPhoto = new EventPhoto();
			eventPhoto.setPhoto(dto.getPhoto());
			eventPhoto.setUploaderId(uRepo.findById(dto.getUploaderId()).orElse(null));
			eventPhoto.setEvent(eRepo.findById(dto.getEventId()).orElse(null));
			return eventPhoto;

		}
}
