/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.EasyExcel
 *  com.alibaba.excel.read.builder.ExcelReaderBuilder
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.systemparam.util.EntParamInitFileUtil
 */
package com.jiuqi.gcreport.reportparam.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.systemparam.util.EntParamInitFileUtil;
import com.jiuqi.gcreport.reportparam.dto.GcReportParamInfoDTO;
import com.jiuqi.gcreport.reportparam.enums.GcReportParamType;
import com.jiuqi.gcreport.reportparam.vo.GcReportParamInitVO;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GcReportParamInfoUtils {
    private static Logger logger = LoggerFactory.getLogger(GcReportParamInfoUtils.class);
    private static final String REPORT_INIT_INDEX_FILE_URL = "report-param-init/reportInitIndex.xlsx";
    private List<GcReportParamInfoDTO> gcReportParamInfoList;
    private List<GcReportParamInitVO> gcReportParamInitVOList;
    private Map<String, List<GcReportParamInfoDTO>> paramNameToInfoMap = new ConcurrentHashMap<String, List<GcReportParamInfoDTO>>();

    private void init() {
        List list;
        if (!CollectionUtils.isEmpty(this.gcReportParamInfoList)) {
            return;
        }
        String fullFileUrl = EntParamInitFileUtil.getFullFilePath((String)REPORT_INIT_INDEX_FILE_URL, (String)"gcreport");
        if (StringUtils.isEmpty((String)fullFileUrl)) {
            logger.info("\u672a\u627e\u5230\u62a5\u8868\u53c2\u6570\u521d\u59cb\u5316\u7d22\u5f15\u6587\u4ef6\uff0c\u8df3\u8fc7\u62a5\u8868\u53c2\u6570\u521d\u59cb\u5316\u6587\u4ef6\u5904\u7406\uff01");
            return;
        }
        try (InputStream inputStream = EntParamInitFileUtil.getResourceInputStream((String)fullFileUrl);){
            list = ((ExcelReaderBuilder)EasyExcel.read((InputStream)inputStream).head(GcReportParamInfoDTO.class)).sheet().doReadSync();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        if (CollectionUtils.isEmpty((Collection)list)) {
            return;
        }
        this.gcReportParamInfoList = list;
        this.gcReportParamInitVOList = new ArrayList<GcReportParamInitVO>();
        this.paramNameToInfoMap.clear();
        for (GcReportParamInfoDTO dto : list) {
            if (!this.paramNameToInfoMap.containsKey(dto.getParamName())) {
                this.paramNameToInfoMap.put(dto.getParamName(), new ArrayList());
                GcReportParamInitVO vo = new GcReportParamInitVO();
                vo.setName(dto.getParamName());
                vo.setType(GcReportParamType.getByName(dto.getParamType()).getCode());
                vo.setDescription(dto.getDescription());
                this.gcReportParamInitVOList.add(vo);
            }
            this.paramNameToInfoMap.get(dto.getParamName()).add(dto);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Map<String, List<GcReportParamInfoDTO>> getParamNameToInfoMap() {
        if (this.paramNameToInfoMap.isEmpty()) {
            GcReportParamInfoUtils gcReportParamInfoUtils = this;
            synchronized (gcReportParamInfoUtils) {
                this.init();
            }
        }
        return this.paramNameToInfoMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<GcReportParamInitVO> getParamInitVOList() {
        if (CollectionUtils.isEmpty(this.gcReportParamInitVOList)) {
            GcReportParamInfoUtils gcReportParamInfoUtils = this;
            synchronized (gcReportParamInfoUtils) {
                this.init();
            }
        }
        return this.gcReportParamInitVOList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<GcReportParamInfoDTO> getGcReportParamInfoList() {
        if (CollectionUtils.isEmpty(this.gcReportParamInfoList)) {
            GcReportParamInfoUtils gcReportParamInfoUtils = this;
            synchronized (gcReportParamInfoUtils) {
                this.init();
            }
        }
        return this.gcReportParamInfoList;
    }
}

