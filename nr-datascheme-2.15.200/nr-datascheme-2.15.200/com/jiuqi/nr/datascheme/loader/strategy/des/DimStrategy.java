/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.loader.des.DataSchemeLoaderStrategy
 *  com.jiuqi.nr.datascheme.api.loader.des.ReverseSchemeNodeVisitor
 *  com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.datascheme.loader.strategy.des;

import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.loader.des.DataSchemeLoaderStrategy;
import com.jiuqi.nr.datascheme.api.loader.des.ReverseSchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor;
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
class DimStrategy
implements DataSchemeLoaderStrategy {
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DataModelService dataModelService;

    DimStrategy() {
    }

    public boolean matching(int nodeType) {
        return (NodeType.DIM.getValue() & nodeType) != 0;
    }

    public <E> void visitRoot(SchemeNode<E> root, SchemeNodeVisitor<E> schemeNodeVisitor) {
    }

    public <E> List<SchemeNode<E>> visitNode(SchemeNode<E> next, SchemeNodeVisitor<E> schemeNodeVisitor, Integer interestType) {
        String dimKey;
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        boolean periodEntity = periodAdapter.isPeriodEntity(dimKey = next.getKey());
        if (periodEntity) {
            TableModelDefine tableModel = periodAdapter.getPeriodEntityTableModel(dimKey);
            List showFields = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
            schemeNodeVisitor.visitDimNode(next, showFields);
        } else {
            IEntityModel entityModel = this.entityMetaService.getEntityModel(dimKey);
            List showFields = entityModel.getShowFields();
            schemeNodeVisitor.visitDimNode(next, showFields);
        }
        return null;
    }

    public <E> SchemeNode<E> visitNode(SchemeNode<E> next, ReverseSchemeNodeVisitor<E> reverseSchemeNodeVisitor, Integer interestType) {
        return null;
    }
}

