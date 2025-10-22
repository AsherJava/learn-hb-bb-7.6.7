/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloneUtils {
    private static final Logger log = LoggerFactory.getLogger(CloneUtils.class);

    public static <T extends Serializable> T clone(T obj) {
        Serializable cloneObj = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obs = new ObjectOutputStream(out);
            obs.writeObject(obj);
            obs.close();
            ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(ios);
            cloneObj = (Serializable)ois.readObject();
            ois.close();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return (T)cloneObj;
    }
}

