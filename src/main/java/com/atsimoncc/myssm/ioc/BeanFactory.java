package com.atsimoncc.myssm.ioc;

/**
 * ClassName: BeanFactory
 * Package: com.atsimoncc.myssm.io
 * Description:
 */
public interface BeanFactory {
    //根據id獲取物件
    Object getBean(String id);
}
