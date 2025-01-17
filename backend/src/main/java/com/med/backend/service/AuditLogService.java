package com.med.backend.service;

import com.med.backend.model.request.AuditLogReq;
import com.med.backend.model.response.AuditLogRes;

import java.util.List;
import java.util.UUID;

public interface AuditLogService extends BaseService<AuditLogReq, UUID, AuditLogRes> {

    List<AuditLogRes> getLogsByEmployeeId(UUID employeeId);
}
