/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.slf4j.LoggerFactory;

public final class FileUtil {
    private FileUtil() {
    }

    public static String getFontsDir() {
        try {
            String winDir = System.getenv("windir");
            String fileSeparator = System.getProperty("file.separator");
            return winDir + fileSeparator + "fonts";
        }
        catch (SecurityException e) {
            LoggerFactory.getLogger(FileUtil.class).warn("Can't access System.getenv(\"windir\") to load fonts. Please, add RuntimePermission for getenv.windir.");
            return null;
        }
    }

    public static boolean fileExists(String path) {
        if (path != null) {
            File f = new File(path);
            return f.exists() && f.isFile();
        }
        return false;
    }

    public static boolean isFileNotEmpty(String path) {
        if (path != null) {
            File f = new File(path);
            return f.exists() && f.isFile() && f.length() > 0L;
        }
        return false;
    }

    public static boolean directoryExists(String path) {
        if (path != null) {
            File f = new File(path);
            return f.exists() && f.isDirectory();
        }
        return false;
    }

    public static String[] listFilesInDirectory(String path, boolean recursive) {
        File[] files;
        File root;
        if (path != null && (root = new File(path)).exists() && root.isDirectory() && (files = root.listFiles()) != null) {
            Arrays.sort(files, new CaseSensitiveFileComparator());
            ArrayList<String> list = new ArrayList<String>();
            for (File file : files) {
                if (file.isDirectory() && recursive) {
                    FileUtil.listAllFiles(file.getAbsolutePath(), list);
                    continue;
                }
                list.add(file.getAbsolutePath());
            }
            return list.toArray(new String[0]);
        }
        return null;
    }

    public static File[] listFilesInDirectoryByFilter(String outPath, FileFilter fileFilter) {
        File[] result = null;
        if (outPath != null && !outPath.isEmpty()) {
            result = new File(outPath).listFiles(fileFilter);
        }
        if (result != null) {
            Arrays.sort(result, new CaseSensitiveFileComparator());
        }
        return result;
    }

    private static void listAllFiles(String dir, List<String> list) {
        File[] files = new File(dir).listFiles();
        if (files != null) {
            Arrays.sort(files, new CaseSensitiveFileComparator());
            for (File file : files) {
                if (file.isDirectory()) {
                    FileUtil.listAllFiles(file.getAbsolutePath(), list);
                    continue;
                }
                list.add(file.getAbsolutePath());
            }
        }
    }

    public static PrintWriter createPrintWriter(OutputStream output, String encoding) throws UnsupportedEncodingException {
        return new PrintWriter(new OutputStreamWriter(output, encoding));
    }

    public static OutputStream getBufferedOutputStream(String filename) throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream(filename));
    }

    public static OutputStream wrapWithBufferedOutputStream(OutputStream outputStream) {
        if (outputStream instanceof ByteArrayOutputStream || outputStream instanceof BufferedOutputStream) {
            return outputStream;
        }
        return new BufferedOutputStream(outputStream);
    }

    public static File constructFileByDirectoryAndName(String directory, String fileName) {
        return new File(directory, fileName);
    }

    public static File createTempFile(String path) throws IOException {
        File tempFile = new File(path);
        if (tempFile.isDirectory()) {
            tempFile = File.createTempFile("pdf", null, tempFile);
        }
        return tempFile;
    }

    public static FileOutputStream getFileOutputStream(File tempFile) throws FileNotFoundException {
        return new FileOutputStream(tempFile);
    }

    public static InputStream getInputStreamForFile(String path) throws IOException {
        return Files.newInputStream(Paths.get(path, new String[0]), new OpenOption[0]);
    }

    public static InputStream getInputStreamForFile(File file) throws IOException {
        return Files.newInputStream(file.toPath(), new OpenOption[0]);
    }

    public static OutputStream getFileOutputStream(String path) throws IOException {
        return Files.newOutputStream(Paths.get(path, new String[0]), new OpenOption[0]);
    }

    public static RandomAccessFile getRandomAccessFile(File tempFile) throws FileNotFoundException {
        return new RandomAccessFile(tempFile, "rw");
    }

    public static void createDirectories(String outPath) {
        new File(outPath).mkdirs();
    }

    public static String getParentDirectoryUri(File file) throws MalformedURLException {
        return file != null ? Paths.get(file.getParent(), new String[0]).toUri().toURL().toExternalForm() : "";
    }

    public static boolean deleteFile(File file) {
        return file.delete();
    }

    public static String parentDirectory(URL url) throws URISyntaxException {
        return url.toURI().resolve(".").toString();
    }

    public static File createTempFile(String tempFilePrefix, String tempFilePostfix) throws IOException {
        return File.createTempFile(tempFilePrefix, tempFilePostfix);
    }

    public static String createTempCopy(String file, String tempFilePrefix, String tempFilePostfix) throws IOException {
        Path replacementFilePath = null;
        try {
            replacementFilePath = Files.createTempFile(tempFilePrefix, tempFilePostfix, new FileAttribute[0]);
            Path pathToPassedFile = Paths.get(file, new String[0]);
            Files.copy(pathToPassedFile, replacementFilePath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            if (null != replacementFilePath) {
                FileUtil.removeFiles(new String[]{replacementFilePath.toString()});
            }
            throw e;
        }
        return replacementFilePath.toString();
    }

    public static void copy(String inputFile, String outputFile) throws IOException {
        Files.copy(Paths.get(inputFile, new String[0]), Paths.get(outputFile, new String[0]), StandardCopyOption.REPLACE_EXISTING);
    }

    public static String createTempDirectory(String tempFilePrefix) throws IOException {
        return Files.createTempDirectory(tempFilePrefix, new FileAttribute[0]).toString();
    }

    public static boolean removeFiles(String[] paths) {
        boolean allFilesAreRemoved = true;
        for (String path : paths) {
            try {
                if (null == path) continue;
                Files.delete(Paths.get(path, new String[0]));
            }
            catch (Exception e) {
                allFilesAreRemoved = false;
            }
        }
        return allFilesAreRemoved;
    }

    private static class CaseSensitiveFileComparator
    implements Comparator<File> {
        private CaseSensitiveFileComparator() {
        }

        @Override
        public int compare(File f1, File f2) {
            return f1.getPath().compareTo(f2.getPath());
        }
    }
}

