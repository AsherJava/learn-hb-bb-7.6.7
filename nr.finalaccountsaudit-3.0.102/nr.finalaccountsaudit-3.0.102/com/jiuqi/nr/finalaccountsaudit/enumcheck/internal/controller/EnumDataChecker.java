/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableDataEngineService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.util.Guid
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowCallbackHandler
 */
package com.jiuqi.nr.finalaccountsaudit.enumcheck.internal.controller;

import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.common.DataQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.common.FormFieldWrapper;
import com.jiuqi.nr.finalaccountsaudit.common.TmpTableUtils;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.BizAssTable;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.DataFdInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.DataFdInfoBase;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumAssTable;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumCheckErrorKind;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumCheckException;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.internal.controller.EnumFilterCondition;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.internal.controller.MainDimData;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.util.Guid;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

@Component
public class EnumDataChecker {
    private PeriodWrapper periodWrapper;
    private IDataAssist dataAssist;
    private IMonitor monitor;
    private ExecutorContext context;
    private EnumDataCheckInfo condition;
    @Autowired
    private JdbcTemplate jdbcTpl;
    @Autowired
    IRunTimeViewController viewCtrl;
    @Autowired
    IDataAccessProvider dataAccessProvider;
    @Autowired
    private TmpTableUtils tmpTableUtils;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityMetaService metaService;
    @Autowired
    EntityQueryHelper entityQueryHelper;
    @Autowired
    IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    DataQueryHelper dataQueryHelper;
    @Autowired
    private IJtableDataEngineService tableDataEngineService;
    @Autowired
    private IDataAccessProvider iDataAccessProvider;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private DataModelService dataModelService;
    private HashMap<String, String> enumDic;
    private HashMap<String, HashMap<String, String>> enumDataDic;
    private HashMap<String, String> formDic;
    private HashMap<String, EntityViewData> dimViewDic;
    private HashMap<String, IEntityRow> masterEntityDic;
    private HashMap<String, String[]> tmpTableDic;
    private String masterDimName;
    private AtomicInteger errCount;
    private EnumFilterCondition enumFilterCondition;
    private String configEnum;
    private HashMap<String, String> dicFilterFdCode = new HashMap();
    private static final String BZ1 = "BZ1";

    private EnumDataChecker() {
    }

    private void init(String configEnum) {
        this.dataAssist = this.dataAccessProvider.newDataAssist((com.jiuqi.np.dataengine.executors.ExecutorContext)this.context);
        this.enumDataDic = new HashMap();
        this.errCount = new AtomicInteger(0);
        this.configEnum = null == configEnum ? this.taskOptionController.getValue(this.condition.getContext().getTaskKey(), "ENUMCHECK_CONDITION") : configEnum;
        this.enumFilterCondition = this.initEnumCondition();
    }

    private EnumFilterCondition initEnumCondition() {
        EnumFilterCondition result = new EnumFilterCondition();
        result.init(this.configEnum);
        return result;
    }

    public static EnumDataChecker CreateChecker(PeriodWrapper periodWrapper, ExecutorContext executorContext, EnumDataCheckInfo condition, String masterDimName) {
        EnumDataChecker result = (EnumDataChecker)BeanUtil.getBean(EnumDataChecker.class);
        EnumDataChecker checker = new EnumDataChecker();
        checker.dataAccessProvider = result.dataAccessProvider;
        checker.viewCtrl = result.viewCtrl;
        checker.jdbcTpl = result.jdbcTpl;
        checker.tmpTableUtils = result.tmpTableUtils;
        checker.jtableParamService = result.jtableParamService;
        checker.periodEntityAdapter = result.periodEntityAdapter;
        checker.metaService = result.metaService;
        checker.entityQueryHelper = result.entityQueryHelper;
        checker.dataQueryHelper = result.dataQueryHelper;
        checker.dataSchemeService = result.dataSchemeService;
        checker.periodWrapper = periodWrapper;
        checker.context = executorContext;
        checker.condition = condition;
        checker.masterDimName = masterDimName;
        checker.taskOptionController = result.taskOptionController;
        checker.tableDataEngineService = result.tableDataEngineService;
        checker.iDataAccessProvider = result.iDataAccessProvider;
        checker.dataModelService = result.dataModelService;
        checker.init(condition.getFilterFormula());
        return checker;
    }

    private void doDataTableCheck(String key, List<EnumAssTable> tableList, List<EnumDataCheckResultItem> result, String formCode) throws Exception {
        if (this.enumFilterCondition.isFilter(formCode) && StringUtils.isEmpty((String)this.enumFilterCondition.getFromFilterFml(formCode))) {
            return;
        }
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByName(key);
        String dataTableKey = this.dataSchemeService.getDataTableByTableModel(tableModelDefine.getID());
        DataTable dataTable = this.dataSchemeService.getDataTable(dataTableKey);
        String tableName = tableModelDefine.getName();
        String[] bizFdKeys = dataTable.getBizKeys();
        if (bizFdKeys.length == 0) {
            return;
        }
        ArrayList<BizAssTable> masterBizAssTable = new ArrayList<BizAssTable>();
        ArrayList<DataFdInfoBase> bizFds = new ArrayList<DataFdInfoBase>();
        ArrayList<DataFdInfoBase> bizFloats = new ArrayList<DataFdInfoBase>();
        for (String fdKey : bizFdKeys) {
            String dimName;
            DataField keyField = this.dataSchemeService.getDataField(fdKey);
            String string = "DATATIME".equals(keyField.getCode()) ? "DATATIME" : (dimName = StringUtils.isNotEmpty((String)keyField.getRefDataEntityKey()) ? this.metaService.getDimensionName(keyField.getRefDataEntityKey()) : null);
            if (this.isSelectDim(dimName)) {
                if (dimName.equals(this.masterDimName) || dimName.equals("DATATIME")) {
                    masterBizAssTable.add(this.createMasterBizAssTable(keyField.getCode(), dimName));
                    bizFds.add(new DataFdInfoBase(keyField.getCode(), tableName));
                } else {
                    masterBizAssTable.add(this.createMasterBizAssTable(this.entityQueryHelper.getFieldNameByFieldKey(keyField.getKey()), dimName));
                    bizFds.add(new DataFdInfoBase(this.entityQueryHelper.getFieldNameByFieldKey(keyField.getKey()), tableName));
                }
            } else if ("BIZKEYORDER".equals(keyField.getCode())) {
                bizFds.add(new DataFdInfoBase("BIZKEYORDER", tableName));
            } else {
                bizFds.add(new DataFdInfoBase(this.entityQueryHelper.getFieldNameByFieldKey(keyField.getKey()), tableName));
            }
            if (this.isSelectDim(dimName) || keyField.getCode().equals(BZ1)) continue;
            if ("BIZKEYORDER".equals(keyField.getCode())) {
                bizFloats.add(new DataFdInfoBase("BIZKEYORDER", tableName));
                continue;
            }
            bizFloats.add(new DataFdInfoBase(this.entityQueryHelper.getFieldNameByFieldKey(keyField.getKey()), tableName));
        }
        ((BizAssTable)masterBizAssTable.get(0)).setMainDim(true);
        for (EnumAssTable enumTable : tableList) {
            boolean hasVersion = false;
            String fdCode = this.getFilterFmlFdCode(enumTable, formCode);
            try {
                if (enumTable.getAllowMultipleSelect()) {
                    this.doMultCheck(tableName, hasVersion, bizFds, bizFloats, masterBizAssTable, enumTable, fdCode, result);
                    continue;
                }
                String sqlFormat = this.generateSql1(tableName, hasVersion, bizFds, bizFloats, masterBizAssTable, fdCode, enumTable);
                if (StringUtils.isEmpty((String)sqlFormat)) continue;
                this.executeSql(false, hasVersion, sqlFormat, enumTable, bizFds, bizFloats, result);
            }
            catch (Exception e) {
                if (!(e.getCause() instanceof EnumCheckException)) continue;
                return;
            }
        }
    }

