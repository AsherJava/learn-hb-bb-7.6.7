/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatRegionConfigData
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExportContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatRegionConfigData;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.ImportInnerColumnUtil;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class ExcelExplainInfo {
    public static List<String[]> listExportExcelExplainInfo(FetchSettingExportContext fetchSettingExportContext) {
        ArrayList<String[]> titles = new ArrayList<String[]>();
        titles.add(new String[]{"\u586b\u62a5\u9879\u53ca\u586b\u62a5\u5185\u5bb9\u8bf4\u660e", "\u586b\u62a5\u9879\u53ca\u586b\u62a5\u5185\u5bb9\u8bf4\u660e"});
        titles.add(new String[]{"\u586b\u62a5\u9879", "\u586b\u62a5\u5185\u5bb9\u53ca\u8981\u6c42"});
        titles.add(new String[]{"\u6307\u6807\u4ee3\u7801", "\u5bfc\u51fa\u6587\u4ef6\u6216\u5bfc\u51fa\u6a21\u677f\u4e2d\u5305\u542b\u5bfc\u51fa\u62a5\u8868\u7684\u6240\u6709\u6307\u6807\uff0c\u5982\u540c\u4e00\u4e2a\u6307\u6807\u9700\u914d\u7f6e\u591a\u4e2a\u4e1a\u52a1\u6a21\u578b\uff0c\u90a3\u4e48\u9700\u624b\u5de5\u63d2\u884c"});
        titles.add(new String[]{"\u6307\u6807\u540d\u79f0", "\u540c\u6307\u6807\u4ee3\u7801\u5217"});
        titles.add(new String[]{"\u8fd0\u7b97\u7b26", "\u5fc5\u586b\uff0c\u4e0b\u62c9\u9009\u62e9\uff0c\u5355\u9009\uff0c\u53ef\u9009+\u548c-"});
        titles.add(new String[]{"\u4e1a\u52a1\u6a21\u578b", "\u5fc5\u586b\uff0c\u4e0b\u62c9\u9009\u62e9\uff0c\u5355\u9009\uff0c\u53ef\u9009\u7cfb\u7edf\u4e2d\u914d\u7f6e\u7684\u6240\u6709\u4e1a\u52a1\u6a21\u578b\uff0c\u5982\u679c\u4e0b\u62c9\u5217\u8868\u4e2d\u7f3a\u5c11\u4e1a\u52a1\u6240\u9700\u8981\u7684\u4e1a\u52a1\u6a21\u578b\uff0c\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u3002"});
        titles.add(new String[]{"\u79d1\u76ee", "\u5f55\u5165\u6838\u7b97\u79d1\u76ee\u4ee3\u7801\uff0c\u591a\u4e2a\u79d1\u76ee\u95f4\u7528\u82f1\u6587\u72b6\u6001\u9017\u53f7\u9694\u5f00\uff0c\u8303\u56f4\u95f4\u7528\u82f1\u6587\u72b6\u6001\u7684\u5206\u53f7\u9694\u5f00\uff1b\n\u4e1a\u52a1\u6a21\u578b=\u79d1\u76ee\u4f59\u989d\u8868\uff0c\u79d1\u76ee\u5fc5\u586b\uff0c\u652f\u6301\u5f55\u5165\u5355\u4e2a\u3001\u591a\u4e2a\u3001\u8303\u56f4\u4e09\u79cd\u65b9\u5f0f\uff1b\u5355\u4e2a\u793a\u4f8b\u30101001\u3011\uff0c\u591a\u4e2a\u793a\u4f8b\u30101001,1002,1003\u3011\uff0c\u8303\u56f4\u793a\u4f8b\u301010011:1121\u3011\uff1b\n\u4e1a\u52a1\u6a21\u578b=\u8f85\u52a9\u4f59\u989d\u8868\uff0c\u79d1\u76ee\u5fc5\u586b\uff0c\u652f\u6301\u5f55\u5165\u5355\u4e2a\u3001\u591a\u4e2a\u3001\u8303\u56f4\u4e09\u79cd\u65b9\u5f0f\uff1b\u5355\u4e2a\u793a\u4f8b\u30101001\u3011\uff0c\u591a\u4e2a\u793a\u4f8b\u30101001,1002,1003\u3011\uff0c\u8303\u56f4\u793a\u4f8b\u301010011:1121\u3011\uff1b\n\u4e1a\u52a1\u6a21\u578b=\u4f59\u989d\u91cd\u5206\u7c7b\uff0c\u79d1\u76ee\u5fc5\u586b\uff0c\u652f\u6301\u5f55\u5165\u5355\u4e2a\uff1b\u5355\u4e2a\u793a\u4f8b\u30101001\u3011\uff1b\n\u4e1a\u52a1\u6a21\u578b=\u5230\u671f\u65e5\u91cd\u5206\u7c7b\uff0c\u79d1\u76ee\u4e0d\u5141\u8bb8\u586b\u5199\uff1b\n\u4e1a\u52a1\u6a21\u578b=\u5f80\u6765\u8d26\u9f84\uff0c\u79d1\u76ee\u5fc5\u586b\uff0c\u652f\u6301\u5f55\u5165\u5355\u4e2a\u3001\u591a\u4e2a\u3001\u8303\u56f4\u4e09\u79cd\u65b9\u5f0f\uff1b\u5355\u4e2a\u793a\u4f8b\u30101001\u3011\uff0c\u591a\u4e2a\u793a\u4f8b\u30101001,1002,1003\u3011\uff0c\u8303\u56f4\u793a\u4f8b\u301010011:1121\u3011\uff1b\n\u4e1a\u52a1\u6a21\u578b=\u73b0\u6d41\u4f59\u989d\uff0c\u79d1\u76ee\u975e\u5fc5\u586b\uff0c\u652f\u6301\u5f55\u5165\u5355\u4e2a\uff1b\u5355\u4e2a\u793a\u4f8b\u30101001\u3011\uff1b\n\u4e1a\u52a1\u6a21\u578b=\u62b5\u51cf\u4f59\u989d\uff0c\u79d1\u76ee\u5fc5\u586b\uff0c\u79d1\u76ee\u5fc5\u987b\u5f55\u5165\u591a\u4e2a\uff0c\u591a\u4e2a\u793a\u4f8b\u30101001,1002,1003\u3011\uff1b"});
        titles.add(new String[]{"\u6392\u9664\u79d1\u76ee", "\u975e\u5fc5\u586b\uff0c\u5f55\u5165\u6838\u7b97\u79d1\u76ee\u4ee3\u7801\uff0c\u652f\u6301\u5f55\u5165\u5355\u4e2a\u3001\u591a\u4e2a\uff0c\u5355\u4e2a\u793a\u4f8b\u30101001\u3011\uff0c\u591a\u4e2a\u793a\u4f8b\u30101001,1002,1003\u3011\n\u4e1a\u52a1\u6a21\u578b=\u79d1\u76ee\u4f59\u989d\u8868\u3001\u8f85\u52a9\u4f59\u989d\u8868\u8868\u3001\u4f59\u989d\u91cd\u5206\u7c7b\u3001\u62b5\u51cf\u4f59\u989d\uff0c\u652f\u6301\u586b\u5199\u6392\u9664\u79d1\u76ee\uff1b\n\u4e1a\u52a1\u6a21\u578b=\u5230\u671f\u65e5\u91cd\u5206\u7c7b\u3001\u5f80\u6765\u8d26\u9f84\u3001\u73b0\u6d41\u4f59\u989d\uff0c\u4e0d\u652f\u6301\u586b\u5199\u6392\u9664\u79d1\u76ee\uff1b"});
        titles.add(new String[]{"\u91cd\u5206\u7c7b/\u62b5\u51cf\u4f59\u989d--\u91cd\u5206\u7c7b/\u62b5\u51cf\u7ef4\u5ea6", "\u4e1a\u52a1\u6a21\u578b=\u4f59\u989d\u91cd\u5206\u7c7b\u3001\u62b5\u51cf\u4f59\u989d\uff0c\u5fc5\u586b\uff0c\u5176\u4f59\u4e1a\u52a1\u6a21\u578b\u4e0d\u5141\u8bb8\u586b\u5199\uff1b\n\u4e0b\u62c9\u9009\u62e9\uff0c\u652f\u6301\u5f55\u5165\u5355\u4e2a\u3001\u591a\u4e2a\uff1b"});
        titles.add(new String[]{"\u91cd\u5206\u7c7b/\u62b5\u51cf\u4f59\u989d--\u91cd\u5206\u7c7b\u6c47\u603b\u7c7b\u578b", "\u4e1a\u52a1\u6a21\u578b=\u4f59\u989d\u91cd\u5206\u7c7b\uff0c\u5fc5\u586b\uff0c\u5176\u4f59\u4e1a\u52a1\u6a21\u578b\u4e0d\u5141\u8bb8\u586b\u5199\uff0c\u4e0b\u62c9\u9009\u62e9\uff0c\u53ef\u9009\u62e9\u660e\u7ec6\u548c\u975e\u660e\u7ec6\uff1b"});
        titles.add(new String[]{"\u5230\u671f\u65e5\u91cd\u5206\u7c7b--\u91cd\u5206\u7c7b\u540e\u79d1\u76ee", "\u5fc5\u586b\uff0c\u5f55\u5165\u6838\u7b97\u79d1\u76ee\u4ee3\u7801\uff0c\u4ec5\u80fd\u5f55\u5165\u5355\u4e2a\u79d1\u76ee\u4ee3\u7801"});
        titles.add(new String[]{"\u5230\u671f\u65e5\u91cd\u5206\u7c7b--\u91cd\u5206\u7c7b\u524d\u79d1\u76ee", "\u975e\u5fc5\u586b\uff0c\u5f55\u5165\u6838\u7b97\u79d1\u76ee\u4ee3\u7801\uff0c\u4ec5\u80fd\u5f55\u5165\u5355\u4e2a\u79d1\u76ee\u4ee3\u7801"});
        titles.add(new String[]{"\u5f80\u6765\u8d26\u9f84--\u533a\u95f4\u7c7b\u578b", "\u4e1a\u52a1\u6a21\u578b=\u62b5\u51cf\u4f59\u989d\uff0c\u5fc5\u586b\uff0c\u5176\u4f59\u4e1a\u52a1\u6a21\u578b\u4e0d\u5141\u8bb8\u586b\u5199\uff1b\n\u4e0b\u62c9\u9009\u62e9\uff0c\u53ef\u9009\u62e9\u6708\u6216\u5e74\uff1b"});
        titles.add(new String[]{"\u5f80\u6765\u8d26\u9f84--\u8d77\u59cb\u533a\u95f4", "\u4e1a\u52a1\u6a21\u578b=\u62b5\u51cf\u4f59\u989d\uff0c\u5fc5\u586b\uff0c\u5176\u4f59\u4e1a\u52a1\u6a21\u578b\u4e0d\u5141\u8bb8\u586b\u5199\uff1b\n\u624b\u5de5\u5f55\u5165\u6570\u503c\uff0c\u59823/6/9\u7b49\uff1b"});
        titles.add(new String[]{"\u5f80\u6765\u8d26\u9f84--\u622a\u6b62\u533a\u95f4", "\u4e1a\u52a1\u6a21\u578b=\u62b5\u51cf\u4f59\u989d\uff0c\u5fc5\u586b\uff0c\u5176\u4f59\u4e1a\u52a1\u6a21\u578b\u4e0d\u5141\u8bb8\u586b\u5199\uff1b\n\u624b\u5de5\u5f55\u5165\u6570\u503c\uff0c\u59823/6/9\u7b49\uff1b"});
        titles.add(new String[]{"\u6307\u5b9a\u5e74\u5ea6", "\u975e\u5fc5\u586b\uff0c\u624b\u5de5\u5f55\u5165\u5e74\u5ea6"});
        titles.add(new String[]{"\u6307\u5b9a\u671f\u95f4", "\u975e\u5fc5\u586b\uff0c\u624b\u5de5\u5f55\u5165\u671f\u95f4"});
        titles.add(new String[]{"\u6307\u5b9a\u5e01\u79cd", "\u975e\u5fc5\u586b\uff0c\u624b\u5de5\u5f55\u5165\u5e01\u79cd\u4ee3\u7801"});
        titles.add(new String[]{"\u73b0\u6d41\u9879\u76ee", "\u4e1a\u52a1\u6a21\u578b=\u73b0\u6d41\u4f59\u989d\uff0c\u5fc5\u586b\uff0c\u5176\u4f59\u4e1a\u52a1\u6a21\u578b\u4e0d\u5141\u8bb8\u586b\u5199\uff1b\n\u624b\u5de5\u5f55\u5165\u73b0\u6d41\u9879\u76ee\u4ee3\u7801\uff0c\u652f\u6301\u5f55\u5165\u5355\u4e2a\u3001\u591a\u4e2a\u3001\u8303\u56f4\u4e09\u79cd\u65b9\u5f0f\uff0c\u5355\u4e2a\u793a\u4f8b\u3010XL01\u3011\uff0c\u591a\u4e2a\u793a\u4f8b\u3010XL01,XL02,XL03\u3011\uff0c\u8303\u56f4\u793a\u4f8b\u3010XL11:XL21\u3011\uff1b"});
        titles.add(new String[]{"\u8f85\u52a9\u7ef4\u5ea6--\u8f85\u52a9\u9879", "\u624b\u5de5\u5f55\u5165\u8f85\u52a9\u9879\u4ee3\u7801\uff0c\u4ec5\u652f\u6301\u5f55\u5165\u5355\u4e2a\uff1b\n\u6839\u636e\u9009\u62e9\u7684\u4e1a\u52a1\u6a21\u578b\u586b\u5199\u8f85\u52a9\u7ef4\u5ea6\uff0c\u5982\u4e1a\u52a1\u6a21\u578b\u4e3a\u5ba2\u5546\u8f85\u52a9\u4f59\u989d\u5219\u5ba2\u5546\u8f85\u52a9\u5fc5\u586b\uff0c\u5982\u4e1a\u52a1\u6a21\u578b\u4e3a\u9879\u76ee\u8f85\u52a9\u4f59\u989d\u5219\u9879\u76ee\u8f85\u52a9\u5fc5\u586b\uff1b"});
        titles.add(new String[]{"\u8f85\u52a9\u7ef4\u5ea6--\u5339\u914d\u89c4\u5219", "\u4e0b\u62c9\u9009\u62e9\uff0c\u53ef\u9009\u7b49\u4e8e\u6216\u6a21\u7cca\u5339\u914d\uff1b"});
        titles.addAll(ExcelExplainInfo.buildBlankTitle(4));
        titles.add(new String[]{"\u4e1a\u52a1\u6a21\u578b\u4e0e\u53d6\u6570\u7c7b\u578b\u5bf9\u5e94\u5173\u7cfb", "\u4e1a\u52a1\u6a21\u578b\u4e0e\u53d6\u6570\u7c7b\u578b\u5bf9\u5e94\u5173\u7cfb"});
        titles.add(new String[]{"\u4e1a\u52a1\u6a21\u578b", "\u53d6\u6570\u7c7b\u578b"});
        for (BizModelDTO bizModelDTO2 : fetchSettingExportContext.getBizModels()) {
            titles.add(new String[]{bizModelDTO2.getName(), ImportInnerColumnUtil.getBizModelFieldName(bizModelDTO2)});
        }
        titles.addAll(ExcelExplainInfo.buildBlankTitle(2));
        titles.add(new String[]{"\u53d6\u6570\u89c4\u5219\u914d\u7f6e\u793a\u4f8b", "\u53d6\u6570\u89c4\u5219\u914d\u7f6e\u793a\u4f8b"});
        titles.add(new String[]{"1\u3001\u56fa\u5b9a\u8868\u914d\u7f6e\u793a\u4f8b\uff08\u4e0d\u540c\u4e1a\u52a1\u6a21\u578b\u6240\u9700\u8981\u586b\u5199\u7684\u5185\u5bb9\u6709\u6240\u4e0d\u540c\uff0c\u4e0b\u8868\u4e2d\u7f6e\u7070\u5355\u5143\u683c\u65e0\u9700\u586b\u5199\uff0c\u4e3a\u7a7a\u6839\u636e\u5b9e\u9645\u60c5\u51b5\u9009\u586b\uff09"});
        titles.addAll(ExcelExplainInfo.buildFixExampleConfigTitle());
        titles.add(new String[]{"QE42_NHNKB01[QE42_NHNKB01_A01]", "\u8d27\u5e01\u8d44\u91d1 \u671f\u672b\u91d1\u989d", "+", "\u79d1\u76ee\u4f59\u989d\u8868", "\u671f\u521d\u4f59\u989d", "1001,1002", "", " ", " ", " ", " ", " ", " ", " ", "", "", "", " ", " ", " ", " "});
        titles.add(new String[]{"QE42_NHNKB01[QE42_NHNKB01_A02]", "\u25b3\u7ed3\u7b97\u5907\u4ed8\u91d1 \u671f\u672b\u91d1\u989d", "+", "\u8f85\u52a9\u4f59\u989d\u8868", "\u671f\u672b\u4f59\u989d", "1122", "", " ", " ", " ", " ", " ", " ", " ", "", "", "", " ", "M10099007", "", " "});
        titles.add(new String[]{"QE42_NHNKB01[QE42_NHNKB01_A03]", "\u25b3\u62c6\u51fa\u8d44\u91d1 \u671f\u672b\u91d1\u989d", "+", "\u73b0\u91d1\u6d41\u91cf\u4f59\u989d\u8868", "\u672c\u671f\u53d1\u751f\u989d", "", " ", " ", " ", " ", " ", " ", " ", " ", "", "", "", "10012001"});
        titles.add(new String[]{"QE42_NHNKB01[QE42_NHNKB01_A04]", "\u2606\u4ea4\u6613\u6027\u91d1\u878d\u8d44\u4ea7 \u671f\u672b\u91d1\u989d", "+", "\u91cd\u5206\u7c7b\u4f59\u989d\u8868", "\u501f\u65b9\u5e74\u521d\u5408\u8ba1", "1122", "", "\u5ba2\u6237,\u90e8\u95e8", "\u975e\u660e\u7ec6", " ", " ", " ", " ", " ", "", "", "", " ", " ", " ", " "});
        titles.add(new String[]{"QE42_NHNKB01[QE42_NHNKB01_A05]", "\u4ee5\u516c\u5141\u4ef7\u503c\u8ba1\u91cf\u4e14\u5176\u53d8\u52a8\u8ba1\u5165\u5f53\u671f\u635f\u76ca\u7684\u91d1\u878d\u8d44\u4ea7 \u671f\u672b\u91d1\u989d", "+", "\u8f85\u52a9\u4f59\u989d\u91cd\u5206\u7c7b\u8868", "\u8d37\u65b9\u5e74\u521d\u5408\u8ba1", "1122", "", "\u5ba2\u6237", "\u975e\u660e\u7ec6", " ", " ", " ", " ", " ", "", "", "", " ", "M1002110033", "", " "});
        titles.add(new String[]{"QE42_NHNKB01[QE42_NHNKB01_A06]", "\u884d\u751f\u91d1\u878d\u8d44\u4ea7 \u671f\u672b\u91d1\u989d", "+", "\u62b5\u51cf\u4f59\u989d\u8868", "\u501f\u65b9\u4f59\u989d\u5408\u8ba1", "1122,1123", "", "\u5ba2\u6237", " ", " ", " ", " ", " ", " ", "", "", "", " ", "", "", " "});
        titles.add(new String[]{"QE42_NNHNKB01[QE42_NNHNKB01_A07]", "\u5e94\u6536\u7968\u636e \u671f\u672b\u91d1\u989d", "+", "\u8d26\u9f84\u4f59\u989d\u8868", "\u672c\u5e01\u6838\u9500\u4f59\u989d", "1122", "", " ", " ", " ", " ", "\u6708", "1", "12", "", "", "", " ", "", "", " "});
        titles.add(new String[]{"QE42_NHNKB01[QE42_NHNKB01_A08_1]", "\u5176\u4e2d\uff1a\u5e94\u6536\u8d26\u6b3e \u671f\u672b\u91d1\u989d", "+", "\u81ea\u5b9a\u4e49\u53d6\u6570", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "select SUM(cf_5) from DC_PREASSBALANCE_2020  WHERE UNITCODE ='GL000000202009011R0'  AND SUBJECTCODE  not LIKE '113101%' and MD_CUSTSUPPLIER not like '#'"});
        titles.add(new String[]{"QE42_NHNKB01[QE42_NHNKB01_F1_01]", "QE42_NHNKB01_F1_01", "+", "\u5dee\u989d\u62b5\u9500\u8868", "\u62b5\u9500\u4f59\u989d", "1122", "", " ", " ", " ", " ", " ", " ", " ", "", "", "", " ", "", "", " "});
        titles.add(new String[]{"QE42_NHNKB01[QE42_NHNKB01_A08]", "\u9884\u4ed8\u6b3e\u9879 \u671f\u672b\u91d1\u989d", "+", "\u51ed\u8bc1", "\u501f\u65b9\u53d1\u751f\u989d", "1122", "", " ", " ", " ", " ", " ", " ", " ", "", "", "", " ", "M10011223301", "", " "});
        titles.addAll(ExcelExplainInfo.buildBlankTitle(2));
        titles.add(new String[]{"2\u3001\u6d6e\u52a8\u8868\u914d\u7f6e\u793a\u4f8b\uff1a\u4e1a\u52a1\u6a21\u578b"});
        FloatRegionConfigData businessFloatConfig = new FloatRegionConfigData();
        businessFloatConfig.setFloatConfig("\u8f85\u52a9\u4f59\u989d\u8868{\u79d1\u76ee|1131,1001;\u5e01\u79cd|CNY}");
        businessFloatConfig.setOptionalField("\u79d1\u76ee,\u5e01\u79cd,\u5ba2\u6237,\u90e8\u95e8,\u5e74\u521d\u6570,\u671f\u521d\u4f59\u989d,\u501f\u65b9\u53d1\u751f\u989d,\u8d37\u65b9\u53d1\u751f\u989d,\u501f\u65b9\u7d2f\u8ba1\u53d1\u751f\u989d,\u8d37\u65b9\u7d2f\u8ba1\u53d1\u751f\u989d,\u671f\u672b\u4f59\u989d,\u6298\u7b97\u5dee");
        businessFloatConfig.setFloatType("\u4e1a\u52a1\u6a21\u578b");
        businessFloatConfig.setRegionId("7309fabf-fc9b-4285-b1ca-4dfc58dc157b");
        businessFloatConfig.setRegionName("\u5185\u90e8\u503a\u52a1\u9879\u76ee\u53d1\u751f\u60c5\u51b5\u8868\u884c\u6d6e\u52a8\u533a\u57df");
        titles.addAll(ExcelExplainInfo.buildFloatExampleConfigTitle(businessFloatConfig));
        titles.add(new String[]{"QE42_LAC30TQ8_F3[LAC30TQ8_F3_001]", "\u5bf9\u65b9\u5355\u4f4d", "\u7ed3\u679c\u5217", "\u5ba2\u6237", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "});
        titles.add(new String[]{"QE42_LAC30TQ8_F3[LAC30TQ8_F3_002]", "\u4f1a\u8ba1\u79d1\u76ee\u540d\u79f0", "\u7ed3\u679c\u5217", "\u79d1\u76ee", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "});
        titles.add(new String[]{"QE42_LAC30TQ8_F3[LAC30TQ8_F3_004]", "\u8d37\u65b9\u7d2f\u8ba1\u53d1\u751f\u989d.\u4ef7\u6b3e", "\u7ed3\u679c\u5217", "\u671f\u672b\u4f59\u989d", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "});
        titles.add(new String[]{"QE42_LAC30TQ8_F3[LAC30TQ8_F3_005]", "\u8d28\u4fdd\u91d1", "\u81ea\u5b9a\u4e49\u89c4\u5219", "", "+", "\u8f85\u52a9\u4f59\u989d\u8868", "\u671f\u672b\u4f59\u989d", "SUBJECTCODE", "", "", "", "", "", "", "", "", "", "", "", "", "\u5ba2\u6237", "", ""});
        titles.addAll(ExcelExplainInfo.buildBlankTitle(2));
        titles.add(new String[]{"3\u3001\u6d6e\u52a8\u8868\u914d\u7f6e\u793a\u4f8b\uff1a\u81ea\u5b9a\u4e49\u67e5\u8be2"});
        FloatRegionConfigData definedFloatConfig = new FloatRegionConfigData();
        definedFloatConfig.setFloatConfig("KMYEB{\u5355\u4f4d\u4ee3\u7801|\u81ea\u5b9a\u4e49|M1112203;\u8d26\u7c3f|\u9884\u5236|\u8d26\u7c3f}");
        definedFloatConfig.setOptionalField("ACCT_SET_CODE,AGENCY_CODE,MOF_DIV_CODE,SUBJECTCODE,CURRENCYCODE,ORIENT,PERIOD,NC,C,JF,DF,JL,DL,YE,WC,WJF,WDF,WJL,WDL,WYE");
        definedFloatConfig.setFloatType("\u81ea\u5b9a\u4e49\u67e5\u8be2");
        definedFloatConfig.setRegionId("fc01cc17-b97d-41e8-abd9-8e839ac1597e");
        definedFloatConfig.setRegionName("\u5185\u90e8\u503a\u52a1\u9879\u76ee\u53d1\u751f\u60c5\u51b5\u8868_1\u884c\u6d6e\u52a8\u533a\u57df");
        titles.addAll(ExcelExplainInfo.buildFloatExampleConfigTitle(definedFloatConfig));
        titles.add(new String[]{"QE42_LAC34DLB_F3[LAC34DLB_F3_001]", "\u5bf9\u65b9\u5355\u4f4d", "\u7ed3\u679c\u5217", "ACCT_SET_CODE", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "});
        titles.add(new String[]{"QE42_LAC34DLB_F3[LAC34DLB_F3_003]", "\u4f1a\u8ba1\u79d1\u76ee\u4ee3\u7801", "\u7ed3\u679c\u5217", "SUBJECTCODE", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "});
        titles.add(new String[]{"QE42_LAC34DLB_F3[LAC34DLB_F3_004]", "\u8d37\u65b9\u7d2f\u8ba1\u53d1\u751f\u989d.\u4ef7\u6b3e", "\u7ed3\u679c\u5217", "NC", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "});
        titles.add(new String[]{"QE42_LAC34DLB_F3[LAC34DLB_F3_005]", "\u8d28\u4fdd\u91d1", "\u81ea\u5b9a\u4e49\u89c4\u5219", "", "+", "\u8f85\u52a9\u4f59\u989d\u8868", "\u671f\u672b\u4f59\u989d", "SUBJECTCODE", "", "", "", "", "", "", "", "", "", "", "", "", "ACCT_SET_CODE", "", ""});
        titles.addAll(ExcelExplainInfo.buildBlankTitle(2));
        titles.add(new String[]{"4\u3001\u6d6e\u52a8\u8868\u914d\u7f6e\u793a\u4f8b\uff1a\u81ea\u5b9a\u4e49SQL"});
        FloatRegionConfigData sqlFloatConfig = new FloatRegionConfigData();
        sqlFloatConfig.setFloatConfig("select SUBJECTCODE,MD_CUSTSUPPLIER,SUM(cf_5) from DC_PREASSBALANCE_2020 WHERE UNITCODE = 'GL000000202009011R0' AND SUBJECTCODE LIKE '113101%' GROUP BY SUBJECTCODE,MD_CUSTSUPPLIER ORDER BY SUBJECTCODE,MD_CUSTSUPPLIER");
        sqlFloatConfig.setOptionalField("SUBJECTCODE,MD_CUSTSUPPLIER,SUM(cf_5)");
        sqlFloatConfig.setFloatType("\u81ea\u5b9a\u4e49SQL");
        sqlFloatConfig.setRegionId("d26de06c-3503-45a8-bd6e-3772e4365ff3");
        sqlFloatConfig.setRegionName("\u5185\u90e8\u5f80\u6765-\u5e94\u4ed8\u8d26\u6b3e\u884c\u6d6e\u52a8\u533a\u57df");
        titles.addAll(ExcelExplainInfo.buildFloatExampleConfigTitle(sqlFloatConfig));
        titles.add(new String[]{"QE42_GC_INPUTDATA_JKY[OPPUNITID]", "\u5bf9\u65b9\u5355\u4f4d", "\u7ed3\u679c\u5217", "MD_CUSTSUPPLIER", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "});
        titles.add(new String[]{"QE42_GC_INPUTDATA_JKY[SUBJECTOBJ]", "\u79d1\u76ee\uff08\u4e0b\u62c9\u9009\u62e9\uff09", "\u7ed3\u679c\u5217", "SUBJECTCODE", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "});
        titles.add(new String[]{"QE42_GC_INPUTDATA_JKY[ORGCODE]", "\u672c\u65b9\u5355\u4f4d", "\u7ed3\u679c\u5217", "SUM(cf_5)", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "});
        titles.add(new String[]{"QE42_GC_INPUTDATA_JKY[AMT]", "\u91d1\u989d", "\u81ea\u5b9a\u4e49\u89c4\u5219", "", "+", "\u8f85\u52a9\u4f59\u989d\u8868", "\u671f\u672b\u4f59\u989d", "SUBJECTCODE", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""});
        LinkedHashSet fetchTypeCodes = new LinkedHashSet();
        fetchSettingExportContext.getBizModels().stream().filter(item -> !ComputationModelEnum.CUSTOMFETCH.getCode().equals(item.getComputationModelCode()) && !ComputationModelEnum.BASEDATA.getCode().equals(item.getComputationModelCode())).filter(bizModelDTO -> !CollectionUtils.isEmpty(FetchSettingNrUtil.getBizModelFields(bizModelDTO))).forEach(bizModelDTO -> fetchTypeCodes.addAll(FetchSettingNrUtil.getBizModelFields(bizModelDTO)));
        List<String> fetchTypeNames = fetchTypeCodes.stream().map(code -> FetchTypeEnum.getEnumByCode((String)code).getName()).collect(Collectors.toList());
        fetchTypeNames.add(0, "\u53d6\u6570\u7c7b\u578b");
        titles.addAll(ExcelExplainInfo.buildBlankTitle(1));
        titles.add(fetchTypeNames.toArray(new String[fetchTypeNames.size()]));
        return titles;
    }

    private static List<String[]> buildFixExampleConfigTitle() {
        ArrayList<String[]> titles = new ArrayList<String[]>();
        titles.add(new String[]{"\u6307\u6807\u4fe1\u606f", "\u6307\u6807\u4fe1\u606f", "\u8fd0\u7b97\u7b26", "\u4e1a\u52a1\u6a21\u578b", "\u53d6\u6570\u7c7b\u578b", "\u79d1\u76ee", "\u6392\u9664\u79d1\u76ee", "\u91cd\u5206\u7c7b/\u62b5\u51cf\u4f59\u989d", "\u91cd\u5206\u7c7b/\u62b5\u51cf\u4f59\u989d", "\u5230\u671f\u65e5\u91cd\u5206\u7c7b", "\u5230\u671f\u65e5\u91cd\u5206\u7c7b", "\u5f80\u6765\u8d26\u9f84", "\u5f80\u6765\u8d26\u9f84", "\u5f80\u6765\u8d26\u9f84", "\u6307\u5b9a\u5e74\u5ea6", "\u6307\u5b9a\u671f\u95f4", "\u6307\u5b9a\u5e01\u79cd", "\u6307\u5b9a\u6570\u636e\u6e90", "\u73b0\u6d41\u9879\u76ee", "\u8f85\u52a9\u7ef4\u5ea6\u8bbe\u7f6e", "\u8f85\u52a9\u7ef4\u5ea6\u8bbe\u7f6e", "\u53d6\u6570SQL"});
        titles.add(new String[]{"\u6307\u6807\u4ee3\u7801", "\u6307\u6807\u540d\u79f0", "\u8fd0\u7b97\u7b26", "\u4e1a\u52a1\u6a21\u578b", "\u53d6\u6570\u7c7b\u578b", "\u79d1\u76ee", "\u6392\u9664\u79d1\u76ee", "\u91cd\u5206\u7c7b/\u62b5\u51cf\u7ef4\u5ea6", "\u91cd\u5206\u7c7b\u6c47\u603b\u7c7b\u578b", "\u91cd\u5206\u7c7b\u540e\u79d1\u76ee", "\u91cd\u5206\u7c7b\u524d\u79d1\u76ee", "\u533a\u95f4\u7c7b\u578b", "\u8d77\u59cb\u533a\u95f4", "\u622a\u6b62\u533a\u95f4", "\u6307\u5b9a\u5e74\u5ea6", "\u6307\u5b9a\u671f\u95f4", "\u6307\u5b9a\u5e01\u79cd", "\u6307\u5b9a\u6570\u636e\u6e90", "\u73b0\u6d41\u9879\u76ee", "\u5ba2\u6237", "\u90e8\u95e8", "\u53d6\u6570SQL"});
        return titles;
    }

    private static List<String[]> buildFloatExampleConfigTitle(FloatRegionConfigData floatRegionConfigData) {
        ArrayList<String[]> titles = new ArrayList<String[]>();
        titles.add(new String[]{"\u6d6e\u52a8\u533a\u57df\u6807\u8bc6", floatRegionConfigData.getRegionId(), "\u6d6e\u52a8\u533a\u57df\u540d\u79f0", "\u5185\u90e8\u503a\u52a1\u9879\u76ee\u53d1\u751f\u60c5\u51b5\u8868\u884c\u6d6e\u52a8\u533a\u57df"});
        titles.add(new String[]{"\u6d6e\u52a8\u884c\u8bbe\u7f6e\u7c7b\u578b", floatRegionConfigData.getFloatType(), "\u6d6e\u52a8\u884c\u8bbe\u7f6e\u5185\u5bb9", floatRegionConfigData.getFloatConfig(), "\u53d6\u6570\u89c4\u5219\u53ef\u9009\u5b57\u6bb5", floatRegionConfigData.getOptionalField()});
        titles.add(new String[]{"\u6307\u6807\u4fe1\u606f", "\u6307\u6807\u4fe1\u606f", "\u6d6e\u52a8\u5217\u8bbe\u7f6e\u7c7b\u578b", "\u6d6e\u52a8\u5217\u8bbe\u7f6e\u5185\u5bb9", "\u8fd0\u7b97\u7b26", "\u4e1a\u52a1\u6a21\u578b", "\u53d6\u6570\u7c7b\u578b", "\u79d1\u76ee", "\u6392\u9664\u79d1\u76ee", "\u91cd\u5206\u7c7b/\u62b5\u51cf\u4f59\u989d", "\u91cd\u5206\u7c7b/\u62b5\u51cf\u4f59\u989d", "\u5230\u671f\u65e5\u91cd\u5206\u7c7b", "\u5230\u671f\u65e5\u91cd\u5206\u7c7b", "\u5f80\u6765\u8d26\u9f84", "\u5f80\u6765\u8d26\u9f84", "\u5f80\u6765\u8d26\u9f84", "\u6307\u5b9a\u5e74\u5ea6", "\u6307\u5b9a\u671f\u95f4", "\u6307\u5b9a\u5e01\u79cd", "\u73b0\u6d41\u9879\u76ee", "\u8f85\u52a9\u7ef4\u5ea6\u8bbe\u7f6e", "\u8f85\u52a9\u7ef4\u5ea6\u8bbe\u7f6e", "\u53d6\u6570SQL"});
        titles.add(new String[]{"\u6307\u6807\u4ee3\u7801", "\u6307\u6807\u540d\u79f0", "\u6d6e\u52a8\u5217\u8bbe\u7f6e\u7c7b\u578b", "\u6d6e\u52a8\u5217\u8bbe\u7f6e\u5185\u5bb9", "\u8fd0\u7b97\u7b26", "\u4e1a\u52a1\u6a21\u578b", "\u53d6\u6570\u7c7b\u578b", "\u79d1\u76ee", "\u6392\u9664\u79d1\u76ee", "\u91cd\u5206\u7c7b/\u62b5\u51cf\u7ef4\u5ea6", "\u91cd\u5206\u7c7b\u6c47\u603b\u7c7b\u578b", "\u91cd\u5206\u7c7b\u540e\u79d1\u76ee", "\u91cd\u5206\u7c7b\u524d\u79d1\u76ee", "\u533a\u95f4\u7c7b\u578b", "\u8d77\u59cb\u533a\u95f4", "\u622a\u6b62\u533a\u95f4", "\u6307\u5b9a\u5e74\u5ea6", "\u6307\u5b9a\u671f\u95f4", "\u6307\u5b9a\u5e01\u79cd", "\u73b0\u6d41\u9879\u76ee", "\u5ba2\u6237", "\u90e8\u95e8", "\u53d6\u6570SQL"});
        return titles;
    }

    private static List<String[]> buildBlankTitle(int lineSize) {
        ArrayList<String[]> titles = new ArrayList<String[]>();
        String[] blankLine = new String[]{};
        for (int i = 0; i < lineSize; ++i) {
            titles.add(blankLine);
        }
        return titles;
    }
}

