package com.spark.bean;

import java.sql.Date;
import java.util.Objects;

public class Emp {

    private int empno;
    private String ename;
    private String job;
    private int mgr;

    private Date hiredate;
    private double salary;
    private double comm;
    private int deptno;

    public Emp() {
    }

    public Emp(int empno, String ename, String job, int mgr, Date hiredate, double salary, double comm, int deptno) {
        this.empno = empno;
        this.ename = ename;
        this.job = job;
        this.mgr = mgr;
        this.hiredate = hiredate;
        this.salary = salary;
        this.comm = comm;
        this.deptno = deptno;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "empno=" + empno +
                ", ename='" + ename + '\'' +
                ", job='" + job + '\'' +
                ", mgr=" + mgr +
                ", hiredate=" + hiredate +
                ", salary=" + salary +
                ", comm=" + comm +
                ", deptno=" + deptno +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emp emp = (Emp) o;
        return empno == emp.empno &&
                mgr == emp.mgr &&
                Double.compare(emp.salary, salary) == 0 &&
                Double.compare(emp.comm, comm) == 0 &&
                deptno == emp.deptno &&
                Objects.equals(ename, emp.ename) &&
                Objects.equals(job, emp.job) &&
                Objects.equals(hiredate, emp.hiredate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empno, ename, job, mgr, hiredate, salary, comm, deptno);
    }

    public int getEmpno() {
        return empno;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getMgr() {
        return mgr;
    }

    public void setMgr(int mgr) {
        this.mgr = mgr;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getComm() {
        return comm;
    }

    public void setComm(double comm) {
        this.comm = comm;
    }

    public int getDeptno() {
        return deptno;
    }

    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }
}
