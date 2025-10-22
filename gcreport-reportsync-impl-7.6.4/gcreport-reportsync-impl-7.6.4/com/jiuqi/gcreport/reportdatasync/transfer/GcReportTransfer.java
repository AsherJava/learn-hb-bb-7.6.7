/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.io.datacheck.DefaultTransfer
 *  com.jiuqi.nr.io.datacheck.TransferData
 *  com.jiuqi.nr.io.datacheck.param.TransferParam
 *  com.jiuqi.nr.io.datacheck.param.TransferSource
 *  com.jiuqi.nr.io.service.impl.DefaultDataTransferProvider
 */
package com.jiuqi.gcreport.reportdatasync.transfer;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.io.datacheck.DefaultTransfer;
import com.jiuqi.nr.io.datacheck.TransferData;
import com.jiuqi.nr.io.datacheck.param.TransferParam;
import com.jiuqi.nr.io.datacheck.param.TransferSource;
import com.jiuqi.nr.io.service.impl.DefaultDataTransferProvider;
import java.util.HashMap;
import java.util.Map;

public class GcReportTransfer
extends DefaultTransfer {
    public GcReportTransfer(DefaultDataTransferProvider dataTransferProvider, TransferParam param) {
        super(dataTransferProvider, param);
    }

    public TransferData enumDataIllegal(TransferSource transferSource) {
        String value;
        String json;
        DsContext context = DsContextHolder.getDsContext();
        String string = json = context.getExtension().get("GzOrgCodeMap") == null ? "" : context.getExtension().get("GzOrgCodeMap").toString();
        if (StringUtils.isEmpty((String)json)) {
            return super.enumDataIllegal(transferSource);
        }
        String refDataEntiteKey = (String)this.enumDatakeys.get(transferSource.getDataField().getKey());
        if (!refDataEntiteKey.startsWith("MD_ORG") || transferSource.getValue() == null) {
            return super.enumDataIllegal(transferSource);
        }
        Map gzOrgCodeMap = (Map)JsonUtils.readValue((String)json, HashMap.class);
        if (gzOrgCodeMap.containsKey(value = transferSource.getValue().toString())) {
            transferSource.setValue(gzOrgCodeMap.get(value));
        }
        return super.enumDataIllegal(transferSource);
    }
}

