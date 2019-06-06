package com.m2istvequipe3.ServeurDomotique.Equipement;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Equipement_Repository extends CrudRepository<Equipement, Long> {

    @Query("select e from Equipement e where e.id=:id")
    Equipement getEquipement(@Param("id") String id);

    @Query(value = "SELECT value_after FROM  donnees.donnees WHERE id=(SELECT max(id) FROM donnees.donnees where type='Temperature') and type='Temperature'",nativeQuery = true)
    String resultat();

    @Query(value = "SELECT value_after FROM  donnees.donnees WHERE id=(SELECT max(id) FROM donnees.donnees where type='Luminance') and type='Luminance'",nativeQuery = true)
    String Luminance();
}
