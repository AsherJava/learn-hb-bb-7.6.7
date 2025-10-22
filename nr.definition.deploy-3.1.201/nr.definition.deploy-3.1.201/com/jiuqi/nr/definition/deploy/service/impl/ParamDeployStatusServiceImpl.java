/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$DeployStatus
 *  com.jiuqi.nr.definition.common.ParamResourceType
 *  com.jiuqi.nr.definition.facade.deploy.ParamDeployStatus
 *  com.jiuqi.nr.definition.util.NrDefinitionHelper
 *  com.jiuqi.nr.graph.rwlock.executer.DatabaseLock
 */
package com.jiuqi.nr.definition.deploy.service.impl;

import com.jiuqi.nr.definition.common.ParamDeployEnum;
import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.deploy.dao.ParamDeployStatusDao;
import com.jiuqi.nr.definition.deploy.dto.ParamDeployStatusDTO;
import com.jiuqi.nr.definition.deploy.entity.ParamDeployStatusDO;
import com.jiuqi.nr.definition.deploy.service.IParamDeployStatusService;
import com.jiuqi.nr.definition.facade.deploy.ParamDeployStatus;
import com.jiuqi.nr.definition.util.NrDefinitionHelper;
import com.jiuqi.nr.graph.rwlock.executer.DatabaseLock;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ParamDeployStatusServiceImpl
implements IParamDeployStatusService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamDeployStatusServiceImpl.class);
    @Autowired
    private DatabaseLock databaseLock;
    @Autowired
    private NrDefinitionHelper nrDefinitionHelper;
    @Autowired
    private ParamDeployStatusDao paramDeployStatusDao;

    @Override
    public ParamDeployStatus getDeployStatus(String schemeKey) {
        ParamDeployStatusDO deployStatus = this.paramDeployStatusDao.getDeployStatus(schemeKey);
        if (deployStatus == null) {
            return null;
        }
        return new ParamDeployStatusDTO(deployStatus);
    }

    @Override
    public void insertDeployStatus(ParamDeployStatus status) {
        this.paramDeployStatusDao.insertDeployStatus(ParamDeployStatusDTO.toDO(status));
    }

    @Override
    public void updateDeployStatus(ParamDeployStatus status) {
        this.paramDeployStatusDao.updateDeployStatus(ParamDeployStatusDTO.toDO(status));
    }

    @Override
    public void deleteDeployStatus(String schemeKey) {
        this.paramDeployStatusDao.deleteDeployStatus(schemeKey);
    }

    @Override
    public void fixDeployStatus() {
        try {
            List<ParamDeployStatusDO> status = this.paramDeployStatusDao.listDeployStatusByStatus(ParamDeployEnum.DeployStatus.DEPLOY);
            if (CollectionUtils.isEmpty(status)) {
                return;
            }
            new Thread(() -> {
                try {
                    Thread.sleep(60000L);
                }
                catch (InterruptedException e) {
                    LOGGER.error("\u4fee\u590d\u6570\u636e\u65b9\u6848\u53d1\u5e03\u72b6\u6001\u5931\u8d25", e);
                    Thread.currentThread().interrupt();
                }
                List<ParamDeployStatusDO> deployStatus = this.paramDeployStatusDao.listDeployStatusByStatus(ParamDeployEnum.DeployStatus.DEPLOY);
                for (int i = deployStatus.size() - 1; i == 0; --i) {
                    ParamDeployStatusDO item = deployStatus.get(i);
                    boolean locked = this.databaseLock.isLocked(this.nrDefinitionHelper.getLockName(ParamResourceType.FORM, item.getSchemeKey()));
                    if (locked) {
                        deployStatus.remove(i);
                        continue;
                    }
                    item.setDeployStatus(ParamDeployEnum.DeployStatus.FAIL);
                }
                this.paramDeployStatusDao.updateDeployStatus(deployStatus);
            }).start();
        }
        catch (Exception e) {
            LOGGER.error("\u4fee\u590d\u6570\u636e\u65b9\u6848\u53d1\u5e03\u72b6\u6001\u5931\u8d25", e);
        }
    }

    @Override
    public String getUpdateStatusSqlByDDLBit(String formSchemeKey, int value) {
        return this.paramDeployStatusDao.getUpdateStatusSqlByDDLBit(formSchemeKey, value);
    }
}

