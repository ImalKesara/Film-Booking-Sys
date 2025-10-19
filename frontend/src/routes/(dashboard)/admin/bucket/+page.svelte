<script lang="ts">
	import BucketMovie from '$lib/components/BucketMovie.svelte';
	import Movie from '$lib/components/Movie.svelte';
	import type { MovieDto } from '$lib/types';
	let { data }: { data: { movies: MovieDto[] } } = $props();
</script>

<main class="max-w-10/12 mx-auto my-5 space-y-8 p-4">
	<div class="grid grid-cols-1 gap-4 md:grid-cols-3 lg:grid-cols-4">
		{#each data.movies as movie (movie.movieId)}
			<BucketMovie {movie} />
		{/each}
	</div>
</main>
