package com.rest.springbootemployee.enities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rest.springbootemployee.utils.SnowFlakeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "employee")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Employee {
    @Id
    @GeneratedValue(generator = "snowflake")
    @GenericGenerator(name = "snowflake",strategy = "com.rest.springbootemployee.utils.SnowFlakeUtil")
    private String id;
    @Column(name = "name")
    private String name;

    public Employee() {
    }

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private String gender;
    @Column(name = "salary")
    private Integer salary;
    @Column(name = "companyName")
    private String companyName;


    public Employee(String id, String name, int age, String gender, Integer salary, String companyName) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyName = companyName;
        createTime = new Date();
        updateTime = new Date();
    }
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "createTime")
    @CreatedDate
    private Date createTime;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "updateTime")
    @LastModifiedDate
    private Date updateTime;
}
