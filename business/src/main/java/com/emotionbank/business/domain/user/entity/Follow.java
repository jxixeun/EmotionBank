package com.emotionbank.business.domain.user.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.emotionbank.business.domain.user.dto.FollowId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@IdClass(FollowId.class)
public class Follow implements Serializable {
	@Id
	@ManyToOne
	@JoinColumn(name = "follower")
	private User follower;

	@Id
	@ManyToOne
	@JoinColumn(name = "followee")
	private User followee;
}
