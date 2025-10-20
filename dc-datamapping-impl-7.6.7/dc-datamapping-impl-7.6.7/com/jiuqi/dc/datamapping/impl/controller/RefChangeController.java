/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.datamapping.client.RefChangeClient
 *  com.jiuqi.dc.datamapping.client.dto.RefChangeDTO
 *  com.jiuqi.dc.datamapping.client.vo.DataRefSaveVO
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.datamapping.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.datamapping.client.RefChangeClient;
import com.jiuqi.dc.datamapping.client.dto.RefChangeDTO;
import com.jiuqi.dc.datamapping.client.vo.DataRefSaveVO;
import com.jiuqi.dc.datamapping.impl.enums.RefHandleStatus;
import com.jiuqi.dc.datamapping.impl.service.RefChangeService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefChangeController
implements RefChangeClient {
    @Autowired
    private RefChangeService refChangeService;

    public BusinessResponseEntity<DataRefSaveVO> dataRefChange(@RequestBody RefChangeDTO changeParam) {
        return BusinessResponseEntity.ok((Object)this.refChangeService.handleRefChange(changeParam));
    }

    public BusinessResponseEntity<DataRefSaveVO> dataRefChange(@RequestBody RefChangeDTO[] changeParam) {
        DataRefSaveVO saveVO = new DataRefSaveVO();
        for (RefChangeDTO refChangeDTO : changeParam) {
            if (RefHandleStatus.PENDING.getCode().equals(refChangeDTO.getRefData().getHandleStatus())) {
                saveVO.setErrorMessage((Map)CollectionUtils.newHashMap());
                this.refChangeService.deletePendingData(refChangeDTO.getTableName(), refChangeDTO.getRefData().getId());
                continue;
            }
            saveVO = this.refChangeService.handleRefChange(refChangeDTO);
        }
        return BusinessResponseEntity.ok((Object)saveVO);
    }
}

