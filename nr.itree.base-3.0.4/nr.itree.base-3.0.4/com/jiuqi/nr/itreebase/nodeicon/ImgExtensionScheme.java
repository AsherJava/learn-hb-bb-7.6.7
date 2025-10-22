/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.nodeicon;

import com.jiuqi.nr.itreebase.nodeicon.ImageType;

public enum ImgExtensionScheme {
    JPG_EXT(ImageType.JPG, "data:image/jpg;base64,"),
    JPEG_EXT(ImageType.JPEG, "data:image/jpeg;base64,"),
    PNG_EXT(ImageType.PNG, "data:image/png;base64,"),
    GIF_EXT(ImageType.GIF, "data:image/gif;base64,"),
    ICON_EXT(ImageType.ICON, "data:image/x-icon;base64,"),
    SVG_EXT(ImageType.SVG, "data:image/svg+xml;base64,");

    public final ImageType type;
    public final String extensionScheme;

    private ImgExtensionScheme(ImageType type, String extensionScheme) {
        this.type = type;
        this.extensionScheme = extensionScheme;
    }

    public static ImgExtensionScheme get(ImageType type) {
        for (ImgExtensionScheme scheme : ImgExtensionScheme.values()) {
            if (scheme.type.compareTo(type) != 0) continue;
            return scheme;
        }
        return null;
    }
}

