/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.esotericsoftware.kryo.Kryo
 *  com.esotericsoftware.kryo.Serializer
 *  com.esotericsoftware.kryo.io.Input
 *  com.esotericsoftware.kryo.io.Output
 *  com.esotericsoftware.kryo.serializers.DefaultSerializers$IntSerializer
 *  com.jiuqi.va.domain.basedata.BaseDataCacheDO
 *  org.springframework.data.redis.serializer.SerializationException
 */
package com.jiuqi.va.basedata.common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import com.jiuqi.va.domain.basedata.BaseDataCacheDO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.SerializationException;

public class BaseDataKryoSerializer {
    private static Logger logger = LoggerFactory.getLogger(BaseDataKryoSerializer.class);
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final ThreadLocal<Kryo> kryos = ThreadLocal.withInitial(Kryo::new);

    protected Kryo getKryo() {
        Kryo kryo = kryos.get();
        kryo.register(BaseDataCacheDO.class);
        kryo.register(UUID.class, (Serializer)new UUIDSerializer());
        kryo.register(Integer.class, (Serializer)new DefaultSerializers.IntSerializer());
        kryo.register(BigDecimal.class);
        kryo.register(Date.class);
        kryo.register(Map.class);
        kryo.register(ArrayList.class);
        return kryo;
    }

    /*
     * Exception decompiling
     */
    public byte[] serialize(BaseDataCacheDO obj) throws SerializationException {
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public BaseDataCacheDO deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) return null;
        if (bytes.length <= 0) {
            return null;
        }
        Kryo kryo = this.getKryo();
        try (Input input = new Input(bytes);){
            BaseDataCacheDO baseDataCacheDO = (BaseDataCacheDO)kryo.readObject(input, BaseDataCacheDO.class);
            return baseDataCacheDO;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public void set(Kryo kryo) {
        kryos.set(kryo);
    }

    public void remove() {
        kryos.remove();
    }

    private class UUIDSerializer
    extends Serializer<UUID> {
        private UUIDSerializer() {
        }

        public void write(Kryo kryo, Output output, UUID uuid) {
            output.writeLong(uuid.getMostSignificantBits());
            output.writeLong(uuid.getLeastSignificantBits());
        }

        public UUID read(Kryo kryo, Input input, Class type) {
            return new UUID(input.readLong(), input.readLong());
        }
    }
}

