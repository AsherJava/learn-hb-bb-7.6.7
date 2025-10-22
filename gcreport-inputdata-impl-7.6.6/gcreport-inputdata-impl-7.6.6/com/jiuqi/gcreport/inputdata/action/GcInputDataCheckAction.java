/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.action.env.GcActionItemEnv
 *  com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.UniversalTableDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.gcreport.inputdata.action;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.UniversalTableDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcInputDataCheckAction
extends AbstractGcActionItem {
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;

    protected GcInputDataCheckAction() {
        super("gcInputDataCheckAction", "\u5bf9\u8d26\u6a21\u5f0f", "", "#icon-16_GJ_A_GC_qiehuandaoheduijiemian");
    }

    public Object execute(GcActionItemEnv actionItemEnv) {
        return null;
    }

    public boolean isEnable(String enableContextJson) {
        if (StringUtils.isEmpty((String)enableContextJson)) {
            return false;
        }
        DataEntryContext dataEntryContext = (DataEntryContext)JsonUtils.readValue((String)enableContextJson, DataEntryContext.class);
        DimensionValue orgDimensionValue = (DimensionValue)dataEntryContext.getDimensionSet().get("MD_ORG");
        DimensionValue dataTimeValue = (DimensionValue)dataEntryContext.getDimensionSet().get("DATATIME");
        if (orgDimensionValue == null || dataTimeValue == null) {
            return false;
        }
        String taskId = dataEntryContext.getTaskKey();
        if (StringUtils.isEmpty((String)taskId)) {
            return false;
        }
        try {
            String tableCode = this.inputDataNameProvider.getTableCodeByTaskId(taskId);
            if (StringUtils.isEmpty((String)tableCode)) {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
        String formKey = dataEntryContext.getFormKey();
        if (StringUtils.isEmpty((String)formKey)) {
            return false;
        }
        return this.isExistInputDataByFromKey(formKey);
    }

    private boolean isExistInputDataByFromKey(String formKey) {
        List dataRegionDefines = this.runTimeViewController.getAllRegionsInForm(formKey);
        for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
            String tableCode;
            List tableDefines;
            List fieldKeys;
            if (DataRegionKind.DATA_REGION_SIMPLE.equals((Object)dataRegionDefine.getRegionKind()) || CollectionUtils.isEmpty((Collection)(fieldKeys = this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey()))) || CollectionUtils.isEmpty((Collection)(tableDefines = this.runtimeController.queryTableDefinesByFields((Collection)fieldKeys))) || !(tableCode = tableDefines.stream().map(UniversalTableDefine::getCode).findFirst().get()).contains("GC_INPUTDATA")) continue;
            return true;
        }
        return false;
    }
}

