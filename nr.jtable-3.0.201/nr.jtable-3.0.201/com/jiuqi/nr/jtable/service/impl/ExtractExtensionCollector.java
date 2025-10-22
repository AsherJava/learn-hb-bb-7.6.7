/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.ExtractCellInfo;
import com.jiuqi.nr.jtable.service.IExtractExtensionCollector;
import com.jiuqi.nr.jtable.service.IExtractExtensions;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExtractExtensionCollector
implements IExtractExtensionCollector {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final List<IExtractExtensions> extractExtensions;

    public ExtractExtensionCollector(List<IExtractExtensions> extractExtensions) {
        this.extractExtensions = extractExtensions;
    }

    @Override
    public List<ExtractCellInfo> getExtractDataLinkList(JtableContext jtableContext) {
        ArrayList<ExtractCellInfo> extractCellInfoList = new ArrayList<ExtractCellInfo>();
        try {
            for (IExtractExtensions extractExtension : this.extractExtensions) {
                if (!extractExtension.getEnable(jtableContext)) continue;
                FormulaSchemeDefine formulaSchemeDefine = extractExtension.getSoluctionByDimensions(jtableContext);
                extractCellInfoList.addAll(extractExtension.getExtractDataLinkList(jtableContext, formulaSchemeDefine.getKey()));
            }
        }
        catch (Exception e) {
            this.logger.error("\u8d22\u52a1\u63d0\u53d6\u6269\u5c55\u4fe1\u606f\u67e5\u8be2\u5931\u8d25", e);
        }
        return extractCellInfoList;
    }
}

