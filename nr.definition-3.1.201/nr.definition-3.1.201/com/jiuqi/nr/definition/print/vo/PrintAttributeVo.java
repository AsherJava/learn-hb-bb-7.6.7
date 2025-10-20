/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid.Font
 *  com.jiuqi.xg.process.Paper
 *  com.jiuqi.xlib.utils.StringUtil
 */
package com.jiuqi.nr.definition.print.vo;

import com.jiuqi.np.grid.Font;
import com.jiuqi.nr.definition.facade.print.NumericTextFormatDefine;
import com.jiuqi.nr.definition.facade.print.PrintPaperDefine;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.internal.impl.print.PrintSchemeAttributeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.print.WordLabelDefineImpl;
import com.jiuqi.nr.definition.print.common.PrintElementUtils;
import com.jiuqi.nr.definition.print.vo.PrintWordVo;
import com.jiuqi.nr.definition.print.vo.WatermarkConfigVo;
import com.jiuqi.xg.process.Paper;
import com.jiuqi.xlib.utils.StringUtil;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class PrintAttributeVo {
    private String printSchemeKey;
    private String paperName;
    private int direction;
    private List<PrintWordVo> wordLabels;
    private boolean useDefaultMargin;
    private double marginTop;
    private double marginLeft;
    private double marginRight;
    private double marginBottom;
    private boolean separator;
    private String nullResult;
    private String zeroResult;
    private String roundingZeroResult;
    private String decimal;
    private WatermarkConfigVo markConfig;

    public static PrintAttributeVo toPrintSchemeAttributeDefine(String printSchemeKey, PrintSchemeAttributeDefine define) {
        PrintAttributeVo vo = new PrintAttributeVo();
        vo.setPrintSchemeKey(printSchemeKey);
        PrintPaperDefine paper = define.getPaper();
        Paper paperByType = PrintElementUtils.getPaperByType(paper.getPaperType());
        vo.setPaperName(paperByType != null ? paperByType.getName() : "A4");
        vo.setDirection(paper.getDirection());
        vo.setMarginBottom(paper.getMarginBottom());
        vo.setMarginLeft(paper.getMarginLeft());
        vo.setMarginRight(paper.getMarginRight());
        vo.setMarginTop(paper.getMarginTop());
        ArrayList<PrintWordVo> wordLabels = new ArrayList<PrintWordVo>();
        List<WordLabelDefine> wordLabelDefines = define.getWordLabels();
        for (WordLabelDefine wordLabelDefine : wordLabelDefines) {
            wordLabels.add(PrintWordVo.toPrintWordVo(wordLabelDefine));
        }
        vo.setWordLabels(wordLabels);
        NumericTextFormatDefine format = define.getFormat();
        vo.setSeparator(StringUtil.isNotEmpty((String)format.getThousandsSeparator()));
        vo.setNullResult(format.getFormatNullResult());
        vo.setZeroResult(format.getFormatZeroResult());
        vo.setRoundingZeroResult(format.getFormatRoundingZeroResult());
        vo.setDecimal(format.getDecimal());
        if (null != define.getMarkConfig()) {
            vo.markConfig = new WatermarkConfigVo(define.getMarkConfig());
        }
        return vo;
    }

    public static PrintSchemeAttributeDefine toAttributeDefine(PrintAttributeVo vo) {
        List<PrintWordVo> printWordVos;
        PrintSchemeAttributeDefineImpl define = new PrintSchemeAttributeDefineImpl();
        PrintPaperDefine paper = define.getPaper();
        Paper paperByName = PrintElementUtils.getPaperByName(vo.getPaperName());
        paper.setPaperType(paperByName != null ? paperByName.getSize() : 0);
        paper.setDirection(vo.getDirection());
        paper.setMarginTop(vo.getMarginTop());
        paper.setMarginRight(vo.getMarginRight());
        paper.setMarginBottom(vo.getMarginBottom());
        paper.setMarginLeft(vo.getMarginLeft());
        List<WordLabelDefine> wordLabels = define.getWordLabels();
        if (wordLabels == null) {
            wordLabels = new ArrayList<WordLabelDefine>();
        }
        if (!wordLabels.isEmpty()) {
            wordLabels.clear();
        }
        if ((printWordVos = vo.getWordLabels()) != null && !printWordVos.isEmpty()) {
            for (PrintWordVo printWordVo : printWordVos) {
                wordLabels.add(PrintWordVo.toWordLabelDefine(printWordVo));
            }
        }
        NumericTextFormatDefine format = define.getFormat();
        if (vo.getSeparator()) {
            format.setThousandsSeparator(",");
        }
        format.setFormatNullResult(vo.getNullResult());
        format.setFormatRoundingZeroResult(vo.getRoundingZeroResult());
        format.setFormatZeroResult(vo.getZeroResult());
        format.setDecimal(vo.getDecimal());
        define.setMarkConfig(WatermarkConfigVo.toWatermarkConfig(vo.getMarkConfig()));
        return define;
    }

    public static PrintSchemeAttributeDefine defaultAttributeDefine() {
        PrintSchemeAttributeDefineImpl attribute = new PrintSchemeAttributeDefineImpl();
        PrintPaperDefine paper = attribute.getPaper();
        paper.setPaperType(0);
        paper.setDirection(0);
        List<WordLabelDefine> wordLabels = attribute.getWordLabels();
        WordLabelDefineImpl define = new WordLabelDefineImpl();
        define.setElement(0);
        define.setHorizontalPos(1);
        define.setVerticalPos(0);
        define.setText("{#RPTMAINTITLE}");
        define.setScope(0);
        Font font = new Font();
        font.setBold(true);
        font.setSize(20);
        define.setFont(font);
        wordLabels.add(define);
        define = new WordLabelDefineImpl();
        define.setElement(1);
        define.setHorizontalPos(0);
        define.setVerticalPos(0);
        define.setText("{[SYS_UNITTITLE]}");
        define.setScope(0);
        font = new Font();
        font.setBold(false);
        font.setSize(9);
        define.setFont(font);
        wordLabels.add(define);
        define = new WordLabelDefineImpl();
        define.setElement(1);
        define.setHorizontalPos(2);
        define.setVerticalPos(0);
        define.setText("{[CUR_PERIODSTR]}");
        define.setScope(0);
        define.setFont(font);
        wordLabels.add(define);
        return attribute;
    }

    public String getPrintSchemeKey() {
        return this.printSchemeKey;
    }

    public void setPrintSchemeKey(String printSchemeKey) {
        this.printSchemeKey = printSchemeKey;
    }

    public String getPaperName() {
        return this.paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public List<PrintWordVo> getWordLabels() {
        return this.wordLabels;
    }

    public void setWordLabels(List<PrintWordVo> wordLabels) {
        this.wordLabels = wordLabels;
    }

    public boolean isUseDefaultMargin() {
        return this.useDefaultMargin;
    }

    public void setUseDefaultMargin(boolean useDefaultMargin) {
        this.useDefaultMargin = useDefaultMargin;
    }

    public double getMarginTop() {
        return this.marginTop;
    }

    public void setMarginTop(double marginTop) {
        this.marginTop = marginTop;
    }

    public double getMarginLeft() {
        return this.marginLeft;
    }

    public void setMarginLeft(double marginLeft) {
        this.marginLeft = marginLeft;
    }

    public double getMarginRight() {
        return this.marginRight;
    }

    public void setMarginRight(double marginRight) {
        this.marginRight = marginRight;
    }

    public double getMarginBottom() {
        return this.marginBottom;
    }

    public void setMarginBottom(double marginBottom) {
        this.marginBottom = marginBottom;
    }

    public boolean getSeparator() {
        return this.separator;
    }

    public void setSeparator(boolean separator) {
        this.separator = separator;
    }

    public String getNullResult() {
        return this.nullResult;
    }

    public void setNullResult(String nullResult) {
        this.nullResult = nullResult;
    }

    public String getZeroResult() {
        return this.zeroResult;
    }

    public void setZeroResult(String zeroResult) {
        this.zeroResult = zeroResult;
    }

    public String getRoundingZeroResult() {
        return this.roundingZeroResult;
    }

    public void setRoundingZeroResult(String roundingZeroResult) {
        this.roundingZeroResult = roundingZeroResult;
    }

    public String getDecimal() {
        return this.decimal;
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }

    public WatermarkConfigVo getMarkConfig() {
        return this.markConfig;
    }

    public void setMarkConfig(WatermarkConfigVo markConfig) {
        this.markConfig = markConfig;
    }
}

