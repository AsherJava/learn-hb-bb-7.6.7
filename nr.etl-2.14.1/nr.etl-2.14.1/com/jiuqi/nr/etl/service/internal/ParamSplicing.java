/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.etl.service.internal;

import com.jiuqi.nr.etl.common.ServeType;
import com.jiuqi.nr.etl.service.internal.ETLFilterParam;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ParamSplicing {
    public JSONObject paramSplicing(ETLFilterParam etlFilterParam, String nrUserName, String userTokenID, ServeType serveType) {
        JSONObject param = new JSONObject();
        String period = etlFilterParam.getPeriod();
        String unitid = etlFilterParam.getUnitidByString();
        String unidCode = etlFilterParam.getUnidCodeByString();
        String formKeySet = etlFilterParam.getFormKeySetByString();
        String formCode = etlFilterParam.getFormCodeByString();
        if (serveType == ServeType.ETL) {
            param.put("datatime", (Object)period);
            param.put("unitid", (Object)unitid);
            param.put("unitcode", (Object)unidCode);
            param.put("rpid", (Object)formKeySet);
            param.put("rpcode", (Object)formCode);
        }
        if (serveType == ServeType.DATA_INTEGRATION) {
            param.put("DATATIME", (Object)period);
            param.put("UNITID", (Object)unitid);
            param.put("UNITCODE", (Object)unidCode);
            param.put("RPID", (Object)formKeySet);
            param.put("RPCODE", (Object)formCode);
            param.put("NR_USER", (Object)nrUserName);
            param.put("USER_TOKEN", (Object)userTokenID);
        }
        return param;
    }
}

