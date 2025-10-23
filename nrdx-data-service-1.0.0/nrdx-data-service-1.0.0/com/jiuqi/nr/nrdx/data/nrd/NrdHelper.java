/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.nrdx.adapter.Const
 *  com.jiuqi.nvwa.encryption.crypto.SymmetricDecryptor
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.nrdx.data.nrd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.nrdx.adapter.Const;
import com.jiuqi.nr.nrdx.data.nrd.CheckRes;
import com.jiuqi.nr.nrdx.data.nrd.INRDHelper;
import com.jiuqi.nvwa.encryption.crypto.SymmetricDecryptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class NrdHelper
implements INRDHelper {
    private static final String TMP_DIR = Const.TMP_DIR + "NRD";
    private static final Logger log = LoggerFactory.getLogger(NrdHelper.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public CheckRes isNRD(MultipartFile fileData) {
        CheckRes checkRes = new CheckRes();
        File file = null;
        try (InputStream inputStream = fileData.getInputStream();){
            String originalFilename = fileData.getOriginalFilename();
            if (!StringUtils.hasText(originalFilename)) {
                throw new IllegalArgumentException("filename is empty");
            }
            String filePath = TMP_DIR + File.separator + originalFilename;
            filePath = FilenameUtils.normalize(filePath);
            file = NrdHelper.createIfNotExists(filePath);
            NrdHelper.writeInputStreamToFile(inputStream, file);
            checkRes.setTmpFile(file);
            checkRes.setNrd(NrdHelper.isNrdFile(file));
            if (checkRes.isNrd()) {
                checkRes.setEncryptType(0);
            } else {
                checkRes.setEncryptType(NrdHelper.getEncryptType(file));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        if (file != null) {
            try {
                checkRes.setOriginalData(Files.newInputStream(file.toPath(), new OpenOption[0]));
            }
            catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return checkRes;
    }

    private static File createIfNotExists(String path) throws IOException {
        boolean newFile;
        boolean mkdirs;
        File file = new File(FilenameUtils.normalize(path));
        if (!file.getParentFile().exists() && !(mkdirs = file.getParentFile().mkdirs())) {
            throw new IOException("Failed to create directory " + file.getParentFile());
        }
        if (!file.exists() && !(newFile = file.createNewFile())) {
            throw new IOException("Failed to create file " + file);
        }
        return file;
    }

    private static void writeInputStreamToFile(InputStream inputStream, File file) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, read);
            }
        }
    }

    /*
     * Exception decompiling
     */
    private static boolean isNrdFile(File file) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static int getEncryptType(File file) {
        try (FileInputStream fis = new FileInputStream(file);){
            if (SymmetricDecryptor.isEncrypt((InputStream)fis)) {
                int n2 = 1;
                return n2;
            }
            int n = 0;
            return n;
        }
        catch (Exception e) {
            return 2;
        }
    }
}

