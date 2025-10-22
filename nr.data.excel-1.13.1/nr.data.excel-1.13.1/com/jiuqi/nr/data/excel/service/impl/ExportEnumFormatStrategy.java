/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.impl.RegionRelationFactory
 *  com.jiuqi.nr.datacrud.impl.format.strategy.EnumTypeStrategy
 *  com.jiuqi.nr.datacrud.spi.IEntityTableFactory
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.util.LevelSetting
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.format.strategy.EnumTypeStrategy;
import com.jiuqi.nr.datacrud.spi.IEntityTableFactory;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.util.LevelSetting;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

public class ExportEnumFormatStrategy
extends EnumTypeStrategy {
    private final IRunTimeViewController runTimeViewController;
    private IRowData rowData;
    private final Map<String, Set<String>> dataRegionHide0GatherFields = new HashMap<String, Set<String>>();

    public ExportEnumFormatStrategy(IEntityMetaService entityMetaService, IEntityTableFactory entityTableFactory, RegionRelationFactory regionRelationFactory, IRunTimeViewController runTimeViewController) {
        super(entityMetaService, entityTableFactory, regionRelationFactory);
        this.runTimeViewController = runTimeViewController;
    }

    public void setRowData(IRowData rowData) {
        this.rowData = rowData;
    }

    public String format(DataLinkDefine link, DataField field, AbstractData abstractData) {
        if (this.rowData != null && this.rowData.getGroupTreeDeep() >= 0 && StringUtils.hasText(field.getRefDataEntityKey()) && abstractData != null && !abstractData.getAsNull() && this.hide0(link.getRegionKey(), field)) {
            DataRegionDefine dataRegionDefine = this.runTimeViewController.queryDataRegionDefine(link.getRegionKey());
            LevelSetting levelSetting = dataRegionDefine.getLevelSetting();
            if (levelSetting != null && levelSetting.getType() == 1) {
                List gradeLength = Arrays.stream(levelSetting.getCode().split(";")).collect(Collectors.toList());
                if (this.rowData.getGroupTreeDeep() >= gradeLength.size()) {
                    return this.removeEnd0(abstractData.getAsString());
                }
                Collections.reverse(gradeLength);
                int enumCodeLength = Integer.parseInt((String)gradeLength.get(this.rowData.getGroupTreeDeep()));
                return this.removeEnd0(abstractData.getAsString(), enumCodeLength);
            }
            return this.removeEnd0(abstractData.getAsString());
        }
        return super.format(link, field, abstractData);
    }

    private boolean hide0(String dataRegionKey, DataField field) {
        return this.getHide0Fields(dataRegionKey).contains(field.getKey());
    }

    private Set<String> getHide0Fields(String dataRegionKey) {
        String hideZeroGatherFields;
        if (this.dataRegionHide0GatherFields.containsKey(dataRegionKey)) {
            return this.dataRegionHide0GatherFields.get(dataRegionKey);
        }
        Set<String> hide0Fields = Collections.emptySet();
        DataRegionDefine dataRegionDefine = this.runTimeViewController.queryDataRegionDefine(dataRegionKey);
        if (dataRegionDefine != null && StringUtils.hasText(hideZeroGatherFields = dataRegionDefine.getHideZeroGatherFields())) {
            hide0Fields = Arrays.stream(hideZeroGatherFields.split(";")).collect(Collectors.toSet());
        }
        this.dataRegionHide0GatherFields.put(dataRegionKey, hide0Fields);
        return hide0Fields;
    }

    private String removeEnd0(String str) {
        return this.removeEnd0(str, -1);
    }

    private String removeEnd0(String str, int enumCodeLength) {
        boolean careLength;
        if (str == null) {
            return null;
        }
        if (enumCodeLength >= str.length()) {
            return str;
        }
        String shortestStr = "";
        boolean bl = careLength = enumCodeLength > 0;
        if (careLength) {
            shortestStr = str.substring(0, enumCodeLength);
        }
        StringBuilder tempStr = new StringBuilder();
        char[] chars = str.toCharArray();
        boolean isLast = true;
        int lastCheckIndex = careLength ? enumCodeLength : 0;
        for (int i = chars.length - 1; i >= lastCheckIndex; --i) {
            if (isLast) {
                if (chars[i] != '0') {
                    tempStr.append(chars[i]);
                }
                if (tempStr.length() <= 0) continue;
                isLast = false;
                continue;
            }
            tempStr.append(chars[i]);
        }
        return shortestStr + tempStr.reverse();
    }
}

