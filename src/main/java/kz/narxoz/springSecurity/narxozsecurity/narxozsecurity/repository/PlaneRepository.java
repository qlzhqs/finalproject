package kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.repository;

import kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.model.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PlaneRepository extends JpaRepository<Plane, Long> {
}
