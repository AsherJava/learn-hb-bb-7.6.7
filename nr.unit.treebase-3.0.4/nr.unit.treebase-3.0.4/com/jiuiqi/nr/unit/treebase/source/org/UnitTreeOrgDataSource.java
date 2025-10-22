/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme
 *  com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceSchemeOfStatus
 *  com.jiuqi.nr.itreebase.source.ITreeNodeProvider
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 *  com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig
 *  org.json.JSONObject
 */
package com.jiuiqi.nr.unit.treebase.source.org;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeNodeRangeParam;
import com.jiuiqi.nr.unit.treebase.entity.counter2.IUnitTreeNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.counter2.OnlyUnitTreeNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.counter2.RangeNodesWithListCounter;
import com.jiuiqi.nr.unit.treebase.entity.counter2.RangeNodesWithRootsCounter;
import com.jiuiqi.nr.unit.treebase.entity.provider.AsyncUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.provider.RangeWithListProvider;
import com.jiuiqi.nr.unit.treebase.entity.provider.RangeWithRootsProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.search.SearchNodeWithMemory;
import com.jiuiqi.nr.unit.treebase.entity.search.SearchNodeWithMemoryExtendAllDisplayField;
import com.jiuiqi.nr.unit.treebase.enumeration.UnitTreeNodeCountPloy;
import com.jiuiqi.nr.unit.treebase.enumeration.UnitTreeRangeDisplay;
import com.jiuiqi.nr.unit.treebase.node.builder.UnitTreeNodeBuilder;
import com.jiuiqi.nr.unit.treebase.node.builder.UnitTreeNodeBuilderHelper;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuiqi.nr.unit.treebase.source.def.DefaultSelectFirstNodeProvider;
import com.jiuiqi.nr.unit.treebase.source.def.ExpandAllLevelTreeNodeProvider;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceSchemeOfStatus;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig;
import java.util.List;
import java.util.Objects;
import org.json.JSONObject;

