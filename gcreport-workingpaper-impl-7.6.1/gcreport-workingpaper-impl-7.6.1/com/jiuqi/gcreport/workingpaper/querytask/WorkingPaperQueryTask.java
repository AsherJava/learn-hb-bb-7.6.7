/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableVO
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.workingpaper.querytask;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperDxsTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperQmsTypeEnum;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperDxsItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperRytzItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperUnDxsItemDTO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableVO;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;

public interface WorkingPaperQueryTask {
    public List<WorkingPaperTableHeaderVO> getHeaderVO(WorkingPaperQueryCondition var1, WorkingPaperQmsTypeEnum var2, WorkingPaperDxsTypeEnum var3);

    public List<WorkingPaperTableDataVO> getDataVO(WorkingPaperQueryCondition var1, WorkingPaperQmsTypeEnum var2, WorkingPaperDxsTypeEnum var3);

    public WorkingPaperTableVO queryAllData(WorkingPaperQueryCondition var1);

    public ExportExcelSheet getExcelVO(ExportContext var1, Workbook var2, WorkingPaperQueryCondition var3, WorkingPaperQmsTypeEnum var4, WorkingPaperDxsTypeEnum var5);

    public Object getWorkPaperPentrationDatas(HttpServletRequest var1, HttpServletResponse var2, WorkingPaperPentrationQueryCondtion var3, WorkingPaperQmsTypeEnum var4, WorkingPaperDxsTypeEnum var5);

    default public WorkingPaperDxsItemDTO buildWorkingPaperOffsetItemDTOByMap(WorkingPaperQueryCondition queryCondition, Map<String, Object> map) {
        WorkingPaperDxsItemDTO dto = new WorkingPaperDxsItemDTO();
        dto.setOrgCode(ConverterUtils.getAsString((Object)map.get("UNITID")));
        dto.setSubjectCode(ConverterUtils.getAsString((Object)map.get("SUBJECTCODE")));
        dto.setSubjectOrient(OrientEnum.valueOf((Integer)ConverterUtils.getAsInteger((Object)map.get("SUBJECTORIENT"), (Integer)OrientEnum.D.getValue())));
        dto.setElmMode(ConverterUtils.getAsString((Object)map.get("ELMMODE")));
        dto.setYwlxCode(ConverterUtils.getAsString((Object)map.get("GCBUSINESSTYPECODE")));
        dto.setRuleId(ConverterUtils.getAsString((Object)map.get("RULEID")));
        dto.setDebit(ConverterUtils.getAsBigDecimal((Object)map.get("DEBIT_VALUE")));
        dto.setCredit(ConverterUtils.getAsBigDecimal((Object)map.get("CREDIT_VALUE")));
        dto.setOffSetDebit(ConverterUtils.getAsBigDecimal((Object)map.get("OFFSET_DEBIT_VALUE")));
        dto.setOffSetCredit(ConverterUtils.getAsBigDecimal((Object)map.get("OFFSET_CREDIT_VALUE")));
        dto.setDiffd(ConverterUtils.getAsBigDecimal((Object)map.get("DIFFD_VALUE")));
        dto.setDiffc(ConverterUtils.getAsBigDecimal((Object)map.get("DIFFC_VALUE")));
        Integer offSetSrcType = ConverterUtils.getAsInteger((Object)map.get("OFFSETSRCTYPE"));
        dto.setOffSetSrcType(offSetSrcType == null ? null : OffSetSrcTypeEnum.getEnumByValue((int)offSetSrcType));
        HashMap<String, String> otherColumnValueMap = new HashMap<String, String>();
        List otherColumnKeys = queryCondition.getOtherShowColumnKeys();
        Iterator iterator = otherColumnKeys.iterator();
        while (iterator.hasNext()) {
            String columnKey;
            otherColumnValueMap.put(columnKey, map.get(columnKey = (String)iterator.next()) == null ? "" : String.valueOf(map.get(columnKey)));
        }
        dto.setOtherColumnsValueMap(otherColumnValueMap);
        return dto;
    }

