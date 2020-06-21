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

    public InmemoryCandidate setFullName(String fullName) {
        properties.put("fullName", fullName.trim());
        return this;
    }

    public String getFirstName() {
        return (String) properties.get("firstName");
    }

    public InmemoryCandidate setFirstName(String firstName) {
        properties.put("firstName", firstName.trim());
        return this;
    }

    public String getMiddleName() {
        return (String) properties.get("middleName");
    }

    public InmemoryCandidate setMiddleName(String middleName) {
        properties.put("middleName", middleName.trim());
        return this;
    }

    public String getLastName() {
        return (String) properties.get("lastName");
    }

    public InmemoryCandidate setLastName(String lastName) {
        properties.put("lastName", lastName.trim());
        return this;
    }

    public String getEmail() {
        return (String) properties.get("email");
    }

    public InmemoryCandidate setEmail(String email) {
        properties.put("email", email.trim());
        return this;
    }

    public Integer getAge() {
        return (Integer) properties.get("age");
    }

    public InmemoryCandidate setAge(Integer age) {
        properties.put("age", age);
        return this;
    }

    public Boolean isDefault() {
        return (Boolean) properties.get("default");
    }

    public InmemoryCandidate setDefault(Boolean defaultCandidate) {
        properties.put("default", defaultCandidate);
        return this;
    }

    public InmemoryCandidate(){
        setFullName("");
        setDefault(false);
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
