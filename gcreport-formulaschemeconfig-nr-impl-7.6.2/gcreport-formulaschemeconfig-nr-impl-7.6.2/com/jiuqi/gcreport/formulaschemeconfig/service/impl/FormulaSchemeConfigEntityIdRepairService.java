/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.controller.event.FormSchemeUpdateEvent
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.formulaschemeconfig.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.formulaschemeconfig.dao.FormulaSchemeConfigDao;
import com.jiuqi.gcreport.formulaschemeconfig.utils.NrFormulaSchemeConfigUtils;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.controller.event.FormSchemeUpdateEvent;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormulaSchemeConfigEntityIdRepairService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormulaSchemeConfigEntityIdRepairService.class);
    @Autowired
    private FormulaSchemeConfigDao formulaSchemeConfigDao;

    @Async
    @Transactional(rollbackFor={Exception.class})
    public void doRepair(String username, FormSchemeUpdateEvent event) {
        LOGGER.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1\u5f00\u59cb\u6267\u884c\u4fee\u590d\u903b\u8f91============================");
        try {
            Thread.sleep(1000L);
        }
        catch (InterruptedException e) {
            LOGGER.error("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\uff0c\u5f02\u6b65\u4efb\u52a1\u4fee\u590d\u903b\u8f91\u7b49\u5f85\u8fc7\u7a0b\u4e2d\u51fa\u73b0\u9519\u8bef============================");
            Thread.currentThread().interrupt();
            return;
        }
        try {
            LOGGER.debug("\u6ce8\u5165\u53d6\u6570\u6267\u884c\u4e0a\u4e0b\u6587\u7528\u6237", (Object)username);
            NpContext npContext = ((NpApplication)ApplicationContextRegister.getBean(NpApplication.class)).getTempContext(username);
            NpContextHolder.setContext((NpContext)npContext);
            LOGGER.debug("\u6ce8\u5165\u53d6\u6570\u6267\u884c\u4e0a\u4e0b\u6587\u7528\u6237\u5b8c\u6210", (Object)npContext);
        }
        catch (Exception e) {
            LOGGER.error("\u6ce8\u5165\u53d6\u6570\u6267\u884c\u4e0a\u4e0b\u6587\u7528\u6237\u51fa\u73b0\u9519\u8bef\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
        DesignTaskDefine taskDefine = event.getTaskDefine();
        boolean enableTaskMultiOrg = NrFormulaSchemeConfigUtils.enableTaskMultiOrg(taskDefine.getKey());
        if (enableTaskMultiOrg) {
            LOGGER.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\uff0c\u5f02\u6b65\u4efb\u52a1\u4fee\u590d\u4efb\u52a1\u3010{}|{}\u3011\u4e3a\u591a\u53e3\u5f84\u4e0d\u6ee1\u8db3\u4fee\u590d\u6761\u4ef6\uff0c\u81ea\u52a8\u8df3\u8fc7============================", (Object)taskDefine.getKey(), (Object)taskDefine.getTitle());
            return;
        }
        this.executeRepair(taskDefine);
    }

    public void executeRepair(DesignTaskDefine taskDefine) {
        List<String> entityIdList = this.formulaSchemeConfigDao.selectEntityIdByTask(taskDefine.getKey());
        if (CollectionUtils.isEmpty(entityIdList)) {
            return;
        }
        if (entityIdList.size() > 1) {
            LOGGER.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\uff0c\u5f02\u6b65\u4efb\u52a1\u4fee\u590d\u4efb\u52a1\u3010{}|{}\u3011,\u5df2\u5b58\u5728\u591a\u4e2a\u53e3\u5f84\u3010{}\u3011\u7684\u914d\u7f6e\u4e0d\u6ee1\u8db3\u4fee\u590d\u6761\u4ef6\uff0c\u81ea\u52a8\u8df3\u8fc7============================", taskDefine.getKey(), taskDefine.getTitle(), entityIdList);
            return;
        }
        String entityId = taskDefine.getDw();
        if (entityId.equals(entityIdList.get(0))) {
            LOGGER.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\uff0c\u5f02\u6b65\u4efb\u52a1\u4fee\u590d\u4efb\u52a1\u3010{}|{}\u3011,\u5df2\u5b58\u5728\u7684\u53e3\u5f84\u3010{}\u3011\u548c\u65b0\u914d\u7f6e\u3010{}\u3011\u4e00\u81f4\uff0c\u4e0d\u518d\u89e6\u53d1\u4fee\u590d\uff0c\u81ea\u52a8\u8df3\u8fc7============================", taskDefine.getKey(), taskDefine.getTitle(), entityIdList, entityId);
            return;
        }
        int ct = this.formulaSchemeConfigDao.batchUpdateEntityId(taskDefine.getKey(), entityIdList.get(0), entityId);
        LOGGER.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\uff0c\u5f02\u6b65\u4efb\u52a1\u4fee\u590d\u4efb\u52a1\u3010{}|{}\u3011,\u53e3\u5f84\u4fee\u590d\u4e3a\u3010{}\u3011\u5171\u4fee\u590d{}\u6761\u6570\u636e\uff0c\u81ea\u52a8\u8df3\u8fc7============================", taskDefine.getKey(), taskDefine.getTitle(), entityId, ct);
    }
}

