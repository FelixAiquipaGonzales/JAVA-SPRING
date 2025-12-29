package pe.edu.sencico.contacto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.sencico.contacto.entity.Contacto;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long>{

}
