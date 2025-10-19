<script lang="ts">
	import Movie from '$lib/components/Movie.svelte';
	import Pagination from '$lib/components/Pagination.svelte';
	import type { MovieDto, MovieType } from '$lib/types';
	let {
		data
	}: {
		data: {
			movies: MovieType[];
			totalPages: number;
			currentPage: number;
			totalResult: number;
			springMovies: MovieDto[];
		};
	} = $props();

	const springMovies = $derived(new Set((data.springMovies ?? []).map((m) => String(m.tmdbId))));
	let allMoviesFromTmdbIds = $derived(
		data.movies.filter((movie) => !springMovies.has(String(movie.id)))
	);
</script>

<main class="max-w-10/12 mx-auto my-5 space-y-8 p-4">
	<div class="grid grid-cols-1 gap-4 md:grid-cols-3 lg:grid-cols-4">
		{#each allMoviesFromTmdbIds as movie}
			<Movie {movie} />
		{/each}
	</div>
	<div>
		<Pagination
			currentPage={data.currentPage}
			pageSize={data.movies.length}
			totalResult={data.totalResult}
		/>
	</div>
</main>
