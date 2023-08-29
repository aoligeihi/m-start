package com.yy.star.utils;

//import cn.hutool.core.io.IoUtil;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Map;
import java.util.Optional;

/**
 * @author zhuzi
 * 2023/8/2 16:06
 * <br>
 * 填充模板|中文需要设置字体|
 * <br>
 * ttc 的字体可能有问题,window 取出来的字体是 ttc，使用
 * <a href="https://transfonter.org/ttc-unpack">此转换</a>
 */
@Slf4j
public class PdfUtil {
    private static final String fontPath = "config/font/SimSun.ttf";

    /**
     * 填写模板
     *
     * @param templatePath 模板路径
     * @param destPath     输出路径
     * @param map          填充数据
     */
    public static void fillTemplate(String templatePath, String destPath, Map<String, Object> map) throws IOException {
        OutputStream os = null;
        PdfStamper ps = null;
        PdfReader reader = null;
        try {
            os = Files.newOutputStream(new File(destPath).toPath());
            // 2 读入pdf表单
            reader = new PdfReader(templatePath);
            // 3 根据表单生成一个新的pdf
            ps = new PdfStamper(reader, os);
            // 4 获取pdf表单
            AcroFields form = ps.getAcroFields();
            // 5给表单添加中文字体
            BaseFont bf = BaseFont.createFont(getFilePath(fontPath), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //Font f = new Font(bf, Font.BOLD);
            form.addSubstitutionFont(bf);
            // 6查询数据

            //---------设置字体 start----------
            Map<String, AcroFields.Item> fields = form.getFields();
            for (String fieldName : fields.keySet()) {
                //设置字体
                form.setFieldProperty(fieldName, "textfont", bf, null);
                // 或者使用下面的方式设置字体大小｜此处不设置,使用模版字体
                // form.setFieldProperty(fieldName, "textsize", 12.0f, null);
            }
            //---------设置字体 end----------
            // 7遍历data 给pdf表单表格赋值
            for (String key : map.keySet()) {
                String value = Optional.ofNullable(map.get(key)).orElse("").toString();
                form.setField(key, value);
            }
            // true代表生成的PDF文件不可编辑
            ps.setFormFlattening(true);
            log.info("PDF导出成功");
        } catch (Exception e) {
            log.error("PDF导出失败", e);
        } finally {

            close(ps, reader);
//            IoUtil.close(os);
            os.close();
        }
    }

    private static void close(PdfStamper ps, PdfReader reader) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
        }
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 返回按照模板生成的pdf的字节数组
     *
     * @param templatePath
     * @param map
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static byte[] fillTemplate(String templatePath, Map<String, Object> map) throws DocumentException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfStamper ps = null;
        PdfReader reader = null;
        try {
            reader = new PdfReader(templatePath);
            ps = new PdfStamper(reader, baos);
            AcroFields form = ps.getAcroFields();

            BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            form.addSubstitutionFont(bf);
            // 设置默认字段属性，包括字体
            Map<String, AcroFields.Item> fields = form.getFields();
            /*for (String fieldName : fields.keySet()) {
                //设置字体
                form.setFieldProperty(fieldName, "textfont", bf, null);
                // 或者使用下面的方式设置字体大小｜此处不设置,使用模版字体
                // form.setFieldProperty(fieldName, "textsize", 12.0f, null);
            }*/
            for (String key : map.keySet()) {
                form.setField(key, map.get(key) == null ? "" : map.get(key).toString());
            }
            ps.setFormFlattening(true);
        } finally {
            close(ps, reader);
        }
        // 将填充后的 PDF 数据返回
        return baos.toByteArray();
    }

    public static String getFilePath(String path) {
        try {
            return new ClassPathResource(path).getFile().getAbsolutePath();
        } catch (IOException e) {
        }
        return null;
    }
}
