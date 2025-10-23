/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.BasicDBObject
 *  com.mongodb.DB
 *  com.mongodb.DBObject
 *  com.mongodb.MongoClient
 *  com.mongodb.gridfs.GridFS
 *  com.mongodb.gridfs.GridFSDBFile
 *  com.mongodb.gridfs.GridFSInputFile
 */
package com.jiuqi.np.blob.impl;

import com.jiuqi.np.blob.BlobContainer;
import com.jiuqi.np.blob.util.BlobUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class BlobContainerMongoImpl
implements BlobContainer {
    private static final Logger logger = LoggerFactory.getLogger(BlobContainerMongoImpl.class);
    private static final String DIR_SEP = "\\";
    private GridFS _gridFS = null;
    private static Object lock = new Object();
    private String bucket = "";

    public BlobContainerMongoImpl(String dataBase, String name, MongoClient client) {
        if (name == null || "".equals(name)) {
            throw new IllegalArgumentException("name");
        }
        if (client == null) {
            throw new IllegalArgumentException("client");
        }
        this.bucket = name;
        DB db = null;
        db = dataBase != null && !dataBase.equals("") ? client.getDB(dataBase) : client.getDB("NpBlobStorage");
        this._gridFS = new GridFS(db, name.replace("/", "_"));
    }

    @Override
    public boolean exists(String key) {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        GridFSDBFile file = this._gridFS.findOne(key);
        return file != null;
    }

    @Override
    public boolean exists(String key, String directory) {
        key = this.joinKey(key, directory);
        return this.exists(key);
    }

    @Override
    public void uploadFromStream(String key, InputStream source) throws IOException {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        if (source == null) {
            throw new IllegalArgumentException("source");
        }
        GridFSDBFile oldFile = this._gridFS.findOne(key);
        if (oldFile != null) {
            this._gridFS.remove(key);
        }
        GridFSInputFile file = this._gridFS.createFile(source, key);
        file.save();
    }

    @Override
    public String uploadFromStreamExten(String extension, InputStream source) throws IOException {
        if (source == null) {
            throw new IllegalArgumentException("source");
        }
        String key = BlobUtils.generateFileKey(extension);
        GridFSDBFile oldFile = this._gridFS.findOne(key);
        if (oldFile != null) {
            this._gridFS.remove(key);
        }
        GridFSInputFile file = this._gridFS.createFile(source, key);
        file.save();
        return key;
    }

    @Override
    public void uploadFromStream(String key, String directory, InputStream source) throws IOException {
        key = this.joinKey(key, directory);
        this.uploadFromStream(key, source);
    }

    @Override
    public void uploadText(String key, String text) throws IOException {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        if (text == null) {
            throw new IllegalArgumentException("text");
        }
        GridFSDBFile oldFile = this._gridFS.findOne(key);
        if (oldFile != null) {
            this._gridFS.remove(key);
        }
        try (ByteArrayInputStream is = new ByteArrayInputStream(text.getBytes());){
            GridFSInputFile file = this._gridFS.createFile((InputStream)is, key);
            file.save();
        }
    }

    @Override
    public void uploadText(String key, String directory, String text) throws IOException {
        key = this.joinKey(key, directory);
        this.uploadText(key, text);
    }

    @Override
    public void downloadToStream(String key, OutputStream outStream) throws IOException {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        if (outStream == null) {
            throw new IllegalArgumentException("outStream");
        }
        GridFSDBFile file = this._gridFS.findOne(key);
        if (file != null) {
            file.writeTo(outStream);
        }
    }

    @Override
    public void downloadToStream(String key, String directory, OutputStream outStream) throws IOException {
        key = this.joinKey(key, directory);
        this.downloadToStream(key, outStream);
    }

    /*
     * Loose catch block
     */
    @Override
    public String downloadText(String key) throws IOException {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        try (FileInputStream iss = new FileInputStream(File.createTempFile(key, ""));){
            GridFSDBFile file = this._gridFS.findOne(key);
            if (file != null) {
                Throwable throwable = null;
                try (InputStream is = file.getInputStream();){
                    if (is != null) {
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is));){
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
                    {
                        catch (Throwable throwable2) {
                            throwable = throwable2;
                            throw throwable2;
                        }
                        catch (Throwable throwable3) {
                            throw throwable3;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String downloadText(String key, String directory) throws IOException {
        key = this.joinKey(key, directory);
        return this.downloadText(key);
    }

    /*
     * Exception decompiling
     */
    @Override
    public byte[] downloadBytes(String key) throws IOException {
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
    public byte[] downloadBytes(String key, String directory) throws IOException {
        key = this.joinKey(key, directory);
        return this.downloadBytes(key);
    }

    @Override
    public void deleteIfExists(String key) {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        this._gridFS.remove(key);
    }

    public void deleteIfExists(String key, String directory) {
        key = this.joinKey(key, directory);
        this.deleteIfExists(key);
    }

    @Override
    public void deleteAllBlobs() {
        this._gridFS.remove((DBObject)null);
    }

    @Override
    public void deleteBlobs(String directory) {
        if (directory == null || "".equals(directory)) {
            throw new IllegalArgumentException("directory");
        }
        BasicDBObject searchQuery = this.getDirQuery(directory);
        this._gridFS.remove((DBObject)searchQuery);
    }

    @Override
    public URI getURI() throws URISyntaxException {
        return null;
    }

    @Override
    public URI getURI(String key) throws URISyntaxException {
        return null;
    }

    @Override
    public URI getURI(String key, String directory) throws URISyntaxException {
        return null;
    }

    @Override
    public void startCopyFromBlob(String desDirectory, String sourceDirectory) throws IOException {
    }

    private String joinKey(String key, String directory) {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        return directory == null || "".equals(directory) ? key : directory + DIR_SEP + key;
    }

    private BasicDBObject getDirQuery(String directory) {
        if (directory == null || "".equals(directory)) {
            throw new IllegalArgumentException("directory");
        }
        directory = StringUtils.trimTrailingCharacter(directory, '\\') + DIR_SEP;
        directory = directory.replace(DIR_SEP, "\\\\");
        Pattern pattern = Pattern.compile("^" + directory, 2);
        return new BasicDBObject("filename", (Object)pattern);
    }

    private List<GridFSDBFile> ListFiles(String directory) {
        BasicDBObject searchQuery = this.getDirQuery(directory);
        return this._gridFS.find((DBObject)searchQuery);
    }

    private void writeInput2Output(OutputStream os, InputStream is) throws IOException {
        int len;
        int size = 1024;
        byte[] data = new byte[size];
        while ((len = is.read(data, 0, size)) != -1) {
            os.write(data, 0, len);
        }
    }

    public static void main(String[] args) throws IOException {
        BlobContainerMongoImpl.batchTest();
        logger.info("OK");
    }

    private static void batchTest() throws IOException {
        int i;
        MongoClient client = new MongoClient("10.2.38.86");
        BlobContainerMongoImpl container = new BlobContainerMongoImpl("", "test", client);
        int loops = 100;
        int fileSize = 10240;
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        for (int i2 = 0; i2 < loops; ++i2) {
            map.put(i2, BlobContainerMongoImpl.newText(fileSize, String.valueOf(i2)));
        }
        StringBuilder log = new StringBuilder();
        logger.info("\u5f00\u59cb\u4e0a\u4f20\uff1a");
        long start = System.currentTimeMillis();
        for (int i3 = 0; i3 < loops; ++i3) {
            String text = (String)map.get(i3);
            container.uploadText("key_" + i3, "batch", text);
        }
        long end = System.currentTimeMillis();
        logger.info("\u4e0a\u4f20\u5b8c\u6210");
        log.append("\u4e0a\u4f20\u6267\u884c\u65f6\u95f4\uff1a" + (end - start) + "ms\n");
        logger.info("\u5f00\u59cb\u8bfb\u53d6");
        start = System.currentTimeMillis();
        for (i = 0; i < loops; ++i) {
            byte[] bytes = container.downloadBytes("key_" + i, "batch");
        }
        end = System.currentTimeMillis();
        logger.info("\u8bfb\u53d6\u5b8c\u6210");
        log.append("\u8bfb\u53d6\u6267\u884c\u65f6\u95f4\uff1a" + (end - start) + "ms\n");
        logger.info("\u5f00\u59cb\u5220\u9664");
        start = System.currentTimeMillis();
        for (i = 0; i < loops; ++i) {
            container.deleteBlobs("batch");
        }
        end = System.currentTimeMillis();
        logger.info("\u5220\u9664\u5b8c\u6210");
        log.append("\u5220\u9664\u6267\u884c\u65f6\u95f4\uff1a" + (end - start) + "ms");
        logger.info(log.toString());
    }

    private static void commTest() throws IOException {
        Throwable throwable;
        InputStream is;
        Throwable throwable2;
        InputStream is2;
        MongoClient client = new MongoClient("10.2.38.86");
        BlobContainerMongoImpl container = new BlobContainerMongoImpl(null, "test", client);
        List<GridFSDBFile> files = container.ListFiles("a\\\\b\\\\");
        if (!container.exists("test.jpg")) {
            is2 = BlobContainerMongoImpl.getInput("C:\\test.jpg");
            throwable2 = null;
            try {
                container.uploadFromStream("test.jpg", is2);
            }
            catch (Throwable throwable3) {
                throwable2 = throwable3;
                throw throwable3;
            }
            finally {
                if (is2 != null) {
                    if (throwable2 != null) {
                        try {
                            is2.close();
                        }
                        catch (Throwable throwable4) {
                            throwable2.addSuppressed(throwable4);
                        }
                    } else {
                        is2.close();
                    }
                }
            }
        }
        throwable2 = null;
        try (OutputStream os = BlobContainerMongoImpl.getOutput("C:\\output\\test.jpg");){
            container.downloadToStream("test.jpg", os);
        }
        catch (Throwable throwable5) {
            throwable2 = throwable5;
            throw throwable5;
        }
        container.deleteIfExists("test.jpg");
        is2 = BlobContainerMongoImpl.getInput("C:\\OnKeyDetector.log");
        throwable2 = null;
        try {
            container.uploadFromStream("OnKeyDetector", is2);
        }
        catch (Throwable throwable6) {
            throwable2 = throwable6;
            throw throwable6;
        }
        finally {
            if (is2 != null) {
                if (throwable2 != null) {
                    try {
                        is2.close();
                    }
                    catch (Throwable throwable7) {
                        throwable2.addSuppressed(throwable7);
                    }
                } else {
                    is2.close();
                }
            }
        }
        String text = container.downloadText("OnKeyDetector");
        logger.info(text);
        if (!container.exists("aa.png", "a\\b")) {
            is = BlobContainerMongoImpl.getInput("C:\\aa.png");
            throwable = null;
            try {
                container.uploadFromStream("aa.png", "a\\b", is);
            }
            catch (Throwable throwable8) {
                throwable = throwable8;
                throw throwable8;
            }
            finally {
                if (is != null) {
                    if (throwable != null) {
                        try {
                            is.close();
                        }
                        catch (Throwable throwable9) {
                            throwable.addSuppressed(throwable9);
                        }
                    } else {
                        is.close();
                    }
                }
            }
        }
        throwable = null;
        try (OutputStream os = BlobContainerMongoImpl.getOutput("C:\\output\\aa.png");){
            container.downloadToStream("aa.png", "a\\b", os);
        }
        catch (Throwable throwable10) {
            throwable = throwable10;
            throw throwable10;
        }
        is = BlobContainerMongoImpl.getInput("C:\\OnKeyDetector.log");
        throwable = null;
        try {
            container.uploadFromStream("OnKeyDetector", "a\\b", is);
        }
        catch (Throwable throwable11) {
            throwable = throwable11;
            throw throwable11;
        }
        finally {
            if (is != null) {
                if (throwable != null) {
                    try {
                        is.close();
                    }
                    catch (Throwable throwable12) {
                        throwable.addSuppressed(throwable12);
                    }
                } else {
                    is.close();
                }
            }
        }
        text = container.downloadText("OnKeyDetector", "a\\b");
        container.deleteBlobs("a\\b");
        logger.info(text);
    }

    private static InputStream getInput(String path) throws IOException {
        File file = new File(path);
        return new FileInputStream(file);
    }

    private static OutputStream getOutput(String path) throws IOException {
        File file = new File(path);
        return new FileOutputStream(file);
    }

    private static String newText(int length, String symbol) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(symbol);
        }
        return sb.toString();
    }
}

