package com.yy.star.dto.pdf;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description:
 * @Param:
 * @return:
 * @Author: yang
 * @Date: 2023/8/29
 */
@Data
@Accessors(chain = true)
public class PdfDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 保函编号
     */
    private String bhno;

    /**
     * 防伪码
     */
    private String captcha;

}
