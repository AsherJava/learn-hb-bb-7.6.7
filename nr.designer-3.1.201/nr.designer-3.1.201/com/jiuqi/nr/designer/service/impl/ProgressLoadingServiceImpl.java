/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.definition.internal.dao.ProgressLoadingDao
 *  com.jiuqi.nr.definition.internal.impl.ProgressLoadingImpl
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.internal.dao.ProgressLoadingDao;
import com.jiuqi.nr.definition.internal.impl.ProgressLoadingImpl;
import com.jiuqi.nr.designer.service.ProgressLoadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgressLoadingServiceImpl
implements ProgressLoadingService {
    @Autowired
    ProgressLoadingDao progressLoadingDao;

    @Override
    public void publishSuccess(String taskId, String userId) throws JQException {
        ProgressLoadingImpl proImpl = new ProgressLoadingImpl();
        try {
            proImpl.setProgressId(UUIDUtils.getKey());
            proImpl.setTaskId(taskId);
            proImpl.setUserId(userId);
            proImpl.setOperType(1);
            proImpl.setOperStatus(0);
            proImpl.setInfo("");
            proImpl.setNeedShow(0);
            proImpl.setStackinfos("");
            this.progressLoadingDao.insertProgressLoading(proImpl);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_016, (Throwable)e);
        }
    }

    @Override
    public void publishFail(String taskId, String userId, String message, String stackinfos) throws JQException {
        ProgressLoadingImpl proImpl = new ProgressLoadingImpl();
        try {
            proImpl.setProgressId(UUIDUtils.getKey());
            proImpl.setTaskId(taskId);
            proImpl.setUserId(userId);
            proImpl.setOperType(1);
            proImpl.setOperStatus(1);
            proImpl.setInfo(message);
            proImpl.setNeedShow(1);
            proImpl.setStackinfos(stackinfos);
            this.progressLoadingDao.insertProgressLoading(proImpl);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_016, (Throwable)e);
        }
    }

    @Override
    public void publishWarring(String taskId, String userId, String message, String stackinfos) throws JQException {
        ProgressLoadingImpl proImpl = new ProgressLoadingImpl();
        try {
            proImpl.setProgressId(UUIDUtils.getKey());
            proImpl.setTaskId(taskId);
            proImpl.setUserId(userId);
            proImpl.setOperType(1);
            proImpl.setOperStatus(2);
            proImpl.setInfo(message);
            proImpl.setNeedShow(1);
            proImpl.setStackinfos(stackinfos);
            this.progressLoadingDao.insertProgressLoading(proImpl);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_016, (Throwable)e);
        }
    }

    @Override
    public ProgressLoadingImpl queryProgressLoading(String taskId) throws Exception {
        ProgressLoadingImpl impl = this.progressLoadingDao.queryProgressLoadingBytaskId(taskId);
        return impl;
    }

    @Override
    public void deleteProgressLoading(String taskId) throws Exception {
        ProgressLoadingImpl impl = this.progressLoadingDao.queryProgressLoadingBytaskId(taskId);
        if (impl != null) {
            this.progressLoadingDao.deleteByTaskId(taskId);
        }
    }
}

