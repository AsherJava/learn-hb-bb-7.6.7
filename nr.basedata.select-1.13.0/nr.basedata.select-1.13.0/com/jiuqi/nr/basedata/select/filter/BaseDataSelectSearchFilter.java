/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.IFieldsInfo
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.basedata.select.filter;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.basedata.select.param.BaseDataQueryInfo;
import com.jiuqi.nr.basedata.select.service.IBaseDataSelectFilter;
import com.jiuqi.nr.basedata.select.service.IBaseDataSelectParamService;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@ApiModel(value="BaseDataSelectSearchFilter", description="\u57fa\u7840\u6570\u636e\u641c\u7d22\u5b57\u6bb5\u8fc7\u6ee4\u5668")
@Component
public class BaseDataSelectSearchFilter
implements IBaseDataSelectFilter {
    private String entityKey;
    @ApiModelProperty(value="\u8fc7\u6ee4\u5b57\u7b26\u4e32", name="search")
    private String search;
    @ApiModelProperty(value="\u8fc7\u6ee4\u5b57\u7b26\u4e32\u5217\u8868", name="searchList")
    private Set<String> searchList;
    @ApiModelProperty(value="\u8fc7\u6ee4\u6307\u6807", name="fields")
    private List<String> fields = new ArrayList<String>();
    @ApiModelProperty(value="\u662f\u5426\u5168\u8bcd\u5339\u914d", name="matchAll")
    private boolean matchAll = false;
    @ApiModelProperty(value="\u8fc7\u6ee4\u5b57\u7b26\u662f\u5426\u89e3\u6790\u5168\u8def\u5f84", name="fullPath")
    private boolean fullPath = false;
    private Map<String, IEntityTable> referEntityTableMap;
    private Map<String, String> referEntityIdMap;
    @Autowired
    private IBaseDataSelectParamService baseDataSelectParamService;
    public static final String FILTER_NAME = "SEARCH_FILTER";

    @Override
    public String getFilterName() {
        return FILTER_NAME;
    }

    @Override
    public void initFilterParams(Object params) {
        this.resetProperty();
        if (params == null) {
            return;
        }
        Map filterParam = (Map)params;
        if (filterParam.get("entityKey") != null) {
            this.entityKey = (String)filterParam.get("entityKey");
        }
        if (filterParam.get("search") != null) {
            this.search = (String)filterParam.get("search");
        }
        if (filterParam.get("fields") != null) {
            this.fields = (List)filterParam.get("fields");
        }
        if (filterParam.get("matchAll") != null) {
            this.matchAll = (Boolean)filterParam.get("matchAll");
        }
        if (filterParam.get("fullPath") != null) {
            this.fullPath = (Boolean)filterParam.get("fullPath");
        }
        String[] splits = null;
        if (StringUtils.isNotEmpty((String)this.search)) {
            splits = this.search.split("\\|");
        }
        if (null != splits) {
            this.searchList = new HashSet<String>(Arrays.asList(splits));
            if (this.fullPath) {
                for (String oneSearch : this.searchList) {
                    String[] oneSearchList = oneSearch.split("/");
                    this.searchList.add(oneSearchList[oneSearchList.length - 1]);
                }
            }
        }
        this.gteReferEntityMap();
    }

    private void gteReferEntityMap() {
        if (this.fields == null || this.fields.isEmpty()) {
            return;
        }
        Map<String, String> referEntityIdMap = this.baseDataSelectParamService.getReferEntityIdMap(this.entityKey, this.fields);
        if (referEntityIdMap == null || referEntityIdMap.size() == 0) {
            return;
        }
        this.referEntityIdMap = referEntityIdMap;
        HashMap<String, IEntityTable> referEntityTableMap = new HashMap<String, IEntityTable>();
        for (Map.Entry<String, String> entry : referEntityIdMap.entrySet()) {
            String entityId = entry.getValue();
            if (!StringUtils.isNotEmpty((String)entityId)) continue;
            BaseDataQueryInfo baseDataQueryInfo = new BaseDataQueryInfo();
            baseDataQueryInfo.setEntityKey(entityId);
            baseDataQueryInfo.setEntityAuth(false);
            baseDataQueryInfo.setReadAuth(false);
            IEntityTable iEntityTable = this.baseDataSelectParamService.buildEntityTable(baseDataQueryInfo, true, true);
            referEntityTableMap.put(entityId, iEntityTable);
        }
        if (referEntityTableMap == null || referEntityTableMap.size() == 0) {
            return;
        }
        this.referEntityTableMap = referEntityTableMap;
    }

    private void resetProperty() {
        if (StringUtils.isNotEmpty((String)this.entityKey)) {
            this.entityKey = null;
        }
        if (StringUtils.isNotEmpty((String)this.search)) {
            this.search = null;
        }
        if (this.fields != null && this.fields.size() > 0) {
            this.fields = new ArrayList<String>();
        }
        if (this.searchList != null && this.searchList.size() > 0) {
            this.searchList = new HashSet<String>();
        }
        if (this.referEntityTableMap != null) {
            this.referEntityTableMap = null;
        }
        if (this.referEntityIdMap != null) {
            this.referEntityIdMap = null;
        }
        this.matchAll = false;
        this.fullPath = false;
    }

    @Override
    public boolean accept(IEntityRow entityRow) {
        if (StringUtils.isEmpty((String)this.search)) {
            return true;
        }
        if (this.filterFieldValue(entityRow.getCode())) {
            return true;
        }
        if (this.filterFieldValue(entityRow.getTitle())) {
            return true;
        }
        IFieldsInfo fieldsInfo = entityRow.getFieldsInfo();
        for (int i = 0; i < fieldsInfo.getFieldCount(); ++i) {
            AbstractData value;
            String fieldValue;
            String referValue;
            String fieldCode = fieldsInfo.getFieldByIndex(i).getCode();
            if (!this.fields.contains(fieldCode) || !(StringUtils.isNotEmpty((String)(referValue = this.getReferEntityCodeValue(entityRow, fieldCode))) ? this.filterFieldValue(referValue) : this.filterFieldValue(fieldValue = (value = entityRow.getValue(fieldCode)).getAsString()))) continue;
            return true;
        }
        return false;
    }

    private String getReferEntityCodeValue(IEntityRow entityRow, String fieldCode) {
        if (this.referEntityIdMap != null && this.referEntityIdMap.containsKey(fieldCode)) {
            String entityKeyData;
            IEntityRow referEntityRow;
            IEntityTable iEntityTable;
            String entityId = this.referEntityIdMap.get(fieldCode);
            if (this.referEntityTableMap != null && this.referEntityTableMap.containsKey(entityId) && (iEntityTable = this.referEntityTableMap.get(entityId)) != null && (referEntityRow = iEntityTable.quickFindByEntityKey(entityKeyData = entityRow.getValue(fieldCode).getAsString())) != null) {
                String showTitle = entityKeyData + "|" + referEntityRow.getTitle();
                return showTitle;
            }
        }
        return null;
    }

    private boolean filterFieldValue(String fieldValue) {
        if (StringUtils.isNotEmpty((String)fieldValue)) {
            if (this.matchAll) {
                if (fieldValue.equals(this.search)) {
                    return true;
                }
            } else {
                if (fieldValue.toLowerCase().contains(this.search.toLowerCase())) {
                    return true;
                }
                if (this.searchList != null && this.searchList.size() > 0) {
                    for (String searchStr : this.searchList) {
                        if (!fieldValue.toLowerCase().contains(searchStr.trim().toLowerCase())) continue;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

