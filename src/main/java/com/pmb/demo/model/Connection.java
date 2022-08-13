/*package com.pmb.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "connection")
public class Connection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "connection_id")
	private Long connectionId;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserAccount userAccountId;
	
	@ManyToOne
	@JoinColumn(name = "friend_id")
	private UserAccount userFriendId;
	
	
	public Connection() {
		super();
	}

	public Connection(UserAccount userAccountId, UserAccount userFriendId) {
		super();
		this.userAccountId = userAccountId;
		this.userFriendId = userFriendId;
	}

	public Connection(Long connectionId, UserAccount userAccountId, UserAccount userFriendId) {
		super();
		this.connectionId = connectionId;
		this.userAccountId = userAccountId;
		this.userFriendId = userFriendId;
	}

	public Long getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(Long id) {
		connectionId = id;
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
*/