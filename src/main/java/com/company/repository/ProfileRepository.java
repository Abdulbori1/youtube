package com.company.repository;

import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update ProfileEntity set photo.id = :attachId where id = :id")
    int updateAttach(@Param("attachId") String attachId, @Param("id") Integer id);

    Optional<ProfileEntity> findByEmailAndPassword(String email, String pswd);

    Optional<ProfileEntity> findByEmail(String email);
}