public class UnitTreeOrgDataSource
implements IUnitTreeDataSource {
    public static final String SOURCE_ID = "unit-tree-org-data-source";
    protected final IUnitTreeContextWrapper contextWrapper;
    protected final IUnitTreeEntityDataQuery entityDataQuery;
    protected final UnitTreeSystemConfig unitTreeSystemConfig;
    protected final UnitTreeNodeBuilderHelper nodeBuilderHelper;
    private static final String TREE_EXPAND_ALL_LEVEL = "treeExpandAllLevel";

    public UnitTreeOrgDataSource(IUnitTreeContextWrapper contextWrapper, IUnitTreeEntityDataQuery entityDataQuery, UnitTreeSystemConfig unitTreeSystemConfig) {
        this.contextWrapper = contextWrapper;
        this.entityDataQuery = entityDataQuery;
        this.unitTreeSystemConfig = unitTreeSystemConfig;
        this.nodeBuilderHelper = (UnitTreeNodeBuilderHelper)BeanUtil.getBean(UnitTreeNodeBuilderHelper.class);
    }

    @Override
    public String getSourceId() {
        return SOURCE_ID;
    }

    @Override
    public IconSourceScheme[] getNodeIconSource(IUnitTreeContext ctx) {
        return new IconSourceScheme[]{this.contextWrapper.getBBLXIConSourceScheme(ctx.getEntityDefine()), new IconSourceSchemeOfStatus()};
    }

    @Override
    public ITreeNodeProvider getTreeNodeProvider(IUnitTreeContext ctx) {
        IUnitTreeEntityRowProvider entityRowProvider = this.getUnitTreeEntityRowProvider(ctx);
        IUnitTreeNodeBuilder nodeBuilder = this.getNodeBuilder(ctx, entityRowProvider);
        if (this.contextWrapper.isTreeExpandAllLevel(ctx) && this.isTreeExpandAllLevel(ctx)) {
            return new ExpandAllLevelTreeNodeProvider(entityRowProvider, nodeBuilder, ctx.getActionNode());
        }
        return new DefaultSelectFirstNodeProvider(entityRowProvider, nodeBuilder, ctx.getActionNode());
    }

    @Override
    public IUnitTreeNodeBuilder getNodeBuilder(IUnitTreeContext context, IUnitTreeEntityRowProvider entityRowProvider) {
        IconSourceProvider iconProvider = context.getIconProvider();
        UnitTreeNodeBuilder nodeBuilder = new UnitTreeNodeBuilder(entityRowProvider, iconProvider);
        return this.nodeBuilderHelper.getNodeBuilder(context, entityRowProvider, nodeBuilder);
    }

    @Override
    public IUnitTreeNodeCounter getNodeCounter(IUnitTreeContext ctx) {
        UnitTreeNodeRangeParam rangeParam = UnitTreeNodeRangeParam.translate2EntityRangeParam(ctx.getCustomVariable());
        if (null != rangeParam && rangeParam.isValidParam()) {
            UnitTreeRangeDisplay rangeType = UnitTreeRangeDisplay.toDisplay(rangeParam.getRangeType());
            switch (Objects.requireNonNull(rangeType)) {
                case RANGE_WITH_ROOTS: {
                    return new RangeNodesWithRootsCounter(ctx, this.entityDataQuery, rangeParam.getEntityDataRange());
                }
                case RANGE_WITH_TREE: 
                case RANGE_WITH_LIST: {
                    return new RangeNodesWithListCounter(ctx, this.entityDataQuery);
                }
            }
        }
        UnitTreeNodeCountPloy nodeCountPloy = UnitTreeNodeCountPloy.translatePloy(this.unitTreeSystemConfig.isCountOfDiffUnit(), this.unitTreeSystemConfig.isCountOfLeaves());
        return new OnlyUnitTreeNodeCounter(ctx, this.entityDataQuery, this.contextWrapper, nodeCountPloy);
    }

    @Override
    public ISearchNodeProvider getSearchDataProvider(IUnitTreeContext ctx) {
        IUnitTreeEntityRowProvider entityRowProvider = this.getUnitTreeEntityRowProvider(ctx);
        IUnitTreeContextWrapper contextWrapper = (IUnitTreeContextWrapper)SpringBeanUtils.getBean(IUnitTreeContextWrapper.class);
        List<IFMDMAttribute> cationFields = contextWrapper.getCationFields(ctx.getFormScheme(), ctx.getEntityDefine(), ctx.getEntityQueryPloy());
        if (cationFields == null || cationFields.isEmpty()) {
            return new SearchNodeWithMemory(entityRowProvider);
        }
        return new SearchNodeWithMemoryExtendAllDisplayField(ctx, entityRowProvider);
    }

    @Override
    public IUnitTreeEntityRowProvider getUnitTreeEntityRowProvider(IUnitTreeContext ctx) {
        UnitTreeNodeRangeParam rangeParam = UnitTreeNodeRangeParam.translate2EntityRangeParam(ctx.getCustomVariable());
        if (null != rangeParam && rangeParam.isValidParam()) {
            return this.getRangeEntityRowProvider(ctx, rangeParam);
        }
        return new AsyncUnitTreeEntityRowProvider(ctx, this.entityDataQuery, this.contextWrapper);
    }

    private boolean isTreeExpandAllLevel(IUnitTreeContext context) {
        JSONObject customVariable = context.getCustomVariable();
        return customVariable.has(TREE_EXPAND_ALL_LEVEL) && customVariable.getBoolean(TREE_EXPAND_ALL_LEVEL);
    }

    protected IUnitTreeEntityRowProvider getRangeEntityRowProvider(IUnitTreeContext ctx, UnitTreeNodeRangeParam rangeParam) {
        UnitTreeRangeDisplay rangeType = UnitTreeRangeDisplay.toDisplay(rangeParam.getRangeType());
        switch (Objects.requireNonNull(rangeType)) {
            case RANGE_WITH_ROOTS: {
                return new RangeWithRootsProvider(ctx, this.entityDataQuery, rangeParam.getEntityDataRange());
            }
        }
        return new RangeWithListProvider(ctx, this.entityDataQuery, rangeParam.getEntityDataRange());
    }
}

