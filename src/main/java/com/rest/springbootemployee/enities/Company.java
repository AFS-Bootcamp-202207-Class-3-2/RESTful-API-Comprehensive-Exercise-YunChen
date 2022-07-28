package com.rest.springbootemployee.enities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rest.springbootemployee.utils.SnowFlakeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "company")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Company {
    @Id
    @GeneratedValue(generator = "snowflake")
    @GenericGenerator(name = "snowflake",strategy = "com.rest.springbootemployee.utils.SnowFlakeUtil")
    public String id;
    public Company() {

    }

    public Company(String id, String companyName, List<Employee> employees) {
        this.id = id;
        this.companyName = companyName;
        this.employees = employees;
    }

    @Column(name = "companyName")
    private String companyName;
    @OneToMany
//    @OneToMany(targetEntity=Employee.class,mappedBy = "companyName",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="companyId",referencedColumnName = "id")
    private List<Employee> employees = new ArrayList<>();

    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "createTime")
    @CreatedDate
    private Date createTime;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "updateTime")
    @LastModifiedDate
    private Date updateTime;
}
