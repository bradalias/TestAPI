package com.brad.at.valueobjects.harrypotter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterVO
{
    @XmlAttribute()
    @JsonProperty("fullName")
    private String fullName;
    @XmlAttribute()
    @JsonProperty("nickname")
    private String nickname;
    @XmlAttribute()
    @JsonProperty("hogwartsHouse")
    private String hogwartsHouse;
    @XmlAttribute()
    @JsonProperty("interpretedBy")
    private String interpretedBy;
    @XmlAttribute()
    @JsonProperty("children")
    private String[] children; //Array??
    @XmlAttribute()
    @JsonProperty("image")
    private String image;
    @XmlAttribute()
    @JsonProperty("birthDate")
    private String birthDate;
    @XmlAttribute()
    @JsonProperty("index")
    private String index; //Integer
}
