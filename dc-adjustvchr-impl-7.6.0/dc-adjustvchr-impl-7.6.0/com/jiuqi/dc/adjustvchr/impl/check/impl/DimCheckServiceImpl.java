/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.subject.impl.subject.dto.SubjectDTO
 *  com.jiuqi.common.subject.impl.subject.service.SubjectService
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.dc.adjustvchr.impl.check.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.service.SubjectService;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO;
import com.jiuqi.dc.adjustvchr.impl.check.AdjustVchrImportCheckService;
import com.jiuqi.dc.adjustvchr.impl.impexp.entity.AdjustVchrItemImportVO;
import com.jiuqi.dc.adjustvchr.impl.utils.AdjustVchrImportCheckUtil;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class DimCheckServiceImpl
implements AdjustVchrImportCheckService {
    private static final String EMPTY_REQUIRED_DIM_MSG = "\u7b2c%1$s\u884c\u3010%2$s\u3011\u8f85\u52a9\u9879\u5fc5\u586b\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u786e\u8ba4\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String NO_EXISTP_DIM_MSG = "\u7b2c%1$s\u884c\u3010%2$s\u3011\u8f85\u52a9\u9879\u4e0d\u5b58\u5728\uff0c\u8bf7\u786e\u8ba4\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String MULTI_DIM_MSG = "\u7b2c%1$s\u884c\u3010%2$s\u3011\u8f85\u52a9\u9879\u4e0d\u5b58\u5728\uff0c\u8bf7\u786e\u8ba4\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private BaseDataClient baseDataClient;

    @Override
    public String checkImportData(int startIndex, List<AdjustVchrItemImportVO> importData, AdjustVoucherQueryDTO importParam, AdjustVchrSysOptionVO optionVO, boolean convertEnable) {
        StringBuilder errorCheckImportMsg = new StringBuilder();
        Map<String, Map> subjectDimMap = this.subjectService.list().parallelStream().collect(Collectors.toMap(BaseDataDO::getCode, SubjectDTO::getAssTypeMap));
        List dimensionVOList = this.dimensionService.findAllDimFieldsVOByTableName("DC_ADJUSTVCHRITEM");
        HashMap<String, String> dimTitleMap = new HashMap<String, String>();
        HashMap<String, String> dimReferFieldMap = new HashMap<String, String>();
        for (DimensionVO dimensionVO : dimensionVOList) {
            dimTitleMap.put(dimensionVO.getTitle(), dimensionVO.getCode());
            if (StringUtils.isNull((String)dimensionVO.getReferField())) continue;
            dimReferFieldMap.put(dimensionVO.getTitle(), dimensionVO.getReferField());
        }
        for (int i = 0; i < importData.size(); ++i) {
            AdjustVchrItemImportVO itemImportVO = importData.get(i);
            String subjectCode = itemImportVO.getSubject();
            Map dimMap = subjectDimMap.get(subjectCode);
            if (ObjectUtils.isEmpty(dimMap)) continue;
            ArrayList<String> emptyDimName = new ArrayList<String>();
            ArrayList<String> noExistDimName = new ArrayList<String>();
            ArrayList<String> mutilDimName = new ArrayList<String>();
            for (Map.Entry<String, String> assist : itemImportVO.getAssistMap().entrySet()) {
                String columnName = assist.getKey();
                String dimCode = (String)dimTitleMap.get(columnName);
                if (!ObjectUtils.isEmpty(dimMap.get(dimCode)) && (Integer)dimMap.get(dimCode) == 1 && StringUtils.isNull((String)assist.getValue())) {
                    emptyDimName.add(columnName);
                    continue;
                }
                if (StringUtils.isNull((String)assist.getValue()) || ObjectUtils.isEmpty(dimReferFieldMap.get(columnName))) continue;
                List<String> resultCode = AdjustVchrImportCheckUtil.checkDimBaseData(this.baseDataClient, (String)dimReferFieldMap.get(columnName), assist.getValue());
                assist.setValue("");
                if (resultCode.size() > 1) {
                    mutilDimName.add(columnName);
                    continue;
                }
                if (resultCode.isEmpty()) {
                    noExistDimName.add(columnName);
                    continue;
                }
                assist.setValue(resultCode.get(0));
            }
            if (!emptyDimName.isEmpty()) {
                errorCheckImportMsg.append(String.format(EMPTY_REQUIRED_DIM_MSG, startIndex + i, emptyDimName.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
            }
            if (!noExistDimName.isEmpty()) {
                errorCheckImportMsg.append(String.format("\u7b2c%1$s\u884c\u3010%2$s\u3011\u8f85\u52a9\u9879\u4e0d\u5b58\u5728\uff0c\u8bf7\u786e\u8ba4\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n", startIndex + i, noExistDimName.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
            }
            if (mutilDimName.isEmpty()) continue;
            errorCheckImportMsg.append(String.format("\u7b2c%1$s\u884c\u3010%2$s\u3011\u8f85\u52a9\u9879\u4e0d\u5b58\u5728\uff0c\u8bf7\u786e\u8ba4\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n", startIndex + i, mutilDimName.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        return errorCheckImportMsg.toString();
    }

    @Override
    public int checkOrder() {
        return 3;
    }
}

