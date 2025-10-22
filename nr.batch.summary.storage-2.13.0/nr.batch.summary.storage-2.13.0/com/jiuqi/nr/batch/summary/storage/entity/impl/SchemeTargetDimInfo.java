/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.batch.summary.storage.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.batch.summary.storage.CustomCalibreDao;
import com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeTargetDim;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.enumeration.TargetDimType;
import com.jiuqi.util.StringUtils;
import java.util.List;

public class SchemeTargetDimInfo
implements SchemeTargetDim {
    private String dimValue;
    private TargetDimType targetDimType;
    private SummaryScheme scheme;
    private CustomCalibreDao customCalibreDao;
    private List<CustomCalibreRow> customCalibreRows;

    public SchemeTargetDimInfo() {
    }

    public SchemeTargetDimInfo(CustomCalibreDao customCalibreDao, SummaryScheme scheme) {
        this.scheme = scheme;
        this.customCalibreDao = customCalibreDao;
    }

    @Override
    public String getDimValue() {
        return this.dimValue;
    }

    public void setDimValue(String dimValue) {
        this.dimValue = dimValue;
    }

    @Override
    public TargetDimType getTargetDimType() {
        return this.targetDimType;
    }

    public void setTargetDimType(TargetDimType targetDimType) {
        this.targetDimType = targetDimType;
    }

    @Override
    public List<CustomCalibreRow> getCustomCalibreRows() {
        if (TargetDimType.CONDITION == this.targetDimType) {
            if (this.customCalibreRows == null) {
                this.customCalibreRows = this.customCalibreDao.findConditionRow(this.scheme.getKey());
            }
            return this.customCalibreRows;
        }
        return null;
    }

    public void setCustomCalibreRows(List<CustomCalibreRow> customCalibreRows) {
        this.customCalibreRows = customCalibreRows;
    }

    @Override
    @JsonIgnore
    public boolean isValidTargetDim() {
        return this.targetDimType.equals((Object)TargetDimType.CONDITION) || StringUtils.isNotEmpty((String)this.dimValue);
    }
}

