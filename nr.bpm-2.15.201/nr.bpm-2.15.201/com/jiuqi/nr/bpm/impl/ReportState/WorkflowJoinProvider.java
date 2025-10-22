/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider
 *  com.jiuqi.nvwa.dataengine.intf.JoinType
 *  com.jiuqi.nvwa.dataengine.intf.SqlJoinItem
 *  com.jiuqi.nvwa.dataengine.intf.SqlJoinOneItem
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.bpm.impl.ReportState;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.bpm.de.dataflow.util.WorkflowReportDimService;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider;
import com.jiuqi.nvwa.dataengine.intf.JoinType;
import com.jiuqi.nvwa.dataengine.intf.SqlJoinItem;
import com.jiuqi.nvwa.dataengine.intf.SqlJoinOneItem;
import com.jiuqi.util.StringUtils;
import java.io.Serializable;
import java.util.List;

public class WorkflowJoinProvider
implements Serializable,
ISqlJoinProvider {
    private static final long serialVersionUID = 1L;
    private String MD_CODE = "MDCODE";
    private String MD_ORG_CODE = "ORGCODE";
    private TaskDefine taskDefine;
    private String dimAttributeDimensionName;
    private String dimAttributeCode;
    private IEntityMetaService entityMetaService;

    public WorkflowJoinProvider(TaskDefine taskDefine) {
        this.taskDefine = taskDefine;
        this.entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
    }

    public SqlJoinItem getSqlJoinItem(String srcTable, String desTable) {
        SqlJoinItem joinItem = new SqlJoinItem(srcTable, desTable);
        joinItem.setJoinType(JoinType.Right);
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(this.taskDefine.getDw());
        IEntityAttribute bizKeyField = dwEntityModel.getBizKeyField();
        SqlJoinOneItem recidItem = new SqlJoinOneItem(this.MD_CODE, bizKeyField.getCode());
        joinItem.addJoinItem(recidItem);
        this.getCorporateEntityId(this.taskDefine);
        if (this.dimAttributeDimensionName != null && this.dimAttributeCode != null) {
            SqlJoinOneItem item = new SqlJoinOneItem(this.dimAttributeDimensionName, this.dimAttributeCode);
            joinItem.addJoinItem(item);
        }
        return joinItem;
    }

    public JoinType getDefaultJoinType() {
        return JoinType.Right;
    }

    private void getCorporateEntityId(TaskDefine taskDefine) {
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        WorkflowReportDimService workflowReportDimService = (WorkflowReportDimService)SpringBeanUtils.getBean(WorkflowReportDimService.class);
        List<DataDimension> dataSchemeDimension = workflowReportDimService.getDataDimension(taskDefine.getKey());
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
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
        IEntityAttribute attribute = dwEntityModel.getAttribute(dimAttribute);
        DataDimension report = dataSchemeDimension.stream().filter(dataDimension -> dimension.getDimKey().equals(dataDimension.getDimKey())).findFirst().orElse(null);
        String dimReferAttr = report == null ? null : report.getDimAttribute();
        return DimensionType.DIMENSION == dimension.getDimensionType() && attribute != null && !attribute.isMultival() && StringUtils.isNotEmpty((String)dimReferAttr);
    }
}

