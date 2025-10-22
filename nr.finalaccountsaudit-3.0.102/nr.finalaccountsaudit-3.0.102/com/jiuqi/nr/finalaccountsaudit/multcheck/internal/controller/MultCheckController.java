/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.internal.LinkedTreeMap
 *  com.google.gson.reflect.TypeToken
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil
 *  com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.asynctask.BatchCheckAsyncTaskExecutor
 *  com.jiuqi.nr.dataentry.asynctask.NodeCheckAsyncTaskExecutor
 *  com.jiuqi.nr.dataentry.bean.BatchCheckParam
 *  com.jiuqi.nr.dataentry.bean.NodeCheckInfo
 *  com.jiuqi.nr.dataentry.bean.ReviewInfoParam
 *  com.jiuqi.nr.dataentry.internal.service.FormGroupProvider
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCheckResultGroupInfo
 *  com.jiuqi.nr.dataentry.paramInfo.BatchDimensionValueFormInfo
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.dataentry.service.IReviewInfoService
 *  com.jiuqi.nr.dataentry.util.Consts$FormAccessLevel
 *  com.jiuqi.nr.definition.common.PeriodMatchingType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definitionext.taskExtConfig.controller.ITaskExtConfigController
 *  com.jiuqi.nr.definitionext.taskExtConfig.model.ExtensionBasicModel
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  com.jiuqi.nr.query.dao.IQueryModalDefineDao
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 *  com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  javax.annotation.Resource
 *  nr.single.data.bean.CheckParam
 *  org.docx4j.com.google.common.collect.Maps
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.internal.controller;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.asynctask.BatchCheckAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.NodeCheckAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.bean.BatchCheckParam;
import com.jiuqi.nr.dataentry.bean.NodeCheckInfo;
import com.jiuqi.nr.dataentry.bean.ReviewInfoParam;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckResultGroupInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchDimensionValueFormInfo;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.dataentry.service.IReviewInfoService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.common.PeriodMatchingType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definitionext.taskExtConfig.controller.ITaskExtConfigController;
import com.jiuqi.nr.definitionext.taskExtConfig.model.ExtensionBasicModel;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.asynctask.BlobFileSizeCheckAsyncTaskExecutor;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFieldStruct;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFileSizeCheckParam;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFormStruct;
import com.jiuqi.nr.finalaccountsaudit.common.DataQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.dataanalysischeck.asynctask.DataAnalysisCheckAsyncTaskExecutor;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.asynctask.EntityCheckAsyncTaskExecutor;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.entitytreecheck.asynctask.EntityTreeCheckAsyncTaskExecutor;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckResultInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.internal.service.EnumDataCheckAsyncTaskExecutor;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.service.IEnumDataCheckService;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.asynctask.ExplainInfoCheckAsyncTaskExecutor;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.ExplainInfoCheckParam;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.asynctask.IntegrityCheckAsyncTaskExecutor;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.AnalysisResultInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.AsyncCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.EntityCheckPeriodTransform;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.EntityShortInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.IntegrityCheckParams;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.JsonGetUtil;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.LastCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.MultCheckItem;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.MultCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.OneKeyCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.IMultCheckItemBase;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.MultCheckConfigBean;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.MultCheckEnum;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.MultCheckItemImpl;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.entity.MultCheckTableBean;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.service.IMultCheckTableService;
import com.jiuqi.nr.finalaccountsaudit.multcheck.controller.IActionHandler;
import com.jiuqi.nr.finalaccountsaudit.multcheck.controller.IMultCheckController;
import com.jiuqi.nr.finalaccountsaudit.multcheck.dao.FormSchemeDao;
import com.jiuqi.nr.finalaccountsaudit.multcheck.dao.MultCheckDao;
import com.jiuqi.nr.finalaccountsaudit.onekeycheck.controller.IOneKeyCheckController;
import com.jiuqi.nr.finalaccountsaudit.querycheck.asynctask.QueryCheckAsyncTaskExecutor;
import com.jiuqi.nr.finalaccountsaudit.querycheck.bean.QueryParamInfo;
import com.jiuqi.nr.finalaccountsaudit.zbquerycheck.asynctask.ZBQueryCheckAsyncTaskExecutor;
import com.jiuqi.nr.finalaccountsaudit.zbquerycheck.bean.ZBQueryParamInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.JsonUtil;
import com.jiuqi.nr.query.dao.IQueryModalDefineDao;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService;
import com.jiuqi.util.StringUtils;
import com.jiuqi.va.domain.common.JSONUtil;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import nr.single.data.bean.CheckParam;
import org.docx4j.com.google.common.collect.Maps;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MultCheckController
implements IMultCheckController,
IActionHandler {
    private static final Logger logger = LoggerFactory.getLogger(MultCheckController.class);
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    IDesignTimeViewController nrDesignController;
    @Autowired
    private IFormulaRunTimeController runTimeFormulaController;
    @Autowired
    IDataDefinitionDesignTimeController npDesignController;
    @Resource
    AsyncTaskManager asyncTaskManager;
    @Resource
    private JdbcTemplate jdbcTpl;
    @Autowired
    private ITaskExtConfigController TaskExtConfigController;
    @Autowired
    IEnumDataCheckService enumDataCheckService;
    @Autowired
    MultCheckDao multCheckDao;
    @Autowired
    CommonUtil commonUtil;
    @Autowired
    DimensionUtil dimensionUtil;
    @Resource
    IRunTimeViewController viewCtrl;
    @Autowired
    FormGroupProvider formGroupProvider;
    @Resource
    IQueryModalDefineDao queryModalDefineDao;
    @Autowired
    DataDefinitionRuntimeController2 dataDefinitionRuntimeController;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    IJtableParamService jtableParamService;
    @Autowired
    IOneKeyCheckController oneKeyCheckController;
    @Autowired
    private EntityQueryHelper entityQueryHelper;
    @Autowired
    DataQueryHelper dataQueryHelper;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IFuncExecuteService funcExecuteService;
    @Autowired
    private IMultCheckTableService iMultCheckTableService;
    @Resource
    ResourceTreeNodeService resourceTreeNodeService;
    @Autowired
    private FormSchemeDao formSchemeDao;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Resource
    IJtableEntityService jtableEntityService;
    @Autowired
    IReviewInfoService reviewInfoService;
    private static final String REALTIME_TASK_PARAMSKEY_ARGS = "NR_ARGS";

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<MultCheckItem> queryAllItemList(OneKeyCheckInfo oneKeyCheckInfo) throws Exception {
        List<Map<String, Object>> checkItemList;
        ExtensionBasicModel basicCheckItems;
        MultCheckConfigBean multCheckConfigBean;
        if (StringUtils.isEmpty((String)oneKeyCheckInfo.getContext().getFormSchemeKey())) {
            String formSchemeKey = this.formSchemeDao.getFormSchemeByTaskAndPeriod(((DimensionValue)oneKeyCheckInfo.getContext().getDimensionSet().get("DATATIME")).getValue(), oneKeyCheckInfo.getContext().getTaskKey());
            oneKeyCheckInfo.getContext().setFormSchemeKey(formSchemeKey);
        }
        if ((multCheckConfigBean = (MultCheckConfigBean)(basicCheckItems = this.TaskExtConfigController.getTaskExtConfigDefineBySchemakey(oneKeyCheckInfo.getContext().getTaskKey(), oneKeyCheckInfo.getContext().getFormSchemeKey(), "taskextension-multcheck")).getExtInfoModel()) == null || !multCheckConfigBean.getMultCheckEnable()) return this.getDefaultCheckItems(oneKeyCheckInfo.getContext());
        if (oneKeyCheckInfo.getBeforeUpload() != null && oneKeyCheckInfo.getBeforeUpload().booleanValue()) {
            if (oneKeyCheckInfo.getCheckSchemeKey().equals("defaultScheme")) {
                return this.getDefaultCheckItems(oneKeyCheckInfo.getContext());
            }
            checkItemList = this.multCheckDao.getCheckItemList(oneKeyCheckInfo.getCheckSchemeKey());
        } else if (oneKeyCheckInfo.getSingleApp()) {
            if (oneKeyCheckInfo.getCheckSchemeKey().equals("defaultScheme")) return this.getDefaultCheckItems(oneKeyCheckInfo.getContext());
            checkItemList = this.multCheckDao.getCheckItemList(oneKeyCheckInfo.getCheckSchemeKey());
        } else {
            String mainDimName = this.dimensionUtil.getDwMainDimName(oneKeyCheckInfo.getContext().getFormSchemeKey());
            String currentUnit = ((DimensionValue)oneKeyCheckInfo.getContext().getDimensionSet().get(mainDimName)).getValue();
            HashMap schemeMap = multCheckConfigBean.getMultCheckMap();
            if (schemeMap == null) {
                return this.getDefaultCheckItems(oneKeyCheckInfo.getContext());
            }
            Object sKey = schemeMap.get(currentUnit);
            if (sKey == null) return this.getDefaultCheckItems(oneKeyCheckInfo.getContext());
            checkItemList = this.multCheckDao.getCheckItemList(sKey.toString());
        }
        ArrayList<MultCheckItem> returnCheckItemList = new ArrayList<MultCheckItem>();
        for (int j = 0; j < checkItemList.size(); ++j) {
            MultCheckItem multCheckItem = new MultCheckItem();
            Map<String, Object> itemMap = checkItemList.get(j);
            String multCheckType = itemMap.get("multCheckType").toString();
            if (multCheckType.equalsIgnoreCase(MultCheckEnum.ENTITY_CHECK.getKey())) {
                String period = "";
                Gson gson = new Gson();
                String auditScopeStr = gson.toJson(itemMap.get("auditScope"));
                MultCheckTableBean.AuditScopeEntityCheck auditScopeEntityCheck = (MultCheckTableBean.AuditScopeEntityCheck)gson.fromJson(auditScopeStr, new TypeToken<MultCheckTableBean.AuditScopeEntityCheck>(){}.getType());
                String associationStr = gson.toJson((Object)auditScopeEntityCheck.getAssociation());
                MultCheckTableBean.Association association = (MultCheckTableBean.Association)gson.fromJson(associationStr, new TypeToken<MultCheckTableBean.Association>(){}.getType());
                String dimensionValueStr = gson.toJson((Object)auditScopeEntityCheck.getDatatime());
                DimensionValue dimensionValue = (DimensionValue)gson.fromJson(dimensionValueStr, new TypeToken<DimensionValue>(){}.getType());
                multCheckItem.setItemSetting(itemMap.get("auditScope"));
            } else {
                multCheckItem.setItemSetting(itemMap.get("auditScope"));
            }
            multCheckItem.setId(itemMap.get("key").toString());
            multCheckItem.setKey(itemMap.get("multCheckKey").toString());
            multCheckItem.setName(itemMap.get("multCheckName").toString());
            multCheckItem.setCheckType(itemMap.get("multCheckType").toString());
            multCheckItem.setIndex((int)Double.parseDouble(itemMap.get("index").toString()));
            returnCheckItemList.add(multCheckItem);
        }
        return returnCheckItemList;
    }

    private List<MultCheckItem> getDefaultCheckItems(JtableContext context) {
        List<MultCheckItem> multCheckItemList = new ArrayList<MultCheckItem>();
        List formulaSchemes = this.runTimeFormulaController.getAllRPTFormulaSchemeDefinesByFormScheme(context.getFormSchemeKey());
        MultCheckItem enumCheckItem = null;
        MultCheckItem gatherCheckItem = null;
        MultCheckItem integrityCheckItem = null;
        MultCheckItem entityCheckItem = null;
        MultCheckItem ccsmCheckItem = null;
        MultCheckItem fbwdCheckItem = null;
        MultCheckItem sxjcCheckItem = null;
        MultCheckTableBean.AuditScopeForm auditScopeForm = new MultCheckTableBean.AuditScopeForm();
        HashSet<MultCheckTableBean.FormInfo> formInfo = new HashSet<MultCheckTableBean.FormInfo>();
        auditScopeForm.setFormList(formInfo);
        MultCheckTableBean.AuditScopeFormula auditScopeFormula = new MultCheckTableBean.AuditScopeFormula();
        HashMap<String, List<String>> formulasMap = new HashMap<String, List<String>>();
        ArrayList<String> errDesSetting = new ArrayList<String>();
        auditScopeFormula.setFormulaMap(formulasMap);
        for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemes) {
            if (!formulaSchemeDefine.isDefault()) continue;
            MultCheckItem multCheckItem = new MultCheckItem();
            multCheckItem.setId(formulaSchemeDefine.getKey() + "");
            multCheckItem.setName(formulaSchemeDefine.getTitle());
            multCheckItem.setActionName("\u5ba1\u6838");
            multCheckItem.setKey(formulaSchemeDefine.getKey());
            multCheckItem.setCheckType(MultCheckEnum.GSSH.getKey());
            multCheckItem.setItemSetting(auditScopeFormula);
            multCheckItem.setIndex(0);
            multCheckItemList.add(multCheckItem);
            errDesSetting.add(formulaSchemeDefine.getKey());
        }
        gatherCheckItem = new MultCheckItem();
        gatherCheckItem.setId(MultCheckEnum.NODE_CHECK.getKey());
        gatherCheckItem.setName(MultCheckEnum.NODE_CHECK.getDesc());
        gatherCheckItem.setActionName("\u68c0\u67e5");
        gatherCheckItem.setCheckType(MultCheckEnum.NODE_CHECK.getKey());
        gatherCheckItem.setItemSetting(auditScopeForm);
        gatherCheckItem.setIndex(2);
        multCheckItemList.add(gatherCheckItem);
        integrityCheckItem = new MultCheckItem();
        integrityCheckItem.setId(MultCheckEnum.INTEGRITY_FORM.getKey());
        integrityCheckItem.setName(MultCheckEnum.INTEGRITY_FORM.getDesc());
        integrityCheckItem.setActionName("\u68c0\u67e5");
        integrityCheckItem.setCheckType(MultCheckEnum.INTEGRITY_FORM.getKey());
        integrityCheckItem.setItemSetting(auditScopeForm);
        integrityCheckItem.setIndex(6);
        multCheckItemList.add(integrityCheckItem);
        entityCheckItem = new MultCheckItem();
        MultCheckTableBean.AuditScopeEntityCheck auditScopeEntityCheck = new MultCheckTableBean.AuditScopeEntityCheck();
        auditScopeEntityCheck.setTaskTitle(this.runtimeViewController.queryTaskDefine(context.getTaskKey()).getTitle());
        auditScopeEntityCheck.setTaskKey(context.getTaskKey());
        auditScopeEntityCheck.setDatatime((DimensionValue)context.getDimensionSet().get("DATATIME"));
        auditScopeEntityCheck.setFormSchemeKey(context.getFormSchemeKey());
        MultCheckTableBean.Association association = new MultCheckTableBean.Association();
        association.setConfiguration(1);
        auditScopeEntityCheck.setAssociation(association);
        entityCheckItem.setId(MultCheckEnum.ENTITY_CHECK.getKey());
        entityCheckItem.setName(MultCheckEnum.ENTITY_CHECK.getDesc());
        entityCheckItem.setActionName("\u6838\u5bf9");
        entityCheckItem.setCheckType(MultCheckEnum.ENTITY_CHECK.getKey());
        entityCheckItem.setItemSetting(auditScopeEntityCheck);
        entityCheckItem.setIndex(7);
        multCheckItemList.add(entityCheckItem);
        MultCheckTableBean.AuditScopeEnum auditScopeEnum = new MultCheckTableBean.AuditScopeEnum();
        HashSet<String> enumInfo = new HashSet<String>();
        auditScopeEnum.setEnumList(enumInfo);
        enumCheckItem = new MultCheckItem();
        enumCheckItem.setId(MultCheckEnum.ENUM_CHECK.getKey());
        enumCheckItem.setName(MultCheckEnum.ENUM_CHECK.getDesc());
        enumCheckItem.setActionName("\u68c0\u67e5");
        enumCheckItem.setCheckType(MultCheckEnum.ENUM_CHECK.getKey());
        enumCheckItem.setItemSetting(auditScopeEnum);
        enumCheckItem.setIndex(3);
        multCheckItemList.add(enumCheckItem);
        MultCheckTableBean.AuditScopeErrorDesc auditScopeErrorDesc = new MultCheckTableBean.AuditScopeErrorDesc();
        auditScopeErrorDesc.setFormulaSolution(errDesSetting);
        auditScopeErrorDesc.setImpactReport(true);
        ccsmCheckItem = new MultCheckItem();
        ccsmCheckItem.setId(MultCheckEnum.ERROR_DESC_CHECK.getKey());
        ccsmCheckItem.setName(MultCheckEnum.ERROR_DESC_CHECK.getDesc());
        ccsmCheckItem.setActionName("\u68c0\u67e5");
        ccsmCheckItem.setCheckType(MultCheckEnum.ERROR_DESC_CHECK.getKey());
        ccsmCheckItem.setItemSetting(auditScopeErrorDesc);
        ccsmCheckItem.setIndex(4);
        multCheckItemList.add(ccsmCheckItem);
        MultCheckTableBean.AuditScopeAttachment auditScopeAttachment = new MultCheckTableBean.AuditScopeAttachment();
        HashSet<MultCheckTableBean.ZBInfo> zbList = new HashSet<MultCheckTableBean.ZBInfo>();
        auditScopeAttachment.setZbList(zbList);
        fbwdCheckItem = new MultCheckItem();
        fbwdCheckItem.setId(MultCheckEnum.ATTACHMENT_CHECK.getKey());
        fbwdCheckItem.setName(MultCheckEnum.ATTACHMENT_CHECK.getDesc());
        fbwdCheckItem.setActionName("\u68c0\u67e5");
        fbwdCheckItem.setIndex(5);
        fbwdCheckItem.setCheckType(MultCheckEnum.ATTACHMENT_CHECK.getKey());
        sxjcCheckItem = new MultCheckItem();
        sxjcCheckItem.setId(MultCheckEnum.ENTITY_TREE_CHECK.getKey());
        sxjcCheckItem.setName(MultCheckEnum.ENTITY_TREE_CHECK.getDesc());
        sxjcCheckItem.setActionName("\u68c0\u67e5");
        sxjcCheckItem.setCheckType(MultCheckEnum.ENTITY_TREE_CHECK.getKey());
        sxjcCheckItem.setIndex(8);
        multCheckItemList.add(sxjcCheckItem);
        fbwdCheckItem.setItemSetting(auditScopeAttachment);
        multCheckItemList.add(fbwdCheckItem);
        multCheckItemList = this.getMultListByIndex(multCheckItemList);
        return multCheckItemList;
    }

    public List<MultCheckItem> getMultListByIndex(List<MultCheckItem> multCheckItemList) {
        Collections.sort(multCheckItemList, new Comparator<MultCheckItem>(){

            @Override
            public int compare(MultCheckItem o1, MultCheckItem o2) {
                if (o1.getIndex() > o2.getIndex()) {
                    return 1;
                }
                if (o1.getIndex() == o2.getIndex()) {
                    return 0;
                }
                return -1;
            }
        });
        return multCheckItemList;
    }

    @Override
    public boolean oneKeyCheck(OneKeyCheckInfo oneKeyCheckInfo, AsyncTaskMonitor asyncTaskMonitor, JobContext jobContext) throws Exception {
        ExtensionBasicModel basicCheckItems;
        MultCheckConfigBean multCheckConfigBean;
        List<MultCheckItem> checkItems = oneKeyCheckInfo.getCheckItems();
        Gson gson = new Gson();
        OneKeyCheckInfo oneKeyCheck_shjl = (OneKeyCheckInfo)gson.fromJson(gson.toJson((Object)oneKeyCheckInfo), OneKeyCheckInfo.class);
        HashMap<String, MultCheckItem> asyncTaskMap = new HashMap<String, MultCheckItem>();
        HashMap<MultCheckItem, String> itemAsynIdMap = new HashMap<MultCheckItem, String>();
        ArrayList<String> taskIds = new ArrayList<String>();
        asyncTaskMonitor.progressAndMessage(0.01, "start");
        String asyncTaskId = "";
        AsyncCheckInfo asyncCheckInfo = null;
        block26: for (int i = 0; i < checkItems.size(); ++i) {
            MultCheckItem checkItem = checkItems.get(i);
            switch (checkItem.getCheckType()) {
                case "enumCheck": {
                    asyncCheckInfo = this.enumCheck(oneKeyCheckInfo, checkItem, jobContext);
                    asyncTaskId = asyncCheckInfo.getAsyncTaskId();
                    checkItem.setCheckParams(asyncCheckInfo.getCheckParams());
                    asyncTaskMap.put(asyncTaskId, checkItem);
                    itemAsynIdMap.put(checkItem, asyncTaskId);
                    taskIds.add(asyncTaskId);
                    continue block26;
                }
                case "integrityForm": {
                    asyncCheckInfo = this.integrityFormCheck(oneKeyCheckInfo, checkItem, jobContext);
                    asyncTaskId = asyncCheckInfo.getAsyncTaskId();
                    checkItem.setCheckParams(asyncCheckInfo.getCheckParams());
                    asyncTaskMap.put(asyncTaskId, checkItem);
                    itemAsynIdMap.put(checkItem, asyncTaskId);
                    taskIds.add(asyncTaskId);
                    continue block26;
                }
                case "nodeCheck": {
                    asyncCheckInfo = this.nodeCheck(oneKeyCheckInfo, checkItem, jobContext);
                    asyncTaskId = asyncCheckInfo.getAsyncTaskId();
                    checkItem.setCheckParams(asyncCheckInfo.getCheckParams());
                    asyncTaskMap.put(asyncTaskId, checkItem);
                    itemAsynIdMap.put(checkItem, asyncTaskId);
                    taskIds.add(asyncTaskId);
                    continue block26;
                }
                case "entityCheck": {
                    asyncCheckInfo = this.entityCheck(oneKeyCheckInfo, checkItem, jobContext);
                    asyncTaskId = asyncCheckInfo.getAsyncTaskId();
                    checkItem.setCheckParams(asyncCheckInfo.getCheckParams());
                    asyncTaskMap.put(asyncTaskId, checkItem);
                    itemAsynIdMap.put(checkItem, asyncTaskId);
                    taskIds.add(asyncTaskId);
                    continue block26;
                }
                case "errorDescCheck": {
                    asyncCheckInfo = this.errorDescCheck(oneKeyCheckInfo, checkItem, jobContext);
                    asyncTaskId = asyncCheckInfo.getAsyncTaskId();
                    checkItem.setCheckParams(asyncCheckInfo.getCheckParams());
                    asyncTaskMap.put(asyncTaskId, checkItem);
                    itemAsynIdMap.put(checkItem, asyncTaskId);
                    taskIds.add(asyncTaskId);
                    continue block26;
                }
                case "attachmentCheck": {
                    asyncCheckInfo = this.attachmentCheck(oneKeyCheckInfo, checkItem, jobContext);
                    asyncTaskId = asyncCheckInfo.getAsyncTaskId();
                    checkItem.setCheckParams(asyncCheckInfo.getCheckParams());
                    asyncTaskMap.put(asyncTaskId, checkItem);
                    itemAsynIdMap.put(checkItem, asyncTaskId);
                    taskIds.add(asyncTaskId);
                    continue block26;
                }
                case "queryTemplate": {
                    asyncCheckInfo = this.queryCheck(oneKeyCheckInfo, checkItem);
                    asyncTaskId = asyncCheckInfo.getAsyncTaskId();
                    checkItem.setCheckParams(asyncCheckInfo.getCheckParams());
                    asyncTaskMap.put(asyncTaskId, checkItem);
                    itemAsynIdMap.put(checkItem, asyncTaskId);
                    taskIds.add(asyncTaskId);
                    continue block26;
                }
                case "entityTreeCheck": {
                    asyncCheckInfo = this.entityTreeCheck(oneKeyCheckInfo, checkItem, jobContext);
                    asyncTaskId = asyncCheckInfo.getAsyncTaskId();
                    checkItem.setCheckParams(asyncCheckInfo.getCheckParams());
                    asyncTaskMap.put(asyncTaskId, checkItem);
                    itemAsynIdMap.put(checkItem, asyncTaskId);
                    taskIds.add(asyncTaskId);
                    continue block26;
                }
                case "zbQueryTemplate": {
                    asyncCheckInfo = this.zbQueryCheck(oneKeyCheckInfo, checkItem, jobContext);
                    asyncTaskId = asyncCheckInfo.getAsyncTaskId();
                    checkItem.setCheckParams(asyncCheckInfo.getCheckParams());
                    asyncTaskMap.put(asyncTaskId, checkItem);
                    itemAsynIdMap.put(checkItem, asyncTaskId);
                    taskIds.add(asyncTaskId);
                    continue block26;
                }
                case "dataAnalysis": {
                    asyncCheckInfo = this.dataAnalysisCheck(oneKeyCheckInfo, checkItem, jobContext);
                    asyncTaskId = asyncCheckInfo.getAsyncTaskId();
                    checkItem.setCheckParams(asyncCheckInfo.getCheckParams());
                    asyncTaskMap.put(asyncTaskId, checkItem);
                    itemAsynIdMap.put(checkItem, asyncTaskId);
                    taskIds.add(asyncTaskId);
                    continue block26;
                }
                default: {
                    asyncCheckInfo = this.formulaCheck(oneKeyCheckInfo, checkItem, jobContext);
                    asyncTaskId = asyncCheckInfo.getAsyncTaskId();
                    checkItem.setCheckParams(asyncCheckInfo.getCheckParams());
                    asyncTaskMap.put(asyncTaskId, checkItem);
                    itemAsynIdMap.put(checkItem, asyncTaskId);
                    taskIds.add(asyncTaskId);
                    continue block26;
                }
            }
        }
        double currTaskProgress = 0.0;
        double weight = 0.9 / (double)checkItems.size();
        int totalProgress = 0;
        asyncTaskMonitor.progressAndMessage(0.08, "running");
        while (currTaskProgress < 0.9) {
            currTaskProgress = 0.0;
            totalProgress = 0;
            Map<String, AsyncTask> tasks = this.queryTasks(taskIds);
            for (Map.Entry<String, AsyncTask> entry : tasks.entrySet()) {
                AsyncTask asyncTask = entry.getValue();
                if (asyncTask.getState().equals((Object)TaskState.FINISHED)) {
                    ++totalProgress;
                    currTaskProgress += 1.0 * weight;
                    continue;
                }
                double asyncProcess = 0.0;
                if (asyncTask.getState().equals((Object)TaskState.ERROR) || asyncTask.getState().equals((Object)TaskState.CANCELED) || asyncTask.getState().equals((Object)TaskState.OVERTIME) || asyncTask.getState().equals((Object)TaskState.OUTOFQUEUE)) {
                    asyncProcess = 1.0;
                    ++totalProgress;
                }
                currTaskProgress += asyncProcess * weight;
            }
            asyncTaskMonitor.progressAndMessage(currTaskProgress, "running");
            if (totalProgress == asyncTaskMap.size()) break;
            try {
                Thread.sleep(1500L);
            }
            catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
        this.deleteResults(this.getResultTableName(oneKeyCheckInfo.getContext().getFormSchemeKey()));
        for (MultCheckItem checkItem : checkItems) {
            String asynId = "";
            if (!itemAsynIdMap.containsKey((Object)checkItem)) continue;
            asynId = (String)itemAsynIdMap.get((Object)checkItem);
            this.multCheckDao.insertMultCheckResult(asynId, checkItem, oneKeyCheckInfo, asyncTaskMonitor);
        }
        asyncTaskMonitor.progressAndMessage(1.0, "finish");
        if (asyncTaskMonitor != null) {
            asyncTaskMonitor.finish("task_success_info", (Object)"");
        }
        if ((multCheckConfigBean = (MultCheckConfigBean)(basicCheckItems = this.TaskExtConfigController.getTaskExtConfigDefineBySchemakey(oneKeyCheckInfo.getContext().getTaskKey(), oneKeyCheckInfo.getContext().getFormSchemeKey(), "taskextension-multcheck")).getExtInfoModel()) != null && multCheckConfigBean.getRecordEnable()) {
            this.oneKeyCheckController.invokeMultCheck(asyncTaskMap, oneKeyCheck_shjl);
        }
        return true;
    }

    private AsyncCheckInfo entityTreeCheck(OneKeyCheckInfo oneKeyCheckInfo, MultCheckItem checkItem, JobContext jobContext) throws JobExecutionException {
        AsyncCheckInfo asyncCheckInfo = new AsyncCheckInfo();
        CheckParam checkParam = new CheckParam();
        checkParam.setTaskKey(oneKeyCheckInfo.getContext().getTaskKey());
        checkParam.setPeriodCode(((DimensionValue)oneKeyCheckInfo.getContext().getDimensionSet().get("DATATIME")).getValue());
        String subTaskTitle = com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType.ASYNCTASK_ENTITYTREECHECK.getName();
        EntityTreeCheckAsyncTaskExecutor subJob = new EntityTreeCheckAsyncTaskExecutor();
        Map params = subJob.getParams();
        params.put(REALTIME_TASK_PARAMSKEY_ARGS, SimpleParamConverter.SerializationUtils.serializeToString((Object)checkParam));
        subJob.setTitle(subTaskTitle);
        String asynTaskID = jobContext.executeRealTimeSubJob((AbstractRealTimeJob)subJob);
        asyncCheckInfo.setAsyncTaskId(asynTaskID);
        asyncCheckInfo.setCheckParams(JsonUtil.objectToJson((Object)oneKeyCheckInfo.getContext()));
        return asyncCheckInfo;
    }

    private AsyncCheckInfo queryCheck(OneKeyCheckInfo oneKeyCheckInfo, MultCheckItem checkItem) {
        String asynTaskID = "";
        AsyncCheckInfo asyncCheckInfo = new AsyncCheckInfo();
        QueryParamInfo queryInfo = new QueryParamInfo();
        ArrayList<String> queryDefines = new ArrayList<String>();
        queryDefines.add(checkItem.getKey());
        queryInfo.setQueryDefines(queryDefines);
        queryInfo.setContext(oneKeyCheckInfo.getContext());
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(queryInfo.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(queryInfo.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)queryInfo));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new QueryCheckAsyncTaskExecutor());
        asynTaskID = this.asyncTaskManager.publishAndExecuteTask((Object)npRealTimeTaskInfo, com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType.ASYNCTASK_QUERYCHECK.getName());
        asyncCheckInfo.setAsyncTaskId(asynTaskID);
        asyncCheckInfo.setCheckParams(JsonUtil.objectToJson((Object)npRealTimeTaskInfo));
        return asyncCheckInfo;
    }

    private AsyncCheckInfo zbQueryCheck(OneKeyCheckInfo oneKeyCheckInfo, MultCheckItem checkItem, JobContext jobContext) throws JobExecutionException {
        AsyncCheckInfo asyncCheckInfo = new AsyncCheckInfo();
        ZBQueryParamInfo zbQueryInfo = new ZBQueryParamInfo();
        HashMap checkScope = new HashMap();
        if (oneKeyCheckInfo.getBeforeUpload() != null && oneKeyCheckInfo.getBeforeUpload().booleanValue()) {
            if (checkItem.getItemSetting() != null) {
                if (checkItem.getItemSetting() instanceof LinkedTreeMap) {
                    for (Iterator<Object> key : ((LinkedTreeMap)checkItem.getItemSetting()).keySet()) {
                        checkScope.put(key, ((LinkedTreeMap)checkItem.getItemSetting()).get(key));
                    }
                } else {
                    checkScope = Maps.newHashMap();
                    BeanMap beanMap = BeanMap.create(checkItem.getItemSetting());
                    for (Object key : beanMap.keySet()) {
                        checkScope.put(key + "", beanMap.get(key));
                    }
                }
            }
        } else {
            checkScope = (HashMap)checkItem.getItemSetting();
        }
        List zbQueryDefines = (List)checkScope.get("zbQueryTemplateList");
        zbQueryInfo.setZbQueryModelIds(zbQueryDefines);
        zbQueryInfo.setContext(oneKeyCheckInfo.getContext());
        String subTaskTitle = com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType.ASYNCTASK_ZBQUERYCHECK.getName();
        ZBQueryCheckAsyncTaskExecutor subJob = new ZBQueryCheckAsyncTaskExecutor();
        Map params = subJob.getParams();
        params.put(REALTIME_TASK_PARAMSKEY_ARGS, SimpleParamConverter.SerializationUtils.serializeToString((Object)zbQueryInfo));
        subJob.setTitle(subTaskTitle);
        String asynTaskID = jobContext.executeRealTimeSubJob((AbstractRealTimeJob)subJob);
        asyncCheckInfo.setAsyncTaskId(asynTaskID);
        asyncCheckInfo.setCheckParams(JsonUtil.objectToJson((Object)zbQueryInfo));
        return asyncCheckInfo;
    }

    private AsyncCheckInfo dataAnalysisCheck(OneKeyCheckInfo oneKeyCheckInfo, MultCheckItem checkItem, JobContext jobContext) throws JobExecutionException {
        AsyncCheckInfo asyncCheckInfo = new AsyncCheckInfo();
        ZBQueryParamInfo dataAnalysisInfo = new ZBQueryParamInfo();
        HashMap checkScope = (HashMap)checkItem.getItemSetting();
        List zbQueryDefines = (List)checkScope.get("analysisList");
        dataAnalysisInfo.setZbQueryModelIds(zbQueryDefines);
        dataAnalysisInfo.setContext(oneKeyCheckInfo.getContext());
        String subTaskTitle = com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType.ASYNCTASK_DATAANALYSISCHECK.getName();
        DataAnalysisCheckAsyncTaskExecutor subJob = new DataAnalysisCheckAsyncTaskExecutor();
        Map params = subJob.getParams();
        params.put(REALTIME_TASK_PARAMSKEY_ARGS, SimpleParamConverter.SerializationUtils.serializeToString((Object)dataAnalysisInfo));
        subJob.setTitle(subTaskTitle);
        String asynTaskID = jobContext.executeRealTimeSubJob((AbstractRealTimeJob)subJob);
        asyncCheckInfo.setAsyncTaskId(asynTaskID);
        asyncCheckInfo.setCheckParams(JsonUtil.objectToJson((Object)dataAnalysisInfo));
        return asyncCheckInfo;
    }

    private AsyncCheckInfo entityCheck(OneKeyCheckInfo oneKeyCheckInfo, MultCheckItem checkItem, JobContext jobContext) throws JobExecutionException {
        AsyncCheckInfo asyncCheckInfo = new AsyncCheckInfo();
        EntityCheckInfo info = new EntityCheckInfo();
        String taskKey = oneKeyCheckInfo.getContext().getTaskKey();
        String formSchemeKey = oneKeyCheckInfo.getContext().getFormSchemeKey();
        String period = ((DimensionValue)oneKeyCheckInfo.getContext().getDimensionSet().get("DATATIME")).getValue();
        HashMap checkScope = new HashMap();
        if (oneKeyCheckInfo.getBeforeUpload() != null && oneKeyCheckInfo.getBeforeUpload().booleanValue()) {
            if (checkItem.getItemSetting() instanceof LinkedTreeMap) {
                for (Object key : ((LinkedTreeMap)checkItem.getItemSetting()).keySet()) {
                    checkScope.put(key, ((LinkedTreeMap)checkItem.getItemSetting()).get(key));
                }
            } else {
                checkScope = Maps.newHashMap();
                if (checkItem.getItemSetting() != null) {
                    BeanMap beanMap = BeanMap.create(checkItem.getItemSetting());
                    for (Object key : beanMap.keySet()) {
                        checkScope.put(key + "", beanMap.get(key));
                    }
                }
            }
        } else {
            checkScope = (HashMap)checkItem.getItemSetting();
        }
        String associatedTaskKey = checkScope.get("taskKey") == null ? oneKeyCheckInfo.getContext().getTaskKey() : checkScope.get("taskKey").toString();
        String associatedFormSchemeKey = checkScope.get("formSchemeKey") == null ? oneKeyCheckInfo.getContext().getFormSchemeKey() : checkScope.get("formSchemeKey").toString();
        String mainDimName = this.dimensionUtil.getDwMainDimName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        info.setContext(oneKeyCheckInfo.getContext());
        info.setTaskKey(taskKey);
        info.setFormSchemeKey(formSchemeKey);
        info.setPeriod(period);
        info.setAssociatedTaskKey(associatedTaskKey);
        info.setAssociatedFormSchemeKey(associatedFormSchemeKey);
        String currentEntity = "";
        currentEntity = oneKeyCheckInfo.getSingleApp() || oneKeyCheckInfo.getBeforeUpload() != null && oneKeyCheckInfo.getBeforeUpload() != false && checkScope.get("scop") != null ? (checkScope.get("scop") == null ? this.getRootNode(oneKeyCheckInfo.getContext()).getKey().toString() : checkScope.get("scop").toString()) : ((DimensionValue)oneKeyCheckInfo.getContext().getDimensionSet().get(mainDimName)).getValue();
        info.setScop(currentEntity);
        HashMap association = new HashMap();
        if (oneKeyCheckInfo.getBeforeUpload() != null && oneKeyCheckInfo.getBeforeUpload().booleanValue()) {
            if (checkScope.get("association") != null) {
                if (checkScope.get("association") instanceof LinkedTreeMap) {
                    for (Iterator<Object> key : ((LinkedTreeMap)checkScope.get("association")).keySet()) {
                        association.put(key, ((LinkedTreeMap)checkScope.get("association")).get(key));
                    }
                } else {
                    association = Maps.newHashMap();
                    BeanMap beanMap = BeanMap.create(checkScope.get("association"));
                    for (Object key : beanMap.keySet()) {
                        association.put(key + "", beanMap.get(key));
                    }
                }
            }
        } else {
            association = (HashMap)checkScope.get("association");
        }
        String associatedperiod = "";
        if (association != null && association.size() > 0) {
            int configuration = association.get("configuration") instanceof Double ? ((Number)association.get("configuration")).intValue() : Integer.parseInt(association.get("configuration").toString());
            String specified = association.get("specified") == null ? "" : association.get("specified").toString();
            String lastIssue = association.get("lastIssue") == null ? "" : association.get("lastIssue").toString();
            String nextIssue = association.get("nextIssue") == null ? "" : association.get("nextIssue").toString();
            String periodOffse = association.get("periodOffse") == null ? "" : association.get("periodOffse").toString();
            associatedperiod = this.getAssociatedPeriod(period, configuration, specified, lastIssue, nextIssue, periodOffse);
        } else {
            HashMap datatimeMap = new HashMap();
            if (oneKeyCheckInfo.getBeforeUpload() != null && oneKeyCheckInfo.getBeforeUpload().booleanValue()) {
                if (checkScope.get("datatime") instanceof LinkedTreeMap) {
                    for (Object key : ((LinkedTreeMap)checkScope.get("datatime")).keySet()) {
                        datatimeMap.put(key, ((LinkedTreeMap)checkScope.get("datatime")).get(key));
                    }
                } else if (checkScope.get("datatime") != null) {
                    datatimeMap = Maps.newHashMap();
                    BeanMap beanMap = BeanMap.create(checkScope.get("datatime"));
                    for (Object key : beanMap.keySet()) {
                        datatimeMap.put(key + "", beanMap.get(key));
                    }
                }
            } else {
                datatimeMap = (HashMap)checkScope.get("datatime");
            }
            associatedperiod = datatimeMap.get("value").toString();
        }
        info.setAssociatedperiod(associatedperiod);
        String subTaskTitle = com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType.ASYNCTASK_ENTITYCHECK.getName();
        EntityCheckAsyncTaskExecutor subJob = new EntityCheckAsyncTaskExecutor();
        Map params = subJob.getParams();
        params.put(REALTIME_TASK_PARAMSKEY_ARGS, SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)info)));
        subJob.setTitle(subTaskTitle);
        String asynTaskID = jobContext.executeRealTimeSubJob((AbstractRealTimeJob)subJob);
        asyncCheckInfo.setAsyncTaskId(asynTaskID);
        asyncCheckInfo.setCheckParams(JsonUtil.objectToJson((Object)((Object)info)));
        return asyncCheckInfo;
    }

    public String getAssociatedPeriod(String period, int configuration, String specified, String lastIssue, String nextIssue, String periodOffset) {
        String associatedPeriod = "";
        PeriodMatchingType type = PeriodMatchingType.forValue((int)configuration);
        DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
        PeriodWrapper periodWrapper = new PeriodWrapper(period);
        switch (type) {
            case PERIOD_TYPE_CURRENT: {
                associatedPeriod = period;
                break;
            }
            case PERIOD_TYPE_PREVIOUS: {
                periodAdapter.priorPeriod(periodWrapper);
                associatedPeriod = periodWrapper.toString();
                break;
            }
            case PERIOD_TYPE_NEXT: {
                periodAdapter.nextPeriod(periodWrapper);
                associatedPeriod = periodWrapper.toString();
                break;
            }
            case PERIOD_TYPE_SPECIFIED: {
                associatedPeriod = specified;
                break;
            }
            case PERIOD_TYPE_OFFSET: {
                periodAdapter.modify(periodWrapper, PeriodModifier.parse((String)periodOffset));
                associatedPeriod = periodWrapper.toString();
                break;
            }
            case PERIOD_TYPE_ALL: {
                break;
            }
        }
        return associatedPeriod;
    }

    private AsyncCheckInfo formulaCheck(OneKeyCheckInfo oneKeyCheckInfo, MultCheckItem checkItem, JobContext jobContext) throws JobExecutionException {
        AsyncCheckInfo asyncCheckInfo = new AsyncCheckInfo();
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormKey(oneKeyCheckInfo.getContext().getFormKey());
        jtableContext.setFormSchemeKey(oneKeyCheckInfo.getContext().getFormSchemeKey());
        jtableContext.setFormulaSchemeKey(oneKeyCheckInfo.getContext().getFormulaSchemeKey());
        jtableContext.setTaskKey(oneKeyCheckInfo.getContext().getTaskKey());
        BatchCheckInfo batchCheckInfo = new BatchCheckInfo();
        jtableContext.setDimensionSet(oneKeyCheckInfo.getSelectedDimensionSet());
        HashMap checkScope = new HashMap();
        if (oneKeyCheckInfo.getBeforeUpload() != null && oneKeyCheckInfo.getBeforeUpload().booleanValue()) {
            if (checkItem.getItemSetting() != null) {
                if (checkItem.getItemSetting() instanceof LinkedTreeMap) {
                    for (Iterator<Object> key : ((LinkedTreeMap)checkItem.getItemSetting()).keySet()) {
                        checkScope.put(key, ((LinkedTreeMap)checkItem.getItemSetting()).get(key));
                    }
                } else {
                    checkScope = Maps.newHashMap();
                    BeanMap beanMap = BeanMap.create(checkItem.getItemSetting());
                    for (Object key : beanMap.keySet()) {
                        checkScope.put(key + "", beanMap.get(key));
                    }
                }
            }
        } else {
            checkScope = (HashMap)checkItem.getItemSetting();
        }
        if (checkScope.containsKey("unitList")) {
            ArrayList unitList = (ArrayList)checkScope.get("unitList");
            if (unitList == null) {
                unitList = new ArrayList();
            }
            StringBuffer unitStr = new StringBuffer("");
            if (unitList.size() > 0) {
                for (String str : unitList) {
                    unitStr.append(str).append(";");
                }
                unitStr.deleteCharAt(unitStr.length() - 1);
            }
            String mainDimName = this.dimensionUtil.getDwMainDimName(oneKeyCheckInfo.getContext().getFormSchemeKey());
            ((DimensionValue)jtableContext.getDimensionSet().get(mainDimName)).setValue(unitStr.toString());
        }
        HashMap formulaMap = (HashMap)checkScope.get("formulaMap");
        batchCheckInfo.setContext(jtableContext);
        if (formulaMap == null) {
            formulaMap = new HashMap();
            ArrayList formulas = new ArrayList();
            String FormKeys = String.join((CharSequence)";", this.viewCtrl.queryAllFormKeysByFormScheme(oneKeyCheckInfo.getContext().getFormSchemeKey()));
            BatchDimensionValueFormInfo batchDimensionValueFormInfo = this.formGroupProvider.getForms(oneKeyCheckInfo.getContext(), FormKeys, Consts.FormAccessLevel.FORM_READ);
            List forms = batchDimensionValueFormInfo.getForms();
            for (String form : forms) {
                formulaMap.put(form, formulas);
            }
            batchCheckInfo.setFormulas(formulaMap);
        } else {
            batchCheckInfo.setFormulas((Map)formulaMap);
        }
        String subTaskTitle = AsynctaskPoolType.ASYNCTASK_BATCHCHECK.getName();
        BatchCheckAsyncTaskExecutor subJob = new BatchCheckAsyncTaskExecutor();
        Map params = subJob.getParams();
        params.put(REALTIME_TASK_PARAMSKEY_ARGS, SimpleParamConverter.SerializationUtils.serializeToString((Object)batchCheckInfo));
        subJob.setTitle(subTaskTitle);
        String asynTaskID = jobContext.executeRealTimeSubJob((AbstractRealTimeJob)subJob);
        batchCheckInfo.setFormulaSchemeKeys(checkItem.getKey());
        ReviewInfoParam reviewInfoParam = new ReviewInfoParam();
        BatchCheckResultGroupInfo batchCheckResultGroupInfo = new BatchCheckResultGroupInfo();
        batchCheckResultGroupInfo.setAsyncTaskKey(asynTaskID);
        batchCheckResultGroupInfo.setContext(jtableContext);
        batchCheckResultGroupInfo.setCheckTypes(batchCheckInfo.getCheckTypes());
        batchCheckResultGroupInfo.setFormulas(batchCheckInfo.getFormulas());
        batchCheckResultGroupInfo.setCustomCheckFilter(batchCheckInfo.getCustomCheckFilter());
        batchCheckResultGroupInfo.setPagerInfo(batchCheckInfo.getPagerInfo());
        batchCheckResultGroupInfo.setUploadCheckTypes(batchCheckInfo.getUploadCheckTypes());
        batchCheckResultGroupInfo.setCheckDesNull(batchCheckInfo.isCheckDesNull());
        batchCheckResultGroupInfo.setFormulaSchemeKeys(batchCheckInfo.getFormulaSchemeKeys());
        batchCheckResultGroupInfo.setOrderField(batchCheckInfo.getOrderField());
        batchCheckResultGroupInfo.setChooseTypes(batchCheckInfo.getChooseTypes());
        reviewInfoParam.setCheckInfo(batchCheckResultGroupInfo);
        this.reviewInfoService.saveOrUpdatetRevienInfo(reviewInfoParam);
        Map<String, Object> param = this.buildFormulaCheckParam(batchCheckInfo, jtableContext);
        asyncCheckInfo.setCheckParams(JsonUtil.objectToJson(param));
        asyncCheckInfo.setAsyncTaskId(asynTaskID);
        return asyncCheckInfo;
    }

    private Map<String, Object> buildFormulaCheckParam(BatchCheckInfo batchCheckInfo, JtableContext context) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BatchCheckParam checkParam = this.funcExecuteService.getBatchCheckParam(context);
        ReviewInfoParam reviewInfoParam = checkParam.getReviewInfoParam();
        HashMap<String, Object> param = new HashMap<String, Object>();
        if (reviewInfoParam == null) {
            reviewInfoParam = this.getReviewInfoParam(batchCheckInfo, context);
        }
        param.put("taskName", reviewInfoParam.getTaskName());
        param.put("formSchemeTitle", reviewInfoParam.getFormSchemeTitle());
        param.put("formulaSchemeTitle", reviewInfoParam.getFormulaSchemeTitle());
        param.put("periodName", reviewInfoParam.getFormSchemeDate());
        param.put("checkInfo", reviewInfoParam.getCheckInfo());
        param.put("reviewDate", sdf.format(new Date()));
        param.put("unitList", null);
        param.put("formList", null);
        param.put("sysParam", checkParam.getSysParam());
        param.put("entityList", checkParam.getEntityList());
        param.put("formulaSchemeList", checkParam.getFormulaSchemeList());
        return param;
    }

    private ReviewInfoParam getReviewInfoParam(BatchCheckInfo batchCheckInfo, JtableContext context) {
        FormulaSchemeDefine formulaSchemeDefine;
        FormSchemeDefine formSchemeDefine;
        ReviewInfoParam reviewInfoParam = new ReviewInfoParam();
        BatchCheckResultGroupInfo groupInfo = new BatchCheckResultGroupInfo();
        groupInfo.setCheckTypes(batchCheckInfo.getCheckTypes());
        groupInfo.setContext(batchCheckInfo.getContext());
        groupInfo.setPagerInfo(batchCheckInfo.getPagerInfo());
        groupInfo.setFormulas(batchCheckInfo.getFormulas());
        groupInfo.setFormulaSchemeKeys(batchCheckInfo.getFormulaSchemeKeys());
        groupInfo.setOrderField(batchCheckInfo.getOrderField());
        groupInfo.setUploadCheckTypes(batchCheckInfo.getUploadCheckTypes());
        groupInfo.setChooseTypes(batchCheckInfo.getChooseTypes());
        groupInfo.setIsBatchUpload(false);
        groupInfo.setCustomCheckFilter(batchCheckInfo.getCustomCheckFilter());
        reviewInfoParam.setCheckInfo(groupInfo);
        TaskDefine taskDefine = this.runtimeViewController.queryTaskDefine(context.getTaskKey());
        if (taskDefine != null) {
            reviewInfoParam.setTaskName(taskDefine.getTitle());
        }
        if ((formSchemeDefine = this.runtimeViewController.getFormScheme(context.getFormSchemeKey())) != null) {
            reviewInfoParam.setFormSchemeTitle(formSchemeDefine.getTitle());
        }
        if ((formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(batchCheckInfo.getFormulaSchemeKeys())) != null) {
            reviewInfoParam.setFormulaSchemeTitle(formulaSchemeDefine.getTitle());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        reviewInfoParam.setReviewDate(sdf.format(new Date()));
        reviewInfoParam.setFormSchemeDate(((DimensionValue)context.getDimensionSet().get("DATATIME")).getValue());
        return reviewInfoParam;
    }

    private AsyncCheckInfo nodeCheck(OneKeyCheckInfo oneKeyCheckInfo, MultCheckItem checkItem, JobContext jobContext) throws JobExecutionException {
        JSONObject jsob;
        AsyncCheckInfo asyncCheckInfo = new AsyncCheckInfo();
        NodeCheckInfo nodeCheckInfo = new NodeCheckInfo();
        nodeCheckInfo.setContext(oneKeyCheckInfo.getContext());
        if (oneKeyCheckInfo.getBeforeUpload() != null && oneKeyCheckInfo.getBeforeUpload().booleanValue()) {
            HashMap checkScope = new HashMap();
            if (checkItem.getItemSetting() != null) {
                if (checkItem.getItemSetting() instanceof LinkedTreeMap) {
                    for (Iterator<Object> key : ((LinkedTreeMap)checkItem.getItemSetting()).keySet()) {
                        checkScope.put(key, ((LinkedTreeMap)checkItem.getItemSetting()).get(key));
                    }
                } else {
                    checkScope = Maps.newHashMap();
                    BeanMap beanMap = BeanMap.create(checkItem.getItemSetting());
                    for (Object key : beanMap.keySet()) {
                        checkScope.put(key + "", beanMap.get(key));
                    }
                }
            }
            if (!checkScope.containsKey("formList")) {
                checkScope.put("formList", new ArrayList());
            }
            jsob = new JSONObject((Map)checkScope);
        } else {
            jsob = new JSONObject((Map)checkItem.getItemSetting());
        }
        JSONArray formList = (JSONArray)jsob.get("formList");
        String contextStr = JsonGetUtil.getString(jsob, "context");
        JtableContext jtableContext = (JtableContext)JSONUtil.parseObject((String)contextStr, JtableContext.class);
        if (jtableContext == null) {
            jtableContext = oneKeyCheckInfo.getContext();
        }
        boolean recursive = false;
        if (JsonGetUtil.getBoolean(jsob, "tierCheck") || JsonGetUtil.getBoolean(jsob, "recursive")) {
            recursive = true;
        }
        BigDecimal errorRange = JsonGetUtil.getBigDecimal(jsob, "errorRange");
        if (oneKeyCheckInfo.getSingleApp()) {
            String mainDimName = this.dimensionUtil.getDwMainDimName(oneKeyCheckInfo.getContext().getFormSchemeKey());
            String currentEntity = JsonGetUtil.getString(jsob, "entityCode") == null ? this.getRootNode(oneKeyCheckInfo.getContext()).getKey().toString() : JsonGetUtil.getString(jsob, "entityCode");
            ((DimensionValue)jtableContext.getDimensionSet().get(mainDimName)).setValue(currentEntity);
        }
        nodeCheckInfo.setContext(jtableContext);
        String forms = "";
        for (int i = 0; i < formList.length(); ++i) {
            JSONObject jsonForm = formList.getJSONObject(i);
            forms = i != formList.length() - 1 ? forms + jsonForm.getString("formKey") + ";" : forms + jsonForm.getString("formKey");
        }
        nodeCheckInfo.setFormKeys(forms);
        nodeCheckInfo.setRecursive(recursive);
        nodeCheckInfo.setErrorRange(errorRange);
        String subTaskTitle = AsynctaskPoolType.ASYNCTASK_NODECHECK.getName();
        NodeCheckAsyncTaskExecutor subJob = new NodeCheckAsyncTaskExecutor();
        Map params = subJob.getParams();
        params.put(REALTIME_TASK_PARAMSKEY_ARGS, SimpleParamConverter.SerializationUtils.serializeToString((Object)nodeCheckInfo));
        subJob.setTitle(subTaskTitle);
        String asynTaskID = jobContext.executeRealTimeSubJob((AbstractRealTimeJob)subJob);
        asyncCheckInfo.setAsyncTaskId(asynTaskID);
        asyncCheckInfo.setCheckParams(JsonUtil.objectToJson((Object)nodeCheckInfo));
        return asyncCheckInfo;
    }

    private AsyncCheckInfo integrityFormCheck(OneKeyCheckInfo oneKeyCheckInfo, MultCheckItem checkItem, JobContext jobContext) throws JobExecutionException {
        List formList;
        AsyncCheckInfo asyncCheckInfo = new AsyncCheckInfo();
        String period = ((DimensionValue)oneKeyCheckInfo.getContext().getDimensionSet().get("DATATIME")).getValue();
        String areacode = "";
        ArrayList<String> selectedForms = new ArrayList<String>();
        HashMap checkScope = new HashMap();
        if (oneKeyCheckInfo.getBeforeUpload() != null && oneKeyCheckInfo.getBeforeUpload().booleanValue()) {
            if (checkItem.getItemSetting() != null) {
                if (checkItem.getItemSetting() instanceof LinkedTreeMap) {
                    for (Iterator<Object> key : ((LinkedTreeMap)checkItem.getItemSetting()).keySet()) {
                        checkScope.put(key, ((LinkedTreeMap)checkItem.getItemSetting()).get(key));
                    }
                } else {
                    checkScope = Maps.newHashMap();
                    BeanMap beanMap = BeanMap.create(checkItem.getItemSetting());
                    for (Object key : beanMap.keySet()) {
                        checkScope.put(key + "", beanMap.get(key));
                    }
                }
            }
            formList = checkScope.get("formList") != null ? (checkItem.getItemSetting() instanceof LinkedTreeMap ? (List)checkScope.get("formList") : new ArrayList((HashSet)checkScope.get("formList"))) : new ArrayList();
        } else {
            checkScope = (HashMap)checkItem.getItemSetting();
            formList = (ArrayList)checkScope.get("formList");
        }
        for (int i = 0; i < formList.size(); ++i) {
            selectedForms.add(((HashMap)formList.get(i)).get("formKey").toString());
        }
        ArrayList<String> unitList = new ArrayList<String>();
        if (checkScope.containsKey("unitList")) {
            unitList = (ArrayList<String>)checkScope.get("unitList");
            if (unitList == null) {
                unitList = new ArrayList<String>();
            }
        } else {
            String mainDimName = this.dimensionUtil.getDwMainDimName(oneKeyCheckInfo.getContext().getFormSchemeKey());
            String enityListStr = oneKeyCheckInfo.getSelectedDimensionSet().get(mainDimName).getValue();
            if (!enityListStr.isEmpty()) {
                for (String entityKey : enityListStr.split(";")) {
                    unitList.add(entityKey);
                }
            }
        }
        IntegrityCheckParams info = new IntegrityCheckParams();
        info.setTaskKey(oneKeyCheckInfo.getContext().getTaskKey());
        info.setFormSchemeKey(oneKeyCheckInfo.getContext().getFormSchemeKey());
        info.setperiod(period);
        info.setEntityKeys(unitList);
        info.setFormKeys(selectedForms);
        info.setContext(oneKeyCheckInfo.getContext());
        String subTaskTitle = com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType.ASYNCTASK_INTEGRITYCHECK.getName();
        IntegrityCheckAsyncTaskExecutor subJob = new IntegrityCheckAsyncTaskExecutor();
        Map params = subJob.getParams();
        params.put(REALTIME_TASK_PARAMSKEY_ARGS, SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)info)));
        subJob.setTitle(subTaskTitle);
        String asynTaskID = jobContext.executeRealTimeSubJob((AbstractRealTimeJob)subJob);
        asyncCheckInfo.setAsyncTaskId(asynTaskID);
        asyncCheckInfo.setCheckParams(JsonUtil.objectToJson((Object)((Object)info)));
        return asyncCheckInfo;
    }

    private AsyncCheckInfo enumCheck(OneKeyCheckInfo oneKeyCheckInfo, MultCheckItem checkItem, JobContext jobContext) throws JobExecutionException {
        ArrayList enumStr;
        AsyncCheckInfo asyncCheckInfo = new AsyncCheckInfo();
        EnumDataCheckInfo enumDataCheckInfo = new EnumDataCheckInfo();
        JtableContext jtableContext = new JtableContext(oneKeyCheckInfo.getContext());
        enumDataCheckInfo.setContext(jtableContext);
        Map<String, DimensionValue> selectedDimensionSet = oneKeyCheckInfo.getSelectedDimensionSet();
        HashMap<String, DimensionValue> temp = new HashMap<String, DimensionValue>();
        for (Map.Entry<String, DimensionValue> entry : selectedDimensionSet.entrySet()) {
            temp.put(entry.getKey(), new DimensionValue(entry.getValue()));
        }
        enumDataCheckInfo.setSelectedDimensionSet(temp);
        HashMap checkScope = new HashMap();
        if (oneKeyCheckInfo.getBeforeUpload() != null && oneKeyCheckInfo.getBeforeUpload().booleanValue()) {
            if (checkItem.getItemSetting() != null) {
                if (checkItem.getItemSetting() instanceof LinkedTreeMap) {
                    for (Iterator<Object> key : ((LinkedTreeMap)checkItem.getItemSetting()).keySet()) {
                        checkScope.put(key, ((LinkedTreeMap)checkItem.getItemSetting()).get(key));
                    }
                } else {
                    checkScope = Maps.newHashMap();
                    BeanMap beanMap = BeanMap.create(checkItem.getItemSetting());
                    for (Iterator key : beanMap.keySet()) {
                        checkScope.put(key + "", beanMap.get(key));
                    }
                }
            }
            enumStr = checkScope.get("enumList") != null ? (checkItem.getItemSetting() instanceof LinkedTreeMap ? (ArrayList)checkScope.get("enumList") : new ArrayList((HashSet)checkScope.get("enumList"))) : new ArrayList();
        } else {
            checkScope = (HashMap)checkItem.getItemSetting();
            enumStr = (ArrayList)checkScope.get("enumList");
        }
        if (checkScope.containsKey("unitList")) {
            ArrayList unitList = (ArrayList)checkScope.get("unitList");
            if (unitList == null) {
                unitList = new ArrayList();
            }
            StringBuffer unitStr = new StringBuffer("");
            if (unitList.size() > 0) {
                for (String str : unitList) {
                    unitStr.append(str).append(";");
                }
                unitStr.deleteCharAt(unitStr.length() - 1);
            }
            String mainDimName = this.dimensionUtil.getDwMainDimName(oneKeyCheckInfo.getContext().getFormSchemeKey());
            enumDataCheckInfo.getSelectedDimensionSet().get(mainDimName).setValue(unitStr.toString());
        }
        String enumSels = "";
        if (enumStr.size() > 0) {
            int jL = enumStr.size();
            for (int j = 0; j < jL; ++j) {
                enumSels = enumSels + ((String)enumStr.get(j)).substring(1, ((String)enumStr.get(j)).indexOf(12305)) + ";";
            }
        }
        enumDataCheckInfo.setEnumNames(enumSels);
        String subTaskTitle = com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType.ASYNCTASK_ENUMDATACHECK.getName();
        EnumDataCheckAsyncTaskExecutor subJob = new EnumDataCheckAsyncTaskExecutor();
        Map params = subJob.getParams();
        params.put(REALTIME_TASK_PARAMSKEY_ARGS, SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)enumDataCheckInfo)));
        subJob.setTitle(subTaskTitle);
        String asynTaskID = jobContext.executeRealTimeSubJob((AbstractRealTimeJob)subJob);
        asyncCheckInfo.setAsyncTaskId(asynTaskID);
        asyncCheckInfo.setCheckParams(JsonUtil.objectToJson((Object)((Object)enumDataCheckInfo)));
        return asyncCheckInfo;
    }

    private AsyncCheckInfo attachmentCheck(OneKeyCheckInfo oneKeyCheckInfo, MultCheckItem checkItem, JobContext jobContext) throws Exception {
        ArrayList zbList;
        AsyncCheckInfo asyncCheckInfo = new AsyncCheckInfo();
        BlobFileSizeCheckParam blobFileSizeCheckParam = new BlobFileSizeCheckParam();
        JtableContext jtableContext = new JtableContext(oneKeyCheckInfo.getContext());
        blobFileSizeCheckParam.setContext(jtableContext);
        HashMap checkScope = new HashMap();
        if (oneKeyCheckInfo.getBeforeUpload() != null && oneKeyCheckInfo.getBeforeUpload().booleanValue()) {
            if (checkItem.getItemSetting() != null) {
                if (checkItem.getItemSetting() instanceof LinkedTreeMap) {
                    for (Iterator<Object> key : ((LinkedTreeMap)checkItem.getItemSetting()).keySet()) {
                        checkScope.put(key, ((LinkedTreeMap)checkItem.getItemSetting()).get(key));
                    }
                } else {
                    checkScope = Maps.newHashMap();
                    BeanMap beanMap = BeanMap.create(checkItem.getItemSetting());
                    for (Object key : beanMap.keySet()) {
                        checkScope.put(key + "", beanMap.get(key));
                    }
                }
            }
            zbList = checkScope.get("zbList") != null ? (checkItem.getItemSetting() instanceof LinkedTreeMap ? (ArrayList)checkScope.get("zbList") : new ArrayList((HashSet)checkScope.get("zbList"))) : new ArrayList();
        } else {
            checkScope = (HashMap)checkItem.getItemSetting();
            zbList = (ArrayList)checkScope.get("zbList");
        }
        if (checkScope.containsKey("unitList")) {
            ArrayList unitList = (ArrayList)checkScope.get("unitList");
            if (unitList == null) {
                unitList = new ArrayList();
            }
            blobFileSizeCheckParam.setUnitKeys(unitList);
        } else {
            ArrayList<String> unitKeys = new ArrayList<String>();
            String mainDimName = this.dimensionUtil.getDwMainDimName(oneKeyCheckInfo.getContext().getFormSchemeKey());
            String enityListStr = oneKeyCheckInfo.getSelectedDimensionSet().get(mainDimName).getValue();
            if (!enityListStr.isEmpty()) {
                for (String entityKey : enityListStr.split(";")) {
                    unitKeys.add(entityKey);
                }
            }
            blobFileSizeCheckParam.setUnitKeys(unitKeys);
        }
        HashMap<String, BlobFormStruct> formMaps = new HashMap<String, BlobFormStruct>();
        if (zbList != null) {
            for (HashMap zb : zbList) {
                FormDefine formDefine = this.runTimeViewController.queryFormById(zb.get("formKey").toString());
                FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(zb.get("fieldkey").toString());
                if (formDefine == null || fieldDefine == null) continue;
                BlobFormStruct formStruct = null;
                if (formMaps.containsKey(zb.get("formKey").toString())) {
                    formStruct = (BlobFormStruct)formMaps.get(zb.get("formKey").toString());
                } else {
                    formStruct = new BlobFormStruct();
                    formStruct.setFlag(formDefine.getFormCode());
                    formStruct.setKey(formDefine.getKey());
                    formStruct.setTitle(formDefine.getTitle());
                    formMaps.put(zb.get("formKey").toString(), formStruct);
                }
                BlobFieldStruct fieldStruct = new BlobFieldStruct();
                fieldStruct.setDataLinkKey(zb.get("dataLinkKey").toString());
                fieldStruct.setKey(fieldDefine.getKey());
                fieldStruct.setFlag(fieldDefine.getCode());
                fieldStruct.setFormCode(formDefine.getFormCode());
                fieldStruct.setFormKey(formDefine.getKey());
                fieldStruct.setFormTitle(formDefine.getTitle());
                fieldStruct.setTitle(fieldDefine.getTitle());
                formStruct.getChildren().add(fieldStruct);
            }
        }
        blobFileSizeCheckParam.setSelBlobItem(new ArrayList<BlobFormStruct>(formMaps.values()));
        String subTaskTitle = com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType.ASYNCTASK_BLOBFILESIZECHECK.getName();
        BlobFileSizeCheckAsyncTaskExecutor subJob = new BlobFileSizeCheckAsyncTaskExecutor();
        Map params = subJob.getParams();
        params.put(REALTIME_TASK_PARAMSKEY_ARGS, SimpleParamConverter.SerializationUtils.serializeToString((Object)blobFileSizeCheckParam));
        subJob.setTitle(subTaskTitle);
        String asynTaskID = jobContext.executeRealTimeSubJob((AbstractRealTimeJob)subJob);
        asyncCheckInfo.setAsyncTaskId(asynTaskID);
        asyncCheckInfo.setCheckParams(JsonUtil.objectToJson((Object)blobFileSizeCheckParam));
        return asyncCheckInfo;
    }

    private AsyncCheckInfo errorDescCheck(OneKeyCheckInfo oneKeyCheckInfo, MultCheckItem checkItem, JobContext jobContext) throws JobExecutionException {
        AsyncCheckInfo asyncCheckInfo = new AsyncCheckInfo();
        ExplainInfoCheckParam explainInfoCheckParam = new ExplainInfoCheckParam();
        JtableContext jtableContext = new JtableContext(oneKeyCheckInfo.getContext());
        explainInfoCheckParam.setContext(jtableContext);
        HashMap checkScope = new HashMap();
        if (oneKeyCheckInfo.getBeforeUpload() != null && oneKeyCheckInfo.getBeforeUpload().booleanValue()) {
            if (checkItem.getItemSetting() != null) {
                if (checkItem.getItemSetting() instanceof LinkedTreeMap) {
                    for (Iterator<Object> key : ((LinkedTreeMap)checkItem.getItemSetting()).keySet()) {
                        checkScope.put(key, ((LinkedTreeMap)checkItem.getItemSetting()).get(key));
                    }
                } else {
                    checkScope = Maps.newHashMap();
                    BeanMap beanMap = BeanMap.create(checkItem.getItemSetting());
                    for (Object key : beanMap.keySet()) {
                        checkScope.put(key + "", beanMap.get(key));
                    }
                }
            }
        } else {
            checkScope = (HashMap)checkItem.getItemSetting();
        }
        ArrayList<String> unitList = new ArrayList<String>();
        if (checkScope.containsKey("unitList")) {
            unitList = (ArrayList<String>)checkScope.get("unitList");
            if (unitList == null) {
                unitList = new ArrayList<String>();
            }
        } else {
            String mainDimName = this.dimensionUtil.getDwMainDimName(oneKeyCheckInfo.getContext().getFormSchemeKey());
            String enityListStr = oneKeyCheckInfo.getSelectedDimensionSet().get(mainDimName).getValue();
            if (!enityListStr.isEmpty()) {
                for (String entityKey : enityListStr.split(";")) {
                    unitList.add(entityKey);
                }
            }
        }
        ArrayList formulaSolution = (ArrayList)checkScope.get("formulaSolution");
        String formulaSchemeKeys = "";
        for (int i = 0; i < formulaSolution.size(); ++i) {
            formulaSchemeKeys = i != formulaSolution.size() - 1 ? formulaSchemeKeys + (String)formulaSolution.get(i) + ";" : formulaSchemeKeys + (String)formulaSolution.get(i);
        }
        explainInfoCheckParam.setFormulaSchemeKey(formulaSchemeKeys);
        boolean impactReport = (Boolean)checkScope.get("impactReport");
        explainInfoCheckParam.setUnitKeys(unitList);
        explainInfoCheckParam.setItemKey(checkItem.getId());
        explainInfoCheckParam.setImpactReport(impactReport);
        explainInfoCheckParam.setMainAsynTaskID(jobContext.getInstanceId());
        String subTaskTitle = com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType.ASYNCTASK_EXPLAININFOCHECK.getName();
        ExplainInfoCheckAsyncTaskExecutor subJob = new ExplainInfoCheckAsyncTaskExecutor();
        Map params = subJob.getParams();
        params.put(REALTIME_TASK_PARAMSKEY_ARGS, SimpleParamConverter.SerializationUtils.serializeToString((Object)explainInfoCheckParam));
        subJob.setTitle(subTaskTitle);
        String asynTaskID = jobContext.executeRealTimeSubJob((AbstractRealTimeJob)subJob);
        asyncCheckInfo.setAsyncTaskId(asynTaskID);
        asyncCheckInfo.setCheckParams(JsonUtil.objectToJson((Object)explainInfoCheckParam));
        return asyncCheckInfo;
    }

    @Override
    public List<MultCheckResultItem> getCheckItemResults(String asyncTaskId, String formSchemeKey) {
        List<MultCheckResultItem> results = this.multCheckDao.getCheckItemResults(asyncTaskId, formSchemeKey);
        return results;
    }

    @Override
    public LastCheckInfo lastCheckResults(String formSchemeKey) {
        return this.multCheckDao.lastCheckResults(formSchemeKey);
    }

    @Override
    public EnumDataCheckResultInfo getEnumCheckResults(String asyncTaskId) {
        EnumDataCheckResultInfo enumDataCheckResultInfo = (EnumDataCheckResultInfo)this.asyncTaskManager.queryDetail(asyncTaskId);
        return enumDataCheckResultInfo;
    }

    @Override
    public void HandleMsg(String checkItemid, String jobId) {
    }

    @Override
    public List<FormulaSchemeDefine> queryformulaSchemes(String formSchemeKey) {
        List formulaSchemes = null;
        if (formSchemeKey != null) {
            formulaSchemes = this.runTimeFormulaController.getAllRPTFormulaSchemeDefinesByFormScheme(formSchemeKey);
        }
        return formulaSchemes;
    }

    @Override
    public EntityShortInfo getRootNode(JtableContext context) {
        EntityShortInfo entityInfo = new EntityShortInfo();
        IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(context.getFormSchemeKey());
        entityQuery.setIgnoreViewFilter(true);
        DimensionValueSet valueSet = new DimensionValueSet();
        valueSet.setValue("DATATIME", (Object)((DimensionValue)context.getDimensionSet().get("DATATIME")).getValue());
        entityQuery.setMasterKeys(valueSet);
        IEntityTable entityTable = null;
        try {
            entityTable = this.entityQueryHelper.buildEntityTable(entityQuery, context.getFormSchemeKey(), true);
            if (entityTable.getTotalCount() > 0) {
                IEntityRow row = (IEntityRow)entityTable.getRootRows().get(0);
                entityInfo.setCode(row.getCode());
                entityInfo.setKey(row.getEntityKeyData());
                entityInfo.setTitle(row.getTitle());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return entityInfo;
    }

    @Override
    public boolean paramCheck(String formSchemeKey) {
        StringBuffer sql = new StringBuffer();
        FormSchemeDefine scheme = this.runtimeViewController.getFormScheme(formSchemeKey);
        String str = String.format("%s%s", "SYS_MULTCHECK_", scheme.getFormSchemeCode());
        try {
            String libTableName = str;
            sql.append("SELECT * FROM ").append(libTableName).append(" WHERE 1=0");
            ArrayList paramValues = new ArrayList();
            this.jdbcTpl.queryForList(sql.toString(), paramValues.toArray());
            return true;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List getCheckSchemes(String formSchemeKey) throws Exception {
        FormSchemeDefine formSchemeDefine = this.viewCtrl.getFormScheme(formSchemeKey);
        ExtensionBasicModel basicCheckItems = this.TaskExtConfigController.getTaskExtConfigDefineBySchemakey(formSchemeDefine.getTaskKey(), formSchemeKey, "taskextension-multcheck");
        MultCheckConfigBean multCheckConfigBean = (MultCheckConfigBean)basicCheckItems.getExtInfoModel();
        List<Object> list = new ArrayList();
        if (multCheckConfigBean != null && multCheckConfigBean.getMultCheckEnable()) {
            list = this.iMultCheckTableService.getSchemeList(formSchemeKey);
        } else {
            HashMap<String, String> defaultScheme = new HashMap<String, String>();
            defaultScheme.put("s_key", "defaultScheme");
            defaultScheme.put("s_name", "\u9ed8\u8ba4\u5ba1\u6838\u65b9\u6848");
            defaultScheme.put("s_dw", "");
            list.add(defaultScheme);
        }
        return list;
    }

    @Override
    public List<IMultCheckItemBase> getDataAnalysis(String type) throws Exception {
        List<IMultCheckItemBase> groupTmp = this.buildDataAnalysisTree(type, "root");
        ArrayList<IMultCheckItemBase> groupItem = new ArrayList<IMultCheckItemBase>();
        for (IMultCheckItemBase itemBase : groupTmp) {
            if (itemBase.getChildren() == null) continue;
            groupItem.add(itemBase);
        }
        return groupItem;
    }

    @Override
    public AnalysisResultInfo getAnalysisResults(String asyncTaskId) throws Exception {
        AnalysisResultInfo results = (AnalysisResultInfo)new Gson().fromJson(this.asyncTaskManager.queryDetail(asyncTaskId).toString(), AnalysisResultInfo.class);
        return results;
    }

    @Override
    public String periodTransform(EntityCheckPeriodTransform periodTransform) {
        return this.getAssociatedPeriod(periodTransform.getPeriod(), periodTransform.getConfiguration(), periodTransform.getSpecified(), periodTransform.getLastIssue(), periodTransform.getNextIssue(), periodTransform.getPeriodOffset());
    }

    private void deleteResults(String tableName) throws SQLException {
        GregorianCalendar c = new GregorianCalendar();
        Date date = new Date();
        c.setTime(date);
        ((Calendar)c).add(13, -259200);
        Date dateBefore = c.getTime();
        String libTableName = tableName;
        String sql = "delete  from " + libTableName + " where zhsh_updatetime <=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.jdbcTpl.getDataSource());
        jdbcTemplate.update(sql, pss -> pss.setDate(1, new java.sql.Date(dateBefore.getTime())));
    }

    private String getResultTableName(String formSchemeKey) {
        FormSchemeDefine scheme = this.runtimeViewController.getFormScheme(formSchemeKey);
        String str = String.format("%s%s", "SYS_MULTCHECK_", scheme.getFormSchemeCode());
        return str;
    }

    private List<IMultCheckItemBase> buildDataAnalysisTree(String type, String forderid) throws Exception {
        ArrayList<IMultCheckItemBase> groupItem = new ArrayList<IMultCheckItemBase>();
        List treeNodeFolders = this.resourceTreeNodeService.getChildren(forderid);
        for (ResourceTreeNode resourceTreeNode : treeNodeFolders) {
            MultCheckItemImpl group0 = null;
            ArrayList<MultCheckItemImpl> childrenItem = null;
            if (resourceTreeNode.isFolder()) {
                List childTreeNodes = this.resourceTreeNodeService.getChildren(resourceTreeNode.getGuid());
                if (childTreeNodes.size() == 0) continue;
                for (ResourceTreeNode childTreeNode : childTreeNodes) {
                    if (childTreeNode.isFolder()) {
                        MultCheckItemImpl childGroup = new MultCheckItemImpl(childTreeNode.getGuid(), type, childTreeNode.getTitle());
                        List<IMultCheckItemBase> childGroupList = this.buildDataAnalysisTree(type, childTreeNode.getGuid());
                        if (childGroupList.size() <= 0) continue;
                        childGroup.setChildren(childGroupList);
                        if (childrenItem == null) {
                            childrenItem = new ArrayList<MultCheckItemImpl>();
                        }
                        childrenItem.add(childGroup);
                        continue;
                    }
                    if (childTreeNode.getType().equals("com.jiuqi.nvwa.dataanalyze")) continue;
                    String checkType = childTreeNode.getType();
                    if (type.equals("dataAnalysis") && !checkType.equals("com.jiuqi.nvwa.quickreport.business") || type.equals("zbQueryTemplate") && !checkType.equals("com.jiuqi.nr.zbquery.manage")) continue;
                    MultCheckItemImpl item0 = new MultCheckItemImpl(childTreeNode.getGuid(), type, childTreeNode.getTitle());
                    if (childrenItem == null) {
                        childrenItem = new ArrayList();
                    }
                    childrenItem.add(item0);
                }
            }
            String leafNode = resourceTreeNode.getType();
            if (type.equals("dataAnalysis") && !resourceTreeNode.getParent().equals("root") && !leafNode.equals("com.jiuqi.nvwa.quickreport.business") || type.equals("zbQueryTemplate") && !resourceTreeNode.getParent().equals("root") && !leafNode.equals("com.jiuqi.nr.zbquery.manage")) continue;
            group0 = new MultCheckItemImpl(resourceTreeNode.getGuid(), type, resourceTreeNode.getTitle());
            group0.setChildren(childrenItem);
            groupItem.add(group0);
        }
        return groupItem;
    }

    private Map<String, AsyncTask> queryTasks(List<String> taskIds) {
        AsyncTaskManager asyncTaskManager = (AsyncTaskManager)BeanUtils.getBean(AsyncTaskManager.class);
        HashMap<String, AsyncTask> tasks = new HashMap<String, AsyncTask>();
        for (String taskId : taskIds) {
            AsyncTask asyncTask = asyncTaskManager.queryTask(taskId);
            tasks.put(taskId, asyncTask);
        }
        return tasks;
    }
}

