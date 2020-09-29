package com.tyzq.salary.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 对象工具类
 *
 * @author ningliang
 */
public class ObjectUtil {

    /**
     * 含指定字符串的属性赋值
     *
     * @param strPosition            部分字符串位置。 1=开始，2=末尾，3=任意位置
     * @param sourceObject           需要被赋值的对象
     * @param partPropertyNameString 被包含的部分属性名称字符串
     *                               忽略大小写
     * @param propertyValue          属性值
     */
    public static void setPropertyValueByString(int strPosition, Object sourceObject, String partPropertyNameString, Object propertyValue) {

        if (sourceObject != null && StringUtils.isNotBlank(partPropertyNameString)) {
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(sourceObject
                        .getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo
                        .getPropertyDescriptors();

                for (PropertyDescriptor property : propertyDescriptors) {
                    String key = property.getName();
                    //判断是否包含部分属性名称
                    boolean bContain = false;
                    switch (strPosition) {
                        case 1:
                            bContain = key.toLowerCase().startsWith(partPropertyNameString.toLowerCase());
                            break;
                        case 2:
                            bContain = key.toLowerCase().endsWith(partPropertyNameString.toLowerCase());
                            break;
                        case 3:
                            bContain = (key.toLowerCase().indexOf(partPropertyNameString.toLowerCase()) > -1) ? true : false;
                            break;
                    }
                    if (bContain) {
                        try {

                            Method setter = property.getWriteMethod();
                            setter.invoke(sourceObject, propertyValue);

                        } catch (Exception e) {
                        }
                    }

                }

            } catch (Exception e) {
            }
        }

    }

