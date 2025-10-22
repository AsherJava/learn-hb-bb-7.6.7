/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datapartnerapi.domain.FieldGrowthData
 *  com.jiuqi.nr.datapartnerapi.domain.FieldInfo
 *  com.jiuqi.nr.datapartnerapi.domain.FormInfo
 *  com.jiuqi.nr.datapartnerapi.domain.TaskInfo
 */
package com.jiuqi.nr.datapartnerapi.service;

import com.jiuqi.nr.datapartnerapi.domain.FieldGrowthData;
import com.jiuqi.nr.datapartnerapi.domain.FieldGrowthDataQueryDTO;
import com.jiuqi.nr.datapartnerapi.domain.FieldInfo;
import com.jiuqi.nr.datapartnerapi.domain.FormAuthorityFilterDTO;
import com.jiuqi.nr.datapartnerapi.domain.FormDataQueryDTO;
import com.jiuqi.nr.datapartnerapi.domain.FormInfo;
import com.jiuqi.nr.datapartnerapi.domain.TaskInfo;
import java.util.List;
import java.util.Map;

public interface FormDataApiService {
    public List<TaskInfo> queryAllForms(String var1, String var2);

    public TaskInfo queryTaskByKey(String var1);

    public FormInfo queryFormByKey(String var1);

    public List<FieldInfo> queryFormFields(String var1);

    public byte[] downloadFormStyle(FormInfo var1);

    public List<String> getAccessForms(FormAuthorityFilterDTO var1);

    public byte[] exportTaskData(FormDataQueryDTO var1);

    public Map<String, FieldGrowthData> queryFieldsGrowthData(FieldGrowthDataQueryDTO var1);
}

