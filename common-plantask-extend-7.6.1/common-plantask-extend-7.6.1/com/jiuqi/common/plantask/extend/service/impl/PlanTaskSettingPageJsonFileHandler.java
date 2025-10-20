/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.common.plantask.extend.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.plantask.extend.enumerate.WindowTypeEnum;
import com.jiuqi.common.plantask.extend.service.PlanTaskSettingPageHandler;
import com.jiuqi.common.plantask.extend.vo.ColParamVO;
import com.jiuqi.common.plantask.extend.vo.RowParamVO;
import com.jiuqi.common.plantask.extend.vo.SettingPageTemplateVO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

@Component
public class PlanTaskSettingPageJsonFileHandler
implements PlanTaskSettingPageHandler {
    private final Logger logger = LoggerFactory.getLogger(PlanTaskSettingPageJsonFileHandler.class);

    @Override
    public Map<String, SettingPageTemplateVO> getSettingPageTemplateMap() {
        HashMap<String, SettingPageTemplateVO> result = new HashMap<String, SettingPageTemplateVO>();
        Resource[] resources = null;
        try {
            resources = new PathMatchingResourcePatternResolver().getResources("classpath*:settingpage/**.json");
        }
        catch (IOException e) {
            this.logger.error("\u8ba1\u5212\u4efb\u52a1\u9ad8\u7ea7\u754c\u9762\u6a21\u7248\u4fe1\u606f\u521d\u59cb\u5316\u5931\u8d25:", e);
        }
        if (resources == null || resources.length == 0) {
            return result;
        }
        for (Resource resource : resources) {
            String filename = resource.getFilename();
            String templateCode = filename.substring(0, filename.length() - 5);
            if (result.containsKey(templateCode)) {
                this.logger.error("\u8ba1\u5212\u4efb\u52a1\u9ad8\u7ea7\u754c\u9762\u6a21\u7248\u4fe1\u606f\u521d\u59cb\u5316\u5f02\u5e38\uff0c\u53d1\u73b0\u91cd\u590d\u7684\u6a21\u677f\u4ee3\u7801\u3010{}\u3011", (Object)templateCode);
                continue;
            }
            try {
                List colParamVOList;
                String fileString = this.fileToStr(resource.getInputStream());
                if (StringUtils.isEmpty((String)fileString) || CollectionUtils.isEmpty((Collection)(colParamVOList = (List)JsonUtils.readValue((String)fileString, (TypeReference)new TypeReference<List<ColParamVO>>(){})))) continue;
                SettingPageTemplateVO settingPageTemplateVO = this.colParamListToSettingPageTemplate(colParamVOList);
                result.put(templateCode, settingPageTemplateVO);
            }
            catch (IOException e) {
                this.logger.error("\u8ba1\u5212\u4efb\u52a1\u9ad8\u7ea7\u754c\u9762\u6a21\u7248\u4fe1\u606f\u521d\u59cb\u5316,\u5904\u7406[{}]\u6587\u4ef6\u53d1\u751fIO\u5f02\u5e38:", (Object)resource.getFilename(), (Object)e);
            }
            catch (Exception e) {
                this.logger.error("\u8ba1\u5212\u4efb\u52a1\u9ad8\u7ea7\u754c\u9762\u6a21\u7248\u4fe1\u606f\u521d\u59cb\u5316,\u5904\u7406[{}]\u6587\u4ef6\u53d1\u751f\u5f02\u5e38:", (Object)resource.getFilename(), (Object)e);
            }
        }
        return result;
    }

    private SettingPageTemplateVO colParamListToSettingPageTemplate(List<ColParamVO> colParamVOList) {
        HashMap<Integer, RowParamVO> rowParamMap = new HashMap<Integer, RowParamVO>();
        ArrayList<ColParamVO> rowSpanColParamVOList = new ArrayList<ColParamVO>();
        for (ColParamVO colParamVO : colParamVOList) {
            if (!rowParamMap.containsKey(colParamVO.getRowIndex())) {
                rowParamMap.put(colParamVO.getRowIndex(), new RowParamVO(colParamVO.getRowIndex()));
            }
            ((RowParamVO)rowParamMap.get(colParamVO.getRowIndex())).addColParams(colParamVO);
            if (colParamVO.getRowSpan() <= 1) continue;
            rowSpanColParamVOList.add(colParamVO);
        }
        this.calcRowSpanTotalColSpan(rowSpanColParamVOList, rowParamMap);
        ArrayList<RowParamVO> rowParamVOList = new ArrayList<RowParamVO>();
        int maxRowTotalColSpan = 0;
        for (Integer rowIndex : rowParamMap.keySet()) {
            RowParamVO rowParamVO = (RowParamVO)rowParamMap.get(rowIndex);
            rowParamVO.getColParams().sort(Comparator.comparing(ColParamVO::getColIndex));
            rowParamVOList.add(rowParamVO);
            if (rowParamVO.getTotalcolSpan() <= maxRowTotalColSpan) continue;
            maxRowTotalColSpan = rowParamVO.getTotalcolSpan();
        }
        rowParamVOList.sort(Comparator.comparing(RowParamVO::getRowIndex));
        SettingPageTemplateVO settingPageTemplateVO = new SettingPageTemplateVO();
        settingPageTemplateVO.setRowParams(rowParamVOList);
        WindowTypeEnum windowTypeEnum = rowParamVOList.size() <= 5 && maxRowTotalColSpan <= 2 ? WindowTypeEnum.MINI : (colParamVOList.size() <= 10 && maxRowTotalColSpan <= 3 ? WindowTypeEnum.MEDIUM : WindowTypeEnum.MAX);
        settingPageTemplateVO.setWindowType(windowTypeEnum.getType());
        return settingPageTemplateVO;
    }

    private void calcRowSpanTotalColSpan(List<ColParamVO> rowSpanColParamVOList, Map<Integer, RowParamVO> rowParamMap) {
        for (ColParamVO colParamVO : rowSpanColParamVOList) {
            RowParamVO mergeRowParamVO = rowParamMap.get(colParamVO.getRowIndex());
            int maxColSpan = 0;
            for (int i = 1; i < colParamVO.getRowSpan(); ++i) {
                RowParamVO rowParamVO = rowParamMap.get(colParamVO.getRowIndex() + i);
                if (rowParamVO == null) continue;
                int currentRowTotalColSpan = rowParamVO.getTotalcolSpan() + colParamVO.getColSpan();
                if (currentRowTotalColSpan > maxColSpan) {
                    maxColSpan = currentRowTotalColSpan;
                }
                rowParamVO.setTotalcolSpan(currentRowTotalColSpan);
            }
            if (mergeRowParamVO.getTotalcolSpan() >= maxColSpan) continue;
            mergeRowParamVO.setTotalcolSpan(maxColSpan);
        }
    }

    private String fileToStr(InputStream fileInputStream) throws IOException {
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
        StringBuilder result = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        bufferedReader.close();
        return result.toString();
    }
}

