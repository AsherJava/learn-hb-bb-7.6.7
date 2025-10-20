/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.cloud;

import com.jiuqi.nvwa.sf.ModuleWrapper;
import com.jiuqi.nvwa.sf.ServiceUrl;
import com.jiuqi.nvwa.sf.adapter.spring.Response;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.RemoteServiceException;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.SFService;
import com.jiuqi.nvwa.sf.adapter.spring.product.domain.ProductJarBean;
import com.jiuqi.nvwa.sf.adapter.spring.product.domain.ProductLineBean;
import com.jiuqi.nvwa.sf.operator.ModuleUpgradeLockOperator;
import java.util.List;

public interface IServiceManager {
    public String getServiceName();

    public SFService getService() throws RemoteServiceException;

    public SFService getProductInfo();

    public void addLicenceInfo(byte[] var1) throws RemoteServiceException;

    public void executeModule(String var1) throws RemoteServiceException;

    public List<ModuleUpgradeLockOperator.UpgradeLogInfo> executeLogs(long var1);

    public ModuleWrapper getModuleWrapper(String var1) throws RemoteServiceException;

    public List<ProductJarBean> getJarList();

    public List<ProductLineBean> getProductLineList();

    public void preExecute();

    public void preExecuteForward();

    public Response saveSerciceUrl(String var1, ServiceUrl var2);

    public ServiceUrl getServiceUrl();
}

