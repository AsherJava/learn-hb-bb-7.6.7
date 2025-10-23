/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.dynamic;

import com.jiuqi.nr.multcheck2.bean.MCErrorDescription;
import java.util.List;

public interface IMCErrorInfoService {
    @Deprecated
    public List<MCErrorDescription> getByResource(String var1, String var2, String var3, String var4) throws Exception;

    @Deprecated
    public List<MCErrorDescription> getByOrg(String var1, String var2, String var3, String var4) throws Exception;

    public List<MCErrorDescription> getByResourcesAndOrgs(String var1, String var2, String var3, List<String> var4, List<String> var5) throws Exception;

    public List<MCErrorDescription> getByResourcesAndOrg(String var1, String var2, String var3, List<String> var4, String var5) throws Exception;

    public List<MCErrorDescription> getByResourceAndOrg(String var1, String var2, String var3, String var4, String var5) throws Exception;

    public String add(MCErrorDescription var1) throws Exception;

    public void batchAdd(List<MCErrorDescription> var1, String var2) throws Exception;

    public void modify(MCErrorDescription var1) throws Exception;

    public void batchDeleteByKeys(List<String> var1, String var2) throws Exception;

    public void batchDeleteByOrgAndModel(List<String> var1, List<String> var2, String var3, String var4, String var5) throws Exception;

    public void deleteByKey(String var1, String var2) throws Exception;
}

