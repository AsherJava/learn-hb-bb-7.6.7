/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.sf.Framework
 */
package com.jiuqi.nr.authorize.service;

import com.jiuqi.nr.authorize.service.AuthorizeService;
import com.jiuqi.nvwa.sf.Framework;
import java.util.Map;
import org.springframework.stereotype.Service;

@Deprecated
@Service
public class AuthorizeServiceImpl
implements AuthorizeService {
    @Override
    public String findAuthorizeConfig(String moduleId, String funcPointId) throws Exception {
        return Framework.getInstance().getLicenceManager().getFuncPointValue(Framework.getInstance().getProductId(), moduleId, funcPointId).toString();
    }

    @Override
    public Map<String, String> findAuthorizeConfig(String moduleId) throws Exception {
        throw new Exception("\u672a\u5b9e\u73b0\u7684\u51fd\u6570\u3002");
    }
}

