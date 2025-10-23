/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.internal.domain;

import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalibreSubListDO {
    private String code;
    private String calibreCode;
    private String value;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCalibreCode() {
        return this.calibreCode;
    }

    public void setCalibreCode(String calibreCode) {
        this.calibreCode = calibreCode;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<String, Object>(3);
        map.put("CS_CODE", this.getCode());
        map.put("CS_CALIBRE_CODE", this.getCalibreCode());
        map.put("CS_VALUE", this.getValue());
        return map;
    }

    public static List<CalibreSubListDO> getInstance(CalibreDataDTO dataDTO) {
        ArrayList<CalibreSubListDO> subListDOList = new ArrayList<CalibreSubListDO>();
        if (dataDTO.getValue().getExpression() instanceof List) {
            for (String entityKey : (List)dataDTO.getValue().getExpression()) {
                CalibreSubListDO subListDO = new CalibreSubListDO();
                subListDO.setCalibreCode(dataDTO.getCalibreCode());
                subListDO.setValue(entityKey);
                subListDO.setCode(dataDTO.getCode());
                subListDOList.add(subListDO);
            }
        }
        return subListDOList;
    }
}

