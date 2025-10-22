/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.io.util;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.params.input.QueryParms;
import com.jiuqi.nr.io.service.impl.ParameterService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParamUtil {
    private static final ConcurrentHashMap<String, ParameterService> cacheParameter = new ConcurrentHashMap();
    private static final String CACHEPARAMETER = "cacheParameter";

    public static TableContext getRealParam(QueryParms params) {
        DimensionValueSet dimensionSet = new DimensionValueSet();
        Map<String, Object> dimSetMap = params.getDimensionSet();
        if (null != dimSetMap) {
            for (String key : dimSetMap.keySet()) {
                dimensionSet.setValue(key, dimSetMap.get(key));
            }
        }
        TableContext context = new TableContext(params.getTaskKey(), params.getFormSchemeKey(), params.getFormKey(), dimensionSet, OptTypes.valueOf(params.getOptType().toUpperCase()), params.getFileType());
        context.setAttachment(params.isAttachment());
        context.setAttachmentArea(params.getAttachmentArea());
        context.setExpEntryFields(ExpViewFields.valueOf(params.getExpEntryFields().toUpperCase()));
        context.setExpEnumFields(ExpViewFields.valueOf(params.getExpEnumFields().toUpperCase()));
        return context;
    }

    public static TableContext getAllParam(QueryParms params) {
        DimensionValueSet dimensionSet = new DimensionValueSet();
        Map<String, Object> dimSetMap = params.getDimensionSet();
        if (null != dimSetMap) {
            for (String key : dimSetMap.keySet()) {
                dimensionSet.setValue(key, dimSetMap.get(key));
            }
        }
        TableContext context = new TableContext(params.getTaskKey(), params.getFormSchemeKey(), params.getFormKey(), dimensionSet, OptTypes.valueOf(params.getOptType().toUpperCase()), params.getFileType(), params.getSplit(), params.getSyncTaskID());
        context.setAttachment(params.isAttachment());
        context.setAttachmentArea(params.getAttachmentArea());
        context.setExpEntryFields(ExpViewFields.valueOf(params.getExpEntryFields().toUpperCase()));
        context.setExpEnumFields(ExpViewFields.valueOf(params.getExpEnumFields().toUpperCase()));
        return context;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ParameterService getParameterService() {
        ParameterService parameterService = cacheParameter.get(CACHEPARAMETER);
        if (parameterService != null) {
            return parameterService;
        }
        ConcurrentHashMap<String, ParameterService> concurrentHashMap = cacheParameter;
        synchronized (concurrentHashMap) {
            parameterService = cacheParameter.get(CACHEPARAMETER);
            if (parameterService != null) {
                return parameterService;
            }
            parameterService = (ParameterService)BeanUtil.getBean(ParameterService.class);
            cacheParameter.put(CACHEPARAMETER, parameterService);
        }
        return parameterService;
    }
}

