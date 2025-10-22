/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustServiceAbstract
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.gcreport.offsetitem.utils;

import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustServiceAbstract;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcReduceUtil
extends GcOffSetItemAdjustServiceAbstract {
    private static final Logger logger = LoggerFactory.getLogger(GcReduceUtil.class);
    private Map<String, String> unitId2TitleCache = new HashMap<String, String>();
    private Map<String, String> subject2TitleCache = new HashMap<String, String>();

    public void setReclassifyViewPropsDTO(QueryParamsVO queryParamsVO, List<GcOffSetVchrItemDTO> records, String systemId, ConsolidatedOptionVO optionVO) {
        Map fieldCode2DictTableMap = this.initFieldCode2DictTableMap(queryParamsVO.getOtherShowColumns());
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        for (GcOffSetVchrItemDTO itemDTO : records) {
            Map record = itemDTO.getFields();
            this.setReclassifyViewPropsRecord(queryParamsVO, fieldCode2DictTableMap, tool, record, systemId, optionVO);
        }
    }

    private void setReclassifyViewPropsRecord(QueryParamsVO queryParamsVO, Map<String, String> fieldCode2DictTableMap, GcOrgCenterService tool, Map<String, Object> record, String systemId, ConsolidatedOptionVO optionVO) {
        Object debitValue;
        boolean showDictCode = "1".equals(optionVO.getShowDictCode());
        Integer orient = this.getOrient(record);
        record.put("OPPUNITTITLE", this.getUnitTitle((String)record.get("OPPUNITID"), this.unitId2TitleCache, tool));
        record.put("UNITTITLE", this.getUnitTitle((String)record.get("UNITID"), this.unitId2TitleCache, tool));
        this.setSubjectTitle(systemId, record, this.subject2TitleCache, "SUBJECTTITLE", "SUBJECTCODE");
        Object creditValue = record.get("OFFSETCREDIT");
        if (creditValue instanceof Double) {
            record.put("OFFSETCREDIT", NumberUtils.doubleToString((Double)((Double)creditValue)));
        }
        if ((debitValue = record.get("OFFSETDEBIT")) instanceof Double) {
            record.put("OFFSETDEBIT", NumberUtils.doubleToString((Double)((Double)debitValue)));
        }
        if (Objects.equals(orient, OrientEnum.C.getValue())) {
            record.put("OFFSETDEBIT", "");
        } else {
            record.put("OFFSETCREDIT", "");
        }
        this.setOtherShowColumnDictTitle(record, queryParamsVO.getOtherShowColumns(), fieldCode2DictTableMap, showDictCode);
    }

    private Integer getOrient(Map<String, Object> record) {
        String offsetcredit;
        String offsetdebit;
        String string = offsetdebit = record.get("OFFSETDEBIT") == null ? null : String.valueOf(record.get("OFFSETDEBIT"));
        if (!StringUtils.isEmpty((CharSequence)offsetdebit)) {
            try {
                double debitValue = Double.parseDouble(offsetdebit.replace(",", ""));
                if (debitValue != 0.0) {
                    return 1;
                }
            }
            catch (NumberFormatException e) {
                logger.warn("OFFSETDEBIT\u6570\u636e\u8f6c\u6362\u5931\u8d25: {}", (Object)offsetdebit, (Object)e);
            }
        }
        String string2 = offsetcredit = record.get("OFFSETCREDIT") == null ? null : String.valueOf(record.get("OFFSETCREDIT"));
        if (!StringUtils.isEmpty((CharSequence)offsetcredit)) {
            try {
                double creditValue = Double.parseDouble(offsetcredit.replace(",", ""));
                if (creditValue != 0.0) {
                    return -1;
                }
            }
            catch (NumberFormatException e) {
                logger.warn("OFFSETCREDIT\u6570\u636e\u8f6c\u6362\u5931\u8d25: {}", (Object)offsetcredit, (Object)e);
            }
        }
        return (Integer)record.get("ORIENT");
    }
}

