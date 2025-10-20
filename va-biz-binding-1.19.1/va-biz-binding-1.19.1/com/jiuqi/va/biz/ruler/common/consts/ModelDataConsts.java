/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.common.consts;

import com.jiuqi.va.biz.ruler.CountDataNode;
import com.jiuqi.va.biz.ruler.ModelDataNode;
import java.util.HashMap;
import java.util.Map;

public class ModelDataConsts {
    public static final String[] CONSTANT_NODE_COUNT = new String[]{"SUM", "AGE", "MAX", "MIN", "COUNT", "FIRST", "LIST"};
    public static final String ALL = "ALL";
    public static final Map<String, Class<? extends ModelDataNode>> modelDataNodeMap = new HashMap<String, Class<? extends ModelDataNode>>();
    public static final String FUNCTION_GROUP_MODEL = "\u6a21\u578b\u51fd\u6570";

    static {
        modelDataNodeMap.put(CONSTANT_NODE_COUNT[0], CountDataNode.class);
        modelDataNodeMap.put(CONSTANT_NODE_COUNT[1], CountDataNode.class);
        modelDataNodeMap.put(CONSTANT_NODE_COUNT[2], CountDataNode.class);
        modelDataNodeMap.put(CONSTANT_NODE_COUNT[3], CountDataNode.class);
        modelDataNodeMap.put(CONSTANT_NODE_COUNT[4], CountDataNode.class);
        modelDataNodeMap.put(CONSTANT_NODE_COUNT[5], CountDataNode.class);
        modelDataNodeMap.put(CONSTANT_NODE_COUNT[6], CountDataNode.class);
    }

    public static enum FormulaType {
        BILL,
        WORKFLOW,
        BASEDATA;

    }
}

