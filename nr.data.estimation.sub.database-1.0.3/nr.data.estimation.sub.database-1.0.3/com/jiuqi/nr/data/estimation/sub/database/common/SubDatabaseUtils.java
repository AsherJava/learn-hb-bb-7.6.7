/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.sub.database.common;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubDatabaseUtils {
    private SubDatabaseUtils() {
    }

    public static boolean validateDatabaseCode(String databaseCode) {
        String regex = "^_[A-Z_]*_$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(databaseCode);
        return matcher.matches();
    }

    @SafeVarargs
    public static <T> T[] concatArrays(T[] first, T[] ... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    @SafeVarargs
    public static <T> T[] appendElement(T[] arr, T ... els) {
        return SubDatabaseUtils.concatArrays(arr, new Object[][]{els});
    }
}

