package com.yy.star.service.impl;

import com.yy.star.service.FileService;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileServiceImpl implements FileService {
    /**
     * 文件上传
     *
     * @return
     */
    @Override
    public String fileUpload(MultipartFile file) {
        // 保存图片地址例 rootPath/方法名/年月日/年月日时分秒.后缀名
        try {
            // 检查文件类型 限制文件大小
            if (file.getSize() > 52428800) {
                // 文件大小超过50MB，拒绝上传
                return file.getSize()+" 拒绝上传";
            }

            // 存放目标目录
            String afterPath = "/fileupload/" + formatDate(LocalDateTime.now(), "date");
            // 上传文件的全路径
            String fullPath = createRootDirectory(afterPath);
            // 原文件名
            String originalFilename = file.getOriginalFilename();// 原文件名
            //后缀名
            String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 保存的新文件名
            String newFileName = formatDate(LocalDateTime.now(), "datetime") + suffixName;
            File dest = new File(fullPath, newFileName);
            file.transferTo(dest);
            return newFileName;
        } catch (IOException e) {
            System.out.println(e);
        }
        return "newFileName";
    }

    /**
     * 文件下载
     */
    @Override
    public String fileDownload() {
        return null;
    }

    /**
     * 返回问价访问地址
     */
    @Override
    public String fileView() {
        return null;
    }

    public static void main(String[] args) {
//        String path = new File("").getAbsolutePath();
//        File folder = new File(path + "/../folder");
//        if (folder.exists()) {
//            System.out.println("文件存在");
//            // 文件夹存在
//        } else {
//            // 文件夹不存在
//            System.out.println(path);
//            // 不存在时候创建该根目录
//        }
//        DateFormat dateInstance = DateFormat.getDateInstance();
//        System.out.println(dateInstance.);
//        createRootDirectory("");
    }

    String createRootDirectory(String afterPath) {
        String path = new File("").getAbsolutePath();
        File folder = new File(path + "/../star_file" + afterPath + "/");
        if (!folder.exists()) {
            // mkdir() 只会创建最后一个目录。
            folder.mkdirs();// 方法将创建所有必需的父目录，
        }
        return folder.getPath();
    }

    /**
     * @param type
     * @return
     */
    public String formatDate(LocalDateTime localDateTime, String type) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if ("date".equals(type)) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        } else if ("datetime".equals(type)) {
            formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        }
        String formattedDateTime = localDateTime.format(formatter);
        return formattedDateTime;
    }

    /**
     * 文件上传ftp
     * @param file
     * @return
     */
    public String handleFileUploadFtp(MultipartFile file) {
        // 获取文件名
        String fileName = file.getOriginalFilename();

        // 连接到 FTP 服务器
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect("ftp.example.com");
            ftpClient.login("username", "password");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 上传文件
            ftpClient.storeFile(fileName, file.getInputStream());

            // 注销并断开连接
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return "success";
    }
}
