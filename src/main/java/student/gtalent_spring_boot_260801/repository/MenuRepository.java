package student.gtalent_spring_boot_260801.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import student.gtalent_spring_boot_260801.entity.MenuItem;

@Repository
public class MenuRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<MenuItem> findAvailable() {
        return entityManager.createQuery(
                "SELECT m FROM MenuItem m WHERE m.status = 1 ORDER BY m.id", MenuItem.class)
                .getResultList();
    }

    public List<MenuItem> findAvailableByIds(List<Long> ids) {
        return entityManager.createQuery(
                "SELECT m FROM MenuItem m WHERE m.status = 1 AND m.id IN :ids", MenuItem.class)
                .setParameter("ids", ids)
                .getResultList();
    }
}
