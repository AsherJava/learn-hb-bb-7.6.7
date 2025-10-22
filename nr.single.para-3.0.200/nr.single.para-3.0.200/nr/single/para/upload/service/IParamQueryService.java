/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.org.ZB
 */
package nr.single.para.upload.service;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.org.ZB;
import java.util.List;
import java.util.Map;
import nr.single.para.upload.domain.CommonParamDTO;
import nr.single.para.upload.domain.DataLinkDTO;
import nr.single.para.upload.domain.EnumDataDTO;
import nr.single.para.upload.domain.EnumDataMappingDTO;
import nr.single.para.upload.domain.EnumDefineMappingDTO;
import nr.single.para.upload.domain.FMDMMappingDTO;
import nr.single.para.upload.domain.FormMappingDTO;
import nr.single.para.upload.domain.ZBMappingDTO;
import nr.single.para.upload.vo.DefaultCodeObject;
import nr.single.para.upload.vo.FormSchemeVO;
import nr.single.para.upload.vo.RepeatAndDefaultCodeVO;
import nr.single.para.upload.vo.TaskInfoVO;
import nr.single.para.upload.vo.TaskQueryVO;
import nr.single.para.upload.vo.ZBInfoVO;

public interface IParamQueryService {
    public List<TaskInfoVO> getTaskList(List<String> var1);

    public List<FormSchemeVO> listFormScheme(String var1);

    public List<CommonParamDTO> listDataTable(String var1, Boolean var2);

    public List<CommonParamDTO> listZBByTableCode(String var1);

    public List<CommonParamDTO> getEnumList(String var1);

    public List<CommonParamDTO> getEnumListByPrefix(String var1);

    public List<CommonParamDTO> listPeriod();

    public List<CommonParamDTO> listEntity(List<String> var1);

    public List<BaseDataDO> listBaseData(String var1, String var2);

    public List<ZB> queryZBInfo(ZBInfoVO var1);

    public Map<String, String> queryOrgZbInfo(String var1);

    public Map<String, String> queryZBsInForm(FMDMMappingDTO var1);

    public Map<String, CommonParamDTO> queryZBsExInForm(FMDMMappingDTO var1);

    public Map<String, String> getMdInfoZbsInDataScheme(String var1);

    public Map<String, CommonParamDTO> getMdInfoZbsExInDataScheme(String var1);

    public List<TaskQueryVO> queryAllTaskAndFormScheme();

    public void checkDataScheme(DesignDataScheme var1);

    public List<ZBMappingDTO> getOriginalZB(List<ZBMappingDTO> var1);

    public List<FMDMMappingDTO> getOriginalFMDM(List<FMDMMappingDTO> var1);

    public List<EnumDataMappingDTO> getOriginalEnumData(EnumDataDTO var1, String var2);

    public List<EnumDefineMappingDTO> getOriginalEnumDefine(String var1, List<EnumDefineMappingDTO> var2);

    public List<FormMappingDTO> getOriginalForm(String var1, List<FormMappingDTO> var2);

    public List<String> getZBRepeatInfos(String var1, String var2, List<ZBMappingDTO> var3, List<ZBMappingDTO> var4, List<DesignDataField> var5);

    public List<DataLinkDTO> getDataLinkByForm(String var1);

    public List<DefaultCodeObject> getZBDefaultCode(String var1, String var2, List<ZBMappingDTO> var3);

    public List<String> getFormRepeatInfos(String var1, List<FormMappingDTO> var2);

    public RepeatAndDefaultCodeVO getFMDMRepeatInfos(String var1, List<FMDMMappingDTO> var2, List<FMDMMappingDTO> var3);

    public RepeatAndDefaultCodeVO getEnumDefineRepeatInfos(String var1, String var2, List<EnumDefineMappingDTO> var3, List<EnumDefineMappingDTO> var4);

    public List<String> getEnumItemRepeatInfos(EnumDataDTO var1, String var2);

    public List<CommonParamDTO> checkEnums(List<String> var1);
}

