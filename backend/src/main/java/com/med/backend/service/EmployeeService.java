package com.med.backend.service;

import com.med.backend.model.request.EmployeeReq;
import com.med.backend.model.response.EmployeeRes;

import java.util.UUID;

public interface EmployeeService extends BaseService<EmployeeReq, UUID, EmployeeRes> {
}
