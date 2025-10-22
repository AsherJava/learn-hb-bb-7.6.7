/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.setting.IFieldsInfo
 *  com.jiuqi.nr.entity.engine.var.PageCondition
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultItem
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.ParameterUtils
 *  com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext$PageInfo
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.bi.publicparam.datasource.entity;

import com.jiuqi.bi.publicparam.datasource.entity.NrEntityDataSourceModel;
import com.jiuqi.bi.publicparam.util.ParamUtils;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;
import com.jiuqi.nr.entity.engine.var.PageCondition;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultItem;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember;
import com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.jdbc.core.JdbcTemplate;

public class NrEntityDataSourceProvider
implements IParameterDataSourceProvider {
    protected NrEntityDataSourceModel dataSourceModel;
    protected IEntityViewRunTimeController entityViewRunTimeController;
    protected IEntityDataService entityDataService;
    protected IEntityMetaService entityMetaService;
    protected IDataDefinitionRuntimeController dataDefinitionController;
    protected JdbcTemplate jdbcTemplate;

    public NrEntityDataSourceProvider(NrEntityDataSourceModel dataSourceModel, IEntityViewRunTimeController entityViewRunTimeController, IEntityDataService entityDataService, IEntityMetaService entityMetaService, IDataDefinitionRuntimeController dataDefinitionController, JdbcTemplate jdbcTemplate) {
        this.dataSourceModel = dataSourceModel;
        this.entityViewRunTimeController = entityViewRunTimeController;
        this.entityDataService = entityDataService;
        this.entityMetaService = entityMetaService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public ParameterResultset getDefaultValue(ParameterDataSourceContext context) throws ParameterException {
        try {
            ParameterModel model = context.getModel();
            AbstractParameterValueConfig cfg = model.getValueConfig();
            String mode = cfg.getDefaultValueMode();
            if (mode.equals("none")) {
                return new ParameterResultset();
            }
            String entityId = this.getEntityId();
            DimensionValueSet masterKeys = this.createMasterkeys(context);
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
            ArrayList<IEntityRow> rows = new ArrayList<IEntityRow>();
            IEntityTable entityTable = null;
            if (mode.equals("appoint")) {
                IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)model.getDatasource());
                List keys = cfg.getDefaultValue().getKeysAsString(null, valueFormat);
                if (keys == null || keys.isEmpty()) {
                    return new ParameterResultset();
                }
                masterKeys.setValue(entityDefine.getDimensionName(), (Object)keys);
                entityTable = this.getCandidateEntityTable(context, entityDefine, masterKeys, null, false);
                rows.addAll(entityTable.getAllRows());
            } else if (mode.equals("first")) {
                entityTable = this.getCandidateEntityTable(context, entityDefine, masterKeys, null, true);
                if (model.isOnlyLeafSelectable()) {
                    List all = entityTable.getAllRows();
                    if (all != null && all.size() > 0) {
                        for (IEntityRow row : all) {
                            if (!row.isLeaf()) continue;
                            rows.add(row);
                            break;
                        }
                    }
                } else {
                    List roots = entityTable.getRootRows();
                    if (roots != null && roots.size() > 0) {
                        rows.add((IEntityRow)roots.get(0));
                    }
                }
            } else if (mode.equals("firstChild")) {
                entityTable = this.getCandidateEntityTable(context, entityDefine, masterKeys, null, true);
                List roots = entityTable.getRootRows();
                if (roots != null && roots.size() > 0) {
                    IEntityRow parent = (IEntityRow)roots.get(0);
                    rows.add(parent);
                    List childs = entityTable.getChildRows(parent.getEntityKeyData());
                    if (childs != null && childs.size() > 0) {
                        rows.addAll(childs);
                    }
                }
            } else if (mode.equals("firstAllChild")) {
                entityTable = this.getCandidateEntityTable(context, entityDefine, masterKeys, null, true);
                List roots = entityTable.getRootRows();
                if (roots != null && roots.size() > 0) {
                    IEntityRow parent = (IEntityRow)roots.get(0);
                    rows.add(parent);
                    List childs = entityTable.getAllChildRows(parent.getEntityKeyData());
                    if (childs != null && childs.size() > 0) {
                        rows.addAll(childs);
                    }
                }
            } else if (mode.equals("expr")) {
                String formula = (String)cfg.getDefaultValue().getValue();
                entityTable = this.getCandidateEntityTable(context, entityDefine, masterKeys, formula, false);
                rows.addAll(entityTable.getAllRows());
            }
            return this.getParameterResultset(context, rows, entityTable, false);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public ParameterResultset getCandidateValue(ParameterDataSourceContext context, ParameterHierarchyFilterItem hierarchyFilter) throws ParameterException {
        try {
            String entityId = this.getEntityId();
            DimensionValueSet masterKeys = this.createMasterkeys(context);
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
            ArrayList<IEntityRow> rows = new ArrayList<IEntityRow>();
            boolean isTree = entityDefine.isTree();
            IEntityTable entityTable = null;
            if (!isTree) {
                entityTable = this.getCandidateEntityTable(context, entityDefine, masterKeys, null, false);
                rows.addAll(entityTable.getAllRows());
            } else {
                String parent = null;
                boolean isAllSub = true;
                if (hierarchyFilter != null) {
                    parent = hierarchyFilter.getParent();
                    isAllSub = hierarchyFilter.isAllSub();
                }
                if (StringUtils.isEmpty(parent)) {
                    if (isAllSub) {
                        entityTable = this.getCandidateEntityTable(context, entityDefine, masterKeys, null, true);
                        rows.addAll(entityTable.getAllRows());
                    } else {
                        entityTable = this.getCandidateEntityTable(context, entityDefine, masterKeys, null, false);
                        rows.addAll(entityTable.getRootRows());
                    }
                } else {
                    entityTable = this.getCandidateEntityTable(context, entityDefine, masterKeys, null, true);
                    if (isAllSub) {
                        rows.addAll(entityTable.getAllChildRows(parent));
                    } else {
                        rows.addAll(entityTable.getChildRows(parent));
                    }
                }
            }
            return this.getParameterResultset(context, rows, entityTable, isTree);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public ParameterResultset compute(ParameterDataSourceContext context, AbstractParameterValue value) throws ParameterException {
        try {
            String entityId = this.getEntityId();
            DimensionValueSet masterKeys = this.createMasterkeys(context);
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
            ArrayList<IEntityRow> rows = new ArrayList<IEntityRow>();
            IEntityTable entityTable = this.getCandidateEntityTable(context, entityDefine, masterKeys, null, true);
            IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)context.getModel().getDatasource());
            List keys = value.getKeysAsString(null, valueFormat);
            if (keys != null && keys.size() > 0) {
                for (String key : keys) {
                    IEntityRow row = entityTable.findByEntityKey(key);
                    if (row == null) continue;
                    rows.add(row);
                }
            } else {
                return ParameterResultset.EMPTY_RESULTSET;
            }
            return this.getParameterResultset(context, rows, entityTable, false);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public ParameterResultset search(ParameterDataSourceContext context, List<String> searchValues) throws ParameterException {
        try {
            String entityId = this.getEntityId();
            DimensionValueSet masterKeys = this.createMasterkeys(context);
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
            ArrayList<IEntityRow> rows = new ArrayList<IEntityRow>();
            IEntityTable entityTable = this.getCandidateEntityTable(context, entityDefine, masterKeys, null, true);
            List allRows = entityTable.getAllRows();
            for (IEntityRow row : allRows) {
                boolean matched = true;
                for (String searchValue : searchValues) {
                    matched = row.getEntityKeyData().contains(searchValue) || row.getTitle().contains(searchValue);
                    if (matched) continue;
                    break;
                }
                if (!matched) continue;
                rows.add(row);
            }
            return this.getParameterResultset(context, rows, entityTable, true);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public List<DataSourceCandidateFieldInfo> getDataSourceCandidateFields(AbstractParameterDataSourceModel datasourceModel) throws ParameterException {
        try {
            String entityId = this.getEntityId();
            IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
            Iterator attributes = entityModel.getAttributes();
            ArrayList<DataSourceCandidateFieldInfo> list = new ArrayList<DataSourceCandidateFieldInfo>();
            while (attributes.hasNext()) {
                IEntityAttribute attribute = (IEntityAttribute)attributes.next();
                DataSourceCandidateFieldInfo info = new DataSourceCandidateFieldInfo(attribute.getCode(), attribute.getTitle());
                list.add(info);
            }
            return list;
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    protected DimensionValueSet createMasterkeys(ParameterDataSourceContext context) {
        DimensionValueSet valueSet = new DimensionValueSet();
        String period = ParamUtils.getPeriod(context);
        if (StringUtils.isNotEmpty((String)period)) {
            valueSet.setValue("DATATIME", (Object)period);
        }
        return valueSet;
    }

    protected IEntityTable getEntityTable(ParameterDataSourceContext context, String entityId, DimensionValueSet masterKeys, String rowFilter, boolean fullBuild) throws Exception {
        Optional<ParameterDependMember> isolateParam;
        IEntityDefine entityDefine;
        List depends;
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionController);
        String periodView = ParamUtils.getPeriodEntityId(context);
        if (StringUtils.isNotEmpty((String)periodView)) {
            executorContext.setPeriodView(periodView);
        }
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setAuthorityOperations(AuthorityType.Read);
        iEntityQuery.setMasterKeys(masterKeys);
        iEntityQuery.setEntityView(entityViewDefine);
        iEntityQuery.sorted(true);
        if (StringUtils.isNotEmpty((String)rowFilter)) {
            iEntityQuery.setExpression(rowFilter);
        }
        if ((depends = context.getModel().getValueConfig().getDepends()) != null && depends.size() > 0 && (entityDefine = this.entityMetaService.queryEntity(entityId)).getIsolation() > 0 && (isolateParam = depends.stream().filter(o -> context.getCalculator().getParameterModelByName(o.getParameterName()).getDatasource() instanceof NrEntityDataSourceModel).findAny()).isPresent()) {
            IParameterValueFormat valueFormat;
            ParameterResultset value = context.getCalculator().getValue(isolateParam.get().getParameterName());
            List values = value.getValueAsString(valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)this.dataSourceModel));
            if (values.size() == 1) {
                iEntityQuery.setIsolateCondition((String)values.get(0));
            } else {
                return new EmptyEntityTable();
            }
        }
        IEntityTable entityTable = fullBuild ? iEntityQuery.executeFullBuild((IContext)executorContext) : iEntityQuery.executeReader((IContext)executorContext);
        return entityTable;
    }

    protected IEntityTable getCandidateEntityTable(ParameterDataSourceContext context, IEntityDefine entityDefine, DimensionValueSet masterKeys, String expression, boolean fullBuild) throws Exception {
        AbstractParameterValueConfig cfg = context.getModel().getValueConfig();
        ParameterCandidateValueMode mode = cfg.getCandidateMode();
        String rowFilter = expression;
        if (mode == ParameterCandidateValueMode.APPOINT) {
            List keys = cfg.getCandidateValue();
            masterKeys.setValue(entityDefine.getDimensionName(), (Object)keys);
        } else if (mode == ParameterCandidateValueMode.EXPRESSION) {
            rowFilter = (String)cfg.getCandidateValue().get(0);
            if (StringUtils.isNotEmpty((String)expression) && StringUtils.isNotEmpty((String)rowFilter)) {
                rowFilter = "(" + rowFilter + ") and (" + expression + ")";
            }
        }
        return this.getEntityTable(context, entityDefine.getId(), masterKeys, rowFilter, fullBuild);
    }

    protected String getEntityId() {
        String entityId = this.dataSourceModel.getEntityViewId();
        if (entityId.length() == 36) {
            try {
                String sql = "select EF_ENTITY_ID from NR_ENTITY_UPGRADE_FIELD where  EF_FIELD_KEY='" + entityId + "'";
                String newEntityId = (String)this.jdbcTemplate.queryForObject(sql, String.class);
                if (newEntityId != null) {
                    entityId = newEntityId;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return entityId;
    }

    protected ParameterResultset getParameterResultset(ParameterDataSourceContext context, List<IEntityRow> rows, IEntityTable rs, boolean needTreeInfo) {
        ArrayList<ParameterResultItem> items = new ArrayList<ParameterResultItem>(rows.size());
        if (rows != null && rows.size() > 0) {
            ParameterDataSourceContext.PageInfo pageInfo;
            if (context.getModel().isOrderReverse()) {
                Collections.reverse(rows);
            }
            if ((pageInfo = context.getPageInfo()) != null && pageInfo.recordSize > 0) {
                int startIndex = pageInfo.startRow - 1;
                if (startIndex > rows.size() - 1 || startIndex < 0) {
                    return new ParameterResultset();
                }
                int toIndex = startIndex + pageInfo.recordSize;
                if (toIndex > rows.size()) {
                    toIndex = rows.size();
                }
                rows = rows.subList(startIndex, toIndex);
            }
            for (IEntityRow obj : rows) {
                String key = obj.getEntityKeyData();
                String name = obj.getTitle();
                ParameterResultItem item = new ParameterResultItem((Object)key, name);
                if (needTreeInfo && rs != null) {
                    if (obj.getParentEntityKey() != null) {
                        item.setParent(obj.getParentEntityKey());
                        StringBuilder path = new StringBuilder();
                        String[] parents = obj.getParentsEntityKeyDataPath();
                        if (parents.length > 0) {
                            for (String parent : parents) {
                                path.append(parent).append("/");
                            }
                        }
                        path.append(item.getValue());
                        item.setPath(path.toString());
                    }
                    item.setLeaf(true);
                    int childCount = rs.getDirectChildCount(obj.getEntityKeyData());
                    if (childCount > 0) {
                        item.setLeaf(false);
                    }
                } else {
                    item.setParent("-");
                    item.setLeaf(true);
                }
                items.add(item);
            }
        }
        return new ParameterResultset(items);
    }

    private class EmptyEntityTable
    implements IEntityTable {
        private EmptyEntityTable() {
        }

        public IFieldsInfo getFieldsInfo() {
            return null;
        }

        public TableModelDefine getEntityTableDefine() {
            return null;
        }

        public IEntityModel getEntityModel() {
            return null;
        }

        public boolean isI18nAttribute(String attributeCode) {
            return false;
        }

        public List<IEntityRow> getAllRows() {
            return new ArrayList<IEntityRow>();
        }

        public List<IEntityRow> getRootRows() {
            return new ArrayList<IEntityRow>();
        }

        public List<IEntityRow> getChildRows(String entityKeyData) {
            return new ArrayList<IEntityRow>();
        }

        public List<IEntityRow> getAllChildRows(String entityKeyData) {
            return new ArrayList<IEntityRow>();
        }

        public IEntityRow findByEntityKey(String entityKeyData) {
            return null;
        }

        public Map<String, IEntityRow> findByEntityKeys(Set<String> entityKeyDatas) {
            return new HashMap<String, IEntityRow>();
        }

        public int getMaxDepth() {
            return 0;
        }

        public int getMaxDepthByEntityKey(String entityKeyData) {
            return 0;
        }

        public IEntityRow findByCode(String codeValue) {
            return null;
        }

        public List<IEntityRow> findAllByCode(String codeValue) {
            return new ArrayList<IEntityRow>();
        }

        public Map<String, IEntityRow> findByCodes(List<String> codeValue) {
            return new HashMap<String, IEntityRow>();
        }

        public Map<String, List<IEntityRow>> findAllByCodes(List<String> codeValue) {
            return new HashMap<String, List<IEntityRow>>();
        }

        public int getDirectChildCount(String entityKeyData) {
            return 0;
        }

        public int getAllChildCount(String entityKeyData) {
            return 0;
        }

        public Map<String, Integer> getDirectChildCountByParent(String parentKey) {
            return new HashMap<String, Integer>();
        }

        public Map<String, Integer> getAllChildCountByParent(String parentKey) {
            return new HashMap<String, Integer>();
        }

        public String[] getParentsEntityKeyDataPath(String entityKeyData) {
            return new String[0];
        }

        public int getTotalCount() {
            return 0;
        }

        public List<IEntityRow> getPageRows(PageCondition pageCondition) {
            return null;
        }

        public IEntityRow quickFindByEntityKey(String entityKeyData) {
            return null;
        }

        public Map<String, IEntityRow> quickFindByEntityKeys(Set<String> entityKeyDatas) {
            return new HashMap<String, IEntityRow>();
        }
    }
}

