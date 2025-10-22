/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 */
package nr.single.para.upload.service;

import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import java.util.List;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.upload.domain.CreateParamDTO;
import nr.single.para.upload.domain.ParamImportInfoDTO;
import nr.single.para.upload.domain.SingleCompareDTO;
import nr.single.para.upload.domain.SingleCompareResult;
import nr.single.para.upload.domain.UploadFileDTO;
import nr.single.para.upload.domain.ZBMappingDTO;
import nr.single.para.upload.vo.FixRegionCompareVO;
import nr.single.para.upload.vo.FloatRegionCompareVO;
import nr.single.para.upload.vo.FormSchemeVO;

public interface IFileExecuteService {
    public UploadFileDTO uploadAndMatchFile(String var1, byte[] var2) throws Exception;

    public AsyncTaskInfo analysisFile(CreateParamDTO var1) throws Exception;

    public SingleCompareResult analysisFileEx(CreateParamDTO var1) throws Exception;

    public AsyncTaskInfo executeUpload(String var1) throws Exception;

    public void queryProcess(String var1);

    public Boolean singleAnalysis(SingleCompareDTO var1) throws Exception;

    public AsyncTaskInfo deleteCompareInfo(String var1) throws Exception;

    public AsyncTaskInfo deleteCompareInfos(List<String> var1) throws Exception;

    public List<ParamImportInfoDTO> listAllCompareInfos() throws Exception;

    public void floatRegionReCompare(FloatRegionCompareVO var1) throws Exception;

    public List<ZBMappingDTO> fixRegionReCompare(FixRegionCompareVO var1) throws Exception;

    public void doEnumCompare(String var1, String var2) throws Exception;

    public void doPartEnumCompare(String var1, String var2, List<String> var3) throws Exception;

    public List<FormSchemeVO> getFormSchemeByTaskYear(String var1, String var2, String var3) throws Exception;

    public List<String> getSingleEnumCodeInFmdm(String var1) throws SingleCompareException;
}

