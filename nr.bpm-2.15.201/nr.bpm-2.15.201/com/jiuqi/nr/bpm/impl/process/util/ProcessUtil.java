/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService
 */
package com.jiuqi.nr.bpm.impl.process.util;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySet;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.process.ProcessTask;
import com.jiuqi.nr.bpm.impl.process.consts.TaskEnum;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ProcessUtil {
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    CustomWorkFolwService customWorkFolwServiceImpl;
    @Autowired
    WorkflowSettingService workflowSettingService;
    @Autowired
    IRuntimeFormService iRuntimeFormService;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private IDimensionProvider iDimensionProvider;

    public Optional<Task> convertToTask(UserTask userTask) {
        List<TaskEnum> taskEnums = Arrays.asList(TaskEnum.values());
        List tasks = taskEnums.stream().filter(e -> e.getTaskId().equals(userTask.getId())).collect(Collectors.toList());
        return Optional.ofNullable(new ProcessTask((TaskEnum)((Object)tasks.stream().findFirst().get()), null));
    }

    public DimensionValueSet buildUploadMasterKey(MasterEntityInfo masterEntity, String formKey, String period) {
        DimensionValueSet dimensionValueSet = this.nrParameterUtils.convertDimensionName(masterEntity, period);
        if (formKey != null) {
            dimensionValueSet.setValue("FORMID", (Object)formKey);
        }
        return dimensionValueSet;
    }

    public DimensionValueSet buildUploadMasterKey(String dimName, String unitId, String formKey, String perid) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(dimName, (Object)unitId);
        dimensionValueSet.setValue("DATATIME", (Object)perid);
        if (formKey != null) {
            dimensionValueSet.setValue("FORMID", (Object)formKey);
        }
        return dimensionValueSet;
    }

    public DimensionValueSet buildUploadMasterKey(BusinessKey businessKeyInfo) {
        DimensionValueSet dimensionValueSet = this.nrParameterUtils.convertDimensionName(businessKeyInfo);
        this.nrParameterUtils.addFormKeyToMasterKeys(dimensionValueSet, businessKeyInfo, businessKeyInfo.getFormKey());
        return dimensionValueSet;
    }

    public DimensionValueSet buildBatchMasterKey(MasterEntitySet masterEntitySet, BusinessKeySet businessKeySet) {
        masterEntitySet.reset();
        DimensionValueSet masterKey = new DimensionValueSet();
        Collection<String> tableNames = null;
        if (masterEntitySet.next()) {
            tableNames = masterEntitySet.getCurrent().getDimessionNames();
        }
        HashMap dimvalues = new HashMap();
        masterEntitySet.reset();
        ExecutorContext context = new ExecutorContext(this.dataDefinitionController);
        while (masterEntitySet.next()) {
            MasterEntityInfo masterEntity = masterEntitySet.getCurrent();
            for (String table : tableNames) {
                String dimensionName = this.iDimensionProvider.getDimensionNameByEntityTableCode(context, table);
                if (dimensionName == null || dimensionName == "DATATIME") continue;
                if (dimvalues.containsKey(dimensionName)) {
                    ((List)dimvalues.get(dimensionName)).add(masterEntity.getMasterEntityKey(table));
                    continue;
                }
                ArrayList<String> val = new ArrayList<String>();
                val.add(masterEntity.getMasterEntityKey(table));
                dimvalues.put(dimensionName, val);
            }
        }
        for (Map.Entry entry : dimvalues.entrySet()) {
            List value = (List)entry.getValue();
            if (value.isEmpty()) continue;
            if (value.size() == 1) {
                masterKey.setValue((String)entry.getKey(), value.get(0));
                continue;
            }
            masterKey.setValue((String)entry.getKey(), (Object)value);
        }
        masterKey.setValue("DATATIME", (Object)businessKeySet.getPeriod());
        if (!CollectionUtils.isEmpty(businessKeySet.getFormKey())) {
            masterKey.setValue("FORMID", new ArrayList<String>(businessKeySet.getFormKey()));
        }
        return masterKey;
    }
}

