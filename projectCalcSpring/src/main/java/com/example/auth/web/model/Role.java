package com.example.auth.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="role")
@Data
public class Role {
  @Id
  @GeneratedValue
  private Long id;
  
  private String name;

  public Role(String name){
    this.name = name;
  }
  public Role(){
      
  }
}