    private void doMultCheck(String tableName, boolean hasVersion, final List<DataFdInfoBase> bizFds, final List<DataFdInfoBase> bizFloats, List<BizAssTable> masterBizAssTable, final EnumAssTable enumTable, String fdCode, final List<EnumDataCheckResultItem> result) throws Exception {
        String sqlFormat = this.generateSql2(tableName, hasVersion, bizFds, bizFloats, masterBizAssTable, fdCode, enumTable);
        if (StringUtils.isEmpty((String)sqlFormat)) {
            return;
        }
        final HashMap<String, String> enumDataDic = this.getEnumDataDic(enumTable.getEntityViewKey());
        int argsCount = EnumDataChecker.getStrCount(sqlFormat, "?");
        Object[] args = new Object[argsCount];
        int[] argTyps = new int[argsCount];
        for (int i = 0; i < args.length; ++i) {
            args[i] = PeriodUtils.getStartDateOfPeriod((String)this.periodWrapper.toString(), (boolean)false);
            argTyps[i] = 91;
        }
        if (argsCount > 0) {
            this.jdbcTpl.query(sqlFormat, args, argTyps, new RowCallbackHandler(){

                public void processRow(ResultSet rs) throws SQLException {
                    EnumDataCheckResultItem item = EnumDataChecker.this.doMultCheckRow(enumDataDic, enumTable, bizFds, bizFloats, rs);
                    if (item != null) {
                        result.add(item);
                    }
                }
            });
        } else {
            this.jdbcTpl.query(sqlFormat, new RowCallbackHandler(){

                public void processRow(ResultSet rs) throws SQLException {
                    EnumDataCheckResultItem item = EnumDataChecker.this.doMultCheckRow(enumDataDic, enumTable, bizFds, bizFloats, rs);
                    if (item != null) {
                        result.add(item);
                    }
                }
            });
        }
    }

    private HashMap<String, String> getEnumDataDic(String entityViewKey) throws Exception {
        if (this.enumDataDic.containsKey(entityViewKey)) {
            return this.enumDataDic.get(entityViewKey);
        }
        HashMap<String, String> result = new HashMap<String, String>();
        EntityViewDefine enumView = this.entityQueryHelper.getEnumViewByEntityId(entityViewKey);
        IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(enumView, null);
        IEntityTable entityTable = this.entityQueryHelper.buildEntityTable(entityQuery, null, false);
        for (IEntityRow row : entityTable.getAllRows()) {
            IEntityRow pRow = StringUtils.isEmpty((String)row.getParentEntityKey()) ? null : entityTable.findByEntityKey(row.getParentEntityKey());
            result.put(row.getEntityKeyData(), pRow == null ? "" : pRow.getCode());
        }
        this.enumDataDic.put(entityViewKey, result);
        return result;
    }

    private void doEntityTableCheck(String key, List<EnumAssTable> tableList, List<EnumDataCheckResultItem> result) throws Exception {
        if (this.enumFilterCondition.isFilter("FMDM") && StringUtils.isEmpty((String)this.enumFilterCondition.getFromFilterFml("FMDM"))) {
            return;
        }
        IEntityDefine entityDefine = this.metaService.queryEntityByCode(key);
        IEntityModel entityModel = this.metaService.getEntityModel(entityDefine.getId());
        TableModelDefine entityTable = null;
        try {
            entityTable = this.metaService.getTableModel(this.metaService.queryEntityByCode(key).getId());
        }
        catch (Exception e) {
            return;
        }
        IEntityAttribute dwField = entityModel.getBizKeyField();
        ArrayList<BizAssTable> masterBizAssTable = new ArrayList<BizAssTable>();
        masterBizAssTable.add(this.createMasterBizAssTable(dwField.getName(), this.masterDimName));
        ((BizAssTable)masterBizAssTable.get(0)).setMainDim(true);
        ArrayList<DataFdInfoBase> bizFds = new ArrayList<DataFdInfoBase>();
        bizFds.add(new DataFdInfoBase(dwField.getName(), entityTable.getName()));
        for (EnumAssTable enumTable : tableList) {
            String fdCode = this.getFilterFmlFdCode(enumTable, "FMDM");
            try {
                String sqlFormat;
                boolean hasVersion = true;
                if (enumTable.getAllowMultipleSelect()) {
                    sqlFormat = this.generateSql3(entityTable.getName(), hasVersion, bizFds, null, masterBizAssTable, fdCode, enumTable);
                    if (StringUtils.isEmpty((String)sqlFormat)) continue;
                    this.executeSql(true, hasVersion, sqlFormat, enumTable, bizFds, null, result);
                    continue;
                }
                sqlFormat = this.generateSql1(entityTable.getName(), hasVersion, bizFds, null, masterBizAssTable, fdCode, enumTable);
                if (StringUtils.isEmpty((String)sqlFormat)) continue;
                this.executeSql(true, hasVersion, sqlFormat, enumTable, bizFds, null, result);
            }
            catch (Exception e) {
                if (!(e.getCause() instanceof EnumCheckException)) continue;
                return;
            }
        }
    }

    private String getFilterFmlFdCode(EnumAssTable enumTable, String formCode) {
        DataLinkDefine dl = this.viewCtrl.queryDataLinkDefine(enumTable.getDataLinkKey());
        String zbFml = String.format("%s[%s,%s]", formCode, dl.getRowNum(), dl.getColNum());
        String fml = this.enumFilterCondition.getZbFilterFml(zbFml);
        if (StringUtils.isEmpty((String)fml)) {
            return null;
        }
        return this.dicFilterFdCode.get(fml);
    }

