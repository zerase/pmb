package com.pmb.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "connection")
public class Connection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "connection_id")
	private Long ConnectionId;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserAccount userAccountId;
	
	@ManyToOne
	@JoinColumn(name = "friend_id")
	private UserAccount userFriendId;

	
	/* Getters and setters */
	
	public Long getConnectionId() {
		return ConnectionId;
	}

	public void setConnectionId(Long connectionId) {
		ConnectionId = connectionId;
	}

	public UserAccount getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(UserAccount userAccountId) {
		this.userAccountId = userAccountId;
	}

	public UserAccount getUserFriendId() {
		return userFriendId;
	}

	public void setUserFriendId(UserAccount userFriendId) {
		this.userFriendId = userFriendId;
	}
	
}
