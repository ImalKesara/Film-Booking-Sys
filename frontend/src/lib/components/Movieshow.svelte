<script lang="ts">
	import type { MovieDto } from '$lib/types';
	let { movies }: { movies: MovieDto[] } = $props();
	const filteredMovieShows = movies.filter((mov) => mov.movieShows.length > 0);
</script>

<main>
	<div class="mx-auto mb-4 flex max-w-4xl justify-center border-b border-gray-300 p-5 pb-2">
		<h1 class="text-xl">Now Showing !!!</h1>
	</div>
	<div class="mb-20 grid max-w-7xl grid-cols-2">
		{#each filteredMovieShows as movie}
			<a href="/movie/{movie.movieId}">
				<h1>{movie.title}</h1>
				<img
					class="h-full w-full object-cover"
					src={`https://image.tmdb.org/t/p/original/${movie.backDropPathUrl}`}
					alt={movie.title || 'Featured movie'}
				/>
			</a>
		{/each}
	</div>
</main>
