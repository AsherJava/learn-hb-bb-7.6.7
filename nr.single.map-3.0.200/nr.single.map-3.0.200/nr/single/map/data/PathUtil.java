/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 */
package nr.single.map.data;

import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.File;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathUtil {
    private static final Logger logger = LoggerFactory.getLogger(PathUtil.class);

    public static String getExportTempFilePath(String pathName) throws SingleFileException {
        return SinglePathUtil.getExportTempFilePath((String)pathName);
    }

    public static String createNewPath(String filePath, String pathName) throws SingleFileException {
        return SinglePathUtil.createNewPath((String)filePath, (String)pathName);
    }

    public static String getNewPath(String filePath, String pathName) {
        return SinglePathUtil.getNewPath((String)filePath, (String)pathName);
    }

    public static void reNameFile(String filePath, String pathSource, String pathDest) throws SingleFileException {
        SinglePathUtil.reNameFile((String)filePath, (String)pathSource, (String)pathDest);
    }

    public static void reNameFileName(String fileSource, String fileDest) throws SingleFileException {
        SinglePathUtil.reNameFileName((String)fileSource, (String)fileDest);
    }

    public static void deleteFile(String fileName) throws SingleFileException {
        SinglePathUtil.deleteFile((String)fileName);
    }

    public static void deleteDir(String dirPath) throws SingleFileException {
        SinglePathUtil.deleteDir((String)dirPath);
    }

    public static List<String> getFileList(String path, boolean doSubDir, String fileExt) throws SingleFileException {
        return SinglePathUtil.getFileList((String)path, (boolean)doSubDir, (String)fileExt);
    }

    public static boolean getFileExists(String fileName) throws SingleFileException {
        return SinglePathUtil.getFileExists((String)fileName);
    }

    public static void copyFile(String sourceFile, String toFile) {
        SinglePathUtil.copyFile((String)sourceFile, (String)toFile);
    }

    public static void mergeFiles(List<String> sourceFiles, String sourcePath, File toFile) {
        SinglePathUtil.mergeFiles(sourceFiles, (String)sourcePath, (File)toFile);
    }

    public static String getFileExtensionName(String fileName) {
        return SinglePathUtil.getFileExtensionName((String)fileName);
    }

    public static String getFileNoExtensionName(String fileName) {
        return SinglePathUtil.getFileNoExtensionName((String)fileName);
    }
}

