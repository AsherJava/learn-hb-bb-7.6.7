/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.nr.basedata.select.param.BaseDataQueryInfo
 *  com.jiuqi.nr.basedata.select.param.BaseDataResponse
 *  com.jiuqi.nr.basedata.select.service.IBaseDataSelectService
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.datacrud.spi.filter.FormulaFilter
 *  com.jiuqi.nr.datacrud.spi.filter.InValuesFilter
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nr.basedata.select.param.BaseDataQueryInfo;
import com.jiuqi.nr.basedata.select.param.BaseDataResponse;
import com.jiuqi.nr.basedata.select.service.IBaseDataSelectService;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.filter.FormulaFilter;
import com.jiuqi.nr.datacrud.spi.filter.InValuesFilter;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.input.CellQueryInfo;
import com.jiuqi.nr.jtable.params.input.FilterCondition;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.SortingMethod;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterMethod {
    private static final Logger logger = LoggerFactory.getLogger(SortingMethod.class);
    public static String moreThanEverage = "moreThanEverage";
    public static String lessThanEverage = "lessThanEverage";
    public static String topTen = "topTen";

    public void filterMethod(CellQueryInfo fieldQueryInfo, QueryInfoBuilder queryInfoBuilder, JtableContext context, FieldData fieldData) {
        StringBuffer cellFilterBuf = new StringBuffer();
        String filter = fieldQueryInfo.getFilterFormula();
        if (StringUtils.isNotEmpty((String)filter)) {
            if (cellFilterBuf.length() == 0) {
                cellFilterBuf.append(filter);
            } else {
                cellFilterBuf.append(" AND " + filter);
            }
        }
        FieldType type = FieldType.forValue((int)fieldData.getFieldType());
        switch (type) {
            case FIELD_TYPE_STRING: {
                this.filterMethodString(fieldQueryInfo, fieldData, queryInfoBuilder, context);
                break;
            }
            case FIELD_TYPE_DATE: {
                this.filterMethodDate(fieldQueryInfo, fieldData, queryInfoBuilder);
                break;
            }
            case FIELD_TYPE_FLOAT: 
            case FIELD_TYPE_INTEGER: 
            case FIELD_TYPE_DECIMAL: 
            case FIELD_TYPE_LOGIC: 
            case FIELD_TYPE_UUID: {
                this.filterMethodNum(fieldQueryInfo, fieldData, queryInfoBuilder);
                break;
            }
        }
    }

    private void filterMethodString(CellQueryInfo fieldQueryInfo, FieldData fieldData, QueryInfoBuilder queryInfoBuilder, JtableContext context) {
        StringBuffer cellFilterBufSb = new StringBuffer();
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        DataTable dataTable = runtimeDataSchemeService.getDataTable(fieldData.getOwnerTableKey());
        String tableName = dataTable.getCode();
        boolean existlike = false;
        String searchText = null;
        for (FilterCondition cond : fieldQueryInfo.getOpList()) {
            String stringF = this.stringFormat(fieldQueryInfo.getAttendedMode());
            String string = this.stringFormat(cond.getOpCode());
            if (StringUtils.isEmpty((String)string)) continue;
            if (cellFilterBufSb.length() != 0 && "and".equals(stringF)) {
                cellFilterBufSb.append(" and ");
            } else if (cellFilterBufSb.length() != 0 && "or".equals(stringF)) {
                cellFilterBufSb.append(" or ");
            }
            if ("eq".equals(string)) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldData.getFieldCode() + "] =  '" + cond.getOpValue() + "' ");
                continue;
            }
            if ("noteq".equals(string)) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldData.getFieldCode() + "] !=  '" + cond.getOpValue() + "' ");
                continue;
            }
            if ("contain".equals(string)) {
                cellFilterBufSb.append(" Position('" + cond.getOpValue() + "', " + tableName + "[" + fieldData.getFieldCode() + "])  > 0  ");
                continue;
            }
            if ("notcontain".equals(string)) {
                cellFilterBufSb.append(" Position('" + cond.getOpValue() + "', " + tableName + "[" + fieldData.getFieldCode() + "])  < 1  ");
                continue;
            }
            if (!"like".equals(string)) continue;
            existlike = true;
            searchText = cond.getOpValue();
        }
        List<String> outList = fieldQueryInfo.getOutList();
        List<String> inList = fieldQueryInfo.getInList();
        if (StringUtils.isNotEmpty((String)fieldData.getEntityKey())) {
            Object entityDataKeys;
            if (outList != null && outList.size() > 0) {
                for (String string : outList) {
                    if (cellFilterBufSb.length() != 0) {
                        cellFilterBufSb.append(" and ");
                    }
                    cellFilterBufSb.append(tableName + "[" + fieldData.getFieldCode() + "] !=  '" + string + "' ");
                }
            }
            if (existlike && StringUtils.isNotEmpty(searchText) && fieldQueryInfo.isSelectOut() && (entityDataKeys = this.convertEntiyDataTextToKeys(context, fieldData.getDataLinkKey(), fieldData.getEntityKey(), searchText)) != null && entityDataKeys.size() > 0) {
                ArrayList<String> arrayList = new ArrayList<String>();
                Iterator<Object> iterator = entityDataKeys.iterator();
                while (iterator.hasNext()) {
                    String entityDataKey = (String)iterator.next();
                    if (StringUtils.isEmpty((String)entityDataKey)) {
                        arrayList.add(null);
                        arrayList.add("");
                        continue;
                    }
                    arrayList.add(entityDataKey);
                }
                if (inList != null && inList.size() > 0) {
                    for (String inValue : inList) {
                        if (StringUtils.isEmpty((String)inValue)) {
                            arrayList.add(null);
                            arrayList.add("");
                            continue;
                        }
                        arrayList.add(inValue);
                    }
                }
                if (!arrayList.isEmpty()) {
                    InValuesFilter inValuesFilter = new InValuesFilter(fieldData.getDataLinkKey(), arrayList);
                    queryInfoBuilder.where((RowFilter)inValuesFilter);
                }
            }
            if (!fieldQueryInfo.isSelectOut() && inList != null && inList.size() > 0) {
                for (String string : inList) {
                    if (cellFilterBufSb.length() != 0) {
                        cellFilterBufSb.append(" or ");
                    }
                    if (StringUtils.isEmpty((String)string)) {
                        cellFilterBufSb.append(tableName + "[" + fieldData.getFieldCode() + "] = '' OR " + tableName + "[" + fieldData.getFieldCode() + "] IS NULL");
                        continue;
                    }
                    cellFilterBufSb.append(tableName + "[" + fieldData.getFieldCode() + "] =  '" + string + "' ");
                }
            }
        } else if (outList != null && outList.size() > 0) {
            for (String string : outList) {
                if (cellFilterBufSb.length() != 0) {
                    cellFilterBufSb.append(" and ");
                }
                cellFilterBufSb.append(tableName + "[" + fieldData.getFieldCode() + "] !=  '" + string + "' ");
            }
        } else {
            ArrayList<String> filteredValues = new ArrayList<String>();
            if (inList.size() < 200) {
                for (String inValue : inList) {
                    if (StringUtils.isEmpty((String)inValue)) {
                        filteredValues.add(null);
                        filteredValues.add("");
                        continue;
                    }
                    filteredValues.add(inValue);
                }
            }
            if (!filteredValues.isEmpty()) {
                InValuesFilter inValuesFilter = new InValuesFilter(fieldData.getDataLinkKey(), filteredValues);
                queryInfoBuilder.where((RowFilter)inValuesFilter);
            }
        }
        if (StringUtils.isNotEmpty((String)cellFilterBufSb.toString())) {
            FormulaFilter formulaFilter = new FormulaFilter(cellFilterBufSb.toString());
            queryInfoBuilder.where((RowFilter)formulaFilter);
        }
    }

    private List<String> convertEntiyDataTextToKeys(JtableContext jtableContext, String linkKey, String entityKey, String search) {
        IBaseDataSelectService iBaseDataSelectService = (IBaseDataSelectService)BeanUtil.getBean(IBaseDataSelectService.class);
        IJtableParamService iJtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        BaseDataQueryInfo baseDataQueryInfo = new BaseDataQueryInfo();
        baseDataQueryInfo.setTaskKey(jtableContext.getTaskKey());
        baseDataQueryInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
        baseDataQueryInfo.setDimensionSet(jtableContext.getDimensionSet());
        baseDataQueryInfo.setEntityKey(entityKey);
        baseDataQueryInfo.setAllChildren(true);
        LinkData link = iJtableParamService.getLink(linkKey);
        EnumLinkData enumLinkData = (EnumLinkData)link;
        baseDataQueryInfo.setEntityAuth(enumLinkData.isEntityAuth());
        BaseDataResponse baseDataResponse = iBaseDataSelectService.getBaseDataTree(baseDataQueryInfo);
        ITree baseDataTree = baseDataResponse.getBaseDataTree();
        List childrens = baseDataTree.getChildren();
        ArrayList<String> entityDataIds = new ArrayList<String>();
        if (childrens != null && childrens.size() > 0) {
            DataFormaterCache dataFormaterCache = new DataFormaterCache(jtableContext);
            dataFormaterCache.jsonData();
            for (ITree children : childrens) {
                boolean success = enumLinkData.checkFuzzyValue2(children.getKey(), dataFormaterCache, search);
                if (!success) continue;
                entityDataIds.add(children.getKey());
            }
            return entityDataIds;
        }
        return null;
    }

    private void filterMethodNum(CellQueryInfo fieldQueryInfo, FieldData fieldData, QueryInfoBuilder queryInfoBuilder) {
        StringBuffer cellFilterBufSb = new StringBuffer();
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        DataTable dataTable = runtimeDataSchemeService.getDataTable(fieldData.getOwnerTableKey());
        String tableName = dataTable.getCode();
        for (FilterCondition cond : fieldQueryInfo.getOpList()) {
            String stringF = this.stringFormat(fieldQueryInfo.getAttendedMode());
            String stringFC = this.stringFormat(cond.getOpCode());
            if (StringUtils.isEmpty((String)stringFC)) continue;
            if (cellFilterBufSb.length() != 0 && "and".equals(stringF)) {
                cellFilterBufSb.append(" and ");
            } else if (cellFilterBufSb.length() != 0 && "or".equals(stringF)) {
                cellFilterBufSb.append(" or ");
            }
            if ("eq".equals(stringFC)) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldData.getFieldCode() + "]  =  " + cond.getOpValue() + " ");
                continue;
            }
            if ("noteq".equals(stringFC)) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldData.getFieldCode() + "]  !=  " + cond.getOpValue() + " ");
                continue;
            }
            if ("more".equals(stringFC)) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldData.getFieldCode() + "]  >  " + cond.getOpValue() + " ");
                continue;
            }
            if ("less".equals(stringFC)) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldData.getFieldCode() + "]  <  " + cond.getOpValue() + " ");
                continue;
            }
            if ("notless".equals(stringFC)) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldData.getFieldCode() + "]  >=  " + cond.getOpValue() + " ");
                continue;
            }
            if (!"notmore".equals(stringFC)) continue;
            cellFilterBufSb.append(" " + tableName + "[" + fieldData.getFieldCode() + "]  <=  " + cond.getOpValue() + " ");
        }
        if (fieldQueryInfo.getShortcuts() != null) {
            String[] stringa;
            if (moreThanEverage.equals(fieldQueryInfo.getShortcuts())) {
                if (cellFilterBufSb.length() != 0) {
                    cellFilterBufSb.append(" and ");
                }
                cellFilterBufSb.append(" " + tableName + "[" + fieldData.getFieldCode() + "]  > " + tableName + "[" + fieldData.getFieldCode() + ",avg] ");
            }
            if (lessThanEverage.equals(fieldQueryInfo.getShortcuts())) {
                if (cellFilterBufSb.length() != 0) {
                    cellFilterBufSb.append(" and ");
                }
                cellFilterBufSb.append(" " + tableName + "[" + fieldData.getFieldCode() + "]  < " + tableName + "[" + fieldData.getFieldCode() + ",avg] ");
            }
            if (topTen.equals((stringa = fieldQueryInfo.getShortcuts().split(";"))[0])) {
                PageInfo pageInfo = new PageInfo();
                pageInfo.setRowsPerPage(Integer.parseInt(stringa[1]));
                pageInfo.setPageIndex(0);
                queryInfoBuilder.setPage(pageInfo);
            }
        }
        List<String> inList = fieldQueryInfo.getInList();
        ArrayList<String> filteredValues = new ArrayList<String>();
        if (inList.size() < 200) {
            for (String inValue : inList) {
                if (StringUtils.isEmpty((String)inValue)) {
                    filteredValues.add(null);
                    filteredValues.add("");
                    continue;
                }
                if (inValue.equals("true")) {
                    filteredValues.add(Integer.toString(1));
                    continue;
                }
                if (inValue.equals("false")) {
                    filteredValues.add(Integer.toString(0));
                    continue;
                }
                filteredValues.add(inValue);
            }
        }
        if (!filteredValues.isEmpty()) {
            InValuesFilter inValuesFilter = new InValuesFilter(fieldData.getDataLinkKey(), filteredValues);
            queryInfoBuilder.where((RowFilter)inValuesFilter);
        }
        if (StringUtils.isNotEmpty((String)cellFilterBufSb.toString())) {
            FormulaFilter formulaFilter = new FormulaFilter(cellFilterBufSb.toString());
            queryInfoBuilder.where((RowFilter)formulaFilter);
        }
    }

    private void filterMethodDate(CellQueryInfo fieldQueryInfo, FieldData fieldData, QueryInfoBuilder queryInfoBuilder) {
        StringBuffer cellFilterBufSb = new StringBuffer();
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        DataTable dataTable = runtimeDataSchemeService.getDataTable(fieldData.getOwnerTableKey());
        String tableName = dataTable.getCode();
        for (FilterCondition cond : fieldQueryInfo.getOpList()) {
            String stringF = this.stringFormat(fieldQueryInfo.getAttendedMode());
            String stringFC = this.stringFormat(cond.getOpCode());
            if (StringUtils.isEmpty((String)stringFC)) continue;
            if (cellFilterBufSb.length() != 0 && stringF.equals("and")) {
                cellFilterBufSb.append(" and ");
            } else if (cellFilterBufSb.length() != 0 && stringF.equals("or")) {
                cellFilterBufSb.append(" or ");
            }
            if (stringFC.equals("eq")) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldData.getFieldCode() + "]  = '" + cond.getOpValue() + "' ");
                continue;
            }
            if (stringFC.equals("noteq")) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldData.getFieldCode() + "]  != '" + cond.getOpValue() + "' ");
                continue;
            }
            if (stringFC.equals("more")) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldData.getFieldCode() + "]  > '" + cond.getOpValue() + "' ");
                continue;
            }
            if (stringFC.equals("less")) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldData.getFieldCode() + "]  < '" + cond.getOpValue() + "' ");
                continue;
            }
            if (stringFC.equals("notless")) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldData.getFieldCode() + "]  >= '" + cond.getOpValue() + "' ");
                continue;
            }
            if (!stringFC.equals("notmore")) continue;
            cellFilterBufSb.append(" " + tableName + "[" + fieldData.getFieldCode() + "]  <= '" + cond.getOpValue() + "' ");
        }
        List<String> inList = fieldQueryInfo.getInList();
        ArrayList<String> filteredValues = new ArrayList<String>();
        if (inList.size() < 200) {
            for (String inValue : inList) {
                if (StringUtils.isEmpty((String)inValue)) {
                    filteredValues.add(null);
                    filteredValues.add("");
                    continue;
                }
                filteredValues.add(inValue);
            }
        }
        if (!filteredValues.isEmpty()) {
            InValuesFilter inValuesFilter = new InValuesFilter(fieldData.getDataLinkKey(), filteredValues);
            queryInfoBuilder.where((RowFilter)inValuesFilter);
        }
        if (StringUtils.isNotEmpty((String)cellFilterBufSb.toString())) {
            FormulaFilter formulaFilter = new FormulaFilter(cellFilterBufSb.toString());
            queryInfoBuilder.where((RowFilter)formulaFilter);
        }
    }

    private String stringFormat(String s) {
        String sTwo = "";
        String sOne = s.toLowerCase();
        sTwo = sOne.replaceAll("[^a-zA-Z]", "");
        return sTwo;
    }
}

