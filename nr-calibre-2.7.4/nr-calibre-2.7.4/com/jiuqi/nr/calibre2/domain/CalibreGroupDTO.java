/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.domain;

import com.jiuqi.nr.calibre2.common.CalibreGroupOption;
import com.jiuqi.nr.calibre2.domain.CalibreGroupDO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class CalibreGroupDTO
extends CalibreGroupDO {
    private String keyWords;
    private Integer isSelectCalibre;
    private Boolean ignoreShareStatus;
    private Integer moverWay;
    private CalibreGroupOption.GroupTreeType groupTreeType;

    public CalibreGroupDTO() {
    }

    public CalibreGroupDTO(CalibreGroupDO calibreGroupDO) {
        this.setKey(calibreGroupDO.getKey());
        this.setName(calibreGroupDO.getName());
        this.setParent(calibreGroupDO.getParent());
        this.setOrder(calibreGroupDO.getOrder());
    }

    public static List<CalibreGroupDTO> DOToDTO(List<CalibreGroupDO> calibreGroupDOList) {
        ArrayList<CalibreGroupDTO> calibreGroupDTOList = new ArrayList<CalibreGroupDTO>();
        if (!CollectionUtils.isEmpty(calibreGroupDOList)) {
            for (CalibreGroupDO calibreGroupDO : calibreGroupDOList) {
                CalibreGroupDTO calibreGroupDTO = new CalibreGroupDTO();
                calibreGroupDTO.setKey(calibreGroupDO.getKey());
                calibreGroupDTO.setName(calibreGroupDO.getName());
                calibreGroupDTO.setParent(calibreGroupDO.getParent());
                calibreGroupDTO.setOrder(calibreGroupDO.getOrder());
                calibreGroupDTOList.add(calibreGroupDTO);
            }
        }
        return calibreGroupDTOList;
    }

    public String getKeyWords() {
        return this.keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public Integer getIsSelectCalibre() {
        return this.isSelectCalibre;
    }

    public void setIsSelectCalibre(Integer isSelectCalibre) {
        this.isSelectCalibre = isSelectCalibre;
    }

    public Boolean getIgnoreShareStatus() {
        return this.ignoreShareStatus;
    }

    public void setIgnoreShareStatus(Boolean ignoreShareStatus) {
        this.ignoreShareStatus = ignoreShareStatus;
    }

    public Integer getMoverWay() {
        return this.moverWay;
    }

    public void setMoverWay(Integer moverWay) {
        this.moverWay = moverWay;
    }

    public CalibreGroupOption.GroupTreeType getGroupTreeType() {
        return this.groupTreeType;
    }

    public void setGroupTreeType(CalibreGroupOption.GroupTreeType groupTreeType) {
        this.groupTreeType = groupTreeType;
    }
}

