/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.itreebase.nodeicon;

import com.jiuqi.bi.util.StringUtils;

public enum ImageType {
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    GIF("gif"),
    ICON("icon"),
    SVG("svg");

    private final String value;

    private ImageType(String value) {
        this.value = value;
    }

    public static ImageType getType(String imageExtension) {
        if (StringUtils.isNotEmpty((String)imageExtension)) {
            for (ImageType type : ImageType.values()) {
                if (!type.value.equalsIgnoreCase(imageExtension)) continue;
                return type;
            }
        }
        return null;
    }
}