    private void executeSql(final boolean isEntityTableCheck, boolean hasVersion, String sqlFormat, final EnumAssTable enumTable, final List<DataFdInfoBase> bizFds, final List<DataFdInfoBase> bizFloats, final List<EnumDataCheckResultItem> result) {
        int argsCount = EnumDataChecker.getStrCount(sqlFormat, "?");
        Object[] args = new Object[argsCount];
        int[] argTyps = new int[argsCount];
        for (int i = 0; i < args.length; ++i) {
            args[i] = PeriodUtils.getStartDateOfPeriod((String)this.periodWrapper.toString(), (boolean)false);
            argTyps[i] = 91;
        }
        if (argsCount > 0) {
            this.jdbcTpl.query(sqlFormat, args, argTyps, new RowCallbackHandler(){

                public void processRow(ResultSet rs) throws SQLException {
                    EnumDataCheckResultItem item = EnumDataChecker.this.dataRow2Data(enumTable, bizFds, bizFloats, rs);
                    if (item != null) {
                        if (isEntityTableCheck && "PARENTCODE".equals(enumTable.getDataField().getCode()) && "-".equals(item.getDataValue())) {
                            return;
                        }
                        result.add(item);
                    }
                }
            });
        } else {
            this.jdbcTpl.query(sqlFormat, new RowCallbackHandler(){

                public void processRow(ResultSet rs) throws SQLException {
                    EnumDataCheckResultItem item = EnumDataChecker.this.dataRow2Data(enumTable, bizFds, bizFloats, rs);
                    if (item != null) {
                        if (isEntityTableCheck && "PARENTCODE".equals(enumTable.getDataField().getCode()) && "-".equals(item.getDataValue())) {
                            return;
                        }
                        result.add(item);
                    }
                }
            });
        }
    }

    public List<EnumDataCheckResultItem> executeCheck(TaskDefine task, String entityId, PeriodWrapper periodWrapper, String formKey, HashSet<String> enumTableDic) throws Exception {
        ArrayList<EnumDataCheckResultItem> result = new ArrayList<EnumDataCheckResultItem>();
        if (this.isFilterForm(formKey)) {
            return result;
        }
        FormDefine fm = this.viewCtrl.queryFormById(formKey);
        List links = this.viewCtrl.getAllLinksInForm(formKey);
        HashMap dataTableInfoDic = new HashMap();
        HashMap entityTableInfoDic = new HashMap();
        for (DataLinkDefine dataLinkDefine : links) {
            EnumAssTable enumAssTable;
            List<EnumAssTable> tables;
            FormFieldWrapper fmF;
            if (dataLinkDefine.getPosX() < 1 || dataLinkDefine.getPosY() < 1) continue;
            DataRegionDefine dataRegionDefine = this.viewCtrl.queryDataRegionDefine(dataLinkDefine.getRegionKey());
            if (dataLinkDefine.getPosY() > dataRegionDefine.getRegionBottom() || dataLinkDefine.getPosX() > dataRegionDefine.getRegionRight() || (fmF = this.entityQueryHelper.getFormField(task.getDataScheme(), entityId, fm, dataLinkDefine)) == null || fmF.getRefEntity() == null || this.isFilterEnum1(fmF.getRefEntity())) continue;
            if (!fmF.isEntityTable()) {
                tables = (ArrayList<EnumAssTable>)dataTableInfoDic.get(fmF.getTableName());
                if (tables == null) {
                    tables = new ArrayList<EnumAssTable>();
                    dataTableInfoDic.put(fmF.getTableName(), tables);
                }
                enumAssTable = this.createEnumAssTable(dataLinkDefine, fmF);
                tables.add(enumAssTable);
            } else {
                tables = (List)entityTableInfoDic.get(fmF.getTableName());
                if (tables == null) {
                    tables = new ArrayList();
                    entityTableInfoDic.put(fmF.getTableName(), tables);
                }
                enumAssTable = this.createEnumAssTable(dataLinkDefine, fmF);
                tables.add(enumAssTable);
            }
            if (enumTableDic.contains(fmF.getRefEntity().getId())) continue;
            enumTableDic.add(fmF.getRefEntity().getId());
        }
        for (Map.Entry entry : dataTableInfoDic.entrySet()) {
            String k = (String)entry.getKey();
            List value = (List)entry.getValue();
            this.doDataTableCheck(k, value, result, fm.getFormCode());
        }
        for (Map.Entry entry : entityTableInfoDic.entrySet()) {
            String key = (String)entry.getKey();
            List tableList = (List)entry.getValue();
            this.doEntityTableCheck(key, tableList, result);
        }
        for (EnumDataCheckResultItem enumDataCheckResultItem : result) {
            enumDataCheckResultItem.setBbfz(fm.getTitle());
            enumDataCheckResultItem.setFormKey(fm.getKey());
        }
        return result;
    }

    private BizAssTable createMasterBizAssTable(String dataFieldName, String dimName) {
        BizAssTable bizAssTable = new BizAssTable();
        String[] tmpTableArr = this.tmpTableDic.get(dimName);
        bizAssTable.setDbFieldName(dataFieldName);
        bizAssTable.setTableName(tmpTableArr[0]);
        bizAssTable.setFieldName(tmpTableArr[1]);
        return bizAssTable;
    }

    private EnumDataCheckResultItem doMultCheckRow(HashMap<String, String> enumDataDic, EnumAssTable enumAssTable, List<DataFdInfoBase> bizFds, List<DataFdInfoBase> bizFloats, ResultSet rs) throws SQLException {
        EnumDataCheckResultItem result = null;
        String enumDataValue = rs.getString("dc");
        if (StringUtils.isEmpty((String)enumDataValue)) {
            if (!enumAssTable.getCanNull()) {
                result = this.dataRow2Data(enumAssTable, bizFds, bizFloats, rs);
                result.setErrorKind(EnumCheckErrorKind.ISNULL);
                return result;
            }
            return null;
        }
        for (String subValue : enumDataValue.split(";")) {
            if (StringUtils.isEmpty((String)subValue)) continue;
            if (enumAssTable.getFixSize() > 0 && subValue.length() != enumAssTable.getFixSize()) {
                result = this.dataRow2Data(enumAssTable, bizFds, bizFloats, rs);
                result.setErrorKind(EnumCheckErrorKind.NOTFIXSIZE);
                return result;
            }
            if (!enumAssTable.getAllUndefineCode() && !enumDataDic.containsKey(subValue)) {
                result = this.dataRow2Data(enumAssTable, bizFds, bizFloats, rs);
                result.setErrorKind(EnumCheckErrorKind.UNDEFINE_CODE);
                return result;
            }
            if (!enumAssTable.getOnlyLeafNode() || !enumDataDic.containsKey(subValue) || !enumDataDic.containsValue(subValue)) continue;
            result = this.dataRow2Data(enumAssTable, bizFds, bizFloats, rs);
            result.setErrorKind(EnumCheckErrorKind.NOTLEAF);
            return result;
        }
        return null;
    }

