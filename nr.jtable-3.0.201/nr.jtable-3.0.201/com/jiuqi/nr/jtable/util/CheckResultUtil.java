/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.INumberData
 *  com.jiuqi.np.dataengine.data.IntData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.INumberData;
import com.jiuqi.np.dataengine.data.IntData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormulaData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.FormulaCheckResultInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableCheckMontiorServiceDefault;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.impl.CheckMonitor;
import com.jiuqi.nr.jtable.util.DateUtils;
import com.jiuqi.util.StringUtils;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class CheckResultUtil {
    private static final Logger logger = LoggerFactory.getLogger(CheckResultUtil.class);

    public static void orderCheckResult(FormulaCheckReturnInfo returnInfo, List<EntityViewData> entityList) {
        List<Map<String, DimensionValue>> dimensionList = returnInfo.getDimensionList();
        HashMap unitEntityDataInfoMap = new HashMap();
        Collections.sort(returnInfo.getResults(), (arg0, arg1) -> {
            double unitOrder1;
            String formOrder1;
            FormulaData formula0 = arg0.getFormula();
            FormulaData formula1 = arg1.getFormula();
            String formOrder0 = CheckResultUtil.getFormOrder(formula0.getFormKey());
            if (!formOrder0.equals(formOrder1 = CheckResultUtil.getFormOrder(formula1.getFormKey()))) {
                return formOrder0.compareTo(formOrder1);
            }
            if (formula0.getChecktype() != formula1.getChecktype()) {
                return formula0.getChecktype() > formula1.getChecktype() ? -1 : 1;
            }
            if (formula0.getOrder() != null && formula1.getOrder() != null && !formula0.getOrder().equals(formula1.getOrder())) {
                return formula0.getOrder().compareTo(formula1.getOrder());
            }
            if (formula0.getGlobRow() != formula1.getGlobRow()) {
                return formula0.getGlobRow() - formula1.getGlobRow();
            }
            if (formula0.getGlobCol() != formula1.getGlobCol()) {
                return formula0.getGlobCol() - formula1.getGlobCol();
            }
            double unitOrder0 = CheckResultUtil.getUnitOrder((Map)dimensionList.get(arg0.getDimensionIndex()), entityList, unitEntityDataInfoMap);
            if (unitOrder0 != (unitOrder1 = CheckResultUtil.getUnitOrder((Map)dimensionList.get(arg1.getDimensionIndex()), entityList, unitEntityDataInfoMap))) {
                return unitOrder0 > unitOrder1 ? 1 : -1;
            }
            return 0;
        });
    }

    public static double getUnitOrder(Map<String, DimensionValue> dimensionValue, List<EntityViewData> entitys, Map<String, EntityData> unitEntityDataInfoMap) {
        IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
        double unitOrder = 0.0;
        for (EntityViewData entity : entitys) {
            if (!entity.isMasterEntity() || !dimensionValue.containsKey(entity.getDimensionName())) continue;
            String unitId = dimensionValue.get(entity.getDimensionName()).getValue();
            EntityData unitData = null;
            if (unitEntityDataInfoMap.containsKey(unitId)) {
                unitData = unitEntityDataInfoMap.get(unitId);
            } else {
                EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                entityQueryByKeyInfo.setEntityViewKey(entity.getKey());
                entityQueryByKeyInfo.setEntityKey(unitId);
                EntityByKeyReturnInfo entityData = jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                if (entityData.getEntity() != null) {
                    unitData = entityData.getEntity();
                    unitEntityDataInfoMap.put(unitId, unitData);
                }
            }
            if (unitData == null) continue;
            unitOrder = unitData.getOrder();
        }
        return unitOrder;
    }

    public static String getFormOrder(String formKey) {
        String formOrder = "Z";
        if (StringUtils.isNotEmpty((String)formKey) && !"00000000-0000-0000-0000-000000000000".equals(formKey)) {
            RuntimeViewController runTimeViewController = (RuntimeViewController)BeanUtil.getBean(RuntimeViewController.class);
            FormDefine form = runTimeViewController.queryFormById(formKey);
            formOrder = form.getOrder();
        }
        return formOrder;
    }

    public static String getFormOrder(FormDefine formDefine) {
        String formOrder = "Z";
        if (formDefine != null) {
            formOrder = formDefine.getOrder();
        }
        return formOrder;
    }

    public static String valueFormate(DecimalFormat decimalFormat, AbstractData valueData) {
        String value = "";
        if (valueData instanceof INumberData) {
            double number = 0.0;
            try {
                number = valueData.getAsFloat();
            }
            catch (DataTypeException e) {
                logger.error("\u683c\u5f0f\u5316\u5ba1\u6838\u9519\u8bef\u6570\u636e\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            if (valueData instanceof IntData) {
                decimalFormat.setMaximumFractionDigits(0);
                decimalFormat.setMinimumFractionDigits(0);
            } else {
                BigDecimal bigDecimal = valueData.getAsCurrency();
                if (bigDecimal != null) {
                    decimalFormat.setMaximumFractionDigits(bigDecimal.scale());
                    decimalFormat.setMinimumFractionDigits(bigDecimal.scale());
                }
            }
            value = decimalFormat.format(number);
        } else {
            value = valueData.getAsString();
        }
        return value;
    }

    public static String valueFormate(DecimalFormat decimalFormat, AbstractData valueData, int fractionDigits) {
        String value = "";
        if (valueData instanceof INumberData) {
            double number = 0.0;
            try {
                number = valueData.getAsFloat();
            }
            catch (DataTypeException e) {
                logger.error("\u683c\u5f0f\u5316\u5ba1\u6838\u9519\u8bef\u6570\u636e\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            decimalFormat.setMaximumFractionDigits(fractionDigits);
            decimalFormat.setMinimumFractionDigits(fractionDigits);
            value = decimalFormat.format(number);
        } else {
            value = valueData.getAsString();
        }
        return value;
    }

    public static String getFormKey(FormulaData formula) {
        String formKey = "00000000-0000-0000-0000-000000000000";
        FormDefine form = null;
        if (StringUtils.isNotEmpty((String)formula.getFormKey())) {
            RuntimeViewController runTimeViewController = (RuntimeViewController)BeanUtil.getBean(RuntimeViewController.class);
            form = runTimeViewController.queryFormById(formula.getFormKey());
        }
        if (form == null) {
            formula.setFormKey(formKey);
            formula.setFormTitle("\u8868\u95f4");
        } else {
            formKey = form.getKey();
            formula.setFormTitle(form.getTitle());
        }
        return formKey;
    }

    public static String getFormKey(FormulaData formula, FormDefine formDefine) {
        String formKey = "00000000-0000-0000-0000-000000000000";
        if (formDefine == null) {
            formula.setFormKey(formKey);
            formula.setFormTitle("\u8868\u95f4");
        } else {
            formKey = formDefine.getKey();
            formula.setFormTitle(formDefine.getTitle());
        }
        return formKey;
    }

    public static void setCheckResultOtherInfo(FormulaCheckReturnInfo returnInfo, List<EntityViewData> entityList, JtableContext jtableContext) {
        returnInfo.setTotalCount(returnInfo.getResults().size());
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
            int checktype;
            DimensionValue dimensionValue = dimensionList.get(result.getDimensionIndex()).get(masterEntity.getDimensionName());
            if (dimensionValue != null) {
                if (unitKeyTitleMap.containsKey(dimensionValue.getValue())) {
                    result.setUnitKey(dimensionValue.getValue());
                    result.setUnitTitle((String)unitKeyTitleMap.get(dimensionValue.getValue()));
                    result.setUnitCode((String)unitKeyCodeMap.get(dimensionValue.getValue()));
                } else {
                    EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                    entityQueryByKeyInfo.setEntityViewKey(masterEntity.getKey());
                    entityQueryByKeyInfo.setEntityKey(dimensionValue.getValue());
                    if (jtableContext != null) {
                        entityQueryByKeyInfo.setContext(jtableContext);
                    }
                    EntityData entityData = jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo).getEntity();
                    result.setUnitKey(dimensionValue.getValue());
                    result.setUnitTitle(entityData.getTitle());
                    result.setUnitCode(entityData.getCode());
                    unitKeyTitleMap.put(dimensionValue.getValue(), entityData.getTitle());
                    unitKeyCodeMap.put(dimensionValue.getValue(), entityData.getCode());
                }
            }
            if ((checktype = result.getFormula().getChecktype()) == FormulaCheckType.FORMULA_CHECK_HINT.getValue()) {
                returnInfo.incrHintCount();
                continue;
            }
            if (checktype == FormulaCheckType.FORMULA_CHECK_WARNING.getValue()) {
                returnInfo.incrWranCount();
                continue;
            }
            if (checktype != FormulaCheckType.FORMULA_CHECK_ERROR.getValue()) continue;
            returnInfo.incrErrorCount();
        }
    }

    public static String buildDesKey(String formSchemeKey, String formulaSchemeKey, String formKey, String formulaKey, int globRow, int globCol, Map<String, DimensionValue> dimensionSet) {
        String formulaID = formulaKey.substring(0, 36);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (DimensionValue value : dimensionSet.values()) {
            dimensionValueSet.setValue(value.getName(), (Object)value.getValue());
        }
        if (dimensionValueSet.hasValue("RECORDKEY")) {
            Object value = dimensionValueSet.getValue("RECORDKEY");
            dimensionValueSet.clearValue("RECORDKEY");
            dimensionValueSet.setValue("ID", value);
        } else if (!dimensionValueSet.hasValue("ID")) {
            dimensionValueSet.setValue("ID", (Object)"null");
        }
        if (!dimensionValueSet.hasValue("VERSIONID")) {
            dimensionValueSet.setValue("VERSIONID", (Object)"00000000-0000-0000-0000-000000000000");
        }
        if (dimensionValueSet.hasValue("ALLCKR_ASYNCTASKID")) {
            dimensionValueSet.clearValue("ALLCKR_ASYNCTASKID");
        }
        String masterStr = formSchemeKey + ";" + formulaSchemeKey + ";" + formKey + ";" + formulaID + ";" + DataEngineConsts.FormulaType.CHECK.getValue() + ";" + globRow + ";" + globCol + ";" + dimensionValueSet.toString();
        return CheckResultUtil.tofakeUUID(masterStr).toString();
    }

    public static UUID tofakeUUID(String string) {
        int i;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException nsae) {
            throw new InternalError("MD5 not supported", nsae);
        }
        byte[] data = md.digest(string.getBytes());
        long msb = 0L;
        long lsb = 0L;
        assert (data.length == 16) : "data must be 16 bytes in length";
        for (i = 0; i < 8; ++i) {
            msb = msb << 8 | (long)(data[i] & 0xFF);
        }
        for (i = 8; i < 16; ++i) {
            lsb = lsb << 8 | (long)(data[i] & 0xFF);
        }
        return new UUID(msb, lsb);
    }

    public static CheckMonitor getMonitor(FormSchemeDefine formScheme, FormulaSchemeDefine formulaSchemeDefine, JtableContext jtableContext) {
        IJtableCheckMontiorServiceDefault jtableCheckMontiorServiceDefault = (IJtableCheckMontiorServiceDefault)BeanUtil.getBean(IJtableCheckMontiorServiceDefault.class);
        if (null != jtableCheckMontiorServiceDefault) {
            return jtableCheckMontiorServiceDefault.getMonitor(formScheme, formulaSchemeDefine, jtableContext);
        }
        return new CheckMonitor(formScheme, formulaSchemeDefine);
    }

    public static String buildAllCheckCacheKey(String formulaSchemeKey, DimensionValueSet dimensionValueSet) {
        if (!dimensionValueSet.hasValue("VERSIONID")) {
            dimensionValueSet.setValue("VERSIONID", (Object)"00000000-0000-0000-0000-000000000000");
        }
        for (int dimIndex = 0; dimIndex < dimensionValueSet.size(); ++dimIndex) {
            Object dimValue = dimensionValueSet.getValue(dimIndex);
            if (!(dimValue instanceof List)) continue;
            List dimValueList = (List)dimValue;
            Collections.sort(dimValueList);
            dimensionValueSet.setValue(dimensionValueSet.getName(dimIndex), (Object)dimValueList);
        }
        String masterStr = formulaSchemeKey + ";" + dimensionValueSet;
        String cacheKey = CheckResultUtil.tofakeUUID(masterStr).toString();
        logger.info(new Date().toLocaleString() + "   CheckResultUtil\u5168\u5ba1\u7ed3\u679c\u7f13\u5b58masterStr :" + masterStr + "; cacheKey:" + cacheKey);
        return cacheKey;
    }

    public static String valueFormateDateTime(AbstractData value) {
        long dateTime = 0L;
        try {
            dateTime = value.getAsDateTime();
        }
        catch (DataTypeException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
        return DateUtils.dateToStringTime(new Date(dateTime));
    }

    public static String valueFormateDate(AbstractData value) {
        try {
            Date date = value.getAsDateObj();
            return DateUtils.dateToString(date);
        }
        catch (DataTypeException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }
}

