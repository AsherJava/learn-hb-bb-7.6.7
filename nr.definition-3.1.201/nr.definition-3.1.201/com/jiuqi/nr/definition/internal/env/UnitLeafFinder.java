/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IUnitLeafFinder
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IUnitLeafFinder;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.CollectionUtils;

public class UnitLeafFinder
implements IUnitLeafFinder {
    private EntityViewDefine entityViewDefine;
    private IEntityDataService entityDataService;
    private static final Logger logger = LogFactory.getLogger(UnitLeafFinder.class);

    public UnitLeafFinder(EntityViewDefine entityViewDefine, IEntityDataService entityDataService) {
        this.entityViewDefine = entityViewDefine;
        this.entityDataService = entityDataService;
    }

    public Map<String, List<String>> getAllSubLeafUnits(ExecutorContext context, String period, String linkAlias, List<String> unitKeys) {
        if (StringUtils.isNotEmpty((String)linkAlias) || CollectionUtils.isEmpty(unitKeys)) {
            return null;
        }
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();
        IEntityQuery entityQuery = this.getiEntityQuery(context, period);
        ExecutorContext queryContext = new ExecutorContext(context.getRuntimeController());
        try {
            IEntityTable entityTable = entityQuery.executeFullBuild((IContext)queryContext);
            for (String unitKey : unitKeys) {
                ArrayList<String> children = new ArrayList<String>();
                List childRows = entityTable.getAllChildRows(unitKey);
                if (childRows != null) {
                    for (IEntityRow row : childRows) {
                        if (!row.isLeaf()) continue;
                        children.add(row.getEntityKeyData());
                    }
                }
                result.put(unitKey, children);
            }
        }
        catch (Exception e) {
            logger.error("\u4e3b\u7ef4\u5ea6\u67e5\u8be2\u5931\u8d25", (Throwable)e);
            return null;
        }
        return result;
    }

    public List<String> getSubLeafUnits(ExecutorContext context, String period, String linkAlias, String unitKey) {
        if (StringUtils.isNotEmpty((String)linkAlias) || StringUtils.isEmpty((String)unitKey)) {
            return null;
        }
        ArrayList<String> result = new ArrayList<String>();
        IEntityQuery entityQuery = this.getiEntityQuery(context, period);
        ExecutorContext queryContext = new ExecutorContext(context.getRuntimeController());
        try {
            IEntityTable entityTable = entityQuery.executeFullBuild((IContext)queryContext);
            List childRows = entityTable.getAllChildRows(unitKey);
            if (childRows != null) {
                for (IEntityRow row : childRows) {
                    if (!row.isLeaf()) continue;
                    result.add(row.getEntityKeyData());
                }
            }
        }
        catch (Exception e) {
            logger.error("\u4e3b\u7ef4\u5ea6\u67e5\u8be2\u5931\u8d25", (Throwable)e);
            return null;
        }
        return result;
    }

    @NotNull
    private IEntityQuery getiEntityQuery(ExecutorContext context, String period) {
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        if (StringUtils.isEmpty((String)period)) {
            entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
        } else {
            this.fixVersion(context, period, entityQuery);
        }
        entityQuery.setAuthorityOperations(AuthorityType.Read);
        entityQuery.setIgnoreViewFilter(true);
        entityQuery.markLeaf();
        if (StringUtils.isNotEmpty((String)context.getOrgEntityId())) {
            RunTimeEntityViewDefineImpl entityViewDefine1 = new RunTimeEntityViewDefineImpl();
            entityViewDefine1.setEntityId(context.getOrgEntityId());
            entityViewDefine1.setRowFilterExpression(this.entityViewDefine.getRowFilterExpression());
            entityViewDefine1.setFilterRowByAuthority(this.entityViewDefine.getFilterRowByAuthority());
            entityQuery.setEntityView((EntityViewDefine)entityViewDefine1);
        } else {
            entityQuery.setEntityView(this.entityViewDefine);
        }
        return entityQuery;
    }

    private void fixVersion(ExecutorContext context, String period, IEntityQuery entityQuery) {
        try {
            IPeriodAdapter periodAdapter = context.getPeriodAdapter();
            Date[] periodDateRegion = periodAdapter.getPeriodDateRegion(new PeriodWrapper(period));
            entityQuery.setQueryVersionDate(periodDateRegion[1]);
        }
        catch (Exception e1) {
            entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
        }
    }
}

