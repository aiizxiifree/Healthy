package com.example.enjoy.healthy02.Comment;

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

public class CommentAdapter extends ArrayAdapter<Comment>{

    private Context context;
    private ArrayList<Comment> comments;

    public CommentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Comment> objects) {
        super(context, resource, objects);
        this.comments = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        View commentItem = LayoutInflater.from(context).inflate(R.layout.fragment_comment_item, parent, false);

        TextView id = commentItem.findViewById(R.id.commentitem_id);
        TextView body = commentItem.findViewById(R.id.commentitem_body);
        TextView name = commentItem.findViewById(R.id.commentitem_name);
        TextView email = commentItem.findViewById(R.id.commentitem_email);
        Comment row = comments.get(position);
        id.setText(row.getPostId()+" : " + row.getId());
        name.setText(row.getName());
        body.setText(row.getBody());
        email.setText("(" + row.getEmail()+ ")");


        return commentItem;
    }
}
