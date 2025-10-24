<script lang="ts">
	import { page } from '$app/state';
	import type { MovieDto } from '$lib/types';
	import { onMount } from 'svelte';

	let data: MovieDto | null = $state(null);
	let loading: boolean = $state(true);

	onMount(async () => {
		const paramId = page.params.slug;
		const token = localStorage.getItem('token');

		try {
			const response = await fetch(`/api/admin/movie/${paramId}`, {
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}`
				}
			});

			if (!response.ok) {
				throw new Error('Result not found');
			}
			data = await response.json();
		} catch (error) {
			console.error(error);
		} finally {
			loading = false;
		}
	});
</script>

<section class="mx-auto my-5 max-w-5xl">
	<div class="card p-4">
		{#if loading}
			Loading...
		{:else if data}
			<div class="grid grid-cols-2 gap-x-2 card p-4 preset-outlined-primary-500">
				<div>
					<img
						class="h-[450px] w-full object-cover"
						src={`https://image.tmdb.org/t/p/original/${data.posterUrl}`}
						alt="Upcoming movies"
					/>
				</div>
				<div class="text-center">
					<h1 class="text-center">{data.title}</h1>
					<p>{data.description}</p>
				</div>
			</div>
		{/if}
	</div>
</section>
