/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.DataModelConstant;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class QueryModel {
    private String tableName;
    private Map<String, ColumnModelDefine> columnMap = new HashMap<String, ColumnModelDefine>();

    public String getTableName() {
        return this.tableName;
    }

    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ColumnModelDefine getColumn(String columnName) {
        return this.columnMap.get(columnName);
    }

    protected void addColumn(ColumnModelDefine columnModel) {
        this.columnMap.put(columnModel.getName(), columnModel);
    }

    protected void addColumns(Collection<ColumnModelDefine> columnModels) {
        for (ColumnModelDefine columnModel : columnModels) {
            this.columnMap.put(columnModel.getName(), columnModel);
        }
    }

    public int getColumnCount() {
        return this.columnMap.size();
    }

    public static class ProcessOperationQueryModel
    extends QueryModel {
        public ColumnModelDefine getIdColumn() {
            return this.getColumn("OPT_ID");
        }

        public ColumnModelDefine getIstIdColumn() {
            return this.getColumn("IST_ID");
        }

        public ColumnModelDefine getFromNodeColumn() {
            return this.getColumn("OPT_FROMNODE");
        }

        public ColumnModelDefine getActionColumn() {
            return this.getColumn("OPT_ACTION");
        }

        public ColumnModelDefine getToNodeColumn() {
            return this.getColumn("OPT_TONODE");
        }

        public ColumnModelDefine getNewStatusColumn() {
            return this.getColumn("OPT_NEWSTATUS");
        }

        public ColumnModelDefine getOperateTimeColumn() {
            return this.getColumn("OPT_TIME");
        }

        public ColumnModelDefine getOperateUserColumn() {
            return this.getColumn("OPT_USER");
        }

        public ColumnModelDefine getOperateIdentityColumn() {
            return this.getColumn("OPT_IDENTITY");
        }

        public ColumnModelDefine getCommentColumn() {
            return this.getColumn("OPT_COMMENT");
        }

        public ColumnModelDefine getOperateTypeColumn() {
            return this.getColumn("OPT_TYPE");
        }

        public ColumnModelDefine getForceReportColumn() {
            return this.getColumn("OPT_FORCEREPORT");
        }

        public static ProcessOperationQueryModel build(DataModelService dataModelService, FormSchemeDefine formScheme) {
            String tableName = DataModelConstant.getHistoryTableName(formScheme);
            return ProcessOperationQueryModel.build(dataModelService, formScheme, tableName);
        }

        public static ProcessOperationQueryModel build(DataModelService dataModelService, FormSchemeDefine formScheme, String tableName) {
            TableModelDefine table = dataModelService.getTableModelDefineByCode(tableName);
            if (table == null) {
                return null;
            }
            ProcessOperationQueryModel queryModel = new ProcessOperationQueryModel();
            queryModel.setTableName(tableName);
            HashMap<String, ColumnModelDefine> columns = new HashMap<String, ColumnModelDefine>(16);
            for (ColumnModelDefine col : dataModelService.getColumnModelDefinesByTable(table.getID())) {
                columns.put(col.getName(), col);
            }
            ColumnModelDefine col = (ColumnModelDefine)columns.get("OPT_ID");
            if (col == null) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5OPT_ID");
            }
            queryModel.addColumn(col);
            col = (ColumnModelDefine)columns.get("IST_ID");
            if (col == null) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5IST_ID");
            }
            queryModel.addColumn(col);
            col = (ColumnModelDefine)columns.get("OPT_FROMNODE");
            if (col == null) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5OPT_FROMNODE");
            }
            queryModel.addColumn(col);
            col = (ColumnModelDefine)columns.get("OPT_ACTION");
            if (col == null) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5OPT_ACTION");
            }
            queryModel.addColumn(col);
            col = (ColumnModelDefine)columns.get("OPT_TONODE");
            if (col == null) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5OPT_TONODE");
            }
            queryModel.addColumn(col);
            col = (ColumnModelDefine)columns.get("OPT_NEWSTATUS");
            if (col == null) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5OPT_NEWSTATUS");
            }
            queryModel.addColumn(col);
            col = (ColumnModelDefine)columns.get("OPT_TIME");
            if (col == null) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5OPT_TIME");
            }
            queryModel.addColumn(col);
            col = (ColumnModelDefine)columns.get("OPT_USER");
            if (col == null) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5OPT_USER");
            }
            queryModel.addColumn(col);
            col = (ColumnModelDefine)columns.get("OPT_IDENTITY");
            if (col == null) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5OPT_IDENTITY");
            }
            queryModel.addColumn(col);
            col = (ColumnModelDefine)columns.get("OPT_COMMENT");
            if (col == null) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5OPT_COMMENT");
            }
            queryModel.addColumn(col);
            col = (ColumnModelDefine)columns.get("OPT_TYPE");
            if (col == null) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5OPT_TYPE");
            }
            queryModel.addColumn(col);
            col = (ColumnModelDefine)columns.get("OPT_FORCEREPORT");
            if (col == null) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5OPT_FORCEREPORT");
            }
            queryModel.addColumn(col);
            return queryModel;
        }
    }

    public static class ProcessInstanceQueryModel
    extends QueryModel {
        private List<DimensionColumnModelDefine> dimensionColumns = new ArrayList<DimensionColumnModelDefine>(4);
        private ColumnModelDefine formOrFormGroupColumn;
        private boolean includeFormKeyColumn;
        private boolean includeFormGroupKeyColumn;
        private List<String> dimensionNames = new ArrayList<String>(4);

        public ColumnModelDefine getIdColumn() {
            return this.getColumn("IST_ID");
        }

        public ColumnModelDefine getDefinitionIdColumn() {
            return this.getColumn("IST_DEFINITIONID");
        }

        public ColumnModelDefine getStartTimeColumn() {
            return this.getColumn("IST_STARTTIME");
        }

        public ColumnModelDefine getStartUserColumn() {
            return this.getColumn("IST_STARTUSER");
        }

        public ColumnModelDefine getUpdateTimeColumn() {
            return this.getColumn("IST_UPDATETIME");
        }

        public ColumnModelDefine getTaskIdColumn() {
            return this.getColumn("IST_TASKID");
        }

        public ColumnModelDefine getNodeColumn() {
            return this.getColumn("IST_NODE");
        }

        public ColumnModelDefine getStatusColumn() {
            return this.getColumn("IST_STATUS");
        }

        public ColumnModelDefine getLockColumn() {
            return this.getColumn("IST_LOCK");
        }

        public ColumnModelDefine getLastOperationIdColumn() {
            return this.getColumn("IST_LASTOPTIONID");
        }

        public List<DimensionColumnModelDefine> getDimensionColumns() {
            return this.dimensionColumns;
        }

        public ColumnModelDefine getFormOrFormGroupColumn() {
            return this.formOrFormGroupColumn;
        }

        public boolean isIncludeFormKeyColumn() {
            return this.includeFormKeyColumn;
        }

        public boolean isIncludeFormGroupKeyColumn() {
            return this.includeFormGroupKeyColumn;
        }

        public List<String> getDimensionNames() {
            return this.dimensionNames;
        }

        public static ProcessInstanceQueryModel build(DataModelService dataModelService, IEntityMetaService entityMetaService, IRuntimeDataSchemeService dataSchemeService, TaskDefine task, FormSchemeDefine formScheme) {
            ProcessInstanceQueryModelBuilder builder = new ProcessInstanceQueryModelBuilder(dataModelService, entityMetaService, dataSchemeService);
            String tableName = DataModelConstant.getInstanceTableName(formScheme);
            return builder.build(task, tableName);
        }

        public static ProcessInstanceQueryModel build(DataModelService dataModelService, IEntityMetaService entityMetaService, IRuntimeDataSchemeService dataSchemeService, TaskDefine task, FormSchemeDefine formScheme, String tableName) {
            ProcessInstanceQueryModelBuilder builder = new ProcessInstanceQueryModelBuilder(dataModelService, entityMetaService, dataSchemeService);
            return builder.build(task, tableName);
        }

        private static class ProcessInstanceQueryModelBuilder {
            private final DataModelService dataModelService;
            private final IEntityMetaService entityMetaService;
            private final IRuntimeDataSchemeService dataSchemeService;

            public ProcessInstanceQueryModelBuilder(DataModelService dataModelService, IEntityMetaService entityMetaService, IRuntimeDataSchemeService dataSchemeService) {
                this.dataModelService = dataModelService;
                this.entityMetaService = entityMetaService;
                this.dataSchemeService = dataSchemeService;
            }

            public ProcessInstanceQueryModel build(TaskDefine task, String tableName) {
                TableModelDefine table = this.dataModelService.getTableModelDefineByCode(tableName);
                if (table == null) {
                    return null;
                }
                ProcessInstanceQueryModel queryModel = new ProcessInstanceQueryModel();
                queryModel.setTableName(tableName);
                queryModel.addColumns(this.dataModelService.getColumnModelDefinesByTable(table.getID()));
                ColumnModelDefine col = queryModel.getIdColumn();
                if (col == null) {
                    throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5IST_ID");
                }
                col = queryModel.getDefinitionIdColumn();
                if (col == null) {
                    throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5IST_DEFINITIONID");
                }
                col = queryModel.getStartTimeColumn();
                if (col == null) {
                    throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5IST_STARTTIME");
                }
                col = queryModel.getStartUserColumn();
                if (col == null) {
                    throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5IST_STARTUSER");
                }
                col = queryModel.getUpdateTimeColumn();
                if (col == null) {
                    throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5IST_UPDATETIME");
                }
                col = queryModel.getTaskIdColumn();
                if (col == null) {
                    throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5IST_TASKID");
                }
                col = queryModel.getNodeColumn();
                if (col == null) {
                    throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5IST_NODE");
                }
                col = queryModel.getStatusColumn();
                if (col == null) {
                    throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5IST_STATUS");
                }
                col = queryModel.getLockColumn();
                if (col == null) {
                    throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5IST_LOCK");
                }
                col = queryModel.getColumn("MDCODE");
                if (col == null) {
                    throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5MDCODE");
                }
                DimensionColumnModelDefine dimCol = new DimensionColumnModelDefine(col, this.getEntityDimensionName(task.getDw()), task.getDw());
                queryModel.dimensionColumns.add(dimCol);
                queryModel.dimensionNames.add(dimCol.getDimensionName());
                col = queryModel.getColumn("DATATIME");
                if (col == null) {
                    throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5DATATIME");
                }
                dimCol = new DimensionColumnModelDefine(col, this.getPeriodDimensionName(task.getDateTime()), task.getDateTime());
                queryModel.dimensionColumns.add(dimCol);
                queryModel.dimensionNames.add(dimCol.getDimensionName());
                List sceneDimensions = this.dataSchemeService.getDataSchemeDimension(task.getDataScheme(), DimensionType.DIMENSION);
                for (DataDimension dimension : sceneDimensions) {
                    if (!dimension.getReportDim().booleanValue()) continue;
                    String dimensionName = this.getEntityDimensionName(dimension.getDimKey());
                    col = queryModel.getColumn(dimensionName);
                    if (col == null) {
                        throw new ProcessRuntimeException("jiuqi.nr.default", "\u6d41\u7a0b\u5b9e\u4f8b\u8868\u7ed3\u6784\u9519\u8bef\uff0c\u672a\u80fd\u627e\u5230\u5b57\u6bb5" + dimensionName);
                    }
                    dimCol = new DimensionColumnModelDefine(col, dimensionName, dimension.getDimKey());
                    queryModel.dimensionColumns.add(dimCol);
                    queryModel.dimensionNames.add(dimCol.getDimensionName());
                }
                col = queryModel.getColumn("IST_FORMKEY");
                if (col != null) {
                    queryModel.formOrFormGroupColumn = col;
                    queryModel.includeFormKeyColumn = true;
                }
                if ((col = queryModel.getColumn("IST_FORMGROUPKEY")) != null) {
                    queryModel.formOrFormGroupColumn = col;
                    queryModel.includeFormGroupKeyColumn = true;
                }
                return queryModel;
            }

            private String getPeriodDimensionName(String periodEntityId) {
                try {
                    IPeriodEntityAdapter periodEntityAdapter = (IPeriodEntityAdapter)SpringBeanUtils.getBean(IPeriodEntityAdapter.class);
                    return periodEntityAdapter.getPeriodDimensionName(periodEntityId);
                }
                catch (Exception e) {
                    throw new ProcessRuntimeException("jiuqi.nr.default", "\u83b7\u53d6\u65f6\u671f\u5b9e\u4f53\u7ef4\u5ea6\u540d\u65f6\u53d1\u751f\u9519\u8bef\uff0c\u5b9e\u4f53ID\uff1a" + periodEntityId, (Throwable)e);
                }
            }

            private String getEntityDimensionName(String entityId) {
                try {
                    return this.entityMetaService.getDimensionName(entityId);
                }
                catch (Exception e) {
                    throw new ProcessRuntimeException("jiuqi.nr.default", "\u83b7\u53d6\u5b9e\u4f53\u7ef4\u5ea6\u540d\u65f6\u53d1\u751f\u9519\u8bef\uff0c\u5b9e\u4f53ID\uff1a" + entityId, (Throwable)e);
                }
            }
        }
    }

    public static class DimensionColumnModelDefine {
        private final ColumnModelDefine columnModel;
        private final String dimensionName;
        private final String entityId;

        public DimensionColumnModelDefine(ColumnModelDefine columnModel, String dimensionName, String entityId) {
            this.columnModel = columnModel;
            this.dimensionName = dimensionName;
            this.entityId = entityId;
        }

        public ColumnModelDefine getColumnModelDefine() {
            return this.columnModel;
        }

        public String getName() {
            return this.columnModel.getName();
        }

        public String getDimensionName() {
            return this.dimensionName;
        }

        public String getEntityId() {
            return this.entityId;
        }
    }
}

