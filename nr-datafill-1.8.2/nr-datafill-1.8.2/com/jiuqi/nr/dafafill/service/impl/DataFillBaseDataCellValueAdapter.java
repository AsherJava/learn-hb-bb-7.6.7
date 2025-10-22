/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataBase;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataQueryInfo;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.service.IDFDimensionQueryFieldParser;
import com.jiuqi.nr.dafafill.service.IDataFillEntityDataAdapter;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

@Order(value=10)
public class DataFillBaseDataCellValueAdapter
implements IDataFillEntityDataAdapter {
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private IDFDimensionQueryFieldParser dFDimensionQueryFieldParser;

    @Override
    public boolean accept(QueryField queryField) {
        return queryField.getExpression().endsWith("@BASE");
    }

    @Override
    public List<DataFillEntityDataBase> query(DataFillEntityDataQueryInfo queryInfo) {
        DataFillContext context = queryInfo.getContext();
        DimensionValueSet dimensionValueSet = this.dFDimensionQueryFieldParser.parserGetEntityDimensionValueSet(context);
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionQueryFieldParser.getFieldTypeQueryFields(context);
        Map<String, QueryField> queryFieldsMap = this.dFDimensionQueryFieldParser.getQueryFieldsMap(context);
        List<QueryField> list = fieldTypeQueryFields.get((Object)FieldType.PERIOD);
        String periodName = list.get(0).getSimplifyFullCode();
        String periodValue = (String)dimensionValueSet.getValue(periodName);
        GregorianCalendar gregorianCalendar = PeriodUtil.period2Calendar((String)periodValue);
        QueryField queryField = queryFieldsMap.get(queryInfo.getFullCode());
        String tableName = queryField.getExpression().split("@")[0];
        Date versionDate = new Date(gregorianCalendar.getTimeInMillis());
        BaseDataDefineDTO param = new BaseDataDefineDTO();
        param.setName(tableName);
        BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(param);
        Integer structtype = baseDataDefineDO.getStructtype();
        BaseDataDTO baseDataFilter = this.buildDataDTO(queryInfo, tableName, versionDate);
        ArrayList<DataFillEntityDataBase> result = new ArrayList<DataFillEntityDataBase>();
        PageVO page = this.baseDataClient.list(baseDataFilter);
        if (page.getTotal() != 0) {
            List rows = page.getRows();
            for (BaseDataDO baseDataDO : rows) {
                DataFillEntityDataBase dataFillEntityData = this.buildDataBase(tableName, versionDate, structtype, baseDataDO);
                result.add(dataFillEntityData);
            }
        }
        return result;
    }

    private BaseDataDTO buildDataDTO(DataFillEntityDataQueryInfo queryInfo, String tableName, Date versionDate) {
        BaseDataDTO baseDataFilter = new BaseDataDTO();
        baseDataFilter.setTableName(tableName);
        baseDataFilter.setVersionDate(versionDate);
        baseDataFilter.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        baseDataFilter.setAuthType(BaseDataOption.AuthType.ACCESS);
        if (StringUtils.hasLength(queryInfo.getSearch())) {
            baseDataFilter.setSearchKey(queryInfo.getSearch());
        }
        baseDataFilter.setShowFullPath(Boolean.valueOf(queryInfo.isShowFullPath()));
        if (StringUtils.hasLength(queryInfo.getCode())) {
            baseDataFilter.setCode(queryInfo.getCode());
        } else if (StringUtils.hasLength(queryInfo.getParentKey())) {
            baseDataFilter.setObjectcode(queryInfo.getParentKey());
            if (queryInfo.isAllChildren()) {
                baseDataFilter.setQueryChildrenType(BaseDataOption.QueryChildrenType.ALL_CHILDREN);
            } else {
                baseDataFilter.setQueryChildrenType(BaseDataOption.QueryChildrenType.DIRECT_CHILDREN);
            }
        } else if (queryInfo.isAllChildren()) {
            baseDataFilter.setQueryChildrenType(BaseDataOption.QueryChildrenType.ALL_CHILDREN);
            baseDataFilter.setCode("-");
        } else {
            baseDataFilter.setParentcode("-");
            baseDataFilter.setLazyLoad(Boolean.valueOf(true));
        }
        Locale locale = NpContextHolder.getContext().getLocale();
        String langType = locale.getLanguage();
        if (!"zh".equalsIgnoreCase(langType)) {
            baseDataFilter.setLanguage(langType);
        }
        return baseDataFilter;
    }

    @Override
    public DataFillEntityDataBase queryByIdOrCode(DataFillEntityDataQueryInfo queryInfo) {
        List rows;
        Iterator iterator;
        Date versionDate;
        DimensionValueSet dimensionValueSet = this.dFDimensionQueryFieldParser.parserGetEntityDimensionValueSet(queryInfo.getContext());
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionQueryFieldParser.getFieldTypeQueryFields(queryInfo.getContext());
        Map<String, QueryField> queryFieldsMap = this.dFDimensionQueryFieldParser.getQueryFieldsMap(queryInfo.getContext());
        List<QueryField> list = fieldTypeQueryFields.get((Object)FieldType.PERIOD);
        String periodName = list.get(0).getSimplifyFullCode();
        String periodValue = (String)dimensionValueSet.getValue(periodName);
        GregorianCalendar gregorianCalendar = PeriodUtil.period2Calendar((String)periodValue);
        QueryField queryField = queryFieldsMap.get(queryInfo.getFullCode());
        String tableName = queryField.getExpression().split("@")[0];
        BaseDataDTO baseDataFilter = this.buildDataDTO(queryInfo, tableName, versionDate = new Date(gregorianCalendar.getTimeInMillis()));
        PageVO page = this.baseDataClient.list(baseDataFilter);
        if (page.getTotal() != 0 && (iterator = (rows = page.getRows()).iterator()).hasNext()) {
            BaseDataDO baseDataDO = (BaseDataDO)iterator.next();
            return this.buildDataBase(tableName, versionDate, 0, baseDataDO);
        }
        return null;
    }

    private DataFillEntityDataBase buildDataBase(String tableName, Date versionDate, Integer structtype, BaseDataDO baseDataDO) {
        DataFillEntityDataBase dataFillEntityData = new DataFillEntityDataBase();
        dataFillEntityData.setId(baseDataDO.getCode());
        dataFillEntityData.setCode(baseDataDO.getCode());
        dataFillEntityData.setTitle(baseDataDO.getLocalizedName());
        boolean leaf = true;
        if (structtype != null && structtype > 1) {
            BaseDataDTO baseDataFilter = new BaseDataDTO();
            baseDataFilter.setTableName(tableName);
            baseDataFilter.setVersionDate(versionDate);
            baseDataFilter.setQueryDataStructure(BaseDataOption.QueryDataStructure.SIMPLE);
            baseDataFilter.setAuthType(BaseDataOption.AuthType.ACCESS);
            baseDataFilter.setObjectcode(baseDataDO.getCode());
            baseDataFilter.setQueryChildrenType(BaseDataOption.QueryChildrenType.DIRECT_CHILDREN);
            int count = this.baseDataClient.count(baseDataFilter);
            if (count != 0) {
                leaf = false;
            }
        }
        dataFillEntityData.setLeaf(leaf);
        dataFillEntityData.setPath(baseDataDO.getParents());
        return dataFillEntityData;
    }
}

