package com.zensar.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zensar.entity.XmlOrderEntity;

public interface XmlOrderMessageConsumerRepo extends JpaRepository<XmlOrderEntity, Integer>{

}
