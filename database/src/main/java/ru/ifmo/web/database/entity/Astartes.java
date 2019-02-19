package ru.ifmo.web.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class Astartes {
    private Long id;
    private String name;
    private String title;
    private String position;
    private String planet;
    private Date birthdate;
}
