/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.internal.LinkedTreeMap
 *  com.google.gson.reflect.TypeToken
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.bean.JobInstanceBean
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil
 *  com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil
 *  com.jiuqi.nr.bpm.setting.utils.SettingUtil
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.NodeCheckInfo
 *  com.jiuqi.nr.dataentry.bean.NodeCheckResultInfo
 *  com.jiuqi.nr.dataentry.bean.NodeCheckResultItem
 *  com.jiuqi.nr.dataentry.internal.service.FormGroupProvider
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo
 *  com.jiuqi.nr.dataentry.paramInfo.BatchDimensionValueFormInfo
 *  com.jiuqi.nr.dataentry.paramInfo.DimensionValueFormInfo
 *  com.jiuqi.nr.dataentry.service.IBatchCheckResultService
 *  com.jiuqi.nr.dataentry.util.Consts$FormAccessLevel
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.designer.common.NrDesignLogHelper
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  javax.annotation.Resource
 *  org.docx4j.com.google.common.collect.Maps
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.setting.utils.SettingUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.NodeCheckInfo;
import com.jiuqi.nr.dataentry.bean.NodeCheckResultInfo;
import com.jiuqi.nr.dataentry.bean.NodeCheckResultItem;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchDimensionValueFormInfo;
import com.jiuqi.nr.dataentry.paramInfo.DimensionValueFormInfo;
import com.jiuqi.nr.dataentry.service.IBatchCheckResultService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFileSizeCheckReturnInfo;
import com.jiuqi.nr.finalaccountsaudit.common.DataQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckResultInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.service.IEnumDataCheckService;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.ExplainInfoCheckReturnInfo;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.IntegrityDataInfo;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.controller.IIntegrityCheckController;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.AnalysisResultInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.LastCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.MultCheckItem;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.MultCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.OneKeyCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.MultCheckEnum;
import com.jiuqi.nr.finalaccountsaudit.multcheck.dao.MultCheckDao;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.JsonUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.util.StringUtils;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import org.docx4j.com.google.common.collect.Maps;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MultCheckDaoImpl
implements MultCheckDao {
    private static final Logger logger = LoggerFactory.getLogger(MultCheckDaoImpl.class);
    @Resource
    AsyncTaskManager asyncTaskManager;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    CommonUtil commonUtil;
    @Autowired
    DimensionUtil dimensionUtil;
    @Autowired
    IIntegrityCheckController integrityCheckController;
    @Autowired
    IJtableParamService jtableParamService;
    @Resource
    IRunTimeViewController viewCtrl;
    @Autowired
    FormGroupProvider formGroupProvider;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IDataDefinitionDesignTimeController designTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataController;
    @Autowired
    IBatchCheckResultService batchCheckService;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    IEnumDataCheckService enumDataCheckService;
    @Resource
    private JdbcTemplate jdbcTpl;
    @Autowired
    public IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private EntityQueryHelper entityQueryHelper;
    @Autowired
    DataQueryHelper dataQueryHelper;
    private String tableName;

    @Override
    public boolean insertMultCheckResult(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo, AsyncTaskMonitor asyncTaskMonitor) {
        try {
            switch (multCheckItem.getCheckType()) {
                case "enumCheck": {
                    this.saveEnumCheckResult(asyncTaskId, multCheckItem, oneKeyCheckInfo, asyncTaskMonitor);
                    break;
                }
                case "integrityForm": {
                    this.saveIntegrityFormResult(asyncTaskId, multCheckItem, oneKeyCheckInfo, asyncTaskMonitor);
                    break;
                }
                case "nodeCheck": {
                    this.saveNodeCheckResult(asyncTaskId, multCheckItem, oneKeyCheckInfo, asyncTaskMonitor);
                    break;
                }
                case "entityCheck": {
                    this.saveEntityCheckResult(asyncTaskId, multCheckItem, oneKeyCheckInfo, asyncTaskMonitor);
                    break;
                }
                case "errorDescCheck": {
                    this.saveErrorDescCheck(asyncTaskId, multCheckItem, oneKeyCheckInfo, asyncTaskMonitor);
                    break;
                }
                case "attachmentCheck": {
                    this.saveAttachmentCheck(asyncTaskId, multCheckItem, oneKeyCheckInfo, asyncTaskMonitor);
                    break;
                }
                case "queryTemplate": {
                    this.saveQueryCheckResult(asyncTaskId, multCheckItem, oneKeyCheckInfo, asyncTaskMonitor);
                    break;
                }
                case "entityTreeCheck": {
                    this.saveEntityTreeCheckResult(asyncTaskId, multCheckItem, oneKeyCheckInfo, asyncTaskMonitor);
                    break;
                }
                case "zbQueryTemplate": 
                case "dataAnalysis": {
                    this.saveZBQueryCheckResult(asyncTaskId, multCheckItem, oneKeyCheckInfo, asyncTaskMonitor);
                    break;
                }
                default: {
                    this.saveFormulaCheckResult(asyncTaskId, multCheckItem, oneKeyCheckInfo, asyncTaskMonitor);
                    break;
                }
            }
        }
        catch (Exception e) {
            logger.error("\u4fdd\u5b58" + multCheckItem.getName() + "\u7ed3\u679c\u65f6\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
        return true;
    }

    private void saveEntityTreeCheckResult(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo, AsyncTaskMonitor asyncTaskMonitor) {
        TaskState state = this.asyncTaskManager.queryTaskState(asyncTaskId);
        StringBuffer htmlStr = new StringBuffer();
        int isPass = 2;
        if (state.getValue() != TaskState.FINISHED.getValue()) {
            htmlStr.append("<div>").append("<span>").append(multCheckItem.getName() + state.getTitle()).append("</span>").append("</div>");
        } else {
            int errorCount = (Integer)this.asyncTaskManager.queryDetail(asyncTaskId);
            if (errorCount > 0) {
                htmlStr.append("<div>").append("<div class='left'><span>\u68c0\u67e5\u6709\u8bef</span></div>").append("<div class='right'><a id='").append(multCheckItem.getId()).append("'>\u70b9\u51fb\u67e5\u770b</a></div>").append("</div>").append("<div>").append("<span>\u6811\u5f62\u68c0\u67e5\u9519\u8bef\u6570\u5171</span>").append("<span class='word'>").append(errorCount).append("</span>").append("<span>\u4e2a</span>").append("</div>");
                isPass = 0;
            } else {
                isPass = 1;
                htmlStr.append("<div>").append("<span>\u5ba1\u6838\u901a\u8fc7</span>").append("</div>");
            }
        }
        this.tableName = this.getResultTableName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> paramValues = new ArrayList<Object>();
        sql.append("insert into ").append(this.tableName).append(" \n ");
        sql.append("(").append("ZHSH_KEY").append(",").append("ZHSH_ASYNCTASKID").append(",").append("ZHSH_CHECKITEMASYNCTASKID").append(",").append("ZHSH_CHECKITEMKEY").append(",").append("ZHSH_CHECKITEMNAME").append(",").append("ZHSH_FORMSCHEMEKEY").append(",").append("ZHSH_CHECKTYPE").append(",").append("ZHSH_CHECKPARAMS").append(",").append("ZHSH_CHECKRESULT").append(",").append("ZHSH_CHECKDETAIL").append(",").append("ZHSH_ISPASS").append(",").append("ZHSH_ORDER").append(",").append("ZHSH_UPDATETIME").append(",").append("ZHSH_OPERATOR").append(") \n");
        sql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
        paramValues.add(UUID.randomUUID().toString());
        paramValues.add(asyncTaskMonitor.getTaskId());
        paramValues.add(asyncTaskId);
        paramValues.add(multCheckItem.getId());
        paramValues.add(multCheckItem.getName());
        paramValues.add(oneKeyCheckInfo.getContext().getFormSchemeKey());
        paramValues.add(multCheckItem.getCheckType());
        paramValues.add(multCheckItem.getCheckParams());
        paramValues.add(htmlStr);
        paramValues.add(JsonUtil.objectToJson((Object)this.asyncTaskManager.queryDetail(asyncTaskId)));
        paramValues.add(isPass);
        paramValues.add(multCheckItem.getIndex());
        Date date = new Date();
        paramValues.add(date);
        paramValues.add(SettingUtil.getCurrentUser().getName());
        this.jdbcTemplate.update(sql.toString(), paramValues.toArray());
    }

    private void saveAttachmentCheck(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo, AsyncTaskMonitor asyncTaskMonitor) {
        this.filterDW(multCheckItem, oneKeyCheckInfo);
        TaskState state = this.asyncTaskManager.queryTaskState(asyncTaskId);
        StringBuffer htmlStr = new StringBuffer();
        int isPass = 2;
        if (state.getValue() != TaskState.FINISHED.getValue()) {
            htmlStr.append("<div>").append("<span>").append(multCheckItem.getName() + state.getTitle()).append("</span>").append("</div>");
        } else {
            BlobFileSizeCheckReturnInfo blobFileSizeCheckReturnInfo = (BlobFileSizeCheckReturnInfo)this.asyncTaskManager.queryDetail(asyncTaskId);
            if (blobFileSizeCheckReturnInfo.getErrItems().size() <= 0) {
                isPass = 1;
                htmlStr.append("<div class='checkPass'>").append("<span>\u5ba1\u6838\u901a\u8fc7</span>").append("</div>").append("<div>").append("<span>\u5355\u4f4d\u5171</span>").append("<span class='word'>").append(blobFileSizeCheckReturnInfo.getUnitCount()).append("</span>").append("<span>\u6237\uff0c</span>").append("<span>\u4e0d\u901a\u8fc7</span>").append("<span class='word'>").append(blobFileSizeCheckReturnInfo.getErrUnitCount()).append("</span>").append("<span>\u6237</span>").append("</div>").append("<div>").append("<span>\u6307\u6807\u5171</span>").append("<span class='word'>").append(blobFileSizeCheckReturnInfo.getSelZBCount()).append("</span>").append("<span>\u4e2a\uff0c</span>").append("<span>\u4e0d\u5408\u89c4\u8303</span>").append("<span class='word'>").append(blobFileSizeCheckReturnInfo.getErrFileCount()).append("</span>").append("<span>\u4e2a</span>").append("</div>");
            } else {
                isPass = 0;
                htmlStr.append("<div>").append("<div class='left'><span>\u5ba1\u6838\u6709\u8bef</span></div>").append("<div class='right'><a id='").append(multCheckItem.getId()).append("'>\u70b9\u51fb\u67e5\u770b</a></div>").append("</div>").append("<div>").append("<span>\u5355\u4f4d\u5171</span>").append("<span class='word'>").append(blobFileSizeCheckReturnInfo.getUnitCount()).append("</span>").append("<span>\u6237\uff0c</span>").append("<span>\u4e0d\u901a\u8fc7</span>").append("<span class='word'>").append(blobFileSizeCheckReturnInfo.getErrUnitCount()).append("</span>").append("<span>\u6237</span>").append("</div>").append("<div>").append("<span>\u6307\u6807\u5171</span>").append("<span class='word'>").append(blobFileSizeCheckReturnInfo.getSelZBCount()).append("</span>").append("<span>\u4e2a\uff0c</span>").append("<span>\u4e0d\u5408\u89c4\u8303</span>").append("<span class='word'>").append(blobFileSizeCheckReturnInfo.getErrFileCount()).append("</span>").append("<span>\u4e2a</span>").append("</div>");
            }
        }
        this.tableName = this.getResultTableName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> paramValues = new ArrayList<Object>();
        sql.append("insert into ").append(this.tableName).append(" \n ");
        sql.append("(").append("ZHSH_KEY").append(",").append("ZHSH_ASYNCTASKID").append(",").append("ZHSH_CHECKITEMASYNCTASKID").append(",").append("ZHSH_CHECKITEMKEY").append(",").append("ZHSH_CHECKITEMNAME").append(",").append("ZHSH_FORMSCHEMEKEY").append(",").append("ZHSH_CHECKTYPE").append(",").append("ZHSH_CHECKPARAMS").append(",").append("ZHSH_CHECKRESULT").append(",").append("ZHSH_CHECKDETAIL").append(",").append("ZHSH_ISPASS").append(",").append("ZHSH_ORDER").append(",").append("ZHSH_UPDATETIME").append(",").append("ZHSH_OPERATOR").append(") \n");
        sql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
        paramValues.add(UUID.randomUUID().toString());
        paramValues.add(asyncTaskMonitor.getTaskId());
        paramValues.add(asyncTaskId);
        paramValues.add(multCheckItem.getId());
        paramValues.add(multCheckItem.getName());
        paramValues.add(oneKeyCheckInfo.getContext().getFormSchemeKey());
        paramValues.add(multCheckItem.getCheckType());
        paramValues.add(multCheckItem.getCheckParams());
        paramValues.add(htmlStr);
        paramValues.add(JsonUtil.objectToJson((Object)this.asyncTaskManager.queryDetail(asyncTaskId)));
        paramValues.add(isPass);
        paramValues.add(multCheckItem.getIndex());
        Date date = new Date();
        paramValues.add(date);
        paramValues.add(SettingUtil.getCurrentUser().getName());
        this.jdbcTemplate.update(sql.toString(), paramValues.toArray());
    }

    private void saveErrorDescCheck(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo, AsyncTaskMonitor asyncTaskMonitor) {
        this.filterDW(multCheckItem, oneKeyCheckInfo);
        TaskState state = this.asyncTaskManager.queryTaskState(asyncTaskId);
        StringBuffer htmlStr = new StringBuffer();
        int isPass = 2;
        if (state.getValue() != TaskState.FINISHED.getValue()) {
            htmlStr.append("<div>").append("<span>").append(multCheckItem.getName() + state.getTitle()).append("</span>").append("</div>");
        } else {
            ExplainInfoCheckReturnInfo explainInfoCheckReturnInfo = (ExplainInfoCheckReturnInfo)this.asyncTaskManager.queryDetail(asyncTaskId);
            if (explainInfoCheckReturnInfo.getErrUnitCount() > 0) {
                htmlStr.append("<div>").append("<div class='left'><span>\u5ba1\u6838\u6709\u8bef</span></div>").append("<div class='right'><a id='").append(multCheckItem.getId()).append("'>\u70b9\u51fb\u67e5\u770b</a></div>").append("<div>").append("<span>\u5355\u4f4d\u5171</span>").append("<span class='word'>").append(explainInfoCheckReturnInfo.getTotalUnitCount()).append("</span>").append("<span>\u6237\uff0c</span>").append("<span>\u6709\u8bef</span>").append("<span class='word'>").append(explainInfoCheckReturnInfo.getErrUnitCount()).append("</span>").append("<span>\u6237</span>").append("</div>").append("</div>");
                isPass = 0;
            } else {
                isPass = 1;
                htmlStr.append("<div>").append("<span>\u5ba1\u6838\u901a\u8fc7</span>").append("</div>");
            }
        }
        this.tableName = this.getResultTableName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> paramValues = new ArrayList<Object>();
        sql.append("insert into ").append(this.tableName).append(" \n ");
        sql.append("(").append("ZHSH_KEY").append(",").append("ZHSH_ASYNCTASKID").append(",").append("ZHSH_CHECKITEMASYNCTASKID").append(",").append("ZHSH_CHECKITEMKEY").append(",").append("ZHSH_CHECKITEMNAME").append(",").append("ZHSH_FORMSCHEMEKEY").append(",").append("ZHSH_CHECKTYPE").append(",").append("ZHSH_CHECKPARAMS").append(",").append("ZHSH_CHECKRESULT").append(",").append("ZHSH_CHECKDETAIL").append(",").append("ZHSH_ISPASS").append(",").append("ZHSH_ORDER").append(",").append("ZHSH_UPDATETIME").append(",").append("ZHSH_OPERATOR").append(") \n");
        sql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
        paramValues.add(UUID.randomUUID().toString());
        paramValues.add(asyncTaskMonitor.getTaskId());
        paramValues.add(asyncTaskId);
        paramValues.add(multCheckItem.getId());
        paramValues.add(multCheckItem.getName());
        paramValues.add(oneKeyCheckInfo.getContext().getFormSchemeKey());
        paramValues.add(multCheckItem.getCheckType());
        paramValues.add(multCheckItem.getCheckParams());
        paramValues.add(htmlStr);
        paramValues.add(JsonUtil.objectToJson((Object)this.asyncTaskManager.queryDetail(asyncTaskId)));
        paramValues.add(isPass);
        paramValues.add(multCheckItem.getIndex());
        Date date = new Date();
        paramValues.add(date);
        paramValues.add(SettingUtil.getCurrentUser().getName());
        this.jdbcTemplate.update(sql.toString(), paramValues.toArray());
    }

    private void saveQueryCheckResult(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo, AsyncTaskMonitor asyncTaskMonitor) {
        TaskState state = this.asyncTaskManager.queryTaskState(asyncTaskId);
        StringBuffer htmlStr = new StringBuffer();
        int isPass = 2;
        if (state.getValue() != TaskState.FINISHED.getValue()) {
            htmlStr.append("<div>").append("<span>").append(multCheckItem.getName() + state.getTitle()).append("</span>").append("</div>");
        } else {
            Map result = (Map)this.asyncTaskManager.queryDetail(asyncTaskId);
            if (((Boolean)result.get(multCheckItem.getKey())).booleanValue()) {
                htmlStr.append("<div>").append("<div class='left'><span>\u5df2\u5ba1\u6838\uff0c\u7ed3\u679c\u53ef\u80fd\u6709\u8bef</span></div>").append("<div class='right'><a id='").append(multCheckItem.getId()).append("'>\u70b9\u51fb\u67e5\u770b</a></div>").append("</div>");
            } else {
                isPass = 1;
                htmlStr.append("<div>").append("<span>\u5ba1\u6838\u901a\u8fc7</span>").append("</div>");
            }
        }
        this.tableName = this.getResultTableName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> paramValues = new ArrayList<Object>();
        sql.append("insert into ").append(this.tableName).append(" \n ");
        sql.append("(").append("ZHSH_KEY").append(",").append("ZHSH_ASYNCTASKID").append(",").append("ZHSH_CHECKITEMASYNCTASKID").append(",").append("ZHSH_CHECKITEMKEY").append(",").append("ZHSH_CHECKITEMNAME").append(",").append("ZHSH_FORMSCHEMEKEY").append(",").append("ZHSH_CHECKTYPE").append(",").append("ZHSH_CHECKPARAMS").append(",").append("ZHSH_CHECKRESULT").append(",").append("ZHSH_CHECKDETAIL").append(",").append("ZHSH_ISPASS").append(",").append("ZHSH_ORDER").append(",").append("ZHSH_UPDATETIME").append(",").append("ZHSH_OPERATOR").append(") \n");
        sql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
        paramValues.add(UUID.randomUUID().toString());
        paramValues.add(asyncTaskMonitor.getTaskId());
        paramValues.add(asyncTaskId);
        paramValues.add(multCheckItem.getId());
        paramValues.add(multCheckItem.getName());
        paramValues.add(oneKeyCheckInfo.getContext().getFormSchemeKey());
        paramValues.add(multCheckItem.getCheckType());
        paramValues.add(multCheckItem.getCheckParams());
        paramValues.add(htmlStr);
        paramValues.add(JsonUtil.objectToJson((Object)this.asyncTaskManager.queryDetail(asyncTaskId)));
        paramValues.add(isPass);
        paramValues.add(multCheckItem.getIndex());
        Date date = new Date();
        paramValues.add(date);
        paramValues.add(SettingUtil.getCurrentUser().getName());
        this.jdbcTemplate.update(sql.toString(), paramValues.toArray());
    }

    private void saveZBQueryCheckResult(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo, AsyncTaskMonitor asyncTaskMonitor) {
        TaskState state = this.asyncTaskManager.queryTaskState(asyncTaskId);
        StringBuffer htmlStr = new StringBuffer();
        int isPass = 2;
        if (state.getValue() != TaskState.FINISHED.getValue()) {
            htmlStr.append("<div>").append("<span>").append(multCheckItem.getName() + state.getTitle()).append("</span>").append("</div>");
        } else {
            AnalysisResultInfo results = (AnalysisResultInfo)new Gson().fromJson(this.asyncTaskManager.queryDetail(asyncTaskId).toString(), AnalysisResultInfo.class);
            if (!results.getResult()) {
                htmlStr.append("<div>").append("<div class='left'><span>\u5df2\u5ba1\u6838\uff0c\u7ed3\u679c\u53ef\u80fd\u6709\u8bef</span></div>").append("<div class='right'><a id='").append(multCheckItem.getId()).append("'>\u70b9\u51fb\u67e5\u770b</a></div>").append("</div>");
            } else {
                isPass = 1;
                htmlStr.append("<div>").append("<span>\u5ba1\u6838\u901a\u8fc7</span>").append("</div>");
            }
        }
        this.tableName = this.getResultTableName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> paramValues = new ArrayList<Object>();
        sql.append("insert into ").append(this.tableName).append(" \n ");
        sql.append("(").append("ZHSH_KEY").append(",").append("ZHSH_ASYNCTASKID").append(",").append("ZHSH_CHECKITEMASYNCTASKID").append(",").append("ZHSH_CHECKITEMKEY").append(",").append("ZHSH_CHECKITEMNAME").append(",").append("ZHSH_FORMSCHEMEKEY").append(",").append("ZHSH_CHECKTYPE").append(",").append("ZHSH_CHECKPARAMS").append(",").append("ZHSH_CHECKRESULT").append(",").append("ZHSH_CHECKDETAIL").append(",").append("ZHSH_ISPASS").append(",").append("ZHSH_ORDER").append(",").append("ZHSH_UPDATETIME").append(",").append("ZHSH_OPERATOR").append(") \n");
        sql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
        paramValues.add(UUID.randomUUID().toString());
        paramValues.add(asyncTaskMonitor.getTaskId());
        paramValues.add(asyncTaskId);
        paramValues.add(multCheckItem.getId());
        paramValues.add(multCheckItem.getName());
        paramValues.add(oneKeyCheckInfo.getContext().getFormSchemeKey());
        paramValues.add(multCheckItem.getCheckType());
        paramValues.add(multCheckItem.getCheckParams());
        paramValues.add(htmlStr);
        paramValues.add(JsonUtil.objectToJson((Object)this.asyncTaskManager.queryDetail(asyncTaskId)));
        paramValues.add(isPass);
        paramValues.add(multCheckItem.getIndex());
        Date date = new Date();
        paramValues.add(date);
        paramValues.add(SettingUtil.getCurrentUser().getName());
        this.jdbcTemplate.update(sql.toString(), paramValues.toArray());
    }

    private void saveEntityCheckResult(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo, AsyncTaskMonitor asyncTaskMonitor) {
        TaskState state = this.asyncTaskManager.queryTaskState(asyncTaskId);
        StringBuffer htmlStr = new StringBuffer();
        int isPass = 2;
        ObjectMapper objectMapper = new ObjectMapper();
        if (state.getValue() != TaskState.FINISHED.getValue()) {
            String msg = this.asyncTaskManager.queryResult(asyncTaskId);
            htmlStr.append("<div class='checkPass'>").append(msg.equals("") ? state.getTitle() : msg).append("</div>");
        } else {
            JSONObject multCheckObj = new JSONObject(this.asyncTaskManager.queryDetail(asyncTaskId).toString());
            int jsdwCount = multCheckObj.getInt("jsdwCount");
            int ywdwCount = multCheckObj.getInt("ywdwCount");
            int xzdwCount = multCheckObj.getInt("xzdwCount");
            int dqdwCount = multCheckObj.getInt("dqdwCount");
            int yzdwCount = multCheckObj.getInt("yzdwCount");
            if (ywdwCount > 0) {
                htmlStr.append("<div>").append("<div class='left'><span>\u5df2\u6838\u5bf9\uff0c\u5b58\u5728\u5355\u4f4d\u53d8\u52a8</span></div>").append("<div class='right'><a id='").append(multCheckItem.getId()).append("'>\u70b9\u51fb\u67e5\u770b</a></div>").append("</div>").append("<div>").append("<span>\u5171\u8ba1</span>").append("<span class='word'>").append(dqdwCount).append("</span>").append("<span>\u5bb6\u5355\u4f4d\uff0c</span>").append("<span>\u65b0\u589e</span>").append("<span class='word'>").append(xzdwCount).append("</span>").append("<span>\u5bb6\uff0c</span>").append("<span>\u51cf\u5c11</span>").append("<span class='word'>").append(jsdwCount).append("</span>").append("<span>\u5bb6\uff0c</span>").append("<span>\u6709\u8bef</span>").append("<span class='word'>").append(ywdwCount).append("</span>").append("<span>\u5bb6\uff0c</span>").append("<span>\u4fe1\u606f\u4e00\u81f4</span>").append("<span class='word'>").append(yzdwCount).append("</span>").append("<span>\u5bb6</span>").append("</div>");
            } else {
                isPass = 1;
                htmlStr.append("<div class='checkPass'>").append("\u5ba1\u6838\u901a\u8fc7").append("</div>");
            }
        }
        this.tableName = this.getResultTableName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> paramValues = new ArrayList<Object>();
        sql.append("insert into ").append(this.tableName).append(" \n ");
        sql.append("(").append("ZHSH_KEY").append(",").append("ZHSH_ASYNCTASKID").append(",").append("ZHSH_CHECKITEMASYNCTASKID").append(",").append("ZHSH_CHECKITEMKEY").append(",").append("ZHSH_CHECKITEMNAME").append(",").append("ZHSH_FORMSCHEMEKEY").append(",").append("ZHSH_CHECKTYPE").append(",").append("ZHSH_CHECKPARAMS").append(",").append("ZHSH_CHECKRESULT").append(",").append("ZHSH_CHECKDETAIL").append(",").append("ZHSH_ISPASS").append(",").append("ZHSH_ORDER").append(",").append("ZHSH_UPDATETIME").append(",").append("ZHSH_OPERATOR").append(") \n");
        sql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
        paramValues.add(UUID.randomUUID().toString());
        paramValues.add(asyncTaskMonitor.getTaskId());
        paramValues.add(asyncTaskId);
        paramValues.add(multCheckItem.getId());
        paramValues.add(multCheckItem.getName());
        paramValues.add(oneKeyCheckInfo.getContext().getFormSchemeKey());
        paramValues.add(multCheckItem.getCheckType());
        paramValues.add(multCheckItem.getCheckParams());
        paramValues.add(htmlStr);
        paramValues.add(JsonUtil.objectToJson((Object)this.asyncTaskManager.queryDetail(asyncTaskId)));
        paramValues.add(isPass);
        paramValues.add(multCheckItem.getIndex());
        Date date = new Date();
        paramValues.add(date);
        paramValues.add(SettingUtil.getCurrentUser().getName());
        this.jdbcTemplate.update(sql.toString(), paramValues.toArray());
    }

    private void saveNodeCheckResult(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo, AsyncTaskMonitor asyncTaskMonitor) throws JsonProcessingException {
        Gson gson = new Gson();
        NodeCheckInfo nodeCheckInfo = (NodeCheckInfo)gson.fromJson(multCheckItem.getCheckParams(), NodeCheckInfo.class);
        String currEntityTitle = this.getCurrentEntity(nodeCheckInfo.getContext());
        StringBuffer htmlStr = new StringBuffer();
        int isPass = 0;
        NodeCheckResultInfo nodecheckResult = this.nodecheckResult(asyncTaskId);
        if (nodecheckResult == null) {
            isPass = 2;
            htmlStr.append("<div class='checkPass'>").append("\u5ba1\u6838\u5f02\u5e38\uff0c\u672a\u83b7\u53d6\u5230\u5ba1\u6838\u7ed3\u679c").append("</div>").append("<div>").append("<span>\u68c0\u67e5\u8282\u70b9\u4e3a</span>").append("<span>").append(currEntityTitle).append("</span>").append("</div>");
        } else if (nodecheckResult.getNodeCheckResult() == null || nodecheckResult.getNodeCheckResult().size() <= 0) {
            isPass = 1;
            htmlStr.append("<div class='checkPass'>").append("\u5ba1\u6838\u901a\u8fc7").append("</div>").append("<div>").append("<span>\u68c0\u67e5\u8282\u70b9\u4e3a</span>").append("<span>").append(currEntityTitle).append("</span>").append("</div>");
        } else {
            HashSet<String> errorFields = new HashSet<String>();
            HashSet<String> errorForms = new HashSet<String>();
            int errorCount = 0;
            for (Map.Entry entry : nodecheckResult.getNodeCheckResult().entrySet()) {
                for (NodeCheckResultItem item : (List)entry.getValue()) {
                    if (!errorFields.contains(item.getFieldCode())) {
                        errorFields.add(item.getFieldCode());
                    }
                    if (errorForms.contains(item.getNodeCheckFieldMessage().getFormKey())) continue;
                    errorForms.add(item.getNodeCheckFieldMessage().getFormKey());
                }
                errorCount += ((List)entry.getValue()).size();
            }
            htmlStr.append("<div>").append("<div class='left'><span>\u5ba1\u6838\u6709\u8bef\uff0c\u5171</span>").append("<span class='word'>").append(errorCount).append("</span>").append("<span>\u6761\u9519\u8bef\u8bb0\u5f55\u3002</span></div>").append("<div class='right'><a id='").append(multCheckItem.getId()).append("'>\u70b9\u51fb\u67e5\u770b</a></div>").append("</div>").append("<div>").append("<span>\u68c0\u67e5\u8282\u70b9\u4e3a\uff1a</span>").append("<span>").append(currEntityTitle).append("</span>").append("</div>").append("<div>").append("<span>\u6709\u8bef\u6307\u6807\u5171</span>").append("<span class='word'>").append(errorFields.size()).append("</span>").append("<span>\u4e2a\uff0c</span>").append("<span>\u6d89\u53ca\u62a5\u8868</span>").append("<span class='word'>").append(errorForms.size()).append("</span>").append("<span>\u5f20</span>").append("</div>");
        }
        this.tableName = this.getResultTableName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> paramValues = new ArrayList<Object>();
        sql.append("insert into ").append(this.tableName).append(" \n ");
        sql.append("(").append("ZHSH_KEY").append(",").append("ZHSH_ASYNCTASKID").append(",").append("ZHSH_CHECKITEMASYNCTASKID").append(",").append("ZHSH_CHECKITEMKEY").append(",").append("ZHSH_CHECKITEMNAME").append(",").append("ZHSH_FORMSCHEMEKEY").append(",").append("ZHSH_CHECKTYPE").append(",").append("ZHSH_CHECKPARAMS").append(",").append("ZHSH_CHECKRESULT").append(",").append("ZHSH_CHECKDETAIL").append(",").append("ZHSH_ISPASS").append(",").append("ZHSH_ORDER").append(",").append("ZHSH_UPDATETIME").append(",").append("ZHSH_OPERATOR").append(") \n");
        sql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
        paramValues.add(UUID.randomUUID().toString());
        paramValues.add(asyncTaskMonitor.getTaskId());
        paramValues.add(asyncTaskId);
        paramValues.add(multCheckItem.getId());
        paramValues.add(multCheckItem.getName());
        paramValues.add(oneKeyCheckInfo.getContext().getFormSchemeKey());
        paramValues.add(multCheckItem.getCheckType());
        paramValues.add(multCheckItem.getCheckParams());
        paramValues.add(htmlStr);
        paramValues.add("");
        paramValues.add(isPass);
        paramValues.add(multCheckItem.getIndex());
        Date date = new Date();
        paramValues.add(date);
        paramValues.add(SettingUtil.getCurrentUser().getName());
        this.jdbcTemplate.update(sql.toString(), paramValues.toArray());
    }

    public NodeCheckResultInfo nodecheckResult(String asyncTaskID) throws JsonProcessingException {
        Object strResult = this.asyncTaskManager.queryDetail(asyncTaskID);
        if (null != strResult) {
            return (NodeCheckResultInfo)JSONUtil.parseObject((String)this.asyncTaskManager.queryDetail(asyncTaskID).toString(), NodeCheckResultInfo.class);
        }
        return null;
    }

    private void filterDW(MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo) {
        HashMap checkScope = new HashMap();
        if (oneKeyCheckInfo.getBeforeUpload() != null && oneKeyCheckInfo.getBeforeUpload().booleanValue()) {
            if (multCheckItem.getItemSetting() != null) {
                if (multCheckItem.getItemSetting() instanceof LinkedTreeMap) {
                    for (Iterator<Object> key : ((LinkedTreeMap)multCheckItem.getItemSetting()).keySet()) {
                        checkScope.put(key, ((LinkedTreeMap)multCheckItem.getItemSetting()).get(key));
                    }
                } else {
                    checkScope = Maps.newHashMap();
                    BeanMap beanMap = BeanMap.create(multCheckItem.getItemSetting());
                    for (Object key : beanMap.keySet()) {
                        checkScope.put(key + "", beanMap.get(key));
                    }
                }
            }
        } else {
            checkScope = (HashMap)multCheckItem.getItemSetting();
        }
        if (checkScope != null && checkScope.containsKey("unitList")) {
            String mainDimName = this.dimensionUtil.getDwMainDimName(oneKeyCheckInfo.getContext().getFormSchemeKey());
            ArrayList unitList = (ArrayList)checkScope.get("unitList");
            StringBuffer unitStr = new StringBuffer();
            for (String unit : unitList) {
                unitStr.append(unit).append(";");
            }
            if (unitStr.length() > 0) {
                unitStr.deleteCharAt(unitStr.length() - 1);
            }
            oneKeyCheckInfo.getSelectedDimensionSet().get(mainDimName).setValue(unitStr.toString());
        }
    }

    private void saveFormulaCheckResult(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo, AsyncTaskMonitor asyncTaskMonitor) {
        this.filterDW(multCheckItem, oneKeyCheckInfo);
        FormulaCheckReturnInfo batchCheckResult = null;
        StringBuffer htmlStr = new StringBuffer();
        int isPass = 0;
        int formulaCount = 0;
        HashSet<String> units = new HashSet<String>();
        HashSet<String> errorFormula = new HashSet<String>();
        try {
            Map formulas;
            Map<Iterator<Object>, Object> itemSetting;
            batchCheckResult = this.getFormulaCheckReturnInfo(oneKeyCheckInfo, multCheckItem, asyncTaskId);
            List results = batchCheckResult.getResults();
            int warnDescription = 0;
            Boolean beforeUpload = oneKeyCheckInfo.getBeforeUpload() != null && oneKeyCheckInfo.getBeforeUpload() != false;
            for (Object rt : results) {
                if (!units.contains(rt.getUnitKey())) {
                    units.add(rt.getUnitKey());
                }
                if (!errorFormula.contains(rt.getFormula().getCode())) {
                    errorFormula.add(rt.getFormula().getCode());
                }
                if (!beforeUpload.booleanValue() || rt.getFormula().getChecktype() != 2 || !StringUtils.isNotEmpty((String)rt.getDescriptionInfo().getDescription())) continue;
                ++warnDescription;
            }
            if (beforeUpload.booleanValue()) {
                itemSetting = Maps.newHashMap();
                if (multCheckItem.getItemSetting() != null) {
                    if (multCheckItem.getItemSetting() instanceof LinkedTreeMap) {
                        for (Iterator<Object> key : ((LinkedTreeMap)multCheckItem.getItemSetting()).keySet()) {
                            itemSetting.put(key, ((LinkedTreeMap)multCheckItem.getItemSetting()).get(key));
                        }
                    } else {
                        BeanMap beanMap = BeanMap.create(multCheckItem.getItemSetting());
                        for (Object object : beanMap.keySet()) {
                            itemSetting.put((Iterator<Object>)((Object)(object + "")), beanMap.get(object));
                        }
                    }
                }
            } else {
                itemSetting = (Map)multCheckItem.getItemSetting();
            }
            if ((formulas = (Map)itemSetting.get("formulaMap")) == null || formulas.size() == 0) {
                ArrayList forms = new ArrayList();
                String string = String.join((CharSequence)";", this.viewCtrl.queryAllFormKeysByFormScheme(oneKeyCheckInfo.getContext().getFormSchemeKey()));
                BatchDimensionValueFormInfo batchDimensionValueFormInfo = this.formGroupProvider.getForms(oneKeyCheckInfo.getContext(), string, Consts.FormAccessLevel.FORM_READ);
                List acessFormInfos = batchDimensionValueFormInfo.getAccessFormInfos();
                for (int formInfoIndex = 0; formInfoIndex < acessFormInfos.size(); ++formInfoIndex) {
                    DimensionValueFormInfo dimensionValueFormInfo = (DimensionValueFormInfo)acessFormInfos.get(formInfoIndex);
                    forms.addAll(dimensionValueFormInfo.getForms());
                }
                forms.add("betweenParsed");
                Iterator iterator = forms.iterator();
                while (iterator.hasNext()) {
                    String form = (String)iterator.next();
                    List formuls = this.formulaRunTimeController.getCheckFormulasInForm(multCheckItem.getKey(), form.equals("betweenParsed") ? null : form);
                    formulaCount += formuls.size();
                }
            } else {
                for (Map.Entry entry : formulas.entrySet()) {
                    if (((List)entry.getValue()).size() > 0) {
                        formulaCount += ((List)entry.getValue()).size();
                        continue;
                    }
                    String formKey = ((String)entry.getKey()).equals("betweenParsed") ? null : (String)entry.getKey();
                    List formuls = this.formulaRunTimeController.getCheckFormulasInForm(multCheckItem.getKey(), formKey);
                    formulaCount += formuls.size();
                }
            }
            List<String> fmdmKeys = this.getSelectedEntity(oneKeyCheckInfo);
            if (batchCheckResult.getTotalCount() <= 0) {
                isPass = 1;
                htmlStr.append("<div class='checkPass'>").append("\u5ba1\u6838\u901a\u8fc7").append("</div>").append("<div>").append("<span>\u5355\u4f4d\u5171</span>").append("<span class='word'>").append(fmdmKeys.size()).append("</span>").append("<span>\u6237\uff0c</span>").append("<span>\u4e0d\u901a\u8fc7</span>").append("<span class='word'>").append(units.size()).append("</span>").append("<span>\u6237</span>").append("</div>").append("<div>").append("<span>\u516c\u5f0f\u5171</span>").append("<span class='word'>").append(formulaCount).append("</span>").append("<span>\u6761\uff0c</span>").append("<span>\u4e0d\u901a\u8fc7</span>").append("<span class='word'>").append(errorFormula.size()).append("</span>").append("<span>\u6761</span>").append("</div>");
            } else {
                if (beforeUpload.booleanValue() && batchCheckResult.getErrorCount() <= 0 && warnDescription == batchCheckResult.getWarnCount()) {
                    isPass = 1;
                }
                htmlStr.append("<div>").append("<div class='left'><span>\u5ba1\u6838\u6709\u8bef\uff0c\u5171</span>").append("<span class='word'>").append(batchCheckResult.getTotalCount()).append("</span>").append("<span>\u6761\u9519\u8bef\u8bb0\u5f55</span></div>").append("<div class='right'><a id='").append(multCheckItem.getId()).append("'>\u70b9\u51fb\u67e5\u770b</a></div>").append("</div>").append("<div>").append("<span>\u5355\u4f4d\u5171</span>").append("<span class='word'>").append(fmdmKeys.size()).append("</span>").append("<span>\u6237\uff0c</span>").append("<span>\u4e0d\u901a\u8fc7</span>").append("<span class='word'>").append(units.size()).append("</span>").append("<span>\u6237</span>").append("</div>").append("<div>").append("<span>\u516c\u5f0f\u5171</span>").append("<span class='word'>").append(formulaCount).append("</span>").append("<span>\u6761\uff0c</span>").append("<span>\u4e0d\u901a\u8fc7</span>").append("<span class='word'>").append(errorFormula.size()).append("</span>").append("<span>\u6761</span>").append("</div>");
            }
        }
        catch (Exception e) {
            htmlStr.append("\u67e5\u8be2\u516c\u5f0f\u5ba1\u6838\u7ed3\u679c\u65f6\u5f02\u5e38\u7ed3\u675f\uff01");
        }
        this.tableName = this.getResultTableName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> paramValues = new ArrayList<Object>();
        sql.append("insert into ").append(this.tableName).append(" \n ");
        sql.append("(").append("ZHSH_KEY").append(",").append("ZHSH_ASYNCTASKID").append(",").append("ZHSH_CHECKITEMASYNCTASKID").append(",").append("ZHSH_CHECKITEMKEY").append(",").append("ZHSH_CHECKITEMNAME").append(",").append("ZHSH_FORMSCHEMEKEY").append(",").append("ZHSH_CHECKTYPE").append(",").append("ZHSH_CHECKPARAMS").append(",").append("ZHSH_CHECKRESULT").append(",").append("ZHSH_CHECKDETAIL").append(",").append("ZHSH_ISPASS").append(",").append("ZHSH_ORDER").append(",").append("ZHSH_UPDATETIME").append(",").append("ZHSH_OPERATOR").append(") \n");
        sql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
        paramValues.add(UUID.randomUUID().toString());
        paramValues.add(asyncTaskMonitor.getTaskId());
        paramValues.add(asyncTaskId);
        paramValues.add(multCheckItem.getId());
        paramValues.add(multCheckItem.getName());
        paramValues.add(oneKeyCheckInfo.getContext().getFormSchemeKey());
        paramValues.add(multCheckItem.getCheckType());
        paramValues.add(multCheckItem.getCheckParams());
        paramValues.add(htmlStr);
        paramValues.add(JsonUtil.objectToJson((Object)this.asyncTaskManager.queryDetail(asyncTaskId)));
        paramValues.add(isPass);
        paramValues.add(multCheckItem.getIndex());
        Date date = new Date();
        paramValues.add(date);
        paramValues.add(SettingUtil.getCurrentUser().getName());
        this.jdbcTemplate.update(sql.toString(), paramValues.toArray());
    }

    private FormulaCheckReturnInfo getFormulaCheckReturnInfo(OneKeyCheckInfo oneKeyCheckInfo, MultCheckItem item, String asyncTaskId) {
        JtableContext jtableContext = oneKeyCheckInfo.getContext();
        BatchCheckInfo batchCheckInfo = new BatchCheckInfo();
        jtableContext.setDimensionSet(oneKeyCheckInfo.getSelectedDimensionSet());
        jtableContext.setFormulaSchemeKey(item.getKey());
        batchCheckInfo.setContext(jtableContext);
        batchCheckInfo.setFormulaSchemeKeys(item.getKey());
        batchCheckInfo.setAsyncTaskKey(asyncTaskId);
        Map<Iterator<Object>, Object> itemSetting = new HashMap();
        if (oneKeyCheckInfo.getBeforeUpload() != null && oneKeyCheckInfo.getBeforeUpload().booleanValue()) {
            if (item.getItemSetting() != null) {
                if (item.getItemSetting() instanceof LinkedTreeMap) {
                    for (Iterator<Object> key : ((LinkedTreeMap)item.getItemSetting()).keySet()) {
                        itemSetting.put(key, ((LinkedTreeMap)item.getItemSetting()).get(key));
                    }
                } else {
                    itemSetting = Maps.newHashMap();
                    BeanMap beanMap = BeanMap.create(item.getItemSetting());
                    for (Object key : beanMap.keySet()) {
                        itemSetting.put((Iterator<Object>)((Object)(key + "")), beanMap.get(key));
                    }
                }
            }
        } else {
            itemSetting = (Map)item.getItemSetting();
        }
        HashMap formulas = (HashMap)itemSetting.get("formulaMap");
        batchCheckInfo.setContext(jtableContext);
        if (formulas == null) {
            formulas = new HashMap();
            ArrayList formula = new ArrayList();
            String FormKeys = String.join((CharSequence)";", this.viewCtrl.queryAllFormKeysByFormScheme(oneKeyCheckInfo.getContext().getFormSchemeKey()));
            BatchDimensionValueFormInfo batchDimensionValueFormInfo = this.formGroupProvider.getForms(oneKeyCheckInfo.getContext(), FormKeys, Consts.FormAccessLevel.FORM_READ);
            List forms = batchDimensionValueFormInfo.getForms();
            for (String form : forms) {
                formulas.put(form, formula);
            }
            batchCheckInfo.setFormulas(formulas);
        } else {
            batchCheckInfo.setFormulas((Map)formulas);
        }
        batchCheckInfo.setOrderField("form_formula");
        List<Integer> erroStatus = this.getFormulaErrState();
        batchCheckInfo.setCheckTypes(erroStatus);
        FormulaCheckReturnInfo batchCheckResult = this.batchCheckService.batchCheckResult(batchCheckInfo);
        return batchCheckResult;
    }

    private List<Integer> getFormulaErrState() {
        ArrayList<Integer> erroStatus = new ArrayList<Integer>();
        ArrayList<Integer> formualTypes = new ArrayList<Integer>();
        try {
            List auditTypes = this.auditTypeDefineService.queryAllAuditType();
            if (auditTypes == null || auditTypes.size() == 0) {
                throw new Exception();
            }
            for (AuditType auditType : auditTypes) {
                formualTypes.add(auditType.getCode());
            }
        }
        catch (Exception e) {
            formualTypes.add(1);
            formualTypes.add(2);
            formualTypes.add(4);
        }
        for (int i = 0; i < formualTypes.size(); ++i) {
            erroStatus.add((Integer)formualTypes.get(i));
        }
        return erroStatus;
    }

    private void saveIntegrityFormResult(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo, AsyncTaskMonitor asyncTaskMonitor) {
        this.filterDW(multCheckItem, oneKeyCheckInfo);
        IntegrityDataInfo integrityDataInfo = this.integrityDataCheckResult(asyncTaskId);
        TaskState state = this.asyncTaskManager.queryTaskState(asyncTaskId);
        StringBuffer htmlStr = new StringBuffer();
        int isPass = 0;
        if (state.getValue() == TaskState.FINISHED.getValue()) {
            String emptyDwCount = integrityDataInfo.getEmptyTabCount().toString();
            Map<String, List<String>> rowData = integrityDataInfo.getRowData();
            ArrayList<Boolean> colEmpty = new ArrayList<Boolean>();
            for (Map.Entry<String, List<String>> entry : rowData.entrySet()) {
                for (int i = 2; i < entry.getValue().size(); ++i) {
                    if (entry.getKey().equals("1")) {
                        colEmpty.add(false);
                    }
                    if (entry.getValue().get(i) == null) continue;
                    colEmpty.set(i - 2, true);
                }
            }
            int emptyColSum = 0;
            for (Boolean isEmptyByCol : colEmpty) {
                if (!isEmptyByCol.booleanValue()) continue;
                ++emptyColSum;
            }
            List<String> list = this.getSelectedEntity(oneKeyCheckInfo);
            List<String> selectedForms = this.getSelectedForms(multCheckItem, oneKeyCheckInfo);
            if (rowData.size() <= 0) {
                isPass = 1;
                htmlStr.append("<div class='checkPass'>").append("\u5ba1\u6838\u901a\u8fc7").append("</div>").append("<div>").append("<span>\u5355\u4f4d\u5171</span>").append("<span class='word'>").append(list.size()).append("</span>").append("<span>\u6237\uff0c</span>").append("<span>\u7f3a\u8868</span>").append("<span class='word'>").append(emptyDwCount).append("</span>").append("<span>\u6237</span>").append("</div>").append("<div>").append("<span>\u62a5\u8868\u5171</span>").append("<span class='word'>").append(selectedForms.size()).append("</span>").append("<span>\u5f20\uff0c</span>").append("<span>\u7a7a\u8868</span>").append("<span class='word'>").append(emptyColSum).append("</span>").append("<span>\u5f20</span>").append("</div>");
            } else {
                htmlStr.append("<div>").append("<div class='left'>\u5ba1\u6838\u6709\u8bef</div>").append("<div class='right'><a id='").append(multCheckItem.getId()).append("'>\u70b9\u51fb\u67e5\u770b</a></div>").append("</div>").append("<div>").append("<span>\u5355\u4f4d\u5171</span>").append("<span class='word'>").append(list.size()).append("</span>").append("<span>\u6237\uff0c</span>").append("<span>\u7f3a\u8868</span>").append("<span class='word'>").append(emptyDwCount).append("</span>").append("<span>\u6237</span>").append("</div>").append("<div>").append("<span>\u62a5\u8868\u5171</span>").append("<span class='word'>").append(selectedForms.size()).append("</span>").append("<span>\u5f20\uff0c</span>").append("<span>\u7a7a\u8868</span>").append("<span class='word'>").append(emptyColSum).append("</span>").append("<span>\u5f20</span>").append("</div>");
            }
        } else {
            isPass = 2;
            htmlStr.append("<div class='checkPass'>").append(multCheckItem.getName() + state.getTitle()).append("</div>");
        }
        this.tableName = this.getResultTableName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> paramValues = new ArrayList<Object>();
        sql.append("insert into ").append(this.tableName).append(" \n ");
        sql.append("(").append("ZHSH_KEY").append(",").append("ZHSH_ASYNCTASKID").append(",").append("ZHSH_CHECKITEMASYNCTASKID").append(",").append("ZHSH_CHECKITEMKEY").append(",").append("ZHSH_CHECKITEMNAME").append(",").append("ZHSH_FORMSCHEMEKEY").append(",").append("ZHSH_CHECKTYPE").append(",").append("ZHSH_CHECKPARAMS").append(",").append("ZHSH_CHECKRESULT").append(",").append("ZHSH_CHECKDETAIL").append(",").append("ZHSH_ISPASS").append(",").append("ZHSH_ORDER").append(",").append("ZHSH_UPDATETIME").append(",").append("ZHSH_OPERATOR").append(") \n");
        sql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
        paramValues.add(UUID.randomUUID().toString());
        paramValues.add(asyncTaskMonitor.getTaskId());
        paramValues.add(asyncTaskId);
        paramValues.add(multCheckItem.getId());
        paramValues.add(multCheckItem.getName());
        paramValues.add(oneKeyCheckInfo.getContext().getFormSchemeKey());
        paramValues.add(multCheckItem.getCheckType());
        paramValues.add(multCheckItem.getCheckParams());
        paramValues.add(htmlStr);
        paramValues.add(JsonUtil.objectToJson((Object)this.asyncTaskManager.queryDetail(asyncTaskId)));
        paramValues.add(isPass);
        paramValues.add(multCheckItem.getIndex());
        Date date = new Date();
        paramValues.add(date);
        paramValues.add(SettingUtil.getCurrentUser().getName());
        this.jdbcTemplate.update(sql.toString(), paramValues.toArray());
    }

    private List<String> getSelectedForms(MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo) {
        List formList;
        List<String> selectedForms = new ArrayList<String>();
        HashMap checkScope = new HashMap();
        if (oneKeyCheckInfo.getBeforeUpload() != null && oneKeyCheckInfo.getBeforeUpload().booleanValue()) {
            if (multCheckItem.getItemSetting() != null) {
                if (multCheckItem.getItemSetting() instanceof LinkedTreeMap) {
                    for (Iterator<Object> key : ((LinkedTreeMap)multCheckItem.getItemSetting()).keySet()) {
                        checkScope.put(key, ((LinkedTreeMap)multCheckItem.getItemSetting()).get(key));
                    }
                } else {
                    checkScope = Maps.newHashMap();
                    BeanMap beanMap = BeanMap.create(multCheckItem.getItemSetting());
                    for (Object key : beanMap.keySet()) {
                        checkScope.put(key + "", beanMap.get(key));
                    }
                }
            }
            if (checkScope.get("formList") != null) {
                if (multCheckItem.getItemSetting() instanceof LinkedTreeMap) {
                    formList = (List)checkScope.get("formList");
                    if (((List)checkScope.get("formList")).size() > 0 && ((List)checkScope.get("formList")).get(0) instanceof LinkedTreeMap) {
                        formList = new ArrayList();
                        List list = (List)checkScope.get("formList");
                        for (LinkedTreeMap treeMap : list) {
                            HashMap checkScope1 = new HashMap();
                            for (Object key : treeMap.keySet()) {
                                checkScope1.put(key, treeMap.get(key));
                            }
                            formList.add(checkScope1);
                        }
                    }
                } else {
                    formList = new ArrayList((HashSet)checkScope.get("formList"));
                }
            } else {
                formList = new ArrayList();
            }
        } else {
            checkScope = (HashMap)multCheckItem.getItemSetting();
            formList = (ArrayList)checkScope.get("formList");
        }
        if (formList.size() > 0) {
            for (int i = 0; i < formList.size(); ++i) {
                selectedForms.add(((HashMap)formList.get(i)).get("formKey").toString());
            }
        } else {
            String FormKeys = String.join((CharSequence)";", this.viewCtrl.queryAllFormKeysByFormScheme(oneKeyCheckInfo.getContext().getFormSchemeKey()));
            BatchDimensionValueFormInfo batchDimensionValueFormInfo = this.formGroupProvider.getForms(oneKeyCheckInfo.getContext(), FormKeys, Consts.FormAccessLevel.FORM_READ);
            selectedForms = batchDimensionValueFormInfo.getForms();
        }
        return selectedForms;
    }

    private List<String> getSelectedEntity(OneKeyCheckInfo oneKeyCheckInfo) {
        ArrayList<String> entityData = new ArrayList<String>();
        String mainDimName = this.dimensionUtil.getDwMainDimName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        String enityListStr = oneKeyCheckInfo.getSelectedDimensionSet().get(mainDimName).getValue();
        if (StringUtils.isEmpty((String)enityListStr)) {
            EntityViewData entityViewData = this.jtableParamService.getDwEntity(oneKeyCheckInfo.getContext().getFormSchemeKey());
            DimensionValueSet valueSet = new DimensionValueSet();
            valueSet.setValue("DATATIME", (Object)((DimensionValue)oneKeyCheckInfo.getContext().getDimensionSet().get("DATATIME")).getValue());
            IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(oneKeyCheckInfo.getContext().getFormSchemeKey());
            entityQuery.setMasterKeys(valueSet);
            IEntityTable entityTable = null;
            try {
                entityTable = this.entityQueryHelper.buildEntityTable(entityQuery, oneKeyCheckInfo.getContext().getFormSchemeKey(), true);
                for (IEntityRow dataRow : entityTable.getAllRows()) {
                    entityData.add(dataRow.getEntityKeyData());
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            for (String entityKey : enityListStr.split(";")) {
                entityData.add(entityKey);
            }
        }
        return entityData;
    }

    public FormSchemeDefine getFormScheme(String formSchemeKey) {
        try {
            FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(formSchemeKey);
            return formScheme;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public TableDefine findTable(String tableKey) {
        try {
            if (this.designTimeController != null) {
                return this.designTimeController.queryTableDefine(tableKey);
            }
            return this.dataController.queryTableDefine(tableKey);
        }
        catch (Exception e) {
            NrDesignLogHelper.log((String)"\u7efc\u5408\u5ba1\u6838", (String)e.getMessage(), (int)NrDesignLogHelper.LOGLEVEL_INFO);
            return null;
        }
    }

    private String getCurrentEntity(JtableContext context) {
        IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(context.getFormSchemeKey());
        entityQuery.setIgnoreViewFilter(true);
        entityQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet((Map)context.getDimensionSet()));
        IEntityTable entityTable = null;
        String curEntityTitle = "";
        try {
            entityTable = this.entityQueryHelper.buildEntityTable(entityQuery, context.getFormSchemeKey(), true);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (entityTable.getTotalCount() > 0) {
            curEntityTitle = ((IEntityRow)entityTable.getAllRows().get(0)).getTitle();
        }
        return curEntityTitle;
    }

    private void saveEnumCheckResult(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo, AsyncTaskMonitor asyncTaskMonitor) throws JsonProcessingException, JobsException {
        this.filterDW(multCheckItem, oneKeyCheckInfo);
        TaskState state = this.asyncTaskManager.queryTaskState(asyncTaskId);
        JobInstanceBean jobInstanceBean = RealTimeJobManager.getInstance().getMonitorManager().getLastJobInstance(asyncTaskId);
        StringBuffer htmlStr = new StringBuffer();
        int isPass = 0;
        if (state.getValue() != TaskState.FINISHED.getValue()) {
            isPass = 2;
            htmlStr.append("<div class='checkPass'>").append(multCheckItem.getName() + state.getTitle()).append("</div>");
        } else {
            EnumDataCheckResultInfo enumDataCheckResultInfo = (EnumDataCheckResultInfo)this.asyncTaskManager.queryDetail(asyncTaskId);
            List<String> enumCheckResult = enumDataCheckResultInfo.getEnumDataCheckResult();
            int selEnumDicCount = enumDataCheckResultInfo.getSelEnumDicCount();
            int selEntityCount = enumDataCheckResultInfo.getSelEntityCount();
            HashSet<String> errorUnitKeys = new HashSet<String>();
            HashSet<String> errorEnumCodes = new HashSet<String>();
            EnumDataCheckResultItem resultItem = new EnumDataCheckResultItem();
            ObjectMapper objectMapper = new ObjectMapper();
            for (String item : enumCheckResult) {
                resultItem = (EnumDataCheckResultItem)objectMapper.readValue(item, EnumDataCheckResultItem.class);
                if (!errorUnitKeys.contains(resultItem.getMasterEntityKey())) {
                    errorUnitKeys.add(resultItem.getMasterEntityKey());
                }
                if (errorEnumCodes.contains(resultItem.getEnumCode())) continue;
                errorEnumCodes.add(resultItem.getEnumCode());
            }
            if (enumCheckResult.size() <= 0) {
                isPass = 1;
                htmlStr.append("<div class='checkPass'>").append("<span>\u5ba1\u6838\u901a\u8fc7</span>").append("</div>").append("<div>").append("<span>\u5355\u4f4d\u5171</span>").append("<span class='word'>").append(selEntityCount).append("</span>").append("<span>\u6237\uff0c</span>").append("<span>\u4e0d\u901a\u8fc7</span>").append("<span class='word'>").append(errorUnitKeys.size()).append("</span>").append("<span>\u6237</span>").append("</div>").append("<div>").append("<span>\u679a\u4e3e\u9879\u5171</span>").append("<span class='word'>").append(selEnumDicCount).append("</span>").append("<span>\u4e2a\uff0c</span>").append("<span>\u4e0d\u901a\u8fc7</span>").append("<span class='word'>").append(errorEnumCodes.size()).append("</span>").append("<span>\u4e2a</span>").append("</div>");
            } else {
                htmlStr.append("<div>").append("<div class='left'><span>\u5ba1\u6838\u6709\u8bef\uff0c\u5171</span>").append("<span class='word'>").append(enumCheckResult.size()).append("</span>").append("<span>\u6761\u9519\u8bef\u8bb0\u5f55</span></div>").append("<div class='right'><a id='").append(multCheckItem.getId()).append("'>\u70b9\u51fb\u67e5\u770b</a></div>").append("</div>").append("<div>").append("<span>\u5355\u4f4d\u5171</span>").append("<span class='word'>").append(selEntityCount).append("</span>").append("<span>\u6237\uff0c</span>").append("<span>\u4e0d\u901a\u8fc7</span>").append("<span class='word'>").append(errorUnitKeys.size()).append("</span>").append("<span>\u6237</span>").append("</div>").append("<div>").append("<span>\u679a\u4e3e\u9879\u5171</span>").append("<span class='word'>").append(selEnumDicCount).append("</span>").append("<span>\u4e2a\uff0c</span>").append("<span>\u4e0d\u901a\u8fc7</span>").append("<span class='word'>").append(errorEnumCodes.size()).append("</span>").append("<span>\u4e2a</span>").append("</div>");
            }
        }
        this.tableName = this.getResultTableName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> paramValues = new ArrayList<Object>();
        sql.append("insert into ").append(this.tableName).append(" \n ");
        sql.append("(").append("ZHSH_KEY").append(",").append("ZHSH_ASYNCTASKID").append(",").append("ZHSH_CHECKITEMASYNCTASKID").append(",").append("ZHSH_CHECKITEMKEY").append(",").append("ZHSH_CHECKITEMNAME").append(",").append("ZHSH_FORMSCHEMEKEY").append(",").append("ZHSH_CHECKTYPE").append(",").append("ZHSH_CHECKPARAMS").append(",").append("ZHSH_CHECKRESULT").append(",").append("ZHSH_CHECKDETAIL").append(",").append("ZHSH_ISPASS").append(",").append("ZHSH_ORDER").append(",").append("ZHSH_UPDATETIME").append(",").append("ZHSH_OPERATOR").append(") \n");
        sql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
        paramValues.add(UUID.randomUUID().toString());
        paramValues.add(asyncTaskMonitor.getTaskId());
        paramValues.add(asyncTaskId);
        paramValues.add(multCheckItem.getId());
        paramValues.add(multCheckItem.getName());
        paramValues.add(oneKeyCheckInfo.getContext().getFormSchemeKey());
        paramValues.add(multCheckItem.getCheckType());
        paramValues.add(multCheckItem.getCheckParams());
        paramValues.add(htmlStr);
        paramValues.add(JsonUtil.objectToJson((Object)this.asyncTaskManager.queryDetail(asyncTaskId)));
        paramValues.add(isPass);
        paramValues.add(multCheckItem.getIndex());
        Date date = new Date();
        paramValues.add(date);
        paramValues.add(SettingUtil.getCurrentUser().getName());
        this.jdbcTemplate.update(sql.toString(), paramValues.toArray());
    }

    private String getResultTableName(String formSchemeKey) {
        FormSchemeDefine scheme = this.viewCtrl.getFormScheme(formSchemeKey);
        String str = String.format("%s%s", "SYS_MULTCHECK_", scheme.getFormSchemeCode());
        return this.dataQueryHelper.getLibraryTableName(scheme.getTaskKey(), str);
    }

    private IntegrityDataInfo integrityDataCheckResult(String asyncTaskID) {
        Object object = this.asyncTaskManager.queryDetail(asyncTaskID);
        if (null != object) {
            return (IntegrityDataInfo)((Object)object);
        }
        return null;
    }

    @Override
    public List<MultCheckResultItem> getCheckItemResults(String asyncTaskId, String formSchemeKey) {
        this.tableName = this.getResultTableName(formSchemeKey);
        String sql = "SELECT * FROM " + this.tableName + " WHERE zhsh_asynctaskid =? order by zhsh_order";
        List multCheckResults = this.jdbcTemplate.queryForList(sql, new Object[]{asyncTaskId});
        MultCheckResultItem multCheckResultItem = null;
        ArrayList<MultCheckResultItem> returnList = new ArrayList<MultCheckResultItem>();
        for (Map resultItem : multCheckResults) {
            multCheckResultItem = new MultCheckResultItem();
            multCheckResultItem.setKey(resultItem.get("ZHSH_CHECKITEMKEY").toString());
            multCheckResultItem.setCheckType(resultItem.get("ZHSH_CHECKTYPE").toString());
            multCheckResultItem.setCheckDetail(resultItem.get("ZHSH_CHECKDETAIL") == null ? "" : resultItem.get("ZHSH_CHECKDETAIL").toString());
            multCheckResultItem.setCheckResult(resultItem.get("ZHSH_CHECKRESULT").toString());
            multCheckResultItem.setName(resultItem.get("ZHSH_CHECKITEMNAME").toString());
            multCheckResultItem.setCheckParams(resultItem.get("ZHSH_CHECKPARAMS").toString());
            multCheckResultItem.setAsyncTask(resultItem.get("ZHSH_ASYNCTASKID").toString());
            multCheckResultItem.setCheckItemAsyncTask(resultItem.get("ZHSH_CHECKITEMASYNCTASKID").toString());
            multCheckResultItem.setUpdateTime(resultItem.get("ZHSH_UPDATETIME").toString());
            multCheckResultItem.setCheckStatus(resultItem.get("ZHSH_ISPASS").toString());
            returnList.add(multCheckResultItem);
        }
        return returnList;
    }

    @Override
    public LastCheckInfo lastCheckResults(String formSchemeKey) {
        LastCheckInfo lastCheckInfo = null;
        this.tableName = this.getResultTableName(formSchemeKey);
        try {
            String libTableName = this.tableName;
            StringBuffer sql_asytsk = new StringBuffer();
            sql_asytsk.append("select zhsh_asynctaskid from ").append(libTableName).append(" where zhsh_operator= ?").append(" order by zhsh_updatetime desc ");
            List zhsh_asynctaskMap = this.jdbcTemplate.queryForList(sql_asytsk.toString(), new Object[]{SettingUtil.getCurrentUser().getName()});
            if (zhsh_asynctaskMap.size() > 0) {
                StringBuffer sql = new StringBuffer();
                sql.append("select * from ").append(libTableName).append(" where ").append(" zhsh_asynctaskid= ?").append(" order by zhsh_order ");
                List checkResults = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{((Map)zhsh_asynctaskMap.get(0)).get("zhsh_asynctaskid")});
                Gson gson = new Gson();
                lastCheckInfo = new LastCheckInfo();
                for (Map resultItem : checkResults) {
                    JsonObject jsonObject = (JsonObject)gson.fromJson(resultItem.get("ZHSH_CHECKPARAMS").toString(), JsonObject.class);
                    if (resultItem.get("ZHSH_CHECKTYPE").toString().equals(MultCheckEnum.ENTITY_TREE_CHECK.getKey())) {
                        lastCheckInfo.setContext((JtableContext)gson.fromJson((JsonElement)jsonObject, JtableContext.class));
                        lastCheckInfo.setAsyncTaskId(resultItem.get("ZHSH_ASYNCTASKID").toString());
                        break;
                    }
                    if (jsonObject.get("context") == null) {
                        jsonObject = (JsonObject)jsonObject.get("checkInfo");
                    }
                    lastCheckInfo.setAsyncTaskId(resultItem.get("ZHSH_ASYNCTASKID").toString());
                    JtableContext context = (JtableContext)gson.fromJson(jsonObject.get("context").toString(), JtableContext.class);
                    lastCheckInfo.setContext(context);
                    if (lastCheckInfo.getContext() == null) continue;
                    break;
                }
            }
            return lastCheckInfo;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return lastCheckInfo;
        }
    }

    @Override
    public boolean deleteCheckResult(String taskKey, String formSchemeKey, List<String> checkItemList) {
        this.tableName = this.getResultTableName(formSchemeKey);
        if (checkItemList.size() <= 0) {
            return false;
        }
        StringBuffer str = new StringBuffer();
        str.append("(");
        for (int i = 0; i < checkItemList.size(); ++i) {
            str.append(" '").append(checkItemList.get(i)).append(" '").append(",");
            if (i != checkItemList.size() - 1) continue;
            str.append(" '").append(checkItemList.get(i)).append(" '").append(" )");
        }
        String libTableName = this.tableName;
        StringBuffer sql = new StringBuffer();
        sql.append("delete  from ").append(libTableName).append(" \n ");
        sql.append(" where ").append("ZHSH_FORMSCHEMEKEY").append(" = '").append(formSchemeKey).append("'").append(" and ").append("ZHSH_CHECKTYPE").append(" not in ").append(str);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.jdbcTpl.getDataSource());
        int t = jdbcTemplate.update(sql.toString());
        return t > 0;
    }

    @Override
    public List<Map<String, Object>> getCheckItemList(String s_key) {
        String selectSql = "select s_content from SYS_MULTCHECK_SCHEME where s_key = ?";
        logger.info("selectSql: " + selectSql);
        String s_content = null;
        try {
            s_content = (String)this.jdbcTemplate.queryForObject(selectSql, String.class, new Object[]{s_key});
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        Gson gson = new Gson();
        List dataList = (List)gson.fromJson(s_content, new TypeToken<List<Map<String, Object>>>(){}.getType());
        return dataList;
    }
}

