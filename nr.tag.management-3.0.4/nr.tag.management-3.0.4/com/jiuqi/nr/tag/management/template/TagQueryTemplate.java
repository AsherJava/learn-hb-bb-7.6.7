/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.tag.management.template;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.tag.management.dao.TagDefineDao;
import com.jiuqi.nr.tag.management.dao.TagMappingDao;
import com.jiuqi.nr.tag.management.entity.ITagDefine;
import com.jiuqi.nr.tag.management.entity.ITagMapping;
import com.jiuqi.nr.tag.management.exception.TagManageRuntimeException;
import com.jiuqi.nr.tag.management.intf.ITagFacade;
import com.jiuqi.nr.tag.management.intf.ITagQueryContext;
import com.jiuqi.nr.tag.management.intf.ITagQueryTemplate;
import com.jiuqi.nr.tag.management.intf.NRTagQueryTemplate;
import com.jiuqi.nr.tag.management.response.TagManagerShowData;
import com.jiuqi.nr.tag.management.util.FormulaQueryUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;

@NRTagQueryTemplate(value="TagQueryTemplate")
public class TagQueryTemplate
implements ITagQueryTemplate {
    @Resource
    private TagDefineDao tagDefineDao;
    @Resource
    private TagMappingDao tagMappingDao;
    @Resource
    private IRunTimeViewController iRunTimeViewController;
    @Resource
    private SystemIdentityService systemIdentityService;
    @Resource
    private FormulaQueryUtil formulaQueryUtil;

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public List<ITagFacade> getInfoTags(ITagQueryContext context) {
        ArrayList<ITagFacade> tagInfos = new ArrayList<ITagFacade>();
        String owner = NpContextHolder.getContext().getUserId();
        List<ITagDefine> tagDefines = this.systemIdentityService.isSystemByUserId(owner) ? this.tagDefineDao.queryTagDefineRows(owner, context.getEntityId()) : Stream.of(this.tagDefineDao.queryTagDefineRows(owner, context.getEntityId()), this.tagDefineDao.queryAdminTagDefineRows(context.getEntityId())).flatMap(Collection::stream).collect(Collectors.toList());
        for (ITagDefine tagDefine : tagDefines) {
            TagManagerShowData tagManagerShowData = new TagManagerShowData();
            tagManagerShowData.setKey(tagDefine.getKey());
            tagManagerShowData.setTitle(tagDefine.getTitle());
            tagManagerShowData.setCategory(tagDefine.getCategory());
            tagManagerShowData.setIcon(tagDefine.getIcon());
            tagManagerShowData.setDescription(tagDefine.getDescription());
            tagManagerShowData.setShared(tagDefine.getShared());
            tagManagerShowData.setRangeModify(tagDefine.getRangeModify());
            tagInfos.add(tagManagerShowData);
        }
        return tagInfos;
    }

    @Override
    public List<ITagFacade> getInfoTags(ITagQueryContext context, List<String> tagKeys) {
        return this.getInfoTags(context).stream().filter(tagInfo -> tagKeys.contains(tagInfo.getKey())).collect(Collectors.toList());
    }

    @Override
    public Map<String, List<String>> tagCountUnits(ITagQueryContext context, List<String> tagKeys) {
        HashMap<String, List<String>> dataSet = new HashMap<String, List<String>>();
        for (String tagKey : tagKeys) {
            dataSet.put(tagKey, this.getTagAllEntityDatas(context, tagKey));
        }
        return dataSet;
    }

    @Override
    public Map<String, List<String>> unitCountTags(ITagQueryContext context, List<String> entDataKeys) {
        HashMap<String, List<String>> dataSet = new HashMap<String, List<String>>();
        String owner = NpContextHolder.getContext().getUserId();
        List<String> tagKeys = (this.systemIdentityService.isSystemByUserId(owner) ? this.tagDefineDao.queryTagDefineRows(owner, context.getEntityId()) : Stream.of(this.tagDefineDao.queryTagDefineRows(owner, context.getEntityId()), this.tagDefineDao.queryAdminTagDefineRows(context.getEntityId())).flatMap(Collection::stream).collect(Collectors.toList())).stream().map(ITagDefine::getKey).collect(Collectors.toList());
        Map<String, List<String>> tagAndItsAllEntityData = this.tagCountUnits(context, tagKeys);
        for (String entDataKey : entDataKeys) {
            ArrayList filterTagKeys = new ArrayList();
            tagAndItsAllEntityData.forEach((tagKey, allEntityData) -> {
                if (allEntityData.contains(entDataKey)) {
                    filterTagKeys.add(tagKey);
                }
            });
            dataSet.put(entDataKey, filterTagKeys);
        }
        return dataSet;
    }

    public List<String> getTagAllEntityDatas(ITagQueryContext context, String tagKey) {
        List<ITagMapping> tagMappings = this.tagMappingDao.queryTagMappingRowsByTagKey(tagKey);
        ITagDefine tagDefine = this.tagDefineDao.queryTagDefineRowByKey(tagKey);
        List mappingEntityData = tagMappings.stream().map(ITagMapping::getEntityData).collect(Collectors.toList());
        List<Object> filterEntityData = new ArrayList();
        try {
            if (tagDefine != null && tagDefine.getFormula() != null && !tagDefine.getFormula().isEmpty()) {
                filterEntityData = this.formulaQueryUtil.getFilterEntityDatas(context, tagDefine.getFormula());
            }
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            throw new TagManageRuntimeException(e);
        }
        return Stream.of(mappingEntityData, filterEntityData).flatMap(Collection::stream).distinct().collect(Collectors.toList());
    }
}

