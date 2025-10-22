/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.workingpaper.vo.SubjectZbPentrationParamsVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryWayItemVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.workingpaper.service;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperType;
import com.jiuqi.gcreport.workingpaper.vo.SubjectZbPentrationParamsVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryWayItemVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;

public interface WorkingPaperService {
    public List<WorkingPaperQueryWayItemVO> getWorkingPaperQueryWays(WorkingPaperType var1);

    public WorkingPaperQueryWayItemVO addOrUpdateWorkPaperQueryWay(WorkingPaperQueryWayItemVO var1);

    public WorkingPaperQueryWayItemVO queryWorkPaperQueryWay(String var1);

    public Boolean deleteWorkPaperQueryWay(String var1);

    public Boolean exchangeSortWorkPaperQueryWay(String var1, String var2);

    public List<WorkingPaperTableHeaderVO> createHeaderVO(WorkingPaperQueryCondition var1);

    public ExportExcelSheet getExcelVO(ExportContext var1, Workbook var2, WorkingPaperQueryCondition var3);

    public List<WorkingPaperTableDataVO> getDataVO(WorkingPaperQueryCondition var1);

    public Object getWorkPaperPentrationDatas(HttpServletRequest var1, HttpServletResponse var2, WorkingPaperPentrationQueryCondtion var3);

    public SubjectZbPentrationParamsVO getSubjectZbPentrateParams(String var1, WorkingPaperQueryCondition var2);
}

