/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileInfo
 *  javax.servlet.http.HttpServletResponse
 *  nr.single.map.configurations.bean.AutoAppendCode
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.configurations.file.bean.FileAnalysisPojo
 *  nr.single.map.configurations.file.bean.UploadingParam
 */
package nr.single.para.configurations.service;

import com.jiuqi.nr.file.FileInfo;
import javax.servlet.http.HttpServletResponse;
import nr.single.map.configurations.bean.AutoAppendCode;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.file.bean.FileAnalysisPojo;
import nr.single.map.configurations.file.bean.UploadingParam;

public interface FileAnalysisService {
    public FileAnalysisPojo getMappingConfig(String var1, String var2, String var3, String var4, byte[] var5);

    public FileAnalysisPojo reloadMappingConfig(String var1, String var2, String var3, String var4, byte[] var5);

    public void insertDefaultConfigInImport(String var1, byte[] var2, ISingleMappingConfig var3);

    public void updateConfig(String var1, byte[] var2, ISingleMappingConfig var3);

    public FileAnalysisPojo getEntityFile(String var1, String var2, byte[] var3);

    public FileAnalysisPojo getZbFile(String var1, String var2, byte[] var3);

    public FileAnalysisPojo getFormulaFile(String var1, String var2, byte[] var3, String var4);

    public void exportFormulaMapping(String var1, String var2, HttpServletResponse var3);

    public void exportEntityMapping(String var1, HttpServletResponse var2);

    public FileInfo uploadCacheData(UploadingParam var1) throws Exception;

    public AutoAppendCode uploadAppendCodeMapping(String var1, byte[] var2);

    public void exportAppendCode(String var1, HttpServletResponse var2);
}

