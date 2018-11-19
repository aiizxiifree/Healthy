package com.example.enjoy.healthy02.Comment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.enjoy.healthy02.Post.Post;
import com.example.enjoy.healthy02.Post.PostFragment;
import com.example.enjoy.healthy02.R;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CommentFragment extends Fragment{

    private OkHttpClient client = new OkHttpClient();
    private Bundle bundle;
    private String myResponse;
    private ArrayList<Comment> commentArray = new ArrayList<Comment>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button backButton = getView().findViewById(R.id.comment_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        run();
    }

    public void back(){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_view, new PostFragment())
                .addToBackStack(null)
                .commit();
    }

    public void run(){
        bundle = this.getArguments();
        String url = "https://jsonplaceholder.typicode.com/posts/"+ bundle.getString("post_id") +"/comments";
        Request request = new Request.Builder().url(url).build();
        Log.d("comment", bundle.getString("post_id"));
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response.isSuccessful()){
                    Log.d("comment", "response");
                    myResponse = response.body().string();
                    CommentFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ListView commentListview = getView().findViewById(R.id.comment_listview);
                            CommentAdapter commentAdapter = new CommentAdapter(getActivity(), R.layout.fragment_comment_item, commentArray);

                            Gson gson = new Gson();
                            Type type = new TypeToken<Collection<Comment>>(){}.getType();
                            Collection<Comment> comment = gson.fromJson(myResponse, type);


                            final Comment[] comments = comment.toArray(new Comment[comment.size()]);
                            Log.d("comment", comment.toString());
                            for(int i = 0 ; i < comments.length; i++){
                                Comment tempcomment = new Comment();
                                tempcomment.setId(comments[i].getId());
                                Log.d("comment", comments[i].getId());
                                tempcomment.setEmail(comments[i].getEmail());
                                tempcomment.setBody(comments[i].getBody());
                                tempcomment.setName(comments[i].getName());
                                tempcomment.setPostId(comments[i].getPostId());
                                commentArray.add(tempcomment);
                            }

                            commentListview.setAdapter(commentAdapter);

                        }
                    });
                }
            }
        });
    }

}
