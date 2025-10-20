<script lang="ts">
	import BucketMovie from '$lib/components/BucketMovie.svelte';
	import type { MovieDto } from '$lib/types';
	let { data }: { data: { movies: MovieDto[] } } = $props();
</script>

<main class="max-w-10/12 mx-auto my-5 space-y-8 p-4">
	<input type="text"  class="input" name="" id="" placeholder="Search Movie">
	<div class="grid grid-cols-1 gap-4 md:grid-cols-3 lg:grid-cols-5">
		{#each data.movies as movie (movie.movieId)}
			<BucketMovie {movie} />
		{/each}
	</div>
</main>
