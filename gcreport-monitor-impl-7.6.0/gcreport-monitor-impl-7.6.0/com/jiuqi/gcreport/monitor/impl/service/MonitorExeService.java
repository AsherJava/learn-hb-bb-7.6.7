/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.monitor.api.vo.execute.MonitorExeSchemeVO
 *  com.jiuqi.gcreport.monitor.api.vo.execute.ValueAndLabelVO
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.gcreport.monitor.impl.service;

import com.jiuqi.gcreport.monitor.api.vo.execute.MonitorExeSchemeVO;
import com.jiuqi.gcreport.monitor.api.vo.execute.ValueAndLabelVO;
import com.jiuqi.np.common.exception.JQException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface MonitorExeService {
    public List<ValueAndLabelVO> getNrSchemes() throws JQException;

    public List<ValueAndLabelVO> getNrUnitType();

    public List<ValueAndLabelVO> getMonitorNodes();

    public List<MonitorExeSchemeVO> getMonitorExeSchemes();

    public String addScheme(MonitorExeSchemeVO var1);

    public Boolean checkCode(String var1);

    public MonitorExeSchemeVO getExeScheme(String var1);

    public List<String> getUserConfig();

    public String saveOrModifyUserConfig(List<String> var1);

    public void delScheme(String var1);
}

