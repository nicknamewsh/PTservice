package com.pt.ssm.po;

import java.io.Serializable;
import java.util.List;

public class RoleMenu implements Serializable {
	private static final long serialVersionUID = 7801108216512223194L;
	private long id;
	private long roleid;
	private long menuid;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getRoleid() {
		return roleid;
	}
	public void setRoleid(long roleid) {
		this.roleid = roleid;
	}
	public long getMenuid() {
		return menuid;
	}
	public void setMenuid(long menuid) {
		this.menuid = menuid;
	}
	
}
