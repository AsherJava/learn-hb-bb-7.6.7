/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.logic.facade.param.input.GroupType
 *  com.jiuqi.nr.data.logic.internal.obj.EntityData
 *  com.jiuqi.nr.data.logic.internal.util.DimensionUtil
 *  com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil
 *  com.jiuqi.nr.dataentry.bean.FormulaAuditType
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo
 *  com.jiuqi.nr.dataentry.paramInfo.TaskData
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.FormulaSchemeData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.datacheck.fmlcheck.web;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.param.input.GroupType;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.datacheck.fmlcheck.FmlCheckConfig;
import com.jiuqi.nr.datacheck.fmlcheck.vo.CheckResultContext;
import com.jiuqi.nr.datacheck.fmlcheck.vo.CheckResultParam;
import com.jiuqi.nr.datacheck.fmlcheck.vo.DimDropdownQueryPar;
import com.jiuqi.nr.datacheck.fmlcheck.vo.DimensionVO;
import com.jiuqi.nr.datacheck.fmlcheck.vo.EntityDataVO;
import com.jiuqi.nr.datacheck.fmlcheck.vo.FmlSchemeVO;
import com.jiuqi.nr.datacheck.fmlcheck.vo.FormSchemeVO;
import com.jiuqi.nr.dataentry.bean.FormulaAuditType;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;
import com.jiuqi.nr.dataentry.paramInfo.TaskData;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.FormulaSchemeData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u516c\u5f0f\u5ba1\u6838"})
@RequestMapping(value={"/data-check/fml-check"})
public class FmlCheckController {
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private NrdbHelper nrdbHelper;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IAdjustPeriodService adjustPeriodService;
    private static final Logger logger = LoggerFactory.getLogger(FmlCheckController.class);

