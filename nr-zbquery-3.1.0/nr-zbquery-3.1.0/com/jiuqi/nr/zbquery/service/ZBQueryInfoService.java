/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.zbquery.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.zbquery.bean.ZBQueryInfo;
import com.jiuqi.nr.zbquery.bean.ZBQueryParam;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import java.util.List;

public interface ZBQueryInfoService {
    public void addQueryInfo(ZBQueryInfo var1) throws JQException;

    public ZBQueryInfo saveAsQueryInfo(String var1, ZBQueryInfo var2) throws JQException;

    public void modifyQueryInfo(ZBQueryInfo var1) throws JQException;

    public void deleteQueryInfoById(String var1) throws JQException;

    public String deleteQueryInfoByIds(List<String> var1) throws JQException;

    public void deleteAll() throws JQException;

    public ZBQueryInfo getQueryInfoById(String var1);

    public List<ZBQueryInfo> getQueryInfoByGroup(String var1);

    public List<String> getQueryInfoByGroups(List<String> var1);

    public void saveQueryInfoData(ZBQueryModel var1, String var2) throws JQException;

    public ZBQueryModel getQueryInfoData(String var1) throws JQException;

    public ZBQueryModel getQueryInfoData(String var1, ZBQueryParam var2) throws JQException;

    public List<ZBQueryInfo> getAllQueryInfo();

    public ZBQueryModel createQueryModel();
}

