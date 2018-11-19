package com.example.enjoy.healthy02.Post;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.enjoy.healthy02.Comment.CommentFragment;
import com.example.enjoy.healthy02.MainActivity;
import com.example.enjoy.healthy02.MenuFragment;
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

public class PostFragment extends Fragment {

    private OkHttpClient client = new OkHttpClient();
    private String myResponse;
    private ArrayList<Post> postsArray = new ArrayList<Post>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_post, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // back
        Button backButton = getView().findViewById(R.id.post_back_btn);
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
                .replace(R.id.main_view, new MenuFragment())
                .addToBackStack(null)
                .commit();
    }

    public void run(){
        String url = "https://jsonplaceholder.typicode.com/posts";
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(response.isSuccessful()){




                    PostFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final ListView listViewPosts = getView().findViewById(R.id.post_listview);


                            final PostAdapter postAdapter = new PostAdapter(getActivity(), R.layout.fragment_post_item, postsArray);

                            try {
                                myResponse = response.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Gson gson = new Gson();
                            Type type = new TypeToken<Collection<Post>>(){}.getType();
                            Collection<Post> post = gson.fromJson(myResponse, type);

                            final Post[] posts = post.toArray(new Post[post.size()]);
                            Log.d("Json", "post " + post.toString());
                            Log.d("Json", "posts lenght :  " + posts.length);

                            for(int i = 0 ; i < posts.length; i++){
                                Post tempPost = new Post();
                                tempPost.setId(posts[i].getId());
                                Log.d("Json", posts[i].getId());
                                tempPost.setTitle(posts[i].getTitle());
                                tempPost.setBody(posts[i].getBody());
                                tempPost.setUserId(posts[i].getUserId());
                                postsArray.add(tempPost);
                            }



                            listViewPosts.setAdapter(postAdapter);


                            listViewPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override

                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("post_id", postsArray.get(position).getId());
                                    CommentFragment commentFragment = new CommentFragment();
                                    commentFragment.setArguments(bundle);
                                    getActivity().getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.main_view, commentFragment)
                                            .addToBackStack(null)
                                            .commit();
                                }
                            });
                        }
                    });
                }
            }
        });
    }




}
