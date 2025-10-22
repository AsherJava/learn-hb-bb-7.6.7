/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.jqt.BlocksManager;
import com.jiuqi.nr.single.core.para.JQTHeader;
import com.jiuqi.nr.single.core.para.util.ReadUtil;

public class JQTFileMap {
    private int gridLayoutBlock;
    private int hGridLayoutBLock;
    private int strMatrixBlock;
    private int hStrMatrixBlock;
    private int attrMatrixBlock;
    private int hAttrMatrixBlock;
    private int formulasBlock;
    private int hFormulasBlock;
    private int checkFormulasBlock;
    private int hCheckFormulasBlock;
    private int fieldDefsBlock;
    private int hFieldDefsBlock;
    private int[] reserved;
    private int pageDataBlock;
    private int hPageDataBlock;
    private int splitInfoBlock;
    private int hSplitInfoBlock;
    private int printPagesBlock;
    private int hPrintPagesBlock;
    private int dftTextsBlock;
    private int hDftTextsBlock;
    private int graphItemsBlock;
    private int hGraphItemsBlock;
    private int[] reserved0;
    private int fileAttrBlock;
    private int hFileAttrBlock;
    private int appearanceBlock;
    private int hAppearanceBlock;
    private int specialCodeBlock;
    private int hSpecialCodeBlock;
    private int[] reserved1;
    private String code;
    private JQTHeader header;
    private BlocksManager blocks;

    public int Size() {
        return 160;
    }

    public void init(Stream mask0) throws StreamException {
        this.setGridLayoutBlock(ReadUtil.readIntValue(mask0));
        this.sethGridLayoutBLock(ReadUtil.readIntValue(mask0));
        this.setStrMatrixBlock(ReadUtil.readIntValue(mask0));
        this.sethStrMatrixBlock(ReadUtil.readIntValue(mask0));
        this.setAttrMatrixBlock(ReadUtil.readIntValue(mask0));
        this.sethAttrMatrixBlock(ReadUtil.readIntValue(mask0));
        this.setFormulasBlock(ReadUtil.readIntValue(mask0));
        this.sethFormulasBlock(ReadUtil.readIntValue(mask0));
        this.setCheckFormulasBlock(ReadUtil.readIntValue(mask0));
        this.sethCheckFormulasBlock(ReadUtil.readIntValue(mask0));
        this.setFieldDefsBlock(ReadUtil.readIntValue(mask0));
        this.sethFieldDefsBlock(ReadUtil.readIntValue(mask0));
        this.setReserved(ReadUtil.readArrayValue(mask0, 4));
        this.setPageDataBlock(ReadUtil.readIntValue(mask0));
        this.sethPageDataBlock(ReadUtil.readIntValue(mask0));
        this.setSplitInfoBlock(ReadUtil.readIntValue(mask0));
        this.sethSplitInfoBlock(ReadUtil.readIntValue(mask0));
        this.setPrintPagesBlock(ReadUtil.readIntValue(mask0));
        this.sethPrintPagesBlock(ReadUtil.readIntValue(mask0));
        this.setDftTextsBlock(ReadUtil.readIntValue(mask0));
        this.sethDftTextsBlock(ReadUtil.readIntValue(mask0));
        this.setGraphItemsBlock(ReadUtil.readIntValue(mask0));
        this.sethGraphItemsBlock(ReadUtil.readIntValue(mask0));
        this.setReserved0(ReadUtil.readArrayValue(mask0, 6));
        this.setFileAttrBlock(ReadUtil.readIntValue(mask0));
        this.sethFileAttrBlock(ReadUtil.readIntValue(mask0));
        this.setAppearanceBlock(ReadUtil.readIntValue(mask0));
        this.sethAppearanceBlock(ReadUtil.readIntValue(mask0));
        this.setSpecialCodeBlock(ReadUtil.readIntValue(mask0));
        this.sethSpecialCodeBlock(ReadUtil.readIntValue(mask0));
        this.setReserved1(ReadUtil.readArrayValue(mask0, 2));
    }

