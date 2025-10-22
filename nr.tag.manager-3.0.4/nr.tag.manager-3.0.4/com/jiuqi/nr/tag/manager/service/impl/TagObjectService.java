/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.manager.service.impl;

import com.jiuqi.nr.tag.manager.bean.TagManagerInitInfo;
import com.jiuqi.nr.tag.manager.bean.TagManagerPara;
import com.jiuqi.nr.tag.manager.bean.TagObject;
import com.jiuqi.nr.tag.manager.bean.TagsOfEntityDataModify;
import com.jiuqi.nr.tag.manager.service.impl.TagService;
import com.jiuqi.nr.tag.manager.web.response.TagsOfEntityData;
import java.util.List;

public interface TagObjectService
extends TagService {
    public boolean modifyTagsOfEntity(TagManagerPara var1);

    public boolean modifyTagsOfEntityData(TagsOfEntityDataModify var1);

    public List<TagObject> countTagsOfEntity(String var1);

    public TagManagerInitInfo initTagManager(TagManagerPara var1);

    public TagsOfEntityData countTagsOfOneEntityData(String var1, String var2);

    public boolean checkTagTitleRepeat(String var1);
}

