/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.dataentry.gather.IDataentryFormFilter;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FormReadWriteAccessData
implements Serializable {
    private static final long serialVersionUID = 1L;
    private JtableContext jtableContext;
    private Consts.FormAccessLevel formAccessLevel;
    private List<String> formKeys = new ArrayList<String>();
    private Map<String, String> noAccessReasons = new HashMap<String, String>();
    private Map<String, String> noAccessUnitReasons = new HashMap<String, String>();
    private Map<String, IDataentryFormFilter> dataentryFormFilterMap;

    public FormReadWriteAccessData(JtableContext jtableContext, Consts.FormAccessLevel formAccessLevel) {
        this.jtableContext = jtableContext;
        this.formAccessLevel = formAccessLevel;
        this.dataentryFormFilterMap = SpringBeanUtils.getApplicationContext().getBeansOfType(IDataentryFormFilter.class);
    }

    public FormReadWriteAccessData(JtableContext jtableContext, Consts.FormAccessLevel formAccessLevel, List<String> formKeys) {
        this(jtableContext, formAccessLevel);
        this.formKeys = formKeys;
    }

    public JtableContext getJtableContext() {
        return this.jtableContext;
    }

    public void setJtableContext(JtableContext jtableContext) {
        this.jtableContext = jtableContext;
    }

    public Consts.FormAccessLevel getFormAccessLevel() {
        return this.formAccessLevel;
    }

    public List<String> getFormKeys() {
        if (null == this.formKeys) {
            this.formKeys = new ArrayList<String>();
            return this.formKeys;
        }
        ArrayList<String> tempformKeys = new ArrayList<String>();
        tempformKeys.addAll(this.formKeys);
        if (this.dataentryFormFilterMap.size() > 0) {
            Iterator formKeyIterator = tempformKeys.iterator();
            while (formKeyIterator.hasNext()) {
                String formKey = (String)formKeyIterator.next();
                for (Map.Entry<String, IDataentryFormFilter> entry : this.dataentryFormFilterMap.entrySet()) {
                    boolean doFilter = entry.getValue().doFilter(this.jtableContext, formKey);
                    if (doFilter) continue;
                    formKeyIterator.remove();
                }
            }
        }
        return tempformKeys;
    }

    public void setOneFormKeyNoAccess(String formKey, String reason) {
        if (this.formKeys.contains(formKey)) {
            if (this.formKeys.remove(formKey)) {
                this.noAccessReasons.put(formKey, reason);
            }
        } else {
            this.noAccessReasons.put(formKey, reason);
        }
    }

    public void setOneUnitKeyNoAccess(String unitKey, String reason) {
        this.noAccessUnitReasons.put(unitKey, reason);
    }

    public List<String> getNoAccessUnitKey() {
        Set<String> keySet = this.noAccessUnitReasons.keySet();
        return new ArrayList<String>(keySet);
    }

    public String getNoAccessUnitKeyReason(String unitKey) {
        if (this.noAccessUnitReasons.containsKey(unitKey)) {
            return this.noAccessUnitReasons.get(unitKey);
        }
        return "";
    }

    public void setFormKeysNoAccess(List<String> noAccessFormKeys, String reason) {
        if (null != noAccessFormKeys && noAccessFormKeys.size() > 0) {
            for (String formKey : noAccessFormKeys) {
                this.setOneFormKeyNoAccess(formKey, reason);
            }
        }
    }

    public String getOneFormKeyReason(String formKey) {
        if (this.noAccessReasons.containsKey(formKey)) {
            return this.noAccessReasons.get(formKey);
        }
        return "\u62a5\u8868\u4e0d\u7b26\u5408\u9002\u5e94\u6027\u6761\u4ef6";
    }

    public List<String> getNoAccessnFormKeys() {
        Set<String> keySet = this.noAccessReasons.keySet();
        return new ArrayList<String>(keySet);
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.jtableContext == null ? 0 : this.jtableContext.hashCode());
        result = 31 * result + (this.formAccessLevel == null ? 0 : this.formAccessLevel.hashCode());
        result = 31 * result + (this.formKeys == null ? 0 : this.formKeys.hashCode());
        result = 31 * result + (this.noAccessReasons == null ? 0 : this.noAccessReasons.hashCode());
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
        FormReadWriteAccessData other = (FormReadWriteAccessData)obj;
        if (this.jtableContext == null ? other.jtableContext != null : !this.jtableContext.equals((Object)other.jtableContext)) {
            return false;
        }
        if (this.formAccessLevel != other.formAccessLevel) {
            return false;
        }
        if (this.formKeys == null ? other.formKeys != null : !this.formKeys.equals(other.formKeys)) {
            return false;
        }
        return !(this.noAccessReasons == null ? other.noAccessReasons != null : !this.noAccessReasons.equals(other.noAccessReasons));
    }
}

