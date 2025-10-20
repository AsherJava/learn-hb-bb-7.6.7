/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.subject.impl.subject.dto.SubjectDTO
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO
 *  com.jiuqi.gcreport.organization.impl.bean.OrgDataDO
 */
package com.jiuqi.dc.adjustvchr.impl.check.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO;
import com.jiuqi.dc.adjustvchr.impl.check.AdjustVchrImportCheckService;
import com.jiuqi.dc.adjustvchr.impl.impexp.entity.AdjustVchrItemImportVO;
import com.jiuqi.dc.adjustvchr.impl.utils.AdjustVchrImportCheckUtil;
import com.jiuqi.gcreport.organization.impl.bean.OrgDataDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ExistAndMultiCheckServiceImpl
implements AdjustVchrImportCheckService {
    private static final String NO_EXIST_UNIT_MSG = "\u7b2c%1$s\u884c\u7ec4\u7ec7\u673a\u6784\u4e0d\u5b58\u5728\uff0c\u8bf7\u786e\u8ba4\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String MULTI_UNIT_MSG = "\u7b2c%1$s\u884c\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u5bf9\u5e94\u591a\u4e2a\u7ec4\u7ec7\u673a\u6784\uff0c\u8bf7\u786e\u8ba4\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String NO_EXIST_ADJUSTTYPE_MSG = "\u7b2c%1$s\u884c\u8c03\u6574\u7c7b\u578b\u4e0d\u5b58\u5728\uff0c\u8bf7\u786e\u8ba4\u8c03\u6574\u7c7b\u578b\u4ee3\u7801\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String MULTI_ADJUSTTYPE_MSG = "\u7b2c%1$s\u884c\u8c03\u6574\u7c7b\u578b\u540d\u79f0\u5bf9\u5e94\u591a\u4e2a\u8c03\u6574\u7c7b\u578b\uff0c\u8bf7\u786e\u8ba4\u8c03\u6574\u7c7b\u578b\u4ee3\u7801\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String NO_EXIST_SUBJECT_MSG = "\u7b2c%1$s\u884c\u79d1\u76ee\u4e0d\u5b58\u5728\uff0c\u8bf7\u786e\u8ba4\u79d1\u76ee\u4ee3\u7801\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String MULTI_SUBJECT_MSG = "\u7b2c%1$s\u884c\u79d1\u76ee\u540d\u79f0\u5bf9\u5e94\u591a\u4e2a\u79d1\u76ee\uff0c\u8bf7\u786e\u8ba4\u79d1\u76ee\u4ee3\u7801\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String NO_EXIST_CURR_MSG = "\u7b2c%1$s\u884c\u4ea4\u6613\u5e01\u79cd\u4e0d\u5b58\u5728\uff0c\u8bf7\u786e\u8ba4\u4ea4\u6613\u5e01\u79cd\u4ee3\u7801\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String MULTI_CURR_MSG = "\u7b2c%1$s\u884c\u4ea4\u6613\u5e01\u79cd\u540d\u79f0\u5bf9\u5e94\u591a\u4e2a\u79d1\u76ee\uff0c\u8bf7\u786e\u8ba4\u4ea4\u6613\u5e01\u79cd\u4ee3\u7801\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";

    @Override
    public String checkImportData(int startIndex, List<AdjustVchrItemImportVO> importData, AdjustVoucherQueryDTO importParam, AdjustVchrSysOptionVO optionVO, boolean convertEnable) {
        StringBuilder errorCheckImportMsg = new StringBuilder();
        ArrayList<Integer> noExistUnitIndex = new ArrayList<Integer>();
        ArrayList<Integer> multiUnitIndex = new ArrayList<Integer>();
        ArrayList<Integer> noExistAdjustTypeIndex = new ArrayList<Integer>();
        ArrayList<Integer> multiAdjustTypeIndex = new ArrayList<Integer>();
        ArrayList<Integer> noExistSubjectIndex = new ArrayList<Integer>();
        ArrayList<Integer> multiSubjectIndex = new ArrayList<Integer>();
        ArrayList<Integer> noExistCurrIndex = new ArrayList<Integer>();
        ArrayList<Integer> multiCurrIndex = new ArrayList<Integer>();
        HashSet<String> subjectCodeSet = new HashSet<String>();
        HashSet<String> repeatSubjectNameSet = new HashSet<String>();
        HashMap<String, String> subjectNameMap = new HashMap<String, String>();
        AdjustVchrImportCheckUtil.getSubjectMap(subjectNameMap, repeatSubjectNameSet, subjectCodeSet, new HashMap<String, SubjectDTO>());
        String orgVerCode = importParam.getAcctYear() + importParam.getPeriodType() + "00" + (importParam.getAffectPeriodStart() > 9 ? importParam.getAffectPeriodStart() : "0" + importParam.getAffectPeriodStart());
        String orgType = importParam.getOrgType();
        HashSet<String> unitCodeSet = new HashSet<String>();
        HashSet<String> repeatUnitNameSet = new HashSet<String>();
        HashMap<String, String> unitNameMap = new HashMap<String, String>();
        AdjustVchrImportCheckUtil.getOrgMap(orgVerCode, orgType, unitCodeSet, repeatUnitNameSet, unitNameMap, new HashMap<String, OrgDataDO>());
        HashSet<String> adjustTypeCodeSet = new HashSet<String>();
        HashSet<String> repeatAdjustTypeNameSet = new HashSet<String>();
        HashMap<String, String> adjustTypeNameMap = new HashMap<String, String>();
        AdjustVchrImportCheckUtil.getBaseDataMap("MD_ADJUSTTYPE", adjustTypeCodeSet, repeatAdjustTypeNameSet, adjustTypeNameMap);
        HashSet<String> currCodeSet = new HashSet<String>();
        HashSet<String> repeatCurrNameSet = new HashSet<String>();
        HashMap<String, String> currNameMap = new HashMap<String, String>();
        AdjustVchrImportCheckUtil.getBaseDataMap("MD_CURRENCY", currCodeSet, repeatCurrNameSet, currNameMap);
        for (int i = 0; i < importData.size(); ++i) {
            int rowIndex = startIndex + i;
            AdjustVchrItemImportVO itemImportVO = importData.get(i);
            String unitCode = AdjustVchrImportCheckUtil.checkBaseData(rowIndex, itemImportVO.getUnit(), noExistUnitIndex, multiUnitIndex, unitCodeSet, repeatUnitNameSet, unitNameMap);
            itemImportVO.setUnit(unitCode);
            String adjustTypeCode = AdjustVchrImportCheckUtil.checkBaseData(rowIndex, itemImportVO.getAdjustType(), noExistAdjustTypeIndex, multiAdjustTypeIndex, adjustTypeCodeSet, repeatAdjustTypeNameSet, adjustTypeNameMap);
            itemImportVO.setAdjustType(adjustTypeCode);
            String subjectCode = AdjustVchrImportCheckUtil.checkBaseData(rowIndex, itemImportVO.getSubject(), noExistSubjectIndex, multiSubjectIndex, subjectCodeSet, repeatSubjectNameSet, subjectNameMap);
            itemImportVO.setSubject(subjectCode);
            if (StringUtils.isNull((String)itemImportVO.getCurrency())) continue;
            String currencyCode = AdjustVchrImportCheckUtil.checkBaseData(rowIndex, itemImportVO.getCurrency(), noExistCurrIndex, multiCurrIndex, currCodeSet, repeatCurrNameSet, currNameMap);
            itemImportVO.setCurrency(currencyCode);
        }
        if (!noExistUnitIndex.isEmpty()) {
            errorCheckImportMsg.append(String.format(NO_EXIST_UNIT_MSG, noExistUnitIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!multiUnitIndex.isEmpty()) {
            errorCheckImportMsg.append(String.format(MULTI_UNIT_MSG, multiUnitIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!noExistAdjustTypeIndex.isEmpty()) {
            errorCheckImportMsg.append(String.format(NO_EXIST_ADJUSTTYPE_MSG, noExistAdjustTypeIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!multiAdjustTypeIndex.isEmpty()) {
            errorCheckImportMsg.append(String.format(MULTI_ADJUSTTYPE_MSG, multiAdjustTypeIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!noExistSubjectIndex.isEmpty()) {
            errorCheckImportMsg.append(String.format(NO_EXIST_SUBJECT_MSG, noExistSubjectIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!multiSubjectIndex.isEmpty()) {
            errorCheckImportMsg.append(String.format(MULTI_SUBJECT_MSG, multiSubjectIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!noExistCurrIndex.isEmpty()) {
            errorCheckImportMsg.append(String.format(NO_EXIST_CURR_MSG, noExistCurrIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!multiCurrIndex.isEmpty()) {
            errorCheckImportMsg.append(String.format(MULTI_CURR_MSG, multiCurrIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        return errorCheckImportMsg.toString();
    }

    @Override
    public int checkOrder() {
        return 2;
    }
}

