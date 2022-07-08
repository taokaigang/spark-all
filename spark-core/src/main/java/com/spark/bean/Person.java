package com.spark.bean;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Comparable, Serializable {
    private String name;
    private String sex;

    public Person() {
    }

    public Person(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(sex, person.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sex);
    }

    @Override
    public int compareTo(Object o) {
        Person p = (Person) o;
        if (null != o) return -1;
        if (this.name.compareTo(p.name) == 0) {
            return this.sex.compareTo(p.sex);
        }else {
            return this.name.compareTo(p.name);
        }
    }
}
