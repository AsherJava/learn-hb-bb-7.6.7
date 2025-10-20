/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.context.AnalysisContext
 *  com.alibaba.excel.event.AnalysisEventListener
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.dimension.internal.service.DimensionManageService
 *  com.jiuqi.gcreport.dimension.vo.DimensionQueryVO
 *  com.jiuqi.va.basedata.service.BaseDataService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.json.JSONObject
 */
package com.jiuqi.common.subject.impl.subject.expimp;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.enums.BooleanValEnum;
import com.jiuqi.common.subject.impl.subject.enums.OrientEnum;
import com.jiuqi.common.subject.impl.subject.exception.CheckRuntimeException;
import com.jiuqi.common.subject.impl.subject.expimp.intf.ISubjectExpImpFieldDefine;
import com.jiuqi.common.subject.impl.subject.expimp.intf.impl.SubjectFieldDefineHolder;
import com.jiuqi.common.subject.impl.subject.expimp.intf.impl.SubjectImpExpDefaultColumnEnum;
import com.jiuqi.common.subject.impl.subject.expimp.intf.impl.SubjectImpParsePojo;
import com.jiuqi.gcreport.dimension.internal.service.DimensionManageService;
import com.jiuqi.gcreport.dimension.vo.DimensionQueryVO;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.springframework.util.ObjectUtils;

