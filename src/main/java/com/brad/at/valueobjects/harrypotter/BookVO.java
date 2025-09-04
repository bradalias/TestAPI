package com.brad.at.valueobjects.harrypotter;

import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookVO
{
    @XmlAttribute()
    private String number; //Integer
    @XmlAttribute()
    private String title;
    @XmlAttribute()
    private String originalTitle;
    @XmlAttribute()
    private String releaseDate;
    @XmlAttribute()
    private String description;
    @XmlAttribute()
    private String pages; //Integer
    @XmlAttribute()
    private String cover;
    @XmlAttribute()
    private String index; //Integer
}
