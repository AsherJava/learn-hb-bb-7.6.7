/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.param.transfer.formScheme.dto;

import com.jiuqi.nr.param.transfer.definition.dto.form.FormDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formgroup.FormGroupDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.FormSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintSettingDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintTemplateDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintTemplateSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.singlemap.SingleMappingDTO;
import java.util.ArrayList;
import java.util.List;

public class FormSchemeFullDTO {
    private List<FormSchemeDTO> allFormSchemeDTOs = new ArrayList<FormSchemeDTO>();
    private List<FormGroupDTO> allFormGroupDTOs = new ArrayList<FormGroupDTO>();
    private List<FormulaSchemeDTO> allFormulaSchemeDTOs = new ArrayList<FormulaSchemeDTO>();
    private List<FormDTO> allFormDTOs = new ArrayList<FormDTO>();
    private List<FormulaSchemeDTO> allFormFormulaDTOs = new ArrayList<FormulaSchemeDTO>();
    private List<PrintTemplateSchemeDTO> allPrintTemplateSchemeDTOs = new ArrayList<PrintTemplateSchemeDTO>();
    private List<PrintTemplateDTO> allPrintTemplateDTOs = new ArrayList<PrintTemplateDTO>();
    private List<SingleMappingDTO> allSingleMappingDTOs = new ArrayList<SingleMappingDTO>();
    private List<PrintSettingDTO> allPrintSettingDTOs = new ArrayList<PrintSettingDTO>();

    public List<FormSchemeDTO> getAllFormSchemeDTOs() {
        return this.allFormSchemeDTOs;
    }

    public void setAllFormSchemeDTOs(List<FormSchemeDTO> allFormSchemeDTOs) {
        this.allFormSchemeDTOs = allFormSchemeDTOs;
    }

    public List<FormGroupDTO> getAllFormGroupDTOs() {
        return this.allFormGroupDTOs;
    }

    public void setAllFormGroupDTOs(List<FormGroupDTO> allFormGroupDTOs) {
        this.allFormGroupDTOs = allFormGroupDTOs;
    }

    public List<FormulaSchemeDTO> getAllFormulaSchemeDTOs() {
        return this.allFormulaSchemeDTOs;
    }

    public void setAllFormulaSchemeDTOs(List<FormulaSchemeDTO> allFormulaSchemeDTOs) {
        this.allFormulaSchemeDTOs = allFormulaSchemeDTOs;
    }

    public List<FormDTO> getAllFormDTOs() {
        return this.allFormDTOs;
    }

    public void setAllFormDTOs(List<FormDTO> allFormDTOs) {
        this.allFormDTOs = allFormDTOs;
    }

    public List<FormulaSchemeDTO> getAllFormFormulaDTOs() {
        return this.allFormFormulaDTOs;
    }

    public void setAllFormFormulaDTOs(List<FormulaSchemeDTO> allFormFormulaDTOs) {
        this.allFormFormulaDTOs = allFormFormulaDTOs;
    }

    public List<PrintTemplateSchemeDTO> getAllPrintTemplateSchemeDTOs() {
        return this.allPrintTemplateSchemeDTOs;
    }

    public void setAllPrintTemplateSchemeDTOs(List<PrintTemplateSchemeDTO> allPrintTemplateSchemeDTOs) {
        this.allPrintTemplateSchemeDTOs = allPrintTemplateSchemeDTOs;
    }

    public List<PrintTemplateDTO> getAllPrintTemplateDTOs() {
        return this.allPrintTemplateDTOs;
    }

    public void setAllPrintTemplateDTOs(List<PrintTemplateDTO> allPrintTemplateDTOs) {
        this.allPrintTemplateDTOs = allPrintTemplateDTOs;
    }

    public List<SingleMappingDTO> getAllSingleMappingDTOs() {
        return this.allSingleMappingDTOs;
    }

    public void setAllSingleMappingDTOs(List<SingleMappingDTO> allSingleMappingDTOs) {
        this.allSingleMappingDTOs = allSingleMappingDTOs;
    }

    public List<PrintSettingDTO> getAllPrintSettingDTOs() {
        return this.allPrintSettingDTOs;
    }

    public void setAllPrintSettingDTOs(List<PrintSettingDTO> allPrintSettingDTOs) {
        this.allPrintSettingDTOs = allPrintSettingDTOs;
    }
}

