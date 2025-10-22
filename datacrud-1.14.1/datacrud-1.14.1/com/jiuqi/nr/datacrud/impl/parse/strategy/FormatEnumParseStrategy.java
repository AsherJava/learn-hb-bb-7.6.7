/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.datacrud.impl.parse.strategy;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.datacrud.ParseReturnRes;
import com.jiuqi.nr.datacrud.api.IEntityRowSearcher;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.parse.strategy.EnumParseStrategy;
import com.jiuqi.nr.datacrud.spi.IEntityTableFactory;
import com.jiuqi.nr.datacrud.spi.entity.MatchSource;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

public class FormatEnumParseStrategy
extends EnumParseStrategy {
    private boolean entityMatchAll = false;
    protected final IEntityMetaService entityMetaService;
    protected final IEntityRowSearcher entityRowSearcher;
    protected Map<String, List<String>> captionCodesMap = new HashMap<String, List<String>>();
    protected Map<String, List<String>> dropDownCaptionCodesMap = new HashMap<String, List<String>>();
    protected Map<String, IEntityModel> entityModelMap = new HashMap<String, IEntityModel>();

    public boolean isEntityMatchAll() {
        return this.entityMatchAll;
    }

    public void setEntityMatchAll(boolean entityMatchAll) {
        this.entityMatchAll = entityMatchAll;
    }

    public FormatEnumParseStrategy(RegionRelation regionRelation, IEntityTableFactory entityTableFactory, IEntityMetaService entityMetaService, IEntityRowSearcher entityRowSearcher) {
        super(regionRelation, entityTableFactory);
        this.entityMetaService = entityMetaService;
        this.entityRowSearcher = entityRowSearcher;
    }

    @Override
    public ParseReturnRes parseEntityIds(DataLinkDefine link, String refDataEntityKey, boolean isAllowMultipleSelect, Set<String> chooseEntity, Object data) {
        String dataStr = data.toString();
        boolean single = !isAllowMultipleSelect;
        MetaData metaData = this.regionRelation.getMetaDataByLink(link.getKey());
        List<String> entityItems = this.splitEnumCode(dataStr, false);
        List<MetaData> filledEnumLinks = this.regionRelation.getFilledEnumLinks();
        Set fillLinkSet = filledEnumLinks.stream().map(MetaData::getDataLinkDefine).map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        if (this.entityTableWrapper == null) {
            this.entityTableWrapper = this.entityTableFactory.getEntityTable(this.regionRelation, this.currMasterKey, metaData);
        } else {
            this.entityTableFactory.reBuildEntityTable(this.regionRelation, this.currMasterKey, metaData, this.entityTableWrapper);
        }
        List<String> captionCodes = this.getCaptionCodes(link, refDataEntityKey);
        List<String> dropDownCaptionCodes = this.getDropDownCaptionCodes(link, refDataEntityKey);
        String enumShowFullPath = link.getEnumShowFullPath();
        IEntityModel entityModel = this.entityModelMap.computeIfAbsent(refDataEntityKey, refKey -> this.entityMetaService.getEntityModel(refDataEntityKey));
        MatchSource matchSource = new MatchSource();
        matchSource.setCaptionCodes(captionCodes);
        matchSource.setDropDownCaptionCodes(dropDownCaptionCodes);
        matchSource.setEnumShowFullPath(enumShowFullPath);
        matchSource.setEntityTableWrapper(this.entityTableWrapper);
        matchSource.setEntityKey(refDataEntityKey);
        matchSource.setEntityModel(entityModel);
        matchSource.setEntityMatchAll(this.entityMatchAll);
        for (String entityItem : entityItems) {
            int directChildCount;
            IEntityRow entityRow = this.entityRowSearcher.search(matchSource, entityItem);
            if (!link.getAllowNotLeafNodeRefer() && entityRow != null && !fillLinkSet.contains(link.getKey()) && (directChildCount = this.entityTableWrapper.getDirectChildCount(entityRow.getEntityKeyData())) > 0) {
                String message = "\u4e0d\u5141\u8bb8\u9009\u62e9\u975e\u53f6\u5b50\u8282\u70b9\u3002\u9519\u8bef\u503c:" + dataStr;
                this.crudLogger.dataCheckFail(message);
                return this.errRes(dataStr, message);
            }
            if (entityRow != null) {
                chooseEntity.add(entityRow.getEntityKeyData());
            } else if (Boolean.TRUE.equals(link.getAllowUndefinedCode())) {
                chooseEntity.add(entityItem);
            } else {
                String message = "\u672a\u627e\u5230\u5bf9\u5e94\u7684\u679a\u4e3e\u9879\uff0c\u6216\u8005\u627e\u5230\u7684\u679a\u4e3e\u9879\u4e0d\u7b26\u5408\u6761\u4ef6\u3002\u9519\u8bef\u503c:" + dataStr;
                this.crudLogger.dataCheckFail(message);
                return this.errRes(dataStr, message);
            }
            if (!single) continue;
            break;
        }
        return null;
    }

    private List<String> getDropDownCaptionCodes(DataLinkDefine link, String refDataEntityKey) {
        return this.dropDownCaptionCodesMap.computeIfAbsent(link.getKey(), k -> {
            ArrayList<String> list = new ArrayList<String>();
            if (!StringUtils.hasLength(link.getDropDownFieldsString())) {
                IEntityModel entityModel = this.entityModelMap.computeIfAbsent(refDataEntityKey, refKey -> this.entityMetaService.getEntityModel(refDataEntityKey));
                IEntityAttribute nameField = entityModel.getNameField();
                list.add(nameField.getCode());
            } else {
                list.addAll(Arrays.asList(link.getDropDownFieldsString().split(";")));
            }
            return list;
        });
    }

    private List<String> getCaptionCodes(DataLinkDefine link, String refDataEntityKey) {
        return this.captionCodesMap.computeIfAbsent(link.getKey(), k -> {
            ArrayList<String> list = new ArrayList<String>();
            if (!StringUtils.hasLength(link.getCaptionFieldsString())) {
                IEntityModel entityModel = this.entityModelMap.computeIfAbsent(refDataEntityKey, refKey -> this.entityMetaService.getEntityModel(refDataEntityKey));
                IEntityAttribute nameField = entityModel.getNameField();
                list.add(nameField.getCode());
            } else {
                list.addAll(Arrays.asList(link.getCaptionFieldsString().split(";")));
            }
            return list;
        });
    }
}

