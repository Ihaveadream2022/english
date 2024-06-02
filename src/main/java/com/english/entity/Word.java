package com.english.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Word
{
    private Long id;

    @NotBlank(message = "word is required")
    @Size(min = 0, max = 64, message = "word's length is between 0 and 64")
    private String word;

    @Size(min = 0, max = 64, message = "pronounce's length is between 0 and 64")
    private String pronounce;

    @Size(min = 0, max = 64, message = "plural's length is between 0 and 64")
    private String plural;

    @Size(min = 0, max = 64, message = "pastTense's length is between 0 and 64")
    private String pastTense;

    @Size(min = 0, max = 64, message = "pastParticiple's length is between 0 and 64")
    private String pastParticiple;

    @Size(min = 0, max = 64, message = "presentParticiple's length is between 0 and 64")
    private String presentParticiple;

    @Size(min = 0, max = 64, message = "thirdPersonSingular's length is between 0 and 64")
    private String thirdPersonSingular;

    @Size(min = 0, max = 512, message = "chinese's length is between 0 and 512")
    private String chinese;

    @Size(min = 0, max = 1024, message = "examples's length is between 0 and 1024")
    private String examples;

    private Short category;

    private Byte isClassN;

    private Byte isClassV;

    private Byte isClassAdj;

    private Byte isClassAdv;

    private Byte isClassConj;

    private Byte isClassPron;

    private Byte isClassPrep;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPronounce()
    {
        return pronounce;
    }

    public void setPronounce(String pronounce)
    {
        this.pronounce = pronounce;
    }

    public String getPlural() {
        return plural;
    }

    public void setPlural(String plural) {
        this.plural = plural;
    }

    public String getPastTense() {
        return pastTense;
    }

    public void setPastTense(String pastTense) {
        this.pastTense = pastTense;
    }

    public String getPastParticiple() {
        return pastParticiple;
    }

    public void setPastParticiple(String pastParticiple) {
        this.pastParticiple = pastParticiple;
    }

    public String getPresentParticiple() {
        return presentParticiple;
    }

    public void setPresentParticiple(String presentParticiple) {
        this.presentParticiple = presentParticiple;
    }

    public String getThirdPersonSingular() {
        return thirdPersonSingular;
    }

    public void setThirdPersonSingular(String thirdPersonSingular) {
        this.thirdPersonSingular = thirdPersonSingular;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getExamples() {
        return examples;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    public Byte getIsClassN() {
        return isClassN;
    }

    public void setIsClassN(Byte isClassN) {
        this.isClassN = isClassN;
    }

    public Byte getIsClassV() {
        return isClassV;
    }

    public void setIsClassV(Byte isClassV) {
        this.isClassV = isClassV;
    }

    public Byte getIsClassAdj() {
        return isClassAdj;
    }

    public void setIsClassAdj(Byte isClassAdj) {
        this.isClassAdj = isClassAdj;
    }

    public Byte getIsClassAdv() {
        return isClassAdv;
    }

    public void setIsClassAdv(Byte isClassAdv) {
        this.isClassAdv = isClassAdv;
    }

    public Byte getIsClassConj() {
        return isClassConj;
    }

    public void setIsClassConj(Byte isClassConj) {
        this.isClassConj = isClassConj;
    }

    public Byte getIsClassPron() {
        return isClassPron;
    }

    public void setIsClassPron(Byte isClassPron) {
        this.isClassPron = isClassPron;
    }

    public Byte getIsClassPrep() {
        return isClassPrep;
    }

    public void setIsClassPrep(Byte isClassPrep) {
        this.isClassPrep = isClassPrep;
    }

    public Short getCategory()
    {
        return category;
    }

    public void setCategory(Short category)
    {
        this.category = category;
    }
}