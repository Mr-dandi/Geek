package com.geek;

import sun.misc.Launcher;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class JvmClassLoaderPrintPath {

    public static void main(String[] args) {

        //启动类加载器
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        System.out.println("启动类加载器");
        for (URL url : urLs){
            System.out.println("====>"+url.toExternalForm());
        }

        //扩展类加载器
        printClassloader("扩展加载器",JvmClassLoaderPrintPath.class.getClassLoader().getParent());

        //应用加载器
        printClassloader("应用加载器",JvmClassLoaderPrintPath.class.getClassLoader());


    }

    private static void printClassloader(String name,ClassLoader classLoader){
        System.out.println();
        if (null != classLoader){
            System.out.println(name+"Classloader -> "+ classLoader.toString());
            printURLForClassloader(classLoader);
        }else {
            System.out.println(name + " ClassLoader ‐> null");
        }

    }

    private static void printURLForClassloader(ClassLoader classLoader){

        Object ucp = insightField(classLoader, "ucp");
        Object path = insightField(ucp, "path");
        ArrayList ps = (ArrayList) path;
        for (Object p : ps) {
            System.out.println("====>"+p.toString());
        }

    }

    private static Object insightField(Object obj,String fName){

        Field f = null;
        try {
            if (obj instanceof  URLClassLoader) {
                f = URLClassLoader.class.getDeclaredField(fName);
            }else {
                f = obj.getClass().getDeclaredField(fName);
            }
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
