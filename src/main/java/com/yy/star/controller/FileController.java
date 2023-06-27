package com.yy.star.controller;

import com.yy.star.service.FileService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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
        Path file = Paths.get("D:/ideaworkspace/star_file/fileupload/2023-06-20/20230620143455385.sql");
        // 检查文件是否存在
        if (Files.exists(file)) {
            // 设置响应头
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment; filename=myfile.txt");
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
}
