package com.zorro.backend.persistence.repositories;

import com.zorro.backend.persistence.domain.backend.Role;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>{

	
}
