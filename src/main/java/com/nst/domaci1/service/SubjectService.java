package com.nst.domaci1.service;

import com.nst.domaci1.domain.Subject;

import java.util.List;

public interface SubjectService {

    Subject save(Subject subject) throws Exception;

    List<Subject> getAll();

    void delete(Long id) throws Exception;

    Subject update(Long id, int espb) throws Exception;

    Subject findById(Long id) throws Exception;
}
