/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  net.sourceforge.pinyin4j.format.HanyuPinyinCaseType
 */
package com.jiuqi.nr.pinyin.util;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.pinyin.entity.WordEntity;
import com.jiuqi.nr.pinyin.util.HanyuPinyinHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class CacheUtil
implements Serializable {
    private NedisCacheManager cacheManager;
    private static final Logger logger = LoggerFactory.getLogger(CacheUtil.class);
    private static final String CACHENAME = "PINYIN_TASK";
    private static final long serialVersionUID = 1L;

    @Autowired
    public void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager();
    }

    public void createOrUpdate(String hanzi) {
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        WordEntity wordEntity = this.formatHanzi(hanzi);
        caffeineCache.put(wordEntity.getPingyin(), (Object)wordEntity.getHanziList());
        List<String> allPinyin = new ArrayList();
        Cache.ValueWrapper valueWrappers = caffeineCache.get("TheAllPinyin");
        if (valueWrappers != null && !(allPinyin = (List)valueWrappers.get()).contains(wordEntity.getPingyin())) {
            allPinyin.add(wordEntity.getPingyin());
            caffeineCache.put("TheAllPinyin", allPinyin);
        }
    }

    public List<String> findHanziByPinyin(String pinyin) {
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        List<String> hanziList = new ArrayList<String>();
        Cache.ValueWrapper valueWrapper = caffeineCache.get(pinyin);
        if (valueWrapper != null) {
            hanziList = (List)valueWrapper.get();
        }
        return hanziList;
    }

    public List<String> findLikeHanziByPinyin(String pinyin) {
        int i;
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        ArrayList<String> hanziList = new ArrayList<String>();
        List pinyinsList = new ArrayList();
        ArrayList conpinyin = new ArrayList();
        Cache.ValueWrapper valueWrappers = caffeineCache.get("TheAllPinyin");
        if (valueWrappers != null) {
            pinyinsList = (List)valueWrappers.get();
        }
        for (i = 0; i < pinyinsList.size(); ++i) {
            if (!((String)pinyinsList.get(i)).contains(pinyin)) continue;
            conpinyin.add(pinyinsList.get(i));
        }
        for (i = 0; i < conpinyin.size(); ++i) {
            List<String> subHanziList = this.findHanziByPinyin((String)conpinyin.get(i));
            for (int j = 0; j < subHanziList.size(); ++j) {
                hanziList.add(subHanziList.get(j));
            }
        }
        return hanziList;
    }

    public void deletePinyin(String pinyin) throws Exception {
        if ("".equals(pinyin) || null == pinyin) {
            throw new Exception("\u8981\u5220\u9664\u7684\u62fc\u97f3\u5bf9\u8c61\u4e3a\u7a7a");
        }
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        caffeineCache.evict(pinyin);
        List allPinyin = new ArrayList();
        Cache.ValueWrapper valueWrappers = caffeineCache.get("TheAllPinyin");
        if (valueWrappers != null) {
            allPinyin = (List)valueWrappers.get();
            for (String curPinyin : allPinyin) {
                String[] pinyinArray;
                if (!curPinyin.contains(pinyin) || !pinyin.equals((pinyinArray = pinyin.split(";"))[0])) continue;
                allPinyin.remove(curPinyin);
                break;
            }
            caffeineCache.put("TheAllPinyin", allPinyin);
        }
    }

    public void deleteHanzi(String hanzi) {
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        WordEntity wordEntity = this.formatHanzi(hanzi);
        List subListHanzi = new ArrayList();
        Cache.ValueWrapper valueWrappers = caffeineCache.get(wordEntity.getPingyin());
        if (valueWrappers != null) {
            subListHanzi = (List)valueWrappers.get();
            if (subListHanzi.contains(hanzi)) {
                subListHanzi.remove(hanzi);
            }
            if (subListHanzi.size() < 1) {
                caffeineCache.evict(wordEntity.getPingyin());
            }
        }
    }

    public void clear() {
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        caffeineCache.clear();
    }

    public void initCache(List<String> allHanzi) {
        this.clear();
        ArrayList<String> allpinyin = new ArrayList<String>();
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        WordEntity wordEntity = null;
        for (int i = 0; i < allHanzi.size(); ++i) {
            wordEntity = this.formatHanzi(allHanzi.get(i));
            if (wordEntity == null) continue;
            caffeineCache.put(wordEntity.getPingyin(), (Object)wordEntity.getHanziList());
            if (allpinyin.contains(wordEntity.getPingyin())) continue;
            allpinyin.add(wordEntity.getPingyin());
        }
        caffeineCache.put("TheAllPinyin", allpinyin);
        logger.info("\u521d\u59cb\u5316\u62fc\u97f3\u7f13\u5b58");
    }

    public WordEntity formatHanzi(String hanzi) {
        WordEntity wordEntity = new WordEntity();
        String pinyin = HanyuPinyinHelper.toHanyuPinyin(hanzi);
        String firstpinyin = HanyuPinyinHelper.getFirstLetters(hanzi, HanyuPinyinCaseType.LOWERCASE);
        List<String> subHanziList = this.findHanziByPinyin(pinyin + ";" + firstpinyin);
        if (subHanziList.contains(hanzi)) {
            return null;
        }
        subHanziList.add(hanzi);
        wordEntity.setPingyin(pinyin + ";" + firstpinyin);
        wordEntity.setHanziList(subHanziList);
        return wordEntity;
    }
}

