/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.multcheck2.dynamic;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.multcheck2.service.dto.MCOrgTreeDTO;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import java.util.List;
import java.util.Map;

public interface IMCContextService {
    public List<String> getDynamicFieldsByTask(String var1);

    public List<String> getDynamicDimNamesForPage(String var1);

    public IEntityTable getTreeEntityTable(String var1, String var2, String var3, String var4) throws Exception;

    public IEntityTable getTreeEntityTable(String var1, String var2, String var3, String var4, List<String> var5) throws Exception;

    public MCOrgTreeDTO getOrgTreeByTaskPeriodOrg(String var1, String var2, String var3, String var4, List<String> var5) throws Exception;

    public List<MCLabel> getOrgLabels(String var1, String var2, String var3, List<String> var4) throws Exception;

    public DimensionCollection buildDimensionCollection(String var1, String var2, List<String> var3, Map<String, DimensionValue> var4) throws Exception;
}

