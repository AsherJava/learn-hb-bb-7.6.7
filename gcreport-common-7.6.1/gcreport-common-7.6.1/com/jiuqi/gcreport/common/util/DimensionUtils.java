/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.impl.common.NvwaDataModelCreateUtil
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.common.util;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.GCAdjTypeEnum;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.impl.common.NvwaDataModelCreateUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class DimensionUtils {
    private static IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
    private static NvwaDataModelCreateUtil nvwaDataModelCreateUtil = (NvwaDataModelCreateUtil)SpringContextUtils.getBean(NvwaDataModelCreateUtil.class);
    private static IEntityMetaService entityMetaService = (IEntityMetaService)SpringContextUtils.getBean(IEntityMetaService.class);
    private static IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
    private static final String MD_GCADJTYPE = "MD_GCADJTYPE";

    public static boolean isExisAdjType(String taskId) {
        TaskDefine taskDefine = runTimeViewController.queryTaskDefine(taskId);
        String dimes = taskDefine.getDims();
        return !StringUtils.isEmpty((String)dimes) && dimes.indexOf(MD_GCADJTYPE) > -1;
    }

    public static boolean isExistAdjust(String taskId) {
        TaskDefine taskDefine = runTimeViewController.queryTaskDefine(taskId);
        return runtimeDataSchemeService.enableAdjustPeriod(taskDefine.getDataScheme()) != false;
    }

    public static DimensionValueSet generateDimSet(Object orgCode, Object periodStr, Object currencyCode, Object orgTypeCode, String adjustCode, String taskId) {
        DimensionValueSet dimSet = new DimensionValueSet();
        DimensionUtils.setDimValue(dimSet, "DATATIME", periodStr);
        DimensionUtils.setDimValue(dimSet, "MD_ORG", orgCode);
        TaskDefine taskDefine = runTimeViewController.queryTaskDefine(taskId);
        DimensionUtils.setExistDimsValue(taskDefine, "MD_GCORGTYPE", dimSet, orgTypeCode);
        DimensionUtils.setExistDimsValue(taskDefine, "MD_CURRENCY", dimSet, currencyCode);
        if (DimensionUtils.isExisAdjType(taskId)) {
            DimensionUtils.setDimValue(dimSet, MD_GCADJTYPE, (Object)GCAdjTypeEnum.BEFOREADJ.getCode());
        }
        if (DimensionUtils.isExistAdjust(taskId)) {
            if (StringUtils.isEmpty((String)adjustCode)) {
                throw new RuntimeException("\u5f53\u524d\u4efb\u52a1\u5df2\u5f00\u542f\u8c03\u6574\u671f\uff0c\u8c03\u6574\u671f\u53c2\u6570\u4e3a\u7a7a\uff0ctaskId=" + taskId);
            }
            DimensionUtils.setDimValue(dimSet, "ADJUST", (Object)adjustCode);
        }
        return dimSet;
    }

    public static Map<String, DimensionValue> buildDimensionMap(String taskId, String currencyCode, String periodStr, String orgTypeCode, String orgCode, String adjustCode) {
        ConcurrentHashMap<String, DimensionValue> dimensionSetMap = new ConcurrentHashMap<String, DimensionValue>(16);
        DimensionUtils.setDimValue(dimensionSetMap, "DATATIME", periodStr);
        DimensionUtils.setDimValue(dimensionSetMap, "MD_ORG", orgCode);
        TaskDefine taskDefine = runTimeViewController.queryTaskDefine(taskId);
        DimensionUtils.setExistDimsValue(taskDefine, "MD_GCORGTYPE", dimensionSetMap, orgTypeCode);
        DimensionUtils.setExistDimsValue(taskDefine, "MD_CURRENCY", dimensionSetMap, currencyCode);
        if (DimensionUtils.isExisAdjType(taskId)) {
            DimensionUtils.setDimValue(dimensionSetMap, MD_GCADJTYPE, GCAdjTypeEnum.BEFOREADJ.getCode());
        }
        if (DimensionUtils.isExistAdjust(taskId)) {
            if (StringUtils.isEmpty((String)adjustCode)) {
                throw new RuntimeException("\u5f53\u524d\u4efb\u52a1\u5df2\u5f00\u542f\u8c03\u6574\u671f\uff0c\u8c03\u6574\u671f\u53c2\u6570\u4e3a\u7a7a\uff0ctaskId=" + taskId);
            }
            DimensionUtils.setDimValue(dimensionSetMap, "ADJUST", adjustCode);
        }
        return dimensionSetMap;
    }

    public static Map<String, String> generateDimMap(String orgCode, String periodStr, String currencyCode, String orgTypeCode, String adjustCode, String taskId) {
        HashMap<String, String> dimension = new HashMap<String, String>();
        Map<String, DimensionValue> buildDimensionMap = DimensionUtils.buildDimensionMap(taskId, currencyCode, periodStr, orgTypeCode, orgCode, adjustCode);
        for (DimensionValue dimensionValue : buildDimensionMap.values()) {
            dimension.put(dimensionValue.getName(), dimensionValue.getValue());
        }
        return dimension;
    }

    public static void dimensionMapSetAdjType(String taskId, Map<String, DimensionValue> dimensionSetMap) {
        DimensionValue dimensionValue;
        if (DimensionUtils.isExisAdjType(taskId)) {
            DimensionUtils.setDimValue(dimensionSetMap, MD_GCADJTYPE, GCAdjTypeEnum.BEFOREADJ.getCode());
        }
        if (DimensionUtils.isExistAdjust(taskId) && (Objects.isNull(dimensionValue = dimensionSetMap.get("ADJUST")) || StringUtils.isEmpty((String)dimensionValue.getValue()))) {
            throw new RuntimeException("\u5f53\u524d\u4efb\u52a1\u5df2\u5f00\u542f\u8c03\u6574\u671f\uff0c\u8c03\u6574\u671f\u53c2\u6570\u4e3a\u7a7a\uff0ctaskId=" + taskId);
        }
    }

    public static void dimensionMapSetAdjType(DimensionValueSet dimensionValueSet, String taskId) {
        Object dimensionValue;
        if (DimensionUtils.isExisAdjType(taskId)) {
            DimensionUtils.setDimValue(dimensionValueSet, MD_GCADJTYPE, (Object)GCAdjTypeEnum.BEFOREADJ.getCode());
        }
        if (DimensionUtils.isExistAdjust(taskId) && (Objects.isNull(dimensionValue = dimensionValueSet.getValue("ADJUST")) || StringUtils.isEmpty((String)dimensionValue.toString()))) {
            throw new RuntimeException("\u5f53\u524d\u4efb\u52a1\u5df2\u5f00\u542f\u8c03\u6574\u671f\uff0c\u8c03\u6574\u671f\u53c2\u6570\u4e3a\u7a7a\uff0ctaskId=" + taskId);
        }
    }

    public static String getDwEntitieTableByTaskKey(String taskKey) {
        TaskDefine taskDefine = runTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null || StringUtils.isEmpty((String)taskDefine.getDw())) {
            return null;
        }
        TableModelDefine tableDefine = entityMetaService.getTableModel(taskDefine.getDw());
        return tableDefine.getName();
    }

    public static String getOrgTypeCodeByFormScheme(FormSchemeDefine destSchemeDefine) {
        TableModelDefine tableDefine = nvwaDataModelCreateUtil.queryTableModel(destSchemeDefine.getDw());
        return tableDefine.getName();
    }

    private static void setExistDimsValue(TaskDefine taskDefine, String dimName, DimensionValueSet dimensionValueSet, Object dimValue) {
        String dims;
        if (Objects.nonNull(taskDefine) && !StringUtils.isEmpty((String)(dims = taskDefine.getDims())) && dims.contains(dimName)) {
            DimensionUtils.setDimValue(dimensionValueSet, dimName, dimValue);
        }
    }

    private static void setExistDimsValue(TaskDefine taskDefine, String dimName, Map<String, DimensionValue> dimensionSetMap, String dimValue) {
        String dims;
        if (Objects.nonNull(taskDefine) && !StringUtils.isEmpty((String)(dims = taskDefine.getDims())) && dims.contains(dimName)) {
            DimensionUtils.setDimValue(dimensionSetMap, dimName, dimValue);
        }
    }

    private static void setDimValue(DimensionValueSet dimensionValueSet, String dimName, Object dimValue) {
        dimensionValueSet.setValue(dimName, dimValue);
    }

    private static void setDimValue(Map<String, DimensionValue> dimensionSetMap, String dimName, String dimValue) {
        DimensionValue dimensionValue = new DimensionValue();
        dimensionValue.setName(dimName);
        dimensionValue.setValue(dimValue);
        dimensionSetMap.put(dimName, dimensionValue);
    }
}

