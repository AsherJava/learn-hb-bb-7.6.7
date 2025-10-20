/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.TextNode
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gcreport.bde.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.va.domain.common.JSONUtil;

public class BdeClientUtil {
    public static <T> T parseResponse(BusinessResponseEntity<T> response) {
        if (response == null) {
            throw new BusinessRuntimeException("\u8bf7\u6c42\u7ed3\u679c\u4e3a\u7a7a");
        }
        if (!response.isSuccess()) {
            throw new BusinessRuntimeException(BdeClientUtil.getString(response.getErrorMessage() + BdeClientUtil.getString(response.getErrorDetail())));
        }
        return (T)response.getData();
    }

    public static String getString(String val) {
        if (val == null) {
            return "";
        }
        return val;
    }

    public static String getString(JsonNode jsonNode) {
        if (jsonNode == null) {
            return "";
        }
        if (jsonNode instanceof TextNode) {
            return jsonNode.asText();
        }
        return JSONUtil.toJSONString((Object)jsonNode);
    }

    public static Boolean getBoolean(JsonNode jsonNode) {
        if (jsonNode == null) {
            return false;
        }
        return jsonNode.asBoolean();
    }
}

