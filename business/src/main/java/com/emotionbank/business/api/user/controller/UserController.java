package com.emotionbank.business.api.user.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import com.emotionbank.business.domain.notification.constant.NotificationType;
import com.emotionbank.business.domain.notification.dto.PersonalNotificationDto;
import com.emotionbank.business.domain.notification.service.NotificationService;
import com.google.firebase.messaging.FirebaseMessagingException;

import com.emotionbank.business.domain.user.dto.ReportDto;
import com.google.firebase.database.core.Repo;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emotionbank.business.api.user.dto.UserFeedDto;
import com.emotionbank.business.api.user.dto.UserFollowsDto;
import com.emotionbank.business.api.user.dto.UserInformationDto;
import com.emotionbank.business.api.user.dto.UserMyProfileDto;
import com.emotionbank.business.api.user.dto.UserNicknameCheckDto;
import com.emotionbank.business.api.user.dto.UserOtherProfileDto;
import com.emotionbank.business.api.user.dto.UserReportDto;
import com.emotionbank.business.api.user.dto.UserSearchDto;
import com.emotionbank.business.api.user.dto.UserUpdateDto;
import com.emotionbank.business.domain.user.dto.FeedsDto;
import com.emotionbank.business.domain.user.dto.FollowDto;
import com.emotionbank.business.domain.user.dto.UserDto;
import com.emotionbank.business.domain.user.service.UserService;
import com.emotionbank.business.global.jwt.annotation.UserInfo;
import com.emotionbank.business.global.jwt.dto.UserInfoDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final NotificationService notificationService;
	private final int PAGESIZE = 12;

	@GetMapping("/me")
	public ResponseEntity<UserInformationDto.Response> myInfo(@UserInfo UserInfoDto userInfoDto) {
		long userId = userInfoDto.getUserId();
		UserDto userInfo = userService.getUserInfo(userId);
		return ResponseEntity.ok(UserInformationDto.Response.from(userInfo));
	}

	@PatchMapping("/me")
	public ResponseEntity updateUser(@RequestBody UserUpdateDto.Request request, @UserInfo UserInfoDto userInfoDto) {
		long userId = userInfoDto.getUserId();
		UserDto userDto = UserDto.of(userId, request.getNickname());
		userService.updateUser(userDto);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/info/me")
	public ResponseEntity<UserMyProfileDto.Response> getMyProfile(@UserInfo UserInfoDto userInfoDto) {
		UserDto myProfile = userService.getMyProfile(userInfoDto.getUserId());
		return ResponseEntity.ok(UserMyProfileDto.Response.from(myProfile));
	}

	@GetMapping("/info/{userId}")
	public ResponseEntity<?> getOtherProfile(@PathVariable long userId, @UserInfo UserInfoDto userInfoDto) {
		UserDto otherProfile = userService.getOtherProfile(userId);
		return ResponseEntity.ok(
			UserOtherProfileDto.Response.of(otherProfile, userService.isFollow(userInfoDto.getUserId(), userId)));
	}

	@PostMapping("/check")
	public ResponseEntity<UserNicknameCheckDto.Response> checkDuplicateNickname(
		@RequestBody UserNicknameCheckDto.Request request) {
		return ResponseEntity.ok(
			UserNicknameCheckDto.Response.of(userService.checkDuplicateNickname(request.getNickname())));
	}

	@GetMapping("/search")
	public ResponseEntity<UserSearchDto.Response> searchUser(@RequestParam String nickname, Pageable pageable) {
		List<UserDto> userDtos = userService.searchUser(nickname, pageable);
		UserSearchDto.Response response = UserSearchDto.Response.from(userDtos);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/follow/{userId}")
	public ResponseEntity<?> followUser(@PathVariable Long followeeId, @UserInfo UserInfoDto userInfoDto) throws
		FirebaseMessagingException {
		Long userId = userInfoDto.getUserId();
		userService.followUser(FollowDto.of(userId, followeeId));
		notificationService.followNotification(
			PersonalNotificationDto.of(userId, followeeId, userService.getNickname(userId)));
		return ResponseEntity.ok().build();
	}

	@GetMapping("/followee/{userId}")
	public ResponseEntity<UserFollowsDto.Response> getFollowees(@PathVariable Long userId) {
		List<UserDto> followees = userService.getFollowees(userId);
		UserFollowsDto.Response response = UserFollowsDto.Response.from(followees);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/follower/{userId}")
	public ResponseEntity<UserFollowsDto.Response> getFollowers(@PathVariable Long userId) {
		List<UserDto> followers = userService.getFollowers(userId);
		UserFollowsDto.Response response = UserFollowsDto.Response.from(followers);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/feed")
	public ResponseEntity<UserFeedDto.Response> getFeed(@UserInfo UserInfoDto userInfoDto, @RequestParam int page) {
		Pageable pageable = PageRequest.of(page, PAGESIZE);
		FeedsDto feed = userService.getFeed(userInfoDto.getUserId(), pageable);
		return ResponseEntity.ok(UserFeedDto.Response.from(feed));
	}

	@GetMapping("/report/{userId}")
	public ResponseEntity<UserReportDto.Response> getReport(@PathVariable Long userId) {
		ReportDto reportDto = userService.getReport(userId);
		return ResponseEntity.ok(UserReportDto.Response.from(reportDto));
	}
}
