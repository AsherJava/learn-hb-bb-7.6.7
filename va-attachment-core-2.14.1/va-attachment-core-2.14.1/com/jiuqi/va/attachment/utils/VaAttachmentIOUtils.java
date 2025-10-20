/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.exception.VaAttachmentException
 */
package com.jiuqi.va.attachment.utils;

import com.jiuqi.va.attachment.domain.exception.VaAttachmentException;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public final class VaAttachmentIOUtils {
    private static final Logger logger = LoggerFactory.getLogger(VaAttachmentIOUtils.class);

    private VaAttachmentIOUtils() {
    }

    public static void writeFileToZip(ZipOutputStream zipOutputStream, String zipFilePath, InputStream inputStream) {
        Assert.notNull((Object)inputStream, "InputStream cannot be null");
        Assert.notNull((Object)zipOutputStream, "ZipOutputStream cannot be null");
        Assert.notNull((Object)zipFilePath, "ZipFilePath cannot be null");
        try (BufferedInputStream bis = new BufferedInputStream(inputStream);){
            int bytesRead;
            zipOutputStream.putNextEntry(new ZipEntry(zipFilePath));
            byte[] buffer = new byte[8192];
            while ((bytesRead = bis.read(buffer)) != -1) {
                zipOutputStream.write(buffer, 0, bytesRead);
            }
            zipOutputStream.closeEntry();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VaAttachmentException(e);
        }
    }

    public static byte[] compress(byte[] bytes) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();){
            try (GZIPOutputStream gzip = new GZIPOutputStream(bos);){
                gzip.write(bytes);
            }
            byte[] byArray = bos.toByteArray();
            return byArray;
        }
    }

    /*
     * Exception decompiling
     */
    public static byte[] decompress(byte[] compressedData) throws IOException {
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
}

