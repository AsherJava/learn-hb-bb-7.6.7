/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.api.GcUnOffsetOptionClient
 *  com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAdjustExecutor
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType
 *  com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcUnOffsetOptionVo
 *  javax.annotation.Resource
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.offsetitem.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.api.GcUnOffsetOptionClient;
import com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAdjustExecutor;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType;
import com.jiuqi.gcreport.offsetitem.i18n.UnOffsetSelectOptionI18Const;
import com.jiuqi.gcreport.offsetitem.service.GcUnOffsetSelectOptionService;
import com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO;
import com.jiuqi.gcreport.offsetitem.vo.GcUnOffsetOptionVo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
@CrossOrigin
public class GcUnOffsetOptionController
implements GcUnOffsetOptionClient {
    @Autowired
    private GcUnOffsetSelectOptionService gcUnOffsetSelectOptionService;
    @Resource
    GcOffSetItemAdjustExecutor gcOffSetItemAdjustExecutor;

    public BusinessResponseEntity<List<GcInputAdjustVO>> queryContentById(String id) {
        return BusinessResponseEntity.ok(this.gcUnOffsetSelectOptionService.queryContentById(id));
    }

    public BusinessResponseEntity<List<Map<String, Object>>> listUnOffsetConfigDatas(String dataSource) {
        return BusinessResponseEntity.ok(this.gcUnOffsetSelectOptionService.listUnOffsetConfigDatas(dataSource));
    }

    public BusinessResponseEntity<String> exchangeSort(String id, String dataSource, String pageCode, int step) {
        this.gcUnOffsetSelectOptionService.exchangeSort(id, dataSource, pageCode, step);
        return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f");
    }

    public BusinessResponseEntity<String> delete(GcUnOffsetOptionVo gcUnOffsetOptionVo) {
        this.gcUnOffsetSelectOptionService.delete(gcUnOffsetOptionVo);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
    }

    public BusinessResponseEntity<String> save(List<GcUnOffsetOptionVo> gcUnOffsetOptionVos) {
        this.gcUnOffsetSelectOptionService.save(gcUnOffsetOptionVos);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f");
    }

    public BusinessResponseEntity<Map<String, String>> listSelectOption() {
        return BusinessResponseEntity.ok(null);
    }

    public BusinessResponseEntity<List<Map<String, Object>>> listSelectOptionForTab(String dataSource) {
        return BusinessResponseEntity.ok(this.gcUnOffsetSelectOptionService.listSelectOptionForTab(dataSource));
    }

    public BusinessResponseEntity<String> saveSelectOption(List<Map<String, Object>> selectOptions, String dataSource) {
        List isShowSelectOptions = selectOptions.stream().filter(saveSelectOption -> "1".equals(saveSelectOption.get("isShow"))).collect(Collectors.toList());
        Map<Object, List<Map<String, Object>>> selectOptionsGroupByPage = isShowSelectOptions.stream().collect(Collectors.groupingBy(selectOption -> selectOption.get("pageCode")));
        if (selectOptionsGroupByPage.size() < 2) {
            return BusinessResponseEntity.error((String)"\u672c\u7ea7-\u672a\u62b5\u9500\u6216\u4e0a\u7ea7-\u672a\u62b5\u9500\u9875\u7b7e\u5c55\u793a\u65b9\u5f0f\u4e0d\u53ef\u4e3a\u7a7a,\u8bf7\u786e\u8ba4");
        }
        this.gcUnOffsetSelectOptionService.saveSelectOption(selectOptionsGroupByPage, dataSource);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f");
    }

    public BusinessResponseEntity<List<Map<String, String>>> listShowTypeForPage(String pageCode) {
        return BusinessResponseEntity.ok((Object)this.gcOffSetItemAdjustExecutor.listShowTypeForPage(pageCode));
    }

    public BusinessResponseEntity<String> getCurDataSource() {
        return BusinessResponseEntity.ok((Object)this.gcUnOffsetSelectOptionService.getCurDataSource());
    }

    public BusinessResponseEntity<List<Map<String, Object>>> listUnoffsetConfigForPage(String pageCode, String dataSource) {
        List<Map<String, Object>> unOffsetConfigDatas = this.gcUnOffsetSelectOptionService.listSelectOptionForTab(dataSource);
        Map<Object, Object> unOffsetConfigDataMap = new HashMap();
        for (Map<String, Object> unOffsetConfigData : unOffsetConfigDatas) {
            if (!pageCode.equals(unOffsetConfigData.get("code"))) continue;
            unOffsetConfigDataMap = unOffsetConfigData;
        }
        List gcOffsetItemShowTypes = this.gcOffSetItemAdjustExecutor.listShowTypesForCondition(pageCode, dataSource);
        List configList = (List)unOffsetConfigDataMap.get("value");
        for (GcOffsetItemShowType gcOffsetItemShowType : gcOffsetItemShowTypes) {
            boolean isGcOffsetItemShow = false;
            for (Map configMap : configList) {
                if (!gcOffsetItemShowType.getCode().equals(configMap.get("value"))) continue;
                isGcOffsetItemShow = true;
                configMap.put("isSuppportUnitTreeSort", gcOffsetItemShowType.isEnableMemorySort());
            }
            if (isGcOffsetItemShow) continue;
            HashMap<String, Object> configMap = new HashMap<String, Object>();
            configMap.put("isSuppportUnitTreeSort", gcOffsetItemShowType.isEnableMemorySort());
            configMap.put("isShow", "0");
            configMap.put("isUnitTreeSort", "0");
            String I18n = UnOffsetSelectOptionI18Const.getI18nForCode(gcOffsetItemShowType.getCode());
            if (StringUtils.hasLength(I18n)) {
                configMap.put("showType", GcI18nUtil.getMessage((String)I18n));
            } else {
                configMap.put("showType", gcOffsetItemShowType.getTitle());
            }
            configMap.put("value", gcOffsetItemShowType.getCode());
            configList.add(configMap);
        }
        return BusinessResponseEntity.ok(configList.stream().filter(map -> !map.containsKey("value") || !FilterMethodEnum.COMMON.getCode().equals(map.get("value"))).collect(Collectors.toList()));
    }
}

