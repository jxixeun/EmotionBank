package com.emotionbank.business.api.signup.dto;

import com.emotionbank.business.api.account.dto.CreateAccountDto;
import com.emotionbank.business.domain.signup.dto.SignUpUserDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RequestSignUpDto {
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Request {
		private String nickname;
		private String birthday;
		private String accountName;

		@Builder
		public Request(String nickname, String birthday, String accountName) {
			this.nickname = nickname;
			this.birthday = birthday;
			this.accountName = accountName;
		}
	}

	@Getter
	public static class Response {
		private final Long userId;
		private final String nickname;
		private final String birthday;
		private final String role;
		private final CreateAccountDto.Response createAccountDto;

		@Builder
		public Response(Long userId, String nickname, String birthday, String role,
			CreateAccountDto.Response createAccountDto) {
			this.userId = userId;
			this.nickname = nickname;
			this.birthday = birthday;
			this.role = role;
			this.createAccountDto = createAccountDto;
		}

		public static Response of(SignUpUserDto signUpUserDto, CreateAccountDto.Response createAccountDto) {
			return Response.builder()
				.userId(signUpUserDto.getUserId())
				.nickname(signUpUserDto.getNickname())
				.birthday(signUpUserDto.getBirthDay().toString())
				.role(signUpUserDto.getRole())
				.createAccountDto(createAccountDto)
				.build();
		}
	}
}
