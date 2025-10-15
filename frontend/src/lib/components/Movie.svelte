<script lang="ts">
	import type { MovieType } from '$lib/types';
	let { movie }: { movie: MovieType } = $props();
</script>

<main
	class="card preset-filled-surface-100-900 border-surface-200-800 card-hover divide-surface-200-800 block max-w-md divide-y overflow-hidden border-[1px]"
>
	<header>
		<img
			class="h-[500px] w-full object-cover"
			src={`https://image.tmdb.org/t/p/original/${movie.poster_path}`}
			alt="Upcoming movies"
		/>
	</header>

	<article class="space-y-4 p-4">
		<div>
			<h2 class="text-sm text-gray-500">{movie.original_title}</h2>
		</div>
		<p class="line-clamp-3 opacity-60">
			{movie.overview}
		</p>
	</article>

	<footer class="flex items-center justify-center gap-4 p-5">
		<button class="btn preset-filled-secondary-500 capitalize">add to bucket</button>
	</footer>
</main>
