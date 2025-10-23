/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.np.blob.impl;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.blob.BlobContainer;
import com.jiuqi.np.blob.util.BlobUtils;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlobContainerFileImpl
implements BlobContainer {
    private static final Logger logger = LoggerFactory.getLogger(BlobContainerFileImpl.class);
    private static String ROOT_NAME = "NpBlobStorage";
    private String containerName;

    public static void setROOTNAME(String rootname) {
        ROOT_NAME = rootname;
    }

    public BlobContainerFileImpl(String name) {
        if (name == null || "".equals(name)) {
            throw new IllegalArgumentException();
        }
        this.containerName = name;
    }

    @Override
    public boolean exists(String key) {
        return this.exists(key, "");
    }

    @Override
    public boolean exists(String key, String directory) {
        try {
            File file = this.getFile(key, directory);
            return file.exists();
        }
        catch (SecurityContentException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void uploadFromStream(String key, InputStream source) throws IOException {
        this.writeFileFromInputStream(key, "", source);
    }

    @Override
    public String uploadFromStreamExten(String extension, InputStream source) throws IOException {
        String key = BlobUtils.generateFileKey(extension);
        this.writeFileFromInputStream(key, "", source);
        return key;
    }

    @Override
    public void uploadFromStream(String key, String directory, InputStream source) throws IOException {
        this.writeFileFromInputStream(key, directory, source);
    }

    @Override
    public void uploadText(String key, String text) throws IOException {
        try {
            this.writeFileFromString(key, "", text);
        }
        catch (SecurityContentException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    @Override
    public void uploadText(String key, String directory, String text) throws IOException {
        try {
            this.writeFileFromString(key, directory, text);
        }
        catch (SecurityContentException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    private void writeFileFromString(String key, String directory, String text) throws IOException, SecurityContentException {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        if (text == null) {
            throw new IllegalArgumentException("text");
        }
        boolean append = false;
        File file = this.createIfNotExists(key, directory);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append));){
            bw.write(text);
        }
    }

    @Override
    public void downloadToStream(String key, OutputStream outStream) throws IOException {
        try {
            this.readFileToStream(key, "", outStream);
        }
        catch (SecurityContentException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    @Override
    public void downloadToStream(String key, String directory, OutputStream outStream) throws IOException {
        try {
            this.readFileToStream(key, directory, outStream);
        }
        catch (SecurityContentException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    private void readFileToStream(String key, String directory, OutputStream os) throws IOException, SecurityContentException {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        if (os == null) {
            throw new IllegalArgumentException("os");
        }
        File file = this.getFile(key, directory);
        try (FileInputStream is = new FileInputStream(file);){
            this.writeInput2Output(os, is);
        }
    }

    @Override
    public String downloadText(String key) throws IOException {
        try {
            return this.readByLines(key, "");
        }
        catch (SecurityContentException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    @Override
    public String downloadText(String key, String directory) throws IOException {
        try {
            return this.readByLines(key, directory);
        }
        catch (SecurityContentException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    private String readByLines(String key, String directory) throws IOException, SecurityContentException {
        File file = this.getFile(key, directory);
        if (!file.exists() || file.isDirectory()) {
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file));){
            String line;
            StringBuilder sb = new StringBuilder();
            String lineSep = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(lineSep);
            }
            if (sb.length() > 0) {
                sb.delete(sb.length() - lineSep.length(), sb.length());
            }
            String string = sb.toString();
            return string;
        }
    }

    @Override
    public byte[] downloadBytes(String key) throws IOException {
        try {
            return this.readFileToBytes(key, "");
        }
        catch (SecurityContentException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    @Override
    public byte[] downloadBytes(String key, String directory) throws IOException {
        try {
            return this.readFileToBytes(key, directory);
        }
        catch (SecurityContentException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /*
     * Exception decompiling
     */
    private byte[] readFileToBytes(String key, String directory) throws IOException, SecurityContentException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public void deleteIfExists(String key) {
        if (key == null || "".equals(key)) {
            return;
        }
        String path = Paths.get(this.getContainerPath(), key).toString();
        try {
            this.deleteFiles(path);
        }
        catch (SecurityContentException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void deleteIfExists(String key, String directory) {
        if (key == null || "".equals(key)) {
            return;
        }
        directory = directory == null ? "" : directory;
        String path = Paths.get(this.getContainerPath(), directory, key).toString();
        try {
            this.deleteFiles(path);
        }
        catch (SecurityContentException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteAllBlobs() {
        String containerPath = this.getContainerPath();
        try {
            this.deleteFiles(containerPath);
        }
        catch (SecurityContentException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteBlobs(String directory) {
        if (directory == null || "".equals(directory)) {
            throw new IllegalArgumentException("directory");
        }
        String path = Paths.get(this.getContainerPath(), directory).toString();
        try {
            this.deleteFiles(path);
        }
        catch (SecurityContentException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void deleteFiles(String path) throws SecurityContentException {
        File file = new File(path);
        PathUtils.validatePathManipulation((String)path);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else {
            File[] subFiles;
            for (File subfile : subFiles = file.listFiles()) {
                this.deleteFiles(subfile.getAbsolutePath());
            }
            file.delete();
        }
    }

    @Override
    public URI getURI() throws URISyntaxException {
        return this.getUri("", "");
    }

    @Override
    public URI getURI(String key) throws URISyntaxException {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        return this.getUri(key, "");
    }

    @Override
    public URI getURI(String key, String directory) throws URISyntaxException {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        if (directory == null || "".equals(directory)) {
            throw new IllegalArgumentException("directory");
        }
        return this.getUri(key, directory);
    }

    private URI getUri(String key, String directory) throws URISyntaxException {
        String path = "/" + ROOT_NAME + "/" + this.containerName;
        if (directory != null && !"".equals(directory)) {
            directory = directory.startsWith("/") ? directory : "/" + directory;
            directory = directory.endsWith("/") ? directory : directory + "/";
            path = path + directory;
        }
        if (key != null && !"".equals(key)) {
            path = path + key;
        }
        return null;
    }

    @Override
    public void startCopyFromBlob(String desDirectory, String sourceDirectory) throws IOException {
        if (desDirectory == null || "".equals(desDirectory)) {
            throw new IllegalArgumentException("desDirectory");
        }
        if (sourceDirectory == null || "".equals(sourceDirectory)) {
            throw new IllegalArgumentException("sourceDirectory");
        }
        String destPath = Paths.get(this.getContainerPath(), desDirectory).toString();
        String srcPath = Paths.get(this.getContainerPath(), sourceDirectory).toString();
        try {
            this.copyFiles(destPath, srcPath);
        }
        catch (SecurityContentException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    private void copyFiles(String destPath, String srcPath) throws IOException, SecurityContentException {
        PathUtils.validatePathManipulation((String)destPath);
        PathUtils.validatePathManipulation((String)srcPath);
        File srcFile = new File(srcPath);
        if (srcFile.exists()) {
            if (srcFile.isFile()) {
                try (FileInputStream is = new FileInputStream(srcFile);){
                    this.writeFileFromInputStream(destPath, is);
                }
            } else {
                File[] subFiles = srcFile.listFiles();
                if (subFiles.length == 0) {
                    File subDir = new File(destPath);
                    subDir.mkdirs();
                } else {
                    for (File subFile : subFiles) {
                        String subDirPath = destPath + System.getProperty("file.separator") + subFile.getName();
                        this.copyFiles(subDirPath, subFile.getAbsolutePath());
                    }
                }
            }
        }
    }

    private void writeFileFromInputStream(String key, String directory, InputStream inStream) throws IOException {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        directory = directory == null ? "" : directory;
        String path = Paths.get(this.getContainerPath(), directory, key).toString();
        try {
            this.writeFileFromInputStream(path, inStream);
        }
        catch (SecurityContentException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    private void writeFileFromInputStream(String path, InputStream is) throws IOException, SecurityContentException {
        if (path == null || "".equals(path)) {
            throw new IllegalArgumentException("path");
        }
        if (is == null) {
            throw new IllegalArgumentException("is");
        }
        File file = this.createIfNotExists(path);
        boolean append = false;
        try (BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file, append));){
            this.writeInput2Output(os, is);
        }
    }

    private void writeInput2Output(OutputStream os, InputStream is) throws IOException {
        int len;
        int size = 1024;
        byte[] data = new byte[size];
        while ((len = is.read(data, 0, size)) != -1) {
            os.write(data, 0, len);
        }
    }

    private String getContainerPath() {
        return Paths.get(this.getRootPath(), ROOT_NAME, this.containerName).toString();
    }

    private File getFile(String key, String directory) throws SecurityContentException {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        directory = directory == null ? "" : directory;
        String containerPath = this.getContainerPath();
        String path = Paths.get(containerPath, directory, key).toString();
        PathUtils.validatePathManipulation((String)path);
        File file = new File(path);
        return file;
    }

    private File createIfNotExists(String key, String directory) throws IOException, SecurityContentException {
        directory = directory == null ? "" : directory;
        String path = Paths.get(this.getContainerPath(), directory, key).toString();
        return this.createIfNotExists(path);
    }

    private File createIfNotExists(String path) throws IOException, SecurityContentException {
        PathUtils.validatePathManipulation((String)path);
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return file;
    }

    private String getRootPath() {
        if (!StringUtils.isEmpty((String)System.getProperty("jiuqi.np.blob.path"))) {
            return System.getProperty("jiuqi.np.blob.path");
        }
        return System.getProperty("java.io.tmpdir");
    }

    public static void main(String[] args) {
        BlobContainerFileImpl container = new BlobContainerFileImpl("test");
        logger.info(container.getContainerPath());
    }
}

