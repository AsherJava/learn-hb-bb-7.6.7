/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme
 */
package com.jiuiqi.nr.unit.treebase.context;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeGroupField;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeShowTagsOptions;
import com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import java.util.List;

public interface IUnitTreeContextWrapper {
    public boolean isOpenWorkFlow(FormSchemeDefine var1);

    public boolean isOpenTerminal(FormSchemeDefine var1);

    public boolean isOpenFillReport(FormSchemeDefine var1);

    public boolean isShowTimeDeadline(FormSchemeDefine var1);

    public boolean canAddDimension(TaskDefine var1);

    public boolean hasDimGroupConfig(TaskDefine var1);

    public boolean canDisplayFMDMAttributes(FormSchemeDefine var1, IEntityDefine var2, IEntityQueryPloy var3);

    public List<IFMDMAttribute> getCationFields(FormSchemeDefine var1, IEntityDefine var2, IEntityQueryPloy var3);

    public List<IFMDMAttribute> getFMDMShowAttribute(FormSchemeDefine var1, IEntityDefine var2, IEntityQueryPloy var3);

    public boolean hasFMDMFormDefine(FormSchemeDefine var1);

    public boolean canShowNodeTags(UnitTreeShowTagsOptions var1);

    public boolean hasUnitAuditOperation(String var1, String var2, String var3, String var4);

    public boolean hasUnitEditOperation(String var1, String var2, String var3, String var4);

    public String openTerminalRole(TaskDefine var1);

    public UnitTreeGroupField getDimGroupFieldConfig(TaskDefine var1);

    public IEntityRefer getBBLXEntityRefer(IEntityDefine var1);

    public IconSourceScheme getBBLXIConSourceScheme(IEntityDefine var1);

    public List<String> getReportEntityDimensionName(FormSchemeDefine var1);

    public String getDimensionName(String var1);

    public DimensionValueSet buildDimensionValueSet(IUnitTreeContext var1);

    public boolean isOpenADJUST(TaskDefine var1);

    public boolean canLoadWorkFlowState(IUnitTreeContext var1);

    public boolean isTreeExpandAllLevel(IUnitTreeContext var1);

    public String[] queryDimAttributeCode(IUnitTreeContext var1);
}

