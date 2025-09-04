package com.brad.at.valueobjects.harrypotter;

import jakarta.xml.bind.annotation.XmlAttribute;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
public class CharacterVO
{
    @XmlAttribute()
    private String fullName;
    @XmlAttribute()
    private String nickname;
    @XmlAttribute()
    private String hogwartsHouse;
    @XmlAttribute()
    private String interpretedBy;
    @XmlAttribute()
    private String[] children; //Array??
    @XmlAttribute()
    private String image;
    @XmlAttribute()
    private String birthDate;
    @XmlAttribute()
    private String index; //Integer
}
