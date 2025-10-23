/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.service.ITaskService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.io.util;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.service.ITaskService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.util.DefaultUploadState_1_0;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessIOUtil {
    @Autowired
    private ITaskService taskService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;

    public List<DataDimension> getAllReportDimensions(String taskKey) {
        ArrayList<DataDimension> rptDimensionDefines = new ArrayList<DataDimension>();
        List dataDimension = this.taskService.getDataDimension(taskKey);
        rptDimensionDefines.add(dataDimension.stream().filter(e -> e.getDimensionType() == DimensionType.UNIT).findFirst().orElse(null));
        rptDimensionDefines.add(dataDimension.stream().filter(e -> e.getDimensionType() == DimensionType.PERIOD).findFirst().orElse(null));
        rptDimensionDefines.addAll(this.taskService.getReportDimension(taskKey));
        return rptDimensionDefines;
    }

    public String getDimensionName(DataDimension dataDimension) {
        String dimKey = dataDimension.getDimKey();
        if (this.periodEntityAdapter.isPeriodEntity(dimKey)) {
            return this.periodEntityAdapter.getPeriodEntity(dimKey).getDimensionName();
        }
        if ("ADJUST".equals(dimKey)) {
            return "ADJUST";
        }
        return this.entityMetaService.queryEntity(dimKey).getDimensionName();
    }

    public static String transferOldStateCodeToNew(String state) {
        DefaultUploadState_1_0 enumValue;
        try {
            enumValue = DefaultUploadState_1_0.valueOf(state);
        }
        catch (IllegalArgumentException e) {
            return state;
        }
        switch (enumValue) {
            case ORIGINAL_SUBMIT: {
                return "unsubmited";
            }
            case ORIGINAL_UPLOAD: {
                return "unreported";
            }
            case SUBMITED: {
                return "submited";
            }
            case PART_SUBMITED: {
                return "part-submited";
            }
            case UPLOADED: {
                return "reported";
            }
            case PART_UPLOADED: {
                return "part-reported";
            }
            case RETURNED: {
                return "backed";
            }
            case REJECTED: {
                return "rejected";
            }
            case CONFIRMED: {
                return "confirmed";
            }
            case PART_CONFIRMED: {
                return "part-confirmed";
            }
        }
        return null;
    }

    public static boolean isUnitInstance(IProcessIOInstance instance, WorkflowObjectType originalWorkflowObjectType) {
        String formOrFormGroupKey = "";
        if (WorkflowObjectType.FORM.equals((Object)originalWorkflowObjectType)) {
            FormObject formObject = (FormObject)instance.getBusinessObject();
            formOrFormGroupKey = formObject.getFormKey();
        } else if (WorkflowObjectType.FORM_GROUP.equals((Object)originalWorkflowObjectType)) {
            FormGroupObject formGroupObject = (FormGroupObject)instance.getBusinessObject();
            formOrFormGroupKey = formGroupObject.getFormGroupKey();
        }
        return formOrFormGroupKey.equals("11111111-1111-1111-1111-111111111111");
    }
}

