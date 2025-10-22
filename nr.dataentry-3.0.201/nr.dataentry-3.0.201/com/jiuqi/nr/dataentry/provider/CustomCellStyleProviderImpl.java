/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.util.ColorUtil
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.excel.extend.export.cellstyle.CustomCellStyleProvider
 *  com.jiuqi.nr.data.excel.obj.CustomGridCellStyle
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$BackgroundStyle
 */
package com.jiuqi.nr.dataentry.provider;

import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.util.ColorUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.excel.extend.export.cellstyle.CustomCellStyleProvider;
import com.jiuqi.nr.data.excel.obj.CustomGridCellStyle;
import com.jiuqi.nr.dataentry.internal.service.util.ExportUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nvwa.grid2.GridEnums;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CustomCellStyleProviderImpl
implements CustomCellStyleProvider {
    private JtableContext jtableContext;
    private Map<String, Map<String, CustomGridCellStyle>> formCalStylesCache = new HashMap<String, Map<String, CustomGridCellStyle>>();

    public CustomCellStyleProviderImpl(JtableContext jtableContext) {
        this.jtableContext = jtableContext;
    }

    public Map<String, CustomGridCellStyle> provideCustomCellStyles(DimensionCombination dimensionCombination, String formKey) {
        HashMap<String, CustomGridCellStyle> cellStyles = new HashMap<String, CustomGridCellStyle>();
        JtableContext expContext = new JtableContext(this.jtableContext);
        expContext.setFormKey(formKey);
        for (Map.Entry stringDimensionValueEntry : expContext.getDimensionSet().entrySet()) {
            DimensionValue dimensionValue = (DimensionValue)stringDimensionValueEntry.getValue();
            dimensionValue.setValue(dimensionCombination.getValue(dimensionValue.getName()).toString());
        }
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        if (this.formCalStylesCache.containsKey(formKey)) {
            Map<String, CustomGridCellStyle> cacheCellStyles = this.formCalStylesCache.get(formKey);
            for (Map.Entry entry : cacheCellStyles.entrySet()) {
                cellStyles.put((String)entry.getKey(), (CustomGridCellStyle)entry.getValue());
            }
        } else {
            List calcDataLinks = jtableParamService.getCalcDataLinks(expContext);
            HashSet calLinks = new HashSet(calcDataLinks);
            for (Object calLink : calLinks) {
                CustomGridCellStyle customGridCellStyle = new CustomGridCellStyle();
                customGridCellStyle.setBackGroundStyle(GridEnums.getIntValue((Enum)GridEnums.BackgroundStyle.FILL));
                customGridCellStyle.setBackGroundColor(ColorUtil.htmlColorToInt((String)ExportUtil.getCalCellColor()));
                cellStyles.put((String)calLink, customGridCellStyle);
            }
            HashMap hashMap = new HashMap();
            for (Map.Entry entry : cellStyles.entrySet()) {
                hashMap.put(entry.getKey(), entry.getValue());
            }
            this.formCalStylesCache.put(formKey, hashMap);
        }
        List extractDataLinks = jtableParamService.getExtractDataLinkList(expContext);
        HashSet extractDataLinkSet = new HashSet(extractDataLinks);
        for (String extractDataLink : extractDataLinkSet) {
            int n = ColorUtil.htmlColorToInt((String)ExportUtil.getEFDCCellColor());
            if (cellStyles.containsKey(extractDataLink)) {
                n = ColorUtil.mergeColor((int)ColorUtil.htmlColorToInt((String)ExportUtil.getCalCellColor()), (int)ColorUtil.htmlColorToInt((String)ExportUtil.getEFDCCellColor()));
            }
            CustomGridCellStyle style = new CustomGridCellStyle();
            style.setBackGroundStyle(GridEnums.getIntValue((Enum)GridEnums.BackgroundStyle.FILL));
            style.setBackGroundColor(n);
            cellStyles.put(extractDataLink, style);
        }
        return cellStyles;
    }
}

