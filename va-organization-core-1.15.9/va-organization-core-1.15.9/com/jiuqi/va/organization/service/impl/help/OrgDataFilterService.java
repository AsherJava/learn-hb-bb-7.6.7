/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.OrgContext
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataFilter
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$FilterType
 *  com.jiuqi.va.domain.org.OrgDataOption$SearchType
 *  com.jiuqi.va.domain.org.OrgDataSearchDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.organization.service.impl.help;

import com.jiuqi.va.domain.common.OrgContext;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataFilter;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.OrgDataSearchDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.organization.service.impl.help.OrgDataParamService;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="vaOrgDataFilterService")
public class OrgDataFilterService {
    @Autowired
    private OrgDataParamService orgDataParamService;
    private static Set<String> sysSpeCol = new HashSet<String>();
    private static Map<String, OrgDataFilter> filterMap;

    private static Map<String, OrgDataFilter> getFilterMap() {
        if (filterMap == null) {
            filterMap = ApplicationContextRegister.getBeansOfType(OrgDataFilter.class);
        }
        return filterMap;
    }

    public void filterList(List<OrgDO> dataList, OrgDTO param) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        Map<String, DataModelColumn> modelColsMap = this.orgDataParamService.loadModelCols(param.getTenantName(), param.getCategoryname());
        Set<String> modelCols = modelColsMap.keySet();
        for (DataModelColumn column : modelColsMap.values()) {
            if (!StringUtils.hasText(column.getDefaultVal())) continue;
            if (column.getColumnType() == DataModelType.ColumnType.INTEGER) {
                OrgContext.setDataContext((String)param.getCategoryname(), (String)"defaultVal", (String)column.getColumnName().toLowerCase(), (Object)Integer.parseInt(column.getDefaultVal()));
                continue;
            }
            if (column.getColumnType() == DataModelType.ColumnType.NUMERIC) {
                OrgContext.setDataContext((String)param.getCategoryname(), (String)"defaultVal", (String)column.getColumnName().toLowerCase(), (Object)new BigDecimal(column.getDefaultVal()));
                continue;
            }
            OrgContext.setDataContext((String)param.getCategoryname(), (String)"defaultVal", (String)column.getColumnName().toLowerCase(), (Object)column.getDefaultVal());
        }
        String searchKey = null;
        String[] searchKeyArr = null;
        if (StringUtils.hasText(param.getSearchKey()) && (searchKey = param.getSearchKey().trim().toUpperCase()).contains(" ")) {
            searchKeyArr = searchKey.split(" ");
        }
        HashMap<String, OrgDataSearchDTO> deepSearchMap = null;
        List deepSearch = param.getDeepSearch();
        if (deepSearch != null) {
            deepSearchMap = new HashMap<String, OrgDataSearchDTO>();
            for (OrgDataSearchDTO searchDTO : deepSearch) {
                deepSearchMap.put(searchDTO.getColumn().toLowerCase(), searchDTO);
            }
        }
        boolean isLazyLoad = param.isLazyLoad();
        String localColName = "name_" + param.getLanguage();
        if (!modelCols.contains(localColName)) {
            localColName = null;
        }
        HashSet<String> extFieldSet = new HashSet<String>();
        String paramKey = null;
        for (Map.Entry entry : param.entrySet()) {
            paramKey = (String)entry.getKey();
            if (sysSpeCol.contains(paramKey) || !modelCols.contains(paramKey) || param.get((Object)paramKey) == null || isLazyLoad && "parentcode".equals(paramKey)) continue;
            extFieldSet.add(paramKey);
        }
        HashSet codeScopeParam = null;
        List codeScope = param.getCodeScope();
        if (codeScope != null) {
            codeScopeParam = new HashSet(codeScope);
        }
        String dataCode = null;
        Object fieldVal = null;
        OrgDO data2 = null;
        Iterator<OrgDO> it = dataList.iterator();
        while (it.hasNext()) {
            data2 = it.next();
            if (!this.doSearch(data2, deepSearchMap, searchKeyArr, searchKey, localColName)) {
                it.remove();
                continue;
            }
            boolean addFlag = true;
            for (String field : extFieldSet) {
                fieldVal = data2.get((Object)field);
                if (fieldVal == null) {
                    addFlag = false;
                    break;
                }
                if (fieldVal instanceof Number) {
                    if (!(param.get((Object)field) instanceof Number)) {
                        param.put(field, (Object)new BigDecimal(param.get((Object)field).toString()));
                    }
                    if (((Number)fieldVal).doubleValue() == ((Number)param.get((Object)field)).doubleValue()) continue;
                    addFlag = false;
                    break;
                }
                if (fieldVal.toString().equals(param.get((Object)field).toString())) continue;
                addFlag = false;
            }
            if (!addFlag) {
                it.remove();
                continue;
            }
            dataCode = data2.getCode();
            if (codeScopeParam == null || codeScopeParam.contains(dataCode)) continue;
            it.remove();
        }
        this.filterAuth(param, dataList);
        this.filterExtend(param, dataList);
        if (dataList.isEmpty()) {
            return;
        }
        if (!isLazyLoad) {
            return;
        }
        String parentCodeParam = param.getParentcode();
        boolean hasParentCodeParam = StringUtils.hasText(parentCodeParam);
        boolean isLazyRoot = isLazyLoad && "-".equals(parentCodeParam);
        boolean isLazyParent = isLazyLoad && hasParentCodeParam;
        HashSet<String> parentFlag = new HashSet<String>();
        HashSet<String> codeFlag = new HashSet<String>();
        String dataParentCode = null;
        Iterator<OrgDO> it2 = dataList.iterator();
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
            if (!isLazyParent || isLazyRoot || parentCodeParam.equals(dataParentCode)) continue;
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
            for (OrgDO data2 : dataList) {
                data2.put("hasChildren", (Object)parentFlag.contains(data2.getCode()));
            }
        }
    }

    private boolean doSearch(OrgDO data, Map<String, OrgDataSearchDTO> deepSearchMap, String[] searchKeyArr, String searchKey, String localColName) {
        if (deepSearchMap != null) {
            return this.deepSearch(data, deepSearchMap);
        }
        if (searchKeyArr != null) {
            return this.multiKeywordSearch(data, searchKeyArr);
        }
        if (searchKey != null) {
            if (data.getOrgcode().toUpperCase().contains(searchKey)) {
                return true;
            }
            if (data.getName().toUpperCase().contains(searchKey)) {
                return true;
            }
            if (data.getShortname() != null && data.getShortname().toUpperCase().contains(searchKey)) {
                return true;
            }
            return localColName != null && data.get((Object)localColName) != null && data.get((Object)localColName).toString().toUpperCase().contains(searchKey);
        }
        return true;
    }

    private boolean deepSearch(OrgDO data, Map<String, OrgDataSearchDTO> deepSearchMap) {
        Object value = null;
        OrgDataSearchDTO searchDTO = null;
        for (Map.Entry<String, OrgDataSearchDTO> searchEntry : deepSearchMap.entrySet()) {
            value = data.get((Object)searchEntry.getKey());
            if (value == null) {
                return false;
            }
            searchDTO = searchEntry.getValue();
            if (searchDTO.getSearchType() == OrgDataOption.SearchType.EQUALS) {
                if (value.toString().equals(searchDTO.getValue())) continue;
                return false;
            }
            if (searchDTO.getSearchType() == OrgDataOption.SearchType.LIKE) {
                if (value.toString().toUpperCase().contains(searchDTO.getValue().toUpperCase())) continue;
                return false;
            }
            if (searchDTO.getSearchType() == OrgDataOption.SearchType.LIKE_IN) {
                for (String key : searchDTO.getValues()) {
                    if (value.toString().toUpperCase().contains(key.toUpperCase())) continue;
                    return false;
                }
                continue;
            }
            if (searchDTO.getSearchType() == OrgDataOption.SearchType.IN) {
                boolean flag = false;
                if (value instanceof List) {
                    for (String key : searchDTO.getValues()) {
                        if (!((List)value).contains(key)) continue;
                        flag = true;
                        break;
                    }
                } else {
                    for (String key : searchDTO.getValues()) {
                        if (!value.toString().equals(key)) continue;
                        flag = true;
                        break;
                    }
                }
                if (flag) continue;
                return false;
            }
            if (searchDTO.getSearchType() != OrgDataOption.SearchType.BETWEEN) continue;
            if (value instanceof Number) {
                if (value instanceof Integer) {
                    if (searchDTO.getBetweenNumericValues()[0] != null && (Integer)value < searchDTO.getBetweenNumericValues()[0].intValue()) {
                        return false;
                    }
                    if (searchDTO.getBetweenNumericValues()[1] == null || (Integer)value <= searchDTO.getBetweenNumericValues()[1].intValue()) continue;
                    return false;
                }
                if (value instanceof BigDecimal) {
                    if (searchDTO.getBetweenNumericValues()[0] != null && ((BigDecimal)value).compareTo(searchDTO.getBetweenNumericValues()[0]) < 0) {
                        return false;
                    }
                    if (searchDTO.getBetweenNumericValues()[1] == null || ((BigDecimal)value).compareTo(searchDTO.getBetweenNumericValues()[1]) <= 0) continue;
                    return false;
                }
                if (searchDTO.getBetweenNumericValues()[0] != null && ((Number)value).doubleValue() < searchDTO.getBetweenNumericValues()[0].doubleValue()) {
                    return false;
                }
                if (searchDTO.getBetweenNumericValues()[1] == null || !(((Number)value).doubleValue() > searchDTO.getBetweenNumericValues()[1].doubleValue())) continue;
                return false;
            }
            if (!(value instanceof Date)) continue;
            if (searchDTO.getBetweenDateValues()[0] != null && ((Date)value).before(searchDTO.getBetweenDateValues()[0])) {
                return false;
            }
            if (searchDTO.getBetweenDateValues()[1] == null || !((Date)value).after(searchDTO.getBetweenDateValues()[1])) continue;
            return false;
        }
        return true;
    }

    private boolean multiKeywordSearch(OrgDO data, String[] searchKeyArr) {
        String dataName = data.getName().toUpperCase();
        for (String key : searchKeyArr) {
            if (dataName.contains(key)) continue;
            return false;
        }
        return true;
    }

    private void filterAuth(OrgDTO param, List<OrgDO> dataList) {
        if (dataList.isEmpty() || param.getAuthType() == OrgDataOption.AuthType.NONE) {
            return;
        }
        Set orgAuthCodes = (Set)param.get((Object)"orgAuthCodes");
        if (orgAuthCodes == null) {
            return;
        }
        boolean onlyMarkAuth = param.isOnlyMarkAuth();
        if (orgAuthCodes.isEmpty() && !onlyMarkAuth) {
            dataList.clear();
            return;
        }
        if (onlyMarkAuth) {
            for (OrgDO data : dataList) {
                data.put("authMark", (Object)orgAuthCodes.contains(data.getCode()));
            }
            return;
        }
        OrgDO data = null;
        Iterator<OrgDO> it = dataList.iterator();
        while (it.hasNext()) {
            data = it.next();
            if (orgAuthCodes.contains(data.getCode())) continue;
            it.remove();
        }
    }

    private void filterExtend(OrgDTO param, List<OrgDO> dataList) {
        if (dataList.isEmpty()) {
            return;
        }
        Map<String, OrgDataFilter> filterMap = OrgDataFilterService.getFilterMap();
        if (filterMap == null || filterMap.size() == 0) {
            return;
        }
        boolean isFormula = StringUtils.hasText(param.getExpression());
        for (OrgDataFilter orgDataFilter : filterMap.values()) {
            if (!isFormula && orgDataFilter.getFilterType() == OrgDataOption.FilterType.FORMULA || !orgDataFilter.isEnable(param)) continue;
            orgDataFilter.filterList(param, dataList);
        }
    }

    static {
        String[] fiexCols = new String[]{"id", "ver", "code", "orgcode", "stopflag", "recoveryflag", "validtime", "invalidtime", "parents", "searchKey"};
        Collections.addAll(sysSpeCol, fiexCols);
    }
}

