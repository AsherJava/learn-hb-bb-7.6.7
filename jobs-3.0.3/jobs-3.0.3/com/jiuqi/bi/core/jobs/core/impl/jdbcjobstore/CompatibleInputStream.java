/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.core.impl.jdbcjobstore;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompatibleInputStream
extends ObjectInputStream {
    private static Logger logger = LoggerFactory.getLogger(CompatibleInputStream.class);

    public CompatibleInputStream(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        Class<?> localClass;
        ObjectStreamClass resultClassDescriptor = super.readClassDescriptor();
        try {
            localClass = Class.forName(resultClassDescriptor.getName());
        }
        catch (ClassNotFoundException e) {
            logger.error("No local class for " + resultClassDescriptor.getName(), e);
            return resultClassDescriptor;
        }
        ObjectStreamClass localClassDescriptor = ObjectStreamClass.lookup(localClass);
        if (localClassDescriptor != null) {
            long localSUID = localClassDescriptor.getSerialVersionUID();
            long streamSUID = resultClassDescriptor.getSerialVersionUID();
            if (streamSUID != localSUID) {
                resultClassDescriptor = localClassDescriptor;
            }
        }
        return resultClassDescriptor;
    }
}

