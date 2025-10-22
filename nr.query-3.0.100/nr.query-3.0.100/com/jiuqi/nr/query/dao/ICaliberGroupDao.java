/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.dao;

import com.jiuqi.nr.query.caliber.CaliberGroup;
import java.util.List;

public interface ICaliberGroupDao {
    public Boolean insertCaliberGroup(CaliberGroup var1) throws Exception;

    public Boolean updateCaliberGroup(CaliberGroup var1) throws Exception;

    public Boolean deleteCaliberGroupById(String var1) throws Exception;

    public CaliberGroup getCaliberGroupById(String var1) throws Exception;

    public List<CaliberGroup> getAllCaliberGroups() throws Exception;
}

