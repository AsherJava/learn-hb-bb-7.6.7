/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.datacrud.impl.parse.strategy;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.datacrud.ParseReturnRes;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.parse.strategy.StringParseStrategy;
import com.jiuqi.nr.datacrud.spi.IEntityTableFactory;
import com.jiuqi.nr.datacrud.spi.entity.IEntityTableWrapper;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class EnumParseStrategy
extends StringParseStrategy {
    protected final IEntityTableFactory entityTableFactory;
    protected final RegionRelation regionRelation;
    protected DimensionCombination currMasterKey;
    protected IEntityTableWrapper entityTableWrapper;

    public EnumParseStrategy(RegionRelation regionRelation, IEntityTableFactory entityTableFactory) {
        this.regionRelation = regionRelation;
        this.entityTableFactory = entityTableFactory;
    }

    @Override
    public void setRowKey(DimensionCombination masterKey) {
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
        for (FixedDimensionValue fixedDimensionValue : masterKey) {
            builder.setValue(fixedDimensionValue);
        }
        this.currMasterKey = builder.getCombination();
    }

    @Override
    public ParseReturnRes checkParse(DataLinkDefine link, DataField field, Object data) {
        String refDataEntityKey;
        if (field != null && StringUtils.hasLength(refDataEntityKey = field.getRefDataEntityKey())) {
            ReturnRes returnRes = this.checkNonNull(link, field, data);
            if (returnRes != null && returnRes.getCode() != 0) {
                return new ParseReturnRes(returnRes);
            }
            if (ObjectUtils.isEmpty(data)) {
                return this.okValue(null);
            }
            LinkedHashSet<String> chooseEntity = new LinkedHashSet<String>();
            ParseReturnRes res = this.parseEntityIds(link, refDataEntityKey, field.isAllowMultipleSelect(), chooseEntity, data);
            if (res != null) {
                return res;
            }
            int precision = field.getPrecision() == null ? 0 : field.getPrecision();
            String value = String.join((CharSequence)";", chooseEntity);
            if (precision > 0 && precision < value.length()) {
                String message = "\u6307\u6807:" + field.getTitle() + ";\u6570\u636e\u8d85\u51fa\u6307\u6807\u957f\u5ea6" + precision + "\u3002\u9519\u8bef\u503c:" + value;
                this.crudLogger.dataCheckFail(message);
                return this.errRes(data, message);
            }
            return this.okValue(value);
        }
        return super.checkParse(link, field, data);
    }

    @Override
    public ParseReturnRes checkParse(DataLinkDefine link, IFMDMAttribute attribute, Object data) {
        String refDataEntityKey;
        if (attribute != null && StringUtils.hasLength(refDataEntityKey = attribute.getReferEntityId())) {
            ReturnRes returnRes = this.checkNonNull(link, attribute, data);
            if (returnRes != null && returnRes.getCode() != 0) {
                return new ParseReturnRes(returnRes);
            }
            if (ObjectUtils.isEmpty(data)) {
                return this.okValue(null);
            }
            HashSet<String> chooseEntity = new HashSet<String>();
            ParseReturnRes res = this.parseEntityIds(link, refDataEntityKey, attribute.isMultival(), chooseEntity, data);
            if (res != null) {
                return res;
            }
            int precision = attribute.getPrecision();
            String value = String.join((CharSequence)";", chooseEntity);
            if (precision > 0 && precision < value.length()) {
                String message = "\u6307\u6807:" + attribute.getTitle() + ";\u6570\u636e\u8d85\u51fa\u6307\u6807\u957f\u5ea6" + precision + "\u3002\u9519\u8bef\u503c:" + value;
                this.crudLogger.dataCheckFail(message);
                return this.errRes(data, message);
            }
            return this.okValue(value);
        }
        return super.checkParse(link, attribute, data);
    }

    public ParseReturnRes parseEntityIds(DataLinkDefine link, String refDataEntityKey, boolean isAllowMultipleSelect, Set<String> chooseEntity, Object data) {
        String dataStr = data.toString();
        boolean single = !isAllowMultipleSelect;
        List<String> entityItems = this.splitEnumCode(dataStr, true);
        List<MetaData> filledEnumLinks = this.regionRelation.getFilledEnumLinks();
        Set fillLinkSet = filledEnumLinks.stream().map(MetaData::getDataLinkDefine).map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        MetaData metaData = this.regionRelation.getMetaDataByLink(link.getKey());
        if (this.entityTableWrapper == null) {
            this.entityTableWrapper = this.entityTableFactory.getEntityTable(this.regionRelation, this.currMasterKey, metaData);
        } else {
            this.entityTableFactory.reBuildEntityTable(this.regionRelation, this.currMasterKey, metaData, this.entityTableWrapper);
        }
        for (String keyOrCode : entityItems) {
            int directChildCount;
            IEntityRow entityRow = this.entityTableWrapper.findByEntityKey(keyOrCode);
            if (entityRow == null) {
                entityRow = this.entityTableWrapper.findByCode(keyOrCode);
            }
            if (!link.getAllowNotLeafNodeRefer() && entityRow != null && !fillLinkSet.contains(link.getKey()) && (directChildCount = this.entityTableWrapper.getDirectChildCount(entityRow.getEntityKeyData())) > 0) {
                String message = "\u4e0d\u5141\u8bb8\u9009\u62e9\u975e\u53f6\u5b50\u8282\u70b9\u3002\u9519\u8bef\u503c:" + dataStr;
                this.crudLogger.dataCheckFail(message);
                return this.errRes(dataStr, message);
            }
            if (entityRow != null) {
                chooseEntity.add(entityRow.getEntityKeyData());
            } else if (Boolean.TRUE.equals(link.getAllowUndefinedCode())) {
                chooseEntity.add(keyOrCode);
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

    public List<String> splitEnumCode(String data, boolean innerSplit) {
        ArrayList<String> result = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length(); ++i) {
            char currentChar = data.charAt(i);
            sb.append(currentChar);
            if (i < data.length() - 1) {
                char next = data.charAt(i + 1);
                if (currentChar == '|' && next == currentChar) continue;
            }
            if (i > 0) {
                char pre = data.charAt(i - 1);
                if (currentChar == '|' && pre == currentChar) continue;
            }
            if (currentChar != ';' && (!innerSplit || currentChar != '|')) continue;
            sb.setLength(sb.length() - 1);
            result.add(sb.toString());
            sb.setLength(0);
        }
        result.add(sb.toString());
        return result;
    }
}

