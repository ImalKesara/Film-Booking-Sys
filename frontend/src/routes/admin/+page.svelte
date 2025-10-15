<script lang="ts">
	import { goto } from '$app/navigation';
	import Movie from '$lib/components/Movie.svelte';
	import type { MovieType } from '$lib/types';
	let { data }: { data: { movies: MovieType[]; totalPages: number; currentPage: number } } =
		$props();
	const goToPage = function (pNumber: number = 1) {
		goto(`/admin?page=${pNumber}`);
	};
</script>

<main class="mx-auto max-w-7xl space-y-8 p-4">
	<h1 class="text-center text-lg">TMDB Movies</h1>
	<div class="grid grid-cols-2 gap-4 md:grid-cols-3 lg:grid-cols-4">
		{#each data.movies as movie (movie.id)}
			<Movie {movie} />
		{/each}
	</div>
	<div>
		<button
			class="btn preset-filled-primary-500 text-black"
			onclick={() => goToPage(data.currentPage + 1)}>Next</button
		>
	</div>
</main>
