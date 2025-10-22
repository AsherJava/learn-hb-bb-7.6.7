/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvancedChineseNumberSort {
    private static final Map<Character, Integer> NUMBER_MAP = new HashMap<Character, Integer>(){
        {
            this.put(Character.valueOf('\u96f6'), 0);
            this.put(Character.valueOf('\u4e00'), 1);
            this.put(Character.valueOf('\u4e8c'), 2);
            this.put(Character.valueOf('\u4e09'), 3);
            this.put(Character.valueOf('\u56db'), 4);
            this.put(Character.valueOf('\u4e94'), 5);
            this.put(Character.valueOf('\u516d'), 6);
            this.put(Character.valueOf('\u4e03'), 7);
            this.put(Character.valueOf('\u516b'), 8);
            this.put(Character.valueOf('\u4e5d'), 9);
            this.put(Character.valueOf('\u5341'), 10);
            this.put(Character.valueOf('\u767e'), 100);
            this.put(Character.valueOf('\u5343'), 1000);
        }
    };

    public static List<String> sort(List<String> roleNames) {
        roleNames.sort((s1, s2) -> {
            int num1 = AdvancedChineseNumberSort.parseChineseNumber(AdvancedChineseNumberSort.extractNumberPart(s1));
            int num2 = AdvancedChineseNumberSort.parseChineseNumber(AdvancedChineseNumberSort.extractNumberPart(s2));
            return Integer.compare(num1, num2);
        });
        return roleNames;
    }

    private static String extractNumberPart(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (!NUMBER_MAP.containsKey(Character.valueOf(c)) && c != '\u5341' && c != '\u767e' && c != '\u5343') continue;
            sb.append(c);
        }
        return sb.toString();
    }

    private static int parseChineseNumber(String chineseNumber) {
        if (chineseNumber.isEmpty()) {
            return Integer.MAX_VALUE;
        }
        int total = 0;
        int current = 0;
        int prevValue = 0;
        for (char c : chineseNumber.toCharArray()) {
            int value = NUMBER_MAP.getOrDefault(Character.valueOf(c), 0);
            if (value >= 10) {
                total += current * value;
                current = 0;
                continue;
            }
            if (value < 1) continue;
            current = value;
            prevValue = value;
        }
        return total += current;
    }
}

