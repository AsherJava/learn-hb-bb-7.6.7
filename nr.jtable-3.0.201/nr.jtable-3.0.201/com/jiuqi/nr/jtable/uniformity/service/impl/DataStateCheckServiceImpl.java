/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.exception.ExceptionResult
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.jtable.uniformity.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.exception.ExceptionResult;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.uniformity.service.IDataStateCheckService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataStateCheckServiceImpl
implements IDataStateCheckService {
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;

    @Override
    public String createDataCheckTag(JtableContext jtableContext) {
        String formKey = jtableContext.getFormKey();
        StringBuffer statusKeyBuf = this.getStatusKey(jtableContext);
        String unitStatueKey = statusKeyBuf.toString();
        Object unitStatueValue = this.find(unitStatueKey);
        statusKeyBuf.append(formKey);
        String formStatueKey = statusKeyBuf.toString();
        Object formStatueValue = this.find(formStatueKey);
        String key = "";
        if (null != formStatueValue && null != unitStatueValue) {
            key = formStatueValue.toString() + ":" + unitStatueValue.toString();
        } else if (null != formStatueValue) {
            key = formStatueValue.toString();
        } else if (null != unitStatueValue) {
            key = ":" + unitStatueValue.toString();
        }
        return key;
    }

    private StringBuffer getStatusKey(JtableContext jtableContext) {
        StringBuffer statusKeyBuf = new StringBuffer();
        Map<String, DimensionValue> dimensionSet = jtableContext.getDimensionSet();
        if (null != dimensionSet && dimensionSet.size() > 0) {
            EntityViewData dataTimeEntity;
            DimensionValue datatimeDimensionValue;
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
            DimensionValue dwDimensionValue = dimensionSet.get(dwEntity.getDimensionName());
            if (null != dwDimensionValue) {
                statusKeyBuf.append(dwDimensionValue.getValue()).append(":");
            }
            if (null != (datatimeDimensionValue = dimensionSet.get((dataTimeEntity = this.jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey())).getDimensionName()))) {
                statusKeyBuf.append(datatimeDimensionValue.getValue()).append(":");
            }
        }
        return statusKeyBuf;
    }

    private Object find(String key) {
        return this.cacheObjectResourceRemote.find((Object)key);
    }

    private void create(Object id, Object object) {
        this.cacheObjectResourceRemote.create(id, object);
    }

    @Override
    public ExceptionResult checkDataState(JtableContext jtableContext) {
        Map<String, Object> variableMap = jtableContext.getVariableMap();
        if (null != variableMap && variableMap.containsKey("DATA_STATE_CHECK_EDIT")) {
            Object object = variableMap.get("DATA_STATE_CHECK_EDIT");
            String fromUserValue = "";
            if (null != object) {
                fromUserValue = (String)object;
            }
            String formKey = jtableContext.getFormKey();
            StringBuffer statusKeyBuf = this.getStatusKey(jtableContext);
            String formValue = "";
            String unitValue = "";
            String[] unitAndForms = null;
            if (!StringUtils.isEmpty((String)fromUserValue)) {
                unitAndForms = fromUserValue.split(":");
                formValue = unitAndForms[0];
            }
            String unitStatueKey = statusKeyBuf.toString();
            Object unitStatueValue = this.find(unitStatueKey);
            FormData formData = this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
            String formType = formData.getFormType();
            if (null != unitStatueValue && !formType.equals("FORM_TYPE_ANALYSISREPORT") && !formType.equals("FORM_TYPE_INSERTANALYSIS")) {
                if (null != unitAndForms && unitAndForms.length > 1) {
                    unitValue = unitAndForms[1];
                    if (null != unitStatueValue) {
                        Long cacheUnitValue = (Long)unitStatueValue;
                        Long unitValueFromUser = Long.valueOf(unitValue);
                        if (cacheUnitValue > unitValueFromUser) {
                            return new ExceptionResult(JtableExceptionCodeCost.NR_EXCEPTION_UNIT_NOT_NEW);
                        }
                    }
                } else {
                    return new ExceptionResult(JtableExceptionCodeCost.NR_EXCEPTION_UNIT_NOT_NEW);
                }
            }
            if (!StringUtils.isEmpty((String)formKey)) {
                statusKeyBuf.append(formKey);
                String formStatueKey = statusKeyBuf.toString();
                Object cacheFormValue = this.find(formStatueKey);
                if (null != cacheFormValue) {
                    if (!StringUtils.isEmpty((String)formValue)) {
                        Long cacheFormValueLong = (Long)cacheFormValue;
                        Long formValueUser = Long.valueOf(formValue);
                        if (cacheFormValueLong > formValueUser) {
                            return new ExceptionResult(JtableExceptionCodeCost.NR_EXCEPTION_FORM_NOT_NEW);
                        }
                    } else {
                        return new ExceptionResult(JtableExceptionCodeCost.NR_EXCEPTION_FORM_NOT_NEW);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void updateDimensionCache(JtableContext jtableContext) {
        String unitStatueKey = this.getStatusKey(jtableContext).toString();
        Long unitValue = System.currentTimeMillis();
        this.create(unitStatueKey, unitValue);
    }

    @Override
    public void updateDimensionFormKeyCache(JtableContext jtableContext) {
        StringBuffer statusKeyBuf = this.getStatusKey(jtableContext);
        statusKeyBuf.append(jtableContext.getFormKey());
        String formStatueKey = statusKeyBuf.toString();
        Long formStatueValue = System.currentTimeMillis();
        this.create(formStatueKey, formStatueValue);
    }

    @Override
    public void batchUpdateDimensionFormKeyCache(JtableContext jtableContext, List<String> formKeys) {
        String unitStatusKey = this.getStatusKey(jtableContext).toString();
        Long formStatueValue = System.currentTimeMillis();
        for (String formKey : formKeys) {
            String formStatueKey = unitStatusKey + formKey;
            this.create(formStatueKey, formStatueValue);
        }
    }

    @Override
    public void batchUpdateDimensionCache(JtableContext jtableContext, List<Map<String, DimensionValue>> dimensions) {
        Long unitValue = System.currentTimeMillis();
        for (Map<String, DimensionValue> dimensionSet : dimensions) {
            JtableContext curJtableContext = new JtableContext(jtableContext);
            curJtableContext.setDimensionSet(dimensionSet);
            String unitStatueKey = this.getStatusKey(jtableContext).toString();
            this.create(unitStatueKey, unitValue);
        }
    }
}

