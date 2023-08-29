package com.yy.star.controller;

import com.yy.star.service.FileService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileService fileService;

    /**
     * @return
     */
    @PostMapping("/upload")
    public String fileUpload(MultipartFile file) {
        return fileService.fileUpload(file);
    }

    /**
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("download")
    public void downloadFile(HttpServletResponse response) throws IOException {
        // 指定要下载的文件路径
        Path file = Paths.get("D://ideaworkspace/star_file/fileupload/2023-08-29/20230829100851257.jpg");
        // 检查文件是否存在
        if (Files.exists(file)) {
            // 设置响应头
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment; filename=myfile.jpg");
            // 将文件内容写入响应流
            try (InputStream inputStream = Files.newInputStream(file)) {
                int read;
                byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    response.getOutputStream().write(bytes, 0, read);
                }
            }
        } else {
            // 文件不存在时返回 404 错误
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * 文件下载示例
     * @param req
     * @param response
     */
    @GetMapping("newDownload")
    public void newDownloadFile(HttpServletRequest req,HttpServletResponse response){
        String filePath = "D://ideaworkspace/star_file/fileupload/2023-08-29/";
        String fileName = "20230829100851257.jpg";
        Path paths = Paths.get(filePath+fileName);
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            String filenamedisplay = java.net.URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
            if ("FF".equals(getBrowser(req))) {
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

    /**
     * get user agent
     * @param request
     * @return
     */
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
}
