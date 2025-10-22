/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.datascheme.internal.service;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.util.CollectionUtils;

public interface DataFieldService {
    public DataFieldDTO getDataField(String var1);

    public List<DataFieldDTO> getDataFields(List<String> var1);

    default public List<DataFieldDTO> getDataFields(String dataSchemeKey, List<String> keys) {
        return this.getDataFields(keys);
    }

    public List<DataFieldDTO> getAllDataField(String var1);

    public List<DataFieldDTO> getDataFieldByTable(String var1);

    default public List<DataFieldDTO> getDataFieldByTables(List<String> tableKeys) {
        if (CollectionUtils.isEmpty(tableKeys)) {
            return Collections.emptyList();
        }
        List<DataFieldDTO> dataFields = this.getDataFields(tableKeys);
        for (String tableKey : tableKeys) {
            dataFields.addAll(this.getDataFieldByTable(tableKey));
        }
        return dataFields;
    }

    public DataFieldDTO getDataFieldByTableKeyAndCode(String var1, String var2);

    public DataFieldDTO getZbKindDataFieldBySchemeKeyAndCode(String var1, String var2);

    public List<DataFieldDTO> getDataFieldByTableCode(String var1);

    public List<DataFieldDTO> getBizDataFieldByTableKey(String var1);

    public List<DataFieldDTO> getBizDataFieldByTableCode(String var1);

    public List<DataFieldDTO> getDataFieldByTableKeyAndType(String var1, DataFieldType ... var2);

    public List<DataFieldDTO> getDataFieldByTableKeyAndKind(String var1, DataFieldKind ... var2);

    public List<DataFieldDTO> getDataFieldByTableCodeAndType(String var1, DataFieldType ... var2);

    public List<DataFieldDTO> getDataFieldByTableCodeAndKind(String var1, DataFieldKind ... var2);

    public List<DataFieldDTO> getDataFieldBySchemeAndCode(String var1, String var2, DataFieldKind ... var3);

    public List<DataFieldDTO> getDataFieldBySchemeAndKind(String var1, DataFieldKind ... var2);

    public List<DataFieldDTO> searchField(FieldSearchQuery var1);

    public DataFieldDTO getDataFieldFromMdInfoByCode(String var1, String var2);

    default public DataFieldDTO findDataField(String dataSchemeKey, List<String> dataFieldKeys, Predicate<DataField> predicate) {
        return this.getDataFields(dataSchemeKey, dataFieldKeys).stream().filter(predicate).findFirst().orElse(null);
    }
}

