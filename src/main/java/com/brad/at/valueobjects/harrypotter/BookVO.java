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
public class BookVO
{
    @XmlAttribute()
    @JsonProperty("number")
    private String number; //Integer
    @XmlAttribute()
    @JsonProperty("title")
    private String title;
    @XmlAttribute()
    @JsonProperty("originalTitle")
    private String originalTitle;
    @XmlAttribute()
    @JsonProperty("releaseDate")
    private String releaseDate;
    @XmlAttribute()
    @JsonProperty("description")
    private String description;
    @XmlAttribute()
    @JsonProperty("pages")
    private String pages; //Integer
    @XmlAttribute()
    @JsonProperty("cover")
    private String cover;
    @XmlAttribute()
    @JsonProperty("index")
    private String index; //Integer
}