    @RequestMapping(value={"/list-fml-schemes/{formSchemeKey}"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u6240\u6709\u516c\u5f0f\u65b9\u6848")
    public List<FmlSchemeVO> queryRPTFmlSchemes(@PathVariable String formSchemeKey) {
        List allRPTFormulaSchemeDefinesByFormScheme;
        if (formSchemeKey != null && !CollectionUtils.isEmpty(allRPTFormulaSchemeDefinesByFormScheme = this.formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(formSchemeKey))) {
            return allRPTFormulaSchemeDefinesByFormScheme.stream().map(FmlSchemeVO::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @RequestMapping(value={"/query-formScheme/{formSchemeKey}"}, method={RequestMethod.GET})
    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u65b9\u6848")
    public FormSchemeVO queryFormScheme(@PathVariable String formSchemeKey) {
        FormSchemeDefine formScheme;
        if (formSchemeKey != null && (formScheme = this.runTimeViewController.getFormScheme(formSchemeKey)) != null) {
            return new FormSchemeVO(formScheme);
        }
        return null;
    }

    @RequestMapping(value={"/get-result-param"}, method={RequestMethod.POST})
    @ApiOperation(value="\u83b7\u53d6\u7ed3\u679c\u5c55\u793a\u6240\u9700\u53c2\u6570")
    public CheckResultParam getResultParam(@RequestBody CheckResultContext context) {
        return this.buildResultParam(context);
    }

    @RequestMapping(value={"/get-dim-dropdown"}, method={RequestMethod.POST})
    @ApiOperation(value="\u83b7\u53d6\u7ed3\u679c\u5c55\u793a\u6240\u9700\u60c5\u666f\u4e0b\u62c9\u5217\u8868")
    public List<DimensionVO> getDimDropdown(@RequestBody DimDropdownQueryPar par) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(par.getFormSchemeKey());
        DimensionValueSet masterKeys = DimensionUtil.getDimensionValueSet(par.getDimensionSet());
        return this.getDimDropdown(par, formScheme, masterKeys);
    }

    private CheckResultParam buildResultParam(CheckResultContext context) {
        CheckResultParam checkResultParam = new CheckResultParam();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(context.getTaskKey());
        checkResultParam.setTaskName(taskDefine.getTitle());
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(context.getFormSchemeKey());
        checkResultParam.setFormSchemeTitle(formScheme.getTitle());
        FmlCheckConfig checkConfig = context.getFmlCheckConfig();
        FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(checkConfig.getFormulaSchemeKey());
        checkResultParam.setFormulaSchemeTitle(formulaSchemeDefine.getTitle());
        checkResultParam.setPeriodName(context.getPeriod());
        Map<String, Object> sysParamMap = this.getSysParam(taskDefine.getKey());
        checkResultParam.setSysParam(sysParamMap);
        BatchCheckInfo batchCheckInfo = this.getCheckInfo(context, formScheme);
        checkResultParam.setCheckInfo(batchCheckInfo);
        List entityList = this.jtableParamService.getEntityList(formScheme.getKey());
        checkResultParam.setEntityList(entityList);
        FormulaSchemeData formulaSchemeData = new FormulaSchemeData();
        formulaSchemeData.init(formulaSchemeDefine);
        checkResultParam.setFormulaSchemeList(Collections.singletonList(formulaSchemeData));
        checkResultParam.setMasterDimName(this.entityMetaService.getDimensionName(formScheme.getDw()));
        checkResultParam.setEnableNrdb(this.nrdbHelper.isEnableNrdb());
        DimensionValueSet masterKeys = DimensionUtil.getDimensionValueSet((Map)batchCheckInfo.getContext().getDimensionSet());
        checkResultParam.setDimDropdown(this.getDimDropdown(context, formScheme, masterKeys));
        return checkResultParam;
    }

    private List<DimensionVO> getDimDropdown(CheckResultContext context, FormSchemeDefine formScheme, DimensionValueSet queryMasterKeys) {
        Map<String, String> dimSet = context.getDimSet();
        if (CollectionUtils.isEmpty(dimSet)) {
            return Collections.emptyList();
        }
        ArrayList<DimensionVO> result = new ArrayList<DimensionVO>();
        String period = context.getPeriod();
        String periodEntityId = formScheme.getDateTime();
        List dimEntities = this.entityUtil.getDimEntities(formScheme);
        for (EntityData dimEntity : dimEntities) {
            String[] split;
            String dimensionName = dimEntity.getDimensionName();
            String dimValues = dimSet.get(dimensionName);
            if (!StringUtils.hasText(dimValues) || (split = dimValues.split(";")).length <= 1) continue;
            String dimensionTitle = "";
            ArrayList<EntityDataVO> dims = new ArrayList<EntityDataVO>();
            if ("ADJUST_ENTITY_KIND".equals(dimEntity.getKind())) {
                dimensionTitle = "\u8c03\u6574\u671f";
                String dataScheme = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey()).getDataScheme();
                List adjustPeriods = this.adjustPeriodService.queryAdjustPeriods(dataScheme, period);
                Set valueSet = Arrays.stream(split).collect(Collectors.toSet());
                adjustPeriods.forEach(adjustPeriod -> {
                    if (valueSet.contains(adjustPeriod.getCode())) {
                        dims.add(new EntityDataVO(adjustPeriod.getCode(), adjustPeriod.getTitle()));
                    }
                });
            } else {
                if (dimEntity.getEntityDefine() != null) {
                    dimensionTitle = dimEntity.getEntityDefine().getTitle();
                }
                Date entityQueryVersionDate = this.entityUtil.period2Date(periodEntityId, period);
                IEntityQuery query = this.entityUtil.getEntityQuery(dimEntity.getKey(), entityQueryVersionDate, queryMasterKeys, null);
                query.sorted(true);
                query.sortedByQuery(false);
                ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                try {
                    IEntityTable entityTable = query.executeReader((IContext)executorContext);
                    entityTable.getAllRows().forEach(row -> dims.add(new EntityDataVO(row.getEntityKeyData(), row.getTitle())));
                }
                catch (Exception e) {
                    logger.error("\u5b9e\u4f53ID\u4e3a{}\u7684\u5b9e\u4f53\u6570\u636e\u672a\u627e\u5230:{}", dimEntity.getKey(), e.getMessage(), e);
                }
            }
            result.add(new DimensionVO(dimensionName, dimensionTitle, dims));
        }
        return result;
    }

    private BatchCheckInfo getCheckInfo(CheckResultContext context, FormSchemeDefine formScheme) {
        BatchCheckInfo batchCheckInfo = new BatchCheckInfo();
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(formScheme.getTaskKey());
        jtableContext.setFormSchemeKey(formScheme.getKey());
        Map<String, DimensionValue> dimensionValues = this.getDimensionValues(context, formScheme);
        jtableContext.setDimensionSet(dimensionValues);
        batchCheckInfo.setContext(jtableContext);
        FmlCheckConfig checkConfig = context.getFmlCheckConfig();
        batchCheckInfo.setAsyncTaskKey(checkConfig.getExecuteId());
        batchCheckInfo.setOrderField(GroupType.unit.getKey());
        batchCheckInfo.setFormulaSchemeKeys(checkConfig.getFormulaSchemeKey());
        Map<String, List<String>> formulas = checkConfig.getFormulas();
        if (formulas != null && !formulas.containsKey("ALL-FORM")) {
            batchCheckInfo.setFormulas(formulas);
        }
        batchCheckInfo.setCheckTypes(new ArrayList<Integer>(checkConfig.getCheckRequires().keySet()));
        return batchCheckInfo;
    }

    private Map<String, DimensionValue> getDimensionValues(CheckResultContext context, FormSchemeDefine formScheme) {
        Map<String, String> dimSet;
        HashMap<String, DimensionValue> dimensionValues = new HashMap<String, DimensionValue>();
        String periodDimensionName = this.periodEngineService.getPeriodAdapter().getPeriodDimensionName(formScheme.getDateTime());
        DimensionValue periodDimValue = new DimensionValue();
        periodDimValue.setName(periodDimensionName);
        periodDimValue.setValue(context.getPeriod());
        dimensionValues.put(periodDimensionName, periodDimValue);
        List<String> orgCode = context.getOrgCode();
        if (!CollectionUtils.isEmpty(orgCode)) {
            String dwDimName = this.entityMetaService.getDimensionName(formScheme.getDw());
            DimensionValue dwDimValue = new DimensionValue();
            dwDimValue.setName(dwDimName);
            dwDimValue.setValue(String.join((CharSequence)";", orgCode));
            dimensionValues.put(dwDimName, dwDimValue);
        }
        if (!CollectionUtils.isEmpty(dimSet = context.getDimSet())) {
            for (Map.Entry<String, String> e : dimSet.entrySet()) {
                String dimName = e.getKey();
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName(dimName);
                dimensionValue.setValue(e.getValue());
                dimensionValues.put(dimName, dimensionValue);
            }
        }
        return dimensionValues;
    }

    private Map<String, Object> getSysParam(String taskKey) {
        List<FormulaAuditType> formulaAuditTypes;
        HashMap<String, Object> sysParamMap = new HashMap<String, Object>();
        TaskData taskData = this.dataEntryParamService.getRuntimeTaskByKey(taskKey);
        if (taskData.getFlowButtonConfig() != null) {
            sysParamMap.put("FLOW_BUTTON_CONFIG", taskData.getFlowButtonConfig());
        }
        try {
            List auditTypes = this.auditTypeDefineService.queryAllAuditType();
            if (auditTypes == null || auditTypes.size() == 0) {
                formulaAuditTypes = FmlCheckController.getDefaultAuditTypes();
            } else {
                formulaAuditTypes = new ArrayList<FormulaAuditType>();
                for (AuditType auditType : auditTypes) {
                    FormulaAuditType formulaAuditType = new FormulaAuditType();
                    formulaAuditType.setKey(auditType.getCode().intValue());
                    formulaAuditType.setIcon(auditType.getIcon());
                    formulaAuditType.setTitle(auditType.getTitle());
                    formulaAuditType.setGridColor(auditType.getGridColor());
                    formulaAuditType.setBackGroundColor(auditType.getBackGroundColor());
                    formulaAuditType.setFontColor(auditType.getFontColor());
                    formulaAuditTypes.add(formulaAuditType);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            formulaAuditTypes = FmlCheckController.getDefaultAuditTypes();
        }
        sysParamMap.put("FORMULAAUDITING", formulaAuditTypes);
        String checkResultShowFormula = this.iNvwaSystemOptionService.get("nr-audit-group", "CHECK_RESULT_SHOW_FORMULA");
        sysParamMap.put("CHECK_RESULT_SHOW_FORMULA", checkResultShowFormula);
        String checkedErrorTrack = this.iNvwaSystemOptionService.get("nr-data-entry-group", "CHECKED_ERROR_TRACK_JTABLE_SHOW");
        sysParamMap.put("CHECKED_ERROR_TRACK_JTABLE_SHOW", checkedErrorTrack);
        String showErrorMsg = this.iNvwaSystemOptionService.get("nr-audit-group", "SHOW_ERROR_MSG");
        sysParamMap.put("SHOW_ERROR_MSG", showErrorMsg);
        String charNumOfErrorMsg = this.iNvwaSystemOptionService.get("nr-audit-group", "CHAR_NUMBER_OF_ERROR_MSG");
        sysParamMap.put("CHAR_NUMBER_OF_ERROR_MSG", charNumOfErrorMsg);
        String maxNumOfErrorMsg = this.iNvwaSystemOptionService.get("nr-audit-group", "MAX_NUMBER_OF_ERROR_MSG");
        sysParamMap.put("MAX_NUMBER_OF_ERROR_MSG", maxNumOfErrorMsg);
        String errMsgContainChinese = this.iNvwaSystemOptionService.get("nr-audit-group", "ERROR_MSG_CONTAIN_CHINESE_CHAR");
        sysParamMap.put("ERROR_MSG_CONTAIN_CHINESE_CHAR", errMsgContainChinese);
        String allowEditErrDesAfterUpload = this.iNvwaSystemOptionService.get("nr-audit-group", "ALLOW_EDIT_ERR_DES_AFTER_UPLOAD");
        sysParamMap.put("ALLOW_EDIT_ERR_DES_AFTER_UPLOAD", "1".equals(allowEditErrDesAfterUpload));
        return sysParamMap;
    }

    private static List<FormulaAuditType> getDefaultAuditTypes() {
        ArrayList<FormulaAuditType> defaultTypes = new ArrayList<FormulaAuditType>();
        FormulaAuditType hintType = new FormulaAuditType();
        hintType.setKey(1);
        hintType.setIcon("#icon-_Txiaoxitishi");
        hintType.setTitle("\u63d0\u793a\u578b");
        defaultTypes.add(hintType);
        FormulaAuditType warningType = new FormulaAuditType();
        warningType.setKey(2);
        warningType.setIcon("#icon-_Tjinggaotishi");
        warningType.setTitle("\u8b66\u544a\u578b");
        defaultTypes.add(warningType);
        FormulaAuditType errorType = new FormulaAuditType();
        errorType.setKey(4);
        errorType.setIcon("#icon-_Tcuowutishi");
        errorType.setTitle("\u9519\u8bef\u578b");
        defaultTypes.add(errorType);
        return defaultTypes;
    }
}

