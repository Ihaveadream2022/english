package com.english.model.request;

public class DeleteRequestBody {

    private Long id;

    private Long[] ids;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long[] getIds()
    {
        return ids;
    }

    public void setIds(Long[] ids)
    {
        this.ids = ids;
    }
}
