/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.utils;

import com.jiuqi.dc.base.common.utils.CopyCallBack;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;

public class BeanCopyUtil {
    public static <S, T> T copyObj(Class<T> targetClazz, S source) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClazz.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public static <S, T> T copyObj(Class<T> targetClazz, S source, CopyCallBack<T> callBack) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClazz.newInstance();
            BeanUtils.copyProperties(source, target);
            if (callBack != null) {
                callBack.doOtherSettings(target);
            }
            return target;
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public static <S, T> List<T> copyObj(Class<T> targetClazz, List<S> sourceList) {
        if (sourceList == null || sourceList.size() == 0) {
            return new ArrayList();
        }
        try {
            ArrayList<T> resultList = new ArrayList<T>();
            for (S source : sourceList) {
                T target = targetClazz.newInstance();
                BeanUtils.copyProperties(source, target);
                resultList.add(target);
            }
            return resultList;
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public static <S, T> List<T> copyObj(Class<T> targetClazz, List<S> sourceList, CopyCallBack<T> callBack) {
        if (sourceList == null || sourceList.size() == 0) {
            return new ArrayList();
        }
        try {
            ArrayList<T> resultList = new ArrayList<T>();
            for (S source : sourceList) {
                T target = targetClazz.newInstance();
                BeanUtils.copyProperties(source, target);
                if (callBack != null) {
                    callBack.doOtherSettings(target);
                }
                resultList.add(target);
            }
            return resultList;
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}

