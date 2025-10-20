/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.definition.reportTag.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

public interface IDesignReportTagService {
    public List<DesignReportTagDefine> queryAllTagsByRptKey(String var1);

    public List<DesignReportTagDefine> queryTagsByRptKeys(Set<String> var1);

    public void deleteTagByKeys(List<String> var1) throws JQException;

    public void insertTags(List<DesignReportTagDefine> var1) throws JQException;

    public void updateTags(List<DesignReportTagDefine> var1) throws JQException;

    public void deleteTagsByRptKey(String var1) throws JQException;

    public void deleteTagsByRptKeys(Set<String> var1) throws JQException;

    public void saveTag(DesignReportTagDefine var1) throws JQException;

    public List<DesignReportTagDefine> filterCustomTagsInRpt(InputStream var1, String var2) throws JQException;
}

