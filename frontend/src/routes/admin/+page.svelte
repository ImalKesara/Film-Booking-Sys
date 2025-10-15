<script lang="ts">
	import { goto } from '$app/navigation';
	import Movie from '$lib/components/Movie.svelte';
	import Pagination from '$lib/components/Pagination.svelte';
	import type { MovieType } from '$lib/types';
	let {
		data
	}: {
		data: { movies: MovieType[]; totalPages: number; currentPage: number; totalResult: number };
	} = $props();
</script>

<main class="mx-auto max-w-7xl space-y-8 p-4 my-5">
	<div class="grid grid-cols-1 gap-4 md:grid-cols-2 lg:grid-cols-4">
		{#each data.movies as movie (movie.id)}
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
