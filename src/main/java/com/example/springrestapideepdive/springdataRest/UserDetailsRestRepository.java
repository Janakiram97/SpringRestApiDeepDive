package com.example.springrestapideepdive.springdataRest;

import com.example.springrestapideepdive.springdata.UserDetailsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface UserDetailsRestRepository  extends PagingAndSortingRepository<UserDetailsEntity, Long>{
}
