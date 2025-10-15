<script lang="ts">
	import Movie from '$lib/components/Movie.svelte';
	import type { MovieType } from '$lib/types';
	let { data }: { data: { movies: MovieType[] } } = $props();
</script>

<main class="mx-auto max-w-6xl space-y-8 p-4">
	<h1 class="text-center text-lg">TMDB Movies</h1>
	<div class="grid grid-cols-1 gap-4 md:grid-cols-2 lg:grid-cols-3">
		{#each data.movies as movie (movie.id)}
			<Movie {movie} />
		{/each}
	</div>
</main>
