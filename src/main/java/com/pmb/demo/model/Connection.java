package com.pmb.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "connection")
public class Connection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "connection_id")
	private Long id;
	
	@Column(name = "user_account_id")
	private Long userAccountId;
	
	@Column(name = "user_friend_id")
	private Long userFriendId;

	
	/* Getters and setters */
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}

	public Long getUserFriendId() {
		return userFriendId;
	}

	public void setUserFriendId(Long userFriendId) {
		this.userFriendId = userFriendId;
	}
	
}
