/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.multcheck2.service;

import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.service.dto.MCOrgTreeDTO;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import java.util.List;

public interface IMCOrgService {
    public List<String> getOrgsBySchemePeriod(MultcheckScheme var1, String var2) throws Exception;

    public List<String> getOrgsByTaskPeriodOrg(String var1, String var2, String var3) throws Exception;

    public List<MCLabel> getOrgLabels(String var1, String var2, String var3, List<String> var4) throws Exception;

    public IEntityTable getTreeEntityTable(MultcheckScheme var1, String var2, List<String> var3) throws Exception;

    public IEntityTable getTreeEntityTable(String var1, String var2, String var3, String var4) throws Exception;

    public IEntityTable getTreeEntityTable(String var1, String var2, String var3, List<String> var4, String var5) throws Exception;

    public MCOrgTreeDTO getOrgTreeBySchemePeriod(MultcheckScheme var1, String var2) throws Exception;

    public MCOrgTreeDTO getOrgTreeByTaskPeriodOrg(String var1, String var2, String var3, List<String> var4, String var5) throws Exception;

    public List<String> getLeafOrgsByTaskPeriodOrg(String var1, String var2, String var3, List<String> var4) throws Exception;
}

