/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorConfigDetailItemVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorConfigDetailVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorGroupVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorNrSchemeVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorSaveInfoVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorSceneNodeInfoVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorSceneNodeVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorSchemeCopyVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.MonitorVO
 *  com.jiuqi.gcreport.monitor.api.vo.config.NrSchemesVO
 *  com.jiuqi.gcreport.monitor.api.vo.execute.MonitorShowDataVO
 *  com.jiuqi.gcreport.monitor.api.vo.execute.ValueAndLabelVO
 */
package com.jiuqi.gcreport.monitor.impl.service;

import com.jiuqi.gcreport.monitor.api.vo.config.MonitorConfigDetailItemVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorConfigDetailVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorGroupVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorNrSchemeVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorSaveInfoVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorSceneNodeInfoVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorSceneNodeVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorSchemeCopyVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorVO;
import com.jiuqi.gcreport.monitor.api.vo.config.NrSchemesVO;
import com.jiuqi.gcreport.monitor.api.vo.execute.MonitorShowDataVO;
import com.jiuqi.gcreport.monitor.api.vo.execute.ValueAndLabelVO;
import java.util.List;

public interface MonitorConfigService {
    public MonitorGroupVO saveMonitorGroup(MonitorGroupVO var1);

    public MonitorGroupVO modifyMonitorGroup(MonitorGroupVO var1);

    public String delMonitorGroup(String var1);

    public MonitorGroupVO getMonitorGroup(String var1);

    public List<ValueAndLabelVO> getMonitorGroups();

    public MonitorGroupVO getMonitorGroupSchemes();

    public MonitorVO saveScheme(MonitorVO var1);

    public MonitorVO updateScheme(MonitorVO var1);

    public MonitorVO addScheme(MonitorSaveInfoVO var1);

    public MonitorVO deleteScheme(String var1);

    public MonitorVO getScheme(String var1);

    public MonitorSaveInfoVO getMonitorSchemeById(String var1);

    public MonitorNrSchemeVO saveMonitorNrScheme(MonitorNrSchemeVO var1);

    public void saveMonitorNrScheme(List<MonitorNrSchemeVO> var1);

    public MonitorNrSchemeVO deleteMonitorNrScheme(String var1);

    public List<MonitorNrSchemeVO> getMonitorNrSchemeByMonitorId(String var1);

    public List<MonitorSceneNodeVO> getTreeNodes();

    public List<MonitorSceneNodeVO> getTreeNodes(String var1);

    public MonitorConfigDetailVO saveMonitorNodeScheme(MonitorConfigDetailVO var1);

    public void saveMonitorNodeScheme(List<MonitorConfigDetailVO> var1);

    public List<MonitorConfigDetailVO> getMonitorConfigDetailsByMonitorId(String var1);

    public MonitorConfigDetailVO getMonitorConfigDetail(String var1, String var2);

    public void saveNodeDatailItemList(List<MonitorConfigDetailItemVO> var1, String var2);

    public MonitorConfigDetailItemVO saveNodeDatailItem(MonitorConfigDetailItemVO var1);

    public List<MonitorConfigDetailItemVO> getConfigItems(String var1);

    public List<MonitorConfigDetailItemVO> getConfigItems(String var1, String var2);

    public List<MonitorSceneNodeInfoVO> getNodeChilds(String var1);

    public MonitorSceneNodeInfoVO getNodeInfo(String var1);

    public List<NrSchemesVO> getNrSchemes() throws Exception;

    public List<MonitorSceneNodeInfoVO> getMonitorNodesNoTree();

    public List<MonitorShowDataVO> unitFilter();

    public String saveCopyScheme(MonitorSchemeCopyVO var1);

    public List<ValueAndLabelVO> getMonitorOrgTypes();

    public List<NrSchemesVO> getBoundFormSchemes();

    public List<MonitorSceneNodeInfoVO> getMonitorSceneNodes(String var1);
}

