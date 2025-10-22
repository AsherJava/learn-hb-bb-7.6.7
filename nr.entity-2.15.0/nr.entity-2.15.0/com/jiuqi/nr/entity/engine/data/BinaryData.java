/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.data;

import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.exception.DataTypeException;
import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinaryData
extends AbstractData {
    private static final Logger logger = LoggerFactory.getLogger(BinaryData.class);
    private static final long serialVersionUID = 4436836705068529157L;
    private byte[] value;
    public static final BinaryData NULL = new BinaryData();

    public BinaryData() {
        super(37, true);
        this.value = null;
    }

    public BinaryData(byte[] bytes) {
        super(37, false);
        this.value = bytes;
    }

    @Override
    public String getAsString() {
        if (this.isNull || this.value.length <= 0) {
            return null;
        }
        try {
            return new String(this.value, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            return new String(this.value);
        }
    }

    @Override
    public Object getAsObject() throws DataTypeException {
        if (this.isNull) {
            return null;
        }
        return this.value;
    }
}

