/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.ent.bamboocloud.bim.service;

import com.jiuqi.ent.bamboocloud.bim.dto.org.OrgExtFieldDTO;
import com.jiuqi.np.common.exception.JQException;
import java.util.List;
import java.util.Map;

public interface BimOrgExtService {
    public void createBaseOrg(OrgExtFieldDTO var1);

    public void updateBaseOrg(OrgExtFieldDTO var1);

    public void deleteBaseOrg(String var1);

    public boolean isOrgExists(String var1);

    public List<String> queryAllOrgIds();

    public Map<String, Object> queryOrgById(String var1);

    public void bindNpUserContext() throws JQException;

    public void unbindNpUserContext();
}

