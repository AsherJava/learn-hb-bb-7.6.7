/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model;

import com.jiuqi.nr.survey.model.Element;

public class FileQuestion
extends Element {
    private String acceptedTypes;
    private boolean allowMultiple;
    private boolean allowImagesPreview;
    private String imageHeight;
    private String imageWidth;
    private int maxSize;
    private boolean needConfirmRemoveFile;
    private String defaultValue;
    private int maxNums;

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getAcceptedTypes() {
        return this.acceptedTypes;
    }

    public void setAcceptedTypes(String acceptedTypes) {
        this.acceptedTypes = acceptedTypes;
    }

    public boolean isAllowMultiple() {
        return this.allowMultiple;
    }

    public void setAllowMultiple(boolean allowMultiple) {
        this.allowMultiple = allowMultiple;
    }

    public boolean isAllowImagesPreview() {
        return this.allowImagesPreview;
    }

    public void setAllowImagesPreview(boolean allowImagesPreview) {
        this.allowImagesPreview = allowImagesPreview;
    }

    public String getImageHeight() {
        return this.imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getImageWidth() {
        return this.imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isNeedConfirmRemoveFile() {
        return this.needConfirmRemoveFile;
    }

    public void setNeedConfirmRemoveFile(boolean needConfirmRemoveFile) {
        this.needConfirmRemoveFile = needConfirmRemoveFile;
    }

    public int getMaxNums() {
        return this.maxNums;
    }

    public void setMaxNums(int maxNums) {
        this.maxNums = maxNums;
    }
}