    public void save(Stream mask0) throws StreamException {
        mask0.writeInt(this.gridLayoutBlock);
        mask0.writeInt(this.hGridLayoutBLock);
        mask0.writeInt(this.strMatrixBlock);
        mask0.writeInt(this.hStrMatrixBlock);
        mask0.writeInt(this.attrMatrixBlock);
        mask0.writeInt(this.hAttrMatrixBlock);
        mask0.writeInt(this.formulasBlock);
        mask0.writeInt(this.hFormulasBlock);
        mask0.writeInt(this.checkFormulasBlock);
        mask0.writeInt(this.hCheckFormulasBlock);
        mask0.writeInt(this.fieldDefsBlock);
        mask0.writeInt(this.hFieldDefsBlock);
        ReadUtil.writeArrayValue(mask0, this.reserved);
        mask0.writeInt(this.pageDataBlock);
        mask0.writeInt(this.hPageDataBlock);
        mask0.writeInt(this.splitInfoBlock);
        mask0.writeInt(this.hSplitInfoBlock);
        mask0.writeInt(this.printPagesBlock);
        mask0.writeInt(this.hPrintPagesBlock);
        mask0.writeInt(this.dftTextsBlock);
        mask0.writeInt(this.hDftTextsBlock);
        mask0.writeInt(this.graphItemsBlock);
        mask0.writeInt(this.hGraphItemsBlock);
        ReadUtil.writeArrayValue(mask0, this.reserved0);
        mask0.writeInt(this.fileAttrBlock);
        mask0.writeInt(this.hFileAttrBlock);
        mask0.writeInt(this.appearanceBlock);
        mask0.writeInt(this.hAppearanceBlock);
        mask0.writeInt(this.specialCodeBlock);
        mask0.writeInt(this.hSpecialCodeBlock);
        ReadUtil.writeArrayValue(mask0, this.reserved1);
    }

    public final int getGridLayoutBlock() {
        return this.gridLayoutBlock;
    }

    public final void setGridLayoutBlock(int gridLayoutBlock_0) {
        this.gridLayoutBlock = gridLayoutBlock_0;
    }

    public final long gethGridLayoutBLock() {
        return this.hGridLayoutBLock;
    }

    public final void sethGridLayoutBLock(int hGridLayoutBLock_0) {
        this.hGridLayoutBLock = hGridLayoutBLock_0;
    }

    public final int getStrMatrixBlock() {
        return this.strMatrixBlock;
    }

    public final void setStrMatrixBlock(int strMatrixBlock_0) {
        this.strMatrixBlock = strMatrixBlock_0;
    }

    public final long gethStrMatrixBlock() {
        return this.hStrMatrixBlock;
    }

    public final void sethStrMatrixBlock(int hStrMatrixBlock_0) {
        this.hStrMatrixBlock = hStrMatrixBlock_0;
    }

    public final int getAttrMatrixBlock() {
        return this.attrMatrixBlock;
    }

    public final void setAttrMatrixBlock(int attrMatrixBlock_0) {
        this.attrMatrixBlock = attrMatrixBlock_0;
    }

    public final long gethAttrMatrixBlock() {
        return this.hAttrMatrixBlock;
    }

    public final void sethAttrMatrixBlock(int hAttrMatrixBlock_0) {
        this.hAttrMatrixBlock = hAttrMatrixBlock_0;
    }

    public final int getFormulasBlock() {
        return this.formulasBlock;
    }

    public final void setFormulasBlock(int formulasBlock_0) {
        this.formulasBlock = formulasBlock_0;
    }

    public final long gethFormulasBlock() {
        return this.hFormulasBlock;
    }

    public final void sethFormulasBlock(int hFormulasBlock_0) {
        this.hFormulasBlock = hFormulasBlock_0;
    }

    public final int getCheckFormulasBlock() {
        return this.checkFormulasBlock;
    }

    public final void setCheckFormulasBlock(int checkFormulasBlock_0) {
        this.checkFormulasBlock = checkFormulasBlock_0;
    }

    public final long gethCheckFormulasBlock() {
        return this.hCheckFormulasBlock;
    }

    public final void sethCheckFormulasBlock(int hCheckFormulasBlock_0) {
        this.hCheckFormulasBlock = hCheckFormulasBlock_0;
    }

    public final int getFieldDefsBlock() {
        return this.fieldDefsBlock;
    }

    public final void setFieldDefsBlock(int fieldDefsBlock_0) {
        this.fieldDefsBlock = fieldDefsBlock_0;
    }

    public final long gethFieldDefsBlock() {
        return this.hFieldDefsBlock;
    }

    public final void sethFieldDefsBlock(int hFieldDefsBlock_0) {
        this.hFieldDefsBlock = hFieldDefsBlock_0;
    }

    public final int[] getReserved() {
        return this.reserved;
    }

    public final void setReserved(int[] reserved_0) {
        this.reserved = reserved_0;
    }

