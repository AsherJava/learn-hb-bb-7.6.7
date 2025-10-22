/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.FormulaCheckResultInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableCheckMontiorServiceDefault;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.impl.CheckMonitor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class CheckResultUnitUtil {
    public static void setCheckResultUnitInfo(FormulaCheckReturnInfo returnInfo, List<EntityViewData> entityList) {
        IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
        EntityViewData masterEntity = null;
        for (EntityViewData entity : entityList) {
            if (!entity.isMasterEntity()) continue;
            masterEntity = entity;
            break;
        }
        if (masterEntity == null) {
            return;
        }
        HashMap<String, String> unitKeyTitleMap = new HashMap<String, String>(1000);
        HashMap<String, String> unitKeyCodeMap = new HashMap<String, String>(1000);
        List<FormulaCheckResultInfo> checkResultList = returnInfo.getResults();
        List<Map<String, DimensionValue>> dimensionList = returnInfo.getDimensionList();
        for (FormulaCheckResultInfo result : checkResultList) {
            DimensionValue dimensionValue = dimensionList.get(result.getDimensionIndex()).get(masterEntity.getDimensionName());
            if (dimensionValue == null) continue;
            if (unitKeyTitleMap.containsKey(dimensionValue.getValue())) {
                result.setUnitKey(dimensionValue.getValue());
                result.setUnitTitle((String)unitKeyTitleMap.get(dimensionValue.getValue()));
                result.setUnitCode((String)unitKeyCodeMap.get(dimensionValue.getValue()));
                continue;
            }
            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            entityQueryByKeyInfo.setEntityViewKey(masterEntity.getKey());
            entityQueryByKeyInfo.setEntityKey(dimensionValue.getValue());
            EntityData entityData = jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo).getEntity();
            result.setUnitKey(dimensionValue.getValue());
            result.setUnitTitle(entityData.getTitle());
            result.setUnitCode(entityData.getCode());
            unitKeyTitleMap.put(dimensionValue.getValue(), entityData.getTitle());
            unitKeyCodeMap.put(dimensionValue.getValue(), entityData.getCode());
        }
    }

    public static CheckMonitor getMonitor(FormSchemeDefine formScheme, FormulaSchemeDefine formulaSchemeDefine, JtableContext jtableContext) {
        IJtableCheckMontiorServiceDefault jtableCheckMontiorServiceDefault = (IJtableCheckMontiorServiceDefault)BeanUtil.getBean(IJtableCheckMontiorServiceDefault.class);
        if (null != jtableCheckMontiorServiceDefault) {
            return jtableCheckMontiorServiceDefault.getMonitor(formScheme, formulaSchemeDefine, jtableContext);
        }
        return new CheckMonitor(formScheme, formulaSchemeDefine);
    }
}

