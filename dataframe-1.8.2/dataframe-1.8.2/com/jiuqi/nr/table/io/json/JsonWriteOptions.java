/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io.json;

import com.jiuqi.nr.table.io.Destination;
import com.jiuqi.nr.table.io.WriteOptions;
import java.io.Writer;

public class JsonWriteOptions
extends WriteOptions {
    public JsonWriteOptions(Builder builder) {
        super(builder);
    }

    public static Builder builder(Writer writer) {
        return new Builder(new Destination(writer));
    }

    public static Builder builder(Destination destination) {
        return new Builder(destination);
    }

    public static class Builder
    extends WriteOptions.Builder {
        private boolean asObjects = true;
        private boolean header = false;

        protected Builder(Destination destination) {
            super(destination);
        }

        public Builder asObjects(boolean asObjects) {
            this.asObjects = asObjects;
            return this;
        }

        public Builder header(boolean header) {
            this.header = header;
            return this;
        }

        public JsonWriteOptions build() {
            return new JsonWriteOptions(this);
        }
    }
}

