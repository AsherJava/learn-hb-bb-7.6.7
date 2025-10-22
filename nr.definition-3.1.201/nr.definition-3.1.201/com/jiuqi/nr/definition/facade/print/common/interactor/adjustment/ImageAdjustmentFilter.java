/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.xg.process.IDrawObject
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.obj.ImageTemplateObject
 */
package com.jiuqi.nr.definition.facade.print.common.interactor.adjustment;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.facade.print.common.interactor.PaginateInteractorBase;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.AdjustmentFilter;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.AdjustmentResponse;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.FilterChain;
import com.jiuqi.xg.process.IDrawObject;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.obj.ImageTemplateObject;
import org.springframework.stereotype.Component;

@Component
public class ImageAdjustmentFilter
implements AdjustmentFilter {
    @Override
    public void doFilter(ITemplateObject templateObj, IDrawObject drawObj, int pageIndex, PaginateInteractorBase pdfIPaginateInteractor, AdjustmentResponse res, FilterChain filterChain) {
        if (templateObj.getKind().equals("image")) {
            ImageTemplateObject image = (ImageTemplateObject)templateObj;
            String property = image.getProperty("relatedZbCode");
            if (!StringUtils.isNotEmpty((String)property)) {
                res.setAdjustment(true);
                res.setMsg("ImageAdjustmentFilter==\u5ffd\u7565\u8c03\u6574");
            }
            return;
        }
        filterChain.doFilter(templateObj, drawObj, pageIndex, pdfIPaginateInteractor, res);
    }
}

