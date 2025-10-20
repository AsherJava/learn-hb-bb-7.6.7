/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.util.StringUtils
 *  javax.servlet.ServletException
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.bi.oss.storage.servlet;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.oss.storage.ObjectTypeEnum;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.util.StringUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ObjectStorageServlet
extends HttpServlet {
    private static final long serialVersionUID = -4071990221087651696L;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        String bucketName = req.getParameter("bucket");
        String objKey = req.getParameter("objkey");
        try (ServletOutputStream sos = resp.getOutputStream();){
            Bucket bucket;
            try (BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();){
                bucket = bucketService.getBucket(bucketName);
                if (bucket == null) {
                    resp.sendError(404, "bucket\u4e0d\u5b58\u5728\uff1a" + HtmlUtils.cleanUrlXSS((String)bucketName));
                    return;
                }
            }
            if (!bucket.isOpen()) {
                resp.sendError(403, "\u6743\u9650\u4e0d\u8db3");
                return;
            }
            try (ObjectStorageService objService = ObjectStorageManager.getInstance().createObjectService(bucketName);){
                ObjectInfo info = objService.getObjectInfo(objKey);
                if (info == null) {
                    resp.sendError(404, "\u5bf9\u8c61\u4e0d\u5b58\u5728\uff1a" + HtmlUtils.cleanUrlXSS((String)objKey));
                    return;
                }
                String extension = info.getExtension();
                ObjectTypeEnum typeEnum = ObjectTypeEnum.findByExtension(extension);
                resp.setContentType("text/html;charset=UTF-8");
                resp.setHeader("Content-Type", typeEnum.getContentType());
                String ims = req.getHeader("if-modified-since");
                if (ims != null && ims.equals(info.getCreateTime())) {
                    resp.setStatus(304);
                    return;
                }
                resp.setHeader("last-modified", HtmlUtils.cleanHeaderValue((String)info.getCreateTime()));
                resp.setHeader("Cache-Control", "no-cache");
                StringBuilder buf = new StringBuilder();
                if (StringUtils.isNotEmpty((String)info.getName())) {
                    buf.append(info.getName());
                } else {
                    buf.append(objKey);
                }
                if (StringUtils.isNotEmpty((String)extension)) {
                    buf.append(".").append(extension);
                }
                String filename = buf.toString();
                filename = URLEncoder.encode(filename, "utf-8");
                resp.setHeader("Content-Disposition", "attachment; filename=" + HtmlUtils.cleanHeaderValue((String)filename));
                objService.download(objKey, (OutputStream)sos);
                return;
            }
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

