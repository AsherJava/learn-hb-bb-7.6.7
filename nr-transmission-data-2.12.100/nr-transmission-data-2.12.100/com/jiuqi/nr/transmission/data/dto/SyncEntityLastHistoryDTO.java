/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.dto;

import com.jiuqi.nr.transmission.data.domain.SyncEntityLastHistoryDO;
import java.util.ArrayList;
import java.util.List;

public class SyncEntityLastHistoryDTO
extends SyncEntityLastHistoryDO {
    public static SyncEntityLastHistoryDTO getInstance(SyncEntityLastHistoryDO syncEntityLastHistoryDO) {
        SyncEntityLastHistoryDTO syncEntityLastHistoryDTO = new SyncEntityLastHistoryDTO();
        if (syncEntityLastHistoryDO != null) {
            syncEntityLastHistoryDTO.setKey(syncEntityLastHistoryDO.getKey());
            syncEntityLastHistoryDTO.setTaskKey(syncEntityLastHistoryDO.getTaskKey());
            syncEntityLastHistoryDTO.setPeriod(syncEntityLastHistoryDO.getPeriod());
            syncEntityLastHistoryDTO.setFormKey(syncEntityLastHistoryDO.getFormKey());
            syncEntityLastHistoryDTO.setEntity(syncEntityLastHistoryDO.getEntity());
            syncEntityLastHistoryDTO.setUserId(syncEntityLastHistoryDO.getUserId());
            syncEntityLastHistoryDTO.setTime(syncEntityLastHistoryDO.getTime());
        }
        return syncEntityLastHistoryDTO;
    }

    public static List<SyncEntityLastHistoryDTO> toListInstance(List<SyncEntityLastHistoryDO> SyncEntityLastHistoryDOS) {
        ArrayList<SyncEntityLastHistoryDTO> dtos = new ArrayList<SyncEntityLastHistoryDTO>(SyncEntityLastHistoryDOS.size());
        for (SyncEntityLastHistoryDO syncEntityLastHistoryDO : SyncEntityLastHistoryDOS) {
            SyncEntityLastHistoryDTO syncEntityLastHistoryDTO = SyncEntityLastHistoryDTO.getInstance(syncEntityLastHistoryDO);
            dtos.add(syncEntityLastHistoryDTO);
        }
        return dtos;
    }

    public static List<SyncEntityLastHistoryDO> calibreDataDTOssToDOs(List<SyncEntityLastHistoryDTO> SyncEntityLastHistoryDTOs) {
        ArrayList<SyncEntityLastHistoryDO> SyncEntityLastHistoryDOs = new ArrayList<SyncEntityLastHistoryDO>(SyncEntityLastHistoryDTOs.size());
        for (SyncEntityLastHistoryDTO syncEntityLastHistoryDTO : SyncEntityLastHistoryDTOs) {
            SyncEntityLastHistoryDOs.add(syncEntityLastHistoryDTO);
        }
        return SyncEntityLastHistoryDOs;
    }
}

