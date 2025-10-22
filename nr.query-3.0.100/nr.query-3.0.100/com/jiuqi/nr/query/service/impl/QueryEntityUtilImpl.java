/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.definition.facade.TableModel
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.query.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.query.service.QueryEntityUtil;
import com.jiuqi.nvwa.definition.facade.TableModel;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryEntityUtilImpl
implements QueryEntityUtil {
    private static final Logger log = LoggerFactory.getLogger(QueryEntityUtilImpl.class);
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityDataService entityDataService;

    @Override
    public IEntityTable getEntityTable(EntityViewDefine entityView, DimensionValueSet masterKeys, String rowFilter, ReloadTreeInfo reloadTreeInfo, boolean ignoreViewFilter, AuthorityType authorityType) {
        try {
            if (entityView != null) {
                ExecutorContext context = new ExecutorContext(this.runtimeController);
                IEntityQuery query = this.entityDataService.newEntityQuery();
                query.sorted(true);
                query.setEntityView(entityView);
                if (masterKeys != null) {
                    query.setMasterKeys(masterKeys);
                }
                if (rowFilter != null) {
                    query.setRowFilter(rowFilter);
                }
                query.setIgnoreViewFilter(ignoreViewFilter);
                if (authorityType != null) {
                    query.setAuthorityOperations(authorityType);
                }
                return query.executeReader((IContext)context);
            }
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
        return null;
    }

    @Override
    public TableModelDefine getEntityTablelDefineByView(String viewKey) {
        boolean periodView = this.periodEntityAdapter.isPeriodEntity(viewKey);
        if (periodView) {
            return this.periodEntityAdapter.getPeriodEntityTableModel(viewKey);
        }
        return this.iEntityMetaService.getTableModel(viewKey);
    }

    @Override
    public IEntityTable getEntityTableUseCache(EntityViewDefine view, DimensionValueSet dimensionValueSet, AuthorityType authorityType) {
        try {
            if (dimensionValueSet == null) {
                dimensionValueSet = new DimensionValueSet();
            }
            IEntityQuery query = this.entityDataService.newEntityQuery();
            query.sorted(true);
            query.setEntityView(view);
            query.setMasterKeys(dimensionValueSet);
            if (authorityType != null) {
                query.setAuthorityOperations(authorityType);
            }
            ExecutorContext context = new ExecutorContext(this.runtimeController);
            IEntityTable entityTable = query.executeReader((IContext)context);
            return entityTable;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public TableModel getEntityTablelModelByEntityId(String entityId) {
        return null;
    }

    @Override
    public TableKind getEntityTablelKindByView(String viewKey) {
        try {
            boolean periodView = this.periodEntityAdapter.isPeriodEntity(viewKey);
            if (periodView) {
                return TableKind.TABLE_KIND_ENTITY_PERIOD;
            }
            return TableKind.TABLE_KIND_ENTITY;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String getDicTreeStructByView(String viewKey) {
        try {
            IEntityDefine entityDefine;
            boolean periodView = this.periodEntityAdapter.isPeriodEntity(viewKey);
            if (!periodView && (entityDefine = this.iEntityMetaService.queryEntity(viewKey)).getTreeStruct() != null) {
                return entityDefine.getTreeStruct().getLevelCode();
            }
            return null;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<TableDefine> getAllTableDefinesByTableKind(TableKind tableKind) {
        return null;
    }

    @Override
    public TableDefine queryTableDefineByCode(String tableDefineCode) {
        try {
            return this.runtimeController.queryTableDefineByCode(tableDefineCode);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String getTableTitleByViewKey(String viewKey) {
        try {
            return this.getEntityTablelDefineByView(viewKey).getTitle();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean isPeriodUnit(String viewKey) throws JQException {
        return this.periodEntityAdapter.isPeriodEntity(viewKey);
    }

    @Override
    public String getTableKeyByView(String viewKey) throws JQException {
        return this.getEntityTablelDefineByView(viewKey).getID();
    }
}

