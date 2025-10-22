/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  com.jiuqi.nr.itreebase.source.ITreeNodeProvider
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 */
package com.jiuqi.nr.batch.summary.service.ext.unittree;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.batch.summary.service.ext.unittree.BSEntityRowQuery;
import com.jiuqi.nr.batch.summary.service.ext.unittree.FSEntityRowProvider;
import com.jiuqi.nr.batch.summary.service.ext.unittree.FSTreeDataProvider;
import com.jiuqi.nr.batch.summary.service.ext.unittree.FSTreeNodeBuilder;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;

public class FSUnitTreeSource
implements IUnitTreeDataSource {
    private BSEntityRowQuery entityRowQuery;
    private FormSchemeDefine formSchemeDefine;
    private IUnitTreeContextWrapper contextWrapper;

    public FSUnitTreeSource(FormSchemeDefine formSchemeDefine, BSEntityRowQuery entityRowQuery) {
        this.entityRowQuery = entityRowQuery;
        this.formSchemeDefine = formSchemeDefine;
        this.contextWrapper = (IUnitTreeContextWrapper)SpringBeanUtils.getBean(IUnitTreeContextWrapper.class);
    }

    public String getSourceId() {
        return "form.scheme.unit.tree.source";
    }

    public ITreeNodeProvider getTreeNodeProvider(IUnitTreeContext ctx) {
        IUnitTreeEntityRowProvider entityRowProvider = this.getUnitTreeEntityRowProvider(ctx);
        IUnitTreeNodeBuilder nodeBuilder = this.getNodeBuilder(ctx, entityRowProvider);
        return new FSTreeDataProvider(entityRowProvider, nodeBuilder, ctx.getActionNode());
    }

    public IUnitTreeNodeBuilder getNodeBuilder(IUnitTreeContext ctx, IUnitTreeEntityRowProvider entityRowProvider) {
        IconSourceProvider iconProvider = ctx.getIconProvider();
        IUnitTreeNodeBuilder nodeBuilder = super.getNodeBuilder(ctx, entityRowProvider);
        IEntityRefer referBBLXEntity = this.contextWrapper.getBBLXEntityRefer(ctx.getEntityDefine());
        return new FSTreeNodeBuilder(this.formSchemeDefine, nodeBuilder, iconProvider, referBBLXEntity);
    }

    public ISearchNodeProvider getSearchDataProvider(IUnitTreeContext ctx) {
        return null;
    }

    public IUnitTreeEntityRowProvider getUnitTreeEntityRowProvider(IUnitTreeContext ctx) {
        return new FSEntityRowProvider(this.formSchemeDefine, this.entityRowQuery.makeFullTreeData(ctx));
    }
}

