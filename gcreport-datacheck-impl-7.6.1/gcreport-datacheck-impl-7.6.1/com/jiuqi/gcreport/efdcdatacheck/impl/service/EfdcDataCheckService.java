/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckConfigVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultGroupVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.tree.FormTree
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.service;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckConfigVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultGroupVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.CheckEfdcFunctionService;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.tree.FormTree;
import java.util.List;
import java.util.Map;

public interface EfdcDataCheckService
extends CheckEfdcFunctionService {
    public void saveEfdcDataCheckConfig(String var1, List<EfdcCheckConfigVO> var2) throws Exception;

    public BusinessResponseEntity<List<EfdcCheckConfigVO>> getEfdcDataCheckConfig(String var1) throws Exception;

    public EfdcCheckResultGroupVO processEfdcDataCheckResultGroup(NpContext var1, GcFormOperationInfo var2);

    public FormTree queryDataCheckConfigForms(String var1, String var2);

    public FormTree queryDataCheckConfigFormsByReport(String var1, Map<String, DimensionValue> var2);
}

