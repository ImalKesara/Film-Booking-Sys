<script lang="ts">
	import type { MovieDto } from '$lib/types';

	let { movies }: { movies: MovieDto[] } = $props();
	const filteredMovieShows = movies.filter((mov) => mov.movieShows.length > 0);
</script>

<main class="w-full py-12">
	<!-- Section Header -->
	<div class="mx-auto mb-12 max-w-7xl px-6">
		<div class="text-center">
			<h1 class="mb-3 text-4xl font-bold">Now Showing</h1>
			<p class="text-gray-600 dark:text-gray-400">Book your seats for these amazing movies</p>
		</div>
		<div class="mx-auto mt-6 h-1 w-32 preset-filled-warning-500 rounded-full"></div>
	</div>

	<!-- Movies Grid -->
	<div class="mx-auto mb-20 max-w-7xl px-6">
		<div class="grid grid-cols-1 gap-8 md:grid-cols-2">
			{#each filteredMovieShows as movie}
				<a
					href="/movie/{movie.movieId}"
					class="group relative overflow-hidden rounded-2xl shadow-xl transition-all duration-500 hover:-translate-y-2 hover:shadow-2xl"
				>
					<!-- Movie Image -->
					<div class="relative aspect-[16/9] overflow-hidden">
						<img
							class="h-full w-full object-cover transition-transform duration-700 group-hover:scale-110"
							src={`https://image.tmdb.org/t/p/original/${movie.backDropPathUrl}`}
							alt={movie.title || 'Featured movie'}
							loading="lazy"
						/>

						<!-- Dark Gradient Overlay -->
						<div
							class="absolute inset-0 bg-gradient-to-t from-black via-black/60 to-transparent opacity-80 transition-opacity duration-300 group-hover:opacity-90"
						></div>

						<!-- Play Icon (appears on hover) -->
						<div
							class="absolute inset-0 flex items-center justify-center opacity-0 transition-all duration-300 group-hover:opacity-100"
						>
							<div
								class="flex h-20 w-20 scale-75 transform items-center justify-center rounded-full bg-red-600 shadow-2xl transition-transform duration-300 group-hover:scale-100"
							>
								<svg class="ml-1 h-10 w-10 text-white" fill="currentColor" viewBox="0 0 24 24">
									<path d="M8 5v14l11-7z" />
								</svg>
							</div>
						</div>

						<!-- Now Showing Badge -->
						<div class="absolute right-4 top-4">
							<span
								class="flex items-center gap-2 rounded-full bg-green-500 px-4 py-2 text-xs font-bold text-white shadow-lg backdrop-blur-sm"
							>
								<span class="h-2 w-2 animate-pulse rounded-full bg-white"></span>
								SHOWING
							</span>
						</div>
					</div>

					<!-- Movie Info -->
					<div class="absolute bottom-0 left-0 right-0 transform p-6 transition-all duration-300">
						<h2 class="mb-2 line-clamp-2 text-2xl font-bold text-white drop-shadow-lg md:text-3xl">
							{movie.title}
						</h2>

						<!-- Additional Info (shows on hover) -->
						<div
							class="translate-y-4 transform opacity-0 transition-all duration-300 group-hover:translate-y-0 group-hover:opacity-100"
						>
							{#if movie.description}
								<p class="mb-4 line-clamp-2 text-sm text-white/90 drop-shadow-md">
									{movie.description}
								</p>
							{/if}

							<div class="mb-4 flex items-center gap-4">
								{#if movie.movieShows && movie.movieShows.length > 0}
									<div class="flex items-center gap-2 text-sm text-white/90">
										<svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
											<path
												stroke-linecap="round"
												stroke-linejoin="round"
												stroke-width="2"
												d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"
											></path>
										</svg>
										<span>{movie.movieShows.length} Showtimes</span>
									</div>
								{/if}
								<div class="flex items-center gap-2 text-sm text-white/90">
									<svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
										<path
											stroke-linecap="round"
											stroke-linejoin="round"
											stroke-width="2"
											d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"
										></path>
									</svg>
									<span>2h 15m</span>
								</div>
							</div>

							<button
								class="flex w-full items-center justify-center gap-2 rounded-lg preset-filled-warning-500 py-3 font-semibold text-white shadow-lg transition-all duration-300 hover:preset-filled-warning-600"
							>
								<svg class="h-5 w-5" fill="currentColor" viewBox="0 0 24 24">
									<path
										d="M15 5v2m0 4v2m0 4v2M5 5a2 2 0 00-2 2v3a2 2 0 110 4v3a2 2 0 002 2h14a2 2 0 002-2v-3a2 2 0 110-4V7a2 2 0 00-2-2H5z"
									/>
								</svg>
								Book Tickets
							</button>
						</div>
					</div>
				</a>
			{/each}
		</div>

		<!-- Empty State -->
		{#if filteredMovieShows.length === 0}
			<div class="py-20 text-center">
				<div
					class="mx-auto mb-6 flex h-32 w-32 items-center justify-center rounded-full bg-gray-100 dark:bg-gray-800"
				>
					<svg
						class="h-16 w-16 text-gray-400"
						fill="none"
						stroke="currentColor"
						viewBox="0 0 24 24"
					>
						<path
							stroke-linecap="round"
							stroke-linejoin="round"
							stroke-width="2"
							d="M15 10l4.553-2.276A1 1 0 0121 8.618v6.764a1 1 0 01-1.447.894L15 14M5 18h8a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v8a2 2 0 002 2z"
						></path>
					</svg>
				</div>
				<h3 class="mb-2 text-2xl font-bold text-gray-700 dark:text-gray-300">
					No Movies Currently Showing
				</h3>
				<p class="text-gray-500">Check back soon for new releases</p>
			</div>
		{/if}
	</div>
</main>

<style>
	.line-clamp-2 {
		display: -webkit-box;
		-webkit-line-clamp: 2;
		-webkit-box-orient: vertical;
		overflow: hidden;
	}

	@keyframes pulse {
		0%,
		100% {
			opacity: 1;
		}
		50% {
			opacity: 0.5;
		}
	}

	.animate-pulse {
		animation: pulse 2s cubic-bezier(0.4, 0, 0.6, 1) infinite;
	}
</style>
