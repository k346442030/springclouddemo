package com.platform.service.business.dao;

import com.platform.service.business.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<K>, K extends Serializable>
		extends JpaRepository<T, K>, JpaSpecificationExecutor<T> {

}
