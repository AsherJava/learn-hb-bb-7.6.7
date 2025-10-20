/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.html.message;

import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class MessageParser {
    public static final String NAME_SEPARATOR = ".";
    @Deprecated
    public static final String nameSeparator = ".";
    private List<ParamItem> paramList = new ArrayList<ParamItem>();
    private String paramDelimiter = "||";
    private String valueDelimiter = "@";
    private String newStr = "|";
    private String mutiValueDelimiter = "|";

    public MessageParser() {
    }

    public MessageParser(String msg) {
        this.parse(msg);
    }

    public String getParamDelimiter() {
        return this.paramDelimiter;
    }

    public String getValueDelimiter() {
        return this.valueDelimiter;
    }

    public void setParamDelimiter(String paramDelimiter) {
        this.paramDelimiter = paramDelimiter;
    }

    public void setValueDelimiter(String valueDelimiter) {
        this.valueDelimiter = valueDelimiter;
    }

    public void parse(String msg) {
        String[] paramItem = MessageParser.splitWithSpaceElement(msg, this.paramDelimiter);
        LinkedHashMap<String, ParamItem> map = new LinkedHashMap<String, ParamItem>();
        for (int i = 0; i < paramItem.length; ++i) {
            if (StringUtils.isEmpty((String)paramItem[i])) continue;
            int n = paramItem[i].indexOf(this.valueDelimiter);
            int len = this.valueDelimiter.length();
            String paramCode = "";
            String paramValue = "";
            if (n == -1) {
                paramCode = paramItem[i];
            } else {
                if (n == 0) continue;
                paramCode = paramItem[i].substring(0, n);
                paramValue = paramItem[i].substring(n + len);
            }
            ParamItem param = new ParamItem(paramCode, paramValue);
            map.put(paramCode, param);
        }
        this.paramList.clear();
        this.paramList.addAll(map.values());
    }

    public List<String> getParamNames() {
        ArrayList<String> ret = new ArrayList<String>();
        for (int i = 0; i < this.paramList.size(); ++i) {
            ParamItem paramItem = this.paramList.get(i);
            if (ret.contains(paramItem.name)) continue;
            ret.add(paramItem.name);
        }
        return ret;
    }

    public String getParamValue(String paramName) {
        String ret = null;
        for (int i = 0; i < this.paramList.size(); ++i) {
            ParamItem paramItem = this.paramList.get(i);
            if (!StringUtils.equalsIgnoreCase((String)paramName, (String)paramItem.name)) continue;
            ret = paramItem.value;
            break;
        }
        return ret;
    }

    public List<String> getParamValues(String paramName) {
        ArrayList<String> ret = new ArrayList<String>();
        for (int i = 0; i < this.paramList.size(); ++i) {
            ParamItem paramItem = this.paramList.get(i);
            if (!StringUtils.equalsIgnoreCase((String)paramName, (String)paramItem.name) || ret.contains(paramItem.value)) continue;
            if (paramItem.value.indexOf(this.mutiValueDelimiter) != -1) {
                String[] mutiValues;
                for (String value : mutiValues = paramItem.value.split("\\|")) {
                    ret.add(StringUtils.trim((String)value));
                }
                continue;
            }
            if (StringUtils.isEmpty((String)paramItem.value)) continue;
            ret.add(StringUtils.trim((String)paramItem.value));
        }
        return ret;
    }

    public boolean isExistsParam(String paramName) {
        boolean isExist = false;
        for (int i = 0; i < this.paramList.size(); ++i) {
            ParamItem paramItem = this.paramList.get(i);
            if (!paramItem.name.equalsIgnoreCase(paramName)) continue;
            isExist = true;
            break;
        }
        return isExist;
    }

    public String getParamStr() {
        StringBuilder ret = new StringBuilder();
        List<String> paraNames = this.getParamNames();
        for (String name : paraNames) {
            List<String> values = this.getParamValues(name);
            if (values == null || values.size() == 0) {
                if (paraNames.indexOf(name) != 0) {
                    ret.append(this.paramDelimiter);
                }
                ret.append(name).append(this.valueDelimiter).append("");
                continue;
            }
            for (int i = 0; i < values.size(); ++i) {
                if (i == 0) {
                    if (ret.length() > 0) {
                        ret.append(this.paramDelimiter);
                    }
                    ret.append(name).append(this.valueDelimiter);
                } else {
                    ret.append(this.newStr);
                }
                ret.append(values.get(i));
            }
        }
        return ret.toString();
    }

    public boolean addParam(String paramName, String paramValue) {
        if (null == paramName || "".equals(paramName.trim())) {
            return false;
        }
        if (null == paramValue) {
            paramValue = "";
        }
        ParamItem param = new ParamItem(paramName, paramValue);
        this.paramList.add(param);
        return true;
    }

    public boolean addParam(String paramName, List<String> paramValues) {
        if (null == paramName || "".equals(paramName.trim())) {
            return false;
        }
        if (paramValues != null) {
            if (paramValues.size() == 0) {
                ParamItem param = new ParamItem(paramName, "");
                this.paramList.add(param);
                return true;
            }
            for (String paramValue : paramValues) {
                this.addParam(paramName, paramValue);
            }
        }
        return true;
    }

    public boolean setParam(String paramName, String paramValue) {
        if (null == paramName || "".equals(paramName.trim())) {
            return false;
        }
        if (null == paramValue) {
            paramValue = "";
        }
        for (int i = 0; i < this.paramList.size(); ++i) {
            if (!this.paramList.get((int)i).name.equalsIgnoreCase(paramName)) continue;
            this.paramList.get((int)i).value = paramValue;
            return true;
        }
        return false;
    }

    public boolean removeParams(String paramName) {
        boolean result = false;
        for (int i = this.paramList.size() - 1; i >= 0; --i) {
            if (!this.paramList.get((int)i).name.equalsIgnoreCase(paramName)) continue;
            this.paramList.remove(i);
            result = true;
        }
        return result;
    }

    private static String[] splitWithSpaceElement(String text, String separator) {
        int pos = text.indexOf(separator);
        int count = 0;
        int len = separator.length();
        while (pos != -1) {
            ++count;
            pos = text.indexOf(separator, pos + len);
        }
        String[] sReturn = new String[count + 1];
        pos = text.indexOf(separator);
        count = 0;
        int prepos = 0;
        while (pos != -1) {
            sReturn[count] = text.substring(prepos, pos);
            prepos = pos + len;
            pos = text.indexOf(separator, pos + len);
            ++count;
        }
        sReturn[sReturn.length - 1] = prepos == text.length() ? "" : text.substring(prepos);
        return sReturn;
    }

    public void processParamWithPoint(List<String> names) {
        int index;
        if (names == null || names.size() == 0) {
            return;
        }
        boolean paramNameWithSep = false;
        HashSet<String> paramNameSet = new HashSet<String>();
        List<String> paramNames = this.getParamNames();
        for (String string : paramNames) {
            int index2;
            paramNameSet.add(string);
            if (paramNameWithSep || (index2 = string.indexOf(".")) == -1 || index2 == string.length() - 1) continue;
            paramNameWithSep = true;
        }
        boolean nameWithSep = false;
        for (String name : names) {
            if (name == null || (index = name.indexOf(".")) == -1 || index == name.length() - 1) continue;
            nameWithSep = true;
            break;
        }
        if (paramNameWithSep || !nameWithSep) {
            return;
        }
        HashSet<String> hashSet = new HashSet<String>();
        String shortName = null;
        index = -1;
        for (String name : names) {
            if (name == null || (index = name.indexOf(".")) == -1 || index == name.length() - 1 || paramNameSet.contains(name) || !paramNameSet.contains(shortName = name.substring(index + 1)) || hashSet.contains(shortName)) continue;
            this.addParam(name, this.getParamValue(shortName));
            paramNameSet.add(name);
            hashSet.add(shortName);
        }
    }

    public void splitParam(String separator) {
        List<String> paramNames = this.getParamNames();
        for (String paramName : paramNames) {
            HashSet<String> vSet = new HashSet<String>();
            List<String> values = this.getParamValues(paramName);
            this.removeParams(paramName);
            for (String value : values) {
                if (value.length() == 0) {
                    if (vSet.contains(value)) continue;
                    this.addParam(paramName, value);
                    vSet.add(value);
                    continue;
                }
                String[] _values = MessageParser.splitWithSpaceElement(value, separator);
                for (int n = 0; n < _values.length; ++n) {
                    if (_values[n].length() <= 0 || vSet.contains(_values[n])) continue;
                    this.addParam(paramName, _values[n]);
                    vSet.add(_values[n]);
                }
            }
        }
    }

    class ParamItem {
        public String name;
        public String value;

        public ParamItem(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = 31 * result + this.getEnclosingInstance().hashCode();
            result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            ParamItem other = (ParamItem)obj;
            if (!this.getEnclosingInstance().equals(other.getEnclosingInstance())) {
                return false;
            }
            return !(this.name == null ? other.name != null : !this.name.equals(other.name));
        }

        private MessageParser getEnclosingInstance() {
            return MessageParser.this;
        }
    }
}

