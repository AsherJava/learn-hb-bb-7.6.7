/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.inputdata.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class OffsetVchrItemUtils {
    public static final List<String> FILTER_FIELDS = ImmutableList.of((Object)"ID", (Object)"RECVER", (Object)"ACCTYEAR", (Object)"ACCTPERIOD", (Object)"CREATEUSER", (Object)"INPUTUNITID", (Object)"CREATETIME", (Object)"SRCTYPE", (Object)"OFFSETSTATE", (Object)"OFFSETTIME", (Object)"OFFSETPERSON", (Object)"OFFSETGROUPID", (Object[])new String[]{"UNIONRULEID", "TASKID", "SCHEMEID", "REPORTSYSTEMID", "DATATIME", "MD_CURRENCY", "OFFSETAMT", "DIFFAMT", "SUBJECTOBJ", "ORGCODE", "CONVERTSRCID", "CONVERTGROUPID", "UPDATETIME", "RECORDTIMESTAMP", "EFDCMOREFILTERFML", "FORMID", "MEMO", "BIZKEYORDER", "FLOATORDER", "MDCODE", "MD_GCORGTYPE", "OPPUNITID", "SUBJECTCODE", "CHECKSTATE", "CHECKAMT", "UNCHECKAMT", "CHECKTYPE", "CHECKTIME", "CHECKUSER", "CHECKGROUPID"});

    private OffsetVchrItemUtils() {
    }

    public static List getOffsetGroupingField(String systemId) {
        ArrayList result = Lists.newArrayList();
        try {
            DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
            ConsolidatedTaskService consolidatedTaskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
            List consolidatedTasks = consolidatedTaskService.getConsolidatedTasks(systemId);
            String tableName = "GC_INPUTDATATEMPLATE";
            if (!CollectionUtils.isEmpty((Collection)consolidatedTasks)) {
                String dataSchemeKey = ((ConsolidatedTaskVO)consolidatedTasks.get(0)).getDataScheme();
                InputDataNameProvider inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
                tableName = inputDataNameProvider.getTableNameByDataSchemeKey(dataSchemeKey);
            }
            TableModelDefine tableModelDefine = dataModelService.getTableModelDefineByName(tableName);
            List allFieldsInTable = dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
            allFieldsInTable.forEach(entity -> {
                if (FILTER_FIELDS.contains(entity.getCode())) {
                    return;
                }
                HashMap<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("key", entity.getCode());
                resultMap.put("label", entity.getTitle());
                boolean isNumber = false;
                if (ColumnModelType.BIGDECIMAL.equals((Object)entity.getColumnType()) || ColumnModelType.DOUBLE.equals((Object)entity.getColumnType())) {
                    isNumber = true;
                }
                resultMap.put("isNumber", isNumber);
                result.add(resultMap);
            });
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u62b5\u6d88\u5206\u7ec4\u5b57\u6bb5\u5931\u8d25", (Throwable)e);
        }
        return result;
    }
}

