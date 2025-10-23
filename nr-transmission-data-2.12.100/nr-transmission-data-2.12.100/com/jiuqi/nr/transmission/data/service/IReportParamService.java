/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataentry.bean.FormsQueryResult
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nvwa.subsystem.core.model.SubServer
 */
package com.jiuqi.nr.transmission.data.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataentry.bean.FormsQueryResult;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.transmission.data.domain.TaskNodeParam;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import com.jiuqi.nr.transmission.data.intf.DimInfoParam;
import com.jiuqi.nr.transmission.data.intf.EntityInfoParam;
import com.jiuqi.nr.transmission.data.vo.ReselectPeriodVO;
import com.jiuqi.nvwa.subsystem.core.model.SubServer;
import java.util.List;

public interface IReportParamService {
    public List<TaskNodeParam> initTask(List<String> var1);

    public List<String> getTaskList();

    public List<TaskNodeParam> initUser(List<String> var1);

    public List<String> checkUser(List<String> var1);

    public FormsQueryResult getForms(String var1);

    public List<EntityInfoParam> getEntityList(DimensionValueSet var1, String var2, AuthorityType var3, Boolean var4);

    public List<DimInfoParam> getDimValues(String var1, String var2);

    public IEntityTable getEntityList(DimensionValueSet var1, String var2);

    public String doLogHelperMessage(SyncSchemeParamDTO var1);

    public ReselectPeriodVO reselectPeriod(ReselectPeriodVO var1);

    public ReselectPeriodVO getPeriodValue(int var1, String var2);

    public SubServer getParentServeNode() throws Exception;
}

