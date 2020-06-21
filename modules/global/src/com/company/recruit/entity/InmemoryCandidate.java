package com.company.recruit.entity;

import com.haulmont.cuba.core.entity.KeyValueEntity;

/**
 * Кандидат храящийся в памяти.
 * Значения атрибутов в Map, в связи с тем что необходимо наследование
 * от KeyValueEntity для соблюдения контракта.
 */
public class InmemoryCandidate extends KeyValueEntity {

    public String getFullName() {
       return (String) properties.get("fullName");
    }

    public void setFullName(String fullName) {
        properties.put("fullName", fullName.trim());
    }

    public String getFirstName() {
        return (String) properties.get("firstName");
    }

    public void setFirstName(String firstName) {
        properties.put("firstName", firstName.trim());
    }

    public String getMiddleName() {
        return (String) properties.get("middleName");
    }

    public void setMiddleName(String middleName) {
        properties.put("middleName", middleName.trim());
    }

    public String getLastName() {
        return (String) properties.get("lastName");
    }

    public void setLastName(String lastName) {
        properties.put("lastName", lastName.trim());
    }

    public String getEmail() {
        return (String) properties.get("email");
    }

    public void setEmail(String email) {
        properties.put("email", email.trim());
    }

    public Integer getAge() {
        return (Integer) properties.get("age");
    }

    public void setAge(Integer age) {
        properties.put("age", age);
    }

    public Boolean isDefault() {
        return (Boolean) properties.get("default");
    }

    public void setDefault(Boolean defaultCandidate) {
        properties.put("default", defaultCandidate);
    }

    public InmemoryCandidate(String fullName, String firstName, String middleName, String lastName, String email, Integer age, Boolean defaultCandidate) {
        properties.put("fullName", fullName);
        properties.put("firstName", firstName);
        properties.put("middleName", middleName);
        properties.put("lastName", lastName);
        properties.put("email", email);
        properties.put("age", age);
        properties.put("default", defaultCandidate);
    }

}
