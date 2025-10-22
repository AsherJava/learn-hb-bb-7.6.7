/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil
 *  com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.asynctask.BatchCheckAsyncTaskExecutor
 *  com.jiuqi.nr.dataentry.bean.FormulaAuditType
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo
 *  com.jiuqi.nr.dataentry.service.IBatchCheckResultService
 *  com.jiuqi.nr.dataentry.service.IBatchCheckService
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckResultInfo
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.finalaccountsaudit.explainlencheck.service;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.asynctask.BatchCheckAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.bean.FormulaAuditType;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;
import com.jiuqi.nr.dataentry.service.IBatchCheckResultService;
import com.jiuqi.nr.dataentry.service.IBatchCheckService;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.ExplainInfoCheckParam;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.ExplainInfoCheckResultCache;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.ExplainInfoCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.ExplainInfoCheckReturnInfo;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.QueryExplainCheckResultInfo;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.FormulaCheckResultInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplainInfoCheck {
    private static final Logger logger = LoggerFactory.getLogger(ExplainInfoCheck.class);
    private static final int PAGER_COUNT = 20;
    private static final int CHECK_CHAR_NUM = 10;
    private static final int CHECK_MAX_NUM = 172;
    private JtableContext _context = null;
    private FormSchemeDefine _formScheme = null;
    private Map<String, String> entityMap = null;
    private AsyncTaskMonitor monitor;
    private String mainDimName;
    private int totalUnitCount;
    private List<String> unitKeys;
    IJtableParamService jtableParamService;
    DataDefinitionRuntimeController2 dataDefinitionRuntimeController;
    CommonUtil commonUtil;
    DimensionUtil dimensionUtil;
    IDataAccessProvider dataAccessProvider;
    IRunTimeViewController runtimeView;
    IEntityViewRunTimeController entityViewRunTimeController;
    IFormulaRunTimeController runTimeFormulaController;
    private IBatchCheckService batchCheckService;
    private INvwaSystemOptionService iNvwaSystemOptionService;
    private AuditTypeDefineService auditTypeDefineService;
    IDataentryFlowService dataentryFlowService;
    EntityQueryHelper entityQueryHelper;
    DataModelService dataModelService;
    DesignDataModelService designDataModelService;
    ExplainInfoCheckResultCache cache = null;
    IBatchCheckResultService batchCheckResultService;
    AsyncThreadExecutor asyncThreadExecutor;
    private ArrayList<Object> checkTypeList;
    private ArrayList<Object> upLoadCheckTypeList;

    private void getParam() {
        this.jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        this.dataDefinitionRuntimeController = (DataDefinitionRuntimeController2)BeanUtil.getBean(DataDefinitionRuntimeController2.class);
        this.commonUtil = (CommonUtil)BeanUtil.getBean(CommonUtil.class);
        this.dimensionUtil = (DimensionUtil)BeanUtil.getBean(DimensionUtil.class);
        this.dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
        this.runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        this.entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        this.runTimeFormulaController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
        this.batchCheckService = (IBatchCheckService)BeanUtil.getBean(IBatchCheckService.class);
        this.iNvwaSystemOptionService = (INvwaSystemOptionService)BeanUtil.getBean(INvwaSystemOptionService.class);
        this.auditTypeDefineService = (AuditTypeDefineService)BeanUtil.getBean(AuditTypeDefineService.class);
        this.dataentryFlowService = (IDataentryFlowService)BeanUtil.getBean(IDataentryFlowService.class);
        this.entityQueryHelper = (EntityQueryHelper)BeanUtil.getBean(EntityQueryHelper.class);
        this.cache = (ExplainInfoCheckResultCache)BeanUtil.getBean(ExplainInfoCheckResultCache.class);
        this.dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        this.batchCheckResultService = (IBatchCheckResultService)BeanUtil.getBean(IBatchCheckResultService.class);
        this.asyncThreadExecutor = (AsyncThreadExecutor)BeanUtil.getBean(AsyncThreadExecutor.class);
    }

    public ExplainInfoCheckReturnInfo checkExplainInfo(ExplainInfoCheckParam param, AsyncTaskMonitor asyncTaskMonitor, JobContext jobContext) throws Exception {
        String cacheKey;
        ArrayList<ExplainInfoCheckResultItem> resultItems;
        this.monitor = asyncTaskMonitor;
        this._context = param.getContext();
        if (this.monitor != null) {
            this.monitor.progressAndMessage(0.1, "lengthCheckRunning");
        }
        this.getParam();
        this.mainDimName = this.dimensionUtil.getDwMainDimName(param.getContext().getFormSchemeKey());
        String batchId = this.batchCheck(param, asyncTaskMonitor, jobContext);
        if (batchId.isEmpty()) {
            logger.error("\u6267\u884c\u51fa\u9519\u8bf4\u660e\u68c0\u67e5\u524d\u7684\u516c\u5f0f\u5ba1\u6838\u65f6\u5f02\u5e38\u3002");
            return null;
        }
        ExplainInfoCheckReturnInfo returnInfo = new ExplainInfoCheckReturnInfo();
        LinkedHashMap<String, ExplainInfoCheckResultItem> resultMap = new LinkedHashMap<String, ExplainInfoCheckResultItem>();
        if (this.unitKeys == null) {
            this.unitKeys = new ArrayList<String>();
        }
        this.unitKeys.clear();
        this.InitUnitKeys(param);
        JtableContext jtableContext = param.getContext();
        try {
            this._formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return returnInfo;
        }
        int minLen = this.getCheckCharNum();
        int maxLen = this.getCheckCharMaxNum();
        boolean checkHasChinese = this.getCheckHasChinese();
        this.getCheckTypesAndUploadCheckTypes(param);
        String info = String.format("\u68c0\u67e5\u957f\u5ea6%d-%d\u4e4b\u95f4", minLen, maxLen);
        if (checkHasChinese) {
            info = info + ",\u5e76\u4e14\u5305\u542b\u6c49\u5b57";
        }
        logger.info(info);
        BatchCheckInfo batchCheckInfo = this.getBatchCheckInfo(param);
        batchCheckInfo.setAsyncTaskKey(batchId);
        FormulaCheckReturnInfo formulaCheckReturnInfo = this.batchCheckResultService.batchCheckResult(batchCheckInfo);
        if (this.monitor != null) {
            this.monitor.progressAndMessage(0.4, "lengthCheckRunning");
        }
        logger.info(String.format("\u67e5\u8be2\u5230\u8bb0\u5f55%d", formulaCheckReturnInfo.getResults().size()));
        HashSet tmpUnitKeys = new HashSet();
        int resultSize = formulaCheckReturnInfo.getResults().size();
        if (resultSize != 0) {
            double stepValue = 0.5 / (double)resultSize;
            for (int i = 0; i < formulaCheckReturnInfo.getResults().size(); ++i) {
                String unitKey;
                if (this.monitor != null) {
                    this.monitor.progressAndMessage(0.4 + stepValue * (double)(i + 1), "lengthCheckRunning");
                }
                FormulaCheckResultInfo resultInfo = (FormulaCheckResultInfo)formulaCheckReturnInfo.getResults().get(i);
                if (param.getImpactReport()) {
                    // empty if block
                }
                if (!this.unitKeys.contains(unitKey = resultInfo.getUnitKey())) continue;
                String desc = resultInfo.getDescriptionInfo().getDescription();
                boolean hasAdded = false;
                ExplainInfoCheckResultItem item = null;
                if (desc == null || desc.length() < minLen || desc.length() > maxLen) {
                    item = this.getResultItem(resultInfo, minLen, maxLen);
                    resultMap.put(unitKey, item);
                    hasAdded = true;
                }
                if (hasAdded || !checkHasChinese) continue;
                if (item == null) {
                    item = this.getResultItem(resultInfo, minLen, maxLen);
                }
                if (this.isContainChinese(desc)) continue;
                resultMap.put(unitKey, item);
            }
        }
        if ((resultItems = new ArrayList<ExplainInfoCheckResultItem>(resultMap.values())).size() > 0) {
            final IEntityTable entityTable = this.getEntityTable(jtableContext);
            Collections.sort(resultItems, new Comparator<ExplainInfoCheckResultItem>(){

                @Override
                public int compare(ExplainInfoCheckResultItem o1, ExplainInfoCheckResultItem o2) {
                    if (o1 == null || o2 == null) {
                        return -1;
                    }
                    IEntityRow entityRowO1 = entityTable.findByEntityKey(o1.getUnitKey());
                    IEntityRow entityRowO2 = entityTable.findByEntityKey(o2.getUnitKey());
                    int level1 = 0;
                    int level2 = 0;
                    if (entityRowO1 != null) {
                        o1.setUnitCode(entityRowO1.getCode());
                        if (entityRowO1.getParentsEntityKeyDataPath() != null) {
                            level1 = entityRowO1.getParentsEntityKeyDataPath().length;
                        }
                    }
                    if (entityRowO2 != null) {
                        o2.setUnitCode(entityRowO2.getCode());
                        if (entityRowO2.getParentsEntityKeyDataPath() != null) {
                            level2 = entityRowO2.getParentsEntityKeyDataPath().length;
                        }
                    }
                    if (level1 == level2 && entityRowO1 != null && entityRowO2 != null && entityRowO1.getEntityOrder() != null && entityRowO2.getEntityOrder() != null) {
                        return entityRowO1.getEntityOrder().toString().compareTo(entityRowO2.getEntityOrder().toString());
                    }
                    return level1 - level2;
                }
            });
        }
        returnInfo.setTotalUnitCount(this.totalUnitCount);
        returnInfo.setErrUnitCount(resultItems.size());
        String str = String.format("\u5b57\u6570\u9650\u5236\u8303\u56f4\u662f\u5728%d-%d\u4e4b\u95f4", minLen, maxLen);
        if (checkHasChinese) {
            str = str + ",\u5e76\u4e14\u9700\u8981\u5305\u542b\u6c49\u5b57\u3002";
        }
        returnInfo.setCheckTip(str);
        String string = cacheKey = param.getMainAsynTaskID() == null || "".equals(param.getMainAsynTaskID()) ? this.monitor.getTaskId() : param.getMainAsynTaskID();
        if (!"".equals(param.getItemKey())) {
            cacheKey = cacheKey + param.getItemKey();
        }
        this.cache.addResult(cacheKey, resultItems);
        if (this.monitor != null) {
            this.monitor.progressAndMessage(0.99, "lengthCheckRunning");
        }
        return returnInfo;
    }

    private String getFormulaSchemeKey(ExplainInfoCheckParam param) {
        String curFormulaSchemeKey = "";
        curFormulaSchemeKey = param.getFormulaSchemeKey();
        return curFormulaSchemeKey;
    }

    private int getCheckCharNum() {
        try {
            String charNumOfErrorMsg = this.iNvwaSystemOptionService.get("nr-audit-group", "CHAR_NUMBER_OF_ERROR_MSG");
            if (!StringUtils.isEmpty((String)charNumOfErrorMsg)) {
                int charNum = Integer.parseInt(charNumOfErrorMsg);
                return charNum;
            }
            return 10;
        }
        catch (Exception e) {
            return 10;
        }
    }

    private int getCheckCharMaxNum() {
        try {
            String charMAXNumOfErrorMsg = this.iNvwaSystemOptionService.get("nr-audit-group", "MAX_NUMBER_OF_ERROR_MSG");
            if (!StringUtils.isEmpty((String)charMAXNumOfErrorMsg)) {
                int charNum = Integer.parseInt(charMAXNumOfErrorMsg);
                return charNum;
            }
            return 172;
        }
        catch (Exception e) {
            return 172;
        }
    }

    private boolean getCheckHasChinese() {
        try {
            String charMsg = this.iNvwaSystemOptionService.get("nr-audit-group", "ERROR_MSG_CONTAIN_CHINESE_CHAR");
            if (!StringUtils.isEmpty((String)charMsg)) {
                return charMsg.equals("1");
            }
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    private boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /*
     * WARNING - void declaration
     */
    private BatchCheckInfo getBatchCheckInfo(ExplainInfoCheckParam param) {
        BatchCheckInfo info = new BatchCheckInfo();
        JtableContext context = new JtableContext(param.getContext());
        if (param.getUnitKeys() == null || param.getUnitKeys().size() == 0) {
            if (context.getDimensionSet().get(this.mainDimName) != null) {
                ((DimensionValue)context.getDimensionSet().get(this.mainDimName)).setValue("");
            }
        } else if (context.getDimensionSet().get(this.mainDimName) != null) {
            ((DimensionValue)context.getDimensionSet().get(this.mainDimName)).setValue(String.join((CharSequence)";", param.getUnitKeys()));
        }
        info.setContext(context);
        if (param.getOnlyCurFormulaSheme() && StringUtils.isEmpty((String)param.getFormulaSchemeKey())) {
            String curFormulaSchemeKey = "";
            if ("".equals(param.getContext().getFormulaSchemeKey())) {
                List list = this.runTimeFormulaController.getAllRPTFormulaSchemeDefinesByFormScheme(param.getContext().getFormSchemeKey());
                for (FormulaSchemeDefine aDefine : list) {
                    if (!aDefine.isDefault()) continue;
                    curFormulaSchemeKey = aDefine.getKey();
                    break;
                }
            } else {
                curFormulaSchemeKey = param.getContext().getFormulaSchemeKey();
            }
            info.setFormulaSchemeKeys(curFormulaSchemeKey);
        } else if (StringUtils.isEmpty((String)param.getFormulaSchemeKey())) {
            void var5_8;
            List formulaSchemeDefines = this.runTimeFormulaController.getAllRPTFormulaSchemeDefinesByFormScheme(param.getContext().getFormSchemeKey());
            String string = "";
            Iterator iterator = formulaSchemeDefines.iterator();
            while (iterator.hasNext()) {
                FormulaSchemeDefine f = (FormulaSchemeDefine)iterator.next();
                if ("".equals(var5_8)) {
                    String string2 = f.getKey();
                    continue;
                }
                String string3 = (String)var5_8 + ";" + f.getKey();
            }
            info.setFormulaSchemeKeys((String)var5_8);
        } else {
            info.setFormulaSchemeKeys(param.getFormulaSchemeKey());
        }
        try {
            if (this.checkTypeList != null) {
                info.getCheckTypes().clear();
                for (Object e : this.checkTypeList) {
                    info.getCheckTypes().add((Integer)e);
                }
            } else {
                List auditTypes = this.auditTypeDefineService.queryAllAuditType();
                if (auditTypes == null || auditTypes.size() == 0) {
                    throw new Exception();
                }
                for (AuditType auditType : auditTypes) {
                    info.getCheckTypes().add(auditType.getCode());
                }
            }
        }
        catch (Exception e) {
            info.getCheckTypes().add(1);
            info.getCheckTypes().add(2);
            info.getCheckTypes().add(4);
        }
        return info;
    }

    public List<ExplainInfoCheckResultItem> QueryExplainInfoCheckResult(QueryExplainCheckResultInfo info) {
        if (StringUtils.isEmpty((String)info.getAsyncTaskId())) {
            return null;
        }
        if ("".equals(info.getAsyncTaskId())) {
            return null;
        }
        if (this.cache == null) {
            this.cache = (ExplainInfoCheckResultCache)BeanUtil.getBean(ExplainInfoCheckResultCache.class);
        }
        String cacheKey = info.getAsyncTaskId();
        if (!"".equals(info.getItemKey())) {
            cacheKey = cacheKey + info.getItemKey();
        }
        List<ExplainInfoCheckResultItem> resultItems = this.cache.getResultItemsByasynTaskID(cacheKey);
        if (info.getItemOffset() < 0) {
            return resultItems;
        }
        ArrayList<ExplainInfoCheckResultItem> result = new ArrayList();
        int fromIndex = info.getItemOffset() * 20;
        int toIndex = (info.getItemOffset() + 1) * 20;
        if (toIndex > resultItems.size()) {
            toIndex = resultItems.size();
        }
        result = resultItems.subList(fromIndex, toIndex);
        return result;
    }

    private ExplainInfoCheckResultItem getResultItem(FormulaCheckResultInfo resultInfo, int minLen, int maxLen) {
        FormulaSchemeDefine define;
        ExplainInfoCheckResultItem item = new ExplainInfoCheckResultItem();
        if (this.entityMap == null) {
            this.entityMap = new HashMap<String, String>();
        }
        String unitKey = resultInfo.getUnitKey();
        String formulaKey = resultInfo.getFormula().getKey();
        if (formulaKey.contains("_")) {
            int endIndex = formulaKey.indexOf(95);
            formulaKey = formulaKey.substring(0, endIndex);
        }
        if ((define = this.runTimeFormulaController.queryFormulaSchemeDefine(resultInfo.getFormula().getFormulaSchemeKey())) == null) {
            return null;
        }
        FormulaDefine formulaDefine = this.runTimeFormulaController.queryFormulaDefine(formulaKey);
        if (formulaDefine == null) {
            return null;
        }
        TaskDefine taskDefine = this.commonUtil.getTaskDefine(this._formScheme.getTaskKey());
        if (taskDefine == null) {
            return null;
        }
        String unitTitle = "";
        if (this.entityMap.containsKey(unitKey)) {
            unitTitle = this.entityMap.get(unitKey);
        } else {
            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            EntityViewData masterEntity = this.getMasterEntityview(this._context.getFormSchemeKey());
            if (masterEntity != null) {
                entityQueryByKeyInfo.setEntityViewKey(masterEntity.getKey());
                entityQueryByKeyInfo.setEntityKey(unitKey);
                if (this._context != null) {
                    entityQueryByKeyInfo.setContext(this._context);
                }
                IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
                EntityData entityData = jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo).getEntity();
                unitTitle = entityData.getTitle();
                this.entityMap.put(unitKey, entityData.getTitle());
            }
        }
        String period = "";
        item.setUnitKey(unitKey);
        item.setUnitTitle(unitTitle);
        String unitCode = unitKey;
        item.setUnitCode(unitCode);
        item.setPeriod(period);
        item.setTaskKey(this._formScheme.getTaskKey());
        item.setTaskTitle(taskDefine.getTitle());
        item.setFormSchemeKey(this._formScheme.getKey());
        item.setFormSchemeTitle(this._formScheme.getTitle());
        item.setFormulaSchemeKey(define.getKey());
        item.setFormulaSchemeTitle(define.getTitle());
        item.setFormulaCode(formulaDefine.getCode());
        item.setFormulaKey(formulaKey);
        item.setFormulaExp(formulaDefine.getExpression());
        item.setFormSchemeTitle(formulaDefine.getTitle());
        item.setDesc(resultInfo.getDescriptionInfo().getDescription());
        item.setMinLen(minLen);
        item.setMaxLen(maxLen);
        return item;
    }

    private EntityViewData getMasterEntityview(String formSchemeKey) {
        return this.jtableParamService.getDwEntity(formSchemeKey);
    }

    private String batchCheck(ExplainInfoCheckParam param, AsyncTaskMonitor asyncTaskMonitor, JobContext jobContext) {
        try {
            Double asyncProcess;
            JtableContext excutorJtableContext = new JtableContext(param.getContext());
            if (param.getUnitKeys() == null || param.getUnitKeys().size() == 0) {
                if (excutorJtableContext.getDimensionSet().get(this.mainDimName) != null) {
                    ((DimensionValue)excutorJtableContext.getDimensionSet().get(this.mainDimName)).setValue("");
                }
            } else if (excutorJtableContext.getDimensionSet().get(this.mainDimName) != null) {
                ((DimensionValue)excutorJtableContext.getDimensionSet().get(this.mainDimName)).setValue(String.join((CharSequence)";", param.getUnitKeys()));
            }
            BatchCheckInfo batchCheckInfo = new BatchCheckInfo();
            batchCheckInfo.setContext(excutorJtableContext);
            batchCheckInfo.setFormulaSchemeKeys(this.getFormulaSchemeKey(param));
            String subTaskTitle = AsynctaskPoolType.ASYNCTASK_BATCHCHECK.getName();
            BatchCheckAsyncTaskExecutor subJob = new BatchCheckAsyncTaskExecutor();
            Map params = subJob.getParams();
            params.put("NR_ARGS", SimpleParamConverter.SerializationUtils.serializeToString((Object)batchCheckInfo));
            subJob.setTitle(subTaskTitle);
            NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
            npRealTimeTaskInfo.setTaskKey(param.getContext().getTaskKey());
            npRealTimeTaskInfo.setFormSchemeKey(param.getContext().getFormSchemeKey());
            npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)batchCheckInfo));
            npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)subJob);
            String asynTaskID = this.asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
            do {
                Thread.sleep(2000L);
                asyncProcess = this.asyncThreadExecutor.queryProcess(asynTaskID);
                if (asyncProcess == null) {
                    asyncProcess = 0.0;
                }
                if (this.asyncThreadExecutor.queryTaskState(asynTaskID) == TaskState.FINISHED) {
                    return asynTaskID;
                }
                if (this.asyncThreadExecutor.queryTaskState(asynTaskID) == TaskState.CANCELED) {
                    return asynTaskID;
                }
                if (this.asyncThreadExecutor.queryTaskState(asynTaskID) == TaskState.OVERTIME) {
                    return asynTaskID;
                }
                if (this.asyncThreadExecutor.queryTaskState(asynTaskID) == TaskState.OUTOFQUEUE) {
                    return asynTaskID;
                }
                if (this.asyncThreadExecutor.queryTaskState(asynTaskID) != TaskState.ERROR) continue;
                if (this.asyncThreadExecutor.queryResult(asynTaskID).equals("check_warn_info")) {
                    return asynTaskID;
                }
                return "";
            } while (!(asyncProcess >= 1.0));
            return asynTaskID;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return "";
        }
    }

    private void InitUnitKeys(ExplainInfoCheckParam param) {
        JtableContext jtableContext = param.getContext();
        JtableContext excutorJtableContext = new JtableContext(jtableContext);
        if (param.getUnitKeys() == null || param.getUnitKeys().size() == 0) {
            if (excutorJtableContext.getDimensionSet().get(this.mainDimName) != null) {
                ((DimensionValue)excutorJtableContext.getDimensionSet().get(this.mainDimName)).setValue("");
            }
            this.totalUnitCount = -1;
        } else {
            if (excutorJtableContext.getDimensionSet().get(this.mainDimName) != null) {
                ((DimensionValue)excutorJtableContext.getDimensionSet().get(this.mainDimName)).setValue(String.join((CharSequence)";", param.getUnitKeys()));
            }
            this.totalUnitCount = param.getUnitKeys().size();
            this.unitKeys.addAll(param.getUnitKeys());
        }
        excutorJtableContext.setFormKey("");
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)excutorJtableContext);
        if (this.totalUnitCount == -1) {
            Object Value = dimensionValueSet.getValue(this.mainDimName);
            if (Value instanceof List) {
                this.unitKeys = (List)Value;
            } else {
                String units = dimensionValueSet.getValue(this.mainDimName).toString();
                this.unitKeys = Arrays.asList(units.split(","));
            }
            if (this.unitKeys != null) {
                this.totalUnitCount = this.unitKeys.size();
            }
        }
    }

    private void getCheckTypesAndUploadCheckTypes(ExplainInfoCheckParam param) {
        TaskFlowsDefine flowsSetting = this._formScheme.getFlowsSetting();
        if (this.checkTypeList == null) {
            this.checkTypeList = new ArrayList();
        }
        if (this.upLoadCheckTypeList == null) {
            this.upLoadCheckTypeList = new ArrayList();
        }
        this.checkTypeList.clear();
        this.upLoadCheckTypeList.clear();
        ArrayList<FormulaAuditType> defaultType = new ArrayList<FormulaAuditType>();
        try {
            List auditTypes = this.auditTypeDefineService.queryAllAuditType();
            if (auditTypes == null || auditTypes.size() == 0) {
                throw new Exception();
            }
            for (AuditType auditType : auditTypes) {
                FormulaAuditType formulaAuditType = new FormulaAuditType();
                formulaAuditType.setKey(auditType.getCode().intValue());
                formulaAuditType.setIcon(auditType.getIcon());
                formulaAuditType.setTitle(auditType.getTitle());
                defaultType.add(formulaAuditType);
            }
        }
        catch (Exception e) {
            FormulaAuditType hintType = new FormulaAuditType();
            hintType.setKey(1);
            hintType.setIcon("#icon-_Txiaoxitishi");
            hintType.setTitle("\u63d0\u793a\u578b");
            defaultType.add(hintType);
            FormulaAuditType warningType = new FormulaAuditType();
            warningType.setKey(2);
            warningType.setIcon("#icon-_Tjinggaotishi");
            warningType.setTitle("\u8b66\u544a\u578b");
            defaultType.add(warningType);
            FormulaAuditType errorType = new FormulaAuditType();
            errorType.setKey(4);
            errorType.setIcon("#icon-_Tcuowutishi");
            errorType.setTitle("\u9519\u8bef\u578b");
            defaultType.add(errorType);
            logger.error(e.getMessage(), e);
        }
        String erroStatus = flowsSetting.getErroStatus();
        String promptStatus = flowsSetting.getPromptStatus();
        for (FormulaAuditType formulaAuditType : defaultType) {
            if (formulaAuditType == null || promptStatus == null || !promptStatus.contains(formulaAuditType.getKey() + "")) continue;
            this.checkTypeList.add(formulaAuditType.getKey());
        }
    }

    private String getPeriodDimName(String formSchemeKey) {
        EntityViewData periodEntityView = this.jtableParamService.getDataTimeEntity(formSchemeKey);
        return periodEntityView.getDimensionName();
    }

    private IEntityTable getEntityTable(JtableContext jtableContext) throws Exception {
        EntityViewDefine entityView = this.entityQueryHelper.getDwEntityView(this._formScheme.getKey());
        String periodDimName = this.getPeriodDimName(this._formScheme.getKey());
        String period = ((DimensionValue)jtableContext.getDimensionSet().get(periodDimName)).getValue();
        IEntityTable entityTable = this.entityQueryHelper.buildEntityTable(entityView, period, this._formScheme.getKey(), false);
        return entityTable;
    }
}

