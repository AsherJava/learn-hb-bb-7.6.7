/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.subject.impl.subject.dto.SubjectDTO
 *  com.jiuqi.common.subject.impl.subject.enums.SubjectClassEnum
 *  com.jiuqi.common.subject.impl.subject.service.SubjectService
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO
 *  com.jiuqi.dc.adjustvchr.client.service.AdjustVoucherClientService
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.dc.adjustvchr.impl.check.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.enums.SubjectClassEnum;
import com.jiuqi.common.subject.impl.subject.service.SubjectService;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO;
import com.jiuqi.dc.adjustvchr.client.service.AdjustVoucherClientService;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO;
import com.jiuqi.dc.adjustvchr.impl.check.AdjustVchrImportCheckService;
import com.jiuqi.dc.adjustvchr.impl.impexp.entity.AdjustVchrItemImportVO;
import com.jiuqi.dc.adjustvchr.impl.utils.AdjustVchrImportCheckUtil;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class ExtraFieldsCheckServiceImpl
implements AdjustVchrImportCheckService {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private AdjustVoucherClientService adjustVoucherService;
    @Autowired
    private BaseDataClient baseDataClient;
    private static final String BIZDATE = "\u4e1a\u52a1\u65e5\u671f";
    private static final String CFITEMCODE = "\u73b0\u91d1\u6d41\u91cf\u5c5e\u6027";
    private static final String EXPIREDATE = "\u5230\u671f\u65e5";
    private static final String ACCOUNTSUBJ = "accountSubj";
    private static final String RECLASSIFYSUBJ = "reclassifySubj";
    private static final String EMPTY_BIZDATE_MSG = "\u7b2c%1$s\u884c\u79d1\u76ee\u6d89\u53ca\u8d26\u9f84\uff0c\u8bf7\u586b\u5199\u4e1a\u52a1\u65e5\u671f\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String EMPTY_EXPIREDATE_MSG = "\u7b2c%1$s\u884c\u6d89\u53ca\u5230\u671f\u65e5\u91cd\u5206\u7c7b\uff0c\u8bf7\u586b\u5199\u5230\u671f\u65e5\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String ERROR_BIZDATE_TYPE_MSG = "\u7b2c%1$s\u884c\u4e1a\u52a1\u65e5\u671f\u6570\u636e\u7c7b\u578b\u4e0d\u6b63\u786e\uff0c\u8bf7\u53c2\u8003\u8bf4\u660e\u793a\u4f8b\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String ERROR_EXPIREDATE_TYPE_MSG = "\u7b2c%1$s\u884c\u5230\u671f\u65e5\u6570\u636e\u7c7b\u578b\u4e0d\u6b63\u786e\uff0c\u8bf7\u53c2\u8003\u8bf4\u660e\u793a\u4f8b\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String NO_EXIST_CFITEM_MSG = "\u7b2c%1$s\u884c\u73b0\u91d1\u6d41\u91cf\u6570\u636e\u4e0d\u5b58\u5728\uff0c\u8bf7\u786e\u8ba4\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String MULTI_CFITEM_MSG = "\u7b2c%1$s\u884c\u67e5\u8be2\u5230\u591a\u4e2a\u73b0\u91d1\u6d41\u91cf\u6570\u636e\uff0c\u8bf7\u786e\u8ba4\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String NO_CFITEM_MSG = "\u7b2c%1$s\u884c\u79d1\u76ee\u5c5e\u4e8e\u73b0\u91d1\u6d41\u91cf\u7c7b\uff0c\u73b0\u6d41\u5c5e\u6027\u5217\u4e0d\u80fd\u586b\u5199\uff0c\u8bf7\u4fee\u6539\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";

    @Override
    public String checkImportData(int startIndex, List<AdjustVchrItemImportVO> importData, AdjustVoucherQueryDTO importParam, AdjustVchrSysOptionVO optionVO, boolean convertEnable) {
        if (CollectionUtils.isEmpty((Collection)optionVO.getExtraFields())) {
            return "";
        }
        HashSet<String> extraFieldSet = new HashSet<String>();
        for (String extraField : optionVO.getExtraFields()) {
            if ("BIZDATE".equals(extraField)) {
                extraFieldSet.add(BIZDATE);
                continue;
            }
            if ("CFITEMCODE".equals(extraField)) {
                extraFieldSet.add(CFITEMCODE);
                continue;
            }
            if (!"EXPIREDATE".equals(extraField)) continue;
            extraFieldSet.add(EXPIREDATE);
        }
        Map extraSubjMap = this.adjustVoucherService.getAccountAndReclassifly();
        Map<String, SubjectDTO> subjectDTOMap = this.subjectService.list().parallelStream().collect(Collectors.toMap(BaseDataDO::getCode, item -> item, (k1, k2) -> k2));
        StringBuilder errorCheckImportMsg = new StringBuilder();
        ArrayList<Integer> errorBizDateTypeIndex = new ArrayList<Integer>();
        ArrayList<Integer> errorExpiredateTypeIndex = new ArrayList<Integer>();
        ArrayList<Integer> emptyBizDateIndex = new ArrayList<Integer>();
        ArrayList<Integer> emptyExpiredateIndex = new ArrayList<Integer>();
        ArrayList<Integer> noExistCfitemIndex = new ArrayList<Integer>();
        ArrayList<Integer> multiCfitemIndex = new ArrayList<Integer>();
        HashSet<Integer> noCfitemIndex = new HashSet<Integer>();
        for (int i = 0; i < importData.size(); ++i) {
            int rowIndex = startIndex + i;
            AdjustVchrItemImportVO itemImportVO = importData.get(i);
            String subjectCode = itemImportVO.getSubject();
            SubjectDTO subjectDTO = ObjectUtils.isEmpty(subjectDTOMap.get(subjectCode)) ? new SubjectDTO() : subjectDTOMap.get(subjectCode);
            boolean accountFlag = ((List)extraSubjMap.get(ACCOUNTSUBJ)).contains(subjectDTO.getCode());
            boolean reclassifyFlag = ((List)extraSubjMap.get(RECLASSIFYSUBJ)).contains(subjectDTO.getCode());
            if (!StringUtils.isNull((String)itemImportVO.getBizDate()) && !this.checkDateType(itemImportVO.getBizDate())) {
                errorBizDateTypeIndex.add(rowIndex);
            }
            if (!StringUtils.isNull((String)itemImportVO.getExpireDate()) && !this.checkDateType(itemImportVO.getExpireDate())) {
                errorExpiredateTypeIndex.add(rowIndex);
            }
            if (StringUtils.isNull((String)itemImportVO.getBizDate()) && accountFlag) {
                emptyBizDateIndex.add(rowIndex);
            }
            if (StringUtils.isNull((String)itemImportVO.getExpireDate()) && reclassifyFlag) {
                emptyExpiredateIndex.add(rowIndex);
            }
            if (!StringUtils.isNull((String)subjectDTO.getCode()) && SubjectClassEnum.CASH.getCode().equals(subjectDTO.getGeneralType()) && !StringUtils.isNull((String)itemImportVO.getCfitemCode())) {
                noCfitemIndex.add(rowIndex);
            }
            if (StringUtils.isNull((String)itemImportVO.getCfitemCode())) continue;
            List<String> resultCode = AdjustVchrImportCheckUtil.checkDimBaseData(this.baseDataClient, "MD_CFITEM", itemImportVO.getCfitemCode());
            if (resultCode.isEmpty()) {
                noExistCfitemIndex.add(rowIndex);
                continue;
            }
            if (resultCode.size() > 1) {
                multiCfitemIndex.add(rowIndex);
                continue;
            }
            itemImportVO.setCfitemCode(resultCode.get(0));
        }
        if (!CollectionUtils.isEmpty(emptyBizDateIndex)) {
            errorCheckImportMsg.append(String.format(EMPTY_BIZDATE_MSG, emptyBizDateIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!CollectionUtils.isEmpty(emptyExpiredateIndex)) {
            errorCheckImportMsg.append(String.format(EMPTY_EXPIREDATE_MSG, emptyExpiredateIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!CollectionUtils.isEmpty(errorBizDateTypeIndex)) {
            errorCheckImportMsg.append(String.format(ERROR_BIZDATE_TYPE_MSG, errorBizDateTypeIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!CollectionUtils.isEmpty(errorExpiredateTypeIndex)) {
            errorCheckImportMsg.append(String.format(ERROR_EXPIREDATE_TYPE_MSG, errorExpiredateTypeIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!CollectionUtils.isEmpty(noExistCfitemIndex)) {
            errorCheckImportMsg.append(String.format(NO_EXIST_CFITEM_MSG, noExistCfitemIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!CollectionUtils.isEmpty(multiCfitemIndex)) {
            errorCheckImportMsg.append(String.format(MULTI_CFITEM_MSG, multiCfitemIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!CollectionUtils.isEmpty(noCfitemIndex)) {
            errorCheckImportMsg.append(String.format(NO_CFITEM_MSG, noCfitemIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        return errorCheckImportMsg.toString();
    }

    @Override
    public int checkOrder() {
        return 4;
    }

    private boolean checkDateType(String colValue) {
        colValue = colValue.replace("\\/", "-");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sdf.parse(colValue);
        }
        catch (ParseException e) {
            return false;
        }
        return true;
    }
}

