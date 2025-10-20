/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.base.common.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.config.CommonAutoConfiguration;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public class CompressUtil {
    public static boolean enableCompress(String content) {
        if (StringUtils.isEmpty((String)content)) {
            return false;
        }
        return content.length() >= CommonAutoConfiguration.getCompressMinLength();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String compress(String content) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();){
            try (DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(out);){
                deflaterOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
            }
            String string = new String(Base64.getEncoder().encodeToString(out.toByteArray()));
            return string;
        }
        catch (IOException e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String deCompress(String content) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();){
            try (InflaterOutputStream outputStream = new InflaterOutputStream(os);){
                ((OutputStream)outputStream).write(Base64.getDecoder().decode(content));
            }
            String string = new String(os.toByteArray(), StandardCharsets.UTF_8);
            return string;
        }
        catch (IOException e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
    }
}

