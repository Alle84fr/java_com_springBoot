package br.com.afr8799.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.annotation.Bean;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

public class Utils {

    // por ser static não tem de instanciar classe
    public static void  copyNonNullProperties(Object source, Object target) {
        BeanUtil.copyProperties(source, target, getNullPropertyName(source));
    }
    
    //pega tudo que for propriedade nula e vai por public de cima
    public static String[] getNullPropertyName(Object source) {

        //BeanWrapper é uma interface que fornce forma de acessar propriedades de um objeto no java
        final BeanWrapper src = new BeanWrapperImpl(source);

        //obter propriedade do obj
          PropertyDescriptor[] pds = src.getPropertyDescriptor();

          Set<String> emptyNames = new HashSet<>();

          for(PropertyDescriptor pd: pds) {
            Object srcValue = src.getPropertyValue(pd.getName());

            if(srcValue == null) {
                emptyNames.add(pd.getName());
            }
          }

          String[] result = new String[emptyNames.size()];
          return emptyNames.toArray(result);
    }
}
