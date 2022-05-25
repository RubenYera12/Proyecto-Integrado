package com.ruben.rfaf.referee.application;

import com.ruben.rfaf.referee.domain.Referee;

import java.util.List;

public interface RefereeService {

    Referee findByEmail(String email) throws Exception;

    Referee findByLicenseNum(String licenseNum) throws Exception;

    Referee addUser(Referee user) throws Exception;
    Referee updateUser(Referee user,String id) throws Exception;
    List<Referee> findAll();

    List<Referee> findByCategory(String name) throws Exception;

    String deleteById(String id) throws Exception;
    Referee findById(String id) throws Exception;
}
