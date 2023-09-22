package com.emotionbank.business.domain.terms.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.emotionbank.business.domain.agreement.entity.Agreement;
import com.emotionbank.business.domain.terms.constant.Mandatory;
import com.emotionbank.business.domain.terms.dto.TermsDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Terms {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "terms_id")
	private Long termsId;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String content;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Mandatory mandatory;
	@Column(name = "create_time", nullable = false)
	private LocalDateTime createTime;
	@Column(name = "update_time")
	private LocalDateTime updateTime;
	@OneToOne(mappedBy = "terms")
	private Agreement agreement;

	@Builder
	public Terms(Long termsId, String title, String content, Mandatory mandatory, LocalDateTime createTime,
		LocalDateTime updateTime) {
		this.termsId = termsId;
		this.title = title;
		this.content = content;
		this.mandatory = mandatory;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public static Terms from(TermsDto termsDto) {
		return Terms.builder()
			.title(termsDto.getTitle())
			.content(termsDto.getContent())
			.mandatory(termsDto.getMandatory())
			.createTime(LocalDateTime.now())
			.build();
	}
}
