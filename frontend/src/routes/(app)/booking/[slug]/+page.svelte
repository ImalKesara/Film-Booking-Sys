<script lang="ts">
	import { page } from '$app/state';
	import type { MovieDto } from '$lib/types';
	import { onMount } from 'svelte';
	const slug = page.params.slug;
	let movieData: MovieDto | null = $state(null);

	onMount(async () => {
		const [movie] = await Promise.all([fetch(`/api/user/movie/${slug}`)]);
		if (!movie.ok) throw new Error('Movie not found');
		const data = await movie.json();
		movieData = data;
	});
</script>

<section class="grid grid-cols-2">
	<div>
      
   </div>
	<!-- img section -->
	<div>
		<h1>{movieData?.title}</h1>
		<img
			class="h-[320px] w-full object-cover"
			src={`https://image.tmdb.org/t/p/original/${movieData?.posterUrl}`}
			alt="Upcoming movies"
		/>
	</div>
</section>
