package com.school.data.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientModel extends AbstractModel
{
    protected String name;
    protected String first;
    protected String city;
    protected String postal;
    protected String street;
    protected String tel;
}
