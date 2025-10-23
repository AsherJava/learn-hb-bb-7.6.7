/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.vo;

import com.jiuqi.nr.transmission.data.dto.SyncHistoryDTO;
import java.util.ArrayList;
import java.util.List;

public class SyncHistoryVO
extends SyncHistoryDTO {
    public static SyncHistoryVO getVOInstance(SyncHistoryDTO syncHistoryDTO) {
        SyncHistoryVO syncHistoryVO = new SyncHistoryVO();
        if (syncHistoryDTO != null) {
            syncHistoryVO.setKey(syncHistoryDTO.getKey());
            syncHistoryVO.setSchemeKey(syncHistoryDTO.getSchemeKey());
            syncHistoryVO.setStatus(syncHistoryDTO.getStatus());
            syncHistoryVO.setDetail(syncHistoryDTO.getDetail());
            syncHistoryVO.setSyncSchemeParamDO(syncHistoryDTO.getSyncSchemeParamDO());
            syncHistoryVO.setSyncTimes(syncHistoryDTO.getSyncTimes());
            syncHistoryVO.setStartTime(syncHistoryDTO.getStartTime());
            syncHistoryVO.setEndTime(syncHistoryDTO.getEndTime());
            syncHistoryVO.setFileKey(syncHistoryDTO.getFileKey());
            syncHistoryVO.setUserId(syncHistoryDTO.getUserId());
            syncHistoryVO.setFinishEntity(syncHistoryDTO.getFinishEntity());
            syncHistoryVO.setType(syncHistoryDTO.getType());
            syncHistoryVO.setInstanceId(syncHistoryDTO.getInstanceId());
            syncHistoryVO.setRangedetailResult(syncHistoryDTO.getRangedetailResult());
        }
        return syncHistoryVO;
    }

    public static List<SyncHistoryVO> toVOListInstance(List<SyncHistoryDTO> dataDTOs) {
        ArrayList<SyncHistoryVO> vos = new ArrayList<SyncHistoryVO>(dataDTOs.size());
        for (SyncHistoryDTO dataDO : dataDTOs) {
            SyncHistoryVO vo = SyncHistoryVO.getVOInstance(dataDO);
            vos.add(vo);
        }
        return vos;
    }

    public static SyncHistoryDTO syncHistoryVoToDto(SyncHistoryVO syncHistoryVO) {
        SyncHistoryDTO syncHistoryDTO = new SyncHistoryDTO();
        if (syncHistoryVO != null) {
            syncHistoryDTO.setKey(syncHistoryVO.getKey());
            syncHistoryDTO.setSchemeKey(syncHistoryVO.getSchemeKey());
            syncHistoryDTO.setStatus(syncHistoryVO.getStatus());
            syncHistoryDTO.setDetail(syncHistoryVO.getDetail());
            syncHistoryDTO.setSyncSchemeParamDO(syncHistoryVO.getSyncSchemeParamDO());
            syncHistoryDTO.setSyncTimes(syncHistoryVO.getSyncTimes());
            syncHistoryDTO.setStartTime(syncHistoryVO.getStartTime());
            syncHistoryDTO.setEndTime(syncHistoryVO.getEndTime());
            syncHistoryDTO.setFileKey(syncHistoryVO.getFileKey());
            syncHistoryDTO.setUserId(syncHistoryVO.getUserId());
            syncHistoryDTO.setFinishEntity(syncHistoryVO.getFinishEntity());
            syncHistoryDTO.setType(syncHistoryVO.getType());
            syncHistoryDTO.setInstanceId(syncHistoryVO.getInstanceId());
            syncHistoryDTO.setResult(syncHistoryVO.getResult());
            syncHistoryDTO.setRangedetailResult(syncHistoryVO.getRangedetailResult());
        }
        return syncHistoryDTO;
    }
}

