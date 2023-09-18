package com.emotionbank.business.domain.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OAuthTokenDto {
	@Getter
	public static class Request {
		private final String grantType = "authorization_code";
		private final String clientId;
		private final String clientSecret;
		private final String redirectUri;
		private final String code;

		@Builder
		public Request(String clientId, String clientSecret, String redirectUri, String code) {
			this.clientId = clientId;
			this.clientSecret = clientSecret;
			this.redirectUri = redirectUri;
			this.code = code;
		}

		public static Request of(String clientId, String clientSecret, String redirectUri, String code) {
			return Request.builder()
				.clientId(clientId)
				.clientSecret(clientSecret)
				.redirectUri(redirectUri)
				.code(code)
				.build();
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Response {
		private String tokenType;
		private String accessToken;
		private Integer expiresIn;
		private String scope;

		@Builder
		public Response(String tokenType, String accessToken, Integer expiresIn, String scope) {
			this.tokenType = tokenType;
			this.accessToken = accessToken;
			this.expiresIn = expiresIn;
			this.scope = scope;
		}
	}
}
