/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.nodeicon.impl;

import com.jiuqi.nr.itreebase.nodeicon.IconSource;
import com.jiuqi.nr.itreebase.nodeicon.ImageType;

public class IconSourceImpl
implements IconSource {
    private String sourceId;
    private String key;
    private ImageType type;
    private byte[] content;
    private String imgSrc;

    public IconSourceImpl(String sourceId, String key) {
        this.sourceId = sourceId;
        this.key = key;
    }

    public IconSourceImpl(String sourceId, String key, String imgSrc) {
        this(sourceId, key);
        this.imgSrc = imgSrc;
    }

    public IconSourceImpl(String sourceId, String key, ImageType type, byte[] content) {
        this(sourceId, key);
        this.type = type;
        this.content = content;
    }

    @Override
    public String getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public ImageType getType() {
        return this.type;
    }

    public void setType(ImageType type) {
        this.type = type;
    }

    @Override
    public byte[] getContent() {
        return this.content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String getImgSrc() {
        return this.imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}

