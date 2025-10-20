/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.common.BaseDataContext
 *  com.jiuqi.va.domain.basedata.BaseDataCacheDO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataFilter
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$FilterType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.basedata.BaseDataSearchUtil
 *  com.jiuqi.va.domain.basedata.handle.BaseDataSearchDTO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.basedata.service.impl.help;

import com.jiuqi.va.basedata.common.BaseDataContext;
import com.jiuqi.va.basedata.service.BaseDataCommonlyUsedService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataParamService;
import com.jiuqi.va.domain.basedata.BaseDataCacheDO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataFilter;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.BaseDataSearchUtil;
import com.jiuqi.va.domain.basedata.handle.BaseDataSearchDTO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="vaBaseDataFilterService")
public class BaseDataFilterService {
    @Autowired
    private BaseDataParamService baseDataParamService;
    @Autowired
    private BaseDataCommonlyUsedService commonlyUsedService;
    private static Map<String, BaseDataFilter> filterMap;
    private static Set<String> sysSpeCol;

    public void filterList(List<BaseDataDO> dataList, BaseDataDTO param, boolean isFromCache) {
        Map<String, DataModelColumn> modelColsMap = this.baseDataParamService.loadModelColMap(param.getTenantName(), param.getTableName());
        for (DataModelColumn column : modelColsMap.values()) {
            if (!StringUtils.hasText(column.getDefaultVal())) continue;
            if (column.getColumnType() == DataModelType.ColumnType.INTEGER) {
                BaseDataContext.setDataContext((String)param.getTableName(), (String)"defaultVal", (String)column.getColumnName().toLowerCase(), (Object)Integer.parseInt(column.getDefaultVal()));
                continue;
            }
            if (column.getColumnType() == DataModelType.ColumnType.NUMERIC) {
                BaseDataContext.setDataContext((String)param.getTableName(), (String)"defaultVal", (String)column.getColumnName().toLowerCase(), (Object)new BigDecimal(column.getDefaultVal()));
                continue;
            }
            BaseDataContext.setDataContext((String)param.getTableName(), (String)"defaultVal", (String)column.getColumnName().toLowerCase(), (Object)column.getDefaultVal());
        }
        this.filterList(dataList, param, modelColsMap.keySet(), isFromCache);
    }

