/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.vo;

import com.jiuqi.nr.transmission.data.dto.SyncHistoryDTO;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeDTO;
import java.util.ArrayList;
import java.util.List;

public class SyncSchemeVO
extends SyncSchemeDTO {
    private String groupName;
    private String taskTitle;
    private String formSchemeKey;
    private SyncHistoryDTO lastSyncHistory;
    private List<SyncHistoryDTO> alllSyncHistory;

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public SyncHistoryDTO getLastSyncHistory() {
        return this.lastSyncHistory;
    }

    public void setLastSyncHistory(SyncHistoryDTO lastSyncHistory) {
        this.lastSyncHistory = lastSyncHistory;
    }

    public List<SyncHistoryDTO> getAlllSyncHistory() {
        return this.alllSyncHistory;
    }

    public void setAlllSyncHistory(List<SyncHistoryDTO> alllSyncHistory) {
        this.alllSyncHistory = alllSyncHistory;
    }

    public static SyncSchemeVO getVOInstance(SyncSchemeDTO syncSchemeDTO) {
        SyncSchemeVO syncSchemeVO = new SyncSchemeVO();
        if (syncSchemeDTO != null) {
            syncSchemeVO.setKey(syncSchemeDTO.getKey());
            syncSchemeVO.setCode(syncSchemeDTO.getCode());
            syncSchemeVO.setTitle(syncSchemeDTO.getTitle());
            syncSchemeVO.setGroup(syncSchemeDTO.getGroup());
            syncSchemeVO.setDesc(syncSchemeDTO.getDesc());
            syncSchemeVO.setUpdataTime(syncSchemeDTO.getUpdataTime());
            syncSchemeVO.setOrder(syncSchemeDTO.getOrder());
            syncSchemeVO.setExecutorKey(syncSchemeDTO.getExecutorKey());
            syncSchemeVO.setLastSyncTime(syncSchemeDTO.getLastSyncTime());
            syncSchemeVO.setLastSyncStatus(syncSchemeDTO.getLastSyncStatus());
            syncSchemeVO.setSchemeParam(syncSchemeDTO.getSchemeParam());
        }
        return syncSchemeVO;
    }

    public static List<SyncSchemeVO> toVOListInstance(List<SyncSchemeDTO> dataDTOs) {
        ArrayList<SyncSchemeVO> vos = new ArrayList<SyncSchemeVO>(dataDTOs.size());
        for (SyncSchemeDTO dataDO : dataDTOs) {
            SyncSchemeVO vo = SyncSchemeVO.getVOInstance(dataDO);
            vos.add(vo);
        }
        return vos;
    }
}

