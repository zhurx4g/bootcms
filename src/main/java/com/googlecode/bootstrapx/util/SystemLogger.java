package com.googlecode.bootstrapx.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import net.paoding.rose.web.Invocation;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.bootstrapx.model.SystemLog;
import com.googlecode.bootstrapx.service.SystemLogService;

public class SystemLogger {
    static final Logger LOGGER = LoggerFactory.getLogger(SystemLogger.class);

    private SystemLogService adminService;
    private int featureId;

    public static int SYSTEM_LOG_ID_YELLOWPAGE = 1;

    public static final int ADD = (0);
    public static final int UPDATE = (1);
    public static final int DELETE = (2);
    public static final int LOCK = (3);
    public static final int UNLOCK = (4);
    
    private SystemLogger(SystemLogService service){
        this.adminService = service;
    }
    public static SystemLogger getLogger(SystemLogService service, int featureId){
        SystemLogger logger = new SystemLogger(service);
        logger.featureId = featureId;
        return logger;
    }

    public long logDelete(long recordId, Invocation inv, String description){
        return log(recordId,inv, DELETE, description);
    }
    public long logUpdate(long recordId, Invocation inv, String description){
        return log(recordId,inv, UPDATE, description);
    }

    public long logAdd(long recordId, Invocation inv, String description){
        return log(recordId,inv, ADD, description);
    }
    public long logLock(long recordId, Invocation inv, String description){
        return log(recordId,inv, LOCK, description);
    }

    public long logUnlock(long recordId, Invocation inv, String description){
    	return log(recordId,inv, UNLOCK, description);
    }

    public long log(long recordId, Invocation inv,int action, String description){
        String ip = inv.getRequest().getHeader("X-forward");
        Object user = inv.getModel("loginUser");
        int userId = -1;;
        try {
            userId = NumberUtils.toInt(BeanUtils.getProperty(user, "id"));
        } catch (Exception e) {
            LOGGER.warn("", e);
        }
        return log(recordId, userId, ip, action, description);
    }
    public long log(long recordId,int userId,String ip,int action, String description){
        SystemLog systemLog = new SystemLog();
        systemLog.setFeatureId(featureId);
        systemLog.setRecordId(recordId);
        systemLog.setUserId(userId);
        systemLog.setIp(ip);
        systemLog.setAction(action);
        systemLog.setDescription(description);
        try {
            return adminService.log(systemLog);
        } catch (Exception e) {
            LOGGER.warn("", e);
        }
        return -1;
    }
}
