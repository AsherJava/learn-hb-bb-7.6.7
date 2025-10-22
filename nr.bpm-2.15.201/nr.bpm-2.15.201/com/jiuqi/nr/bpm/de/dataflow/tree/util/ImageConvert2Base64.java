/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.bpm.de.dataflow.tree.util;

import com.jiuqi.bi.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;

public class ImageConvert2Base64 {
    private static Map<ImageType, String> extension2Scheme = new HashMap<ImageType, String>();

    private static ImageType getScheme(String imageExtension) {
        if (StringUtils.isNotEmpty((String)imageExtension)) {
            for (ImageType type : ImageType.values()) {
                if (!type.toString().equalsIgnoreCase(imageExtension)) continue;
                return type;
            }
        }
        return null;
    }

    public static String toBase64(InputStream inputStream, ImageType type) throws IOException {
        byte[] imageBytes = IOUtils.toByteArray(inputStream);
        String imageBase64 = ImageConvert2Base64.bytesEncode2Base64(imageBytes);
        if (null != type) {
            imageBase64 = extension2Scheme.get((Object)type) + imageBase64;
        }
        return imageBase64;
    }

    public static String toBase64(InputStream inputStream, String imageExtension) throws IOException {
        ImageType type = ImageConvert2Base64.getScheme(imageExtension);
        return ImageConvert2Base64.toBase64(inputStream, type);
    }

    private static String bytesEncode2Base64(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
    }

    static {
        extension2Scheme.put(ImageType.JPG, "data:image/jpg;base64,");
        extension2Scheme.put(ImageType.JPEG, "data:image/jpeg;base64,");
        extension2Scheme.put(ImageType.PNG, "data:image/png;base64,");
        extension2Scheme.put(ImageType.GIF, "data:image/gif;base64,");
        extension2Scheme.put(ImageType.ICON, "data:image/x-icon;base64,");
        extension2Scheme.put(ImageType.SVG, "data:image/svg+xml;base64,");
    }

    public static enum ImageType {
        JPG,
        JPEG,
        PNG,
        GIF,
        ICON,
        SVG;

    }
}

