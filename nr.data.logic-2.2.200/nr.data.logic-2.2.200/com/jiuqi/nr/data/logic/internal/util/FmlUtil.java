/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.exception.UnknownReadWriteException
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.data.logic.internal.util;

import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.exception.UnknownReadWriteException;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class FmlUtil {
    private static final Logger logger = LoggerFactory.getLogger(FmlUtil.class);

    private FmlUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean needZBWriteFml(IParsedExpression parsedExpression) {
        if (StringUtils.hasText(parsedExpression.getFormKey())) {
            return false;
        }
        DynamicDataNode assignNode = parsedExpression.getAssignNode();
        if (assignNode != null && StringUtils.hasText(assignNode.getRelateTaskItem())) {
            return false;
        }
        QueryFields writeFields = null;
        try {
            writeFields = parsedExpression.getWriteQueryFields();
        }
        catch (UnknownReadWriteException e) {
            logger.warn("{}-\u83b7\u53d6\u5199\u6307\u6807\u5f02\u5e38:{}", new Object[]{parsedExpression.getSource(), e.getMessage(), e});
        }
        if (writeFields == null) {
            return false;
        }
        boolean dimRestrict = false;
        for (QueryField writeField : writeFields) {
            if (writeField.getTable() == null || writeField.getTable().getIsSimple()) continue;
            dimRestrict = true;
            break;
        }
        return !dimRestrict;
    }
}

