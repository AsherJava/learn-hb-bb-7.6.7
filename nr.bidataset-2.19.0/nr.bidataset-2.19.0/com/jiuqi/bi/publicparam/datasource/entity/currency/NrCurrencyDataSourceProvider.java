/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.ParameterUtils
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.bi.publicparam.datasource.entity.currency;

import com.jiuqi.bi.publicparam.datasource.entity.NrEntityDataSourceProvider;
import com.jiuqi.bi.publicparam.datasource.entity.currency.NrBwbEntityRow;
import com.jiuqi.bi.publicparam.datasource.entity.currency.NrCurrencyDataSourceModel;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

public class NrCurrencyDataSourceProvider
extends NrEntityDataSourceProvider
implements IParameterDataSourceProvider {
    private static NrBwbEntityRow bwbEntityRow = new NrBwbEntityRow();

    public NrCurrencyDataSourceProvider(NrCurrencyDataSourceModel dataSourceModel, IEntityViewRunTimeController entityViewRunTimeController, IEntityDataService entityDataService, IEntityMetaService entityMetaService, IDataDefinitionRuntimeController dataDefinitionController, JdbcTemplate jdbcTemplate) {
        super(dataSourceModel, entityViewRunTimeController, entityDataService, entityMetaService, dataDefinitionController, jdbcTemplate);
    }

    @Override
    public ParameterResultset getDefaultValue(ParameterDataSourceContext context) throws ParameterException {
        try {
            AbstractParameterValueConfig cfg = context.getModel().getValueConfig();
            String mode = cfg.getDefaultValueMode();
            if (mode.equals("first")) {
                ArrayList<IEntityRow> rows = new ArrayList<IEntityRow>();
                rows.add(bwbEntityRow);
                return this.getParameterResultset(context, rows, null, false);
            }
            if (mode.equals("appoint")) {
                IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)context.getModel().getDatasource());
                List keys = cfg.getDefaultValue().getKeysAsString(valueFormat);
                if (keys.size() == 1 && ((String)keys.get(0)).equals("PROVIDER_BASECURRENCY")) {
                    ArrayList<IEntityRow> rows = new ArrayList<IEntityRow>();
                    rows.add(bwbEntityRow);
                    return this.getParameterResultset(context, rows, null, false);
                }
            } else if (mode.equals("none")) {
                return new ParameterResultset();
            }
            return super.getDefaultValue(context);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public ParameterResultset getCandidateValue(ParameterDataSourceContext context, ParameterHierarchyFilterItem hierarchyFilter) throws ParameterException {
        try {
            String entityId = this.getEntityId();
            DimensionValueSet masterKeys = this.createMasterkeys(context);
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
            ArrayList<IEntityRow> rows = new ArrayList<IEntityRow>();
            rows.add(bwbEntityRow);
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

    @Override
    public ParameterResultset compute(ParameterDataSourceContext context, AbstractParameterValue value) throws ParameterException {
        try {
            String entityId = this.getEntityId();
            DimensionValueSet masterKeys = this.createMasterkeys(context);
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
            ArrayList<IEntityRow> rows = new ArrayList<IEntityRow>();
            IEntityTable entityTable = this.getCandidateEntityTable(context, entityDefine, masterKeys, null, false);
            IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)context.getModel().getDatasource());
            List keys = value.getKeysAsString(valueFormat);
            if (keys != null && keys.size() > 0) {
                for (String key : keys) {
                    IEntityRow row = entityTable.findByEntityKey(key);
                    if (row != null) {
                        rows.add(row);
                        continue;
                    }
                    if (!key.equals("PROVIDER_BASECURRENCY")) continue;
                    rows.add(bwbEntityRow);
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

    @Override
    public ParameterResultset search(ParameterDataSourceContext context, List<String> searchValues) throws ParameterException {
        try {
            String entityId = this.getEntityId();
            DimensionValueSet masterKeys = this.createMasterkeys(context);
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
            ArrayList<IEntityRow> rows = new ArrayList<IEntityRow>();
            IEntityTable entityTable = this.getCandidateEntityTable(context, entityDefine, masterKeys, null, false);
            List allRows = entityTable.getAllRows();
            allRows.add(bwbEntityRow);
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
            return this.getParameterResultset(context, rows, entityTable, false);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }
}

