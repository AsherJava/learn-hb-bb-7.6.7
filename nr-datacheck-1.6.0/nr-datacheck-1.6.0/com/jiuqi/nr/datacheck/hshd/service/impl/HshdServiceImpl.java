/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.common.PeriodMatchingType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definitionext.taskExtConfig.controller.ITaskExtConfigController
 *  com.jiuqi.nr.definitionext.taskExtConfig.model.ExtensionBasicModel
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper
 *  com.jiuqi.nr.finalaccountsaudit.common.UUIDMerger
 *  com.jiuqi.nr.finalaccountsaudit.dao.MultCheckRes
 *  com.jiuqi.nr.finalaccountsaudit.dao.MultCheckResDao
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.common.CheckConfigurationContent
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckInfo
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckResult
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpData
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpRecord
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpType
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.common.MultCheckObj
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.common.MultCheckType
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.controller.IEntityCheckController
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.dto.EntityCheckUpDTO
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller.EntityCheckDWZDMController
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller.EntityCheckHandle
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.Association
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.ConfigInfo
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.EntityCheckConfigData
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableDataEngineService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.lwtree.provider.impl.LightTreeProvider
 *  com.jiuqi.nr.lwtree.request.LightTreeLoadParam
 *  com.jiuqi.nr.lwtree.response.INodeInfos
 *  com.jiuqi.nr.lwtree.response.LightNodeData
 *  com.jiuqi.nr.multcheck2.bean.MultcheckItem
 *  com.jiuqi.nr.multcheck2.bean.MultcheckScheme
 *  com.jiuqi.nr.multcheck2.bean.MultcheckSchemeOrg
 *  com.jiuqi.nr.multcheck2.common.CheckRestultState
 *  com.jiuqi.nr.multcheck2.provider.CheckItemParam
 *  com.jiuqi.nr.multcheck2.provider.CheckItemResult
 *  com.jiuqi.nr.multcheck2.provider.FailedOrgInfo
 *  com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO
 *  com.jiuqi.nr.multcheck2.service.IMCSchemeService
 *  com.jiuqi.nr.unit.treecommon.utils.IReturnObject
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.datacheck.hshd.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datacheck.common.SerializeUtil;
import com.jiuqi.nr.datacheck.hshd.HshdConfig;
import com.jiuqi.nr.datacheck.hshd.PeriodConfig;
import com.jiuqi.nr.datacheck.hshd.service.HshdTreeProvider;
import com.jiuqi.nr.datacheck.hshd.service.IHshdService;
import com.jiuqi.nr.datacheck.hshd.vo.AssFormSchemeVO;
import com.jiuqi.nr.datacheck.hshd.vo.AssTaskVO;
import com.jiuqi.nr.datacheck.hshd.vo.HshdCheckPM;
import com.jiuqi.nr.datacheck.hshd.vo.HshdCheckResult;
import com.jiuqi.nr.datacheck.hshd.vo.HshdTreePM;
import com.jiuqi.nr.datacheck.hshd.vo.TaskOrgLinkVO;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.common.PeriodMatchingType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definitionext.taskExtConfig.controller.ITaskExtConfigController;
import com.jiuqi.nr.definitionext.taskExtConfig.model.ExtensionBasicModel;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.common.UUIDMerger;
import com.jiuqi.nr.finalaccountsaudit.dao.MultCheckRes;
import com.jiuqi.nr.finalaccountsaudit.dao.MultCheckResDao;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.CheckConfigurationContent;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckResult;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpData;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpRecord;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpType;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.MultCheckObj;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.MultCheckType;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.controller.IEntityCheckController;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.dto.EntityCheckUpDTO;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller.EntityCheckDWZDMController;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller.EntityCheckHandle;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.Association;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.ConfigInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.EntityCheckConfigData;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.lwtree.provider.impl.LightTreeProvider;
import com.jiuqi.nr.lwtree.request.LightTreeLoadParam;
import com.jiuqi.nr.lwtree.response.INodeInfos;
import com.jiuqi.nr.lwtree.response.LightNodeData;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.bean.MultcheckSchemeOrg;
import com.jiuqi.nr.multcheck2.common.CheckRestultState;
import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import com.jiuqi.nr.multcheck2.provider.FailedOrgInfo;
import com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nr.unit.treecommon.utils.IReturnObject;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class HshdServiceImpl
implements IHshdService {
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IMCSchemeService schemeService;
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController runTimeViewController;
    @Autowired
    private IRunTimeViewController runTimeViewControllerApi;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController definitionRuntimeController;
    @Autowired
    private MultCheckResDao multCheckResDao;
    @Autowired
    private IMCSchemeService mcSchemeService;
    @Autowired
    private IEntityCheckController entityCheckController;
    @Autowired
    private ITaskExtConfigController taskExtConfigController;
    @Autowired
    private IFMDMDataService fmdmDataService;
    @Autowired
    private EntityQueryHelper entityQueryHelper;
    @Autowired
    private EntityCheckDWZDMController entityCheckDWZdmController;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IEntityMetaService metaService;
    private static final Logger logger = LoggerFactory.getLogger(HshdServiceImpl.class);

    @Override
    public IEntityTable queryEntityTable(MultcheckScheme multcheckScheme, String task) {
        IEntityTable entityTable;
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(task);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.sorted(true);
        switch (multcheckScheme.getOrgType()) {
            case SELECT: {
                List orgList = this.schemeService.getOrgListByScheme(multcheckScheme.getKey());
                List orgKeys = orgList.stream().map(MultcheckSchemeOrg::getOrg).collect(Collectors.toList());
                DimensionValueSet dimensionValueSet = new DimensionValueSet();
                dimensionValueSet.setValue("DATATIME", (Object)taskDefine.getFromPeriod());
                dimensionValueSet.setValue("MD_ORG", orgKeys);
                entityQuery.setMasterKeys(dimensionValueSet);
                entityQuery.sortedByQuery(false);
                break;
            }
            case FORMULA: {
                entityQuery.setExpression(multcheckScheme.getOrgFml());
                entityQuery.sortedByQuery(false);
                break;
            }
        }
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(taskDefine.getDw());
        entityQuery.setEntityView(entityViewDefine);
        com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = new com.jiuqi.np.dataengine.executors.ExecutorContext(this.definitionRuntimeController);
        try {
            entityTable = entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return entityTable;
    }

    public IEntityTable getEntityTableByCheckScheme(String scheme) {
        MultcheckScheme multcheckScheme = this.schemeService.getSchemeByKey(scheme);
        String task = multcheckScheme.getTask();
        return this.queryEntityTable(multcheckScheme, task);
    }

    public IReturnObject<INodeInfos<LightNodeData>> buildTree(HshdTreePM treePM, Function<HshdTreeProvider, INodeInfos<LightNodeData>> function) {
        IReturnObject instance;
        MultcheckScheme multcheckScheme = this.schemeService.getSchemeByKey(treePM.getCheckSchemeKey());
        String task = multcheckScheme.getTask();
        LightTreeLoadParam loadParam = treePM.getTreeLoadParam();
        IEntityTable entityTable = this.queryEntityTable(multcheckScheme, task);
        try {
            loadParam.checkPara();
            instance = IReturnObject.getSuccessInstance(function.apply(new HshdTreeProvider(loadParam, entityTable)));
        }
        catch (Exception e) {
            instance = IReturnObject.getErrorInstance((String)e.getMessage(), null);
            logger.error(e.getMessage(), e);
        }
        return instance;
    }

    @Override
    public IReturnObject<INodeInfos<LightNodeData>> queryRoots(HshdTreePM treePM) {
        return this.buildTree(treePM, LightTreeProvider::loadingTree);
    }

    @Override
    public IReturnObject<INodeInfos<LightNodeData>> queryChildren(HshdTreePM treePM) {
        return this.buildTree(treePM, LightTreeProvider::getChildren);
    }

    @Override
    public IReturnObject<INodeInfos<LightNodeData>> searchNodes(HshdTreePM treePM) {
        return this.buildTree(treePM, HshdTreeProvider::searchNode);
    }

    @Override
    public IReturnObject<INodeInfos<LightNodeData>> locateNode(HshdTreePM treePM) {
        return this.buildTree(treePM, LightTreeProvider::locateTree);
    }

    @Override
    public String getItemDescribe(String formSchemeKey, MultcheckItem item) {
        try {
            HshdConfig config = SerializeUtil.deserializeFromJson(item.getConfig(), HshdConfig.class);
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(config.getAssFormScheme());
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(config.getAssTask());
            if (formScheme == null || taskDefine == null) {
                return null;
            }
            return String.format("\u5173\u8054\u4efb\u52a1 | %s \\ %s", taskDefine.getTitle(), formScheme.getTitle());
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getRunItemDescribe(String formSchemeKey, MultcheckItem item) {
        try {
            HshdConfig config = SerializeUtil.deserializeFromJson(item.getConfig(), HshdConfig.class);
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(config.getAssTask());
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(config.getAssFormScheme());
            if (formScheme == null || taskDefine == null) {
                return null;
            }
            return String.format("%s | %s", taskDefine.getTitle(), formScheme.getTitle());
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public CheckItemResult runCheck(CheckItemParam param) {
        AsyncTaskMonitor taskMonitor = param.getAsyncTaskMonitor();
        taskMonitor.progressAndMessage(0.1, "\u6237\u6570\u6838\u5bf9\u5f00\u59cb");
        try {
            CheckItemResult result = this.initRes();
            if (param.getCheckItem().getConfig() == null) {
                return this.errorRes(param, "\u6237\u6570\u6838\u5bf9\u914d\u7f6e\u6709\u8bef\uff0c\u8bf7\u68c0\u67e5\u4efb\u52a1\u914d\u7f6e");
            }
            HshdConfig config = this.getResultConfig(param);
            if (!org.springframework.util.StringUtils.hasText(config.getAssFormScheme()) || !org.springframework.util.StringUtils.hasText(config.getAssTask()) || config.getPeriodConfig() == null || config.getAssEntity() == null) {
                return this.errorRes(param, "\u6237\u6570\u6838\u5bf9\u914d\u7f6e\u6709\u8bef\uff0c\u8bf7\u68c0\u67e5\u4efb\u52a1\u914d\u7f6e");
            }
            result.setRunConfig(SerializeUtil.serializeToJson(config));
            if (taskMonitor.isCancel()) {
                return null;
            }
            taskMonitor.progressAndMessage(0.2, "\u6237\u6570\u6838\u5bf9\u53c2\u6570\u6821\u9a8c\u5b8c\u6210");
            List<EntityCheckInfo> checkInfos = this.buildCheckInfos(param, config);
            CheckConfigurationContent checkConfigurationContent = this.entityCheckController.getCheckConfigurationContent(param.getContext().getTaskKey(), param.getContext().getFormSchemeKey(), config.getAssTask(), config.getAssFormScheme());
            if (checkConfigurationContent == null || checkConfigurationContent.getMatchingInfo() == null) {
                return this.errorRes(param, "\u83b7\u53d6\u6838\u5bf9\u914d\u7f6e\u5185\u5bb9\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u662f\u5426\u914d\u7f6e\u6838\u5bf9\u914d\u7f6e");
            }
            taskMonitor.progressAndMessage(0.3, "\u6237\u6570\u6838\u5bf9\u5ba1\u6838\u53c2\u6570\u51c6\u5907\u5b8c\u6210\uff0c\u5f00\u59cb\u8fdb\u884c\u5ba1\u6838");
            ArrayList<EntityCheckResult> checkResults = new ArrayList<EntityCheckResult>();
            Map records = this.entityCheckController.getRecord(param.getContext().getTaskKey(), param.getContext().getFormSchemeKey(), param.getContext().getPeriod(), config.getAssTask(), config.getAssFormScheme(), config.getAssPeriod(), checkConfigurationContent);
            if (records != null && !records.isEmpty()) {
                for (int i = 0; i < checkInfos.size(); ++i) {
                    if (taskMonitor.isCancel()) {
                        return null;
                    }
                    EntityCheckResult entityResultInfo = this.entityCheckController.getEntityCheckResult(records, checkConfigurationContent, checkInfos.get(i).getScop());
                    checkResults.add(entityResultInfo);
                    this.checkReason(entityResultInfo);
                    MultCheckObj checkObj = entityResultInfo.getCheckObj();
                    if (checkObj.getCheckType() == MultCheckType.ERROR) {
                        FailedOrgInfo orgInfo = new FailedOrgInfo();
                        orgInfo.setDesc(checkObj.getMessage());
                        result.getFailedOrgs().put(checkInfos.get(i).getScop(), orgInfo);
                    } else {
                        result.getSuccessOrgs().add(checkInfos.get(i).getScop());
                    }
                    taskMonitor.progressAndMessage(0.9 * ((double)(i + 1) / (double)checkInfos.size()), String.format("\u6237\u6570\u6838\u5bf9: %s", checkInfos.get(i).getScop()));
                }
            }
            if (!result.getFailedOrgs().isEmpty()) {
                result.setResult(CheckRestultState.FAIL);
            }
            taskMonitor.finish("\u6237\u6570\u6838\u5bf9\u5b8c\u6210", null);
            this.log(checkResults, param);
            return result;
        }
        catch (Exception e) {
            logger.error("\u6237\u6570\u6838\u5bf9\u5ba1\u6838\u5f02\u5e38", e);
            taskMonitor.error("\u6237\u6570\u6838\u5bf9\u5ba1\u6838\u5f02\u5e38", (Throwable)e);
            return this.errorRes(param, "\u6237\u6570\u6838\u5bf9\u5ba1\u6838\u5f02\u5e38\uff0c\u8bf7\u68c0\u67e5\u4efb\u52a1\u914d\u7f6e");
        }
    }

    private Map<String, String> getExpFormulaValues(String taskKey, String formSchemeKey, String period, String sndmFormula) throws Exception {
        HashMap<String, String> codeToFormulaValues = new HashMap<String, String>();
        IDataAccessProvider accessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
        IExpressionEvaluator evaluator = accessProvider.newExpressionEvaluator();
        JtableContext executorTableContext = new JtableContext();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        executorTableContext.setTaskKey(taskKey);
        executorTableContext.setFormSchemeKey(formSchemeKey);
        executorTableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet));
        IJtableDataEngineService tableDataEngineService = (IJtableDataEngineService)SpringBeanProvider.getBean(IJtableDataEngineService.class);
        com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = tableDataEngineService.getExecutorContext(executorTableContext);
        ArrayList<String> list = new ArrayList<String>();
        list.add(sndmFormula);
        Map expressionFormulaMap = evaluator.evalBatch(list, executorContext, dimensionValueSet);
        for (Map.Entry entry : expressionFormulaMap.entrySet()) {
            Object obj;
            String key = (String)entry.getKey();
            Object[] objects = (Object[])entry.getValue();
            if (objects == null || objects.length <= 0 || !((obj = objects[0]) instanceof String) || obj == null || !StringUtils.isNotEmpty((CharSequence)((String)obj))) continue;
            codeToFormulaValues.put(key, (String)obj);
        }
        return codeToFormulaValues;
    }

    private List<IFMDMData> getFmData(String formSchemeKey, String period) {
        return this.getFmData(formSchemeKey, period, null, null);
    }

    private List<IFMDMData> getFmData(String formSchemeKey, String period, String taskKey, String dwEntity) {
        FMDMDataDTO fMDMDataDTO = new FMDMDataDTO();
        fMDMDataDTO.setSorted(false);
        fMDMDataDTO.setSortedByQuery(false);
        fMDMDataDTO.setFormSchemeKey(formSchemeKey);
        fMDMDataDTO.setDwEntityId(dwEntity);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        if (taskKey != null) {
            ExecutorContext context = new ExecutorContext(this.definitionRuntimeController);
            context.getVariableManager().add(new Variable("associatedTaskKey", "\u5355\u673a\u7248\u83b7\u53d6\u6838\u5bf9\u4efb\u52a1key", 6, (Object)taskKey));
            fMDMDataDTO.setContext((IContext)context);
        }
        DimensionCollection dimensionCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)dimensionValueSet, (String)formSchemeKey);
        return this.fmdmDataService.list(fMDMDataDTO, dimensionCollection);
    }

    private IEntityTable getEntityTable(String formSchemeKey, String period) {
        return this.getEntityTable(formSchemeKey, period, Collections.emptyList());
    }

    private IEntityTable getReferEntityTable(String formSchemeKey, String period, String taskKey) {
        ExecutorContext context = new ExecutorContext(this.definitionRuntimeController);
        ReportFmlExecEnvironment execEnvironment = new ReportFmlExecEnvironment(this.runTimeViewController, this.definitionRuntimeController, this.entityViewRunTimeController, formSchemeKey);
        context.setEnv((IFmlExecEnvironment)execEnvironment);
        context.getVariableManager().add(new Variable("associatedTaskKey", "\u5355\u673a\u7248\u83b7\u53d6\u6838\u5bf9\u4efb\u52a1key", 6, (Object)taskKey));
        try {
            EntityViewDefine entityView = this.entityQueryHelper.getDwEntityView(formSchemeKey);
            IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(entityView, period);
            context.setVarDimensionValueSet(entityQuery.getMasterKeys());
            return entityQuery.executeFullBuild((IContext)context);
        }
        catch (Exception e) {
            return null;
        }
    }

    private IEntityTable getEntityTable(String formScheme, String period, List<String> orgCode) {
        EntityViewDefine entityView = this.entityQueryHelper.getDwEntityView(formScheme);
        IEntityTable currentTable = null;
        try {
            currentTable = CollectionUtils.isEmpty(orgCode) ? this.entityQueryHelper.buildEntityTable(entityView, period, formScheme, false) : this.entityQueryHelper.buildEntityTable(entityView, period, formScheme, String.join((CharSequence)";", orgCode), false);
        }
        catch (Exception e) {
            return null;
        }
        return currentTable;
    }

    private CheckItemResult errorRes(CheckItemParam param, String errorMessage) {
        CheckItemResult result = new CheckItemResult();
        result.setResult(CheckRestultState.FAIL);
        HashMap<String, FailedOrgInfo> failedOrgs = new HashMap<String, FailedOrgInfo>();
        result.setFailedOrgs(failedOrgs);
        for (String org : param.getContext().getOrgList()) {
            FailedOrgInfo failedOrgInfo = new FailedOrgInfo();
            failedOrgInfo.setDesc(errorMessage);
            failedOrgs.put(org, failedOrgInfo);
        }
        result.setSuccessOrgs(new ArrayList());
        result.setIgnoreOrgs(new ArrayList());
        return result;
    }

    private void checkReason(EntityCheckResult entityResultInfo) {
        MultCheckObj checkObj = entityResultInfo.getCheckObj();
        Map resultMap = entityResultInfo.getResultMap();
        if (checkObj.getCheckType() == MultCheckType.ERROR || checkObj.getCheckType() == MultCheckType.INVALID || resultMap == null) {
            return;
        }
        for (Map mapValue : entityResultInfo.getResultMap().values()) {
            for (EntityCheckUpRecord value : mapValue.values()) {
                EntityCheckUpData entityCheckUpData = value.GetCheckUpData();
                if (entityCheckUpData == null) continue;
                if (value.getKey().GetCheckType() == EntityCheckUpType.DECREASE && !org.springframework.util.StringUtils.hasLength(entityCheckUpData.getZjys())) {
                    checkObj.setCheckType(MultCheckType.ERROR);
                    checkObj.setMessage("\u6237\u6570\u6838\u5bf9\u5ba1\u6838\u5931\u8d25\uff0c\u672a\u8bbe\u7f6e\u589e\u51cf\u56e0\u7d20\u4ee3\u7801");
                    return;
                }
                if (value.getKey().GetCheckType().ordinal() <= EntityCheckUpType.DECREASE.ordinal() || org.springframework.util.StringUtils.hasLength(entityCheckUpData.getXbys())) continue;
                checkObj.setCheckType(MultCheckType.ERROR);
                checkObj.setMessage("\u6237\u6570\u6838\u5bf9\u5ba1\u6838\u5931\u8d25\uff0c\u672a\u8bbe\u7f6e\u65b0\u62a5\u56e0\u7d20\u4ee3\u7801");
                return;
            }
        }
    }

    private void log(List<EntityCheckResult> checkResults, CheckItemParam param) {
        String merge = UUIDMerger.merge((String)param.getRunId(), (String)param.getCheckItem().getKey());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.multCheckResDao.insert(merge, objectMapper.writeValueAsString(checkResults));
        }
        catch (JsonProcessingException e) {
            logger.error("\u5ba1\u6838\u7ed3\u679c\u5e8f\u5217\u5316\u5931\u8d25\uff1a", e);
        }
    }

    private List<EntityCheckInfo> buildCheckInfos(CheckItemParam param, HshdConfig hshdConfig) {
        ArrayList<EntityCheckInfo> list = new ArrayList<EntityCheckInfo>();
        JtableContext context = this.buildJtableContext(param.getContext().getTaskKey(), param.getContext().getFormSchemeKey(), param.getContext().getPeriod());
        for (String org : param.getContext().getOrgList()) {
            EntityCheckInfo entityCheckInfo = new EntityCheckInfo();
            entityCheckInfo.setContext(context);
            entityCheckInfo.setTaskKey(param.getContext().getTaskKey());
            entityCheckInfo.setFormSchemeKey(param.getContext().getFormSchemeKey());
            entityCheckInfo.setPeriod(param.getContext().getPeriod());
            entityCheckInfo.setScop(org);
            entityCheckInfo.setAssociatedTaskKey(hshdConfig.getAssTask());
            entityCheckInfo.setAssociatedFormSchemeKey(hshdConfig.getAssFormScheme());
            entityCheckInfo.setAssociatedperiod(hshdConfig.getAssPeriod());
            list.add(entityCheckInfo);
        }
        return list;
    }

    private HshdConfig getResultConfig(CheckItemParam param) throws Exception {
        HshdConfig hshdConfig = SerializeUtil.deserializeFromJson(param.getCheckItem().getConfig(), HshdConfig.class);
        if (hshdConfig.getAssPeriod() == null) {
            PeriodConfig periodConfig = hshdConfig.getPeriodConfig();
            hshdConfig.setAssPeriod(this.getAssociatedPeriod(param.getContext().getPeriod(), periodConfig));
        }
        if (hshdConfig.getAssEntity() == null) {
            List orgLinkDefines = this.runTimeViewControllerApi.listTaskOrgLinkByTask(hshdConfig.getAssTask());
            Optional first = orgLinkDefines.stream().findFirst();
            first.ifPresent(taskOrgLinkDefine -> hshdConfig.setAssEntity(taskOrgLinkDefine.getEntity()));
        }
        return hshdConfig;
    }

    private List<ConfigInfo> getAssFormSchemeConfigInfo(String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        try {
            ExtensionBasicModel model = this.taskExtConfigController.getTaskExtConfigDefineBySchemakey(formScheme.getTaskKey(), formSchemeKey, "taskextension-entitycheck");
            Object extInfoModel = model.getExtInfoModel();
            if (extInfoModel instanceof EntityCheckConfigData) {
                EntityCheckConfigData configData = (EntityCheckConfigData)extInfoModel;
                List configInfos = configData.getConfigInfos();
                if (configData.getEntityCheckEnable()) {
                    return configInfos;
                }
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5173\u8054\u4efb\u52a1\u5931\u8d25", e);
        }
        return null;
    }

    private String getAssociatedPeriod(String period, PeriodConfig periodConfig) {
        String associatedPeriod = null;
        PeriodMatchingType type = PeriodMatchingType.forValue((int)periodConfig.getType());
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
                associatedPeriod = periodConfig.getValue();
                break;
            }
            case PERIOD_TYPE_OFFSET: {
                periodAdapter.modify(periodWrapper, PeriodModifier.parse((String)periodConfig.getValue()));
                associatedPeriod = periodWrapper.toString();
                break;
            }
            case PERIOD_TYPE_ALL: {
                break;
            }
        }
        return associatedPeriod;
    }

    private JtableContext buildJtableContext(String task, String formScheme, String period) {
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(task);
        jtableContext.setFormSchemeKey(formScheme);
        jtableContext.setFormKey("");
        jtableContext.setFormGroupKey("");
        jtableContext.setFormulaSchemeKey("");
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        DimensionValue periodDim = new DimensionValue();
        periodDim.setName("DATATIME");
        periodDim.setType(DimensionType.DIMENSION_PERIOD.getValue());
        periodDim.setValue(period);
        dimensionSet.put(periodDim.getName(), periodDim);
        DimensionValue orgDim = new DimensionValue();
        orgDim.setName("MD_ORG");
        orgDim.setType(DimensionType.DIMENSION_PERIOD.getValue());
        orgDim.setValue("");
        dimensionSet.put(orgDim.getName(), orgDim);
        jtableContext.setDimensionSet(dimensionSet);
        jtableContext.setVariableMap(new HashMap());
        jtableContext.setMeasureMap(new HashMap());
        return jtableContext;
    }

    private CheckItemResult initRes() {
        CheckItemResult result = new CheckItemResult();
        result.setResult(CheckRestultState.SUCCESS);
        ArrayList successOrgs = new ArrayList();
        HashMap failedOrgs = new HashMap();
        result.setSuccessOrgs(successOrgs);
        result.setFailedOrgs(failedOrgs);
        result.setIgnoreOrgs(new ArrayList());
        return result;
    }

    private Map<Integer, Map<String, EntityCheckUpRecord>> InitEntityCheckUpRecordObj() {
        HashMap<Integer, Map<String, EntityCheckUpRecord>> records = new HashMap<Integer, Map<String, EntityCheckUpRecord>>();
        records.put(1, new LinkedHashMap());
        records.put(2, new LinkedHashMap());
        records.put(3, new LinkedHashMap());
        records.put(4, new LinkedHashMap());
        records.put(5, new LinkedHashMap());
        records.put(6, new LinkedHashMap());
        records.put(7, new LinkedHashMap());
        records.put(8, new LinkedHashMap());
        records.put(-1, new LinkedHashMap());
        return records;
    }

    @Override
    public HshdCheckResult entityCheckUp(HshdCheckPM checkPM) {
        if (checkPM.getConfig() == null) {
            return this.errorRes(checkPM);
        }
        HshdCheckResult result = new HshdCheckResult();
        try {
            String merge = UUIDMerger.merge((String)checkPM.getRunId(), (String)checkPM.getItemKey());
            MultCheckRes checkRes = this.multCheckResDao.findById(merge);
            if (checkRes == null || checkRes.getData() == null) {
                return this.errorRes(checkPM);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            List entityCheckResults = (List)objectMapper.readValue(checkRes.getData(), (TypeReference)new TypeReference<List<EntityCheckResult>>(){});
            Map<Integer, Map<String, EntityCheckUpRecord>> records = this.InitEntityCheckUpRecordObj();
            records.get(-1).put("-1", new EntityCheckUpRecord());
            HashSet<String> codes = new HashSet<String>(checkPM.getOrgCode());
            for (EntityCheckResult check : entityCheckResults) {
                if (!codes.contains(check.getUnitCode()) || check.getResultMap() == null) continue;
                Map tempMap = check.getResultMap();
                for (Map.Entry entry : tempMap.entrySet()) {
                    records.get(entry.getKey()).putAll((Map)entry.getValue());
                }
            }
            EntityCheckUpDTO entityCheckUpDTO = this.buildEntityCheckUpDTO(checkPM);
            CheckConfigurationContent checkConfigurationContent = this.entityCheckController.getCheckConfigurationContent(checkPM.getTask(), checkPM.getFormScheme(), entityCheckUpDTO.getAssociatedTaskKey(), entityCheckUpDTO.getAssociatedFormSchemeKey());
            if (checkConfigurationContent == null || checkConfigurationContent.getMatchingInfo() == null) {
                result.setResult(new EntityCheckHandle().returnRsultMap("", "", true, "ExpInfo2", entityCheckUpDTO.getWebTabName()));
                return result;
            }
            String s = this.entityCheckController.entityCheckUps(entityCheckUpDTO, checkConfigurationContent, records);
            result.setResult(s);
        }
        catch (Exception e) {
            try {
                String error = new EntityCheckHandle().returnRsultMap("", "", true, "Exception", checkPM.getWebTabName());
                result.setResult(error);
            }
            catch (Exception ex) {
                logger.error("\u6237\u6570\u6838\u5bf9\u67e5\u8be2\u7ed3\u679c\u5931\u8d25", e);
            }
        }
        return result;
    }

    private Map<String, IFMDMData> getFmDataMap(List<IFMDMData> dataList) {
        HashMap<String, IFMDMData> dataMap = new HashMap<String, IFMDMData>();
        if (dataList != null && dataList.size() > 0) {
            for (IFMDMData data : dataList) {
                dataMap.put(data.getValue("CODE").getAsString(), data);
            }
        }
        return dataMap;
    }

    private HshdCheckResult errorRes(HshdCheckPM checkPM) {
        HshdCheckResult result = new HshdCheckResult();
        try {
            String res = new EntityCheckHandle().returnRsultMap("", "", true, "AssParameterExpInfo", checkPM.getWebTabName());
            result.setResult(res);
        }
        catch (Exception e) {
            logger.error("\u6237\u6570\u6838\u5bf9\u67e5\u8be2\u7ed3\u679c\u5931\u8d25", e);
        }
        return result;
    }

    private EntityCheckUpDTO buildEntityCheckUpDTO(HshdCheckPM checkPM) throws Exception {
        EntityCheckUpDTO entityCheckUpDTO = new EntityCheckUpDTO();
        HshdConfig hshdConfig = SerializeUtil.deserializeFromJson(checkPM.getConfig(), HshdConfig.class);
        JtableContext context = this.buildJtableContext(checkPM.getTask(), checkPM.getFormScheme(), checkPM.getPeriod());
        String period = checkPM.getPeriod();
        String associatedPeriod = hshdConfig.getAssPeriod();
        if (associatedPeriod == null) {
            associatedPeriod = this.getAssociatedPeriod(period, hshdConfig.getPeriodConfig());
        }
        entityCheckUpDTO.setTaskKey(checkPM.getTask());
        entityCheckUpDTO.setFormSchemeKey(checkPM.getFormScheme());
        entityCheckUpDTO.setPeriod(checkPM.getPeriod());
        List<String> orgCode = checkPM.getOrgCode();
        if (orgCode.size() > 1) {
            IEntityTable entityTable = this.getEntityTable(checkPM.getFormScheme(), checkPM.getPeriod(), checkPM.getOrgCode());
            List rootRows = entityTable.getRootRows();
            orgCode = new ArrayList<String>(rootRows.size());
            for (IEntityRow rootRow : rootRows) {
                orgCode.add(rootRow.getEntityKeyData());
            }
        }
        entityCheckUpDTO.setScope(orgCode);
        entityCheckUpDTO.setWebTabName(checkPM.getWebTabName());
        entityCheckUpDTO.setDetailed(checkPM.isDetailed());
        entityCheckUpDTO.setAssociatedTaskKey(hshdConfig.getAssTask());
        entityCheckUpDTO.setAssociatedFormSchemeKey(hshdConfig.getAssFormScheme());
        entityCheckUpDTO.setAssociatedPeriod(associatedPeriod);
        entityCheckUpDTO.setJtableContext(context);
        return entityCheckUpDTO;
    }

    @Override
    public MultCheckItemDTO getDefaultCheckItem(String formSchemeKey) {
        MultCheckItemDTO multCheckItemDTO = new MultCheckItemDTO();
        multCheckItemDTO.setTitle("\u6237\u6570\u6838\u5bf9");
        multCheckItemDTO.setType("HSHD");
        List<ConfigInfo> configInfos = this.getAssFormSchemeConfigInfo(formSchemeKey);
        if (!CollectionUtils.isEmpty(configInfos)) {
            ConfigInfo configInfo = configInfos.get(0);
            HshdConfig config = new HshdConfig();
            config.setAssTask(configInfo.getAssTaskKey());
            config.setAssFormScheme(configInfo.getAssFormSchemeKey());
            config.setPeriodConfig(this.convertPeriodConfig(configInfo.getAssociation()));
            try {
                List orgLinkDefines = this.runTimeViewControllerApi.listTaskOrgLinkByTask(configInfo.getAssTaskKey());
                Optional first = orgLinkDefines.stream().findFirst();
                if (first.isPresent()) {
                    config.setMultiEntity(orgLinkDefines.size() > 1);
                    config.setAssEntity(((TaskOrgLinkDefine)first.get()).getEntity());
                }
                multCheckItemDTO.setConfig(SerializeUtil.serializeToJson(config));
            }
            catch (Exception e) {
                logger.error("\u521b\u5efa\u9ed8\u8ba4\u6237\u6570\u6838\u5bf9\u5ba1\u6838\u9879\u5931\u8d25", e);
            }
        }
        return multCheckItemDTO;
    }

    private PeriodConfig convertPeriodConfig(Association association) {
        PeriodConfig periodConfig = new PeriodConfig();
        if (association == null) {
            periodConfig.setType(PeriodMatchingType.PERIOD_TYPE_CURRENT.getValue());
            return periodConfig;
        }
        periodConfig.setType(association.getConfiguration());
        if (PeriodMatchingType.PERIOD_TYPE_PREYEAR.getValue() == periodConfig.getType().intValue()) {
            periodConfig.setValue(association.getLastIssue());
        } else if (PeriodMatchingType.PERIOD_TYPE_NEXT.getValue() == periodConfig.getType().intValue()) {
            periodConfig.setValue(association.getNextIssue());
        } else if (PeriodMatchingType.PERIOD_TYPE_SPECIFIED.getValue() == periodConfig.getType().intValue()) {
            periodConfig.setValue(association.getSpecified());
        } else if (PeriodMatchingType.PERIOD_TYPE_OFFSET.getValue() == periodConfig.getType().intValue()) {
            periodConfig.setValue(association.getPeriodOffset());
        } else {
            periodConfig.setValue(null);
        }
        periodConfig.setPreviousIssue(association.getLastIssue());
        periodConfig.setNextIssue(association.getNextIssue());
        return periodConfig;
    }

    @Override
    public Map<String, AssTaskVO> getAssTaskConfig(String formScheme) {
        List<ConfigInfo> configInfos = this.getAssFormSchemeConfigInfo(formScheme);
        if (CollectionUtils.isEmpty(configInfos)) {
            return Collections.emptyMap();
        }
        HashMap<String, AssTaskVO> res = new HashMap<String, AssTaskVO>();
        for (ConfigInfo configInfo : configInfos) {
            AssTaskVO taskVO = (AssTaskVO)res.get(configInfo.getAssTaskKey());
            if (taskVO == null) {
                taskVO = new AssTaskVO();
                taskVO.setFormSchemes(new HashMap<String, AssFormSchemeVO>());
                res.put(configInfo.getAssTaskKey(), taskVO);
                taskVO.setAssTask(configInfo.getAssTaskKey());
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(configInfo.getAssTaskKey());
                if (taskDefine != null) {
                    taskVO.setAssTaskTitle(taskDefine.getTitle());
                }
                List orgLinkDefines = this.runTimeViewControllerApi.listTaskOrgLinkByTask(configInfo.getAssTaskKey());
                for (TaskOrgLinkDefine orgLinkDefine : orgLinkDefines) {
                    TableModelDefine tableModel = this.metaService.getTableModel(orgLinkDefine.getEntity());
                    taskVO.addTaskOrgLink(new TaskOrgLinkVO(orgLinkDefine.getEntity(), tableModel.getTitle()));
                }
            }
            AssFormSchemeVO formSchemeVO = new AssFormSchemeVO();
            formSchemeVO.setAssFormScheme(configInfo.getAssFormSchemeKey());
            formSchemeVO.setPeriodConfig(this.convertPeriodConfig(configInfo.getAssociation()));
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(configInfo.getAssFormSchemeKey());
            if (formSchemeDefine != null) {
                formSchemeVO.setAssFormSchemeTitle(formSchemeDefine.getTitle());
            }
            taskVO.getFormSchemes().put(configInfo.getAssFormSchemeKey(), formSchemeVO);
        }
        return res;
    }
}