    /**
     * 设置对象的属性值
     *
     * @param objBean    对象
     * @param fieldName  属性名称
     * @param fieldValue 属性值
     */
    public static <T> void setPropertyValue(T objBean, String fieldName,
                                            Object fieldValue) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(objBean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo
                    .getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (fieldName.equalsIgnoreCase(key)) {
                    //设置属性值
                    setPropertyValue(objBean, property, fieldValue);
                    break;
                }

            }

        } catch (Exception e) {
        }
    }

    /**
     * 设置对象中的属性值
     *
     * @param objTarget 对象
     * @param pTarget   属性
     * @param value     值
     */
    public static <T> void setPropertyValue(T objTarget,
                                            PropertyDescriptor pTarget, Object value) {
        if (objTarget != null && pTarget != null) {
            String ptTarget = pTarget.getPropertyType().getName();

            try {
                Method setter = pTarget.getWriteMethod();
                if (setter != null)
                    setter.invoke(objTarget, value);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 判断对象中是否存在此属性
     *
     * @param className 对象
     * @param fieldName 属性名称
     * @return
     */
    public static <T> boolean existProperty(Class<T> className, String fieldName) {
        boolean bReturn = false;

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(className);
            PropertyDescriptor[] propertyDescriptors = beanInfo
                    .getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (fieldName.equalsIgnoreCase(key)) {
                    bReturn = true;
                    break;
                }

            }

        } catch (Exception e) {
        }

        return bReturn;
    }


    /**
     * @Title: getPropertyObject
     * @Description: TODO通过反射机制得到一个对象的内部属性
     * @param: @param objTarget：目标对象
     * @param: @param fieldName：属性名称
     * @param: @param isInstance：是否实例化
     * @param: @return
     * @return: Object
     * @throws:
     * @author: yangweijun
     * @Date: 2015年8月25日 下午3:37:12
     */
    public static Object getPropertyObject(Object objTarget, String fieldName, boolean isInstance) {
        Object valueTarget = null;

        if (objTarget != null && fieldName != null) {
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(objTarget.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo
                        .getPropertyDescriptors();

                for (PropertyDescriptor property : propertyDescriptors) {
                    String key = property.getName();

                    if (fieldName.equalsIgnoreCase(key)) {
                        String ptTarget = property.getPropertyType().getName();
                        try {
                            Method getterTarget = property.getReadMethod();
                            if (getterTarget != null)
                                valueTarget = getterTarget.invoke(objTarget);
                        } catch (Exception e) {
                        }
                        if (valueTarget == null && isInstance) {

                            try {
                                // 实例化，并赋值
                                valueTarget = Class.forName(ptTarget).newInstance();

                                Method setter = property.getWriteMethod();
                                if (setter != null)
                                    setter.invoke(objTarget, valueTarget);
                            } catch (Exception e) {
                            }

                        }
                        break;
                    }

                }

            } catch (Exception e) {
            }
        }


        return valueTarget;
    }


    /**
     * 设置数据库Map到JavaBean
     *
     * @param map 数据库Map，key为列名
     * @param obj JavaBean
     * @return 设置的个数
     */
    public static <T> int transDatabaseMap2Bean(Map<String, Object> map, T obj) {
        int nReturn = 0;

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo
                    .getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                String propertyType = property.getPropertyType().getName();

                for (String sKey : map.keySet()) {
                    //将列名转为属性名称
                    String keyMap = CamelUtils.underlineToCamel(sKey);
                    if (keyMap.equalsIgnoreCase(key)) {

                        Object value = map.get(sKey);

                        try {

                            Method setter = property.getWriteMethod();
                            if (setter != null) {
                                setter.invoke(obj, value);

                                //设置个数+1
                                nReturn++;
                            }

                        } catch (Exception e) {
                        	e.printStackTrace();
                        }
                        break;
                    }
                }

            }

        } catch (Exception e) {
        }

        return nReturn;
    }

    /**
     * 赋值一个JavaBean到另一个JavaBean
     *
     * @param objSource 源JavaBean
     * @param objTarget 目标JavaBean
     * @return 赋值个数
     */
    public static <T> int copyBean2Bean(Object objSource, T objTarget) {
        int nReturn = 0;

        try {
            //源Bean属性
            BeanInfo beanInfoSource = Introspector.getBeanInfo(objSource.getClass());
            PropertyDescriptor[] propertyDescriptorsSource = beanInfoSource
                    .getPropertyDescriptors();

            //目标Bean属性
            BeanInfo beanInfoTarget = Introspector.getBeanInfo(objTarget.getClass());
            PropertyDescriptor[] propertyDescriptorsTarget = beanInfoTarget
                    .getPropertyDescriptors();

            //循环源Bean属性
            for (PropertyDescriptor propertySource : propertyDescriptorsSource) {
                String keySource = propertySource.getName();

                String propertyTypeSource = propertySource.getPropertyType().getName();

                //循环目标Bean属性
                for (PropertyDescriptor propertyTarget : propertyDescriptorsTarget) {
                    String keyTarget = propertyTarget.getName();
                    if (keySource.equalsIgnoreCase(keyTarget)) {
                        //得到源值
                        Object valueSource = null;
                        try {
                            Method getterSource = propertySource.getReadMethod();
                            if (getterSource != null)
                                valueSource = getterSource.invoke(objSource);
                        } catch (Exception e) {
                        }

                        //设置目标值
                        try {
                            Method setterTarget = propertyTarget.getWriteMethod();
                            if (setterTarget != null) {
                                setterTarget.invoke(objTarget, valueSource);

                                //设置个数+1
                                nReturn++;
                            }

                        } catch (Exception e) {
                        }
                        break;
                    }
                }
            }

        } catch (Exception e) {
        }

        return nReturn;
    }

    /**
     * 赋值一个JavaBean到另一个JavaBean
     *
     * @param objSource        源对象
     * @param objTarget        目标对象
     * @param notContainSuffix 不包括此后缀的属性
     * @return
     */
    public static <T> int copyBean2Bean(Object objSource, T objTarget, String notContainSuffix) {
        int nReturn = 0;

        try {
            //源Bean属性
            BeanInfo beanInfoSource = Introspector.getBeanInfo(objSource.getClass());
            PropertyDescriptor[] propertyDescriptorsSource = beanInfoSource
                    .getPropertyDescriptors();

            //目标Bean属性
            BeanInfo beanInfoTarget = Introspector.getBeanInfo(objTarget.getClass());
            PropertyDescriptor[] propertyDescriptorsTarget = beanInfoTarget
                    .getPropertyDescriptors();

            //循环源Bean属性
            for (PropertyDescriptor propertySource : propertyDescriptorsSource) {
                String keySource = propertySource.getName();

                String propertyTypeSource = propertySource.getPropertyType().getName();

                if (!keySource.endsWith(notContainSuffix)) {
                    //循环目标Bean属性
                    for (PropertyDescriptor propertyTarget : propertyDescriptorsTarget) {
                        String keyTarget = propertyTarget.getName();
                        if (keySource.equalsIgnoreCase(keyTarget)) {
                            //得到源值
                            Object valueSource = null;
                            try {
                                Method getterSource = propertySource.getReadMethod();
                                if (getterSource != null)
                                    valueSource = getterSource.invoke(objSource);
                            } catch (Exception e) {
                            }

                            //设置目标值
                            try {
                                Method setterTarget = propertyTarget.getWriteMethod();
                                if (setterTarget != null) {
                                    setterTarget.invoke(objTarget, valueSource);

                                    //设置个数+1
                                    nReturn++;
                                }

                            } catch (Exception e) {
                            }
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
        }

        return nReturn;
    }

    /**
     * 将对象列表转换为指定类型的列表
     *
     * @param listSource 源列表
     * @param className  目标类型
     * @return
     */
    public static <T> List<T> copyList2T(List listSource, Class<T> className) {
        List<T> listReturn = new ArrayList<T>();
        if (listSource != null) {
            for (Object object : listReturn) {
                try {
                    T objTarget = className.newInstance();
                    int num = copyBean2Bean(object, objTarget);
                    if (num > 0)
                        listReturn.add(objTarget);
                } catch (Exception e) {
                }
            }
        } else
            listReturn = null;

        return listReturn;
    }

    /**
     * 比较2个对象中相同字段的金额是否相等
     *
     * @param objSource   源
     * @param objTarget   目标
     * @param moneySuffix 结尾的属性
     * @return
     */
    public static boolean equalsObjectOfBigDecimal(Object objSource, Object objTarget, String moneySuffix) {
        boolean bReturn = true;

        try {

            //目标Bean属性
            BeanInfo beanInfoTarget = Introspector.getBeanInfo(objTarget.getClass());
            PropertyDescriptor[] propertyDescriptorsTarget = beanInfoTarget
                    .getPropertyDescriptors();

            //循环源Bean属性
            for (PropertyDescriptor propertyTarget : propertyDescriptorsTarget) {
                String keyTarget = propertyTarget.getName();
                String propertyTypeTarget = propertyTarget.getPropertyType().getName();

                if (keyTarget.endsWith(moneySuffix) && propertyTypeTarget.indexOf("BigDecimal") > -1) {
                    BigDecimal bdTarget = (BigDecimal) getPropertyObject(objTarget, keyTarget, false);
                    Object objSourceValue = getPropertyObject(objSource, keyTarget, false);
                    if (objSourceValue != null && objSourceValue.getClass().getName().indexOf("BigDecimal") > -1 && bdTarget != null
                            && bdTarget.compareTo((BigDecimal) objSourceValue) != 0) {
                        bReturn = false;
                        break;
                    }

                }

            }

        } catch (Exception e) {
        }

        return bReturn;
    }
    

    /**
     * 得到对象的字符串数据
     * @param objSource
     * @return
     */
    public static String toString(Object objSource){
    	StringBuffer sb = new StringBuffer();
    	
    	try {
            //源Bean属性
            BeanInfo beanInfoSource = Introspector.getBeanInfo(objSource.getClass());
            PropertyDescriptor[] propertyDescriptorsSource = beanInfoSource
                    .getPropertyDescriptors();

           

            //循环源Bean属性
            for (PropertyDescriptor propertySource : propertyDescriptorsSource) {
                String keySource = propertySource.getName();

                String propertyTypeSource = propertySource.getPropertyType().getName();
                
                //得到源值
                Object valueSource = null;
                try {
                    Method getterSource = propertySource.getReadMethod();
                    if (getterSource != null)
                        valueSource = getterSource.invoke(objSource);
                } catch (Exception e) {
                }
                if(sb.length() > 0)
                	sb.append(",");
                if(valueSource != null)
                	sb.append( keySource + "=" + valueSource.toString());
                else
                	sb.append(keySource + "=null");
                	
               
            }

        } catch (Exception e) {
        }
    	
    	return sb.toString();
    }

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }


    public List<String> getPropertyStatus(Class a, Class b, boolean same) {
        List<String> properties = new ArrayList<>();
        for (Field f1 : getAllFields(null, a)) {
            boolean found = false;
            for (Field f2 : getAllFields(null, b)) {
                if (f1.getName().equals(f2.getName())) {
                    if (same) {
                        properties.add(f1.getName());
                        break;
                    }
                    found = true;
                }
            }
            if (!same && !found) {
                properties.add(f1.getName());
            }
        }
        return properties;
    }


    public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    public static void copyProperties(Object source, Object target, Iterable<String> props) {

        BeanWrapper sourceWrapper = PropertyAccessorFactory.forBeanPropertyAccess(source);
        BeanWrapper targetWrapper = PropertyAccessorFactory.forBeanPropertyAccess(target);

        props.forEach(p -> targetWrapper.setPropertyValue(p, sourceWrapper.getPropertyValue(p)));

    }
    

    public static void main(String[] args) {
        List<String> a = Arrays.asList("a", "b");
        List<String> b = new ArrayList<>();
        b.add(new String("b"));
        b.add("b");
        b.add("a");
        List<Long> c = new ArrayList<>();
        c.add(1L);
        c.add(2L);
        System.out.println(c.contains(new Long("1")));
        //System.out.println(CollectionUtils.isEqualCollection(a,b));;
       // new ObjectUtil().getPropertyStatus(BudgetFormationSubjectBean.class, BaseBudgetSubjectPO.class, true).forEach(property -> System.out.println(property));
    }
}