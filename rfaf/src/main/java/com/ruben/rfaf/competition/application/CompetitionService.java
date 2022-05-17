package com.ruben.rfaf.competition.application;

import com.ruben.rfaf.competition.domain.Competition;

import java.util.List;

public interface CompetitionService {


    Competition createCompetition(Competition competition) throws Exception;

    Competition findById(String id) throws Exception;

    Competition findByName(String name) throws Exception;

    List<Competition> findAll();

    void deleteById(String id) throws Exception;

    Competition update(Competition competition, String id) throws Exception;
}