    private EnumDataCheckResultItem entityRow2Data(HashMap<String, String> dicCode2DimName, EnumAssTable enumTable, List<ColumnModelDefine> bizFds, ResultSet rs) throws SQLException {
        EnumDataCheckResultItem result = new EnumDataCheckResultItem();
        result.setId(Guid.newGuid());
        int idx = 1;
        String unitKey = null;
        for (ColumnModelDefine bizFd : bizFds) {
            String dimName = dicCode2DimName.get(bizFd.getName());
            if (this.masterDimName.startsWith(dimName)) {
                unitKey = rs.getString(idx);
            }
            ++idx;
        }
        result.setMasterEntityKey(unitKey);
        IEntityRow entityRow = this.masterEntityDic.get(unitKey);
        if (entityRow != null) {
            result.setEntityTitle(entityRow.getTitle());
            result.setEntityOrder(this.getEntityOrder(entityRow));
        } else {
            result.setEntityTitle(unitKey);
            result.setEntityOrder(999999999);
        }
        result.setDataValue(rs.getString(idx++));
        result.setEnumCode(rs.getString(idx++));
        ArrayList<String> enumTitle = new ArrayList<String>();
        for (IEntityAttribute fd : enumTable.getEnumTitleField()) {
            enumTitle.add(rs.getString(idx++));
        }
        result.setEnumTitle(String.join((CharSequence)"|", enumTitle));
        result.setDataLinkKey(enumTable.getDataLinkKey());
        result.setRegionId(enumTable.getRegionId());
        result.setField(enumTable.getDataField().getCode() + "/" + enumTable.getDataField().getName());
        if (StringUtils.isEmpty((String)result.getDataValue())) {
            if (!enumTable.getCanNull()) {
                result.setErrorKind(EnumCheckErrorKind.ISNULL);
            }
        } else if (enumTable.getFixSize() > 0 && result.getDataValue().length() != enumTable.getFixSize()) {
            result.setErrorKind(EnumCheckErrorKind.NOTFIXSIZE);
        } else if (!enumTable.getAllUndefineCode()) {
            if (StringUtils.isEmpty((String)result.getEnumCode())) {
                result.setErrorKind(EnumCheckErrorKind.UNDEFINE_CODE);
            } else if (enumTable.getOnlyLeafNode()) {
                result.setErrorKind(EnumCheckErrorKind.NOTLEAF);
            }
        } else if (enumTable.getOnlyLeafNode() && !StringUtils.isEmpty((String)result.getEnumCode())) {
            result.setErrorKind(EnumCheckErrorKind.NOTLEAF);
        }
        result.setEnumCode(enumTable.getTable().getCode());
        result.setEnumTitle(enumTable.getTable().getTitle());
        return result;
    }

    private EnumDataCheckResultItem dataRow2Data(EnumAssTable enumTable, List<DataFdInfoBase> bizFds, List<DataFdInfoBase> bizFloats, ResultSet rs) throws SQLException {
        EnumDataCheckResultItem result = new EnumDataCheckResultItem();
        result.setId(Guid.newGuid());
        int idx = 1;
        String unitKey = null;
        for (DataFdInfoBase bizFd : bizFds) {
            if (idx == 1) {
                unitKey = rs.getString(idx);
            }
            ++idx;
        }
        if (bizFloats != null) {
            int bizftIdx = 1;
            StringBuilder idStr = new StringBuilder();
            for (DataFdInfoBase bizFt : bizFloats) {
                idStr.append(rs.getString(idx++));
                if (bizftIdx < bizFloats.size()) {
                    idStr.append("#^$");
                }
                ++bizftIdx;
            }
            result.setDataId(idStr.toString());
        }
        result.setMasterEntityKey(unitKey);
        IEntityRow entityRow = this.masterEntityDic.get(unitKey);
        if (entityRow != null) {
            result.setEntityTitle(entityRow.getTitle());
            result.setEntityOrder(this.getEntityOrder(entityRow));
        } else {
            result.setEntityTitle(unitKey);
            result.setEntityOrder(999999999);
        }
        result.setDataValue(rs.getString(idx++));
        result.setEnumCode(rs.getString(idx++));
        ArrayList<String> enumTitle = new ArrayList<String>();
        for (IEntityAttribute fd : enumTable.getEnumTitleField()) {
            enumTitle.add(rs.getString(idx++));
        }
        result.setEnumTitle(String.join((CharSequence)"|", enumTitle));
        result.setDataLinkKey(enumTable.getDataLinkKey());
        result.setRegionId(enumTable.getRegionId());
        result.setField(enumTable.getDataField().getCode() + "/" + enumTable.getDataField().getName());
        if (StringUtils.isEmpty((String)result.getDataValue())) {
            if (!enumTable.getCanNull()) {
                result.setErrorKind(EnumCheckErrorKind.ISNULL);
            }
        } else if (enumTable.getFixSize() > 0 && result.getDataValue().length() != enumTable.getFixSize()) {
            result.setErrorKind(EnumCheckErrorKind.NOTFIXSIZE);
        } else if (!enumTable.getAllUndefineCode()) {
            if (StringUtils.isEmpty((String)result.getEnumCode())) {
                result.setErrorKind(EnumCheckErrorKind.UNDEFINE_CODE);
            } else if (enumTable.getOnlyLeafNode()) {
                result.setErrorKind(EnumCheckErrorKind.NOTLEAF);
            }
        } else if (enumTable.getOnlyLeafNode() && !StringUtils.isEmpty((String)result.getEnumCode())) {
            result.setErrorKind(EnumCheckErrorKind.NOTLEAF);
        }
        result.setEnumCode(enumTable.getTable().getCode());
        result.setEnumTitle(enumTable.getTable().getTitle());
        return result;
    }

