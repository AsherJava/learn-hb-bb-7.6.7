/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.domain;

import com.jiuqi.nr.calibre2.common.CalibreDataOption;
import com.jiuqi.nr.calibre2.domain.CalibreDataDO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;

public class CalibreDataDTO
extends CalibreDataDO {
    private String defineKey;
    private String keyWords;
    private CalibreDataOption.DataTreeType dataTreeType;
    private boolean desc;
    private List<String> codes;
    private String entityId;

    public String getDefineKey() {
        return this.defineKey;
    }

    public void setDefineKey(String defineKey) {
        this.defineKey = defineKey;
    }

    public CalibreDataOption.DataTreeType getDataTreeType() {
        return this.dataTreeType;
    }

    public void setDataTreeType(CalibreDataOption.DataTreeType dataTreeType) {
        this.dataTreeType = dataTreeType;
    }

    public String getKeyWords() {
        return this.keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public boolean isDesc() {
        return this.desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }

    public List<String> getCodes() {
        return this.codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public static CalibreDataDTO getInstance(CalibreDataDO calibreDataDO) {
        CalibreDataDTO dto = new CalibreDataDTO();
        BeanUtils.copyProperties(calibreDataDO, dto);
        return dto;
    }

    public static List<CalibreDataDTO> DOsToDTOs(List<CalibreDataDO> calibreDataDOs) {
        ArrayList<CalibreDataDTO> dtos = new ArrayList<CalibreDataDTO>(calibreDataDOs.size());
        for (CalibreDataDO calibreDataDO : calibreDataDOs) {
            CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
            BeanUtils.copyProperties(calibreDataDO, calibreDataDTO);
            dtos.add(calibreDataDTO);
        }
        return dtos;
    }

    public static List<CalibreDataDTO> CalibreDataDOsToDTOsWithDefineKey(List<CalibreDataDO> calibreDataDOs, String defineKey) {
        ArrayList<CalibreDataDTO> dtos = new ArrayList<CalibreDataDTO>(calibreDataDOs.size());
        for (CalibreDataDO calibreDataDO : calibreDataDOs) {
            CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
            BeanUtils.copyProperties(calibreDataDO, calibreDataDTO);
            calibreDataDTO.setDefineKey(defineKey);
            dtos.add(calibreDataDTO);
        }
        return dtos;
    }

    public static List<CalibreDataDO> calibreDataDTOssToDOs(List<CalibreDataDTO> calibreDataDTOs) {
        ArrayList<CalibreDataDO> calibreDataDOS = new ArrayList<CalibreDataDO>(calibreDataDTOs.size());
        for (CalibreDataDTO calibreDataDTO : calibreDataDTOs) {
            calibreDataDOS.add(calibreDataDTO);
        }
        return calibreDataDOS;
    }
}

