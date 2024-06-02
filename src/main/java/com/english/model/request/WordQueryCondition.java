package com.english.model.request;

public class WordQueryCondition extends QueryCondition
{
    private String word;

    private String category;

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }
}
