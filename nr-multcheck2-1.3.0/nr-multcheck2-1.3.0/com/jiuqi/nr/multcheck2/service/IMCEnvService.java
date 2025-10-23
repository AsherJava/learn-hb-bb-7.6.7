/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.multcheck2.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.multcheck2.bean.MCHistoryScheme;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider;
import com.jiuqi.nr.multcheck2.web.vo.MCHistorySchemeVO;
import com.jiuqi.nr.multcheck2.web.vo.MultcheckSchemeVO;
import com.jiuqi.nr.multcheck2.web.vo.OrgExportVO;
import com.jiuqi.nr.multcheck2.web.vo.TaskTreeNodeVO;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public interface IMCEnvService {
    public void addScheme(MultcheckSchemeVO var1) throws Exception;

    public void initReportDim(String var1, String var2);

    public void modifyScheme(MultcheckSchemeVO var1) throws JQException;

    public void copyScheme(String var1, MultcheckSchemeVO var2) throws JQException;

    public List<MultcheckSchemeVO> getSchemeAndOrgCountByFormScheme(String var1);

    public List<MultcheckSchemeVO> getSchemeAndOrgCountByTask(String var1);

    public List<MultcheckScheme> getSchemeListByTask(String var1);

    public MultcheckSchemeVO getSchemeByKey(String var1);

    public ITree<TaskTreeNodeVO> buildRootNode(String var1, boolean var2) throws Exception;

    public List<TaskDefine> getAllTaskDefines();

    public IMultcheckItemProvider getProvider(String var1);

    public List<IMultcheckItemProvider> getProviderList();

    public DimensionCollection buildDimensionCollection(String var1, String var2, List<String> var3, Map<String, DimensionValue> var4) throws Exception;

    public MCHistoryScheme addHistoryScheme(MCHistoryScheme var1);

    public String getHisSchemeConfigByKey(String var1);

    public List<MCHistorySchemeVO> getHistorySchemeByUserSource(String var1) throws Exception;

    public void delHistorySchemeByKey(String var1);

    public void batchDeleteHistory(List<String> var1);

    public void getNoDimTasks(List<TaskDefine> var1);

    public void exportResult(List<OrgExportVO> var1, HttpServletResponse var2) throws JQException;
}

