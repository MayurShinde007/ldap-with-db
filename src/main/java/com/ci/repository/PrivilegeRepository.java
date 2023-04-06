package com.ci.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ci.models.EPrivilege;
import com.ci.models.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	Optional<Privilege> findByPrivilege(EPrivilege privilege);

	@Override
	void delete(Privilege privilege);

}