    private String generateSql3(String tableName, boolean hasVersion, List<DataFdInfoBase> bizFds, List<DataFdInfoBase> bizFloats, List<BizAssTable> masterBizAssTable, String fdCode, EnumAssTable enumAssTable) {
        String libTableName = this.dataQueryHelper.getLibraryTableName(this.condition.getContext().getTaskKey(), tableName);
        StringBuffer sqlStr = new StringBuffer();
        sqlStr.append("SELECT ");
        for (int i = 0; i < bizFds.size(); ++i) {
            sqlStr.append(" dt.").append(bizFds.get(i).getFieldName()).append(",");
        }
        sqlStr.append(" dt1.fieldValue as dC,");
        sqlStr.append(" et.").append(enumAssTable.getEnumCodeField().getCode()).append(" as enumC,");
        int idxEnumTitle = 0;
        for (IEntityAttribute fd : enumAssTable.getEnumTitleField()) {
            if (idxEnumTitle > 0) {
                sqlStr.append(",");
            }
            sqlStr.append(" et.").append(fd.getCode()).append(" as enumT").append(idxEnumTitle++);
        }
        sqlStr.append(" from ").append(libTableName).append(" dt ");
        sqlStr.append(" left join ").append(libTableName).append("_SUBLIST dt1 ON dt.id = dt1.MASTERID AND '").append(enumAssTable.getDataField().getFieldName()).append("' = dt1.FIELDNAME");
        sqlStr.append(" left join ").append(this.getEnumTableStr(enumAssTable.getTable().getCode())).append(" et");
        sqlStr.append(" on dt1.FIELDVALUE = ").append(" et.").append(enumAssTable.getEnumCodeField().getCode());
        sqlStr.append(" where ");
        if (hasVersion) {
            sqlStr.append(String.format(" ((dt.%s <= ? or dt.%s is null) and (dt.%s > ? or dt.%s is null)) ", "VALIDTIME", "VALIDTIME", "INVALIDTIME", "INVALIDTIME"));
        }
        for (int i = 0; i < masterBizAssTable.size(); ++i) {
            if (i > 0 || hasVersion) {
                sqlStr.append(" and ");
            }
            String bizAssTable = masterBizAssTable.get(i).getTableName();
            sqlStr.append(" exists (select 1 from ").append(bizAssTable).append(" ass").append(i);
            sqlStr.append(" where ass").append(i).append(".").append(masterBizAssTable.get(i).getFieldName()).append(" = ").append(" dt.").append(masterBizAssTable.get(i).getDbFieldName());
            if (masterBizAssTable.get(i).isMainDim() && !StringUtils.isEmpty((String)fdCode)) {
                sqlStr.append(" and ass").append(i).append(".").append(fdCode).append("=\u5426");
            }
            sqlStr.append(") ");
        }
        boolean needQuery = false;
        sqlStr.append(" and ");
        if (!enumAssTable.getCanNull()) {
            sqlStr.append("(");
            sqlStr.append(" dt1.FIELDVALUE is null ");
            needQuery = true;
        } else {
            sqlStr.append(" dt1.FIELDVALUE is not null ");
            sqlStr.append(" and (");
        }
        if (enumAssTable.getFixSize() > 0) {
            if (needQuery) {
                sqlStr.append(" or ");
            }
            sqlStr.append(" length(dt1.FIELDVALUE) <> ").append(enumAssTable.getFixSize());
            needQuery = true;
        }
        if (!enumAssTable.getAllUndefineCode()) {
            if (needQuery) {
                sqlStr.append(" or ");
            }
            sqlStr.append(" et.").append(enumAssTable.getEnumCodeField().getCode()).append(" is null ");
            needQuery = true;
            if (enumAssTable.getOnlyLeafNode() && enumAssTable.getEnumPCodeField() != null) {
                sqlStr.append(" or ");
                sqlStr.append(" exists(select 1 from ").append(this.getEnumTableStr(enumAssTable.getTable().getCode())).append(" etp ");
                sqlStr.append(" where dt1.FIELDVALUE = ").append(" etp.").append(enumAssTable.getEnumPCodeField().getCode());
                sqlStr.append(")");
                needQuery = true;
            }
        } else if (enumAssTable.getOnlyLeafNode()) {
            if (needQuery) {
                sqlStr.append(" or ");
            }
            sqlStr.append(" et.").append(enumAssTable.getEnumCodeField().getCode()).append(" is not null and");
            sqlStr.append(" exists(select 1 from ").append(this.getEnumTableStr(enumAssTable.getTable().getCode())).append(" etp ");
            sqlStr.append(" where dt1.FIELDVALUE = ").append(" etp.").append(enumAssTable.getEnumPCodeField().getCode());
            sqlStr.append(")");
            needQuery = true;
        }
        sqlStr.append(")");
        if (!needQuery) {
            return null;
        }
        return sqlStr.toString();
    }

    private String generateSql2(String tableName, boolean hasVersion, List<DataFdInfoBase> bizFds, List<DataFdInfoBase> bizFloats, List<BizAssTable> masterBizAssTable, String fdCode, EnumAssTable enumAssTable) {
        int i;
        String libTableName = this.dataQueryHelper.getLibraryTableName(this.condition.getContext().getTaskKey(), tableName);
        StringBuffer sqlStr = new StringBuffer();
        sqlStr.append("SELECT ");
        for (i = 0; i < bizFds.size(); ++i) {
            sqlStr.append(" dt.").append(bizFds.get(i).getFieldName()).append(",");
        }
        for (i = 0; i < bizFloats.size(); ++i) {
            sqlStr.append(" dt.").append(bizFloats.get(i).getFieldName()).append(" as ").append(bizFloats.get(i).getFieldName() + 1).append(",");
        }
        sqlStr.append(" dt.").append(enumAssTable.getDataField().getFieldName()).append(" as dC,");
        sqlStr.append(" et.").append(enumAssTable.getEnumCodeField().getCode()).append(" as enumC,");
        int idxEnumTitle = 0;
        for (IEntityAttribute fd : enumAssTable.getEnumTitleField()) {
            if (idxEnumTitle > 0) {
                sqlStr.append(",");
            }
            sqlStr.append(" et.").append(fd.getCode()).append(" as enumT").append(idxEnumTitle++);
        }
        sqlStr.append(" from ").append(libTableName).append(" dt ");
        sqlStr.append(" left join ").append(this.getEnumTableStr(enumAssTable.getTable().getCode())).append(" et");
        sqlStr.append(" on dt.").append(enumAssTable.getDataField().getFieldName()).append(" = ").append(" et.").append(enumAssTable.getEnumCodeField().getCode());
        sqlStr.append(" where ");
        if (hasVersion) {
            sqlStr.append(String.format(" ((dt.%s <= ? or dt.%s is null) and (dt.%s > ? or dt.%s is null)) ", "VALIDTIME", "VALIDTIME", "INVALIDTIME", "INVALIDTIME"));
        }
        for (int i2 = 0; i2 < masterBizAssTable.size(); ++i2) {
            if (i2 > 0 || hasVersion) {
                sqlStr.append(" and ");
            }
            sqlStr.append(" exists (select 1 from ").append(masterBizAssTable.get(i2).getTableName()).append(" ass").append(i2);
            sqlStr.append(" where ass").append(i2).append(".").append(masterBizAssTable.get(i2).getFieldName()).append(" = ").append(" dt.").append(masterBizAssTable.get(i2).getDbFieldName());
            if (masterBizAssTable.get(i2).isMainDim() && !StringUtils.isEmpty((String)fdCode)) {
                sqlStr.append(" and ass").append(i2).append(".").append(fdCode).append("='\u5426'");
            }
            sqlStr.append(") ");
        }
        return sqlStr.toString();
    }

