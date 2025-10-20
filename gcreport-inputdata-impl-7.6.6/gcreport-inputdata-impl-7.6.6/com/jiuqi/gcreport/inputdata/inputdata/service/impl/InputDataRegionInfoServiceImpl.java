/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.UniversalTableDefine
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.gcreport.inputdata.inputdata.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.inputdata.inputdata.service.InputDataRegionInfoService;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.UniversalTableDefine;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InputDataRegionInfoServiceImpl
implements InputDataRegionInfoService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;

    @Override
    public Set<String> listInputDataRegionKeyByFromKey(List<String> formKeys) {
        HashSet<String> regionKeys = new HashSet<String>();
        for (String formKey : formKeys) {
            Set<String> regionKeysByFormKey = this.getInputDataRegionKeyByFromKey(formKey);
            if (CollectionUtils.isEmpty(regionKeysByFormKey)) continue;
            regionKeys.addAll(regionKeysByFormKey);
        }
        return regionKeys;
    }

    private Set<String> getInputDataRegionKeyByFromKey(String formKey) {
        HashSet<String> regionKeys = new HashSet<String>();
        List dataRegionDefines = this.runTimeViewController.getAllRegionsInForm(formKey);
        for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
            String tableCode;
            List tableDefines;
            List fieldKeys;
            if (DataRegionKind.DATA_REGION_SIMPLE.equals((Object)dataRegionDefine.getRegionKind()) || CollectionUtils.isEmpty((Collection)(fieldKeys = this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey()))) || CollectionUtils.isEmpty((Collection)(tableDefines = this.runtimeController.queryTableDefinesByFields((Collection)fieldKeys))) || CollectionUtils.isEmpty((Collection)tableDefines) || StringUtils.isEmpty((String)(tableCode = tableDefines.stream().map(UniversalTableDefine::getCode).findFirst().get())) || !tableCode.contains("GC_INPUTDATA")) continue;
            regionKeys.add(dataRegionDefine.getKey());
        }
        return regionKeys;
    }
}

