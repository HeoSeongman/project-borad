package com.fastcampus.projectborad.repository;

import com.fastcampus.projectborad.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@RepositoryRestResource
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {

}