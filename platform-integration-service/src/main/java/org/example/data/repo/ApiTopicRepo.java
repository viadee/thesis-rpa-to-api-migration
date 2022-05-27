package org.example.data.repo;

import org.example.data.model.ApiTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApiTopicRepo extends JpaRepository<ApiTopic, String> {
    List<ApiTopic> findAll();
}
