/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchFormula {
    private static final Pattern pattern = Pattern.compile("\\[(.*?)\\]");

    public String convertErrorItem(String netExp, String mappingExp) {
        if (netExp == null) {
            return mappingExp;
        }
        if (mappingExp == null) {
            return netExp;
        }
        if (!netExp.equals(mappingExp)) {
            netExp = this.getNbErrorExp(mappingExp, netExp);
        }
        return netExp;
    }

    public int[] praseWildcard(String mappingExp, String compileNetExp) {
        int[] wildcardResult = new int[]{-1, -1};
        if (mappingExp == null || compileNetExp == null || !mappingExp.contains("*")) {
            return wildcardResult;
        }
        String rowColStarValue = null;
        int starValue = -1;
        List<String> mappingNetExpArr = this.extractBracketContents(mappingExp);
        List<String> compileNetExpArr = this.extractBracketContents(compileNetExp);
        for (int i = 0; i < mappingNetExpArr.size(); ++i) {
            String mappingNetContent = mappingNetExpArr.get(i);
            String[] complieContentArr = compileNetExpArr.get(i).split(",");
            if (mappingNetContent.matches("^[1-9]\\d*$")) {
                if (!complieContentArr[0].equals(mappingNetContent)) {
                    wildcardResult[0] = starValue = Integer.parseInt(complieContentArr[0]);
                    return wildcardResult;
                }
                if (!complieContentArr[1].equals(mappingNetContent)) {
                    wildcardResult[1] = starValue = Integer.parseInt(complieContentArr[1]);
                    return wildcardResult;
                }
            } else if (mappingNetContent.contains("*")) {
                if (mappingNetContent.equals("*,*")) {
                    rowColStarValue = compileNetExpArr.get(i);
                    String[] wild = rowColStarValue.split(",");
                    wildcardResult[0] = Integer.valueOf(wild[0]);
                    wildcardResult[1] = Integer.valueOf(wild[1]);
                    return wildcardResult;
                }
                if (mappingNetContent.indexOf("*") == 0) {
                    wildcardResult[0] = starValue = Integer.parseInt(complieContentArr[0]);
                    return wildcardResult;
                }
                wildcardResult[1] = starValue = Integer.parseInt(complieContentArr[1]);
                return wildcardResult;
            }
            if (starValue != -1) break;
        }
        return wildcardResult;
    }

    public String convertErrorItem(String mappingNetExp, String mappingPtExp, String compileNetExp) {
        if (compileNetExp == null) {
            return mappingPtExp;
        }
        if (mappingPtExp == null || mappingNetExp == null) {
            return compileNetExp;
        }
        String result = compileNetExp;
        if (!compileNetExp.equals(mappingPtExp)) {
            boolean rowStar = false;
            boolean colStar = false;
            boolean rowColStar = false;
            String rowColStarValue = null;
            int starValue = -1;
            List<String> mappingNetExpArr = this.extractBracketContents(mappingNetExp);
            List<String> compileNetExpArr = this.extractBracketContents(compileNetExp);
            for (int i = 0; i < mappingNetExpArr.size(); ++i) {
                String mappingNetContent = mappingNetExpArr.get(i);
                String[] complieContentArr = compileNetExpArr.get(i).split(",");
                if (mappingNetContent.matches("^[1-9]\\d*$")) {
                    if (!complieContentArr[0].equals(mappingNetContent)) {
                        starValue = Integer.parseInt(complieContentArr[0]);
                        rowStar = true;
                    }
                    if (!complieContentArr[1].equals(mappingNetContent)) {
                        starValue = Integer.parseInt(complieContentArr[1]);
                        colStar = true;
                    }
                } else if (mappingNetContent.contains("*")) {
                    if (mappingNetContent.equals("*,*")) {
                        rowColStar = true;
                        rowColStarValue = compileNetExpArr.get(i);
                    } else {
                        starValue = mappingNetContent.indexOf("*") == 0 ? Integer.parseInt(complieContentArr[0]) : Integer.parseInt(complieContentArr[1]);
                    }
                }
                if (starValue != -1) break;
            }
            Matcher matcher = pattern.matcher(mappingPtExp);
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                String originalContent = matcher.group(1);
                String newContent = originalContent.replace("*", String.valueOf(starValue));
                if (rowStar) {
                    newContent = starValue + "," + originalContent;
                }
                if (colStar) {
                    newContent = originalContent + "," + starValue;
                }
                if (rowColStar) {
                    newContent = rowColStarValue;
                }
                matcher.appendReplacement(sb, "[" + newContent + "]");
            }
            matcher.appendTail(sb);
            result = sb.toString();
            result = result.replaceAll("(\\{[^}]*\\})+$", "");
        }
        return result;
    }

    public List<String> extractBracketContents(String input) {
        ArrayList<String> contents = new ArrayList<String>();
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            contents.add(matcher.group(1));
        }
        return contents;
    }

    private String getNbErrorExp(String targetExp, String sourceExp) {
        String[] errorExpArr;
        String[] nbExpArr = (targetExp = this.castTargetExp(targetExp)).split("\\[");
        if (nbExpArr.length == (errorExpArr = sourceExp.split("\\[")).length) {
            return this.getNbErrorExpInternal(nbExpArr, errorExpArr);
        }
        return this.getNbErrorExpInternal(nbExpArr, errorExpArr);
    }

    private String getNbErrorExpInternal(String[] nbExpArr, String[] errorExpArr) {
        StringBuffer ret = new StringBuffer("");
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 1; i < errorExpArr.length; ++i) {
            String str = errorExpArr[i];
            String cell = "";
            char c = str.trim().charAt(0);
            if ((c > '9' || c < '0') && c != '*') continue;
            try {
                int index = str.indexOf(93);
                cell = index > 0 ? str.substring(0, index) : str;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            list.add(cell);
        }
        ret.append(nbExpArr[0]);
        ArrayList<String> tarList = new ArrayList<String>();
        for (int i = 1; i < nbExpArr.length; ++i) {
            String str = nbExpArr[i];
            String cell = "";
            char c = str.trim().charAt(0);
            if ((c > '9' || c < '0') && c != '*') continue;
            try {
                int index = str.indexOf(93);
                cell = index > 0 ? str.substring(0, index) : str;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            tarList.add(cell);
        }
        int rowValue = this.calCulateRowStar(list, tarList);
        int colValue = this.calCulateColStar(list, tarList);
        for (int i = 1; i < nbExpArr.length; ++i) {
            String str = nbExpArr[i];
            char c = str.trim().charAt(0);
            if (list.size() > 0 && (c <= '9' && c >= '0' || c == '*')) {
                str = this.onlyReplaceStar((String)list.get(0), str, rowValue, colValue);
                list.remove(0);
            }
            ret.append("[");
            ret.append(str);
        }
        return ret.toString();
    }

    private int calCulateRowStar(List<String> srcValue, List<String> tarValue) {
        for (int i = 0; i < tarValue.size(); ++i) {
            if (srcValue.size() <= i) continue;
            String srcRowValue = this.getRowValue(srcValue.get(i));
            String tarRowValue = this.getRowValue(tarValue.get(i));
            if (!tarRowValue.trim().equals("*")) continue;
            if (!this.isDigit(srcRowValue)) {
                char c;
                StringBuffer sb = new StringBuffer();
                for (int ka = 0; ka < srcRowValue.length() && (c = srcRowValue.trim().charAt(ka)) <= '9' && c >= '0'; ++ka) {
                    sb.append(c);
                }
                srcRowValue = sb.toString();
            }
            try {
                return Integer.valueOf(srcRowValue.trim());
            }
            catch (Exception e) {
                return -1;
            }
        }
        return -1;
    }

    private int calCulateColStar(List<String> srcValue, List<String> tarValue) {
        for (int i = 0; i < tarValue.size(); ++i) {
            if (srcValue.size() <= i) continue;
            String srcColValue = this.getColValue(srcValue.get(i));
            String tarColValue = this.getColValue(tarValue.get(i));
            if (!tarColValue.trim().equals("*")) continue;
            if (!this.isDigit(srcColValue)) {
                char c;
                StringBuffer sb = new StringBuffer();
                for (int ka = 0; ka < srcColValue.length() && (c = srcColValue.trim().charAt(ka)) <= '9' && c >= '0'; ++ka) {
                    sb.append(c);
                }
                tarColValue = sb.toString();
            }
            try {
                return Integer.valueOf(srcColValue.trim());
            }
            catch (Exception e) {
                return -1;
            }
        }
        return -1;
    }

    private String getRowValue(String str) {
        int first = str.indexOf(44);
        if (first > 0) {
            String value = str.substring(0, first);
            if (value == null || value.trim().length() == 0) {
                return "*";
            }
            return value;
        }
        return "*";
    }

    private String getColValue(String str) {
        int first = str.indexOf(44);
        if (first > 0) {
            int second = str.indexOf(",", first + 1);
            int third = str.indexOf("]");
            if (third > 0 && third < second) {
                second = third;
            }
            if (second > 0 && first != str.length()) {
                String value = str.substring(first + 1, second);
                if (value == null || value.trim().length() == 0) {
                    return "*";
                }
                return value;
            }
            String value = str.substring(first + 1);
            if (value == null || value.trim().length() == 0) {
                return "*";
            }
            return value;
        }
        return "*";
    }

    private String onlyReplaceStar(String src, String tar, int rowStarValue, int colStarValue) {
        if (colStarValue < 0 && rowStarValue < 0) {
            return tar;
        }
        StringBuffer ret = new StringBuffer("");
        int rightMark = src.indexOf("]");
        if (rightMark > 0) {
            src = src.substring(0, rightMark);
        }
        String[] srcData = src.split(",");
        rightMark = tar.indexOf("]");
        String rest = "";
        if (rightMark > 0) {
            rest = tar.substring(rightMark);
            tar = tar.substring(0, rightMark);
        }
        String[] tarData = tar.split(",");
        if (rowStarValue >= 0 && !this.isDigit(tarData[0])) {
            if (tarData[0].contains("*")) {
                ret.append(tarData[0].replace("*", String.valueOf(rowStarValue)));
            } else {
                ret.append(srcData[0]);
            }
        } else {
            ret.append(srcData[0]);
        }
        ret.append(",");
        if (tarData.length > 1 && (this.isDigit(tarData[1]) || tarData[1].contains("*"))) {
            if (colStarValue >= 0 && !this.isDigit(tarData[1])) {
                if (tarData[1].contains("*")) {
                    ret.append(tarData[1].replace("*", String.valueOf(colStarValue)));
                } else {
                    ret.append(String.valueOf(colStarValue));
                }
            } else {
                ret.append(tarData[1]);
            }
        } else if (tarData.length == 1 && colStarValue >= 0) {
            ret.append(String.valueOf(colStarValue));
        }
        if (tarData.length > 2) {
            for (int i = 2; i < tarData.length; ++i) {
                ret.append(",").append(tarData[i]);
            }
        }
        ret.append(rest);
        return ret.toString();
    }

    private boolean isDigit(String str) {
        try {
            Integer.valueOf(str);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private String castTargetExp(String str) {
        int idx = str.indexOf(123);
        if (idx <= 0) {
            return str;
        }
        StringBuffer sb = new StringBuffer(2000);
        sb.append(str.substring(0, idx));
        sb.append(str.substring(str.indexOf(125) + 1));
        return this.castTargetExp(sb.toString());
    }

    public String doMatchConf(String sourceExp, boolean isDown, String matchConf) {
        String[] matchConfArr = null;
        List<MatchConf> confList = null;
        String ret = "";
        if (matchConf != null && !"".equals(matchConf.trim())) {
            MatchConf conf;
            matchConfArr = matchConf.split("\\{");
            confList = new ArrayList<MatchConf>();
            for (int i = 0; i < matchConfArr.length; ++i) {
                String str = matchConfArr[i];
                if (str.indexOf("}") < 0) continue;
                str = str.substring(0, str.indexOf("}"));
                String[] confAttr = str.split(",");
                conf = new MatchConf();
                conf.zbIdx = Integer.parseInt(confAttr[0]);
                conf.paraIdx = Integer.parseInt(confAttr[1]);
                conf.condition = confAttr[2];
                conf.direction = confAttr.length == 4 ? Integer.parseInt(confAttr[3]) : 1;
                confList.add(conf);
            }
            confList = this.sort(confList);
            String[] expArr = MatchFormula.getExpArr(sourceExp);
            for (int i = 0; i < confList.size(); ++i) {
                int s;
                boolean needSplit;
                StringBuffer zbSb = new StringBuffer();
                conf = confList.get(i);
                String zbExp = expArr[conf.zbIdx * 2 - 1];
                String[] zbParaArr = zbExp.split(",");
                if (isDown) {
                    if (conf.direction > 0) {
                        needSplit = false;
                        for (s = 0; s < zbParaArr.length; ++s) {
                            if (s == conf.paraIdx - 1) continue;
                            if (needSplit) {
                                zbSb.append(",");
                            }
                            zbSb.append(zbParaArr[s]);
                            needSplit = true;
                        }
                    } else {
                        needSplit = false;
                        for (s = 0; s < zbParaArr.length; ++s) {
                            if (needSplit) {
                                zbSb.append(",");
                            }
                            if (s == conf.paraIdx) {
                                zbSb.append(conf.condition);
                                zbSb.append(",");
                            }
                            zbSb.append(zbParaArr[s]);
                            needSplit = true;
                        }
                        if (conf.paraIdx >= zbParaArr.length) {
                            zbSb.append(",").append(conf.condition);
                        }
                    }
                } else if (conf.direction < 0) {
                    needSplit = false;
                    for (s = 0; s < zbParaArr.length; ++s) {
                        if (s == conf.paraIdx - 1) continue;
                        if (needSplit) {
                            zbSb.append(",");
                        }
                        zbSb.append(zbParaArr[s]);
                        needSplit = true;
                    }
                } else {
                    needSplit = false;
                    for (s = 0; s < zbParaArr.length; ++s) {
                        if (needSplit) {
                            zbSb.append(",");
                        }
                        if (s == conf.paraIdx) {
                            zbSb.append(conf.condition);
                            zbSb.append(",");
                        }
                        zbSb.append(zbParaArr[s]);
                        needSplit = true;
                    }
                    if (conf.paraIdx > zbParaArr.length) {
                        zbSb.append(",").append(conf.condition);
                    }
                }
                expArr[conf.zbIdx * 2 - 1] = zbSb.toString();
            }
            StringBuffer retSB = new StringBuffer();
            for (int i = 0; i < expArr.length; ++i) {
                retSB.append(expArr[i]);
            }
            ret = retSB.toString();
        } else {
            ret = sourceExp;
        }
        return ret;
    }

    public List<MatchConf> sort(List<MatchConf> matchConfList) {
        ArrayList<MatchConf> list = new ArrayList<MatchConf>();
        for (int i = 0; i < matchConfList.size(); ++i) {
            MatchConf conf = matchConfList.get(i);
            if (i == 0) {
                list.add(conf);
                continue;
            }
            for (int j = list.size() - 1; j >= 0; --j) {
                MatchConf conf1 = (MatchConf)list.get(j);
                if (conf.compare(conf1)) continue;
                list.add(j, conf);
            }
        }
        return list;
    }

    private static String[] getExpArr(String sourceExp) {
        ArrayList<String> expList = new ArrayList<String>();
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < sourceExp.length(); ++i) {
            char charAt = sourceExp.charAt(i);
            if (charAt == '[') {
                sb.append(charAt);
                expList.add(sb.toString());
                sb = new StringBuffer();
                continue;
            }
            if (charAt == ']') {
                expList.add(sb.toString());
                sb = new StringBuffer();
                sb.append(charAt);
                continue;
            }
            sb.append(charAt);
        }
        expList.add(sb.toString());
        String[] expArr = new String[expList.size()];
        for (int i = 0; i < expArr.length; ++i) {
            expArr[i] = (String)expList.get(i);
        }
        return expArr;
    }

    public class MatchConf {
        int zbIdx;
        int paraIdx;
        int direction;
        String condition;

        protected boolean compare(MatchConf target) {
            if (target == null) {
                return true;
            }
            return this.zbIdx <= target.zbIdx && this.paraIdx < target.paraIdx;
        }
    }
}

