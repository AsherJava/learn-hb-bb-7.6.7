/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io;

import com.jiuqi.nr.table.df.Options;
import com.jiuqi.nr.table.io.Destination;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class WriteOptions
implements Options {
    protected final Destination dest;
    protected final boolean autoClose;

    protected WriteOptions(Builder builder) {
        this.dest = builder.dest;
        this.autoClose = builder.autoClose;
    }

    public Destination destination() {
        return this.dest;
    }

    public static class Builder {
        protected Destination dest;
        protected boolean autoClose = false;

        public Builder(Destination dest) {
            this.dest = dest;
        }

        protected Builder(OutputStream dest) {
            this.dest = new Destination(dest);
        }

        protected Builder(Writer dest) {
            this.dest = new Destination(dest);
        }

        protected Builder(File dest) throws IOException {
            this.dest = new Destination(dest);
            this.autoClose = true;
        }
    }
}

