/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.fmdm.internal.dto;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.domain.EntityDataDO;
import java.util.List;

public class QueryParamDTO
extends FMDMDataDTO {
    private String entityId;
    private String formKey;
    private boolean preCheck;
    private boolean ignoreErrorData;
    private String expression;
    private String filterExpression;
    private DimensionCollection dimensionCollection;
    private List<EntityDataDO> entityList;
    private List<DimensionValueSet> mergedByOrgCodeDims;
    private String entityDimsionName;

    public QueryParamDTO() {
    }

    public QueryParamDTO(String entityId, String formSchemeKey, IContext context) {
        this.entityId = entityId;
        super.setFormSchemeKey(formSchemeKey);
        super.setContext(context);
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public boolean isPreCheck() {
        return this.preCheck;
    }

    public void setPreCheck(boolean preCheck) {
        this.preCheck = preCheck;
    }

    public boolean isIgnoreErrorData() {
        return this.ignoreErrorData;
    }

    public void setIgnoreErrorData(boolean ignoreErrorData) {
        this.ignoreErrorData = ignoreErrorData;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getFilterExpression() {
        return this.filterExpression;
    }

    public void setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
    }

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public List<EntityDataDO> getEntityList() {
        return this.entityList;
    }

    public void setEntityList(List<EntityDataDO> entityList) {
        this.entityList = entityList;
    }

    public List<DimensionValueSet> getMergedByOrgCodeDims() {
        return this.mergedByOrgCodeDims;
    }

    public void setMergedByOrgCodeDims(List<DimensionValueSet> mergedByOrgCodeDims) {
        this.mergedByOrgCodeDims = mergedByOrgCodeDims;
    }

    public String getEntityDimsionName() {
        return this.entityDimsionName;
    }

    public void setEntityDimsionName(String entityDimsionName) {
        this.entityDimsionName = entityDimsionName;
    }

    public static QueryParamDTO getInstance(FMDMDataDTO fmdmDataDTO) {
        QueryParamDTO queryParamDTO = new QueryParamDTO();
        queryParamDTO.setDimensionCombination(fmdmDataDTO.getDimensionCombination());
        queryParamDTO.setFormSchemeKey(fmdmDataDTO.getFormSchemeKey());
        queryParamDTO.setContext(fmdmDataDTO.getContext());
        queryParamDTO.setPageCondition(fmdmDataDTO.getPageCondition());
        queryParamDTO.setSorted(fmdmDataDTO.getSorted());
        queryParamDTO.setSortedByQuery(fmdmDataDTO.getSortedByQuery());
        queryParamDTO.setExtendParam(fmdmDataDTO.getExtendParam());
        queryParamDTO.setAuthorityType(fmdmDataDTO.getAuthorityType());
        queryParamDTO.setFilter(fmdmDataDTO.isFilter());
        queryParamDTO.setIgnoreCheck(fmdmDataDTO.isIgnoreCheck());
        queryParamDTO.setModifyValue(fmdmDataDTO.getModifyValueMap());
        queryParamDTO.setEntityModify(fmdmDataDTO.getEntityModify());
        queryParamDTO.setDataModify(fmdmDataDTO.getDataModify());
        queryParamDTO.setQueryPartZb(fmdmDataDTO.isQueryPartZb());
        queryParamDTO.setFmdmAttributeList(fmdmDataDTO.getFmdmAttributeList());
        queryParamDTO.setDwEntityId(fmdmDataDTO.getDwEntityId());
        queryParamDTO.setDataMasking(fmdmDataDTO.isDataMasking());
        if (fmdmDataDTO instanceof QueryParamDTO) {
            queryParamDTO.setExpression(((QueryParamDTO)fmdmDataDTO).getExpression());
        }
        return queryParamDTO;
    }
}

