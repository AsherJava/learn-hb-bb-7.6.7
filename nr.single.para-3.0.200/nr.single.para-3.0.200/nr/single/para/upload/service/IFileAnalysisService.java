/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.service;

import java.util.List;
import java.util.Map;
import nr.single.para.compare.definition.common.CompareUpdateType;
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
import nr.single.para.upload.domain.UploadResultInfoDTO;
import nr.single.para.upload.domain.ZBMappingDTO;
import nr.single.para.upload.vo.RepeatAndEmptyInfos;

public interface IFileAnalysisService {
    public List<FMDMMappingDTO> listFMDMResult(String var1);

    public List<EnumDefineMappingDTO> listEnumDefineResult(String var1);

    public List<EnumDefineMappingDTO> listEnumDefineChangeResult(String var1);

    public EnumDataDTO listEnumDataResult(String var1, EnumDataDTO var2);

    public List<TaskLinkMappingDTO> listTaskLinkResult(String var1);

    public List<FormulaSchemeMappingDTO> listFormulaSchemeResult(String var1);

    public List<FormulaFormMappingDTO> listFormulaFormResult(String var1);

    public List<FormMappingDTO> listFormResult(String var1);

    public List<FormMappingDTO> listFormChangeResult(String var1, int var2);

    public List<ZBMappingDTO> listZBResult(String var1, String var2, Integer var3, boolean var4);

    public List<ZBMappingDTO> listAllZBResultByUpdateType(String var1, CompareUpdateType var2);

    public List<ZBMappingDTO> listZBInfo(String var1, ZBMappingDTO var2);

    public List<PrintSchemeMappingDTO> listPrintSchemeResult(String var1, PrintSchemeMappingDTO var2);

    public UploadResultInfoDTO queryUploadInfo(String var1);

    public UploadResultInfoDTO queryUploadResult(String var1);

    public Map<Integer, String> getStyleByTypeAndFormComapreKey(String var1, String var2);

    public List<String> getAllSinglePrintScheme(String var1);

    public RepeatAndEmptyInfos getEnumItemEmptyInfo(String var1, boolean var2, String var3, List<EnumDefineMappingDTO> var4);

    public RepeatAndEmptyInfos getZBEmptyInfo(String var1, String var2, boolean var3, List<FormMappingDTO> var4);

    public List<String> getEmptyInfo(String var1, String var2);

    public UploadFileOptionDTO getImportOption(String var1) throws SingleCompareException;
}

