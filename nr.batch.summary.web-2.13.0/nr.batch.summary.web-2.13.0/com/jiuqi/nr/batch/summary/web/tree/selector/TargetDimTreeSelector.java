/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.calibre2.ICalibreDefineService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.itreebase.context.ITreeContext
 *  com.jiuqi.nr.itreebase.source.INodeDataBuilder
 *  com.jiuqi.nr.itreebase.source.INodeDataSource
 *  com.jiuqi.nr.itreebase.source.ITreeDataSource
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.web.tree.selector;

import com.jiuqi.nr.batch.summary.web.tree.selector.TargetDimNodeBuilder;
import com.jiuqi.nr.batch.summary.web.tree.selector.TargetDimTreeDataSource;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.itreebase.context.ITreeContext;
import com.jiuqi.nr.itreebase.source.INodeDataBuilder;
import com.jiuqi.nr.itreebase.source.INodeDataSource;
import com.jiuqi.nr.itreebase.source.ITreeDataSource;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class TargetDimTreeSelector
implements ITreeDataSource {
    public static final String SOURCE_ID = "nr.batch.summary.target.dim.tree.selector";
    @Resource
    private IRunTimeViewController rtController;
    @Resource
    private IEntityMetaService entityMetaService;
    @Resource
    private ICalibreDefineService calibreDefineService;

    public String getSourceId() {
        return SOURCE_ID;
    }

    public INodeDataSource getNodeDataSource(ITreeContext context) {
        return new TargetDimTreeDataSource(this.entityMetaService, this.calibreDefineService, this.rtController, context);
    }

    public INodeDataBuilder getNodeBuilder(ITreeContext context, INodeDataSource dataSource) {
        return new TargetDimNodeBuilder(dataSource);
    }
}

