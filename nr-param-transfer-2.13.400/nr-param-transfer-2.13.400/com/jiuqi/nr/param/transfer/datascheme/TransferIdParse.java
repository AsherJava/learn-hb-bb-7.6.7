/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 */
package com.jiuqi.nr.param.transfer.datascheme;

import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.param.transfer.datascheme.TransferId;
import org.springframework.util.StringUtils;

public class TransferIdParse {
    public static final String SPLIT = "_";
    public static final String BUSINESS_FLAG = "business";

    public static TransferId parse(String transferId) {
        if (transferId == null) {
            return null;
        }
        String[] split = transferId.split(SPLIT);
        if (split.length < 2) {
            return null;
        }
        int value = Integer.parseInt(split[0]);
        NodeType nodeType = NodeType.valueOf((int)value);
        TransferId transfer = new TransferId();
        transfer.setNodeType(nodeType);
        transfer.setKey(split[1]);
        if (split.length > 2) {
            transfer.setBusiness(BUSINESS_FLAG.equals(split[2]));
        }
        return transfer;
    }

    public static TransferId parseId(String s) throws TransferException {
        TransferId transferId = TransferIdParse.parse(s);
        if (transferId == null) {
            throw new TransferException("\u89e3\u6790\u5931\u8d25\uff1a\u8d44\u6e90id\u9519\u8bef\uff0c\u65e0\u6cd5\u89e3\u6790");
        }
        NodeType nodeType = transferId.getNodeType();
        if (nodeType == null) {
            throw new TransferException("\u89e3\u6790\u5931\u8d25\uff1a\u8d44\u6e90id\u9519\u8bef\uff0c\u65e0\u6cd5\u89e3\u6790");
        }
        if (transferId.getKey() == null) {
            throw new TransferException("\u89e3\u6790\u5931\u8d25\uff1a\u8d44\u6e90id\u9519\u8bef\uff0c\u65e0\u6cd5\u89e3\u6790");
        }
        return transferId;
    }

    public static String toTransferId(NodeType nodeType, String key) {
        if (nodeType == null) {
            return null;
        }
        if (!StringUtils.hasLength(key)) {
            return null;
        }
        return nodeType.getValue() + SPLIT + key;
    }

    public static String toBusinessId(NodeType nodeType, String key) {
        if (nodeType == null) {
            return null;
        }
        if (!StringUtils.hasLength(key)) {
            return null;
        }
        return nodeType.getValue() + SPLIT + key + SPLIT + BUSINESS_FLAG;
    }
}

