/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.paramsync.domain;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VaParamSyncUtils {
    private static Logger logger = LoggerFactory.getLogger(VaParamSyncUtils.class);

    public static Map<String, byte[]> uncompress(InputStream inputStream) {
        HashMap<String, byte[]> map = new HashMap<String, byte[]>();
        try (ZipInputStream zis = new ZipInputStream(inputStream);){
            ZipEntry ze = null;
            while ((ze = zis.getNextEntry()) != null && !ze.isDirectory()) {
                String name = ze.getName();
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    Throwable throwable = null;
                    try {
                        byte[] buffer = new byte[2048];
                        int length = -1;
                        while ((length = zis.read(buffer, 0, buffer.length)) > -1) {
                            byteArrayOutputStream.write(buffer, 0, length);
                        }
                        map.put(name, byteArrayOutputStream.toByteArray());
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        if (byteArrayOutputStream == null) continue;
                        if (throwable != null) {
                            try {
                                byteArrayOutputStream.close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                            continue;
                        }
                        byteArrayOutputStream.close();
                    }
                }
                catch (Throwable e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
        return map;
    }
}

