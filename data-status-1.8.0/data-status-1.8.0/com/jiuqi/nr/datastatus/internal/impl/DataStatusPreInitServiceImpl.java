/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.datastatus.IDataStatusPreInitService
 *  com.jiuqi.nr.dataservice.core.datastatus.obj.DataStatusPresetInfo
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeFormulaController
 */
package com.jiuqi.nr.datastatus.internal.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.datastatus.IDataStatusPreInitService;
import com.jiuqi.nr.dataservice.core.datastatus.obj.DataStatusPresetInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.datastatus.facade.service.IDataStatusService;
import com.jiuqi.nr.datastatus.internal.util.CommonUtil;
import com.jiuqi.nr.datastatus.internal.util.Utils;
import com.jiuqi.nr.definition.api.IRunTimeFormulaController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class DataStatusPreInitServiceImpl
implements IDataStatusPreInitService {
    @Autowired
    private IDataStatusService dataStatusService;
    @Autowired
    private IRunTimeFormulaController runTimeFormulaController;
    @Autowired
    private Utils utils;

    public DataStatusPresetInfo initInfo(DimensionCombination dimensionCombination, String formSchemeKey, String formulaSchemeKey, Collection<String> formulaKeys) throws Exception {
        if (!this.utils.fsOpenStatus(formSchemeKey)) {
            return null;
        }
        Map effectiveForms = this.runTimeFormulaController.getEffectiveForms(formulaSchemeKey, new ArrayList<String>(formulaKeys));
        List<String> calFormKeys = effectiveForms.values().stream().filter(StringUtils::hasText).filter(CommonUtil.distinctByKey(Function.identity())).collect(Collectors.toList());
        return this.initInfo(dimensionCombination, formSchemeKey, calFormKeys);
    }

    public DataStatusPresetInfo initInfo(DimensionCombination dimensionCombination, String formSchemeKey, Collection<String> formKeys) throws Exception {
        if (!this.utils.fsOpenStatus(formSchemeKey)) {
            return null;
        }
        List<String> filledFormKey = this.dataStatusService.getFilledFormKey(formSchemeKey, dimensionCombination);
        DataStatusPresetInfo info = new DataStatusPresetInfo();
        info.setDimensionValueSet(dimensionCombination.toDimensionValueSet());
        info.setSingleDim(true);
        ArrayList<String> emptyFms = new ArrayList<String>();
        ArrayList<String> fullFms = new ArrayList<String>();
        for (String formKey : formKeys) {
            if (filledFormKey.contains(formKey)) {
                fullFms.add(formKey);
                continue;
            }
            emptyFms.add(formKey);
        }
        info.setEmptyForms(emptyFms);
        info.setFullForms(fullFms);
        return info;
    }

    private boolean dimSingle(DimensionValueSet dimensionValueSet) {
        if (dimensionValueSet != null) {
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                List stringList;
                Object value = dimensionValueSet.getValue(i);
                if (!(value instanceof List) || (stringList = (List)value).size() <= 1) continue;
                return false;
            }
        }
        return true;
    }
}

