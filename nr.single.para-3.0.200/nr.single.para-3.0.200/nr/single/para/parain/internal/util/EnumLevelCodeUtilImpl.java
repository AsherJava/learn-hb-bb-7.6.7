/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.single.para.parain.internal.util;

import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nr.single.para.parain.util.IEnumLevelCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumLevelCodeUtilImpl
implements IEnumLevelCodeUtil {
    private static final Logger log = LoggerFactory.getLogger(EnumLevelCodeUtilImpl.class);
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    public String getLevelParentCode(String levelCode, String structure, boolean sameWidth) throws Exception {
        if (StringUtils.isEmpty((String)levelCode)) {
            return null;
        }
        List<Integer> levelLengths = this.getLevelLengths(structure);
        Integer codeLength = levelLengths.get(levelLengths.size() - 1);
        String resultCode = this.getUpperLevelCode(levelCode, levelLengths, codeLength, sameWidth);
        return resultCode;
    }

    @Override
    public String getLevelParentCode(String levelCode, String structure) throws Exception {
        if (StringUtils.isEmpty((String)levelCode)) {
            return null;
        }
        String resultCode = null;
        List<Integer> levelLengths = this.getLevelLengths(structure);
        int levelIndex = this.getCurLevelIndex(levelCode, levelLengths);
        if (levelLengths.size() > 0) {
            resultCode = "";
            Integer codeLength = levelLengths.get(levelLengths.size() - 1);
            int parentLen = 0;
            if (levelIndex > 1) {
                parentLen = levelLengths.get(levelIndex - 2);
            }
            if ((resultCode = levelCode.substring(0, parentLen)).length() < codeLength) {
                int len = codeLength - resultCode.length();
                for (int j = 0; j < len; ++j) {
                    resultCode = resultCode + "0";
                }
            }
        }
        return resultCode;
    }

    @Override
    public int getLevelCodeLevel(String enumItemCode, String structure, boolean sameWidth) throws Exception {
        if (StringUtils.isEmpty((String)enumItemCode)) {
            return 0;
        }
        List<Integer> levelLengths = this.getLevelLengths(structure);
        return this.getCurLevelIndex(enumItemCode, levelLengths);
    }

    @Override
    public String getLevelRecode(String parentItemCode, int codeLen, String structure, boolean sameWidth, Set<String> codeList) throws Exception {
        int j;
        int len1;
        List<Object> levelLengths = new ArrayList();
        if (StringUtils.isNotEmpty((String)structure)) {
            levelLengths = this.getLevelLengths(structure);
        }
        if (levelLengths.size() <= 1) {
            ArrayList<String> list1 = new ArrayList<String>(codeList);
            Collections.sort(list1);
            String code1 = (String)list1.get(list1.size() - 1);
            int id = Integer.parseInt(code1);
            code1 = String.valueOf(id + 1);
            if (code1.length() < codeLen) {
                int len = codeLen - code1.length();
                for (int j2 = 0; j2 < len; ++j2) {
                    code1 = "0" + code1;
                }
            }
            if (code1.length() > codeLen || codeList.contains(code1)) {
                int maxNum = (int)Math.pow(10.0, codeLen);
                for (int i = 1; i < maxNum; ++i) {
                    code1 = String.valueOf(i);
                    if (code1.length() < codeLen) {
                        int len = codeLen - code1.length();
                        for (int j3 = 0; j3 < len; ++j3) {
                            code1 = "0" + code1;
                        }
                    }
                    if (!codeList.contains(code1)) break;
                }
            }
            if (code1.length() < codeLen) {
                log.info("\u91cd\u7f16\u7801\u9519\u8bef\uff1a" + code1);
            }
            return code1;
        }
        Integer codeLength = (Integer)levelLengths.get(levelLengths.size() - 1);
        int parentLevel = this.getCurLevelIndex(parentItemCode, levelLengths);
        int parentLen = 0;
        if (parentLevel > 0) {
            parentLen = (Integer)levelLengths.get(parentLevel - 1);
        }
        String onlyParentCode = parentItemCode.substring(0, parentLen);
        int childLen = parentLen;
        if (parentLevel < levelLengths.size()) {
            childLen = (Integer)levelLengths.get(parentLevel);
        }
        int onlyChildLen = childLen - parentLen;
        int otherLen = codeLength - childLen;
        HashSet<String> childList = new HashSet<String>();
        for (String code : codeList) {
            if (StringUtils.isEmpty((String)code)) continue;
            String onlyParentCode1 = code;
            if (parentLen <= code.length()) {
                onlyParentCode1 = code.substring(0, parentLen);
            }
            if (!onlyParentCode1.equalsIgnoreCase(onlyParentCode)) continue;
            if (parentLen >= 0 && parentLen <= childLen) {
                if (childLen <= code.length()) {
                    String code1 = code.substring(parentLen, childLen);
                    if (childList.contains(code1)) continue;
                    childList.add(code1);
                    continue;
                }
                log.info("\u91cd\u65b0\u7f16\u7801\u9519\u8bef");
                continue;
            }
            log.info("\u91cd\u65b0\u7f16\u7801\u9519\u8bef");
        }
        ArrayList list1 = new ArrayList(childList);
        Collections.sort(list1);
        String code1 = null;
        if (list1.size() > 0) {
            code1 = (String)list1.get(list1.size() - 1);
        }
        boolean isNum = true;
        int id = 0;
        try {
            if (StringUtils.isNotEmpty(code1)) {
                id = Integer.parseInt(code1);
            }
        }
        catch (Exception ex) {
            log.info(ex.getMessage());
            isNum = false;
        }
        if (isNum) {
            code1 = String.valueOf(id + 1);
            boolean needReCode = false;
            if (code1.length() > onlyChildLen) {
                needReCode = true;
            } else {
                int len12 = code1.length();
                for (int j4 = 0; j4 < onlyChildLen - len12; ++j4) {
                    code1 = "0" + code1;
                }
                code1 = onlyParentCode + code1;
                if (code1.length() < codeLength) {
                    int len = codeLength - code1.length();
                    for (int j5 = 0; j5 < len; ++j5) {
                        code1 = code1 + "0";
                    }
                    if (code1.length() < codeLength) {
                        log.info("\u91cd\u7f16\u7801\u9519\u8bef\uff1a" + code1);
                    }
                }
                if (code1.length() > codeLen || codeList.contains(code1)) {
                    needReCode = true;
                }
            }
            if (needReCode) {
                int maxChildNum = (int)Math.pow(10.0, onlyChildLen);
                for (int i = 1; i < maxChildNum; ++i) {
                    String childCode = String.valueOf(i);
                    len1 = childCode.length();
                    for (j = 0; j < onlyChildLen - len1; ++j) {
                        childCode = "0" + childCode;
                    }
                    childCode = onlyParentCode + childCode;
                    for (j = 0; j < otherLen; ++j) {
                        childCode = childCode + "0";
                    }
                    if (codeList.contains(childCode)) continue;
                    if (childCode.length() < codeLength) {
                        log.info("\u91cd\u7f16\u7801\u9519\u8bef\uff1a" + childCode);
                    }
                    return childCode;
                }
                isNum = false;
            }
        }
        if (!isNum) {
            int maxChildNum = (int)Math.pow(36.0, onlyChildLen);
            for (int i = 1; i < maxChildNum; ++i) {
                String childCode = "";
                for (int order = i; order > 0; order /= 36) {
                    childCode = String.valueOf(CHARS.charAt(order % 36)) + childCode;
                }
                len1 = childCode.length();
                for (j = 0; j < onlyChildLen - len1; ++j) {
                    childCode = "0" + childCode;
                }
                for (j = 0; j < otherLen; ++j) {
                    childCode = childCode + "0";
                }
                if (codeList.contains(childCode)) continue;
                if (childCode.length() < codeLength) {
                    log.info("\u91cd\u7f16\u7801\u9519\u8bef\uff1a" + childCode);
                }
                return childCode;
            }
        }
        if (code1.length() < codeLen) {
            log.info("\u91cd\u7f16\u7801\u9519\u8bef\uff1a" + code1);
        }
        return code1;
    }

    @Override
    public int getCurLevelIndex(String codeData, List<Integer> levelLengths) {
        boolean isNotZero = false;
        int length = codeData.length();
        while (length > 0) {
            char ch;
            if ((ch = codeData.charAt(--length)) == '0') continue;
            isNotZero = true;
            break;
        }
        if (isNotZero) {
            Integer i = 0;
            while (i <= levelLengths.size() - 1) {
                int len = levelLengths.get(i);
                if (len > length) {
                    return i + 1;
                }
                Integer n = i;
                Integer n2 = i = Integer.valueOf(i + 1);
            }
        }
        return 0;
    }

    private String getUpperLevelCode(String codeData, List<Integer> levelLengths, Integer codeLength, Boolean sameWidth) {
        int length = codeData.length();
        Integer i = levelLengths.size() - 1;
        while (i >= 0) {
            int len = levelLengths.get(i);
            boolean hasFind = false;
            while (length > len) {
                char ch;
                hasFind = true;
                if ((ch = codeData.charAt(--length)) == '0') continue;
                return sameWidth != false ? this.getWidthCode(codeData, codeLength, len) : codeData.substring(0, len);
            }
            if (hasFind && !sameWidth.booleanValue()) {
                return codeData.substring(0, length);
            }
            Integer n = i;
            Integer n2 = i = Integer.valueOf(i - 1);
        }
        return "";
    }

    private String getWidthCode(String codeData, int codeLength, int len) {
        int i;
        char[] buffer = new char[codeLength];
        for (i = 0; i < len; ++i) {
            buffer[i] = codeData.charAt(i);
        }
        for (i = len; i < codeLength; ++i) {
            buffer[i] = 48;
        }
        return new String(buffer);
    }

    @Override
    public List<Integer> getLevelLengths(Object value) throws Exception {
        if (value == null || StringUtils.isEmpty((String)value.toString())) {
            throw new Exception("\u7f16\u7801\u7ed3\u6784\u65e0\u6548\uff0c\u5e94\u8be5\u4e3a\u9017\u53f7\u5206\u9694\u7684\u5bbd\u5ea6\u5217\u8868\uff0c\u5f62\u5982\u201c2,2,2\u201d");
        }
        String structure = value.toString();
        ArrayList<Integer> levelArray = new ArrayList<Integer>();
        try {
            String[] levelObjs = structure.split(",|;");
            int codeLength = 0;
            for (String levelObj : levelObjs) {
                int level = Integer.parseInt(levelObj);
                if (level <= 0) {
                    throw new Exception("\u7f16\u7801\u7ed3\u6784\u65e0\u6548\uff1a\u7f16\u7801\u5bbd\u5ea6\u5e94\u5927\u4e8e0");
                }
                levelArray.add(codeLength += level);
            }
        }
        catch (Exception e) {
            throw new Exception("\u7f16\u7801\u7ed3\u6784\u65e0\u6548\uff1a" + e.getMessage());
        }
        return levelArray;
    }
}

