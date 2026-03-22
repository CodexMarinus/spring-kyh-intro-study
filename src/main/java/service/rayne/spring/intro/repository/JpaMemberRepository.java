package service.rayne.spring.intro.repository;

import service.rayne.spring.intro.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
  private final EntityManager entityManager;

  public JpaMemberRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Member save(Member member) {
    entityManager.persist(member);
    return member;
  }

  @Override
  public Optional<Member> findById(Long id) {
    Member member = entityManager.find(Member.class, id);
    return Optional.ofNullable(member);
  }

  @Override
  public Optional<Member> findByName(String name) {
    List<Member> result = entityManager.createQuery("select m from Member m where m.name = :name", Member.class)
            .setParameter("name", name)
            .getResultList();

    return result.stream().findAny();
  }

  @Override
  public List<Member> findAll() {
    return entityManager.createQuery("select m from Member m", Member.class).getResultList();
  }
}
