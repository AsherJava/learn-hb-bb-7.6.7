/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.zbquery.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.zbquery.bean.ZBQueryGroup;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ZBQueryGroupService {
    public String addQueryGroup(ZBQueryGroup var1) throws JQException;

    public String modifyQueryGroup(ZBQueryGroup var1) throws JQException;

    public String deleteQueryGroup(String var1) throws JQException;

    public Optional<ZBQueryGroup> getQueryGroupById(String var1);

    public List<ZBQueryGroup> getQueryGroupByTitle(String var1, boolean var2);

    public List<ZBQueryGroup> getQueryGroupChildren(String var1);

    public List<String> getQueryGroupAllChildrenId(String var1);

    public List<String> getAllQueryGroupParentId();

    public Map<String, List<ZBQueryGroup>> getParentIdMap(boolean var1);

    public void getParentId(List<String> var1, String var2);

    public List<ZBQueryGroup> getAllQueryGroup();
}

