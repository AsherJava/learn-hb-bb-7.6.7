/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.draw2d.Graphics
 *  com.jiuqi.xg.draw2d.IGraphicalDevice
 *  com.jiuqi.xg.draw2d.IPageDrawer
 *  com.jiuqi.xg.pdf.PDFPrintDevice
 *  com.jiuqi.xg.pdf.util.PDFPrintUtil
 *  com.jiuqi.xg.print.PrinterDrawManager
 *  com.jiuqi.xg.process.IPaginateInteractor
 *  com.jiuqi.xg.process.IPaintInteractor
 *  com.jiuqi.xg.process.IProcessMonitor
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.util.ProcessUtil
 *  com.jiuqi.xlib.XSeriesMessageMonitor
 *  com.jiuqi.xlib.utils.ExceptionUtils
 */
package com.jiuqi.nr.bpm.de.dataflow.util;

import com.jiuqi.xg.draw2d.Graphics;
import com.jiuqi.xg.draw2d.IGraphicalDevice;
import com.jiuqi.xg.draw2d.IPageDrawer;
import com.jiuqi.xg.pdf.PDFPrintDevice;
import com.jiuqi.xg.pdf.util.PDFPrintUtil;
import com.jiuqi.xg.print.PrinterDrawManager;
import com.jiuqi.xg.process.IPaginateInteractor;
import com.jiuqi.xg.process.IPaintInteractor;
import com.jiuqi.xg.process.IProcessMonitor;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.util.ProcessUtil;
import com.jiuqi.xlib.XSeriesMessageMonitor;
import com.jiuqi.xlib.utils.ExceptionUtils;
import java.io.OutputStream;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PDFPrintUtil2
extends PDFPrintUtil {
    private static final Logger logger = LoggerFactory.getLogger(PDFPrintUtil2.class);

    public static void printPDF(ITemplateDocument tmplDoc, IPaginateInteractor paginateInteractor, IPaintInteractor paintInteractor, OutputStream out, IProcessMonitor monitor) {
        block2: {
            String nature = tmplDoc.getNature();
            PDFPrintDevice device = new PDFPrintDevice(out);
            Graphics g = new Graphics((IGraphicalDevice)device);
            PrinterDrawManager drawManager = new PrinterDrawManager(nature, g);
            device.setDrawManager(drawManager);
            device.setProperty(3, (Object)tmplDoc.getID());
            device.setPageDrawer((IPageDrawer)drawManager);
            Object[] streams = ProcessUtil.getPaginateContentStreams((String)nature, (ITemplateDocument)tmplDoc, (IPaginateInteractor)paginateInteractor, (IProcessMonitor)monitor);
            try {
                logger.info(Arrays.toString(streams));
            }
            catch (Exception e) {
                XSeriesMessageMonitor.getMonitor().message(ExceptionUtils.getStackTrace((Throwable)e, null), null);
                if (device == null) break block2;
                device.close();
            }
        }
    }
}

