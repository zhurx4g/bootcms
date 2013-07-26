package com.googlecode.bootstrapx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.bootstrapx.dao.SystemLogDAO;
import com.googlecode.bootstrapx.model.SystemLog;

@Service
public class SystemLogService {
    @Autowired
    private SystemLogDAO systemLogDAO;

    public long log(SystemLog systemLog){
        return systemLogDAO.add(systemLog);
    }
    
    public List<SystemLog> select(int featureId, long recordId, int page, int size){
        return systemLogDAO.select(featureId, recordId, (page - 1) * size, size);
    }
    
    public int getCount(int featureId, long recordId){
        return systemLogDAO.getCount(featureId, recordId);
    }
}