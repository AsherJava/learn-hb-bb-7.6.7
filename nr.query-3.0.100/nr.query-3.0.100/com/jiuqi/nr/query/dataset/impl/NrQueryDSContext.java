/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.query.dataset.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

public class NrQueryDSContext
implements IContext {
    private String versionPeriod;
    private DimensionValueSet masterKeys;
    private ExecutorContext executorContext;
    private Map<String, EntityViewDefine> dimEntityMap = new HashMap<String, EntityViewDefine>();
    private Map<String, IEntityTable> entityTableMap = new HashMap<String, IEntityTable>();
    private PeriodType periodType;
    private Map<String, String> tableNameMap = new HashMap<String, String>();
    private Map<String, String> oldTableNameMap = new HashMap<String, String>();
    private Map<String, String> fieldNameMap = new HashMap<String, String>();
    private Map<String, TableModelRunInfo> allTableInfos = new HashMap<String, TableModelRunInfo>();
    private JdbcTemplate jdbcTemplate;

    public NrQueryDSContext(ExecutorContext executorContext) {
        this.executorContext = executorContext;
    }

    public String getVersionPeriod() {
        return this.versionPeriod;
    }

    public void setVersionPeriod(String versionPeriod) {
        this.versionPeriod = versionPeriod;
    }

    public DimensionValueSet getMasterKeys() {
        return this.masterKeys;
    }

    public void setMasterKeys(DimensionValueSet masterKeys) {
        this.masterKeys = masterKeys;
    }

    public Map<String, EntityViewDefine> getDimEntityMap() {
        return this.dimEntityMap;
    }

    public Map<String, IEntityTable> getEntityTableMap() {
        return this.entityTableMap;
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }

    public PeriodType getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public Map<String, String> getTableNameMap() {
        return this.tableNameMap;
    }

    public Map<String, String> getFieldNameMap() {
        return this.fieldNameMap;
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, String> getOldTableNameMap() {
        return this.oldTableNameMap;
    }

    public Map<String, TableModelRunInfo> getAllTableInfos() {
        return this.allTableInfos;
    }
}

