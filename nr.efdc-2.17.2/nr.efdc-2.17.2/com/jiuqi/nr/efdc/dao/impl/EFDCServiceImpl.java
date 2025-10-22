/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.context.AsyncTaskContextOrg
 *  com.jiuqi.np.asynctask.context.AsyncTaskContextUser
 *  com.jiuqi.np.asynctask.util.AsyncTaskConsts
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextOrganization
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.nr.data.logic.facade.extend.param.AutoCalFormFmlParam
 *  com.jiuqi.nr.data.logic.facade.extend.param.BaseFmlFactoryParam
 *  com.jiuqi.nr.data.logic.facade.param.input.CalPar
 *  com.jiuqi.nr.data.logic.facade.service.ICalculateService
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo
 *  com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData
 *  com.jiuqi.nr.dataentry.paramInfo.NoAccessFormInfo
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider
 *  com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams
 *  com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager
 *  com.jiuqi.nr.dataentry.service.IBatchCalculateService
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.dataentry.util.Consts$FormAccessLevel
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.EntityReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.efdc.dao.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.context.AsyncTaskContextOrg;
import com.jiuqi.np.asynctask.context.AsyncTaskContextUser;
import com.jiuqi.np.asynctask.util.AsyncTaskConsts;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.nr.data.logic.facade.extend.param.AutoCalFormFmlParam;
import com.jiuqi.nr.data.logic.facade.extend.param.BaseFmlFactoryParam;
import com.jiuqi.nr.data.logic.facade.param.input.CalPar;
import com.jiuqi.nr.data.logic.facade.service.ICalculateService;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataentry.paramInfo.NoAccessFormInfo;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.efdc.bean.FetchEfdcParam;
import com.jiuqi.nr.efdc.extract.ExtractDataRegion;
import com.jiuqi.nr.efdc.extract.IExtractDataUpdator;
import com.jiuqi.nr.efdc.extract.IExtractRequest;
import com.jiuqi.nr.efdc.extract.exception.ExtractException;
import com.jiuqi.nr.efdc.extract.impl.EFDCExtractRequestFactory;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import com.jiuqi.nr.efdc.internal.service.IEFDCEntityUpgrader;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.nr.efdc.pojo.EfdcReturnInfo;
import com.jiuqi.nr.efdc.service.IEFDCConfigService;
import com.jiuqi.nr.efdc.service.IEFDCService;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.CollectionUtils;

