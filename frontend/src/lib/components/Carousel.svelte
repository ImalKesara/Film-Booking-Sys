<script lang="ts">
	import type { MovieDto } from '$lib/types';
	import { ArrowLeftIcon, ArrowRightIcon } from '@lucide/svelte';
	import { onMount, onDestroy } from 'svelte';

	let { movies }: { movies: MovieDto[] } = $props();
	let carousel: HTMLElement;
	let currentSlide = $state(0);
	let autoPlayInterval: ReturnType<typeof setInterval>;

	function scrollLeft() {
		if (carousel) {
			carousel.scrollBy({ left: -400, behavior: 'smooth' });
		}
	}

	function scrollRight() {
		if (carousel) {
			carousel.scrollBy({ left: 400, behavior: 'smooth' });
		}
	}

	function nextSlide() {
		currentSlide = (currentSlide + 1) % movies.length;
	}

	function goToSlide(index: number) {
		currentSlide = index;
	}

	onMount(() => {
		autoPlayInterval = setInterval(() => {
			nextSlide();
		}, 5000);
	});

	onDestroy(() => {
		if (autoPlayInterval) {
			clearInterval(autoPlayInterval);
		}
	});
</script>

<div class="w-full space-y-6">
	<!-- Auto-Scrolling Backdrop Section -->
	<div class="rounded-container-token relative h-[800px] w-full overflow-hidden">
		{#if movies.length > 0}
			{#each movies as movie, index}
				<div
					class="absolute inset-0 transition-opacity duration-700 ease-in-out"
					class:opacity-100={currentSlide === index}
					class:opacity-0={currentSlide !== index}
				>
					<img
						class="h-full w-full object-cover"
						src={`https://image.tmdb.org/t/p/original/${movie.backDropPathUrl}`}
						alt={movie.title || 'Featured movie'}
						loading={index === 0 ? 'eager' : 'lazy'}
					/>
					<div
						class="absolute inset-0 flex items-end bg-gradient-to-t from-black/80 via-black/40 to-transparent p-8"
					>
						<div class="text-white">
							<h2 class="mb-2 text-4xl font-bold">{movie.title || 'Featured Movie'}</h2>
							{#if movie.description}
								<p class="line-clamp-3 max-w-2xl text-lg opacity-90">{movie.description}</p>
							{/if}
						</div>
					</div>
				</div>
			{/each}

			<div class="absolute bottom-4 left-1/2 z-10 flex -translate-x-1/2 gap-2">
				{#each movies as _, index}
					<button
						onclick={() => goToSlide(index)}
						class="h-2 rounded-full transition-all duration-300"
						class:w-8={currentSlide === index}
						class:w-2={currentSlide !== index}
						class:bg-white={currentSlide === index}
						aria-label={`Go to slide ${index + 1}`}
					></button>
				{/each}
			</div>
		{/if}
	</div>

	<div class="group relative w-full">
		<button
			onclick={scrollLeft}
			class="btn variant-filled-surface absolute left-4 top-1/2 z-10 flex h-12 w-12 -translate-y-1/2 items-center justify-center rounded-full opacity-0 shadow-xl transition-opacity duration-300 hover:scale-110 group-hover:opacity-100"
			aria-label="Scroll left"
		>
			<ArrowLeftIcon class="h-6 w-6" />
		</button>

		<button
			onclick={scrollRight}
			class="btn variant-filled-surface absolute right-4 top-1/2 z-10 flex h-12 w-12 -translate-y-1/2 items-center justify-center rounded-full opacity-0 shadow-xl transition-opacity duration-300 hover:scale-110 group-hover:opacity-100"
			aria-label="Scroll right"
		>
			<ArrowRightIcon class="h-6 w-6" />
		</button>

		<div class="mx-auto mb-4 flex max-w-7xl justify-center border-b border-gray-300 p-5 pb-2">
			<h1 class="text-xl">Coming Soon !!!</h1>
		</div>

		<div class="card h-[450px] overflow-hidden">
			<div
				bind:this={carousel}
				class="scrollbar-hide flex h-full gap-6 overflow-x-auto scroll-smooth px-6 py-4"
				style="scroll-snap-type: x mandatory;"
			>
				{#each movies as movie, i}
					<a href="/admin">
						<div
							class="group/item relative h-full flex-shrink-0"
							style="scroll-snap-align: start; width: 300px;"
						>
							<img
								class="rounded-container-token h-full w-full object-cover shadow-lg transition-transform duration-300 group-hover/item:scale-105"
								src={`https://image.tmdb.org/t/p/original/${movie.posterUrl || movie.backDropPathUrl}`}
								alt={movie.title || `Movie ${i + 1}`}
								loading="lazy"
							/>

							<div
								class="rounded-container-token absolute inset-0 flex items-end bg-gradient-to-t from-black/80 to-transparent p-4 opacity-0 transition-opacity duration-300 group-hover/item:opacity-100"
							>
								<div class="text-white">
									<h3 class="mb-1 text-lg font-bold">{movie.title || 'Untitled'}</h3>
								</div>
							</div>
						</div>
					</a>
				{/each}
			</div>
		</div>
	</div>
</div>

<style>
	.scrollbar-hide::-webkit-scrollbar {
		display: none;
	}

	.scrollbar-hide {
		-ms-overflow-style: none;
		scrollbar-width: none;
	}

	.line-clamp-3 {
		display: -webkit-box;
		-webkit-box-orient: vertical;
		overflow: hidden;
	}
</style>
