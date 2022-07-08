package com.spark.core;

public class Demo03Bean {
    public static void main(String[] args) {
        Person p1 = new Person("kg","man");
        Person p2 = new Person("kg","man");
        System.out.println(p1 == p2);
        System.out.println(p1.equals(p2));
    }
}


class Person {
    private String name;
    private String sex;

    public Person(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public boolean equals(Object obj) {
        boolean bool = false;
        if (obj == null) return bool;
        Person that = (Person)obj;
        if (this.name == that.name && this.sex == that.sex) {
            bool = true;
            return bool;
        }
        return bool;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
