package com.dd.blog.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 复制对象或者集合属性
 * @author DD
 * @date 2022/4/5 18:18
 */
public class BeanCopyUtils {

    /**
     * 拷贝对象
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T copyObject(Object source, Class<T> target) {
        T tmp = null;
        try {
            tmp = target.newInstance();
            if(null != source) {
                BeanUtils.copyProperties(source, tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmp;
    }

    /**
     * 拷贝集合
     * @param source
     * @param target
     * @param <T>
     * @param <S>
     * @return
     */
    public static <T, S> List<T> copyList(List<S> source, Class<T> target) {
        List<T> list = new ArrayList<>();
        if(source != null && source.size() > 0) {
            for(Object obj : source) {
                list.add(BeanCopyUtils.copyObject(obj, target));
            }
        }
        return list;
    }
}
