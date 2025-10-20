<script lang="ts">
	import { invalidateAll } from '$app/navigation';
	import { toaster } from '$lib/states/toaster.svelte';
	import type { MovieType } from '$lib/types';
	let { movie }: { movie: MovieType } = $props();

	const addToBucket = async () => {
		const token = localStorage.getItem('token');
		const passMovie = await fetch('/api/admin/movie', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${token}}`
			},
			body: JSON.stringify({
				title: movie.title,
				description: movie.overview,
				rating: movie.vote_average,
				posterUrl: movie.poster_path,
				backDropPathUrl: movie.backdrop_path,
				tmdbId: movie.id,
				createdAt: new Date()
			})
		});

		if (passMovie.ok) {
			toaster.success({
				title: 'Added',
				description: `Movie "${movie.title}" added to bucket successfully.`
			});
			invalidateAll();
		} else {
			toaster.error({
				title: 'Error',
				description: `Failed to add movie "${movie.title}" to bucket.`
			});
		}
	};
</script>

<main
	class="card preset-filled-surface-100-900 border-surface-200-800 card-hover divide-surface-200-800 block max-w-md divide-y overflow-hidden border-[1px]"
>
	<header>
		<img
			class="h-[450px] w-full object-cover"
			src={`https://image.tmdb.org/t/p/original/${movie.poster_path}`}
			alt="Upcoming movies"
		/>
	</header>

	<article class="space-y-4 p-4">
		<div>
			<h2 class="line-clamp-1 text-sm text-gray-500">{movie.original_title}</h2>
		</div>
		<p class="line-clamp-3 opacity-60">
			{movie.overview}
		</p>
	</article>

	<footer class="flex items-center justify-center gap-4 p-5">
		<button class="btn preset-filled-secondary-500 capitalize" onclick={addToBucket}
			>add to bucket</button
		>
	</footer>
</main>
