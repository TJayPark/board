package com.example.board.repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.example.board.entity.User;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ExRepository {
    private final EntityManager em;

    public void saveUser(User user){
        System.out.println("8888");
        em.persist(user);
        System.out.println("9999");
    }

    public User findUserByEmail(String email){
        TypedQuery<User> query = em.createQuery("select m from User as m where m.email = ?1", User.class)
                .setParameter(1, email);
        return query.getSingleResult();
    }
}
