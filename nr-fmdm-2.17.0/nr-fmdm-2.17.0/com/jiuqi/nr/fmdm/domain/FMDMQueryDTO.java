/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.var.PageCondition
 */
package com.jiuqi.nr.fmdm.domain;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.var.PageCondition;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FMDMQueryDTO {
    @Deprecated
    private DimensionValueSet dimensionValueSet;
    private DimensionCombination dimensionCombination;
    private String formSchemeKey;
    private IContext context;
    private PageCondition pageCondition;
    private boolean sorted = true;
    private boolean sortedByQuery = true;
    private Map<String, Object> extendParam;
    private AuthorityType authorityType = AuthorityType.None;
    private boolean filter;
    private boolean isQueryPartZb;
    private List<IFMDMAttribute> fmdmAttributeList;
    private String dwEntityId;
    private boolean isDataMasking = false;

    @Deprecated
    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    @Deprecated
    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public IContext getContext() {
        return this.context;
    }

    public void setContext(IContext context) {
        this.context = context;
    }

    public PageCondition getPageCondition() {
        return this.pageCondition;
    }

    public void setPageCondition(PageCondition pageCondition) {
        this.pageCondition = pageCondition;
    }

    public Boolean getSorted() {
        return this.sorted;
    }

    public void setSorted(Boolean sorted) {
        this.sorted = sorted;
    }

    public Boolean getSortedByQuery() {
        return this.sortedByQuery;
    }

    public void setSortedByQuery(Boolean sortedByQuery) {
        this.sortedByQuery = sortedByQuery;
    }

    public Map<String, Object> getExtendParam() {
        if (this.extendParam == null) {
            this.extendParam = new HashMap<String, Object>(16);
        }
        return this.extendParam;
    }

    public void setExtendParam(Map<String, Object> extendParam) {
        this.extendParam = extendParam;
    }

    public AuthorityType getAuthorityType() {
        return this.authorityType;
    }

    public void setAuthorityType(AuthorityType authorityType) {
        this.authorityType = authorityType;
    }

    public boolean isFilter() {
        return this.filter;
    }

    public boolean isSorted() {
        return this.sorted;
    }

    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    public boolean isSortedByQuery() {
        return this.sortedByQuery;
    }

    public void setSortedByQuery(boolean sortedByQuery) {
        this.sortedByQuery = sortedByQuery;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public boolean isQueryPartZb() {
        return this.isQueryPartZb;
    }

    public void setQueryPartZb(boolean queryPartZb) {
        this.isQueryPartZb = queryPartZb;
    }

    public List<IFMDMAttribute> getFmdmAttributeList() {
        return this.fmdmAttributeList;
    }

    public void setFmdmAttributeList(List<IFMDMAttribute> fmdmAttributeList) {
        this.fmdmAttributeList = fmdmAttributeList;
    }

    public String getDwEntityId() {
        return this.dwEntityId;
    }

    public void setDwEntityId(String dwEntityId) {
        this.dwEntityId = dwEntityId;
    }

    public boolean isDataMasking() {
        return this.isDataMasking;
    }

    public void setDataMasking(boolean dataMasking) {
        this.isDataMasking = dataMasking;
    }
}

