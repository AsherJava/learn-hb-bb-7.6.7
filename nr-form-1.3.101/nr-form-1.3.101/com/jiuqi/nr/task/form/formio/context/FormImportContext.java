/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 */
package com.jiuqi.nr.task.form.formio.context;

import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.task.form.formio.dto.ImportDropDownDTO;
import com.jiuqi.nr.task.form.formio.dto.ImportFormulaDTO;
import com.jiuqi.nr.task.form.formio.dto.ImportLinkDTO;
import com.jiuqi.nr.task.form.formio.service.impl.ExcelReverseModelDataProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;

public class FormImportContext {
    private Sheet currentSheet;
    private DesignDataScheme dataScheme;
    private DesignFormSchemeDefine formSchemeDefine;
    private DesignFormGroupDefine groupDefine;
    private DesignFormDefine currentForm;
    private DesignDataRegionDefine dataRegion;
    private ExcelReverseModelDataProvider reverseModelDataProvider;
    private List<ImportLinkDTO> linkCells;
    private List<ImportDropDownDTO> DropDownCells;
    private List<ImportFormulaDTO> formulaCells;

    public FormImportContext() {
        this.init();
    }

    public FormImportContext(Sheet currentSheet) {
        this.currentSheet = currentSheet;
        this.init();
    }

    private void init() {
        this.DropDownCells = new ArrayList<ImportDropDownDTO>();
        this.linkCells = new ArrayList<ImportLinkDTO>();
        this.formulaCells = new ArrayList<ImportFormulaDTO>();
    }

    public DesignDataScheme getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(DesignDataScheme dataScheme) {
        this.dataScheme = dataScheme;
    }

    public DesignFormSchemeDefine getFormSchemeDefine() {
        return this.formSchemeDefine;
    }

    public void setFormSchemeDefine(DesignFormSchemeDefine formSchemeDefine) {
        this.formSchemeDefine = formSchemeDefine;
    }

    public DesignFormGroupDefine getGroupDefine() {
        return this.groupDefine;
    }

    public void setGroupDefine(DesignFormGroupDefine groupDefine) {
        this.groupDefine = groupDefine;
    }

    public DesignFormDefine getCurrentForm() {
        return this.currentForm;
    }

    public void setCurrentForm(DesignFormDefine currentForm) {
        this.currentForm = currentForm;
    }

    public DesignDataRegionDefine getDataRegion() {
        return this.dataRegion;
    }

    public void setDataRegion(DesignDataRegionDefine dataRegion) {
        this.dataRegion = dataRegion;
    }

    public Sheet getCurrentSheet() {
        return this.currentSheet;
    }

    public void setCurrentSheet(Sheet currentSheet) {
        this.currentSheet = currentSheet;
    }

    public List<ImportLinkDTO> getLinkCells() {
        return this.linkCells;
    }

    public void setLinkCells(List<ImportLinkDTO> linkCells) {
        this.linkCells = linkCells;
    }

    public List<ImportDropDownDTO> getDropDownCells() {
        return this.DropDownCells;
    }

    public void setDropDownCells(List<ImportDropDownDTO> dropDownCells) {
        this.DropDownCells = dropDownCells;
    }

    public List<ImportFormulaDTO> getFormulaCells() {
        return this.formulaCells;
    }

    public void setFormulaCells(List<ImportFormulaDTO> formulaCells) {
        this.formulaCells = formulaCells;
    }

    public ExcelReverseModelDataProvider getReverseModelDataProvider() {
        return this.reverseModelDataProvider;
    }

    public void setReverseModelDataProvider(ExcelReverseModelDataProvider reverseModelDataProvider) {
        this.reverseModelDataProvider = reverseModelDataProvider;
        this.reverseModelDataProvider.changeImportContext(this);
    }
}

