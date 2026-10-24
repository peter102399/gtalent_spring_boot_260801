package student.gtalent_spring_boot_260801.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import student.gtalent_spring_boot_260801.entity.MoneyRecord;

@Repository
public interface MoneyRecordRepository extends JpaRepository<MoneyRecord, Long> {
}