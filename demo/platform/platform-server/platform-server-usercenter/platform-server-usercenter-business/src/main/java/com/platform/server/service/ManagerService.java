package com.platform.server.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.server.dao.ManagerRepository;
import com.platform.server.protocol.usercenter.entity.Manager;
import com.platform.service.business.dao.BaseRepository;
import com.platform.service.business.service.BaseServiceImpl;

@Service
public class ManagerService extends BaseServiceImpl<Manager, Long> {
    @Autowired
    private ManagerRepository managerRepository;

    @Override
    protected BaseRepository<Manager, Long> getDao() {
        return managerRepository;
    }
}
