/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.common.DataFieldMap
 *  com.jiuqi.nr.dataservice.core.common.DataFieldMappingConverter
 */
package com.jiuqi.nr.datacopy.param;

import com.jiuqi.nr.dataservice.core.common.DataFieldMap;
import com.jiuqi.nr.dataservice.core.common.DataFieldMappingConverter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFieldMappingConverterImpl
implements DataFieldMappingConverter {
    private Map<String, String> sourceTable2TargetTable;
    private Map<String, Map<String, String>> sourceTable2SourceFiled2TargetField;

    public DataFieldMappingConverterImpl(Map<String, String> sourceTable2TargetTable, Map<String, Map<String, String>> sourceTable2SourceFiled2TargetField) {
        this.sourceTable2TargetTable = sourceTable2TargetTable;
        this.sourceTable2SourceFiled2TargetField = sourceTable2SourceFiled2TargetField;
    }

    public Map<String, DataFieldMap> getDataFieldMapByTable(String dataTableCode, List<String> dataFieldCodes) {
        HashMap<String, DataFieldMap> dataFieldMapByTable = new HashMap<String, DataFieldMap>();
        String targetTableCode = this.sourceTable2TargetTable.get(dataTableCode);
        for (String dataFieldCode : dataFieldCodes) {
            String targetFieldCode = this.sourceTable2SourceFiled2TargetField.get(dataTableCode).get(dataFieldCode);
            DataFieldMap dataFieldMap = new DataFieldMap();
            dataFieldMap.setDataTableCode(targetTableCode);
            dataFieldMap.setFieldCode(targetFieldCode);
            dataFieldMapByTable.put(dataFieldCode, dataFieldMap);
        }
        return dataFieldMapByTable;
    }
}

