package com.example.enjoy.healthy02.Post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.enjoy.healthy02.R;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {

    private Context context;
    private ArrayList<Post> posts;

    public PostAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Post> objects) {
        super(context, resource, objects);
        this.context = context;
        this.posts = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View postItem = LayoutInflater.from(context).inflate(R.layout.fragment_post_item, parent, false);

        TextView id = postItem.findViewById(R.id.postitem_id);
        TextView body = postItem.findViewById(R.id.postitem_body);

        Post row = posts.get(position);

        id.setText(row.getId() + " : " + row.getTitle());
        body.setText(row.getBody());

        return postItem;
    }
}
