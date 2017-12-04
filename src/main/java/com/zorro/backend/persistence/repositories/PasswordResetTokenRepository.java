package com.zorro.backend.persistence.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zorro.backend.persistence.domain.backend.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long>{
	
	PasswordResetToken findByToken(String token);
	
	@Query("select prt from PasswordResetToken prt inner join prt.user u where prt.user.id = ?1")
	Set<PasswordResetToken> findAllByUserId(long userId);
}
