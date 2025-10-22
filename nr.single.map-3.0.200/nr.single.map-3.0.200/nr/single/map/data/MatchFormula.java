/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatchFormula {
    private static final Logger logger = LoggerFactory.getLogger(MatchFormula.class);

    public String convertErrorItem(String netExp, String mappingExp) {
        if (netExp == null) {
            return mappingExp;
        }
        if (mappingExp == null) {
            return netExp;
        }
        if (!netExp.equals(mappingExp)) {
            netExp = this.getnbErrorExp(mappingExp, netExp);
        }
        return netExp;
    }

    private String getnbErrorExp(String targetExp, String sourceExp) {
        StringBuffer ret = new StringBuffer("");
        targetExp = this.castTargetExp(targetExp);
        String[] nbExpArr = targetExp.split("\\[");
        String[] errorExpArr = sourceExp.split("\\[");
        List<String> list = this.getExps(errorExpArr);
        ret.append(nbExpArr[0]);
        List<String> tarList = this.getExps(nbExpArr);
        int rowValue = this.calCulateRowStar(list, tarList);
        int colValue = this.calCulateColStar(list, tarList);
        for (int i = 1; i < nbExpArr.length; ++i) {
            String str = nbExpArr[i];
            char c = str.trim().charAt(0);
            if (list.size() > 0 && (c <= '9' && c >= '0' || c == '*')) {
                str = this.onlyReplaceStar(list.get(0), str, rowValue, colValue);
                list.remove(0);
            }
            ret.append("[");
            ret.append(str);
        }
        return ret.toString();
    }

    public List<String> getExps(String exp) {
        String[] errorExpArr = exp.split("\\[");
        return this.getExps(errorExpArr);
    }

    public List<String> getExps(String[] errorExpArr) {
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
                logger.error(e.getMessage(), e);
            }
            list.add(cell);
        }
        return list;
    }

    public int calCulateRowStar(List<String> srcValue, List<String> tarValue) {
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

    public int calCulateColStar(List<String> srcValue, List<String> tarValue) {
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
        return str;
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

