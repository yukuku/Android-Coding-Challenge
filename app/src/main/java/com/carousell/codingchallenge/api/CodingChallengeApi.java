package com.carousell.codingchallenge.api;

import com.carousell.codingchallenge.model.Post;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface CodingChallengeApi {
	@GET("android/carousell_news.json")
	Call<List<Post>> getAllPosts();
}
