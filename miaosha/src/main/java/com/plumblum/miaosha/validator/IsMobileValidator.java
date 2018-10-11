package com.plumblum.miaosha.validator;

import com.plumblum.miaosha.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT.value;

/**
 * Created with IDEA
 * author:plumblum
 * Date:2018/10/9
 * Time:23:08
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;
//  初始化注解
    public void initialize(IsMobile isMobile) {
        required = isMobile.required();
    }
//  验证
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(required) {
            return ValidatorUtil.isMobile(value);
        }else {
            if(StringUtils.isEmpty(value)) {
                return true;
            }else {
                return ValidatorUtil.isMobile(value);
            }
        }
    }

}
