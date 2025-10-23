/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.defaultlog.Logger
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.multcheck2.service.impl;

import com.jiuqi.bi.core.jobs.defaultlog.Logger;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResRecord;
import com.jiuqi.nr.multcheck2.bean.MultcheckResScheme;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.common.CheckRestultState;
import com.jiuqi.nr.multcheck2.common.CheckSource;
import com.jiuqi.nr.multcheck2.common.ResultType;
import com.jiuqi.nr.multcheck2.common.SchemeExecuteResult;
import com.jiuqi.nr.multcheck2.common.SerializeUtil;
import com.jiuqi.nr.multcheck2.provider.FailedOrgInfo;
import com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider;
import com.jiuqi.nr.multcheck2.service.IMCDimService;
import com.jiuqi.nr.multcheck2.service.IMCEnvService;
import com.jiuqi.nr.multcheck2.service.IMCOrgService;
import com.jiuqi.nr.multcheck2.service.IMCResultService;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nr.multcheck2.service.res.FileItemService;
import com.jiuqi.nr.multcheck2.service.res.FileOrgService;
import com.jiuqi.nr.multcheck2.service.res.FileRecordService;
import com.jiuqi.nr.multcheck2.service.res.FileResUtil;
import com.jiuqi.nr.multcheck2.service.res.FileSchemeService;
import com.jiuqi.nr.multcheck2.web.result.GatherAuto;
import com.jiuqi.nr.multcheck2.web.result.MOrgTreeNode;
import com.jiuqi.nr.multcheck2.web.result.MultcheckContext;
import com.jiuqi.nr.multcheck2.web.result.MultiplScheme;
import com.jiuqi.nr.multcheck2.web.result.MultiplSchemeInfo;
import com.jiuqi.nr.multcheck2.web.result.OrgTreeNode;
import com.jiuqi.nr.multcheck2.web.result.ResultDetailVO;
import com.jiuqi.nr.multcheck2.web.result.ResultItemPMVO;
import com.jiuqi.nr.multcheck2.web.result.ResultItemTBVO;
import com.jiuqi.nr.multcheck2.web.result.ResultParam;
import com.jiuqi.nr.multcheck2.web.result.SchemeTreeNode;
import com.jiuqi.nr.multcheck2.web.result.SingleOrg;
import com.jiuqi.nr.multcheck2.web.result.SingleScheme;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MCResultServiceImpl
implements IMCResultService {
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(MCResultServiceImpl.class);
    @Autowired
    private IRuntimeTaskService taskService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    @Qualifier(value="RuntimeDataSchemeNoCacheServiceImpl-NO_CACHE")
    private IRuntimeDataSchemeService dataSchemeServiceNo;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController runTimeViewController;
    @Autowired
    private IRunTimeViewController runTimeViewController1;
    @Autowired
    private IMCSchemeService schemeService;
    @Autowired
    private IMCEnvService envService;
    @Autowired
    private IMCDimService mcDimService;
    @Autowired
    private FileRecordService fRecord;
    @Autowired
    private FileSchemeService fScheme;
    @Autowired
    private FileItemService fItem;
    @Autowired
    private FileOrgService fOrg;
    @Autowired
    private FileResUtil fUtil;
    @Autowired
    private IMCOrgService mcOrgService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    protected IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IAdjustPeriodService adjustPeriodService;
    @Autowired
    private SubDatabaseTableNamesProvider subTableNamesProvider;

    @Override
    public String getTableName(String tablePrefix, String task, DataScheme dataScheme) {
        String tableName = tablePrefix + dataScheme.getBizCode();
        return this.subTableNamesProvider.getSubDatabaseTableName(task, tableName);
    }

    @Override
    public List<String> getDynamicFieldsByTask(String task) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(task);
        return this.mcDimService.getDynamicFields(taskDefine.getDataScheme());
    }

    @Override
    public void cleanRecord(Date cleanDate, Logger defaultLogger) {
        List taskDefines = this.taskService.getAllTaskDefines();
        HashSet<String> dataSchemeKeys = new HashSet<String>();
        for (TaskDefine taskDefine : taskDefines) {
            DataScheme dataScheme = this.dataSchemeServiceNo.getDataScheme(taskDefine.getDataScheme());
            if (!dataSchemeKeys.add(taskDefine.getDataScheme())) continue;
            try {
                String record = this.getTableName("NR_MCR_RECORD_", taskDefine.getKey(), dataScheme);
                this.fRecord.cleanRecord(cleanDate, record);
                String scheme = this.getTableName("NR_MCR_SCHEME_", taskDefine.getKey(), dataScheme);
                this.fScheme.cleanRecord(cleanDate, scheme);
                String item = this.getTableName("NR_MCR_ITEM_", taskDefine.getKey(), dataScheme);
                this.fItem.cleanRecord(cleanDate, item);
                String org = this.getTableName("NR_MCR_ITEMORG_", taskDefine.getKey(), dataScheme);
                this.fOrg.cleanRecord(cleanDate, org);
                defaultLogger.info(String.format("\u5f00\u59cb\u6e05\u7406\u4efb\u52a1\u3010%s|%s\u3011\u5bf9\u5e94\u7684\u6570\u636e\u65b9\u6848\u3010%s|%s\u3011\u5ba1\u68382.0\u7ed3\u679c\u8868\u5b8c\u6210", taskDefine.getTaskCode(), taskDefine.getTitle(), dataScheme.getCode(), dataScheme.getTitle()));
            }
            catch (Exception e) {
                defaultLogger.error("\u7efc\u5408\u5ba1\u6838\u8ba1\u5212\u4efb\u52a1\u6e05\u9664\u5386\u53f2\u6570\u636e\u51fa\u73b0\u9519\u8bef\uff0c\u6570\u636e\u65b9\u6848\uff1a" + dataScheme.getTitle() + "[" + dataScheme.getCode() + "]\u5931\u8d25:" + e.getMessage());
                log.error("\u7efc\u5408\u5ba1\u6838\u8ba1\u5212\u4efb\u52a1\u6e05\u9664\u5386\u53f2\u6570\u636e\u51fa\u73b0\u9519\u8bef\uff0c\u6570\u636e\u65b9\u6848\uff1a" + dataScheme.getTitle() + "[" + dataScheme.getCode() + "]\u5931\u8d25", (Throwable)e);
            }
        }
        List<IMultcheckItemProvider> providerList = this.envService.getProviderList();
        for (IMultcheckItemProvider provider : providerList) {
            provider.cleanCheckItemTables(cleanDate);
        }
    }

    @Override
    public void itemBatchAdd(List<MultcheckResItem> items, String tableName) {
        this.fItem.batchAdd(items, tableName);
    }

    @Override
    public List<MultcheckResItem> getResultItem(String recordKey, String tableName) {
        return this.fItem.getByRecord(recordKey, tableName);
    }

    @Override
    public void schemeBatchAdd(List<MultcheckResScheme> resSchemes, String tableName) {
        this.fScheme.batchAdd(resSchemes, tableName);
    }

    @Override
    public List<MultcheckResScheme> getResultScheme(String recordKey, String tableName) {
        return this.fScheme.getByRecord(recordKey, tableName);
    }

    @Override
    public void recordAdd(String tableName, MultcheckResRecord record, List<String> dims) {
        this.fRecord.add(tableName, record, dims);
    }

    @Override
    public void recordAdd(String tableName, MultcheckResRecord record) {
        this.fRecord.add(tableName, record);
    }

    @Override
    public MultcheckResRecord getResultRecord(String tableName, String key, List<String> dims) {
        return this.fRecord.getRecordByKey(tableName, key, dims);
    }

    @Override
    public ResultDetailVO buildResult(String runId, String task) throws Exception {
        List<String> dynamicFields;
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String recordTable = this.getTableName("NR_MCR_RECORD_", taskDefine.getKey(), dataScheme);
        MultcheckResRecord record = this.getResultRecord(recordTable, runId, dynamicFields = this.mcDimService.getDynamicFields(dataScheme.getKey()));
        if (record.getSuccess() < 0) {
            int size = record.getSuccess() * -1;
            MultcheckContext context = new MultcheckContext();
            context.setRunId(runId);
            context.setTask(task);
            context.setPeriod(record.getPeriod());
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            context.setTime(sf.format(record.getBegin()) + " - " + sf.format(record.getEnd()));
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
            context.setPeriodEntity(periodProvider.getPeriodEntity().getCode());
            if (record.getSource() == CheckSource.APP_SCHEME) {
                MultcheckScheme scheme = this.schemeService.getSchemeByKey(record.getSchemeKey());
                context.setMcScheme(scheme.getKey());
                context.setFormScheme(scheme.getFormScheme());
                context.setOrgEntity(scheme.getOrg());
            } else {
                SchemePeriodLinkDefine formScheme = this.runTimeViewController1.getSchemePeriodLinkByPeriodAndTask(context.getPeriod(), context.getTask());
                if (formScheme != null) {
                    context.setFormScheme(formScheme.getSchemeKey());
                }
                context.setOrgEntity(record.getSchemeKey());
            }
            ResultDetailVO res = new ResultDetailVO();
            res.setType(ResultType.GATHERAUTO);
            res.setContext(context);
            res.setGatherAutoDetail(new GatherAuto(String.format("\u81ea\u52a8\u6c47\u603b\u4efb\u52a1\uff0c\u9009\u62e9\u5ba1\u6838\u5355\u4f4d %s \u5bb6\u5747\u4e3a\u5408\u5e76\u8282\u70b9\uff0c\u9ed8\u8ba4\u5168\u90e8\u5ba1\u6838\u901a\u8fc7", size)));
            return res;
        }
        String itemTable = this.getTableName("NR_MCR_ITEM_", taskDefine.getKey(), dataScheme);
        List<MultcheckResItem> items = this.getResultItem(runId, itemTable);
        String schemeTable = this.getTableName("NR_MCR_SCHEME_", taskDefine.getKey(), dataScheme);
        List<MultcheckResScheme> noSortSchemes = this.getResultScheme(runId, schemeTable);
        List<MultcheckScheme> schemeList = this.schemeService.getSchemeByKeys(noSortSchemes.stream().map(MultcheckResScheme::getSchemeKey).collect(Collectors.toList()));
        ArrayList<MultcheckResScheme> schemes = new ArrayList<MultcheckResScheme>();
        for (MultcheckScheme scheme : schemeList) {
            Optional<MultcheckResScheme> first = noSortSchemes.stream().filter(e -> e.getSchemeKey().equals(scheme.getKey())).findFirst();
            first.ifPresent(schemes::add);
        }
        if (schemes.size() > 1) {
            return this.buildMultipleScheme(runId, taskDefine, record, schemes, items, dataScheme);
        }
        int count = record.getSuccess() + record.getFailed();
        if (count > 1) {
            return this.buildSingleScheme(runId, taskDefine, record, schemes, items, dataScheme, schemeList.get(0));
        }
        return this.buildSingleOrg(runId, taskDefine, record, schemes, items, dataScheme);
    }

    private ResultDetailVO buildMultipleScheme(String runId, TaskDefine taskDefine, MultcheckResRecord record, List<MultcheckResScheme> schemes, List<MultcheckResItem> items, DataScheme dataScheme) throws Exception {
        ResultDetailVO result = new ResultDetailVO(ResultType.MULTIPLESCHEME);
        result.setContext(this.buildContext(runId, taskDefine, dataScheme, record, schemes));
        MultiplScheme multiplScheme = new MultiplScheme();
        List<MultcheckScheme> schemeList = this.schemeService.getSchemeByKeys(schemes.stream().map(MultcheckResScheme::getSchemeKey).collect(Collectors.toList()));
        Map<String, String> schemeToTitle = schemeList.stream().collect(Collectors.toMap(MultcheckScheme::getKey, MultcheckScheme::getTitle));
        ArrayList<String> successList = new ArrayList<String>();
        HashMap<String, List<String>> successWithExplainMap = new HashMap<String, List<String>>();
        HashMap<String, Map<String, FailedOrgInfo>> failedMap = new HashMap<String, Map<String, FailedOrgInfo>>();
        HashMap<String, List<String>> ignoreMap = new HashMap<String, List<String>>();
        HashMap<String, String> orgToScheme = new HashMap<String, String>();
        HashMap<String, MultcheckItem> itemMap = new HashMap<String, MultcheckItem>();
        HashMap<String, MultiplSchemeInfo> schemeInfoMap = new HashMap<String, MultiplSchemeInfo>();
        HashMap schemeToItems = new HashMap();
        for (MultcheckResScheme scheme : schemes) {
            SchemeExecuteResult eResult = SerializeUtil.deserializeFromJson(scheme.getOrgs(), SchemeExecuteResult.class);
            successList.addAll(eResult.getSuccessList());
            for (String org : eResult.getSuccessList()) {
                orgToScheme.put(org, scheme.getSchemeKey());
            }
            for (String org : eResult.getFailedMap().keySet()) {
                orgToScheme.put(org, scheme.getSchemeKey());
            }
            for (String org : eResult.getSuccessWithExplainMap().keySet()) {
                orgToScheme.put(org, scheme.getSchemeKey());
            }
            successWithExplainMap.putAll(eResult.getSuccessWithExplainMap());
            failedMap.putAll(eResult.getFailedMap());
            ignoreMap.putAll(eResult.getIgnoreMap());
            List<MultcheckItem> list = this.schemeService.getItemInfoList(scheme.getSchemeKey());
            ArrayList<String> itemKeys = new ArrayList<String>();
            for (MultcheckItem item : list) {
                itemMap.put(item.getKey(), item);
                itemKeys.add(item.getKey());
            }
            schemeToItems.put(scheme.getSchemeKey(), itemKeys);
            if (schemeInfoMap.containsKey(scheme.getSchemeKey())) continue;
            MultiplSchemeInfo info = new MultiplSchemeInfo();
            info.setTitle(schemeToTitle.get(scheme.getSchemeKey()));
            info.setSuccess(eResult.getSuccessList().size());
            info.setFailed(eResult.getFailedMap().size());
            info.setTime(ChronoUnit.SECONDS.between(scheme.getBegin().toInstant(), scheme.getEnd().toInstant()) + "\u79d2");
            ArrayList<Object> list2 = new ArrayList<Object>(Collections.nCopies(itemKeys.size(), null));
            info.setSchemeItemState(list2);
            info.setReportDimVO(eResult.getReportDim());
            info.setOrgDims(eResult.getOrgDims());
            info.setItemsOrgDims(eResult.getItemsOrgDims());
            schemeInfoMap.put(scheme.getSchemeKey(), info);
        }
        multiplScheme.setFailed(record.getFailed());
        multiplScheme.setOrgTree(this.getMOrgTree(record, successList, successWithExplainMap, failedMap, ignoreMap, orgToScheme, schemeInfoMap, schemeList.get(0)));
        multiplScheme.setDesc("\u5355\u4f4d\u5ba1\u6838\u901a\u8fc7" + record.getSuccess() + " \u5bb6, \u672a\u901a\u8fc7" + record.getFailed() + " \u5bb6");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HashMap<String, ResultItemPMVO> itemPM = new HashMap<String, ResultItemPMVO>();
        for (MultcheckResItem multcheckResItem : items) {
            ResultItemTBVO rItem = new ResultItemTBVO();
            MultcheckItem mcItem = (MultcheckItem)itemMap.get(multcheckResItem.getItemKey());
            IMultcheckItemProvider provider = this.envService.getProvider(mcItem.getType());
            rItem.setKey(mcItem.getKey());
            rItem.setTitle(mcItem.getTitle());
            rItem.setState(multcheckResItem.getState());
            rItem.setSuccess(multcheckResItem.getSuccess());
            rItem.setFailed(multcheckResItem.getFailed());
            rItem.setIgnore(multcheckResItem.getIgnore());
            rItem.setTime(ChronoUnit.SECONDS.between(multcheckResItem.getBegin().toInstant(), multcheckResItem.getEnd().toInstant()) + "\u79d2");
            rItem.setAlwaysDisplayView(provider.entryAlwaysDisplayView(new HashMap<String, Object>()));
            int index = ((List)schemeToItems.get(multcheckResItem.getSchemeKey())).indexOf(multcheckResItem.getItemKey());
            ((MultiplSchemeInfo)schemeInfoMap.get(multcheckResItem.getSchemeKey())).getSchemeItemState().set(index, rItem);
            if (CheckRestultState.SUCCESS == multcheckResItem.getState()) {
                int itemSuccess = ((MultiplSchemeInfo)schemeInfoMap.get(multcheckResItem.getSchemeKey())).getItemSuccess();
                ((MultiplSchemeInfo)schemeInfoMap.get(multcheckResItem.getSchemeKey())).setItemSuccess(++itemSuccess);
            } else if (CheckRestultState.FAIL == multcheckResItem.getState()) {
                int itemFailed = ((MultiplSchemeInfo)schemeInfoMap.get(multcheckResItem.getSchemeKey())).getItemFailed();
                ((MultiplSchemeInfo)schemeInfoMap.get(multcheckResItem.getSchemeKey())).setItemFailed(++itemFailed);
            } else if (CheckRestultState.WARN == multcheckResItem.getState()) {
                int itemWarn = ((MultiplSchemeInfo)schemeInfoMap.get(multcheckResItem.getSchemeKey())).getItemWarn();
                ((MultiplSchemeInfo)schemeInfoMap.get(multcheckResItem.getSchemeKey())).setItemWarn(++itemWarn);
            } else if (CheckRestultState.SUCCESS_ERROR == multcheckResItem.getState()) {
                int itemSuccessError = ((MultiplSchemeInfo)schemeInfoMap.get(multcheckResItem.getSchemeKey())).getItemSuccessHasError();
                ((MultiplSchemeInfo)schemeInfoMap.get(multcheckResItem.getSchemeKey())).setItemSuccessHasError(++itemSuccessError);
            }
            ResultItemPMVO pm = new ResultItemPMVO();
            pm.setMcscheme(mcItem.getScheme());
            pm.setPlugin(provider.getResultPlugin());
            pm.setEntryView(provider.getEntryView());
            pm.setRunConfig(multcheckResItem.getRunConfig());
            itemPM.put(multcheckResItem.getItemKey(), pm);
        }
        LinkedHashMap<String, MultiplSchemeInfo> sortedMap = new LinkedHashMap<String, MultiplSchemeInfo>();
        for (MultcheckScheme scheme : schemeList) {
            String schemeKey = scheme.getKey();
            if (!schemeInfoMap.containsKey(schemeKey)) continue;
            sortedMap.put(schemeKey, (MultiplSchemeInfo)schemeInfoMap.get(schemeKey));
        }
        multiplScheme.setSchemeInfoMap(sortedMap);
        multiplScheme.setItemPM(itemPM);
        SchemeTreeNode schemeTreeNode = new SchemeTreeNode();
        schemeTreeNode.setKey("SCHEME000");
        schemeTreeNode.setCode("SCHEME000");
        schemeTreeNode.setTitle("\u5168\u90e8\u65b9\u6848");
        ITree sRootNode = new ITree((INode)schemeTreeNode);
        sRootNode.setExpanded(true);
        sRootNode.setSelected(true);
        ArrayList<ITree> sChildren = new ArrayList<ITree>();
        sRootNode.setChildren(sChildren);
        for (MultcheckScheme scheme : schemeList) {
            SchemeTreeNode node = new SchemeTreeNode();
            node.setKey(scheme.getKey());
            node.setCode(scheme.getCode());
            node.setTitle(scheme.getTitle());
            ITree treeNode = new ITree((INode)node);
            treeNode.setExpanded(false);
            treeNode.setLeaf(true);
            sChildren.add(treeNode);
        }
        ArrayList<ITree<SchemeTreeNode>> schemeTree = new ArrayList<ITree<SchemeTreeNode>>();
        schemeTree.add(sRootNode);
        multiplScheme.setSchemeTree(schemeTree);
        result.setMultiplDetail(multiplScheme);
        return result;
    }

    private ResultDetailVO buildSingleScheme(String runId, TaskDefine taskDefine, MultcheckResRecord record, List<MultcheckResScheme> schemes, List<MultcheckResItem> items, DataScheme dataScheme, MultcheckScheme multcheckScheme) throws Exception {
        ResultDetailVO result = new ResultDetailVO(ResultType.SINGLESCHEME);
        result.setContext(this.buildContext(runId, taskDefine, dataScheme, record, schemes));
        SingleScheme singleScheme = new SingleScheme();
        singleScheme.setDesc("\u5355\u4f4d\u5ba1\u6838\u901a\u8fc7" + record.getSuccess() + " \u5bb6\uff0c\u672a\u901a\u8fc7" + record.getFailed() + " \u5bb6");
        singleScheme.setFailed(record.getFailed());
        SchemeExecuteResult eResult = SerializeUtil.deserializeFromJson(schemes.get(0).getOrgs(), SchemeExecuteResult.class);
        this.getOrgTree(singleScheme, record, eResult, multcheckScheme);
        singleScheme.setReportDimVO(eResult.getReportDim());
        singleScheme.setOrgDims(eResult.getOrgDims());
        singleScheme.setItemsOrgDims(eResult.getItemsOrgDims());
        result.setSchemeDetail(singleScheme);
        ArrayList<ResultItemTBVO> itemTB = new ArrayList<ResultItemTBVO>();
        HashMap<String, ResultItemPMVO> itemPM = new HashMap<String, ResultItemPMVO>();
        singleScheme.setItemTB(itemTB);
        singleScheme.setItemPM(itemPM);
        this.buildItems(schemes, items, itemTB, itemPM);
        return result;
    }

    private void buildItems(List<MultcheckResScheme> schemes, List<MultcheckResItem> items, List<ResultItemTBVO> itemTB, Map<String, ResultItemPMVO> itemPM) {
        String schemeKey = schemes.get(0).getSchemeKey();
        List<MultcheckItem> itemInfoList = this.schemeService.getItemInfoList(schemeKey);
        Map<String, MultcheckResItem> resItemMap = items.stream().collect(Collectors.toMap(MultcheckResItem::getItemKey, item -> item));
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (MultcheckItem item2 : itemInfoList) {
            MultcheckResItem resItem = resItemMap.get(item2.getKey());
            if (resItem == null) continue;
            IMultcheckItemProvider provider = this.envService.getProvider(item2.getType());
            ResultItemTBVO tb = new ResultItemTBVO();
            tb.setKey(item2.getKey());
            tb.setTitle(item2.getTitle());
            tb.setState(resItem.getState());
            tb.setSuccess(resItem.getSuccess());
            tb.setFailed(resItem.getFailed());
            tb.setIgnore(resItem.getIgnore());
            tb.setTime(ChronoUnit.SECONDS.between(resItem.getBegin().toInstant(), resItem.getEnd().toInstant()) + "\u79d2");
            tb.setAlwaysDisplayView(provider.entryAlwaysDisplayView(new HashMap<String, Object>()));
            itemTB.add(tb);
            ResultItemPMVO pm = new ResultItemPMVO();
            pm.setPlugin(provider.getResultPlugin());
            pm.setEntryView(provider.getEntryView());
            pm.setRunConfig(resItem.getRunConfig());
            itemPM.put(item2.getKey(), pm);
        }
    }

    private ResultDetailVO buildSingleOrg(String runId, TaskDefine taskDefine, MultcheckResRecord record, List<MultcheckResScheme> schemes, List<MultcheckResItem> items, DataScheme dataScheme) throws Exception {
        Iterator<Object> iterator;
        ResultDetailVO result = new ResultDetailVO(ResultType.SINGLEORG);
        SingleOrg singleOrg = new SingleOrg();
        result.setSingleDetail(singleOrg);
        SchemeExecuteResult eResult = SerializeUtil.deserializeFromJson(schemes.get(0).getOrgs(), SchemeExecuteResult.class);
        String org = null;
        Map<Object, Object> failedOrgInfoMap = new HashMap();
        if (record.getSuccess() == 1) {
            if (!CollectionUtils.isEmpty(eResult.getSuccessList())) {
                org = eResult.getSuccessList().get(0);
            } else if (!CollectionUtils.isEmpty(eResult.getSuccessWithExplainMap())) {
                iterator = eResult.getSuccessWithExplainMap().keySet().iterator();
                while (iterator.hasNext()) {
                    String key;
                    org = key = (String)iterator.next();
                }
            }
            singleOrg.setDesc("\u3010\u5ba1\u6838\u901a\u8fc7\u3011");
            singleOrg.setCheckRes(true);
        } else {
            iterator = eResult.getFailedMap().entrySet().iterator();
            if (iterator.hasNext()) {
                Map.Entry<String, Map<String, FailedOrgInfo>> entry = iterator.next();
                org = entry.getKey();
            }
            failedOrgInfoMap = eResult.getFailedMap().get(org);
            singleOrg.setDesc("\u3010\u5ba1\u6838\u672a\u901a\u8fc7\u3011\u5176\u4e2d" + failedOrgInfoMap.size() + " \u4e2a\u5ba1\u6838\u9879\u672a\u901a\u8fc7");
            singleOrg.setCheckRes(false);
        }
        if (!CollectionUtils.isEmpty(eResult.getIgnoreMap())) {
            singleOrg.setIgnore(eResult.getIgnoreMap().get(org));
        }
        if (!CollectionUtils.isEmpty(eResult.getSuccessWithExplainMap())) {
            singleOrg.setSuccessWithExplain(eResult.getSuccessWithExplainMap().get(org));
        }
        singleOrg.setReportDimVO(eResult.getReportDim());
        singleOrg.setOrgDims(eResult.getOrgDims());
        singleOrg.setItemsOrgDims(eResult.getItemsOrgDims());
        ArrayList<ResultItemTBVO> itemTB = new ArrayList<ResultItemTBVO>();
        HashMap<String, ResultItemPMVO> itemPM = new HashMap<String, ResultItemPMVO>();
        result.setContext(this.buildContext(runId, taskDefine, dataScheme, record, schemes, org));
        singleOrg.setOrgCode(org);
        singleOrg.setItemTB(itemTB);
        singleOrg.setItemPM(itemPM);
        singleOrg.setFailed(failedOrgInfoMap);
        this.buildItems(schemes, items, itemTB, itemPM);
        return result;
    }

    private MultcheckContext buildContext(String runId, TaskDefine taskDefine, DataScheme dataScheme, MultcheckResRecord record, List<MultcheckResScheme> schemes) throws Exception {
        return this.buildContext(runId, taskDefine, dataScheme, record, schemes, null);
    }

    private MultcheckContext buildContext(String runId, TaskDefine taskDefine, DataScheme dataScheme, MultcheckResRecord record, List<MultcheckResScheme> schemes, String org) throws Exception {
        MultcheckContext context = new MultcheckContext();
        ResultParam resultParam = new ResultParam();
        context.setResultParam(resultParam);
        resultParam.setTask(taskDefine.getTitle());
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
        String periodTitle = periodProvider.getPeriodTitle(record.getPeriod());
        context.setPeriodEntity(periodProvider.getPeriodEntity().getCode());
        resultParam.setPeriod(periodTitle);
        MultcheckScheme multcheckScheme = this.schemeService.getSchemeByKey(schemes.get(0).getSchemeKey());
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(multcheckScheme.getOrg());
        String period = record.getPeriod();
        if (StringUtils.hasText(org)) {
            List<MCLabel> entityLabelList = this.mcOrgService.getOrgLabels(taskDefine.getKey(), period, multcheckScheme.getOrg(), Arrays.asList(org));
            MCLabel label = entityLabelList.get(0);
            resultParam.setOrg(new MCLabel(entityDefine.getTitle(), label.getOrgCode() + " | " + label.getTitle()));
        } else {
            resultParam.setOrg(new MCLabel(entityDefine.getTitle(), String.valueOf(record.getSuccess() + record.getFailed())));
        }
        resultParam.setExecuteCount(record.getSuccess() + record.getFailed());
        context.setOrgEntity(entityDefine.getId());
        context.setRunId(runId);
        context.setTask(taskDefine.getKey());
        context.setPeriod(record.getPeriod());
        List<String> schemeKeys = schemes.stream().map(MultcheckResScheme::getSchemeKey).collect(Collectors.toList());
        List<MultcheckScheme> mcScheme = this.schemeService.getSchemeByKeys(schemeKeys);
        context.setMcScheme(String.join((CharSequence)";", schemeKeys));
        context.setFormScheme(mcScheme.get(0).getFormScheme());
        List<DataDimension> dims = this.mcDimService.getDynamicDimsForPage(dataScheme.getKey());
        context.setEnableDim(!CollectionUtils.isEmpty(dims));
        if (!CollectionUtils.isEmpty(record.getDims())) {
            context.setDimSet(record.getDims());
            if (record.getDims().size() == 1 && record.getDims().containsKey("ADJUST") && !"0".equals(record.getDims().get("ADJUST"))) {
                resultParam.setPeriod(periodTitle + "(" + this.adjustPeriodService.queryAdjustPeriods(dataScheme.getKey(), period, record.getDims().get("ADJUST")).getTitle() + ")");
            } else {
                HashMap dimNameEntityIdMap = new HashMap();
                for (DataDimension dim : dims) {
                    IEntityDefine entity = this.entityMetaService.queryEntity(dim.getDimKey());
                    if (entity == null) continue;
                    dimNameEntityIdMap.put(entity.getDimensionName(), entity.getId());
                }
                for (String dimKey : record.getDims().keySet()) {
                    String dimValue = record.getDims().get(dimKey);
                    if (dimKey.equals("MD_ORG")) continue;
                    if (dimKey.equals("ADJUST")) {
                        if ("0".equals(record.getDims().get("ADJUST"))) continue;
                        resultParam.setPeriod(periodTitle + "(" + this.adjustPeriodService.queryAdjustPeriods(dataScheme.getKey(), period, record.getDims().get("ADJUST")).getTitle() + ")");
                        continue;
                    }
                    String entityId = dimNameEntityIdMap.containsKey(dimKey) ? (String)dimNameEntityIdMap.get(dimKey) : dimKey;
                    entityDefine = this.entityMetaService.queryEntity(entityId);
                    EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
                    if (entityViewDefine == null || entityDefine == null) continue;
                    if (!StringUtils.hasText(dimValue)) {
                        resultParam.addDimList(new MCLabel(entityDefine.getTitle(), "\u5168\u90e8"));
                        continue;
                    }
                    boolean isAdd = false;
                    if (dimKey.equals("MD_CURRENCY")) {
                        if ("PROVIDER_BASECURRENCY".equals(dimValue)) {
                            resultParam.addDimList(new MCLabel(entityDefine.getTitle(), "\u672c\u4f4d\u5e01"));
                            isAdd = true;
                        } else if ("PROVIDER_PBASECURRENCY".equals(dimValue)) {
                            resultParam.addDimList(new MCLabel(entityDefine.getTitle(), "\u4e0a\u7ea7\u672c\u4f4d\u5e01"));
                            isAdd = true;
                        }
                    }
                    if (isAdd) continue;
                    DimensionValueSet dimensionValueSet = new DimensionValueSet();
                    dimensionValueSet.setValue("DATATIME", (Object)period);
                    if (StringUtils.hasText(dimValue) && !"PROVIDER_BASECURRENCY".equals(dimValue) && !"PROVIDER_PBASECURRENCY".equals(dimValue)) {
                        dimensionValueSet.setValue(dimKey, Arrays.asList(dimValue.split(";")));
                    }
                    IEntityQuery query = this.entityDataService.newEntityQuery();
                    query.sorted(true);
                    query.setAuthorityOperations(AuthorityType.Read);
                    query.setEntityView(entityViewDefine);
                    query.setMasterKeys(dimensionValueSet);
                    ExecutorContext context1 = new ExecutorContext(this.dataDefinitionRuntimeController);
                    context1.setVarDimensionValueSet(dimensionValueSet);
                    context1.setPeriodView(taskDefine.getDateTime());
                    IEntityTable entityTable = query.executeReader((IContext)context1);
                    List allrows = entityTable.getAllRows();
                    ArrayList<String> titles = new ArrayList<String>();
                    for (IEntityRow row : allrows) {
                        if (!StringUtils.hasText(dimValue)) continue;
                        titles.add(row.getTitle());
                    }
                    resultParam.addDimList(new MCLabel(entityDefine.getTitle(), String.join((CharSequence)" ", titles)));
                }
            }
        }
        resultParam.setScheme(mcScheme.stream().map(s -> "\u3010" + s.getTitle() + "\u3011").collect(Collectors.joining(" ")));
        if (!schemes.isEmpty()) {
            for (MultcheckResScheme scheme : schemes) {
                List<MCLabel> unboundList;
                SchemeExecuteResult eResult = SerializeUtil.deserializeFromJson(scheme.getOrgs(), SchemeExecuteResult.class);
                if (eResult == null || CollectionUtils.isEmpty(unboundList = eResult.getUnboundList())) continue;
                context.setUnboundList(unboundList);
                resultParam.setUnboundCount(unboundList.size());
                break;
            }
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        context.setTime(sf.format(record.getBegin()) + " - " + sf.format(record.getEnd()));
        context.setTotalTime(ChronoUnit.SECONDS.between(record.getBegin().toInstant(), record.getEnd().toInstant()) + "\u79d2");
        return context;
    }

    private void getOrgTree(SingleScheme singleScheme, MultcheckResRecord record, SchemeExecuteResult eResult, MultcheckScheme multcheckScheme) throws Exception {
        ArrayList<ITree<OrgTreeNode>> treeList = new ArrayList<ITree<OrgTreeNode>>();
        HashSet<String> orgList = new HashSet<String>();
        orgList.addAll(eResult.getSuccessList());
        orgList.addAll(eResult.getFailedMap().keySet());
        OrgTreeNode root = new OrgTreeNode();
        root.setKey("MC000");
        root.setCode("MC000");
        root.setTitle("\u5168\u90e8\u5355\u4f4d");
        root.setTitle("\u5168\u90e8\u5355\u4f4d\uff08" + orgList.size() + "\uff09");
        ITree rootNode = new ITree((INode)root);
        rootNode.setExpanded(true);
        rootNode.setSelected(true);
        ArrayList<ITree> children = new ArrayList<ITree>();
        rootNode.setChildren(children);
        treeList.add(rootNode);
        IEntityTable entityTable = this.mcOrgService.getTreeEntityTable(multcheckScheme, record.getPeriod(), new ArrayList<String>(orgList));
        List rootRows = entityTable.getRootRows();
        for (IEntityRow row : rootRows) {
            String entityKey = row.getEntityKeyData();
            OrgTreeNode node = new OrgTreeNode(row, eResult, entityKey);
            ITree treeNode = new ITree((INode)node);
            singleScheme.addOrg(entityKey);
            treeNode.setIcons(new String[]{this.getNodeIcon(node)});
            treeNode.setChildren(this.getChildren(entityKey, entityTable, eResult, singleScheme));
            treeNode.setLeaf(CollectionUtils.isEmpty(treeNode.getChildren()));
            children.add(treeNode);
        }
        singleScheme.setOrgTree(treeList);
    }

    @NotNull
    private String getNodeIcon(OrgTreeNode node) {
        String icon = "#icon-J_HXGLB_C_ZFJS_GreenDot";
        if (!CollectionUtils.isEmpty(node.getFailed())) {
            icon = "#icon-J_HXGLB_C_ZFJS_RedDot";
        } else if (!CollectionUtils.isEmpty(node.getSuccessWithExplain())) {
            icon = "#icon-J_HXGLB_C_ZFJS_YellowDot";
        }
        return icon;
    }

    private List<ITree<OrgTreeNode>> getChildren(String parentId, IEntityTable entityTable, SchemeExecuteResult eResult, SingleScheme singleScheme) {
        ArrayList<ITree<OrgTreeNode>> childList = new ArrayList<ITree<OrgTreeNode>>();
        List childRows = entityTable.getChildRows(parentId);
        for (IEntityRow childRow : childRows) {
            String entityKey = childRow.getEntityKeyData();
            singleScheme.addOrg(entityKey);
            List<ITree<OrgTreeNode>> children = this.getChildren(entityKey, entityTable, eResult, singleScheme);
            OrgTreeNode node = new OrgTreeNode(childRow, eResult, entityKey);
            ITree treeNode = new ITree((INode)node);
            treeNode.setIcons(new String[]{this.getNodeIcon(node)});
            if (CollectionUtils.isEmpty(children)) {
                treeNode.setLeaf(true);
            } else {
                treeNode.setChildren(children);
            }
            childList.add((ITree<OrgTreeNode>)treeNode);
        }
        return childList;
    }

    private List<ITree<MOrgTreeNode>> getMOrgTree(MultcheckResRecord record, List<String> successList, Map<String, List<String>> successWithExplainMap, Map<String, Map<String, FailedOrgInfo>> failedMap, Map<String, List<String>> ignoreMap, Map<String, String> orgToScheme, Map<String, MultiplSchemeInfo> schemeInfoMap, MultcheckScheme multcheckScheme) throws Exception {
        ArrayList<ITree<MOrgTreeNode>> treeList = new ArrayList<ITree<MOrgTreeNode>>();
        HashSet<String> orgList = new HashSet<String>();
        orgList.addAll(successList);
        orgList.addAll(failedMap.keySet());
        MOrgTreeNode root = new MOrgTreeNode();
        root.setKey("MC000");
        root.setCode("MC000");
        root.setTitle("\u5168\u90e8\u5355\u4f4d");
        root.setTitle("\u5168\u90e8\u5355\u4f4d(" + orgList.size() + ")");
        ITree rootNode = new ITree((INode)root);
        rootNode.setExpanded(true);
        rootNode.setSelected(true);
        ArrayList<ITree> children = new ArrayList<ITree>();
        rootNode.setChildren(children);
        treeList.add(rootNode);
        IEntityTable entityTable = this.mcOrgService.getTreeEntityTable(multcheckScheme, record.getPeriod(), new ArrayList<String>(orgList));
        List rootRows = entityTable.getRootRows();
        for (IEntityRow row : rootRows) {
            String entityKey = row.getEntityKeyData();
            String schemeKey = orgToScheme.get(entityKey);
            MultiplSchemeInfo multiplSchemeInfo = schemeInfoMap.get(schemeKey);
            multiplSchemeInfo.addOrg(entityKey);
            MOrgTreeNode node = new MOrgTreeNode(row, successWithExplainMap, failedMap, ignoreMap, schemeKey, entityKey);
            ITree treeNode = new ITree((INode)node);
            treeNode.setIcons(new String[]{this.getNodeIcon(node)});
            treeNode.setChildren(this.getMChildren(entityKey, entityTable, successWithExplainMap, failedMap, ignoreMap, orgToScheme, schemeInfoMap));
            treeNode.setLeaf(CollectionUtils.isEmpty(treeNode.getChildren()));
            children.add(treeNode);
        }
        return treeList;
    }

    private List<ITree<MOrgTreeNode>> getMChildren(String parentId, IEntityTable entityTable, Map<String, List<String>> successWithExplainMap, Map<String, Map<String, FailedOrgInfo>> failedMap, Map<String, List<String>> ignoreMap, Map<String, String> orgToScheme, Map<String, MultiplSchemeInfo> schemeInfoMap) {
        ArrayList<ITree<MOrgTreeNode>> childList = new ArrayList<ITree<MOrgTreeNode>>();
        List childRows = entityTable.getChildRows(parentId);
        for (IEntityRow childRow : childRows) {
            String entityKey = childRow.getEntityKeyData();
            String schemeKey = orgToScheme.get(entityKey);
            MultiplSchemeInfo multiplSchemeInfo = schemeInfoMap.get(schemeKey);
            multiplSchemeInfo.addOrg(entityKey);
            List<ITree<MOrgTreeNode>> children = this.getMChildren(entityKey, entityTable, successWithExplainMap, failedMap, ignoreMap, orgToScheme, schemeInfoMap);
            MOrgTreeNode node = new MOrgTreeNode(childRow, successWithExplainMap, failedMap, ignoreMap, schemeKey, entityKey);
            ITree treeNode = new ITree((INode)node);
            treeNode.setIcons(new String[]{this.getNodeIcon(node)});
            if (CollectionUtils.isEmpty(children)) {
                treeNode.setLeaf(true);
            } else {
                treeNode.setChildren(children);
            }
            childList.add((ITree<MOrgTreeNode>)treeNode);
        }
        return childList;
    }

    @NotNull
    private static OrgTreeNode getOrgTreeNode(MCLabel label, Map<String, List<String>> ignoreMap) {
        OrgTreeNode tree = new OrgTreeNode();
        tree.setKey(label.getCode());
        tree.setCode(label.getCode());
        tree.setTitle(label.getOrgCode() + " | " + label.getTitle());
        tree.setIgnore(ignoreMap.get(label.getCode()));
        return tree;
    }

    @Override
    public ResultDetailVO buildLastResult(String task, String period, String org, String scheme) throws Exception {
        List<String> records = this.getRecords(task, period, org, scheme);
        if (CollectionUtils.isEmpty(records)) {
            ResultDetailVO res = new ResultDetailVO();
            res.setLastMsg("\u5f53\u524d\u4efb\u52a1\u65f6\u671f\u4e0b\u6ca1\u6709\u6267\u884c\u8fc7\u7efc\u5408\u5ba1\u6838\uff01");
            return res;
        }
        String runId = records.get(0);
        return this.buildResult(runId, task);
    }

    @Override
    public boolean hasLastResult(String task, String period, String org, String scheme) {
        return !CollectionUtils.isEmpty(this.getRecords(task, period, org, scheme));
    }

    private List<String> getRecords(String task, String period, String org, String scheme) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String recordTable = this.getTableName("NR_MCR_RECORD_", taskDefine.getKey(), dataScheme);
        List<String> records = null;
        records = StringUtils.hasText(scheme) ? this.fRecord.getRecordByTaskPeriodScheme(recordTable, task, period, scheme, NpContextHolder.getContext().getUserName(), CheckSource.APP_SCHEME) : this.fRecord.getRecordByTaskPeriodScheme(recordTable, task, period, org, NpContextHolder.getContext().getUserName(), CheckSource.APP_FLOW);
        return records;
    }

    private String getMasterTableName(String tablePrefix, DataScheme dataScheme) {
        return tablePrefix + dataScheme.getBizCode();
    }

    @Override
    public void cleanAllRecords(DataScheme dataScheme) {
        this.fUtil.cleanAllRecords(this.getMasterTableName("NR_MCR_RECORD_", dataScheme));
        this.fUtil.cleanAllRecords(this.getMasterTableName("NR_MCR_SCHEME_", dataScheme));
        this.fUtil.cleanAllRecords(this.getMasterTableName("NR_MCR_ITEM_", dataScheme));
        this.fUtil.cleanAllRecords(this.getMasterTableName("NR_MCR_ITEMORG_", dataScheme));
        this.fUtil.cleanAllRecords(this.getMasterTableName("NR_MCR_ERRORINFO_", dataScheme));
    }
}

