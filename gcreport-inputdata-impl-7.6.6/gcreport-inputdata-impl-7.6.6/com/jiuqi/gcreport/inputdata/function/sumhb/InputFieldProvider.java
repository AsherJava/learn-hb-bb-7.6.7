/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.inputdata.function.sumhb;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputFieldProvider {
    public static Map<String, String> getFieldCodeAndFieldNameMapping(String taskId) {
        List<ColumnModelDefine> fieldDefines = InputFieldProvider.getFieldDefines(taskId);
        HashMap<String, String> fieldCodeAndNameMapping = new HashMap<String, String>(128);
        fieldDefines.forEach(fieldDefine -> fieldCodeAndNameMapping.put(fieldDefine.getCode(), fieldDefine.getName()));
        return fieldCodeAndNameMapping;
    }

    public static Map<String, String> getFieldNameAndCodeMapping(String taskId) {
        List<ColumnModelDefine> fieldDefines = InputFieldProvider.getFieldDefines(taskId);
        HashMap<String, String> fieldCodeAndNameMapping = new HashMap<String, String>(128);
        fieldDefines.forEach(fieldDefine -> fieldCodeAndNameMapping.put(fieldDefine.getName(), fieldDefine.getCode()));
        return fieldCodeAndNameMapping;
    }

    private static List<ColumnModelDefine> getFieldDefines(String taskId) {
        InputDataNameProvider inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
        String tableName = inputDataNameProvider.getTableNameByTaskId(taskId);
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        TableModelDefine inputDataTableDefine = dataModelService.getTableModelDefineByName(tableName);
        return dataModelService.getColumnModelDefinesByTable(inputDataTableDefine.getID());
    }
}

