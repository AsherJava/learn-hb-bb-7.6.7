/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.model;

import com.jiuqi.nr.entity.model.ITreeStruct;
import java.io.Serializable;

public interface IEntityDefine
extends Serializable {
    public String getId();

    public String getCode();

    public String getTitle();

    public String getDesc();

    public Integer getDimensionFlag();

    public Integer getIncludeSubTreeEntity();

    public String getDimensionName();

    public ITreeStruct getTreeStruct();

    public Integer getIsolation();

    public Integer getVersion();

    public Boolean isTree();

    public String getGroup();

    public Boolean isAuthFlag();

    public String getCategory();
}

