/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.offsetitem.gather.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.offsetitem.enums.TabSelectEnum;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAdjustExecutor;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemButton;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcOffSetItemAdjustExecutorImpl
implements GcOffSetItemAdjustExecutor {
    @Autowired
    private List<GcOffsetItemShowType> gcOffsetItemShowTypeList;
    private final Logger logger = LoggerFactory.getLogger(GcOffSetItemAdjustExecutorImpl.class);

    @Override
    public GcOffSetItemAction getActionForCode(GcOffsetExecutorVO gcOffsetExecutorVO) {
        List gcOffSetItemActionList = this.gcOffsetItemShowTypeList.stream().flatMap(showType -> showType.actions().stream().filter(item -> {
            GcOffsetExecutorVO vo = new GcOffsetExecutorVO(item.code(), showType.getPage().getPageCode(), showType.getDataSource() != null ? showType.getDataSource().getDataSourceCode() : null, showType.getCode());
            return vo.equals(gcOffsetExecutorVO);
        })).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(gcOffSetItemActionList) || gcOffSetItemActionList.size() > 1) {
            String error = gcOffsetExecutorVO.getPageCode() + "\u9875\u7b7e " + gcOffsetExecutorVO.getFilterMethod() + "\u5c55\u793a\u65b9\u5f0f";
            if (!StringUtils.isEmpty((String)gcOffsetExecutorVO.getDataSourceCode())) {
                error = error + gcOffsetExecutorVO.getDataSourceCode() + "\u6570\u636e\u6e90";
            }
            error = error + gcOffsetExecutorVO.getActionCode() + "\u6267\u884c\u5668\u5728\u7cfb\u7edf\u4e2d\u5339\u914d\u5230[" + gcOffSetItemActionList.size() + "]\u4e2a\u6267\u884c\u5668\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458";
            this.logger.error(error);
            throw new BusinessRuntimeException(error);
        }
        return (GcOffSetItemAction)gcOffSetItemActionList.get(0);
    }

    @Override
    public List<Map<String, String>> listShowTypeForPage(String pageCode) {
        ArrayList<Map<String, String>> showTypeForPage = new ArrayList<Map<String, String>>();
        this.gcOffsetItemShowTypeList.forEach(showType -> {
            if (showType.getPage().getPageCode().equals(pageCode)) {
                HashMap<String, String> showTypeMap = new HashMap<String, String>();
                showTypeMap.put("value", showType.getCode());
                showTypeMap.put("label", showType.getTitle());
                showTypeForPage.add(showTypeMap);
            }
        });
        return showTypeForPage;
    }

    @Override
    public List<GcOffsetItemShowType> listShowTypesForCondition(String pageCode, String dataSourceCode) {
        return this.gcOffsetItemShowTypeList.stream().filter(showTypeItem -> showTypeItem.getPage().getPageCode().equals(pageCode) && showTypeItem.getDataSource().getDataSourceCode().equals(dataSourceCode)).collect(Collectors.toList());
    }

    public String listButtonsForCode(GcOffsetExecutorVO gcOffsetExecutorVO) {
        String pageCode = gcOffsetExecutorVO.getPageCode();
        String showType = gcOffsetExecutorVO.getFilterMethod();
        String dataSourceCode = gcOffsetExecutorVO.getDataSourceCode();
        List<Object> gcNotOffsetItemShowTypes = new ArrayList();
        gcNotOffsetItemShowTypes = pageCode.equals(TabSelectEnum.ALL_PAGE.getCode()) ? this.gcOffsetItemShowTypeList.stream().filter(showTypeItem -> showTypeItem.getPage().getPageCode().equals(pageCode) && "AMT".equals(showTypeItem.getCode())).collect(Collectors.toList()) : (pageCode.equals(TabSelectEnum.OFFSET_PAGE.getCode()) ? this.gcOffsetItemShowTypeList.stream().filter(showTypeItem -> showTypeItem.getPage().getPageCode().equals(pageCode) && "OFFSET".equals(showTypeItem.getCode())).collect(Collectors.toList()) : this.gcOffsetItemShowTypeList.stream().filter(showTypeItem -> showTypeItem.getPage().getPageCode().equals(pageCode) && "AMT".equals(showTypeItem.getCode()) && showTypeItem.getDataSource().getDataSourceCode().equals(dataSourceCode)).collect(Collectors.toList()));
        if (gcNotOffsetItemShowTypes.size() > 1) {
            String error = pageCode + "\u9875\u7b7e " + showType + "\u5c55\u793a\u65b9\u5f0f" + dataSourceCode + "\u6570\u636e\u6e90\u6267\u884c\u5668\u5728\u7cfb\u7edf\u4e2d\u5339\u914d\u5230[" + gcNotOffsetItemShowTypes.size() + "]\u4e2a\u6267\u884c\u5668\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458";
            this.logger.error(error);
            throw new BusinessRuntimeException(error);
        }
        JSONArray jsonArray = new JSONArray();
        ((GcOffsetItemShowType)gcNotOffsetItemShowTypes.get(0)).actions().stream().filter(action -> action instanceof GcOffsetItemButton).map(action -> (GcOffsetItemButton)action).forEach(button -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", (Object)button.code());
            jsonObject.put("title", (Object)button.title());
            jsonObject.put("icon", (Object)button.icon());
            QueryParamsVO params = new QueryParamsVO();
            jsonObject.put("visible", button.isVisible(params));
            jsonObject.put("enable", button.isEnable(params));
            jsonArray.put((Object)jsonObject);
        });
        return jsonArray.toString();
    }

    @Override
    public List<GcOffsetItemShowType> listShowTypeForDataSource(String dataSource) {
        return this.gcOffsetItemShowTypeList.stream().filter(showTypeItem -> showTypeItem.getDataSource() != null && showTypeItem.getDataSource().getDataSourceCode().equals(dataSource)).collect(Collectors.toList());
    }
}

