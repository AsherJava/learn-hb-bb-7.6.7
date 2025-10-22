/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.service.ITreeNodeIconColorService
 *  com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState
 *  com.jiuqi.nr.bpm.de.dataflow.util.WorkflowReportDimService
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodeicon.IconCategory
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 *  com.jiuqi.util.StringUtils
 */
package com.jiuiqi.nr.unit.treebase.node.builder;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.service.ITreeNodeIconColorService;
import com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState;
import com.jiuqi.nr.bpm.de.dataflow.util.WorkflowReportDimService;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodeicon.IconCategory;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import com.jiuqi.util.StringUtils;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkFlowNodeDataBuilder
implements IUnitTreeNodeBuilder {
    private static final String KEY_OF_STATUS = "status";
    private static final String KEY_OF_STATE_TITLE = "stateTitle";
    private final IUnitTreeContext context;
    private IconSourceProvider iconProvider;
    private IUnitTreeNodeBuilder baseNodeBuilder;
    protected boolean isShowStateIcon;
    protected DimensionValueSet dimValueSet;
    protected String dimAttributeCode;
    protected String dimAttributeDimensionName;
    protected TreeState flowWorkState = (TreeState)SpringBeanUtils.getBean(TreeState.class);
    private IUnitTreeContextWrapper contextWrapper = (IUnitTreeContextWrapper)SpringBeanUtils.getBean(IUnitTreeContextWrapper.class);
    private IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
    protected Map<DimensionValueSet, ActionStateBean> statusMap;

    public WorkFlowNodeDataBuilder(IUnitTreeContext context, IUnitTreeNodeBuilder baseNodeBuilder, IconSourceProvider iconProvider) {
        this.context = context;
        this.iconProvider = iconProvider;
        this.baseNodeBuilder = baseNodeBuilder;
        this.dimValueSet = this.createDimensionValueSet(context);
        this.isShowStateIcon = this.isNodeIconType();
        this.queryDimAttributeCode(context);
    }

    private boolean isNodeIconType() {
        ITreeNodeIconColorService flowWorkStateIcon = (ITreeNodeIconColorService)BeanUtil.getBean(ITreeNodeIconColorService.class);
        return flowWorkStateIcon.isNodeIconType();
    }

    private DimensionValueSet createDimensionValueSet(IUnitTreeContext context) {
        return this.contextWrapper.buildDimensionValueSet(context);
    }

    public void queryDimAttributeCode(IUnitTreeContext context) {
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        WorkflowReportDimService workflowReportDimService = (WorkflowReportDimService)SpringBeanUtils.getBean(WorkflowReportDimService.class);
        TaskDefine taskDefine = context.getTaskDefine();
        List dataSchemeDimension = workflowReportDimService.getDataDimension(taskDefine.getKey());
        for (DataDimension dimension : dataSchemeDimension) {
            boolean corporate = this.isCorporate(taskDefine, dimension, dataSchemeDimension);
            if (!corporate) continue;
            this.dimAttributeDimensionName = entityMetaService.getDimensionName(dimension.getDimKey());
            IEntityModel dwEntityModel = entityMetaService.getEntityModel(taskDefine.getDw());
            String dimAttribute = dimension.getDimAttribute();
            if (dimAttribute == null) continue;
            IEntityAttribute attribute = dwEntityModel.getAttribute(dimAttribute);
            this.dimAttributeCode = attribute.getCode();
        }
    }

    public boolean isCorporate(TaskDefine taskDefine, DataDimension dimension, List<DataDimension> dataSchemeDimension) {
        String dimAttribute = dimension.getDimAttribute();
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        IEntityModel dwEntityModel = entityMetaService.getEntityModel(taskDefine.getDw());
        IEntityAttribute attribute = dwEntityModel.getAttribute(dimAttribute);
        DataDimension report = dataSchemeDimension.stream().filter(dataDimension -> dimension.getDimKey().equals(dataDimension.getDimKey())).findFirst().orElse(null);
        String dimReferAttr = report == null ? null : report.getDimAttribute();
        return DimensionType.DIMENSION == dimension.getDimensionType() && attribute != null && !attribute.isMultival() && StringUtils.isNotEmpty((String)dimReferAttr);
    }

    @Override
    public void beforeCreateITreeNode(List<IEntityRow> rows) {
        this.baseNodeBuilder.beforeCreateITreeNode(rows);
        String mainDimName = this.context.getEntityDefine().getDimensionName();
        this.dimValueSet.setValue(mainDimName, rows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
        String formSchemeKey = this.context.getFormScheme().getKey();
        this.statusMap = this.flowWorkState.getWorkflowUploadState(this.dimValueSet, null, formSchemeKey);
    }

    @Override
    public ITree<IBaseNodeData> buildTreeNode(IEntityRow row) {
        ActionStateBean actionStateBean;
        ITree<IBaseNodeData> node = this.baseNodeBuilder.buildTreeNode(row);
        String attributeValue = "";
        if (StringUtils.isNotEmpty((String)this.dimAttributeCode)) {
            AbstractData value = row.getValue(this.dimAttributeCode);
            attributeValue = value.getAsString();
        }
        if ((actionStateBean = this.readStateBean(node.getKey(), attributeValue)) != null) {
            ((IBaseNodeData)node.getData()).put(KEY_OF_STATUS, (Object)actionStateBean.getCode());
            ((IBaseNodeData)node.getData()).put(KEY_OF_STATE_TITLE, (Object)actionStateBean.getTitile());
            this.setNodeIcon(node, actionStateBean.getCode());
        }
        return node;
    }

    public ActionStateBean readStateBean(String nodeKey, String attributeValue) {
        this.dimValueSet.setValue(this.context.getEntityDefine().getDimensionName(), (Object)nodeKey);
        if (StringUtils.isNotEmpty((String)attributeValue)) {
            this.dimValueSet.setValue(this.dimAttributeDimensionName, (Object)attributeValue);
        }
        DimensionValueSet dimensionValueSet = this.buildDimensionValueSet(this.context, this.dimValueSet);
        return this.statusMap.get(dimensionValueSet);
    }

    private DimensionValueSet buildDimensionValueSet(IUnitTreeContext context, DimensionValueSet dimensionValue) {
        Object value1;
        boolean contains;
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        TaskDefine taskDefine = context.getTaskDefine();
        String dw = taskDefine.getDw();
        String dwDimensionName = this.entityMetaService.getDimensionName(dw);
        dimensionValueSet.setValue(dwDimensionName, dimensionValue.getValue(dwDimensionName));
        dimensionValueSet.setValue("DATATIME", dimensionValue.getValue("DATATIME"));
        Object value = dimensionValue.getValue("ADJUST");
        if (null != value) {
            dimensionValueSet.setValue("ADJUST", value);
        }
        DimensionSet dimensionSet = dimensionValue.getDimensionSet();
        if (this.dimAttributeDimensionName != null && (contains = dimensionSet.contains(this.dimAttributeDimensionName)) && null != (value1 = dimensionValue.getValue(this.dimAttributeDimensionName))) {
            dimensionValueSet.setValue(this.dimAttributeDimensionName, value1);
        }
        return dimensionValueSet;
    }

    private void setNodeIcon(ITree<IBaseNodeData> node, String stateCode) {
        String statusIconKey;
        if (this.isShowStateIcon && null != (statusIconKey = this.iconProvider.getIconKey(IconCategory.STATUS_ICONS, stateCode))) {
            node.setIcons((String[])JavaBeanUtils.appendElement((Object[])node.getIcons(), (Object[])new String[]{statusIconKey}));
        }
    }
}

