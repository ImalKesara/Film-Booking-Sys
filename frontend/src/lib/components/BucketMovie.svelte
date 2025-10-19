<script lang="ts">
	import type { MovieDto } from '$lib/types';
	let { movie }: { movie: MovieDto } = $props();

	const addToBucket = async () => {};
</script>

<main
	class="card preset-filled-surface-100-900 border-surface-200-800 card-hover divide-surface-200-800 block max-w-md divide-y overflow-hidden border-[1px]"
>
	<header>
		<img
			class="h-[450px] w-full object-cover"
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
		<button class="btn preset-filled-secondary-500 capitalize" onclick={addToBucket}>Return</button>
	</footer>
</main>
