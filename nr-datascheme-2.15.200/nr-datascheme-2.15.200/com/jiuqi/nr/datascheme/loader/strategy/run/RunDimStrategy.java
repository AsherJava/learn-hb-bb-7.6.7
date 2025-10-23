/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.loader.run.RunTimeSchemeLoaderStrategy
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.datascheme.loader.strategy.run;

import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.loader.run.RunTimeSchemeLoaderStrategy;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class RunDimStrategy
implements RunTimeSchemeLoaderStrategy {
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DataModelService dataModelService;

    RunDimStrategy() {
    }

    public boolean matching(int nodeType) {
        return (NodeType.DIM.getValue() & nodeType) != 0;
    }

    public <E> void visitRoot(SchemeNode<E> root, RuntimeSchemeVisitor<E> visitor) {
    }

    public <E> List<SchemeNode<E>> visitNode(SchemeNode<E> next, RuntimeSchemeVisitor<E> visitor, Integer interestType) {
        String dimKey = next.getKey();
        try {
            IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
            boolean periodEntity = periodAdapter.isPeriodEntity(dimKey);
            if (periodEntity) {
                TableModelDefine tableModel = periodAdapter.getPeriodEntityTableModel(dimKey);
                List showFields = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
                visitor.visitDimNode(next, showFields);
            } else {
                IEntityModel entityModel = this.entityMetaService.getEntityModel(dimKey);
                List showFields = entityModel.getShowFields();
                visitor.visitDimNode(next, showFields);
            }
        }
        catch (Exception e) {
            throw new SchemeDataException(" \u5b9e\u4f53 " + dimKey + " \u672a\u627e\u5230\u6216\u5df2\u4e22\u5931", (Throwable)e);
        }
        return null;
    }

    public <E> SchemeNode<E> visitNode(SchemeNode<E> next, RuntimeReverseSchemeVisitor<E> reverse, Integer interestType) {
        return null;
    }
}

