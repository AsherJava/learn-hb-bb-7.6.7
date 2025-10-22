/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckDescription
 *  com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil
 *  com.jiuqi.nr.definition.api.IRunTimeFormulaController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.stream.param.FormulaSchemeListStream
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.checkdes.internal.helper;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.checkdes.exception.CKDIOException;
import com.jiuqi.nr.data.checkdes.facade.obj.ImpFailType;
import com.jiuqi.nr.data.checkdes.facade.obj.ImpFailedInfo;
import com.jiuqi.nr.data.checkdes.facade.obj.InvalidData;
import com.jiuqi.nr.data.checkdes.internal.ctxt.ExpContext;
import com.jiuqi.nr.data.checkdes.internal.ctxt.ImpContext;
import com.jiuqi.nr.data.checkdes.internal.io.BnDim;
import com.jiuqi.nr.data.checkdes.internal.io.BnDimFieldInfo;
import com.jiuqi.nr.data.checkdes.internal.io.CKDExpEntity;
import com.jiuqi.nr.data.checkdes.internal.util.IOUtils;
import com.jiuqi.nr.data.checkdes.obj.CKDTransObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.output.CheckDescription;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil;
import com.jiuqi.nr.definition.api.IRunTimeFormulaController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.stream.param.FormulaSchemeListStream;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class Helper {
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IColumnModelFinder columnModelFinder;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IRunTimeFormulaController formulaController;
    @Autowired
    private DimensionBuildUtil dimensionBuildUtil;
    @Autowired
    private ICheckErrorDescriptionService ckdService;
    private static final Logger logger = LoggerFactory.getLogger(Helper.class);

    public boolean validDimsSame(ImpContext context, @NonNull List<CKDExpEntity> ckdExpEntities) {
        CKDExpEntity ckdExpEntity = ckdExpEntities.get(0);
        List<InvalidData> invalidDataList = context.getCkdImpMes().getInvalidDataList();
        String invalidMes = null;
        FormSchemeDefine formSchemeByCode = context.getFormSchemeDefine();
        List<String> schemeDimEntityIds = this.getSchemeDimEntityIds(context.getFormSchemeDefine());
        int dimNum = schemeDimEntityIds.size();
        if (dimNum != ckdExpEntity.getDims().size()) {
            invalidMes = String.format("\u60c5\u666f\u6570\u91cf\u548c\u5f53\u524d\u62a5\u8868\u65b9\u6848%s\u4e0d\u5339\u914d:%s", formSchemeByCode.getFormSchemeCode(), ckdExpEntity.getDims());
        } else {
            Set<Map.Entry<String, String>> dimsEntrySet = ckdExpEntity.getDims().entrySet();
            for (Map.Entry<String, String> e : dimsEntrySet) {
                String entityId = e.getKey();
                boolean dimValid = schemeDimEntityIds.contains(entityId);
                if (dimValid) continue;
                invalidMes = String.format("\u60c5\u666f\u548c\u5f53\u524d\u62a5\u8868\u65b9\u6848%s\u4e0d\u5339\u914d:%s;\u4e0d\u5339\u914d\u7684\u60c5\u666fentityId:%s", formSchemeByCode.getFormSchemeCode(), ckdExpEntity.getDims(), entityId);
                break;
            }
        }
        if (invalidMes != null) {
            String finalInvalidMes = invalidMes;
            ckdExpEntities.forEach(o -> invalidDataList.add(new InvalidData((CKDExpEntity)o, finalInvalidMes)));
            return false;
        }
        return true;
    }

    public CKDTransObj getValidCKDTransObj(ImpContext context, CKDExpEntity ckdExpEntity) throws Exception {
        List<InvalidData> invalidDataList = context.getCkdImpMes().getInvalidDataList();
        FormSchemeDefine formSchemeByCode = context.getFormSchemeDefine();
        FormulaSchemeDefine formulaSchemeDefine = context.getFormulaSchemeDefines().get(ckdExpEntity.getFormulaSchemeTitle());
        if (formulaSchemeDefine == null) {
            String invalidMes = String.format("\u62a5\u8868\u65b9\u6848%s\u4e0b\u516c\u5f0f\u65b9\u6848\u4e0d\u5b58\u5728:%s", formSchemeByCode.getFormSchemeCode(), ckdExpEntity.getFormulaSchemeTitle());
            InvalidData invalidData = new InvalidData(ckdExpEntity, invalidMes);
            invalidDataList.add(invalidData);
            return null;
        }
        FormDefine formDefine = null;
        String formCode = ckdExpEntity.getFormCode();
        if (!"FORMULA-MAPPING-BETWEEN".equals(formCode) && (formDefine = this.runTimeViewController.queryFormByCodeInScheme(formSchemeByCode.getKey(), formCode)) == null) {
            String invalidMes = String.format("\u62a5\u8868\u65b9\u6848%s\u4e0b\u62a5\u8868\u4e0d\u5b58\u5728:%s", formSchemeByCode.getFormSchemeCode(), formCode);
            InvalidData invalidData = new InvalidData(ckdExpEntity, invalidMes);
            invalidDataList.add(invalidData);
            return null;
        }
        IParsedExpression parsedExpression = context.getParsedExpression(formulaSchemeDefine.getKey(), ckdExpEntity.getFormulaCode(), ckdExpEntity.getGlobRow(), ckdExpEntity.getGlobCol());
        if (parsedExpression == null) {
            String invalidMes = String.format("\u62a5\u8868\u65b9\u6848%s\u516c\u5f0f\u65b9\u6848%s\u4e0b\u516c\u5f0f\u4e0d\u5b58\u5728:%s-%s-%s", formSchemeByCode.getFormSchemeCode(), formulaSchemeDefine.getTitle(), ckdExpEntity.getFormulaCode(), ckdExpEntity.getGlobRow(), ckdExpEntity.getGlobCol());
            InvalidData invalidData = new InvalidData(ckdExpEntity, invalidMes);
            invalidDataList.add(invalidData);
            return null;
        }
        List<BnDim> bnDims = IOUtils.parseBNDim(ckdExpEntity.getDimStr());
        StringBuilder dataBaseDimStr = new StringBuilder();
        for (BnDim bnDim : bnDims) {
            String dimName = bnDim.isBizKey() ? "ID" : context.getDimensionName(bnDim.getDataTableCode(), bnDim.getDataFieldCode());
            if (dimName == null) {
                String invalidMes = String.format("\u8868\u5185\u7ef4\u5ea6\u672a\u627e\u5230:%s", bnDim);
                InvalidData invalidData = new InvalidData(ckdExpEntity, invalidMes);
                invalidDataList.add(invalidData);
                return null;
            }
            dataBaseDimStr.append(dimName).append(":").append(bnDim.getDimValue()).append(";");
        }
        CKDTransObj ckdTransObj = new CKDTransObj();
        ckdTransObj.setFormSchemeKey(formSchemeByCode.getKey());
        ckdTransObj.setFormulaSchemeTitle(formulaSchemeDefine.getTitle());
        ckdTransObj.setFormulaSchemeKey(formulaSchemeDefine.getKey());
        ckdTransObj.setFormCode(formDefine == null ? "FORMULA-MAPPING-BETWEEN" : formDefine.getFormCode());
        ckdTransObj.setFormKey(formDefine == null ? "00000000-0000-0000-0000-000000000000" : formDefine.getKey());
        ckdTransObj.setFormTitle(formDefine == null ? "\u8868\u95f4" : formDefine.getTitle());
        ckdTransObj.setFormulaKey(parsedExpression.getSource().getId());
        ckdTransObj.setFormulaCode(parsedExpression.getSource().getCode());
        ckdTransObj.setFormulaExpressionKey(parsedExpression.getKey());
        ckdTransObj.setGlobRow(String.valueOf(parsedExpression.getRealExpression().getWildcardRow()));
        ckdTransObj.setGlobCol(String.valueOf(parsedExpression.getRealExpression().getWildcardCol()));
        ckdTransObj.setDimStr(dataBaseDimStr.toString());
        ckdTransObj.setUserId(ckdExpEntity.getUserId());
        ckdTransObj.setUserNickName(ckdExpEntity.getUserNickName());
        ckdTransObj.setUpdateTime(ckdExpEntity.getUpdateTime());
        ckdTransObj.setDescription(ckdExpEntity.getDescription());
        HashMap<String, String> dimMap = new HashMap<String, String>();
        dimMap.put(context.getDimName(formSchemeByCode.getDw()), ckdExpEntity.getMdCode());
        String periodDimensionName = this.periodEngineService.getPeriodAdapter().getPeriodDimensionName(formSchemeByCode.getDateTime());
        dimMap.put(periodDimensionName, ckdExpEntity.getPeriod());
        for (Map.Entry<String, String> e : ckdExpEntity.getDims().entrySet()) {
            String dimName = "ADJUST".equals(e.getKey()) ? "ADJUST" : context.getDimName(e.getKey());
            dimMap.put(dimName, e.getValue());
        }
        ckdTransObj.setDimMap(dimMap);
        return ckdTransObj;
    }

    public CKDExpEntity transCKDExpEntity(ExpContext context, CheckDesObj checkDesObj) {
        DimensionValue mdDim = (DimensionValue)checkDesObj.getDimensionSet().get(context.getMdDimName());
        if (mdDim == null) {
            return null;
        }
        DimensionValue periodDim = (DimensionValue)checkDesObj.getDimensionSet().get(context.getPeriodDimName());
        if (periodDim == null) {
            return null;
        }
        FormulaSchemeDefine fmlSchemeByKey = context.getFmlSchemeByKey(checkDesObj.getFormulaSchemeKey());
        if (fmlSchemeByKey == null) {
            return null;
        }
        String formCodeByKey = context.getFormCodeByKey(checkDesObj.getFormKey());
        if (formCodeByKey == null) {
            return null;
        }
        IParsedExpression parsedExpression = context.getParsedExpression(fmlSchemeByKey.getKey(), checkDesObj.getFormulaCode(), checkDesObj.getGlobRow(), checkDesObj.getGlobCol());
        if (parsedExpression == null || !parsedExpression.getKey().equals(checkDesObj.getFormulaExpressionKey())) {
            return null;
        }
        String transExpDimStr = null;
        try {
            transExpDimStr = this.transExpDimStr(checkDesObj.getFloatId(), parsedExpression, context);
        }
        catch (CKDIOException e) {
            logger.warn(e.getMessage(), e);
        }
        if (transExpDimStr == null) {
            return null;
        }
        CKDExpEntity ckdExpEntity = new CKDExpEntity();
        ckdExpEntity.setMdCode(mdDim.getValue());
        ckdExpEntity.setPeriod(periodDim.getValue());
        ckdExpEntity.setFormulaSchemeTitle(fmlSchemeByKey.getTitle());
        ckdExpEntity.setFormCode(formCodeByKey);
        ckdExpEntity.setFormulaCode(checkDesObj.getFormulaCode());
        ckdExpEntity.setGlobRow(checkDesObj.getGlobRow());
        ckdExpEntity.setGlobCol(checkDesObj.getGlobCol());
        ckdExpEntity.setDimStr(transExpDimStr);
        CheckDescription checkDescription = checkDesObj.getCheckDescription();
        if (checkDescription != null) {
            ckdExpEntity.setDescription(checkDescription.getDescription());
            ckdExpEntity.setUserId(checkDescription.getUserId());
            ckdExpEntity.setUserNickName(checkDescription.getUserNickName());
            Instant updateTime = checkDescription.getUpdateTime();
            if (updateTime != null) {
                ckdExpEntity.setUpdateTime(updateTime.toEpochMilli());
            }
        }
        for (Map.Entry<String, String> e : context.getDinNameEntityIdMap().entrySet()) {
            String dimName = e.getKey();
            String entityId = e.getValue();
            DimensionValue dimensionValue = (DimensionValue)checkDesObj.getDimensionSet().get(dimName);
            ckdExpEntity.addDim(entityId, dimensionValue.getValue());
        }
        return ckdExpEntity;
    }

    public String transExpDimStr(String dataBaseDimStr, IParsedExpression parsedExpression, ExpContext context) throws CKDIOException {
        Map<String, String> dimMap = IOUtils.parseDataBaseDimStr(dataBaseDimStr);
        if (CollectionUtils.isEmpty(dimMap)) {
            throw new CKDIOException("\u8868\u5185\u7ef4\u5ea6\u4e3a\u7a7a");
        }
        StringBuilder r = new StringBuilder();
        for (Map.Entry<String, String> e : dimMap.entrySet()) {
            String dimName = e.getKey();
            String dimValue = e.getValue();
            if ("ID".equals(dimName)) {
                r.append("ID").append(":").append(dimValue).append(";");
                continue;
            }
            BnDimFieldInfo bnDimFieldInfo = context.getBnDimFieldInfo(dimName, parsedExpression);
            if (bnDimFieldInfo == null) {
                throw new CKDIOException(String.format("\u8868\u5185\u7ef4\u5ea6\u6307\u6807\u672a\u627e\u5230,\u7ef4\u5ea6\u540d:%s,\u516c\u5f0f:%s", dimName, parsedExpression.getKey()));
            }
            r.append(bnDimFieldInfo.getExpDimKey()).append(":").append(dimValue).append(";");
        }
        return r.toString();
    }

    public ImpFailedInfo getImpFailedInfo(CKDTransObj ckdTransObj, String message, ImpFailType impFailType) {
        ImpFailedInfo failedInfo = new ImpFailedInfo(impFailType);
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder(ckdTransObj.getDimensionValueSet());
        failedInfo.setCombination(builder.getCombination());
        failedInfo.setFormulaSchemeKey(ckdTransObj.getFormulaSchemeKey());
        failedInfo.setFormulaSchemeTitle(ckdTransObj.getFormulaSchemeTitle());
        failedInfo.setFormKey(ckdTransObj.getFormKey());
        failedInfo.setFormCode(ckdTransObj.getFormCode());
        failedInfo.setFormTitle(ckdTransObj.getFormTitle());
        failedInfo.setFormulaKey(ckdTransObj.getFormulaKey());
        failedInfo.setFormulaCode(ckdTransObj.getFormulaCode());
        failedInfo.setCkdTransObj(ckdTransObj);
        failedInfo.setErrorMessage(message);
        return failedInfo;
    }

    public IDataAccessService getDataAccessService(ImpContext impContext) {
        FormSchemeDefine formScheme = impContext.getFormSchemeDefine();
        return this.dataAccessServiceProvider.getDataAccessService(formScheme.getTaskKey(), formScheme.getKey());
    }

    @Nullable
    public TableModelRunInfo getTableModelRunInfo(String dataTableCode, ExecutorContext executorContext) {
        if (!StringUtils.hasText(dataTableCode)) {
            return null;
        }
        DataTable dataTableByCode = this.dataSchemeService.getDataTableByCode(dataTableCode);
        if (dataTableByCode == null) {
            return null;
        }
        List deployInfoByDataTableKey = this.dataSchemeService.getDeployInfoByDataTableKey(dataTableByCode.getKey());
        if (CollectionUtils.isEmpty(deployInfoByDataTableKey)) {
            return null;
        }
        String tableModelKey = ((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getTableModelKey();
        return this.getTableModelRunInfoByKey(tableModelKey, executorContext);
    }

    @Nullable
    public TableModelRunInfo getTableModelRunInfoByKey(String tableModelKey, ExecutorContext executorContext) {
        TableModelDefine tableModelDefineById = this.dataModelService.getTableModelDefineById(tableModelKey);
        return this.getTableModelRunInfo(executorContext, tableModelDefineById);
    }

    @Nullable
    public TableModelRunInfo getTableModelRunInfoByName(String tableName, ExecutorContext executorContext) {
        TableModelDefine tableModelDefineById = this.dataModelService.getTableModelDefineByName(tableName);
        return this.getTableModelRunInfo(executorContext, tableModelDefineById);
    }

    @Nullable
    public TableModelRunInfo getTableModelRunInfo(ExecutorContext executorContext, TableModelDefine tableModel) {
        if (tableModel == null) {
            return null;
        }
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        if (CollectionUtils.isEmpty(columns)) {
            return null;
        }
        TableModelRunInfo tableModelRunInfo = new TableModelRunInfo(tableModel, columns, this.columnModelFinder);
        try {
            tableModelRunInfo.buildTableInfo(executorContext);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return tableModelRunInfo;
    }

    public ExecutorContext getExecutorContext(FormSchemeDefine formSchemeDefine) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeDefine.getKey(), null, null);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        return executorContext;
    }

    public ColumnModelDefine getDataFieldColumn(@NonNull TableModelRunInfo tableModelRunInfo, String dataTableCode, String dataFieldCode) {
        if (!StringUtils.hasText(dataTableCode) || !StringUtils.hasText(dataFieldCode)) {
            return null;
        }
        DataTable dataTableByCode = this.dataSchemeService.getDataTableByCode(dataTableCode);
        if (dataTableByCode == null) {
            return null;
        }
        DataField dataField = this.dataSchemeService.getDataFieldByTableKeyAndCode(dataTableByCode.getKey(), dataFieldCode);
        if (dataField == null) {
            return null;
        }
        List deployInfoByDataTableKey = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataField.getKey()});
        if (CollectionUtils.isEmpty(deployInfoByDataTableKey)) {
            return null;
        }
        return tableModelRunInfo.getFieldByKey(((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getColumnModelKey());
    }

    @NonNull
    public List<IParsedExpression> getParsedExpression(@NonNull String formScheme, List<String> formulaSchemeKeys, List<String> formulaKeys) {
        if (CollectionUtils.isEmpty(formulaKeys)) {
            return Collections.emptyList();
        }
        if (CollectionUtils.isEmpty(formulaSchemeKeys)) {
            FormulaSchemeListStream formulaSchemeListStream = this.formulaController.listFormulaSchemeByFormScheme(formScheme);
            formulaSchemeKeys = formulaSchemeListStream.getList().stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(formulaSchemeKeys)) {
            return Collections.emptyList();
        }
        ArrayList iParsedExpressions = new ArrayList();
        for (String formulaSchemeKey : formulaSchemeKeys) {
            iParsedExpressions.addAll(this.formulaController.listExpressionBySchemeAndFormAndType(formulaSchemeKey, null, DataEngineConsts.FormulaType.CHECK));
        }
        return iParsedExpressions.stream().filter(o -> o.getSource() != null && formulaKeys.contains(o.getSource().getId())).collect(Collectors.toList());
    }

    @NonNull
    public List<String> getSchemeDimEntityIds(@NonNull FormSchemeDefine formSchemeDefine) {
        boolean enableAdjustPeriod;
        ArrayList<String> result = new ArrayList<String>();
        String dims = formSchemeDefine.getDims();
        if (StringUtils.hasText(dims)) {
            String[] split = dims.split(";");
            result.addAll(Arrays.asList(split));
        }
        if ((enableAdjustPeriod = this.formSchemeService.enableAdjustPeriod(formSchemeDefine.getKey())) && !result.contains("ADJUST")) {
            result.add("ADJUST");
        }
        return result;
    }

    public PeriodEngineService getPeriodEngineService() {
        return this.periodEngineService;
    }

    public IFormSchemeService getFormSchemeService() {
        return this.formSchemeService;
    }

    public IRunTimeViewController getRunTimeViewController() {
        return this.runTimeViewController;
    }

    public IRuntimeDataSchemeService getDataSchemeService() {
        return this.dataSchemeService;
    }

    public DimensionBuildUtil getDimensionBuildUtil() {
        return this.dimensionBuildUtil;
    }

    public ICheckErrorDescriptionService getCkdService() {
        return this.ckdService;
    }
}

