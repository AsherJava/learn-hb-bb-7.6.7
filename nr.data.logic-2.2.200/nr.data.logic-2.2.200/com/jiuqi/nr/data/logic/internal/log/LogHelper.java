/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.RunTimeFormDefineImpl
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.data.logic.internal.log;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormDefineImpl;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class LogHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogHelper.class);
    private static final String MODULE_CALCULATE = "\u8fd0\u7b97\u670d\u52a1";
    private static final String MODULE_CHECK = "\u5ba1\u6838\u670d\u52a1";
    private static final String MODULE_CKD = "\u51fa\u9519\u8bf4\u660e\u670d\u52a1";
    private final DataServiceLogHelper calLogHelper;
    private final DataServiceLogHelper checkLogHelper;
    private final DataServiceLogHelper ckdLogHelper;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;

    public LogHelper(DataServiceLoggerFactory dataServiceLoggerFactory) {
        this.calLogHelper = dataServiceLoggerFactory.getLogger(MODULE_CALCULATE, OperLevel.USER_OPER);
        this.checkLogHelper = dataServiceLoggerFactory.getLogger(MODULE_CHECK, OperLevel.USER_OPER);
        this.ckdLogHelper = dataServiceLoggerFactory.getLogger(MODULE_CKD, OperLevel.USER_OPER);
    }

    public void calInfo(String taskKey, LogDimensionCollection dimensionCollection, String title, String message) {
        this.calLogHelper.info(taskKey, dimensionCollection, title, message);
        this.logDebug(taskKey, dimensionCollection, title, message);
    }

    public void calError(String taskKey, LogDimensionCollection dimensionCollection, String title, String message) {
        this.calLogHelper.error(taskKey, dimensionCollection, title, message);
        LOGGER.error(this.getMsg(taskKey, dimensionCollection, title, message));
    }

    public void checkInfo(String taskKey, LogDimensionCollection dimensionCollection, String title, String message) {
        this.checkLogHelper.info(taskKey, dimensionCollection, title, message);
        this.logDebug(taskKey, dimensionCollection, title, message);
    }

    public void checkError(String taskKey, LogDimensionCollection dimensionCollection, String title, String message) {
        this.checkLogHelper.error(taskKey, dimensionCollection, title, message);
        LOGGER.error(this.getMsg(taskKey, dimensionCollection, title, message));
    }

    public void ckdInfo(String taskKey, LogDimensionCollection dimensionCollection, String title, String message) {
        this.ckdLogHelper.info(taskKey, dimensionCollection, title, message);
        this.logDebug(taskKey, dimensionCollection, title, message);
    }

    public void ckdError(String taskKey, LogDimensionCollection dimensionCollection, String title, String message) {
        this.ckdLogHelper.error(taskKey, dimensionCollection, title, message);
        LOGGER.error(this.getMsg(taskKey, dimensionCollection, title, message));
    }

    public String getFormulaInfo(Collection<String> formulaKeys) {
        if (CollectionUtils.isEmpty(formulaKeys)) {
            return "\u516c\u5f0f\u4fe1\u606f\uff1a\u7a7a";
        }
        StringBuilder formulaInfo = new StringBuilder("\u516c\u5f0f\u4fe1\u606f\uff1a");
        boolean simpleModeEnabled = com.jiuqi.np.log.LogHelper.isSimpleModeEnabled();
        int count = 0;
        for (String formulaKey : formulaKeys) {
            FormulaDefine formulaDefine = this.formulaRunTimeController.queryFormulaDefine(formulaKey);
            if (formulaDefine == null) continue;
            formulaInfo.append(formulaDefine.getCode()).append("\u3001");
            ++count;
            if (!simpleModeEnabled) continue;
            break;
        }
        if (count > 0) {
            formulaInfo.setLength(formulaInfo.length() - 1);
            if (count < formulaKeys.size()) {
                formulaInfo.append("\u7b49").append(formulaKeys.size()).append("\u6761\u516c\u5f0f");
            }
        } else {
            formulaInfo.append("\u7a7a");
        }
        return formulaInfo.toString();
    }

    public String getFormulaInfoByCode(Collection<String> formulaCodes) {
        if (CollectionUtils.isEmpty(formulaCodes)) {
            return "\u516c\u5f0f\u4fe1\u606f\uff1a\u7a7a";
        }
        StringBuilder formulaInfo = new StringBuilder("\u516c\u5f0f\u4fe1\u606f\uff1a");
        boolean simpleModeEnabled = com.jiuqi.np.log.LogHelper.isSimpleModeEnabled();
        int count = 0;
        for (String formulaCode : formulaCodes) {
            if (!StringUtils.hasText(formulaCode)) continue;
            formulaInfo.append(formulaCode).append("\u3001");
            ++count;
            if (!simpleModeEnabled) continue;
            break;
        }
        if (count > 0) {
            formulaInfo.setLength(formulaInfo.length() - 1);
            if (count < formulaCodes.size()) {
                formulaInfo.append("\u7b49").append(formulaCodes.size()).append("\u6761\u516c\u5f0f");
            }
        } else {
            formulaInfo.append("\u7a7a");
        }
        return formulaInfo.toString();
    }

    public String getFormulaSchemeInfo(List<String> formulaSchemeKeys) {
        if (CollectionUtils.isEmpty(formulaSchemeKeys)) {
            return "\u516c\u5f0f\u65b9\u6848\u4fe1\u606f\uff1a\u7a7a";
        }
        StringBuilder formulaSchemeInfo = new StringBuilder("\u516c\u5f0f\u65b9\u6848\u4fe1\u606f\uff1a");
        for (String formulaSchemeKey : formulaSchemeKeys) {
            FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
            if (formulaSchemeDefine == null) continue;
            formulaSchemeInfo.append(formulaSchemeDefine.getTitle()).append("\u3001");
        }
        if (formulaSchemeInfo.length() > 0) {
            formulaSchemeInfo.setLength(formulaSchemeInfo.length() - 1);
        } else {
            formulaSchemeInfo.append("\u7a7a");
        }
        return formulaSchemeInfo.toString();
    }

    public String getFormInfo(List<String> formKeys) {
        if (CollectionUtils.isEmpty(formKeys)) {
            return "\u62a5\u8868\u4fe1\u606f\uff1a\u7a7a";
        }
        StringBuilder formInfo = new StringBuilder("\u62a5\u8868\u4fe1\u606f\uff1a");
        boolean simpleModeEnabled = com.jiuqi.np.log.LogHelper.isSimpleModeEnabled();
        int count = 0;
        for (String formKey : formKeys) {
            FormDefine formDefine = this.getFormDefine(formKey);
            if (formDefine == null) continue;
            formInfo.append(formDefine.getTitle()).append("\u3010").append(formDefine.getFormCode()).append("\u3011").append("\u3001");
            ++count;
            if (!simpleModeEnabled) continue;
            break;
        }
        if (count > 0) {
            formInfo.setLength(formInfo.length() - 1);
            if (count < formKeys.size()) {
                formInfo.append("\u7b49").append(formKeys.size()).append("\u5f20\u62a5\u8868");
            }
        } else {
            formInfo.append("\u7a7a");
        }
        return formInfo.toString();
    }

    private FormDefine getFormDefine(String formKey) {
        if ("00000000-0000-0000-0000-000000000000".equals(formKey)) {
            RunTimeFormDefineImpl runTimeFormDefine = new RunTimeFormDefineImpl();
            runTimeFormDefine.setTitle("\u8868\u95f4");
            runTimeFormDefine.setFormCode("BJ");
            return runTimeFormDefine;
        }
        return this.runTimeViewController.queryFormById(formKey);
    }

    @Nullable
    public LogDimensionCollection getLogDimension(FormSchemeDefine formSchemeDefine, DimensionCollection dimensionCollection) {
        DimensionValueSet mergeDimensionValueSet = this.dimensionCollectionUtil.getMergeDimensionValueSet(dimensionCollection);
        if (mergeDimensionValueSet == null) {
            return null;
        }
        return this.getLogDimension(formSchemeDefine, mergeDimensionValueSet);
    }

    public LogDimensionCollection getLogDimension(FormSchemeDefine formSchemeDefine, DimensionValueSet dimensionValueSet) {
        String mainDimId = this.entityUtil.getContextMainDimId(formSchemeDefine.getDw());
        String mainDimName = this.entityMetaService.getDimensionName(mainDimId);
        String periodDimensionName = this.periodEngineService.getPeriodAdapter().getPeriodDimensionName();
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        Object mainDimValue = dimensionValueSet.getValue(mainDimName);
        if (mainDimValue instanceof String) {
            logDimensionCollection.setDw(mainDimId, new String[]{(String)mainDimValue});
        } else {
            logDimensionCollection.setDw(mainDimId, ((List)mainDimValue).toArray(new String[0]));
        }
        logDimensionCollection.setPeriod(formSchemeDefine.getDateTime(), dimensionValueSet.getValue(periodDimensionName).toString());
        return logDimensionCollection;
    }

    public LogDimensionCollection getLogDimension(FormSchemeDefine formSchemeDefine, Map<String, DimensionValue> dimensionValueMap) {
        String mainDimId = this.entityUtil.getContextMainDimId(formSchemeDefine.getDw());
        String mainDimName = this.entityMetaService.getDimensionName(mainDimId);
        String periodDimensionName = this.periodEngineService.getPeriodAdapter().getPeriodDimensionName();
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        String mainDimValue = dimensionValueMap.get(mainDimName).getValue();
        logDimensionCollection.setDw(mainDimId, mainDimValue.split(";"));
        logDimensionCollection.setPeriod(formSchemeDefine.getDateTime(), dimensionValueMap.get(periodDimensionName).getValue());
        return logDimensionCollection;
    }

    private String getMsg(String taskKey, LogDimensionCollection dimensionCollection, String title, String message) {
        return taskKey + "-" + title + "\n\u65f6\u671f\uff1a" + dimensionCollection.getPeriodDimension() + "\n\u5355\u4f4d\uff1a" + dimensionCollection.getDwDimension() + "\nMESSAGE:" + message;
    }

    private void logDebug(String taskKey, LogDimensionCollection dimensionCollection, String title, String message) {
        LOGGER.debug("\u4efb\u52a1\uff1a{}-{}\n\u65f6\u671f\uff1a{}\n\u5355\u4f4d\uff1a{}\nMESSAGE\uff1a{}", taskKey, title, dimensionCollection.getPeriodDimension(), dimensionCollection.getDwDimension(), message);
    }
}

