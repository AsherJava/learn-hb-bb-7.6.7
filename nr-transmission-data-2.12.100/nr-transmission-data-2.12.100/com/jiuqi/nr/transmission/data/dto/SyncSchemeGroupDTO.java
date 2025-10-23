/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.dto;

import com.jiuqi.nr.transmission.data.domain.SyncSchemeGroupDO;
import java.util.ArrayList;
import java.util.List;

public class SyncSchemeGroupDTO
extends SyncSchemeGroupDO {
    private String fuzzyNameWord;
    private Integer isSelectScheme;

    public String getFuzzyNameWord() {
        return this.fuzzyNameWord;
    }

    public void setFuzzyNameWord(String fuzzyNameWord) {
        this.fuzzyNameWord = fuzzyNameWord;
    }

    public Integer getIsSelectScheme() {
        return this.isSelectScheme;
    }

    public void setIsSelectScheme(Integer isSelectScheme) {
        this.isSelectScheme = isSelectScheme;
    }

    public static SyncSchemeGroupDTO getInstance(SyncSchemeGroupDO syncSchemeGroupDO) {
        SyncSchemeGroupDTO syncSchemeGroupDTO = new SyncSchemeGroupDTO();
        if (syncSchemeGroupDO != null) {
            syncSchemeGroupDTO.setKey(syncSchemeGroupDO.getKey());
            syncSchemeGroupDTO.setTitle(syncSchemeGroupDO.getTitle());
            syncSchemeGroupDTO.setParent(syncSchemeGroupDO.getParent());
            syncSchemeGroupDTO.setOrder(syncSchemeGroupDO.getOrder());
        }
        return syncSchemeGroupDTO;
    }

    public static List<SyncSchemeGroupDTO> toListInstance(List<SyncSchemeGroupDO> dataDOs) {
        ArrayList<SyncSchemeGroupDTO> dtos = new ArrayList<SyncSchemeGroupDTO>(dataDOs.size());
        for (SyncSchemeGroupDO dataDO : dataDOs) {
            SyncSchemeGroupDTO vo = SyncSchemeGroupDTO.getInstance(dataDO);
            dtos.add(vo);
        }
        return dtos;
    }
}

