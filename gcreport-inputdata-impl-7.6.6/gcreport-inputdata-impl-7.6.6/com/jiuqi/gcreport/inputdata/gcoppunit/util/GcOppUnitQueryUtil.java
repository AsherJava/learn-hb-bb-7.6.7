/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectTreeNodeVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectTreeVO
 */
package com.jiuqi.gcreport.inputdata.gcoppunit.util;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectTreeNodeVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectTreeVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GcOppUnitQueryUtil {
    public static ConsolidatedSubjectTreeNodeVO findSubjectFromTree(ConsolidatedSubjectTreeVO tree, String code) {
        return GcOppUnitQueryUtil.findSubjectFromTree(tree.getTree(), code);
    }

    public static ConsolidatedSubjectTreeNodeVO findSubjectFromTree(List<ConsolidatedSubjectTreeNodeVO> tree, String code) {
        if (tree == null || tree.size() == 0 || StringUtils.isEmpty((String)code)) {
            return null;
        }
        for (ConsolidatedSubjectTreeNodeVO vo : tree) {
            ConsolidatedSubjectTreeNodeVO children;
            if (vo.getCode().equals(code)) {
                return vo;
            }
            if (code.startsWith(vo.getCode())) {
                children = GcOppUnitQueryUtil.findSubjectFromTree(vo.getChildren(), code);
                if (children == null) continue;
                return children;
            }
            children = GcOppUnitQueryUtil.findSubjectFromTree(vo.getChildren(), code);
            if (children == null) continue;
            if (vo.getCode().equals(code)) {
                return vo;
            }
            if (!code.startsWith(children.getCode())) continue;
            return children;
        }
        return null;
    }

    public static List<ConsolidatedSubjectTreeNodeVO> findSubjectFromTree(ConsolidatedSubjectTreeVO tree, List<String> code) {
        ArrayList<ConsolidatedSubjectTreeNodeVO> select = new ArrayList<ConsolidatedSubjectTreeNodeVO>();
        List<String> clearCode = GcOppUnitQueryUtil.formatSubjectCode(code);
        for (String c : clearCode) {
            ConsolidatedSubjectTreeNodeVO vo = GcOppUnitQueryUtil.findSubjectFromTree(tree.getTree(), c);
            select.add(vo);
        }
        return select;
    }

    public static List<String> formatSubjectCode(List<String> code) {
        String[] nc = code.toArray(new String[0]);
        for (int i = 0; i < nc.length; ++i) {
            String b = nc[i];
            if (StringUtils.isEmpty((String)b)) continue;
            for (int j = 0; j < code.size(); ++j) {
                String c = code.get(j);
                if (i == j) continue;
                if (b.startsWith(c)) {
                    nc[i] = c;
                    nc[j] = null;
                    b = c;
                    continue;
                }
                if (!c.startsWith(b)) continue;
                nc[j] = null;
            }
        }
        return Arrays.asList(nc).stream().filter(f -> f != null).collect(Collectors.toList());
    }
}

