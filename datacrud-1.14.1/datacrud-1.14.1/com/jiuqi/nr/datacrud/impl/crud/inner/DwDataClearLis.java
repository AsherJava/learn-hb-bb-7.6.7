/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.datacrud.impl.crud.inner;

import com.jiuqi.nr.datacrud.impl.crud.inner.BaseDwDataClearLis;
import com.jiuqi.nr.datacrud.spi.IDwClearChangeDataListener;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class DwDataClearLis
extends BaseDwDataClearLis
implements IDwClearChangeDataListener {
    @Autowired
    protected NrdbHelper nrdbHelper;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;
    @Autowired
    private DataModelService dataModelService;
    protected static final String DW_DEL_SQL = "delete from %s where %s =?";
    protected static final String DW_PERIOD_DEL_SQL = "delete from %s where %s =? and %s >= ?";
    protected static final String DW_PERIOD_DEL_SQ_1 = "delete from %s where %s =? and %s <= ?";
    protected static final String DW_PERIOD_DEL_SQ_2 = "delete from %s where %s =? and %s >= ? and %s <= ?";

    @Deprecated
    public DwDataClearLis(IRuntimeDataSchemeService runtimeDataSchemeService, JdbcTemplate jdbcTemplate) {
        this.runtimeDataSchemeService = runtimeDataSchemeService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Deprecated
    public DwDataClearLis(IRuntimeDataSchemeService runtimeDataSchemeService, NrdbHelper nrdbHelper, JdbcTemplate jdbcTemplate) {
        this.runtimeDataSchemeService = runtimeDataSchemeService;
        this.jdbcTemplate = jdbcTemplate;
        this.nrdbHelper = nrdbHelper;
    }

    public DwDataClearLis() {
    }

    public void setNrdbHelper(NrdbHelper nrdbHelper) {
        this.nrdbHelper = nrdbHelper;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setDataAccessProvider(INvwaDataAccessProvider dataAccessProvider) {
        this.dataAccessProvider = dataAccessProvider;
    }

    public void setDataModelService(DataModelService dataModelService) {
        this.dataModelService = dataModelService;
    }

    @Override
    protected void doClear(Set<String> tableNames, List<String> entityIds, String startPeriod, String endPeriod) {
        if (this.nrdbHelper != null && this.nrdbHelper.isEnableNrdb()) {
            this.nrdbDoClear(tableNames, entityIds, startPeriod, endPeriod);
        } else {
            this.dbDoClear(tableNames, entityIds, startPeriod, endPeriod);
        }
    }

    private void dbDoClear(Set<String> tableNames, List<String> entityIds, String startPeriod, String endPeriod) {
        ArrayList<Object[]> argsList = null;
        for (String tableName : tableNames) {
            try {
                Object[] args;
                String sql;
                if (startPeriod == null && endPeriod == null) {
                    sql = String.format(DW_DEL_SQL, tableName, "MDCODE");
                    if (argsList == null) {
                        argsList = new ArrayList<Object[]>(entityIds.size());
                        for (String entityKeyData : entityIds) {
                            args = new Object[]{entityKeyData};
                            argsList.add(args);
                        }
                    }
                } else if (startPeriod != null && endPeriod != null) {
                    sql = String.format(DW_PERIOD_DEL_SQ_2, tableName, "MDCODE", "DATATIME", "DATATIME");
                    if (argsList == null) {
                        argsList = new ArrayList(entityIds.size());
                        for (String entityKeyData : entityIds) {
                            args = new Object[]{entityKeyData, startPeriod, endPeriod};
                            argsList.add(args);
                        }
                    }
                } else if (endPeriod == null) {
                    sql = String.format(DW_PERIOD_DEL_SQL, tableName, "MDCODE", "DATATIME");
                    if (argsList == null) {
                        argsList = new ArrayList(entityIds.size());
                        for (String entityKeyData : entityIds) {
                            args = new Object[]{entityKeyData, startPeriod};
                            argsList.add(args);
                        }
                    }
                } else {
                    sql = String.format(DW_PERIOD_DEL_SQ_1, tableName, "MDCODE", "DATATIME");
                    if (argsList == null) {
                        argsList = new ArrayList(entityIds.size());
                        for (String entityKeyData : entityIds) {
                            args = new Object[]{entityKeyData, endPeriod};
                            argsList.add(args);
                        }
                    }
                }
                int[] deleteCounts = this.jdbcTemplate.batchUpdate(sql, argsList);
                StringBuilder builder = new StringBuilder();
                for (int deleteCount : deleteCounts) {
                    builder.append(deleteCount).append(",");
                }
                this.LOGGER.info("{}\u62a5\u8868\u6570\u636e\u5220\u9664\u8bb0\u5f55", (Object)tableName);
                this.LOGGER.info("{} \n {}", (Object)String.join((CharSequence)",", entityIds), (Object)builder);
            }
            catch (Exception e) {
                this.LOGGER.warn("\u5355\u4f4d{}\u7684{}\u62a5\u8868\u6570\u636e\u5220\u9664\u5931\u8d25", String.join((CharSequence)",", entityIds), tableName, e);
            }
        }
    }

    private void nrdbDoClear(Set<String> tableNames, List<String> entityIds, String startPeriod, String endPeriod) {
        for (String tableName : tableNames) {
            NvwaQueryModel queryModel = new NvwaQueryModel();
            TableModelDefine table = this.dataModelService.getTableModelDefineByName(tableName);
            if (table == null) continue;
            ColumnModelDefine unit = null;
            ColumnModelDefine period = null;
            List allColumns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
            for (ColumnModelDefine columnModel : allColumns) {
                if ("DATATIME".equals(columnModel.getName())) {
                    period = columnModel;
                } else if ("MDCODE".equals(columnModel.getName())) {
                    unit = columnModel;
                }
                queryModel.getColumns().add(new NvwaQueryColumn(columnModel));
            }
            if (unit == null || period == null) continue;
            queryModel.getColumnFilters().put(unit, entityIds);
            ArrayList<String> filters = new ArrayList<String>();
            if (startPeriod != null) {
                filters.add(tableName + "[" + period.getName() + "] >= '" + startPeriod + "'");
            }
            if (endPeriod != null) {
                filters.add(tableName + "[" + period.getName() + "] <= '" + endPeriod + "'");
            }
            if (!filters.isEmpty()) {
                queryModel.setFilter(String.join((CharSequence)" AND ", filters));
            }
            try {
                INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
                DataAccessContext context = new DataAccessContext(this.dataModelService);
                INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
                iNvwaDataUpdator.deleteAll();
                iNvwaDataUpdator.commitChanges(context);
                this.LOGGER.info("{}\u62a5\u8868\u6570\u636e\u5220\u9664\u8bb0\u5f55,\u5220\u9664\u8303\u56f4\u5355\u4f4d {} \u5f00\u59cb\u65f6\u671f {} \u7ed3\u675f\u65f6\u671f {} ", tableName, String.join((CharSequence)",", entityIds), startPeriod, endPeriod);
            }
            catch (Exception e) {
                this.LOGGER.warn("{}\u62a5\u8868\u6570\u636e\u5220\u9664\u5931\u8d25,\u5220\u9664\u8303\u56f4\u5355\u4f4d {} \u5f00\u59cb\u65f6\u671f {} \u7ed3\u675f\u65f6\u671f {} ", tableName, String.join((CharSequence)",", entityIds), startPeriod, endPeriod);
            }
        }
    }
}

