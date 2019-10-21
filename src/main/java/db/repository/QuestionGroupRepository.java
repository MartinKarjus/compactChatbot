package db.repository;

import objects.dbentities.Company;
import objects.dbentities.QuestionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionGroupRepository extends JpaRepository<QuestionGroup, Long> {
    List<QuestionGroup> findByCompanyId(Long id);

}
