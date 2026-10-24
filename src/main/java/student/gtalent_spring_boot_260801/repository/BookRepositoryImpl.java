package student.gtalent_spring_boot_260801.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import student.gtalent_spring_boot_260801.entity.Book;

@Repository
@Transactional
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> findAll(int page, int size, String sortBy) {
        String orderBy = switch (sortBy == null ? "id_asc" : sortBy) {
            case "price_asc" -> "b.price ASC";
            case "price_desc" -> "b.price DESC";
            default -> "b.id ASC";
        };
        return entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.status = 1 ORDER BY " + orderBy, Book.class)
                .setFirstResult(Math.max(0, page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public Book findOneById(Long id) {
        return entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.id = :id AND b.status = 1", Book.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Book> findOneByName(String name) {
        return entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.status = 1 AND b.name LIKE :name", Book.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public long countAll() {
        return entityManager.createQuery(
                "SELECT COUNT(b) FROM Book b WHERE b.status = 1", Long.class)
                .getSingleResult();
    }

    @Override
    public Book create(Book book) {
        entityManager.persist(book);
        return book;
    }

    @Override
    public Book update(Long id, Book book) {
        Book existing = entityManager.find(Book.class, id);
        if (existing == null || existing.getStatus() == 0) {
            return null;
        }
        existing.setName(book.getName());
        existing.setPrice(book.getPrice());
        return existing;
    }

    @Override
    public void delete(Long id) {
        Book existing = entityManager.find(Book.class, id);
        if (existing != null && existing.getStatus() == 1) {
            existing.setStatus((byte) 0);
            existing.setDeletedAt(LocalDateTime.now());
        }
    }
}
