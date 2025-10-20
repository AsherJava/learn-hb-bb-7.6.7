/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IEntityTable
 *  com.jiuqi.np.dataengine.intf.IModifierEntityTable
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.designer.service;

import com.jiuqi.np.dataengine.intf.IEntityTable;
import com.jiuqi.np.dataengine.intf.IModifierEntityTable;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.designer.web.treebean.FieldTableObj;
import java.util.List;

public interface IRunTimeService {
    public IEntityTable createRunTimeQueryEntity(String var1) throws Exception;

    public List<FieldTableObj> getAllRows(IEntityTable var1, String var2);

    public List<String> getCodes(IEntityTable var1);

    public IModifierEntityTable createHandleEntity(String var1, List<FieldDefine> var2) throws Exception;
}

