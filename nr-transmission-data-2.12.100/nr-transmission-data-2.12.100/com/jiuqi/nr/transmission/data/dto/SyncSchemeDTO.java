/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.dto;

import com.jiuqi.nr.transmission.data.domain.SyncSchemeDO;
import com.jiuqi.nr.transmission.data.domain.SyncSchemeParamDO;
import java.util.ArrayList;
import java.util.List;

public class SyncSchemeDTO
extends SyncSchemeDO {
    private String executorKey;
    private String lastSyncTime;
    private int lastSyncStatus;
    private SyncSchemeParamDO schemeParam;

    public String getExecutorKey() {
        return this.executorKey;
    }

    public void setExecutorKey(String executorKey) {
        this.executorKey = executorKey;
    }

    public String getLastSyncTime() {
        return this.lastSyncTime;
    }

    public void setLastSyncTime(String lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public int getLastSyncStatus() {
        return this.lastSyncStatus;
    }

    public void setLastSyncStatus(int lastSyncStatus) {
        this.lastSyncStatus = lastSyncStatus;
    }

    public SyncSchemeParamDO getSchemeParam() {
        return this.schemeParam;
    }

    public void setSchemeParam(SyncSchemeParamDO schemeParam) {
        this.schemeParam = schemeParam;
    }

    public static SyncSchemeDTO getInstance(SyncSchemeDO syncSchemeDO) {
        SyncSchemeDTO syncSchemeDTO = new SyncSchemeDTO();
        if (syncSchemeDO != null) {
            syncSchemeDTO.setKey(syncSchemeDO.getKey());
            syncSchemeDTO.setCode(syncSchemeDO.getCode());
            syncSchemeDTO.setTitle(syncSchemeDO.getTitle());
            syncSchemeDTO.setGroup(syncSchemeDO.getGroup());
            syncSchemeDTO.setDesc(syncSchemeDO.getDesc());
            syncSchemeDTO.setUpdataTime(syncSchemeDO.getUpdataTime());
            syncSchemeDTO.setOrder(syncSchemeDO.getOrder());
        }
        return syncSchemeDTO;
    }

    public static List<SyncSchemeDTO> toListInstance(List<SyncSchemeDO> dataDOs) {
        ArrayList<SyncSchemeDTO> dtos = new ArrayList<SyncSchemeDTO>(dataDOs.size());
        for (SyncSchemeDO dataDO : dataDOs) {
            SyncSchemeDTO vo = SyncSchemeDTO.getInstance(dataDO);
            dtos.add(vo);
        }
        return dtos;
    }
}

