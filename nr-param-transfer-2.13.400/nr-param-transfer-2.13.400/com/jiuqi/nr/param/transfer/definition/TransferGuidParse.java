/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 */
package com.jiuqi.nr.param.transfer.definition;

import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.nr.param.transfer.definition.TransferGuid;
import com.jiuqi.nr.param.transfer.definition.TransferNodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class TransferGuidParse {
    private static final Logger logger = LoggerFactory.getLogger(TransferGuidParse.class);
    public static final String SPLIT = "_";
    public static final String BUSINESS_FLAG = "business";
    public static final String FLAG = "_t";

    public static TransferGuid parse(String transferId) {
        if (transferId == null) {
            return null;
        }
        String[] split = transferId.split(SPLIT);
        if (split.length < 2) {
            return null;
        }
        int value = Integer.parseInt(split[0]);
        TransferNodeType nodeType = TransferNodeType.valueOf(value);
        TransferGuid transfer = new TransferGuid();
        transfer.setTransferNodeType(nodeType);
        transfer.setKey(split[1]);
        if (split.length > 2) {
            transfer.setBusiness(BUSINESS_FLAG.equals(split[2]));
        }
        return transfer;
    }

    public static TransferGuid parseId(String s) throws TransferException {
        TransferGuid transferId = TransferGuidParse.parse(s);
        if (transferId == null) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
        }
        TransferNodeType nodeType = transferId.getTransferNodeType();
        if (nodeType == null) {
            logger.info("\u89e3\u6790guid {} \u5176\u53c2\u6570\u7c7b\u578b\u5728\u5f53\u524d\u670d\u52a1\u4e0d\u5b58\u5728", (Object)s);
        }
        if (transferId.getKey() == null) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
        }
        return transferId;
    }

    public static String toTransferId(TransferNodeType nodeType, String key) {
        if (nodeType == null) {
            return null;
        }
        if (!StringUtils.hasLength(key)) {
            return null;
        }
        return nodeType.getValue() + SPLIT + key + FLAG;
    }

    public static String toBusinessId(TransferNodeType nodeType, String key) {
        if (nodeType == null) {
            return null;
        }
        if (!StringUtils.hasLength(key)) {
            return null;
        }
        return nodeType.getValue() + SPLIT + key + SPLIT + BUSINESS_FLAG + FLAG;
    }
}

