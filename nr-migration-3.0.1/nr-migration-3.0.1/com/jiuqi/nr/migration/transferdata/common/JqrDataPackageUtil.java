/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.migration.transferdata.common;

import com.jiuqi.np.definition.common.UUIDUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JqrDataPackageUtil {
    private static final Logger logger = LoggerFactory.getLogger(JqrDataPackageUtil.class);
    private static final int BUFFER_SIZE = 8192;

    /*
     * Exception decompiling
     */
    public static byte[] getByteData(File f) throws IOException {
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

    /*
     * Exception decompiling
     */
    public static byte[] getByteData(InputStream is) throws IOException {
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

    public static File writeByteToFile(String fileName, byte[] data) throws Exception {
        File file = JqrDataPackageUtil.createTempFile(fileName);
        try (FileOutputStream os = new FileOutputStream(file);){
            os.write(data);
        }
        return file;
    }

    public static Map<String, byte[]> getFileDataMap(String fileName, byte[] data) throws Exception {
        File jqrZipFile = JqrDataPackageUtil.writeByteToFile(fileName, data);
        return JqrDataPackageUtil.getFileDataMap(jqrZipFile);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Map<String, byte[]> getFileDataMap(File jqrTempFile) {
        try (ZipFile zipFile = new ZipFile(jqrTempFile, Charset.forName("GBK"));){
            HashMap<String, byte[]> importFileData = new HashMap<String, byte[]>();
            Enumeration<? extends ZipEntry> emu = zipFile.entries();
            while (emu.hasMoreElements()) {
                ZipEntry entry = emu.nextElement();
                if (entry.isDirectory()) continue;
                String entryName = entry.getName();
                byte[] fileData = JqrDataPackageUtil.getFileData(zipFile, entry);
                importFileData.put(entryName, fileData);
            }
            HashMap<String, byte[]> hashMap = importFileData;
            return hashMap;
        }
        catch (IOException e) {
            logger.error("\u8bfb\u53d6JQR\u6587\u4ef6\u65f6\u53d1\u751fIO\u5f02\u5e38", e);
            throw new RuntimeException(e);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static byte[] getFileData(ZipFile zipFile, ZipEntry entry) {
        try (InputStream is = zipFile.getInputStream(entry);){
            byte[] byArray = JqrDataPackageUtil.getByteData(is);
            return byArray;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File createTempFile(String fileName) throws IOException {
        File tempDir = JqrDataPackageUtil.createTempDir();
        String dir = tempDir.getAbsolutePath();
        logger.info("{}:\u521b\u5efa\u4e34\u65f6\u6587\u4ef6:{}", (Object)"JQR\u6570\u636e\u5305\u5bfc\u5165", (Object)fileName);
        File file = new File(dir + File.separator + fileName);
        if (file.createNewFile()) {
            return file;
        }
        throw new RuntimeException("JQR\u6570\u636e\u5305\u5bfc\u5165:\u521b\u5efa\u4e34\u65f6\u6587\u4ef6\u5931\u8d25");
    }

    public static ByteArrayOutputStream getBAOStream(Map<String, byte[]> dataMap) throws IOException {
        return JqrDataPackageUtil.compress(dataMap);
    }

    /*
     * Exception decompiling
     */
    private static ByteArrayOutputStream compress(Map<String, byte[]> filemap) throws IOException {
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

    private static File createTempDir() throws IOException {
        String tempDir;
        File tempDirFile;
        String sysTempDir = System.getProperty("java.io.tmpdir");
        if (!sysTempDir.endsWith(File.separator)) {
            sysTempDir = sysTempDir + File.separator;
        }
        if ((tempDirFile = new File(tempDir = sysTempDir + UUIDUtils.getKey().replace("-", ""))).mkdir()) {
            logger.info("{}:\u521b\u5efa\u4e34\u65f6\u76ee\u5f55[{}]", (Object)"JQR\u6570\u636e\u5305\u5bfc\u5165", (Object)tempDirFile.getName());
            return tempDirFile;
        }
        throw new IOException("JQR\u6570\u636e\u5305\u5bfc\u5165:\u521b\u5efa\u4e34\u65f6\u76ee\u5f55\u5931\u8d25");
    }
}

