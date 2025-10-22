/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.service;

import java.util.List;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.upload.domain.EnumDataDTO;
import nr.single.para.upload.domain.EnumDefineMappingDTO;
import nr.single.para.upload.domain.FMDMMappingDTO;
import nr.single.para.upload.domain.FormMappingDTO;
import nr.single.para.upload.domain.FormulaFormMappingDTO;
import nr.single.para.upload.domain.FormulaSchemeMappingDTO;
import nr.single.para.upload.domain.PrintSchemeMappingDTO;
import nr.single.para.upload.domain.TaskLinkMappingDTO;
import nr.single.para.upload.domain.UploadFileOptionDTO;
import nr.single.para.upload.domain.ZBMappingDTO;

public interface IFileUpdateService {
    public void saveFMDMResult(String var1, List<FMDMMappingDTO> var2) throws SingleCompareException;

    public void saveEnumDefineResult(String var1, List<EnumDefineMappingDTO> var2) throws SingleCompareException;

    public void saveEnumDataResult(String var1, EnumDataDTO var2) throws SingleCompareException;

    public void saveTaskResult(String var1, List<TaskLinkMappingDTO> var2) throws SingleCompareException;

    public void savePrintScheme(String var1, List<PrintSchemeMappingDTO> var2) throws SingleCompareException;

    public void saveFormulaScheme(String var1, List<FormulaSchemeMappingDTO> var2) throws SingleCompareException;

    public void saveFormulaForm(String var1, List<FormulaFormMappingDTO> var2) throws SingleCompareException;

    public void saveFormResult(String var1, List<FormMappingDTO> var2) throws SingleCompareException;

    public void saveZBResult(String var1, List<ZBMappingDTO> var2) throws SingleCompareException;

    public void saveImportOption(String var1, UploadFileOptionDTO var2) throws SingleCompareException;
}

