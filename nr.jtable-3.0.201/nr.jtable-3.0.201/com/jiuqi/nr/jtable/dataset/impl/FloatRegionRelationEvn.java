/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.FieldValueType
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import com.jiuqi.nr.jtable.exception.NotFoundFieldException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.TableData;
import com.jiuqi.nr.jtable.util.RegionSettingUtil;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FloatRegionRelationEvn
extends AbstractRegionRelationEvn {
    private static final Logger logger = LoggerFactory.getLogger(FloatRegionRelationEvn.class);

    public FloatRegionRelationEvn(RegionData regionDefine, JtableContext jtableContext) {
        super(regionDefine, jtableContext);
    }

    @Override
    protected void initOrderFieldData() {
        List<LinkData> allLinks = this.jtableParamService.getLinks(this.regionData.getKey());
        List<TableData> allTables = this.jtableParamService.getAllTableInRegion(this.regionData.getKey());
        String formSchemeKey = this.getDataFormaterCache().getJtableContext().getFormSchemeKey();
        List<EntityViewData> entityList = this.jtableParamService.getEntityList(formSchemeKey);
        ArrayList<String> dimNames = new ArrayList<String>();
        for (EntityViewData entity : entityList) {
            dimNames.add(entity.getDimensionName());
        }
        if (allTables == null || allTables.isEmpty()) {
            return;
        }
        for (TableData table : allTables) {
            if (StringUtils.isEmpty((String)this.gatherType)) {
                this.gatherType = table.getGatherType();
            }
            this.tableKeys.add(table.getTableKey());
            ArrayList<FieldData> bizKeyOrderFieldList = new ArrayList<FieldData>();
            this.bizKeyOrderFields.add(bizKeyOrderFieldList);
            List<String> bizKeyFieldsID = table.getBizKeyFields();
            FieldData bizKeyOrderField = null;
            for (String fieldKey : bizKeyFieldsID) {
                FieldData fieldDefine = null;
                try {
                    fieldDefine = this.jtableParamService.getField(fieldKey);
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                if (fieldDefine == null) {
                    throw new NotFoundFieldException(JtableExceptionCodeCost.NOTFOUND_FIELD, new String[]{"\u672a\u627e\u5230" + table.getTableName() + "\u7684" + fieldKey + "\u6307\u6807"});
                }
                String fieldDimensionName = this.jtableDataEngineService.getDimensionName(fieldDefine);
                if (dimNames.contains(fieldDimensionName) || "ADJUST".equals(fieldDimensionName)) continue;
                if (this.regionData.getAllowDuplicateKey() && fieldDefine.getFieldValueType() == FieldValueType.FIELD_VALUE_BIZKEY_ORDER.getValue()) {
                    bizKeyOrderField = fieldDefine;
                    continue;
                }
                boolean findDimField = false;
                for (LinkData link : allLinks) {
                    if (!fieldKey.equals(link.getZbid())) continue;
                    bizKeyOrderFieldList.add(fieldDefine);
                    this.bizKeyLinks.add(link.getKey().toString());
                    findDimField = true;
                    break;
                }
                if (findDimField || !StringUtils.isEmpty((String)fieldDefine.getDefaultValue()) || RegionSettingUtil.checkRegionSettingContainDefaultVal(this.regionData, fieldDefine)) continue;
                throw new NotFoundFieldException(JtableExceptionCodeCost.NOTFOUND_FIELD, new String[]{table.getTableName() + "\u7684" + fieldDefine.getFieldCode() + "\u4e3b\u952e\u6307\u6807\u672a\u8bbe\u7f6e\u5728\u533a\u57df\u4e2d"});
            }
            if (bizKeyOrderField != null) {
                bizKeyOrderFieldList.add(bizKeyOrderField);
            }
            FieldData floatOrderField = null;
            try {
                floatOrderField = this.jtableParamService.getFieldByCodeInTable("FLOATORDER", table.getTableKey());
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            if (floatOrderField == null) {
                throw new NotFoundFieldException(JtableExceptionCodeCost.NOTFOUND_FIELD, new String[]{"\u672a\u627e\u5230" + table.getTableName() + "\u7684FLOATORDER\u6307\u6807"});
            }
            this.floatOrderFields.add(floatOrderField);
        }
    }
}

