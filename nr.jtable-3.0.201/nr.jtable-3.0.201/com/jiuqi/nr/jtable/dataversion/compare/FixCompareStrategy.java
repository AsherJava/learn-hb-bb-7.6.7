/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.data.engine.bean.CompareDifferenceItem
 *  com.jiuqi.nr.data.engine.bean.FixedRegionCompareDifference
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.jtable.dataversion.compare;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.data.engine.bean.CompareDifferenceItem;
import com.jiuqi.nr.data.engine.bean.FixedRegionCompareDifference;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.dataversion.compare.AbstractCompareStrategy;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.CompareDiffenceUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FixCompareStrategy
extends AbstractCompareStrategy {
    public FixedRegionCompareDifference compareRegionVersionData(RegionData region, JtableContext jtableContext, UUID initialDataVersionId, UUID compareDataVersionId) {
        FixedRegionCompareDifference fixedRegionCompareDifference = new FixedRegionCompareDifference();
        fixedRegionCompareDifference.setRegionKey(region.getKey());
        fixedRegionCompareDifference.setRegionName(region.getTitle());
        ArrayList<CompareDifferenceItem> differenceItems = new ArrayList<CompareDifferenceItem>();
        fixedRegionCompareDifference.setUpdateItems(differenceItems);
        RegionDataSet initialRegionDataSet = this.getRegionDataSet(region, jtableContext, initialDataVersionId);
        List<Object> initialRegionData = null;
        if (initialRegionDataSet.getData().size() > 0) {
            initialRegionData = initialRegionDataSet.getData().get(0);
        }
        RegionDataSet compareRegionDataSet = this.getRegionDataSet(region, jtableContext, compareDataVersionId);
        List<Object> compareRegionData = null;
        if (compareRegionDataSet.getData().size() > 0) {
            compareRegionData = compareRegionDataSet.getData().get(0);
        }
        if (initialRegionData == null && compareRegionData == null) {
            return fixedRegionCompareDifference;
        }
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        List<String> cells = initialRegionDataSet.getCells().get(region.getKey().toString());
        for (int cellIndex = 0; cellIndex < cells.size(); ++cellIndex) {
            LinkData link;
            String cell = cells.get(cellIndex);
            String initialValue = "";
            if (initialRegionData != null && null != initialRegionData.get(cellIndex)) {
                Object object = initialRegionData.get(cellIndex);
                if (object instanceof BigDecimal) {
                    BigDecimal bigDecimal = (BigDecimal)object;
                    initialValue = bigDecimal.toPlainString();
                } else {
                    initialValue = object.toString();
                }
            }
            String compareValue = "";
            if (null != compareRegionData && compareRegionData.get(cellIndex) != null) {
                Object object = compareRegionData.get(cellIndex);
                if (object instanceof BigDecimal) {
                    BigDecimal bigDecimal = (BigDecimal)object;
                    compareValue = bigDecimal.toPlainString();
                } else {
                    compareValue = object.toString();
                }
            }
            if (initialValue.equals(compareValue) || (link = jtableParamService.getLink(cell)).getType() == LinkType.LINK_TYPE_PICTURE.getValue() || link.getType() == LinkType.LINK_TYPE_FILE.getValue()) continue;
            CompareDifferenceItem differenceItem = new CompareDifferenceItem();
            initialValue = CompareDiffenceUtil.translateString(link, initialValue);
            compareValue = CompareDiffenceUtil.translateString(link, compareValue);
            differenceItem.setInitialValue(initialValue);
            differenceItem.setCompareValue(compareValue);
            String difference = CompareDiffenceUtil.compareDifference(link.getType(), initialValue, compareValue);
            differenceItem.setDifference(difference);
            differenceItem.setDataLinkKey(link.getKey());
            if (StringUtils.isNotEmpty((String)link.getZbid())) {
                differenceItem.setFieldKey(link.getZbid());
                differenceItem.setFieldCode(link.getZbcode());
                differenceItem.setFieldTitle(link.getZbtitle());
            }
            differenceItems.add(differenceItem);
        }
        return fixedRegionCompareDifference;
    }
}