    default public WorkingPaperRytzItemDTO buildWorkingPaperRyOffsetItemDTOByMap(WorkingPaperQueryCondition queryCondition, Map<String, Object> map) {
        WorkingPaperRytzItemDTO dto = new WorkingPaperRytzItemDTO();
        dto.setOrgCode(ConverterUtils.getAsString((Object)map.get("UNITID")));
        dto.setSubjectCode(ConverterUtils.getAsString((Object)map.get("SUBJECTCODE")));
        dto.setSubjectOrient(OrientEnum.valueOf((Integer)ConverterUtils.getAsInteger((Object)map.get("SUBJECTORIENT"), (Integer)OrientEnum.D.getValue())));
        dto.setElmMode(ConverterUtils.getAsInteger((Object)map.get("ELMMODE")));
        dto.setYwlxCode(ConverterUtils.getAsString((Object)map.get("GCBUSINESSTYPECODE")));
        dto.setRuleId(ConverterUtils.getAsString((Object)map.get("RULEID")));
        dto.setDebit(ConverterUtils.getAsBigDecimal((Object)map.get("DEBIT_VALUE")));
        dto.setCredit(ConverterUtils.getAsBigDecimal((Object)map.get("CREDIT_VALUE")));
        dto.setOffSetDebit(ConverterUtils.getAsBigDecimal((Object)map.get("OFFSET_DEBIT_VALUE")));
        dto.setOffSetCredit(ConverterUtils.getAsBigDecimal((Object)map.get("OFFSET_CREDIT_VALUE")));
        dto.setDiffd(ConverterUtils.getAsBigDecimal((Object)map.get("DIFFD_VALUE")));
        dto.setDiffc(ConverterUtils.getAsBigDecimal((Object)map.get("DIFFC_VALUE")));
        Integer offSetSrcType = ConverterUtils.getAsInteger((Object)map.get("OFFSETSRCTYPE"));
        dto.setOffSetSrcType(offSetSrcType == null ? null : OffSetSrcTypeEnum.getEnumByValue((int)offSetSrcType));
        HashMap<String, String> otherColumnValueMap = new HashMap<String, String>();
        List otherColumnKeys = queryCondition.getOtherShowColumnKeys();
        Iterator iterator = otherColumnKeys.iterator();
        while (iterator.hasNext()) {
            String columnKey;
            otherColumnValueMap.put(columnKey, map.get(columnKey = (String)iterator.next()) == null ? "" : String.valueOf(map.get(columnKey)));
        }
        dto.setOtherColumnsValueMap(otherColumnValueMap);
        return dto;
    }

    default public WorkingPaperUnDxsItemDTO buildWorkingPaperUnOffsetItemDTOByMap(WorkingPaperQueryCondition queryCondition, Map<String, Object> map) {
        WorkingPaperUnDxsItemDTO dto = new WorkingPaperUnDxsItemDTO();
        dto.setOrgCode(ConverterUtils.getAsString((Object)map.get("UNITID")));
        dto.setSubjectCode(ConverterUtils.getAsString((Object)map.get("SUBJECTCODE")));
        Integer subjectOrient = ConverterUtils.getAsInteger((Object)map.get("SUBJECTORIENT"));
        dto.setSubjectOrient(OrientEnum.valueOf((Integer)ConverterUtils.getAsInteger((Object)map.get("SUBJECTORIENT"), (Integer)OrientEnum.D.getValue())));
        dto.setUnOffSetDebit(Objects.equals(OrientEnum.D.getValue(), subjectOrient) ? ConverterUtils.getAsBigDecimal((Object)map.get("OFFSET_VALUE")) : BigDecimal.ZERO);
        dto.setUnOffSetCredit(Objects.equals(OrientEnum.C.getValue(), subjectOrient) ? ConverterUtils.getAsBigDecimal((Object)map.get("OFFSET_VALUE")) : BigDecimal.ZERO);
        dto.setDiffd(Objects.equals(OrientEnum.D.getValue(), subjectOrient) ? ConverterUtils.getAsBigDecimal((Object)map.get("DIFF_VALUE")) : BigDecimal.ZERO);
        dto.setDiffc(Objects.equals(OrientEnum.C.getValue(), subjectOrient) ? ConverterUtils.getAsBigDecimal((Object)map.get("DIFF_VALUE")) : BigDecimal.ZERO);
        HashMap<String, String> otherColumnValueMap = new HashMap<String, String>();
        List otherColumnKeys = queryCondition.getOtherShowColumnKeys();
        Iterator iterator = otherColumnKeys.iterator();
        while (iterator.hasNext()) {
            String columnKey;
            otherColumnValueMap.put(columnKey, map.get(columnKey = (String)iterator.next()) == null ? "" : String.valueOf(map.get(columnKey)));
        }
        dto.setOtherColumnsValueMap(otherColumnValueMap);
        return dto;
    }
}

