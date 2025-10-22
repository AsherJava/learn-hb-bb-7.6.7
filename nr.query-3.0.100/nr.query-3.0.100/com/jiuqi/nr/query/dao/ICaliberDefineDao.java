/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.dao;

import com.jiuqi.nr.query.caliber.CaliberDefine;
import java.util.List;

public interface ICaliberDefineDao {
    public Boolean insertCaliberDefine(CaliberDefine var1) throws Exception;

    public Boolean updateCaliberDefine(CaliberDefine var1) throws Exception;

    public Boolean deleteCaliberDefineById(String var1) throws Exception;

    public CaliberDefine getCaliberDefineById(String var1) throws Exception;

    public List<CaliberDefine> getAllCalibers() throws Exception;

    public List<CaliberDefine> getCaliberDefinesByGroupId(String var1) throws Exception;
}

