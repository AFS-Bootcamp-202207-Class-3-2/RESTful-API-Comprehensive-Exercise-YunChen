package com.rest.springbootemployee.utils;

import com.rest.springbootemployee.Dto.PageResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MapperDtoUtil {
    /**
     * 将数据库的数据List过滤掉敏感字段，如薪资
     * @param list
     * @param companyResponse
     * @param <T>
     * @param <E>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T,E> List<T> toResponse(List<E> list,Class<T> companyResponse) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        List<T> returnList = new ArrayList<>();
        for (int idx = 0; idx < list.size(); idx++) {
            returnList.add(toResponse(list.get(idx),companyResponse));
        }
        return returnList;
    }

    /**
     * 将数据库字段过滤敏感字段
     * @param origin
     * @param response
     * @param <T>处理完敏感字段的类
     * @param <E>源数据FromDb
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T,E> T toResponse (E origin,Class<T> response) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        T targetObject = (T)response.newInstance();
        List<String> fieldsName = new ArrayList<>();
        Field[] declaredFields = response.getDeclaredFields();

        Field[] fields = origin.getClass().getDeclaredFields();
        for (int idx = 0; idx < declaredFields.length; idx++) {
            String name = declaredFields[idx].getName();
            for (int subIdx = 0; subIdx < fields.length; subIdx++) {
                String findEqualsName = fields[subIdx].getName();
                if (findEqualsName.equals(name)) {
                    declaredFields[idx].setAccessible(true);
                    Field originField = origin.getClass().getDeclaredField(name);
                    originField.setAccessible(true);
                    declaredFields[idx].set(targetObject,originField.get(origin));
                    break;
                }
            }
        }
        return targetObject;
    }
    public static <E> PageResponse<E> toResponse(Page<E> dataOfPage, Class<E> targetClass) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        PageResponse<E> pageResponse = new PageResponse<>();
        List<E> content = dataOfPage.getContent();
        List<E> mapperContent = new ArrayList<>();
        for (int idx = 0; idx < content.size(); idx++) {
            mapperContent.add(toResponse(content.get(idx),targetClass));
        }
        pageResponse.setContent(mapperContent);
        pageResponse.setTotalPages(dataOfPage.getTotalPages());
        pageResponse.setHashNext(dataOfPage.hasNext());
        pageResponse.setHasPrevious(dataOfPage.hasPrevious());
        pageResponse.setHasContent(dataOfPage.hasContent());
        pageResponse.setTotalElements(dataOfPage.getTotalElements());
        return pageResponse;
    }

    /**
     * 插入和更新的时候将请求数据进行转换
     * @param needToDeal
     * @param targetClass
     * @param <T>
     * @param <E>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T,E>T toRequest(E needToDeal,Class<T> targetClass) throws IllegalAccessException, InstantiationException {
        T requestObj = (T) targetClass.newInstance();
        BeanUtils.copyProperties(needToDeal, requestObj);
        return requestObj;
    }

    /**
     * 用于更新时只修改能修改的数据
     */
    public static <T,E>  T toUpdate(T companyRequest,T companyFromDb,Class<E> toGetRequestChangeMethodName) throws NoSuchFieldException, IllegalAccessException {
        List<String> setMethodsField = new ArrayList<>();
        Field[] fields = toGetRequestChangeMethodName.getDeclaredFields();
        for (int idx = 0; idx < fields.length; idx++) {
            setMethodsField.add(fields[idx].getName());
        }
        System.out.println(setMethodsField);
        for (int idx = 0; idx < setMethodsField.size(); idx++) {
            String setMethodName = setMethodsField.get(idx);
            Field field = companyRequest.getClass().getDeclaredField(setMethodName);
            field.setAccessible(true);
            field.set(companyFromDb,field.get(companyRequest));
        }
        return companyFromDb;
    }

}