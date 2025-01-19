package com.med.backend.service;

import com.med.backend.model.enums.Role;
import com.med.backend.model.request.UserReq;
import com.med.backend.model.response.UserRes;

import java.util.UUID;

public interface UserService extends BaseService<UserReq, UUID, UserRes> {

    UserRes updateRole(UUID id, Role role);
}
