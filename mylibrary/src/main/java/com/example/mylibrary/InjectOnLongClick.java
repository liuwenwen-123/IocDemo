package com.example.mylibrary;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)// 定义  注解: 类 。。 字段  方法 等等
// RetentionPolicy.SOURCE  运行时去动态获取注解信息   注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；
//RetentionPolicy.CLASS： 编译时进行获取注解信息    注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期；
//RetentionPolicy.RUNTIME：     注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
@Retention(RetentionPolicy.RUNTIME)
/**
 *   注入事件
 *
 *    textView.setOnLongClickListener(new View.OnLongClickListener() {
 *             @Override
 *             public boolean onLongClick(View v) {
 *                 return false;
 *             }
 *         });
 *
 */
@InjectEventType(listenerSetter= "setOnLongClickListener",listenerType = View.OnLongClickListener.class,
        callBackMethod = "onLongClick")   //  设置注解类型
public @interface InjectOnLongClick { // InjectOnLongClick  注解的名称  使用:  @InjectOnLongClick(R.id.btn)
    int[] ids();
}
