/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.FormCheckStatusInfo;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

public class FormCheckStatusMessage
implements Externalizable {
    private static final long serialVersionUID = 4436535344485836643L;
    private JtableContext context;
    private List<FormCheckStatusInfo> statusList = new ArrayList<FormCheckStatusInfo>();
    private String tenant;
    private String userName;
    private String identityId;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public List<FormCheckStatusInfo> getStatusList() {
        return this.statusList;
    }

    public void setStatusList(List<FormCheckStatusInfo> statusList) {
        this.statusList = statusList;
    }

    public String getTenant() {
        return this.tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentityId() {
        return this.identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.tenant);
        out.writeObject(this.userName);
        out.writeObject(this.identityId);
        out.writeObject(this.context);
        out.writeInt(this.statusList.size());
        for (int i = 0; i < this.statusList.size(); ++i) {
            out.writeObject(this.statusList.get(i));
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.tenant = (String)in.readObject();
        this.userName = (String)in.readObject();
        this.identityId = (String)in.readObject();
        this.context = (JtableContext)in.readObject();
        int statusListSize = in.readInt();
        ArrayList<FormCheckStatusInfo> list = new ArrayList<FormCheckStatusInfo>();
        for (int i = 0; i < statusListSize; ++i) {
            list.add((FormCheckStatusInfo)in.readObject());
        }
        this.statusList = new ArrayList<FormCheckStatusInfo>(list);
    }
}

