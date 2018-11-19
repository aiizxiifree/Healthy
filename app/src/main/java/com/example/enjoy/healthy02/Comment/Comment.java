package com.example.enjoy.healthy02.Comment;

public class Comment {

    private String id;

    private String body;

    private String email;

    private String name;

    private String postId;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getBody ()
    {
        return body;
    }

    public void setBody (String body)
    {
        this.body = body;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getPostId ()
    {
        return postId;
    }

    public void setPostId (String postId)
    {
        this.postId = postId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", body = "+body+", email = "+email+", name = "+name+", postId = "+postId+"]";
    }
}
