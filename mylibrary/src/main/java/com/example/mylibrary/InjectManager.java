package com.example.mylibrary;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import android.view.View;


public class InjectManager {
    public static void initJect(Object object) {
//           注入布局
        initLayout(object);
//        注入控件view
        inJectView(object);
        //        注入事件
        inJectEvent(object);
    }


    private static void initLayout(Object object) {
//         实现  setContentView(R.layout.activity_main);
//   1: 反射获取类对象 因为setContentView 是类的方法  所以先获取 类对象
        final Class<?> aClass = object.getClass();
//        2  获取注解对象
        InjectLayout injectLayout = aClass.getAnnotation(InjectLayout.class);
        if (injectLayout != null) {
//             3: 获取类中的方法;    因为setContentView(R.layout.activity_main)
//            3.1 方法名 和方法 需要传递的参数的类型
            try {
                Method method = aClass.getMethod("setContentView", int.class);
//                执行方法
//                静态 方法  非静态方法

                int layoutId = injectLayout.value();
//               因为 setContentView 是非静态方法
//                所以 需要   设置  参数1:object  执行的那个类  参数2 执行类中的的方法的参数
//                相当于 new  MainActivity().setContentView(layoutId)
//                object === new  MainActivity()
                method.invoke(object, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    /**
     * 控件注入view （就是 需要使用注解 字段）    findViewById(R.id.tv_test)
     *
     * @param object
     */
    private static void inJectView(Object object) {
//           findViewById(R.id.tv_test)
        //   1: 反射获取类对象
        final Class aClass = object.getClass();
//        因为  控件 例如  Button  btn  是类中的一个字段
//         2： 获取类中的 所有字段
        Field[] declaredFields = aClass.getDeclaredFields();
//           遍历 类中的字段 获取我们需要的字段
        for (Field field : declaredFields) {
//              3 获取字段的注解
            InjectView injectView = field.getAnnotation(InjectView.class);
            if (injectView != null) {

//            4 获取 类中 执行方法  findViewById()   int.class 表示  findViewById(R.ix.cc) 方法 总需要 传递的参数
                try {
                    Method method = aClass.getMethod("findViewById", int.class);
                    //            获取  注解 中的参数  也就是   View 的 id
                    int viewID = injectView.value();
//               5  执行方法  获取到View 对象
//              如果 只是 method.invoke(object, viewID);  先当与 只是执行了    findViewById(R.ix.cc)
                    View view = (View) method.invoke(object, viewID);
//                通过反射获取私有变量的值，在访问时会忽略访问修饰符的检查
                    field.setAccessible(true);
                    //         相当于  btn =view
                    field.set(object, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }


    private static void inJectEvent(Object object) {
//   1: 反射获取类对象 因为setContentView 是类的方法  所以先获取 类对象
        final Class<?> aClass = object.getClass();
//      2 获取 所有的方法
        Method[] declaredMethods = aClass.getDeclaredMethods();

        for (Method method : declaredMethods) {


//            因为类中的方法  可有有多个注解    所以使用 注解类型来区分  系统注解和我们的注解
//            3：获取方法 上的所有注解
            Annotation[] annotations = method.getAnnotations();

            for (Annotation annotation : annotations) {
//                获取方法上注解的类对象
                Class<? extends Annotation> annotationTypeClass = annotation.annotationType();

//                获取我们自行定义的  注解
                InjectEventType injectEventType = annotationTypeClass.getAnnotation(InjectEventType.class);
                if (injectEventType != null) {  // 表示 这个注解是 我们自定义 的注解
//                      获取 三要素
//                    "setOnClickListener"
                    String listenerSetter = injectEventType.listenerSetter();
//                   new View.OnClickListener.class
                    Class listenerType = injectEventType.listenerType();
//                    "onClick"
                    String callBackMethod = injectEventType.callBackMethod();

//                    设置代理对象
                    Listenerandler listenerandler = new Listenerandler(object, method);
//                      代理对象 new Class[listenerType]
                    Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType},
                            listenerandler);

//                    获取
                    try {
//                        获取 点击事件 注解类 中的 方法
                        Method method1 = annotationTypeClass.getDeclaredMethod("ids");
//                        执行  点击事件 注解类 中的 方法
                        int[] ids = (int[]) method1.invoke(annotation);
//                          遍历 所有 View 的 id
                        for (int id : ids) {
//                         获取   findViewById()  方法
                            Method findViewByIdMethod = aClass.getMethod("findViewById", int.class);
//                 执行   View view = findViewById(R.id.xxx) '
                            View view = (View) findViewByIdMethod.invoke(object, id);
//                           执行 view 的 点击事件  长按事件  textView.setOnClickListener（） textView.setOnLongClickListener（）

//                        因为要执行  view 的 点击事件   所以先获取 view 的类对象
                            Class<? extends View> viewClass = view.getClass();
//                             textView.setOnClickListener（）   如果追调用 不会执行 回调函数  所以需要代理对象
                            Method setListneerMethos = viewClass.getMethod(listenerSetter, listenerType);
//                            执行方法  动态代理
                            setListneerMethos.invoke(view, listener);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        }

    }
}
