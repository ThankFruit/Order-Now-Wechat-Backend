package com.tkft.sell.VO;

import lombok.Data;

/**
 * http请求返回的最外层对象
 * @Auther xinyuan
 * @Date 11/17/18 3:22 PM
 **/
@Data
public class ResultVO<T> {
//    错误码
    private Integer code;

    private String msg;

    private T data;
}