    public final int getPageDataBlock() {
        return this.pageDataBlock;
    }

    public final String getCode() {
        return this.code;
    }

    public final void setCode(String code_0) {
        this.code = code_0;
    }

    public final void setPageDataBlock(int pageDataBlock_0) {
        this.pageDataBlock = pageDataBlock_0;
    }

    public final long gethPageDataBlock() {
        return this.hPageDataBlock;
    }

    public final void sethPageDataBlock(int hPageDataBlock_0) {
        this.hPageDataBlock = hPageDataBlock_0;
    }

    public final int getSplitInfoBlock() {
        return this.splitInfoBlock;
    }

    public final void setSplitInfoBlock(int splitInfoBlock_0) {
        this.splitInfoBlock = splitInfoBlock_0;
    }

    public final long gethSplitInfoBlock() {
        return this.hSplitInfoBlock;
    }

    public final void sethSplitInfoBlock(int hSplitInfoBlock_0) {
        this.hSplitInfoBlock = hSplitInfoBlock_0;
    }

    public final int getPrintPagesBlock() {
        return this.printPagesBlock;
    }

    public final void setPrintPagesBlock(int printPagesBlock_0) {
        this.printPagesBlock = printPagesBlock_0;
    }

    public final long gethPrintPagesBlock() {
        return this.hPrintPagesBlock;
    }

    public final void sethPrintPagesBlock(int hPrintPagesBlock_0) {
        this.hPrintPagesBlock = hPrintPagesBlock_0;
    }

    public final int getDftTextsBlock() {
        return this.dftTextsBlock;
    }

    public final void setDftTextsBlock(int dftTextsBlock_0) {
        this.dftTextsBlock = dftTextsBlock_0;
    }

    public final long gethDftTextsBlock() {
        return this.hDftTextsBlock;
    }

    public final void sethDftTextsBlock(int hDftTextsBlock_0) {
        this.hDftTextsBlock = hDftTextsBlock_0;
    }

    public final int getGraphItemsBlock() {
        return this.graphItemsBlock;
    }

    public final void setGraphItemsBlock(int graphItemsBlock_0) {
        this.graphItemsBlock = graphItemsBlock_0;
    }

    public final long gethGraphItemsBlock() {
        return this.hGraphItemsBlock;
    }

    public final void sethGraphItemsBlock(int hGraphItemsBlock_0) {
        this.hGraphItemsBlock = hGraphItemsBlock_0;
    }

    public final int[] getReserved0() {
        return this.reserved0;
    }

    public final void setReserved0(int[] reserved0_0) {
        this.reserved0 = reserved0_0;
    }

    public final int getFileAttrBlock() {
        return this.fileAttrBlock;
    }

    public final void setFileAttrBlock(int fileAttrBlock_0) {
        this.fileAttrBlock = fileAttrBlock_0;
    }

    public final long gethFileAttrBlock() {
        return this.hFileAttrBlock;
    }

    public final void sethFileAttrBlock(int hFileAttrBlock_0) {
        this.hFileAttrBlock = hFileAttrBlock_0;
    }

    public final int getAppearanceBlock() {
        return this.appearanceBlock;
    }

    public final void setAppearanceBlock(int appearanceBlock_0) {
        this.appearanceBlock = appearanceBlock_0;
    }

    public final int gethAppearanceBlock() {
        return this.hAppearanceBlock;
    }

    public final void sethAppearanceBlock(int hAppearanceBlock_0) {
        this.hAppearanceBlock = hAppearanceBlock_0;
    }

    public final int getSpecialCodeBlock() {
        return this.specialCodeBlock;
    }

    public final void setSpecialCodeBlock(int specialCodeBlock_0) {
        this.specialCodeBlock = specialCodeBlock_0;
    }

    public final long gethSpecialCodeBlock() {
        return this.hSpecialCodeBlock;
    }

    public final void sethSpecialCodeBlock(int hSpecialCodeBlock_0) {
        this.hSpecialCodeBlock = hSpecialCodeBlock_0;
    }

    public final int[] getReserved1() {
        return this.reserved1;
    }

    public final void setReserved1(int[] reserved1_0) {
        this.reserved1 = reserved1_0;
    }

    public JQTHeader getHeader() {
        return this.header;
    }

    public void setHeader(JQTHeader header) {
        this.header = header;
    }

    public BlocksManager getBlocks() {
        return this.blocks;
    }

    public void setBlocks(BlocksManager blocks) {
        this.blocks = blocks;
    }
}

