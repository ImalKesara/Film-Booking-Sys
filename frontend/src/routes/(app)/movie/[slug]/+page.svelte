<script lang="ts">
	import { goto } from '$app/navigation';
	import { page } from '$app/state';
	import { auth } from '$lib/states/auth.svelte';
	import type { MovieDto } from '$lib/types';
	import { Timer } from '@lucide/svelte';
	import { onMount } from 'svelte';

	const slug = page.params.slug;
	let data: MovieDto | null = $state(null);

	const handleShowSelection = (showId: number) => {
		if (auth.isAuthenticated && auth.isAdmin) {
			goto('/admin');
			return;
		}

		if (!auth.isAuthenticated) {
			const intendedUrl = `/booking/${showId}?slug=${slug}`;
			sessionStorage.setItem('redirectAfterLogin', intendedUrl);
			goto('/login');
		} else {
			goto(`/booking/${showId}?slug=${slug}`);
		}
	};

	const formatTime = (timeString: string) => {
		return timeString.substring(0, 5);
	};

	onMount(async () => {
		const response = await fetch(`/api/user/movie/${slug}`);
		if (!response.ok) {
			throw new Error('Something wrong !!!!');
		}
		data = await response.json();
	});
</script>

<section class="relative min-h-[70vh] overflow-hidden">
	<div class="absolute inset-0">
		<img
			class="h-full w-full object-cover"
			src={`https://image.tmdb.org/t/p/original/${data?.backDropPathUrl}`}
			alt={data?.title || 'Featured movie'}
		/>
		<div
			class="absolute inset-0 bg-gradient-to-t from-gray-900 via-gray-900/80 to-transparent"
		></div>
	</div>

	<div class="container relative mx-auto px-4 py-16">
		<div
			class="flex flex-col items-center justify-center gap-8 md:flex-row md:items-end md:justify-start"
		>
			<div class="w-64 flex-shrink-0 transform transition-transform duration-300 hover:scale-105">
				<img
					class="w-full rounded-lg shadow-2xl ring-4 ring-white/10"
					src={`https://image.tmdb.org/t/p/w500/${data?.posterUrl}`}
					alt={data?.title}
				/>
			</div>

			<!-- Movie Info -->
			<div class="flex-1 text-center md:text-left">
				<h1 class="mb-4 text-5xl font-bold text-white md:text-6xl">
					{data?.title || 'Loading...'}
				</h1>

				<div class="mb-6 flex flex-wrap items-center justify-center gap-4 md:justify-start">
					<div class="flex items-center gap-2 rounded-full bg-white/10 px-4 py-2 backdrop-blur-sm">
						<svg class="h-5 w-5 text-yellow-400" fill="currentColor" viewBox="0 0 20 20">
							<path
								d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"
							/>
						</svg>
						<span class="font-semibold text-white">{data?.rating || 'N/A'}</span>
					</div>

					{#if data?.tmdbId}
						<span class="rounded-full bg-white/10 px-4 py-2 text-sm text-white backdrop-blur-sm">
							TMDB ID: {data.tmdbId}
						</span>
					{/if}
				</div>

				<p class="mb-8 max-w-3xl text-lg leading-relaxed text-white/90">
					{data?.description || ''}
				</p>
			</div>
		</div>
	</div>
</section>

{#if data?.movieShows && data.movieShows.length > 0}
	<section class="bg-gradient-to-b from-gray-900 to-gray-800 py-16">
		<div class="container mx-auto px-4">
			<div class="mb-12 text-center">
				<h2 class="mb-2 text-4xl font-bold text-white">Book Your Tickets</h2>
				<p class="text-gray-400">Select your preferred showtime</p>
			</div>

			<div class="mx-auto max-w-4xl">
				{#if data.movieShows.length > 0}
					<div class="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
						{#each data.movieShows as show}
							<button onclick={() => handleShowSelection(show.showId)}>
								<div class="card preset-outlined-warning-500 p-4">
									<div class="relative z-10">
										<div class="mb-4 flex items-center justify-between">
											<div class="flex items-center gap-2">
												<span
													class="flex items-center justify-center gap-1 text-2xl font-bold text-white"
													><Timer /> {formatTime(show.showTime)}</span
												>
											</div>
											<div class="rounded-full bg-green-500/20 px-3 py-1">
												<span class="text-sm font-semibold text-green-400"
													>{show.availableSeats} seats</span
												>
											</div>
										</div>
									</div>

									<div class="flex items-center justify-between">
										<div>
											<p class="text-sm text-gray-400">Price</p>
											<p class="text-xl font-bold text-white">LKR {show.price.toFixed(2)}</p>
										</div>
									</div>
								</div>
							</button>
						{/each}
					</div>
				{/if}
			</div>
		</div>
	</section>
{/if}
