package com.carousell.codingchallenge.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.carousell.codingchallenge.R;
import com.carousell.codingchallenge.api.ApiRepository;
import com.carousell.codingchallenge.api.CodingChallengeApi;
import com.carousell.codingchallenge.model.Post;
import com.carousell.codingchallenge.util.Formatter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
	RecyclerView lsPosts;
	View tError;
	PostsAdapter adapter;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);

		final Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		tError = findViewById(R.id.tError);

		lsPosts = findViewById(R.id.lsPosts);
		lsPosts.setLayoutManager(new LinearLayoutManager(this));
		lsPosts.setAdapter(adapter = new PostsAdapter());

		new Thread(this::loadPosts).start();
	}

	void loadPosts() {
		final CodingChallengeApi api = ApiRepository.get().getCodingChallengeApi();

		try {
			final List<Post> posts = api.getAllPosts().execute().body();
			Collections.sort(posts, (a, b) -> b.time_created - a.time_created);
			runOnUiThread(() -> adapter.setData(posts));
		} catch (IOException e) {
			runOnUiThread(() -> {
				lsPosts.setVisibility(View.GONE);
				tError.setVisibility(View.VISIBLE);
			});
		}
	}

	static class PostHolder extends RecyclerView.ViewHolder {
		final ImageView imgBanner;
		final TextView tTitle;
		final TextView tDescription;
		final TextView tTimeCreated;

		PostHolder(final View itemView) {
			super(itemView);

			imgBanner = itemView.findViewById(R.id.imgBanner);
			tTitle = itemView.findViewById(R.id.tTitle);
			tDescription = itemView.findViewById(R.id.tDescription);
			tTimeCreated = itemView.findViewById(R.id.tTimeCreated);
		}
	}

	class PostsAdapter extends RecyclerView.Adapter<PostHolder> {
		final List<Post> posts = new ArrayList<>();

		PostsAdapter() {
			setHasStableIds(true);
		}

		@Override
		public long getItemId(final int position) {
			final String id = posts.get(position).id;
			try {
				return Long.parseLong(id);
			} catch (NumberFormatException e) {
				return id.hashCode();
			}
		}

		@NonNull
		@Override
		public PostHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
			return new PostHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
		}

		@Override
		public void onBindViewHolder(@NonNull final PostHolder holder, final int position) {
			final Post post = posts.get(position);

			Picasso.with(holder.itemView.getContext()).load(post.banner_url).centerCrop().fit().into(holder.imgBanner);
			holder.tTitle.setText(post.title);
			holder.tDescription.setText(post.description);
			holder.tTimeCreated.setText(Formatter.formatTime(post.time_created));
		}

		@Override
		public int getItemCount() {
			return posts.size();
		}

		public void setData(List<Post> posts) {
			this.posts.clear();
			this.posts.addAll(posts);
			notifyDataSetChanged();
		}
	}
}
