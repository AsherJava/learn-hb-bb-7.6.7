/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.subdatabase.controller;

import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import com.jiuqi.nr.subdatabase.facade.impl.SubDataBaseImpl;
import java.util.List;

public interface SubDataBaseController {
    public void insertSubDataBaseObj(SubDataBase var1, String var2) throws Exception;

    public void insertSubDataBaseObj(SubDataBase var1, boolean var2, String var3) throws Exception;

    public void deleteSubDataBaseObj(SubDataBase var1, String var2);

    public void deleteSubDataBaseObj(SubDataBase var1, boolean var2, String var3);

    public void updateSubDataBaseObj(SubDataBase var1) throws Exception;

    public SubDataBase getSubDataBaseObjByCode(String var1, String var2);

    public List<SubDataBase> getSubDataBaseObjByDataScheme(String var1);

    public Boolean paramTablesExist(String var1, String var2, String var3) throws Exception;

    public List<SubDataBase> getAllSubDataBase();

    public List<SubDataBaseImpl> getAllSubDataBase(String var1, boolean var2);

    public List<SubDataBase> getSameTitleSubDataBase(String var1);

    public SubDataBase getSameTitleSubDataBase(String var1, String var2);
}

