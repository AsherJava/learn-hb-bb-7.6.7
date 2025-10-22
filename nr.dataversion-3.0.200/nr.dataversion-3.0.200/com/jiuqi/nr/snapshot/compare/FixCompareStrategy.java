/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.io.dataset.impl.RegionDataSet
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 */
package com.jiuqi.nr.snapshot.compare;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.io.dataset.impl.RegionDataSet;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.snapshot.bean.CompareDifferenceItem;
import com.jiuqi.nr.snapshot.bean.FixedRegionCompareDifference;
import com.jiuqi.nr.snapshot.compare.AbstractCompareStrategy;
import com.jiuqi.nr.snapshot.compare.CompareDiffenceUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixCompareStrategy
extends AbstractCompareStrategy {
    private static final Logger logger = LoggerFactory.getLogger(FixCompareStrategy.class);
    private IRunTimeViewController runTimeViewController = null;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController = null;

    @Override
    public FixedRegionCompareDifference compareRegionVersionData(RegionData region, TableContext tableContext, String initialDimension, String compareDimension, String dimensionName) {
        FixedRegionCompareDifference fixedRegionCompareDifference = new FixedRegionCompareDifference();
        fixedRegionCompareDifference.setRegionKey(region.getKey());
        fixedRegionCompareDifference.setRegionName(region.getTitle());
        ArrayList<CompareDifferenceItem> differenceItems = new ArrayList<CompareDifferenceItem>();
        fixedRegionCompareDifference.setUpdateItems(differenceItems);
        RegionDataSet initialRegionDataSet = this.getRegionDataSet(region, tableContext, initialDimension, dimensionName);
        initialRegionDataSet.hasNext();
        ArrayList next = (ArrayList)initialRegionDataSet.next();
        ArrayList initialRegionData = null;
        if (initialRegionDataSet.getTotalCount() > 0) {
            initialRegionData = next;
        }
        RegionDataSet compareRegionDataSet = this.getRegionDataSet(region, tableContext, compareDimension, dimensionName);
        ArrayList compareRegionData = null;
        compareRegionDataSet.hasNext();
        ArrayList next2 = (ArrayList)compareRegionDataSet.next();
        if (compareRegionDataSet.getTotalCount() > 0) {
            compareRegionData = next2;
        }
        if (initialRegionData == null && compareRegionData == null) {
            return fixedRegionCompareDifference;
        }
        if (this.runTimeViewController == null) {
            this.runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        }
        String ownerTableKey = ((FieldDefine)initialRegionDataSet.getBizFieldDefList().get(0)).getOwnerTableKey();
        List allFields = null;
        try {
            allFields = this.dataDefinitionRuntimeController.getAllFieldsInTable(ownerTableKey);
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u6307\u5b9a\u5b58\u50a8\u8868\u7684\u6307\u6807\u5217\u8868\u5931\u8d25", e);
        }
        List listFieldDefine = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)this.runTimeViewController.getFieldKeysInRegion(region.getKey()));
        listFieldDefine.addAll(allFields);
        HashMap<String, FieldDefine> fields = new HashMap<String, FieldDefine>();
        for (FieldDefine fieldDefine : listFieldDefine) {
            fields.put(fieldDefine.getCode(), fieldDefine);
        }
        List fieldDataList = initialRegionDataSet.getFieldDataList();
        for (int cellIndex = 0; cellIndex < fieldDataList.size(); ++cellIndex) {
            String cell = ((ExportFieldDefine)fieldDataList.get(cellIndex)).getCode();
            FieldDefine fieldDefine = null;
            if (cell.contains(".")) {
                fieldDefine = (FieldDefine)fields.get(cell.split("\\.")[1]);
            }
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
            if (initialValue.equals(compareValue) || fieldDefine == null || fieldDefine.getType() == FieldType.FIELD_TYPE_PICTURE || fieldDefine.getType() == FieldType.FIELD_TYPE_FILE) continue;
            CompareDifferenceItem differenceItem = new CompareDifferenceItem();
            initialValue = CompareDiffenceUtil.translateString(fieldDefine, initialValue);
            compareValue = CompareDiffenceUtil.translateString(fieldDefine, compareValue);
            differenceItem.setInitialValue(initialValue);
            differenceItem.setCompareValue(compareValue);
            String difference = CompareDiffenceUtil.compareDifference(fieldDefine.getType().getValue(), initialValue, compareValue);
            differenceItem.setDifference(difference);
            List linksInRegionByField = this.runTimeViewController.getLinksInRegionByField(region.getKey(), fieldDefine.getKey());
            DataLinkDefine dataLinkDefine = null;
            if (linksInRegionByField != null && !linksInRegionByField.isEmpty()) {
                dataLinkDefine = (DataLinkDefine)linksInRegionByField.get(0);
            }
            if (dataLinkDefine != null) {
                differenceItem.setDataLinkKey(dataLinkDefine.getKey());
            }
            if (StringUtils.isNotEmpty((String)fieldDefine.getKey())) {
                differenceItem.setFieldKey(fieldDefine.getKey());
                differenceItem.setFieldCode(fieldDefine.getCode());
                differenceItem.setFieldTitle(fieldDefine.getTitle());
            }
            differenceItems.add(differenceItem);
        }
        return fixedRegionCompareDifference;
    }
}

