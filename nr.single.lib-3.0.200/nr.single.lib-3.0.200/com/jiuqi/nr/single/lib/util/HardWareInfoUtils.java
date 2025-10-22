/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.lib.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Scanner;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HardWareInfoUtils {
    private static final Logger logger = LoggerFactory.getLogger(HardWareInfoUtils.class);
    private static final String OSNAME = System.getProperty("os.name").toLowerCase();
    private static final String CSCRIPT_NOLOGO = "cscript //NoLogo ";

    private HardWareInfoUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void printInfo() {
        logger.info("\u7cfb\u7edf\u7248\u672c\uff1a" + OSNAME);
        logger.info("\u83b7\u53d6\u786c\u4ef6\u4fe1\u606f");
        logger.info("CPU  SN:" + HardWareInfoUtils.getCPUSerial1());
        logger.info("CPU  SN2:" + HardWareInfoUtils.getCPUSerial2());
        if (OSNAME != null && OSNAME.indexOf("linux") >= 0) {
            logger.info("CPU  Info:" + HardWareInfoUtils.getCPUInfo());
        }
        logger.info("\u4e3b\u677f  SN:" + HardWareInfoUtils.getMotherboardSN());
        logger.info("C\u76d8   SN:" + HardWareInfoUtils.getHardDiskSN("c"));
        logger.info("MAC  SN:" + HardWareInfoUtils.getMac());
    }

    public static String getMotherboardSN() {
        StringBuilder result = new StringBuilder();
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            try (FileWriter fw = new FileWriter(file);){
                String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_BaseBoard\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.SerialNumber \n    exit for  ' do the first cpu only! \nNext \n";
                fw.write(vbs);
            }
            Process p = Runtime.getRuntime().exec(CSCRIPT_NOLOGO + file.getPath());
            try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));){
                String line;
                while ((line = input.readLine()) != null) {
                    result.append(line);
                }
            }
        }
        catch (Exception e) {
            logger.info("objWMIService winmgmts\u51fa\u9519:" + e.getMessage());
        }
        return result.toString().trim();
    }

    public static String getHardDiskSN(String drive) {
        StringBuilder result = new StringBuilder();
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            try (FileWriter fw = new FileWriter(file);){
                String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives = objFSO.Drives\nSet objDrive = colDrives.item(\"" + drive + "\")\nWscript.Echo objDrive.SerialNumber";
                fw.write(vbs);
            }
            Process p = Runtime.getRuntime().exec(CSCRIPT_NOLOGO + file.getPath());
            try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));){
                String line;
                while ((line = input.readLine()) != null) {
                    result.append(line);
                }
            }
        }
        catch (Exception e) {
            logger.info("objFSO Scripting\u51fa\u9519:" + e.getMessage());
        }
        return result.toString().trim();
    }

    public static String getCPUSerial() {
        String result = "";
        if (OSNAME != null) {
            if (OSNAME.indexOf("linux") >= 0) {
                result = HardWareInfoUtils.getCPUInfo();
            } else if (OSNAME.indexOf("windows") >= 0) {
                result = HardWareInfoUtils.getCPUSerial2();
            }
        }
        return result;
    }

    public static String getCPUSerial1() {
        String result = "";
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            try (FileWriter fw = new FileWriter(file);){
                String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_Processor\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.ProcessorId \n    exit for  ' do the first cpu only! \nNext \n";
                fw.write(vbs);
            }
            Process p = Runtime.getRuntime().exec(CSCRIPT_NOLOGO + file.getPath());
            try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));){
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = input.readLine()) != null) {
                    sb.append(line);
                }
                result = sb.toString();
            }
            file.deleteOnExit();
        }
        catch (Exception e) {
            logger.info("objWMIService\u51fa\u9519:" + e.getMessage());
        }
        if (result.trim().length() < 1) {
            result = "\u65e0CPU_ID\u88ab\u8bfb\u53d6";
        }
        return result.trim();
    }

    public static String getCPUSerial2() {
        String result = "";
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            sc.next();
            String serial = sc.next();
            sc.close();
            result = serial;
        }
        catch (Exception e) {
            logger.info("wmic\u51fa\u9519:" + e.getMessage());
        }
        return result;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getCPUInfo() {
        String result = "";
        try {
            String fileName = "/proc/stat";
            File file = new File(fileName);
            try (BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));){
                StringTokenizer token = new StringTokenizer(bReader.readLine());
                if (!token.hasMoreTokens()) {
                    String string = result;
                    return string;
                }
                result = token.nextToken();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 4; ++i) {
                    String s;
                    if (i != 0) {
                        sb.append("-");
                    }
                    sb.append((s = Integer.toHexString(Integer.parseInt(token.nextToken()))).length() == 1 ? 0 + s : s);
                }
                result = result + ":" + sb.toString();
                return result;
            }
        }
        catch (Exception e) {
            logger.info("/proc/stat\u51fa\u9519:" + e.getMessage());
        }
        return result;
    }

    public static String getMac() {
        try {
            byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; ++i) {
                String s;
                if (i != 0) {
                    sb.append("-");
                }
                sb.append((s = Integer.toHexString(mac[i] & 0xFF)).length() == 1 ? 0 + s : s);
            }
            return sb.toString().toUpperCase();
        }
        catch (Exception e) {
            return "";
        }
    }
}

