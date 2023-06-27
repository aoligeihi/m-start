package com.yy.star.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

//@Configuration
public class MvcConfig implements WebMvcConfigurer {
    /**
     * 代码不好用
     * 代码将本地目录 /path/to/your/local/directory/ 映射到了URL路径 /files/。这样，您就可以通过访问 /files/filename 来访问上传到该目录中的文件了。
     *
     * @param registry
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        String path = new File("").getAbsolutePath();
//        System.out.println(path);
//        registry.addResourceHandler("/files/**")
//                .addResourceLocations("D:/ideaworkspace/star_file/");
//    }

}
