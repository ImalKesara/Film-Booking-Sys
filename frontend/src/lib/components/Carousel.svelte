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

<div class="w-full space-y-12">
	<div
		class="relative h-[70vh] max-h-[700px] min-h-[500px] w-full overflow-hidden rounded-2xl shadow-2xl"
	>
		{#if movies.length > 0}
			{#each movies as movie, index}
				<div
					class="absolute inset-0 transition-opacity duration-1000 ease-in-out"
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
						class="absolute inset-0 bg-gradient-to-t from-black via-black/60 to-transparent"
					></div>

					<!-- Content -->
					<div class="absolute inset-0 flex items-end p-8 md:p-12 lg:p-16">
						<div class="max-w-3xl space-y-4 text-white">
							<div
								class="preset-filled-warning-500 mb-2 inline-block rounded-full px-4 py-1.5 text-sm font-bold shadow-lg"
							>
								Featured
							</div>
							<h2 class="text-4xl font-bold leading-tight drop-shadow-2xl md:text-5xl lg:text-6xl">
								{movie.title || 'Featured Movie'}
							</h2>
							{#if movie.description}
								<p
									class="line-clamp-3 max-w-2xl text-base opacity-95 drop-shadow-lg md:text-lg lg:text-xl"
								>
									{movie.description}
								</p>
							{/if}
							<div class="flex gap-4 pt-4">
								<button
									class="btn preset-filled-warning-500 transform font-semibold text-white shadow-xl transition-all duration-300 hover:scale-105"
								>
									<div class="flex items-center justify-center gap-x-1">
										<svg class="h-5 w-5" fill="currentColor" viewBox="0 0 24 24">
											<path d="M8 5v14l11-7z" />
										</svg>
										<p>Watch Now</p>
									</div>
								</button>
								<button
									class="rounded-lg border border-white/30 bg-white/20 px-8 py-3.5 font-semibold backdrop-blur-sm transition-all duration-300 hover:bg-white/30"
								>
									More Info
								</button>
							</div>
						</div>
					</div>
				</div>
			{/each}

			<div class="absolute bottom-6 left-1/2 z-10 flex -translate-x-1/2 gap-3">
				{#each movies as _, index}
					<button
						onclick={() => goToSlide(index)}
						class="h-1.5 rounded-full transition-all duration-300 hover:bg-white/90"
						class:w-12={currentSlide === index}
						class:w-8={currentSlide !== index}
						class:bg-white={currentSlide === index}
						class:bg-gray={currentSlide !== index}
						aria-label={`Go to slide ${index + 1}`}
					></button>
				{/each}
			</div>
		{/if}
	</div>

	<div class="group relative w-full">
		<button
			onclick={scrollLeft}
			class="absolute left-0 top-1/2 z-10 flex h-14 w-14 -translate-y-1/2 items-center justify-center rounded-full bg-black/70 text-white opacity-0 shadow-xl backdrop-blur-sm transition-all duration-300 hover:scale-110 hover:bg-black/90 group-hover:left-4 group-hover:opacity-100"
			aria-label="Scroll left"
		>
			<ArrowLeftIcon class="h-6 w-6" />
		</button>

		<button
			onclick={scrollRight}
			class="absolute right-0 top-1/2 z-10 flex h-14 w-14 -translate-y-1/2 items-center justify-center rounded-full bg-black/70 text-white opacity-0 shadow-xl backdrop-blur-sm transition-all duration-300 hover:scale-110 hover:bg-black/90 group-hover:right-4 group-hover:opacity-100"
			aria-label="Scroll right"
		>
			<ArrowRightIcon class="h-6 w-6" />
		</button>

		<div class="mx-auto mb-12 max-w-7xl px-6">
			<div class="text-center">
				<h1 class="mb-3 text-4xl font-bold">Coming Soon</h1>
				<p class="text-gray-600 dark:text-gray-400">Get ready for these upcoming blockbusters</p>
			</div>
			<div class="preset-filled-warning-500 mx-auto mt-6 h-1 w-32 rounded-full"></div>
		</div>

		<div class="relative overflow-hidden">
			<div
				bind:this={carousel}
				class="scrollbar-hide flex gap-6 overflow-x-auto scroll-smooth px-4 pb-6"
				style="scroll-snap-type: x mandatory;"
			>
				{#each movies as movie, i}
					<a
						href="/admin"
						class="group/item block flex-shrink-0"
						style="scroll-snap-align: start; width: 280px;"
					>
						<div
							class="relative h-[420px] overflow-hidden rounded-xl shadow-lg transition-all duration-300 hover:-translate-y-2 hover:shadow-2xl"
						>
							<img
								class="h-full w-full object-cover transition-transform duration-700 group-hover/item:scale-110"
								src={`https://image.tmdb.org/t/p/original/${movie.posterUrl || movie.backDropPathUrl}`}
								alt={movie.title || `Movie ${i + 1}`}
								loading="lazy"
							/>

							<div
								class="absolute inset-0 bg-gradient-to-t from-black via-transparent to-transparent opacity-70"
							></div>

							<div class="absolute right-4 top-4">
								<span
									class="preset-filled-warning-500 rounded-full px-3 py-1.5 text-xs font-bold text-white shadow-lg"
								>
									COMING SOON
								</span>
							</div>

							<div
								class="absolute inset-0 flex flex-col justify-end p-5 transition-all duration-300"
							>
								<div
									class="translate-y-2 transform transition-all duration-300 group-hover/item:translate-y-0"
								>
									<h3 class="mb-2 line-clamp-2 text-xl font-bold text-white drop-shadow-lg">
										{movie.title || 'Untitled'}
									</h3>

									<div
										class="space-y-3 opacity-0 transition-opacity duration-300 group-hover/item:opacity-100"
									>
										{#if movie.description}
											<p class="line-clamp-2 text-sm text-white/90">
												{movie.description}
											</p>
										{/if}
										<button
											class="flex w-full items-center justify-center gap-2 rounded-lg bg-white/90 py-2.5 font-semibold text-black transition-all duration-300 hover:bg-white"
										>
											<svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
												<path
													stroke-linecap="round"
													stroke-linejoin="round"
													stroke-width="2"
													d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9"
												></path>
											</svg>
											Get Notified
										</button>
									</div>
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

	.line-clamp-2 {
		display: -webkit-box;
		-webkit-line-clamp: 2;
		-webkit-box-orient: vertical;
		overflow: hidden;
	}

	.line-clamp-3 {
		display: -webkit-box;
		-webkit-line-clamp: 3;
		-webkit-box-orient: vertical;
		overflow: hidden;
	}
</style>
