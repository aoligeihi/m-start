package com.yy.star.fillpdf;

import com.yy.star.dto.pdf.PdfDto;
import com.yy.star.utils.PdfUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Description:
 * @Param:
 * @return:
 * @Author: yang
 * @Date: 2023/8/29
 */

@Slf4j
public class Demo {

    @Test
    public void file() throws IOException {

        String model = "config/model/pay.pdf";
        PdfDto dto = new PdfDto();
        dto.setBhno("DY23472094023I");
        dto.setCaptcha("GBYWIT");
        HashMap<String, Object> toMap = new HashMap<>();
        toMap.put("bhno", "DY23472094023I");
        toMap.put("captcha", "GBYWIT");

//        Map<String, Object> toMap = BeanUtil.beanToMap(dto);
        System.out.println(toMap);
        PdfUtil.fillTemplate(getFile(model).getPath(), "1.pdf", toMap);

    }

    /**
     * 获取给定路径文件
     *
     * @param path 路径  eg config/model/pay.pdf
     * @return {@link File}
     * @throws IOException ioexception
     */
    public static File getFile(String path) throws IOException {
        return new ClassPathResource(path).getFile();
    }
}
