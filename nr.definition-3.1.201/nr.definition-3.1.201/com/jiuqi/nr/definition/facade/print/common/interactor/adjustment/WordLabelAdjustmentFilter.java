/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.xg.draw2d.Font
 *  com.jiuqi.xg.draw2d.FontMetrics
 *  com.jiuqi.xg.draw2d.XG
 *  com.jiuqi.xg.process.IDrawObject
 *  com.jiuqi.xg.process.IGraphicalElement
 *  com.jiuqi.xg.process.IGraphicalText
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.obj.PageDrawObject
 *  com.jiuqi.xg.process.table.obj.TableDrawObject
 *  com.jiuqi.xg.process.table.util.TableUtil
 *  com.jiuqi.xg.process.util.TextureUtil
 */
package com.jiuqi.nr.definition.facade.print.common.interactor.adjustment;

import com.jiuqi.grid.GridData;
import com.jiuqi.nr.definition.facade.print.common.define.element.WordLabelDrawObject;
import com.jiuqi.nr.definition.facade.print.common.interactor.PaginateInteractorBase;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.AdjustmentFilter;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.AdjustmentResponse;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.FilterChain;
import com.jiuqi.nr.definition.facade.print.common.other.PrintUtil;
import com.jiuqi.nr.definition.facade.print.core.WordLabelTemplateObject;
import com.jiuqi.xg.draw2d.Font;
import com.jiuqi.xg.draw2d.FontMetrics;
import com.jiuqi.xg.draw2d.XG;
import com.jiuqi.xg.process.IDrawObject;
import com.jiuqi.xg.process.IGraphicalElement;
import com.jiuqi.xg.process.IGraphicalText;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.obj.PageDrawObject;
import com.jiuqi.xg.process.table.obj.TableDrawObject;
import com.jiuqi.xg.process.table.util.TableUtil;
import com.jiuqi.xg.process.util.TextureUtil;
import org.springframework.stereotype.Component;

@Component
public class WordLabelAdjustmentFilter
implements AdjustmentFilter {
    @Override
    public void doFilter(ITemplateObject templateObj, IDrawObject drawObj, int pageIndex, PaginateInteractorBase paginateInteractor, AdjustmentResponse res, FilterChain filterChain) {
        if (templateObj.getKind().equals("element_wordLabel")) {
            WordLabelDrawObject labelDrawObject;
            PageDrawObject drawPage;
            int pageCount;
            if (templateObj.getDrawScope() == 7 && !PrintUtil.isLastPage(pageIndex, pageCount = (drawPage = (PageDrawObject)(labelDrawObject = (WordLabelDrawObject)drawObj).getParent()).getParent().getPageCount())) {
                res.setAdjustment(false);
                res.setMsg("WordLabelAdjustmentFilter==\u6ca1\u6709\u8c03\u6574");
                return;
            }
            labelDrawObject = (WordLabelDrawObject)drawObj;
            if (paginateInteractor.getReplaceMap() != null) {
                String content = labelDrawObject.getContent();
                if (paginateInteractor.getReplaceMap().containsKey(content)) {
                    labelDrawObject.setContent(paginateInteractor.getReplaceMap().get(content));
                    res.setAdjustment(true);
                    res.setMsg("WordLabelAdjustmentFilter==\u8c03\u6574");
                    return;
                }
            }
            WordLabelTemplateObject labelObject = (WordLabelTemplateObject)templateObj;
            PageDrawObject drawPage2 = (PageDrawObject)labelDrawObject.getParent();
            int pageCount2 = drawPage2.getParent().getPageCount();
            String unParsedExp = labelDrawObject.getContent();
            if (labelDrawObject.containsPageNum()) {
                if (paginateInteractor.getParam().isPrintPageNum()) {
                    PrintUtil.addExtraProperty(labelDrawObject, pageIndex, unParsedExp, paginateInteractor.getPageNumberGenerateStrategy());
                } else {
                    labelDrawObject.setContent("");
                    res.setAdjustment(true);
                    res.setMsg("WordLabelAdjustmentFilter==\u8c03\u6574");
                    return;
                }
            }
            String processedContent = PrintUtil.executeparse(pageIndex, labelObject, labelDrawObject, unParsedExp, paginateInteractor);
            if (labelObject.isAutoWrap()) {
                Font font = labelObject.getFont().clone();
                font.setSize(XG.DEFAULT_LENGTH_UNIT.fromPoint(font.getSize()));
                processedContent = TextureUtil.autoWrap((String)processedContent, (double)TextureUtil.getAvailableWidth((IGraphicalText)labelObject), (FontMetrics)FontMetrics.getMetrics((Font)font));
            }
            if (labelObject.isSplitPrint()) {
                if (PrintUtil.isOddPage(pageIndex)) {
                    if (PrintUtil.isLastPage(pageIndex, pageCount2)) {
                        labelDrawObject.setContent(processedContent);
                        res.setAdjustment(true);
                        res.setMsg("WordLabelAdjustmentFilter==\u8c03\u6574");
                    } else {
                        PrintUtil.drawLeftLabel(labelDrawObject, processedContent, pageIndex);
                        res.setAdjustment(false);
                        res.setMsg("WordLabelAdjustmentFilter==\u6ca1\u6709\u8c03\u6574");
                    }
                } else if (PrintUtil.isEvenPage(pageIndex)) {
                    PrintUtil.drawRightLabel(labelDrawObject, processedContent, pageIndex);
                    res.setAdjustment(false);
                    res.setMsg("WordLabelAdjustmentFilter==\u6ca1\u6709\u8c03\u6574");
                }
            } else if (labelObject.isEvenAndLastPagePrint()) {
                if (PrintUtil.isEvenPage(pageIndex) || PrintUtil.isOddPage(pageIndex) && PrintUtil.isLastPage(pageIndex, pageCount2)) {
                    labelDrawObject.setContent(processedContent);
                    res.setAdjustment(true);
                    res.setMsg("WordLabelAdjustmentFilter==\u8c03\u6574");
                } else {
                    res.setAdjustment(false);
                    res.setMsg("WordLabelAdjustmentFilter==\u6ca1\u6709\u8c03\u6574");
                }
            } else if (labelObject.isUnderTable()) {
                IGraphicalElement[] elements;
                PageDrawObject pdo = (PageDrawObject)labelDrawObject.getParent();
                for (IGraphicalElement ele : elements = pdo.getGraphicalElements()) {
                    if (!ele.getKind().equals("table")) continue;
                    TableDrawObject rdo = (TableDrawObject)ele;
                    GridData gridData = rdo.getGridData();
                    double height = TableUtil.getGridRangeHeight((GridData)gridData, (int)0, (int)(gridData.getRowCount() - 1));
                    double y = height + rdo.getY();
                    labelDrawObject.setY(y);
                    labelDrawObject.setContent(processedContent);
                }
            } else {
                labelDrawObject.setContent(processedContent);
                res.setAdjustment(true);
                res.setMsg("WordLabelAdjustmentFilter==\u8c03\u6574");
            }
            return;
        }
        filterChain.doFilter(templateObj, drawObj, pageIndex, paginateInteractor, res);
    }
}

