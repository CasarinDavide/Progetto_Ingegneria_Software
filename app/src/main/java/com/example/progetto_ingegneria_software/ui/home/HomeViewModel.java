package com.example.progetto_ingegneria_software.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Post;

import java.util.List;


/**
 * The ViewModel contains information on the structure of data presented, it does not care about how it is displayed
 * It is used to pass data between fragments
 *
 * It's not useful, for now
 */
public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<Post>> postList;

    public HomeViewModel() {
        postList = new MutableLiveData<>();
    }
    /*
    public MutableLiveData<List<Post>> getPosts() {
        //return postList;
    }

    public void setPosts(List<Post> posts) {
        //postList.setValue(posts);
    }
    */
}