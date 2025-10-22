/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.conditionalstyle.utils;

import com.jiuqi.nr.conditionalstyle.facade.CoordObject;
import java.util.Arrays;
import java.util.List;

public class CellCoordChangeUtil {
    public static CoordObject StrToNum(String param) {
        CoordObject result = new CoordObject();
        int index = CellCoordChangeUtil.getFirstNumberIndex(param);
        int y = Integer.parseInt(param.substring(index));
        String posx = param.substring(0, index);
        int x = CellCoordChangeUtil.ColStrToNum(posx);
        result.setPosX(x);
        result.setPosY(y);
        return result;
    }

    public static int ColStrToNum(String column) {
        int result = 0;
        int length = column.length();
        for (int i = 0; i < length; ++i) {
            char ch = column.charAt(length - i - 1);
            int num = ch - 65 + 1;
            num = (int)((double)num * Math.pow(26.0, i));
            result += num;
        }
        return result;
    }

    public static int getFirstNumberIndex(String param) {
        String[] a = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        List<String> list = Arrays.asList(a);
        int i = 0;
        char target;
        while (!list.contains(Character.toString(target = param.charAt(i)))) {
            ++i;
        }
        return i;
    }
}

