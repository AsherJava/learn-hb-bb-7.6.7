/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO
 *  com.jiuqi.gcreport.org.api.vo.field.ExportMessageVO
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.org.impl.io.service;

import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO;
import com.jiuqi.gcreport.org.api.vo.field.ExportMessageVO;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgParam;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface GcOrgDataService {
    public List<ExportMessageVO> uploadOrgData(MultipartFile var1, ExportConditionVO var2);

    public List<ExportMessageVO> uploadOrgData(Workbook var1, ExportConditionVO var2);

    public List<ExportMessageVO> uploadOrgData(Workbook var1, ExportConditionVO var2, boolean var3);

    public List<GcOrgCacheVO> list(GcOrgParam var1);
}

