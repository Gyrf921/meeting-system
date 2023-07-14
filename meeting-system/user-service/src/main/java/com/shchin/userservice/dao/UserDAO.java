package com.shchin.userservice.dao;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "users", schema="public")
public class UserDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;

    public UserDAO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDAO userDAO = (UserDAO) o;

        if (id != null ? !id.equals(userDAO.id) : userDAO.id != null) return false;
        return userName != null ? userName.equals(userDAO.userName) : userDAO.userName == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }
}
