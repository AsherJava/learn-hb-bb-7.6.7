/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.datawarning.service;

import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datawarning.defines.DataWarningDefine;
import com.jiuqi.nr.datawarning.defines.DataWarningIdentify;
import com.jiuqi.nr.datawarning.defines.DataWarningType;
import com.jiuqi.nr.datawarning.service.IDataWarningTable;
import java.util.List;
import java.util.Map;

public interface IDataWarningExecutor {
    public void setIdentify(DataWarningIdentify var1);

    public void setWarningType(DataWarningType var1);

    public int setField(List<FieldDefine> var1);

    public void setFieldValues(Map<String, List<Object>> var1);

    public void setWarnigItems(List<DataWarningDefine> var1);

    public IDataWarningTable execute(IGroupingTable var1, Map<String, Integer> var2);

    public void setShowedFieldsIndex(Map<String, Integer> var1);
}

