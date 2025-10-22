/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgDataClient
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
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
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

@Order(value=0)
public class DataFillMdOrgCellValueAdapter
implements IDataFillEntityDataAdapter {
    @Autowired
    private IDFDimensionQueryFieldParser dFDimensionQueryFieldParser;
    @Autowired
    private OrgDataClient orgDataClient;

    @Override
    public boolean accept(QueryField queryField) {
        return queryField.getExpression().endsWith("@ORG");
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
        ArrayList<DataFillEntityDataBase> result = new ArrayList<DataFillEntityDataBase>();
        PageVO page = this.orgDataClient.list(this.buildDataDTO(queryInfo, tableName, versionDate));
        if (page.getTotal() != 0) {
            List rows = page.getRows();
            for (OrgDO orgDO : rows) {
                DataFillEntityDataBase dataFillEntityData = this.buildDataBase(tableName, versionDate, orgDO);
                result.add(dataFillEntityData);
            }
        }
        return result;
    }

    private OrgDTO buildDataDTO(DataFillEntityDataQueryInfo queryInfo, String tableName, Date versionDate) {
        Locale locale;
        String langType;
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname(tableName);
        orgDTO.setVersionDate(versionDate);
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
        if (StringUtils.hasLength(queryInfo.getSearch())) {
            orgDTO.setSearchKey(queryInfo.getSearch());
        }
        if (StringUtils.hasLength(queryInfo.getCode())) {
            orgDTO.setCode(queryInfo.getCode());
        } else if (StringUtils.hasLength(queryInfo.getParentKey())) {
            orgDTO.setParentcode(queryInfo.getParentKey());
        } else if (queryInfo.isAllChildren()) {
            orgDTO.setCode("-");
        } else {
            orgDTO.setParentcode("-");
        }
        orgDTO.setLazyLoad(Boolean.valueOf(!queryInfo.isAllChildren()));
        if (queryInfo.isAllChildren()) {
            orgDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN);
        }
        if (!"zh".equalsIgnoreCase(langType = (locale = NpContextHolder.getContext().getLocale()).getLanguage())) {
            orgDTO.setLanguage(langType);
        }
        return orgDTO;
    }

    private DataFillEntityDataBase buildDataBase(String tableName, Date versionDate, OrgDO orgDO) {
        OrgDTO orgDTO = new OrgDTO();
        DataFillEntityDataBase dataFillEntityData = new DataFillEntityDataBase();
        dataFillEntityData.setId(orgDO.getOrgcode());
        dataFillEntityData.setCode(orgDO.getOrgcode());
        dataFillEntityData.setTitle(orgDO.getLocalizedName());
        boolean leaf = true;
        orgDTO.setCategoryname(tableName);
        orgDTO.setVersionDate(versionDate);
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.SIMPLE);
        orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
        orgDTO.setParentcode(orgDO.getCode());
        orgDTO.setLazyLoad(Boolean.valueOf(true));
        int count = this.orgDataClient.count(orgDTO);
        if (count != 0) {
            leaf = false;
        }
        dataFillEntityData.setLeaf(leaf);
        dataFillEntityData.setPath(orgDO.getParents());
        return dataFillEntityData;
    }

    @Override
    public DataFillEntityDataBase queryByIdOrCode(DataFillEntityDataQueryInfo queryInfo) {
        List rows;
        Iterator iterator;
        Date versionDate;
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
        PageVO page = this.orgDataClient.list(this.buildDataDTO(queryInfo, tableName, versionDate = new Date(gregorianCalendar.getTimeInMillis())));
        if (page.getTotal() != 0 && (iterator = (rows = page.getRows()).iterator()).hasNext()) {
            OrgDO orgDO = (OrgDO)iterator.next();
            return this.buildDataBase(tableName, versionDate, orgDO);
        }
        return null;
    }
}

