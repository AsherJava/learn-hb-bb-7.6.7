/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition.convert;

import nr.single.para.compare.definition.CompareDataDTO;
import nr.single.para.compare.definition.CompareDataEnumDTO;
import nr.single.para.compare.definition.CompareDataEnumItemDTO;
import nr.single.para.compare.definition.CompareDataFMDMFieldDTO;
import nr.single.para.compare.definition.CompareDataFieldDTO;
import nr.single.para.compare.definition.CompareDataFormDTO;
import nr.single.para.compare.definition.CompareDataFormulaDTO;
import nr.single.para.compare.definition.CompareDataFormulaFormDTO;
import nr.single.para.compare.definition.CompareDataFormulaSchemeDTO;
import nr.single.para.compare.definition.CompareDataPrintItemDTO;
import nr.single.para.compare.definition.CompareDataPrintSchemeDTO;
import nr.single.para.compare.definition.CompareDataTaskLinkDTO;
import nr.single.para.compare.definition.CompareInfoDTO;
import nr.single.para.compare.definition.CompareMapFieldDTO;
import nr.single.para.compare.definition.ICompareData;
import nr.single.para.compare.definition.ICompareDataEnum;
import nr.single.para.compare.definition.ICompareDataEnumItem;
import nr.single.para.compare.definition.ICompareDataFMDMField;
import nr.single.para.compare.definition.ICompareDataField;
import nr.single.para.compare.definition.ICompareDataForm;
import nr.single.para.compare.definition.ICompareDataFormula;
import nr.single.para.compare.definition.ICompareDataFormulaForm;
import nr.single.para.compare.definition.ICompareDataFormulaScheme;
import nr.single.para.compare.definition.ICompareDataPrintItem;
import nr.single.para.compare.definition.ICompareDataPrintScheme;
import nr.single.para.compare.definition.ICompareDataTaskLink;
import nr.single.para.compare.definition.ICompareInfo;
import nr.single.para.compare.definition.ICompareMapField;

public class Convert {
    public static CompareInfoDTO ci2Do(ICompareInfo compareInfo) {
        return CompareInfoDTO.valueOf(compareInfo);
    }

    public static CompareMapFieldDTO cmf2Do(ICompareMapField compareInfo) {
        return CompareMapFieldDTO.valueOf(compareInfo);
    }

    public static CompareDataDTO cd2Do(ICompareData value) {
        return CompareDataDTO.valueOf(value);
    }

    public static CompareDataEnumDTO cde2Do(ICompareDataEnum value) {
        return CompareDataEnumDTO.valueOf(value);
    }

    public static CompareDataEnumItemDTO cdei2Do(ICompareDataEnumItem value) {
        return CompareDataEnumItemDTO.valueOf(value);
    }

    public static CompareDataFieldDTO cdf2Do(ICompareDataField value) {
        return CompareDataFieldDTO.valueOf(value);
    }

    public static CompareDataFMDMFieldDTO cdff2Do(ICompareDataFMDMField value) {
        return CompareDataFMDMFieldDTO.valueOf(value);
    }

    public static CompareDataFormDTO cdformDo(ICompareDataForm value) {
        return CompareDataFormDTO.valueOf(value);
    }

    public static CompareDataPrintItemDTO cdpiDo(ICompareDataPrintItem value) {
        return CompareDataPrintItemDTO.valueOf(value);
    }

    public static CompareDataPrintSchemeDTO cdpsDo(ICompareDataPrintScheme value) {
        return CompareDataPrintSchemeDTO.valueOf(value);
    }

    public static CompareDataFormulaSchemeDTO cdfsDo(ICompareDataFormulaScheme value) {
        return CompareDataFormulaSchemeDTO.valueOf(value);
    }

    public static CompareDataFormulaFormDTO cdfffromDo(ICompareDataFormulaForm value) {
        return CompareDataFormulaFormDTO.valueOf(value);
    }

    public static CompareDataFormulaDTO cdffromDo(ICompareDataFormula value) {
        return CompareDataFormulaDTO.valueOf(value);
    }

    public static CompareDataTaskLinkDTO cdtlDo(ICompareDataTaskLink value) {
        return CompareDataTaskLinkDTO.valueOf(value);
    }
}

