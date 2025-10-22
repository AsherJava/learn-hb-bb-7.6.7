/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.util;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.util.SingleSecurityUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SinglePathUtil {
    private static final Logger logger = LoggerFactory.getLogger(SinglePathUtil.class);

    public static String getTempFilePath() throws SingleFileException {
        String tmpdir = FilenameUtils.normalize("java.io.tmpdir");
        SinglePathUtil.validatePathManipulation(tmpdir);
        String filePath = System.getProperty(tmpdir);
        String temp = filePath.substring(filePath.length() - 1, filePath.length());
        if (!temp.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        filePath = FilenameUtils.normalize(filePath);
        SinglePathUtil.validatePathManipulation(filePath);
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        return filePath;
    }

    public static String getExportTempFilePath(String pathName) throws SingleFileException {
        String filePath = SinglePathUtil.getTempFilePath();
        filePath = SinglePathUtil.createNewPath(filePath, pathName);
        return filePath;
    }

    public static String createNewPath(String filePath, String pathName) throws SingleFileException {
        filePath = SinglePathUtil.getNewPath(filePath, pathName);
        SinglePathUtil.validatePathManipulation(filePath);
        File targetFile2 = new File(FilenameUtils.normalize(filePath));
        if (!targetFile2.exists()) {
            targetFile2.mkdirs();
        }
        return filePath;
    }

    public static String getPath(String filePath) {
        if (StringUtils.isNotEmpty((String)filePath) && !filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        return filePath;
    }

    public static String getExtractPath(String fileName) throws SingleFileException {
        String dirPathName = FilenameUtils.normalize(fileName);
        SinglePathUtil.validatePathManipulation(dirPathName);
        File targetFile = new File(dirPathName);
        return targetFile.getPath();
    }

    public static String getExtractFileName(String fileName) throws SingleFileException {
        String dirPathName = FilenameUtils.normalize(fileName);
        SinglePathUtil.validatePathManipulation(dirPathName);
        File targetFile = new File(dirPathName);
        return targetFile.getName();
    }

    public static long getFileSize(String fileName) throws SingleFileException {
        String dirPathName = FilenameUtils.normalize(fileName);
        SinglePathUtil.validatePathManipulation(dirPathName);
        File targetFile = new File(dirPathName);
        return targetFile.length();
    }

    public static String getNewPath(String filePath, String pathName) {
        if (StringUtils.isNotEmpty((String)filePath) && !filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        if (StringUtils.isNotEmpty((String)pathName)) {
            filePath = filePath + pathName + File.separator;
        }
        return filePath;
    }

    public static String getNewFilePath(String filePath, String fileName) {
        if (StringUtils.isNotEmpty((String)filePath) && !filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        if (StringUtils.isNotEmpty((String)fileName)) {
            filePath = filePath + fileName;
        }
        return filePath;
    }

    public static void makeDir(String filePath) throws SingleFileException {
        String dirPathName = FilenameUtils.normalize(filePath);
        SinglePathUtil.validatePathManipulation(dirPathName);
        File targetFile = new File(dirPathName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
    }

    public static void reNameFile(String filePath, String pathSource, String pathDest) throws SingleFileException {
        String taskDataPath1 = SinglePathUtil.getNewPath(filePath, pathSource);
        String taskDataPath2 = SinglePathUtil.getNewPath(filePath, pathDest);
        taskDataPath1 = FilenameUtils.normalize(taskDataPath1);
        taskDataPath2 = FilenameUtils.normalize(taskDataPath2);
        SinglePathUtil.validatePathManipulation(taskDataPath1);
        SinglePathUtil.validatePathManipulation(taskDataPath2);
        File file1 = new File(FilenameUtils.normalize(taskDataPath1));
        File file2 = new File(FilenameUtils.normalize(taskDataPath2));
        if (file1.exists() && !file2.exists() && !file1.renameTo(file2)) {
            logger.info("\u91cd\u547d\u540d\u6587\u4ef6\u5931\u8d25\uff1a" + pathDest);
        }
    }

    public static void reNameFileName(String fileSource, String fileDest) throws SingleFileException {
        SinglePathUtil.validatePathManipulation(fileSource);
        SinglePathUtil.validatePathManipulation(fileDest);
        File file1 = new File(fileSource);
        File file2 = new File(fileDest);
        if (file1.exists() && !file2.exists() && !file1.renameTo(file2)) {
            logger.info("\u91cd\u547d\u540d\u6587\u4ef6\u5931\u8d25\uff1a" + fileDest);
        }
    }

    public static void deleteFile(String fileName) throws SingleFileException {
        String fileName2 = FilenameUtils.normalize(fileName);
        SinglePathUtil.validatePathManipulation(fileName2);
        File file = new File(fileName2);
        try {
            if (file.exists()) {
                Files.delete(file.toPath());
            }
        }
        catch (IOException e) {
            logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25\uff1a" + fileName2);
        }
    }

    public static void deleteDir(String dirPath) throws SingleFileException {
        String dirPathName = FilenameUtils.normalize(dirPath);
        SinglePathUtil.validatePathManipulation(dirPathName);
        File file = new File(dirPathName);
        if (file.isFile()) {
            try {
                Files.delete(file.toPath());
            }
            catch (IOException e) {
                logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25\uff1a" + dirPath);
            }
        } else {
            File[] files = file.listFiles();
            if (files == null) {
                try {
                    Files.delete(file.toPath());
                }
                catch (IOException e) {
                    logger.info("\u76ee\u5f55\u5220\u9664\u5931\u8d25\uff1a" + dirPath);
                }
            } else {
                for (int i = 0; i < files.length; ++i) {
                    SinglePathUtil.deleteDir(files[i].getAbsolutePath());
                }
                try {
                    Files.delete(file.toPath());
                }
                catch (IOException e) {
                    logger.info("\u76ee\u5f55\u5220\u9664\u5931\u8d25\uff1a" + dirPath);
                }
            }
        }
    }

    public static void copyDir(String sourcePath, String destPath) throws SingleFileException {
        String sourcePath1 = FilenameUtils.normalize(sourcePath);
        String destPath1 = FilenameUtils.normalize(destPath);
        SinglePathUtil.validatePathManipulation(sourcePath1);
        SinglePathUtil.validatePathManipulation(destPath1);
        File sourceFile = new File(sourcePath1);
        File destFile = new File(destPath1);
        if (sourceFile.isFile()) {
            SinglePathUtil.copyFile(sourcePath, destPath);
        } else if (!destFile.isFile()) {
            File[] sourcefiles;
            if (!destFile.exists()) {
                destFile.mkdirs();
            }
            if ((sourcefiles = sourceFile.listFiles()) != null) {
                for (int i = 0; i < sourcefiles.length; ++i) {
                    File subSourceFile = sourcefiles[i];
                    if (subSourceFile.isFile()) {
                        SinglePathUtil.copyFile(subSourceFile.getAbsolutePath(), destPath + subSourceFile.getName());
                        continue;
                    }
                    String subSourceFile1 = subSourceFile.getAbsolutePath() + File.separator;
                    String subDestFile = destPath + subSourceFile.getName() + File.separator;
                    SinglePathUtil.copyDir(subSourceFile1, subDestFile);
                }
            }
        }
    }

    public static List<String> getFileList(String path, boolean doSubDir, String fileExt) throws SingleFileException {
        SinglePathUtil.validatePathManipulation(path);
        File rootFile = new File(path);
        ArrayList<String> files = new ArrayList<String>();
        SinglePathUtil.foreachFile(rootFile.getPath(), rootFile, files, doSubDir, fileExt);
        return files;
    }

    public static List<String> getDirList(String path, boolean doSubDir) throws SingleFileException {
        SinglePathUtil.validatePathManipulation(path);
        ArrayList<String> dirs = new ArrayList<String>();
        File rootFile = new File(path);
        SinglePathUtil.foreachDir(rootFile.getPath(), rootFile, dirs, doSubDir);
        return dirs;
    }

    public static Map<String, String> getSubDirList(String path) throws SingleFileException {
        File[] subfiles;
        SinglePathUtil.validatePathManipulation(path);
        HashMap<String, String> dirs = new HashMap<String, String>();
        File rootFile = new File(path);
        if (rootFile.exists() && rootFile.isDirectory() && (subfiles = rootFile.listFiles()) != null) {
            for (File f : subfiles) {
                if (!f.isDirectory()) continue;
                String subPath = SinglePathUtil.getPath(f.getPath());
                dirs.put(f.getName(), subPath);
            }
        }
        return dirs;
    }

    public static Map<String, String> getFilesInDir(String path) throws SingleFileException {
        File[] subfiles;
        SinglePathUtil.validatePathManipulation(path);
        HashMap<String, String> files = new HashMap<String, String>();
        File rootFile = new File(path);
        if (rootFile.exists() && rootFile.isDirectory() && (subfiles = rootFile.listFiles()) != null) {
            for (File f : subfiles) {
                if (!f.isFile()) continue;
                files.put(f.getName(), f.getPath());
            }
        }
        return files;
    }

    public static boolean getFileExists(String fileName) throws SingleFileException {
        String fileName2 = FilenameUtils.normalize(fileName);
        SinglePathUtil.validatePathManipulation(fileName2);
        File file = new File(fileName2);
        return file.exists();
    }

    public static void copyFile(String sourceFile, String toFile) {
        MemStream stream = new MemStream();
        try {
            String sourceFile1 = FilenameUtils.normalize(sourceFile);
            String toFile1 = FilenameUtils.normalize(toFile);
            SinglePathUtil.validatePathManipulation(sourceFile1);
            SinglePathUtil.validatePathManipulation(toFile1);
            stream.loadFromFile(sourceFile1);
            stream.seek(0L, 0);
            stream.saveToFile(toFile1);
            stream = null;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public static void mergeFiles(List<String> sourceFiles, String sourcePath, File toFile) {
        try (FileOutputStream f0 = new FileOutputStream(toFile);
             FileChannel outChannel = f0.getChannel();){
            for (String f : sourceFiles) {
                String subFile;
                if (!StringUtils.isNotEmpty((String)f) || !SinglePathUtil.getFileExists(subFile = sourcePath + File.separator + f)) continue;
                String subFile1 = FilenameUtils.normalize(subFile);
                SinglePathUtil.validatePathManipulation(subFile1);
                FileInputStream f1 = new FileInputStream(subFile1);
                Throwable throwable = null;
                try {
                    FileChannel fc = f1.getChannel();
                    Throwable throwable2 = null;
                    try {
                        ByteBuffer bb = ByteBuffer.allocate(1024);
                        while (fc.read(bb) != -1) {
                            bb.flip();
                            outChannel.write(bb);
                            bb.clear();
                        }
                    }
                    catch (Throwable throwable3) {
                        throwable2 = throwable3;
                        throw throwable3;
                    }
                    finally {
                        if (fc == null) continue;
                        if (throwable2 != null) {
                            try {
                                fc.close();
                            }
                            catch (Throwable throwable4) {
                                throwable2.addSuppressed(throwable4);
                            }
                            continue;
                        }
                        fc.close();
                    }
                }
                catch (Throwable throwable5) {
                    throwable = throwable5;
                    throw throwable5;
                }
                finally {
                    if (f1 == null) continue;
                    if (throwable != null) {
                        try {
                            f1.close();
                        }
                        catch (Throwable throwable6) {
                            throwable.addSuppressed(throwable6);
                        }
                        continue;
                    }
                    f1.close();
                }
            }
        }
        catch (IOException ioe) {
            logger.error(ioe.getMessage(), ioe);
        }
        catch (SingleFileException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static void validatePathManipulation(String filePath) throws SingleFileException {
        SingleSecurityUtils.validatePathManipulation(filePath);
    }

    private static void foreachFile(String rootPath, File rootFile, List<String> files, boolean doSubDir, String fileExt) {
        File[] fs = rootFile.listFiles();
        if (fs != null) {
            for (File f : fs) {
                if (f.isDirectory() && doSubDir) {
                    SinglePathUtil.foreachFile(rootPath, f, files, doSubDir, fileExt);
                }
                if (!f.isFile() || !StringUtils.isEmpty((String)fileExt) && !f.getName().endsWith(fileExt)) continue;
                files.add(f.getPath());
            }
        }
    }

    private static void foreachDir(String rootPath, File rootFile, List<String> dirs, boolean doSubDir) {
        File[] fs = rootFile.listFiles();
        if (fs != null) {
            for (File f : fs) {
                if (!f.isDirectory()) continue;
                dirs.add(f.getPath());
                if (!doSubDir) continue;
                SinglePathUtil.foreachDir(rootPath, f, dirs, doSubDir);
            }
        }
    }

    public static String getFileExtensionName(String fileName) {
        int pos;
        if (StringUtils.isNotEmpty((String)fileName) && (pos = fileName.lastIndexOf(".")) > -1 && pos < fileName.length() - 1) {
            return fileName.substring(pos + 1);
        }
        return fileName;
    }

    public static String getFileNoExtensionName(String fileName) {
        int pos;
        if (StringUtils.isNotEmpty((String)fileName) && (pos = fileName.lastIndexOf(".")) > -1 && pos < fileName.length() - 1) {
            return fileName.substring(0, pos);
        }
        return fileName;
    }

    public static String normalize(String fileName) throws SingleFileException {
        String newFileName = FilenameUtils.normalize(fileName);
        SinglePathUtil.validatePathManipulation(newFileName);
        return newFileName;
    }

    public static File getNormalizeFile(String fileName) throws SingleFileException {
        String newFileName = SinglePathUtil.normalize(fileName);
        return new File(newFileName);
    }
}

