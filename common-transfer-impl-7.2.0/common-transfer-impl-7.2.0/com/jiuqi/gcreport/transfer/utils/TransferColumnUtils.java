/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.transfer.cons.WebTableColumn
 *  com.jiuqi.gcreport.transfer.vo.TableFormatEnum
 *  com.jiuqi.gcreport.transfer.vo.TransferColumnVo
 */
package com.jiuqi.gcreport.transfer.utils;

import com.jiuqi.gcreport.transfer.cons.WebTableColumn;
import com.jiuqi.gcreport.transfer.vo.TableFormatEnum;
import com.jiuqi.gcreport.transfer.vo.TransferColumnVo;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TransferColumnUtils {
    public static <T> List<TransferColumnVo> getAllColumns(Class<T> voClass) {
        return TransferColumnUtils.getAllColumns(voClass, true);
    }

    public static <T> List<TransferColumnVo> getShowColumns(Class<T> voClass) {
        return TransferColumnUtils.getAllColumns(voClass, false);
    }

    private static <T> List<TransferColumnVo> getAllColumns(Class<T> voClass, boolean isAll) {
        ArrayList<TransferColumnVo> resutlList = new ArrayList<TransferColumnVo>();
        try {
            Field[] field;
            TransferColumnVo vo;
            WebTableColumn tmpColumn;
            Class<T> supperClass = voClass.getSuperclass();
            if (!supperClass.getName().equals("java.lang.Object")) {
                Field[] parentField;
                for (Field tmpField : parentField = supperClass.getDeclaredFields()) {
                    tmpColumn = tmpField.getAnnotation(WebTableColumn.class);
                    if (tmpColumn == null) continue;
                    vo = new TransferColumnVo();
                    vo.setKey(tmpField.getName());
                    if (tmpColumn.title() == null || tmpColumn.title().trim().length() <= 0) {
                        vo.setLabel(tmpColumn.value());
                    } else {
                        vo.setLabel(tmpColumn.title());
                    }
                    vo.setDefaultShow(tmpColumn.show());
                    vo.setAlign(tmpColumn.align());
                    if (vo.getAlign() == null || vo.getAlign().trim().length() <= 0) {
                        if (tmpField.getType().toString().equals("class java.lang.Double")) {
                            vo.setAlign("right");
                        } else {
                            vo.setAlign("left");
                        }
                    }
                    if (tmpColumn.width() > 0) {
                        vo.setDefaultWidth(Integer.valueOf(tmpColumn.width()));
                    }
                    if (tmpColumn.formatter() != null && tmpColumn.formatter() != TableFormatEnum.STRING) {
                        vo.setDefaultFormatter(tmpColumn.formatter());
                    }
                    if (isAll) {
                        resutlList.add(vo);
                        continue;
                    }
                    if (!tmpColumn.show()) continue;
                    resutlList.add(vo);
                }
            }
            for (Field tmpField : field = voClass.getDeclaredFields()) {
                tmpColumn = tmpField.getAnnotation(WebTableColumn.class);
                if (tmpColumn == null) continue;
                vo = new TransferColumnVo();
                vo.setKey(tmpField.getName());
                if (tmpColumn.title() == null || tmpColumn.title().trim().length() <= 0) {
                    vo.setLabel(tmpColumn.value());
                } else {
                    vo.setLabel(tmpColumn.title());
                }
                vo.setAlign(tmpColumn.align());
                if ((vo.getAlign() == null || vo.getAlign().trim().length() <= 0) && tmpField.getType().toString().equals("class java.lang.Double")) {
                    vo.setAlign("right");
                }
                vo.setDefaultShow(tmpColumn.show());
                if (tmpColumn.width() > 0) {
                    vo.setDefaultWidth(Integer.valueOf(tmpColumn.width()));
                }
                if (tmpColumn.formatter() != null && tmpColumn.formatter() != TableFormatEnum.STRING) {
                    vo.setDefaultFormatter(tmpColumn.formatter());
                }
                if (isAll) {
                    resutlList.add(vo);
                    continue;
                }
                if (!tmpColumn.show()) continue;
                resutlList.add(vo);
            }
            return resutlList;
        }
        catch (Exception e) {
            throw new RuntimeException("\u83b7\u53d6\u9700\u8981" + voClass.getName() + "\u6240\u6709\u5217\u51fa\u73b0\u5f02\u5e38\uff01", e);
        }
    }
}

