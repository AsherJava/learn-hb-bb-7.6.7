/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.inputdata.query.constant;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.query.constant.EntitiesOrgMode;
import com.jiuqi.gcreport.inputdata.query.constant.InnerTableTabsType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class FInnerTableTabsDoc {
    private static String OFFSET_TABLE_NAME = "GC_OFFSETVCHRITEM";
    private static Map<String, Object> fieldRefMap = new HashMap<String, Object>();
    private static DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);

    public static Map<String, String> initRefField() {
        HashMap<String, String> refFields = new HashMap<String, String>();
        refFields.put("OFFSETGROUPID", " %1$s.SRCOFFSETGROUPID ");
        refFields.put("AMT", " %1$s.SUBJECTORIENT * (%1$s.OFFSET_DEBIT - %1$s.OFFSET_CREDIT) ");
        refFields.put("OFFSETAMT", " %1$s.SUBJECTORIENT * (%1$s.OFFSET_DEBIT - %1$s.OFFSET_CREDIT) ");
        refFields.put("DIFFAMT", " %1$s.SUBJECTORIENT * (%1$s.DIFFD - %1$s.DIFFC) ");
        refFields.put("BIZKEYORDER", " %1$s.ID ");
        refFields.put("SUBJECTOBJ", " concat(concat(%1$s.SUBJECTCODE,'||'),%1$s.SYSTEMID) ");
        refFields.put("MDCODE", " %1$s.UNITID ");
        refFields.put("ORGCODE", " %1$s.UNITID ");
        refFields.put("OFFSETSTATE", " N'1' ");
        refFields.put("MD_GCADJTYPE", " N'BEFOREADJ'");
        refFields.put("MD_CURRENCY", " %1$s.OFFSETCURR ");
        refFields.put("FLOATORDER", " 0 ");
        return refFields;
    }

    public static void initQueryField(String tableName) {
        String key = " " + tableName + " ";
        Map<String, String> refFields = FInnerTableTabsDoc.initRefField();
        Map<String, ColumnModelDefine> offsetFields = FInnerTableTabsDoc.getFieldDefinesByTableName(OFFSET_TABLE_NAME);
        Map<String, ColumnModelDefine> inputFields = FInnerTableTabsDoc.getFieldDefinesByTableName(tableName);
        if (!fieldRefMap.containsKey(key)) {
            fieldRefMap.put(key, " " + OFFSET_TABLE_NAME + " ");
        }
        inputFields.forEach((fieldName, value) -> {
            ColumnModelDefine offsetField = (ColumnModelDefine)offsetFields.get(fieldName);
            if (offsetField == null) {
                String ref = (String)refFields.get(fieldName);
                if (StringUtils.isEmpty((String)ref)) {
                    ref = " null ";
                }
                if (!fieldRefMap.containsKey(" %1$s." + fieldName + " ")) {
                    fieldRefMap.put(" %1$s." + fieldName + " ", ref);
                }
            }
        });
    }

    private static Map<String, ColumnModelDefine> getFieldDefinesByTableName(String tableName) {
        TableModelDefine tableModelDefine = dataModelService.getTableModelDefineByName(tableName);
        if (tableModelDefine == null) {
            return Collections.emptyMap();
        }
        try {
            return dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID()).stream().collect(Collectors.toMap(ColumnModelDefine::getName, f -> f));
        }
        catch (Exception e) {
            Object[] args = new Object[]{tableName};
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.notfielddefinemsg", (Object[])args) + e.getMessage(), e);
        }
    }

    public static Map<String, String> getInputDataFieldRefMap(EntitiesOrgMode mode, InnerTableTabsType type, String parent, String tableName) {
        HashMap<String, String> refMap;
        block7: {
            block5: {
                block6: {
                    refMap = new HashMap<String, String>();
                    if (mode != EntitiesOrgMode.UNIONORG) break block5;
                    if (type != InnerTableTabsType.DIFFERENCE) break block6;
                    refMap.put(" %1$s.AMT ", " %1$s.DIFFAMT ");
                    break block7;
                }
                if (type != InnerTableTabsType.DETAILDATA) break block7;
                refMap.put(" %1$s.AMT ", " (case when SUBSTR(ORG2.PARENTS, 1, " + parent.length() + ") != '" + parent + "' then %1$s.AMT when %1$s.DIFFAMT <> 0  then %1$s.DIFFAMT else %1$s.AMT end)");
                break block7;
            }
            if (mode == EntitiesOrgMode.DIFFERENCE && (type == InnerTableTabsType.OFFSET || type == InnerTableTabsType.ALLDATA || type == InnerTableTabsType.DETAILDATA)) {
                Map<String, ColumnModelDefine> inputFields = FInnerTableTabsDoc.getFieldDefinesByTableName(tableName);
                if (CollectionUtils.isEmpty(inputFields)) {
                    Object[] args = new Object[]{tableName};
                    throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.notfielddefinemsg", (Object[])args));
                }
                for (ColumnModelDefine fieldDefine : inputFields.values()) {
                    if (StringUtils.isEmpty((String)fieldDefine.getName()) || fieldDefine.getColumnType() == null || !FInnerTableTabsDoc.isNumberType(fieldDefine.getColumnType()) || FInnerTableTabsDoc.dislodgeField().contains(fieldDefine.getName())) continue;
                    if (type == InnerTableTabsType.OFFSET && "AMT".equals(fieldDefine.getName())) {
                        refMap.put(" %1$s.AMT ", " -%1$s.OFFSETAMT ");
                        continue;
                    }
                    if ((type == InnerTableTabsType.ALLDATA || type == InnerTableTabsType.DETAILDATA) && "AMT".equals(fieldDefine.getName())) {
                        refMap.put(" %1$s.AMT ", " (case when %1$s.OFFSETSTATE='1' then -%1$s.OFFSETAMT else -%1$s.AMT end) ");
                        continue;
                    }
                    refMap.put(" %1$s." + fieldDefine.getName() + " ", " -%1$s." + fieldDefine.getName() + " ");
                }
            }
        }
        return refMap;
    }

    private static boolean isNumberType(ColumnModelType fieldType) {
        return EnumSet.of(ColumnModelType.DOUBLE, ColumnModelType.INTEGER, ColumnModelType.BIGDECIMAL).contains(fieldType);
    }

    public static Map<String, String> getOffsetFieldRefMap(boolean useDna) {
        HashMap<String, String> refMap = new HashMap<String, String>();
        if (!fieldRefMap.isEmpty()) {
            fieldRefMap.forEach((k, v) -> {
                String val = v instanceof UUID ? (useDna ? String.format(" '%1$s' ", v) : String.format(" HEXTORAW('%1$s') ", v)) : String.valueOf(v);
                refMap.put((String)k, val);
            });
        }
        return refMap;
    }

    private static List<String> dislodgeField() {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("FLOATORDER");
        fields.add("ACCTYEAR");
        fields.add("ACCTPERIOD");
        fields.add("SRCTYPE");
        fields.add("DC");
        return fields;
    }
}

