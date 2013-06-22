package com.googlecode.bootstrapx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.bootstrapx.dao.UserPrivilegeDAO;
import com.googlecode.bootstrapx.model.UserPrivilege;

@Service
public class UserPrivilegeService {

	@Autowired
	private UserPrivilegeDAO userPrivilegeDAO;

	public List<UserPrivilege> getListByUserId(int userId, int status) {
		return userPrivilegeDAO.getListByUserId(userId,status);
	}

	public int add(UserPrivilege userPriv) {
		return userPrivilegeDAO.add(userPriv);
	}
	public int remove(UserPrivilege userPriv) {
		return userPrivilegeDAO.remove(userPriv);
	}
}
