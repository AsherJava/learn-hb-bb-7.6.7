/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.dafafill.owner.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.dafafill.owner.entity.DataFillDefinitionPrivate;
import com.jiuqi.nr.dafafill.owner.entity.DataFillGroupPrivate;
import java.util.List;

public interface IDataFillPrivateService {
    public void addGroup(DataFillGroupPrivate var1);

    public void deleteGroupByKey(String var1);

    public List<DataFillGroupPrivate> getGroupByParentAndUser(String var1, String var2);

    public void modifyGroup(String var1, String var2);

    public void addDefinition(DataFillDefinitionPrivate var1, String var2, String var3) throws JQException, JsonProcessingException;

    public void deleteDefinitionByKey(String var1) throws JQException;

    public DataFillDefinitionPrivate getDefinitionByKey(String var1);

    public List<DataFillDefinitionPrivate> getAllDefinition();

    public List<DataFillDefinitionPrivate> getDefinitionByParentAndUser(String var1, String var2);

    public void modifyModel(String var1, String var2, String var3) throws JQException;

    public void modifyDefinition(DataFillDefinitionPrivate var1);
}

