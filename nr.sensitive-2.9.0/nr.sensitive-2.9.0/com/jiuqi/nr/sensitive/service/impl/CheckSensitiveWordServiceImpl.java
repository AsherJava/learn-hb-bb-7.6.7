/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.message.MessagePublisher
 */
package com.jiuqi.nr.sensitive.service.impl;

import com.jiuqi.np.cache.message.MessagePublisher;
import com.jiuqi.nr.sensitive.bean.daoObject.SensitiveWordDaoObject;
import com.jiuqi.nr.sensitive.common.checkSensitiveUtil.CheckSensitiveWord;
import com.jiuqi.nr.sensitive.dao.SensitiveWordDao;
import com.jiuqi.nr.sensitive.service.CheckSensitiveWordService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class CheckSensitiveWordServiceImpl
implements CheckSensitiveWordService {
    @Autowired
    private SensitiveWordDao sensitiveWordDao;
    @Autowired(required=false)
    private MessagePublisher messagePublisher;
    public static final String DEFAULT = "com.jiuqi.nr.sensitive";
    private static HashMap sensitiveMap = new HashMap();
    private static HashMap whiteMap = new HashMap();
    private static HashMap<String, SensitiveWordDaoObject> sensitiveWordDaoObjectMapOfString = new HashMap();
    private static HashMap<String, SensitiveWordDaoObject> sensitiveWordDaoObjectMapOfReg = new HashMap();
    private static List<String> sensitiveRegList = new ArrayList<String>();
    private static List<String> whiteRegList = new ArrayList<String>();
    private static boolean isInit = false;

    @Override
    public List<SensitiveWordDaoObject> thisWordIsSensitiveWord(String wordInfo) {
        SensitiveWordDaoObject sensitiveWordDaoObject;
        if (wordInfo == null || wordInfo.length() <= 0) {
            return new ArrayList<SensitiveWordDaoObject>();
        }
        if (!isInit) {
            this.cacheSensitiveWordMap(this.sensitiveWordDao.queryAllSensitiveWordByWordType(0), this.sensitiveWordDao.queryAllSensitiveWordByWordType(1));
        }
        CheckSensitiveWord checkSensitiveWord = new CheckSensitiveWord();
        ArrayList<SensitiveWordDaoObject> sensitiveWordList = new ArrayList<SensitiveWordDaoObject>();
        if (!sensitiveMap.isEmpty()) {
            List<String> sensitiveWordListTypeOfString = checkSensitiveWord.checkSensitiveOfStringType(wordInfo, sensitiveMap);
            for (String sensitiveWordInfo : sensitiveWordListTypeOfString) {
                sensitiveWordDaoObject = sensitiveWordDaoObjectMapOfString.get(sensitiveWordInfo.toUpperCase());
                sensitiveWordList.add(sensitiveWordDaoObject);
            }
        }
        if (sensitiveRegList.size() > 0) {
            List<String> sensitiveWordListTypeOfReg = checkSensitiveWord.checkSensitiveOfRegType(wordInfo, sensitiveRegList);
            for (String sensitiveWordInfo : sensitiveWordListTypeOfReg) {
                sensitiveWordDaoObject = sensitiveWordDaoObjectMapOfReg.get(sensitiveWordInfo.toUpperCase());
                sensitiveWordList.add(sensitiveWordDaoObject);
            }
        }
        return sensitiveWordList;
    }

    @Override
    public synchronized void cacheSensitiveWordMap(List<SensitiveWordDaoObject> sensitiveWordDaoObjectList, List<SensitiveWordDaoObject> whiteWordDaoObjectList) {
        CheckSensitiveWord checkSensitiveWord = new CheckSensitiveWord();
        ArrayList<String> sensitiveRegInfoList = new ArrayList<String>();
        ArrayList whiteRegInfoList = new ArrayList();
        HashSet<String> sensitiveWordInfoList = new HashSet<String>();
        HashSet whiteWordInfoList = new HashSet();
        for (SensitiveWordDaoObject sensitiveWordDaoObject : sensitiveWordDaoObjectList) {
            if (sensitiveWordDaoObject.getSensitiveType() == 0 && sensitiveWordDaoObject.getIsEffective() == 0) {
                sensitiveWordInfoList.add(sensitiveWordDaoObject.getSensitiveInfo().toUpperCase());
                continue;
            }
            if (sensitiveWordDaoObject.getSensitiveType() != 1 || sensitiveWordDaoObject.getIsEffective() != 0) continue;
            sensitiveRegInfoList.add(sensitiveWordDaoObject.getSensitiveInfo().toUpperCase());
        }
        isInit = true;
        List<SensitiveWordDaoObject> sensitiveWordDaoObjectListOfString = this.sensitiveWordDao.queryAllSensitiveWordByType(0);
        for (SensitiveWordDaoObject sensitiveWordDaoObject : sensitiveWordDaoObjectListOfString) {
            sensitiveWordDaoObjectMapOfString.put(sensitiveWordDaoObject.getSensitiveInfo().toUpperCase(), sensitiveWordDaoObject);
        }
        List<SensitiveWordDaoObject> list = this.sensitiveWordDao.queryAllSensitiveWordByType(1);
        for (SensitiveWordDaoObject sensitiveWordDaoObject : list) {
            sensitiveWordDaoObjectMapOfReg.put(sensitiveWordDaoObject.getSensitiveInfo().toUpperCase(), sensitiveWordDaoObject);
        }
        sensitiveRegList = sensitiveRegInfoList;
        sensitiveMap = checkSensitiveWord.addSensitiveWordMap(sensitiveWordInfoList);
    }

    @EventListener
    public void changeSensitiveWordMap(SensitiveWordDaoObject sensitiveWordDaoObject) {
        try {
            this.cacheSensitiveWordMap(this.sensitiveWordDao.queryAllSensitiveWordByWordType(0), this.sensitiveWordDao.queryAllSensitiveWordByWordType(1));
            if (this.messagePublisher != null) {
                this.messagePublisher.publishMessage(DEFAULT, (Object)"\u654f\u611f\u8bcd\u4fe1\u606f\u5df2\u66f4\u65b0");
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

