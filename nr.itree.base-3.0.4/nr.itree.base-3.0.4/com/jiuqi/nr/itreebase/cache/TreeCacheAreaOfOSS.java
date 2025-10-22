/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException
 */
package com.jiuqi.nr.itreebase.cache;

import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.nr.itreebase.cache.ITreeCacheArea;
import com.jiuqi.nr.itreebase.lock.ISourceLockManager;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class TreeCacheAreaOfOSS
implements ITreeCacheArea {
    private static final String EXT = ".uscsd";
    private final ObjectStorageService objectStorage = this.loadOSSObjectStorage();

    public TreeCacheAreaOfOSS(ISourceLockManager sourceLockManager) {
    }

    @Override
    public boolean contains(String sourceId) {
        try {
            return this.objectStorage.existObject(sourceId);
        }
        catch (ObjectStorageException e) {
            throw new UnitTreeRuntimeException((Throwable)e);
        }
    }

    @Override
    public <T extends Serializable> T getCacheData(String sourceId, Class<T> clazz) {
        try {
            if (this.objectStorage.existObject(sourceId)) {
                InputStream is = this.objectStorage.download(sourceId);
                ObjectInputStream ois = new ObjectInputStream(is);
                return (T)((Serializable)clazz.cast(ois.readObject()));
            }
            return null;
        }
        catch (ObjectStorageException | IOException | ClassNotFoundException e) {
            throw new UnitTreeRuntimeException(e);
        }
    }

    @Override
    public <T extends Serializable> void putCacheData(String sourceId, T contentData) {
        try {
            if (this.objectStorage.existObject(sourceId)) {
                this.objectStorage.deleteObject(sourceId);
            }
            byte[] objBytes = this.serialize(contentData);
            ByteArrayInputStream bis = new ByteArrayInputStream(objBytes);
            this.objectStorage.upload(sourceId, (InputStream)bis);
        }
        catch (ObjectStorageException e) {
            throw new UnitTreeRuntimeException((Throwable)e);
        }
    }

    public String getFileName(String sourceId) {
        return sourceId + EXT;
    }

    private ObjectStorageService loadOSSObjectStorage() {
        try {
            return ObjectStorageManager.getInstance().createTemporaryObjectService();
        }
        catch (ObjectStorageException e) {
            throw new UnitTreeRuntimeException((Throwable)e);
        }
    }

    /*
     * Exception decompiling
     */
    protected <T extends Serializable> byte[] serialize(T object) {
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
     * Exception decompiling
     */
    protected <T extends Serializable> T deserialize(byte[] bytes, Class<T> clazz) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 1[TRYBLOCK]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
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

