/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.dataentry.bean.facade.FDimensionState;
import com.jiuqi.nr.dataentry.bean.impl.DimensionStateImpl;
import com.jiuqi.nr.dataentry.service.IPermission;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor={NpRollbackException.class})
@Service(value="FormPermissionImpl")
public class FormPermissionImpl
implements IPermission {
    @Autowired
    private DefinitionAuthorityProvider authoritryProvider;

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public boolean isReadable(JtableContext context) {
        return true;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public FDimensionState isWriteable(JtableContext context) {
        String formKey = context.getFormKey();
        boolean canWriteForm = this.authoritryProvider.canWriteForm(formKey);
        return new DimensionStateImpl("form", canWriteForm);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public int getOrder() {
        return -1;
    }
}

