/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.HardwareUtilEx
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.single.lib.reg.internal;

import com.jiuqi.bi.util.HardwareUtilEx;
import com.jiuqi.nr.single.lib.reg.SingleRegister;
import com.jiuqi.nr.single.lib.reg.exception.SingleRegRuntimeException;
import com.jiuqi.nr.single.lib.util.HardWareInfoUtils;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleRegisterImpl
implements SingleRegister {
    private static final Logger logger = LoggerFactory.getLogger(SingleRegisterImpl.class);

    private long longCycShl(long l, byte shlBit) {
        long b1 = shlBit;
        if (b1 < 0L) {
            b1 = 256L + b1;
        }
        long result = 0L;
        long px = l << (int)b1;
        long c = px % Integer.MIN_VALUE;
        long d = px / Integer.MIN_VALUE;
        long e = d % 2L;
        px = e != 0L ? c + Integer.MAX_VALUE + 1L : c;
        result = px | l >> (int)(32L - b1);
        return result;
    }

    private int byteCycShl2(byte b, byte shlBit) {
        int b1 = b;
        if (b < 0) {
            b1 = 256 + b;
        }
        int shlBit1 = shlBit;
        if (shlBit < 0) {
            shlBit1 = 256 + shlBit;
        }
        int value = b1 << shlBit1 | b1 >> 8 - shlBit1;
        return value % 256;
    }

    private String intToHex(int value, int digits) {
        String code = Integer.toHexString(value).toUpperCase();
        code = digits > code.length() ? "00000000000".substring(0, digits - code.length()) + code : code.substring(code.length() - digits, code.length());
        code = code.toUpperCase();
        return code;
    }

    @Override
    public String getLongToHex(int code) {
        String result = this.intToHex(code, 8);
        result = result.substring(0, 4) + "-" + result.substring(4, result.length());
        return result;
    }

    private int charToInt(String c) {
        int result = 0;
        byte a = this.getByteByCode(c);
        String[] codes1 = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] codes2 = new String[]{"A", "B", "C", "D", "E", "F"};
        List<String> aList1 = Arrays.asList(codes1);
        List<String> aList2 = Arrays.asList(codes2);
        result = aList1.contains(c) ? a - this.getByteByCode("0") : (aList2.contains(c) ? a - this.getByteByCode("A") + 10 : 0);
        return result;
    }

    private byte getByteByCode(String code) {
        try {
            byte[] bytes = code.getBytes("gb2312");
            return bytes[0];
        }
        catch (Exception ex) {
            logger.info("getByteByCode\u51fa\u73b0\u5f02\u5e38\uff1a" + ex.getMessage());
            return 0;
        }
    }

    private byte[] getBytesByCode(String code) {
        try {
            return code.getBytes("gbk");
        }
        catch (Exception ex) {
            logger.info("getBytesByCode\u51fa\u73b0\u5f02\u5e38\uff1a" + ex.getMessage());
            return new byte[]{0};
        }
    }

    public int hexToInt(String hexStr) {
        int result = 0;
        hexStr = hexStr.toUpperCase();
        int power = 1;
        for (int i = hexStr.length() - 1; i >= 0; --i) {
            result += this.charToInt(hexStr.substring(i, i + 1)) * power;
            power *= 16;
        }
        return result;
    }

    @Override
    public int getHexToLong(String hexStr) {
        int code = 0;
        if ((hexStr = hexStr.toUpperCase()).length() != 9) {
            return code;
        }
        if (!hexStr.substring(4, 5).equalsIgnoreCase("-")) {
            return code;
        }
        hexStr = hexStr.substring(0, 4) + hexStr.substring(5, 9);
        String[] codes1 = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] codes2 = new String[]{"A", "B", "C", "D", "E", "F"};
        List<String> aList1 = Arrays.asList(codes1);
        List<String> aList2 = Arrays.asList(codes2);
        for (int i = 0; i < hexStr.length(); ++i) {
            String c = hexStr.substring(i, i + 1);
            if (aList1.contains(c) || aList2.contains(c)) continue;
            return code;
        }
        int power = 1;
        for (int i = hexStr.length() - 1; i >= 0; --i) {
            code += this.charToInt(hexStr.substring(i, i + 1)) * power;
            power *= 16;
        }
        return code;
    }

    private String clearSpaceChar(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            String s1 = s.substring(i, i + 1);
            if (s1.equalsIgnoreCase(" ")) continue;
            sb.append(s1);
        }
        return sb.toString();
    }

    @Override
    public String getStringToMD5(String value) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("SHA-256").digest(value.getBytes());
        }
        catch (NoSuchAlgorithmException e) {
            throw new SingleRegRuntimeException("\u6ca1\u6709\u8fd9\u4e2aSHA-256\u7b97\u6cd5\uff01");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        StringBuilder md5codeSb = new StringBuilder();
        md5codeSb.append(md5code);
        for (int i = 0; i < 32 - md5code.length(); ++i) {
            md5codeSb.insert(0, "0");
        }
        return md5codeSb.toString();
    }

    @Override
    public int getRegisterCode(String username, String companyName, String machecode, String checkCode) {
        int code = 0;
        if (StringUtils.isEmpty((CharSequence)checkCode)) {
            return code;
        }
        int machCode = this.getOriginMachine() + 1010101997;
        String newCheckCode = this.getLongToHex(machCode);
        if (!(newCheckCode = this.getStringToMD5(newCheckCode)).equalsIgnoreCase(checkCode)) {
            return code;
        }
        int machecodeValue = this.getHexToLong(machecode) + 313739;
        return this.getRegisterCodeValue(username, companyName, machecodeValue) + 547592;
    }

    @Override
    public int checkRegisterCode(String username, String companyName, String machecode, String regMd5Code, String checkCode) {
        int regValue = this.getRegisterCode(username, companyName, machecode, checkCode) - 547592;
        String regCode = this.getLongToHex(regValue);
        String regCode2 = this.getStringToMD5(regCode);
        if (regCode2.equalsIgnoreCase(regMd5Code)) {
            return 1;
        }
        return 2;
    }

    private int getRegisterCodeValue(String username, String companyName, int machecodeValue) {
        byte c;
        long b;
        byte a;
        int i;
        String machecode = this.getLongToHex(machecodeValue - 313739);
        int result = 305419896;
        int machineCode = 0;
        int tempLongint = 0;
        machineCode = this.getHexToLong(machecode);
        username = this.clearSpaceChar(username);
        companyName = this.clearSpaceChar(companyName);
        byte[] usernameBytes = this.getBytesByCode(username);
        byte[] companyNameBytes = this.getBytesByCode(companyName);
        for (i = 1; i <= usernameBytes.length; ++i) {
            a = (byte)((i + 1) % 32);
            b = machineCode;
            if (machineCode < 0) {
                b = b + Integer.MAX_VALUE + Integer.MAX_VALUE + 2L;
            }
            tempLongint = (int)this.longCycShl(b, a);
            byte by = c = usernameBytes[i - 1];
            if (c < 0) {
                by = 256 + c;
            }
            result += (tempLongint += (this.byteCycShl2(c, (byte)3) << by % 8 + 6) * this.byteCycShl2(c, (byte)3));
        }
        for (i = 1; i <= companyNameBytes.length; ++i) {
            a = (byte)((i + 6) % 32);
            b = machineCode;
            if (machineCode < 0) {
                b = b + Integer.MAX_VALUE + Integer.MAX_VALUE + 2L;
            }
            tempLongint = (int)this.longCycShl(b, a);
            byte by = c = companyNameBytes[i - 1];
            if (c < 0) {
                by = 256 + c;
            }
            result += (tempLongint += (this.byteCycShl2(c, (byte)1) << by % 8 + 3) * this.byteCycShl2(c, (byte)2));
        }
        return result += machineCode;
    }

    public int[] getCpuId() {
        String cpuInfo = this.getCpuInfo();
        return this.getCpuId2(cpuInfo);
    }

    private int[] getCpuId2(String randCode) {
        int[] result = new int[4];
        String cpuInfo = randCode;
        if (StringUtils.isNotEmpty((CharSequence)cpuInfo) && cpuInfo.length() > 9) {
            cpuInfo = cpuInfo.substring(0, 9);
        }
        String code0 = "";
        code0 = StringUtils.isNotEmpty((CharSequence)cpuInfo) && cpuInfo.length() >= 9 ? cpuInfo.substring(5, 9) : "AAAA";
        String code3 = "FFFFFFFF";
        if (StringUtils.isNotEmpty((CharSequence)cpuInfo) && cpuInfo.length() >= 4) {
            code3 = code3 + cpuInfo.substring(0, 4);
        }
        result[0] = this.hexToInt(code0);
        result[1] = 0;
        result[2] = 0;
        result[3] = this.hexToInt(code3);
        return result;
    }

    public String getCPUVendor() {
        return this.getCPUVendor2(this.getCpuInfo());
    }

    private String getCPUVendor2(String randCode) {
        String result = randCode;
        if (StringUtils.isNotEmpty((CharSequence)result) && result.length() > 9) {
            result = result.substring(9, result.length());
        }
        return result;
    }

    private String getOtherInfo(String code) {
        try {
            Properties props = System.getProperties();
            code = code + props.getProperty("os.version") + "_";
            code = code + props.getProperty("os.arch") + "_";
            code = code + props.getProperty("os.name") + "_";
            code = code + props.getProperty("java.specification.version") + "_";
            code = code + props.getProperty("java.specification.vender") + "_";
            code = code + props.getProperty("java.vm.specification.version") + "_";
            code = code + props.getProperty("java.vm.specification.vendor") + "_";
            code = code + props.getProperty("java.vm.specification.version") + "_";
            code = code + props.getProperty("java.version") + "_";
            code = code + props.getProperty("java.vendor");
            logger.info(code);
        }
        catch (Exception e) {
            logger.info(e.getMessage());
        }
        return code;
    }

    private String getCpuMainInfo() {
        String code = "";
        try {
            String oldCode = HardWareInfoUtils.getCPUSerial();
            if (StringUtils.isNotEmpty((CharSequence)oldCode)) {
                if (oldCode.length() <= 16) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 4; ++i) {
                        if (i > 0) {
                            sb.append("-");
                        }
                        if (oldCode.length() < i * 4 + 4) continue;
                        sb.append(oldCode.substring(i * 4, i * 4 + 4));
                    }
                    code = sb.toString();
                }
            } else {
                code = HardwareUtilEx.getMachineCode();
            }
        }
        catch (Exception ex) {
            logger.info("\u83b7\u53d6\u786c\u4ef6\u4fe1\u606f\u5931\u8d25\uff1a" + ex.getMessage());
        }
        return code;
    }

    private String getCpuInfo() {
        try {
            String code = this.getCpuMainInfo();
            if (StringUtils.isEmpty((CharSequence)code) || code.length() < 9) {
                if (StringUtils.isEmpty((CharSequence)code)) {
                    code = "";
                }
                code = this.getOtherInfo(code);
                code = this.getStringToMD5(code);
            }
            return code;
        }
        catch (Exception ex) {
            logger.info(ex.getMessage());
            return "";
        }
    }

    private int byteCycShr2(byte b, byte shrBit) {
        int b1 = b;
        if (b < 0) {
            b1 = 256 + b;
        }
        int shrBit1 = shrBit;
        if (shrBit < 0) {
            shrBit1 = 256 + shrBit;
        }
        int value = b1 >> shrBit | b1 << 8 - shrBit1;
        return value % 256;
    }

    @Override
    public int getOriginMachine() {
        return this.getMachineValueByCpu(this.getCpuId());
    }

    @Override
    public int getMachineValue(int addValue) {
        return this.getOriginMachine() + addValue;
    }

    @Override
    public int getMachineValue(String randCode) {
        return this.getMachineValueByCpu(this.getCpuId2(randCode));
    }

    private int getMachineValueByCpu(int[] cpuId) {
        int value;
        int i;
        int result = 85004839;
        String cpuVendor = this.getCPUVendor();
        byte[] cpuVendorBytes = this.getBytesByCode(cpuVendor);
        for (i = 0; i < cpuId.length; ++i) {
            value = cpuId[i] * this.byteCycShr2((byte)cpuId[i], (byte)2);
            result += result * value;
        }
        for (i = 0; i < cpuVendorBytes.length; ++i) {
            value = this.byteCycShl2(cpuVendorBytes[i], (byte)3) * (i + 1) * (i + 2) * (i + 3);
            result += result * value;
        }
        int second = -305419897;
        for (int i2 = 0; i2 < cpuVendorBytes.length; ++i2) {
            int d = this.byteCycShl2(cpuVendorBytes[i2], (byte)4) * (cpuVendorBytes[i2] << 11);
            second += d;
        }
        return result += second;
    }
}

