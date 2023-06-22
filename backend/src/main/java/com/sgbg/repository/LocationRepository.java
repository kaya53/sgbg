package com.sgbg.repository;

import com.sgbg.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LocationRepository extends JpaRepository<Location, String> {
}
