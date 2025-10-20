/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.unionrule.vo.ImportMessageVO
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.unionrule.service;

import com.jiuqi.gcreport.unionrule.vo.ImportMessageVO;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface UnionRuleFileService {
    public Resource exportJson(String var1, Map<String, Object> var2);

    public void exportRuleToExcel(String var1, Map<String, Object> var2, HttpServletResponse var3);

    public Set<ImportMessageVO> importJson(String var1, MultipartFile var2, boolean var3);
}

