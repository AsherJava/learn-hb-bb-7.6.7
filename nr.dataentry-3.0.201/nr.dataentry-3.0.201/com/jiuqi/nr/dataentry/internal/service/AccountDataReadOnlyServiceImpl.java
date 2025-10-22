/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  org.apache.shiro.util.Assert
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataentry.internal.service.util.AccountRollBackTableUtil;
import com.jiuqi.nr.dataentry.readwrite.bean.BatchAccountDataReadWriteResult;
import com.jiuqi.nr.dataentry.service.IAccountDataReadOnlyService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class AccountDataReadOnlyServiceImpl
implements IAccountDataReadOnlyService {
    private static final Logger logger = LoggerFactory.getLogger(AccountDataReadOnlyServiceImpl.class);
    @Autowired
    private IRunTimeViewController controller;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AccountRollBackTableUtil accountRollBackTableUtil;
    @Autowired
    private IEntityMetaService metaService;
    public static final String TN_ACCOUNT_RPT_SUFFIX = "_RPT";
    public static final String TN_ACCOUNT_HIS_SUFFIX = "_HIS";
    private static final String MD_ORG_START = "MD_ORG";
    private static final ThreadLocal<Map<String, String>> dimFields = ThreadLocal.withInitial(() -> new HashMap());
    private static final RowMapper<DimensionValueSet> rowMapper = (rs, rowNum) -> {
        Map<String, String> dimensionFieldsMap = dimFields.get();
        DimensionValueSet rowKey = new DimensionValueSet();
        for (Map.Entry<String, String> dimMap : dimensionFieldsMap.entrySet()) {
            rowKey.setValue(dimMap.getKey(), (Object)rs.getString(dimMap.getValue()));
        }
        return rowKey;
    };

    @Override
    public Boolean readOnly(DimensionValueSet masterKey, String formKey) {
        Assert.notNull((Object)masterKey, (String)"masterKey must not be null!");
        Assert.notNull((Object)formKey, (String)"formKey must not be null!");
        if (!this.isAcctTable(formKey)) {
            return false;
        }
        String actTableName = this.getAccTableName(formKey);
        if (actTableName == null) {
            return false;
        }
        return this.readOnlyQuery(masterKey, actTableName) || this.readOnlyQueryHis(masterKey, actTableName);
    }

    @Override
    public Map<DimensionValueSet, Boolean> batchReadOnly(DimensionValueSet masterKey, String formKey) {
        List<DimensionValueSet> res = this.getResDims(masterKey, formKey);
        List splitDimensions = DimensionValueSetUtil.getDimensionSetList((DimensionValueSet)masterKey);
        return this.parseResult(splitDimensions, res);
    }

    /*
     * Exception decompiling
     */
    private List<DimensionValueSet> getResDims(DimensionValueSet masterKey, String formKey) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public List<DimensionValueSet> batchReadOnlyDimensions(DimensionValueSet masterKey, String formKey) {
        List<DimensionValueSet> res = this.getResDims(masterKey, formKey);
        List splitDimensions = DimensionValueSetUtil.getDimensionSetList((DimensionValueSet)masterKey);
        return this.parseResultToReadOnly(splitDimensions, res);
    }

    /*
     * Exception decompiling
     */
    @Override
    public List<BatchAccountDataReadWriteResult> batchReadOnlyAccResult(DimensionValueSet masterKey, List<String> formKeys) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private boolean isAcctTable(String formKey) {
        FormDefine formDefine = this.controller.queryFormById(formKey);
        if (formDefine == null) {
            return false;
        }
        return FormType.FORM_TYPE_ACCOUNT.equals((Object)formDefine.getFormType());
    }

    private boolean needTempTable(DimensionValueSet masterKey, String unitDim) {
        int size = DataEngineUtil.getMaxInSize((IDatabase)DatabaseInstance.getDatabase());
        Object unitValues = masterKey.getValue(unitDim);
        if (unitValues == null) {
            return false;
        }
        if (unitValues instanceof Collection) {
            Collection unitIds = (Collection)unitValues;
            return unitIds.size() > size;
        }
        return false;
    }

    private Map<DimensionValueSet, Boolean> parseResult(List<DimensionValueSet> dimensionSplit, List<DimensionValueSet> res) {
        HashMap<DimensionValueSet, Boolean> readOnlyMap = new HashMap<DimensionValueSet, Boolean>();
        List<DimensionValueSet> noAccess = dimensionSplit.stream().filter(e -> res.contains(e)).collect(Collectors.toList());
        dimensionSplit.removeAll(noAccess);
        noAccess.forEach(e -> readOnlyMap.put((DimensionValueSet)e, false));
        dimensionSplit.forEach(e -> readOnlyMap.put((DimensionValueSet)e, true));
        return readOnlyMap;
    }

    private List<DimensionValueSet> parseResultToReadOnly(List<DimensionValueSet> dimensionSplit, List<DimensionValueSet> res) {
        List<DimensionValueSet> noAccess = dimensionSplit.stream().filter(e -> res.contains(e)).collect(Collectors.toList());
        return noAccess;
    }

    private boolean readOnlyQuery(DimensionValueSet masterKey, String actTableName) {
        DimensionSet dimesions = masterKey.getDimensionSet();
        StringBuffer sql = new StringBuffer();
        sql.append("select ").append(" count(1) from ").append(actTableName);
        sql.append(" where ");
        Object[] args = new Object[dimesions.size()];
        boolean addFalag = false;
        for (int index = 0; index < dimesions.size(); ++index) {
            String dimesion = dimesions.get(index);
            String dimensionField = this.parseDimField(dimesion);
            if (addFalag) {
                sql.append(" and ");
            }
            if (dimesion.equals("DATATIME")) {
                sql.append("VALIDDATATIME>?");
            } else {
                sql.append(dimensionField).append("=?");
            }
            args[index] = masterKey.getValue(dimesion);
            addFalag = true;
        }
        int count = (Integer)this.jdbcTemplate.queryForObject(sql.toString(), Integer.class, args);
        return count > 0;
    }

    private boolean readOnlyQueryHis(DimensionValueSet masterKey, String actTableName) {
        String hisTableName = this.accountRollBackTableUtil.getAccHiTableName(actTableName);
        DimensionSet dimesions = masterKey.getDimensionSet();
        StringBuffer sql = new StringBuffer();
        sql.append("select ").append(" count(1) from ").append(hisTableName);
        sql.append(" where ");
        Object[] args = new Object[dimesions.size()];
        boolean addFalag = false;
        for (int index = 0; index < dimesions.size(); ++index) {
            String dimesion = dimesions.get(index);
            String dimensionField = this.parseDimField(dimesion);
            if (addFalag) {
                sql.append(" and ");
            }
            if (dimesion.equals("DATATIME")) {
                sql.append("INVALIDDATATIME>?");
            } else {
                sql.append(dimensionField).append("=?");
            }
            args[index] = masterKey.getValue(dimesion);
            addFalag = true;
        }
        int count = (Integer)this.jdbcTemplate.queryForObject(sql.toString(), Integer.class, args);
        return count > 0;
    }

    private List<DimensionValueSet> batchReadOnlyQuery(DimensionValueSet masterKey, String tableName, boolean needTemp, ITempTable tempTable) {
        if (Objects.isNull(tableName)) {
            return Collections.emptyList();
        }
        try {
            String tempTableName = null;
            String tempKey = null;
            if (needTemp) {
                tempTableName = tempTable.getTableName();
                tempKey = ((LogicField)tempTable.getMeta().getLogicFields().get(0)).getFieldName();
            }
            DimensionSet dimesions = masterKey.getDimensionSet();
            StringBuffer selectSql = new StringBuffer();
            StringBuffer joinSql = new StringBuffer();
            StringBuffer sqlWhere = new StringBuffer();
            StringBuffer sqlGroupBy = new StringBuffer();
            selectSql.append("select ").append(" count(1)");
            boolean addFalag = false;
            ArrayList<Object> args = new ArrayList<Object>();
            HashMap<String, String> dimensionFieldsMap = new HashMap<String, String>();
            for (int index = 0; index < dimesions.size(); ++index) {
                String dimesion = dimesions.get(index);
                Object dimValue = masterKey.getValue(dimesion);
                boolean needIn = dimValue instanceof List;
                String dimesionField = this.parseDimField(dimesion);
                if (addFalag) {
                    sqlWhere.append(" and ");
                    if (!dimesion.equals("DATATIME")) {
                        selectSql.append(",");
                        if (sqlGroupBy.length() > 0) {
                            sqlGroupBy.append(",");
                        }
                    }
                }
                if (dimesion.equals("DATATIME")) {
                    sqlWhere.append("VALIDDATATIME>?");
                    selectSql.append(",MAX(VALIDDATATIME) as ".concat("DATATIME"));
                } else if (needTemp && dimesionField != null && dimesionField.equals("MDCODE")) {
                    joinSql.append(" inner join ").append(tempTableName);
                    joinSql.append(" on ").append(String.format("%s.%s=%s.%s ", tableName, dimesionField, tempTableName, tempKey));
                    sqlGroupBy.append(dimesionField);
                    selectSql.append(dimesionField);
                } else {
                    sqlWhere.append(dimesionField);
                    if (needIn) {
                        List dimValueList = (List)dimValue;
                        args.addAll(dimValueList);
                        this.appendArgValue(sqlWhere, dimValue);
                    } else {
                        sqlWhere.append("=?");
                    }
                    selectSql.append(dimesionField);
                    sqlGroupBy.append(dimesionField);
                }
                if (!needIn) {
                    args.add(dimValue);
                }
                dimensionFieldsMap.put(dimesion, dimesionField);
                addFalag = true;
            }
            if (selectSql.lastIndexOf(",") == selectSql.length() - 1) {
                selectSql.setLength(selectSql.length() - 1);
            }
            if (sqlWhere.lastIndexOf("and") == sqlWhere.length() - 4) {
                sqlWhere.setLength(sqlWhere.length() - 4);
            }
            selectSql.append(" from ").append(tableName);
            if (needTemp) {
                selectSql.append(joinSql);
            }
            selectSql.append(" where ").append(sqlWhere);
            selectSql.append(" group by ").append(sqlGroupBy);
            dimFields.set(dimensionFieldsMap);
            List res = this.jdbcTemplate.query(selectSql.toString(), rowMapper, args.toArray());
            dimFields.remove();
            return res;
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5931\u8d25", e);
            throw new RuntimeException(e);
        }
    }

    private static List<Object[]> getRecords(Object values) {
        ArrayList<Object[]> records = new ArrayList<Object[]>();
        if (values != null) {
            if (values instanceof Collection) {
                Collection valueList = (Collection)values;
                for (Object s : valueList) {
                    Object[] args = new Object[]{s};
                    records.add(args);
                }
            } else {
                Object[] args = new Object[]{values};
                records.add(args);
            }
        }
        return records;
    }

    private List<DimensionValueSet> batchReadOnlyHisQuery(DimensionValueSet masterKey, String tableName, boolean needTemp, ITempTable tempTable) {
        if (Objects.isNull(tableName)) {
            return Collections.emptyList();
        }
        try {
            String tempTableName = null;
            String tempKey = null;
            if (needTemp) {
                tempTableName = tempTable.getTableName();
                tempKey = ((LogicField)tempTable.getMeta().getLogicFields().get(0)).getFieldName();
            }
            DimensionSet dimesions = masterKey.getDimensionSet();
            StringBuffer selectSql = new StringBuffer();
            StringBuffer joinSql = new StringBuffer();
            StringBuffer sqlWhere = new StringBuffer();
            StringBuffer sqlGroupBy = new StringBuffer();
            selectSql.append("select ").append(" count(1)");
            boolean addFalag = false;
            ArrayList<Object> args = new ArrayList<Object>();
            HashMap<String, String> dimensionFieldsMap = new HashMap<String, String>();
            for (int index = 0; index < dimesions.size(); ++index) {
                String dimesion = dimesions.get(index);
                Object dimValue = masterKey.getValue(dimesion);
                boolean needIn = dimValue instanceof List;
                String dimesionField = this.parseDimField(dimesion);
                if (addFalag) {
                    sqlWhere.append(" and ");
                    if (!dimesion.equals("DATATIME")) {
                        selectSql.append(",");
                        if (sqlGroupBy.length() > 0) {
                            sqlGroupBy.append(",");
                        }
                    }
                }
                if (dimesion.equals("DATATIME")) {
                    sqlWhere.append("INVALIDDATATIME>?");
                    selectSql.append(",MAX(INVALIDDATATIME) as ".concat("DATATIME"));
                } else if (needTemp && dimesionField != null && dimesionField.equals("MDCODE")) {
                    joinSql.append(" inner join ").append(tempTableName);
                    joinSql.append(" on ").append(String.format("%s.%s=%s.%s ", tableName, dimesionField, tempTableName, tempKey));
                    sqlGroupBy.append(dimesionField);
                    selectSql.append(dimesionField);
                } else {
                    sqlWhere.append(dimesionField);
                    if (needIn) {
                        List dimValueList = (List)dimValue;
                        args.addAll(dimValueList);
                        this.appendArgValue(sqlWhere, dimValue);
                    } else {
                        sqlWhere.append("=?");
                    }
                    selectSql.append(dimesionField);
                    sqlGroupBy.append(dimesionField);
                }
                if (!needIn) {
                    args.add(dimValue);
                }
                dimensionFieldsMap.put(dimesion, dimesionField);
                addFalag = true;
            }
            if (selectSql.lastIndexOf(",") == selectSql.length() - 1) {
                selectSql.setLength(selectSql.length() - 1);
            }
            if (sqlWhere.lastIndexOf("and") == sqlWhere.length() - 4) {
                sqlWhere.setLength(sqlWhere.length() - 4);
            }
            selectSql.append(" from ").append(tableName);
            if (needTemp) {
                selectSql.append(joinSql);
            }
            selectSql.append(" where ").append(sqlWhere);
            selectSql.append(" group by ").append(sqlGroupBy);
            dimFields.set(dimensionFieldsMap);
            List res = this.jdbcTemplate.query(selectSql.toString(), rowMapper, args.toArray());
            dimFields.remove();
            return res;
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5931\u8d25", e);
            throw new RuntimeException(e);
        }
    }

    private void appendArgValue(StringBuffer sql, Object value) {
        List values = (List)value;
        boolean addFlag = false;
        sql.append(" in (");
        for (Object o : values) {
            if (addFlag) {
                sql.append(",");
            }
            sql.append("?");
            addFlag = true;
        }
        sql.append(") ");
    }

    private String getAccTableName(String formKey) {
        String accTableName = null;
        List region = this.controller.getAllRegionsInForm(formKey);
        if ((region = region.stream().filter(e -> !DataRegionKind.DATA_REGION_SIMPLE.equals((Object)e.getRegionKind())).collect(Collectors.toList())).isEmpty()) {
            return accTableName;
        }
        List fieldKeys = this.controller.getFieldKeysInRegion(((DataRegionDefine)region.get(0)).getKey());
        String[] fieldKeyAry = fieldKeys.toArray(new String[fieldKeys.size()]);
        List dataFieldDeployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(fieldKeyAry);
        Set<String> tableNames = dataFieldDeployInfos.stream().map(e -> e.getTableName()).collect(Collectors.toSet());
        Optional<String> accTable = this.findAcctTableName(tableNames);
        if (accTable.isPresent()) {
            accTableName = accTable.get();
        }
        return accTableName;
    }

    private String getUnitDim(String formKey) {
        FormDefine formDefine = this.controller.queryFormById(formKey);
        String formSchemeKey = formDefine.getFormScheme();
        FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.controller.queryTaskDefine(formScheme.getTaskKey());
        String dw = taskDefine.getDw();
        String dimName = this.metaService.getDimensionName(dw);
        return dimName;
    }

    private Optional<String> findAcctTableName(Set<String> tableNames) {
        Optional<String> tableName = tableNames.stream().filter(e -> !e.endsWith(TN_ACCOUNT_RPT_SUFFIX) && !e.endsWith(TN_ACCOUNT_HIS_SUFFIX)).findFirst();
        return tableName;
    }

    private String parseDimField(String dimension) {
        if (StringUtils.isEmpty((String)dimension)) {
            return null;
        }
        if (dimension.startsWith(MD_ORG_START)) {
            return "MDCODE";
        }
        if (dimension.equals("DATATIME")) {
            return "DATATIME";
        }
        if (dimension.equals("RECORDKEY")) {
            return "SBID";
        }
        return dimension;
    }

    private static /* synthetic */ void lambda$batchReadOnlyAccResult$3(List result, String form, DimensionValueSet dim) {
        result.add(new BatchAccountDataReadWriteResult(dim, form));
    }

    private /* synthetic */ void lambda$batchReadOnlyAccResult$2(DimensionValueSet masterKey, boolean needTemp, ITempTable oneKeyTempTable, Map res, String fk) {
        String actTableName = this.getAccTableName(fk);
        if (actTableName == null) {
            return;
        }
        List<DimensionValueSet> resByForm = this.batchReadOnlyQuery(masterKey, actTableName, needTemp, oneKeyTempTable);
        List<DimensionValueSet> resHisByForm = this.batchReadOnlyHisQuery(masterKey, this.accountRollBackTableUtil.getAccHiTableName(actTableName), needTemp, oneKeyTempTable);
        resByForm.addAll(resHisByForm);
        res.put(fk, resByForm);
    }

    private /* synthetic */ boolean lambda$batchReadOnlyAccResult$1(String formKey) {
        return !this.isAcctTable(formKey);
    }
}

