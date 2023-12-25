package com.yy.star.utils;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownFile {
    private final static String brow_FF = "FF";


    /**
     * 文件下载
     *
     * @param req      HttpServletRequest
     * @param response HttpServletResponse
     * @param paths    文件路径
     * @param fileName 下载显示的文件名
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static void webDownFile(HttpServletRequest req, HttpServletResponse response, Path paths, String fileName) {
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            String filenamedisplay = java.net.URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
            if (brow_FF.equals(getBrowser(req))) {
                // 针对火狐浏览器处理方式不一样了
                filenamedisplay = new String(fileName.getBytes(StandardCharsets.UTF_8.name()), StandardCharsets.ISO_8859_1.name());
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + filenamedisplay);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");

            if (Files.exists(paths)) {
                Files.copy(paths, out);// JDK自带的;缓存是8192
            }
        } catch (Exception e) {
        }
    }

    private static String getBrowser(HttpServletRequest request) {
        String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (UserAgent != null) {
            if (UserAgent.indexOf("msie") >= 0) {
                return "IE";
            }

            if (UserAgent.indexOf("firefox") >= 0) {
                return "FF";
            }

            if (UserAgent.indexOf("safari") >= 0) {
                return "SF";
            }
            if (UserAgent.indexOf("mozilla") >= 0) {
                return "Chrome";
            }
        }
        return null;
    }


    //文件上传
    public static String uploadImg(MultipartFile files, String fileName, String imagePath) throws Exception {
        Path path = Paths.get(imagePath + fileName);
        files.transferTo(path);
        return path.toString();
    }


}
