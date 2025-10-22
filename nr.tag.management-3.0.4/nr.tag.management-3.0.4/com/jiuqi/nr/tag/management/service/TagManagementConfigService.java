/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.tag.management.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.tag.management.bean.TagFacadeImpl;
import com.jiuqi.nr.tag.management.dao.TagDefineDao;
import com.jiuqi.nr.tag.management.dao.TagMappingDao;
import com.jiuqi.nr.tag.management.entity.ITagDefine;
import com.jiuqi.nr.tag.management.entity.ITagMapping;
import com.jiuqi.nr.tag.management.entityimpl.TagDefine;
import com.jiuqi.nr.tag.management.entityimpl.TagMapping;
import com.jiuqi.nr.tag.management.enumeration.TagConfigServiceStateEnum;
import com.jiuqi.nr.tag.management.environment.BaseTagContextData;
import com.jiuqi.nr.tag.management.environment.TagAddMappingsContextData;
import com.jiuqi.nr.tag.management.environment.TagCountContextData;
import com.jiuqi.nr.tag.management.environment.TagDeleteContextData;
import com.jiuqi.nr.tag.management.environment.TagNewOrQueryUnitRangeContextData;
import com.jiuqi.nr.tag.management.environment.TagSaveContextData;
import com.jiuqi.nr.tag.management.environment.TagSaveUnitRangeContextData;
import com.jiuqi.nr.tag.management.exception.TagManageRuntimeException;
import com.jiuqi.nr.tag.management.intf.ITagFacade;
import com.jiuqi.nr.tag.management.response.PageConfig;
import com.jiuqi.nr.tag.management.response.TagCountResponse;
import com.jiuqi.nr.tag.management.response.TagManagerResponse;
import com.jiuqi.nr.tag.management.response.TagManagerShowData;
import com.jiuqi.nr.tag.management.response.TagUnitRangeData;
import com.jiuqi.nr.tag.management.service.ITagManagementConfigService;
import com.jiuqi.nr.tag.management.util.FormulaQueryUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TagManagementConfigService
implements ITagManagementConfigService {
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
    public TagManagerResponse queryAllTags(BaseTagContextData context) {
        TagManagerResponse response = new TagManagerResponse();
        PageConfig pageConfig = new PageConfig();
        pageConfig.setSystemUser(this.systemIdentityService.isSystemByUserId(NpContextHolder.getContext().getUserId()));
        response.setPageConfig(pageConfig);
        response.setRowDatas(this.getRowDatas(context));
        return response;
    }

    @Override
    public TagManagerShowData addOrCopyTag(TagNewOrQueryUnitRangeContextData context) {
        TagDefine tagDefine = new TagDefine();
        String key = UUID.randomUUID().toString();
        String owner = NpContextHolder.getContext().getUserId();
        tagDefine.setKey(key);
        tagDefine.setOwner(owner);
        tagDefine.setEntity(context.getEntityId());
        tagDefine.setShared(false);
        if (this.systemIdentityService.isSystemByUserId(owner)) {
            tagDefine.setShared(true);
        }
        if (context.getTagKey() != null && context.getTagKey().isEmpty()) {
            throw new TagManageRuntimeException("\u53d1\u751f\u5f02\u5e38\uff1atagKey\u5b58\u5728\u4f46\u503c\u4e3a\u7a7a\uff01");
        }
        if (context.getTagKey() == null) {
            this.initAddTag(tagDefine);
        }
        if (context.getTagKey() != null && !context.getTagKey().isEmpty()) {
            this.initCopyTag(tagDefine, context);
        }
        if (this.tagDefineDao.insertTagDefineRow(tagDefine) == 0) {
            throw new TagManageRuntimeException("\u6807\u8bb0\u5b9a\u4e49\u6570\u636e\u63d2\u5165\u5931\u8d25");
        }
        return this.getRowData(key);
    }

    @Override
    public TagUnitRangeData queryUnitRange(TagNewOrQueryUnitRangeContextData context) {
        TagUnitRangeData response = new TagUnitRangeData();
        response.setFormula(this.tagDefineDao.queryTagDefineRowByKey(context.getTagKey()).getFormula());
        response.setEntDataKeys(this.tagMappingDao.queryTagMappingRowsByTagKey(context.getTagKey()).stream().map(ITagMapping::getEntityData).collect(Collectors.toList()));
        return response;
    }

    @Override
    public TagConfigServiceStateEnum saveUnitRange(TagSaveUnitRangeContextData context) {
        boolean isSuccess;
        String formula = context.getTagRange().getFormula();
        List<String> entDataKeys = context.getTagRange().getEntDataKeys();
        this.tagMappingDao.deleteTagMappingRowByTagKeys(context.getTagKey());
        boolean bl = isSuccess = this.tagDefineDao.updateTagDefineFormula(context.getTagKey(), formula) > 0;
        if (!isSuccess) {
            throw new TagManageRuntimeException("\u6807\u8bb0\u5355\u4f4d\u8303\u56f4 \u7b5b\u9009\u516c\u5f0f\u66f4\u65b0\u5931\u8d25");
        }
        if (entDataKeys != null && entDataKeys.size() > 0) {
            ArrayList<ITagMapping> tagMappings = new ArrayList<ITagMapping>();
            for (String entDataKey : entDataKeys) {
                tagMappings.add(new TagMapping(context.getTagKey(), entDataKey, OrderGenerator.newOrder()));
            }
            boolean bl2 = isSuccess = this.tagMappingDao.insertTagMappingRows(tagMappings).length > 0;
            if (!isSuccess) {
                throw new TagManageRuntimeException("\u6807\u8bb0\u5355\u4f4d\u8303\u56f4 \u9009\u4e2d\u5355\u4f4d\u6620\u5c04\u63d2\u5165\u5931\u8d25");
            }
        }
        return TagConfigServiceStateEnum.SUCCESS;
    }

    @Override
    public TagConfigServiceStateEnum deleteTags(TagDeleteContextData context) {
        return this.tagDefineDao.deleteTagDefineRow(context.getTagKeys()).length > 0 && this.tagMappingDao.deleteTagMappingRowByTagKeys(context.getTagKeys()).length > 0 ? TagConfigServiceStateEnum.SUCCESS : TagConfigServiceStateEnum.FAIL;
    }

    @Override
    public TagConfigServiceStateEnum saveTags(TagSaveContextData context) {
        return this.tagDefineDao.updateTagDefineRow(context.getTagDataRows()).length > 0 ? TagConfigServiceStateEnum.SUCCESS : TagConfigServiceStateEnum.FAIL;
    }

    @Override
    public TagConfigServiceStateEnum addTagMapping(TagAddMappingsContextData context) {
        ArrayList<ITagMapping> tagMappings = new ArrayList<ITagMapping>();
        if (context.getTagKeys() == null && context.getTagKeys().size() == 0) {
            return TagConfigServiceStateEnum.FAIL;
        }
        this.tagMappingDao.deleteTagMappingRowByEntityData(context.getEntityData());
        for (String tagKey : context.getTagKeys()) {
            tagMappings.add(new TagMapping(tagKey, context.getEntityData(), OrderGenerator.newOrder()));
        }
        if (this.tagMappingDao.insertTagMappingRows(tagMappings).length > 0) {
            return TagConfigServiceStateEnum.SUCCESS;
        }
        return TagConfigServiceStateEnum.FAIL;
    }

    @Override
    public TagCountResponse countTagsUnits(TagCountContextData context) {
        if (context.getDataKeys() == null || context.getDataKeys().size() == 0) {
            return null;
        }
        TagCountResponse response = new TagCountResponse();
        ArrayList<TagFacadeImpl> tagInfos = new ArrayList<TagFacadeImpl>();
        response.setTotal(0);
        for (String tagKey : context.getDataKeys()) {
            TagFacadeImpl tagInfo = new TagFacadeImpl();
            List<ITagMapping> tagMappings = this.tagMappingDao.queryTagMappingRowsByTagKey(tagKey);
            ITagDefine tagDefine = this.tagDefineDao.queryTagDefineRowByKey(tagKey);
            if (tagDefine == null) continue;
            List mappingEntityData = tagMappings.stream().map(ITagMapping::getEntityData).collect(Collectors.toList());
            List<Object> filterEntityData = new ArrayList();
            if (tagDefine.getFormula() != null && !tagDefine.getFormula().isEmpty()) {
                filterEntityData = this.formulaQueryUtil.getFilterEntityDatas(context, tagDefine.getFormula());
            }
            List resultEntityData = Stream.of(mappingEntityData, filterEntityData).flatMap(Collection::stream).distinct().collect(Collectors.toList());
            tagInfo.setKey(tagKey);
            tagInfo.setTitle(tagDefine.getTitle() + "(" + resultEntityData.size() + ")");
            response.setTotal(response.getTotal() + resultEntityData.size());
            tagInfo.setCategory(tagDefine.getCategory());
            tagInfo.setIcon(tagDefine.getIcon());
            tagInfo.setOrder(tagDefine.getOrder());
            tagInfo.setDescription(tagDefine.getDescription());
            tagInfos.add(tagInfo);
        }
        response.setCountTagInfos(tagInfos);
        return response;
    }

    @Override
    public String addTagDefinePurely(BaseTagContextData context, TagDefine tagDefine) {
        String key = UUID.randomUUID().toString();
        String owner = NpContextHolder.getContext().getUserId();
        tagDefine.setKey(key);
        tagDefine.setOwner(owner);
        tagDefine.setEntity(context.getEntityId());
        tagDefine.setIcon("icon nr-iconfont icon-_YJWtubiao25");
        tagDefine.setShared(this.systemIdentityService.isSystemByUserId(owner));
        tagDefine.setOrder(OrderGenerator.newOrder());
        if (this.tagDefineDao.insertTagDefineRow(tagDefine) == 0) {
            return null;
        }
        return key;
    }

    @Override
    public TagConfigServiceStateEnum addTagMappingPurely(TagAddMappingsContextData context) {
        ArrayList<ITagMapping> tagMappings = new ArrayList<ITagMapping>();
        if (context.getTagKeys() == null && context.getTagKeys().size() == 0) {
            return TagConfigServiceStateEnum.FAIL;
        }
        for (String tagKey : context.getTagKeys()) {
            tagMappings.add(new TagMapping(tagKey, context.getEntityData(), OrderGenerator.newOrder()));
        }
        if (this.tagMappingDao.insertTagMappingRows(tagMappings).length > 0) {
            return TagConfigServiceStateEnum.SUCCESS;
        }
        return TagConfigServiceStateEnum.FAIL;
    }

    public void initAddTag(TagDefine tagDefine) {
        tagDefine.setTitle("\u9ed8\u8ba4\u6807\u7b7e");
        tagDefine.setIcon("icon nr-iconfont icon-_YJWtubiao25");
        tagDefine.setCategory("#000000");
        tagDefine.setOrder(OrderGenerator.newOrder());
        tagDefine.setDescription("\u6682\u65e0\u63cf\u8ff0\u4fe1\u606f");
        tagDefine.setRangeModify(true);
    }

    public void initCopyTag(TagDefine tagDefine, TagNewOrQueryUnitRangeContextData context) {
        ITagDefine sourceTag = this.tagDefineDao.queryTagDefineRowByKey(context.getTagKey());
        tagDefine.setTitle(sourceTag.getTitle() + "_\u526f\u672c");
        tagDefine.setIcon(sourceTag.getIcon());
        tagDefine.setCategory(sourceTag.getCategory());
        tagDefine.setFormula(sourceTag.getFormula());
        tagDefine.setOrder(OrderGenerator.newOrder());
        tagDefine.setDescription(sourceTag.getDescription());
        tagDefine.setRangeModify(sourceTag.getRangeModify());
        this.tagMappingDao.insertTagMappingRows(this.tagMappingDao.queryTagMappingRowsByTagKey(context.getTagKey()).stream().map(sourceTagMapping -> new TagMapping(tagDefine.getKey(), sourceTagMapping.getEntityData(), OrderGenerator.newOrder())).collect(Collectors.toList()));
    }

    public TagManagerShowData getRowData(String key) {
        return this.transferDefineToShowData(this.tagDefineDao.queryTagDefineRowByKey(key));
    }

    public List<ITagFacade> getRowDatas(BaseTagContextData context) {
        ArrayList<TagManagerShowData> rowDatas = new ArrayList<TagManagerShowData>();
        String owner = NpContextHolder.getContext().getUserId();
        String unitEntity = context.getEntityId();
        List<ITagDefine> tagDefines = this.systemIdentityService.isSystemByUserId(owner) ? this.tagDefineDao.queryTagDefineRows(owner, unitEntity) : Stream.of(this.tagDefineDao.queryTagDefineRows(owner, unitEntity), this.tagDefineDao.queryAdminTagDefineRows(unitEntity)).flatMap(Collection::stream).collect(Collectors.toList());
        for (ITagDefine tagDefine : tagDefines) {
            rowDatas.add(this.transferDefineToShowData(tagDefine));
        }
        return rowDatas.stream().sorted(Comparator.comparing(ITagFacade::getOrder)).collect(Collectors.toList());
    }

    public TagManagerShowData transferDefineToShowData(ITagDefine tagDefine) {
        StringBuilder showText;
        TagManagerShowData showDataRow = new TagManagerShowData();
        showDataRow.setKey(tagDefine.getKey());
        showDataRow.setTitle(tagDefine.getTitle());
        showDataRow.setCategory(tagDefine.getCategory());
        showDataRow.setIcon(tagDefine.getIcon());
        showDataRow.setOrder(tagDefine.getOrder());
        showDataRow.setDescription(tagDefine.getDescription());
        showDataRow.setShared(tagDefine.getShared());
        showDataRow.setRangeModify(tagDefine.getRangeModify());
        int numberOfSelect = this.tagMappingDao.queryTagMappingRowsByTagKey(tagDefine.getKey()).size();
        StringBuilder stringBuilder = showText = numberOfSelect > 0 ? new StringBuilder("\u9009\u4e2d" + numberOfSelect + "\u5bb6\u5355\u4f4d") : new StringBuilder("\u9009\u62e9\u5355\u4f4d");
        if (tagDefine.getFormula() != null && !tagDefine.getFormula().isEmpty()) {
            showText = numberOfSelect > 0 ? showText.append(" + ").append(tagDefine.getFormula()) : new StringBuilder(tagDefine.getFormula());
        }
        showDataRow.setShowText(showText.toString());
        return showDataRow;
    }
}

