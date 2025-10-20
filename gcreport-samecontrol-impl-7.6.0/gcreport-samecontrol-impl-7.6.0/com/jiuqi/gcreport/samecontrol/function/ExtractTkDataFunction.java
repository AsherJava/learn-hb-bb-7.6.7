/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.GCAdjTypeEnum
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.nr.impl.service.impl.GCFormTabSelectServiceImpl
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.node.CellDataNode
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.samecontrol.function;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.GCAdjTypeEnum;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.nr.impl.service.impl.GCFormTabSelectServiceImpl;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgSettingZbAttrDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgSettingZbAttrEO;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlChgEnvContextImpl;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractDataService;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ExtractTkDataFunction
extends Function
implements INrFunction {
    @Lazy
    @Autowired
    private transient IRunTimeViewController iRunTimeViewController;
    @Lazy
    @Autowired
    private transient IDataAccessProvider dataAccessProvider;
    @Lazy
    @Autowired
    private transient IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Lazy
    @Resource
    private transient IJtableParamService jtableParamService;
    @Lazy
    @Autowired
    private transient SameCtrlChgSettingZbAttrDao sameCtrlChgSettingZbAttrDao;
    @Lazy
    @Autowired
    private DataModelService dataModelService;
    @Lazy
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Lazy
    @Autowired
    private DefinitionAuthorityProvider authoritryProvider;
    @Lazy
    @Autowired
    private SameCtrlExtractDataService sameCtrlExtractDataService;
    private ConsolidatedTaskService taskService;
    private static final Logger logger = LoggerFactory.getLogger(ExtractTkDataFunction.class);

    public ExtractTkDataFunction() {
        this.parameters().add(new Parameter("SameCtrlUnitCode", 6, "\u5bf9\u5e94\u540c\u63a7\u53d8\u52a8\u5355\u4f4d"));
        this.parameters().add(new Parameter("SameCtrlDate", 2, "\u540c\u63a7\u5212\u51fa\u65e5\u671f"));
        this.parameters().add(new Parameter("SameCtrlInventUnitCode", 6, "\u540c\u63a7\u865a\u62df\u5355\u4f4d\u7c7b\u578b"));
    }

    public String name() {
        return "EXTRACTTKDATA";
    }

    public String title() {
        return "\u540c\u63a7\u6570\u636e\u63d0\u53d6";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> iAstNodeList) throws SyntaxException {
        long start = System.currentTimeMillis();
        this.taskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        logger.info("\u540c\u63a7\u6570\u636e\u63d0\u53d6\u8fd0\u7b97\u5f00\u59cb");
        QueryContext qContext = (QueryContext)iContext;
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)qContext.getExeContext().getEnv();
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(env.getFormSchemeKey());
        try {
            if (PeriodType.MONTH.equals((Object)formScheme.getPeriodType())) {
                List<SameCtrlChgSettingZbAttrEO> sameCtrlChgSettingZbAttrEOList = this.sameCtrlChgSettingZbAttrDao.getOptionZbAttrByTaskAndShcemeId(formScheme.getTaskKey(), formScheme.getKey());
                this.handleSameCtrl(sameCtrlChgSettingZbAttrEOList, iContext, iAstNodeList);
            }
        }
        catch (Exception e) {
            logger.error("\u540c\u63a7\u6570\u636e\u63d0\u53d6\u8fd0\u7b97\u5931\u8d25", e);
        }
        logger.info("\u540c\u63a7\u6570\u636e\u63d0\u53d6\u8fd0\u7b97\u7ed3\u675f");
        logger.info("\u540c\u63a7\u6570\u636e\u63d0\u53d6\u8fd0\u7b97\u8017\u65f6\uff1a" + (System.currentTimeMillis() - start) / 1000L + "\u79d2");
        return null;
    }

    private void handleSameCtrl(List<SameCtrlChgSettingZbAttrEO> sameCtrlChgSettingZbAttrEOList, IContext iContext, List<IASTNode> iAstNodeList) throws Exception {
        QueryContext qContext = (QueryContext)iContext;
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)qContext.getExeContext().getEnv();
        DimensionValueSet ds = qContext.getCurrentMasterKey();
        DimensionParamsVO queryParamsVO = this.initDimensionParamsVO(ds, env);
        if (queryParamsVO == null || !this.unitWriteable(queryParamsVO)) {
            logger.error("\u540c\u63a7\u6570\u636e\u63d0\u53d6\u8fd0\u7b97\uff0c\u5355\u4f4d\u4e0d\u53ef\u5199\u3002");
            return;
        }
        FieldDefine unitCodeFD = this.getFieldDefine(iAstNodeList.get(0));
        IDataTable unitCodeIDT = this.getIDataTable(ds, unitCodeFD);
        if (unitCodeIDT.getCount() == 0 || unitCodeIDT.getItem(0).getValue(unitCodeFD).getAsObject() == null) {
            logger.error("\u540c\u63a7\u6570\u636e\u63d0\u53d6\u8fd0\u7b97\uff0c\u5bf9\u5e94\u540c\u63a7\u53d8\u52a8\u5355\u4f4d\u4e3a\u7a7a\u3002");
            return;
        }
        FieldDefine dateFD = this.getFieldDefine(iAstNodeList.get(1));
        IDataTable dateIDT = this.getIDataTable(ds, dateFD);
        if (dateIDT.getCount() == 0 || dateIDT.getItem(0).getValue(dateFD).getAsObject() == null) {
            logger.error("\u540c\u63a7\u6570\u636e\u63d0\u53d6\u8fd0\u7b97\uff0c\u5bf9\u5e94\u540c\u63a7\u5212\u51fa\u65e5\u671f\u4e3a\u7a7a\u3002");
            return;
        }
        Date dateSource = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(dateIDT.getItem(0).getValue(dateFD).getAsString()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateSource);
        calendar.set(5, 1);
        FieldDefine unitCodeTypeFD = this.getFieldDefine(iAstNodeList.get(2));
        IDataTable unitCodeTypeIDT = this.getIDataTable(ds, unitCodeTypeFD);
        if (unitCodeTypeIDT.getCount() == 0 || unitCodeTypeIDT.getItem(0).getValue(unitCodeTypeFD).getAsObject() == null) {
            logger.error("\u540c\u63a7\u6570\u636e\u63d0\u53d6\u8fd0\u7b97\uff0c\u5bf9\u5e94\u540c\u63a7\u865a\u62df\u5355\u4f4d\u7c7b\u578b\u4e3a\u7a7a\u3002");
            return;
        }
        if ("2".equals(unitCodeTypeIDT.getItem(0).getAsString(unitCodeTypeFD))) {
            this.handleCommonSuperior(ds, env, unitCodeFD, unitCodeTypeFD, queryParamsVO);
        } else {
            SameCtrlExtractReportCond sameCtrlExtractReportData = new SameCtrlExtractReportCond();
            sameCtrlExtractReportData.setTaskId(queryParamsVO.getTaskId());
            sameCtrlExtractReportData.setSchemeId(queryParamsVO.getSchemeId());
            String systemId = this.taskService.getSystemIdByTaskId(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
            if (StringUtils.isEmpty((String)systemId)) {
                throw new BusinessRuntimeException("\u4efb\u52a1\u4e0e\u5408\u5e76\u4f53\u7cfb\u5173\u8054\u5173\u7cfb\u4e22\u5931\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
            }
            sameCtrlExtractReportData.setSystemId(systemId);
            sameCtrlExtractReportData.setOrgType(queryParamsVO.getOrgType());
            sameCtrlExtractReportData.setChangedCode(unitCodeIDT.getItem(0).getAsString(unitCodeFD));
            sameCtrlExtractReportData.setPeriodStr(queryParamsVO.getPeriodStr());
            sameCtrlExtractReportData.setChangeDate(dateSource);
            sameCtrlExtractReportData.setCurrencyId(queryParamsVO.getCurrencyId());
            sameCtrlExtractReportData.setAdjTypeId(String.valueOf(ds.getValue("MD_GCADJTYPE")));
            sameCtrlExtractReportData.setVirtualCode(queryParamsVO.getOrgId());
            sameCtrlExtractReportData.setSelectAdjustCode(String.valueOf(ds.getValue("ADJUST")));
            SameCtrlChgEnvContextImpl sameCtrlChgEnvContext = new SameCtrlChgEnvContextImpl(UUIDUtils.newUUIDStr());
            sameCtrlChgEnvContext.setSuccessFlag(true);
            sameCtrlChgEnvContext.setSameCtrlExtractReportCond(sameCtrlExtractReportData);
            this.sameCtrlExtractDataService.extractReportData(sameCtrlChgEnvContext);
        }
    }

    private List<FormDefine> getPowerFormForScheme(String formSchemeKey, DimensionParamsVO queryParamsVO, DimensionValueSet ds) {
        List formDefineList = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey).stream().filter(formDefine -> !formDefine.getTitle().contains("\u5c01\u9762\u4ee3\u7801")).collect(Collectors.toList());
        GCFormTabSelectServiceImpl formTabSelectService = (GCFormTabSelectServiceImpl)SpringContextUtils.getBean(GCFormTabSelectServiceImpl.class);
        return formDefineList.stream().filter(define -> formTabSelectService.isFormCondition(this.convert2JtableContext(ds, queryParamsVO.getTaskId(), formSchemeKey), define.getFormCondition(), ds)).collect(Collectors.toList());
    }

    private boolean writeableStatus(UploadState status, String formTitle) {
        if (status == UploadState.SUBMITED) {
            logger.error("\u62a5\u8868\u3010" + formTitle + "\u3011\u6570\u636e\u5df2\u9001\u5ba1\uff0c\u4e0d\u5141\u8bb8\u6267\u884c\u64cd\u4f5c");
            return false;
        }
        if (status == UploadState.UPLOADED) {
            logger.error("\u62a5\u8868\u3010" + formTitle + "\u3011\u6570\u636e\u5df2\u4e0a\u62a5\uff0c\u4e0d\u5141\u8bb8\u6267\u884c\u64cd\u4f5c");
            return false;
        }
        if (status == UploadState.CONFIRMED) {
            logger.error("\u62a5\u8868\u3010" + formTitle + "\u3011\u6570\u636e\u5df2\u786e\u8ba4\uff0c\u4e0d\u5141\u8bb8\u6267\u884c\u64cd\u4f5c");
            return false;
        }
        return true;
    }

    private void handleCommonSuperior(DimensionValueSet ds, ReportFmlExecEnvironment env, FieldDefine unitCodeFD, FieldDefine unitCodeTypeFD, DimensionParamsVO queryParamsVO) throws Exception {
        IDataTable unitCodeIDT = this.getIDataTable(ds, unitCodeFD);
        Map<String, List<FieldDefine>> fieldDefineLisMap = this.getFieldDefineList(env.getFormSchemeKey(), queryParamsVO, ds);
        List<GcOrgCacheVO> orgCacheVOList = this.getOrgByCodeList(ds, env);
        fieldDefineLisMap.forEach((type, fieldDefineList) -> {
            Map<String, List<FieldDefine>> tableKey2FieldList = fieldDefineList.stream().collect(Collectors.groupingBy(fieldDefine -> {
                List dataFieldDeployInfoList = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldDefine.getKey()});
                String tableModelKey = ((DataFieldDeployInfo)dataFieldDeployInfoList.get(0)).getTableModelKey();
                TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(tableModelKey);
                return tableModelDefine.getID();
            }));
            HashMap<String, List<Map<String, Object>>> tableKey2zbDataMap = new HashMap<String, List<Map<String, Object>>>();
            orgCacheVOList.forEach(gcOrgCacheVO -> {
                try {
                    IDataTable otherIDataTable;
                    DimensionValueSet otherDs = new DimensionValueSet(ds);
                    otherDs.setValue("MD_ORG", (Object)gcOrgCacheVO.getCode());
                    IDataTable otherUnitCode = this.getIDataTable(otherDs, unitCodeFD);
                    IDataTable otherUnitCodeType = this.getIDataTable(otherDs, unitCodeTypeFD);
                    if (otherUnitCode.getCount() == 0 || otherUnitCode.getItem(0).getValue(unitCodeFD).getAsObject() == null || otherUnitCodeType.getCount() == 0 || otherUnitCodeType.getItem(0).getValue(unitCodeTypeFD).getAsObject() == null) {
                        return;
                    }
                    if (otherUnitCode.getItem(0).getValue(unitCodeFD).getAsString().equals(unitCodeIDT.getItem(0).getValue(unitCodeFD).getAsString()) && otherUnitCodeType.getItem(0).getValue(unitCodeTypeFD).getAsInt() == 1 && (otherIDataTable = this.getIDataTableList(otherDs, (List<FieldDefine>)fieldDefineList)).getCount() != 0) {
                        int count = 0;
                        while (count < otherIDataTable.getCount()) {
                            int finalCount = count++;
                            tableKey2FieldList.forEach((tableKey, fieldDefines) -> {
                                Map<String, Object> fieldCode2Value = this.getFieldCodeFieldValueMap(queryParamsVO, (String)type);
                                int size = fieldCode2Value.size();
                                fieldDefines.forEach(fieldDefine -> {
                                    try {
                                        IDataRow otherIDataRow = otherIDataTable.getItem(finalCount);
                                        if (this.isZbValueN(fieldDefine.getType().getValue()).booleanValue() && otherIDataRow.getValue(fieldDefine).getAsCurrency() != null && otherIDataRow.getValue(fieldDefine).getAsCurrency().compareTo(new BigDecimal(0)) != 0) {
                                            fieldCode2Value.put(fieldDefine.getCode(), new BigDecimal(0).subtract(otherIDataRow.getValue(fieldDefine).getAsCurrency()));
                                        } else if (otherIDataRow.getValue(fieldDefine).getAsObject() != null) {
                                            fieldCode2Value.put(fieldDefine.getCode(), otherIDataRow.getValue(fieldDefine).getAsObject());
                                        }
                                    }
                                    catch (Exception e) {
                                        logger.error("\u540c\u63a7\u6570\u636e\u63d0\u53d6\u516c\u5f0f\u6307\u6807\u3010" + fieldDefine.getCode() + "\u3011\u6267\u884c\u5931\u8d25", e);
                                    }
                                });
                                if (size != fieldCode2Value.size()) {
                                    List dataList = tableKey2zbDataMap.getOrDefault(tableKey, new ArrayList());
                                    dataList.add(fieldCode2Value);
                                    tableKey2zbDataMap.put((String)tableKey, dataList);
                                }
                            });
                        }
                    }
                }
                catch (Exception e) {
                    logger.error("\u540c\u63a7\u6570\u636e\u63d0\u53d6\u516c\u5f0f\u6267\u884c\u5355\u4f4d\u3010" + gcOrgCacheVO.getCode() + "\u3011\u5931\u8d25", e);
                }
            });
            if (type.equals("notSimple")) {
                this.deleteVirtualCodeSql(tableKey2zbDataMap, queryParamsVO);
                this.saveReportData(tableKey2zbDataMap);
            } else {
                this.saveSimple(tableKey2zbDataMap, queryParamsVO);
            }
        });
    }

    private void saveSimple(Map<String, List<Map<String, Object>>> tableKey2zbDataMap, DimensionParamsVO queryParamsVO) {
        tableKey2zbDataMap.forEach((tableKey, zbDataAllList) -> {
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(tableKey);
            try {
                int count = this.getVirtualCodeCount(tableModelDefine.getName(), queryParamsVO);
                Map<String, String> fieldCode2FieldName = this.initFieldName(tableModelDefine);
                if (count == 0) {
                    zbDataAllList.forEach(zbData2zbValue -> this.insertVirtualCodeSql((Map<String, Object>)zbData2zbValue, tableModelDefine.getName(), fieldCode2FieldName));
                } else {
                    this.updateVirtualCodeSql((List<Map<String, Object>>)zbDataAllList, queryParamsVO, tableModelDefine.getName(), fieldCode2FieldName);
                }
            }
            catch (Exception e) {
                logger.error("[" + tableModelDefine.getTitle() + "]\u8868\u63d0\u53d6\u5f02\u5e38\u3002", e);
            }
        });
    }

    private void updateVirtualCodeSql(List<Map<String, Object>> zbDataAllList, DimensionParamsVO queryParamsVO, String tableName, Map<String, String> fieldCode2FieldName) {
        List<String> dimensionNames = this.listDimensionName(queryParamsVO.getTaskId());
        StringBuilder sqlBuilder = new StringBuilder(10);
        sqlBuilder.append(" UPDATE ").append(tableName).append("  set ");
        zbDataAllList.forEach(zbCode2zbValue -> zbCode2zbValue.forEach((zbCode, zbValue) -> {
            if (dimensionNames.contains(zbCode)) {
                return;
            }
            if (zbValue instanceof Number) {
                sqlBuilder.append((String)fieldCode2FieldName.get(zbCode)).append("=").append(zbValue).append(",");
            } else if (zbValue != null) {
                sqlBuilder.append((String)fieldCode2FieldName.get(zbCode)).append("='").append(zbValue).append("',");
            } else {
                sqlBuilder.append((String)fieldCode2FieldName.get(zbCode)).append("=").append((Object)null).append(",");
            }
        }));
        sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
        sqlBuilder.append(" WHERE ");
        StringBuilder whereSql = this.getDimensionWhereSql(queryParamsVO);
        if (whereSql != null && whereSql.length() <= 0) {
            return;
        }
        EntNativeSqlDefaultDao.getInstance().execute(sqlBuilder.append((CharSequence)whereSql).toString(), new Object[]{queryParamsVO.getOrgId()});
    }

    private int getVirtualCodeCount(String tableName, DimensionParamsVO queryParamsVO) {
        StringBuilder sqlBuilder = new StringBuilder(10);
        sqlBuilder.append(" SELECT count(1)").append(" FROM ").append(tableName).append("  WHERE ");
        StringBuilder whereSql = this.getDimensionWhereSql(queryParamsVO);
        if (whereSql != null && whereSql.length() <= 0) {
            return 0;
        }
        return EntNativeSqlDefaultDao.getInstance().count(sqlBuilder.append((CharSequence)whereSql).toString(), new Object[]{queryParamsVO.getOrgId()});
    }

    private List<String> initFieldNameMap(String type) {
        ArrayList<String> fieldNames = new ArrayList<String>();
        fieldNames.add("MDCODE");
        fieldNames.add("MD_GCORGTYPE");
        fieldNames.add("DATATIME");
        if (type.equals("notSimple")) {
            fieldNames.add("BIZKEYORDER");
        }
        fieldNames.add("MD_CURRENCY");
        fieldNames.add("MD_GCADJTYPE");
        return fieldNames;
    }

    private void saveReportData(Map<String, List<Map<String, Object>>> tableKey2zbDataMap) {
        tableKey2zbDataMap.forEach((tableKey, zbDataAllList) -> {
            DataTable dataTable = this.runtimeDataSchemeService.getDataTable(tableKey);
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode(dataTable.getCode());
            Map<String, String> fieldCode2FieldName = this.initFieldName(tableModelDefine);
            zbDataAllList.forEach(zbCode2zbValue -> this.insertVirtualCodeSql((Map<String, Object>)zbCode2zbValue, tableModelDefine.getName(), fieldCode2FieldName));
        });
    }

    private Map<String, String> initFieldName(TableModelDefine tableModelDefine) {
        List columnModelDefineList = this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
        return columnModelDefineList.stream().collect(Collectors.toMap(IModelDefineItem::getCode, ColumnModelDefine::getName, (o1, o2) -> o1));
    }

    private void insertVirtualCodeSql(Map<String, Object> zbCode2zbValue, String tableName, Map<String, String> fieldCode2FieldName) {
        if (CollectionUtils.isEmpty(zbCode2zbValue)) {
            return;
        }
        ArrayList<Object> zbDataList = new ArrayList<Object>();
        ArrayList<String> zbFieldNames = new ArrayList<String>();
        zbCode2zbValue.forEach((code, value) -> {
            zbFieldNames.add((String)code);
            zbDataList.add(value);
        });
        String selectFields = this.getSelectFields(zbFieldNames, fieldCode2FieldName);
        String zbValues = this.getZbValueStr(zbDataList);
        String sql = " INSERT INTO " + tableName + " (" + selectFields + ")  VALUES (" + zbValues + ")";
        EntNativeSqlDefaultDao.getInstance().execute(sql);
    }

    private String getSelectFields(List<String> zbFieldNames, Map<String, String> fieldCode2FieldName) {
        StringBuffer zbFieldNameStr = new StringBuffer();
        zbFieldNames.forEach(fieldName -> zbFieldNameStr.append(fieldCode2FieldName.getOrDefault(fieldName, (String)fieldName)).append(","));
        return zbFieldNameStr.deleteCharAt(zbFieldNameStr.length() - 1).toString();
    }

    private String getZbValueStr(List<Object> zbDataList) {
        StringBuilder zbFieldNameStr = new StringBuilder();
        for (Object value : zbDataList) {
            if (value instanceof Number) {
                zbFieldNameStr.append(value).append(",");
                continue;
            }
            if (value == null) {
                zbFieldNameStr.append((Object)null).append(",");
                continue;
            }
            zbFieldNameStr.append("'").append(value).append("',");
        }
        return zbFieldNameStr.deleteCharAt(zbFieldNameStr.length() - 1).toString();
    }

    private List<Object> getZbDataList(DimensionParamsVO queryParamsVO, String type) {
        ArrayList<Object> zbDatas = new ArrayList<Object>();
        zbDatas.add(queryParamsVO.getOrgId());
        zbDatas.add(queryParamsVO.getOrgType());
        zbDatas.add(queryParamsVO.getPeriodStr());
        if (type.equals("notSimple")) {
            zbDatas.add(UUIDUtils.newUUIDStr());
        }
        zbDatas.add(queryParamsVO.getCurrencyId());
        zbDatas.add(GCAdjTypeEnum.BEFOREADJ.getCode());
        return zbDatas;
    }

    private Map<String, Object> getFieldCodeFieldValueMap(DimensionParamsVO queryParamsVO, String type) {
        HashMap<String, Object> fieldKey2Value = new HashMap<String, Object>();
        fieldKey2Value.put("MDCODE", queryParamsVO.getOrgId());
        fieldKey2Value.put("MD_GCORGTYPE", queryParamsVO.getOrgType());
        fieldKey2Value.put("DATATIME", queryParamsVO.getPeriodStr());
        if (type.equals("notSimple")) {
            fieldKey2Value.put("BIZKEYORDER", UUIDUtils.newUUIDStr());
        }
        fieldKey2Value.put("MD_CURRENCY", queryParamsVO.getCurrencyId());
        if (DimensionUtils.isExisAdjType((String)queryParamsVO.getTaskId())) {
            fieldKey2Value.put("MD_GCADJTYPE", GCAdjTypeEnum.BEFOREADJ.getCode());
        }
        if (!StringUtils.isEmpty((String)queryParamsVO.getSelectAdjustCode())) {
            fieldKey2Value.put("ADJUST", queryParamsVO.getSelectAdjustCode());
        }
        return fieldKey2Value;
    }

    private void deleteVirtualCodeSql(Map<String, List<Map<String, Object>>> tableKey2zbDataMap, DimensionParamsVO queryParamsVO) {
        tableKey2zbDataMap.forEach((tableKey, value) -> {
            DataTable dataTable = this.runtimeDataSchemeService.getDataTable(tableKey);
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode(dataTable.getCode());
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append(" DELETE  FROM ").append(tableModelDefine.getName()).append("  WHERE ");
            StringBuilder whereSql = this.getDimensionWhereSql(queryParamsVO);
            if (whereSql != null && whereSql.length() <= 0) {
                return;
            }
            EntNativeSqlDefaultDao.getInstance().execute(sqlBuilder.append((CharSequence)whereSql).toString(), new Object[]{queryParamsVO.getOrgId()});
        });
    }

    private StringBuilder getDimensionWhereSql(DimensionParamsVO queryParamsVO) {
        List<String> dimensionNames = this.listDimensionName(queryParamsVO.getTaskId());
        if (dimensionNames.isEmpty()) {
            return null;
        }
        StringBuilder sqlBuilder = new StringBuilder(10);
        for (String dimensionName : dimensionNames) {
            if ("MDCODE".equals(dimensionName)) {
                sqlBuilder.append(" ").append(dimensionName).append("=?");
                continue;
            }
            if ("MD_CURRENCY".equals(dimensionName)) {
                sqlBuilder.append(" AND ").append(dimensionName).append("='").append(queryParamsVO.getCurrencyId()).append("' ");
                continue;
            }
            if ("MD_GCORGTYPE".equals(dimensionName)) {
                sqlBuilder.append(" AND ").append(dimensionName).append(" in ('").append(queryParamsVO.getOrgType()).append("','").append(GCOrgTypeEnum.NONE.getCode()).append("')");
                continue;
            }
            if ("DATATIME".equals(dimensionName)) {
                sqlBuilder.append(" AND ").append(dimensionName).append("='").append(queryParamsVO.getPeriodStr()).append("'");
                continue;
            }
            if (!"ADJUST".equals(dimensionName)) continue;
            sqlBuilder.append(" AND ").append("ADJUST").append("='").append(queryParamsVO.getSelectAdjustCode()).append("'");
        }
        return sqlBuilder;
    }

    private List<String> listDimensionName(String taskId) {
        ArrayList<String> dimensionNames = new ArrayList<String>();
        dimensionNames.add("MDCODE");
        dimensionNames.add("MD_GCORGTYPE");
        dimensionNames.add("DATATIME");
        dimensionNames.add("MD_CURRENCY");
        if (DimensionUtils.isExisAdjType((String)taskId)) {
            dimensionNames.add("MD_GCADJTYPE");
        }
        if (DimensionUtils.isExistAdjust((String)taskId)) {
            dimensionNames.add("ADJUST");
        }
        return dimensionNames;
    }

    private DimensionParamsVO initDimensionParamsVO(DimensionValueSet ds, ReportFmlExecEnvironment env) {
        String orgId = "";
        try {
            DimensionParamsVO queryParamsVO = new DimensionParamsVO();
            if (ds.getValue("MD_CURRENCY") != null) {
                queryParamsVO.setCurrency(String.valueOf(ds.getValue("MD_CURRENCY")));
                queryParamsVO.setCurrencyId(String.valueOf(ds.getValue("MD_CURRENCY")));
            }
            if (ds.getValue("DATATIME") != null) {
                queryParamsVO.setPeriodStr(String.valueOf(ds.getValue("DATATIME")));
            }
            if (ds.getValue("MD_ORG") != null) {
                queryParamsVO.setOrgId(String.valueOf(ds.getValue("MD_ORG")));
                orgId = String.valueOf(ds.getValue("MD_ORG"));
            }
            queryParamsVO.setSchemeId(env.getFormSchemeKey());
            if (ds.getValue("MD_GCORGTYPE") != null) {
                queryParamsVO.setOrgType(String.valueOf(ds.getValue("MD_GCORGTYPE")));
                queryParamsVO.setOrgTypeId(String.valueOf(ds.getValue("MD_GCORGTYPE")));
            }
            queryParamsVO.setTaskId(env.getTaskDefine().getKey());
            if (ds.getValue("ADJUST") != null) {
                queryParamsVO.setSelectAdjustCode(String.valueOf(ds.getValue("ADJUST")));
            }
            return queryParamsVO;
        }
        catch (Exception e) {
            logger.error("\u5355\u4f4d\u3010" + orgId + "\u3011\u83b7\u53d6\u72b6\u6001\u5931\u8d25", e);
            return null;
        }
    }

    private boolean unitWriteable(DimensionParamsVO queryParamsVO) {
        return UploadStateTool.getInstance().writeable(queryParamsVO).getAble();
    }

    private boolean formWriteable(DimensionParamsVO queryParamsVO, String formId) {
        boolean isFormLocked = FormUploadStateTool.getInstance().isFormLocked(queryParamsVO, queryParamsVO.getOrgId(), formId);
        if (isFormLocked) {
            FormDefine formDefine = this.iRunTimeViewController.queryFormById(formId);
            logger.error("\u62a5\u8868\u3010" + formDefine.getTitle() + "\u3011\u5df2\u9501\u5b9a\uff0c\u4e0d\u5141\u8bb8\u6267\u884c\u64cd\u4f5c");
            return false;
        }
        return true;
    }

    private JtableContext convert2JtableContext(DimensionValueSet ds, String taskKey, String formSchemeKey) {
        Map<String, DimensionValue> dimensionSet = this.getDimensionSet(ds, formSchemeKey);
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(dimensionSet);
        jtableContext.setFormSchemeKey(formSchemeKey);
        jtableContext.setTaskKey(taskKey);
        return jtableContext;
    }

    private Map<String, DimensionValue> getDimensionSet(DimensionValueSet dimensionSet, String formSchemeKey) {
        HashMap<String, DimensionValue> dimensionSetMap = new HashMap<String, DimensionValue>();
        DimensionValue dimensionValue = new DimensionValue();
        dimensionValue.setName("DATATIME");
        dimensionValue.setValue(String.valueOf(dimensionSet.getValue("DATATIME")));
        dimensionSetMap.put("DATATIME", dimensionValue);
        dimensionValue = new DimensionValue();
        dimensionValue.setName("MD_ORG");
        dimensionValue.setValue(String.valueOf(dimensionSet.getValue("MD_ORG")));
        dimensionSetMap.put("MD_ORG", dimensionValue);
        if (dimensionSet.getValue("MD_CURRENCY") != null) {
            dimensionValue = new DimensionValue();
            dimensionValue.setName("MD_CURRENCY");
            dimensionValue.setValue(String.valueOf(dimensionSet.getValue("MD_CURRENCY")));
            dimensionSetMap.put("MD_CURRENCY", dimensionValue);
        }
        if (dimensionSet.getValue("MD_GCORGTYPE") != null) {
            dimensionValue = new DimensionValue();
            dimensionValue.setName("MD_GCORGTYPE");
            dimensionValue.setValue(String.valueOf(dimensionSet.getValue("MD_GCORGTYPE")));
            dimensionSetMap.put("MD_GCORGTYPE", dimensionValue);
        }
        return dimensionSetMap;
    }

    private Map<String, List<FieldDefine>> getFieldDefineList(String formSchemeKey, DimensionParamsVO queryParamsVO, DimensionValueSet ds) {
        List<FormDefine> formDefineList = this.getPowerFormForScheme(formSchemeKey, queryParamsVO, ds);
        List formIdList = formDefineList.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        List uploadStateList = FormUploadStateTool.getInstance().queryUploadState(queryParamsVO, String.valueOf(ds.getValue("MD_ORG")), formIdList);
        ArrayList fieldDefineSimpleList = new ArrayList();
        ArrayList fieldDefineNoSimpleList = new ArrayList();
        formDefineList.forEach(formDefine -> {
            try {
                if (formDefine.getTitle().contains("\u5c01\u9762\u4ee3\u7801")) {
                    return;
                }
                boolean canWriteForm = this.authoritryProvider.canWriteForm(formDefine.getKey());
                if (!canWriteForm) {
                    logger.error("\u62a5\u8868\u3010" + formDefine.getTitle() + "\u3011\u65e0\u53ef\u5199\u6743\u9650\uff0c\u4e0d\u5141\u8bb8\u6267\u884c\u64cd\u4f5c");
                    return;
                }
                if (!this.formWriteable(queryParamsVO, formDefine.getKey()) || !this.writeableStatus((UploadState)uploadStateList.get(formIdList.indexOf(formDefine.getKey())), formDefine.getTitle())) {
                    return;
                }
                List dataRegionDefineList = this.iRunTimeViewController.getAllRegionsInForm(formDefine.getKey());
                dataRegionDefineList.forEach(dataRegionDefine -> {
                    List fieldIdList = this.iRunTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
                    List list = fieldIdList.stream().map(fieldId -> {
                        try {
                            return this.iDataDefinitionRuntimeController.queryFieldDefine(fieldId);
                        }
                        catch (Exception e) {
                            logger.error("\u540c\u63a7\u6570\u636e\u63d0\u53d6\u516c\u5f0f\u83b7\u53d6\u6307\u6807\u3010" + fieldId + "\u3011\u9519\u8bef", e);
                            return null;
                        }
                    }).collect(Collectors.toList());
                    if (DataRegionKind.DATA_REGION_SIMPLE.equals((Object)dataRegionDefine.getRegionKind())) {
                        fieldDefineSimpleList.addAll(list.stream().filter(Objects::nonNull).collect(Collectors.toList()));
                    } else {
                        fieldDefineNoSimpleList.addAll(list.stream().filter(Objects::nonNull).collect(Collectors.toList()));
                    }
                });
            }
            catch (Exception e) {
                logger.error("\u540c\u63a7\u6570\u636e\u63d0\u53d6\u516c\u5f0f\u83b7\u53d6\u62a5\u8868\u3010" + formDefine.getFormCode() + "\u3011\u5931\u8d25", e);
            }
        });
        HashMap<String, List<FieldDefine>> type2FieldList = new HashMap<String, List<FieldDefine>>();
        type2FieldList.put("simple", fieldDefineSimpleList);
        type2FieldList.put("notSimple", fieldDefineNoSimpleList);
        return type2FieldList;
    }

    private Boolean isZbValueN(int typeValue) {
        return FieldType.FIELD_TYPE_FLOAT.getValue() == typeValue || FieldType.FIELD_TYPE_INTEGER.getValue() == typeValue || FieldType.FIELD_TYPE_DECIMAL.getValue() == typeValue;
    }

    private List<GcOrgCacheVO> getOrgByCodeList(DimensionValueSet ds, ReportFmlExecEnvironment env) {
        String periodWrapper = String.valueOf(ds.getValue("DATATIME"));
        String orgVersionType = String.valueOf(ds.getValue("MD_GCORGTYPE"));
        YearPeriodObject yp = new YearPeriodObject(env.getFormSchemeKey(), periodWrapper);
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgVersionType, (GcAuthorityType)GcAuthorityType.WRITE, (YearPeriodObject)yp);
        List orgTree = instance.getOrgTree();
        ArrayList<GcOrgCacheVO> orgList = new ArrayList<GcOrgCacheVO>();
        orgTree.forEach(org -> orgList.addAll(instance.listAllOrgByParentIdContainsSelf(org.getId())));
        return orgList;
    }

    private FieldDefine getFieldDefine(IASTNode iAstNode) throws Exception {
        DynamicDataNode sourceZb = null;
        if (iAstNode.getClass() == CellDataNode.class) {
            sourceZb = (DynamicDataNode)iAstNode.getChild(0);
        } else if (iAstNode.getClass() == DynamicDataNode.class) {
            sourceZb = (DynamicDataNode)iAstNode;
        }
        if (sourceZb == null) {
            logger.error("\u540c\u63a7\u6570\u636e\u63d0\u53d6\u8fd0\u7b97\u5931\u8d25");
            throw new Exception("\u540c\u63a7\u6570\u636e\u63d0\u53d6\u8fd0\u7b97\u5931\u8d25");
        }
        QueryField queryField = sourceZb.getQueryField();
        String tableName = queryField.getTableName();
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByName(tableName);
        DataTable dataTable = this.runtimeDataSchemeService.getDataTableByCode(tableModelDefine.getCode());
        return this.iDataDefinitionRuntimeController.queryFieldByCodeInTable(queryField.getFieldCode(), dataTable.getKey());
    }

    private IDataTable getIDataTable(DimensionValueSet ds, FieldDefine fieldDefine) throws Exception {
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery();
        dataQuery.addColumn(fieldDefine);
        dataQuery.setMasterKeys(ds);
        ExecutorContext context = new ExecutorContext(this.iDataDefinitionRuntimeController);
        context.setUseDnaSql(false);
        return dataQuery.executeQuery(context);
    }

    private IDataTable getIDataTableList(DimensionValueSet ds, List<FieldDefine> fieldDefineList) throws Exception {
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery();
        fieldDefineList.forEach(fieldDefine -> {
            if (fieldDefine != null) {
                dataQuery.addColumn(fieldDefine);
            }
        });
        dataQuery.setMasterKeys(ds);
        ExecutorContext context = new ExecutorContext(this.iDataDefinitionRuntimeController);
        context.setUseDnaSql(false);
        return dataQuery.executeQuery(context);
    }

    public String toDescription() {
        return "\u63d0\u53d6\u540c\u63a7\u76f8\u5173\u6570\u636e\u5230\u5f53\u524d\u5355\u4f4d\u3002\n\u53c2\u6570\uff1a\n\u53c2\u6570\u4e00\uff1a\u201c\u5bf9\u5e94\u540c\u63a7\u53d8\u52a8\u5355\u4f4d\u201d\u6307\u6807\u4ee3\u7801\n\u53c2\u6570\u4e8c\uff1a\u201c\u540c\u63a7\u5212\u51fa\u65e5\u671f\u201d\u6307\u6807\u4ee3\u7801\n\u53c2\u6570\u4e09\uff1a\u201c\u540c\u63a7\u865a\u62df\u5355\u4f4d\u7c7b\u578b\u201d\u6307\u6807\u4ee3\u7801\n\n\u8fd4\u56de\u503c\uff1a\u65e0";
    }
}

