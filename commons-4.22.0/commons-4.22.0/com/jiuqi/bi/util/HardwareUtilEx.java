/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util;

import com.jiuqi.bi.util.Bytes;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.TreeSet;

public class HardwareUtilEx {
    private static final int CODE_SEED = 151;

    private static final String getMachineCode(String softFlag) throws Exception {
        byte[] codeData;
        String code = softFlag + "@" + HardwareUtilEx.getMacAddress();
        try {
            codeData = code.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            codeData = code.getBytes();
        }
        MessageDigest md5 = MessageDigest.getInstance("md5");
        byte[] data = md5.digest(codeData);
        data = HardwareUtilEx.encodeData(data);
        String md5Data = Bytes.bytesToHexString(data);
        String machineCode = "";
        int i = 0;
        for (i = 0; i < md5Data.length() - 4; i += 4) {
            machineCode = machineCode + md5Data.substring(i, i + 4) + "-";
        }
        machineCode = machineCode + md5Data.substring(i, md5Data.length());
        return machineCode;
    }

    public static final String getMachineCode() throws Exception {
        return HardwareUtilEx.getMachineCode("");
    }

    public static String getMacAddress() throws Exception {
        return HardwareUtilEx.getMacByAPI();
    }

    private static String getMacByAPI() throws Exception {
        TreeSet<String> macList = new TreeSet<String>();
        Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
        while (el.hasMoreElements()) {
            String macAddr;
            NetworkInterface ni = el.nextElement();
            byte[] mac = ni.getHardwareAddress();
            if (mac == null || mac.length == 0 || macList.contains(macAddr = HardwareUtilEx.toMacString(mac))) continue;
            macList.add(macAddr);
        }
        StringBuffer result = new StringBuffer();
        for (String mac : macList) {
            if (result.length() > 0) {
                result.append(';');
            }
            result.append(mac);
        }
        return result.toString();
    }

    private static String toMacString(byte[] mac) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mac.length; ++i) {
            String s;
            if (i != 0) {
                sb.append("-");
            }
            sb.append((s = Integer.toHexString(mac[i] & 0xFF)).length() == 1 ? "0" + s : s);
        }
        return sb.toString().toUpperCase();
    }

    private static byte[] encodeData(byte[] data) {
        if (data == null) {
            return null;
        }
        int newSize = data.length / 2;
        byte[] result = new byte[newSize];
        for (int i = 0; i < newSize; ++i) {
            byte a = data[i * 2];
            byte b = data[i * 2 + 1];
            result[i] = (byte)(a ^ 151 + b ^ 0xFFFFFF68);
        }
        return result;
    }
}

