<script lang="ts">
	import { browser } from '$app/environment';
	import { invalidateAll } from '$app/navigation';
	import { toaster } from '$lib/states/toaster.svelte';
	import type { MovieDto } from '$lib/types';
	import { Undo2 } from '@lucide/svelte';
	let { movie }: { movie: MovieDto } = $props();

	const addToBucket = async () => {
		if (browser) {
			const token = localStorage.getItem('token');
			const response = await fetch(`/api/admin/movie/${movie.movieId}`, {
				method: 'DELETE',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}`
				}
			});

			if (response.ok) {
				toaster.success({
					title: 'Movie Returned',
					description: `${movie.title} has been returned from the bucket successfully.`
				});
				invalidateAll();
			} else {
				toaster.error({
					title: 'Error',
					description: `Failed to return ${movie.title} from the bucket. Please try again.`
				});
			}
		}
	};
</script>

<main
	class="card preset-filled-surface-100-900 border-surface-200-800 card-hover divide-surface-200-800 block max-w-md divide-y overflow-hidden border-[1px]"
>
	<header>
		<img
			class="h-[320px] w-full object-cover"
			src={`https://image.tmdb.org/t/p/original/${movie.posterUrl}`}
			alt="Upcoming movies"
		/>
	</header>

	<article class="space-y-4 p-4">
		<div>
			<h2 class="line-clamp-1 text-sm text-gray-500">{movie.title}</h2>
		</div>
		<p class="line-clamp-3 opacity-60">
			{movie.description}
		</p>
	</article>

	<footer class="flex items-center justify-center gap-4 p-5">
		<button class="btn preset-filled-error-500 capitalize" onclick={addToBucket}
			>Return <Undo2 size="16" /></button
		>
	</footer>
</main>
