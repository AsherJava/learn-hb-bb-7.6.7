/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.nr.basedata.select.param.BaseDataQueryInfo
 *  com.jiuqi.nr.basedata.select.param.BaseDataResponse
 *  com.jiuqi.nr.basedata.select.service.IBaseDataSelectService
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nr.basedata.select.param.BaseDataQueryInfo;
import com.jiuqi.nr.basedata.select.param.BaseDataResponse;
import com.jiuqi.nr.basedata.select.service.IBaseDataSelectService;
import com.jiuqi.nr.common.itree.ITree;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SortingMethod {
    private static final Logger logger = LoggerFactory.getLogger(SortingMethod.class);
    public static String moreThanEverage = "moreThanEverage";
    public static String lessThanEverage = "lessThanEverage";
    public static String topTen = "topTen";

    public StringBuffer sortingMethod(CellQueryInfo fieldQueryInfo, FieldData fieldData, ICommonQuery dataQuery, int columnIndex, JtableContext context) {
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
                cellFilterBuf = this.sortingMethodString(fieldQueryInfo, fieldData, dataQuery, columnIndex, context);
                break;
            }
            case FIELD_TYPE_DATE: {
                cellFilterBuf = this.sortingMethodDate(fieldQueryInfo, fieldData, dataQuery, columnIndex);
                break;
            }
            case FIELD_TYPE_FLOAT: 
            case FIELD_TYPE_INTEGER: 
            case FIELD_TYPE_DECIMAL: 
            case FIELD_TYPE_LOGIC: 
            case FIELD_TYPE_UUID: {
                cellFilterBuf = this.sortingMethodNum(fieldQueryInfo, fieldData, dataQuery, columnIndex);
                break;
            }
        }
        return cellFilterBuf;
    }

    private StringBuffer sortingMethodString(CellQueryInfo fieldQueryInfo, FieldData fieldDefine, ICommonQuery dataQuery, int columnIndex, JtableContext context) {
        StringBuffer cellFilterBufSb = new StringBuffer();
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        DataTable dataTable = runtimeDataSchemeService.getDataTable(fieldDefine.getOwnerTableKey());
        String tableName = dataTable.getCode();
        boolean existlike = false;
        String searchText = null;
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
                cellFilterBufSb.append(" " + tableName + "[" + fieldDefine.getFieldCode() + "] =  '" + cond.getOpValue() + "' ");
                continue;
            }
            if ("noteq".equals(stringFC)) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldDefine.getFieldCode() + "] !=  '" + cond.getOpValue() + "' ");
                continue;
            }
            if ("contain".equals(stringFC)) {
                cellFilterBufSb.append(" Position('" + cond.getOpValue() + "', " + tableName + "[" + fieldDefine.getFieldCode() + "])  > 0  ");
                continue;
            }
            if ("notcontain".equals(stringFC)) {
                cellFilterBufSb.append(" Position('" + cond.getOpValue() + "', " + tableName + "[" + fieldDefine.getFieldCode() + "])  < 1  ");
                continue;
            }
            if (!"like".equals(stringFC)) continue;
            existlike = true;
            searchText = cond.getOpValue();
        }
        List<String> outList = fieldQueryInfo.getOutList();
        List<String> inList = fieldQueryInfo.getInList();
        if (StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) {
            List entityDataKeys;
            if (outList != null && outList.size() > 0) {
                for (String value : outList) {
                    if (cellFilterBufSb.length() != 0) {
                        cellFilterBufSb.append(" and ");
                    }
                    cellFilterBufSb.append(tableName + "[" + fieldDefine.getFieldCode() + "] !=  '" + value + "' ");
                }
            }
            if (existlike && StringUtils.isNotEmpty(searchText) && (entityDataKeys = this.convertEntiyDataTextToKeys(context, fieldDefine.getDataLinkKey(), fieldDefine.getEntityKey(), searchText)) != null && entityDataKeys.size() > 0) {
                if (cellFilterBufSb.length() != 0) {
                    cellFilterBufSb.append(" and ");
                }
                cellFilterBufSb.append(" InList(");
                cellFilterBufSb.append(tableName).append("[").append(fieldDefine.getFieldCode()).append("],");
                entityDataKeys = entityDataKeys.stream().map(r -> "'" + r + "'").collect(Collectors.toList());
                cellFilterBufSb.append(String.join((CharSequence)",", entityDataKeys));
                cellFilterBufSb.append(")");
            }
            if (inList != null && inList.size() > 0) {
                for (String inValue : inList) {
                    if (cellFilterBufSb.length() != 0) {
                        cellFilterBufSb.append(" or ");
                    }
                    if (StringUtils.isEmpty((String)inValue)) {
                        cellFilterBufSb.append(tableName + "[" + fieldDefine.getFieldCode() + "] = '' OR " + tableName + "[" + fieldDefine.getFieldCode() + "] IS NULL");
                        continue;
                    }
                    cellFilterBufSb.append(tableName + "[" + fieldDefine.getFieldCode() + "] =  '" + inValue + "' ");
                }
            }
        } else if (outList != null && outList.size() > 0) {
            for (String value : outList) {
                if (cellFilterBufSb.length() != 0) {
                    cellFilterBufSb.append(" and ");
                }
                cellFilterBufSb.append(tableName + "[" + fieldDefine.getFieldCode() + "] !=  '" + value + "' ");
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
                dataQuery.setColumnFilterValueList(columnIndex, filteredValues);
            }
        }
        return cellFilterBufSb;
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
        BaseDataResponse baseDataResponse = iBaseDataSelectService.getBaseDataTree(baseDataQueryInfo);
        ITree baseDataTree = baseDataResponse.getBaseDataTree();
        List childrens = baseDataTree.getChildren();
        ArrayList<String> entityDataIds = new ArrayList<String>();
        if (childrens != null && childrens.size() > 0) {
            DataFormaterCache dataFormaterCache = new DataFormaterCache(jtableContext);
            dataFormaterCache.jsonData();
            LinkData link = iJtableParamService.getLink(linkKey);
            EnumLinkData enumLinkData = (EnumLinkData)link;
            for (ITree children : childrens) {
                boolean success = enumLinkData.checkFuzzyValue2(children.getKey(), dataFormaterCache, search);
                if (!success) continue;
                entityDataIds.add(children.getKey());
            }
            return entityDataIds;
        }
        return null;
    }

    private StringBuffer sortingMethodNum(CellQueryInfo fieldQueryInfo, FieldData fieldDefine, ICommonQuery dataQuery, int columnIndex) {
        StringBuffer cellFilterBufSb = new StringBuffer();
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        DataTable dataTable = runtimeDataSchemeService.getDataTable(fieldDefine.getOwnerTableKey());
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
                cellFilterBufSb.append(" " + tableName + "[" + fieldDefine.getFieldCode() + "]  =  " + cond.getOpValue() + " ");
                continue;
            }
            if ("noteq".equals(stringFC)) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldDefine.getFieldCode() + "]  !=  " + cond.getOpValue() + " ");
                continue;
            }
            if ("more".equals(stringFC)) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldDefine.getFieldCode() + "]  >  " + cond.getOpValue() + " ");
                continue;
            }
            if ("less".equals(stringFC)) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldDefine.getFieldCode() + "]  <  " + cond.getOpValue() + " ");
                continue;
            }
            if ("notless".equals(stringFC)) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldDefine.getFieldCode() + "]  >=  " + cond.getOpValue() + " ");
                continue;
            }
            if (!"notmore".equals(stringFC)) continue;
            cellFilterBufSb.append(" " + tableName + "[" + fieldDefine.getFieldCode() + "]  <=  " + cond.getOpValue() + " ");
        }
        if (fieldQueryInfo.getShortcuts() != null) {
            String[] stringa;
            if (moreThanEverage.equals(fieldQueryInfo.getShortcuts())) {
                if (cellFilterBufSb.length() != 0) {
                    cellFilterBufSb.append(" and ");
                }
                cellFilterBufSb.append(" " + tableName + "[" + fieldDefine.getFieldCode() + "]  > " + tableName + "[" + fieldDefine.getFieldCode() + ",avg] ");
            }
            if (lessThanEverage.equals(fieldQueryInfo.getShortcuts())) {
                if (cellFilterBufSb.length() != 0) {
                    cellFilterBufSb.append(" and ");
                }
                cellFilterBufSb.append(" " + tableName + "[" + fieldDefine.getFieldCode() + "]  < " + tableName + "[" + fieldDefine.getFieldCode() + ",avg] ");
            }
            if (topTen.equals((stringa = fieldQueryInfo.getShortcuts().split(";"))[0])) {
                dataQuery.setPagingInfo(Integer.parseInt(stringa[1]), 0);
            }
        }
        List<String> inList = fieldQueryInfo.getInList();
        ArrayList<Object> filteredValues = new ArrayList<Object>();
        if (inList.size() < 200) {
            for (String inValue : inList) {
                if (StringUtils.isEmpty((String)inValue)) {
                    filteredValues.add(null);
                    continue;
                }
                if (inValue.equals("true")) {
                    filteredValues.add(1);
                    continue;
                }
                if (inValue.equals("false")) {
                    filteredValues.add(0);
                    continue;
                }
                filteredValues.add(inValue);
            }
        }
        if (!filteredValues.isEmpty()) {
            dataQuery.setColumnFilterValueList(columnIndex, filteredValues);
        }
        return cellFilterBufSb;
    }

    private StringBuffer sortingMethodDate(CellQueryInfo fieldQueryInfo, FieldData fieldDefine, ICommonQuery dataQuery, int columnIndex) {
        StringBuffer cellFilterBufSb = new StringBuffer();
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        DataTable dataTable = runtimeDataSchemeService.getDataTable(fieldDefine.getOwnerTableKey());
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
                cellFilterBufSb.append(" " + tableName + "[" + fieldDefine.getFieldCode() + "]  = '" + cond.getOpValue() + "' ");
                continue;
            }
            if (stringFC.equals("noteq")) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldDefine.getFieldCode() + "]  != '" + cond.getOpValue() + "' ");
                continue;
            }
            if (stringFC.equals("more")) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldDefine.getFieldCode() + "]  > '" + cond.getOpValue() + "' ");
                continue;
            }
            if (stringFC.equals("less")) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldDefine.getFieldCode() + "]  < '" + cond.getOpValue() + "' ");
                continue;
            }
            if (stringFC.equals("notless")) {
                cellFilterBufSb.append(" " + tableName + "[" + fieldDefine.getFieldCode() + "]  >= '" + cond.getOpValue() + "' ");
                continue;
            }
            if (!stringFC.equals("notmore")) continue;
            cellFilterBufSb.append(" " + tableName + "[" + fieldDefine.getFieldCode() + "]  <= '" + cond.getOpValue() + "' ");
        }
        List<String> inList = fieldQueryInfo.getInList();
        ArrayList<Date> filteredValues = new ArrayList<Date>();
        if (inList.size() < 200) {
            for (String inValue : inList) {
                if (StringUtils.isEmpty((String)inValue)) {
                    filteredValues.add(null);
                    continue;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = sdf.parse(inValue);
                    filteredValues.add(date);
                }
                catch (ParseException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        if (!filteredValues.isEmpty()) {
            dataQuery.setColumnFilterValueList(columnIndex, filteredValues);
        }
        return cellFilterBufSb;
    }

    private String stringFormat(String s) {
        String sTwo = "";
        String sOne = s.toLowerCase();
        sTwo = sOne.replaceAll("[^a-zA-Z]", "");
        return sTwo;
    }
}

