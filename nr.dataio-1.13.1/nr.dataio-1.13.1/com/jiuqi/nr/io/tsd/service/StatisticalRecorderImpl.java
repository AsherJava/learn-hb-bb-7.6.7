/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.data.common.service.StatisticalRecorder
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.io.tsd.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.data.common.service.StatisticalRecorder;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.io.record.bean.FormStatisticLog;
import com.jiuqi.nr.io.record.service.FormStatisticService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class StatisticalRecorderImpl
implements StatisticalRecorder {
    private final FormStatisticService formStatisticService;
    private final String recKey;
    private final Map<String, FormDefine> formDefineMap;
    private final String factoryId;
    private final List<FormStatisticLog> formStatisticLogs = new ArrayList<FormStatisticLog>();

    public void formRecord(String formKey, String desc) {
        if (this.recKey == null) {
            return;
        }
        FormStatisticLog formStatisticLog = new FormStatisticLog();
        formStatisticLog.setFormKey(formKey);
        formStatisticLog.setDesc(desc);
        formStatisticLog.setKey(UUIDUtils.getKey());
        formStatisticLog.setRecKey(this.recKey);
        formStatisticLog.setFactoryId(this.factoryId);
        FormDefine formDefine = this.formDefineMap.get(formKey);
        formStatisticLog.setFormOrder(formDefine.getOrder());
        this.formStatisticLogs.add(formStatisticLog);
        if (this.formStatisticLogs.size() == 1000) {
            this.flush();
        }
    }

    public StatisticalRecorderImpl(FormStatisticService formStatisticService, String recKey, String factoryId, List<FormDefine> formDefines) {
        this.formStatisticService = formStatisticService;
        this.recKey = recKey;
        this.factoryId = factoryId;
        this.formDefineMap = new HashMap<String, FormDefine>();
        if (formDefines == null) {
            return;
        }
        for (FormDefine formDefine : formDefines) {
            this.formDefineMap.put(formDefine.getKey(), formDefine);
        }
    }

    public void flush() {
        if (this.recKey == null) {
            return;
        }
        if (CollectionUtils.isEmpty(this.formStatisticLogs)) {
            return;
        }
        this.formStatisticService.saveStatisticLogs(this.formStatisticLogs);
        this.formStatisticLogs.clear();
    }
}

