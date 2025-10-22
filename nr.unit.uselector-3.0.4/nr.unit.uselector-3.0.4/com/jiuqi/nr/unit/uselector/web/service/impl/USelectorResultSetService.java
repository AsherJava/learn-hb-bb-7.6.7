/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.entity.query.FMDMNodeTitleQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.collection.IFilterCacheSetHelper
 *  com.jiuqi.nr.itreebase.collection.IFilterStringList
 *  com.jiuqi.nr.itreebase.collection.IFilterStringListSortParam
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.tag.management.entityimpl.TagDefine
 *  com.jiuqi.nr.tag.management.environment.BaseTagContextData
 *  com.jiuqi.nr.tag.management.environment.TagAddMappingsContextData
 *  com.jiuqi.nr.tag.management.service.TagManagementConfigService
 *  javax.annotation.Resource
 *  org.json.JSONObject
 */
package com.jiuqi.nr.unit.uselector.web.service.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.query.FMDMNodeTitleQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.collection.IFilterCacheSetHelper;
import com.jiuqi.nr.itreebase.collection.IFilterStringList;
import com.jiuqi.nr.itreebase.collection.IFilterStringListSortParam;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.tag.management.entityimpl.TagDefine;
import com.jiuqi.nr.tag.management.environment.BaseTagContextData;
import com.jiuqi.nr.tag.management.environment.TagAddMappingsContextData;
import com.jiuqi.nr.tag.management.service.TagManagementConfigService;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSource;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSourceHelper;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import com.jiuqi.nr.unit.uselector.web.request.FilterSetRemoveParam;
import com.jiuqi.nr.unit.uselector.web.request.FilterSetSortParam;
import com.jiuqi.nr.unit.uselector.web.request.FilterSetTagParam;
import com.jiuqi.nr.unit.uselector.web.service.IUSelectorResultSetService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class USelectorResultSetService
implements IUSelectorResultSetService {
    @Resource
    private USelectorResultSet resultSet;
    @Resource
    private TagManagementConfigService tagManagementConfigService;
    @Resource
    private IFilterCacheSetHelper cacheSetHelper;
    @Resource
    private IUSelectorDataSourceHelper dataSourceHelper;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;
    static final String isShowFMDMAttributes = "cusRowShowFMDMAttributes";

    @Override
    public List<IBaseNodeData> getPageFilterSet(String selector, Integer fromIndex, Integer pagesize) {
        List<IEntityRow> onePageRows;
        IFilterStringList cacheSet = this.cacheSetHelper.getInstance(selector);
        int totalCount = cacheSet.size();
        fromIndex = fromIndex + 1;
        int toIndex = Math.min(fromIndex + pagesize, totalCount);
        List onePage = cacheSet.subList(fromIndex.intValue(), toIndex);
        IUnitTreeContext context = this.resultSet.getRunContext(selector);
        IUSelectorDataSource dataSource = this.dataSourceHelper.getBaseTreeDataSource(context.getDataSourceId());
        IUSelectorEntityRowProvider entityRowProvider = dataSource.getUSelectorEntityRowProvider(context);
        if (onePage != null && !onePage.isEmpty() && (onePageRows = entityRowProvider.getCheckRows(onePage)) != null && !onePageRows.isEmpty()) {
            if (this.contextWrapper.canDisplayFMDMAttributes(context.getFormScheme(), context.getEntityDefine(), context.getEntityQueryPloy())) {
                FMDMNodeTitleQuery fmdmNodeTitleQuery = new FMDMNodeTitleQuery(context);
                if (this.isCuDataSourceFMDMAttributes(context)) {
                    fmdmNodeTitleQuery.batchBuildCacheRowTitle(onePageRows);
                } else {
                    fmdmNodeTitleQuery.batchQueryCacheRowTitle(onePageRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
                }
                return onePageRows.stream().map(row -> {
                    BaseNodeDataImpl impl = new BaseNodeDataImpl();
                    impl.putKey(row.getEntityKeyData());
                    impl.putCode(row.getCode());
                    impl.putTitle(fmdmNodeTitleQuery.getAttributesTitle(row.getEntityKeyData()));
                    return impl;
                }).collect(Collectors.toList());
            }
            return onePageRows.stream().map(row -> {
                BaseNodeDataImpl impl = new BaseNodeDataImpl();
                impl.putKey(row.getEntityKeyData());
                impl.putCode(row.getCode());
                impl.putTitle(row.getTitle());
                return impl;
            }).collect(Collectors.toList());
        }
        return new ArrayList<IBaseNodeData>();
    }

    @Override
    public String sortFilterSet(FilterSetSortParam sortParam) {
        if (null != sortParam) {
            IFilterStringList cacheSet = this.cacheSetHelper.getInstance(sortParam.getSelector());
            cacheSet.sort((IFilterStringListSortParam)sortParam);
        }
        return "";
    }

    @Override
    public int removeListFromFilterSet(FilterSetRemoveParam removeParam) {
        List<String> removeList = removeParam.getRemoveList();
        if (null != removeList && !removeList.isEmpty()) {
            IFilterStringList cacheSet = this.cacheSetHelper.getInstance(removeParam.getSelector());
            cacheSet.removeAll(removeList);
            return cacheSet.size();
        }
        return 0;
    }

    @Override
    public int tagFilterSet(FilterSetTagParam tagParam) {
        String selector = tagParam.getSelector();
        IUnitTreeContext context = this.resultSet.getRunContext(selector);
        IFilterStringList cacheSet = this.cacheSetHelper.getInstance(selector);
        List filterSet = cacheSet.toList();
        BaseTagContextData tagDefineContext = new BaseTagContextData();
        tagDefineContext.setEntityId(context.getEntityDefine().getId());
        tagDefineContext.setPeriod(context.getPeriod());
        tagDefineContext.setDimValueSet(context.getDimValueSet());
        tagDefineContext.setCustomVariable(context.getCustomVariable());
        TagDefine tagDefine = new TagDefine();
        tagDefine.setTitle(tagParam.getTitle());
        tagDefine.setCategory(tagParam.getCategory());
        tagDefine.setDescription(tagParam.getDescription());
        tagDefine.setRangeModify(false);
        String tagKey = this.tagManagementConfigService.addTagDefinePurely(tagDefineContext, tagDefine);
        for (String entityData : filterSet) {
            TagAddMappingsContextData tagMappingContext = new TagAddMappingsContextData();
            tagMappingContext.setEntityData(entityData);
            tagMappingContext.setTagKeys(Collections.singletonList(tagKey));
            this.tagManagementConfigService.addTagMappingPurely(tagMappingContext);
        }
        return filterSet.size();
    }

    private boolean isCuDataSourceFMDMAttributes(IUnitTreeContext context) {
        JSONObject customVariable = context.getCustomVariable();
        if (customVariable != null && customVariable.has(isShowFMDMAttributes)) {
            return customVariable.getBoolean(isShowFMDMAttributes);
        }
        return false;
    }
}

