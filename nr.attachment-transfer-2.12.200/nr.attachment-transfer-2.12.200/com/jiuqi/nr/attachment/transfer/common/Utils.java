/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.common;

import com.jiuqi.nr.attachment.transfer.log.AttachmentLogHelper;
import java.io.File;
import java.io.FilenameFilter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFileFilter;

public class Utils {
    static DecimalFormat DF = new DecimalFormat("#.##");
    static final FilenameFilter nameFilter = new FileFileFilter(){

        @Override
        public boolean accept(File file) {
            return file.isFile() && file.getName().endsWith(".jio");
        }
    };

    public static String getFileMBSize(long fileSize) {
        double v = (double)fileSize / 1024.0 / 1024.0;
        String format = DF.format(v);
        return format + "MB";
    }

    public static List<String> getCurrentPageData(List<String> dataList, int pageSize, int currentPage) {
        int startIndex = (currentPage - 1) * pageSize;
        int totalSize = dataList.size();
        if (startIndex >= totalSize) {
            return Collections.emptyList();
        }
        int endIndex = Math.min(startIndex + pageSize, totalSize);
        return dataList.subList(startIndex, endIndex);
    }

    public static List<File> listFiles(String path) {
        File file = new File(FilenameUtils.normalize(path));
        if (!file.exists()) {
            throw new RuntimeException("\u8def\u5f84\u9519\u8bef\uff1a" + path);
        }
        return Arrays.stream(file.listFiles(nameFilter)).collect(Collectors.toList());
    }

    public static boolean cleanFile(String path) {
        File file = new File(FilenameUtils.normalize(path));
        if (!file.exists()) {
            throw new RuntimeException("\u8def\u5f84\u9519\u8bef\uff1a" + path);
        }
        return file.delete();
    }

    public static void deleteFile(File file) {
        try {
            File[] files;
            if (file == null || !file.exists()) {
                System.out.println("\u6587\u4ef6\u5220\u9664\u5931\u8d25,\u8bf7\u68c0\u67e5\u6587\u4ef6\u8def\u5f84\u662f\u5426\u6b63\u786e");
                return;
            }
            for (File f : files = file.listFiles()) {
                if (f.isDirectory()) {
                    Utils.deleteFile(f);
                    continue;
                }
                f.delete();
            }
            file.delete();
        }
        catch (Exception e) {
            AttachmentLogHelper.error("\u5220\u9664\u6587\u4ef6\u5931\u8d25", e);
        }
    }

    public static String hashStr(String str) {
        MessageDigest instance;
        try {
            instance = MessageDigest.getInstance("SHA-1");
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] digest = instance.digest(str.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append("0");
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

