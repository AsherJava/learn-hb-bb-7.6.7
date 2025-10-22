/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model;

import com.jiuqi.nr.survey.model.FileQuestion;

public class FilePoolQuestion
extends FileQuestion {
    private String acceptedTypes;
    private boolean allowMultiple;
    private boolean allowImagesPreview;
    private String imageHeight;
    private String imageWidth;
    private int maxSize;
    private boolean needConfirmRemoveFile;
    private String defaultValue;

    @Override
    public String getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getAcceptedTypes() {
        return this.acceptedTypes;
    }

    @Override
    public void setAcceptedTypes(String acceptedTypes) {
        this.acceptedTypes = acceptedTypes;
    }

    @Override
    public boolean isAllowMultiple() {
        return this.allowMultiple;
    }

    @Override
    public void setAllowMultiple(boolean allowMultiple) {
        this.allowMultiple = allowMultiple;
    }

    @Override
    public boolean isAllowImagesPreview() {
        return this.allowImagesPreview;
    }

    @Override
    public void setAllowImagesPreview(boolean allowImagesPreview) {
        this.allowImagesPreview = allowImagesPreview;
    }

    @Override
    public String getImageHeight() {
        return this.imageHeight;
    }

    @Override
    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    @Override
    public String getImageWidth() {
        return this.imageWidth;
    }

    @Override
    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    @Override
    public int getMaxSize() {
        return this.maxSize;
    }

    @Override
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public boolean isNeedConfirmRemoveFile() {
        return this.needConfirmRemoveFile;
    }

    @Override
    public void setNeedConfirmRemoveFile(boolean needConfirmRemoveFile) {
        this.needConfirmRemoveFile = needConfirmRemoveFile;
    }
}

