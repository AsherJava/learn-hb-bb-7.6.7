/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.option.BdeOptionTypeEnum
 *  com.jiuqi.bde.common.option.IBdeOptionDeclareItems
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 *  com.jiuqi.nvwa.troubleshooting.bean.LogFileItem
 *  com.jiuqi.nvwa.troubleshooting.service.LogCheckService
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.bde.bizmodel.define.option;

import com.jiuqi.bde.bizmodel.define.config.BdeBizModelConfig;
import com.jiuqi.bde.common.option.BdeOptionTypeEnum;
import com.jiuqi.bde.common.option.IBdeOptionDeclareItems;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import com.jiuqi.nvwa.troubleshooting.bean.LogFileItem;
import com.jiuqi.nvwa.troubleshooting.service.LogCheckService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class BdeFetchOptionDeclareItems
implements IBdeOptionDeclareItems {
    @Autowired
    @Lazy
    private LogCheckService logCheckService;
    private static final Logger logger = LoggerFactory.getLogger(BdeFetchOptionDeclareItems.class);

    public BdeOptionTypeEnum getOptionType() {
        return BdeOptionTypeEnum.SYSTEM;
    }

    public String getId() {
        return "bde-fetch-option";
    }

    public String getTitle() {
        return "\u53d6\u6570\u6267\u884c";
    }

    public List<ISystemOptionItem> getItems() {
        ArrayList<ISystemOptionItem> list = new ArrayList<ISystemOptionItem>();
        SystemOptionItem debug = new SystemOptionItem();
        debug.setId("BDE_IS_DEBUG");
        debug.setTitle("\u8bb0\u5f55\u53d6\u6570\u8fc7\u7a0b\u8be6\u7ec6\u65e5\u5fd7");
        int minutes = BdeBizModelConfig.getDebugTimeoutMinutes();
        String logFilePatch = this.getLogFilePatch();
        debug.setDescribe(String.format("\u5f00\u542f\u540e\u4f1a\u8bb0\u5f55\u53d6\u6570\u8be6\u7ec6\u8fc7\u7a0b\uff0c\u65e5\u5fd7\u6587\u4ef6\u4f4d\u7f6e\uff1a\u3010%1$s\u3011\uff0c\u5f00\u542f\u540e%2$s\u5206\u949f\u81ea\u52a8\u5173\u95ed\u3002", logFilePatch, minutes));
        debug.setEditMode(SystemOptionConst.EditMode.TRUE_FALSE);
        debug.setDefaultValue("0");
        list.add((ISystemOptionItem)debug);
        SystemOptionItem fetchResultOptionItem = new SystemOptionItem();
        fetchResultOptionItem.setId("BDE_TABLEDATA_EXPIRATION");
        fetchResultOptionItem.setTitle("\u70ed\u70b9\u6570\u636e\u8fc7\u671f\u65f6\u95f4(\u79d2)");
        fetchResultOptionItem.setDescribe("\u6838\u7b97\u6570\u636e\u8f6c\u6362\u4e3aBDE\u7cfb\u7edf\u70ed\u70b9\u6570\u636e\u7684\u8fc7\u671f\u65f6\u95f4\uff0c\u70ed\u70b9\u6570\u636e\u6709\u6548\u671f\u5185\u4e0d\u4f1a\u91cd\u65b0\u89e6\u53d1\u6838\u7b97\u6570\u636e\u67e5\u8be2");
        fetchResultOptionItem.setEditMode(SystemOptionConst.EditMode.DEFAULT);
        fetchResultOptionItem.setDefaultValue("180");
        list.add((ISystemOptionItem)fetchResultOptionItem);
        SystemOptionItem cleanZeroRecord = new SystemOptionItem();
        cleanZeroRecord.setId("CLEAN_ZERO_RECORDS");
        cleanZeroRecord.setTitle("\u6d6e\u52a8\u8868\u53d6\u6570\u7ed3\u679c\u6e05\u7a7a\u6570\u503c\u5217\u5747\u4e3a0\u8bb0\u5f55");
        cleanZeroRecord.setEditMode(SystemOptionConst.EditMode.TRUE_FALSE);
        cleanZeroRecord.setDefaultValue("1");
        list.add((ISystemOptionItem)cleanZeroRecord);
        SystemOptionItem customFetchAsFloatConfig = new SystemOptionItem();
        customFetchAsFloatConfig.setId("ENABLE_CUSTOMFETCH_AS_FLOAT");
        customFetchAsFloatConfig.setTitle("\u81ea\u5b9a\u4e49\u53d6\u6570\u4e1a\u52a1\u6a21\u578b\u4f5c\u4e3a\u6d6e\u52a8\u53d6\u6570\u6765\u6e90");
        customFetchAsFloatConfig.setEditMode(SystemOptionConst.EditMode.TRUE_FALSE);
        customFetchAsFloatConfig.setDefaultValue("0");
        list.add((ISystemOptionItem)customFetchAsFloatConfig);
        SystemOptionItem customFetchModelRowLimit = new SystemOptionItem();
        customFetchModelRowLimit.setId("BDE_CUSTOMFETCH_ROW_LIMIT");
        customFetchModelRowLimit.setTitle("\u81ea\u5b9a\u4e49\u53d6\u6570\u4e1a\u52a1\u6a21\u578b\u7ed3\u679c\u96c6\u884c\u6570\u9650\u5236");
        customFetchModelRowLimit.setDescribe("\u81ea\u5b9a\u4e49\u53d6\u6570\u4e1a\u52a1\u6a21\u578b\u6267\u884c\u65f6\u5982\u679c\u7ed3\u679c\u96c6\u8d85\u51fa\u884c\u6570\u9650\u5236\uff0c\u7cfb\u7edf\u4f1a\u62a5\u9519");
        customFetchModelRowLimit.setEditMode(SystemOptionConst.EditMode.NUMBER_INPUT);
        customFetchModelRowLimit.setVerifyRegex("^\\d+$");
        customFetchModelRowLimit.setVerifyRegexMessage("\u8bf7\u8f93\u5165\u975e\u8d1f\u6574\u6570");
        customFetchModelRowLimit.setDefaultValue("500000");
        list.add((ISystemOptionItem)customFetchModelRowLimit);
        list.addAll(this.baseDataInputConfigSystemOptions());
        return list;
    }

    private List<ISystemOptionItem> baseDataInputConfigSystemOptions() {
        ArrayList<ISystemOptionItem> items = new ArrayList<ISystemOptionItem>();
        SystemOptionItem baseDataSystemOptionItem = new SystemOptionItem();
        baseDataSystemOptionItem.setId("BDE_FETCH_SETTING_BASE_DATA_INPUT_SWITCH");
        baseDataSystemOptionItem.setTitle("\u53d6\u6570\u8bbe\u7f6e\u542f\u7528\u57fa\u7840\u6570\u636e\u7ec4\u4ef6\u5f55\u5165");
        baseDataSystemOptionItem.setEditMode(SystemOptionConst.EditMode.TRUE_FALSE);
        baseDataSystemOptionItem.setDefaultValue("0");
        items.add((ISystemOptionItem)baseDataSystemOptionItem);
        return items;
    }

    private String getLogFilePatch() {
        String LOG_FILE_PATH = "./bde-fetch.log";
        String LOG_PATH_KEYWORD = "bde-fetch";
        String FN_ID = "id";
        try {
            JSONArray fileSourceArr = this.logCheckService.getLogFileSource();
            JSONObject fileSource = null;
            for (Object fileSourceObj : fileSourceArr) {
                fileSource = (JSONObject)fileSourceObj;
                String id = fileSource.getString("id");
                List logFilePathList = this.logCheckService.getLogFilePath(id);
                if (CollectionUtils.isEmpty((Collection)logFilePathList)) continue;
                for (LogFileItem logFileItem : logFilePathList) {
                    if (!logFileItem.getPath().contains("bde-fetch")) continue;
                    return logFileItem.getShowTitle();
                }
            }
        }
        catch (Exception e) {
            logger.debug("\u83b7\u53d6\u6545\u969c\u68c0\u67e5\u6587\u4ef6\u8def\u5f84\u53d1\u751f\u9519\u8bef\uff0c\u663e\u793a\u9ed8\u8ba4\u6587\u4ef6\u4f4d\u7f6e", e);
        }
        return "./bde-fetch.log";
    }

    public int getOrder() {
        return 100;
    }
}

