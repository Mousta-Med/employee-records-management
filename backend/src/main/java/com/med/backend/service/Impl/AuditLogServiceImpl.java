package com.med.backend.service.Impl;

import com.med.backend.model.entity.AuditLog;
import com.med.backend.model.request.AuditLogReq;
import com.med.backend.model.response.AuditLogRes;
import com.med.backend.repository.AuditLogRepository;
import com.med.backend.service.AuditLogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    @Autowired
    private ModelMapper modelMapper;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public AuditLogRes save(AuditLogReq auditLogReq) {
        AuditLog auditLog = modelMapper.map(auditLogReq, AuditLog.class);
        return modelMapper.map(auditLogRepository.save(auditLog), AuditLogRes.class);
    }

    @Override
    public List<AuditLogRes> findAll() {
        return auditLogRepository.findAll().stream().map(AuditLog -> modelMapper.map(AuditLog, AuditLogRes.class)).collect(Collectors.toList());
    }

    @Override
    public List<AuditLogRes> getLogsByEmployeeId(UUID employeeId) {
//        return auditLogRepository.findAll().stream()
//                .filter(log -> log.getEmployeeId().equals(employeeId))
//                .map(AuditLog -> modelMapper.map(AuditLog, AuditLogRes.class))
//                .collect(Collectors.toList());
        return auditLogRepository.findAll().stream().map(AuditLog -> modelMapper.map(AuditLog, AuditLogRes.class)).collect(Collectors.toList());
    }

    @Override
    public AuditLogRes findOne(UUID uuid) {
        return null;
    }

    @Override
    public AuditLogRes update(UUID uuid, AuditLogReq auditLogReq) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }
}
