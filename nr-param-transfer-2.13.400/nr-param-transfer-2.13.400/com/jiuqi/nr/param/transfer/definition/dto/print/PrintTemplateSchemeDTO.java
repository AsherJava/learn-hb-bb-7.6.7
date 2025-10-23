/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.param.transfer.definition.dto.print;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.param.transfer.definition.dto.BaseDTO;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintTemplateDTO;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintTemplateSchemeInfoDTO;
import java.io.IOException;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PrintTemplateSchemeDTO
extends BaseDTO {
    private PrintTemplateSchemeInfoDTO printTemplateSchemeInfo;
    private List<PrintTemplateDTO> printTemplates;
    private DesParamLanguageDTO desParamLanguageDTO;

    public PrintTemplateSchemeInfoDTO getPrintTemplateSchemeInfo() {
        return this.printTemplateSchemeInfo;
    }

    public void setPrintTemplateSchemeInfo(PrintTemplateSchemeInfoDTO printTemplateSchemeInfo) {
        this.printTemplateSchemeInfo = printTemplateSchemeInfo;
    }

    public List<PrintTemplateDTO> getPrintTemplates() {
        return this.printTemplates;
    }

    public void setPrintTemplates(List<PrintTemplateDTO> printTemplates) {
        this.printTemplates = printTemplates;
    }

    public DesParamLanguageDTO getDesParamLanguageDTO() {
        return this.desParamLanguageDTO;
    }

    public void setDesParamLanguageDTO(DesParamLanguageDTO desParamLanguageDTO) {
        this.desParamLanguageDTO = desParamLanguageDTO;
    }

    public static PrintTemplateSchemeDTO valueOf(byte[] bytes, ObjectMapper objectMapper) throws IOException {
        return (PrintTemplateSchemeDTO)objectMapper.readValue(bytes, PrintTemplateSchemeDTO.class);
    }
}

