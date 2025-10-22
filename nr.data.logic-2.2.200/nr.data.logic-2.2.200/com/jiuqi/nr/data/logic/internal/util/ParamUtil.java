/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.dataservice.core.access.UnitPermission
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.data.logic.internal.util;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.logic.common.ExceptionEnum;
import com.jiuqi.nr.data.logic.facade.extend.ICalFmlDimFormExtService;
import com.jiuqi.nr.data.logic.facade.extend.ICalFmlProvider;
import com.jiuqi.nr.data.logic.facade.extend.ICheckFmlDimFormExtService;
import com.jiuqi.nr.data.logic.facade.extend.SpecificDimBuilder;
import com.jiuqi.nr.data.logic.facade.extend.param.FmlDimFormExtParam;
import com.jiuqi.nr.data.logic.facade.extend.param.GetCalFmlEnv;
import com.jiuqi.nr.data.logic.facade.param.base.BaseEnv;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.internal.obj.FmlDimForm;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.FormulaParseUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.FixedDimBuilder;
import com.jiuqi.nr.dataservice.core.access.UnitPermission;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class ParamUtil {
    private static final int USER_BATCH = 900;
    private static final Logger logger = LoggerFactory.getLogger(ParamUtil.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired(required=false)
    private ICheckFmlDimFormExtService checkFmlDimFormExtService;
    @Autowired(required=false)
    private ICalFmlDimFormExtService calFmlDimFormExtService;
    @Autowired
    private List<ICalFmlProvider> fmlProviders;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private FormulaParseUtil formulaParseUtil;

    public NpRealTimeTaskInfo getNpRealTimeTaskInfo(BaseEnv baseEnv, AbstractRealTimeJob realTimeJob) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        FormSchemeDefine formScheme = this.getFormSchemeByFormulaSchemeKey(baseEnv.getFormulaSchemeKey());
        if (formScheme == null) {
            throw new IllegalArgumentException(String.format("incorrect formulaSchemeKey %s!", baseEnv.getFormulaSchemeKey()));
        }
        npRealTimeTaskInfo.setTaskKey(formScheme.getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(formScheme.getKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)baseEnv));
        npRealTimeTaskInfo.setAbstractRealTimeJob(realTimeJob);
        return npRealTimeTaskInfo;
    }

    public FormSchemeDefine getFormSchemeByFormulaSchemeKey(String formulaSchemeKey) {
        assert (formulaSchemeKey != null) : "\u516c\u5f0f\u65b9\u6848key\u4e0d\u80fd\u4e3a\u7a7a";
        FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
        assert (formulaSchemeDefine != null) : "\u516c\u5f0f\u65b9\u6848\u672a\u627e\u5230";
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formulaSchemeDefine.getFormSchemeKey());
        assert (formScheme != null) : "\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230";
        return formScheme;
    }

    public FormSchemeDefine getFormSchemeByFormulaSchemeKeys(List<String> formulaSchemeKeys) {
        if (!CollectionUtils.isEmpty(formulaSchemeKeys)) {
            for (String formulaSchemeKey : formulaSchemeKeys) {
                FormSchemeDefine formScheme;
                FormulaSchemeDefine formulaSchemeDefine;
                if (!StringUtils.hasText(formulaSchemeKey) || (formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey)) == null || (formScheme = this.runTimeViewController.getFormScheme(formulaSchemeDefine.getFormSchemeKey())) == null) continue;
                return formScheme;
            }
        }
        throw new IllegalArgumentException("\u6839\u636e\u516c\u5f0f\u65b9\u6848" + formulaSchemeKeys + "\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848");
    }

    public List<String> getAllFormScheme() {
        return this.jdbcTemplate.queryForList("SELECT FC_CODE FROM NR_PARAM_FORMSCHEME", String.class);
    }

    public Map<String, String> getIdNickNameMap(List<String> userId) {
        ArrayList users = new ArrayList();
        int userCount = userId.size();
        int count = userCount % 900 == 0 ? userCount / 900 : userCount / 900 + 1;
        for (int index = 0; index < count; ++index) {
            int fromIndex = index * 900;
            int toIndex = Math.min(fromIndex + 900, userCount);
            List<String> tempIds = userId.subList(fromIndex, toIndex);
            List userDefines = this.userService.get(tempIds.toArray(new String[0]));
            users.addAll(userDefines);
        }
        return users.stream().collect(Collectors.toMap(User::getId, User::getNickname, (oldValue, newValue) -> oldValue));
    }

    public String getUserNickNameById(String userId) {
        User user = this.userService.get(userId);
        return user == null ? "\u7cfb\u7edf\u7ba1\u7406\u5458" : user.getNickname();
    }

    @Deprecated
    public ExecutorContext getExecutorContext(String formSchemeKey) {
        return this.formulaParseUtil.getExecutorContext(formSchemeKey);
    }

    public Map<Integer, String> getCheckTypeCodeTitleMap() {
        HashMap<Integer, String> codeTitleMap = new HashMap();
        try {
            codeTitleMap = this.auditTypeDefineService.queryAllAuditType().stream().collect(Collectors.toMap(AuditType::getCode, AuditType::getTitle, (oldValue, newValue) -> newValue));
        }
        catch (Exception e) {
            logger.error("\u5ba1\u6838\u7c7b\u578b\u67e5\u8be2\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
        return codeTitleMap;
    }

    public List<FmlDimForm> getFmlDimForms2(FormSchemeDefine formScheme, List<UnitPermission> accessForms, DataEngineConsts.FormulaType formulaType) {
        FmlDimFormExtParam fmlDimFormExtParam;
        List<FmlDimForm> fmlDimForms = new ArrayList<FmlDimForm>();
        String mainDimId = this.entityUtil.getContextMainDimId(formScheme.getDw());
        String formSchemeKey = formScheme.getKey();
        String taskKey = formScheme.getTaskKey();
        for (UnitPermission accessForm : accessForms) {
            Map dimensions = accessForm.getMasterKey();
            List formKeys = accessForm.getResourceIds();
            FixedDimBuilder fixedDimBuilder = new FixedDimBuilder(mainDimId, dimensions);
            DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensions, formSchemeKey, (SpecificDimBuilder)fixedDimBuilder);
            fmlDimForms.add(new FmlDimForm(dimensionCollection, formKeys));
        }
        if (DataEngineConsts.FormulaType.CHECK == formulaType) {
            if (this.checkFmlDimFormExtService != null) {
                fmlDimFormExtParam = new FmlDimFormExtParam(taskKey, formSchemeKey, fmlDimForms);
                fmlDimForms = this.checkFmlDimFormExtService.getFmlDimForm(fmlDimFormExtParam);
            }
        } else if (DataEngineConsts.FormulaType.CALCULATE == formulaType && this.calFmlDimFormExtService != null) {
            fmlDimFormExtParam = new FmlDimFormExtParam(taskKey, formSchemeKey, fmlDimForms);
            fmlDimForms = this.calFmlDimFormExtService.getFmlDimForm(fmlDimFormExtParam);
        }
        return fmlDimForms;
    }

    public List<FmlDimForm> getFmlDimForms(FormSchemeDefine formScheme, List<DimensionAccessFormInfo.AccessFormInfo> accessForms, DataEngineConsts.FormulaType formulaType) {
        FmlDimFormExtParam fmlDimFormExtParam;
        List<FmlDimForm> fmlDimForms = new ArrayList<FmlDimForm>();
        String mainDimId = this.entityUtil.getContextMainDimId(formScheme.getDw());
        String formSchemeKey = formScheme.getKey();
        String taskKey = formScheme.getTaskKey();
        for (DimensionAccessFormInfo.AccessFormInfo accessForm : accessForms) {
            Map dimensions = accessForm.getDimensions();
            List formKeys = accessForm.getFormKeys();
            FixedDimBuilder fixedDimBuilder = new FixedDimBuilder(mainDimId, dimensions);
            DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensions, formSchemeKey, (SpecificDimBuilder)fixedDimBuilder);
            fmlDimForms.add(new FmlDimForm(dimensionCollection, formKeys));
        }
        if (DataEngineConsts.FormulaType.CHECK == formulaType) {
            if (this.checkFmlDimFormExtService != null) {
                fmlDimFormExtParam = new FmlDimFormExtParam(taskKey, formSchemeKey, fmlDimForms);
                fmlDimForms = this.checkFmlDimFormExtService.getFmlDimForm(fmlDimFormExtParam);
            }
        } else if (DataEngineConsts.FormulaType.CALCULATE == formulaType && this.calFmlDimFormExtService != null) {
            fmlDimFormExtParam = new FmlDimFormExtParam(taskKey, formSchemeKey, fmlDimForms);
            fmlDimForms = this.calFmlDimFormExtService.getFmlDimForm(fmlDimFormExtParam);
        }
        return fmlDimForms;
    }

    public void addBJFormAccess(List<FmlDimForm> fmlDimForms, String formSchemeKey, String mainDimName) {
        if (fmlDimForms.size() == 1) {
            fmlDimForms.get(0).getFormKeys().add("00000000-0000-0000-0000-000000000000");
        } else {
            HashMap dimensions = new HashMap();
            for (FmlDimForm fmlDimForm : fmlDimForms) {
                DimensionCollection dimensionCollection = fmlDimForm.getDimensionCollection();
                DimensionValueSet mergeDimensionValueSet = this.dimensionCollectionUtil.getMergeDimensionValueSet(dimensionCollection);
                if (mergeDimensionValueSet == null) {
                    logger.error(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
                    return;
                }
                Object value = mergeDimensionValueSet.getValue(mainDimName);
                HashSet<String> dWs = new HashSet<String>();
                if (value instanceof String) {
                    dWs.add((String)value);
                } else if (value instanceof List) {
                    dWs.addAll((List)value);
                }
                if (CollectionUtils.isEmpty(dWs)) continue;
                mergeDimensionValueSet.clearValue(mainDimName);
                DimensionValueSet dimWithoutDW = new DimensionValueSet(mergeDimensionValueSet);
                if (!dimensions.containsKey(dimWithoutDW)) {
                    dimensions.put(dimWithoutDW, new HashSet());
                }
                ((Set)dimensions.get(dimWithoutDW)).addAll(dWs);
            }
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            for (Map.Entry e : dimensions.entrySet()) {
                DimensionValueSet dimWithoutDW = (DimensionValueSet)e.getKey();
                Set dWs = (Set)e.getValue();
                DimensionValueSet dimensionValueSet = new DimensionValueSet(dimWithoutDW);
                if (dWs.size() == 1) {
                    dimensionValueSet.setValue(mainDimName, dWs.stream().findFirst().get());
                } else {
                    dimensionValueSet.setValue(mainDimName, new ArrayList(dWs));
                }
                List<String> formKeys = Collections.singletonList("00000000-0000-0000-0000-000000000000");
                FixedDimBuilder fixedDimBuilder = new FixedDimBuilder(this.entityUtil.getContextMainDimId(formScheme.getDw()), dimensionValueSet);
                DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionValueSet, formSchemeKey, (SpecificDimBuilder)fixedDimBuilder);
                fmlDimForms.add(new FmlDimForm(dimensionCollection, formKeys));
            }
        }
    }

    public void addBJAllPermission(FormSchemeDefine formScheme, DimensionCollection dimensionCollection, String mainDimName, List<FmlDimForm> fmlDimForms) {
        List<DimensionCollection> dimensionCollections = this.dimensionCollectionUtil.mergeDimensionByDw(dimensionCollection, mainDimName, formScheme);
        for (DimensionCollection collection : dimensionCollections) {
            fmlDimForms.add(new FmlDimForm(collection, Collections.singletonList("00000000-0000-0000-0000-000000000000")));
        }
    }

    public ICalFmlProvider getCalFmlProvider(GetCalFmlEnv env) {
        ICalFmlProvider defaultFmlProvider = null;
        for (ICalFmlProvider fmlProvider : this.fmlProviders) {
            if (fmlProvider.isUse(env)) {
                return fmlProvider;
            }
            if (defaultFmlProvider != null || !"DefaultCalFmlProvider".equals(fmlProvider.getType())) continue;
            defaultFmlProvider = fmlProvider;
        }
        return defaultFmlProvider;
    }

    public List<DimensionAccessFormInfo.AccessFormInfo> getAccessFormInfos(List<String> formKeys, BaseEnv baseEnv, FormSchemeDefine formSchemeDefine, AccessLevel.FormAccessLevel formAccessLevel) {
        if (CollectionUtils.isEmpty(formKeys) && Mode.FORMULA == baseEnv.getMode()) {
            return new ArrayList<DimensionAccessFormInfo.AccessFormInfo>();
        }
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setCollectionMasterKey(baseEnv.getDimensionCollection());
        accessFormParam.setTaskKey(formSchemeDefine.getTaskKey());
        accessFormParam.setIgnoreAccessItems(baseEnv.getIgnoreItems());
        accessFormParam.setFormSchemeKey(formSchemeDefine.getKey());
        accessFormParam.setFormKeys(formKeys);
        accessFormParam.setFormAccessLevel(formAccessLevel);
        IDataAccessFormService dataAccessFormService = this.dataAccessServiceProvider.getDataAccessFormService();
        DimensionAccessFormInfo dimensionAccessFormInfo = dataAccessFormService.getBatchAccessForms(accessFormParam);
        return dimensionAccessFormInfo.getAccessForms();
    }

    public IDatabase getDataBase() {
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)Objects.requireNonNull(this.jdbcTemplate.getDataSource()));
            IDatabase iDatabase = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            return iDatabase;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
        return null;
    }
}

