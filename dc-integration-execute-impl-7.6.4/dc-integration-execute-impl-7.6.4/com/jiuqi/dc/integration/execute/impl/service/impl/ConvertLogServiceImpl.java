/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.dc.integration.execute.client.dto.ConvertLogDTO
 *  com.jiuqi.dc.integration.execute.client.vo.ConvertLogVO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.ModelTypeEnum
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  org.springframework.transaction.annotation.Propagation
 */
package com.jiuqi.dc.integration.execute.impl.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.dc.integration.execute.client.dto.ConvertLogDTO;
import com.jiuqi.dc.integration.execute.client.vo.ConvertLogVO;
import com.jiuqi.dc.integration.execute.impl.dao.ConvertLogDao;
import com.jiuqi.dc.integration.execute.impl.domain.ConvertLogDO;
import com.jiuqi.dc.integration.execute.impl.service.ConvertLogService;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.ModelTypeEnum;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

@Service
public class ConvertLogServiceImpl
implements ConvertLogService {
    @Autowired
    private ConvertLogDao convLogDao;
    @Autowired
    private DataSchemeService dataSchemeService;

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void insertLog(ConvertLogDO log) {
        this.convLogDao.insert(log);
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void batchInsertLog(List<ConvertLogDO> logs) {
        this.convLogDao.batchInsert(logs);
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public ConvertLogDTO queryDataConvertLog(String dataSchemeCode, int page, int pageSize) {
        DataSchemeDTO dataSchemeDTO = this.dataSchemeService.findByCode(dataSchemeCode);
        ConvertLogDTO convertLogDTO = new ConvertLogDTO();
        convertLogDTO.setTotal(this.convLogDao.queryCount(dataSchemeCode));
        if (convertLogDTO.getTotal() > 0) {
            convertLogDTO.setLogs(this.convLogDao.queryWithTaskLog(dataSchemeCode, page, pageSize));
            for (ConvertLogVO convertLogVO : convertLogDTO.getLogs()) {
                convertLogVO.setSchemeType(ModelTypeEnum.valueOf((String)convertLogVO.getSchemeType()).getName());
                convertLogVO.setDataSchemeName(dataSchemeDTO.getName());
            }
        }
        return convertLogDTO;
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public ConvertLogVO getExecuteById(ConvertLogVO convertLogVO) {
        ConvertLogVO logVO = this.convLogDao.getConvertLogById(convertLogVO.getId());
        DataSchemeDTO dataSchemeDTO = this.dataSchemeService.findByCode(logVO.getDataSchemeCode());
        logVO.setDataSchemeName(dataSchemeDTO.getName());
        logVO.setSchemeType(ModelTypeEnum.valueOf((String)logVO.getSchemeType()).getName());
        return logVO;
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public Boolean batchDeleteById(List<String> deleteIdList) {
        if (deleteIdList == null || deleteIdList.size() == 0) {
            throw new BusinessRuntimeException("\u672a\u9009\u62e9\u8981\u5220\u9664\u7684\u6570\u636e");
        }
        return this.convLogDao.batchDeleteById(deleteIdList) > 0;
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void updateExecuteById(String id) {
        this.convLogDao.updateExecuteById(id);
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void updateRunnerIdById(String id, String runnerId) {
        this.convLogDao.updateRunnerIdById(id, runnerId);
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void updateMessageAndState(String id, String message, int state) {
        this.convLogDao.updateMessageAndState(id, message, state);
    }
}

