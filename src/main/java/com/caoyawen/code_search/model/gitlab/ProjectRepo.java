package com.caoyawen.code_search.model.gitlab;

import org.springframework.data.repository.CrudRepository;

public interface ProjectRepo extends CrudRepository<ProjectModel, Integer> {
}
