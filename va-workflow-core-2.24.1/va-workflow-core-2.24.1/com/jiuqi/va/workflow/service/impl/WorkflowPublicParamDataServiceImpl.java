/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDataDTO
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDataDTO;
import com.jiuqi.va.workflow.dao.WorkflowPublicParamDataDao;
import com.jiuqi.va.workflow.service.WorkflowPublicParamDataService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WorkflowPublicParamDataServiceImpl
implements WorkflowPublicParamDataService {
    @Autowired
    private WorkflowPublicParamDataDao workflowPublicParamDataDao;

    @Override
    public void updatePublicParamData(String data, UUID id) {
        WorkflowPublicParamDataDTO dataDTO = new WorkflowPublicParamDataDTO();
        if (StringUtils.hasText(data)) {
            dataDTO.setId(id);
            int count = this.workflowPublicParamDataDao.selectCount(dataDTO);
            dataDTO.setParamdata(data);
            if (count == 0) {
                this.workflowPublicParamDataDao.insert(dataDTO);
            } else {
                this.workflowPublicParamDataDao.updateByPrimaryKey(dataDTO);
            }
        } else {
            dataDTO.setId(id);
            this.workflowPublicParamDataDao.delete(dataDTO);
        }
    }
}

