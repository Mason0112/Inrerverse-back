package com.interverse.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.model.Club;
import com.interverse.demo.model.ClubFavorite;
import com.interverse.demo.model.ClubFavoriteId;
import com.interverse.demo.model.User;
import com.interverse.demo.service.ClubFavoriteService;
import com.interverse.demo.service.ClubService;
import com.interverse.demo.service.UserService;

@RestController
@RequestMapping("/{club-favorite}")
public class ClubFavoriteController {

	@Autowired
	private ClubFavoriteService cfService;
	
	@Autowired
    private ClubService cService;

    @Autowired
    private UserService uService;
    
    //儲存ClubFavorite實體前，使用cService和uService取得對應的Club和User實體，設定到clubFavorite實體中。
	@PostMapping
	public ResponseEntity<?> createClubFavorite(@RequestBody ClubFavorite clubFavorite) {
		
		Club club = cService.findClubById(clubFavorite.getClubFavoriteId().getClubId());
        User user = uService.findUserById(clubFavorite.getClubFavoriteId().getUserId());
        
        if (club == null || user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Club or User ID");
        }
        
        clubFavorite.setClub(club);
        clubFavorite.setUser(user);

        ClubFavorite savedFavorite = cfService.saveClubFavorite(clubFavorite);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFavorite);
	}

	@GetMapping
	public List<ClubFavorite> getAllClubFavorite() {
		return cfService.findAllClubFavorite();
	}

	@GetMapping("/{clubFavoriteId}")
	public ResponseEntity<?> getClubFavorite(@PathVariable ClubFavoriteId clubFavoriteId) {
		ClubFavorite existingCf = cfService.findClubFavoriteById(clubFavoriteId);

		if (existingCf != null) {
			return ResponseEntity.ok(existingCf);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");

	}

	@DeleteMapping("/{clubFavoriteId}")
	public ResponseEntity<String> deleteClubFavorite(@PathVariable ClubFavoriteId clubFavoriteId) {
		ClubFavorite existingCf = cfService.findClubFavoriteById(clubFavoriteId);

		if (existingCf == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
		}
		cfService.deleteClubFavoriteById(clubFavoriteId);
		return ResponseEntity.status(HttpStatus.OK).body("刪除成功");

	}
	
	//用userId查詢
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getClubFavoriteByUserId(@PathVariable Integer userId) {
		List<ClubFavorite> existingCf = cfService.findClubFavoriteByUserId(userId);

		if (existingCf != null) {
			return ResponseEntity.ok(existingCf);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");

	}
}
