package com.bing.lan.serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 反序列化Java对象时必须提供该对象的class文件,现在问题是,随着项目的升级,系统的class文件也会升级
 * (增加一个字段/删除一个字段),如何保证两个class文件的兼容性?
 * <p>
 * Java通过serialVersionUID(序列化版本号) 来判断字节码是否发生改变.
 * 如果不显示定义serialVersionUID类变量,该类变量的值由JVM根据类相关信息计算,
 * 而修改后的类的计算方式和之前往往不同.从而造成了对象反序列化因为版本不兼容而失败的问题
 * <p>
 * Created by 蓝兵 on 2019/4/16.
 */

public class SerializableTest {

    public static void main(String[] args) throws Exception {

        File file = new File("file\\obj.txt");

        //writeObject(new User(12, "user"), file);
        readObject(file);
    }

    private static void writeObject(User user, File file) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
        outputStream.writeObject(user);
        outputStream.close();
    }

    private static void readObject(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
        User user = (User) inputStream.readObject();
        inputStream.close();
        System.out.println("readObject(): " + user);
    }

    static class User implements Serializable {

        static final long serialVersionUID = -323;

        int age;
        transient String name;// 不做序列化操作
        //String sex;

        User(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            return "User {" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