    public void filterList(List<BaseDataDO> dataList, BaseDataDTO param, Set<String> modelCols, boolean isFromCache) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        Set<String> commonlyFilter = null;
        if (param.containsKey((Object)"commonlyFilter")) {
            BaseDataDTO cmrdParam = new BaseDataDTO();
            cmrdParam.setCreateuser(ShiroUtil.getUser().getId());
            cmrdParam.setTableName(param.getTableName());
            commonlyFilter = this.commonlyUsedService.listObjectcode(cmrdParam);
            if (commonlyFilter.isEmpty()) {
                dataList.clear();
                return;
            }
        }
        String searchKey = null;
        String[] searchKeyArr = null;
        if (StringUtils.hasText(param.getSearchKey()) && (searchKey = param.getSearchKey().trim().toUpperCase()).contains(" ")) {
            searchKeyArr = searchKey.split(" ");
        }
        Map colIndexMap = BaseDataContext.getColIndexMap((String)param.getTenantName(), (String)param.getTableName());
        HashMap<String, BaseDataSearchDTO> deepSearchMap = null;
        List deepSearch = param.getDeepSearch();
        if (deepSearch != null) {
            deepSearchMap = new HashMap<String, BaseDataSearchDTO>();
            String colName = null;
            for (BaseDataSearchDTO searchDTO : deepSearch) {
                colName = searchDTO.getColumn().toLowerCase();
                if (colIndexMap.containsKey(colName)) {
                    searchDTO.setColumnIndex(((Integer)colIndexMap.get(colName)).intValue());
                }
                deepSearchMap.put(colName, searchDTO);
            }
        }
        List sharefields = (List)param.get((Object)"sharefields");
        boolean isLazyLoad = param.isLazyLoad();
        String localColName = "name_" + param.getLanguage();
        if (!modelCols.contains(localColName)) {
            localColName = null;
        }
        HashSet<String> extFieldSet = new HashSet<String>();
        String paramKey = null;
        for (Map.Entry entry : param.entrySet()) {
            paramKey = (String)entry.getKey();
            if (sysSpeCol.contains(paramKey) || !modelCols.contains(paramKey) || param.get((Object)paramKey) == null || sharefields != null && sharefields.contains(paramKey) || isLazyLoad && "parentcode".equals(paramKey)) continue;
            extFieldSet.add(paramKey);
        }
        HashSet objectcodeScopeParam = null;
        List objectcodeScope = param.getObjectcodeScope();
        if (objectcodeScope != null) {
            objectcodeScopeParam = new HashSet(objectcodeScope);
        }
        HashSet codeScopeParam = null;
        List codeScope = param.getCodeScope();
        if (codeScope != null) {
            codeScopeParam = new HashSet(codeScope);
        }
        Object fieldVal = null;
        BaseDataDO data2 = null;
        Iterator<BaseDataDO> it = dataList.iterator();
        while (it.hasNext()) {
            data2 = it.next();
            if (!BaseDataSearchUtil.doSearch((BaseDataDO)data2, deepSearchMap, (String[])searchKeyArr, (String)searchKey, (String)localColName)) {
                it.remove();
                continue;
            }
            boolean addFlag = true;
            for (String field : extFieldSet) {
                fieldVal = data2 instanceof BaseDataCacheDO && colIndexMap.containsKey(field) ? ((BaseDataCacheDO)data2).getFieldValue((Object)field, ((Integer)colIndexMap.get(field)).intValue(), true) : data2.get((Object)field);
                if (fieldVal != null && fieldVal.toString().equals(param.get((Object)field).toString())) continue;
                addFlag = false;
                break;
            }
            if (!addFlag) {
                it.remove();
                continue;
            }
            if (objectcodeScopeParam != null && !objectcodeScopeParam.contains(data2.getObjectcode())) {
                it.remove();
                continue;
            }
            if (codeScopeParam != null && !codeScopeParam.contains(data2.getCode())) {
                it.remove();
                continue;
            }
            if (commonlyFilter == null || commonlyFilter.contains(data2.getObjectcode())) continue;
            it.remove();
        }
        this.filterAuth(param, dataList);
        this.filterExtend(param, dataList);
        if (dataList.isEmpty()) {
            return;
        }
        if (isFromCache && !isLazyLoad) {
            return;
        }
        BaseDataOption.QueryChildrenType queryChildrenType = param.getQueryChildrenType();
        String codeParam = param.getCode();
        boolean hasCodeParam = StringUtils.hasText(codeParam);
        String includeCodeParam = null;
        if (hasCodeParam) {
            includeCodeParam = "/" + codeParam + "/";
        }
        String parentCodeParam = param.getParentcode();
        boolean hasParentCodeParam = StringUtils.hasText(parentCodeParam);
        boolean isLazyRoot = isLazyLoad && "-".equals(parentCodeParam);
        boolean isLazyParent = isLazyLoad && hasParentCodeParam;
        boolean hasCodeListParam = param.getBaseDataCodes() != null;
        HashSet<String> parentFlag = new HashSet<String>();
        HashSet<String> codeFlag = new HashSet<String>();
        String dataCode = null;
        String dataParentCode = null;
        String dataParents = null;
        Iterator<BaseDataDO> it2 = dataList.iterator();
        while (it2.hasNext()) {
            data2 = it2.next();
            dataCode = data2.getCode();
            if (isLazyRoot) {
                codeFlag.add(dataCode);
            }
            dataParentCode = data2.getParentcode();
            if (isLazyParent) {
                parentFlag.add(dataParentCode);
            }
            if (isLazyParent && !isLazyRoot && !parentCodeParam.equals(dataParentCode)) {
                it2.remove();
                continue;
            }
            if (isFromCache || isLazyLoad || queryChildrenType != null && hasCodeParam && ((queryChildrenType == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN || queryChildrenType == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF) && dataParentCode.equals(codeParam) || (queryChildrenType == BaseDataOption.QueryChildrenType.ALL_CHILDREN || queryChildrenType == BaseDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF) && ((dataParents = data2.getParents()) != null ? ("/-/" + dataParents).contains(includeCodeParam) : dataParentCode.equals(codeParam))) || hasCodeListParam) continue;
            if (queryChildrenType != null && (queryChildrenType == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN || queryChildrenType == BaseDataOption.QueryChildrenType.ALL_CHILDREN)) {
                it2.remove();
                continue;
            }
            if (!hasCodeParam || dataCode.equals(codeParam)) continue;
            it2.remove();
        }
        if (isLazyRoot && !dataList.isEmpty()) {
            it2 = dataList.iterator();
            while (it2.hasNext()) {
                data2 = it2.next();
                if (!codeFlag.contains(data2.getParentcode())) continue;
                it2.remove();
            }
        }
        if (isLazyParent && !dataList.isEmpty()) {
            for (BaseDataDO data2 : dataList) {
                data2.put("hasChildren", (Object)parentFlag.contains(data2.getCode()));
            }
        }
    }

    public static Map<String, BaseDataFilter> getFilterMap() {
        if (filterMap == null) {
            filterMap = ApplicationContextRegister.getBeansOfType(BaseDataFilter.class);
        }
        return filterMap;
    }

    private void filterAuth(BaseDataDTO param, List<BaseDataDO> dataList) {
        if (dataList.isEmpty() || param.getAuthType() == BaseDataOption.AuthType.NONE) {
            return;
        }
        Set dataAuthCodes = (Set)param.get((Object)"dataAuthSet");
        if (dataAuthCodes == null) {
            return;
        }
        boolean onlyMarkAuth = param.isOnlyMarkAuth();
        if (dataAuthCodes.isEmpty() && !onlyMarkAuth) {
            dataList.clear();
            return;
        }
        if (onlyMarkAuth) {
            for (BaseDataDO data : dataList) {
                data.put("authMark", (Object)dataAuthCodes.contains(data.getObjectcode()));
            }
            return;
        }
        BaseDataDO data = null;
        Iterator<BaseDataDO> it = dataList.iterator();
        while (it.hasNext()) {
            data = it.next();
            if (dataAuthCodes.contains(data.getObjectcode())) continue;
            it.remove();
        }
    }

    private void filterExtend(BaseDataDTO param, List<BaseDataDO> dataList) {
        if (dataList.isEmpty()) {
            return;
        }
        Map<String, BaseDataFilter> filterMap = BaseDataFilterService.getFilterMap();
        if (filterMap == null || filterMap.isEmpty()) {
            return;
        }
        boolean isFormula = StringUtils.hasText(param.getExpression());
        for (BaseDataFilter baseDataFilter : filterMap.values()) {
            if (!isFormula && baseDataFilter.getFilterType() == BaseDataOption.FilterType.FORMULA || !baseDataFilter.isEnable(param)) continue;
            baseDataFilter.filterList(param, dataList);
        }
    }

    static {
        sysSpeCol = new HashSet<String>();
        String[] fiexCols = new String[]{"id", "ver", "code", "objectcode", "unitcode", "stopflag", "recoveryflag", "validtime", "invalidtime", "parents", "searchKey"};
        Collections.addAll(sysSpeCol, fiexCols);
    }
}

