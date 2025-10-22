/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.tag.manager.resultset;

import com.jiuqi.nr.tag.manager.entitydata.query.ITagEntityDataQuery;
import com.jiuqi.nr.tag.manager.entitydata.query.TagQueryParamImpl;
import com.jiuqi.nr.tag.manager.resultset.ITagRecordData;
import com.jiuqi.nr.tag.manager.resultset.ITagRecordDataHelper;
import com.jiuqi.nr.tag.manager.resultset.TagRecordData;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class TagRecordDataHelper
implements ITagRecordDataHelper {
    @Resource
    private ITagEntityDataQuery entityDataQuery;

    @Override
    public ITagRecordData getITagRecordData(TagQueryParamImpl queryParam) {
        return new TagRecordData(queryParam, this.entityDataQuery);
    }
}