    private String generateSql1(String tableName, boolean hasVersion, List<DataFdInfoBase> bizFds, List<DataFdInfoBase> bizFloats, List<BizAssTable> masterBizAssTable, String fdCode, EnumAssTable enumAssTable) {
        int i;
        String libTableName = this.dataQueryHelper.getLibraryTableName(this.condition.getContext().getTaskKey(), tableName);
        StringBuffer sqlStr = new StringBuffer();
        sqlStr.append("SELECT ");
        for (i = 0; i < bizFds.size(); ++i) {
            sqlStr.append(" dt.").append(bizFds.get(i).getFieldName()).append(",");
        }
        if (bizFloats != null) {
            for (i = 0; i < bizFloats.size(); ++i) {
                sqlStr.append(" dt.").append(bizFds.get(i).getFieldName()).append(" as ").append(bizFds.get(i).getFieldName() + 1).append(",");
            }
        }
        sqlStr.append(" dt.").append(enumAssTable.getDataField().getFieldName()).append(" as dC,");
        sqlStr.append(" et.").append(enumAssTable.getEnumCodeField().getCode()).append(" as enumC,");
        int idxEnumTitle = 0;
        for (IEntityAttribute fd : enumAssTable.getEnumTitleField()) {
            if (idxEnumTitle > 0) {
                sqlStr.append(",");
            }
            sqlStr.append(" et.").append(fd.getCode()).append(" as enumT").append(idxEnumTitle++);
        }
        sqlStr.append(" from ").append(libTableName).append(" dt ");
        sqlStr.append(" left join ").append(this.getEnumTableStr(enumAssTable.getTable().getCode())).append(" et");
        sqlStr.append(" on dt.").append(enumAssTable.getDataField().getFieldName()).append(" = ").append(" et.").append(enumAssTable.getEnumCodeField().getCode());
        sqlStr.append(" where ");
        if (hasVersion) {
            sqlStr.append(String.format(" ((dt.%s <= ? or dt.%s is null) and (dt.%s > ? or dt.%s is null)) ", "VALIDTIME", "VALIDTIME", "INVALIDTIME", "INVALIDTIME"));
        }
        for (int i2 = 0; i2 < masterBizAssTable.size(); ++i2) {
            if (i2 > 0 || hasVersion) {
                sqlStr.append(" and ");
            }
            String bizAssTable = masterBizAssTable.get(i2).getTableName();
            sqlStr.append(" exists (select 1 from ").append(bizAssTable).append(" ass").append(i2);
            sqlStr.append(" where ass").append(i2).append(".").append(masterBizAssTable.get(i2).getFieldName()).append(" = ").append(" dt.").append(masterBizAssTable.get(i2).getDbFieldName());
            if (masterBizAssTable.get(i2).isMainDim() && !StringUtils.isEmpty((String)fdCode)) {
                sqlStr.append(" and ass").append(i2).append(".").append(fdCode).append("='\u5426'");
            }
            sqlStr.append(") ");
        }
        boolean needQuery = false;
        sqlStr.append(" and ");
        if (!enumAssTable.getCanNull()) {
            sqlStr.append("(");
            sqlStr.append(" dt.").append(enumAssTable.getDataField().getFieldName()).append(" is null ");
            needQuery = true;
        } else {
            sqlStr.append(" dt.").append(enumAssTable.getDataField().getFieldName()).append(" is not null ");
            sqlStr.append(" and (");
        }
        if (enumAssTable.getFixSize() > 0) {
            if (needQuery) {
                sqlStr.append(" or ");
            }
            sqlStr.append(" length(dt.").append(enumAssTable.getDataField().getFieldName()).append(") <> ").append(enumAssTable.getFixSize());
            needQuery = true;
        }
        if (!enumAssTable.getAllUndefineCode()) {
            if (needQuery) {
                sqlStr.append(" or ");
            }
            sqlStr.append(" et.").append(enumAssTable.getEnumCodeField().getCode()).append(" is null ");
            needQuery = true;
            if (enumAssTable.getOnlyLeafNode() && enumAssTable.getEnumPCodeField() != null) {
                sqlStr.append(" or ");
                sqlStr.append(" exists(select 1 from ").append(this.getEnumTableStr(enumAssTable.getTable().getCode())).append(" etp ");
                sqlStr.append(" where dt.").append(enumAssTable.getDataField().getFieldName()).append(" = ").append(" etp.").append(enumAssTable.getEnumPCodeField().getCode());
                sqlStr.append(")");
                needQuery = true;
            }
        } else if (enumAssTable.getOnlyLeafNode()) {
            if (needQuery) {
                sqlStr.append(" or ");
            }
            sqlStr.append(" et.").append(enumAssTable.getEnumCodeField().getCode()).append(" is not null and");
            sqlStr.append(" exists(select 1 from ").append(this.getEnumTableStr(enumAssTable.getTable().getCode())).append(" etp ");
            sqlStr.append(" where dt.").append(enumAssTable.getDataField().getFieldName()).append(" = ").append(" etp.").append(enumAssTable.getEnumPCodeField().getCode());
            sqlStr.append(")");
            needQuery = true;
        }
        sqlStr.append(")");
        if (!needQuery) {
            return null;
        }
        return sqlStr.toString();
    }

    private String getEnumTableStr(String tableCode) {
        return String.format(" (select * from %s where ((%s <= ? or %s is null) and (%s > ? or %s is null)) and recoveryflag = 0) ", tableCode, "VALIDTIME", "VALIDTIME", "INVALIDTIME", "INVALIDTIME");
    }

