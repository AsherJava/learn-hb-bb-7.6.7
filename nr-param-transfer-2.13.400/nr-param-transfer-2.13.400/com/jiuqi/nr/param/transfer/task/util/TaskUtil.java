/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.param.transfer.task.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskUtil {
    public static byte[] changeMapToByte(Map<String, List<byte[]>> map) {
        byte[] bytes = null;
        try {
            bytes = TaskUtil.serilizable(map).getBytes();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static Map<String, List<byte[]>> changeByteToMap(byte[] bytes) {
        HashMap<String, List<byte[]>> map = new HashMap<String, List<byte[]>>();
        try {
            if (bytes != null) {
                map = TaskUtil.desSerilizable(new String(bytes));
            } else {
                System.out.println("null");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    protected static String serilizable(Object object) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String str = objectMapper.writeValueAsString(object);
        return str;
    }

    protected static HashMap<String, List<byte[]>> desSerilizable(String str) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = (Map)objectMapper.readValue(str, (TypeReference)new TypeReference<Map<String, List<byte[]>>>(){});
        return (HashMap)map;
    }
}

