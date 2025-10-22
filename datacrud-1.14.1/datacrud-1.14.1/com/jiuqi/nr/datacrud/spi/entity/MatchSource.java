/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityModel
 */
package com.jiuqi.nr.datacrud.spi.entity;

import com.jiuqi.nr.datacrud.spi.entity.IEntityTableWrapper;
import com.jiuqi.nr.entity.model.IEntityModel;
import java.util.List;

public class MatchSource {
    private List<String> captionCodes;
    private List<String> dropDownCaptionCodes;
    private String enumShowFullPath;
    private String entityKey;
    private IEntityModel entityModel;
    private IEntityTableWrapper entityTableWrapper;
    private boolean entityMatchAll = false;

    public List<String> getCaptionCodes() {
        return this.captionCodes;
    }

    public void setCaptionCodes(List<String> captionCodes) {
        this.captionCodes = captionCodes;
    }

    public List<String> getDropDownCaptionCodes() {
        return this.dropDownCaptionCodes;
    }

    public void setDropDownCaptionCodes(List<String> dropDownCaptionCodes) {
        this.dropDownCaptionCodes = dropDownCaptionCodes;
    }

    public String getEnumShowFullPath() {
        return this.enumShowFullPath;
    }

    public void setEnumShowFullPath(String enumShowFullPath) {
        this.enumShowFullPath = enumShowFullPath;
    }

    public IEntityTableWrapper getEntityTableWrapper() {
        return this.entityTableWrapper;
    }

    public void setEntityTableWrapper(IEntityTableWrapper entityTableWrapper) {
        this.entityTableWrapper = entityTableWrapper;
    }

    public IEntityModel getEntityModel() {
        return this.entityModel;
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public void setEntityModel(IEntityModel entityModel) {
        this.entityModel = entityModel;
    }

    public boolean isEntityMatchAll() {
        return this.entityMatchAll;
    }

    public void setEntityMatchAll(boolean entityMatchAll) {
        this.entityMatchAll = entityMatchAll;
    }
}

