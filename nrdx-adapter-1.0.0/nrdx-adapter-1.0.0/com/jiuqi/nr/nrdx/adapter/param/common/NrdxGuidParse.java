/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 */
package com.jiuqi.nr.nrdx.adapter.param.common;

import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;
import com.jiuqi.nr.nrdx.adapter.param.common.ParamGuid;
import org.springframework.util.StringUtils;

public class NrdxGuidParse {
    public static final String SPLIT = "_";
    public static final String BUSINESS_FLAG = "business";
    public static final String FLAG = "_t";

    public static ParamGuid parse(String paramId) {
        if (paramId == null) {
            return null;
        }
        String[] split = paramId.split(SPLIT);
        if (split.length < 2) {
            return null;
        }
        NrdxParamNodeType nodeType = NrdxParamNodeType.codeOf(split[0]);
        ParamGuid paramGuid = new ParamGuid();
        paramGuid.setNrdxParamNodeType(nodeType);
        paramGuid.setKey(split[1]);
        if (split.length > 2) {
            paramGuid.setBusiness(BUSINESS_FLAG.equals(split[2]));
        }
        return paramGuid;
    }

    public static ParamGuid parseId(String s) throws TransferException {
        ParamGuid paramGuid = NrdxGuidParse.parse(s);
        if (paramGuid == null) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
        }
        NrdxParamNodeType nrdxParamNodeType = paramGuid.getNrdxParamNodeType();
        if (nrdxParamNodeType == null) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
        }
        if (paramGuid.getKey() == null) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
        }
        return paramGuid;
    }

    public static String toTransferId(NrdxParamNodeType nodeType, String key) {
        if (nodeType == null) {
            return null;
        }
        if (!StringUtils.hasLength(key)) {
            return null;
        }
        return nodeType.getCode() + SPLIT + key + FLAG;
    }

    public static String toBusinessId(NrdxParamNodeType nodeType, String key) {
        if (nodeType == null) {
            return null;
        }
        if (!StringUtils.hasLength(key)) {
            return null;
        }
        return nodeType.getCode() + SPLIT + key + SPLIT + BUSINESS_FLAG + FLAG;
    }
}

