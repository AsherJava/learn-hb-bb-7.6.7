/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.report.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.report.dto.ReportTagDTO;
import com.jiuqi.nr.report.web.vo.CustomTagVO;
import com.jiuqi.nr.report.web.vo.ExportTagParam;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IReportTagManageService {
    public void insertTags(List<ReportTagDTO> var1);

    public List<ReportTagDTO> listAllTagsByRpt(String var1);

    public void deleteTags(List<String> var1);

    public void deleteTagsByRptKey(String var1);

    public void updateTags(List<CustomTagVO> var1);

    public List<ReportTagDTO> filterCustomTagsInRpt(String var1, String var2) throws JQException;

    public void exportTagInfo(ExportTagParam var1, HttpServletResponse var2) throws Exception;

    public void importTagInfo(MultipartFile var1, String var2) throws JQException;
}

