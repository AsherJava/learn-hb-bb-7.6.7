/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.option.OptionItemLabel
 *  com.jiuqi.va.domain.option.OptionItemType
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.oss.feign.client.VaOssFeignClient
 */
package com.jiuqi.va.attachment.entity;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.option.OptionItemLabel;
import com.jiuqi.va.domain.option.OptionItemType;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.oss.feign.client.VaOssFeignClient;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileOptionConsts {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileOptionConsts.class);
    public static final String FILE1001 = "FILE1001";
    public static final String FILE1001_TITLE = "\u542f\u7528\u6587\u4ef6\u9884\u89c8\u670d\u52a1";
    public static final String FILE1002 = "FILE1002";
    public static final String FILE1002_TITLE = "\u6587\u4ef6\u9884\u89c8\u670d\u52a1\u5730\u5740";
    public static final String FILE1003 = "FILE1003";
    public static final String FILE1003_TITLE = "\u7b7e\u7ae0\u5b58\u50a8\u65b9\u6848";

    public static LinkedHashMap<String, OptionItemVO> optionFoMap(String groupName) {
        LinkedHashMap<String, OptionItemVO> infos = new LinkedHashMap<String, OptionItemVO>();
        OptionItemVO file1001 = new OptionItemVO();
        file1001.setName(FILE1001);
        file1001.setTitle(FILE1001_TITLE);
        file1001.setRemark("\u9ed8\u8ba4\u4e3a\u5426\uff0c\u53c2\u6570\u503c\u4e3a\u201c\u5426\u201d\u65f6\uff0c\u901a\u8fc7\u7cfb\u7edf\u5185\u7f6e\u65b9\u5f0f\u9884\u89c8\u6587\u4ef6\uff08\u4ec5\u652f\u6301\u56fe\u7247\u3001pdf\u683c\u5f0f\uff09\uff1b\u53c2\u6570\u503c\u4e3a\u201c\u662f\u201d\u65f6\uff0c\u901a\u8fc7\u6587\u4ef6\u9884\u89c8\u670d\u52a1\u9884\u89c8\u6587\u4ef6\uff08\u652f\u6301\u56fe\u7247\u3001pdf\u3001doc\u3001docx\u3001xls\u3001xlsx\u3001ppt\u3001pptx\u7b49\u4e3b\u6d41\u683c\u5f0f\uff09");
        file1001.setDefauleVal("0");
        file1001.setItemtype(OptionItemType.LIST);
        file1001.addLabel(new OptionItemLabel("\u662f", "1"));
        file1001.addLabel(new OptionItemLabel("\u5426", "0"));
        infos.put(FILE1001, file1001);
        OptionItemVO file1002 = new OptionItemVO();
        file1002.setName(FILE1002);
        file1002.setTitle(FILE1002_TITLE);
        file1002.setRemark("\u6587\u4ef6\u9884\u89c8\u670d\u52a1\u5730\u5740\uff0c\u683c\u5f0f\u4e3a\uff1ahttp://ip:port");
        file1002.setDefauleVal("");
        file1002.setItemtype(OptionItemType.TEXT);
        infos.put(FILE1002, file1002);
        OptionItemVO file1003 = new OptionItemVO();
        file1003.setName(FILE1003);
        file1003.setTitle(FILE1003_TITLE);
        file1003.setRemark("\u4ece\u3010\u7cfb\u7edf\u914d\u7f6e-\u5bf9\u8c61\u5b58\u50a8-\u5bf9\u8c61/\u6587\u4ef6\u57df\u5217\u8868\u3011\u4e2d\u9009\u62e9\uff0c\u9ed8\u8ba4\u4e3aVA_SIGNATURE\u57df\u65b9\u6848");
        file1003.setDefauleVal("VA_SIGNATURE");
        file1003.setItemtype(OptionItemType.LIST);
        VaOssFeignClient feignClient = (VaOssFeignClient)ApplicationContextRegister.getBean(VaOssFeignClient.class);
        try {
            PageVO mapList = feignClient.listBucket(false);
            if (mapList != null) {
                for (Map map : mapList.getRows()) {
                    file1003.addLabel(new OptionItemLabel(map.get("name").toString(), map.get("name").toString()));
                }
            }
            infos.put(FILE1003, file1003);
        }
        catch (Exception e) {
            LOGGER.error("\u83b7\u53d6\u5bf9\u8c61\u5b58\u50a8\u65b9\u6848\u5217\u8868\u5931\u8d25:" + e.getMessage(), e);
        }
        return infos;
    }
}

