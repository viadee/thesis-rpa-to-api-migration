package org.example.data.repo;

import org.example.data.model.VarMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VarMappingRepo extends JpaRepository<VarMapping, Long> {
    List<VarMapping> findAll();
    @Query("SELECT v FROM VarMapping v WHERE v.apiTopic.id = :apiTopic")
    List<VarMapping> findVarMappingsByTopic(@Param("apiTopic") String apiTopic);
    @Query("SELECT v FROM VarMapping v WHERE v.apiTopic.id = :apiTopic AND v.varType = 'IN'")
    List<VarMapping> findInVarsOfTopic(@Param("apiTopic") String apiTopic);
    @Query("SELECT v FROM VarMapping v WHERE v.apiTopic.id = :apiTopic AND v.varType = 'OUT'")
    List<VarMapping> findOutVarsOfTopic(@Param("apiTopic") String apiTopic);
    @Query("SELECT v.botVar FROM VarMapping v WHERE v.apiTopic.id = :apiTopic")
    List<String> findBotVarsByTopic(@Param("apiTopic") String apiTopic);
}