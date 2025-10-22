/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.nr.authorize.service.LicenceService
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.nr.authorize.service.LicenceService;
import com.jiuqi.nr.dataentry.service.IFunctionAuthorService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FunctionAuthorImpl
implements IFunctionAuthorService {
    private static Set<String> FunctionPoint = new HashSet<String>();
    @Autowired
    private LicenceService authorizeService;

    @Override
    public int queryAuthorByModule(String function) {
        if (FunctionPoint.contains(function)) {
            String cf = "true";
            try {
                cf = this.authorizeService.findAuthorizeConfig("com.jiuqi.nr.dataentry", function);
            }
            catch (Exception e) {
                Log.info((String)e.getMessage());
            }
            return "true".equals(cf) || "1".equals(cf) ? 1 : -1;
        }
        return 0;
    }

    static {
        FunctionPoint.add("dataPublish");
        FunctionPoint.add("efdcFetch");
        FunctionPoint.add("importFormData");
        FunctionPoint.add("nodeCheck");
        FunctionPoint.add("selectDataSum");
        FunctionPoint.add("uploadJIO");
        FunctionPoint.add("workflow");
        FunctionPoint.add("dataSum");
    }
}

