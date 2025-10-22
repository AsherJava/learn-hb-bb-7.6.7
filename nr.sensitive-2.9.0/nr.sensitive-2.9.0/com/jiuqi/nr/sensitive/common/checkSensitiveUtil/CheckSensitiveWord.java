/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.sensitive.common.checkSensitiveUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckSensitiveWord {
    public HashMap addSensitiveWordMap(Set<String> sensitiveSet) {
        HashMap sensitiveMap = new HashMap(sensitiveSet.size());
        String key2 = null;
        Map<Object, Object> nowMap = null;
        HashMap<String, String> nowWorMap = null;
        for (String key2 : sensitiveSet) {
            nowMap = sensitiveMap;
            for (int i = 0; i < key2.length(); ++i) {
                char keyChar = key2.charAt(i);
                Object wordMap = nowMap.get(Character.valueOf(keyChar));
                if (wordMap != null) {
                    nowMap = (Map)wordMap;
                } else {
                    nowWorMap = new HashMap<String, String>();
                    nowWorMap.put("isEnd", "0");
                    nowMap.put(Character.valueOf(keyChar), nowWorMap);
                    nowMap = nowWorMap;
                }
                if (i != key2.length() - 1) continue;
                nowMap.put("isEnd", "1");
            }
        }
        return sensitiveMap;
    }

    public List<String> checkSensitiveOfStringType(String sensitiveWordInfo, HashMap sensitiveMap) {
        String sensitiveWordInfoFormat = sensitiveWordInfo.replaceAll("\n", "");
        char[] test = sensitiveWordInfoFormat.toUpperCase().toCharArray();
        int start = 0;
        int senStart = 0;
        Map nowMap = sensitiveMap;
        boolean flag = false;
        ArrayList<String> senList = new ArrayList<String>();
        for (int i = 0; i < test.length; ++i) {
            if ((nowMap = (Map)nowMap.get(Character.valueOf(test[i]))) == null) {
                if (flag) {
                    i = senStart;
                    flag = false;
                }
                senStart = start = i + 1;
                nowMap = sensitiveMap;
                continue;
            }
            flag = true;
            if (!"1".equals(nowMap.get("isEnd"))) continue;
            senList.add(sensitiveWordInfo.substring(start, i + 1));
            start = i + 1;
            flag = false;
            nowMap = sensitiveMap;
        }
        return senList;
    }

    public List<String> checkSensitiveOfRegType(String sensitiveWordInfo, List<String> regStringList) {
        ArrayList<String> senList = new ArrayList<String>();
        for (String regString : regStringList) {
            Pattern compile = Pattern.compile(regString);
            Matcher matcher = compile.matcher(sensitiveWordInfo);
            if (!matcher.find()) continue;
            senList.add(regString);
        }
        return senList;
    }
}

