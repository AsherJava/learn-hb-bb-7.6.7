/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.formula.intf.AggregatedNode
 */
package com.jiuqi.va.biz.ruler;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.impl.value.ListContainerImpl;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataRowState;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.biz.ruler.ModelDataNode;
import com.jiuqi.va.biz.ruler.ModelNode;
import com.jiuqi.va.biz.ruler.common.consts.ModelDataConsts;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.formula.intf.AggregatedNode;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class CountDataNode
extends ModelDataNode
implements AggregatedNode {
    private static final long serialVersionUID = -3989423243559812341L;
    private IASTNode conditionNode;
    private String countType;
    private Map<String, ValueType> paramNodesType;

    public CountDataNode(Token token, ModelNode modelNode, String countType, IASTNode conditionNode, Map<String, ValueType> paramNodesType) {
        super(token, modelNode);
        this.countType = countType;
        this.conditionNode = conditionNode;
        this.paramNodesType = paramNodesType;
    }

    public IASTNode getChild(int index) {
        if (index < 0 || index >= this.childrenSize()) {
            throw new IndexOutOfBoundsException();
        }
        return this.conditionNode;
    }

    public int childrenSize() {
        return this.conditionNode == null ? 0 : 1;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        ModelDataContext dataContext = (ModelDataContext)context;
        DataImpl dataImpl = dataContext.model.getPlugins().get(DataImpl.class);
        DataTableImpl dataTableImpl = (DataTableImpl)((DataTableNodeContainerImpl)dataImpl.getTables()).get(this.getModelNode().tableDefine.getName());
        List<Object> pIds = this.gatherPids(dataImpl, dataContext);
        ListContainer<DataRowImpl> listContainer = dataTableImpl.getRows();
        if (listContainer.size() == 0 && ModelDataConsts.CONSTANT_NODE_COUNT[5].equals(this.countType)) {
            return dataContext.valueOf(null, this.getType((IContext)dataContext));
        }
        Object rowId = null;
        ArrayList<BigDecimal> numbers = new ArrayList<BigDecimal>();
        ArrayList<Object> resultList = new ArrayList<Object>();
        Integer dataType = null;
        ModelDataContext tmpDataContext = new ModelDataContext(dataContext.model);
        if (dataContext.getParams() != null) {
            dataContext.getParams().forEach((k, w) -> tmpDataContext.put((String)k, w));
        }
        for (int row = 0; row < listContainer.size(); ++row) {
            rowId = dataTableImpl.getParentId().equals(dataImpl.getMasterTable().getId()) ? listContainer.get(row).getMasterId() : listContainer.get(row).getGroupId();
            if (!pIds.contains(rowId)) continue;
            String curtable = this.getModelNode().tableDefine.getName();
            DataRowImpl curRow = listContainer.get(row);
            tmpDataContext.put(curtable, curRow);
            if (!this.checkCondition(tmpDataContext, curtable, curRow)) continue;
            if (ModelDataConsts.CONSTANT_NODE_COUNT[6].equals(this.countType)) {
                if (dataType == null) {
                    dataType = this.getBaseType((IContext)tmpDataContext);
                }
                resultList.add(tmpDataContext.valueOf(this.getModelNode().evaluate((IContext)tmpDataContext), dataType));
                continue;
            }
            if (ModelDataConsts.CONSTANT_NODE_COUNT[5].equals(this.countType)) {
                if (dataType == null) {
                    dataType = this.getType((IContext)tmpDataContext);
                }
                return tmpDataContext.valueOf(this.getModelNode().evaluate((IContext)tmpDataContext), dataType);
            }
            if (ModelDataConsts.CONSTANT_NODE_COUNT[4].equals(this.countType) || DataRowState.UNUSED.equals((Object)listContainer.get(row).getState()) || DataRowState.DELETED.equals((Object)listContainer.get(row).getState())) {
                numbers.add(BigDecimal.valueOf(0L));
                continue;
            }
            Object evaluate = this.getModelNode().evaluate((IContext)tmpDataContext);
            if (evaluate == null) continue;
            numbers.add(Convert.cast(evaluate, BigDecimal.class));
        }
        if (ModelDataConsts.CONSTANT_NODE_COUNT[6].equals(this.countType)) {
            if (resultList.size() == 0) {
                return new ArrayData(this.getBaseType((IContext)tmpDataContext), resultList);
            }
            return new ArrayData(dataType.intValue(), resultList);
        }
        return dataContext.valueOf(ModelDataConsts.CONSTANT_NODE_COUNT[5].equals(this.countType) ? null : this.executeCount(numbers), this.getType((IContext)dataContext));
    }

    private boolean checkCondition(ModelDataContext dataContext, String curTableName, DataRowImpl curRow) {
        if (this.conditionNode == null) {
            return true;
        }
        boolean isModelNode = false;
        for (IASTNode node : this.conditionNode) {
            if (!(node instanceof ModelNode)) continue;
            isModelNode = true;
            break;
        }
        try {
            ModelDataContext context = new ModelDataContext(dataContext.model);
            if (isModelNode) {
                context.put(curTableName, curRow);
            } else {
                curRow.getData(false).forEach((k, w) -> {
                    if (w instanceof Map) {
                        context.put((String)k, curRow.getValue((String)k));
                    } else {
                        context.put((String)k, w);
                    }
                    String tableFieldName = curTableName + "_" + k;
                    if (this.paramNodesType.containsKey(tableFieldName)) {
                        context.setFieldValueType((String)k, this.paramNodesType.get(tableFieldName));
                    }
                });
                context.put("model_param_node_tablename", curTableName);
            }
            return Convert.cast(this.conditionNode.evaluate((IContext)context), Boolean.class);
        }
        catch (SyntaxException e) {
            throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.countdatanode.executefilterexception", new Object[]{this.conditionNode.getToken().toString()}), e);
        }
    }

    public int getBaseType(IContext context) throws SyntaxException {
        return super.getType(context);
    }

    @Override
    public int getType(IContext context) throws SyntaxException {
        if (ModelDataConsts.CONSTANT_NODE_COUNT[4].equals(this.countType)) {
            return 3;
        }
        if (ModelDataConsts.CONSTANT_NODE_COUNT[6].equals(this.countType)) {
            return 11;
        }
        return super.getType(context);
    }

    private Object executeCount(List<BigDecimal> numbers) {
        if (CollectionUtils.isEmpty(numbers)) {
            return 0;
        }
        switch (this.countType) {
            case "SUM": {
                return numbers.stream().reduce((sum, cur) -> sum.add((BigDecimal)cur)).orElse(BigDecimal.ZERO);
            }
            case "AGE": {
                return numbers.stream().reduce((sum, cur) -> sum.add((BigDecimal)cur)).orElse(BigDecimal.ZERO).doubleValue() / (double)numbers.size();
            }
            case "MAX": {
                return numbers.stream().reduce((old, cur) -> old.compareTo((BigDecimal)cur) == 1 ? old : cur).orElse(BigDecimal.ZERO);
            }
            case "MIN": {
                return numbers.stream().reduce((old, cur) -> old.compareTo((BigDecimal)cur) == -1 ? old : cur).orElse(BigDecimal.ZERO);
            }
        }
        return numbers.size();
    }

    private List<Object> gatherPids(DataImpl dataImpl, ModelDataContext dataContext) {
        ArrayList<Object> idList = new ArrayList<Object>();
        DataTableImpl dataTableImpl = (DataTableImpl)((DataTableNodeContainerImpl)dataImpl.getTables()).get(this.getModelNode().tableDefine.getName());
        ArrayList<DataTableImpl> pTables = new ArrayList<DataTableImpl>();
        DataTableImpl pTable = this.getPTable(dataImpl, dataTableImpl, dataContext, pTables);
        idList.add(Convert.cast(dataContext.get(pTable.getName()), DataRow.class).getId());
        if (pTables.isEmpty()) {
            return idList;
        }
        if (pTable.getId().equals(dataImpl.getMasterTable().getId())) {
            Object tempId = idList.get(0);
            idList.clear();
            idList.addAll(pTable.getRows().stream().filter(o -> o.getMasterId().equals(tempId)).map(o -> o.getId()).collect(Collectors.toList()));
            pTables.remove(0);
        }
        pTables.forEach(table -> {
            ArrayList tempList = new ArrayList();
            tempList.addAll(idList);
            idList.clear();
            idList.addAll(table.getRows().stream().filter(o -> tempList.contains(o.getGroupId())).map(o -> o.getId()).collect(Collectors.toList()));
        });
        return idList;
    }

    private DataTableImpl getPTable(DataImpl dataImpl, DataTableImpl dataTableImpl, ModelDataContext dataContext, List<DataTableImpl> pTables) {
        DataTableImpl impl = ((ListContainerImpl)((Object)dataImpl.getTables())).stream().filter(o -> o.getId().equals(dataTableImpl.getParentId())).findFirst().orElse(null);
        if (impl == null) {
            throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.countdatanode.nospecifyexecuteline"));
        }
        if (dataContext.hasKey(impl.getName())) {
            return impl;
        }
        if (impl.getParentId() == null) {
            dataContext.put(impl.getName(), impl.getRows().get(0));
            return impl;
        }
        pTables.add(impl);
        return this.getPTable(dataImpl, impl, dataContext, pTables);
    }

    public void toString(StringBuilder buffer) {
        buffer.append(this.countType).append("(");
        this.getModelNode().toString(buffer);
        if (this.conditionNode != null) {
            buffer.append(",");
            buffer.append(this.conditionNode.getChild(0).toString());
        }
        buffer.append(")");
    }
}

