/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.datastatus.constant.DataStatus
 *  com.jiuqi.nr.datastatus.facade.service.IDataStatusService
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodeicon.IconCategory
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 */
package com.jiuiqi.nr.unit.treebase.node.builder;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.datastatus.constant.DataStatus;
import com.jiuqi.nr.datastatus.facade.service.IDataStatusService;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodeicon.IconCategory;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FillReportNodeDataBuilder
implements IUnitTreeNodeBuilder {
    protected IUnitTreeContext context;
    protected IUnitTreeNodeBuilder baseNodeBuilder;
    protected IconSourceProvider iconProvider;
    protected IDataStatusService dataStatusService;
    protected Set<String> statusMdCodes;

    public FillReportNodeDataBuilder(IUnitTreeContext context, IUnitTreeNodeBuilder baseNodeBuilder, IconSourceProvider iconProvider) {
        this.context = context;
        this.iconProvider = iconProvider;
        this.baseNodeBuilder = baseNodeBuilder;
        this.dataStatusService = (IDataStatusService)BeanUtil.getBean(IDataStatusService.class);
    }

    @Override
    public void beforeCreateITreeNode(List<IEntityRow> rows) {
        this.baseNodeBuilder.beforeCreateITreeNode(rows);
        this.statusMdCodes = this.batchQueryRowStatus(rows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
    }

    @Override
    public ITree<IBaseNodeData> buildTreeNode(IEntityRow row) {
        ITree<IBaseNodeData> node = this.baseNodeBuilder.buildTreeNode(row);
        String stateCode = this.readStateCode(node.getKey());
        this.setNodeIcon(node, stateCode);
        return node;
    }

    public Set<String> batchQueryRowStatus(List<String> nodeKeys) {
        String formSchemeKey = this.context.getFormScheme().getKey();
        List filledUnit = this.dataStatusService.getFilledUnit(formSchemeKey, this.getDimensionCollection(nodeKeys));
        return new HashSet<String>(filledUnit);
    }

    public String readStateCode(String nodeKey) {
        return this.statusMdCodes == null ? DataStatus.NOTFILL.toString() : (this.statusMdCodes.contains(nodeKey) ? DataStatus.FILLED.toString() : DataStatus.NOTFILL.toString());
    }

    private void setNodeIcon(ITree<IBaseNodeData> node, String stateCode) {
        String statusIconKey = this.iconProvider.getIconKey(IconCategory.STATUS_ICONS, stateCode);
        if (null != statusIconKey) {
            node.setIcons((String[])JavaBeanUtils.appendElement((Object[])node.getIcons(), (Object[])new String[]{statusIconKey}));
        }
    }

    private DimensionCollection getDimensionCollection(List<String> nodeKeys) {
        DimensionCollectionBuilder dimensionCollectionBuilder = new DimensionCollectionBuilder();
        IEntityDefine entityDefine = this.context.getEntityDefine();
        dimensionCollectionBuilder.setDWValue(entityDefine.getDimensionName(), entityDefine.getId(), nodeKeys.toArray());
        IPeriodEntity periodEntity = this.context.getPeriodEntity();
        dimensionCollectionBuilder.setEntityValue(periodEntity.getDimensionName(), periodEntity.getKey(), new Object[]{this.context.getPeriod()});
        if (this.context.getDimValueSet().containsKey("ADJUST")) {
            dimensionCollectionBuilder.setEntityValue("ADJUST", "ADJUST", new Object[]{this.context.getDimValueSet().get("ADJUST").getValue()});
        }
        return dimensionCollectionBuilder.getCollection();
    }

    public DimensionValueSet getDimensionValueSet(Map<String, DimensionValue> dimensionSet, List<String> nodeKeys) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (dimensionSet == null) {
            return dimensionValueSet;
        }
        for (DimensionValue value : dimensionSet.values()) {
            if (value.getValue() == null) continue;
            String[] values = value.getValue().split(";");
            if (values.length == 1 || values.length == 0) {
                dimensionValueSet.setValue(value.getName(), (Object)value.getValue());
                continue;
            }
            List<String> valueList = Arrays.asList(values);
            dimensionValueSet.setValue(value.getName(), valueList);
        }
        IEntityDefine entityDefine = this.context.getEntityDefine();
        dimensionValueSet.setValue(entityDefine.getDimensionName(), nodeKeys);
        return dimensionValueSet;
    }
}