public class EFDCServiceImpl
implements IEFDCService {
    @Resource
    IRunTimeViewController runTimeViewController;
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    IFormulaRunTimeController formulaRunTimeController;
    @Resource
    IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    IJtableParamService jtableParamService;
    @Resource
    IJtableEntityService jtableEntityService;
    @Resource
    IDataAccessProvider dataAccessProvider;
    @Resource
    IEFDCConfigService EFDCConfigServiceImpl;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Resource
    IDataEntryParamService dataEntryParamService;
    @Resource
    private IBatchCalculateService batchCalculateService;
    @Autowired
    private ReadWriteAccessProvider accessProvider;
    @Autowired(required=false)
    private IExtractDataUpdator dataUpdator;
    @Autowired
    private IEFDCEntityUpgrader iefdcEntityUpgrader;
    @Autowired
    private IEntityMetaService entityMetaService;
    private static final Logger logger = LoggerFactory.getLogger(EFDCServiceImpl.class);
    public static final String ORG_TYPE = "ORGTYPE_CODE";
    @Autowired
    private ICalculateService calculateService;
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 5;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;

    @Override
    public Map<String, EfdcReturnInfo> efdcService(EfdcInfo efdcInfo, AsyncTaskMonitor monitor, Map<String, String> params) {
        int i;
        boolean JQReportModel;
        HashMap<String, EfdcReturnInfo> efdcReturnMap = new HashMap<String, EfdcReturnInfo>();
        EfdcReturnInfo efdcReturn = new EfdcReturnInfo();
        String detail = "";
        HashMap<String, String> formMessage = new HashMap<String, String>();
        try {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(efdcInfo.getTaskKey());
            JQReportModel = taskDefine.getFormulaSyntaxStyle() == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION;
        }
        catch (Exception e1) {
            efdcReturn.setMessage("\u6ca1\u6709\u627e\u5230\u5f53\u524d\u4efb\u52a1");
            efdcReturn.setStatus(2);
            efdcReturnMap.put("2", efdcReturn);
            return efdcReturnMap;
        }
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, efdcInfo.getFormSchemeKey());
        EFDCExtractRequestFactory efdcxtractResultFactory = new EFDCExtractRequestFactory();
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runTimeViewController.getFormScheme(efdcInfo.getFormSchemeKey());
        }
        catch (Exception e) {
            efdcReturn.setMessage("\u672a\u627e\u5230" + efdcInfo.getFormSchemeKey() + "\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848");
            efdcReturn.setStatus(2);
            efdcReturnMap.put("2", efdcReturn);
            return efdcReturnMap;
        }
        ArrayList<String> formkeyList = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)efdcInfo.getFormKey())) {
            String[] formKeys;
            for (String formKey : formKeys = efdcInfo.getFormKey().split(";")) {
                formkeyList.add(formKey);
            }
        }
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
        String masterEntity = dwEntity.getDimensionName();
        String entityID = dwEntity.getKey();
        List dimEntityList = this.jtableParamService.getDimEntityList(formScheme.getKey());
        JtableContext context = new JtableContext();
        context.setDimensionSet(efdcInfo.getDimensionSet());
        context.setFormSchemeKey(efdcInfo.getFormSchemeKey());
        context.setTaskKey(efdcInfo.getTaskKey());
        context.setVariableMap(efdcInfo.getVariableMap());
        EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
        String dimensionValue = efdcInfo.getDimensionSet().get(masterEntity).getValue();
        entityQueryByKeyInfo.setEntityKey(dimensionValue);
        entityQueryByKeyInfo.setEntityViewKey(entityID);
        entityQueryByKeyInfo.setContext(context);
        EntityByKeyReturnInfo queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
        efdcReturn.setEntity(queryEntityDataByKey.getEntity());
        List<String> formKeys = new ArrayList();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)context);
        detail = queryEntityDataByKey.getEntity().getTitle() + "\u83b7\u53d6\u6570\u636e\u3002";
        this.process(0.05, monitor, detail);
        FetchEfdcParam efdcParam = this.getEfdcParam(formScheme, dimensionValueSet, efdcInfo);
        detail = queryEntityDataByKey.getEntity().getTitle() + "\u83b7\u53d6\u53d6\u6570\u65b9\u6848\u3002";
        this.process(0.1, monitor, detail);
        FormulaSchemeDefine extractformulaScheme = this.EFDCConfigServiceImpl.getSoluctionByDimensions(efdcParam.getQueryObj(), efdcParam.getDimMap(), efdcParam.getEntityId());
        detail = queryEntityDataByKey.getEntity().getTitle() + "\u83b7\u53d6\u8fd0\u7b97\u516c\u5f0f\u3002";
        this.process(0.15, monitor, detail);
        List<FormulaSchemeDefine> formulaSchemeDefineList = this.EFDCConfigServiceImpl.getRPTFormulaScheme(efdcParam.getQueryObj(), efdcParam.getDimMap(), efdcParam.getEntityId());
        if (extractformulaScheme == null) {
            String otherDimensionValue = this.getOtherDimension(efdcInfo.getDimensionSet(), dimEntityList);
            formMessage.put("allForm;" + otherDimensionValue, "\u8d22\u52a1\u63d0\u53d6\u65b9\u6848\u672a\u914d\u7f6e");
            efdcReturn.setStatus(1);
            efdcReturn.setFormMessage(formMessage);
            efdcReturnMap.put(queryEntityDataByKey.getEntity().getId(), efdcReturn);
            return efdcReturnMap;
        }
        ReadWriteAccessCacheParams readWriteAccessCacheParams = new ReadWriteAccessCacheParams(context, formkeyList, Consts.FormAccessLevel.FORM_DATA_WRITE);
        ReadWriteAccessCacheManager readWriteAccessCacheManager = (ReadWriteAccessCacheManager)BeanUtil.getBean(ReadWriteAccessCacheManager.class);
        readWriteAccessCacheManager.initCache(readWriteAccessCacheParams);
        FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(context, Consts.FormAccessLevel.FORM_DATA_WRITE, formkeyList);
        FormReadWriteAccessData accessForms = this.accessProvider.getAccessForms(formReadWriteAccessData, readWriteAccessCacheManager);
        formKeys = accessForms.getFormKeys();
        if (formKeys.size() == 0 && efdcInfo.getCurForm()) {
            efdcReturn.setMessage(accessForms.getOneFormKeyReason((String)formkeyList.get(0)));
            formMessage.put("curForm", "curForm");
            efdcReturn.setFormMessage(formMessage);
            efdcReturn.setStatus(1);
            efdcReturnMap.put("1", efdcReturn);
            return efdcReturnMap;
        }
        List noAccessnFormKeys = accessForms.getNoAccessnFormKeys();
        for (String formKey : noAccessnFormKeys) {
            if (!formkeyList.contains(formKey) && formkeyList.size() != 0) continue;
            NoAccessFormInfo noAccessFormInfo = new NoAccessFormInfo(efdcInfo.getDimensionSet(), formKey, accessForms.getOneFormKeyReason(formKey));
            String otherDimensionValue = this.getOtherDimension(efdcInfo.getDimensionSet(), dimEntityList);
            logger.warn(String.format("%s,%s:%s", formKey, otherDimensionValue, noAccessFormInfo.getReason()));
            efdcReturn.setStatus(0);
        }
        int corePoolSize = 5;
        int maxPoolSize = 5;
        String systemSetting = this.iNvwaSystemOptionService.get("fext-settings-group", "EFDC_MAX_THREAD");
        if (StringUtils.isNotEmpty((String)systemSetting)) {
            int settingThread = Integer.parseInt(systemSetting);
            if (settingThread < corePoolSize) {
                corePoolSize = settingThread;
            }
            maxPoolSize = settingThread;
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, (long)KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100), new ThreadPoolExecutor.CallerRunsPolicy());
        ArrayList sliceFormKeys = new ArrayList();
        for (i = 0; i < maxPoolSize; ++i) {
            sliceFormKeys.add(new ArrayList());
        }
        for (i = 0; i < formKeys.size(); ++i) {
            ((List)sliceFormKeys.get(i % maxPoolSize)).add(formKeys.get(i));
        }
        Boolean[] cancelTask = new Boolean[]{false};
        String finalDetail = detail;
        List<String> finalFormKeys = formKeys;
        int[] executeIndex = new int[]{0};
        CountDownLatch countDownLatch = new CountDownLatch(formKeys.size());
        for (int i2 = 0; i2 < sliceFormKeys.size(); ++i2) {
            List executeFormKeys = (List)sliceFormKeys.get(i2);
            if (executeFormKeys.size() > 0) {
                executor.execute(() -> {
                    void var22_29;
                    NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
                    try {
                        npContext.setTenant((String)params.get("NR_CONTEXT_TENANT"));
                        String user = (String)params.get("NR_CONTEXT_USER");
                        npContext.setUser(user != null ? (ContextUser)JsonUtil.toObject((String)user, AsyncTaskContextUser.class) : null);
                        Iterator identity = (String)params.get("NR_CONTEXT_IDENTITY");
                        npContext.setIdentity(identity != null ? (ContextIdentity)JsonUtil.toObject((String)((Object)identity), NpContextIdentity.class) : null);
                        String string = (String)params.get("NR_CONTEXT_ORGANIZATION");
                        npContext.setOrganization(string != null ? (ContextOrganization)JsonUtil.toObject((String)string, AsyncTaskContextOrg.class) : null);
                        String locale = (String)params.get("NR_CONTEXT_LOCALE");
                        npContext.setLocale(locale != null ? (Locale)JsonUtil.toObject((String)((String)params.get("NR_CONTEXT_LOCALE")), Locale.class) : LocaleContextHolder.getLocale());
                        String date = (String)params.get("NR_CONTEXT_LOGINDATE");
                        npContext.setLoginDate(date != null ? (Date)JsonUtil.toObject((String)date, Date.class) : null);
                    }
                    catch (Exception e) {
                        logger.error("efdc\u53d6\u6570\u5b50\u7ebf\u7a0b\u4efb\u52a1\u6267\u884c\u65f6\u6784\u5efaNpContext\u51fa\u9519\uff01{}", (Object)e.getMessage());
                        throw new RuntimeException(e);
                    }
                    npContext.setIp((String)params.get("NR_CONTEXT_IP"));
                    HashMap<String, ContextExtension> extensionMap = new HashMap<String, ContextExtension>();
                    for (Map.Entry entry : params.entrySet()) {
                        String key = (String)entry.getKey();
                        if (!key.startsWith("NR_CONTEXT_EXTENSION_")) continue;
                        extensionMap.put(key.substring(AsyncTaskConsts.NR_CONTEXT_EXTENSION_PRE_LENGTH), (ContextExtension)SimpleParamConverter.SerializationUtils.deserialize((String)((String)entry.getValue())));
                    }
                    if (!extensionMap.isEmpty()) {
                        for (Map.Entry entry : extensionMap.entrySet()) {
                            ContextExtension contextExtension = npContext.getExtension((String)entry.getKey());
                            Consumer<Map.Entry> consumer = item -> {
                                Serializable seriaValue = (Serializable)item.getValue();
                                contextExtension.put((String)item.getKey(), seriaValue);
                            };
                            ((ContextExtension)entry.getValue()).apply(consumer);
                        }
                    }
                    NpContextHolder.setContext((NpContext)npContext);
                    ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                    executorContext.setJQReportModel(JQReportModel);
                    executorContext.setEnv((IFmlExecEnvironment)environment);
                    boolean bl = false;
                    while (var22_29 < executeFormKeys.size() && !cancelTask[0].booleanValue()) {
                        executeIndex[0] = executeIndex[0] + 1;
                        double progress = (double)executeIndex[0] / (double)finalFormKeys.size() * 0.8 + 0.15;
                        FormulaSchemeDefine formluaScheme = CollectionUtils.isEmpty(formulaSchemeDefineList) ? null : (FormulaSchemeDefine)formulaSchemeDefineList.stream().findFirst().get();
                        String warning = this.doEFDCrevice((String)executeFormKeys.get((int)var22_29), efdcInfo, executorContext, efdcxtractResultFactory, extractformulaScheme, formluaScheme, progress, monitor);
                        if (StringUtils.isNotEmpty((String)warning)) {
                            FormDefine formDefine = this.runTimeViewController.queryFormById((String)executeFormKeys.get((int)var22_29));
                            String otherDimensionValue = this.getOtherDimension(efdcInfo.getDimensionSet(), dimEntityList);
                            formMessage.put(formDefine.getKey() + ";" + otherDimensionValue, warning);
                        }
                        countDownLatch.countDown();
                        this.process(progress, monitor, finalDetail);
                        if (monitor.isCancel()) {
                            efdcReturn.setMessage("\u4efb\u52a1\u53d6\u6d88");
                            efdcReturn.setStatus(-1);
                            efdcReturnMap.put("-1", efdcReturn);
                            cancelTask[0] = true;
                            while (countDownLatch.getCount() != 0L) {
                                countDownLatch.countDown();
                            }
                            break;
                        }
                        ++var22_29;
                    }
                });
            }
            if (!cancelTask[0].booleanValue()) continue;
            executor.shutdownNow();
            return efdcReturnMap;
        }
        executor.shutdown();
        try {
            countDownLatch.await();
        }
        catch (InterruptedException e) {
            logger.error("Interrupted!", (Object)e.getMessage());
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        detail = queryEntityDataByKey.getEntity().getTitle() + "\u6b63\u5728\u8fd0\u7b97\u3002";
        this.process(0.95, monitor, detail);
        this.calcAllByCondition(monitor, efdcInfo, formKeys, formulaSchemeDefineList);
        detail = queryEntityDataByKey.getEntity().getTitle() + "\u53d6\u6570\u5b8c\u6210\u3002";
        this.process(1.0, monitor, detail);
        if (formMessage.size() > 0) {
            efdcReturn.setFormMessage(formMessage);
            efdcReturn.setStatus(1);
        }
        if (efdcReturn.getStatus() == 0) {
            return null;
        }
        efdcReturnMap.put(queryEntityDataByKey.getEntity().getId(), efdcReturn);
        return efdcReturnMap;
    }

    private void calcAll(AsyncTaskMonitor monitor, EfdcInfo efdcInfo, List<String> forms, List<FormulaSchemeDefine> formulaSchemeList) {
        if (formulaSchemeList == null || formulaSchemeList.size() < 1) {
            logger.error("\u6ca1\u6709\u516c\u5f0f\u65b9\u6848\uff0c\u65e0\u6cd5\u8fd0\u7b97\uff01");
            return;
        }
        BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
        batchCalculateInfo.setTaskKey(efdcInfo.getTaskKey());
        batchCalculateInfo.setDimensionSet(efdcInfo.getDimensionSet());
        batchCalculateInfo.setVariableMap(efdcInfo.getVariableMap());
        HashMap<String, Object> formulas = new HashMap<String, Object>();
        for (String formKey : forms) {
            formulas.put(formKey, null);
        }
        batchCalculateInfo.setFormulas(formulas);
        StringBuilder formulaSchemeKeys = new StringBuilder();
        for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeList) {
            formulaSchemeKeys.append(formulaSchemeDefine.getKey());
            formulaSchemeKeys.append(";");
        }
        batchCalculateInfo.setFormulaSchemeKey(formulaSchemeKeys.toString().substring(0, formulaSchemeKeys.lastIndexOf(";")));
        batchCalculateInfo.setFormSchemeKey(efdcInfo.getFormSchemeKey());
        this.batchCalculateService.batchCalculateForm(batchCalculateInfo);
    }

    private void calcAllByCondition(AsyncTaskMonitor monitor, EfdcInfo efdcInfo, List<String> forms, List<FormulaSchemeDefine> formulaSchemeList) {
        if (formulaSchemeList == null || formulaSchemeList.size() < 1) {
            logger.error("\u6ca1\u6709\u516c\u5f0f\u65b9\u6848\uff0c\u65e0\u6cd5\u8fd0\u7b97\uff01");
            return;
        }
        CalPar calPar = new CalPar();
        calPar.setFormSchemeKey(efdcInfo.getFormSchemeKey());
        AutoCalFormFmlParam autoCalFormFmlParam = new AutoCalFormFmlParam();
        autoCalFormFmlParam.setDimensionCombination(new DimensionCombinationBuilder(DimensionValueSetUtil.getDimensionValueSet(efdcInfo.getDimensionSet())).getCombination());
        calPar.setBaseFmlFactoryParam((BaseFmlFactoryParam)autoCalFormFmlParam);
        for (FormulaSchemeDefine formulaScheme : formulaSchemeList) {
            autoCalFormFmlParam.setFormulaSchemeKey(formulaScheme.getKey());
            for (String formKey : forms) {
                autoCalFormFmlParam.setFormKey(formKey);
                this.calculateService.calculate(calPar);
            }
        }
    }

    private FetchEfdcParam getEfdcParam(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, EfdcInfo efdcInfo) {
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
        String targetKey = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        EntityViewData datatimeEntity = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
        String periodCode = dimensionValueSet.getValue(datatimeEntity.getDimensionName()).toString();
        List dimEntityList = this.jtableParamService.getDimEntityList(formScheme.getKey());
        HashMap<String, String> dimMap = new HashMap<String, String>();
        for (EntityViewData entityInfo : dimEntityList) {
            dimMap.putAll(this.iefdcEntityUpgrader.getDimMap(entityInfo, dimensionValueSet, efdcInfo));
        }
        dimMap.put(datatimeEntity.getDimensionName(), periodCode);
        HashMap<String, Object> paras = new HashMap<String, Object>();
        paras.put("DATATIME", periodCode);
        EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
        entityQueryByKeyInfo.setEntityKey(targetKey);
        entityQueryByKeyInfo.setEntityViewKey(formScheme.getDw());
        JtableContext context = new JtableContext();
        context.setDimensionSet(efdcInfo.getDimensionSet());
        context.setFormSchemeKey(efdcInfo.getFormSchemeKey());
        context.setTaskKey(efdcInfo.getTaskKey());
        context.setVariableMap(efdcInfo.getVariableMap());
        entityQueryByKeyInfo.setContext(context);
        EntityByKeyReturnInfo entityByKeyReturnInfo = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
        if (null != entityByKeyReturnInfo && null != entityByKeyReturnInfo.getEntity()) {
            String unitCode = entityByKeyReturnInfo.getEntity().getId();
            paras.put("UnitCode", unitCode);
        } else {
            EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
            entityQueryByKeyInfo.setEntityViewKey(dwEntity.getKey());
            EntityReturnInfo entityReturnInfo = this.jtableEntityService.queryEntityData(entityQueryInfo);
            if (!entityReturnInfo.getEntitys().isEmpty()) {
                String unitCode = ((EntityData)entityReturnInfo.getEntitys().get(0)).getId();
                paras.put("UnitCode", unitCode);
            }
        }
        efdcInfo.setParas(paras);
        if (StringUtils.isEmpty((String)targetKey)) {
            return null;
        }
        String formSchemeKey = efdcInfo.getFormSchemeKey();
        String taskKey = efdcInfo.getTaskKey();
        QueryObjectImpl queryObjectImpl = new QueryObjectImpl(taskKey, formSchemeKey, targetKey);
        FetchEfdcParam result = new FetchEfdcParam();
        result.setDimMap(dimMap);
        result.setEntityId(dwEntity.getKey());
        result.setQueryObj(queryObjectImpl);
        return result;
    }

    private String doEFDCrevice(String formKey, EfdcInfo efdcInfo, ExecutorContext executorContext, EFDCExtractRequestFactory efdcxtractResultFactory, FormulaSchemeDefine extractformulaScheme, FormulaSchemeDefine formulaScheme, double progress, AsyncTaskMonitor monitor) {
        String warning = "";
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runTimeViewController.getFormScheme(efdcInfo.getFormSchemeKey());
        }
        catch (Exception e) {
            warning = "\u672a\u627e\u5230" + efdcInfo.getFormSchemeKey() + "\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848";
        }
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
        EntityViewData datatimeEntity = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(efdcInfo.getDimensionSet());
        jtableContext.setFormSchemeKey(efdcInfo.getFormSchemeKey());
        jtableContext.setFormulaSchemeKey(extractformulaScheme.getKey());
        jtableContext.setFormKey(formKey);
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)jtableContext);
        String tableName = dwEntity.getTableName();
        String unitKey = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        String unitBblx = "";
        if (tableName != null) {
            tableName = tableName.toUpperCase();
        }
        IEntityAttribute bblxField = null;
        try {
            IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(dwEntity.getKey());
            bblxField = dwEntityModel.getBblxField();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (bblxField != null) {
            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            entityQueryByKeyInfo.setContext(jtableContext);
            entityQueryByKeyInfo.setEntityKey(unitKey);
            entityQueryByKeyInfo.setEntityViewKey(dwEntity.getKey());
            entityQueryByKeyInfo.getCaptionFields().add(bblxField.getCode());
            EntityByKeyReturnInfo queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
            int bblxIndex = queryEntityDataByKey.getCells().indexOf(bblxField.getCode());
            EntityData entity = queryEntityDataByKey.getEntity();
            if (entity != null && bblxIndex >= 0) {
                unitBblx = (String)entity.getData().get(bblxIndex);
            }
        }
        HashMap<String, String> dimMap = new HashMap<String, String>();
        for (Map.Entry<String, DimensionValue> entry : efdcInfo.getDimensionSet().entrySet()) {
            if (dwEntity.getDimensionName().equals(entry.getKey()) || datatimeEntity.getDimensionName().equals(entry.getKey())) continue;
            dimMap.put(entry.getKey(), entry.getValue().getValue());
        }
        dimMap.put(ORG_TYPE, tableName);
        String address = this.iNvwaSystemOptionService.get("fext-settings-group", "EFDCURL");
        HashMap<String, Object> paras = new HashMap<String, Object>();
        paras.put("EFDCAddress", address);
        paras.put("UnitCode", efdcInfo.getParas().get("UnitCode"));
        paras.put("DATATIME", efdcInfo.getParas().get("DATATIME"));
        paras.put("includUncharged", efdcInfo.isContainsUnbVou());
        paras.put("includeAdjustPeriod", efdcInfo.isAccount());
        paras.put("bblx", unitBblx);
        paras.put("startAdjustPeriod", efdcInfo.getCommencementPeriod());
        paras.put("endAdjustPeriod", efdcInfo.getClosingPeriod());
        paras.put("taskID", efdcInfo.getTaskKey());
        if (!StringUtils.isEmpty((String)efdcInfo.getStartPay()) && !StringUtils.isEmpty((String)efdcInfo.getEndPay())) {
            paras.put("startPay", efdcInfo.getStartPay());
            paras.put("endPay", efdcInfo.getEndPay());
        }
        if (formulaScheme != null) {
            paras.put("calSchemeKey", formulaScheme.getKey());
        }
        if (!dimMap.isEmpty()) {
            paras.put("otherEntity", dimMap);
        }
        FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
        IExtractRequest extractRequest = efdcxtractResultFactory.createReqeust(extractformulaScheme, formDefine);
        try {
            String detail = formDefine.getTitle() + "\u51c6\u5907\u53d6\u6570";
            this.process(progress, monitor, detail);
            List<ExtractDataRegion> region = extractRequest.doPrepare(executorContext, paras, this.formulaRunTimeController);
            this.checkRegion(region);
            detail = formDefine.getTitle() + "\u6b63\u5728\u53d6\u6570";
            this.process(progress, monitor, detail);
            extractRequest.doExtract(executorContext, this.dataAccessProvider, dimensionValueSet, paras, this.dataUpdator);
            detail = formDefine.getTitle() + "\u53d6\u6570\u5b8c\u6210";
            this.process(progress, monitor, detail);
        }
        catch (ExtractException e) {
            String msg = e.getMessage();
            msg = e.getMessage().isEmpty() ? formDefine.getTitle() + ":" + e.getCause() : formDefine.getTitle() + ":" + e.getMessage();
            msg = "EFDC\u6570\u636e\u63d0\u53d6\u6267\u884c\u51fa\u9519\u3010\u5355\u4f4d" + efdcInfo.getParas().get("UnitCode") + ",\u65f6\u671f" + efdcInfo.getParas().get("DATATIME") + "\u3011" + msg;
            warning = warning + msg;
        }
        catch (Exception e) {
            String msg = e.getMessage();
            msg = e.getMessage().isEmpty() ? formDefine.getTitle() + ":" + e.getCause() : formDefine.getTitle() + ":" + e.getMessage();
            warning = warning + msg;
        }
        return warning;
    }

    private void checkRegion(List<ExtractDataRegion> region) throws ExtractException {
        Optional<ExtractDataRegion> errorREgion = region.stream().filter(e -> e.isFloat() && e.getColmumCount() == 0).findAny();
        if (errorREgion.isPresent()) {
            throw new ExtractException("\u6d6e\u52a8\u884c\u4e0e\u6d6e\u52a8\u5217\u516c\u5f0f\u7684\u884c\u5750\u6807\u5e94\u8be5\u4fdd\u6301\u4e00\u81f4\uff0c\u76ee\u524d\u516c\u5f0f\u8bbe\u7f6e\u9519\u8bef\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
        }
    }

    public void process(double progress, AsyncTaskMonitor monitor, String detail) {
        monitor.progressAndMessage(progress, detail);
    }

    public String getOtherDimension(Map<String, DimensionValue> dimensionSet, List<EntityViewData> entityList) {
        String otherDimensionValue = "";
        for (EntityViewData entity : entityList) {
            String dimensionValue = dimensionSet.get(entity.getDimensionName()).getValue();
            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            entityQueryByKeyInfo.setEntityKey(dimensionValue);
            entityQueryByKeyInfo.setEntityViewKey(entity.getKey());
            EntityByKeyReturnInfo queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
            otherDimensionValue = queryEntityDataByKey.getEntity().getTitle();
        }
        return otherDimensionValue;
    }

    @Override
    public FormulaSchemeDefine getEfdcFormula(JtableContext jtableContext) {
        String formSchemeKey = jtableContext.getFormSchemeKey();
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        }
        catch (Exception exception) {
            // empty catch block
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)jtableContext);
        EfdcInfo efdcInfo = new EfdcInfo();
        efdcInfo.setDimensionSet(jtableContext.getDimensionSet());
        efdcInfo.setTaskKey(jtableContext.getTaskKey());
        efdcInfo.setFormSchemeKey(formSchemeKey);
        FetchEfdcParam efdcParam = this.getEfdcParam(formScheme, dimensionValueSet, efdcInfo);
        FormulaSchemeDefine extractformulaScheme = this.EFDCConfigServiceImpl.getSoluctionByDimensions(efdcParam.getQueryObj(), efdcParam.getDimMap(), efdcParam.getEntityId());
        return extractformulaScheme;
    }
}

