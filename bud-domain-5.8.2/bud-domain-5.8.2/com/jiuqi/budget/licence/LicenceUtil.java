/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.authz.LicenceException
 *  com.jiuqi.bi.authz.licence.LicenceManager
 *  com.jiuqi.nvwa.sf.Framework
 */
package com.jiuqi.budget.licence;

import com.jiuqi.bi.authz.LicenceException;
import com.jiuqi.bi.authz.licence.LicenceManager;
import com.jiuqi.nvwa.sf.Framework;
import java.util.Map;

public class LicenceUtil {
    public static Object getAuthValue(Map<String, String> map) {
        Framework framework = Framework.getInstance();
        LicenceManager licenceManager = framework.getLicenceManager();
        Object funcPointValue = false;
        try {
            funcPointValue = licenceManager.getFuncPointValue(framework.getProductId(), map.get("module"), map.get("point"));
        }
        catch (LicenceException e) {
            e.printStackTrace();
        }
        return funcPointValue;
    }
}

