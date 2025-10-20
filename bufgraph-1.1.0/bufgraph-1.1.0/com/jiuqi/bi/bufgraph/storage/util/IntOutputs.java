/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.store.DataInput
 *  org.apache.lucene.store.DataOutput
 *  org.apache.lucene.util.fst.Outputs
 */
package com.jiuqi.bi.bufgraph.storage.util;

import java.io.IOException;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.store.DataOutput;
import org.apache.lucene.util.fst.Outputs;

public class IntOutputs
extends Outputs<Integer> {
    private static final Integer NO_OUTPUT = 0;
    private static final IntOutputs singleton = new IntOutputs();

    private IntOutputs() {
    }

    public static IntOutputs getSingleton() {
        return singleton;
    }

    public Integer common(Integer output1, Integer output2) {
        assert (this.valid(output1));
        assert (this.valid(output2));
        if (output1 == NO_OUTPUT || output2 == NO_OUTPUT) {
            return NO_OUTPUT;
        }
        assert (output1 > 0);
        assert (output2 > 0);
        return Math.min(output1, output2);
    }

    public Integer subtract(Integer output, Integer inc) {
        assert (this.valid(output));
        assert (this.valid(inc));
        assert (output >= inc);
        if (inc == NO_OUTPUT) {
            return output;
        }
        if (output.equals(inc)) {
            return NO_OUTPUT;
        }
        return output - inc;
    }

    public Integer add(Integer prefix, Integer output) {
        assert (this.valid(prefix));
        assert (this.valid(output));
        if (prefix == NO_OUTPUT) {
            return output;
        }
        if (output == NO_OUTPUT) {
            return prefix;
        }
        return prefix + output;
    }

    public void write(Integer output, DataOutput out) throws IOException {
        assert (this.valid(output));
        out.writeVInt(output.intValue());
    }

    public Integer read(DataInput in) throws IOException {
        int v = in.readVInt();
        if (v == 0) {
            return NO_OUTPUT;
        }
        return v;
    }

    private boolean valid(Integer o) {
        assert (o != null);
        assert (o == NO_OUTPUT || o > 0) : "o=" + o;
        return true;
    }

    public Integer getNoOutput() {
        return NO_OUTPUT;
    }

    public String outputToString(Integer output) {
        return output.toString();
    }

    public String toString() {
        return "IntOutputs";
    }

    public long ramBytesUsed(Integer output) {
        return output < 128 ? 0L : 4L;
    }
}

