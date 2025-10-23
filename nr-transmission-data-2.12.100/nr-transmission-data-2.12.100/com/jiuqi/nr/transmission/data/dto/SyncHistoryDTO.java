/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nr.transmission.data.dto;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.transmission.data.api.ITransmissionDataGather;
import com.jiuqi.nr.transmission.data.domain.SyncHistoryDO;
import com.jiuqi.nr.transmission.data.internal.file.BusinessData;
import com.jiuqi.nr.transmission.data.internal.file.FormulaCheck;
import java.util.ArrayList;
import java.util.List;

public class SyncHistoryDTO
extends SyncHistoryDO {
    private List<String> rangedetailResult;

    public List<String> getRangedetailResult() {
        this.rangedetailResult = new ArrayList<String>();
        ITransmissionDataGather bean1 = (ITransmissionDataGather)SpringBeanUtils.getBean(BusinessData.class);
        ITransmissionDataGather bean2 = (ITransmissionDataGather)SpringBeanUtils.getBean(FormulaCheck.class);
        this.rangedetailResult.add(bean1.getTitle());
        this.rangedetailResult.add(bean2.getTitle());
        return this.rangedetailResult;
    }

    public void setRangedetailResult(List<String> rangedetailResult) {
        this.rangedetailResult = rangedetailResult;
    }

    public static SyncHistoryDTO getInstance(SyncHistoryDO syncSchemeDO) {
        SyncHistoryDTO syncHistoryDTO = new SyncHistoryDTO();
        if (syncSchemeDO != null) {
            syncHistoryDTO.setKey(syncSchemeDO.getKey());
            syncHistoryDTO.setSchemeKey(syncSchemeDO.getSchemeKey());
            syncHistoryDTO.setStatus(syncSchemeDO.getStatus());
            syncHistoryDTO.setDetail(syncSchemeDO.getDetail());
            syncHistoryDTO.setSyncSchemeParamDO(syncSchemeDO.getSyncSchemeParamDO());
            syncHistoryDTO.setSyncTimes(syncSchemeDO.getSyncTimes());
            syncHistoryDTO.setStartTime(syncSchemeDO.getStartTime());
            syncHistoryDTO.setEndTime(syncSchemeDO.getEndTime());
            syncHistoryDTO.setFileKey(syncSchemeDO.getFileKey());
            syncHistoryDTO.setUserId(syncSchemeDO.getUserId());
            syncHistoryDTO.setFinishEntity(syncSchemeDO.getFinishEntity());
            syncHistoryDTO.setType(syncSchemeDO.getType());
            syncHistoryDTO.setInstanceId(syncSchemeDO.getInstanceId());
        }
        return syncHistoryDTO;
    }

    public static List<SyncHistoryDTO> toListInstance(List<SyncHistoryDO> syncHistoryDOS) {
        ArrayList<SyncHistoryDTO> dtos = new ArrayList<SyncHistoryDTO>(syncHistoryDOS.size());
        for (SyncHistoryDO syncHistoryDO : syncHistoryDOS) {
            SyncHistoryDTO syncHistoryDTO = SyncHistoryDTO.getInstance(syncHistoryDO);
            dtos.add(syncHistoryDTO);
        }
        return dtos;
    }
}

