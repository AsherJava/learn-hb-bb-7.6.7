/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.definitions.DataDefinitionsCache
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.impl.common.DefinitionHelper
 *  com.jiuqi.np.definition.impl.common.DefinitionTransUtils
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionDesignTimeController2
 *  com.jiuqi.np.definition.impl.internal.FieldDefineImpl
 *  com.jiuqi.nr.authorize.service.LicenceService
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.config.MulCheckConfiguration
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService
 *  com.jiuqi.nr.definition.util.ServeCodeService
 *  com.jiuqi.nr.definition.validation.CompareType
 *  com.jiuqi.nr.definition.validation.DataValidationExpression
 *  com.jiuqi.nr.definition.validation.DataValidationExpressionFactory
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.xlib.utils.StringUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.designer.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.definitions.DataDefinitionsCache;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.impl.common.DefinitionHelper;
import com.jiuqi.np.definition.impl.common.DefinitionTransUtils;
import com.jiuqi.np.definition.impl.controller.DataDefinitionDesignTimeController2;
import com.jiuqi.np.definition.impl.internal.FieldDefineImpl;
import com.jiuqi.nr.authorize.service.LicenceService;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.config.MulCheckConfiguration;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.definition.validation.CompareType;
import com.jiuqi.nr.definition.validation.DataValidationExpression;
import com.jiuqi.nr.definition.validation.DataValidationExpressionFactory;
import com.jiuqi.nr.designer.common.Grid2DataSeralizeToGeGe;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.helper.CommonHelper;
import com.jiuqi.nr.designer.helper.SaveSchemePeriodHelper;
import com.jiuqi.nr.designer.helper.SaveTaskObjHelper;
import com.jiuqi.nr.designer.paramlanguage.service.LanguageTypeService;
import com.jiuqi.nr.designer.service.IDesignRestService;
import com.jiuqi.nr.designer.service.IPrintSchemeService;
import com.jiuqi.nr.designer.service.StepSaveService;
import com.jiuqi.nr.designer.util.InitParamObjPropertyUtil;
import com.jiuqi.nr.designer.web.facade.FormData;
import com.jiuqi.nr.designer.web.facade.FormObj;
import com.jiuqi.nr.designer.web.facade.FormSchemeObj;
import com.jiuqi.nr.designer.web.facade.RegionDuplicateBizKeys;
import com.jiuqi.nr.designer.web.facade.TaskObj;
import com.jiuqi.nr.designer.web.facade.ValidationObj;
import com.jiuqi.nr.designer.web.rest.vo.DesignRestVO;
import com.jiuqi.nr.designer.web.rest.vo.ParamToDesigner;
import com.jiuqi.nr.designer.web.rest.vo.ReturnObject;
import com.jiuqi.nr.designer.web.treebean.FieldObject;
import com.jiuqi.nr.designer.web.treebean.FormGroupObject;
import com.jiuqi.nr.designer.web.treebean.TableObject;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.xlib.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class DesignController {
    private static final Logger log = LoggerFactory.getLogger(DesignController.class);
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private IDesignRestService designRestService;
    @Autowired
    private InitParamObjPropertyUtil initParamObjPropertyUtil;
    @Autowired
    private IDataDefinitionDesignTimeController npDesignTimeController;
    @Autowired
    private ServeCodeService serveCodeService;
    @Autowired
    private CommonHelper commonHelper;
    @Autowired
    private SaveTaskObjHelper saveTaskObjHelper;
    @Autowired
    private IPrintSchemeService iPrintSchemeService;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthority;
    @Autowired
    private StepSaveService stepSaveService;
    @Autowired
    private LicenceService licenceService;
    @Autowired
    private DefaultLanguageService defaultLanguageService;
    @Autowired
    private LanguageTypeService languageService;
    @Autowired
    private SaveSchemePeriodHelper saveSchemePeriodHelper;
    @Autowired
    private DataDefinitionDesignTimeController2 npDesignTimeController2;
    @Autowired
    private IDataDefinitionRuntimeController npRunTimeController;
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    protected DataModelService dataModelService;
    @Autowired
    private INvwaSystemOptionService systemOptionService;
    @Autowired
    private MulCheckConfiguration mulCheckConfiguration;
    private static final int VALIDATION_TYPE_LONG = 0;
    private static final int VALIDATION_TYPE_RANGE = 1;

    @ApiOperation(value="\u83b7\u53d6\u9700\u8981\u5c55\u793a\u7684\u62a5\u8868\u4fe1\u606f")
    @PostMapping(value={"/form/getformData"})
    @ResponseBody
    @RequiresPermissions(value={"nr:task_form:design"})
    public String formList(@RequestBody ParamToDesigner paramToDesigner) throws Exception {
        String taskKey = paramToDesigner.getActivedTaskId();
        String schemeKey = paramToDesigner.getActivedSchemeId();
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)taskKey) && com.jiuqi.bi.util.StringUtils.isEmpty((String)schemeKey)) {
            String logTitle = "\u8fdb\u5165\u4efb\u52a1";
            String taskTitle = "\u672a\u77e5";
            DesignTaskDefine designTaskDefine = this.nrDesignTimeController.queryTaskDefine(taskKey);
            if (designTaskDefine != null) {
                taskTitle = designTaskDefine.getTitle();
                NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_INFO);
            } else {
                NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            }
        }
        return this.designRestService.getFormData(paramToDesigner);
    }

    @ApiOperation(value="\u5207\u6362\u9875\u7b7e\u9700\u8981\u5c55\u793a\u7684\u8868\u6837(OwnGroupId\u6ca1\u8d4b\u503c\uff0c\u53ef\u80fd\u4f1a\u6709\u5f71\u54cd)")
    @PostMapping(value={"/FormService/RequestForm"})
    @RequiresPermissions(value={"nr:task_form:design"})
    public ReturnObject getRequestForm(@RequestBody String jsonData) throws Exception {
        DesignRestVO vo = new DesignRestVO(jsonData);
        DesignFormDefine designFormDefine = this.nrDesignTimeController.queryFormById(vo.getFormKey());
        int defaultLanguage = this.defaultLanguageService.getDefaultLanguage();
        if (vo.getLanguageType() != this.defaultLanguageService.getDefaultLanguage()) {
            byte[] data = this.nrDesignTimeController.getReportDataFromForm(vo.getFormKey(), vo.getLanguageType());
            if (data == null && vo.getLanguageType() == 2) {
                String defaultLang = this.languageService.queryDefaultLanguage();
                data = this.nrDesignTimeController.getReportDataFromForm(vo.getFormKey(), Integer.parseInt(defaultLang));
            }
            designFormDefine.setBinaryData(data);
        }
        FormObj formObject = this.initParamObjPropertyUtil.setFormObjProperty(designFormDefine, vo.getTaskKey(), vo.getFormGroupKey(), true, true);
        try {
            String s = this.systemOptionService.get("other-group", "GRID_LINE_SPACE");
            formObject.setRowSpace(Integer.parseInt(s));
        }
        catch (Exception e) {
            log.info("\u67e5\u8be2\u7cfb\u7edf\u9009\u9879-\u62a5\u8868\u884c\u95f4\u8ddd\u8bbe\u7f6e\u5931\u8d25\uff0c\u4f7f\u7528\u9ed8\u8ba4\u503c0");
        }
        ReturnObject ro = new ReturnObject();
        ro.setMessage(this.serializeFormObject(formObject));
        return ro;
    }

    @ApiOperation(value="\u6839\u636ecode\u67e5\u627e\u5b58\u50a8\u8868")
    @GetMapping(value={"getTableByCode/{tableCode}"})
    public ReturnObject queryTableByCode(@PathVariable(value="tableCode") String code) throws Exception {
        ReturnObject ro = new ReturnObject();
        DesignTableDefine tableDefine = this.npDesignTimeController.queryTableDefinesByCode(code);
        if (null != tableDefine) {
            TableObject tableObject = new TableObject();
            tableObject.setID(tableDefine.getKey());
            tableObject.setBizKeyFieldsStr(tableDefine.getBizKeyFieldsStr());
            tableObject.setCode(tableDefine.getCode());
            tableObject.setKind(tableDefine.getKind().getValue());
            DataTable dataTable = DefinitionHelper.toDataTable((TableDefine)tableDefine);
            tableObject.setTableType(dataTable.getDataTableType().getValue() + "");
            ro.setObj(tableObject);
            ro.setSuccess(true);
        } else {
            ro.setMessage("\u5b58\u50a8\u8868\u4e0d\u5b58\u5728");
            ro.setSuccess(false);
        }
        return ro;
    }

    @ApiOperation(value="\u65b0\u5efa\u4efb\u52a1")
    @PostMapping(value={"/form/createformData"})
    public TaskObj createForm(@RequestBody ParamToDesigner paramToDesigner) throws Exception {
        String logTitle = "\u65b0\u589e\u4efb\u52a1";
        String taskTitle = "\u672a\u77e5";
        try {
            if (com.jiuqi.bi.util.StringUtils.isEmpty((String)paramToDesigner.getActivedTaskId())) {
                paramToDesigner.setActivedTaskId(UUIDUtils.getKey());
            }
            String taskID = paramToDesigner.getActivedTaskId();
            TaskObj taskObject = this.commonHelper.initNewTaskObject(paramToDesigner);
            taskTitle = taskObject.getTitle();
            this.saveTaskObjHelper.conversionTaskObj(taskObject);
            FormSchemeObj schemeObject = this.commonHelper.initNewSchemeObject(taskObject);
            String formSchemeKey = schemeObject.getID();
            this.stepSaveService.stepSaveScheme(schemeObject);
            this.saveSchemePeriodHelper.createSchemePeriodLink(formSchemeKey, taskObject.getFromPeriod(), taskObject.getToPeriod());
            this.iPrintSchemeService.createDefaultPrintScheme(taskID, formSchemeKey);
            FormGroupObject formGroup = this.commonHelper.initNewGroupObject(schemeObject);
            this.stepSaveService.saveFormGroup(formGroup);
            String identityId = NpContextHolder.getContext().getIdentityId();
            if (identityId == null) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_007);
            }
            this.definitionAuthority.grantAllPrivileges(taskID);
            String taskGroupKey = paramToDesigner.getActivedGroupId();
            if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)taskGroupKey)) {
                this.nrDesignTimeController.addTaskToGroup(taskID, taskGroupKey);
            }
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_INFO);
            return taskObject;
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            log.error(e.getMessage(), e);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            log.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    private String serializeFormObject(FormObj formObject) throws JsonProcessingException {
        FormData formData = new FormData();
        formData.setFormObject(formObject);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSeralizeToGeGe());
        this.mapper.registerModule((Module)module);
        return this.mapper.writeValueAsString((Object)formData);
    }

    @ApiOperation(value="\u5237\u65b0\u5934\u4fe1\u606f(\u4efb\u52a1\u3001\u62a5\u8868\u65b9\u6848)")
    @PostMapping(value={"/form/refreshTaskHeader"})
    public String refreshHeader(@RequestBody String jsonData) throws Exception {
        DesignRestVO vo = new DesignRestVO(jsonData);
        String taskKey = vo.getTaskKey();
        DesignTaskDefine designTaskDefine = this.nrDesignTimeController.queryTaskDefine(taskKey);
        TaskObj taskObject = new TaskObj();
        taskObject.setCode(designTaskDefine.getTaskCode());
        HashMap<String, FormSchemeObj> formSchemeDefineMap = new HashMap<String, FormSchemeObj>();
        List designFormSchemeDefineList = this.nrDesignTimeController.queryFormSchemeByTask(taskKey);
        for (DesignFormSchemeDefine designFormSchemeDefine : designFormSchemeDefineList) {
            formSchemeDefineMap.put(designFormSchemeDefine.getKey(), this.commonHelper.convertSchemeObj(designFormSchemeDefine));
        }
        HashMap<String, Object> returnJson = new HashMap<String, Object>();
        returnJson.put("success", true);
        returnJson.put("formSchemeObjs", formSchemeDefineMap);
        returnJson.put("taskObject", taskObject);
        return this.mapper.writeValueAsString(returnJson);
    }

    @ApiOperation(value="\u83b7\u53d6\u5b58\u50a8\u8868\u4e2d\u5173\u8054\u4e86\u5b58\u50a8\u8868\u7684\u6307\u6807")
    @GetMapping(value={"/view/queryEntityFieldsContentsReferField/{entityKey}"})
    public List<FieldObject> queryEntityFieldsContentsReferField(@PathVariable(value="entityKey") String tableKey) throws JQException {
        try {
            List fieldsDefines = this.npDesignTimeController.getAllFieldsInTable(tableKey);
            ArrayList<FieldObject> fieldObjects = new ArrayList<FieldObject>();
            Optional.ofNullable(fieldsDefines).orElse(Collections.emptyList()).forEach(item -> {
                if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)item.getEntityKey()) && !item.getTitle().equals("\u7236\u8282\u70b9")) {
                    fieldObjects.add(new FieldObject((FieldDefine)item));
                }
            });
            return fieldObjects;
        }
        catch (JQException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_023);
        }
    }

    @ApiOperation(value="\u5efa\u6a21\u6743\u9650\u9a8c\u8bc1")
    @RequestMapping(value={"/auth/designer"}, method={RequestMethod.POST})
    public Map<String, Object> authVerify() throws JQException {
        HashMap<String, Object> authInfo = new HashMap<String, Object>();
        try {
            String authorizeConfigToExcel = this.licenceService.findAuthorizeConfig("com.jiuqi.nr.formDesigner", "com.jiuqi.nr.formDesigner.excel");
            this.designRestService.getValidationResult(authorizeConfigToExcel, "Excel", authInfo);
            String authorizeConfigToJIO = this.licenceService.findAuthorizeConfig("com.jiuqi.nr.formDesigner", "com.jiuqi.nr.formDesigner.jio");
            this.designRestService.getValidationResult(authorizeConfigToJIO, "JIO", authInfo);
            String authorizeConfigToDesigner = this.licenceService.findAuthorizeConfig("com.jiuqi.nr.formDesigner", "com.jiuqi.nr.formDesigner.designer");
            this.designRestService.getValidationResult(authorizeConfigToDesigner, "DESIGNER", authInfo);
            try {
                String reverseModeling = this.licenceService.findAuthorizeConfig("com.jiuqi.nr.formDesigner", "com.jiuqi.nr.formDesigner.reverseModeling");
                authInfo.put("REFLEX", reverseModeling);
                String report = this.licenceService.findAuthorizeConfig("com.jiuqi.nr.formDesigner", "com.jiuqi.nr.formDesigner.report");
                this.designRestService.getValidationResult(report, "REPORT", authInfo);
            }
            catch (Exception e) {
                authInfo.put("REFLEX", "1");
                authInfo.put("REPORT", true);
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_129, (Throwable)e);
        }
        return authInfo;
    }

    @ApiOperation(value="\u901a\u8fc7\u4e1a\u4e3b\u4e3b\u952e\u83b7\u53d6\u7ef4\u5ea6\u540d")
    @GetMapping(value={"dimessionnames-by-fieldkeys/{fieldkeys}"})
    public List<String> getDimessionNamesByFieldKeys(@PathVariable(value="fieldkeys") String[] fieldKeyArray) {
        ArrayList<String> dimessionNameList = new ArrayList<String>();
        try {
            if (fieldKeyArray != null && fieldKeyArray.length > 0) {
                DataDefinitionsCache dataDefinitionsCache = new DataDefinitionsCache(null, null, null);
                for (String fieldKey : fieldKeyArray) {
                    dataDefinitionsCache.setDesignTimeController(this.npDesignTimeController);
                    String dimensionName = dataDefinitionsCache.getDimensionName((FieldDefine)this.npDesignTimeController.queryFieldDefine(fieldKey));
                    dimessionNameList.add(fieldKey + ":" + dimensionName);
                }
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dimessionNameList;
    }

    @ApiOperation(value="\u62d6\u62fd\u65f6\u67e5\u8be2\u5b57\u6bb5")
    @GetMapping(value={"getDragField/{feildId}"})
    public ResponseEntity<String> getDragFieldTreeNode(@PathVariable(value="feildId") String id) {
        HttpStatus status = HttpStatus.OK;
        String result = "";
        try {
            FieldObject fieldObj = this.getDragFieldObj(id);
            result = this.mapper.writeValueAsString((Object)fieldObj);
        }
        catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result = e.getMessage();
        }
        return new ResponseEntity((Object)result, null, status.value());
    }

    @ApiOperation(value="\u62d6\u62fd\u65f6\u67e5\u8be2\u5b57\u6bb5")
    @GetMapping(value={"getEntityDragField/{feildId}"})
    public ResponseEntity<String> getDragEntityFieldTreeNode(@PathVariable(value="feildId") String id) {
        HttpStatus status = HttpStatus.OK;
        String result = "";
        try {
            FieldObject fieldObj = this.getDragEntityFieldObj(id);
            result = this.mapper.writeValueAsString((Object)fieldObj);
        }
        catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result = e.getMessage();
        }
        return new ResponseEntity((Object)result, null, status.value());
    }

    @ApiOperation(value="\u62d6\u62fd\u65f6\u67e5\u8be2\u5b58\u50a8\u8868")
    @GetMapping(value={"getTable/{tableId}"})
    public ResponseEntity<String> queryTable(@PathVariable(value="tableId") String id) throws Exception {
        HttpStatus status = HttpStatus.OK;
        DesignTableDefine tableDefine = this.npDesignTimeController.queryTableDefine(id);
        if (null == tableDefine) {
            return this.queryEntityTableFunc(id);
        }
        boolean isFloatTable = false;
        DataTable dataTable = DefinitionHelper.toDataTable((TableDefine)tableDefine);
        if (dataTable.getDataTableType() == DataTableType.DETAIL || dataTable.getDataTableType() == DataTableType.ACCOUNT) {
            isFloatTable = true;
        }
        TableObject tableObject = new TableObject();
        tableObject.setID(tableDefine.getKey().toString());
        String bizKeyFieldsStr = tableDefine.getBizKeyFieldsStr();
        tableObject.setBizKeyFieldsStr(bizKeyFieldsStr);
        tableObject.setCode(tableDefine.getCode());
        tableObject.setKind(tableDefine.getKind().getValue());
        tableObject.setTitle(tableDefine.getTitle());
        tableObject.setOwnerLevelAndId(tableDefine.getOwnerLevelAndId());
        tableObject.setSameServeCode(this.serveCodeService.isSameServeCode(tableDefine.getOwnerLevelAndId()));
        tableObject.setTableType(dataTable.getDataTableType().getValue() + "");
        List fields = this.npDesignTimeController.getAllFieldsInTable(tableDefine.getKey());
        HashMap<String, FieldObject> fieldMap = new HashMap<String, FieldObject>();
        if (fields != null) {
            for (DesignFieldDefine field : fields) {
                FieldObject fieldObj = this.initParamObjPropertyUtil.setFieldObjectProperty((FieldDefine)field);
                if (isFloatTable) {
                    fieldObj.setBizKeyOrder(fieldObj.getOwnerTableID());
                }
                fieldMap.put(fieldObj.getID(), fieldObj);
            }
        }
        tableObject.setFields(fieldMap);
        String result = this.mapper.writeValueAsString((Object)tableObject);
        return new ResponseEntity((Object)result, null, status.value());
    }

    @ApiOperation(value="\u62d6\u62fd\u65f6\u67e5\u8be2\u5b58\u50a8\u8868")
    @GetMapping(value={"getEntityTable/{tableId}"})
    public ResponseEntity<String> queryEntityTable(@PathVariable(value="tableId") String id) throws Exception {
        return this.queryEntityTableFunc(id);
    }

    private ResponseEntity<String> queryEntityTableFunc(String id) throws Exception {
        HttpStatus status = HttpStatus.OK;
        TableDefine tableDefine = null;
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineById(id);
        if (null != tableModel) {
            tableDefine = DefinitionTransUtils.toTableDefine((TableModelDefine)tableModel);
        }
        TableObject tableObject = new TableObject();
        tableObject.setID(tableDefine.getKey());
        String bizKeyFieldsStr = tableDefine.getBizKeyFieldsStr();
        tableObject.setBizKeyFieldsStr(bizKeyFieldsStr);
        tableObject.setCode(tableDefine.getCode());
        tableObject.setKind(tableDefine.getKind().getValue());
        tableObject.setTitle(tableDefine.getTitle());
        tableObject.setOwnerLevelAndId(tableDefine.getOwnerLevelAndId());
        tableObject.setSameServeCode(this.serveCodeService.isSameServeCode(tableDefine.getOwnerLevelAndId()));
        List fields = this.npRunTimeController.getAllFieldsInTable(tableDefine.getKey());
        HashMap<String, FieldObject> fieldMap = new HashMap<String, FieldObject>();
        if (fields != null) {
            for (FieldDefine field : fields) {
                FieldObject fieldObj = this.initParamObjPropertyUtil.setFieldObjectProperty(field);
                this.commonHelper.setRunFieldBizKeyOrderByTable(fieldObj, fields);
                fieldMap.put(fieldObj.getID(), fieldObj);
            }
        }
        tableObject.setFields(fieldMap);
        String result = this.mapper.writeValueAsString((Object)tableObject);
        return new ResponseEntity((Object)result, null, status.value());
    }

    @ApiOperation(value="\u901a\u8fc7\u6307\u6807\u5206\u7ec4key\uff0c\u62ff\u4e0b\u7ea7\u6307\u6807")
    @RequestMapping(value={"/getfieldsbytablekey"}, method={RequestMethod.POST})
    public List<FieldObject> getAllFieldsByTableKeys(@RequestBody String tableKeysStrs) throws JQException {
        try {
            String[] tableKeys;
            String[] stringArray = tableKeys = tableKeysStrs != null && tableKeysStrs.length() > 0 ? tableKeysStrs.split(";") : null;
            if (tableKeys != null && tableKeys.length > 0) {
                ArrayList<FieldObject> resFieldObjects = new ArrayList<FieldObject>();
                for (int i = 0; i < tableKeys.length; ++i) {
                    List designFieldDefines = this.npDesignTimeController.getAllFieldsInTable(tableKeys[i]);
                    if (designFieldDefines == null || designFieldDefines.size() <= 0) continue;
                    for (DesignFieldDefine designFieldDefine : designFieldDefines) {
                        resFieldObjects.add(this.initParamObjPropertyUtil.setFieldObjectProperty((FieldDefine)designFieldDefine));
                    }
                }
                return resFieldObjects;
            }
            return new ArrayList<FieldObject>();
        }
        catch (JQException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_137);
        }
    }

    private FieldObject getDragEntityFieldObj(String designFieldDefineKey) {
        FieldObject fieldObj = null;
        try {
            ColumnModelDefine columnModel = this.dataModelService.getColumnModelDefineByID(designFieldDefineKey);
            if (null != columnModel) {
                TableModelDefine tableModel = this.dataModelService.getTableModelDefineById(columnModel.getTableID());
                FieldDefine field = DefinitionTransUtils.toFieldDefine((ColumnModelDefine)columnModel, (String)tableModel.getName());
                ArrayList<FieldDefine> allFieldsInTable = new ArrayList<FieldDefine>();
                List columnModels = this.dataModelService.getColumnModelDefinesByTable(field.getOwnerTableKey());
                if (null != columnModels && !columnModels.isEmpty()) {
                    for (ColumnModelDefine column : columnModels) {
                        allFieldsInTable.add((FieldDefine)new FieldDefineImpl(column, tableModel.getName()));
                    }
                }
                fieldObj = this.initParamObjPropertyUtil.setFieldObjectProperty(field);
                this.commonHelper.setRunFieldBizKeyOrderByTable(fieldObj, allFieldsInTable);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return fieldObj;
    }

    private FieldObject getDragFieldObj(String designFieldDefineKey) {
        FieldObject fieldObj = null;
        try {
            DesignFieldDefine field = this.npDesignTimeController.queryFieldDefine(designFieldDefineKey);
            if (null != field) {
                DesignDataTable dataTable = this.designDataSchemeService.getDataTable(field.getOwnerTableKey());
                fieldObj = this.initParamObjPropertyUtil.setFieldObjectProperty((FieldDefine)field);
                if (dataTable.getDataTableType() == DataTableType.DETAIL || dataTable.getDataTableType() == DataTableType.ACCOUNT) {
                    fieldObj.setBizKeyOrder(fieldObj.getOwnerTableID());
                }
                return fieldObj;
            }
            fieldObj = this.getDragEntityFieldObj(designFieldDefineKey);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return fieldObj;
    }

    @ApiOperation(value="\u66f4\u65b0\u4efb\u52a1\u53c2\u6570")
    @PostMapping(value={"/taskParamUpdate"})
    @ResponseBody
    public boolean taskParamUpdate(@RequestBody ParamToDesigner paramToDesigner) throws Exception {
        Assert.hasText(paramToDesigner.getActivedTaskId(), "taskKey must not be null.");
        Assert.hasText(paramToDesigner.getDimKey(), "DimKey must not be null.");
        return this.designRestService.taskParamCheck(paramToDesigner);
    }

    @ApiOperation(value="\u83b7\u53d6\u94fe\u63a5\u8bbe\u7f6e\u6821\u9a8c\u8868\u8fbe\u5f0f\uff0c\u4ecefield\u4e2d\u67e5\u8be2")
    @GetMapping(value={"datafield-get-datavalidation/{linkExpression}"})
    public ValidationObj getFieldDataValidationProperty(@PathVariable(value="linkExpression") String linkExpression) {
        DesignDataField designDataField = this.iDesignDataSchemeService.getDataField(linkExpression);
        ValidationObj validationObj = new ValidationObj();
        if (designDataField == null) {
            return validationObj;
        }
        List validationRules = designDataField.getValidationRules();
        try {
            ExecutorContext executorContext = new ExecutorContext(this.npRunTimeController);
            executorContext.setDesignTimeData(true, (IDataDefinitionDesignTimeController)this.npDesignTimeController2);
            HashMap<String, String> dataValidationMap = new HashMap<String, String>();
            int validationType = 0;
            for (ValidationRule validationRule : validationRules) {
                DataValidationExpression dataValidationExpression = DataValidationExpressionFactory.createExpression((ExecutorContext)executorContext, (String)validationRule.getVerification());
                validationType = dataValidationExpression.getFieldDefine().getType() == FieldType.FIELD_TYPE_STRING ? 0 : 1;
                this.solutionValidation(dataValidationExpression, dataValidationMap);
            }
            validationObj.setValidationType(validationType);
            validationObj.setDataValidationMap(dataValidationMap);
            return validationObj;
        }
        catch (Exception e) {
            log.error("\u83b7\u53d6designDataField\u7684\u6570\u636e\u6821\u9a8c\u8868\u8fbe\u5f0f\u51fa\u73b0\u9519\u8bef\uff0cdataField\u4e3a\uff1a" + designDataField.getKey() + "\uff0c\u9519\u8bef\u4fe1\u606f\u4e3a\uff1a" + e.getMessage());
            return null;
        }
    }

    private void solutionValidation(DataValidationExpression dataValidationExpression, Map<String, String> dataValidationMap) {
        int dataValidationType = dataValidationExpression.getCompareType().getValue();
        if (dataValidationType == CompareType.BETWEEN.getValue() || dataValidationType == CompareType.NOT_BETWEEN.getValue()) {
            dataValidationMap.put(String.valueOf(dataValidationType), this.initParamObjPropertyUtil.ObjectToIntegerStr(dataValidationExpression.getMin()) + "|" + this.initParamObjPropertyUtil.ObjectToIntegerStr(dataValidationExpression.getMax()));
        } else if (dataValidationType == CompareType.MOBILEPHONE.getValue() || dataValidationType == CompareType.NOTNULL.getValue()) {
            dataValidationMap.put(String.valueOf(dataValidationType), "true");
        } else {
            dataValidationMap.put(String.valueOf(dataValidationType), this.initParamObjPropertyUtil.ObjectToIntegerStr(dataValidationExpression.getCompareValue()));
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u94fe\u63a5\u8bbe\u7f6e\u6821\u9a8c\u8868\u8fbe\u5f0f")
    @GetMapping(value={"datalink-get-datavalidation/{linkKey}"})
    public ValidationObj getLinkDataValidationProperty(@PathVariable(value="linkKey") String linkKey) {
        DesignDataLinkDefine designDataLinkDefine = this.nrDesignTimeController.queryDataLinkDefine(linkKey);
        try {
            if (designDataLinkDefine.getDataValidation() != null && designDataLinkDefine.getDataValidation().size() > 0) {
                ExecutorContext executorContext = new ExecutorContext(this.npRunTimeController);
                executorContext.setDesignTimeData(true, (IDataDefinitionDesignTimeController)this.npDesignTimeController2);
                HashMap<String, String> dataValidationMap = new HashMap<String, String>();
                int validationType = 0;
                for (String expression : designDataLinkDefine.getDataValidation()) {
                    DataValidationExpression dataValidationExpression = DataValidationExpressionFactory.createExpression((ExecutorContext)executorContext, (String)expression);
                    validationType = dataValidationExpression.getFieldDefine().getType() == FieldType.FIELD_TYPE_STRING ? 0 : 1;
                    this.solutionValidation(dataValidationExpression, dataValidationMap);
                }
                ValidationObj validationObj = new ValidationObj();
                validationObj.setValidationType(validationType);
                validationObj.setDataValidationMap(dataValidationMap);
                return validationObj;
            }
        }
        catch (Exception e) {
            log.error("\u83b7\u53d6LinkDataValidation\u7684\u503c\u51fa\u73b0\u9519\u8bef\uff0clinkkey\u4e3a\uff1a" + designDataLinkDefine.getKey() + "\uff0c\u9519\u8bef\u4fe1\u606f\u4e3a\uff1a" + e.getMessage());
        }
        return null;
    }

    @ApiOperation(value="\u6839\u636e\u533a\u57dfkey\u83b7\u5f97\u533a\u57df\u7684\u4e1a\u52a1\u4e3b\u952e\u548c\u662f\u5426\u53ef\u91cd\u7801")
    @PostMapping(value={"get-bizkeysandduplicatekey"})
    public RegionDuplicateBizKeys getDataRegionBizKeyFields(@RequestBody List<String> fieldKeys) {
        RegionDuplicateBizKeys regionDuplicateBizKey = new RegionDuplicateBizKeys();
        regionDuplicateBizKey.setRegionBizKeyFields(this.getBizKeyFieldsByRegionKey(fieldKeys));
        regionDuplicateBizKey.setAllowDuplicateKey(this.getAllowRepeatCodeByRegionKey(fieldKeys));
        regionDuplicateBizKey.setRegionGatherFields(this.getGatherFieldsByRegionKey(fieldKeys));
        return regionDuplicateBizKey;
    }

    @ApiOperation(value="\u83b7\u53d6\u5bf9\u7efc\u5408\u5ba1\u6838\u7684\u914d\u7f6e")
    @GetMapping(value={"/query/isOpenMulCheck"})
    public boolean isOpenMulCheck() {
        return this.mulCheckConfiguration.isOpenMulCheckBeforeCheck();
    }

    private String getGatherFieldsByRegionKey(List<String> fieldKeys) {
        if (null == fieldKeys || fieldKeys.isEmpty()) {
            return "";
        }
        DesignDataTable dataTable = null;
        for (String fieldKey : fieldKeys) {
            DesignDataField dataField;
            if (!com.jiuqi.bi.util.StringUtils.isNotEmpty((String)fieldKey) || null == (dataField = this.designDataSchemeService.getDataField(fieldKey))) continue;
            dataTable = this.designDataSchemeService.getDataTable(dataField.getDataTableKey());
            break;
        }
        if (null == dataTable) {
            return "";
        }
        List designDataFields = this.designDataSchemeService.getDataFieldByTableCode(dataTable.getCode());
        HashSet<String> gatherFieldKeys = new HashSet<String>();
        String[] unclassifiedKeys = dataTable.getGatherFieldKeys();
        StringBuffer gatherFields = new StringBuffer();
        if (unclassifiedKeys != null && unclassifiedKeys.length != 0) {
            for (int i = 0; i < unclassifiedKeys.length; ++i) {
                gatherFieldKeys.add(unclassifiedKeys[i]);
            }
        }
        for (DesignDataField dDF : designDataFields) {
            String fieldKey = dDF.getKey();
            if (dDF.getDataFieldKind().equals((Object)DataFieldKind.TABLE_FIELD_DIM)) {
                gatherFields.append(fieldKey).append(";");
                continue;
            }
            if (!gatherFieldKeys.contains(fieldKey)) continue;
            gatherFields.append(fieldKey).append(";");
        }
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)gatherFields.toString())) {
            return "";
        }
        return gatherFields.deleteCharAt(gatherFields.length() - 1).toString();
    }

    private String getBizKeyFieldsByRegionKey(List<String> fieldKeys) {
        if (null == fieldKeys || fieldKeys.isEmpty()) {
            return "";
        }
        DesignDataTable dataTable = null;
        for (String fieldKey : fieldKeys) {
            DesignDataField dataField;
            if (!com.jiuqi.bi.util.StringUtils.isNotEmpty((String)fieldKey) || null == (dataField = this.designDataSchemeService.getDataField(fieldKey))) continue;
            dataTable = this.designDataSchemeService.getDataTable(dataField.getDataTableKey());
            break;
        }
        if (null == dataTable) {
            return "";
        }
        List bizFields = this.designDataSchemeService.getDataFields(Arrays.asList(dataTable.getBizKeys())).stream().filter(f -> DataFieldKind.PUBLIC_FIELD_DIM != f.getDataFieldKind() && DataFieldKind.BUILT_IN_FIELD != f.getDataFieldKind()).collect(Collectors.toList());
        bizFields.sort(null);
        List bizKeys = bizFields.stream().map(Basic::getKey).collect(Collectors.toList());
        return StringUtils.collectionToDelimitedString(bizKeys, (String)";");
    }

    private boolean getAllowRepeatCodeByRegionKey(List<String> fieldKeys) {
        if (null == fieldKeys || fieldKeys.isEmpty()) {
            return false;
        }
        DesignDataTable dataTable = null;
        for (String fieldKey : fieldKeys) {
            DesignDataField dataField;
            if (!com.jiuqi.bi.util.StringUtils.isNotEmpty((String)fieldKey) || null == (dataField = this.designDataSchemeService.getDataField(fieldKey))) continue;
            dataTable = this.designDataSchemeService.getDataTable(dataField.getDataTableKey());
            break;
        }
        return null != dataTable && dataTable.isRepeatCode();
    }
}

