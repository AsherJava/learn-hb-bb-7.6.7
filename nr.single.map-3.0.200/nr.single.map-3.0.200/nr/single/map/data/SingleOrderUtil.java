/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.single.map.data;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import java.util.UUID;

public class SingleOrderUtil {
    private static final String[] CHARS3 = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String newOrderCode8() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return SingleOrderUtil.getOrderCode8FromUUID(uuid);
    }

    public static String getOrderCode8FromUUID(String uuid) {
        if (StringUtils.isEmpty((String)uuid)) {
            uuid = UUID.randomUUID().toString().replace("-", "");
        }
        StringBuffer shortBuffer = new StringBuffer();
        for (int i = 0; i < 8; ++i) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(CHARS3[x % 62]);
        }
        return shortBuffer.toString();
    }

    public static String newOrderCode6() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return SingleOrderUtil.getOrderCode6FromUUID(uuid);
    }

    public static String getOrderCode6FromUUID(String uuid) {
        if (StringUtils.isEmpty((String)uuid)) {
            uuid = UUID.randomUUID().toString().replace("-", "");
        }
        StringBuffer shortBuffer = new StringBuffer();
        for (int i = 0; i < 8; ++i) {
            String str = "";
            str = i < 2 ? uuid.substring(i * 6, i * 6 + 6) : uuid.substring(i * 5 + 2, i * 5 + 5 + 2);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(CHARS3[x % 62]);
        }
        return shortBuffer.toString();
    }

    public static String newOrder() {
        return OrderGenerator.newOrder();
    }

    public static String newOrder6() {
        String code = OrderGenerator.newOrder();
        if (code.length() > 6) {
            code = code.substring(2, code.length());
        }
        return code;
    }

    public static long newOrderId() {
        return OrderGenerator.newOrderID();
    }

    public static String getOrder(long order) {
        StringBuffer rt = new StringBuffer(10);
        while (order > 0L) {
            rt.insert(0, CHARS.charAt((int)(order % 36L)));
            order /= 36L;
        }
        return rt.toString();
    }

    public static String getOrder6(long order) {
        String code = SingleOrderUtil.getOrder(order);
        code = code.length() > 6 ? code.substring(2, code.length()) : "00000000".substring(0, 6 - code.length()) + code;
        return code;
    }

    public static long getOrderID(String OrderCode) {
        int len;
        long valueID = 0L;
        OrderCode = OrderCode.trim();
        for (int i = len = OrderCode.length(); i > 0; --i) {
            int index = CHARS.indexOf(OrderCode.charAt(i));
            valueID += (long)((double)index * Math.pow(36.0, (float)(len - i) + 0.0f));
        }
        return valueID;
    }
}

