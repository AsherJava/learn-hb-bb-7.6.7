/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.print.service.IPrintService
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.dataentry.print.impl;

import com.jiuqi.nr.dataentry.print.impl.AbstractNrEnhancedPrintService;
import com.jiuqi.xg.print.service.IPrintService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class NrPrintServiceImpl
extends AbstractNrEnhancedPrintService
implements IPrintService {
    public String printAsyncWork(String type, String printerID, String workName, String result) {
        return this.getINrEnhancedPrintService().printAsyncWork(type, printerID, workName, result);
    }

    public String printAsyncRequest(String printerID, String data, String docId) {
        return this.getINrEnhancedPrintService().printAsyncRequest(printerID, data, docId);
    }

    public Object changeAttribute(String printerID, String changeType, String changeValue) {
        return this.getINrEnhancedPrintService().changeAttribute(printerID, changeType, changeValue);
    }

    public Object result(String printerID) {
        return this.getINrEnhancedPrintService().result(printerID);
    }

    public String printSetIsSetup(String printerID) {
        return this.getINrEnhancedPrintService().printSetIsSetup(printerID);
    }

    public String printResourceVersion(String printerID, int localVersion) {
        return this.getINrEnhancedPrintService().printResourceVersion(printerID, localVersion);
    }

    public void printResourceDownload(String printerID, String fileName, int localVersion, HttpServletResponse response) {
        this.defaultPrintService.printResourceDownload(printerID, fileName, localVersion, response);
    }
}

