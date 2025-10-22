/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.dafafill.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.dafafill.entity.DataFillGroup;
import java.util.List;

public interface IDataFillGroupService {
    public String add(DataFillGroup var1) throws JQException;

    public String modify(DataFillGroup var1) throws JQException;

    public void delete(String var1) throws JQException;

    public DataFillGroup findById(String var1);

    public List<DataFillGroup> findByParentId(String var1);

    public List<DataFillGroup> query();

    public void batchDelete(List<String> var1) throws JQException;

    public List<DataFillGroup> fuzzySearch(String var1);

    public List<String> getAllParentId();

    public void batchModifyParentId(List<String> var1, String var2);
}

