/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.definition.internal.impl.anslysis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.nr.definition.common.DimensionRange;
import com.jiuqi.nr.definition.common.PeriodRangeType;
import com.jiuqi.nr.definition.facade.analysis.DimensionAttribute;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class DimensionAttributeImpl
implements DimensionAttribute {
    private String periodRange;
    private int periodType = 1;
    private boolean showLinkEntity;
    private boolean showOnTree = true;
    private String linkEntityKey;
    private DimensionRange unitRange = DimensionRange.ALL_CHILDREN;
    private PeriodRangeType periodRangeType = PeriodRangeType.SELECTION;
    private List<String> unitKeys = new ArrayList<String>();
    private List<String> unitCodes = new ArrayList<String>();
    private String unitTitles;
    private String condition;

    @Override
    public String getLinkEntityKey() {
        return this.linkEntityKey;
    }

    @Override
    public void setLinkEntityKey(String linkEntityKey) {
        this.linkEntityKey = linkEntityKey;
    }

    @Override
    public String getPeriodRange() {
        if (StringUtils.isEmpty((String)this.periodRange)) {
            this.periodRange = PeriodUtil.currentPeriod((int)this.periodType).toString();
            this.periodRange = this.periodRange + "-" + this.periodRange;
        }
        return this.periodRange;
    }

    @Override
    public void setPeriodRange(String periodRange) {
        this.periodRange = periodRange;
    }

    @Override
    public int getPeriodType() {
        return this.periodType;
    }

    @Override
    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    @Override
    public boolean isShowLinkEntity() {
        return this.showLinkEntity;
    }

    @Override
    public void setShowLinkEntity(boolean showLinkEntity) {
        this.showLinkEntity = showLinkEntity;
    }

    @Override
    public boolean isShowOnTree() {
        return this.showOnTree;
    }

    @Override
    public void setShowOnTree(boolean showOnTree) {
        this.showOnTree = showOnTree;
    }

    @Override
    public DimensionRange getUnitRange() {
        return this.unitRange;
    }

    @Override
    public void setUnitRange(DimensionRange unitRange) {
        this.unitRange = unitRange;
    }

    @Override
    @JsonIgnore
    public String getFromPeriod() {
        String[] split;
        if (!StringUtils.isEmpty((String)this.periodRange) && (split = this.periodRange.split("-")).length == 2) {
            return split[0];
        }
        return null;
    }

    @Override
    @JsonIgnore
    public String getToPeriod() {
        String[] split;
        if (!StringUtils.isEmpty((String)this.periodRange) && (split = this.periodRange.split("-")).length == 2) {
            return split[1];
        }
        return null;
    }

    @Override
    public List<String> getUnitKeys() {
        return this.unitKeys;
    }

    @Override
    public void setUnitKeys(List<String> unitKeys) {
        this.unitKeys = unitKeys;
    }

    @Override
    public String getCondition() {
        return this.condition;
    }

    @Override
    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String getUnitTitles() {
        return this.unitTitles;
    }

    @Override
    public void setUnitTitles(String unitTitles) {
        this.unitTitles = unitTitles;
    }

    @Override
    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    @Override
    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    @Override
    public PeriodRangeType getPeriodRangeType() {
        return this.periodRangeType;
    }

    @Override
    public void setPeriodRangeType(PeriodRangeType periodRangeType) {
        this.periodRangeType = periodRangeType;
    }
}