public class SubjectImpExcelListener
extends AnalysisEventListener<LinkedHashMap<Integer, String>> {
    private List<SubjectDTO> impParseData;
    private Integer totalCount;
    private SubjectFieldDefineHolder fieldDefineHolder;
    private SubjectImpParsePojo impParseVo;
    private Map<String, BaseDataDO> generalTypeMap;
    private Map<String, BaseDataDO> currencyMap;
    private Map<String, BaseDataDO> currencyCodeMap;
    private Map<String, DimensionQueryVO> assistDimMap;
    private static final String FN_DELIMITER_COMMA_CN = "\uff0c";
    private static final Map<String, String> currencyTypeMap = new HashMap<String, String>();

    public SubjectImpExcelListener(SubjectFieldDefineHolder fieldDefineHolder) {
        this.fieldDefineHolder = fieldDefineHolder;
        this.impParseVo = new SubjectImpParsePojo();
        BaseDataService baseDataService = (BaseDataService)ApplicationContextRegister.getBean(BaseDataService.class);
        BaseDataDTO generalTypeCondi = new BaseDataDTO();
        generalTypeCondi.setTableName("MD_ACCTSUBJECTCLASS");
        this.generalTypeMap = baseDataService.list(generalTypeCondi).getRows().stream().collect(Collectors.toMap(BaseDataDO::getName, item -> item, (k1, k2) -> k2));
        BaseDataDTO currencyCondi = new BaseDataDTO();
        currencyCondi.setTableName("MD_CURRENCY");
        List currencyList = baseDataService.list(currencyCondi).getRows();
        this.currencyMap = currencyList.stream().collect(Collectors.toMap(BaseDataDO::getName, item -> item, (k1, k2) -> k2));
        this.currencyCodeMap = currencyList.stream().collect(Collectors.toMap(BaseDataDO::getCode, item -> item, (k1, k2) -> k2));
        this.assistDimMap = ((DimensionManageService)ApplicationContextRegister.getBean(DimensionManageService.class)).listDimensions().stream().collect(Collectors.toMap(DimensionQueryVO::getTitle, item -> item, (k1, k2) -> k2));
    }

    public void clear() {
        if (this.impParseData != null) {
            this.impParseData.clear();
            this.impParseData = null;
        }
    }

    public void invoke(LinkedHashMap<Integer, String> data, AnalysisContext context) {
        SubjectDTO row;
        if (context.readSheetHolder().getSheetNo() != 0 || context.readRowHolder().getRowIndex() == 0) {
            return;
        }
        if (this.totalCount == null) {
            this.totalCount = context.readSheetHolder().getApproximateTotalRowNumber();
            this.impParseData = new ArrayList<SubjectDTO>(this.totalCount);
        }
        if ((row = this.parseRowData(context.readRowHolder().getRowIndex(), data)) == null) {
            return;
        }
        this.impParseData.add(row);
    }

    private SubjectDTO parseRowData(Integer idx, LinkedHashMap<Integer, String> data) {
        if (data.isEmpty()) {
            this.impParseVo.putFailData(idx + 1, "\u5bfc\u5165\u7684\u6570\u636e\u4e3a\u7a7a");
            return null;
        }
        SubjectDTO row = new SubjectDTO();
        String currencyType = "";
        HashMap assistMap = CollectionUtils.newHashMap();
        for (int colIdx = 0; colIdx < this.fieldDefineHolder.getDefineList().size(); ++colIdx) {
            ISubjectExpImpFieldDefine fieldDefine = this.fieldDefineHolder.getDefineList().get(colIdx);
            try {
                if (fieldDefine.isRequired() && StringUtils.isEmpty((String)data.get(colIdx))) {
                    this.impParseVo.putFailData(idx + 1, String.format("%1$s\u5fc5\u586b", fieldDefine.getName()));
                    return null;
                }
                if (fieldDefine.getCode().equals(SubjectImpExpDefaultColumnEnum.CODE.getCode())) {
                    row.put(fieldDefine.getCode(), (Object)data.get(colIdx).replaceAll("\\s", ""));
                    continue;
                }
                if (fieldDefine.getCode().equals(SubjectImpExpDefaultColumnEnum.PARENTCODE.getCode())) {
                    row.put(fieldDefine.getCode(), (Object)(StringUtils.isEmpty((String)data.get(colIdx)) ? "-" : data.get(colIdx)));
                    continue;
                }
                if (fieldDefine.getCode().equals(SubjectImpExpDefaultColumnEnum.ORIENT.getCode())) {
                    row.put(fieldDefine.getCode(), (Object)this.parseOrient(data.get(colIdx)));
                    continue;
                }
                if (fieldDefine.getCode().equals(SubjectImpExpDefaultColumnEnum.GENERALTYPE.getCode())) {
                    row.put(fieldDefine.getCode(), (Object)this.parseGeneralType(data.get(colIdx)));
                    continue;
                }
                if (fieldDefine.getCode().equals(SubjectImpExpDefaultColumnEnum.REQUIRED_ASSIST.getCode())) {
                    Set<String> requiredAssist = this.parseAssist(data.get(colIdx), BooleanValEnum.YES.getCode()).keySet();
                    Set<String> noRequiredAssist = this.parseAssist(data.get(colIdx + 1), BooleanValEnum.YES.getCode()).keySet();
                    requiredAssist.retainAll(noRequiredAssist);
                    if (!requiredAssist.isEmpty()) {
                        throw new IllegalArgumentException("\u5fc5\u586b\u8f85\u52a9\u9879\u548c\u975e\u5fc5\u586b\u8f85\u52a9\u9879\u4e0d\u80fd\u5b58\u5728\u4e00\u81f4\u7684\u8f85\u52a9\u9879");
                    }
                }
                if (fieldDefine.getCode().equals(SubjectImpExpDefaultColumnEnum.REQUIRED_ASSIST.getCode())) {
                    assistMap.putAll(this.parseAssist(data.get(colIdx), BooleanValEnum.YES.getCode()));
                    continue;
                }
                if (fieldDefine.getCode().equals(SubjectImpExpDefaultColumnEnum.NON_REQUIRED_ASSIST.getCode())) {
                    assistMap.putAll(this.parseAssist(data.get(colIdx), BooleanValEnum.NO.getCode()));
                    row.put("asstypeMap", (Object)assistMap);
                    continue;
                }
                row.put(fieldDefine.getCode(), (Object)data.get(colIdx));
                continue;
            }
            catch (Exception e) {
                this.impParseVo.putFailData(idx + 1, e.getMessage());
                return null;
            }
        }
        if (!ObjectUtils.isEmpty(assistMap)) {
            row.put("asstype", (Object)String.valueOf(new JSONObject((Map)assistMap)));
        }
        row.put("IDX", (Object)(idx + 1));
        this.impParseVo.success();
        return row;
    }

    private Integer parseOrient(String orientStr) {
        OrientEnum orient = OrientEnum.fromName(orientStr);
        if (orient == null) {
            throw new CheckRuntimeException(String.format("\u79d1\u76ee\u65b9\u5411\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", orientStr));
        }
        return orient.getCode();
    }

    private String parseGeneralType(String generalType) {
        if (!this.generalTypeMap.containsKey(generalType)) {
            throw new CheckRuntimeException(String.format("\u79d1\u76ee\u5927\u7c7b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", generalType));
        }
        return this.generalTypeMap.get(generalType).getCode();
    }

    private String parseCurrencyType(String currencyType) {
        if (StringUtils.isEmpty((String)currencyType)) {
            currencyType = "\u672c\u4f4d\u5e01";
        }
        if (!currencyTypeMap.containsKey(currencyType)) {
            throw new CheckRuntimeException(String.format("\u5e01\u79cd\u7c7b\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", currencyType));
        }
        return currencyTypeMap.get(currencyType);
    }

    private String parseCurrency(String currencyType, String currency) {
        if ("ALL".equals(currencyType) || "BWB".equals(currencyType)) {
            return currencyType;
        }
        if (StringUtils.isEmpty((String)currency)) {
            throw new CheckRuntimeException("\u6307\u5b9a\u5e01\u79cd\u65f6\uff0c\u5e01\u79cd\u4e0d\u80fd\u4e3a\u7a7a");
        }
        currency = currency.replace(FN_DELIMITER_COMMA_CN, ",");
        String[] currArr = currency.split(",");
        StringBuilder currencyBuilder = new StringBuilder();
        for (String curr : currArr) {
            if (!this.currencyMap.containsKey(curr) && !this.currencyCodeMap.containsKey(curr)) {
                throw new CheckRuntimeException(String.format("\u5e01\u79cd\u3010%s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", curr));
            }
            currencyBuilder.append(this.currencyMap.get(curr) == null ? this.currencyCodeMap.get(curr).getCode() : this.currencyMap.get(curr).getCode()).append("/");
        }
        if (currencyBuilder.length() > 0) {
            currencyBuilder.delete(currencyBuilder.length() - 1, currencyBuilder.length());
        }
        return currencyBuilder.toString();
    }

    private Map<String, Integer> parseAssist(String assStr, Integer required) {
        if (StringUtils.isEmpty((String)assStr)) {
            return CollectionUtils.newHashMap();
        }
        assStr = assStr.replace(FN_DELIMITER_COMMA_CN, ",");
        String[] assistArr = assStr.split(",");
        HashMap<String, Integer> assistMap = new HashMap<String, Integer>(assistArr.length);
        for (String assist : assistArr) {
            if (!this.assistDimMap.containsKey(assist)) {
                throw new CheckRuntimeException(String.format("\u8f85\u52a9\u9879\u3010%s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", assist));
            }
            assistMap.put(this.assistDimMap.get(assist).getCode(), required);
        }
        return assistMap;
    }

    public void doAfterAllAnalysed(AnalysisContext context) {
    }

    public SubjectImpParsePojo getImpParseVo() {
        return this.impParseVo;
    }

    public List<SubjectDTO> getImpParseData() {
        return this.impParseData;
    }

    static {
        currencyTypeMap.put("\u4efb\u610f\u5e01\u79cd", "ALL");
        currencyTypeMap.put("\u672c\u4f4d\u5e01", "BWB");
        currencyTypeMap.put("\u6307\u5b9a\u5e01\u79cd", "");
    }
}

