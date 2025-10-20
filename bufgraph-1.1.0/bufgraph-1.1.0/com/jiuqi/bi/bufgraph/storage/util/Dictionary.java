/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.util.BytesRef
 *  org.apache.lucene.util.IntsRefBuilder
 *  org.apache.lucene.util.fst.Builder
 *  org.apache.lucene.util.fst.ByteSequenceOutputs
 *  org.apache.lucene.util.fst.FST
 *  org.apache.lucene.util.fst.FST$INPUT_TYPE
 *  org.apache.lucene.util.fst.Outputs
 *  org.apache.lucene.util.fst.PositiveIntOutputs
 *  org.apache.lucene.util.fst.Util
 */
package com.jiuqi.bi.bufgraph.storage.util;

import com.jiuqi.bi.bufgraph.storage.util.IntOutputs;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IntsRefBuilder;
import org.apache.lucene.util.fst.Builder;
import org.apache.lucene.util.fst.ByteSequenceOutputs;
import org.apache.lucene.util.fst.FST;
import org.apache.lucene.util.fst.Outputs;
import org.apache.lucene.util.fst.PositiveIntOutputs;
import org.apache.lucene.util.fst.Util;

public class Dictionary<T> {
    private FST<T> fst;
    private Builder<T> builder;
    private Outputs<T> outputs;
    private IntsRefBuilder scratchInts;
    private String lastStr = "";

    private Dictionary() {
    }

    public static final Dictionary<Integer> newIntDict() {
        Dictionary<Integer> dict = new Dictionary<Integer>();
        dict.outputs = IntOutputs.getSingleton();
        dict.builder = new Builder(FST.INPUT_TYPE.BYTE1, dict.outputs);
        dict.scratchInts = new IntsRefBuilder();
        return dict;
    }

    public static final Dictionary<Long> newLongDict() {
        Dictionary<Long> dict = new Dictionary<Long>();
        dict.outputs = PositiveIntOutputs.getSingleton();
        dict.builder = new Builder(FST.INPUT_TYPE.BYTE1, dict.outputs);
        dict.scratchInts = new IntsRefBuilder();
        return dict;
    }

    public static final Dictionary<BytesRef> newBytesDict() {
        Dictionary<BytesRef> dict = new Dictionary<BytesRef>();
        dict.outputs = ByteSequenceOutputs.getSingleton();
        dict.builder = new Builder(FST.INPUT_TYPE.BYTE1, dict.outputs);
        dict.scratchInts = new IntsRefBuilder();
        return dict;
    }

    public void addDict(String word, T value) throws IOException {
        if (this.lastStr.compareTo(word) > 0) {
            throw new IOException("\u5b57\u5178\u8868\u6784\u9020\u8fc7\u7a0b\u4e2d\uff0c\u6240\u6709\u7684\u5b57\u5178\u5fc5\u987b\u662f\u6709\u5e8f\u63d2\u5165\uff08\u4ece\u5c0f\u5230\u5927\uff09");
        }
        this.lastStr = word;
        this.builder.add(Util.toIntsRef((BytesRef)new BytesRef((CharSequence)word), (IntsRefBuilder)this.scratchInts), value);
    }

    public void finish() throws IOException {
        this.fst = this.builder.finish();
    }

    public int getInt(String word) throws IOException {
        Object v = Util.get(this.fst, (BytesRef)new BytesRef((CharSequence)word));
        if (v == null) {
            return -1;
        }
        return (Integer)v;
    }

    public long getLong(String word) throws IOException {
        Object v = Util.get(this.fst, (BytesRef)new BytesRef((CharSequence)word));
        if (v == null) {
            return -1L;
        }
        return (Long)v;
    }

    public byte[] getBytes(String word) throws IOException {
        Object v = Util.get(this.fst, (BytesRef)new BytesRef((CharSequence)word));
        if (v == null) {
            return null;
        }
        return ((BytesRef)v).bytes;
    }

    public void save(File file) throws IOException {
        this.fst.save(file.toPath());
    }

    public void load(File file) throws IOException {
        this.fst = FST.read((Path)file.toPath(), this.outputs);
    }

    public static void main(String[] args) throws IOException {
        int i;
        DecimalFormat numFormat = new DecimalFormat("0000000000000000");
        SimpleDateFormat df = new SimpleDateFormat("mm:ss SSS");
        System.out.println("prepare-----" + df.format(new Date()));
        Dictionary<Integer> dict = Dictionary.newIntDict();
        String[] tmp = new String[100];
        int t = 0;
        String org = "";
        for (i = 0; i < 5000000; ++i) {
            String guid = "ABCDEFGHUIGK_" + numFormat.format(i);
            if (org.compareTo(guid) > 0) continue;
            org = guid;
            dict.addDict(guid, (int)(Math.random() * 100.0));
            if (t >= 100) continue;
            tmp[t++] = guid;
        }
        dict.finish();
        System.out.println("finish -----" + df.format(new Date()));
        System.out.println("query  -----" + df.format(new Date()));
        for (i = 0; i < 100; ++i) {
            int pos = dict.getInt(tmp[i]);
            System.out.print(pos + "\t");
        }
        System.out.println();
        System.out.println("finish -----" + df.format(new Date()));
        System.out.println("save   -----" + df.format(new Date()));
        dict.save(new File("C:/wumingxing/Temp/graphtest/fst"));
        System.out.println("finish -----" + df.format(new Date()));
        System.out.println("load   -----" + df.format(new Date()));
        dict.load(new File("C:/wumingxing/Temp/graphtest/fst"));
        System.out.println("finish -----" + df.format(new Date()));
    }
}

