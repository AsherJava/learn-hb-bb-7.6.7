/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.util.StringUtils
 */
package com.jiuiqi.nr.unit.treebase.entity.filter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.filter.IFilterEntityRow;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkFlowEntityRowFilter
implements IFilterEntityRow {
    private List<String> uploadStatus;
    private final IUnitTreeContext context;
    private final DimensionValueSet dimValueSet;
    private final IUnitTreeEntityDataQuery entityDataQuery;
    protected String dimAttributeCode;
    protected String dimAttributeDimensionName;
    private final Map<DimensionValueSet, ActionStateBean> allStatusMap = new HashMap<DimensionValueSet, ActionStateBean>();

    public WorkFlowEntityRowFilter(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery) {
        this.context = context;
        this.entityDataQuery = entityDataQuery;
        this.dimValueSet = this.buildDimensionValueSet(context);
        this.init(context);
    }

    public WorkFlowEntityRowFilter(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery, List<String> uploadStatus) {
        this(context, entityDataQuery);
        this.uploadStatus = uploadStatus;
    }

    private void init(IUnitTreeContext context) {
        IUnitTreeContextWrapper contextWrapper = (IUnitTreeContextWrapper)SpringBeanUtils.getBean(IUnitTreeContextWrapper.class);
        String[] strings = contextWrapper.queryDimAttributeCode(context);
        if (strings != null) {
            this.dimAttributeDimensionName = strings[0];
            this.dimAttributeCode = strings[1];
        }
    }

    @Override
    public boolean matchRow(IEntityRow row) {
        if (StringUtils.isNotEmpty((String)this.dimAttributeCode)) {
            AbstractData value = row.getValue(this.dimAttributeCode);
            this.dimValueSet.setValue(this.dimAttributeDimensionName, (Object)value.getAsString());
        }
        String mainDimName = this.context.getEntityDefine().getDimensionName();
        this.dimValueSet.setValue(mainDimName, (Object)row.getEntityKeyData());
        ActionStateBean actionStateBean = this.allStatusMap.get(this.dimValueSet);
        return actionStateBean != null && this.isMatch(actionStateBean);
    }

    @Override
    public void setMatchRangeRows(List<IEntityRow> rangeRows) {
        Map<DimensionValueSet, ActionStateBean> statusMap = this.getStatusMap(rangeRows);
        this.allStatusMap.putAll(statusMap);
    }

    @Override
    public List<IEntityRow> getMatchResultSet(List<String> uploadStatus) {
        IEntityTable dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        List oriRows = dataTable.getAllRows();
        String mainDimName = this.context.getEntityDefine().getDimensionName();
        ArrayList<IEntityRow> filterRows = new ArrayList<IEntityRow>();
        Map<DimensionValueSet, ActionStateBean> statusMap = this.getStatusMap(oriRows);
        for (IEntityRow row : oriRows) {
            if (StringUtils.isNotEmpty((String)this.dimAttributeCode)) {
                AbstractData value = row.getValue(this.dimAttributeCode);
                this.dimValueSet.setValue(this.dimAttributeDimensionName, (Object)value.getAsString());
            }
            this.dimValueSet.setValue(mainDimName, (Object)row.getEntityKeyData());
            ActionStateBean actionStateBean = statusMap.get(this.dimValueSet);
            if (actionStateBean == null || !this.isMatch(actionStateBean)) continue;
            filterRows.add(row);
        }
        return filterRows;
    }

    private Map<DimensionValueSet, ActionStateBean> getStatusMap(List<IEntityRow> entityRows) {
        List entityRowKeys = entityRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        TreeState flowWorkState = (TreeState)SpringBeanUtils.getBean(TreeState.class);
        String mainDimName = this.context.getEntityDefine().getDimensionName();
        this.dimValueSet.setValue(mainDimName, entityRowKeys);
        String formSchemeKey = this.context.getFormScheme().getKey();
        return flowWorkState.getWorkflowUploadState(this.dimValueSet, null, formSchemeKey);
    }

    private DimensionValueSet buildDimensionValueSet(IUnitTreeContext context) {
        IUnitTreeContextWrapper contextWrapper = (IUnitTreeContextWrapper)SpringBeanUtils.getBean(IUnitTreeContextWrapper.class);
        DimensionValueSet dimValueSet = new DimensionValueSet();
        dimValueSet.setValue(context.getPeriodEntity().getDimensionName(), (Object)context.getPeriod());
        Map<String, DimensionValue> dimValueMap = context.getDimValueSet();
        if (dimValueMap != null) {
            List<String> reportEntityDimensionName = contextWrapper.getReportEntityDimensionName(context.getFormScheme());
            for (Map.Entry<String, DimensionValue> entrySet : dimValueMap.entrySet()) {
                if (!reportEntityDimensionName.contains(entrySet.getKey())) continue;
                dimValueSet.setValue(entrySet.getKey(), (Object)entrySet.getValue().getValue());
            }
        }
        return dimValueSet;
    }

    private boolean isMatch(ActionStateBean actionStateBean) {
        String state;
        boolean is_match = true;
        Iterator<String> iterator = this.uploadStatus.iterator();
        while (iterator.hasNext() && !(is_match = (state = iterator.next()).contains(actionStateBean.getCode()))) {
        }
        return is_match;
    }
}

