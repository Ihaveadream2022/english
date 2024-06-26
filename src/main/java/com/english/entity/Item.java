package com.english.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

public class Item {

    private Long id;

    private Short category;

    @NotBlank(message = "name is required")
    @Length(min = 1, max = 64, message = "name length is between 1 and 64")
    private String name;

    @Length(min = 0, max = 64, message = "pronounce length is between 0 and 64")
    private String pronounce;

    @Length(min = 0, max = 64, message = "common length is between 0 and 64")
    private String common;

    @Length(min = 0, max = 1024, message = "noun length is between 0 and 1024")
    private String noun;

    @Length(min = 0, max = 64, message = "nounPlural length is between 0 and 64")
    private String nounPlural;

    @Length(min = 0, max = 1024, message = "verb length is between 0 and 1024")
    private String verb;

    @Length(min = 0, max = 64, message = "verbPastTense length is between 0 and 64")
    private String verbPastTense;

    @Length(min = 0, max = 64, message = "verbPastParticiple length is between 0 and 64")
    private String verbPastParticiple;

    @Length(min = 0, max = 64, message = "verbPresentParticiple length is between 0 and 64")
    private String verbPresentParticiple;

    @Length(min = 0, max = 64, message = "verbThirdPersonSingular length is between 0 and 64")
    private String verbThirdPersonSingular;

    @Length(min = 0, max = 1024, message = "adjective length is between 0 and 1024")
    private String adjective;

    @Length(min = 0, max = 1024, message = "adverb length is between 0 and 1024")
    private String adverb;

    @Length(min = 0, max = 1024, message = "conjunction length is between 0 and 1024")
    private String conjunction;

    @Length(min = 0, max = 1024, message = "pronoun length is between 0 and 1024")
    private String pronoun;

    @Length(min = 0, max = 1024, message = "preposition length is between 0 and 1024")
    private String preposition;

    @Length(min = 0, max = 1024, message = "comment length is between 0 and 1024")
    private String comment;

    private ItemTts tts;

    private ItemExample example;

    public Long getId() {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Short getCategory()
    {
        return category;
    }

    public void setCategory(Short category)
    {
        this.category = category;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPronounce()
    {
        return pronounce;
    }

    public void setPronounce(String pronounce)
    {
        this.pronounce = pronounce;
    }

    public String getCommon()
    {
        return common;
    }

    public void setCommon(String common)
    {
        this.common = common;
    }

    public String getNoun()
    {
        return noun;
    }

    public void setNoun(String noun)
    {
        this.noun = noun;
    }

    public String getNounPlural()
    {
        return nounPlural;
    }

    public void setNounPlural(String nounPlural)
    {
        this.nounPlural = nounPlural;
    }

    public String getVerb()
    {
        return verb;
    }

    public void setVerb(String verb)
    {
        this.verb = verb;
    }

    public String getVerbPastTense()
    {
        return verbPastTense;
    }

    public void setVerbPastTense(String verbPastTense)
    {
        this.verbPastTense = verbPastTense;
    }

    public String getVerbPastParticiple()
    {
        return verbPastParticiple;
    }

    public void setVerbPastParticiple(String verbPastParticiple)
    {
        this.verbPastParticiple = verbPastParticiple;
    }

    public String getVerbPresentParticiple()
    {
        return verbPresentParticiple;
    }

    public void setVerbPresentParticiple(String verbPresentParticiple) {
        this.verbPresentParticiple = verbPresentParticiple;
    }

    public String getVerbThirdPersonSingular()
    {
        return verbThirdPersonSingular;
    }

    public void setVerbThirdPersonSingular(String verbThirdPersonSingular) {
        this.verbThirdPersonSingular = verbThirdPersonSingular;
    }

    public String getAdjective()
    {
        return adjective;
    }

    public void setAdjective(String adjective)
    {
        this.adjective = adjective;
    }

    public String getAdverb()
    {
        return adverb;
    }

    public void setAdverb(String adverb)
    {
        this.adverb = adverb;
    }

    public String getConjunction()
    {
        return conjunction;
    }

    public void setConjunction(String conjunction)
    {
        this.conjunction = conjunction;
    }

    public String getPronoun()
    {
        return pronoun;
    }

    public void setPronoun(String pronoun)
    {
        this.pronoun = pronoun;
    }

    public String getPreposition()
    {
        return preposition;
    }

    public void setPreposition(String preposition)
    {
        this.preposition = preposition;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ItemTts getTts() {
        return tts;
    }

    public void setTts(ItemTts tts) {
        this.tts = tts;
    }

    public ItemExample getExample() {
        return example;
    }

    public void setExample(ItemExample example) {
        this.example = example;
    }
}