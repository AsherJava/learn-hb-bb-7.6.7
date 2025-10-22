/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.service;

import com.jiuqi.nr.tag.management.entityimpl.TagDefine;
import com.jiuqi.nr.tag.management.enumeration.TagConfigServiceStateEnum;
import com.jiuqi.nr.tag.management.environment.BaseTagContextData;
import com.jiuqi.nr.tag.management.environment.TagAddMappingsContextData;
import com.jiuqi.nr.tag.management.environment.TagCountContextData;
import com.jiuqi.nr.tag.management.environment.TagDeleteContextData;
import com.jiuqi.nr.tag.management.environment.TagNewOrQueryUnitRangeContextData;
import com.jiuqi.nr.tag.management.environment.TagSaveContextData;
import com.jiuqi.nr.tag.management.environment.TagSaveUnitRangeContextData;
import com.jiuqi.nr.tag.management.response.TagCountResponse;
import com.jiuqi.nr.tag.management.response.TagManagerResponse;
import com.jiuqi.nr.tag.management.response.TagManagerShowData;
import com.jiuqi.nr.tag.management.response.TagUnitRangeData;

public interface ITagManagementConfigService {
    public TagManagerResponse queryAllTags(BaseTagContextData var1);

    public TagManagerShowData addOrCopyTag(TagNewOrQueryUnitRangeContextData var1);

    public TagUnitRangeData queryUnitRange(TagNewOrQueryUnitRangeContextData var1);

    public TagConfigServiceStateEnum saveUnitRange(TagSaveUnitRangeContextData var1);

    public TagConfigServiceStateEnum deleteTags(TagDeleteContextData var1);

    public TagConfigServiceStateEnum saveTags(TagSaveContextData var1);

    public TagConfigServiceStateEnum addTagMapping(TagAddMappingsContextData var1);

    public TagCountResponse countTagsUnits(TagCountContextData var1);

    public String addTagDefinePurely(BaseTagContextData var1, TagDefine var2);

    public TagConfigServiceStateEnum addTagMappingPurely(TagAddMappingsContextData var1);
}

