package com.hyx.service;

import com.hyx.entity.Student;

import java.util.List;

public interface StudentService {
     List<Student> findAll();
     List<Student> findOne(long id);
     //插入100万条
     long insert(List<Student> student);

     boolean updateById(String university,Long l);
     boolean deleteById(long id);
}
