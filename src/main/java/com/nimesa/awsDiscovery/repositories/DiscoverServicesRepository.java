package com.nimesa.awsDiscovery.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nimesa.awsDiscovery.Entities.Instances;
import com.nimesa.awsDiscovery.services.Jobservices.ThreadInfo;

public interface DiscoverServicesRepository extends JpaRepository<Instances, Long> {

	List<Instances> findByThreadId(long threadId);
	List<Instances> findByService(String service);
}