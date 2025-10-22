/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData
 *  com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.exception.EntityUpdateException
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityModify
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.intf.IModifyRow
 *  com.jiuqi.nr.entity.engine.intf.IModifyTable
 *  com.jiuqi.nr.entity.engine.result.EntityUpdateResult
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.treeimpl.web.service.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.exception.EntityUpdateException;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityModify;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.intf.IModifyRow;
import com.jiuqi.nr.entity.engine.intf.IModifyTable;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.unit.treeimpl.web.request.NodeDragParam;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DataEntryTreeNodeDragService {
    @Resource
    private IUnitTreeContextWrapper contextWrapper;
    @Resource
    private IUnitTreeContextBuilder contextBuilder;
    @Resource
    private IEntityDataService entityDataService;
    @Resource
    private IEntityMetaService entityMetaService;
    @Resource
    private IEntityViewRunTimeController runTimeController;
    @Resource
    private IDataDefinitionRuntimeController definitionRuntimeController;
    @Resource
    private UnitTreeEntityDataQuery entityDataQuery;
    private static final String ERROR_MSG = "errorMsg";
    private static final String STATUE_CODE = "statueCode";
    private static final String LOCATE_NODE = "locateNode";

    public Map<String, Object> dragNodeChange(NodeDragParam param) throws JQException, EntityUpdateException {
        BaseNodeDataImpl dragNode = param.getDragNode();
        BaseNodeDataImpl dropNode = param.getDropNode();
        if (dragNode == null || dropNode == null || StringUtils.isEmpty((String)dragNode.getKey()) || StringUtils.isEmpty((String)dropNode.getKey())) {
            HashMap<String, Object> response = new HashMap<String, Object>();
            response.put(STATUE_CODE, 500);
            response.put(ERROR_MSG, "\u65e0\u6548\u7684\u8282\u70b9\u4fe1\u606f\uff0c\u4fee\u6539\u5931\u8d25\uff01\uff01");
            return response;
        }
        IUnitTreeContext treeContext = this.contextBuilder.createTreeContext((UnitTreeContextData)param);
        IEntityModify iEntityModify = this.buildEntityModify(treeContext, dragNode, dropNode);
        IModifyTable iModifyTable = iEntityModify.executeUpdate((IContext)this.buildExecutorContext(treeContext));
        IEntityRow dragNodeRow = iModifyTable.findByEntityKey(dragNode.getKey());
        IEntityRow dropNodeRow = iModifyTable.findByEntityKey(dropNode.getKey());
        if (dragNodeRow == null || dropNodeRow == null) {
            HashMap<String, Object> response = new HashMap<String, Object>();
            response.put(STATUE_CODE, 500);
            response.put(ERROR_MSG, "\u6ca1\u6709\u627e\u5230\u5177\u4f53\u7684\u8282\u70b9\u4fe1\u606f\uff0c\u4fee\u6539\u5931\u8d25\uff01\uff01");
            return response;
        }
        if (!this.contextWrapper.hasUnitEditOperation(treeContext.getEntityDefine().getId(), dragNodeRow.getEntityKeyData(), treeContext.getPeriodEntity().getKey(), treeContext.getPeriod())) {
            HashMap<String, Object> response = new HashMap<String, Object>();
            response.put(STATUE_CODE, 500);
            response.put(ERROR_MSG, "\u60a8\u5bf9[" + dragNodeRow.getCode() + " | " + dragNodeRow.getTitle() + "]\u6ca1\u6709\u7f16\u8f91\u6743\u9650\uff0c\u4fee\u6539\u5931\u8d25\uff01\uff01");
            return response;
        }
        if (param.getPosition() == 0) {
            return this.changeParent(treeContext, iModifyTable, dragNodeRow, dropNodeRow);
        }
        if (param.getState() == 0) {
            return this.exchangeOrder(treeContext, iModifyTable, dragNodeRow, dropNodeRow, param.getPosition());
        }
        if (param.getState() == 1) {
            return this.changeParentAndOrder(treeContext, iModifyTable, dragNodeRow, dropNodeRow, param.getPosition());
        }
        return new HashMap<String, Object>();
    }

    private Map<String, Object> changeParent(IUnitTreeContext treeContext, IModifyTable iModifyTable, IEntityRow dragNodeRow, IEntityRow dropNodeRow) throws JQException, EntityUpdateException {
        IEntityModel entityModel = this.entityMetaService.getEntityModel(treeContext.getEntityDefine().getId());
        IEntityAttribute parentField = entityModel.getParentField();
        DimensionValueSet dimensionValueSet = this.buildPubDimValueSet(treeContext);
        dimensionValueSet.setValue(treeContext.getEntityDefine().getDimensionName(), (Object)dragNodeRow.getEntityKeyData());
        IModifyRow dragModifyRow = iModifyTable.appendModifyRow(dimensionValueSet);
        dragModifyRow.setValue(parentField.getCode(), (Object)dropNodeRow.getEntityKeyData());
        dragModifyRow.buildRow();
        return this.responseObj(iModifyTable.commitChange(), dragNodeRow);
    }

    private Map<String, Object> exchangeOrder(IUnitTreeContext treeContext, IModifyTable iModifyTable, IEntityRow dragNodeRow, IEntityRow dropNodeRow, int position) throws EntityUpdateException {
        IEntityModel entityModel = this.entityMetaService.getEntityModel(treeContext.getEntityDefine().getId());
        IEntityAttribute orderField = entityModel.getOrderField();
        DimensionValueSet dimensionValueSet = this.buildPubDimValueSet(treeContext);
        dimensionValueSet.setValue(treeContext.getEntityDefine().getDimensionName(), (Object)dragNodeRow.getEntityKeyData());
        IModifyRow dragModifyRow = iModifyTable.appendModifyRow(dimensionValueSet);
        dragModifyRow.setValue(orderField.getCode(), (Object)this.updateOrdered(treeContext, orderField, dropNodeRow, position));
        dragModifyRow.buildRow();
        return this.responseObj(iModifyTable.commitChange(), dragNodeRow);
    }

    private Map<String, Object> changeParentAndOrder(IUnitTreeContext treeContext, IModifyTable iModifyTable, IEntityRow dragNodeRow, IEntityRow dropNodeRow, int position) throws EntityUpdateException {
        IEntityModel entityModel = this.entityMetaService.getEntityModel(treeContext.getEntityDefine().getId());
        IEntityAttribute orderField = entityModel.getOrderField();
        IEntityAttribute parentField = entityModel.getParentField();
        DimensionValueSet dimensionValueSet = this.buildPubDimValueSet(treeContext);
        dimensionValueSet.setValue(treeContext.getEntityDefine().getDimensionName(), (Object)dragNodeRow.getEntityKeyData());
        IModifyRow dragModifyRow = iModifyTable.appendModifyRow(dimensionValueSet);
        dragModifyRow.setValue(parentField.getCode(), (Object)dropNodeRow.getParentEntityKey());
        dragModifyRow.setValue(orderField.getCode(), (Object)this.updateOrdered(treeContext, orderField, dropNodeRow, position));
        dragModifyRow.buildRow();
        return this.responseObj(iModifyTable.commitChange(), dragNodeRow);
    }

    private IEntityModify buildEntityModify(IUnitTreeContext treeContext, BaseNodeDataImpl dragNode, BaseNodeDataImpl dropNode) {
        IEntityModify iEntityModify = this.entityDataService.newEntityUpdate();
        iEntityModify.setEntityView(this.runTimeController.buildEntityView(treeContext.getEntityDefine().getId()));
        DimensionValueSet dimensionValueSet = this.buildPubDimValueSet(treeContext);
        dimensionValueSet.setValue(treeContext.getEntityDefine().getDimensionName(), Arrays.asList(dragNode.getKey(), dropNode.getKey()));
        iEntityModify.setMasterKeys(dimensionValueSet);
        return iEntityModify;
    }

    private ExecutorContext buildExecutorContext(IUnitTreeContext treeContext) {
        return new ExecutorContext(this.definitionRuntimeController);
    }

    private DimensionValueSet buildPubDimValueSet(IUnitTreeContext treeContext) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        IPeriodEntity periodEntity = treeContext.getPeriodEntity();
        String period = treeContext.getPeriod();
        if (periodEntity != null && StringUtils.isNotEmpty((String)period)) {
            dimensionValueSet.setValue(treeContext.getPeriodEntity().getDimensionName(), (Object)treeContext.getPeriod());
        }
        return dimensionValueSet;
    }

    private BigDecimal updateOrdered(IUnitTreeContext treeContext, IEntityAttribute orderField, IEntityRow dropNodeRow, int position) {
        IEntityRow[] dropRangeRows = this.getDropRangeRows(treeContext, dropNodeRow, position);
        IEntityRow upperNodeRow = dropRangeRows[0];
        IEntityRow underNodeRow = dropRangeRows[1];
        if (upperNodeRow == null && underNodeRow != null) {
            AbstractData orderDataValue = underNodeRow.getValue(orderField.getCode());
            BigDecimal underNodeOrder = orderDataValue.getAsCurrency();
            return underNodeOrder.subtract(BigDecimal.valueOf(50L));
        }
        if (upperNodeRow != null && underNodeRow == null) {
            AbstractData orderDataValue = upperNodeRow.getValue(orderField.getCode());
            BigDecimal upperNodeOrder = orderDataValue.getAsCurrency();
            return upperNodeOrder.add(BigDecimal.valueOf(50L));
        }
        if (upperNodeRow != null && underNodeRow != null) {
            AbstractData orderDataValue = upperNodeRow.getValue(orderField.getCode());
            BigDecimal upperNodeOrder = orderDataValue.getAsCurrency();
            orderDataValue = underNodeRow.getValue(orderField.getCode());
            BigDecimal underNodeOrder = orderDataValue.getAsCurrency();
            return upperNodeOrder.add(underNodeOrder).divide(BigDecimal.valueOf(2L), orderField.getDecimal(), RoundingMode.HALF_UP);
        }
        AbstractData orderDataValue = dropNodeRow.getValue(orderField.getCode());
        return this.updateOrdered_(orderField, orderDataValue, position);
    }

    private BigDecimal updateOrdered_(IEntityAttribute orderField, AbstractData dropOrder, int position) {
        BigDecimal dropDecimal = dropOrder.getAsCurrency();
        if (orderField.getColumnType() == ColumnModelType.BIGDECIMAL) {
            BigDecimal remainder = dropDecimal.remainder(BigDecimal.TEN);
            int digit = remainder.intValue() == 0 ? 1 : remainder.intValue();
            BigDecimal offsetOrder = BigDecimal.valueOf(digit).divide(BigDecimal.valueOf(2L), orderField.getDecimal(), RoundingMode.HALF_UP);
            if (position == -1) {
                return dropDecimal.subtract(offsetOrder);
            }
            if (position == 1) {
                return dropDecimal.add(offsetOrder);
            }
        }
        return dropDecimal;
    }

    private IEntityRow[] getDropRangeRows(IUnitTreeContext treeContext, IEntityRow dropNodeRow, int position) {
        IEntityTable entityTable = this.entityDataQuery.makeIEntityTable(treeContext);
        IEntityRow parent = entityTable.findByEntityKey(dropNodeRow.getParentEntityKey());
        List rangeRows = parent == null ? entityTable.getRootRows() : entityTable.getChildRows(parent.getEntityKeyData());
        for (int idx = 0; idx < rangeRows.size(); ++idx) {
            if (!((IEntityRow)rangeRows.get(idx)).getEntityKeyData().equals(dropNodeRow.getEntityKeyData())) continue;
            if (position == -1) {
                if (idx == 0) {
                    return new IEntityRow[]{null, (IEntityRow)rangeRows.get(idx)};
                }
                return new IEntityRow[]{(IEntityRow)rangeRows.get(idx - 1), (IEntityRow)rangeRows.get(idx)};
            }
            if (position != 1) continue;
            if (idx == rangeRows.size() - 1) {
                return new IEntityRow[]{(IEntityRow)rangeRows.get(idx), null};
            }
            return new IEntityRow[]{(IEntityRow)rangeRows.get(idx), (IEntityRow)rangeRows.get(idx + 1)};
        }
        return new IEntityRow[]{null, null};
    }

    private Map<String, Object> responseObj(EntityUpdateResult entityUpdateResult, IEntityRow dragNodeRow) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        if (entityUpdateResult.getCodeToKey().containsKey(dragNodeRow.getEntityKeyData())) {
            response.put(STATUE_CODE, 200);
            response.put(ERROR_MSG, "\u4fee\u6539\u6210\u529f\uff01\uff01");
            BaseNodeDataImpl locateNode = new BaseNodeDataImpl();
            locateNode.put("key", (Object)dragNodeRow.getEntityKeyData());
            response.put(LOCATE_NODE, locateNode);
        } else {
            response.put(STATUE_CODE, 500);
            response.put(ERROR_MSG, entityUpdateResult.getCheckResult().getMessage());
        }
        return response;
    }
}