    public static int getStrCount(String str, String subStr) {
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(subStr, index)) != -1) {
            ++count;
            index += subStr.length();
        }
        return count;
    }

    private EnumAssTable createEnumAssTable(DataLinkDefine dl, DataFdInfo fd, IEntityModel refEntityModel) {
        TableModelDefine entityTable = this.metaService.getTableModel(refEntityModel.getEntityId());
        EnumAssTable result = new EnumAssTable();
        result.setTable(entityTable);
        result.setAllUndefineCode(dl.getAllowUndefinedCode());
        result.setOnlyLeafNode(!dl.getAllowNotLeafNodeRefer());
        result.setAllowMultipleSelect(dl.getAllowMultipleSelect());
        result.setDataField(fd);
        result.setFixSize(-1);
        result.setFixSize(fd.getFixedSize());
        result.setCanNull(dl.getDataValidation() == null || dl.getDataValidation().size() == 0 || !dl.getDataValidation().contains(String.format("NOT ISNULL(%s[%s])", fd.getTableName(), fd.getCode())));
        if (result.getCanNull()) {
            result.setCanNull(fd.isNullAble());
        }
        result.setDataLinkKey(dl.getKey());
        result.setRegionId(dl.getRegionKey());
        result.setEnumCodeField(refEntityModel.getCodeField());
        result.setEnumPCodeField(refEntityModel.getParentField());
        result.setEnumTitleField(Arrays.asList(refEntityModel.getNameField()));
        assert (result.getEnumCodeField() != null);
        assert (result.getEnumTitleField() != null);
        assert (dl.getAllowNotLeafNodeRefer() || result.getEnumPCodeField() != null);
        return result;
    }

    private EnumAssTable createEnumAssTable(DataLinkDefine dl, FormFieldWrapper fmF) throws Exception {
        DataFdInfo fd;
        TableModelDefine entityTable = this.metaService.getTableModel(fmF.getRefEntity().getId());
        IEntityModel refEntityModel = this.metaService.getEntityModel(fmF.getRefEntity().getId());
        EnumAssTable result = new EnumAssTable();
        result.setCanNull(true);
        if (fmF.getFieldObj() instanceof ColumnModelDefine) {
            fd = fmF.createFdInfo(this.entityQueryHelper);
            result.setAllowMultipleSelect(((ColumnModelDefine)fmF.getFieldObjAs()).isMultival());
            result.setCanNull(dl.getDataValidation() == null || dl.getDataValidation().size() == 0 || !dl.getDataValidation().contains(String.format("NOT ISNULL(%s[%s])", fd.getTableName(), fd.getCode())));
            if (result.getCanNull()) {
                result.setCanNull(fd.isNullAble());
            }
        } else {
            FieldDefine tmpFd = (FieldDefine)fmF.getFieldObjAs();
            fd = fmF.createFdInfo(this.entityQueryHelper);
            result.setAllowMultipleSelect(tmpFd.getAllowMultipleSelect());
            DataField dataField = this.dataSchemeService.getDataField(tmpFd.getKey());
            if (dataField.getValidationRules() != null && dataField.getValidationRules().size() > 0) {
                for (ValidationRule rule : dataField.getValidationRules()) {
                    if (!StringUtils.isNotEmpty((String)rule.getVerification()) || !rule.getVerification().contains(String.format("NOT ISNULL(%s[%s])", fd.getTableName(), dataField.getCode()))) continue;
                    result.setCanNull(false);
                    break;
                }
            }
            if (result.getCanNull()) {
                result.setCanNull(fd.isNullAble());
            }
        }
        if (result.getCanNull()) {
            result.setCanNull(dl.getAllowNullAble());
        }
        result.setEntityViewKey(refEntityModel.getEntityId());
        result.setTable(entityTable);
        result.setAllUndefineCode(dl.getAllowUndefinedCode());
        result.setOnlyLeafNode(!dl.getAllowNotLeafNodeRefer());
        result.setDataField(fd);
        result.setFixSize(-1);
        result.setFixSize(fd.getFixedSize());
        result.setDataLinkKey(dl.getKey());
        result.setEnumPCodeField(refEntityModel.getParentField());
        result.setRegionId(dl.getRegionKey());
        this.setEnumCodeFiled(result, refEntityModel);
        result.setEnumTitleField(Arrays.asList(refEntityModel.getNameField()));
        assert (result.getEnumCodeField() != null);
        assert (result.getEnumTitleField() != null);
        assert (dl.getAllowNotLeafNodeRefer() || result.getEnumPCodeField() != null);
        if (this.condition.isIgnoreBlank()) {
            result.setCanNull(true);
        }
        return result;
    }

    private void setEnumCodeFiled(EnumAssTable result, IEntityModel refEntityModel) {
        if (refEntityModel.getCodeField().getCode().equals("ORGCODE")) {
            result.setEnumCodeField(refEntityModel.getBizKeyField());
        } else {
            result.setEnumCodeField(refEntityModel.getCodeField());
        }
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    private boolean isSelectDim(String dimName) {
        try {
            return this.condition.getSelectedDimensionSet().containsKey(dimName);
        }
        catch (Exception e) {
            return false;
        }
    }

    private boolean isFilterEnum(TableModelDefine entityTable) {
        return this.enumDic.size() > 0 && !this.enumDic.containsKey(entityTable.getCode());
    }

    private boolean isFilterEnum1(IEntityDefine entityDefine) {
        if (entityDefine == null) {
            return true;
        }
        return this.enumDic.size() > 0 && !this.enumDic.containsKey(entityDefine.getCode());
    }

    private boolean isFilterForm(String formKey) {
        boolean result;
        boolean bl = result = this.formDic.size() > 0 && !this.formDic.containsKey(formKey);
        if (!result) {
            FormDefine fm = this.viewCtrl.queryFormById(formKey);
            if (fm == null) {
                return true;
            }
            switch (fm.getFormType()) {
                case FORM_TYPE_QUERY: 
                case FORM_TYPE_TEXT_INFO: 
                case FORM_TYPE_INTERMEDIATE: 
                case FORM_TYPE_ATTACHED: {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public Set<String> getSelUnitKeys() {
        return this.masterEntityDic.keySet();
    }

    public List<ITempTable> prepareData() throws Exception {
        ArrayList<ITempTable> tmpTables = new ArrayList<ITempTable>();
        if (this.monitor != null) {
            this.monitor.message("\u521d\u59cb\u5316\u6570\u636e", null);
            this.monitor.onProgress(0.1);
        }
        ITempTable tmpTableA = null;
        this.enumDic = new HashMap();
        if (!StringUtils.isEmpty((String)this.condition.getEnumNames())) {
            for (String enumName : this.condition.getEnumNames().split(";")) {
                this.enumDic.put(enumName, enumName);
            }
        }
        this.formDic = new HashMap();
        if (!StringUtils.isEmpty((String)this.condition.getFormKeys())) {
            for (String formKey : this.condition.getFormKeys().split(";")) {
                this.formDic.put(formKey, formKey);
            }
        }
        this.dimViewDic = new HashMap();
        FormSchemeDefine formSchemeDefine = this.viewCtrl.getFormScheme(this.condition.getContext().getFormSchemeKey());
        List entityList = this.jtableParamService.getEntityList(formSchemeDefine.getMasterEntitiesKey());
        for (Object entityInfo : entityList) {
            this.dimViewDic.put(entityInfo.getDimensionName(), (EntityViewData)entityInfo);
        }
        List<String> periodData = null;
        for (String dimStr : this.condition.getSelectedDimensionSet().keySet()) {
            String viewKey = this.dimViewDic.get(dimStr).getKey();
            if (!this.periodEntityAdapter.isPeriodEntity(viewKey)) continue;
            String enityListStr = this.condition.getSelectedDimensionSet().get(dimStr).getValue();
            if (StringUtils.isEmpty((String)enityListStr) && this.periodWrapper == null) {
                throw new Exception("\u65f6\u671f\u4f20\u5165\u6709\u8bef");
            }
            periodData = Arrays.asList(enityListStr.split(";"));
        }
        if (periodData == null) {
            throw new Exception("\u65f6\u671f\u4f20\u5165\u6709\u8bef");
        }
        if (periodData.size() > 1) {
            throw new Exception("\u6682\u4e0d\u652f\u6301\u591a\u65f6\u671f");
        }
        int fmlSize = 0;
        this.tmpTableDic = new HashMap();
        this.masterEntityDic = new HashMap();
        for (String dimStr : this.condition.getSelectedDimensionSet().keySet()) {
            MainDimData mainDimData = new MainDimData();
            String viewKey = this.dimViewDic.get(dimStr).getKey();
            String enityListStr = this.condition.getSelectedDimensionSet().get(dimStr).getValue();
            if (this.periodEntityAdapter.isPeriodEntity(viewKey)) {
                if (StringUtils.isEmpty((String)enityListStr) && this.periodWrapper == null) {
                    throw new Exception("\u65f6\u671f\u4f20\u5165\u6709\u8bef");
                }
                ArrayList<String> newMainLsit = new ArrayList<String>(Arrays.asList(enityListStr.split(";")));
                mainDimData.setMainDimValue(newMainLsit);
            } else if (this.enumFilterCondition.getAllFilterFml().size() == 0) {
                EntityViewDefine entityViewDefine = this.entityQueryHelper.getEntityView(formSchemeDefine.getKey(), viewKey);
                IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(entityViewDefine, periodData.get(0));
                if (!StringUtils.isEmpty((String)enityListStr)) {
                    DimensionValueSet valueSet = entityQuery.getMasterKeys();
                    if (valueSet == null) {
                        valueSet = new DimensionValueSet();
                    }
                    valueSet.setValue(dimStr, Arrays.asList(enityListStr.split(";")));
                    entityQuery.setMasterKeys(valueSet);
                }
                IEntityTable reader = this.entityQueryHelper.buildEntityTable(entityQuery, this.condition.getContext().getFormSchemeKey(), true);
                for (IEntityRow row : reader.getAllRows()) {
                    mainDimData.addMainValue(row.getEntityKeyData());
                    if (!dimStr.equals(this.masterDimName)) continue;
                    this.masterEntityDic.put(row.getEntityKeyData(), row);
                }
            } else {
                ArrayList<String> expressions = new ArrayList<String>();
                expressions.add("SYS_UNITCODE");
                expressions.add("SYS_UNITTITLE");
                int idxEntityCode = expressions.indexOf("SYS_UNITCODE");
                int idxEntityNameCode = expressions.indexOf("SYS_UNITTITLE");
                DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)this.condition.getContext().getDimensionSet());
                EntityViewDefine entityViewDefine = this.entityQueryHelper.getEntityView(formSchemeDefine.getKey(), viewKey);
                IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(entityViewDefine, periodData.get(0));
                if (!StringUtils.isEmpty((String)enityListStr)) {
                    DimensionValueSet valueSet = entityQuery.getMasterKeys();
                    if (valueSet == null) {
                        valueSet = new DimensionValueSet();
                    }
                    valueSet.setValue(dimStr, Arrays.asList(enityListStr.split(";")));
                    entityQuery.setMasterKeys(valueSet);
                }
                IEntityTable reader = this.entityQueryHelper.buildEntityTable(entityQuery, this.condition.getContext().getFormSchemeKey(), true);
                ArrayList<String> mainValueList = new ArrayList<String>();
                for (IEntityRow iEntityRow : reader.getAllRows()) {
                    mainValueList.add(iEntityRow.getEntityKeyData());
                    this.masterEntityDic.put(iEntityRow.getEntityKeyData(), iEntityRow);
                }
                dimensionValueSet.setValue(dimStr, mainValueList);
                JtableContext jtableContext = new JtableContext(this.condition.getContext());
                jtableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet));
                com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = this.tableDataEngineService.getExecutorContext(jtableContext);
                IExpressionEvaluator evaluator = this.iDataAccessProvider.newExpressionEvaluator();
                for (String fml : this.enumFilterCondition.getAllFilterFml()) {
                    expressions.add(fml);
                    this.dicFilterFdCode.put(fml, "FD" + fmlSize++);
                }
                Map result = evaluator.evalBatch(expressions, executorContext, dimensionValueSet);
                mainDimData.setExTFdSize(fmlSize);
                for (String key : result.keySet()) {
                    mainDimData.addMainValue((String)((Object[])result.get(key))[idxEntityCode]);
                    for (int i = 0; i < fmlSize; ++i) {
                        String fml = (String)expressions.get(idxEntityNameCode + i + 1);
                        int fmlResultIndex = expressions.indexOf(fml);
                        mainDimData.addExtValue(i, (Boolean)((Object[])result.get(key))[fmlResultIndex] != false ? "\u662f" : "\u5426");
                    }
                }
            }
            tmpTableA = this.tmpTableUtils.createTempTable(fmlSize);
            this.tmpTableUtils.prepareTempTableData(tmpTableA, mainDimData);
            this.tmpTableDic.put(dimStr, new String[]{tmpTableA.getTableName(), this.tmpTableUtils.getFName()});
            tmpTables.add(tmpTableA);
        }
        return tmpTables;
    }

    public void setMonitor(IMonitor monitor) {
        this.monitor = monitor;
    }

    public int getErrorCount() {
        return this.errCount.get();
    }

    private int getEntityOrder(IEntityRow entityRow) {
        String order = entityRow.getEntityOrder().toString();
        int idx = order.indexOf(".");
        if (idx > 0) {
            order = order.substring(0, idx);
        }
        if (order.length() > 9) {
            order = order.substring(order.length() - 9);
        }
        return Integer.parseInt(order);
    }
}

