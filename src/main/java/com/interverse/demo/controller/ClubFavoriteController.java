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

import com.interverse.demo.dto.ClubFavoriteDTO;
import com.interverse.demo.model.Club;
import com.interverse.demo.model.ClubFavorite;
import com.interverse.demo.model.User;
import com.interverse.demo.service.ClubFavoriteService;
import com.interverse.demo.service.ClubService;
import com.interverse.demo.service.UserService;

@RestController
@RequestMapping("/clubFavorite")
public class ClubFavoriteController {

	@Autowired
	private ClubFavoriteService cfService;
	
	@Autowired
    private ClubService cService;

    @Autowired
    private UserService uService;
    
    // 轉換DTO
    public ClubFavoriteDTO convertToDTO(ClubFavorite clubFavorite) {
         ClubFavoriteDTO dto = new ClubFavoriteDTO();
         
         dto.setUserId(clubFavorite.getUser().getId());
         dto.setClubId(clubFavorite.getClub().getId());
         dto.setAdded(clubFavorite.getAdded());
         
         return dto;
    }
    
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
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedFavorite));
	}

//	@GetMapping
//	public List<ClubFavorite> getAllClubFavorite() {
//		return cfService.findAllClubFavorite();
//	}
//	
//	有誰收藏這個俱樂部(X) 
//	@GetMapping("/{clubFavoriteId}")
//	public ResponseEntity<?> getClubFavorite(@PathVariable ClubFavoriteId clubFavoriteId) {
//		ClubFavorite existingCf = cfService.findClubFavoriteById(clubFavoriteId);
//
//		if (existingCf != null) {
//			return ResponseEntity.ok(existingCf);
//		}
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
//
//	}

	@DeleteMapping("user/{userId}/club/{clubId}")
	public ResponseEntity<String> deleteClubFavorite(@PathVariable Integer userId, @PathVariable Integer clubId ) {
		try {
			cfService.deleteClubFavoriteFromUser(userId,clubId);
			return ResponseEntity.ok("社團收藏已成功刪除");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("錯誤");
		}
	}
//		 ClubFavoriteId clubFavoriteId = new ClubFavoriteId(userId,clubId);
//		 ClubFavorite existingCf = cfService.findClubFavoriteById(clubFavoriteId);
//
//		 if (existingCf == null) {
//		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
//		    }
//
//		    // 验证登录用户是否与请求的 userId 匹配
//		    // 如果你有登录用户信息，可以在这里进行进一步的身份验证
//		    // 如：if (!loggedInUserId.equals(userId)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("無權限");
//
//		    cfService.deleteClubFavoriteFromUser(userId,clubId);
//		    return ResponseEntity.status(HttpStatus.OK).body("刪除成功");
//	}
	
	//用戶收藏哪些俱樂部
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getClubFavoriteByUserId(@PathVariable Integer userId) {
		List<ClubFavorite> existingCf = cfService.findByClubFavoriteIdUserId(userId);
		List<ClubFavoriteDTO> clubFavoriteDTO = existingCf.stream().map(this::convertToDTO).collect(Collectors.toList());
		
		if (clubFavoriteDTO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(clubFavoriteDTO);
	}
}
