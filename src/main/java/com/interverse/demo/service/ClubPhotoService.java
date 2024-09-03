package com.interverse.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.interverse.demo.model.Club;
import com.interverse.demo.model.ClubPhoto;
import com.interverse.demo.model.ClubPhotoRepository;
import com.interverse.demo.model.ClubRepository;
import com.interverse.demo.model.Product;
import com.interverse.demo.model.ProductPhotos;
import com.interverse.demo.model.User;
import com.interverse.demo.model.UserRepository;

@Service
public class ClubPhotoService {

	@Value("${upload.clubphoto.dir}")
	private String uploadDir;

	@Autowired
	private ClubPhotoRepository cpRepo;

	@Autowired
	private ClubRepository cRepo;

	@Autowired
	private UserRepository uRepo;

	public ClubPhoto saveClubPhoto(ClubPhoto clubPhoto) {
		Club club = clubPhoto.getClub();
		User uploaderId = clubPhoto.getUploaderId();
		if (cRepo.existsById(club.getId()) && uRepo.existsById(uploaderId.getId())) {
			return cpRepo.save(clubPhoto);
		}
		throw new IllegalStateException("Club or User is not exists.");
	}

	// 尋找club中所有照片
	public List<ClubPhoto> getAllPhotosByClubId(Integer clubId) {
		return cpRepo.findByClubId(clubId);
	}

	// user從club中刪除自己上傳的照片
	public void deletePhotoIfOwner(Integer id, Integer uploaderId) throws IOException {
		ClubPhoto photo = cpRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Photo not found with ID: " + id));

		// 檢查上傳者ID是否與要求刪除的使用者ID相同
		if (!photo.getUploaderId().getId().equals(uploaderId)) {
			throw new SecurityException("You do not have permission to delete this photo.");
		}
		Path filePath = Paths.get(photo.getPhoto());
		Files.deleteIfExists(filePath);

		cpRepo.deleteById(id);
	}

	@Transactional
	public ClubPhoto createClubPhoto(MultipartFile file, Integer clubId, Integer uploaderId) throws IOException {
		// 获取 Club 对象
		Club club = cRepo.findById(clubId).orElseThrow(() -> new RuntimeException("Club not found with id: " + clubId));

		// 获取上传者 User 对象
		User uploader = uRepo.findById(uploaderId)
				.orElseThrow(() -> new RuntimeException("Uploader not found with id: " + uploaderId));

		// 获取上传文件的名字并去除路径中的不安全字符
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		// 生成唯一文件名，防止名字冲突
		String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

		// 将路径转换为 Path 类的对象
		Path uploadPath = Paths.get(uploadDir);

		// 判断目录是否存在，不存在的话创建目录
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		// 生成完整的路径
		Path filePath = uploadPath.resolve(uniqueFileName);

		// 将文件写入指定位置
		file.transferTo(filePath.toFile());

		// 存入 ClubPhoto 实体
		ClubPhoto clubPhoto = new ClubPhoto();
		clubPhoto.setPhoto(filePath.toString());
		clubPhoto.setClub(club);
		clubPhoto.setUploaderId(uploader); // 设置上传者 ID

		return cpRepo.save(clubPhoto);
	}

	public ClubPhoto getClubPhoto(Integer clubId, Integer photoId) {
		Club club = cRepo.findById(clubId).orElseThrow(() -> new RuntimeException("Club not found with id: " + clubId));

		return cpRepo.findByClubAndId(club, photoId).orElseThrow(
				() -> new RuntimeException("ClubPhoto not found with clubtId: " + clubId + " and photoId: " + photoId));
	}
}
