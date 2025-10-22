/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.ReportAuditType
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.util.ServeCodeService
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataDeserialize
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataDeserialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 */
package com.jiuqi.nr.designer.helper;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.ReportAuditType;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.common.IDesignerEntityUpgrader;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.helper.SaveSchemePeriodHelper;
import com.jiuqi.nr.designer.service.IDesignRestService;
import com.jiuqi.nr.designer.util.InitParamObjPropertyUtil;
import com.jiuqi.nr.designer.web.facade.FlowsObj;
import com.jiuqi.nr.designer.web.facade.FormSchemeObj;
import com.jiuqi.nr.designer.web.facade.TaskObj;
import com.jiuqi.nr.designer.web.facade.TaskOrgListVO;
import com.jiuqi.nr.designer.web.facade.TaskOrgVO;
import com.jiuqi.nr.designer.web.facade.unusual.TableSupportDatedVersion;
import com.jiuqi.nr.designer.web.rest.vo.ParamToDesigner;
import com.jiuqi.nr.designer.web.treebean.FieldObject;
import com.jiuqi.nr.designer.web.treebean.FormGroupObject;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataDeserialize;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataDeserialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class CommonHelper {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private ServeCodeService serveCodeService;
    @Autowired
    private InitParamObjPropertyUtil initParamObjPropertyUtil;
    @Autowired
    private IDesignRestService designRestService;
    @Autowired
    private IDesignerEntityUpgrader iDesignerEntityUpgrader;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private SaveSchemePeriodHelper schemePeriodHelper;

    public DesignTaskFlowsDefine initTaskFlowsDefine(FlowsObj flowsObj) throws JQException {
        DesignTaskFlowsDefine taskflowDefine = new DesignTaskFlowsDefine();
        taskflowDefine.setFlowsType(FlowsType.fromType((int)flowsObj.getFlowType()));
        taskflowDefine.setDesignTableDefines(flowsObj.getSubmitEntityTables());
        taskflowDefine.setSubTable(flowsObj.isSubTable());
        taskflowDefine.setDataConfirm(flowsObj.isDataConfirm());
        taskflowDefine.setSubmitExplain(flowsObj.isSubmitExplain());
        taskflowDefine.setForceSubmitExplain(flowsObj.isForceSubmitExplain());
        taskflowDefine.setReturnVersion(flowsObj.isReturnVersion());
        taskflowDefine.setAllowTakeBack(flowsObj.isAllowTakeBack());
        taskflowDefine.setAllowModifyData(flowsObj.isAllowModifyData());
        taskflowDefine.setUnitSubmitForCensorship(flowsObj.isUnitSubmitForCensorship());
        taskflowDefine.setSelectedRoleKey(flowsObj.getSelectedRoleId());
        taskflowDefine.setUnitSubmitForForce(flowsObj.isUnitSubmitForForce());
        taskflowDefine.setSelectedRoleForceKey(flowsObj.getSelectedRoleForceId());
        taskflowDefine.setCheckBeforeReporting(flowsObj.getCheckBeforeReporting());
        taskflowDefine.setCheckBeforeReportingType(ReportAuditType.forValue((int)flowsObj.getCheckBeforeReportingType()));
        taskflowDefine.setCheckBeforeReportingCustom(flowsObj.getCheckBeforeReportingCustom());
        taskflowDefine.setBackDescriptionNeedWrite(flowsObj.getBackDescriptionNeedWrite());
        taskflowDefine.setAllowFormBack(flowsObj.getAllowFormBack());
        taskflowDefine.setStepByStepReport(flowsObj.getStepByStepReport());
        taskflowDefine.setStepByStepBack(flowsObj.getStepByStepBack());
        taskflowDefine.setFilterCondition(flowsObj.getFilterCondition());
        taskflowDefine.setErroStatus(flowsObj.getErroStatus());
        taskflowDefine.setPromptStatus(flowsObj.getPromptStatus());
        taskflowDefine.setDefaultButtonName(flowsObj.isDefaultButtonName());
        taskflowDefine.setDefaultButtonNameConfig(flowsObj.getDefaultButtonNameConfig());
        taskflowDefine.setSendMessageMail(flowsObj.getSendMessageMail());
        taskflowDefine.setWordFlowType(flowsObj.getWorkFlowType());
        taskflowDefine.setStepReportType(flowsObj.getStepReportType());
        taskflowDefine.setApplyReturn(flowsObj.isApplyReturn());
        taskflowDefine.setReportBeforeOperation(flowsObj.getCheckBeforeFormula());
        taskflowDefine.setReportBeforeOperationValue(flowsObj.getCheckBeforeFormulaValue());
        taskflowDefine.setRealMulCheckBeforeCheck(flowsObj.getMulCheckBeforeCheck());
        taskflowDefine.setReportBeforeAudit(flowsObj.getCheckBeforeCheck());
        taskflowDefine.setReportBeforeAuditValue(flowsObj.getCheckBeforeCheckValue());
        taskflowDefine.setReportBeforeAuditType(ReportAuditType.forValue((int)flowsObj.getCheckBeforeCheckType()));
        taskflowDefine.setReportBeforeAuditCustom(flowsObj.getCheckBeforeCheckCustom());
        taskflowDefine.setSubmitAfterFormula(flowsObj.isSubmitAfterFormula());
        taskflowDefine.setSubmitAfterFormulaValue(flowsObj.getSubmitAfterFormulaValue());
        taskflowDefine.setMessageTemplate(flowsObj.getMessageTemplate());
        taskflowDefine.setGoBackAllSup(flowsObj.getGoBackAllSup());
        taskflowDefine.setOpenForceControl(flowsObj.isOpenForceControl());
        taskflowDefine.setDefaultNodeName(flowsObj.isDefaultNodeName());
        taskflowDefine.setDefaultNodeNameConfig(flowsObj.getDefaultNodeNameConfig());
        taskflowDefine.setSpecialAudit(flowsObj.isSpecialAudit());
        if (flowsObj.isOpenBackType() && !StringUtils.isEmpty((String)flowsObj.getBackTypeEntity())) {
            taskflowDefine.setOpenBackType(flowsObj.isOpenBackType());
            taskflowDefine.setBackTypeEntity(flowsObj.getBackTypeEntity());
        } else {
            taskflowDefine.setOpenBackType(false);
            taskflowDefine.setBackTypeEntity(" ");
        }
        if (flowsObj.isUnitSubmitForCensorship()) {
            taskflowDefine.setReturnExplain(flowsObj.isReturnExplain());
            taskflowDefine.setAllowTakeBackForSubmit(flowsObj.isAllowTakeBackForSubmit());
        } else {
            taskflowDefine.setReturnExplain(false);
            taskflowDefine.setAllowTakeBackForSubmit(false);
        }
        return taskflowDefine;
    }

    public static String printStackTraceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }

    public boolean checkRecord(DesignTableDefine tableDefine) throws JQException {
        return this.iRuntimeDataSchemeService.dataTableCheckData(new String[]{tableDefine.getKey()});
    }

    public ObjectMapper gridSerialize() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
        module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
        mapper.registerModule((Module)module);
        return mapper;
    }

    public ObjectMapper gridDeSerialize() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(GridCellData.class, (JsonDeserializer)new GridCellDataDeserialize());
        module.addDeserializer(Grid2Data.class, (JsonDeserializer)new Grid2DataDeserialize());
        mapper.registerModule((Module)module);
        return mapper;
    }

    public IEntityTable getTableData(String tableKey) {
        return this.iDesignerEntityUpgrader.getTableData(tableKey);
    }

    public void checkSubmitEntity(FlowsObj flowsObj) throws JQException {
        if (StringUtils.isEmpty((String)flowsObj.getSubmitEntityTables())) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_014);
        }
        String[] submitTables = flowsObj.getSubmitEntityTables().split(";");
        List<String> entityList = this.iDesignerEntityUpgrader.getEntityList(submitTables);
        if (CollectionUtils.isEmpty(entityList)) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_015);
        }
    }

    public boolean isSetDimension(String tableKey, String[] regionKeys) throws JQException {
        List allFieldsInTable = this.nrDesignTimeController.getAllFieldsInTable(tableKey);
        if (allFieldsInTable.size() != 0) {
            for (DesignFieldDefine designFieldDefine : allFieldsInTable) {
                String bindingExpression;
                List linkByField = this.nrDesignTimeController.getReferencedDataLinkByField(designFieldDefine);
                if (linkByField.size() == 0) continue;
                if (linkByField.size() > 1) {
                    for (DesignDataLinkDefine designDataLinkDefine : linkByField) {
                        String bindingExpression2;
                        String regionKey = designDataLinkDefine.getRegionKey();
                        boolean contains = Arrays.asList(regionKeys).contains(regionKey);
                        if (!contains || !StringUtils.isEmpty((String)(bindingExpression2 = designDataLinkDefine.getBindingExpression()))) continue;
                        return true;
                    }
                    continue;
                }
                String regionKey = ((DesignDataLinkDefine)linkByField.get(0)).getRegionKey();
                boolean contains = Arrays.asList(regionKeys).contains(regionKey);
                if (!contains || !StringUtils.isEmpty((String)(bindingExpression = ((DesignDataLinkDefine)linkByField.get(0)).getBindingExpression()))) continue;
                return true;
            }
        }
        return false;
    }

    public FormGroupObject initNewGroupObject(FormSchemeObj schemeObject) throws JQException {
        FormGroupObject formGroup = new FormGroupObject();
        formGroup.setID(UUIDUtils.getKey());
        formGroup.setTitle("\u9ed8\u8ba4\u8868\u5355\u5206\u7ec4");
        formGroup.setTaskId(schemeObject.getTaskId());
        formGroup.setParentGroupID("");
        formGroup.setOrder(OrderGenerator.newOrder());
        formGroup.setFormSchemeKey(schemeObject.getID());
        formGroup.setIsNew(true);
        formGroup.setMeasureUnit(schemeObject.getMeasureUnit());
        formGroup.setOwnerLevelAndId(this.serveCodeService.getServeCode());
        formGroup.setSameServeCode(true);
        return formGroup;
    }

    public FormSchemeObj initNewSchemeObject(TaskObj taskObject) throws JQException {
        FormSchemeObj formScheme = new FormSchemeObj();
        formScheme.setDw(null);
        formScheme.setDims(null);
        formScheme.setDatetime(null);
        formScheme.setID(UUIDUtils.getKey());
        formScheme.setTitle("\u9ed8\u8ba4\u62a5\u8868\u65b9\u6848");
        formScheme.setTaskId(taskObject.getID());
        formScheme.setOrder(OrderGenerator.newOrder());
        formScheme.setIsNew(true);
        formScheme.setOwnerLevelAndId(this.serveCodeService.getServeCode());
        formScheme.setSameServeCode(true);
        formScheme.setTableSupportDatedVersion(new TableSupportDatedVersion());
        FlowsObj flowObj = new FlowsObj();
        flowObj.setFlowsSettingIsExtend(true);
        formScheme.setFlowsObj(flowObj);
        formScheme.setEntitiesIsExtend(true);
        formScheme.setEntityList(taskObject.getEntityList());
        formScheme.setPeriodIsExtend(true);
        formScheme.setMeasureUnitIsExtend(true);
        formScheme.setMeasureUnit(taskObject.getMeasureUnit());
        return formScheme;
    }

    public String getFirstStartData(String dimKey) throws Exception {
        String periodDate;
        block8: {
            IPeriodProvider periodProvider;
            IPeriodEntity periodEntity;
            block9: {
                block7: {
                    periodDate = null;
                    periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(dimKey);
                    periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntity.getKey());
                    if (!PeriodType.YEAR.equals((Object)periodEntity.getPeriodType())) break block7;
                    String currPeriod = periodProvider.getCurPeriod().getCode();
                    if (null == currPeriod) break block8;
                    String priorPeriod = periodProvider.priorPeriod(currPeriod);
                    List periodItems = periodProvider.getPeriodItems();
                    boolean periodDataHave = false;
                    for (IPeriodRow periodItem : periodItems) {
                        if (!periodItem.getCode().equals(priorPeriod)) continue;
                        periodDate = priorPeriod;
                        periodDataHave = true;
                        break;
                    }
                    if (!periodDataHave) {
                        periodDate = currPeriod;
                    }
                    break block8;
                }
                if (!PeriodType.CUSTOM.equals((Object)periodEntity.getPeriodType())) break block9;
                List periodItems = periodProvider.getPeriodItems();
                if (periodItems.size() == 0) break block8;
                periodDate = ((IPeriodRow)periodItems.get(0)).getCode();
                break block8;
            }
            List periodItems = periodProvider.getPeriodItems();
            IPeriodRow currPeriod = periodProvider.getCurPeriod();
            if (null != currPeriod) {
                if (PeriodUtils.isPeriod13((String)periodEntity.getCode(), (PeriodType)periodEntity.getPeriodType())) {
                    List collect = periodItems.stream().filter(e -> e.getYear() == currPeriod.getYear()).collect(Collectors.toList());
                    for (IPeriodRow iPeriodRow : collect) {
                        if (Integer.parseInt(iPeriodRow.getCode().substring(5)) != 1) continue;
                        periodDate = iPeriodRow.getCode();
                    }
                } else {
                    for (IPeriodRow periodItem : periodItems) {
                        if (periodDate != null || periodItem.getStartDate().getYear() < currPeriod.getStartDate().getYear()) continue;
                        periodDate = periodItem.getCode();
                    }
                }
            }
        }
        return periodDate;
    }

    public TaskObj initNewTaskObject(ParamToDesigner paramToDesigner) throws Exception {
        String taskID = paramToDesigner.getActivedTaskId();
        TaskObj taskObject = new TaskObj();
        taskObject.setID(taskID);
        taskObject.setCode(OrderGenerator.newOrder());
        taskObject.setTitle(this.designRestService.queryNewTaskTitle());
        List dimensions = this.designDataSchemeService.getDataSchemeDimension(paramToDesigner.getDataScheme());
        dimensions.removeIf(x -> AdjustUtils.isAdjust((String)x.getDimKey()));
        ArrayList<String> entityList = new ArrayList<String>();
        DesignDataDimension periodDimension = null;
        DesignDataDimension unitDimension = null;
        boolean isUnit = true;
        block6: for (int i = 0; i < dimensions.size(); ++i) {
            DesignDataDimension dimension = (DesignDataDimension)dimensions.get(i);
            switch (dimension.getDimensionType()) {
                case PERIOD: {
                    periodDimension = dimension;
                    continue block6;
                }
                case UNIT: {
                    unitDimension = dimension;
                    continue block6;
                }
                case UNIT_SCOPE: {
                    isUnit = false;
                    continue block6;
                }
                case DIMENSION: {
                    entityList.add(dimension.getDimKey());
                }
            }
        }
        if (isUnit) {
            taskObject.setDw(unitDimension.getDimKey());
        } else {
            taskObject.setDw(paramToDesigner.getDimKey());
        }
        if (!CollectionUtils.isEmpty(paramToDesigner.getOrgList())) {
            TaskOrgListVO taskOrgListObj = new TaskOrgListVO();
            ArrayList<TaskOrgVO> orgList = new ArrayList<TaskOrgVO>();
            for (String entityId : paramToDesigner.getOrgList()) {
                TaskOrgVO org = new TaskOrgVO();
                org.setTaskKey(taskID);
                org.setEntityId(entityId);
                orgList.add(org);
            }
            taskOrgListObj.setNeedUpdate(true);
            taskOrgListObj.setOrgList(orgList);
            taskObject.setOrgList(taskOrgListObj);
        } else {
            taskObject.setOrgList(new TaskOrgListVO());
        }
        if (periodDimension != null) {
            String[] dateArr = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodDimension.getDimKey()).getPeriodCodeRegion();
            taskObject.setDatetime(periodDimension.getDimKey());
            String firstStartData = this.getFirstStartData(periodDimension.getDimKey());
            if (StringUtils.isNotEmpty((String)firstStartData)) {
                taskObject.setFromPeriod(firstStartData);
            }
        }
        taskObject.setDims(entityList.stream().collect(Collectors.joining(";")));
        taskObject.setDataScheme(paramToDesigner.getDataScheme());
        taskObject.setTaskType(paramToDesigner.getTaskType().getValue());
        taskObject.setEntityList(this.initParamObjPropertyUtil.initEntityList(taskObject.getTkMasterKey()));
        taskObject.setFunctionList(this.initParamObjPropertyUtil.getFunctionObjs());
        taskObject.setOperatorList(this.initParamObjPropertyUtil.getOperatorObjs());
        taskObject.setFlowsObj(new FlowsObj());
        taskObject.setMeasureUnit("9493b4eb-6516-48a8-a878-25a63a23e63a;YUAN");
        taskObject.setOwnerLevelAndId(this.serveCodeService.getServeCode());
        taskObject.setSameServeCode(true);
        taskObject.setTableSupportDatedVersion(new TableSupportDatedVersion());
        return taskObject;
    }

    public FormSchemeObj convertSchemeObj(DesignFormSchemeDefine designFormSchemeDefine) throws JQException, Exception {
        FormSchemeObj formSchemeObj = new FormSchemeObj();
        formSchemeObj.setID(designFormSchemeDefine.getKey());
        formSchemeObj.setTitle(designFormSchemeDefine.getTitle());
        formSchemeObj.setTaskId(designFormSchemeDefine.getTaskKey());
        formSchemeObj.setOrder(designFormSchemeDefine.getOrder());
        formSchemeObj.setPeriodType(designFormSchemeDefine.getPeriodType().type());
        formSchemeObj.setSchemePeriodOffset(designFormSchemeDefine.getPeriodOffset());
        formSchemeObj.setEntityList(this.designRestService.getFormSchemeEntity(designFormSchemeDefine.getKey(), true));
        formSchemeObj.setFilterExpression(designFormSchemeDefine.getFilterExpression());
        FlowsObj seFlowSObj = this.initParamObjPropertyUtil.designFlowsDefineTransToFlowsObj(designFormSchemeDefine.getFlowsSetting(), designFormSchemeDefine.getKey());
        formSchemeObj.setFlowsObj(seFlowSObj);
        formSchemeObj.setEffectivePeriods(this.schemePeriodHelper.queryEffectivePeriods(formSchemeObj.getID()));
        return formSchemeObj;
    }

    public void setFieldBizKeyOrderByTable(FieldObject field, List<DesignFieldDefine> fieldList) {
        boolean isFloatTable = false;
        for (DesignFieldDefine fieldDefine : fieldList) {
            if (!"BIZKEYORDER".equals(fieldDefine.getCode())) continue;
            isFloatTable = true;
        }
        if (isFloatTable) {
            field.setBizKeyOrder(field.getOwnerTableID());
        }
    }

    public void setRunFieldBizKeyOrderByTable(FieldObject field, List<FieldDefine> fieldList) {
        boolean isFloatTable = false;
        for (FieldDefine fieldDefine : fieldList) {
            if (!"BIZKEYORDER".equals(fieldDefine.getCode())) continue;
            isFloatTable = true;
        }
        if (isFloatTable) {
            field.setBizKeyOrder(field.getOwnerTableID());
        }
    }
}

