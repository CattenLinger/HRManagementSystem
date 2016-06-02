package net.catten.hrsys.util;

/**
 * Created by catten on 16/6/2.
 */
public class ThreadLocalHolder {

    private static ThreadLocal<String> sort = new ThreadLocal<>();

    private static ThreadLocal<String> order = new ThreadLocal<>();

    private static String getSort(){
        return sort.get();
    }

    private static void setSort(String s){
        sort.set(s);
    }

    private static String getOrder(){
        return order.get();
    }

    private static void setOrder(String s){
        order.set(s);
    }
}
