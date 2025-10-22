/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.dao;

import com.jiuqi.nr.query.caliber.CaliberItem;
import java.util.List;

public interface ICaliberItemDao {
    public Boolean insertCaliberItem(CaliberItem var1) throws Exception;

    public Boolean updateCaliberItem(CaliberItem var1) throws Exception;

    public Boolean deleteCaliberItemById(String var1) throws Exception;

    public CaliberItem getCaliberItemById(String var1) throws Exception;

    public List<CaliberItem> getAllCaliberItems() throws Exception;
}

