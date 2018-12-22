package com.tkft.sell.exception;

import com.tkft.sell.enums.ResultEnum;

/**
 * @Auther xinyuan
 * @Date 11/18/18 7:25 PM
 **/
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
