/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.impl.DefaultConnectionProvider
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.node.CalcExpression
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.util.CalcExpressionSortUtil
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionDesignTimeController2
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IViewDeployController
 *  com.jiuqi.nr.definition.deploy.DeployRefreshFormulaEvent
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.DesignTimeViewController
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.impl.AuditTypeImpl
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.definition.util.DefinitionOptionUtils
 *  com.jiuqi.nr.definition.util.ParsedExpressionFilter
 *  com.jiuqi.nr.definition.util.ServeCodeService
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IDesignTimeFMDMAttributeService
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  com.jiuqi.util.StringUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.designer.web.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.impl.DefaultConnectionProvider;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.util.CalcExpressionSortUtil;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.impl.controller.DataDefinitionDesignTimeController2;
import com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.IViewDeployController;
import com.jiuqi.nr.definition.deploy.DeployRefreshFormulaEvent;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.DesignTimeViewController;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.impl.AuditTypeImpl;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.definition.util.DefinitionOptionUtils;
import com.jiuqi.nr.definition.util.ParsedExpressionFilter;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService;
import com.jiuqi.nr.designer.service.IFormFormulaService;
import com.jiuqi.nr.designer.service.SearchFormulaService;
import com.jiuqi.nr.designer.util.CycleUtil;
import com.jiuqi.nr.designer.util.EntityDefineObject;
import com.jiuqi.nr.designer.web.facade.AssFormulaCheckObj;
import com.jiuqi.nr.designer.web.facade.AuditTypeObj;
import com.jiuqi.nr.designer.web.facade.FormulaCheckObj;
import com.jiuqi.nr.designer.web.facade.FormulaObj;
import com.jiuqi.nr.designer.web.facade.FormulaSearchParam;
import com.jiuqi.nr.designer.web.facade.cycle.CycleData;
import com.jiuqi.nr.designer.web.facade.cycle.CycleTree;
import com.jiuqi.nr.designer.web.rest.vo.FormulaDesCreateParam;
import com.jiuqi.nr.designer.web.service.CSFormulaMonitor;
import com.jiuqi.nr.designer.web.service.FormulaMonitor;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IDesignTimeFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import com.jiuqi.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class FormulaParserController {
    private static final Logger log = LoggerFactory.getLogger(FormulaParserController.class);
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private IDataDefinitionDesignTimeController npController;
    @Autowired
    private IDataDefinitionRuntimeController npRuntimeController;
    @Autowired
    private IDesignTimeViewController controller;
    @Autowired
    private TaskPlanPublishExternalService taskPlanPublishExternalService;
    @Autowired
    private IFormFormulaService formulaService;
    @Autowired
    private IRunTimeViewController runTimeController;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    private IViewDeployController iviewDeployController;
    @Autowired
    private DesignTimeViewController designTimeViewController;
    @Autowired
    private SearchFormulaService searchFormulaService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ServeCodeService serveCodeService;
    @Autowired
    private IFormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    @Qualifier(value="DataDefinitionDesignTimeController2")
    private DataDefinitionDesignTimeController2 npDesignTimeController;
    @Autowired
    private DataDefinitionRuntimeController2 npRunTimeController;
    @Autowired
    private IEntityDataService iEntityDataService;
    @Autowired
    private IDesignTimeFMDMAttributeService iFMDMAttributeServicel;
    @Autowired
    DataModelService designDataModelService;
    @Autowired
    private IEntityViewRunTimeController viewRunTimeController;
    @Autowired
    private IDesignDataSchemeService dataSchemeService;
    @Autowired
    private DefinitionOptionUtils definitionOptionUtils;
    @Autowired
    private ParsedExpressionFilter parsedExpressionFilter;
    private static String FORMULA_CODE_REPEAT = "\u516c\u5f0f\u7f16\u53f7\u91cd\u590d!";
    private static String FORMULA_BIAOJIAN = "number_formulas";
    private static String FORMULA_TYPE_NULL = "\u516c\u5f0f\u7c7b\u578b\u4e3a\u7a7a\uff01";

    @ApiOperation(value="\u516c\u5f0f\u6821\u9a8c")
    @RequestMapping(value={"assembly_formula_parse"}, method={RequestMethod.POST})
    public List<FormulaCheckObj> checkFormula(@RequestBody AssFormulaCheckObj assFormulaCheckObj) throws JQException {
        if (assFormulaCheckObj != null) {
            FormulaCheckObj formulaCheckObj = new FormulaCheckObj();
            formulaCheckObj.setFormula(assFormulaCheckObj.getFormula());
            formulaCheckObj.setUseCalculate(assFormulaCheckObj.isUseCalculate());
            formulaCheckObj.setUseCheck(assFormulaCheckObj.isUseCheck());
            formulaCheckObj.setReportName(assFormulaCheckObj.getReportName());
            String formScheme = assFormulaCheckObj.getFormScheme();
            ArrayList<FormulaCheckObj> formuObjList = new ArrayList<FormulaCheckObj>();
            formuObjList.add(formulaCheckObj);
            ArrayList<FormulaCheckObj> useCalculateList = new ArrayList<FormulaCheckObj>();
            ArrayList<FormulaCheckObj> useCheckList = new ArrayList<FormulaCheckObj>();
            ArrayList<FormulaCheckObj> useBalanceList = new ArrayList<FormulaCheckObj>();
            List<FormulaCheckObj> formulaCheckObjs = this.formulaTypeClassification(useCalculateList, useCheckList, useBalanceList, formuObjList);
            useBalanceList.removeIf(item -> StringUtils.isEmpty((String)item.getBalanceZBExp()));
            FormulaMonitor formulaMonitor = new FormulaMonitor();
            if (useCalculateList.size() > 0) {
                ArrayList<Formula> useCalculateLists = new ArrayList<Formula>(useCalculateList);
                this.parseFormulas(formScheme, useCalculateLists, DataEngineConsts.FormulaType.CALCULATE, formulaMonitor);
            }
            if (useCheckList.size() > 0) {
                ArrayList<Formula> useCheckLists = new ArrayList<Formula>(useCheckList);
                this.parseFormulas(formScheme, useCheckLists, DataEngineConsts.FormulaType.CHECK, formulaMonitor);
            }
            List<Object> formulaCheckObjs1 = new ArrayList();
            if (useBalanceList.size() > 0) {
                ArrayList<Formula> useBalanceLists = new ArrayList<Formula>(useBalanceList);
                formulaCheckObjs1 = this.parseBalanceFormula(formScheme, useBalanceLists, formulaMonitor);
            }
            Map<String, FormulaCheckObj> checkResultMap = formulaMonitor.getCheckResultMap();
            Collection<FormulaCheckObj> values = checkResultMap.values();
            ArrayList<FormulaCheckObj> checkResultList = new ArrayList<FormulaCheckObj>(values);
            checkResultList.addAll(formulaCheckObjs1);
            return checkResultList;
        }
        return Collections.emptyList();
    }

    @ApiOperation(value="\u516c\u5f0f\u7f16\u53f7\u6821\u9a8c")
    @RequestMapping(value={"formulaCodeParse"}, method={RequestMethod.POST})
    public Boolean checkFormula(@RequestBody FormulaObj designFormulaDefine) throws JQException {
        if (designFormulaDefine != null) {
            String formulaSchemeKey = designFormulaDefine.getSchemeKey();
            Map codeCountMap = this.nrDesignTimeController.getFormulaCodeCountByScheme(formulaSchemeKey);
            Integer newf = (Integer)codeCountMap.get(designFormulaDefine.getCode());
            if (newf == null) {
                newf = 0;
            }
            newf = newf + 1;
            codeCountMap.put(designFormulaDefine.getCode(), newf);
            Integer i = (Integer)codeCountMap.get(designFormulaDefine.getCode());
            if (i > 1) {
                return false;
            }
            return true;
        }
        return false;
    }

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u4e0b\u6240\u6709\u679a\u4e3e\u7c7b\u578b")
    @RequestMapping(value={"allEnumByForm/{formKey}"}, method={RequestMethod.GET})
    public List<EntityDefineObject> allEnumByForm(@PathVariable String formKey) throws Exception {
        List allRegionsInForm = this.designTimeViewController.getAllRegionsInForm(formKey);
        ArrayList fieldKeys = new ArrayList();
        ArrayList<IEntityDefine> entityDefineList = new ArrayList<IEntityDefine>();
        for (DesignDataRegionDefine region : allRegionsInForm) {
            List allLinksInRegion = this.nrDesignTimeController.getAllLinksInRegion(region.getKey());
            allLinksInRegion.forEach(l -> {
                if (!fieldKeys.contains(l.getLinkExpression()) && StringUtils.isNotEmpty((String)l.getLinkExpression())) {
                    fieldKeys.add(l.getLinkExpression());
                }
            });
        }
        DesignFormDefine queryFormById = this.designTimeViewController.queryFormById(formKey);
        if (null == queryFormById) {
            return new ArrayList<EntityDefineObject>();
        }
        if (FormType.FORM_TYPE_NEWFMDM.getValue() == queryFormById.getFormType().getValue()) {
            FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
            DesignFormSchemeDefine formscheme = this.designTimeViewController.queryFormSchemeDefine(queryFormById.getFormScheme());
            DesignTaskDefine taskdefine = this.designTimeViewController.queryTaskDefine(formscheme.getTaskKey());
            fmdmAttributeDTO.setFormSchemeKey(formscheme.getKey());
            List attributes1 = this.iFMDMAttributeServicel.list(fmdmAttributeDTO);
            for (IFMDMAttribute fmdm : attributes1) {
                for (String code : fieldKeys) {
                    TableModelDefine tableModel;
                    IEntityDefine queryEntity;
                    if (!fmdm.getCode().equals(code) || !StringUtils.isNotEmpty((String)fmdm.getReferTableID()) || null == (queryEntity = this.iEntityMetaService.queryEntityByCode((tableModel = this.designDataModelService.getTableModelDefineById(fmdm.getReferTableID())).getCode()))) continue;
                    entityDefineList.add(queryEntity);
                }
            }
        } else {
            List feilds = this.npDesignTimeController.queryFieldDefines(fieldKeys.toArray(new String[0]));
            for (DesignFieldDefine field : feilds) {
                if (!StringUtils.isNotEmpty((String)field.getEntityKey())) continue;
                try {
                    IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(field.getEntityKey());
                    if (null == queryEntity) continue;
                    entityDefineList.add(queryEntity);
                }
                catch (Exception exception) {}
            }
        }
        List collect = entityDefineList.stream().map(e -> new EntityDefineObject((IEntityDefine)e)).collect(Collectors.toList());
        return collect.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<EntityDefineObject>(Comparator.comparing(EntityDefineObject::getId))), ArrayList::new));
    }

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u4e0b\u6240\u6709\u8fd0\u884c\u671f\u679a\u4e3e\u7c7b\u578b")
    @RequestMapping(value={"allRunEnumByForm/{formKey}"}, method={RequestMethod.GET})
    public List<EntityDefineObject> allRunEnumByForm(@PathVariable String formKey) throws Exception {
        List allRegionsInForm = this.runTimeController.getAllRegionsInForm(formKey);
        ArrayList fieldKeys = new ArrayList();
        for (DataRegionDefine region : allRegionsInForm) {
            List allLinksInRegion = this.runTimeController.getAllLinksInRegion(region.getKey());
            allLinksInRegion.forEach(l -> {
                if (!fieldKeys.contains(l.getLinkExpression()) && StringUtils.isNotEmpty((String)l.getLinkExpression())) {
                    fieldKeys.add(l.getLinkExpression());
                }
            });
        }
        ArrayList<IEntityDefine> entityDefineList = new ArrayList<IEntityDefine>();
        FormDefine queryFormById = this.runTimeController.queryFormById(formKey);
        if (null == queryFormById) {
            return new ArrayList<EntityDefineObject>();
        }
        if (FormType.FORM_TYPE_NEWFMDM.getValue() == queryFormById.getFormType().getValue()) {
            FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
            FormSchemeDefine formscheme = this.runTimeController.getFormScheme(queryFormById.getFormScheme());
            TaskDefine taskdefine = this.runTimeController.queryTaskDefine(formscheme.getTaskKey());
            fmdmAttributeDTO.setFormSchemeKey(formscheme.getKey());
            List attributes1 = this.iFMDMAttributeServicel.list(fmdmAttributeDTO);
            for (IFMDMAttribute fmdm : attributes1) {
                for (String code : fieldKeys) {
                    TableModelDefine tableModel;
                    IEntityDefine queryEntity;
                    if (!fmdm.getCode().equals(code) || !StringUtils.isNotEmpty((String)fmdm.getReferTableID()) || null == (queryEntity = this.iEntityMetaService.queryEntityByCode((tableModel = this.designDataModelService.getTableModelDefineById(fmdm.getReferTableID())).getCode()))) continue;
                    entityDefineList.add(queryEntity);
                }
            }
        } else {
            List feilds = this.npRunTimeController.queryFieldDefines(fieldKeys);
            for (FieldDefine field : feilds) {
                if (!StringUtils.isNotEmpty((String)field.getEntityKey())) continue;
                try {
                    IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(field.getEntityKey());
                    if (null == queryEntity) continue;
                    entityDefineList.add(queryEntity);
                }
                catch (Exception exception) {}
            }
        }
        List collect = entityDefineList.stream().map(e -> new EntityDefineObject((IEntityDefine)e)).collect(Collectors.toList());
        return collect.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<EntityDefineObject>(Comparator.comparing(EntityDefineObject::getId))), ArrayList::new));
    }

    @ApiOperation(value="\u67e5\u8be2\u6307\u5b9a\u679a\u4e3e\u4e0b\u6240\u6709\u679a\u4e3e\u9879")
    @RequestMapping(value={"allEnumContentByEnum/{entityKey}"}, method={RequestMethod.GET})
    public List<Map<String, String>> allEnumContentByEnum(@PathVariable String entityKey) throws Exception {
        IEntityQuery entityQuery = this.iEntityDataService.newEntityQuery();
        entityQuery.sorted(true);
        entityQuery.setIgnoreViewFilter(true);
        entityQuery.setAuthorityOperations(AuthorityType.None);
        entityQuery.setEntityView(this.viewRunTimeController.buildEntityView(entityKey));
        IContext executorContext = null;
        List allRows = new ArrayList();
        try {
            IEntityTable executeReader = entityQuery.executeReader(executorContext);
            allRows = executeReader.getAllRows();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        ArrayList<Map<String, String>> resList = new ArrayList<Map<String, String>>();
        allRows.forEach(ier -> resList.add(new HashMap<String, String>(){
            {
                this.put("label", ier.getTitle());
                this.put("value", ier.getCode());
            }
        }));
        return resList;
    }

    @ApiOperation(value="\u516c\u5f0f\u6821\u9a8c")
    @RequestMapping(value={"formula_parse"}, method={RequestMethod.POST})
    public List<FormulaCheckObj> checkFormula(@RequestBody List<FormulaCheckObj> formuObjList, @RequestParam String schemeKey) throws JQException {
        if (formuObjList != null) {
            ArrayList<FormulaCheckObj> useCalculateList = new ArrayList<FormulaCheckObj>();
            ArrayList<FormulaCheckObj> useCheckList = new ArrayList<FormulaCheckObj>();
            ArrayList<FormulaCheckObj> useBalanceList = new ArrayList<FormulaCheckObj>();
            List<FormulaCheckObj> formulaCheckObjs = this.formulaTypeClassification(useCalculateList, useCheckList, useBalanceList, formuObjList);
            FormulaMonitor formulaMonitor = new FormulaMonitor();
            if (useCalculateList.size() > 0) {
                ArrayList<Formula> useCalculateLists = new ArrayList<Formula>(useCalculateList);
                this.parseFormulas(schemeKey, useCalculateLists, DataEngineConsts.FormulaType.CALCULATE, formulaMonitor);
            }
            if (useCheckList.size() > 0) {
                ArrayList<Formula> useCheckLists = new ArrayList<Formula>(useCheckList);
                this.parseFormulas(schemeKey, useCheckLists, DataEngineConsts.FormulaType.CHECK, formulaMonitor);
            }
            List<Object> formulaCheckObjs1 = new ArrayList();
            if (useBalanceList.size() > 0) {
                ArrayList<Formula> useBalanceLists = new ArrayList<Formula>(useBalanceList);
                formulaCheckObjs1 = this.parseBalanceFormula(schemeKey, useBalanceLists, formulaMonitor);
            }
            Map<String, FormulaCheckObj> checkResultMap = formulaMonitor.getCheckResultMap();
            Collection<FormulaCheckObj> values = checkResultMap.values();
            ArrayList<FormulaCheckObj> checkResultList = new ArrayList<FormulaCheckObj>(values);
            checkResultList.addAll(formulaCheckObjs1);
            checkResultList.addAll(formulaCheckObjs);
            if (formuObjList.size() == 1) {
                this.realFormulaCodeCheck(checkResultList, formuObjList);
            }
            this.checkFormulaOrder(checkResultList);
            return checkResultList;
        }
        return Collections.emptyList();
    }

    @ApiOperation(value="\u516c\u5f0f\u6821\u9a8c")
    @RequestMapping(value={"formula_parse_rsa"}, method={RequestMethod.POST})
    @RequiresPermissions(value={"nr:task_formula:manage"})
    public List<FormulaCheckObj> checkFormulaRsa(@RequestBody @SFDecrypt List<FormulaCheckObj> formuObjList, @RequestParam String schemeKey) throws JQException {
        return this.checkFormula(formuObjList, schemeKey);
    }

    @ApiOperation(value="\u516c\u5f0f\u5faa\u73af\u516c\u5f0f\u68c0\u67e5")
    @RequestMapping(value={"formula_cycle_check/{schemeKey}"}, method={RequestMethod.GET})
    public List<CycleTree> checkFormulaRsaCycle(@PathVariable(value="schemeKey") String schemeKey) throws JQException {
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.queryFormulaSchemeDefine(schemeKey);
        List formDefines = this.designTimeViewController.queryAllSoftFormDefinesByFormScheme(formulaScheme.getFormSchemeKey());
        Map<String, String> formCodeToTitle = formDefines.stream().collect(Collectors.toMap(FormDefine::getFormCode, IBaseMetaItem::getTitle));
        FormulaMonitor formulaMonitor = new FormulaMonitor();
        this.checkCycle(formulaScheme.getFormSchemeKey(), this.getCalcFormulaByScheme(formulaScheme), formulaMonitor);
        Map<String, List<Formula>> cycles = formulaMonitor.getCycles();
        ArrayList<CycleTree> treeObjs = new ArrayList<CycleTree>();
        int index = 0;
        for (String order : cycles.keySet()) {
            CycleTree treeObj = new CycleTree();
            treeObj.setId(order);
            treeObj.setCode(order);
            treeObj.setIsLeaf(true);
            treeObj.setTitle("\u5faa\u73af" + ++index);
            CycleData data = new CycleData();
            data.setKey(order);
            data.setCode(order);
            data.setTitle("\u5faa\u73af" + index);
            List<Formula> formulas = CycleUtil.distinctCycle(cycles.get(order));
            for (Formula formula : formulas) {
                if (StringUtils.isNotEmpty((String)formula.getReportName())) {
                    String formTitle = formCodeToTitle.get(formula.getReportName());
                    String shwoTitle = formTitle.concat("(").concat(formula.getReportName()).concat(")");
                    formula.setMeanning(shwoTitle);
                    continue;
                }
                formula.setMeanning("\u8868\u95f4\u516c\u5f0f");
            }
            data.setFormulas(formulas);
            treeObj.setData(data);
            treeObjs.add(treeObj);
        }
        return treeObjs;
    }

    @RequestMapping(value={"/export_cycle_formulas/{schemeKey}"}, method={RequestMethod.GET})
    @ApiOperation(value="\u5bfc\u51fa\u5f53\u524d\u65b9\u6848\u4e0b\u6240\u6709\u516c\u5f0f\u8868")
    public void exportCycleFormulas(@PathVariable String schemeKey, HttpServletResponse res) throws IOException, JQException {
        DesignFormulaSchemeDefine formulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(schemeKey);
        if (formulaSchemeDefine == null) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_019);
        }
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.queryFormulaSchemeDefine(schemeKey);
        List formDefines = this.designTimeViewController.queryAllSoftFormDefinesByFormScheme(formulaScheme.getFormSchemeKey());
        Map<String, String> formCodeToTitle = formDefines.stream().collect(Collectors.toMap(FormDefine::getFormCode, IBaseMetaItem::getTitle));
        FormulaMonitor formulaMonitor = new FormulaMonitor();
        this.checkCycle(formulaScheme.getFormSchemeKey(), this.getCalcFormulaByScheme(formulaScheme), formulaMonitor);
        Map<String, List<Formula>> cycles = formulaMonitor.getCycles();
        this.formulaService.exportCycleFormulaExcel(cycles, formCodeToTitle, res);
    }

    private List<Formula> getCalcFormulaByScheme(DesignFormulaSchemeDefine formulaScheme) throws JQException {
        List allFormula = this.formulaDesignTimeController.getAllFormulasInScheme(formulaScheme.getKey());
        List formDefines = this.designTimeViewController.queryAllSoftFormDefinesByFormScheme(formulaScheme.getFormSchemeKey());
        Map<String, String> formKeyToCode = formDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, FormDefine::getFormCode));
        List calcFormulas = allFormula.stream().filter(e -> e.getUseCalculate()).collect(Collectors.toList());
        ArrayList<Formula> useCalculateLists = new ArrayList<Formula>();
        for (DesignFormulaDefine formulaDefine : calcFormulas) {
            FormulaCheckObj obj = new FormulaCheckObj();
            obj.setSchemeKey(formulaScheme.getKey());
            obj.setUseCalculate(true);
            obj.setUseCheck(false);
            obj.setUseBalance(false);
            obj.setCode(formulaDefine.getCode());
            obj.setFormula(formulaDefine.getExpression());
            if (null != formKeyToCode.get(formulaDefine.getFormKey())) {
                obj.setReportName(formKeyToCode.get(formulaDefine.getFormKey()));
                useCalculateLists.add(obj);
                continue;
            }
            useCalculateLists.add(obj);
        }
        return useCalculateLists;
    }

    private List<IParsedExpression> checkCycle(String formSchemeKey, List<Formula> formulaList, IMonitor formulaMonitor) throws JQException {
        List<IParsedExpression> parsedExpressions = new ArrayList();
        try {
            ExecutorContext context = new ExecutorContext(this.npRuntimeController);
            DataEngineConsts.FormulaShowType showType = this.getFormulaShowTypeByFormulaScheme(formSchemeKey);
            context.setJQReportModel(showType == DataEngineConsts.FormulaShowType.JQ);
            context.setDesignTimeData(true, this.npController);
            List formulaVariables = this.formulaDesignTimeController.queryAllFormulaVariable(formSchemeKey);
            DesignReportFmlExecEnvironment environment = new DesignReportFmlExecEnvironment(this.controller, this.npController, formSchemeKey, formulaVariables);
            context.setEnv((IFmlExecEnvironment)environment);
            parsedExpressions = DataEngineFormulaParser.parseFormula((ExecutorContext)context, formulaList, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CALCULATE, (IMonitor)formulaMonitor);
            ArrayList<CalcExpression> allCalc = new ArrayList<CalcExpression>();
            for (IParsedExpression parsedExpression : parsedExpressions) {
                if (!(parsedExpression instanceof CalcExpression)) continue;
                allCalc.add((CalcExpression)parsedExpression);
            }
            ArrayList noCycle = new ArrayList();
            ArrayList cycle = new ArrayList();
            QueryParam queryParam = new QueryParam((IConnectionProvider)new DefaultConnectionProvider(), (IDataDefinitionRuntimeController)this.npRunTimeController);
            QueryContext queryContext = new QueryContext(context, queryParam, formulaMonitor);
            CalcExpressionSortUtil.analysisCycles_new((QueryContext)queryContext, allCalc, noCycle, cycle);
        }
        catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_017, (Throwable)e);
        }
        return parsedExpressions;
    }

    @ApiOperation(value="\u6761\u4ef6\u6837\u5f0f\u4e2d\u7684\u6761\u4ef6\u6821\u9a8c")
    @RequestMapping(value={"cs_formula_parse"}, method={RequestMethod.POST})
    public List<FormulaCheckObj> checkCSFormula(@RequestBody List<FormulaCheckObj> formuObjList, @RequestParam String schemeKey) throws JQException {
        if (!CollectionUtils.isEmpty(formuObjList)) {
            CSFormulaMonitor formulaMonitor = new CSFormulaMonitor();
            ArrayList<Formula> useCheckLists = new ArrayList<Formula>(formuObjList);
            this.parseFormulas(schemeKey, useCheckLists, DataEngineConsts.FormulaType.CHECK, formulaMonitor);
            Map<String, FormulaCheckObj> checkResultMap = formulaMonitor.getCheckResultMap();
            Collection<FormulaCheckObj> values = checkResultMap.values();
            ArrayList<FormulaCheckObj> checkResultList = new ArrayList<FormulaCheckObj>(values);
            return checkResultList;
        }
        return Collections.emptyList();
    }

    private void checkFormulaOrder(List<FormulaCheckObj> formulaCheckObjs) {
        String regex = "^[a-zA-Z][\\w-]{0,25}$";
        String errMsg = "\u516c\u5f0f\u7f16\u53f7\u4e0d\u7b26\u5408\u5b57\u6bcd\u5f00\u5934,\u5b57\u6bcd\u52a0\u6570\u5b57\u4e0b\u5212\u7ebf\uff0c\u957f\u5ea6\u9650\u523625\u8981\u6c42!";
        for (FormulaCheckObj fco : formulaCheckObjs) {
            if (Pattern.matches(regex, fco.getCode())) continue;
            if (StringUtils.isEmpty((String)fco.getErrorMsg())) {
                fco.setErrorMsg(errMsg);
                continue;
            }
            fco.setErrorMsg(fco.getErrorMsg() + errMsg);
        }
    }

    private List<FormulaCheckObj> formulaTypeClassification(List<FormulaCheckObj> useCalculateList, List<FormulaCheckObj> useCheckList, List<FormulaCheckObj> useBalanceList, List<FormulaCheckObj> formuObjList) {
        ArrayList<FormulaCheckObj> formulaTypeNll = new ArrayList<FormulaCheckObj>();
        for (int i = 0; i < formuObjList.size(); ++i) {
            if (formuObjList.get(i).getFormula() == null || formuObjList.get(i).getFormula().startsWith("//")) continue;
            if (!(formuObjList.get(i).isUseCalculate() || formuObjList.get(i).isUseCheck() || formuObjList.get(i).isUseBalance())) {
                formuObjList.get(i).setErrorMsg(FORMULA_TYPE_NULL);
                formulaTypeNll.add(formuObjList.get(i));
                continue;
            }
            if (formuObjList.get(i).isUseCalculate()) {
                useCalculateList.add(formuObjList.get(i));
            }
            if (formuObjList.get(i).isUseCheck()) {
                useCheckList.add(formuObjList.get(i));
            }
            if (!formuObjList.get(i).isUseBalance()) continue;
            useBalanceList.add(formuObjList.get(i));
        }
        return formulaTypeNll;
    }

    private void realFormulaCodeCheck(List<FormulaCheckObj> checkResultList, List<FormulaCheckObj> formuObjList) throws JQException {
        FormulaObj formula = new FormulaObj();
        formula.setFormKey(formuObjList.get(0).getFormKey());
        formula.setCode(formuObjList.get(0).getCode());
        formula.setSchemeKey(formuObjList.get(0).getSchemeKey());
        List<FormulaCheckObj> formulaCodeCheck = this.formulaCodeCheck(formula);
        checkResultList.addAll(formulaCodeCheck);
    }

    private List<FormulaCheckObj> parseBalanceFormula(String formSchemeKey, List<Formula> formulaList, FormulaMonitor formulaMonitor) throws JQException {
        ArrayList<FormulaCheckObj> result = new ArrayList<FormulaCheckObj>();
        try {
            ExecutorContext context = new ExecutorContext(this.npRuntimeController);
            DataEngineConsts.FormulaShowType showType = this.getFormulaShowTypeByFormulaScheme(formSchemeKey);
            context.setJQReportModel(showType == DataEngineConsts.FormulaShowType.JQ);
            context.setDesignTimeData(true, this.npController);
            List formulaVariables = this.formulaDesignTimeController.queryAllFormulaVariable(formSchemeKey);
            DesignReportFmlExecEnvironment environment = new DesignReportFmlExecEnvironment(this.controller, this.npController, formSchemeKey, formulaVariables);
            context.setEnv((IFmlExecEnvironment)environment);
            for (Formula formula : formulaList) {
                if (StringUtils.isNotEmpty((String)formula.getBalanceZBExp())) {
                    String dataFieldKey;
                    DesignDataField dataField;
                    ArrayList<Formula> formulas = new ArrayList<Formula>();
                    formulas.add(formula);
                    List iParsedExpressions = DataEngineFormulaParser.parseFormula((ExecutorContext)context, formulas, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.BALANCE, (IMonitor)formulaMonitor);
                    if (iParsedExpressions.size() != 1 || (dataField = this.dataSchemeService.getDataField(dataFieldKey = ((IParsedExpression)iParsedExpressions.get(0)).getBalanceField().getUID())).getDataFieldType() == DataFieldType.BIGDECIMAL || dataField.getDataFieldType() == DataFieldType.INTEGER) continue;
                    FormulaCheckObj fco = formulaMonitor.getCheckResultMap().get(formula.getCode());
                    if (fco == null) {
                        FormulaCheckObj formulaCheckObj = this.changeFormat(formula);
                        formulaCheckObj.setErrorMsg("\u8c03\u6574\u6307\u6807\u5fc5\u987b\u4e3a\u6570\u503c\u578b\uff01");
                        result.add(formulaCheckObj);
                        continue;
                    }
                    if (StringUtils.isEmpty((String)fco.getErrorMsg())) {
                        fco.setErrorMsg("\u8c03\u6574\u6307\u6807\u5fc5\u987b\u4e3a\u6570\u503c\u578b\uff01");
                        continue;
                    }
                    fco.setErrorMsg(fco.getErrorMsg() + "\u8c03\u6574\u6307\u6807\u5fc5\u987b\u4e3a\u6570\u503c\u578b\uff01");
                    continue;
                }
                FormulaCheckObj formulaCheckObj = this.changeFormat(formula);
                formulaCheckObj.setErrorMsg("\u516c\u5f0f\u4e3a\u5e73\u8861\u516c\u5f0f\u65f6\uff0c\u8c03\u6574\u6307\u6807\u4e0d\u80fd\u4e3a\u7a7a\uff01");
                result.add(formulaCheckObj);
            }
            return result;
        }
        catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_017, (Throwable)e);
        }
    }

    private FormulaCheckObj changeFormat(Formula formula) {
        FormulaCheckObj formulaCheckObj = new FormulaCheckObj();
        formulaCheckObj.setCode(formula.getCode());
        formulaCheckObj.setFormula(formula.getFormula());
        formulaCheckObj.setReportName(formula.getReportName());
        formulaCheckObj.setBalanceZBExp(formula.getBalanceZBExp());
        formulaCheckObj.setUseBalance(true);
        return formulaCheckObj;
    }

    private List<IParsedExpression> parseFormulas(String formSchemeKey, List<Formula> formulaList, DataEngineConsts.FormulaType formulaType, IMonitor formulaMonitor) throws JQException {
        List parsedExpressions = new ArrayList();
        try {
            ExecutorContext context = new ExecutorContext(this.npRuntimeController);
            DataEngineConsts.FormulaShowType showType = this.getFormulaShowTypeByFormulaScheme(formSchemeKey);
            context.setJQReportModel(showType == DataEngineConsts.FormulaShowType.JQ);
            context.setDesignTimeData(true, this.npController);
            List formulaVariables = this.formulaDesignTimeController.queryAllFormulaVariable(formSchemeKey);
            DesignReportFmlExecEnvironment environment = new DesignReportFmlExecEnvironment(this.controller, this.npController, formSchemeKey, formulaVariables);
            context.setEnv((IFmlExecEnvironment)environment);
            parsedExpressions = DataEngineFormulaParser.parseFormula((ExecutorContext)context, formulaList, (DataEngineConsts.FormulaType)formulaType, (IMonitor)formulaMonitor);
            if (!this.definitionOptionUtils.isSpecifyDimensionAssignment()) {
                DesignFormSchemeDefine formScheme = this.controller.queryFormSchemeDefine(formSchemeKey);
                DesignTaskDefine taskDefine = this.controller.queryTaskDefine(formScheme.getTaskKey());
                List reportDimension = this.dataSchemeService.getReportDimension(taskDefine.getDataScheme());
                List dwDimension = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.UNIT);
                List periodDimension = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.PERIOD);
                reportDimension.addAll(dwDimension);
                reportDimension.addAll(periodDimension);
                Set dimensionNames = reportDimension.stream().map(d -> this.iEntityMetaService.getDimensionName(d.getDimKey())).collect(Collectors.toSet());
                parsedExpressions = this.parsedExpressionFilter.removeCrossDimensionFML(dimensionNames, parsedExpressions, formulaMonitor);
            }
        }
        catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_017, (Throwable)e);
        }
        return parsedExpressions;
    }

    private DataEngineConsts.FormulaShowType getFormulaShowTypeByFormulaScheme(String schemeKey) {
        DesignFormSchemeDefine formSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(schemeKey);
        DesignTaskDefine taskDefine = this.nrDesignTimeController.queryTaskDefine(formSchemeDefine.getTaskKey());
        DataEngineConsts.FormulaShowType type = DataEngineConsts.FormulaShowType.JQ;
        if (taskDefine.getFormulaSyntaxStyle() == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL) {
            type = DataEngineConsts.FormulaShowType.EXCEL;
        }
        return type;
    }

    private void meangingFormulas(String formSchemeKey, List<Formula> formulaList, FormulaMonitor formulaMonitor) throws JQException {
        try {
            ExecutorContext context = new ExecutorContext(this.npRuntimeController);
            context.setDesignTimeData(true, this.npController);
            List formulaVariables = this.formulaDesignTimeController.queryAllFormulaVariable(formSchemeKey);
            DesignReportFmlExecEnvironment environment = new DesignReportFmlExecEnvironment(this.controller, this.npController, formSchemeKey, formulaVariables);
            context.setEnv((IFmlExecEnvironment)environment);
            List meannings = DataEngineFormulaParser.getFormulaMeannings((ExecutorContext)context, formulaList, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.EXPRESSION, (IMonitor)formulaMonitor);
            if (formulaList.size() != meannings.size()) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_001, "\u516c\u5f0f\u89e3\u6790\u6709\u8bef\uff01");
            }
            for (int i = 0; i < formulaList.size(); ++i) {
                formulaList.get(i).setMeanning((String)meannings.get(i));
            }
        }
        catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_017, (Throwable)e);
        }
    }

    @ApiOperation(value="\u516c\u5f0f\u8bf4\u660e\u81ea\u52a8\u751f\u6210")
    @RequestMapping(value={"/formula_des_create_rsa"}, method={RequestMethod.POST})
    public List<FormulaCheckObj> formulaAutoCreateRsa(@RequestBody @SFDecrypt FormulaDesCreateParam formulaDesCreateParam) throws InterpretException, JQException {
        return this.formulaAutoCreate(formulaDesCreateParam.getFormuObj(), formulaDesCreateParam.getSchemeKey());
    }

    @ApiOperation(value="\u516c\u5f0f\u8bf4\u660e\u81ea\u52a8\u751f\u6210")
    @RequestMapping(value={"/formula_des_create"}, method={RequestMethod.POST})
    public List<FormulaCheckObj> formulaAutoCreate(@RequestBody List<FormulaCheckObj> formuObj, @RequestParam String schemeKey) throws InterpretException, JQException {
        FormulaMonitor formulaMonitor = new FormulaMonitor();
        if (formuObj != null) {
            ArrayList<Formula> formulaList = new ArrayList<Formula>(formuObj);
            this.meangingFormulas(schemeKey, formulaList, formulaMonitor);
            ArrayList<FormulaCheckObj> resultList = new ArrayList<FormulaCheckObj>();
            Map<String, FormulaCheckObj> checkFormulaErrResultMap = formulaMonitor.getCheckResultMap();
            Collection<FormulaCheckObj> values = checkFormulaErrResultMap.values();
            ArrayList<FormulaCheckObj> checkFormulaErrResultList = new ArrayList<FormulaCheckObj>(values);
            for (int i = 0; i < formulaList.size(); ++i) {
                resultList.add((FormulaCheckObj)((Object)formulaList.get(i)));
            }
            resultList.addAll(checkFormulaErrResultList);
            return resultList;
        }
        return Collections.emptyList();
    }

    @RequestMapping(value={"/download_RepeatCode_Excel"}, method={RequestMethod.POST})
    @ApiOperation(value="\u91cd\u590d\u7f16\u53f7\u4fe1\u606f\u516c\u5f0fexcel\u5bfc\u51fa")
    public void exportRepeatCodeExcel(@RequestBody String formulasMap, HttpServletResponse res) throws IOException, JQException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(formulasMap);
        String scheme = jsonNode.get("schemeKey").toString();
        String excelAndCodeRepeat = jsonNode.get("ExcelAndCodeRepeat").toString();
        String schemeKey = (String)mapper.readValue(scheme, String.class);
        Map excelData = (Map)mapper.readValue(excelAndCodeRepeat, (TypeReference)new TypeReference<Map<String, List>>(){});
        DesignFormulaSchemeDefine formulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(schemeKey);
        if (formulaSchemeDefine == null) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_019);
        }
        boolean isEfdc = formulaSchemeDefine.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL);
        this.formulaService.exportRepeatCodeExcel(excelData, res, isEfdc);
    }

    @RequestMapping(value={"/export_All_Formulas"}, method={RequestMethod.POST})
    @ApiOperation(value="\u5bfc\u51fa\u5f53\u524d\u65b9\u6848\u4e0b\u6240\u6709\u516c\u5f0f\u8868")
    @RequiresPermissions(value={"nr:task_formula:manage"})
    public void exportAllFormulas(@RequestBody String formulasInfo, HttpServletResponse res) throws IOException, JQException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(formulasInfo);
        String scheme = jsonNode.get("schemeKey").toString();
        String formsKey = jsonNode.get("formsKey").toString();
        String schemeKey = (String)mapper.readValue(scheme, String.class);
        ArrayList formKeyList = (ArrayList)mapper.readValue(formsKey, (TypeReference)new TypeReference<ArrayList<String>>(){});
        DesignFormulaSchemeDefine formulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(schemeKey);
        if (formulaSchemeDefine == null) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_019);
        }
        boolean isEfdc = formulaSchemeDefine.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL);
        this.formulaService.exportAllFormulas(schemeKey, formKeyList, res, isEfdc);
    }

    @ApiOperation(value="\u516c\u5f0fexcel\u5bfc\u5165")
    @PostMapping(value={"formulaImport"})
    public Map<String, List> importFormulaExcel(String[] formId, String formulaScheme, String importMethod, @RequestParam(value="file") MultipartFile file) throws JQException, ParseException {
        LinkedHashMap<String, List> formulasSheetMap = new LinkedHashMap<String, List>();
        boolean isFullImport = importMethod.equalsIgnoreCase("all_import");
        this.formulaService.paraExcelFormulas(file, formId, formulasSheetMap, formulaScheme, isFullImport);
        if (((List)formulasSheetMap.get("repeatCode")).size() == 0) {
            if (isFullImport) {
                this.formulaService.allImportFormula(formId, formulaScheme, formulasSheetMap);
            } else {
                this.formulaService.addImportFormula(formId, formulaScheme, formulasSheetMap);
            }
        } else {
            return formulasSheetMap;
        }
        return formulasSheetMap;
    }

    @ApiOperation(value="\u516c\u5f0f\u7f16\u53f7\u5b9e\u65f6\u6821\u9a8c")
    @RequestMapping(value={"formula_code_check"}, method={RequestMethod.POST})
    public List<FormulaCheckObj> formulaCodeCheck(@RequestBody FormulaObj formula) throws JQException {
        FormulaCheckObj formuCodeCheck = new FormulaCheckObj();
        ArrayList<FormulaCheckObj> formuCodeChecks = new ArrayList<FormulaCheckObj>();
        if (formula.getCode() != null && formula.getFormKey() != null && formula.getSchemeKey() != null) {
            List findFormulaDefineInFormulaSchemes = null;
            findFormulaDefineInFormulaSchemes = formula.getFormKey().equals(FORMULA_BIAOJIAN) ? this.nrDesignTimeController.findRepeatFormulaDefineFormOutSchemes(formula.getCode(), null, formula.getSchemeKey()) : this.nrDesignTimeController.findRepeatFormulaDefineFormOutSchemes(formula.getCode(), formula.getFormKey(), formula.getSchemeKey());
            if (findFormulaDefineInFormulaSchemes.size() > 0) {
                formuCodeCheck.setCode(((DesignFormulaDefine)findFormulaDefineInFormulaSchemes.get(0)).getCode());
                formuCodeCheck.setErrorMsg(FORMULA_CODE_REPEAT);
                formuCodeChecks.add(formuCodeCheck);
                return formuCodeChecks;
            }
        }
        return Collections.emptyList();
    }

    @ApiOperation(value="\u516c\u5f0f\u6811\u8282\u70b9\u5207\u6362")
    @RequestMapping(value={"formula_tree_node_select"}, method={RequestMethod.POST})
    public List<DesignFormulaDefine> formulaTreeNodeSelect(@RequestBody FormulaObj formula) throws JQException {
        if (StringUtils.isEmpty((String)formula.getFormKey())) {
            return Collections.emptyList();
        }
        List allFormulasInForm = formula.getFormKey().equals(FORMULA_BIAOJIAN) ? this.nrDesignTimeController.getAllFormulasInForm(formula.getSchemeKey(), null) : this.nrDesignTimeController.getAllFormulasInForm(formula.getSchemeKey(), formula.getFormKey());
        if (allFormulasInForm.size() > 0) {
            return allFormulasInForm;
        }
        return Collections.emptyList();
    }

    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u5ba1\u6838\u7c7b\u578b")
    @RequestMapping(value={"query_formula_audit_type"}, method={RequestMethod.GET})
    public List<AuditType> queryFormulaAuditType() throws JQException {
        try {
            List queryAllAuditType = this.auditTypeDefineService.queryAllAuditType();
            if (queryAllAuditType.size() > 0) {
                return queryAllAuditType;
            }
        }
        catch (JQException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_025, (Throwable)e);
        }
        return Collections.emptyList();
    }

    @ApiOperation(value="\u4fdd\u5b58\u5ba1\u6838\u516c\u5f0f\u7c7b\u578b")
    @RequestMapping(value={"save_formula_audit_type"}, method={RequestMethod.POST})
    @Transactional(rollbackFor={Exception.class})
    public void savaFormulaAuditType(@RequestBody List<AuditTypeObj> auditTypeObj) throws JQException {
        if (auditTypeObj == null) {
            return;
        }
        for (AuditTypeObj formulaAuditTypeObj : auditTypeObj) {
            if (formulaAuditTypeObj == null) continue;
            AuditType auditType = this.classChange(formulaAuditTypeObj);
            try {
                if (formulaAuditTypeObj.getState() == 1) {
                    this.auditTypeDefineService.deleteAuditType(formulaAuditTypeObj.getCode(), formulaAuditTypeObj.getAssignCode());
                    continue;
                }
                if (formulaAuditTypeObj.getState() == 2) {
                    this.auditTypeDefineService.updateAuditType(auditType);
                    continue;
                }
                if (formulaAuditTypeObj.getState() != 3) continue;
                this.auditTypeDefineService.insertAuditType(auditType);
            }
            catch (JQException e) {
                throw e;
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_025);
            }
        }
    }

    private AuditType classChange(AuditTypeObj auditTypeObj) {
        AuditTypeImpl auditType = new AuditTypeImpl();
        auditType.setCode(auditTypeObj.getCode());
        auditType.setTitle(auditTypeObj.getTitle());
        auditType.setOrder(auditTypeObj.getOrder());
        auditType.setIcon(auditTypeObj.getIcon());
        auditType.setColor(auditTypeObj.getColor());
        auditType.setBackGroundColor(auditTypeObj.getBackGroundColor());
        auditType.setFontColor(auditTypeObj.getFontColor());
        auditType.setGridColor(auditTypeObj.getGridColor());
        return auditType;
    }

    @ApiOperation(value="\u516c\u5f0f\u53d1\u5e03")
    @RequestMapping(value={"runtime_formula_publish/{formKey}/{formulaScheme}"}, method={RequestMethod.POST})
    @Transactional(rollbackFor={Exception.class})
    public void formulaPublish(@PathVariable String formKey, @PathVariable String formulaScheme) throws JQException {
        String logTitle = "\u53d1\u5e03\u516c\u5f0f";
        String formulaSchemeTitle = "\u672a\u77e5";
        try {
            DesignFormulaSchemeDefine formulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(formulaScheme);
            formulaSchemeTitle = formulaSchemeDefine.getTitle();
            boolean taskCanEdit = this.taskPlanPublishExternalService.formulaSchemeCanEdit(formulaScheme);
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            String formSchemeKey = formulaSchemeDefine.getFormSchemeKey();
            FormSchemeDefine formScheme = this.runTimeController.getFormScheme(formSchemeKey);
            DesignFormulaSchemeDefine designFormulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(formulaScheme);
            if (null == formScheme) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_033);
            }
            if (null == designFormulaSchemeDefine) {
                this.iviewDeployController.deployFormulaScheme(formulaScheme, false);
            }
            if (FORMULA_BIAOJIAN.equals(formKey)) {
                this.iviewDeployController.deployFormulaScheme(formulaScheme, null);
            } else {
                FormDefine formDefine = this.runTimeController.queryFormById(formKey);
                if (null == formDefine) {
                    throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_032);
                }
                this.iviewDeployController.deployFormulaScheme(formulaScheme, formKey);
            }
            ArrayList<String> formulaSchemeKey = new ArrayList<String>();
            formulaSchemeKey.add(formulaSchemeDefine.getKey());
            this.applicationContext.publishEvent((ApplicationEvent)new DeployRefreshFormulaEvent(formulaSchemeKey));
            NrDesignLogHelper.log(logTitle, formulaSchemeTitle, NrDesignLogHelper.LOGLEVEL_INFO);
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, formulaSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, formulaSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_031, (Throwable)e);
        }
    }

    @ApiOperation(value="\u516c\u5f0f\u65b9\u6848\u53d1\u5e03")
    @RequestMapping(value={"publish_formula_scheme/{formulaSchemeKey}"}, method={RequestMethod.POST})
    @Transactional(rollbackFor={Exception.class})
    public void formulaSchemePublish(@PathVariable String formulaSchemeKey) throws Exception {
        try {
            this.iviewDeployController.deployFormulaScheme(formulaSchemeKey, true);
        }
        catch (JQException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_028, e.getMessage());
        }
    }

    @ApiOperation(value="\u8868\u5355code")
    @RequestMapping(value={"query_form_code/{formKey}"}, method={RequestMethod.POST})
    public String queryFormCode(@PathVariable String formKey) {
        String formCode = null;
        DesignFormDefine designFormDefine = null;
        designFormDefine = FORMULA_BIAOJIAN.equals(formKey) ? this.nrDesignTimeController.queryFormById(null) : this.nrDesignTimeController.queryFormById(formKey);
        if (null != designFormDefine) {
            formCode = designFormDefine.getFormCode();
        }
        return formCode == null ? "" : formCode;
    }

    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u5355\u5143\u683c\u6307\u6807\u94fe\u63a5")
    @RequestMapping(value={"query_data_link_define/{formKey}"}, method={RequestMethod.POST})
    public Map<String, String> queryDataLinkDefine(@PathVariable String formKey) {
        HashMap<String, String> dataLinkDefineMap = new HashMap<String, String>();
        List allLinksInForm = this.designTimeViewController.getAllLinksInForm(formKey);
        for (int i = 0; i < allLinksInForm.size(); ++i) {
            String key = "[" + ((DesignDataLinkDefine)allLinksInForm.get(i)).getRowNum() + "," + ((DesignDataLinkDefine)allLinksInForm.get(i)).getColNum() + "]";
            dataLinkDefineMap.put(key, key);
        }
        return dataLinkDefineMap;
    }

    @ApiOperation(value="\u5168\u90e8\u516c\u5f0f\uff1a\u516c\u5f0f\u67e5\u8be2")
    @PostMapping(value={"fuzzyQuery/formula"})
    public FormulaSearchParam fuzzyQueryFormula(@RequestBody FormulaSearchParam formulaSearchParam) throws JQException {
        try {
            return this.searchFormulaService.fuzzyQueryFormula(formulaSearchParam);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_116, (Throwable)e);
        }
    }

    @ApiOperation(value="\u5168\u90e8\u516c\u5f0f\uff1a\u516c\u5f0f\u67e5\u8be2")
    @PostMapping(value={"fuzzyQuery/formula_rsa"})
    public FormulaSearchParam fuzzyQueryFormulaRsa(@RequestBody @SFDecrypt FormulaSearchParam formulaSearchParam) throws JQException {
        return this.fuzzyQueryFormula(formulaSearchParam);
    }

    @ApiOperation(value="\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u6240\u6709\u62a5\u8868")
    @RequestMapping(value={"query_forms_by_formscheme/{formScheme}"}, method={RequestMethod.GET})
    public List<DesignFormDefine> queryFormsByTask(@PathVariable String formScheme) {
        List allFormDefinesByTask = this.controller.getAllFormDefinesInFormSchemeWithoutBinaryData(formScheme);
        return allFormDefinesByTask;
    }

    @ApiOperation(value="\u67e5\u8be2\u672c\u670d\u52a1\u7684\u673a\u5668\u7801")
    @RequestMapping(value={"query_serve_code"}, method={RequestMethod.GET})
    public String queryServeCode() throws JQException {
        return this.serveCodeService.getServeCode();
    }
}

